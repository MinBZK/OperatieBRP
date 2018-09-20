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
import nl.bzk.brp.bevraging.domein.repository.LeveringCommunicatieRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringPersoonRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringRepository;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.autaut.Authenticatiemiddel;
import nl.bzk.brp.domein.ber.Bericht;
import nl.bzk.brp.domein.kern.Persoon;
import nl.bzk.brp.domein.lev.Abonnement;
import nl.bzk.brp.domein.lev.Levering;
import nl.bzk.brp.domein.lev.LeveringCommunicatie;
import nl.bzk.brp.domein.lev.LeveringPersoon;
import nl.bzk.brp.domein.lev.SoortLevering;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Inner class voor de uitvoering van de protocollering. Dit gebeurd in deze class en niet in de
 * {@link ProtocolleringStap} zelf, daar er anders transactie problemen optreden waardoor mogelijk optredende
 * excepties niet in de {@link ProtocolleringStap} worden gevangen, maar pas in de aanroeper van deze stap,
 * waarbij dat in dit geval dus niet zou moeten zijn.
 *
 * @brp.bedrijfsregel BRPE0009
 */
public class ProtocolleringStapExecutor implements ProtocolleringStapExecutorInterface {

    @Inject
    private LeveringRepository             leveringRepository;

    @Inject
    private LeveringPersoonRepository      leveringPersoonRepository;

    @Inject
    private LeveringCommunicatieRepository leveringCommunicatieRepository;

    @Inject
    private DomeinObjectFactory            domeinObjectFactory;

    /**
     * Default constructor. Benodigd voor Spring.
     */
    public ProtocolleringStapExecutor() {
    }

    /**
     * {@inheritDoc}
     *
     * @see ProtocolleringStapExecutorInterface#protocolleer(Collection, BerichtContext, Calendar)
     * @brp.bedrijfsregel BRPE0009
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T extends BerichtAntwoord> Long protocolleer(final Collection<Long> persoonIds,
            final BerichtContext context, final Calendar beschouwingsMoment)
    {
        Levering levering = domeinObjectFactory.createLevering();
        levering.setSoort(SoortLevering.BEVRAGING);
        levering.setAbonnement(creeerAbonnementReferentie(context.getAbonnementId()));
        levering.setAuthenticatiemiddel(creeerAuthenticatiemiddelReferentie(context.getAuthenticatieMiddelId()));
        levering.setGebaseerdOp(creeerBerichtReferentie(context.getIngaandBerichtId()));

        levering.setDatumTijdBeschouwing(beschouwingsMoment);
        levering.setDatumTijdKlaarzettenLevering(Calendar.getInstance());
        leveringRepository.save(levering);

        for (Long persoonId : persoonIds) {
            LeveringPersoon leveringPersoon = domeinObjectFactory.createLeveringPersoon();
            leveringPersoon.setLevering(levering);
            leveringPersoon.setPersoon(creeerPersoonReferentie(persoonId));
            leveringPersoonRepository.save(leveringPersoon);
        }

        LeveringCommunicatie levCom = domeinObjectFactory.createLeveringCommunicatie();
        levCom.setUitgaandBericht(creeerBerichtReferentie(context.getUitgaandBerichtId()));
        levCom.setLevering(levering);
        leveringCommunicatieRepository.save(levCom);

        return levering.getID();
    }

    /**
     * Creeer een stub die gebruikt kan worden om een n-1 relatie te persisteren naar Abonnement.
     *
     * @param id de id van het abonnement
     * @return een stub die gebruikt kan worden om een n-1 relatie te persisteren naar Abonnement
     */
    private Abonnement creeerAbonnementReferentie(final Integer id) {
        Abonnement abonnement = domeinObjectFactory.createAbonnement();
        abonnement.setID(id);
        return abonnement;
    }

    /**
     * Creeer een stub die gebruikt kan worden om een n-1 relatie te persisteren naar Authenticatiemiddel.
     *
     * @param id de id van het Authenticatiemiddel
     * @return een stub die gebruikt kan worden om een n-1 relatie te persisteren naar Authenticatiemiddel
     */
    private Authenticatiemiddel creeerAuthenticatiemiddelReferentie(final Integer id) {
        Authenticatiemiddel authenticatiemiddel = domeinObjectFactory.createAuthenticatiemiddel();
        authenticatiemiddel.setID(id);
        return authenticatiemiddel;
    }

    /**
     * Creeer een stub die gebruikt kan worden om een n-1 relatie te persisteren naar Bericht.
     *
     * @param id de id van het Bericht
     * @return een stub die gebruikt kan worden om een n-1 relatie te persisteren naar Bericht
     */
    private Bericht creeerBerichtReferentie(final Long id) {
        Bericht bericht = domeinObjectFactory.createBericht();
        bericht.setID(id);
        return bericht;
    }

    /**
     * Creeer een stub die gebruikt kan worden om een n-1 relatie te persisteren naar Persoon.
     *
     * @param id de id van het Persoon
     * @return een stub die gebruikt kan worden om een n-1 relatie te persisteren naar Persoon
     */
    private Persoon creeerPersoonReferentie(final Long id) {
        Persoon persoon = domeinObjectFactory.createPersoon();
        persoon.setID(id);
        return persoon;
    }

}
