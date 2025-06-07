package com.study.toy.global;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //AuditingEntityListner 활성화
public class JpaAuditingConfig {
}