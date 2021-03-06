package edu.chalmers.axen2021.model.managers;

import edu.chalmers.axen2021.model.Category;
import edu.chalmers.axen2021.model.projectdata.Project;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SaveManager class.
 * @author Malte Åkvist
 * @author Sam Salek
 */
public class SaveManagerTest {
    private static final SaveManager saveManager = SaveManager.getInstance();

    @BeforeAll
    public static void resetAll() {
        ReflectionTestUtils.setField(saveManager, "directory", "src/test/saveDirectory");
    }

    @BeforeEach
    public void reset() {
        saveManager.verifyDirectory();
    }

    @AfterEach
    public void afterReset() {
        saveManager.removeDirectory();
    }

    @Test
    public void removeDirectoryTest() {
        assertTrue(saveManager.removeDirectory());
    }

    @Test
    public void projectManagerExistsTest() {
        assertFalse(saveManager.projectManagerExists());

        saveManager.verifyDirectory();
        saveManager.saveProjectManager();
        assertTrue(saveManager.projectManagerExists());
    }

    @Test
    public void saveProjectTest() {
        Project project = new Project("test");
        saveManager.saveProject(project);
        assertEquals(saveManager.getProjectNames().get(0), project.getName());
    }

    @Test
    public void readProjectsTest() {
        saveManager.saveProject(new Project("test1"));
        saveManager.saveProject(new Project("test2"));
        saveManager.saveProject(new Project("test3"));

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Project> projects = saveManager.readProjects();
        for (Project project : projects) {
            names.add(project.getName());
        }

        assertEquals(projects.size(), 3);
        assertTrue(names.contains("test1"));
        assertTrue(names.contains("test2"));
        assertTrue(names.contains("test3"));
        assertFalse(names.contains("test4"));
    }

    @Test
    public void readProjectManagerTest() {
        ProjectManager.getInstance().getCostItemNamesMap().get(Category.ANSLUTNINGAR).add("test");
        saveManager.saveProjectManager();

        ProjectManager.getInstance().getCostItemNamesMap().get(Category.ANSLUTNINGAR).remove(0);
        ProjectManager projectManager = SaveManager.getInstance().readProjectManager();
        assertEquals(projectManager.getCostItemNamesMap().get(Category.ANSLUTNINGAR).get(0), "test");
    }

    @Test
    public void removeProjectFileTest() {
        Project project = new Project("test");
        assertFalse(saveManager.removeProjectFile(project));

        saveManager.saveProject(project);
        assertTrue(saveManager.removeProjectFile(project));
    }
}
