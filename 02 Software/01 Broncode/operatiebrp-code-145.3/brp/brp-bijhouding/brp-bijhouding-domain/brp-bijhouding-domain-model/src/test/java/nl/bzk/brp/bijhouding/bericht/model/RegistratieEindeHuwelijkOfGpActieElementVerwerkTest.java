/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.*;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;

/**
 * Tests de verwerking van {@link AbstractRegistratieEindeHuwelijkOfGpActieElement}.
 */
public class RegistratieEindeHuwelijkOfGpActieElementVerwerkTest extends AbstractElementTest {

    private static final String PERSOON_SLEUTEL = "persoon.sleutel";
    private static final String PERSOON_SLEUTEL_ANDER = "persoon.sleutel.ander";
    private static final String BETROKKENHEID_SLEUTEL = "betrokkenheid.sleutel";
    private static final String ANDERE_BETROKKENHEID_SLEUTEL = "andere.betrokkenheid.sleutel";
    private static final String RELATIE_SLEUTEL = "relatie.sleutel";
    private static final String GESLACHTSNAAMSTAM = "jansen";
    private static final RedenBeeindigingRelatie REDEN_EINDE_RELATIE = new RedenBeeindigingRelatie('T', "Test");
    private static final int DATUM_AANVANG_RELATIE = 19990101;
    private static final int DATUM_EINDE_RELATIE = 20160101;
    private static final Gemeente GEMEENTE_EINDE = new Gemeente(Short.parseShort("1"), "Gemeente Einde", "1234", new Partij("Partij Einde", "004321"));
    private static final LandOfGebied LAND_OF_GEBIED = new LandOfGebied("0001", "landGebiedEindeCode");
    private static final LandOfGebied NEDERLAND = new LandOfGebied("6030", "Nederland");
    private static final String WOONPLAATSNAAM_EINDE = "Woonplaats einde";

    @Mock
    private BijhoudingVerzoekBericht bericht;

    private BijhoudingPersoon persoon;
    private BijhoudingPersoon persoonAnder;
    private BijhoudingRelatie relatie;
    private BijhoudingBetrokkenheid ikBetrokkenheid;
    private BijhoudingBetrokkenheid andereBetrokkenheid;

    private PersoonGegevensElement anderePersoonElement;
    private SamengesteldeNaamElement samengesteldeNaamElement;
    private BetrokkenheidElement ikBetrokkenheidElement;
    private BetrokkenheidElement andereBetrokkenheidElement;
    private RelatieGroepElement relatieGroepElement;
    private RelatieGroepElement relatieGroepElementOHB;

    private AdministratieveHandeling administratieveHandeling;

    private Map<String, Number> nieuweObjectSleutelMap;
    private Map<Number, BijhoudingPersoon> bijhoudingPersoonMap;

    @Before
    public void setup() throws ParseException {
        nieuweObjectSleutelMap = new HashMap<>();
        bijhoudingPersoonMap = new HashMap<>();
        final Date tsRegAanvang = DatumUtil.vanStringNaarDatum("19990102");
        final Date tsRegEInde = DatumUtil.vanStringNaarDatum("20160102");
        samengesteldeNaamElement =
                new SamengesteldeNaamElement(
                        new AbstractBmrGroep.AttributenBuilder().build(),
                        BooleanElement.NEE,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement(GESLACHTSNAAMSTAM));
        samengesteldeNaamElement.setVerzoekBericht(bericht);

        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        final Map<String, String> att = new AbstractBmrGroep.AttributenBuilder().objecttype("ot").objectSleutel(PERSOON_SLEUTEL_ANDER).build();
        params.samengesteldeNaam(samengesteldeNaamElement);
        anderePersoonElement = builder.maakPersoonGegevensElement(att, params);
        anderePersoonElement.setVerzoekBericht(bericht);
        ikBetrokkenheidElement =
                new BetrokkenheidElement(
                        new AbstractBmrGroep.AttributenBuilder().objecttype("ot").objectSleutel(BETROKKENHEID_SLEUTEL).build(),
                        BetrokkenheidElementSoort.PARTNER,
                        anderePersoonElement, null, null, null);
        ikBetrokkenheidElement.setVerzoekBericht(bericht);
        andereBetrokkenheidElement =
                new BetrokkenheidElement(
                        new AbstractBmrGroep.AttributenBuilder().objecttype("ot").objectSleutel(ANDERE_BETROKKENHEID_SLEUTEL).build(),
                        BetrokkenheidElementSoort.PARTNER,
                        anderePersoonElement, null, null, null);
        andereBetrokkenheidElement.setVerzoekBericht(bericht);
        relatieGroepElement =
                new RelatieGroepElement(
                        new AbstractBmrGroep.AttributenBuilder().objecttype("ot").objectSleutel(RELATIE_SLEUTEL).build(),
                        null,
                        null,
                        null,
                        new CharacterElement(REDEN_EINDE_RELATIE.getCode()),
                        new DatumElement(DATUM_EINDE_RELATIE),
                        new StringElement(String.valueOf(GEMEENTE_EINDE.getCode())),
                        new StringElement(WOONPLAATSNAAM_EINDE),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatieGroepElement.setVerzoekBericht(bericht);

        relatieGroepElementOHB =
                new RelatieGroepElement(
                        new AbstractBmrGroep.AttributenBuilder().objecttype("ot").objectSleutel(RELATIE_SLEUTEL).build(),
                        null,
                        null,
                        null,
                        new CharacterElement(REDEN_EINDE_RELATIE.getCode()),
                        new DatumElement(DATUM_EINDE_RELATIE),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement("buitenlandsePlaatsEinde"),
                        new StringElement("buitenlandseRegioEinde"),
                        new StringElement("omschrijvingLocatieEinde"),
                        new StringElement("landGebiedEindeCode"));
        relatieGroepElementOHB.setVerzoekBericht(bericht);

        persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        persoon.setId(101L);
        persoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        relatie = BijhoudingRelatie.decorate(new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP));
        relatie.setId(202L);
        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);
        relatieHistorie.setId(1L);
        relatieHistorie.setDatumAanvang(DATUM_AANVANG_RELATIE);
        relatieHistorie.setDatumTijdRegistratie(new Timestamp(tsRegAanvang.getTime()));
        relatie.addRelatieHistorie(relatieHistorie);
        ikBetrokkenheid = BijhoudingBetrokkenheid.decorate(new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie.getDelegate()));
        ikBetrokkenheid.setId(303L);
        persoon.addBetrokkenheid(ikBetrokkenheid);
        andereBetrokkenheid = BijhoudingBetrokkenheid.decorate(new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie.getDelegate()));
        andereBetrokkenheid.setId(404L);
        persoonAnder = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.PSEUDO_PERSOON));
        persoonAnder.setId(102L);
        persoonAnder.addBetrokkenheid(andereBetrokkenheid);
        relatie.addBetrokkenheid(ikBetrokkenheid.getDelegate());
        relatie.addBetrokkenheid(andereBetrokkenheid.getDelegate());

        nieuweObjectSleutelMap.put(PERSOON_SLEUTEL, persoon.getId());
        nieuweObjectSleutelMap.put(PERSOON_SLEUTEL_ANDER, persoonAnder.getId());
        bijhoudingPersoonMap.put(persoon.getId(), persoon);
        bijhoudingPersoonMap.put(persoonAnder.getId(), persoonAnder);

        when(bericht.getEntiteitVoorId(BijhoudingPersoon.class, persoon.getId())).thenReturn(persoon);
        when(bericht.getEntiteitVoorId(BijhoudingPersoon.class, persoonAnder.getId())).thenReturn(persoonAnder);
        when(getDynamischeStamtabelRepository().getRedenBeeindigingRelatieByCode(REDEN_EINDE_RELATIE.getCode())).thenReturn(REDEN_EINDE_RELATIE);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(String.valueOf(GEMEENTE_EINDE.getCode()))).thenReturn(GEMEENTE_EINDE);
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(LAND_OF_GEBIED.getNaam())).thenReturn(LAND_OF_GEBIED);
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(LandOfGebied.CODE_NEDERLAND)).thenReturn(NEDERLAND);
        when(BijhoudingEntiteit.getDynamischeStamtabelRepository().getLandOfGebiedByCode(LandOfGebied.CODE_NEDERLAND)).thenReturn(NEDERLAND);
        when(BijhoudingEntiteit.getDynamischeStamtabelRepository().getLandOfGebiedByCode(String.valueOf(LandOfGebied.CODE_NEDERLAND))).thenReturn(NEDERLAND);

        final Answer<RootEntiteit> linkNaarObjectSleutelIndexMock = invocationOnMock -> {
            Class<? extends RootEntiteit> entiteitType = (Class<? extends RootEntiteit>) invocationOnMock.getArguments()[0];
            final String objectSleutel = (String) invocationOnMock.getArguments()[1];
            if (BijhoudingPersoon.class.equals(entiteitType) && nieuweObjectSleutelMap.containsKey(objectSleutel)) {
                return bijhoudingPersoonMap.get(nieuweObjectSleutelMap.get(objectSleutel));
            } else if (BijhoudingRelatie.class.equals(entiteitType)) {
                if (RELATIE_SLEUTEL.equals(objectSleutel)) {
                    return relatie;
                }
            } else if (BijhoudingBetrokkenheid.class.equals(entiteitType)) {
                if (BETROKKENHEID_SLEUTEL.equals(objectSleutel)) {
                    return ikBetrokkenheid;
                } else if (ANDERE_BETROKKENHEID_SLEUTEL.equals(objectSleutel)) {
                    return andereBetrokkenheid;
                }
            }
            return null;
        };

        when(bericht.getEntiteitVoorObjectSleutel(any(Class.class), anyString())).then(linkNaarObjectSleutelIndexMock);

        administratieveHandeling =
                new AdministratieveHandeling(
                        GEMEENTE_EINDE.getPartij(),
                        SoortAdministratieveHandeling.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                        new Timestamp(tsRegEInde.getTime()));

        final Answer slaNieuweObjectSleutelOp = invocation -> {
            final String objectSleutel = (String) invocation.getArguments()[0];
            final Number databaseId = (Number) invocation.getArguments()[2];
            nieuweObjectSleutelMap.put(objectSleutel, databaseId);
            return null;
        };

        doAnswer(slaNieuweObjectSleutelOp).when(bericht).voegObjectSleutelToe(any(), any(), any());
    }

    @Test
    public void testVerwerkMetBetrokkenheid() {
        testVerwerk(true, false, false, false, false, false);
    }

    @Test
    public void testVerwerkMetOnderzoek() {
        testVerwerk(true, false, false, false, false, true);
    }

    @Test
    public void testVerwerkZonderBetrokkenheid() {
        testVerwerk(false, false, false, false, false, false);
    }

    @Test
    public void testVerwerkMetVerkeerdeBetrokkenheid() {
        testVerwerk(true, true, false, false, false, false);
    }

    @Test
    public void testVerwerkZonderVerwerkbareHoofdPersonen() {
        testVerwerk(true, false, true, false, false, false);
    }

    @Test
    public void testVerwerkEindeBuitenlandORANJE5256() {
        testVerwerk(true, true, false, true, false, false);
    }

    @Test
    public void testVerwerkOmzetting() {
        testVerwerk(true, false, false, false, true, false);
    }

    private void testVerwerk(final boolean metBetrokkenheid, final boolean verkeerdeBetrokkenheid, final boolean nietVerwerkt, final boolean eindeBuitenland,
                             final boolean omzetting, final boolean metOnderzoek) {
        // setup
        if (nietVerwerkt) {
            persoon.setBijhoudingSituatie(BijhoudingSituatie.OPNIEUW_INDIENEN);
        }
        final AbstractRegistratieEindeHuwelijkOfGpActieElement actie = maakActie(metBetrokkenheid, verkeerdeBetrokkenheid, eindeBuitenland);
        assertEquals(1, relatie.getRelatieHistorieSet().size());
        final RelatieHistorie aanvangVoorkomen = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatie.getRelatieHistorieSet());
        assertNotNull(aanvangVoorkomen);
        assertEquals(DATUM_AANVANG_RELATIE, aanvangVoorkomen.getDatumAanvang().intValue());
        assertNull(aanvangVoorkomen.getDatumEinde());
        assertTrue(persoonAnder.getPersoonSamengesteldeNaamHistorieSet().isEmpty());
        ElementBuilder builder = new ElementBuilder();

        if (metOnderzoek) {
            assertNotNull(aanvangVoorkomen.getId());
            final Onderzoek onderzoek = new Onderzoek(administratieveHandeling.getPartij(), persoon);
            final GegevenInOnderzoek gegevenInOnderzoek = new GegevenInOnderzoek(onderzoek, Element.RELATIE_DATUMAANVANG);
            gegevenInOnderzoek.setEntiteitOfVoorkomen(aanvangVoorkomen);
            onderzoek.addGegevenInOnderzoek(gegevenInOnderzoek);
            persoon.addOnderzoek(onderzoek);
        }

        final AdministratieveHandelingElementSoort soortHandeling;
        if (omzetting) {
            soortHandeling = AdministratieveHandelingElementSoort.OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK;
        } else {
            soortHandeling = AdministratieveHandelingElementSoort.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND;
        }
        when(bericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("ci_ah",
                new ElementBuilder.AdministratieveHandelingParameters()
                        .soort(soortHandeling).partijCode("1")
                        .bronnen(Collections.emptyList())));
        // excute
        actie.verwerk(bericht, administratieveHandeling);
        // verify
        if (nietVerwerkt) {
            assertEquals(1, relatie.getRelatieHistorieSet().size());
            assertTrue(persoonAnder.getPersoonSamengesteldeNaamHistorieSet().isEmpty());
        } else {
            assertEquals(2, relatie.getRelatieHistorieSet().size());
            final RelatieHistorie eindeVoorkomen = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatie.getRelatieHistorieSet());
            assertNotNull(eindeVoorkomen);
            assertEquals(DATUM_AANVANG_RELATIE, eindeVoorkomen.getDatumAanvang().intValue());
            assertNotNull(eindeVoorkomen.getDatumEinde());
            assertEquals(DATUM_EINDE_RELATIE, eindeVoorkomen.getDatumEinde().intValue());
            assertEquals(REDEN_EINDE_RELATIE, eindeVoorkomen.getRedenBeeindigingRelatie());
            if (eindeBuitenland) {
                assertEquals("buitenlandseRegioEinde", eindeVoorkomen.getBuitenlandseRegioEinde());
                assertEquals("buitenlandsePlaatsEinde", eindeVoorkomen.getBuitenlandsePlaatsEinde());
                assertEquals("omschrijvingLocatieEinde", eindeVoorkomen.getOmschrijvingLocatieEinde());
                assertEquals(LAND_OF_GEBIED, eindeVoorkomen.getLandOfGebiedEinde());
            } else {
                assertEquals(GEMEENTE_EINDE, eindeVoorkomen.getGemeenteEinde());
                assertEquals(WOONPLAATSNAAM_EINDE, eindeVoorkomen.getWoonplaatsnaamEinde());
            }

            // test
            if (metBetrokkenheid && !verkeerdeBetrokkenheid) {
                assertEquals(1, persoonAnder.getPersoonSamengesteldeNaamHistorieSet().size());
                final PersoonSamengesteldeNaamHistorie naamVoorkomen =
                        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoonAnder.getPersoonSamengesteldeNaamHistorieSet());
                assertNotNull(naamVoorkomen);
                assertEquals(GESLACHTSNAAMSTAM, naamVoorkomen.getGeslachtsnaamstam());
                assertEquals(DATUM_EINDE_RELATIE, naamVoorkomen.getDatumAanvangGeldigheid().intValue());
                assertEquals(eindeVoorkomen.getActieInhoud(), naamVoorkomen.getActieInhoud());
                assertEquals(administratieveHandeling, eindeVoorkomen.getActieInhoud().getAdministratieveHandeling());
                assertEquals(administratieveHandeling, naamVoorkomen.getActieInhoud().getAdministratieveHandeling());
            } else {
                assertTrue(persoonAnder.getPersoonSamengesteldeNaamHistorieSet().isEmpty());
            }
            if (omzetting) {
                assertEquals(2, persoon.getBetrokkenheidSet().size());
                final BijhoudingRelatie huwelijk = BijhoudingRelatie.decorate(new ArrayList<>(persoon.getBetrokkenheidSet()).get(1).getRelatie());
                final RelatieHistorie
                        huwelijkVoorkomen =
                        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(huwelijk.getRelatieHistorieSet());
                assertNotNull(huwelijk);
                assertEquals(SoortRelatie.HUWELIJK, huwelijk.getSoortRelatie());
                assertEquals(eindeVoorkomen.getDatumEinde(), huwelijkVoorkomen.getDatumAanvang());
                assertEquals(eindeVoorkomen.getGemeenteEinde(), huwelijkVoorkomen.getGemeenteAanvang());
                assertEquals(eindeVoorkomen.getWoonplaatsnaamEinde(), huwelijkVoorkomen.getWoonplaatsnaamAanvang());
                assertEquals(NEDERLAND, eindeVoorkomen.getLandOfGebiedEinde());
                assertEquals(eindeVoorkomen.getLandOfGebiedEinde().getCode(), huwelijkVoorkomen.getLandOfGebiedAanvang().getCode());
                assertNull(huwelijkVoorkomen.getDatumEinde());
                assertNull(huwelijkVoorkomen.getGemeenteEinde());
                assertNull(huwelijkVoorkomen.getWoonplaatsnaamEinde());
            } else {
                assertEquals(1, persoon.getBetrokkenheidSet().size());
            }
            if (metOnderzoek) {
                assertEquals(1, persoon.getOnderzoeken().size());
                final Onderzoek onderzoek = persoon.getOnderzoeken().iterator().next();
                assertEquals(2, onderzoek.getGegevenInOnderzoekSet().size());
                final GegevenInOnderzoek
                        gegevenInOnderzoekVoorEindeVoorkomen =
                        onderzoek.getGegevenInOnderzoekSet().stream().filter(gio -> gio.getEntiteitOfVoorkomen().getId() == null).findFirst().get();
                assertNotNull(gegevenInOnderzoekVoorEindeVoorkomen);
                assertEquals(eindeVoorkomen, gegevenInOnderzoekVoorEindeVoorkomen.getEntiteitOfVoorkomen());
            }
        }
    }

    private AbstractRegistratieEindeHuwelijkOfGpActieElement maakActie(
            final boolean metBetrokkenheid,
            final boolean verkeerdeBetrokkenheid,
            final boolean eindeBuitenland) {
        final AbstractRegistratieEindeHuwelijkOfGpActieElement result =
                new AbstractRegistratieEindeHuwelijkOfGpActieElement(
                        new AbstractBmrGroep.AttributenBuilder().objecttype("ot").build(),
                        relatieGroepElement.getDatumEinde(),
                        null,
                        Collections.emptyList(),
                        maakHuwelijkOfGp(metBetrokkenheid, verkeerdeBetrokkenheid, eindeBuitenland)) {
                    @Override
                    public SoortActie getSoortActie() {
                        return SoortActie.REGISTRATIE_EINDE_GEREGISTREERD_PARTNERSCHAP;
                    }
                };
        result.setVerzoekBericht(bericht);
        return result;
    }

    private AbstractHuwelijkOfGpElement maakHuwelijkOfGp(
            final boolean metBetrokkenheid,
            final boolean verkeerdeBetrokkenheid,
            final boolean eindeBuitenland) {
        final List<BetrokkenheidElement> betrokkenheden = new ArrayList<>();
        if (metBetrokkenheid) {
            if (verkeerdeBetrokkenheid) {
                betrokkenheden.add(ikBetrokkenheidElement);
            } else {
                betrokkenheden.add(andereBetrokkenheidElement);
            }
        }
        final AbstractHuwelijkOfGpElement result =
                new AbstractHuwelijkOfGpElement(
                        new AbstractBmrGroep.AttributenBuilder().objecttype("ot").objectSleutel(RELATIE_SLEUTEL).build(),
                        betrokkenheden,
                        eindeBuitenland ? relatieGroepElementOHB : relatieGroepElement) {
                    @Override
                    public SoortRelatie getSoortRelatie() {
                        return SoortRelatie.GEREGISTREERD_PARTNERSCHAP;
                    }
                };
        result.setVerzoekBericht(bericht);
        return result;
    }
}
