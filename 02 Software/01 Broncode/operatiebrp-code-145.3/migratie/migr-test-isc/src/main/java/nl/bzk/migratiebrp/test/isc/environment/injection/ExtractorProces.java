/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import java.util.Collections;
import java.util.Set;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.jbpm.JbpmHelperKanaal;
import nl.bzk.migratiebrp.test.isc.exception.TestException;

/**
 * Proces ID extractor.
 *
 * Bepaalt obv het messageid/bericht referentie in JBPM het proces id.
 */
public final class ExtractorProces implements Extractor {

    @Override
    public String extract(final Context context, final Bericht bericht, final String key) throws TestException {
        final String volgnummer = bericht.getTestBericht().getVolgnummer().toString();
        final String berichtReferentie = context.getCorrelator().getBerichtReferentie(volgnummer);
        if (berichtReferentie == null || "".equals(berichtReferentie)) {
            throw new TestException("Dit bericht heeft geen bericht referentie.");
        }

        final JbpmHelperKanaal jbpmHelperKanaal = context.getJbpmHelperKanaal();
        final Set<Long> processen = jbpmHelperKanaal.bepaalAlleProcessen(Collections.singletonList(berichtReferentie));

        if (processen == null || processen.size() != 1) {
            throw new TestException("Er is geen (enkel) proces aan te wijzigen voor bericht referentie " + berichtReferentie + ".");
        }

        return processen.iterator().next().toString();
    }
}
