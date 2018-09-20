/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;

import nl.bzk.brp.business.actie.AbstractActieUitvoerder;
import nl.bzk.brp.business.actie.ActieFactory;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class BerichtUitvoerStapTest extends AbstractStapTest {

    private static final String VALIDE_BSN = "123456782";

    private BerichtUitvoerStap berichtUitvoerStap;

    @Mock
    private ActieFactory actieFactory;

    @Mock
    private AbstractActieUitvoerder actieUitvoerder;

    @Before
    public void init() {
        berichtUitvoerStap = new BerichtUitvoerStap();
        ReflectionTestUtils.setField(berichtUitvoerStap, "actieFactory", actieFactory);
    }

    @Test
    public void testBerichtZonderActies() {
        AbstractBijhoudingsBericht bericht = maakNieuwBericht();
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetNullAlsActies() {
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(new Actie[] {});
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetActieZonderMeldingen() {
        ActieBericht actie = new ActieBericht();
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(VALIDE_BSN));
        PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(nieuwAdres);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie.setSoort(SoortActie.VERHUIZING);

        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenReturn(actieUitvoerder);
        Mockito.when(actieUitvoerder.voerUit(actie, bouwBerichtContext())).thenReturn(null);

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetMeerdereActiesEnMeldingen() {
        PersoonBericht persoon = new PersoonBericht();
        PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(nieuwAdres);
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(VALIDE_BSN));
        ActieBericht actie1 = new ActieBericht();
        actie1.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie1.setSoort(SoortActie.VERHUIZING);
        ActieBericht actie2 = new ActieBericht();
        actie2.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie2.setSoort(SoortActie.VERHUIZING);
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie1, actie2);

        BerichtContext berichtContext = bouwBerichtContext();
        Mockito.when(actieFactory.getActieUitvoerder(actie1)).thenReturn(actieUitvoerder);
        Mockito.when(actieFactory.getActieUitvoerder(actie2)).thenReturn(actieUitvoerder);
        Mockito.when(actieUitvoerder.voerUit(actie1, berichtContext)).thenReturn(
            Arrays.asList(new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001)));
        Mockito.when(actieUitvoerder.voerUit(actie2, berichtContext)).thenReturn(
            Arrays.asList(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001), new Melding(
                SoortMelding.FOUT, MeldingCode.ALG0001)));

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, berichtContext, resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(3, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtZonderBekendeActieUitvoerder() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(VALIDE_BSN));
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.VERHUIZING);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenReturn(null);

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
    }

    @Test(expected = IllegalStateException.class)
    public void testBerichtMetExceptieInUitvoering() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(VALIDE_BSN));
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.VERHUIZING);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenThrow(new IllegalStateException("Test"));

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);
    }

    /**
     * Instantieert een bericht met de opgegeven acties.
     *
     * @param acties de acties waaruit het bericht dient te bestaan.
     * @return een nieuw bericht met opgegeven acties.
     */
    private AbstractBijhoudingsBericht maakNieuwBericht(final Actie... acties) {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {
            @Override
            public SoortBericht getSoortBericht() {
                return null;
            }
        };
        if (acties == null) {
            bericht.setBrpActies(null);
        } else {
            bericht.setBrpActies(Arrays.asList(acties));
        }
        return bericht;
    }

}
