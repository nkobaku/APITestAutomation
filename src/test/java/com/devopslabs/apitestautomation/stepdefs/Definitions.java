package com.devopslabs.apitestautomation.stepdefs;


import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class Definitions {

    @Before
    public void before() {

    }

    @Given("there {int} cucumber(s) in my belly")
    public void putCucumbersInBelly(Integer num) throws InterruptedException {
        Thread.sleep(2000);
        System.out.println(num + " cucumbers have been eaten");
    }
}