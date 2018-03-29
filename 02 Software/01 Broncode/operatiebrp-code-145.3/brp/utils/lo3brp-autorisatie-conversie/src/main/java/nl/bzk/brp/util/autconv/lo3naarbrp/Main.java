/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;


import java.io.IOException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

/**
 * Main class voor het starten van de conversie.
 */
public final class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private Main() {
    }

    public static void main(String[] args) {

        Assert.isTrue(!StringUtils.isEmpty(System.getProperty("brp.afleverpunt")), "Systeemproperty '-Dbrp.afleverpunt=URI' moet opgegeven worden");
        Assert.isTrue(!StringUtils.isEmpty(System.getProperty("workdir")), "Systeemproperty '-Dworkdir' moet opgegeven worden");

        System.setProperty("spring.profiles.active", "copy");
        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConversieConfiguratie.class)) {
            applicationContext.getBean(Converteerder.class).converteer();
        } catch (IOException e) {
            LOGGER.error("Conversie mislukt", e);
        }
    }
}
