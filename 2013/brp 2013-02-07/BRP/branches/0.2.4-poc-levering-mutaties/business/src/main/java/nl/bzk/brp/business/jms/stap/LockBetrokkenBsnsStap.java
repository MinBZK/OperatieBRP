/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.stap;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.handlers.BsnLockerExceptie;
import nl.bzk.brp.business.jms.LevMutAdmHandBerichtContext;
import nl.bzk.brp.business.service.BsnLocker;
import nl.bzk.brp.dataaccess.special.ActieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class LockBetrokkenBsnsStap extends AbstractBerichtVerwerkingsStap {
    private final Logger LOGGER = LoggerFactory.getLogger(LockBetrokkenBsnsStap.class);

    @Inject
    private BsnLocker bsnLocker;

    @Inject
    private ActieRepository actieRepository;

    @Override
    public StapResultaat voerVerwerkingsStapUitVoorBericht(
            final LevMutAdmHandBerichtContext context)
    {
        Long adminstratieveHandelingId = context.getAdministratieveHandelingId();

        List<String> bsns = context.getBetrokkenBsns();
        if (bsns != null) {
	        try {
//	        	LOGGER.error("Locken BSNs {}:", bsns);
	            bsnLocker.getLocks(bsns, null, adminstratieveHandelingId);
	        } catch (BsnLockerExceptie e) {
	            LOGGER.error("Locken BSNs {} gefaald", bsns, e);
	            return StapResultaat.STOP_VERWERKING;
	        }
        }

        return StapResultaat.DOORGAAN_MET_VERWERKING;
    }

    @Override
    public void naVerwerkingsStapVoorBericht(final LevMutAdmHandBerichtContext context) {
        bsnLocker.unLock();
    }
}
