/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.Arrays;

import junit.framework.Assert;
import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.business.dto.verzoek.PersoonZoekCriteria;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.autaut.Doelbinding;
import nl.bzk.brp.domein.kern.Persoon;
import nl.bzk.brp.domein.kern.SoortPersoon;
import nl.bzk.brp.domein.lev.Abonnement;
import nl.bzk.brp.domein.lev.SoortAbonnement;

import org.junit.Test;


/**
 * Unit test voor de {@link VerstrekkingsBeperkingAchterafStap} class.
 *
 * @brp.bedrijfsregel BRAU0018
 */
public class VerstrekkingsBeperkingAchterafStapTest {

    private DomeinObjectFactory factory = new PersistentDomeinObjectFactory();

    /**
     * Unit test voor de
     * {@link VerstrekkingsBeperkingAchterafStap#voerVerwerkingsStapUitVoorBericht(BerichtVerzoek, BerichtContext, nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord) }
     * methode.
     */
    @Test
    public void testVoerVerwerkingsStapUitVoorAfnemerZonderVerstrekkingsbeperking() {
        Persoon[] personen = { creeerPersoon(false), creeerPersoon(null), creeerPersoon(true) };

        BerichtVerzoek<PersoonZoekCriteriaAntwoord> verzoek = new PersoonZoekCriteria();
        BerichtContext context = creeerContext(false);
        PersoonZoekCriteriaAntwoord antwoord = creeerAntwoord(personen);

        VerstrekkingsBeperkingAchterafStap stap = new VerstrekkingsBeperkingAchterafStap();
        stap.voerVerwerkingsStapUitVoorBericht(verzoek, context, antwoord);

        Assert.assertEquals(3, antwoord.getPersonen().size());
    }

    /**
     * Unit test voor de
     * {@link VerstrekkingsBeperkingAchterafStap#voerVerwerkingsStapUitVoorBericht(BerichtVerzoek, BerichtContext, nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord)
     * )} methode.
     */
    @Test
    public void testVoerVerwerkingsStapUitVoorAfnemerMetVerstrekkingsbeperking() {
        Persoon[] personen = { creeerPersoon(false), creeerPersoon(null), creeerPersoon(true) };

        BerichtVerzoek<PersoonZoekCriteriaAntwoord> verzoek = new PersoonZoekCriteria();
        BerichtContext context = creeerContext(true);
        PersoonZoekCriteriaAntwoord antwoord = creeerAntwoord(personen);

        VerstrekkingsBeperkingAchterafStap stap = new VerstrekkingsBeperkingAchterafStap();
        stap.voerVerwerkingsStapUitVoorBericht(verzoek, context, antwoord);

        Assert.assertEquals(2, antwoord.getPersonen().size());
    }

    private BerichtContext creeerContext(final Boolean verstrekkingsBeperkingHonoreren) {
        Doelbinding doelBinding = factory.createDoelbinding();
        doelBinding.setIndicatieVerstrekkingsbeperkingHonoreren(verstrekkingsBeperkingHonoreren);
        Abonnement abonnement = factory.createAbonnement();
        abonnement.setDoelbinding(doelBinding);
        abonnement.setSoortAbonnement(SoortAbonnement.LEVERING);
        BerichtContext context = new BerichtContext();
        context.setAbonnement(abonnement);
        return context;
    }

    private PersoonZoekCriteriaAntwoord creeerAntwoord(final Persoon... personen) {
        return new PersoonZoekCriteriaAntwoord(Arrays.asList(personen));
    }

    private Persoon creeerPersoon(final Boolean verstrekkingsBeperking) {
        Persoon persoon = factory.createPersoon();
        persoon.setSoort(SoortPersoon.INGESCHREVENE);
        persoon.setVerstrekkingsBeperking(verstrekkingsBeperking);
        return persoon;
    }
}
