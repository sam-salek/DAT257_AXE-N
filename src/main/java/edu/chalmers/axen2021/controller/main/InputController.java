package edu.chalmers.axen2021.controller.main;

import edu.chalmers.axen2021.controller.FXMLController;
import edu.chalmers.axen2021.controller.RootController;
import edu.chalmers.axen2021.controller.items.ApartmentItemController;
import edu.chalmers.axen2021.controller.items.ApartmentItemControllerFactory;
import edu.chalmers.axen2021.model.managers.SaveManager;
import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import edu.chalmers.axen2021.model.Category;
import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller class for the applications inputView.fxml.
 * Handles all event triggered in the inputView.
 * @author Oscar Arvidson
 * @author Erik Wetter
 * @author Sam Salek
 * @author Malte Åkvist
 */
@FXMLController
public class InputController implements Initializable {

    //FXML fx:id for all the TextFields.
    @FXML private TextField tomtkostnaderKkr;
    @FXML private TextField tomtkostnaderKrBoa;
    @FXML private TextField tomtkostnaderKrBta;
    @FXML private TextField nedlagdaBygherreKkr;
    @FXML private TextField nedlagdaBygherreKrBoa;
    @FXML private TextField nedlagdaBygherreKrBta;
    @FXML private TextField anslutningarKkr;
    @FXML private TextField anslutningarKrBoa;
    @FXML private TextField anslutningarKrBta;
    @FXML private TextField byggherrekostnaderKkr;
    @FXML private TextField byggherrekostnaderKrBoa;
    @FXML private TextField byggherrekostnaderKrBta;
    @FXML private TextField entreprenadKkr;
    @FXML private TextField entreprenadKrBoa;
    @FXML private TextField entreprenadKrBta;
    @FXML private TextField oforutsettKkr;
    @FXML private TextField oforutsettKrBoa;
    @FXML private TextField oforutsettKrBta;
    @FXML private TextField finanisellaKostnaderKkr;
    @FXML private TextField finanisellaKostnaderKrBoa;
    @FXML private TextField finanisellaKostnaderKrBta;
    @FXML private TextField mervardeskattKkr;
    @FXML private TextField mervardeskattKrBoa;
    @FXML private TextField mervardeskattKrBta;
    @FXML private TextField investeringsstodKkr;
    @FXML private TextField investeringsstodKrBoa;
    @FXML private TextField investeringsstodKrBta;
    @FXML private TextField projektkostnadKkr;
    @FXML private TextField projektkostnadKrBoa;
    @FXML private TextField projektkostnadKrBta;

    @FXML private TextField hyresintakterMedStod;
    @FXML private TextField hyresintakterUtanStod;
    @FXML private TextField driftUnderhallMedStod;
    @FXML private TextField driftUnderhallUtanStod;
    @FXML private TextField tomtrattsavgaldMedStod;
    @FXML private TextField tomtrattsavgaldUtanStod;
    @FXML private TextField driftnettoMedStod;
    @FXML private TextField driftnettoUtanStod;
    @FXML private TextField yieldMedStod;
    @FXML private TextField yieldUtanStod;
    @FXML private TextField marknadsvardeMedStod;
    @FXML private TextField marknadsvardeUtanStod;

    @FXML private TextField investeringsstod;
    @FXML private TextField antagenPresumtionshyra;
    @FXML private TextField normHyraMedStod;
    @FXML private TextField totalLjusBta;
    @FXML private TextField totalBoa;
    @FXML private TextField oforutsett;
    @FXML private TextField tomtPercent;

    @FXML private Label titleLabel;

    /**
     * Decimal formatter removing decimals
     */
    private DecimalFormat df;
    /**
     * Decimal formatter for percent labels
     */
    private DecimalFormat dfPercent;


    /**
     * Instance of the project manager.
     */
    private final ProjectManager projectManager = ProjectManager.getInstance();

    /**
     * Parent controller
     */
    private final RootController rootController = RootController.getInstance();

    /**
     * List of all input TextFields.
     */
    private final ArrayList<TextField> inputFields = new ArrayList<TextField>();
    /**
     * List of all percent input TextFields.
     */
    private final ArrayList<TextField> inputFieldsPercent = new ArrayList<TextField>();

    /**
     * Vbox in the inputView containing lagenhetsDataSummaryItems.
     */
    @FXML private VBox lagenhetsTypVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        df = new DecimalFormat("#");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));

        dfPercent = new DecimalFormat("#.###");
        dfPercent.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));

        initInputFieldsList();
        initTextFieldProperties();
    }

    /**
     * Init method for input TextFields properties.
     * Adds focus lost property.
     * Adds only allowing doubles property.
     */
    private void initTextFieldProperties(){

        for(TextField textField: inputFields){

            //Adds property to TextField allowing users to only input numbers and ",".
            textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                if(!newValue.matches("[0-9]*" + "[,]?" + "[0-9]*")){
                    textField.setText(oldValue);
                }
            });

            //Adds focus lost property to textFields.
            textField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
                if(!newValue){
                    //Make sure that the textField has a readable value.
                    if(textField.getText().equals("") || textField.getText().equals(",")){
                        textField.setText("0");
                    }
                    setInputFields();
                    rootController.updateAllLabels();
                }
            });

            // Adds 'Enter' key event
            textField.setOnKeyPressed(keyEvent -> {
                // If key pressed was 'Enter'..
                if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                    //Make sure that the textField has a readable value.
                    if(textField.getText().equals("") || textField.getText().equals(",")){
                        textField.setText("0");
                    }
                    setInputFields();
                    rootController.updateAllLabels();
                }
            });
        }
        for(TextField textField: inputFieldsPercent){

            //Adds property to TextField allowing users to only input percent numbers, "," and "%".
            textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                if(!newValue.matches("((100)|[0-9]{0,2}([,][0-9]{0,3})?)[%]?")){
                    textField.setText(oldValue);
                }
            });

            //Adds focus lost property to textFields.
            textField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
                if(!newValue){
                    //Make sure that the textField has a readable value.
                    if(textField.getText().equals("") || textField.getText().equals(",")){
                        textField.setText("0");
                    }
                    setInputFields();
                    rootController.updateAllLabels();
                }
            });

            // Adds 'Enter' key event
            textField.setOnKeyPressed(keyEvent -> {
                // If key pressed was 'Enter'..
                if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                    //Make sure that the textField has a readable value.
                    if(textField.getText().equals("") || textField.getText().equals(",")){
                        textField.setText("0");
                    }
                    setInputFields();
                    rootController.updateAllLabels();
                }
            });
        }
    }

    /**
     * Initialize list of all input TextFields.
     */
    private void initInputFieldsList(){
        inputFields.add(investeringsstod);
        inputFields.add(antagenPresumtionshyra);
        inputFields.add(normHyraMedStod);
        inputFields.add(totalLjusBta);
        inputFieldsPercent.add(yieldMedStod);
        inputFieldsPercent.add(yieldUtanStod);
        inputFieldsPercent.add(oforutsett);
        inputFieldsPercent.add(tomtPercent);
    }

    /**
     * Updates title label of the page to the name of the current project.
     */
    public void updateTitle(){
        titleLabel.setText("Detaljer: " + projectManager.getActiveProject().getName());
    }

    /**
     * Change view to summaryView.
     */
    @FXML private void switchToSummaryView(){
        rootController.updateSummaryView();
    }

    /**
     * Method for opening the modalWindow.
     * Puts the modalWindowAnchorPane to front in scene.
     * @param event that triggered the method.
     */
    @FXML
    private void categoryButtonClicked(ActionEvent event) {
        String fxid = ((Node) event.getSource()).getId();
        Category category = Category.fromButtonFxid(fxid);
        rootController.openModalWindow(category);
    }

    /**
     * Method for adding a new lagenhetsDataSummaryItem.
     * Adds lagenhetsDataSummaryItem to Vbox in inputView.
     * @param event that triggered the method.
     */
    @FXML
    private void addNewApartmentItem(ActionEvent event) {
        ApartmentItem newApartmentItem = projectManager.getActiveProject().addApartmentItem();
        createNewLagenhetstypView(newApartmentItem);
        updateAllTextFields();
        SaveManager.getInstance().saveProject(projectManager.getActiveProject());
    }

    /**
     * Populates the input view with apartmentItems views from the current project.
     */
    public void populateApartmentItems() {
        for (ApartmentItem apartmentItem : projectManager.getActiveProject().getApartmentItems()) {
            createNewLagenhetstypView(apartmentItem);
        }
    }

    /**
     * Remove all apartmentItems in the apartmentTypesView of the window.
     */
    public void clearApartmentItems() {
        ApartmentItemControllerFactory.clearInstances();
        lagenhetsTypVbox.getChildren().clear();
    }

    /**
     * Creates new apartment object and adds it to the view.
     * @param newApartmentItem The apartmentItem.
     */
    private void createNewLagenhetstypView(ApartmentItem newApartmentItem) {
        FXMLLoader apartmentTypeFXML = new FXMLLoader(getClass().getResource("/fxml/lagenhetsDataItem.fxml"));
        ApartmentItemController apartmentItemController = ApartmentItemControllerFactory.create(newApartmentItem);
        apartmentTypeFXML.setController(apartmentItemController);
        Node apartmentTypeNode = null;

        try {
            apartmentTypeNode = apartmentTypeFXML.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lagenhetsTypVbox.getChildren().add(0, apartmentTypeNode);
    }

    /**
     * Updates all TextFields.
     */
    public void updateAllTextFields(){
        updateGrundforutsattingar();
        updateTomtkostnader();
        updateNedlagadaByggherre();
        updateAnslutningar();
        updateByggherrekostnader();
        updateEntreprenad();
        updateOforutsett();
        updateFinansiellaKostnader();
        updateMervardeskatt();
        updateInvesteringsstod();
        updateProjektkostnad();
        updateFastighetsvardeOchResultat();
    }

    /**
     * Updates all TextFields related to Grundforutsattningar.
     */
    private void updateGrundforutsattingar(){
        normHyraMedStod.setText(df.format(projectManager.getActiveProject().getNormhyraMedStod()));
        investeringsstod.setText(df.format(projectManager.getActiveProject().getInvesteringsstod()));
        antagenPresumtionshyra.setText(df.format(projectManager.getActiveProject().getAntagenPresumtionshyra()));
        totalBoa.setText(StringUtils.separateKkr(projectManager.getActiveProject().getTotalBoa()));
        totalLjusBta.setText(df.format(projectManager.getActiveProject().getTotalLjusBta()));
        oforutsett.setText(dfPercent.format(projectManager.getActiveProject().getOforutsettPercent()) + "%");
    }

    /**
     * Update all TextFields related to Tomtkostnader.
     */
    private void updateTomtkostnader(){
        tomtkostnaderKkr.setText(StringUtils.separateKkr(projectManager.getActiveProject().getTomtkostnaderKkr()));
        tomtkostnaderKrBoa.setText(StringUtils.separateKkr(projectManager.getActiveProject().getTomtkostnaderKrBoa()));
        tomtkostnaderKrBta.setText(StringUtils.separateKkr(projectManager.getActiveProject().getTomtkostnaderKrBta()));
    }

    /**
     * Update all TextFields related to NedlagdaByggherre.
     */
    private void updateNedlagadaByggherre(){
        nedlagdaBygherreKkr.setText(StringUtils.separateKkr(projectManager.getActiveProject().getNedlagdaByggherreKkr()));
        nedlagdaBygherreKrBoa.setText(StringUtils.separateKkr(projectManager.getActiveProject().getNedlagdaByggherreKrBoa()));
        nedlagdaBygherreKrBta.setText(StringUtils.separateKkr(projectManager.getActiveProject().getNedlagdaByggherreKrBta()));
    }

    /**
     * Update all TextFields related to Ansultningar.
     */
    private void updateAnslutningar(){
        anslutningarKkr.setText(StringUtils.separateKkr(projectManager.getActiveProject().getAnslutningarKkr()));
        anslutningarKrBoa.setText(StringUtils.separateKkr(projectManager.getActiveProject().getAnslutningarKrBoa()));
        anslutningarKrBta.setText(StringUtils.separateKkr(projectManager.getActiveProject().getAnslutningarKrBta()));
    }

    /**
     * Update all TextFields related to Byggherrekostnader.
     */
    private void updateByggherrekostnader(){
        byggherrekostnaderKkr.setText(StringUtils.separateKkr(projectManager.getActiveProject().getByggherrekostnaderKkr()));
        byggherrekostnaderKrBoa.setText(StringUtils.separateKkr(projectManager.getActiveProject().getByggherrekostnaderKrBoa()));
        byggherrekostnaderKrBta.setText(StringUtils.separateKkr(projectManager.getActiveProject().getByggherrekostnaderKrBta()));
    }

    /**
     * Update all TextFields related to Entreprenad.
     */
    private void updateEntreprenad(){
        entreprenadKkr.setText(StringUtils.separateKkr(projectManager.getActiveProject().getEntreprenadKkr()));
        entreprenadKrBoa.setText(StringUtils.separateKkr(projectManager.getActiveProject().getEntreprenadKrBoa()));
        entreprenadKrBta.setText(StringUtils.separateKkr(projectManager.getActiveProject().getEntreprenadKrBta()));
    }

    /**
     * Update all TextFields related to Oforutsett.
     */
    private void updateOforutsett(){
        oforutsettKkr.setText(StringUtils.separateKkr(projectManager.getActiveProject().getOforutsettKkr()));
        oforutsettKrBoa.setText(StringUtils.separateKkr(projectManager.getActiveProject().getOforutsettKrBoa()));
        oforutsettKrBta.setText(StringUtils.separateKkr(projectManager.getActiveProject().getOforutsettKrBta()));
    }

    /**
     * Update all TextFields related to FinansiellaKostnader.
     */
    private void updateFinansiellaKostnader(){
        finanisellaKostnaderKkr.setText(StringUtils.separateKkr(projectManager.getActiveProject().getFinansiellaKostnaderKkr()));
        finanisellaKostnaderKrBoa.setText(StringUtils.separateKkr(projectManager.getActiveProject().getFinansiellaKostnaderKrBoa()));
        finanisellaKostnaderKrBta.setText(StringUtils.separateKkr(projectManager.getActiveProject().getFinansiellaKostnaderKrBta()));
    }

    /**
     * Update all TextFields related to Mervardeskatt.
     */
    private void updateMervardeskatt(){
        mervardeskattKkr.setText(StringUtils.separateKkr(projectManager.getActiveProject().getMervardeskattKkr()));
        mervardeskattKrBoa.setText(StringUtils.separateKkr(projectManager.getActiveProject().getMervardeskattKrBoa()));
        mervardeskattKrBta.setText(StringUtils.separateKkr(projectManager.getActiveProject().getMervardeskattKrBta()));
    }

    /**
     * Update all TextFields related to Investeringsstod.
     */
    private void updateInvesteringsstod(){
        investeringsstodKkr.setText(StringUtils.separateKkr(projectManager.getActiveProject().getInvesteringsstodKkr()));
        investeringsstodKrBoa.setText(StringUtils.separateKkr(projectManager.getActiveProject().getInvesteringsstodKrBoa()));
        investeringsstodKrBta.setText(StringUtils.separateKkr(projectManager.getActiveProject().getInvesteringsstodKrBta()));
    }

    /**
     * Update all TextFields related to Projektkostnad.
     */
    private void updateProjektkostnad(){
        projektkostnadKkr.setText(StringUtils.separateKkr(projectManager.getActiveProject().getProjektkostnadKkrMedStod()));
        projektkostnadKrBoa.setText(StringUtils.separateKkr(projectManager.getActiveProject().getProjektkostnadKrBoa()));
        projektkostnadKrBta.setText(StringUtils.separateKkr(projectManager.getActiveProject().getProjektkostnadKrBta()));
    }

    /**
     * Update all TextFields related to Fastighetsvarde and Resultat.
     */
    private void updateFastighetsvardeOchResultat(){
        hyresintakterMedStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getHyresintakterMedStod()));
        hyresintakterUtanStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getHyresintakterUtanStod()));
        driftUnderhallMedStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getDriftUnderhallMedStod()));
        driftUnderhallUtanStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getDriftUnderhallUtanStod()));
        tomtPercent.setText(dfPercent.format(projectManager.getActiveProject().getTomtPercent()) + "%");
        tomtrattsavgaldMedStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getTomtrattsavgaldMedStod()));
        tomtrattsavgaldUtanStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getTomtrattsavgaldUtanStod()));
        driftnettoMedStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getDriftnettoMedStod()));
        driftnettoUtanStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getDriftnettoUtanStod()));
        yieldMedStod.setText(dfPercent.format(projectManager.getActiveProject().getYieldMedStod()) + "%");
        yieldUtanStod.setText(dfPercent.format(projectManager.getActiveProject().getYieldUtanStod()) + "%");
        marknadsvardeMedStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getMarknadsvardeMedStod()));
        marknadsvardeUtanStod.setText(StringUtils.separateKkr(projectManager.getActiveProject().getMarknadsvardeUtanStod()));
    }

    /**
     * Set all new values from the inputFields.
     */
    private void setInputFields(){
        projectManager.getActiveProject().setNormhyraMedStod(StringUtils.convertToDouble(normHyraMedStod.getText()));
        projectManager.getActiveProject().setInvesteringsstod(StringUtils.convertToDouble(investeringsstod.getText()));
        projectManager.getActiveProject().setAntagenPresumtionshyra(StringUtils.convertToDouble(antagenPresumtionshyra.getText()));
        projectManager.getActiveProject().setTotalLjusBta(StringUtils.convertToDouble(totalLjusBta.getText()));
        projectManager.getActiveProject().setYieldMedStod(StringUtils.convertToDouble(yieldMedStod.getText()));
        projectManager.getActiveProject().setYieldUtanStod(StringUtils.convertToDouble(yieldUtanStod.getText()));
        projectManager.getActiveProject().setOforutsettPercent(StringUtils.convertToDouble(oforutsett.getText()));
        projectManager.getActiveProject().setTomtPercent(StringUtils.convertToDouble(tomtPercent.getText()));
    }
}
