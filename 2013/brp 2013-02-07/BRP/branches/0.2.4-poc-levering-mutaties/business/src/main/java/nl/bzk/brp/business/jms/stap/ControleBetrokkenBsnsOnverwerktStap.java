/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.stap;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.jms.LevMutAdmHandBerichtContext;
import nl.bzk.brp.dataaccess.special.ActieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControleBetrokkenBsnsOnverwerktStap extends AbstractBerichtVerwerkingsStap {

	private static final Logger LOGGER = LoggerFactory.getLogger(ControleBetrokkenBsnsOnverwerktStap.class);

	@Inject
    private ActieRepository actieRepository;

    @Override
    public StapResultaat voerVerwerkingsStapUitVoorBericht(final LevMutAdmHandBerichtContext context) {

    	List<String> bsnsVanActie = actieRepository.bepaalBetrokkenBsnsVanActie(context.getAdministratieveHandelingId());
    	context.setBetrokkenBsns(bsnsVanActie);

    	List<String> bsnsEerderOnverwerkt = actieRepository.bepaalBetrokkenBsnsVanEerdereOnverwerkteActies(context.getActieModel().getTijdstipRegistratie());
    	if (bsnsVanActie != null) {
			for (String bsn : bsnsVanActie) {
				if (bsnsEerderOnverwerkt.contains(bsn)) {
					LOGGER.warn("Er staan nog eerdere onverwerkte acties voor deze BSN open: {}", bsn);
					return StapResultaat.STOP_VERWERKING;
				}
			}
    	}

        return StapResultaat.DOORGAAN_MET_VERWERKING;
    }
}
