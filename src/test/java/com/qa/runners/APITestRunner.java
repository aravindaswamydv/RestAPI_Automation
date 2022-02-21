package com.qa.runners;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.FeatureWrapper;

//@CucumberOptions(tags = "", features = "src/test/resources/features/TestAPI.feature", glue = "com.qa.stepsdefinitions")

@CucumberOptions(features = "src/test/resources/com/qa/features", 
glue = { "com.qa.stepsdefenitions" }, 
tags = "@tag1",
plugin = "json:target/cucumber-reports/CucumberTestReport.json")

public class APITestRunner extends AbstractTestNGCucumberTests{
	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
	    testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

/*	@Test(groups = "cucumber scenarios", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void scenario(PickleEventWrapper pickleEvent, CucumberFeatureWrapper cucumberFeature) throws Throwable{
	testNGCucumberRunner.runScenario(pickleEvent.getPickleEvent());
	}*/
	
	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
	public void scenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) throws Throwable {
	    testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
	}
	@DataProvider
	public Object[][] scenarios() {
	    return testNGCucumberRunner.provideScenarios();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
	    testNGCucumberRunner.finish();
	}
	
}
