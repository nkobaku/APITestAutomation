package com.devopslabs.apitestautomation.utils;

import static com.devopslabs.apitestautomation.utils.Constants.NEW_LINE;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class CustomLogFilter implements Filter {

  private StringBuilder requestLogBuilder;
  private StringBuilder responseLogBuilder;

  @Override
  public Response filter(FilterableRequestSpecification requestSpec,
      FilterableResponseSpecification responseSpec, FilterContext ctx) {

    Response response = ctx.next(requestSpec, responseSpec);
    requestLogBuilder = new StringBuilder();

    requestLogBuilder.append(NEW_LINE)
        .append("Request Method: " + objectValidation(requestSpec.getMethod())).append(NEW_LINE)
        .append("Request URI: " + requestSpec.getURI())
        .append(NEW_LINE).append("Form Params: " + requestSpec.getFormParams())
        .append(NEW_LINE).append("Request Params: " + requestSpec.getRequestParams())
        .append(NEW_LINE).append("Headers: " + requestSpec.getHeaders())
        .append(NEW_LINE).append("Cookies: " + requestSpec.getCookies())
        .append(NEW_LINE).append("Proxy: " + requestSpec.getProxySpecification())
        .append(NEW_LINE).append("Body: " + requestSpec.getBody())
        .append(NEW_LINE).append("*************************************************");

    responseLogBuilder = new StringBuilder();

    responseLogBuilder.append(NEW_LINE).append(NEW_LINE)
        .append(NEW_LINE).append("Status Code: " + response.getStatusCode())
        .append(NEW_LINE).append("Status Line: " + response.getStatusLine())
        .append(NEW_LINE).append("Response Cookies: " + response.getDetailedCookies())
        .append(NEW_LINE).append("Response Content Type: " + response.getContentType())
        .append(NEW_LINE).append("Response Headers: " + response.getHeaders())
        .append(NEW_LINE).append("Status Code: " + response.getStatusCode())
        .append(NEW_LINE).append("Response Body: " + NEW_LINE + response.getBody().prettyPrint());

    return response;
  }

  public String objectValidation(Object o) {

    if (o == null) {
      return null;
    } else {
      return o.toString();
    }

  }

  public String getRequestLogs() {
    return requestLogBuilder.toString();
  }

  public String getResponseLogs() {
    return responseLogBuilder.toString();
  }
}
