package com.devopslabs.apitestautomation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

  public static final String MAIL_HOST = "mail.host";

  public static final String MAIL_PORT = "mail.port";

  public static final String MAIL_FROM = "mail.from";

  private final Environment env;

  @Autowired
  public MailConfig(Environment env) {
    this.env = env;
  }

  @Bean
  public JavaMailSender mailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(env.getProperty(MAIL_HOST));
    mailSender.setPort(env.getProperty(MAIL_PORT, Integer.class, 25));
    return mailSender;
  }
}
