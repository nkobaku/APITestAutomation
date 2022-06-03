package com.devopslabs.apitestautomation.utils;

import java.io.IOException;
import java.time.OffsetDateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class OffsetDateTimeTypeAdapter extends TypeAdapter<OffsetDateTime> {

  @Override
  public void write(JsonWriter out, OffsetDateTime value) throws IOException {
    out.value(value.toString());
  }

  @Override
  public OffsetDateTime read(JsonReader in) throws IOException {
    return OffsetDateTime.parse(in.nextString());
  }
}
