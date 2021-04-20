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

/**
 * PasswordUpdateDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-04-20T17:35:19.311+03:00[Europe/Kiev]")
public class PasswordUpdateDto {
  public static final String SERIALIZED_NAME_CURRENT = "current";
  @SerializedName(SERIALIZED_NAME_CURRENT)
  private String current;

  public static final String SERIALIZED_NAME_NEWPASSWORD = "newpassword";
  @SerializedName(SERIALIZED_NAME_NEWPASSWORD)
  private String newpassword;


  public PasswordUpdateDto current(String current) {
    
    this.current = current;
    return this;
  }

   /**
   * Get current
   * @return current
  **/
  @ApiModelProperty(required = true, value = "")

  public String getCurrent() {
    return current;
  }


  public void setCurrent(String current) {
    this.current = current;
  }


  public PasswordUpdateDto newpassword(String newpassword) {
    
    this.newpassword = newpassword;
    return this;
  }

   /**
   * Get newpassword
   * @return newpassword
  **/
  @ApiModelProperty(required = true, value = "")

  public String getNewpassword() {
    return newpassword;
  }


  public void setNewpassword(String newpassword) {
    this.newpassword = newpassword;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PasswordUpdateDto passwordUpdateDto = (PasswordUpdateDto) o;
    return Objects.equals(this.current, passwordUpdateDto.current) &&
        Objects.equals(this.newpassword, passwordUpdateDto.newpassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(current, newpassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PasswordUpdateDto {\n");
    sb.append("    current: ").append(toIndentedString(current)).append("\n");
    sb.append("    newpassword: ").append(toIndentedString(newpassword)).append("\n");
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

