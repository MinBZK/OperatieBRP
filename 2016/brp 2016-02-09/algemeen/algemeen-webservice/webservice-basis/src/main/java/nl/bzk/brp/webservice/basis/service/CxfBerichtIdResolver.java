/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.basis.service;

import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import nl.bzk.brp.webservice.kern.interceptor.ArchiveringBericht;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class CxfBerichtIdResolver implements BerichtIdResolver {

    private static final Logger LOG = LoggerFactory.getLogger(CxfBerichtIdResolver.class);

    /**
     * Haalt de ids van het gearchiveerde binnenkomende bericht op, alsmede het id voor het uitgaande bericht. Deze
     * worden gelezen van de CXF message context, welke onderwater wordt opgehaald middels threadlocal.
     *
     * @return de berichten ids.
     */
    @Override
    public BerichtenIds haalBerichtenIdsOp() {
        final Long berichtInId = (Long) PhaseInterceptorChain.getCurrentMessage().getExchange().get(
            ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID);
        final Long berichtUitId = (Long) PhaseInterceptorChain.getCurrentMessage().getExchange().get(
            ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID);

        if (berichtInId == null || berichtUitId == null) {
            throw new IllegalStateException("Bericht archivering ids niet beschikbaar");
        }

        LOG.debug("Berichten ids opgehaald: IN={}, UIT={}", berichtInId, berichtUitId);
        return new BerichtenIds(berichtInId, berichtUitId);
    }
}
