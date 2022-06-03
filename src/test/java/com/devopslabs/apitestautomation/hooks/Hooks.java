package com.devopslabs.apitestautomation.hooks;

import static com.devopslabs.apitestautomation.utils.Constants.API_REQUEST_AND_RESPONSE_FORMAT;
import static com.devopslabs.apitestautomation.utils.Constants.CURRENT_SCENARIO;
import static com.devopslabs.apitestautomation.utils.Constants.CUSTOM_FILTER;
import static com.devopslabs.apitestautomation.utils.Constants.UTF8_NAME;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.devopslabs.apitestautomation.config.TestContext;
import com.devopslabs.apitestautomation.utils.CucumberReportLoggingFilter;
import com.devopslabs.apitestautomation.utils.CustomLogFilter;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

public class Hooks {

  private final TestContext CONTEXT = TestContext.CONTEXT;

  @Before(order = 1)
  public void before(Scenario scenario) throws UnsupportedEncodingException {
    CONTEXT.set(CURRENT_SCENARIO, scenario);
    CONTEXT.getRequest().given().filters(getFilters()).contentType(ContentType.JSON);
  }

  private List<Filter> getFilters() throws UnsupportedEncodingException {
    List<Filter> filters = new ArrayList<>();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(byteArrayOutputStream, true, UTF8_NAME);
    filters.add(new RequestLoggingFilter(printStream));
    //filters.add(new CustomLogFilter());
    filters.add(new CucumberReportLoggingFilter(byteArrayOutputStream, out -> log(out)));
    filters.add(new ResponseLoggingFilter(printStream));
    return filters;
  }

  public Scenario getScenario() {
    return CONTEXT.get(CURRENT_SCENARIO);
  }

  public CustomLogFilter getCustomLogFilter() {
    return CONTEXT.get(CUSTOM_FILTER);
  }

  public void log(String text) {
    if (null != getScenario()) {
      getScenario().log(text);
    }
  }

  public void logAPIRequestAndResponse() {
    if (getCustomLogFilter() instanceof CustomLogFilter) {
      CustomLogFilter customLogFilter = getCustomLogFilter();
      log(String.format(API_REQUEST_AND_RESPONSE_FORMAT, customLogFilter.getRequestLogs(),
          customLogFilter.getResponseLogs()));
    }
  }

}
