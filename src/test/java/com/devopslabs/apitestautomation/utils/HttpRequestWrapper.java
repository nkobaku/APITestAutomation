package com.devopslabs.apitestautomation.utils;

import static com.devopslabs.apitestautomation.utils.CommonUtil.getAsMap;
import static com.devopslabs.apitestautomation.utils.Constants.AUTHORIZATION;

import java.util.HashMap;
import java.util.Map;

import io.github.millij.poi.ss.model.annotations.Sheet;
import io.github.millij.poi.ss.model.annotations.SheetColumn;
import io.restassured.http.Method;
import org.apache.commons.lang.StringUtils;

/**
 * Wrapper for HTTP request.
 */
@Sheet
public class HttpRequestWrapper {

  @SheetColumn("API_NAME")
  private String apiName;

  @SheetColumn("BASE_URI")
  private String baseUri;

  @SheetColumn("END_POINT")
  private String endPoint;

  @SheetColumn("REQUEST_METHOD")
  private String requestMethod;

  @SheetColumn("AUTH_TYPE")
  private String authType;

  @SheetColumn("USER_NAME")
  private String userName;

  @SheetColumn("PASSWORD")
  private String password;

  @SheetColumn("TOKEN")
  private String token;

  @SheetColumn("TOKEN_URL")
  private String tokenURL;

  @SheetColumn("CONSUMER_KEY")
  private String consumerKey;

  @SheetColumn("CONSUMER_KEY")
  private String consumerSecret;

  @SheetColumn("TOKEN_SECRET")
  private String tokenSecret;

  @SheetColumn("ACCESS_TOKEN")
  private String accessToken;

  @SheetColumn("QUERY_PARAMS")
  private String queryParams;

  @SheetColumn("PATH_PARAMS")
  private String pathParams;

  @SheetColumn("MATRIX_PARAMS")
  private String matrixParams;

  @SheetColumn("EXPECTED_STATUSCODE")
  private Integer expectedStatusCode;

  @SheetColumn("REQUEST_BODY")
  private String requestBody;

  @SheetColumn("REQUEST_HEADERS")
  private String requestHeaders;

  public String getApiName() {
    return apiName;
  }

  public void setApiName(String apiName) {
    this.apiName = apiName;
  }

  public String getBaseUri() {
    return baseUri;
  }

  public void setBaseUri(String baseUri) {
    this.baseUri = baseUri;
  }

  public String getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  public String getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(String requestMethod) {
    this.requestMethod = requestMethod;
  }

  public Map<String, String> getHttpHeaders() {
    Map<String, String> headers = new HashMap<>();
    headers.putAll(getAsMap(this.requestHeaders));
    if (StringUtils.isNotBlank(this.accessToken)) {
      headers.put(AUTHORIZATION, this.accessToken);
    }

    return headers;
  }

  public Method getHttpMethod() {
    if (StringUtils.isNotBlank(this.requestMethod)) {
      return Method.valueOf(this.requestMethod.toUpperCase().trim());
    }
    return Method.GET;
  }

  public String getAuthType() {
    return authType;
  }

  public void setAuthType(String authType) {
    this.authType = authType;
  }

  public AuthType getAuthenticationType() {

    if (StringUtils.isNotBlank(this.authType)) {
      return AuthType.getEnum(this.authType.trim());
    }

    return AuthType.NONE;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getTokenURL() {
    return tokenURL;
  }

  public void setTokenURL(String tokenURL) {
    this.tokenURL = tokenURL;
  }

  public String getConsumerKey() {
    return consumerKey;
  }

  public void setConsumerKey(String consumerKey) {
    this.consumerKey = consumerKey;
  }

  public String getConsumerSecret() {
    return consumerSecret;
  }

  public void setConsumerSecret(String consumerSecret) {
    this.consumerSecret = consumerSecret;
  }

  public String getTokenSecret() {
    return tokenSecret;
  }

  public void setTokenSecret(String tokenSecret) {
    this.tokenSecret = tokenSecret;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getQueryParams() {
    return queryParams;
  }

  public void setQueryParams(String queryParams) {
    this.queryParams = queryParams;
  }

  public Map<String, String> getQueryParameters() {
    return getAsMap(queryParams);
  }

  public String getPathParams() {
    return pathParams;
  }

  public void setPathParams(String pathParams) {
    this.pathParams = pathParams;
  }

  public Map<String, String> getPathParameters() {
    return getAsMap(pathParams);
  }

  public String getMatrixParams() {
    return matrixParams;
  }

  public void setMatrixParams(String matrixParams) {
    this.matrixParams = matrixParams;
  }

  public Map<String, String> getMatrixParameters() {
    return getAsMap(matrixParams);
  }

  public Integer getExpectedStatusCode() {
    return expectedStatusCode;
  }

  public void setExpectedStatusCode(Integer expectedStatusCode) {
    this.expectedStatusCode = expectedStatusCode;
  }

  public String getRequestBody() {
    return requestBody;
  }

  public void setRequestBody(String requestBody) {
    this.requestBody = requestBody;
  }

  public String getRequestHeaders() {
    return requestHeaders;
  }

  public void setRequestHeaders(String requestHeaders) {
    this.requestHeaders = requestHeaders;
  }
}
