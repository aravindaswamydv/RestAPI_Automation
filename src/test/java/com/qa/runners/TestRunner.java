package com.qa.runners;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;

@CucumberOptions(features = "src/test/resources", plugin = "json:target/cucumber-report-feature-composite.json")
public class TestRunner {
private TestNGCucumberRunner testNGCucumberRunner;

@BeforeClass(alwaysRun = true)
public void setUpClass() throws Exception {
    testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
}

/*@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
public void feature(CucumberFeatureWrapper cucumberFeature) {
    testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
}

*//**
 * @return returns two dimensional array of {@link CucumberFeatureWrapper} objects.
 *//*
@DataProvider
public Object[][] features() {
    return testNGCucumberRunner.provideFeatures();
}
*/
@AfterClass
public void tearDownClass() throws Exception {
    testNGCucumberRunner.finish();
}
}