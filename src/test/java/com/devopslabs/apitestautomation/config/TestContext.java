package com.devopslabs.apitestautomation.config;

import static com.devopslabs.apitestautomation.utils.CommonUtil.getAPIData;
import static com.devopslabs.apitestautomation.utils.Constants.PAYLOAD;
import static com.devopslabs.apitestautomation.utils.Constants.REQUEST;
import static com.devopslabs.apitestautomation.utils.Constants.RESPONSE;
import static java.lang.ThreadLocal.withInitial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devopslabs.apitestautomation.utils.HttpRequestWrapper;
import io.github.millij.poi.SpreadsheetReadException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.util.CollectionUtils;

/**
 * Singleton to manage objects and share their state between step definitions.
 *
 * <p>TestContext has all the code in place to store HTTP Request Payload, HTTP Request (Headers &
 * Params), HTTP Response Received.
 */
public enum TestContext {
  CONTEXT;

  private static List<HttpRequestWrapper> excelData = new ArrayList<>();

  private static String excelPath;

  private static Integer excelSheetNo;

  /*create variables that can only be read and write by the same thread*/
  private final ThreadLocal<Map<String, Object>> testContexts = withInitial(HashMap::new);

  public static List<HttpRequestWrapper> getExcelData() {
    return excelData;
  }

  public static void loadAPIDataFromExcel(String excelPath, Integer excelSheetNo)
      throws SpreadsheetReadException, IOException {

    if (CollectionUtils.isEmpty(TestContext.excelData) || (TestContext.excelPath != excelPath) || (
        TestContext.excelSheetNo != excelSheetNo)) {
      TestContext.excelPath = excelPath;
      TestContext.excelSheetNo = excelSheetNo;
      TestContext.excelData = getAPIData(excelPath, excelSheetNo);
    }
  }

  public <T> T get(String key) {
    return (T) testContexts.get()
        .get(key);
  }

  public <T> T set(String key, T value) {
    testContexts.get()
        .put(key, value);
    return value;
  }

  public Object getPayload() {
    return get(PAYLOAD);
  }

  public <T> void setPayload(T payload) {
    set(PAYLOAD, payload);
  }

  public <T> T getPayload(Class<T> clazz) {
    return clazz.cast(get(PAYLOAD));
  }

  public RequestSpecification getRequest() {
    if (null == get(REQUEST)) {
      RequestSpecBuilder builder = new RequestSpecBuilder()
          .setContentType(ContentType.JSON).log(LogDetail.ALL);

      RequestSpecificationImpl request = (RequestSpecificationImpl) RestAssured.given().log().all()
          .spec(builder.build());

      set(REQUEST, request);
    }
    return get(REQUEST);
  }

  public Response getResponse() {
    return get(RESPONSE);
  }

  public Response setResponse(Response response) {
    return set(RESPONSE, response);
  }

  public void reset() {
    testContexts.get()
        .clear();
  }

}