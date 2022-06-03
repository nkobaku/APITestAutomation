package com.devopslabs.apitestautomation.stepdefs;


import static com.devopslabs.apitestautomation.utils.CommonUtil.buildMatrixParams;
import static com.devopslabs.apitestautomation.utils.Constants.ACCESS_TOKEN;
import static com.devopslabs.apitestautomation.utils.Constants.BASIC_FORMAT;
import static com.devopslabs.apitestautomation.utils.Constants.BEARER_FORMAT;
import static com.devopslabs.apitestautomation.utils.Constants.CLIENT_CREDENTIALS;
import static com.devopslabs.apitestautomation.utils.Constants.GRANT_TYPE;
import static com.devopslabs.apitestautomation.utils.Constants.HEADER_AUTHORIZATION;
import static io.restassured.RestAssured.given;

import java.util.Map;

import com.devopslabs.apitestautomation.config.TestContext;
import com.devopslabs.apitestautomation.hooks.Hooks;
import com.devopslabs.apitestautomation.utils.AuthType;
import com.devopslabs.apitestautomation.utils.CommonUtil;
import com.devopslabs.apitestautomation.utils.HttpRequestWrapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


/**
 * Class that abstract the test context management and REST API Invocation.
 */
@Slf4j
public class RestAssuredUtils {

  private final TestContext CONTEXT = TestContext.CONTEXT;

  @Autowired
  private Hooks hooks;

  private RequestSpecification request;

  private Response response;

  private static void resetAll() {
    RestAssured.reset();
  }

  private static void resetBaseURI(String baseURI) {
    RestAssured.baseURI = baseURI;
  }

  private static void resetBasePath(String basePath) {
    RestAssured.basePath = basePath;
  }

  protected void sendRequest(HttpRequestWrapper httpRequestWrapper) {
    request = CONTEXT.getRequest();

    request.baseUri(httpRequestWrapper.getBaseUri());

    final Object payload = CONTEXT.getPayload();

    setPayload(payload);
    setQueryParams(httpRequestWrapper.getQueryParameters());
    setPathParams(httpRequestWrapper.getPathParameters());
    setHeaders(httpRequestWrapper.getHttpHeaders());
    setAuthentication(httpRequestWrapper);

    final String path = String.format("%s%s", httpRequestWrapper.getEndPoint(),
        buildMatrixParams(httpRequestWrapper.getMatrixParameters()));

    response = request.accept(ContentType.JSON).log().all().when()
        .request(httpRequestWrapper.getHttpMethod(), path);
    CONTEXT.setResponse(response);

    logResponse(response);
  }


  private void setPayload(Object payload) {

    if (null != payload) {
      request.contentType(ContentType.JSON).body(payload);
    }

  }

  private void setformParams(Map<String, String> formParams) {
    if (null != formParams) {
      request.formParams(formParams).log().ifValidationFails();
    }
  }


  private void setPathParams(Map<String, String> pathParams) {
    if (null != pathParams) {
      request.pathParams(pathParams).log().ifValidationFails();
    }
  }

  private void setQueryParams(Map<String, String> queryParams) {
    if (null != queryParams) {
      request.queryParams(queryParams).log().ifValidationFails();
    }
  }

  private void setHeaders(Map<String, String> httpParams) {
    if (null != httpParams) {
      request.headers(httpParams).log().ifValidationFails();
    }
  }

  private void logResponse(Response response) {
    //hooks.logAPIRequestAndResponse();
    response.then().log().all();
  }

  public void setURLEncoding(boolean enabled) {
    request.urlEncodingEnabled(enabled);
  }


  public void validateStatusCode(int statusCode) {
    response.then().assertThat().statusCode(statusCode);
  }


  public long getResponseTime() {
    return response.time();
  }

  public int getStatusCode() {
    return response.then().log().all().extract().statusCode();
  }

  public String getAPIResponseAsPrettyPrint() {
    return response.prettyPrint();
  }

  public String getAPIResponseAsString() {
    return response.then().log().all().extract().asString();
  }


  /**
   * Client Credential Flow Example:
   * <p>
   * {  "client_id": "some client id", "client_secret": "some client secret", "grant_type":
   * "client_credentials", "scope": "scope"}
   * <p>
   * given().auth().oauth2(getAccessToken('someurl/oauth/token', payload))
   */
  public String getAccessToken(String tokenURL, String payload) {
    return given().contentType(ContentType.JSON).body(payload).post(tokenURL).then()
        .extract().response().jsonPath().getString(ACCESS_TOKEN);
  }

  private void setAuthentication(HttpRequestWrapper httpRequestWrapper) {
    final AuthType authType = httpRequestWrapper.getAuthenticationType();

    switch (authType) {

      case BASIC_AUTH:
        setAuthenticationAsBasic(httpRequestWrapper);
        break;
      case OAUTH1:
        setAuthenticationAsOAUTH1(httpRequestWrapper);
        break;
      case OAUTH2:
        setAuthenticationAsOAUTH2(httpRequestWrapper);
        break;
      case BEARER_TOKEN:
        setAuthenticationAsBearerToken(httpRequestWrapper);
        break;
      case NONE:
        break;
    }
  }

  private void setAuthenticationAsBearerToken(HttpRequestWrapper httpRequestWrapper) {
  }

  private void setAuthenticationAsOAUTH2(HttpRequestWrapper httpRequestWrapper) {
  }

  private void setAuthenticationAsOAUTH1(HttpRequestWrapper httpRequestWrapper) {
  }

  private void setAuthenticationAsBasic(HttpRequestWrapper httpRequestWrapper) {

    request.header(new Header(HEADER_AUTHORIZATION, String.format(BEARER_FORMAT,
        getAccessToken(httpRequestWrapper.getTokenURL(), httpRequestWrapper.getUserName(),
            httpRequestWrapper.getPassword()))));
  }

  private String getAccessToken(String tokenURL, String userName, String password) {
    final String encodedCredentials = CommonUtil.encode(userName, password);

    RequestSpecBuilder builder = new RequestSpecBuilder()
        .addFilter(new RequestLoggingFilter())
        .addFilter(new ResponseLoggingFilter())
        .setContentType(ContentType.JSON).setUrlEncodingEnabled(false).log(LogDetail.ALL);

    RequestSpecificationImpl request = (RequestSpecificationImpl) RestAssured.given().log().all()
        .spec(builder.build());

    return request.auth().basic(userName, password)
        .header(new Header(HEADER_AUTHORIZATION, String.format(BASIC_FORMAT, encodedCredentials)))

        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .formParam(GRANT_TYPE, CLIENT_CREDENTIALS).when().post(tokenURL).then().extract().response()
        .jsonPath().get(ACCESS_TOKEN);
  }


}
