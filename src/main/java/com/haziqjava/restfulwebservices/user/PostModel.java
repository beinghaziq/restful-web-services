package com.haziqjava.restfulwebservices.user;

import jakarta.persistence.*;

@Entity(name = "post")
public class PostModel {
  @Id
  @GeneratedValue
  private Integer id;
  private String description;
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "PostModel{" +
            "id=" + id +
            ", description='" + description + '\'' +
            '}';
  }
}
