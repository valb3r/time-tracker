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
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import ua.timetracker.desktoptracker.api.tracker.model.ProjectDto;
import ua.timetracker.desktoptracker.api.tracker.model.TimeLogDtoDuration;

/**
 * TimeLogDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-04-02T21:37:44.187+03:00[Europe/Kiev]")
public class TimeLogDto {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Long id;

  public static final String SERIALIZED_NAME_PROJECTID = "projectid";
  @SerializedName(SERIALIZED_NAME_PROJECTID)
  private Long projectid;

  public static final String SERIALIZED_NAME_TAGS = "tags";
  @SerializedName(SERIALIZED_NAME_TAGS)
  private List<String> tags = null;

  public static final String SERIALIZED_NAME_DURATION = "duration";
  @SerializedName(SERIALIZED_NAME_DURATION)
  private TimeLogDtoDuration duration;

  public static final String SERIALIZED_NAME_DURATIONMINUTES = "durationminutes";
  @SerializedName(SERIALIZED_NAME_DURATIONMINUTES)
  private Long durationminutes;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_LOCATION = "location";
  @SerializedName(SERIALIZED_NAME_LOCATION)
  private String location;

  public static final String SERIALIZED_NAME_PROJECTS = "projects";
  @SerializedName(SERIALIZED_NAME_PROJECTS)
  private Set<ProjectDto> projects = null;

  public static final String SERIALIZED_NAME_TIMESTAMP = "timestamp";
  @SerializedName(SERIALIZED_NAME_TIMESTAMP)
  private OffsetDateTime timestamp;


  public TimeLogDto id(Long id) {
    
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


  public TimeLogDto projectid(Long projectid) {
    
    this.projectid = projectid;
    return this;
  }

   /**
   * Get projectid
   * @return projectid
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getProjectid() {
    return projectid;
  }


  public void setProjectid(Long projectid) {
    this.projectid = projectid;
  }


  public TimeLogDto tags(List<String> tags) {
    
    this.tags = tags;
    return this;
  }

  public TimeLogDto addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * Get tags
   * @return tags
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getTags() {
    return tags;
  }


  public void setTags(List<String> tags) {
    this.tags = tags;
  }


  public TimeLogDto duration(TimeLogDtoDuration duration) {
    
    this.duration = duration;
    return this;
  }

   /**
   * Get duration
   * @return duration
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public TimeLogDtoDuration getDuration() {
    return duration;
  }


  public void setDuration(TimeLogDtoDuration duration) {
    this.duration = duration;
  }


  public TimeLogDto durationminutes(Long durationminutes) {
    
    this.durationminutes = durationminutes;
    return this;
  }

   /**
   * Get durationminutes
   * @return durationminutes
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getDurationminutes() {
    return durationminutes;
  }


  public void setDurationminutes(Long durationminutes) {
    this.durationminutes = durationminutes;
  }


  public TimeLogDto description(String description) {
    
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


  public TimeLogDto location(String location) {
    
    this.location = location;
    return this;
  }

   /**
   * Get location
   * @return location
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getLocation() {
    return location;
  }


  public void setLocation(String location) {
    this.location = location;
  }


  public TimeLogDto projects(Set<ProjectDto> projects) {
    
    this.projects = projects;
    return this;
  }

  public TimeLogDto addProjectsItem(ProjectDto projectsItem) {
    if (this.projects == null) {
      this.projects = new LinkedHashSet<>();
    }
    this.projects.add(projectsItem);
    return this;
  }

   /**
   * Get projects
   * @return projects
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Set<ProjectDto> getProjects() {
    return projects;
  }


  public void setProjects(Set<ProjectDto> projects) {
    this.projects = projects;
  }


  public TimeLogDto timestamp(OffsetDateTime timestamp) {
    
    this.timestamp = timestamp;
    return this;
  }

   /**
   * Get timestamp
   * @return timestamp
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }


  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimeLogDto timeLogDto = (TimeLogDto) o;
    return Objects.equals(this.id, timeLogDto.id) &&
        Objects.equals(this.projectid, timeLogDto.projectid) &&
        Objects.equals(this.tags, timeLogDto.tags) &&
        Objects.equals(this.duration, timeLogDto.duration) &&
        Objects.equals(this.durationminutes, timeLogDto.durationminutes) &&
        Objects.equals(this.description, timeLogDto.description) &&
        Objects.equals(this.location, timeLogDto.location) &&
        Objects.equals(this.projects, timeLogDto.projects) &&
        Objects.equals(this.timestamp, timeLogDto.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, projectid, tags, duration, durationminutes, description, location, projects, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TimeLogDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    projectid: ").append(toIndentedString(projectid)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    durationminutes: ").append(toIndentedString(durationminutes)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    projects: ").append(toIndentedString(projects)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
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

