/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel;

/**
 * Mapper voor logging.
 */
public final class LoggingMapper {

    /**
     * Map de gegeven logging naar het bericht en de persoon.
     * 
     * @param logging
     *            logging
     * @param berichtLog
     *            berichtlog
     * @param persoon
     *            persoon
     */
    public void mapLogging(final Logging logging, final BerichtLog berichtLog, final Persoon persoon) {
        if (logging != null && berichtLog != null) {
            for (final nl.moderniseringgba.migratie.conversie.model.logging.LogRegel logRegel : logging.getRegels()) {
                berichtLog.addLogRegel(mapLogRegel(logRegel));
            }
            berichtLog.setPersoon(persoon);
        }
    }

    private LogRegel mapLogRegel(final nl.moderniseringgba.migratie.conversie.model.logging.LogRegel logRegel) {
        final LogRegel result = new LogRegel();

        result.setSeverity(logRegel.getSeverity() == null ? null : logRegel.getSeverity().getSeverity());
        result.setType(logRegel.getType().name());

        if (logRegel.getLo3Herkomst() instanceof Lo3Herkomst) {
            final Lo3Herkomst herkomst = (Lo3Herkomst) logRegel.getLo3Herkomst();
            result.setCategorie(Integer.valueOf(herkomst.getCategorie().getCategorie()));
            result.setStapel(herkomst.getStapel());
            result.setVoorkomen(herkomst.getVoorkomen());
        }

        result.setCode(logRegel.getCode());
        result.setOmschrijving(logRegel.getOmschrijving());

        return result;
    }

}
