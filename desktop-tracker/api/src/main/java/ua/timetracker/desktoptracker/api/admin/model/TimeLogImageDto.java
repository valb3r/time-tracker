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
 * TimeLogImageDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-04-20T17:18:58.671+03:00[Europe/Kiev]")
public class TimeLogImageDto {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Long id;

  public static final String SERIALIZED_NAME_IMAGEURL = "imageurl";
  @SerializedName(SERIALIZED_NAME_IMAGEURL)
  private String imageurl;

  public static final String SERIALIZED_NAME_DURATION = "duration";
  @SerializedName(SERIALIZED_NAME_DURATION)
  private String duration;

  public static final String SERIALIZED_NAME_TIMESTAMP = "timestamp";
  @SerializedName(SERIALIZED_NAME_TIMESTAMP)
  private LocalDateTime timestamp;

  public static final String SERIALIZED_NAME_DURATIONSECONDS = "durationseconds";
  @SerializedName(SERIALIZED_NAME_DURATIONSECONDS)
  private Long durationseconds;

  public static final String SERIALIZED_NAME_DURATIONMINUTES = "durationminutes";
  @SerializedName(SERIALIZED_NAME_DURATIONMINUTES)
  private Long durationminutes;


  public TimeLogImageDto id(Long id) {
    
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


  public TimeLogImageDto imageurl(String imageurl) {
    
    this.imageurl = imageurl;
    return this;
  }

   /**
   * Get imageurl
   * @return imageurl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getImageurl() {
    return imageurl;
  }


  public void setImageurl(String imageurl) {
    this.imageurl = imageurl;
  }


  public TimeLogImageDto duration(String duration) {
    
    this.duration = duration;
    return this;
  }

   /**
   * Get duration
   * @return duration
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "PT1S", value = "")

  public String getDuration() {
    return duration;
  }


  public void setDuration(String duration) {
    this.duration = duration;
  }


  public TimeLogImageDto timestamp(LocalDateTime timestamp) {
    
    this.timestamp = timestamp;
    return this;
  }

   /**
   * Get timestamp
   * @return timestamp
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public LocalDateTime getTimestamp() {
    return timestamp;
  }


  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }


  public TimeLogImageDto durationseconds(Long durationseconds) {
    
    this.durationseconds = durationseconds;
    return this;
  }

   /**
   * Get durationseconds
   * @return durationseconds
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getDurationseconds() {
    return durationseconds;
  }


  public void setDurationseconds(Long durationseconds) {
    this.durationseconds = durationseconds;
  }


  public TimeLogImageDto durationminutes(Long durationminutes) {
    
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimeLogImageDto timeLogImageDto = (TimeLogImageDto) o;
    return Objects.equals(this.id, timeLogImageDto.id) &&
        Objects.equals(this.imageurl, timeLogImageDto.imageurl) &&
        Objects.equals(this.duration, timeLogImageDto.duration) &&
        Objects.equals(this.timestamp, timeLogImageDto.timestamp) &&
        Objects.equals(this.durationseconds, timeLogImageDto.durationseconds) &&
        Objects.equals(this.durationminutes, timeLogImageDto.durationminutes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, imageurl, duration, timestamp, durationseconds, durationminutes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TimeLogImageDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    imageurl: ").append(toIndentedString(imageurl)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    durationseconds: ").append(toIndentedString(durationseconds)).append("\n");
    sb.append("    durationminutes: ").append(toIndentedString(durationminutes)).append("\n");
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

