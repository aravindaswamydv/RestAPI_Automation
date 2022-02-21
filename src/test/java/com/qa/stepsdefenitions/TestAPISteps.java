package com.qa.stepsdefenitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TestAPISteps {
	
	@ When("^I call this API : \"([^\"]*)\"$")
	public void testAPI(String searchTxt) throws InterruptedException {
		
		
	}
	
	@ Then ("^This should happen$")
	public void verifyAPI() throws InterruptedException {
		
		
	}
}
