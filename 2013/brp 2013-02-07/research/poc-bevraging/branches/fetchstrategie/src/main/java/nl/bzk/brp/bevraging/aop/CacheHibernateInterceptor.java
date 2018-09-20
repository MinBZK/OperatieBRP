/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.aop;

import org.hibernate.stat.Statistics;
import org.hibernate.SessionFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.springframework.util.StopWatch;


@Aspect
public class CacheHibernateInterceptor {
    private Logger LOGGER = LoggerFactory.getLogger(CacheHibernateInterceptor.class);

    @Around("execution(* nl.bzk.brp.bevraging.dataaccess..*.*(..))")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch timer = new StopWatch();
        timer.start("mapping");
        Object result = pjp.proceed();

        timer.stop();
        LOGGER.info("Mapping time: {}", timer.shortSummary());

        return result;
    }

    @Around("execution(* nl.bzk.copy.dataaccess.repository.jpa..*.*(..))")
    public Object database(ProceedingJoinPoint pjp) throws Throwable {

        StopWatch timer = new StopWatch();
        timer.start("query");
        Object result = pjp.proceed();

        timer.stop();
        LOGGER.info("DB time: {}", timer.shortSummary());
        return result;
    }
}