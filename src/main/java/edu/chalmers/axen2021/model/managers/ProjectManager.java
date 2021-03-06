package edu.chalmers.axen2021.model.managers;

import edu.chalmers.axen2021.model.Category;
import edu.chalmers.axen2021.model.projectdata.CostItem;
import edu.chalmers.axen2021.model.projectdata.Project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Class used to manage projects such as how many cost categories there should be.
 * Implements the 'Serializable' interface to allow serialization (saving) of this classes data.
 * @author Sam Salek
 * @author Malte Åkvist
 */
public class ProjectManager implements Serializable {

    /**
     * Used when deserializing, acts as this class' ID.
     */
    private static final long serialVersionUID = 10L;

    /**
     * The instance of this class.
     */
    private transient static ProjectManager instance = null;

    /**
     * A list of all projects during runtime.
     * Is updated when a new Project is created, and also when program startup.
     * Should not contain duplicates.
     */
    private transient static ArrayList<Project> projects;

    /**
     * The active project.
     */
    private transient Project activeProject;

    /**
     * The active category.
     */
    private transient Category activeCategory;

    /**
     * A map containing all category lists
     */
    private final HashMap<Category, ArrayList<String>> costItemNamesMap = new HashMap<>();

    // Private constructor because Singleton class. Use getInstance() instead.
    private ProjectManager(){
        projects = new ArrayList<>();
        initCostItemNamesMap();
    }

    /**
     * This class acts as a Singleton.
     * Returns the instance of the class.
     * @return Instance of class.
     */
    public static ProjectManager getInstance() {
        if(instance == null) {

            if(!SaveManager.getInstance().projectManagerExists()) {
                instance = new ProjectManager();
            } else {
                instance = SaveManager.getInstance().readProjectManager();
                projects = new ArrayList<>();
            }
        }
        return instance;
    }

    /**
     * Initializes the map by creating an ArrayList for each category.
     */
    private void initCostItemNamesMap() {
        for (Category category : Category.values()) {
            costItemNamesMap.put(category, new ArrayList<>());
        }
    }

    /**
     * Loads the projects existing in the save directory into the application.
     */
    public void loadProjects() {
        ArrayList<Project> readProjects = SaveManager.getInstance().readProjects();
        Collections.reverse(readProjects);

        // If no projects were read then don't continue.
        if(readProjects.size() == 0) {
            return;
        }

        for (Project project : readProjects) {
            addProject(project);      // Add project to 'projects' list during load.
        }
    }

    /**
     * Used to add a project to the 'projects' list.
     * @param project The project to be added.
     */
    public void addProject(Project project) {
        if(projects.contains(project)) {
            throw new IllegalArgumentException("This project already exists in the 'projects' list!");
        }

        projects.add(project);
    }

    /**
     * Removes a project from the application.
     * @param projectName The name of the project to remove.
     * @return Returns True if remove was successful, False if not.
     */
    public boolean removeProject(String projectName) {
        for(Project p: projects){
            if(p.getName().equals(projectName)){
                projects.remove(p);
                return SaveManager.getInstance().removeProjectFile(p);
            }
        }
        throw new IllegalArgumentException("This project does not exist in 'projects' list!");
    }

    /**
     * Getter method used to get project from project name.
     * @param projectName The project that should be returned from projectName.
     */
    public Project getProject(String projectName) {
        if(projectName == null) {
            return null;
        }

        for (Project p : projects) {
            if(p.getName().equals(projectName)) {
                return p;
            }
        }

        // Throws exception if it did not return in the if-statement above.
        throw new IllegalArgumentException("Could not find a project with name \"" + projectName + "\"!");
    }

    /**
     * Setter method used to set activeProject variable
     * @param project the project that should be active
     */
    public void setActiveProject(String project) {
        if(project == null) {
            activeProject = null;
            return;
        }

        for (Project p : projects) {
            if(p.getName().equals(project)) {
                activeProject = p;
                System.out.println("Set project \"" + p.getName() + "\" as the active project!");
                return;
            }
        }

        // Throws exception if it did not return in the if-statement above.
        throw new IllegalArgumentException("Could not set active project from project button name (button name is not a project)");
    }

    /**
     * Changes the name of a cost item.
     * @param category The category where the cost item resides.
     * @param currentName The current name of the cost item.
     * @param newName The new name for the cost item.
     */
    public void changeCostItemName(Category category, String currentName, String newName) {

        // Change cost item name in every project...
        for (Project project : projects) {
            // For every cost item in the given category...if its name is same as the currentName...
            for (CostItem costItem : project.getCostItemsMap().get(category)) {
                if (costItem.getName().equals(currentName)) {

                    // Change cost item name and remove old name from ProjectManager.
                    costItem.setName(newName);
                    ProjectManager.getInstance().getCostItemNamesMap().get(category).remove(currentName);

                    // Only add cost item name to ProjectManager if ProjectManager doesn't already contain it in the active category.
                    // Used to avoid duplicate cost item names in the same categories,
                    // hence avoiding duplicate cost items when populating the category window.
                    if(!ProjectManager.getInstance().getCostItemNamesMap().get(category).contains(newName)) {
                        ProjectManager.getInstance().getCostItemNamesMap().get(category).add(newName);
                    }
                }
            }
        }
    }

    /**
     * Getter for the 'projects' list.
     * @return The 'projects' list.
     */
    public ArrayList<Project> getProjects() { return projects; }

    /**
     * Getter method used to get active project
     * @return current active project
     */
    public Project getActiveProject() { return activeProject; }

    /**
     * Get method for the active category
     * @return current active category
     */
    public Category getActiveCategory() { return activeCategory; }

    /**
     * Set method for activeCategory
     * @param category category to set
     */
    public void setActiveCategory(Category category) {
        activeCategory = category;
    }

    /**
     * Getter method for the categoryMap, used for getting an category ArrayList.
     * @return The categoryMap
     */
    public HashMap<Category, ArrayList<String>> getCostItemNamesMap() { return costItemNamesMap; }

    /**
     * Getter method to get current category's cost item names.
     * @return ArrayList of the names of the cost items.
     */
    public ArrayList<String> getActiveCostItemNames() {
        return costItemNamesMap.get(activeCategory);
    }
}
