package edu.chalmers.axen2021.controller;

import edu.chalmers.axen2021.utils.StringUtils;
import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the applications lagenhetsDataItem.
 * @author Oscar Arvidson
 * @author Sam Salek
 * @author Malte Åkvist
 */
@FXMLController
public class ApartmentItemController implements Initializable {

    private RootController rootController = RootController.getInstance();

    /**
     * MenuButton in the .fxml for choosing apartment type.
     */
    @FXML private MenuButton apartmentTypeMenuButton;

    /**
     * TextField in the .fxml for BOA input.
     */
    @FXML private TextField BOATextField;

    /**
     * TextField in the .fxml for amount input.
     */
    @FXML private TextField amountTextField;

    /**
     * Apartment for this controller's instance.
     */
    private ApartmentItem apartmentItem;

    /**
     * Constructor for ApartmentItemController.
     * @param apartmentItem Apartment.
     */
    public ApartmentItemController(ApartmentItem apartmentItem) {
        this.apartmentItem = apartmentItem;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInitialValues();
        initMenuButtonProperties();
        initBOATextField();
        initAmountTextField();
    }

    /**
     * Sets the initial values for the .fxml nodes.
     */
    private void setInitialValues() {
        // Set its value to the one from the model if it isn't null.
        if(apartmentItem.getApartmentType() != null) {
            apartmentTypeMenuButton.setText(apartmentItem.getApartmentType());
        }
        BOATextField.setText(StringUtils.removeTrailingZeros(apartmentItem.getBOA()));
        amountTextField.setText(String.valueOf(apartmentItem.getAmount()));
    }

    /**
     * Init method for the MenuButtons properties.
     * Makes it possible to switch between apartment types.
     */
    private void initMenuButtonProperties() {
        for (MenuItem menuItem : apartmentTypeMenuButton.getItems()) {
            menuItem.setOnAction(actionEvent -> {
                apartmentTypeMenuButton.setText(menuItem.getText());
                apartmentItem.setApartmentType(menuItem.getText());
            });
        }
    }

    /**
     * Initializes the BOATextField by adding listeners.
     */
    private void initBOATextField() {
        //Adds property to TextField allowing users to only input numbers and ".".
        BOATextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*" + "[.]?" + "[0-9]*")){
                BOATextField.setText(oldValue);
            }
        });

        // Adds focus lost property to textFields.
        BOATextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue){
                //Make sure that the textField has a readable value.
                if(BOATextField.getText().equals("") || BOATextField.getText().equals(".")){
                    BOATextField.setText("0.0");
                }
                // Remove unnecessary zeroes
                BOATextField.setText(StringUtils.removeTrailingZeros(Double.parseDouble(BOATextField.getText())));
                apartmentItem.setBOA(Double.parseDouble(BOATextField.getText()));
            }
        });
    }

    /**
     * Initializes the amountTextField by adding listeners.
     */
    private void initAmountTextField() {
        // Adds property to TextField allowing users to only input numbers and.
        amountTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")){
                amountTextField.setText(oldValue);
            }
        });

        // Adds focus lost property to textFields.
        amountTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue){
                //Make sure that the textField has a readable value.
                if(amountTextField.getText().equals("")){
                    amountTextField.setText("0");
                }
                // Remove unnecessary zeroes
                amountTextField.setText(StringUtils.removeTrailingZeros(Double.parseDouble(amountTextField.getText())));
                apartmentItem.setAmount(Integer.parseInt(amountTextField.getText()));
            }
        });
    }

    @FXML
    private void openRemoveConfirmation(){
        rootController.openConfirmationView(apartmentItem, EventHandlerObjects.APARTMENT_TYPE);
    }
}