/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.business.bedrijfsregels.BerichtContextBedrijfsRegel;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.business.dto.bijhouding.CorrectieAdresBericht;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;


public class BerichtContextValidatieStapTest {

    @Mock
    private BedrijfsRegelManager        bedrijfsRegelManager;

    private BerichtContextValidatieStap berichtContextValidatieStap;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        berichtContextValidatieStap = new BerichtContextValidatieStap();
        ReflectionTestUtils.setField(berichtContextValidatieStap, "bedrijfsRegelManager", bedrijfsRegelManager);
    }

    @Test
    public void testGeldigeContext() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        Mockito.when(bedrijfsRegelManager.getUitTeVoerenBerichtContextBedrijfsRegels(Matchers.any(Class.class))).thenAnswer(
                new Answer<List<BerichtContextBedrijfsRegel>>() {

                    @Override
                    public List<BerichtContextBedrijfsRegel> answer(final InvocationOnMock invocation) throws Throwable
                    {
                        return bouwLijstBerichtContextBedrijfsRegels();
                    }
                });

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("12345"));


        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 2L), 1, new Partij(), "abc");
        context.voegHoofdPersoonToe(persoon);

        Assert.assertTrue(berichtContextValidatieStap.voerVerwerkingsStapUitVoorBericht(new CorrectieAdresBericht(),
                context, resultaat));
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testOnGeldigeContext() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        Mockito.when(bedrijfsRegelManager.getUitTeVoerenBerichtContextBedrijfsRegels(Matchers.any(Class.class))).thenAnswer(
                new Answer<List<BerichtContextBedrijfsRegel>>() {

                    @Override
                    public List<BerichtContextBedrijfsRegel> answer(final InvocationOnMock invocation) throws Throwable
                    {
                        return bouwLijstBerichtContextBedrijfsRegels();
                    }
                });

        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 2L), 1, new Partij(), "abc");

        Assert.assertTrue(berichtContextValidatieStap.voerVerwerkingsStapUitVoorBericht(new CorrectieAdresBericht(),
                context, resultaat));
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testGeenRegels() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        Mockito.when(bedrijfsRegelManager.getUitTeVoerenBerichtContextBedrijfsRegels(Matchers.any(Class.class))).thenReturn(null);

        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 2L), 1, new Partij(), "abc");

        Assert.assertTrue(berichtContextValidatieStap.voerVerwerkingsStapUitVoorBericht(new CorrectieAdresBericht(),
                context, resultaat));
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testGeenBedrijfsRegelManager() {
        ReflectionTestUtils.setField(berichtContextValidatieStap, "bedrijfsRegelManager", null);

        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 2L), 1, new Partij(), "abc");

        Assert.assertTrue(berichtContextValidatieStap.voerVerwerkingsStapUitVoorBericht(new CorrectieAdresBericht(),
                context, resultaat));
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }


    /**
     * Retourneert een lijst met een enkele {@link BerichtContextBedrijfsRegel}, waarbij deze regel alleen een melding
     * retourneert indien het executeert tegen een correctie adres bericht.
     *
     * @return lijst met enkele bedrijfsregel.
     */
    private List<BerichtContextBedrijfsRegel> bouwLijstBerichtContextBedrijfsRegels() {
        List<BerichtContextBedrijfsRegel> regels = new ArrayList<BerichtContextBedrijfsRegel>();

        regels.add(new BerichtContextBedrijfsRegel() {

            @Override
            public String getCode() {
                return "TEST";
            }

            @Override
            public List<Melding> executeer(final BerichtContext context) {
                List<Melding> meldingen;

                if (context.getHoofdPersonen().isEmpty()) {
                    meldingen = Arrays.asList(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001));
                } else {
                    meldingen = Collections.EMPTY_LIST;
                }

                return meldingen;
            }
        });
        return regels;
    }
}
