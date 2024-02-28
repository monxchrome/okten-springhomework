package org.example.rest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * RefreshDto
 */

@JsonTypeName("Refresh")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-28T08:58:20.077542+01:00[Europe/Podgorica]")
public class RefreshDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private String refreshToken;

  public RefreshDto refreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

  /**
   * Get refreshToken
   * @return refreshToken
  */
  @Size(min = 1, max = 64) 
  @Schema(name = "refreshToken", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.pDtE-M44fneD--ljZ9PY3hjPF5HlkNd64_s9w5QdEls", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("refreshToken")
  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RefreshDto refresh = (RefreshDto) o;
    return Objects.equals(this.refreshToken, refresh.refreshToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(refreshToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RefreshDto {\n");
    sb.append("    refreshToken: ").append(toIndentedString(refreshToken)).append("\n");
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

