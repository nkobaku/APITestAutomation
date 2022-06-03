package com.devopslabs.apitestautomation.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Year;
import java.time.ZonedDateTime;

import com.google.gson.GsonBuilder;

public final class DateTypeAdapters {

  private DateTypeAdapters() {
    throw new IllegalAccessError("DateTypeAdapters class");
  }

  public static GsonBuilder register(GsonBuilder builder) {

    return builder
        .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeTypeAdapter().nullSafe())
        .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter().nullSafe())
        .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeTypeAdapter().nullSafe())
        .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter().nullSafe())
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter().nullSafe())
        .registerTypeAdapter(Year.class, new YearTypeAdapter().nullSafe());
  }
}
