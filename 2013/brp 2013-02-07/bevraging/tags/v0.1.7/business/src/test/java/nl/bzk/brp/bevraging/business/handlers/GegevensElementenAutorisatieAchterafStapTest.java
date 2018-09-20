/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.bevraging.domein.GegevensElement;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import nl.bzk.brp.bevraging.domein.kern.Persoon;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.bevraging.domein.lev.AbonnementGegevensElement;
import nl.bzk.brp.bevraging.domein.lev.SoortAbonnement;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link GegevensElementenAutorisatieAchterafStap} class.
 */
public class GegevensElementenAutorisatieAchterafStapTest {

    /**
     * Unit test voor de
     * {@link GegevensElementenAutorisatieAchterafStap#voerVerwerkingsStapUitVoorBericht(nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand)}
     * methode.
     *
     * @brp.bedrijfsregel BRAU0045
     */
    @Test
    public void testVoerVerwerkingsStapUitVoorBerichtZonderVoornaamAbonnement() {
        BerichtContext context = new BerichtContext();
        context.setAbonnement(creeerAbonnement());
        BerichtAntwoord antwoord = creeerAntwoord();

        GegevensElementenAutorisatieAchterafStap stap = new GegevensElementenAutorisatieAchterafStap();
        stap.voerVerwerkingsStapUitVoorBericht(null, context, antwoord);

        Assert.assertEquals(1, antwoord.getPersonen().size());
        Persoon persoon = antwoord.getPersonen().iterator().next();
        // FIXME friso
//        Assert.assertNull(persoon.getVoornamen());
    }

    /**
     * Unit test voor de
     * {@link GegevensElementenAutorisatieAchterafStap#voerVerwerkingsStapUitVoorBericht(nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand)}
     * methode.
     *
     * @brp.bedrijfsregel BRAU0045
     */
    @Test
    public void testVoerVerwerkingsStapUitVoorBerichtMetVoornaamAbonnement() {
        BerichtContext context = new BerichtContext();
        context.setAbonnement(creeerAbonnement(GegevensElement.VOORNAMEN));
        BerichtAntwoord antwoord = creeerAntwoord();

        GegevensElementenAutorisatieAchterafStap stap = new GegevensElementenAutorisatieAchterafStap();
        stap.voerVerwerkingsStapUitVoorBericht(null, context, antwoord);

        Assert.assertEquals(1, antwoord.getPersonen().size());
        Persoon persoon = antwoord.getPersonen().iterator().next();
        Assert.assertEquals("Ab", persoon.getVoornamen());
    }

    private Abonnement creeerAbonnement(final GegevensElement... elementen) {
        Abonnement abonnement = new Abonnement(null, SoortAbonnement.LEVERING);
        Set<AbonnementGegevensElement> abonnementGegevensElementen = new HashSet<AbonnementGegevensElement>();
        for (GegevensElement gegevensElement : elementen) {
            abonnementGegevensElementen.add(new AbonnementGegevensElement(abonnement, gegevensElement));
        }
        ReflectionTestUtils.setField(abonnement, "abonnementGegevensElementen", abonnementGegevensElementen);
        return abonnement;
    }

    private PersoonZoekCriteriaAntwoord creeerAntwoord() {
        return new PersoonZoekCriteriaAntwoord(Arrays.asList(creeerPersoon()));
    }

    private Persoon creeerPersoon() {
        nl.bzk.brp.bevraging.domein.Persoon persoon = new nl.bzk.brp.bevraging.domein.Persoon(SoortPersoon.INGESCHREVENE);
        ReflectionTestUtils.setField(persoon, "voornamen", "Ab");
        return persoon;
    }

}
