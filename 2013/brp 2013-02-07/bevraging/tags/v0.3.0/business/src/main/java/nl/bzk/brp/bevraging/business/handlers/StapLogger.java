/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Logging aspect voor het loggen van de stappen die uitgevoerd worden.
 */
@Component
@Aspect
public class StapLogger {

    private static final Logger LOG = LoggerFactory.getLogger(StapLogger.class);

    /**
     * Pointcut definitie die aangeeft dat elke call naar de 'voerVerwerkingsStapUitVoorBericht' methode wordt
     * gelogd.
     */
    @Pointcut("execution(* nl.bzk.brp.bevraging.business.handlers.*.voerVerwerkingsStapUitVoorBericht(..))")
    public void stapLog() {
    }

    /**
     * De log methode die wordt aangeroepen vlak voor elke stap verwerking.
     *
     * @param joinPoint het punt dat door de aspect wordt geraakt.
     */
    @Before("stapLog()")
    public void log(final JoinPoint joinPoint) {
        LOG.debug(String.format("Start verwerking van stap '%s' voor bericht %s.", joinPoint.getTarget().getClass()
                .getSimpleName(), ((BerichtContext) joinPoint.getArgs()[1]).getIngaandBerichtId()));
    }

}
