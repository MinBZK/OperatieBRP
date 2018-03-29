/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingswijze;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

/**
 * Unittest voor {@link BijhoudingVerzoekBericht}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BijhoudingVerzoekBerichtTest {

    private ElementBuilder builder;

    @Before
    public void setUp() {
        builder = new ElementBuilder();
    }

    @Test
    public void testRegistreerPersoonElementenBijBijhoudingPersonen() {
        final ElementBuilder.PersoonParameters kindParams = new ElementBuilder.PersoonParameters();
        kindParams
                .geslachtsnaamcomponenten(Collections.singletonList(builder.maakGeslachtsnaamcomponentElement("geslnaamcomp", null, null, null, null, "Stam")));

        final PersoonGegevensElement ouderElement = builder.maakPersoonGegevensElement("ouder", null);
        final PersoonGegevensElement kindElement = builder.maakPersoonGegevensElement("kind", null, null, kindParams);
        final OuderschapElement ouderschap = builder.maakOuderschapElement("ouderschap", false);
        final BetrokkenheidElement
                ouderBetrokkenheid =
                builder.maakBetrokkenheidElement("ouderBetr", BetrokkenheidElementSoort.OUDER, ouderElement, ouderschap);
        final BetrokkenheidElement kindBetrokkenheid = builder.maakBetrokkenheidElement("kindBetr", BetrokkenheidElementSoort.KIND, kindElement, null);

        final FamilierechtelijkeBetrekkingElement
                fbrElement =
                builder.maakFamilierechtelijkeBetrekkingElement("fbr", null, Arrays.asList(kindBetrokkenheid, ouderBetrokkenheid));

        final ActieElement actie = builder.maakRegistratieGeboreneActieElement("ident", null, fbrElement);

        final AdministratieveHandelingElement administratieveHandeling = maakAdministratieveHandelingElement(Collections.singletonList(actie));

        assertEquals(0, kindElement.getPersoonEntiteit().getPersoonElementen().size());
        final BijhoudingVerzoekBericht bericht = maakBericht(administratieveHandeling);

        bericht.registreerPersoonElementenBijBijhoudingPersonen();
        assertEquals(1, kindElement.getPersoonEntiteit().getPersoonElementen().size());
    }

    private AdministratieveHandelingElement maakAdministratieveHandelingElement(final List<ActieElement> acties) {
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.acties(acties);
        ahParams.partijCode("1");
        ahParams.soort(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);

        return builder.maakAdministratieveHandelingElement("ah", ahParams);
    }

    @Test
    public void testGetSoort() {
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()));
        assertEquals(BijhoudingBerichtSoort.REGISTREER_GEBOORTE, bericht.getSoort());
    }

    @Test
    public void testGetStuurgegevens() {
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()));
        final StuurgegevensElement stuurgegevens = bericht.getStuurgegevens();
        assertNotNull(stuurgegevens);
        assertEquals("1", stuurgegevens.getZendendePartij().getWaarde());
        assertEquals(BijhoudingBerichtSoort.REGISTREER_GEBOORTE, bericht.getSoort());
    }

    @Test
    public void testGetParameters() {
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()));
        final ParametersElement parameters = bericht.getParameters();
        assertNotNull(parameters);
        assertEquals(Verwerkingswijze.BIJHOUDING.getNaam(), parameters.getVerwerkingswijze().getWaarde());
        assertEquals(BijhoudingBerichtSoort.REGISTREER_GEBOORTE, bericht.getSoort());
    }

    @Test
    public void testOinWaardeOndertekenaar() {
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()));
        final String oinWaarde = "abc";
        bericht.setOinWaardeOndertekenaar(oinWaarde);
        assertEquals(oinWaarde, bericht.getOinWaardeOndertekenaar());
    }

    @Test
    public void testOinWaardeTransporteur() {
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()));
        final String oinWaarde = "abc";
        bericht.setOinWaardeTransporteur(oinWaarde);
        assertEquals(oinWaarde, bericht.getOinWaardeTransporteur());
    }

    @Test
    public void testXml() {
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()));
        final String xml = "<test></test>";
        bericht.setXml(xml);
        assertEquals(xml, bericht.getXml());
    }

    @Test
    public void testIsPrevalidatie_Bijhouding() {
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()));
        assertFalse(bericht.isPrevalidatie());
    }

    @Test
    public void testIsPrevalidatie_Prevalidatie() {
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()), true);
        assertTrue(bericht.isPrevalidatie());
    }

    @Test
    @Bedrijfsregel(Regel.R2577)
    public void testValidatieGeenActies() {
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()), true);
        final List<MeldingElement> meldingen = bericht.valideer();
        assertEquals(1,meldingen.size());
        assertEquals(Regel.R2577,meldingen.get(0).getRegel());
    }

    @Test
    public void testGetOntvangst_tijdstip_en_datum() {
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()), true);
        assertNotNull(bericht.getTijdstipOntvangst());
        assertNotNull(bericht.getDatumOntvangst());
    }

    @Test
    public void testObjectSleutelIndex() {
        final ObjectSleutelIndex index = mock(ObjectSleutelIndex.class);
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()), true);
        bericht.setObjectSleutelIndex(index);
        bericht.laadEntiteitenVoorObjectSleutels();
        verify(index, times(1)).initialize();

        final String objectSleutel = "test";
        final Class<BijhoudingPersoon> entiteitType = BijhoudingPersoon.class;
        bericht.getEntiteitVoorObjectSleutel(entiteitType, objectSleutel);
        verify(index, times(1)).getEntiteitVoorObjectSleutel(entiteitType, objectSleutel);

        final Number databaseId = 1;
        bericht.getEntiteitVoorId(entiteitType, databaseId);
        verify(index, times(1)).getEntiteitVoorId(entiteitType, databaseId);

        final Persoon persoon = mock(Persoon.class);
        bericht.voegToeAanObjectSleutelIndex(persoon);
        verify(index, times(1)).voegToe(persoon);

        final BijhoudingPersoon bijhoudingPersoon = mock(BijhoudingPersoon.class);
        bericht.vervangEntiteitMetId(entiteitType, databaseId, bijhoudingPersoon);
        verify(index, times(1)).vervangEntiteitMetId(entiteitType, databaseId, bijhoudingPersoon);

        bericht.voegObjectSleutelToe(objectSleutel, entiteitType, databaseId);
        verify(index, times(1)).voegObjectSleutelToe(objectSleutel, entiteitType, databaseId);
    }

    @Test
    public void testZendendePartij() {
        final ApplicationContext context = mock(ApplicationContext.class);
        final DynamischeStamtabelRepository dynamischeStamtabelRepository = mock(DynamischeStamtabelRepository.class);

        final ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        applicationContextProvider.setApplicationContext(context);
        when(context.getBean(DynamischeStamtabelRepository.class)).thenReturn(dynamischeStamtabelRepository);

        final Partij partij = new Partij("Partij", "000101");
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()));
        when(dynamischeStamtabelRepository.getPartijByCode("1")).thenReturn(partij);
        assertEquals(partij, bericht.getZendendePartij());
    }

    @Bedrijfsregel(Regel.R1838)
    @Test
    public void testReferenties() {
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()));
        final BmrGroepReferentie referentie = mock(BmrGroepReferentie.class);
        bericht.setReferenties(Collections.singletonList(referentie));
        when(referentie.verwijstNaarBestaandEnJuisteType()).thenReturn(true);
        assertTrue(bericht.controleerReferentiesInBericht().isEmpty());

        when(referentie.verwijstNaarBestaandEnJuisteType()).thenReturn(false);
        when(referentie.getCommunicatieId()).thenReturn("refId");
        final List<MeldingElement> meldingen = bericht.controleerReferentiesInBericht();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1838, meldingen.get(0).getRegel());
    }

    @Test
    public void testGetTeArchiverenPersonen() {
        final BijhoudingPersoon persoon1 = mock(BijhoudingPersoon.class);
        final ObjectSleutelIndex index = mock(ObjectSleutelIndex.class);
        final BijhoudingVerzoekBericht bericht = maakBericht(maakAdministratieveHandelingElement(Collections.emptyList()), true);
        bericht.setObjectSleutelIndex(index);

        when(index.getEntiteiten(BijhoudingPersoon.class)).thenReturn(Collections.singletonList(persoon1));
        when(persoon1.getId()).thenReturn(null);
        when(persoon1.isPersoonIngeschrevene()).thenReturn(true);


        final BijhoudingRelatie relatie = mock(BijhoudingRelatie.class);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);
        betrokkenheid.setPersoon(persoon1);

        when(index.getEntiteiten(BijhoudingRelatie.class)).thenReturn(Collections.singletonList(relatie));
        when(relatie.getBetrokkenheidSet()).thenReturn(Collections.singleton(betrokkenheid));
        // Ook al definieren we hier 1 (mock) persoon, deze persoon wordt nog een keer als bijhouding persoon gedecorated bij het ophalen van de hoofdpersonen
        assertEquals(2, bericht.getTeArchiverenPersonen().size());
    }

    private BijhoudingVerzoekBericht maakBericht(final AdministratieveHandelingElement administratieveHandeling) {
        return maakBericht(administratieveHandeling, false);
    }

    private BijhoudingVerzoekBericht maakBericht(final AdministratieveHandelingElement administratieveHandeling, final boolean isPrevalidatie) {
        final Map<String, String> attr = new AbstractBmrGroep.AttributenBuilder().communicatieId("bericht").build();
        final Verwerkingswijze verwerkingswijze = isPrevalidatie ? Verwerkingswijze.PREVALIDATIE : Verwerkingswijze.BIJHOUDING;
        final ParametersElement parameters = builder.maakParametersElement("params", verwerkingswijze.getNaam());

        final ElementBuilder.StuurgegevensParameters stuurParams = new ElementBuilder.StuurgegevensParameters();
        stuurParams.zendendePartij("1");
        stuurParams.zendendeSysteem("partij");
        stuurParams.referentienummer("ref");
        stuurParams.tijdstipVerzending("2016-03-21T08:32:03.234+01:00");
        final StuurgegevensElement stuurgegevens = builder.maakStuurgegevensElement("stuurgegevens", stuurParams);

        return new BijhoudingVerzoekBerichtImpl(attr, BijhoudingBerichtSoort.REGISTREER_GEBOORTE, stuurgegevens, parameters, administratieveHandeling);
    }
}
