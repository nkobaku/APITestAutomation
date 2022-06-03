package com.devopslabs.apitestautomation.utils;

import java.io.ByteArrayOutputStream;
import java.util.function.Consumer;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class CucumberReportLoggingFilter implements Filter {

  private final ByteArrayOutputStream out;

  private final Consumer<String> outConsumer;

  public CucumberReportLoggingFilter(ByteArrayOutputStream out, Consumer<String> outConsumer) {
    this.out = out;
    this.outConsumer = outConsumer;
  }

  @Override
  public Response filter(FilterableRequestSpecification requestSpec,
      FilterableResponseSpecification responseSpec, FilterContext ctx) {
    Response response = ctx.next(requestSpec, responseSpec);
    outConsumer.accept(out.toString());
    return response;
  }
}
