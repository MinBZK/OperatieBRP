/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.regels.BerichtBedrijfsregel;
import nl.bzk.brp.business.regels.BevragingBedrijfsregelManager;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.business.regels.context.BerichtRegelContext;
import nl.bzk.brp.business.regels.impl.levering.autorisatie.R2130;
import nl.bzk.brp.business.stappen.Stap;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.SoortFout;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.levering.ZoekPersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class VoorVerwerkingRegelsStapTest {

    @InjectMocks
    private final VoorVerwerkingRegelsStap voorVerwerkingRegelsStap = new VoorVerwerkingRegelsStap();

    @Mock
    private BevragingBedrijfsregelManager bedrijfsregelManager;

    @Mock
    private BerichtBedrijfsregel voorVerwerkingRegel;

    @Mock
    private BevragingBerichtContextBasis berichtContext;

    @Spy
    private final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>(0));

    @Before
    public final void init() {
        ReflectionTestUtils.setField(voorVerwerkingRegelsStap, "bedrijfsregelManager", bedrijfsregelManager);

        when(voorVerwerkingRegel.getContextType()).thenReturn(BerichtRegelContext.class);
        when(voorVerwerkingRegel.getRegel()).thenReturn(Regel.ALG0001);

        final List<RegelInterface> voorVerwerkingRegels = new ArrayList<>();
        voorVerwerkingRegels.add(voorVerwerkingRegel);
        when(bedrijfsregelManager.getUitTeVoerenRegelsVoorVerwerking(Mockito.any(SoortBericht.class))).thenReturn(
            voorVerwerkingRegels);

        final PersoonHisVolledigImpl persoonHisVolledig = maakPersoonHisVolledig(123);
        when(berichtContext.getPersoonHisVolledigImpl()).thenReturn(persoonHisVolledig);

        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.GEEF_DETAILS_PERSOON);
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final Leveringinformatie li = new Leveringinformatie(tla, la.geefDienst(SoortDienst.GEEF_DETAILS_PERSOON));

        when(berichtContext.getLeveringinformatie()).thenReturn(li);
    }

    @Test
    public final void testVoerStapUitMetGeefDetailsPersoonBericht() {
        final boolean verwerkingsResultaat =
            voorVerwerkingRegelsStap
                .voerStapUit(new GeefDetailsPersoonBericht(), getBerichtContext(), getResultaat());

        Assert.assertEquals(Stap.DOORGAAN, verwerkingsResultaat);
        Assert.assertEquals(0, getResultaat().getMeldingen().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testVoerStapUitMetZoekPersoonBericht() {
        final boolean verwerkingsResultaat =
            voorVerwerkingRegelsStap.voerStapUit(new ZoekPersoonBericht(), getBerichtContext(), getResultaat());
        Assert.assertEquals(Stap.DOORGAAN, verwerkingsResultaat);
        Assert.assertEquals(0, getResultaat().getMeldingen().size());
    }


    @Test
    public final void testVoerStapUitMetmelding() {
        final BerichtIdentificeerbaar falendeEntiteit1 = Mockito.mock(BerichtIdentificeerbaar.class);
        final List<BerichtIdentificeerbaar> regelUitvoer1 = Collections.singletonList(falendeEntiteit1);

        when(
            voorVerwerkingRegel.valideer(Mockito.any(BerichtRegelContext.class))).thenReturn(regelUitvoer1);

        // Zorg dat de juiste 'RegelParameters' worden geretourneerd na bevraging voor falende regel.
        final RegelParameters regelParameters1 =
            new RegelParameters(new MeldingtekstAttribuut("Test1"), SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001,
                DatabaseObjectKern.PERSOON);
        when(bedrijfsregelManager.getRegelParametersVoorRegel(voorVerwerkingRegel.getRegel())).thenReturn(
            regelParameters1);

        final boolean verwerkingsResultaat =
            voorVerwerkingRegelsStap
                .voerStapUit(new GeefDetailsPersoonBericht(), getBerichtContext(), getResultaat());

        Assert.assertEquals(Stap.DOORGAAN, verwerkingsResultaat);
        Assert.assertEquals(1, getResultaat().getMeldingen().size());
    }

    @Test
    public final void testUitvoerVoorverwerkingRegelsZonderOptredendeFout() {
        final GeefDetailsPersoonBericht geefDetailsPersoonBericht = new GeefDetailsPersoonBericht();
        final List<RegelInterface> regelsVoorverwerking = new ArrayList<>();
        regelsVoorverwerking.add(new R2130());

        when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.R2055)).thenReturn(
            new RegelParameters(null, SoortMelding.FOUT, Regel.R2055, null, SoortFout.VERWERKING_VERHINDERD));
        when(bedrijfsregelManager.getUitTeVoerenRegelsVoorVerwerking(Mockito.any(SoortBericht.class))).thenReturn(
            regelsVoorverwerking);

        voorVerwerkingRegelsStap.voerStapUit(geefDetailsPersoonBericht, getBerichtContext(), getResultaat());
        Assert.assertTrue(getResultaat().getMeldingen().isEmpty());
    }

    @Test
    public final void testUitvoerVoorverwerkingRegelsZonderGeconfigureerdeRegels() {
        final GeefDetailsPersoonBericht geefDetailsPersoonBericht = new GeefDetailsPersoonBericht();
        final List<RegelInterface> regelsVoorverwerking = new ArrayList<>();

        when(bedrijfsregelManager.getUitTeVoerenRegelsVoorVerwerking(Mockito.any(SoortBericht.class))).thenReturn(
            regelsVoorverwerking);

        voorVerwerkingRegelsStap.voerStapUit(geefDetailsPersoonBericht, getBerichtContext(), getResultaat());
        Assert.assertTrue(getResultaat().getMeldingen().isEmpty());
    }

    public final BevragingBerichtContextBasis getBerichtContext() {
        return berichtContext;
    }

    public final BevragingResultaat getResultaat() {
        return resultaat;
    }

    private static PersoonHisVolledigImpl maakPersoonHisVolledig(final Integer id) {
        final PersoonHisVolledigImpl persoon =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(19800101)
                .datumGeboorte(19800101)
                .eindeRecord()
                .nieuwIdentificatienummersRecord(19800101, null, 19800101)
                .burgerservicenummer(1234)
                .administratienummer(1235L)
                .eindeRecord()
                .voegPersoonAdresToe(
                    new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(19800101, null, 19800101)
                        .postcode("1234AB").huisnummer(12).huisletter("A")
                        .identificatiecodeNummeraanduiding("abcd").eindeRecord().build()).build();
        ReflectionTestUtils.setField(persoon, "iD", id);
        return persoon;
    }
}
