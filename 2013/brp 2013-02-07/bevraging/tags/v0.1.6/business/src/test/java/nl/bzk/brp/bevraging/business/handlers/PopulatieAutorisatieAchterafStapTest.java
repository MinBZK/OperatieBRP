/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import nl.bzk.brp.bevraging.business.berichtcmds.OpvragenPersoonBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BrpBerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.PersoonIndicatie;
import nl.bzk.brp.bevraging.domein.SoortIndicatie;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import nl.bzk.brp.bevraging.domein.aut.DoelBinding;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.bevraging.domein.lev.SoortAbonnement;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link PopulatieAutorisatieAchterafStap} class.
 */
public class PopulatieAutorisatieAchterafStapTest {

    /**
     * Unit test voor de {@link PopulatieAutorisatieAchterafStap#voerVerwerkingsStapUitVoorBericht(nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand)} methode.
     * @brp.bedrijfsregel BRAU0018
     */
    @Test
    public void testVoerVerwerkingsStapUitVoorAfnemerZonderVerstrekkingsbeperking() {
        OpvragenPersoonBerichtCommand bericht = new OpvragenPersoonBerichtCommand(null, creeerContext(false));
        Persoon[] personen = {creeerPersoon(false), creeerPersoon(null), creeerPersoon(true)};
        ReflectionTestUtils.setField(bericht, "antwoord", creeerAntwoord(personen));

        PopulatieAutorisatieAchterafStap stap = new PopulatieAutorisatieAchterafStap();
        stap.voerVerwerkingsStapUitVoorBericht(bericht);

        Assert.assertEquals(3, bericht.getAntwoord().getPersonen().size());
    }

    /**
     * Unit test voor de {@link PopulatieAutorisatieAchterafStap#voerVerwerkingsStapUitVoorBericht(nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand)} methode.
     * @brp.bedrijfsregel BRAU0018
     */
    @Test
    public void testVoerVerwerkingsStapUitVoorAfnemerMetVerstrekkingsbeperking() {
        OpvragenPersoonBerichtCommand bericht = new OpvragenPersoonBerichtCommand(null, creeerContext(true));
        Persoon[] personen = {creeerPersoon(false), creeerPersoon(null), creeerPersoon(true)};
        ReflectionTestUtils.setField(bericht, "antwoord", creeerAntwoord(personen));

        PopulatieAutorisatieAchterafStap stap = new PopulatieAutorisatieAchterafStap();
        stap.voerVerwerkingsStapUitVoorBericht(bericht);

        Assert.assertEquals(2, bericht.getAntwoord().getPersonen().size());
    }

    private BrpBerichtContext creeerContext(final Boolean verstrekkingsBeperkingHonoreren) {
        DoelBinding doelBinding = new DoelBinding(null, null);
        ReflectionTestUtils.setField(doelBinding, "verstrekkingsBeperkingHonoreren", verstrekkingsBeperkingHonoreren);
        Abonnement abonnement = new Abonnement(doelBinding, SoortAbonnement.LEVERING);
        BrpBerichtContext context = new BrpBerichtContext();
        context.setAbonnement(abonnement);
        return context;
    }

    private PersoonZoekCriteriaAntwoord creeerAntwoord(final Persoon... personen) {
        return new PersoonZoekCriteriaAntwoord(Arrays.asList(personen));
    }

    private Persoon creeerPersoon(final Boolean verstrekkingsBeperking) {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        if (verstrekkingsBeperking != null) {
            Map<SoortIndicatie, PersoonIndicatie> indicaties = new HashMap<SoortIndicatie, PersoonIndicatie>();
            PersoonIndicatie indicatie = new PersoonIndicatie(persoon, SoortIndicatie.VERSTREKKINGSBEPERKING);
            indicatie.setWaarde(verstrekkingsBeperking);
            indicaties.put(SoortIndicatie.VERSTREKKINGSBEPERKING, indicatie);
            ReflectionTestUtils.setField(persoon, "indicaties", indicaties);
        }
        return persoon;
    }

}
