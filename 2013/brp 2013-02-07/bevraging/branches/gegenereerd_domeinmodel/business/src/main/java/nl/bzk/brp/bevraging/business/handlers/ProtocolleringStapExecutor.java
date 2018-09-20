/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.Calendar;
import java.util.Collection;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.proxies.PersoonProxy;
import nl.bzk.brp.bevraging.domein.ber.LeveringCommunicatie;
import nl.bzk.brp.bevraging.domein.kern.Persoon;
import nl.bzk.brp.bevraging.domein.lev.Levering;
import nl.bzk.brp.bevraging.domein.lev.LeveringPersoon;
import nl.bzk.brp.bevraging.domein.lev.LeveringSoort;
import nl.bzk.brp.bevraging.domein.repository.AbonnementRepository;
import nl.bzk.brp.bevraging.domein.repository.AuthenticatieMiddelRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringCommunicatieRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringPersoonRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Inner class voor de uitvoering van de protocollering. Dit gebeurd in deze class en niet in de
 * {@link ProtocolleringStap} zelf, daar er anders transactie problemen optreden waardoor mogelijk optredende
 * excepties niet in de {@link ProtocolleringStap} worden gevangen, maar pas in de aanroeper van deze stap,
 * waarbij dat in dit geval dus niet zou moeten zijn.
 * @brp.bedrijfsregel BRPE0009
 */
public class ProtocolleringStapExecutor implements ProtocolleringStapExecutorInterface {

    @Inject
    private LeveringRepository             leveringRepository;

    @Inject
    private LeveringPersoonRepository      leveringPersoonRepository;

    @Inject
    private AbonnementRepository           abonnementRepository;

    @Inject
    private AuthenticatieMiddelRepository  authenticatieMiddelRepository;

    @Inject
    private LeveringCommunicatieRepository leveringCommunicatieRepository;

    /**
     * Default constructor. Benodigd voor Spring.
     */
    public ProtocolleringStapExecutor() {
    }

    /**
     * {@inheritDoc}
     * @see nl.bzk.brp.bevraging.business.handlers.ProtocolleringStapExecutorInterface#protocolleer(
     *     java.util.Collection, nl.bzk.brp.bevraging.business.dto.BerichtContext, java.util.Calendar)
     * @brp.bedrijfsregel BRPE0009
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T extends BerichtAntwoord> Long protocolleer(final Collection<Persoon> personen,
            final BerichtContext context, final Calendar beschouwingsMoment)
    {
        Levering levering = new Levering();
        levering.setSoort(LeveringSoort.BRP_BEVRAGING);
        levering.setAbonnement(abonnementRepository.findOne(context.getAbonnementId()));
        levering.setAuthenticatieMiddel(authenticatieMiddelRepository.findOne(context.getAuthenticatieMiddelId()));
        levering.setGebaseerdOp(context.getIngaandBerichtId());

        levering.setBeschouwing(beschouwingsMoment);
        levering.setLevering(Calendar.getInstance());
        leveringRepository.save(levering);

        for (Persoon persoon : personen) {
            LeveringPersoon leveringPersoon = new LeveringPersoon();
            leveringPersoon.setLevering(levering);
            leveringPersoon.setPersoon((nl.bzk.brp.bevraging.domein.Persoon) PersoonProxy.getPersoon(persoon));
            leveringPersoonRepository.save(leveringPersoon);
        }

        LeveringCommunicatie levCom = new LeveringCommunicatie();
        levCom.setBericht(context.getUitgaandBerichtId());
        levCom.setLevering(levering.getId());
        leveringCommunicatieRepository.save(levCom);

        return levering.getId();
    }
}
