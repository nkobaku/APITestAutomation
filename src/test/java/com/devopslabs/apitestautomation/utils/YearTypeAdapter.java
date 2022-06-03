package com.devopslabs.apitestautomation.utils;

import java.io.IOException;
import java.time.Year;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class YearTypeAdapter extends TypeAdapter<Year> {

  @Override
  public void write(JsonWriter out, Year value) throws IOException {
    out.value(value.toString());
  }

  @Override
  public Year read(JsonReader in) throws IOException {
    return Year.parse(in.nextString());
  }
}
