/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.Calendar;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.ber.LeveringBericht;
import nl.bzk.brp.bevraging.domein.lev.Levering;
import nl.bzk.brp.bevraging.domein.lev.LeveringPersoon;
import nl.bzk.brp.bevraging.domein.lev.LeveringSoort;
import nl.bzk.brp.bevraging.domein.repository.AbonnementRepository;
import nl.bzk.brp.bevraging.domein.repository.AuthenticatieMiddelRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringBerichtRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringPersoonRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * De stap in de uitvoering van een bericht waarin de protocollering van een bericht wordt afgehandeld.
 */
public class ProtocolleringStap extends AbstractBerichtVerwerkingsStap {

    @Inject
    private LeveringRepository            leveringRepository;

    @Inject
    private LeveringPersoonRepository     leveringPersoonRepository;

    @Inject
    private LeveringBerichtRepository     leveringBerichtRepository;

    @Inject
    private AbonnementRepository          abonnementRepository;

    @Inject
    private AuthenticatieMiddelRepository authenticatieMiddelRepository;

    /**
     * {@inheritDoc}
     *
     * Protocolleert het BrpBerichtCommand in de tabellen, Levering, Levering/Persoon.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public final boolean voerVerwerkingsStapUitVoorBericht(final BrpBerichtCommand brpBerichtCommand) {

        Levering levering = new Levering();
        levering.setSoort(LeveringSoort.BRP_BEVRAGING);
        levering.setAbonnement(abonnementRepository.findOne(brpBerichtCommand.getContext().getAbonnementId()));
        levering.setAuthenticatieMiddel(authenticatieMiddelRepository.findOne(brpBerichtCommand.getContext()
                .getAuthenticatieMiddelId()));

        levering.setBeschouwing(brpBerichtCommand.getVerzoek().getBeschouwing());
        levering.setLevering(Calendar.getInstance());
        leveringRepository.save(levering);

        for (Persoon persoon : brpBerichtCommand.getAntwoord().getPersonen()) {
            LeveringPersoon leveringPersoon = new LeveringPersoon();
            leveringPersoon.setLevering(levering);
            leveringPersoon.setPersoon(persoon);
            leveringPersoonRepository.save(leveringPersoon);
        }

        LeveringBericht leveringBericht = new LeveringBericht();

        leveringBericht.setLevering(levering);
        leveringBericht.setBericht(brpBerichtCommand.getContext().getBerichtId());

        leveringBerichtRepository.save(leveringBericht);

        return DOORGAAN_MET_VERWERKING;
    }

}
