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


package ua.timetracker.desktoptracker.api.tracker.model;

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
 * ProjectDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-04-06T20:10:36.938113+03:00[Europe/Kiev]")
public class ProjectDto {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Long id;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_CODE = "code";
  @SerializedName(SERIALIZED_NAME_CODE)
  private String code;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_ACTIVITIES = "activities";
  @SerializedName(SERIALIZED_NAME_ACTIVITIES)
  private Set<String> activities = null;

  public static final String SERIALIZED_NAME_SCREENSHOTS = "screenshots";
  @SerializedName(SERIALIZED_NAME_SCREENSHOTS)
  private Boolean screenshots;

  public static final String SERIALIZED_NAME_QUALITY = "quality";
  @SerializedName(SERIALIZED_NAME_QUALITY)
  private Float quality;

  public static final String SERIALIZED_NAME_INTERVALMINUTES = "intervalminutes";
  @SerializedName(SERIALIZED_NAME_INTERVALMINUTES)
  private Long intervalminutes;


  public ProjectDto id(Long id) {
    
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getId() {
    return id;
  }


  public void setId(Long id) {
    this.id = id;
  }


  public ProjectDto name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public ProjectDto code(String code) {
    
    this.code = code;
    return this;
  }

   /**
   * Get code
   * @return code
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getCode() {
    return code;
  }


  public void setCode(String code) {
    this.code = code;
  }


  public ProjectDto description(String description) {
    
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


  public ProjectDto activities(Set<String> activities) {
    
    this.activities = activities;
    return this;
  }

  public ProjectDto addActivitiesItem(String activitiesItem) {
    if (this.activities == null) {
      this.activities = new LinkedHashSet<>();
    }
    this.activities.add(activitiesItem);
    return this;
  }

   /**
   * Get activities
   * @return activities
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Set<String> getActivities() {
    return activities;
  }


  public void setActivities(Set<String> activities) {
    this.activities = activities;
  }


  public ProjectDto screenshots(Boolean screenshots) {
    
    this.screenshots = screenshots;
    return this;
  }

   /**
   * Get screenshots
   * @return screenshots
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getScreenshots() {
    return screenshots;
  }


  public void setScreenshots(Boolean screenshots) {
    this.screenshots = screenshots;
  }


  public ProjectDto quality(Float quality) {
    
    this.quality = quality;
    return this;
  }

   /**
   * Get quality
   * @return quality
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Float getQuality() {
    return quality;
  }


  public void setQuality(Float quality) {
    this.quality = quality;
  }


  public ProjectDto intervalminutes(Long intervalminutes) {
    
    this.intervalminutes = intervalminutes;
    return this;
  }

   /**
   * Get intervalminutes
   * @return intervalminutes
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getIntervalminutes() {
    return intervalminutes;
  }


  public void setIntervalminutes(Long intervalminutes) {
    this.intervalminutes = intervalminutes;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectDto projectDto = (ProjectDto) o;
    return Objects.equals(this.id, projectDto.id) &&
        Objects.equals(this.name, projectDto.name) &&
        Objects.equals(this.code, projectDto.code) &&
        Objects.equals(this.description, projectDto.description) &&
        Objects.equals(this.activities, projectDto.activities) &&
        Objects.equals(this.screenshots, projectDto.screenshots) &&
        Objects.equals(this.quality, projectDto.quality) &&
        Objects.equals(this.intervalminutes, projectDto.intervalminutes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, code, description, activities, screenshots, quality, intervalminutes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    activities: ").append(toIndentedString(activities)).append("\n");
    sb.append("    screenshots: ").append(toIndentedString(screenshots)).append("\n");
    sb.append("    quality: ").append(toIndentedString(quality)).append("\n");
    sb.append("    intervalminutes: ").append(toIndentedString(intervalminutes)).append("\n");
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

