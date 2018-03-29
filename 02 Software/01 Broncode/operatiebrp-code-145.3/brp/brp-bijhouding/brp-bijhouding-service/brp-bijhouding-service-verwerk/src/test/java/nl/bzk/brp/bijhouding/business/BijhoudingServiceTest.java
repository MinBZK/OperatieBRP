/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelServiceImpl;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingBerichtSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingPersoon;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingAntwoordBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import nl.bzk.brp.bijhouding.dal.PersoonRepository;
import nl.bzk.brp.bijhouding.dal.RelatieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Testen voor {@link BijhoudingService}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BijhoudingServiceTest {

    private static final String BIJHOUDING_BERICHT = "uitgebreidVoltrekkingHuwelijkNederlandBericht.xml";
    private static final String GBA_BIJHOUDING_BERICHT = "gba_voltrekkingHuwelijkNederland.xml";
    private static final String BIJHOUDING_ANTWOORDBERICHT = "voltrekkingHuwelijkNederlandAntwoordBericht.xml";
    private static final String FOUT_IN_REFERENTIES_BERICHT = "R1838_foutInReferentiesBericht.xml";
    private static final String BURGERSERVICENUMMER = "681856841";
    private static final String OBJECT_SLEUTEL_PERSOON = "212121";

    @Mock
    private ApplicationContext context;
    @Mock
    private AutorisatieService autorisatieService;
    @Mock
    private ValidatieService validatieService;
    @Mock
    private MutatieNotificatieService mutatieNotificatieService;
    @Mock
    private BijhoudingsplanService bijhoudingsplanService;
    @Mock
    private OntrelateerService ontrelateerService;
    @Mock
    private ArchiefService archiefService;
    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;
    @Mock
    private PersoonRepository persoonRepository;
    @Mock
    private RelatieRepository relatieRepository;
    @Mock
    private ObjectSleutelService objectSleutelService;
    @Mock
    private BijhoudingAntwoordBerichtService bijhoudingAntwoordBerichtService;
    @Mock
    private SoortDocument huwelijksakte;
    @Mock
    private SoortDocument naamswijziging;
    @Mock
    private SoortDocument naamgebruik;
    @Mock
    private LandOfGebied landOfGebied;

    private BijhoudingService bijhoudingService;

    private BijhoudingVerzoekBericht bericht;
    private BijhoudingVerzoekBericht gbaBericht;
    private BijhoudingAntwoordBericht antwoordBericht;
    private BijhoudingVerzoekBericht foutInReferentiesBericht;
    private Partij zendendePartij;
    private BijhoudingsplanContext bijhoudingsplanContext;
    private Persoon persoon;
    private AdministratieveHandeling administratieveHandeling;

    @Before
    public void setup() throws ParseException, OngeldigeObjectSleutelException {
        final ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        applicationContextProvider.setApplicationContext(context);
        when(context.getBean(DynamischeStamtabelRepository.class)).thenReturn(dynamischeStamtabelRepository);
        when(context.getBean(PersoonRepository.class)).thenReturn(persoonRepository);
        when(context.getBean(RelatieRepository.class)).thenReturn(relatieRepository);
        when(context.getBean(ObjectSleutelService.class)).thenReturn(objectSleutelService);

        bericht = new BijhoudingVerzoekBerichtParser().parse(this.getClass().getResourceAsStream(BIJHOUDING_BERICHT));
        gbaBericht = new BijhoudingVerzoekBerichtParser().parse(this.getClass().getResourceAsStream(GBA_BIJHOUDING_BERICHT));
        bijhoudingsplanContext = new BijhoudingsplanContext(bericht);
        antwoordBericht = new BijhoudingAntwoordBerichtParser().parse(this.getClass().getResourceAsStream(BIJHOUDING_ANTWOORDBERICHT));
        foutInReferentiesBericht = new BijhoudingVerzoekBerichtParser().parse(this.getClass().getResourceAsStream(FOUT_IN_REFERENTIES_BERICHT));

        // Validatie service
        when(validatieService.bepaalHoogsteMeldingNiveau(any())).thenReturn(SoortMelding.DEBLOKKEERBAAR);
        when(validatieService.kanVerwerkingDoorgaan(any())).thenReturn(true);

        zendendePartij = new Partij("zendende partij", "053001");

        // persoonsgegevens
        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setBijhoudingsaard(Bijhoudingsaard.INGEZETENE);
        persoon.setId(1L);
        persoon.setBijhoudingspartij(zendendePartij);
        ReflectionTestUtils.setField(persoon, "lockVersie", 1L);

        administratieveHandeling = maakAdministratieveHandeling(zendendePartij);

        final PersoonIDHistorie persoonIDHistorie = new PersoonIDHistorie(persoon);
        persoonIDHistorie.setBurgerservicenummer(BURGERSERVICENUMMER);
        persoonIDHistorie.setAdministratienummer("4829083154");
        persoon.addPersoonIDHistorie(persoonIDHistorie);

        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, zendendePartij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        persoon.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        final PersoonAfgeleidAdministratiefHistorie afgeleidAdministratiefHistorie =
                new PersoonAfgeleidAdministratiefHistorie((short) 1, persoon, administratieveHandeling, Timestamp.from(Instant.now()));
        afgeleidAdministratiefHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        persoon.addPersoonAfgeleidAdministratiefHistorie(afgeleidAdministratiefHistorie);

        // stamtabellen
        when(dynamischeStamtabelRepository.getPartijByCode("053001")).thenReturn(zendendePartij);
        when(dynamischeStamtabelRepository.getSoortDocumentByNaam("Huwelijksakte")).thenReturn(huwelijksakte);
        when(dynamischeStamtabelRepository.getSoortDocumentByNaam("Naamswijziging")).thenReturn(naamswijziging);
        when(dynamischeStamtabelRepository.getSoortDocumentByNaam("Naamgebruik")).thenReturn(naamgebruik);
        when(dynamischeStamtabelRepository.getLandOfGebiedByCode("6008")).thenReturn(landOfGebied);

        when(objectSleutelService.maakPersoonObjectSleutel(OBJECT_SLEUTEL_PERSOON))
                .thenReturn(new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(persoon.getId(), 1));

        when(persoonRepository.findById(persoon.getId())).thenReturn(persoon);

        when(
                bijhoudingAntwoordBerichtService.maakAntwoordBericht(
                        any(BijhoudingVerzoekBericht.class),
                        anyList(),
                        any(AdministratieveHandeling.class),
                        any(BijhoudingsplanContext.class))).thenReturn(antwoordBericht);
        bijhoudingService = new BijhoudingServiceImpl(autorisatieService, validatieService, dynamischeStamtabelRepository,
                mutatieNotificatieService, bijhoudingsplanService, bijhoudingAntwoordBerichtService, ontrelateerService, archiefService);
    }

    @Test
    public void testPostConstruct() {
        BijhoudingVerzoekBericht verzoekBericht = mock(BijhoudingVerzoekBericht.class);
        when(verzoekBericht.getAdministratieveHandeling()).thenReturn(bericht.getAdministratieveHandeling());
        when(verzoekBericht.getSoort()).thenReturn(BijhoudingBerichtSoort.REGISTREER_GEBOORTE);
        when(verzoekBericht.getStuurgegevens()).thenReturn(bericht.getStuurgegevens());
        when(verzoekBericht.getTijdstipOntvangst()).thenReturn(bericht.getTijdstipOntvangst());
        when(verzoekBericht.getParameters()).thenReturn(bericht.getParameters());
        when(validatieService.kanVerwerkingDoorgaan(any())).thenReturn(false);
        bijhoudingService.verwerkBrpBericht(verzoekBericht);
        verify(verzoekBericht, times(1)).postConstruct();
    }

    @Test
    public void testGeenPostConstructBijAutorisatieFout() {
        BijhoudingVerzoekBericht verzoekBericht = mock(BijhoudingVerzoekBericht.class);
        when(verzoekBericht.getAdministratieveHandeling()).thenReturn(bericht.getAdministratieveHandeling());
        when(verzoekBericht.getSoort()).thenReturn(BijhoudingBerichtSoort.REGISTREER_GEBOORTE);
        when(verzoekBericht.getStuurgegevens()).thenReturn(bericht.getStuurgegevens());
        when(verzoekBericht.getTijdstipOntvangst()).thenReturn(bericht.getTijdstipOntvangst());
        when(verzoekBericht.getParameters()).thenReturn(bericht.getParameters());
        when(autorisatieService.autoriseer(verzoekBericht))
                .thenReturn(Collections.singletonList(MeldingElement.getInstance(Regel.R2250, bericht.getAdministratieveHandeling())));
        when(validatieService.kanVerwerkingDoorgaan(any())).thenReturn(false);
        bijhoudingService.verwerkBrpBericht(verzoekBericht);
        verify(verzoekBericht, times(0)).postConstruct();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGedeblokkeerdeMeldingenOK() {
        // setup
        when(bijhoudingsplanService.maakBijhoudingsplan(any(BijhoudingVerzoekBericht.class))).then(invocationOnMock -> {
            bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_PERSOON)
                    .setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
            return bijhoudingsplanContext;
        });
        final List<MeldingElement> meldingen = new ArrayList<>();
        // deze regel (R1292) wordt in het xml bericht gedeblokkeerd
        meldingen.add(MeldingElement.getInstance(Regel.R1292, bericht.getAdministratieveHandeling()));
        when(validatieService.valideer(any())).thenReturn(meldingen);
        // execute
        final BijhoudingAntwoordBericht antwoordBericht = bijhoudingService.verwerkBrpBericht(bericht);

        // verify
        assertNotNull(antwoordBericht);

        ArgumentCaptor<List> meldingenParameter = ArgumentCaptor.forClass(List.class);
        verify(validatieService, times(2)).kanVerwerkingDoorgaan(meldingenParameter.capture());
        final List<MeldingElement> meldingenNaFilter = (List<MeldingElement>) meldingenParameter.getValue();
        assertNotNull(meldingenNaFilter);
        assertTrue(meldingenNaFilter.isEmpty());
        verify(ontrelateerService, times(1)).ontrelateer(bijhoudingsplanContext, bericht);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFoutenInReferenties() {
        // setup
        when(validatieService.kanVerwerkingDoorgaan(any())).thenReturn(false);
        // execute
        bijhoudingService.verwerkBrpBericht(foutInReferentiesBericht);

        // verify
        ArgumentCaptor<List> meldingenParameter = ArgumentCaptor.forClass(List.class);
        verify(validatieService, times(2)).kanVerwerkingDoorgaan(meldingenParameter.capture());
        final List<MeldingElement> meldingen = (List<MeldingElement>) meldingenParameter.getValue();
        assertNotNull(meldingen);
        assertEquals(1, meldingen.size());
        verify(ontrelateerService, times(0)).ontrelateer(any(), any());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGedeblokkeerdeMeldingenOnderdruktDoorAdministratieveHandeling() {
        // setup
        when(bijhoudingsplanService.maakBijhoudingsplan(any(BijhoudingVerzoekBericht.class))).then(invocationOnMock -> {
            gbaBericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_PERSOON).setBijhoudingSituatie(BijhoudingSituatie
                    .AUTOMATISCHE_FIAT);
            return bijhoudingsplanContext;
        });
        final List<MeldingElement> meldingen = new ArrayList<>();
        // deze regel (R1292) wordt in het xml bericht gedeblokkeerd
        meldingen.add(MeldingElement.getInstance(Regel.R1292, bericht.getAdministratieveHandeling()));
        meldingen.add(MeldingElement.getInstance(Regel.R1359, bericht.getAdministratieveHandeling()));
        when(validatieService.valideer(any())).thenReturn(meldingen);
        // execute
        final BijhoudingAntwoordBericht antwoordBericht = bijhoudingService.verwerkBrpBericht(gbaBericht);

        // verify
        assertNotNull(antwoordBericht);

        ArgumentCaptor<List> meldingenParameter = ArgumentCaptor.forClass(List.class);
        verify(validatieService, times(2)).kanVerwerkingDoorgaan(meldingenParameter.capture());
        final List<MeldingElement> meldingenNaFilter = (List<MeldingElement>) meldingenParameter.getValue();
        assertNotNull(meldingenNaFilter);
        assertTrue(meldingenNaFilter.isEmpty());
        verify(ontrelateerService, times(1)).ontrelateer(bijhoudingsplanContext, gbaBericht);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGedeblokkeerdeMeldingenOnderdruktDoorAdministratieveHandelingMetError() {
        // setup

        final List<MeldingElement> meldingen = new ArrayList<>();
        // deze regel (R1292) wordt in het xml bericht gedeblokkeerd
        meldingen.add(MeldingElement.getInstance(Regel.R1292, bericht.getAdministratieveHandeling()));
        meldingen.add(MeldingElement.getInstance(Regel.R1359, bericht.getAdministratieveHandeling()));
        meldingen.add(MeldingElement.getInstance(Regel.R1367, bericht.getAdministratieveHandeling()));
        when(validatieService.valideer(any())).thenReturn(meldingen);
        when(bijhoudingsplanService.maakBijhoudingsplan(any(BijhoudingVerzoekBericht.class))).then(invocationOnMock -> {
            gbaBericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_PERSOON).setBijhoudingSituatie(BijhoudingSituatie
                    .AUTOMATISCHE_FIAT);
            return bijhoudingsplanContext;
        });
        // execute
        final BijhoudingAntwoordBericht antwoordBericht = bijhoudingService.verwerkBrpBericht(gbaBericht);

        // verify
        assertNotNull(antwoordBericht);

        ArgumentCaptor<List> meldingenParameter = ArgumentCaptor.forClass(List.class);
        verify(validatieService, times(2)).kanVerwerkingDoorgaan(meldingenParameter.capture());
        final List<MeldingElement> meldingenNaFilter = (List<MeldingElement>) meldingenParameter.getValue();
        assertNotNull(meldingenNaFilter);
        assertEquals(1, meldingenNaFilter.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGedeblokkeerdeMeldingenNOKVerkeerdeSoortMelding() {
        // setup
        when(bijhoudingsplanService.maakBijhoudingsplan(any(BijhoudingVerzoekBericht.class))).then(invocationOnMock -> {
            gbaBericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_PERSOON).setBijhoudingSituatie(BijhoudingSituatie
                    .AUTOMATISCHE_FIAT);
            return bijhoudingsplanContext;
        });
        final List<MeldingElement> meldingen = new ArrayList<>();
        // deze regel (R1292) wordt in het xml bericht gedeblokkeerd
        meldingen.add(MeldingElement.getInstance(Regel.R1292, bericht.getAdministratieveHandeling()));
        meldingen.add(MeldingElement.getInstance(Regel.R1299, bericht.getAdministratieveHandeling()));
        when(validatieService.valideer(any())).thenReturn(meldingen);
        // execute
        final BijhoudingAntwoordBericht antwoordBericht = bijhoudingService.verwerkBrpBericht(gbaBericht);

        // verify
        assertNotNull(antwoordBericht);

        ArgumentCaptor<List> meldingenParameter = ArgumentCaptor.forClass(List.class);
        verify(validatieService, times(2)).kanVerwerkingDoorgaan(meldingenParameter.capture());
        final List<MeldingElement> meldingenNaFilter = (List<MeldingElement>) meldingenParameter.getValue();
        assertNotNull(meldingenNaFilter);
        assertEquals(1, meldingenNaFilter.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGedeblokkeerdeMeldingenNOKVerkeerdeGroep() {
        // setup
        when(bijhoudingsplanService.maakBijhoudingsplan(any(BijhoudingVerzoekBericht.class))).then(invocationOnMock -> {
            bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_PERSOON)
                    .setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
            return bijhoudingsplanContext;
        });
        final List<MeldingElement> meldingen = new ArrayList<>();
        // deze regel (R1292) wordt in het xml bericht gedeblokkeerd
        meldingen.add(MeldingElement.getInstance(Regel.R1292, bericht.getAdministratieveHandeling().getActies().iterator().next()));
        when(validatieService.valideer(any())).thenReturn(meldingen);
        // execute
        final BijhoudingAntwoordBericht antwoordBericht = bijhoudingService.verwerkBrpBericht(bericht);

        // verify
        assertNotNull(antwoordBericht);

        ArgumentCaptor<List> meldingenParameter = ArgumentCaptor.forClass(List.class);
        verify(validatieService, times(2)).kanVerwerkingDoorgaan(meldingenParameter.capture());
        final List<MeldingElement> meldingenNaFilter = (List<MeldingElement>) meldingenParameter.getValue();
        assertNotNull(meldingenNaFilter);
        assertFalse(meldingenNaFilter.isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGedeblokkeerdeMeldingenNOKOnterechteDeblokkering() {
        when(bijhoudingsplanService.maakBijhoudingsplan(any(BijhoudingVerzoekBericht.class))).then(invocationOnMock -> {
            bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_PERSOON)
                    .setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
            return bijhoudingsplanContext;
        });
        // execute
        final BijhoudingAntwoordBericht antwoordBericht = bijhoudingService.verwerkBrpBericht(bericht);

        // verify
        assertNotNull(antwoordBericht);

        ArgumentCaptor<List> meldingenParameter = ArgumentCaptor.forClass(List.class);
        verify(validatieService, times(2)).kanVerwerkingDoorgaan(meldingenParameter.capture());
        final List<MeldingElement> meldingenNaFilter = (List<MeldingElement>) meldingenParameter.getValue();
        assertNotNull(meldingenNaFilter);
        assertEquals(1, meldingenNaFilter.size());
        assertEquals(Regel.R1839, meldingenNaFilter.get(0).getRegel());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGedeblokkeerdeMeldingenNOKVerkeerdeRegelVoorGroep() {
        when(bijhoudingsplanService.maakBijhoudingsplan(any(BijhoudingVerzoekBericht.class))).then(invocationOnMock -> {
            bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_PERSOON)
                    .setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
            return bijhoudingsplanContext;
        });
        // setup
        final List<MeldingElement> meldingen = new ArrayList<>();
        // Deze regel wordt niet gedeblokeerd voor deze groep maar wel een andere regel voor deze groep
        meldingen.add(MeldingElement.getInstance(Regel.R1299, bericht.getAdministratieveHandeling()));
        when(validatieService.valideer(any())).thenReturn(meldingen);
        // execute
        final BijhoudingAntwoordBericht antwoordBericht = bijhoudingService.verwerkBrpBericht(bericht);

        // verify
        assertNotNull(antwoordBericht);

        ArgumentCaptor<List> meldingenParameter = ArgumentCaptor.forClass(List.class);
        verify(validatieService, times(2)).kanVerwerkingDoorgaan(meldingenParameter.capture());
        final List<MeldingElement> meldingenNaFilter = (List<MeldingElement>) meldingenParameter.getValue();
        assertNotNull(meldingenNaFilter);
        assertEquals(2, meldingenNaFilter.size());
        final List<Regel> regelsInMeldingen = new ArrayList<>();
        for (final MeldingElement meldingNaFilter : meldingenNaFilter) {
            regelsInMeldingen.add(meldingNaFilter.getRegel());
        }
        assertTrue(regelsInMeldingen.contains(Regel.R1299));
        assertTrue(regelsInMeldingen.contains(Regel.R1839));
    }

    @Test
    public void testBijhoudingContext() {
        when(bijhoudingsplanService.maakBijhoudingsplan(any(BijhoudingVerzoekBericht.class))).then(invocationOnMock -> {
            bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_PERSOON)
                    .setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
            return bijhoudingsplanContext;
        });
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        bijhoudingPersoon.setBijhoudingspartijVoorBijhoudingsplan(
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonBijhoudingHistorieSet()).getPartij());
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(bijhoudingPersoon, BijhoudingSituatie.OPNIEUW_INDIENEN);
        // execute
        final BijhoudingAntwoordBericht antwoordBericht = bijhoudingService.verwerkBrpBericht(bericht);

        // verify
        assertNotNull(antwoordBericht);
        assertEquals(1, persoon.getPersoonAfgeleidAdministratiefHistorieSet().size());
    }

    @Test
    public void testBijhoudingContextAfgeleidAdministratief() {
        when(bijhoudingsplanService.maakBijhoudingsplan(any(BijhoudingVerzoekBericht.class))).then(invocationOnMock -> {
            bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OBJECT_SLEUTEL_PERSOON)
                    .setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
            return bijhoudingsplanContext;
        });
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        bijhoudingPersoon.setBijhoudingspartijVoorBijhoudingsplan(
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonBijhoudingHistorieSet()).getPartij());
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(bijhoudingPersoon, BijhoudingSituatie.AUTOMATISCHE_FIAT);
        // execute
        final BijhoudingAntwoordBericht antwoordBericht = bijhoudingService.verwerkBrpBericht(bericht);

        // verify
        assertNotNull(antwoordBericht);
        assertEquals(2, persoon.getPersoonAfgeleidAdministratiefHistorieSet().size());
    }

    private AdministratieveHandeling maakAdministratieveHandeling(final Partij partij) {
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(partij, SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, Timestamp.from(Instant.now()));
        administratieveHandeling.setId(1L);
        return administratieveHandeling;
    }
}
