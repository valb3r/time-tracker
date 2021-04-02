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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * ProjectCreateOrUpdate
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-04-02T21:37:43.523+03:00[Europe/Kiev]")
public class ProjectCreateOrUpdate {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_CODE = "code";
  @SerializedName(SERIALIZED_NAME_CODE)
  private String code;

  public static final String SERIALIZED_NAME_ACTIVITIES = "activities";
  @SerializedName(SERIALIZED_NAME_ACTIVITIES)
  private Set<String> activities = new LinkedHashSet<>();

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;


  public ProjectCreateOrUpdate name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public ProjectCreateOrUpdate code(String code) {
    
    this.code = code;
    return this;
  }

   /**
   * Get code
   * @return code
  **/
  @ApiModelProperty(required = true, value = "")

  public String getCode() {
    return code;
  }


  public void setCode(String code) {
    this.code = code;
  }


  public ProjectCreateOrUpdate activities(Set<String> activities) {
    
    this.activities = activities;
    return this;
  }

  public ProjectCreateOrUpdate addActivitiesItem(String activitiesItem) {
    this.activities.add(activitiesItem);
    return this;
  }

   /**
   * Get activities
   * @return activities
  **/
  @ApiModelProperty(required = true, value = "")

  public Set<String> getActivities() {
    return activities;
  }


  public void setActivities(Set<String> activities) {
    this.activities = activities;
  }


  public ProjectCreateOrUpdate description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectCreateOrUpdate projectCreateOrUpdate = (ProjectCreateOrUpdate) o;
    return Objects.equals(this.name, projectCreateOrUpdate.name) &&
        Objects.equals(this.code, projectCreateOrUpdate.code) &&
        Objects.equals(this.activities, projectCreateOrUpdate.activities) &&
        Objects.equals(this.description, projectCreateOrUpdate.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, code, activities, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectCreateOrUpdate {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    activities: ").append(toIndentedString(activities)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

