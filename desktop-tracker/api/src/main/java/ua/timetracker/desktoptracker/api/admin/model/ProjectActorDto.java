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
import ua.timetracker.desktoptracker.api.admin.model.GroupDto;
import ua.timetracker.desktoptracker.api.admin.model.UserDto;

/**
 * ProjectActorDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-04-02T21:37:43.523+03:00[Europe/Kiev]")
public class ProjectActorDto {
  public static final String SERIALIZED_NAME_ROLEID = "roleid";
  @SerializedName(SERIALIZED_NAME_ROLEID)
  private Long roleid;

  public static final String SERIALIZED_NAME_USER = "user";
  @SerializedName(SERIALIZED_NAME_USER)
  private UserDto user;

  public static final String SERIALIZED_NAME_SOURCE = "source";
  @SerializedName(SERIALIZED_NAME_SOURCE)
  private GroupDto source;


  public ProjectActorDto roleid(Long roleid) {
    
    this.roleid = roleid;
    return this;
  }

   /**
   * Get roleid
   * @return roleid
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getRoleid() {
    return roleid;
  }


  public void setRoleid(Long roleid) {
    this.roleid = roleid;
  }


  public ProjectActorDto user(UserDto user) {
    
    this.user = user;
    return this;
  }

   /**
   * Get user
   * @return user
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public UserDto getUser() {
    return user;
  }


  public void setUser(UserDto user) {
    this.user = user;
  }


  public ProjectActorDto source(GroupDto source) {
    
    this.source = source;
    return this;
  }

   /**
   * Get source
   * @return source
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public GroupDto getSource() {
    return source;
  }


  public void setSource(GroupDto source) {
    this.source = source;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectActorDto projectActorDto = (ProjectActorDto) o;
    return Objects.equals(this.roleid, projectActorDto.roleid) &&
        Objects.equals(this.user, projectActorDto.user) &&
        Objects.equals(this.source, projectActorDto.source);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roleid, user, source);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectActorDto {\n");
    sb.append("    roleid: ").append(toIndentedString(roleid)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
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

