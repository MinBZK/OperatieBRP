/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import nl.bzk.brp.levering.algemeen.service.StamTabelService;
import nl.bzk.brp.levering.business.stappen.populatie.*;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.*;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.*;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.util.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.GegevenInOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ZetPersoonObjectSleutelVoorOnderzoekStapTest {

    private static final String ID = "iD";

    private static final long   VOORKOMENSLEUTEL = 2L;
    private static final long   OBJECTSLEUTEL    = 1L;
    private static final String PERSOON          = "Persoon";

    private final DatumTijdAttribuut tijdstipRegistratie = new DatumTijdAttribuut(new Date());

    @Mock
    private ObjectSleutelService objectSleutelService;

    @Mock
    private StamTabelService stamTabelService;

    @InjectMocks
    private final ZetPersoonObjectSleutelVoorOnderzoekStap zetObjectSleutelStap = new ZetPersoonObjectSleutelVoorOnderzoekStap();
    private AdministratieveHandelingModel administratieveHandelingModel;


    @Test
    public final void testVoerStapUit() {

        final Dienst mockDienst = mock(Dienst.class);

        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("TestLeveringsautorisatie").metPopulatiebeperking
            ("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(DatumAttribuut.gisteren()).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).metDummyGeautoriseerde().maak();

        //bouw de personen
        final PersoonHisVolledigImpl persoon1 =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).voegPersoonAfnemerindicatieToe(
                new PersoonAfnemerindicatieHisVolledigImplBuilder(null, leveringsautorisatie)
                    .nieuwStandaardRecord(mockDienst)
                    .datumAanvangMaterielePeriode(20010101).eindeRecord().build()).build();
        ReflectionTestUtils.setField(persoon1, ID, 1);

        final List<PersoonHisVolledig> persoonHisVolledigs = new ArrayList<>();
        final List<PersoonHisVolledigView> persoonHisVolledigViews = new ArrayList<>();
        persoonHisVolledigs.add(persoon1);
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(persoon1, null);
        persoonHisVolledigViews.add(persoonHisVolledigView);
        //bouw onderzoek voor persoon
        bouwOnderzoek(persoon1);

        //bouw context
        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(toegangLeveringsautorisatie, null);

        final LeveringautorisatieVerwerkingResultaat resultaat = new LeveringautorisatieVerwerkingResultaat();
        final AdministratieveHandelingModel administratieveHandelingModel = getTestAdministratieveHandeling();
        final Map<Integer, Populatie> persoonPopulatie = new HashMap<>();
        persoonPopulatie.put(1, Populatie.BINNEN);
        persoonPopulatie.put(2, Populatie.BINNEN);

        final LeveringsautorisatieVerwerkingContext context = new LeveringsautorisatieVerwerkingContextImpl(administratieveHandelingModel, persoonHisVolledigs,
            persoonPopulatie, null, null);
        final LeveringautorisatieStappenOnderwerp onderwerp = new LeveringautorisatieStappenOnderwerpImpl(leveringAutorisatie, administratieveHandelingModel.getID(),
            Stelsel.BRP);

        zetBerichtenInContext(toegangLeveringsautorisatie, persoonHisVolledigViews, context);

        final String objectSleutel = "x";
        Mockito
            .when(objectSleutelService.genereerObjectSleutelString(persoon1.getID(), toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode()
                .getWaarde()))
            .thenReturn(objectSleutel);

        //zet de object sleutels op personen
        zetObjectSleutelStap.voerStapUit(onderwerp, context, resultaat);

        //valideer
        Assert.assertEquals(objectSleutel, persoonHisVolledigView.getObjectSleutel());

    }

    private void zetBerichtenInContext(final ToegangLeveringsautorisatie toegangLeveringsautorisatie,
        final List<PersoonHisVolledigView> persoonHisVolledigViews,
        final LeveringsautorisatieVerwerkingContext context)
    {
        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();

        final AdministratieveHandelingModel administratieveHandeling =
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.ADOPTIE_INGEZETENE), new PartijAttribuut(toegangLeveringsautorisatie.getGeautoriseerde().getPartij()),
                new OntleningstoelichtingAttribuut(""),
                nu);

        final List<SynchronisatieBericht> berichten = new ArrayList<>(2);
        final SynchronisatieBericht mutatieBericht = new MutatieBericht(null);
        final AdministratieveHandelingSynchronisatie administratieveHandelingSynchronisatie =
            new AdministratieveHandelingSynchronisatie(administratieveHandeling);
        administratieveHandelingSynchronisatie.setBijgehoudenPersonen(persoonHisVolledigViews);
        mutatieBericht.setAdministratieveHandeling(administratieveHandelingSynchronisatie);

        berichten.add(mutatieBericht);

        context.setLeveringBerichten(berichten);
    }
    private AdministratieveHandelingModel getTestAdministratieveHandeling() {
        final AdministratieveHandelingModel administratieveHandelingModel = mock(AdministratieveHandelingModel.class);
        when(administratieveHandelingModel.getTijdstipRegistratie()).thenReturn(tijdstipRegistratie);
        when(administratieveHandelingModel.getSoort()).thenReturn(
            new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE));
        return administratieveHandelingModel;
    }

    private void bouwOnderzoek(final PersoonHisVolledigImpl persoon) {
        final Element elementPersoonObjectType = TestElementBuilder.maker().metId(1).metNaam(ElementEnum.PERSOON).metElementNaam(PERSOON).maak();
        final Element element = TestElementBuilder.maker().metId(2).metNaam(ElementEnum.PERSOON).metElementNaam(PERSOON)
            .metElementObjectType(elementPersoonObjectType).maak();
        final GegevenInOnderzoekHisVolledigImpl gegevenInOnderzoek = new GegevenInOnderzoekHisVolledigImplBuilder(element,
            new SleutelwaardeAttribuut(OBJECTSLEUTEL),
            new SleutelwaardeAttribuut(VOORKOMENSLEUTEL), true).build();

        final OnderzoekHisVolledigImpl onderzoek = new OnderzoekHisVolledigImplBuilder(true).nieuwStandaardRecord(20150909).datumAanvang(20150909)
            .omschrijving("is niet pluis").status(StatusOnderzoek.IN_UITVOERING).verwachteAfhandeldatum(20160101).eindeRecord()
            .voegGegevenInOnderzoekToe(gegevenInOnderzoek).build();
        final HisOnderzoekModel actueleRecord = onderzoek.getOnderzoekHistorie().getActueleRecord();
        ReflectionTestUtils.setField(actueleRecord, ID, 4);
        ReflectionTestUtils.setField(gegevenInOnderzoek, ID, 1);
        ReflectionTestUtils.setField(onderzoek, ID, 2);

        final Set<PersoonOnderzoekHisVolledigImpl> persoonOnderzoeken = persoon.getOnderzoeken();
        final PersoonOnderzoekHisVolledigImpl persoonOnderzoek = new PersoonOnderzoekHisVolledigImplBuilder(persoon, onderzoek, true)
            .nieuwStandaardRecord(20150909)
            .rol(SoortPersoonOnderzoek.DIRECT).eindeRecord().build();

        ReflectionTestUtils.setField(persoonOnderzoek, ID, 3);
        persoonOnderzoeken.add(persoonOnderzoek);

        Mockito.when(stamTabelService.geefElementById(1)).thenReturn(elementPersoonObjectType);
        Mockito.when(stamTabelService.geefElementById(2)).thenReturn(element);
    }
}
