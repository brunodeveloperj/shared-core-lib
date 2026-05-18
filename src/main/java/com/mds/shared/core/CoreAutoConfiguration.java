package com.mds.shared.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring auto-configuration entry point for the MDS Communication Pattern library.
 *
 * <p>Enables component scanning across the {@code com.mds.shared.core} package,
 * registering all beans provided by this module.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@Configuration
@ComponentScan
public class CoreAutoConfiguration {}
