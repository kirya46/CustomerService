package com.common.config.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by Kirill Stoianov on 16/09/17.
 */
public class LiquibaseServiceLocatorApplicationListener
        implements ApplicationListener<ApplicationStartingEvent> {

    private static final Log logger = LogFactory
            .getLog(LiquibaseServiceLocatorApplicationListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
//        if (ClassUtils.isPresent("liquibase.servicelocator.ServiceLocator", null)) {
//            new LiquibasePresent().replaceServiceLocator();
//        }
    }

    /**
     * Inner class to prevent class not found issues.
     */
    private static class LiquibasePresent {

//        public void replaceServiceLocator() {
//            CustomResolverServiceLocator customResolverServiceLocator = new CustomResolverServiceLocator(
//                    new SpringPackageScanClassResolver(logger));
//            customResolverServiceLocator.addPackageToScan(
//                    CommonsLoggingLiquibaseLogger.class.getPackage().getName());
//            ServiceLocator.setInstance(customResolverServiceLocator);
//            liquibase.logging.LogFactory.reset();
//        }

    }

}