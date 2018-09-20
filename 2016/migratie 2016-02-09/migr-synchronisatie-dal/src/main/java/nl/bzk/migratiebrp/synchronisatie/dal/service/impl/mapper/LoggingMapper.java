/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Severity;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3SoortMelding;

/**
 * Mapper voor logging.
 */
public final class LoggingMapper {

    /**
     * Map de gegeven logging naar het bericht en de persoon.
     * 
     * @param logging
     *            logging
     * @param bericht
     *            het lo3 bericht
     */
    public void mapLogging(final Logging logging, final Lo3Bericht bericht) {
        if (logging != null && bericht != null) {
            for (final LogRegel logRegel : logging.getRegels()) {
                final String categorie = logRegel.getLo3Herkomst().getCategorie().getCategorie();
                Integer stapelvolgnummer = null;
                if (logRegel.getLo3Herkomst().getStapel() != -1) {
                    stapelvolgnummer = logRegel.getLo3Herkomst().getStapel();
                }
                Integer voorkomenvolgnummer = null;
                if (logRegel.getLo3Herkomst().getVoorkomen() != -1) {
                    voorkomenvolgnummer = logRegel.getLo3Herkomst().getVoorkomen();
                }
                final BRPActie actie = null;
                final Lo3SoortMelding soortMelding = Lo3SoortMelding.valueOf(logRegel.getSoortMeldingCode().toString());
                final Lo3Severity logSeverity = Lo3Severity.valueOf(logRegel.getSeverity().toString());
                String groep = null;
                String rubriek = null;
                if (logRegel.getLo3ElementNummer() != null) {
                    groep = logRegel.getLo3ElementNummer().getGroep().toString();
                    rubriek = logRegel.getLo3ElementNummer().getRubriek().toString();
                }
                bericht.addMelding(categorie, stapelvolgnummer, voorkomenvolgnummer, actie, soortMelding, logSeverity, groep, rubriek);
            }
        }
    }
}
