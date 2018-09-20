/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.service.bericht;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.levering.mutatielevering.stappen.AfnemerStappenVerwerker;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LO3AfleverServiceTest {

    @InjectMocks
    private final LO3AfleverService brpAfleverService = new LO3AfleverService();

    @Mock
    private AfnemerStappenVerwerker verwerker;

    @Test
    public final void testleverBerichtenSuccesvol() {
        when(verwerker.verwerk(Mockito.any(LeveringautorisatieStappenOnderwerp.class), Mockito.any(
            LeveringsautorisatieVerwerkingContext.class))).thenReturn(new LeveringautorisatieVerwerkingResultaat());

        final AdministratieveHandelingModel handeling = maakAdministratieveHandeling();

        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();

        final Map<Integer, Populatie> populatieMap = new HashMap<>();
        populatieMap.put(persoon.getID(), Populatie.BINNEN);

        final Map<Leveringinformatie, Map<Integer, Populatie>> cachePopulatieMap = maakLeveringinformatieMap(populatieMap, Stelsel.GBA);

        // act
        final LeveringautorisatieVerwerkingResultaat resultaat =
            brpAfleverService.leverBerichten(handeling, Collections.singletonList(persoon), cachePopulatieMap, null, null);

        // assert
        Assert.assertTrue(resultaat.isSuccesvol());
        verify(verwerker).verwerk(any(LeveringautorisatieStappenOnderwerp.class), any(LeveringsautorisatieVerwerkingContext.class));
    }

    @Test
    public final void testleverBerichtenDoetNietsVoorBrp() {
        final AdministratieveHandelingModel handeling = maakAdministratieveHandeling();

        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();

        final Map<Integer, Populatie> populatieMap = new HashMap<>();
        populatieMap.put(persoon.getID(), Populatie.BINNEN);

        final Map<Leveringinformatie, Map<Integer, Populatie>> cachePopulatieMap = maakLeveringinformatieMap(populatieMap, Stelsel.BRP);

        // act
        final LeveringautorisatieVerwerkingResultaat resultaat =
            brpAfleverService.leverBerichten(handeling, Collections.singletonList(persoon), cachePopulatieMap, null, null);

        // assert
        Assert.assertTrue(resultaat.isSuccesvol());
        verifyZeroInteractions(verwerker);
    }

    @Test
    public final void testleverBerichtenDoetNietsVoorEBMS() {
        final AdministratieveHandelingModel handeling = maakAdministratieveHandeling();

        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();

        final Map<Integer, Populatie> populatieMap = new HashMap<>();
        populatieMap.put(persoon.getID(), Populatie.BINNEN);

        final Map<Leveringinformatie, Map<Integer, Populatie>> cachePopulatieMap = maakLeveringinformatieMap(populatieMap, Stelsel.DUMMY);

        // act
        final LeveringautorisatieVerwerkingResultaat resultaat =
            brpAfleverService.leverBerichten(handeling, Collections.singletonList(persoon), cachePopulatieMap, null, null);

        // assert
        Assert.assertTrue(resultaat.isSuccesvol());
        verifyZeroInteractions(verwerker);
    }

    private AdministratieveHandelingModel maakAdministratieveHandeling() {
        return new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
            SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND),
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_SGRAVENHAGE,
            new OntleningstoelichtingAttribuut(""),
            DatumTijdAttribuut.nu());
    }

    @Test
    public final void testleverBerichtenNietSuccesvol() {
        when(verwerker.verwerk(Mockito.any(LeveringautorisatieStappenOnderwerp.class), Mockito.any(
            LeveringsautorisatieVerwerkingContext.class)))
            .thenReturn(new LeveringautorisatieVerwerkingResultaat(Collections.singletonList(new Melding(SoortMelding.FOUT, Regel.ALG0001))));

        final AdministratieveHandelingModel handeling = maakAdministratieveHandeling();

        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();

        final Map<Integer, Populatie> populatieMap = new HashMap<>();
        populatieMap.put(persoon.getID(), Populatie.BINNEN);

        final Map<Leveringinformatie, Map<Integer, Populatie>> leveringAutorisatieMap = maakLeveringinformatieMap(populatieMap, Stelsel.GBA);

        // act
        final LeveringautorisatieVerwerkingResultaat resultaat =
            brpAfleverService.leverBerichten(handeling, Collections.singletonList(persoon), leveringAutorisatieMap, null, null);

        // assert
        Assert.assertFalse(resultaat.isSuccesvol());
        verify(verwerker).verwerk(any(LeveringautorisatieStappenOnderwerp.class), any(LeveringsautorisatieVerwerkingContext.class));
    }

    private Map<Leveringinformatie, Map<Integer, Populatie>> maakLeveringinformatieMap(final Map<Integer, Populatie> populatieMap,
        final Stelsel stelsel)
    {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("TestLeveringsautorisatie").metPopulatiebeperking
            ("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(DatumAttribuut.gisteren()).metStelsel(stelsel).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metDummyGeautoriseerde()
            .metLeveringsautorisatie
                (leveringsautorisatie).maak();

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(toegangLeveringsautorisatie, null);
        final Map<Leveringinformatie, Map<Integer, Populatie>> leveringAutorisatieMap = new HashMap<>();
        leveringAutorisatieMap.put(leveringAutorisatie, populatieMap);
        return leveringAutorisatieMap;
    }
}
