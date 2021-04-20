/*
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package ua.timetracker.desktoptracker.api.admin.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * NewReportDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-04-20T17:18:58.671+03:00[Europe/Kiev]")
public class NewReportDto {
  public static final String SERIALIZED_NAME_TEMPLATEID = "templateid";
  @SerializedName(SERIALIZED_NAME_TEMPLATEID)
  private Long templateid;

  public static final String SERIALIZED_NAME_FROM = "from";
  @SerializedName(SERIALIZED_NAME_FROM)
  private LocalDateTime from;

  public static final String SERIALIZED_NAME_TO = "to";
  @SerializedName(SERIALIZED_NAME_TO)
  private LocalDateTime to;


  public NewReportDto templateid(Long templateid) {
    
    this.templateid = templateid;
    return this;
  }

   /**
   * Get templateid
   * @return templateid
  **/
  @ApiModelProperty(required = true, value = "")

  public Long getTemplateid() {
    return templateid;
  }


  public void setTemplateid(Long templateid) {
    this.templateid = templateid;
  }


  public NewReportDto from(LocalDateTime from) {
    
    this.from = from;
    return this;
  }

   /**
   * Get from
   * @return from
  **/
  @ApiModelProperty(required = true, value = "")

  public LocalDateTime getFrom() {
    return from;
  }


  public void setFrom(LocalDateTime from) {
    this.from = from;
  }


  public NewReportDto to(LocalDateTime to) {
    
    this.to = to;
    return this;
  }

   /**
   * Get to
   * @return to
  **/
  @ApiModelProperty(required = true, value = "")

  public LocalDateTime getTo() {
    return to;
  }


  public void setTo(LocalDateTime to) {
    this.to = to;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewReportDto newReportDto = (NewReportDto) o;
    return Objects.equals(this.templateid, newReportDto.templateid) &&
        Objects.equals(this.from, newReportDto.from) &&
        Objects.equals(this.to, newReportDto.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(templateid, from, to);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewReportDto {\n");
    sb.append("    templateid: ").append(toIndentedString(templateid)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

