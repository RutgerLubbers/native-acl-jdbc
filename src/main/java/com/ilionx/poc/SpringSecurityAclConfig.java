package com.ilionx.poc;

import javax.sql.DataSource;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionCacheOptimizer;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Configuration for Spring Security's Domain Object Security (ACLs).
 */
@Configuration
public class SpringSecurityAclConfig {

  /**
   * Create the ACL authorization strategy.
   */
  @Bean
  public AclAuthorizationStrategy aclAuthorizationStrategy() {
    return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }

  /**
   * Create a {@link PermissionCacheOptimizer} bean.
   */
  @Bean
  public PermissionCacheOptimizer aclPermissionCacheOptimizer(final AclService aclService) {
    return new AclPermissionCacheOptimizer(aclService);
  }

  /**
   * Create an {@link AclPermissionEvaluator} bean.
   */
  @Bean
  public AclPermissionEvaluator aclPermissionEvaluator(final AclService aclService) {
    return new AclPermissionEvaluator(aclService);
  }

  /**
   * Create an {@link AuditLogger} bean.
   */
  @Bean
  public AuditLogger auditLogger() {
    return new ConsoleAuditLogger();
  }

  /**
   * Create the Basic Lookup Strategy (to retrieve ACLs with).
   */
  @Bean
  public LookupStrategy basicLookupStrategy(final DataSource dataSource,
      final AclCache aclCache,
      final AclAuthorizationStrategy aclAuthorizationStrategy,
      final PermissionGrantingStrategy permissionGrantingStrategy) {
    final BasicLookupStrategy basicLookupStrategy = new BasicLookupStrategy(dataSource,
        aclCache,
        aclAuthorizationStrategy,
        permissionGrantingStrategy
    );
    basicLookupStrategy.setAclClassIdSupported(true);
    return basicLookupStrategy;
  }

  /**
   * Create a {@link MethodSecurityExpressionHandler} bean, using the
   * {@link AclPermissionEvaluator}.
   */
  @Bean
  public MethodSecurityExpressionHandler methodSecurityExpressionHandler(
      final AclPermissionEvaluator aclPermissionEvaluator,
      final PermissionCacheOptimizer cacheOptimizer) {
    // https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html
    // https://stackoverflow.com/questions/41872192/access-is-always-denied-in-spring-security-denyallpermissionevaluator

    final DefaultMethodSecurityExpressionHandler expressionHandler =
        new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setPermissionEvaluator(aclPermissionEvaluator);
    expressionHandler.setPermissionCacheOptimizer(cacheOptimizer);
    return expressionHandler;
  }

  /**
   * Create a Mutable ACL service.
   */
  @Bean
  public MutableAclService mutableAclService(final DataSource dataSource,
      final LookupStrategy lookupStrategy,
      final AclCache aclCache) {
    final JdbcMutableAclService aclService = new JdbcMutableAclService(dataSource,
        lookupStrategy,
        aclCache);
    aclService.setClassIdentityQuery("SELECT currval('acl_class_id_seq')");
    aclService.setSidIdentityQuery("SELECT currval('acl_sid_id_seq')");
    aclService.setAclClassIdSupported(true);
    return aclService;
  }

  /**
   * Create a {@link DefaultPermissionGrantingStrategy} bean.
   */
  @Bean
  public PermissionGrantingStrategy permissionGrantingStrategy(final AuditLogger auditLogger) {
    return new DefaultPermissionGrantingStrategy(auditLogger);
  }

  /**
   * Create an ACL with a Concurrent Map backend.
   */
  @Bean
  public AclCache springCacheBasedAclCache(
      final PermissionGrantingStrategy permissionGrantingStrategy,
      final AclAuthorizationStrategy aclAuthorizationStrategy) {
    return new SpringCacheBasedAclCache(new ConcurrentMapCache("userCache"),
        permissionGrantingStrategy,
        aclAuthorizationStrategy);
  }
}
