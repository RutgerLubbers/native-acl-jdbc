package com.ilionx.poc;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration to define a datasource. Required, otherwise Spring ACL will not find a datasource.
 */
@Configuration
public class DataSourceConfig {

  /**
   * Create a proxied {@link DataSource} bean from {@link DataSourceProperties}.
   *
   * @param dataSourceProperties The datasource properties to create a datasource from.
   * @return the {@link DataSource}
   */
  @Primary
  @Bean
  public DataSource dataSource(final DataSourceProperties dataSourceProperties) {
    System.out.println(">>>");
    System.out.printf(">>> jdbc driver: '%s'%n", dataSourceProperties.getDriverClassName());
    System.out.printf(">>> jdbc url: '%s'%n", dataSourceProperties.getUrl());
    System.out.println(">>>");
    return dataSourceProperties.initializeDataSourceBuilder().build();
  }

  /**
   * Create a {@link DataSourceProperties} to use in the datasource proxy if no one is defined yet.
   *
   * @return the {@link DataSourceProperties}.
   */
  @Primary
  @Bean
  public DataSourceProperties dataSourceProperties() {
    System.out.println(">>>");
    System.out.println(">>> Creating DataSourceProperties");
    System.out.println(">>>");
    return new DataSourceProperties();
  }
}
