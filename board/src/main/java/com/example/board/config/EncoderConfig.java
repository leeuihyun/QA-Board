package com.example.board.config;

import com.example.board.security.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncoderConfig {

  @Bean
  public Encoder encoder() {
    return new Encoder();
  }
}
