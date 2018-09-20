/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.Arrays;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.ResultaatCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.business.actie.ActieFactory;
import nl.bzk.brp.business.actie.ActieUitvoerder;
import nl.bzk.brp.model.logisch.BRPActie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de bericht verwerker.
 */
@RunWith(MockitoJUnitRunner.class)
public class BerichtVerwerkerTest {

    private BerichtVerwerker berichtVerwerker;

    @Mock
    private ActieFactory         actieFactory;

    @Mock
    private ActieUitvoerder      actieUitvoerder;

    @Before
    public void init() {
        berichtVerwerker = new BerichtVerwerkerImpl();
        ReflectionTestUtils.setField(berichtVerwerker, "actieFactory", actieFactory);
    }

    @Test
    public void testBerichtZonderActies() {
        BRPBericht bericht = maakNieuwBericht();
        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht);

        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaat());
        Assert.assertNull(resultaat.getMeldingen());
    }

    @Test
    public void testBerichtMetActieZonderMeldingen() {
        BRPActie actie = new BRPActie();
        BRPBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenReturn(actieUitvoerder);
        Mockito.when(actieUitvoerder.voerUit(actie)).thenReturn(null);

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht);
        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaat());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtMetMeerdereActiesEnMeldingen() {
        BRPActie actie1 = new BRPActie();
        BRPActie actie2 = new BRPActie();
        BRPBericht bericht = maakNieuwBericht(actie1, actie2);

        Mockito.when(actieFactory.getActieUitvoerder(actie1)).thenReturn(actieUitvoerder);
        Mockito.when(actieFactory.getActieUitvoerder(actie2)).thenReturn(actieUitvoerder);
        Mockito.when(actieUitvoerder.voerUit(actie1)).thenReturn(Arrays.asList(new Melding(SoortMelding.INFO,
                MeldingCode.ALG0001)));
        Mockito.when(actieUitvoerder.voerUit(actie2)).thenReturn(Arrays.asList(new Melding(SoortMelding.WAARSCHUWING,
                MeldingCode.ALG0001), new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001)));

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht);
        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaat());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(3, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtZonderBekendeActieUitvoerder() {
        BRPActie actie = new BRPActie();
        BRPBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenReturn(null);

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht);
        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaat());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtMetExceptieInUitvoering() {
        BRPActie actie = new BRPActie();
        BRPBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenThrow(new RuntimeException("Test"));

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht);
        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaat());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    /**
     * Instantieert een bericht met de opgegeven acties.
     * @param acties de acties waaruit het bericht dient te bestaan.
     * @return een nieuw bericht met opgegeven acties.
     */
    private BRPBericht maakNieuwBericht(final BRPActie... acties) {
        BRPBericht bericht = new BRPBericht();
        bericht.setBrpActies(Arrays.asList(acties));
        return bericht;
    }
}
