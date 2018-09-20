/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.Calendar;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.domein.kern.Persoon;
import nl.bzk.brp.bevraging.domein.lev.Levering;
import nl.bzk.brp.bevraging.domein.lev.LeveringPersoon;
import nl.bzk.brp.bevraging.domein.lev.LeveringSoort;
import nl.bzk.brp.bevraging.domein.repository.AbonnementRepository;
import nl.bzk.brp.bevraging.domein.repository.AuthenticatieMiddelRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringCommunicatieRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringPersoonRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringRepository;
import nl.bzk.brp.bevraging.domein.ber.LeveringCommunicatie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * De stap in de uitvoering van een bericht waarin de protocollering van een bericht wordt afgehandeld.
 */
public class ProtocolleringStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger           LOGGER = LoggerFactory.getLogger(ProtocolleringStap.class);

    @Inject
    private LeveringRepository leveringRepository;

    @Inject
    private LeveringPersoonRepository leveringPersoonRepository;

    @Inject
    private AbonnementRepository abonnementRepository;

    @Inject
    private AuthenticatieMiddelRepository authenticatieMiddelRepository;

    @Inject
    private LeveringCommunicatieRepository leveringCommunicatieRepository;

    /**
     * {@inheritDoc}
     *
     * Protocolleert het BerichtCommand in de tabellen, Levering, Levering/Persoon.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public final <T extends BerichtAntwoord> boolean voerVerwerkingsStapUitVoorBericht(final BerichtVerzoek<T> verzoek,
            final BerichtContext context, final T antwoord)
    {
        try {
            Levering levering = new Levering();
            levering.setSoort(LeveringSoort.BRP_BEVRAGING);
            levering.setAbonnement(abonnementRepository.findOne(context.getAbonnementId()));
            levering.setAuthenticatieMiddel(authenticatieMiddelRepository.findOne(context.getAuthenticatieMiddelId()));
            levering.setGebaseerdOp(context.getIngaandBerichtId());

            levering.setBeschouwing(verzoek.getBeschouwing());
            levering.setLevering(Calendar.getInstance());
            leveringRepository.save(levering);

            for (Persoon persoon : antwoord.getPersonen()) {
                LeveringPersoon leveringPersoon = new LeveringPersoon();
                leveringPersoon.setLevering(levering);
                leveringPersoon.setPersoon(persoon.getPersoon());
                leveringPersoonRepository.save(leveringPersoon);
            }

            LeveringCommunicatie levCom = new LeveringCommunicatie();
            levCom.setBericht(context.getUitgaandBerichtId());
            levCom.setLevering(levering.getId());
            leveringCommunicatieRepository.save(levCom);

            antwoord.setLeveringId(levering.getId());
            return DOORGAAN_MET_VERWERKING;
        } catch (Throwable t) {
            LOGGER.error("Fout opgetreden bij de protocollering; bericht wordt niet verderverwerkt en fout "
                + "wordt verstuurd.", t);
            antwoord.voegFoutToe(new BerichtVerwerkingsFout(
                    BerichtVerwerkingsFoutCode.BRVE0008_01_PROTOCOLLERING_MISLUKT, BerichtVerwerkingsFoutZwaarte.FOUT));
            return STOP_VERWERKING;
        }
    }

}
