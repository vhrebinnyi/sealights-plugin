package io.sealigths.plugins.sealightsjenkins.integration;

import io.sealigths.plugins.sealightsjenkins.BuildStrategy;
import io.sealigths.plugins.sealightsjenkins.LogLevel;
import io.sealigths.plugins.sealightsjenkins.TestingFramework;
import io.sealigths.plugins.sealightsjenkins.entities.FileBackupInfo;
import org.apache.commons.io.FileUtils;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MavenIntegrationTest {

    String PATH = System.getProperty("user.dir") + "/src/test/cases/MavenIntegration/";

    @Test
    public void InjectSeaLightsPluginWithTestngListenerToAPomWithoutThePluginButWithSurefire() throws Exception {

        //Arrange
        TestingFramework TESTING_FRAMEWORK=TestingFramework.TESTNG;
        String TEST_CASE = "1_Inject_SeaLights_plugin_with_testng_listener_to_a_pom_without_the_plugin_but_with_surefire";
        String testFolder = getTestFolder(TEST_CASE);

        MavenIntegrationInfo mavenIntegrationInfo = createDefaultMavenIntegrationInfo(testFolder);
        mavenIntegrationInfo.setTestingFramework(TESTING_FRAMEWORK);
        MavenIntegration mavenIntegration = new MavenIntegration(new PrintStream(System.out),mavenIntegrationInfo);

        //Act
        mavenIntegration.integrate();

        //Assert
        String expected = readFileAndTrim(testFolder + "/expected.xml");
        String actual = readFileAndTrim(testFolder + "/actual.xml");
        assertXMLEquals(expected, actual);
    }

    @Test
    public void InjectSeaLightsPluginWithJunitListenerToAPomWithoutThePluginButWithSurefire() throws Exception {

        //Arrange
        TestingFramework TESTING_FRAMEWORK=TestingFramework.JUNIT;
        String TEST_CASE = "2_Inject_SeaLights_plugin_with_junit_listener_to_a_pom_without_the_plugin_but_with_surefire";
        String testFolder = getTestFolder(TEST_CASE);

        MavenIntegrationInfo mavenIntegrationInfo = createDefaultMavenIntegrationInfo(testFolder);
        mavenIntegrationInfo.setTestingFramework(TESTING_FRAMEWORK);
        MavenIntegration mavenIntegration = new MavenIntegration(new PrintStream(System.out),mavenIntegrationInfo);

        //Act
        mavenIntegration.integrate();

        //Assert
        String expected = readFileAndTrim(testFolder + "/expected.xml");
        String actual = readFileAndTrim(testFolder + "/actual.xml");
        assertXMLEquals(expected, actual);
    }

    @Test
    public void InjectSeaLightsPluginToAPomWithSingleProfileWithSurefire() throws Exception {

        //Arrange
        TestingFramework TESTING_FRAMEWORK=TestingFramework.JUNIT;
        String TEST_CASE = "3_Inject_SeaLights_plugin_to_a_pom_with_a_single_profile_with_surefire";
        String testFolder = getTestFolder(TEST_CASE);

        MavenIntegrationInfo mavenIntegrationInfo = createDefaultMavenIntegrationInfo(testFolder);
        mavenIntegrationInfo.setTestingFramework(TESTING_FRAMEWORK);
        MavenIntegration mavenIntegration = new MavenIntegration(new PrintStream(System.out),mavenIntegrationInfo);

        //Act
        mavenIntegration.integrate();

        //Assert
        String expected = readFileAndTrim(testFolder + "/expected.xml");
        String actual = readFileAndTrim(testFolder + "/actual.xml");
        assertXMLEquals(expected, actual);
    }

    @Test
    public void InjectSeaLightsPluginToAPomWithTwoProfilesWithSurefire() throws Exception {

        //Arrange
        TestingFramework TESTING_FRAMEWORK=TestingFramework.JUNIT;
        String TEST_CASE = "4_Inject_SeaLights_plugin_to_a_pom_with_a_two_profiles_with_surefire";
        String testFolder = getTestFolder(TEST_CASE);

        MavenIntegrationInfo mavenIntegrationInfo = createDefaultMavenIntegrationInfo(testFolder);
        mavenIntegrationInfo.setTestingFramework(TESTING_FRAMEWORK);
        MavenIntegration mavenIntegration = new MavenIntegration(new PrintStream(System.out),mavenIntegrationInfo);

        //Act
        mavenIntegration.integrate();

        //Assert
        String expected = readFileAndTrim(testFolder + "/expected.xml");
        String actual = readFileAndTrim(testFolder + "/actual.xml");
        assertXMLEquals(expected, actual);
    }

    @Test
    public void InjectSeaLightsPluginToAPomWithTwoProfilesWithSurefireAndAnotherSurefireNotInProfile() throws Exception {

        //Arrange
        TestingFramework TESTING_FRAMEWORK=TestingFramework.JUNIT;
        String TEST_CASE = "5_Inject_SeaLights_plugin_to_a_pom_with_a_two_profiles_with_surefire_and_another_surefire_not_in_profile";
        String testFolder = getTestFolder(TEST_CASE);

        MavenIntegrationInfo mavenIntegrationInfo = createDefaultMavenIntegrationInfo(testFolder);
        mavenIntegrationInfo.setTestingFramework(TESTING_FRAMEWORK);
        MavenIntegration mavenIntegration = new MavenIntegration(new PrintStream(System.out),mavenIntegrationInfo);

        //Act
        mavenIntegration.integrate();

        //Assert
        String expected = readFileAndTrim(testFolder + "/expected.xml");
        String actual = readFileAndTrim(testFolder + "/actual.xml");
        assertXMLEquals(expected, actual);
    }

    @Test
    public void InjectSeaLightsPluginToAPomWithProfileWhichHasSurefireAndAnotherDoesnt() throws Exception {

        //Arrange
        TestingFramework TESTING_FRAMEWORK=TestingFramework.JUNIT;
        String TEST_CASE = "6_Inject_SeaLights_plugin_to_a_pom_with_a_profile_which_has_surefire_and_another_doesnt";
        String testFolder = getTestFolder(TEST_CASE);

        MavenIntegrationInfo mavenIntegrationInfo = createDefaultMavenIntegrationInfo(testFolder);
        mavenIntegrationInfo.setTestingFramework(TESTING_FRAMEWORK);
        MavenIntegration mavenIntegration = new MavenIntegration(new PrintStream(System.out),mavenIntegrationInfo);

        //Act
        mavenIntegration.integrate();

        //Assert
        String expected = readFileAndTrim(testFolder + "/expected.xml");
        String actual = readFileAndTrim(testFolder + "/actual.xml");
        assertXMLEquals(expected, actual);
    }

    @Test
    public void InjectSeaLightsPluginToAPomWithSurefireThatHasExistingConfigurationElement() throws Exception {

        //Arrange
        TestingFramework TESTING_FRAMEWORK=TestingFramework.JUNIT;
        String TEST_CASE = "7_Inject_SeaLights_plugin_to_a_pom_with_surefire_that_has_existing_configuration_element";
        String testFolder = getTestFolder(TEST_CASE);

        MavenIntegrationInfo mavenIntegrationInfo = createDefaultMavenIntegrationInfo(testFolder);
        mavenIntegrationInfo.setTestingFramework(TESTING_FRAMEWORK);
        MavenIntegration mavenIntegration = new MavenIntegration(new PrintStream(System.out),mavenIntegrationInfo);

        //Act
        mavenIntegration.integrate();

        //Assert
        String expected = readFileAndTrim(testFolder + "/expected.xml");
        String actual = readFileAndTrim(testFolder + "/actual.xml");
        assertXMLEquals(expected, actual);
    }

    @Test
    public void InjectSeaLightsPluginToAPomWithSurefireThatHasExistingConfigurationElementWithArgLineElementThatDoesntChainOldValues() throws Exception {

        //Arrange
        TestingFramework TESTING_FRAMEWORK=TestingFramework.JUNIT;
        String TEST_CASE = "8_Inject_SeaLights_plugin_to_a_pom_with_surefire_that_has_existing_configuration_element_with_argLine_element_that_doesnt_chain_old_values";
        String testFolder = getTestFolder(TEST_CASE);

        MavenIntegrationInfo mavenIntegrationInfo = createDefaultMavenIntegrationInfo(testFolder);
        mavenIntegrationInfo.setTestingFramework(TESTING_FRAMEWORK);
        MavenIntegration mavenIntegration = new MavenIntegration(new PrintStream(System.out),mavenIntegrationInfo);

        //Act
        mavenIntegration.integrate();

        //Assert
        String expected = readFileAndTrim(testFolder + "/expected.xml");
        String actual = readFileAndTrim(testFolder + "/actual.xml");
        assertXMLEquals(expected, actual);
    }


    @Test
    public void InjectSeaLightsPluginToAPomWithSurefireThatHasExistingConfigurationElementWithArgLineElementThatDoesChainOldValues() throws Exception {

        //Arrange
        TestingFramework TESTING_FRAMEWORK=TestingFramework.JUNIT;
        String TEST_CASE = "9_Inject_SeaLights_plugin_to_a_pom_with_surefire_that_has_existing_configuration_element_with_argLine_element_that_does_chain_old_values";
        String testFolder = getTestFolder(TEST_CASE);

        MavenIntegrationInfo mavenIntegrationInfo = createDefaultMavenIntegrationInfo(testFolder);
        mavenIntegrationInfo.setTestingFramework(TESTING_FRAMEWORK);
        MavenIntegration mavenIntegration = new MavenIntegration(new PrintStream(System.out),mavenIntegrationInfo);

        //Act
        mavenIntegration.integrate();

        //Assert
        String expected = readFileAndTrim(testFolder + "/expected.xml");
        String actual = readFileAndTrim(testFolder + "/actual.xml");
        assertXMLEquals(expected, actual);
    }

    @Test
    public void InjectSeaLightsPluginToAPomWithSurefireInsidePluginManagementElement() throws Exception {

        //Arrange
        TestingFramework TESTING_FRAMEWORK=TestingFramework.JUNIT;
        String TEST_CASE = "10_Inject_SeaLights_plugin_to_a_pom_with_surefire_inside_pluginManagement_element";
        String testFolder = getTestFolder(TEST_CASE);

        MavenIntegrationInfo mavenIntegrationInfo = createDefaultMavenIntegrationInfo(testFolder);
        mavenIntegrationInfo.setTestingFramework(TESTING_FRAMEWORK);
        MavenIntegration mavenIntegration = new MavenIntegration(new PrintStream(System.out),mavenIntegrationInfo);

        //Act
        mavenIntegration.integrate();

        //Assert
        String expected = readFileAndTrim(testFolder + "/expected.xml");
        String actual = readFileAndTrim(testFolder + "/actual.xml");
        
        assertXMLEquals(expected, actual);
    }

    public static void assertXMLEquals(String expectedXML, String actualXML) throws Exception {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);

        DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(expectedXML, actualXML));

        List<?> allDifferences = diff.getAllDifferences();
        Assert.assertEquals("Differences found: "+ diff.toString(), 0, allDifferences.size());
    }


    private String readFileAndTrim(String filepath) throws IOException {
        String s = FileUtils.readFileToString(new File(filepath));
        return trim(s);
    }

    public static String trim(String input) {
        BufferedReader reader = new BufferedReader(new StringReader(input));
        StringBuffer result = new StringBuffer();
        try {
            String line;
            while ( (line = reader.readLine() ) != null) {
                String trim = line.trim();
                trim = trim.replaceAll(">\\s*<", "><").trim();
                result.append(trim);
            }
            return result.toString().trim().replaceAll(">\\s*<", "><");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MavenIntegrationInfo createDefaultMavenIntegrationInfo(String path){


        SeaLightsPluginInfo slInfo = new SeaLightsPluginInfo();
        slInfo.setEnabled(true);
        slInfo.setBuildName("1");
        slInfo.setCustomerId("fake-customer-id-123");
        slInfo.setServerUrl("http://fake-server-url.com");

        slInfo.setWorkspacepath("c:\\fake-worakpsacepath");
        slInfo.setBuildFilesFolders(path);


        slInfo.setAppName("fake-app-name");
        slInfo.setModuleName("fake-module-name");
        slInfo.setBranchName("fake-branch");
        slInfo.setFilesIncluded("*.class");
        slInfo.setRecursive(true);
        slInfo.setPackagesIncluded("com.fake.*");
        slInfo.setPackagesExcluded("com.fake.excluded.*");
        slInfo.setBuildFilesPatterns("*pom.xml");

        slInfo.setListenerJar("c:\\fake-test-listener.jar");
        slInfo.setScannerJar("c:\\fake-build-scanner.jar");
        slInfo.setApiJar("c:\\fake-api.jar");
        slInfo.setBuildStrategy(BuildStrategy.ONE_BUILD);

        slInfo.setLogEnabled(false);
        slInfo.setLogLevel(LogLevel.INFO);
        slInfo.setLogFolder("c:\\fake-log-folder");

        String source = path + "/pom.xml";
        String target = path + "/actual.xml";
        List<FileBackupInfo> files = new ArrayList<>();
        files.add(new FileBackupInfo(source, target));
        MavenIntegrationInfo info = new MavenIntegrationInfo(files, slInfo, TestingFramework.JUNIT);
        info.setTestingFramework(TestingFramework.TESTNG);
        info.setSeaLightsPluginInfo(slInfo);

        return info;
    }

    private String getTestFolder(String testCaseName)
    {
        return PATH + testCaseName;
    }
}