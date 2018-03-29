/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.jobs;

import nl.bzk.migratiebrp.voisc.runtime.VoiscService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;

/**
 * Job om berichten uit de database te lezen en die te versturen naar ISC.
 */
@DisallowConcurrentExecution
public final class VersturenNaarIscJob extends AbstractVoiscServiceJob {

    @Override
    protected void execute(final JobDataMap jdm, final VoiscService voiscService) {
        voiscService.berichtenVerzendenNaarIsc();
    }
}
