/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.stap;

import javax.inject.Inject;

import nl.bzk.brp.business.jms.LevMutAdmHandBerichtContext;
import nl.bzk.brp.dataaccess.special.ActieRepository;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockTijdstipVerwerkingMutatieStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockTijdstipVerwerkingMutatieStap.class);

    @Inject
    ActieRepository actieRepository;

    @Override
    public StapResultaat voerVerwerkingsStapUitVoorBericht(final LevMutAdmHandBerichtContext context) {

        Long administratieveHandelingId = context.getAdministratieveHandelingId();
        if (context.getActieModel() == null && administratieveHandelingId != null) {

            if (actieRepository.lockTijdstipVerwerkingMutatie(administratieveHandelingId)) {
                ActieModel actieModel = actieRepository.findOne(administratieveHandelingId);

                if (actieModel != null) {
                    if (actieModel.getTijdstipVerwerkingMutatie() == null) {
                        context.setActieModel(actieModel);
                        return StapResultaat.DOORGAAN_MET_VERWERKING;
                    }
                }
            } else {
                LOGGER.warn("Kan geen lock krijgen op actie {}", administratieveHandelingId);
            }
        }
        return StapResultaat.STOP_VERWERKING;
    }

}
