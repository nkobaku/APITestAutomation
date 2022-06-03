package com.devopslabs.apitestautomation.utils;

import java.util.Arrays;

public enum AuthType {
  NONE("None"),
  BEARER_TOKEN("Bearer Token"),
  BASIC_AUTH("Basic Auth"),
  OAUTH1("OAuth1"),
  OAUTH2("OAuth2");

  private final String value;

  AuthType(String value) {
    this.value = value;
  }

  public static AuthType getEnum(String authType) {

    return Arrays.stream(AuthType.values())
        .filter(e -> e.getValue().equals(authType))
        .findFirst()
        .orElseThrow(
            () -> new IllegalStateException(String.format("Unsupported type %s.", authType)));
  }

  public String getValue() {
    return value;
  }
}
