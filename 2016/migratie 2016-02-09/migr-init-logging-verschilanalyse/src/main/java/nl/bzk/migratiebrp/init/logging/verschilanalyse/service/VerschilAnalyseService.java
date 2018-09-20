/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.service;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.FingerPrint;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.VerschilAnalyseRegel;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Interface voor de verschil analyse service.
 */
public interface VerschilAnalyseService {
    /**
     * Bepaal de verschillen tussen de oorspronkelijke LO3 PL en de terug gevonverteerde LO3 PL die in de
     * {@link InitVullingLog} staan. Het resultaat van deze analyse wordt opgeslagen in het {@link InitVullingLog}.
     * 
     * @param vullingLog
     *            log van initiele vulling waar zowel de oorspronkelijk LO3 PL als de terug geconveteerde LO3 PL staan.
     */
    void bepaalVerschillen(final InitVullingLog vullingLog);

    /**
     * Bepaal de verschillen tussen 2 LO3 persoonslijsten.
     * 
     * @param lo3Pl
     *            versie 1 van een LO3 persoonslijst.
     * @param brpLo3Pl
     *            versie 2 van een LO3 persoonslijst.
     * @return een lijst met daarin een {@link Pair} met als key een lijst van alle afzonderlijke verschillen en als
     *         value een {@link FingerPrint} met daarin alle verschillen als 1 regel.
     */
    List<Pair<List<VerschilAnalyseRegel>, FingerPrint>> bepaalVerschillen(final Lo3Persoonslijst lo3Pl, final Lo3Persoonslijst brpLo3Pl);
}
