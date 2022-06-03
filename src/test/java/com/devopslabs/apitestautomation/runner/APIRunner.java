package com.devopslabs.apitestautomation.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileSystemUtils;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(features = "classpath:features", glue = {
    "com.devopslabs.apitestautomation.config",
    "com.devopslabs.apitestautomation.hooks",
    "com.devopslabs.apitestautomation.stepdefs"
}, plugin = {
    "pretty","usage:target/cucumber/usage.json",
    "html:target/cucumber",
    "json:target/cucumber/cucumber.json",
    "de.monochromata.cucumber.report.PrettyReports:target/cucumber/pretty-cucumber"},
    monochrome = true,
    strict = true,
    dryRun = false)
@Slf4j
public class APIRunner extends AbstractTestNGCucumberTests {

  @Test(groups = "cucumber", description = "Run Cucumber Scenarios", dataProvider = "scenarios")
  @Override
  public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper)
      throws Throwable {

    super.runScenario(pickleWrapper, featureWrapper);
  }

  @DataProvider(name = "scenarios", parallel = true)
  @Override
  public Object[][] scenarios() {
    return super.scenarios();
  }

  @BeforeSuite
  public void beforeSuite() throws IOException {
    log.info("----------------- CLEANING target FOLDER -----------------");
    FileSystemUtils.deleteRecursively(new File("target"));
  }

}
