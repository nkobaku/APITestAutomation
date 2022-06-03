package com.devopslabs.apitestautomation.utils;

import static com.devopslabs.apitestautomation.utils.Constants.ACCESS_TOKEN;
import static com.devopslabs.apitestautomation.utils.Constants.CODE;
import static com.devopslabs.apitestautomation.utils.Constants.COLON;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.github.millij.poi.SpreadsheetReadException;
import io.github.millij.poi.ss.reader.XlsxReader;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

@Slf4j
public final class CommonUtil {

  private static final Gson GSON = DateTypeAdapters.register(new GsonBuilder()).create();

  private CommonUtil() {
    throw new IllegalAccessError("CommonUtil class");
  }

  public static Map<String, String> getAsMap(String field) {

    if (StringUtils.isBlank(field)) {
      return new HashMap<>();
    }
    Type listType = new TypeToken<List<HttpParam>>() {
    }.getType();
    List<HttpParam> httpParams = GSON.fromJson(field, listType);
    Map<String, String> params =
        httpParams.stream().collect(Collectors.toMap(HttpParam::getKey, HttpParam::getValue));
    return params;
  }

  /**
   * @param excelPath    File path of the spreadsheet file
   * @param excelSheetNo the index of the sheet to be read (index starts from 0)
   * @return the rows of excel sheet converted to ist of POJOs
   * @throws IOException              an exception is thrown if it fails to read excel file
   * @throws SpreadsheetReadException an exception is thrown in cases where the file data is not
   *                                  readable or row data to bean mapping failed.
   */
  public static List<HttpRequestWrapper> getAPIData(String excelPath, int excelSheetNo)
      throws IOException, SpreadsheetReadException {
    File xlsxFile = new ClassPathResource(excelPath).getFile();
    final XlsxReader reader = new XlsxReader();
    List<HttpRequestWrapper> httpRequestWrappers = reader.read(HttpRequestWrapper.class, xlsxFile,
        excelSheetNo);
    return httpRequestWrappers;
  }

  @SneakyThrows
  public static String readFile(String fileName) {
    return FileUtils.readFileToString(new ClassPathResource(fileName).getFile(),
        StandardCharsets.UTF_8);
  }

  public static String buildQueryParams(Map<String, String> queryParams) {
    return queryParams.entrySet().stream().map(p -> p.getKey() + "=" + p.getValue())
        .reduce((p1, p2) -> p1 + "&" + p2).map(s -> "?" + s).orElse("");
  }

  public static String buildMatrixParams(Map<String, String> queryParams) {
    return queryParams.entrySet().stream().map(p -> p.getKey() + "=" + p.getValue())
        .reduce((p1, p2) -> p1 + ";" + p2).map(s -> ";" + s).orElse("");
  }

  public static Collection<String> getsSourceTags(Scenario scenario) {
    return Optional.ofNullable(scenario.getSourceTagNames()).orElse(new HashSet<>());
  }


  public static <T> List<T> getDataRows(DataTable dataTable, Class<T> classOfT) {
    return dataTable.asMaps().stream().map(m -> mapToDataRow(m, classOfT))
        .collect(Collectors.toList());
  }

  private static <T> T mapToDataRow(Map<String, String> map, Class<T> classOfT) {
    return GSON.fromJson(GSON.toJson(map), classOfT);
  }

  public static String base64Encode(String token) {
    return Base64.getEncoder().encodeToString(token.getBytes());
  }

  public static String base64Decode(String encryptedToken) {
    Base64.Decoder decoder = Base64.getDecoder();
    return new String(decoder.decode(encryptedToken));
  }

  public static String encode(String str1, String str2) {

    return new String(Base64.getEncoder().encode((str1 + COLON + str2).getBytes()));
  }

  public static String parseForOAuth2Code(Response response) {
    return response.jsonPath().getString(CODE);
  }

  /**
   * Using Access Token
   * <p>
   * given().auth().auth().oauth2(accessToken)
   * <p>
   * or
   * <p>
   * given().header("Authorization", "Bearer "+accessToken)  *
   */
  public static String parseForAccessToken(Response response) {
    return response.jsonPath().getString(ACCESS_TOKEN);
  }

}
