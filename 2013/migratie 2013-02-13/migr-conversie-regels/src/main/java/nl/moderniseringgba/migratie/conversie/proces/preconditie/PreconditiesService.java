/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3.Lo3PersoonslijstPrecondities;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.logging.LoggingContext;

import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class PreconditiesService {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private Lo3PersoonslijstPrecondities precondities;
    @Inject
    private Lo3PersoonslijstOpschoner opschoner;

    /**
     * Verwerkt de opgegeven persoonslijst door de precondities.
     * 
     * @param lo3Persoonslijst
     *            de persoonslijst die gecontroleerd moet worden
     * @return de (opgeschonde) lo3Persoonslijst
     * @throws OngeldigePersoonslijstException
     *             Wordt gegooid als er na het opschonen nog steeds een fout met ERROR niveau voorkomt
     */
    // CHECKSTYLE:OFF methode niet final maken anders faalt de test met mockito in
    // migr-sync-runtime.SynchronisatieMessageHandlerTest
    public Lo3Persoonslijst verwerk(final Lo3Persoonslijst lo3Persoonslijst) throws OngeldigePersoonslijstException {
        LOG.debug("verwerk(lo3Persoonslijst={})", lo3Persoonslijst);
        // CHECKSTYLE:ON
        // Log4j logging
        LoggingContext.registreerActueelAdministratienummer(lo3Persoonslijst.getActueelAdministratienummer());

        Lo3Persoonslijst lo3 = lo3Persoonslijst;
        while (true) {
            precondities.controleerPersoonslijst(lo3);

            final Logging logging = Logging.getLogging();
            if (logging.containSeverityLevelError()) {
                lo3 = opschoner.opschonen(lo3);

                // Bij het opschonen wordt de severity terug gezet naar Warning voor die rijen die onjuist zijn
                if (logging.containSeverityLevelError() || logging.containSeverityLevelCritical()) {
                    throw new OngeldigePersoonslijstException();
                }
            } else {
                break;
            }
        }

        return lo3;

    }

}
