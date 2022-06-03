package com.devopslabs.apitestautomation.config;

import com.devopslabs.apitestautomation.APITestAutomationApplication;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;

/** Class to use spring application context while running cucumber */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
    classes = {APITestAutomationApplication.class, BddConfig.class},
    loader = SpringBootContextLoader.class)
@CucumberContextConfiguration
@Slf4j
public class CucumberSpringContextConfig {

  /**
   * Need this method so the cucumber will recognize this class as glue and load spring context
   * configuration
   */
  @Before
  public void setUp() {
    log.info("-------------- TEST CONTEXT SETUP --------------");
    log.info(
        "-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
  }

  @After
  public void tearDown() {
    log.info("-------------- TEST CONTEXT TEAR DOWN --------------");
    TestContext.CONTEXT.reset();
  }
}
