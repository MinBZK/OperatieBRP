/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigeAutorisatieException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Lo3AfnemersindicatiePrecondities;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Lo3AutorisatiePrecondities;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Lo3PersoonslijstPrecondities;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.LoggingContext;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@SuppressWarnings("checkstyle:designforextension")
public class PreconditiesService {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private Lo3PersoonslijstPrecondities persoonslijstPrecondities;
    @Inject
    private Lo3PersoonslijstOpschoner persoonslijstOpschoner;
    @Inject
    private Lo3AfnemersindicatiePrecondities afnemersindicatiePrecondities;
    @Inject
    private Lo3AutorisatiePrecondities autorisatiePrecondities;
    @Inject
    private Lo3AfnemersindicatieOpschoner afnemersindicatieOpschoner;

    /**
     * Verwerkt de opgegeven persoonslijst door de precondities.
     *
     * @param lo3Persoonslijst
     *            de persoonslijst die gecontroleerd moet worden
     * @return de (opgeschonde) lo3Persoonslijst
     * @throws OngeldigePersoonslijstException
     *             Wordt gegooid als er na het opschonen nog steeds een fout met ERROR niveau voorkomt
     */
    public Lo3Persoonslijst verwerk(final Lo3Persoonslijst lo3Persoonslijst) throws OngeldigePersoonslijstException {
        /*
         * methode niet final maken anders faalt de test met mockito in
         * migr-sync-runtime.SynchronisatieMessageHandlerTest
         */
        LOG.debug("verwerk(lo3Persoonslijst={})", lo3Persoonslijst);
        // Log4j logging
        LoggingContext.registreerActueelAdministratienummer(lo3Persoonslijst.getActueelAdministratienummer());

        Lo3Persoonslijst lo3 = lo3Persoonslijst;
        while (true) {
            persoonslijstPrecondities.controleerPersoonslijst(lo3);

            final Logging logging = Logging.getLogging();
            if (logging.containSeverityLevelError()) {
                lo3 = persoonslijstOpschoner.opschonen(lo3);

                // Bij het opschonen wordt de severity terug gezet naar Warning voor die rijen die onjuist zijn
                if (logging.containSeverityLevelError() || logging.containSeverityLevelCritical()) {
                    throw new OngeldigePersoonslijstException("Persoon bevat preconditie fouten");
                }

                // Als we een dummy PL terugkrijgen dan moet die niet opnieuw gecontroleerd worden.
                if (lo3.isDummyPl()) {
                    break;
                }
            } else {
                break;
            }
        }

        return lo3;

    }

    /**
     * Controleer afnemersindicaties.
     *
     * @param afnemersindicatie
     *            afnemersindicatie
     * @return Lo3Afnemersindicatie Lo3Afnemersindicatie
     */
    public Lo3Afnemersindicatie verwerk(final Lo3Afnemersindicatie afnemersindicatie) {
        /*
         * methode niet final maken anders faalt de test met mockito in migr-sync-runtime.AfnemersindicatieServiceTest
         */
        LOG.debug("verwerk(afnemersindicatie={})", afnemersindicatie);
        LoggingContext.registreerActueelAdministratienummer(afnemersindicatie.getANummer());

        afnemersindicatiePrecondities.controleerAfnemersindicatie(afnemersindicatie);
        final Lo3Afnemersindicatie result = afnemersindicatieOpschoner.opschonen(afnemersindicatie);

        return result;
    }

    /**
     * Controleer afnemersindicaties.
     *
     * @param autorisatie
     *            autorisatie
     * @throws OngeldigeAutorisatieException
     *             bij fouten
     */
    public void verwerk(final Lo3Autorisatie autorisatie) throws OngeldigeAutorisatieException {
        /*
         * methode niet final maken anders faalt de test met mockito in migr-sync-runtime.AfnemersindicatieServiceTest
         */
        final Logging logging = Logging.getLogging();
        LOG.debug("verwerk(autorisatie={})", autorisatie);

        autorisatiePrecondities.controleerAutorisatie(autorisatie);

        if (logging.containSeverityLevelError() || logging.containSeverityLevelCritical()) {
            throw new OngeldigeAutorisatieException();
        }
    }

}
