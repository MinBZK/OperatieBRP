/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.stap;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.jms.LevMutAdmHandBerichtContext;
import nl.bzk.brp.business.jms.service.AfnemerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class VindAfnemersStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger LOGGER             = LoggerFactory.getLogger(VindAfnemersStap.class);

    @Inject
    private AfnemerService afnemerService;

    @Override
    public StapResultaat voerVerwerkingsStapUitVoorBericht(
            final LevMutAdmHandBerichtContext context)
    {
        List<Short> afnemerIds = afnemerService.getGeinteresseerdeAfnemers(context.getAdministratieveHandelingId());

        context.setPartijIds(afnemerIds);

        return StapResultaat.DOORGAAN_MET_VERWERKING;
    }
}
