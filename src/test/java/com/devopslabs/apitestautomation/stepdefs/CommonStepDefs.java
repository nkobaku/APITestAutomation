package com.devopslabs.apitestautomation.stepdefs;


import io.cucumber.java8.En;

public class CommonStepDefs extends RestAssuredUtils implements En {


  public CommonStepDefs() {
    Given("Step from {string} in {string} feature file",
        (String scenario, String file) -> {
          System.out.format("Thread ID - %2d - %s from %s feature file.\n",
              Thread.currentThread().getId(), scenario, file);
        });
  }

}
