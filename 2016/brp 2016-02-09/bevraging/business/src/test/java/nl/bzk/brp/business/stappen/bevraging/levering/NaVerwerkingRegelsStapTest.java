/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.regels.BerichtBedrijfsregel;
import nl.bzk.brp.business.regels.BevragingBedrijfsregelManager;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.business.regels.context.BerichtRegelContext;
import nl.bzk.brp.business.regels.context.RegelContext;
import nl.bzk.brp.business.stappen.Stap;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.SoortFout;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.*;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledigImplBuilder;
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

import java.util.*;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class NaVerwerkingRegelsStapTest {

    @Mock
    private BevragingBedrijfsregelManager bedrijfsregelManager;

    @Mock
    private BevragingBerichtContextBasis berichtContext;

    @Mock
    private BerichtBedrijfsregel naVerwerkingRegel;

    @Spy
    private final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>(0));

    @InjectMocks
    private final NaVerwerkingRegelsStap naVerwerkingRegelsStap = new NaVerwerkingRegelsStap();

    @Before
    public final void init() {
        final List<RegelInterface> naVerwerkingRegels = new ArrayList<>();
        naVerwerkingRegels.add(naVerwerkingRegel);
        Mockito.when(bedrijfsregelManager.getUitTeVoerenRegelsNaVerwerking(Mockito.any(SoortBericht.class))).thenReturn(
            naVerwerkingRegels);

        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.DUMMY);
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(tla, la.geefDiensten().iterator().next());
        Mockito.when(berichtContext.getLeveringinformatie()).thenReturn(leveringinformatie);

        final PersoonHisVolledigImpl persoonHisVolledig = maakPersoonHisVolledig(123);
        Mockito.when(berichtContext.getPersoonHisVolledigImpl()).thenReturn(persoonHisVolledig);
        Mockito.when(naVerwerkingRegel.getContextType()).thenReturn(BerichtRegelContext.class);
        Mockito.when(naVerwerkingRegel.getRegel()).thenReturn(Regel.BRLV0001);
    }


    @Test
    public final void testVoerStapUitMetMelding() {
        final BerichtIdentificeerbaar falendeEntiteit1 = Mockito.mock(BerichtIdentificeerbaar.class);
        final List<BerichtIdentificeerbaar> regelUitvoer1 = Arrays.asList(falendeEntiteit1);

        Mockito.when(
            naVerwerkingRegel.valideer(Mockito.any(RegelContext.class))).thenReturn(regelUitvoer1);

        // Zorg dat de juiste 'RegelParameters' worden geretourneerd na bevraging voor falende regel.
        final RegelParameters regelParameters1 =
            new RegelParameters(new MeldingtekstAttribuut("Test1"), SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001,
                DatabaseObjectKern.PERSOON);
        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(naVerwerkingRegel.getRegel())).thenReturn(
            regelParameters1);

        final boolean verwerkingsResultaat =
            naVerwerkingRegelsStap.voerStapUit(new GeefDetailsPersoonBericht(), getBerichtContext(), getResultaat());

        Assert.assertEquals(Stap.DOORGAAN, verwerkingsResultaat);
        Assert.assertEquals(1, getResultaat().getMeldingen().size());
    }

    @Test
    public final void testUitvoerNaverwerkingRegelsMetOptredendeFout() {
        final GeefDetailsPersoonBericht geefDetailsPersoonBericht = new GeefDetailsPersoonBericht();
        final List<RegelInterface> regelsNaverwerking = new ArrayList<>();
        final BerichtBedrijfsregel mock = Mockito.mock(BerichtBedrijfsregel.class);
        regelsNaverwerking.add(mock);

        final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden = new ArrayList<>();
        Mockito.when(mock.valideer(Mockito.any(BerichtRegelContext.class)))
            .thenReturn(objectenDieDeRegelOvertreden);
        objectenDieDeRegelOvertreden.add(new BerichtIdentificeerbaar() {
            @Override
            public final void setCommunicatieID(final String communicatieId) {
            }

            @Override
            public final String getCommunicatieID() {
                return null;
            }
        });
        Mockito.when(mock.getRegel()).thenReturn(Regel.ACT0001);
        Mockito.when(mock.getContextType()).thenReturn(BerichtRegelContext.class);

        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.ACT0001)).thenReturn(
            new RegelParameters(null, SoortMelding.FOUT, Regel.ACT0001, null, SoortFout.VERWERKING_KAN_DOORGAAN));
        Mockito.when(bedrijfsregelManager.getUitTeVoerenRegelsNaVerwerking(Mockito.any(SoortBericht.class))).thenReturn(
            regelsNaverwerking);


        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.zonderGeldigeDienst();
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(tla, null);
        Mockito.when(berichtContext.getLeveringinformatie()).thenReturn(leveringinformatie);

        naVerwerkingRegelsStap.voerStapUit(geefDetailsPersoonBericht, getBerichtContext(), getResultaat());
        Assert.assertFalse(getResultaat().getMeldingen().isEmpty());
        Assert.assertEquals(Regel.ACT0001, getResultaat().getMeldingen().get(0).getRegel());
    }

    @Test
    public final void testUitvoerNaverwerkingRegelsZonderOptredendeFout() {
        final GeefDetailsPersoonBericht geefDetailsPersoonBericht = new GeefDetailsPersoonBericht();
        final List<RegelInterface> regelsNaverwerking = new ArrayList<>();
        final BerichtBedrijfsregel mock = Mockito.mock(BerichtBedrijfsregel.class);
        regelsNaverwerking.add(mock);

        Mockito.when(mock.valideer(Mockito.any(BerichtRegelContext.class)))
            .thenReturn(Collections.<BerichtIdentificeerbaar>emptyList());

        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.R1261)).thenReturn(
            new RegelParameters(null, SoortMelding.FOUT, Regel.R1261, null, SoortFout.VERWERKING_VERHINDERD));
        Mockito.when(bedrijfsregelManager.getUitTeVoerenRegelsNaVerwerking(Mockito.any(SoortBericht.class))).thenReturn(
            regelsNaverwerking);

        Mockito.when(mock.getRegel()).thenReturn(Regel.R1261);
        Mockito.when(mock.getContextType()).thenReturn(BerichtRegelContext.class);

        naVerwerkingRegelsStap.voerStapUit(geefDetailsPersoonBericht, getBerichtContext(), getResultaat());
        Assert.assertTrue(getResultaat().getMeldingen().isEmpty());
    }

    @Test
    public final void testUitvoerNaverwerkingRegelsZonderGeconfigureerdeRegels() {
        final GeefDetailsPersoonBericht geefDetailsPersoonBericht = new GeefDetailsPersoonBericht();
        final List<RegelInterface> regelsNaverwerking = new ArrayList<>();

        Mockito.when(bedrijfsregelManager.getUitTeVoerenRegelsNaVerwerking(Mockito.any(SoortBericht.class))).thenReturn(
            regelsNaverwerking);

        naVerwerkingRegelsStap.voerStapUit(geefDetailsPersoonBericht, getBerichtContext(), getResultaat());
        Assert.assertTrue(getResultaat().getMeldingen().isEmpty());
    }

    @Test
    public final void testVoegMeldingenToeVoorVerstrekkingsBeperkingenPersoonMetVolledigeVerstrBeperking() {
        final GeefDetailsPersoonBericht geefDetailsPersoonBericht = new GeefDetailsPersoonBericht();
        final List<RegelInterface> regelsNaverwerking = new ArrayList<>();

        Mockito.when(bedrijfsregelManager.getUitTeVoerenRegelsNaVerwerking(Mockito.any(SoortBericht.class))).thenReturn(
            regelsNaverwerking);

        final PersoonHisVolledigImpl persoonHisVolledigImpl = getBerichtContext().getPersoonHisVolledigImpl();
        persoonHisVolledigImpl.setIndicatieVolledigeVerstrekkingsbeperking(
            new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder(persoonHisVolledigImpl)
                .nieuwStandaardRecord(20120101).eindeRecord().build()
        );

        naVerwerkingRegelsStap.voerStapUit(geefDetailsPersoonBericht, getBerichtContext(), getResultaat());

        Assert.assertFalse(getResultaat().getMeldingen().isEmpty());
        Assert.assertEquals(Regel.BRLV0032, getResultaat().getMeldingen().get(0).getRegel());
    }

    @Test
    public final void testVoegMeldingenToeVoorVerstrekkingsBeperkingenPersoonMetPartieleVerstrBeperking() {
        final GeefDetailsPersoonBericht geefDetailsPersoonBericht = new GeefDetailsPersoonBericht();
        final List<RegelInterface> regelsNaverwerking = new ArrayList<>();

        Mockito.when(bedrijfsregelManager.getUitTeVoerenRegelsNaVerwerking(Mockito.any(SoortBericht.class))).thenReturn(
            regelsNaverwerking);

        final PersoonHisVolledigImpl persoonHisVolledigImpl = getBerichtContext().getPersoonHisVolledigImpl();
        final PersoonVerstrekkingsbeperkingHisVolledigImpl verstrBEp =
            new PersoonVerstrekkingsbeperkingHisVolledigImplBuilder(persoonHisVolledigImpl, null, null, null).build();
        verstrBEp.getPersoonVerstrekkingsbeperkingHistorie().voegToe(
            new HisPersoonVerstrekkingsbeperkingModel(verstrBEp,
                new ActieModel(null, null, null, null, null,
                    new DatumTijdAttribuut(new Date()), null))
        );
        persoonHisVolledigImpl.getVerstrekkingsbeperkingen().add(verstrBEp);

        naVerwerkingRegelsStap.voerStapUit(geefDetailsPersoonBericht, getBerichtContext(), getResultaat());

        Assert.assertFalse(getResultaat().getMeldingen().isEmpty());
        Assert.assertEquals(Regel.BRLV0032, getResultaat().getMeldingen().get(0).getRegel());
    }

    @Test
    public final void testVoegMeldingenToeVoorVerstrekkingsBeperkingenPersoonMetPartieleEnVolledigeVERVALLENVerstrBeperking() {
        final GeefDetailsPersoonBericht geefDetailsPersoonBericht = new GeefDetailsPersoonBericht();
        final List<RegelInterface> regelsNaverwerking = new ArrayList<>();

        Mockito.when(bedrijfsregelManager.getUitTeVoerenRegelsNaVerwerking(Mockito.any(SoortBericht.class)))
            .thenReturn(regelsNaverwerking);

        final PersoonHisVolledigImpl persoonHisVolledigImpl = getBerichtContext().getPersoonHisVolledigImpl();

        //Partiele:
        final PersoonVerstrekkingsbeperkingHisVolledigImpl verstrBEp =
            new PersoonVerstrekkingsbeperkingHisVolledigImplBuilder(persoonHisVolledigImpl, null, null, null).build();
        verstrBEp.getPersoonVerstrekkingsbeperkingHistorie().voegToe(
            new HisPersoonVerstrekkingsbeperkingModel(verstrBEp,
                new ActieModel(null, null, null, null, null,
                    new DatumTijdAttribuut(new Date()), null))
        );
        verstrBEp.getPersoonVerstrekkingsbeperkingHistorie()
            .verval(new ActieModel(null, null, null, null, null, new DatumTijdAttribuut(new Date()), null),
                new DatumTijdAttribuut(new Date()));
        persoonHisVolledigImpl.getVerstrekkingsbeperkingen().add(verstrBEp);

        //Volledige:
        persoonHisVolledigImpl.setIndicatieVolledigeVerstrekkingsbeperking(
            new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder(persoonHisVolledigImpl)
                .nieuwStandaardRecord(20120101).eindeRecord().build()
        );
        persoonHisVolledigImpl.getIndicatieVolledigeVerstrekkingsbeperking().getPersoonIndicatieHistorie()
            .verval(new ActieModel(null, null, null, null, null, new DatumTijdAttribuut(new Date()),
                    null),
                new DatumTijdAttribuut(new Date()));

        naVerwerkingRegelsStap.voerStapUit(geefDetailsPersoonBericht, getBerichtContext(), getResultaat());

        Assert.assertTrue(getResultaat().getMeldingen().isEmpty());
    }

    @Test
    public final void testVoegMeldingenToeVerstrBeperkingenPersoonMetVollVerstrBeperkingMaarResultaatBevatAlStoppendeFout() {
        final GeefDetailsPersoonBericht geefDetailsPersoonBericht = new GeefDetailsPersoonBericht();
        final List<RegelInterface> regelsNaverwerking = new ArrayList<>();

        Mockito.when(bedrijfsregelManager.getUitTeVoerenRegelsNaVerwerking(Mockito.any(SoortBericht.class)))
            .thenReturn(regelsNaverwerking);

        final PersoonHisVolledigImpl persoonHisVolledigImpl = getBerichtContext().getPersoonHisVolledigImpl();
        persoonHisVolledigImpl.setIndicatieVolledigeVerstrekkingsbeperking(
            new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder(persoonHisVolledigImpl)
                .nieuwStandaardRecord(20120101).eindeRecord().build()
        );

        getResultaat().voegMeldingToe(new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ACT0001));

        naVerwerkingRegelsStap.voerStapUit(geefDetailsPersoonBericht, getBerichtContext(), getResultaat());

        Assert.assertEquals(1, getResultaat().getMeldingen().size());
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
