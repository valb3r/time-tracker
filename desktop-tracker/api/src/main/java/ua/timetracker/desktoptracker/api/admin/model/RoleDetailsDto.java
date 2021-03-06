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
 * RoleDetailsDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-04-20T17:35:19.311+03:00[Europe/Kiev]")
public class RoleDetailsDto {
  public static final String SERIALIZED_NAME_RATE = "rate";
  @SerializedName(SERIALIZED_NAME_RATE)
  private String rate;

  public static final String SERIALIZED_NAME_FROM = "from";
  @SerializedName(SERIALIZED_NAME_FROM)
  private LocalDateTime from;

  public static final String SERIALIZED_NAME_TO = "to";
  @SerializedName(SERIALIZED_NAME_TO)
  private LocalDateTime to;


  public RoleDetailsDto rate(String rate) {
    
    this.rate = rate;
    return this;
  }

   /**
   * Get rate
   * @return rate
  **/
  @ApiModelProperty(required = true, value = "")

  public String getRate() {
    return rate;
  }


  public void setRate(String rate) {
    this.rate = rate;
  }


  public RoleDetailsDto from(LocalDateTime from) {
    
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


  public RoleDetailsDto to(LocalDateTime to) {
    
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
    RoleDetailsDto roleDetailsDto = (RoleDetailsDto) o;
    return Objects.equals(this.rate, roleDetailsDto.rate) &&
        Objects.equals(this.from, roleDetailsDto.from) &&
        Objects.equals(this.to, roleDetailsDto.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rate, from, to);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RoleDetailsDto {\n");
    sb.append("    rate: ").append(toIndentedString(rate)).append("\n");
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

