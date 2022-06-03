package com.devopslabs.apitestautomation.utils;

import java.nio.charset.StandardCharsets;

/**
 * This class hold all final values here.
 */
public final class Constants {

  public static final String AUTHORIZATION = "Authorization";

  public static final String BEARER_FORMAT = "Bearer %s";

  public static final String BASIC_FORMAT = "Basic %s";

  public static final String PAYLOAD = "PAYLOAD";

  public static final String REQUEST = "REQUEST";

  public static final String RESPONSE = "RESPONSE";

  public static final String HEADERS = "HEADERS";

  public static final String CURRENT_SCENARIO = "CURRENT_SCENARIO";

  public static final String CUCUMBER_REPORT_LOGGING_FILTER = "CUCUMBER_REPORT_LOGGING_FILTER";

  public static final String CUSTOM_FILTER = "CUSTOM_FILTER";

  public static final String HTTP_REQUEST = "HTTP_REQUEST";

  public static final String CONTENT_TYPE_JSON = "application/json";

  public static final String HEADER_AUTHORIZATION = "Authorization";

  public static final String CONTENT_TYPE = "Content-Type";

  public static final String COLON = ":";

  public static final String CODE = "code";

  public static final String ACCESS_TOKEN = "access_token";

  public static final String NEW_LINE = "\n";

  public static final String TAB = "\t";

  public static final String EQUALS = "=";

  public static final String NONE = "<none>";

  public static final String UTF8_NAME = StandardCharsets.UTF_8.name();

  public static final String API_REQUEST_AND_RESPONSE_FORMAT = NEW_LINE+"API Request: %s"+NEW_LINE+"API Response: %s";
  public static final String GRANT_TYPE = "grant_type";
  public static final String CLIENT_CREDENTIALS = "client_credentials";


  private Constants() {
  }
}
