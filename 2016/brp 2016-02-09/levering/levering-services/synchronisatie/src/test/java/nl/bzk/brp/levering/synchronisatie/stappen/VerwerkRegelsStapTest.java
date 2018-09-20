/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.business.regels.Bedrijfsregel;
import nl.bzk.brp.business.regels.NaVerwerkingRegel;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.business.regels.impl.levering.autorisatie.BRLV0031;
import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.levering.synchronisatie.regels.SynchronisatieBedrijfsregelManager;
import nl.bzk.brp.levering.synchronisatie.regels.stamgegeven.BRLV0024;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.SoortFout;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.StamgegevenAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingSynchronisatiePersoonBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class VerwerkRegelsStapTest extends AbstractStappenTest {

    @InjectMocks
    private VerwerkRegelsStap verwerkRegelsStap = new VerwerkRegelsStap();

    @Mock
    private SynchronisatieBedrijfsregelManager bedrijfsregelManager;

    @Mock
    private Bedrijfsregel autorisatieBedrijfsregel;

    @Before
    public final void init() {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.SYNCHRONISATIE_PERSOON);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final Leveringinformatie leveringinformatie =  new Leveringinformatie(toegangLeveringsautorisatie, null);
        maakBericht(111222333, la, 1234, "UnitTest");
        getBerichtContext().setLeveringinformatie(leveringinformatie);
        getBerichtContext().setLeveringsautorisatie(la);

        ReflectionTestUtils.setField(verwerkRegelsStap, "bedrijfsregelManager", bedrijfsregelManager);

        Mockito.when(autorisatieBedrijfsregel.getContextType()).thenReturn(AutorisatieRegelContext.class);

        final List<RegelInterface> voorVerwerkingRegels = new ArrayList<>();
        voorVerwerkingRegels.add(autorisatieBedrijfsregel);
        Mockito.when(bedrijfsregelManager.getUitTeVoerenRegelsVoorVerwerking(Matchers.any(SoortBericht.class)))
            .thenReturn(voorVerwerkingRegels);

        final RegelInterface naVerwerkingRegel = Mockito.mock(NaVerwerkingRegel.class);
        Mockito.when(bedrijfsregelManager.getUitTeVoerenRegelsNaVerwerking(Matchers.any(SoortBericht.class)))
            .thenReturn(Collections.singletonList(naVerwerkingRegel));
        Mockito.when(autorisatieBedrijfsregel.valideer(Matchers.any(AutorisatieRegelContext.class)))
            .thenReturn(Bedrijfsregel.VALIDE);
    }

    @Test
    public final void testVoerStapUit() {
        final boolean resultaat = verwerkRegelsStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());
        Assert.assertEquals(AbstractStap.DOORGAAN, resultaat);
        Assert.assertEquals(0, getResultaat().getMeldingen().size());
    }

    @Test
    public final void testVoerNabewerkingStapUit() {
        verwerkRegelsStap.voerNabewerkingStapUit(getOnderwerp(), getBerichtContext(), getResultaat());
        Assert.assertEquals(0, getResultaat().getMeldingen().size());
    }

    @Test
    public final void testVoerStapUitMetMelding() {
        final BerichtIdentificeerbaar falendeEntiteit1 = Mockito.mock(BerichtIdentificeerbaar.class);
        Mockito.when(falendeEntiteit1.getCommunicatieID()).thenReturn("entiteit1.id");

        Mockito.when(autorisatieBedrijfsregel.valideer(Matchers.any(AutorisatieRegelContext.class)))
            .thenReturn(Bedrijfsregel.INVALIDE);

        // Zorg dat de juiste 'RegelParameters' worden geretourneerd na bevraging voor falende regel.
        final RegelParameters regelParameters1 = new RegelParameters(new MeldingtekstAttribuut("Test1"),
            SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001,
            DatabaseObjectKern.PERSOON);
        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(autorisatieBedrijfsregel.getRegel()))
            .thenReturn(regelParameters1);

        final boolean resultaat = verwerkRegelsStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertEquals(AbstractStap.DOORGAAN, resultaat);
        Assert.assertEquals(1, getResultaat().getMeldingen().size());
    }

    @Test
    public final void testUitvoerVoorBerichtRegelsMetOptredendeFout() {
        final GeefSynchronisatieStamgegevenBericht bericht = new GeefSynchronisatieStamgegevenBericht();
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setStamgegeven(new StamgegevenAttribuut("bladiebla"));
        final BerichtStandaardGroepBericht standaardGroep = new BerichtStandaardGroepBericht();
        standaardGroep.setAdministratieveHandeling(new HandelingSynchronisatiePersoonBericht());
        bericht.setStandaard(standaardGroep);
        final List<RegelInterface> regelsVoorBericht = new ArrayList<>();
        regelsVoorBericht.add(new BRLV0024());
        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.BRLV0024)).thenReturn(
            new RegelParameters(null, SoortMelding.FOUT, Regel.BRLV0024, null, SoortFout.VERWERKING_VERHINDERD));
        Mockito.when(bedrijfsregelManager.getUitTeVoerenRegelsVoorBericht(Matchers.any(SoortBericht.class)))
            .thenReturn(regelsVoorBericht);

        verwerkRegelsStap.voerStapUit(bericht, getBerichtContext(), getResultaat());
        Assert.assertFalse(getResultaat().getMeldingen().isEmpty());
        Assert.assertEquals(Regel.BRLV0024, getResultaat().getMeldingen().get(0).getRegel());
    }

    @Test
    public final void testUitvoerVoorRegelMetVerstrekkingsBeperkingContext() {
        final GeefSynchronisatieStamgegevenBericht bericht = new GeefSynchronisatieStamgegevenBericht();
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setStamgegeven(new StamgegevenAttribuut("diebasdasfd"));
        final BerichtStandaardGroepBericht standaardGroep = new BerichtStandaardGroepBericht();
        standaardGroep.setAdministratieveHandeling(new HandelingSynchronisatiePersoonBericht());
        bericht.setStandaard(standaardGroep);
        final List<RegelInterface> regelsVoorBericht = new ArrayList<>();
        final BRLV0031 brlv0031 = new BRLV0031();
        regelsVoorBericht.add(brlv0031);
        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.BRLV0031)).thenReturn(
            new RegelParameters(null, SoortMelding.FOUT, Regel.BRLV0031, null, SoortFout.VERWERKING_VERHINDERD));
        Mockito.when(bedrijfsregelManager.getUitTeVoerenRegelsVoorBericht(Matchers.any(SoortBericht.class)))
            .thenReturn(regelsVoorBericht);

        final SynchronisatieResultaat resultaat = getResultaat();
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRLV0031));

        verwerkRegelsStap.voerStapUit(bericht, getBerichtContext(), resultaat);
        Assert.assertFalse(getResultaat().getMeldingen().isEmpty());
        Assert.assertEquals(Regel.BRLV0031, getResultaat().getMeldingen().get(0).getRegel());
    }

    @Test
    public final void testUitvoerVoorBerichtRegelsZonderOptredendeFout() {
        final GeefSynchronisatieStamgegevenBericht bericht = new GeefSynchronisatieStamgegevenBericht();
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setStamgegeven(new StamgegevenAttribuut("GemeenteTabel"));
        final BerichtStandaardGroepBericht standaardGroep = new BerichtStandaardGroepBericht();
        standaardGroep.setAdministratieveHandeling(new HandelingSynchronisatiePersoonBericht());
        bericht.setStandaard(standaardGroep);
        final List<RegelInterface> regelsVoorBericht = new ArrayList<>();
        regelsVoorBericht.add(new BRLV0024());
        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.BRLV0024)).thenReturn(
            new RegelParameters(null, SoortMelding.FOUT, Regel.BRLV0024, null, SoortFout.VERWERKING_VERHINDERD));
        Mockito.when(bedrijfsregelManager.getUitTeVoerenRegelsVoorBericht(Matchers.any(SoortBericht.class)))
            .thenReturn(regelsVoorBericht);

        verwerkRegelsStap.voerStapUit(bericht, getBerichtContext(), getResultaat());
        Assert.assertTrue(getResultaat().getMeldingen().isEmpty());
    }
}
