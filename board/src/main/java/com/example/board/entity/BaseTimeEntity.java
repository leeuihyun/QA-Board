package com.example.board.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

  private String created_at;

  private String updated_at;

  @PrePersist
  void onPrePersist() {
    this.created_at = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    this.updated_at = created_at;
  }

  @PreUpdate
  void onPreUpdate() {
    this.updated_at = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }
}