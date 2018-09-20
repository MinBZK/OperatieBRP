/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import nl.bzk.brp.bevraging.business.berichtcmds.OpvragenPersoonBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.PersoonIndicatie;
import nl.bzk.brp.bevraging.domein.SoortIndicatie;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link GegevensElementenAutorisatieAchterafStap} class.
 */
public class GegevensElementenAutorisatieAchterafStapTest {

    /**
     * Unit test voor de {@link GegevensElementenAutorisatieAchterafStap#voerVerwerkingsStapUitVoorBericht(BrpBerichtCommand)} methode.
     * @brp.bedrijfsregel BRAU0018
     */
    @Test
    public void testVoerVerwerkingsStapUitVoorBericht() {
        Collection<Persoon> personen = new ArrayList<Persoon>();
        personen.add(creeerPersoonZonderVerstrekkingsbeperking());
        personen.add(creeerPersoonMetVerstrekkingsbeperking());
        PersoonZoekCriteriaAntwoord antwoord = new PersoonZoekCriteriaAntwoord(personen);
        OpvragenPersoonBerichtCommand bericht = new OpvragenPersoonBerichtCommand(null, null);
        ReflectionTestUtils.setField(bericht, "antwoord", antwoord);

        GegevensElementenAutorisatieAchterafStap stap = new GegevensElementenAutorisatieAchterafStap();
        stap.voerVerwerkingsStapUitVoorBericht(bericht);

        Assert.assertEquals(1, bericht.getAntwoord().getPersonen().size());
    }

    private Persoon creeerPersoonZonderVerstrekkingsbeperking() {
        return new Persoon(SoortPersoon.INGESCHREVENE);
    }

    private Persoon creeerPersoonMetVerstrekkingsbeperking() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        Map<SoortIndicatie, PersoonIndicatie> indicaties = new HashMap<SoortIndicatie, PersoonIndicatie>();
        PersoonIndicatie indicatie = new PersoonIndicatie(persoon, SoortIndicatie.VERSTREKKINGSBEPERKING);
        indicatie.setWaarde(Boolean.TRUE);
        indicaties.put(SoortIndicatie.VERSTREKKINGSBEPERKING, indicatie);
        ReflectionTestUtils.setField(persoon, "indicaties", indicaties);
        return persoon;
    }

}
