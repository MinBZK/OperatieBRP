/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECT_SLEUTEL_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.REFERENTIE_ID_ATTRIBUUT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

public class RegistratieEindeHuwelijkOfGpActieElementTest extends AbstractHuwelijkInNederlandTestBericht {

    @Mock
    BijhoudingVerzoekBericht bericht;

    private AbstractRegistratieEindeHuwelijkOfGpActieElement hoofdActie;

    private static final CharacterElement REDEN_BEKEND = new CharacterElement('A');
    private static final CharacterElement REDEN_ONBEKEND = new CharacterElement('X');
    private static final StringElement STRING_ELEMENT_PARTIJ_CODE = new StringElement("01234");
    private final Map<String, String> attr = new AbstractBmrGroep.AttributenBuilder().build();
    private final StringElement dummy = new StringElement("dummy");
    private final SamengesteldeNaamElement samengesteldeNaamElement =
            new SamengesteldeNaamElement(attr, BooleanElement.NEE, dummy, dummy, dummy, dummy, new CharacterElement('A'), dummy);
    private final Gemeente gemeente = new Gemeente(Short.parseShort("1"), "een", "0001", new Partij("een", "000001"));
    private final Gemeente gemeente1 = new Gemeente(Short.parseShort("2"), "twee", "0002", new Partij("twee", "000002"));
    private final Gemeente gemeente2 = new Gemeente(Short.parseShort("3"), "drie", "0003", new Partij("drie", "000003"));
    private final Gemeente gemeente3 = new Gemeente(Short.parseShort("4"), "vier", "0004", new Partij("vier", "000004"));
    private final List<ActieElement> acties = new ArrayList<>();
    ;

    @Before
    public void setup() {
        attr.clear();
        attr.put(OBJECTTYPE_ATTRIBUUT.toString(), "persoon");
        attr.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "test");
        gemeente.setVoortzettendeGemeente(gemeente1);
        gemeente1.setVoortzettendeGemeente(gemeente2);
        gemeente2.setVoortzettendeGemeente(gemeente3);
        hoofdActie = maakValidatieGPActie(0, null, "0002", 20160202, REDEN_BEKEND, false);
        hoofdActie.setVerzoekBericht(bericht);
        HuwelijkOfGpElement huwelijkOfGpElement =
                new GeregistreerdPartnerschapElement(
                        new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_hgp").objecttype("ot_hgp").referentieId("ref").build(),
                        createRelatieGroepElement("0002", 20160101, new CharacterElement('A')),
                        new ArrayList<>());
        huwelijkOfGpElement.setVerzoekBericht(bericht);
        ReflectionTestUtils.setField(hoofdActie, "huwelijkOfGp", huwelijkOfGpElement);
        acties.add(hoofdActie);

        final AdministratieveHandelingElement element =
                new AdministratieveHandelingElement(
                        attr,
                        AdministratieveHandelingElementSoort.OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK,
                        new StringElement("1547"),
                        null,
                        null,
                        null,
                        new ArrayList<>(),
                        acties);
        when(bericht.getAdministratieveHandeling()).thenReturn(element);

        final Plaats plaats = new Plaats("naam");
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam("dummy")).thenReturn(plaats);
        gemeente.getPartij().setId((short) 100);
        gemeente1.getPartij().setId((short) 101);
        gemeente2.getPartij().setId((short) 102);
        gemeente3.getPartij().setId((short) 103);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0001")).thenReturn(gemeente);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0002")).thenReturn(gemeente);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode("1547")).thenReturn(gemeente);

        when(getDynamischeStamtabelRepository().getRedenBeeindigingRelatieByCode(any(Character.class))).thenReturn(
                new RedenBeeindigingRelatie('A', "Oms"));

    }

    @Test
    public void correctEindeGP() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, null, null));
        RegistratieEindeGeregistreerdPartnerschapActieElement actie = maakValidatieGPActie(1, null, "0001", 20160101, new CharacterElement('Z'), false);
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingElements.size());
    }

    @Test
    public void correctEindeGPBL() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, null, null));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        RegistratieEindeGeregistreerdPartnerschapActieElement actie =
                maakValidatieGPActieBuitenland(1, null, null, 20160101, new CharacterElement('Z'), null, "Barcelona", "Catalunia", null, "6037");
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingElements.size());
    }

    @Test
    public void EindeGPBLOnbekendZonderLocOmschrijving() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, null, null));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        RegistratieEindeGeregistreerdPartnerschapActieElement actie =
                maakValidatieGPActieBuitenland(1, null, null, 20160101, new CharacterElement('Z'), null, "Barcelona", "Catalunia", null, "0000");
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingElements.size());
        assertEquals(Regel.R2049, meldingElements.get(0).getRegel());
    }

    @Test
    public void EindeGPBLInternationaalZonderLocOmschrijving() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, null, null));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        RegistratieEindeGeregistreerdPartnerschapActieElement actie =
                maakValidatieGPActieBuitenland(
                        1,
                        null,
                        null,
                        20160101,
                        new CharacterElement('Z'),
                        null,
                        "Barcelona",
                        "Catalunia",
                        null,
                        String.valueOf(LandOfGebied.CODE_INTERNATIONAAL));
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingElements.size());
        assertEquals(Regel.R2049, meldingElements.get(0).getRegel());
    }

    @Test
    public void EindeGPBLNederlandMetLocOmschrijving() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, null, null));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        RegistratieEindeGeregistreerdPartnerschapActieElement actie =
                maakValidatieGPActieBuitenland(
                        1,
                        null,
                        null,
                        20160101,
                        new CharacterElement('Z'),
                        null,
                        "Barcelona",
                        "Catalunia",
                        "Iets",
                        String.valueOf(LandOfGebied.CODE_NEDERLAND));
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingElements.size());
        assertEquals(Regel.R2049, meldingElements.get(0).getRegel());
    }

    @Test
    public void correctEindeHuwelijk() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, null, null));
        RegistratieEindeHuwelijkActieElement actie = maakValidatieHPActie(1, null, "0001", 20160101, new CharacterElement('Z'));
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingElements.size());
    }

    @Test
    public void correct_gemeente_einde_is_voortzettende_gemeente() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, null, null));
        RegistratieEindeGeregistreerdPartnerschapActieElement actie = maakValidatieGPActie(1, null, "0002", 20160101, new CharacterElement('Z'), false);
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingElements.size());
    }

    @Test
    public void R1864_datum_einde_voor_datum_aanvang() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, 20160102, null));
        RegistratieEindeGeregistreerdPartnerschapActieElement actie = maakValidatieGPActie(1, null, "0001", 20160101, new CharacterElement('Z'), false);
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingElements.size());
        assertEquals(Regel.R1864, meldingElements.get(0).getRegel());
    }

    @Test
    public void R1864_datum_einde_op_datum_aanvang() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, 20160101, null));
        RegistratieEindeGeregistreerdPartnerschapActieElement actie = maakValidatieGPActie(1, null, "0001", 20160101, new CharacterElement('Z'), false);
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingElements.size());
    }

    @Test
    public void R1874_Relatie_is_al_beeindigd() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, null, 20100101));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        RegistratieEindeGeregistreerdPartnerschapActieElement actie = maakValidatieGPActie(1, null, "0001", 20160101, new CharacterElement('Z'), false);
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingElements.size());
        assertEquals(Regel.R1874, meldingElements.get(0).getRegel());
    }

    @Test
    public void R1863_ontbinding_gemeente_ongelijk_aan_aanvang() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, null, null));
        RegistratieEindeGeregistreerdPartnerschapActieElement actie = maakValidatieGPActie(1, null, "0005", 20160101, new CharacterElement('A'), false);
        final AdministratieveHandelingElement element =
                new AdministratieveHandelingElement(
                        attr,
                        AdministratieveHandelingElementSoort.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                        new StringElement("1547"),
                        null,
                        null,
                        null,
                        new ArrayList<>(),
                        acties);
        when(bericht.getAdministratieveHandeling()).thenReturn(element);
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingElements.size());
        assertEquals(Regel.R1863, meldingElements.get(0).getRegel());
    }

    @Test
    public void R1863_ontbinding_gemeente_gelijk_aan_aanvang_met_voorloop_nul() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, null, null));
        RegistratieEindeGeregistreerdPartnerschapActieElement actie = maakValidatieGPActie(1, null, "0001", 20160101, new CharacterElement('A'), false);
        final AdministratieveHandelingElement element =
                new AdministratieveHandelingElement(
                        attr,
                        AdministratieveHandelingElementSoort.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                        new StringElement("1547"),
                        null,
                        null,
                        null,
                        new ArrayList<>(),
                        acties);
        when(bericht.getAdministratieveHandeling()).thenReturn(element);
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingElements.size());
    }

    @Test
    public void R1863_gemeente_aanvang_leeg() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(null, null, null));
        RegistratieEindeGeregistreerdPartnerschapActieElement actie = maakValidatieGPActie(1, null, "0005", 20160101, new CharacterElement('Z'), false);
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingElements.size());
    }

    @Test
    public void R1863_redenEindeOverlijden_ORANJE5008() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, null, null));
        RegistratieEindeGeregistreerdPartnerschapActieElement actie = maakValidatieGPActie(1, null, "0005", 20160101, new CharacterElement('O'), false);
        final AdministratieveHandelingElement element =
                new AdministratieveHandelingElement(
                        attr,
                        AdministratieveHandelingElementSoort.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                        new StringElement("1547"),
                        null,
                        null,
                        null,
                        new ArrayList<>(),
                        acties);
        when(bericht.getAdministratieveHandeling()).thenReturn(element);
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingElements.size());
    }

    @Test
    public void R2254_twee_ingrschrevene_p1_samengesteldeNaam() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, null, null));
        final AdministratieveHandelingElement element =
                new AdministratieveHandelingElement(
                        attr,
                        AdministratieveHandelingElementSoort.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                        new StringElement("1547"),
                        null,
                        null,
                        null,
                        new ArrayList<>(),
                        acties);
        when(bericht.getAdministratieveHandeling()).thenReturn(element);
        testgroepen(samengesteldeNaamElement);
    }

    @Test
    public void R2254_groepen_op_pseudo() {
        RegistratieEindeGeregistreerdPartnerschapActieElement actie =
                maakValidatieGPActie(1, samengesteldeNaamElement, "0002", 20160101, new CharacterElement('Z'), false);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, null, null));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.PSEUDO_PERSOON)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingElements.size());
    }

    @Test
    public void testOngeldigeRedenR1876() throws OngeldigeObjectSleutelException {
        final RegistratieEindeGeregistreerdPartnerschapActieElement actieElement =
                maakValidatieGPActie(0, samengesteldeNaamElement, "0001", 20160202, REDEN_ONBEKEND, false);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(maakPersoon(SoortPersoon.INGESCHREVENE, 1L));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(maakPersoon(SoortPersoon.INGESCHREVENE, 2L));
        when(bericht.getStuurgegevens()).thenReturn(
                new StuurgegevensElement(
                        attr,
                        new StringElement("1547"),
                        new StringElement("zendendeSysteem"),
                        null,
                        new StringElement("referentienummer"),
                        null,
                        new DatumTijdElement(DatumUtil.nuAlsZonedDateTime())));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, 20160102, null));
        final List<MeldingElement> meldingElements = actieElement.valideer();
        assertEquals(1, meldingElements.size());
        assertEquals(Regel.R1876, meldingElements.get(0).getRegel());
    }

    @Test
    public void testOntbindingBuitenlandOngeldigeRedenR1876() throws OngeldigeObjectSleutelException {
        final RegistratieEindeGeregistreerdPartnerschapActieElement actieElement =
                maakValidatieGPActie(0, samengesteldeNaamElement, "0001", 20160202, REDEN_ONBEKEND, false);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(maakPersoon(SoortPersoon.INGESCHREVENE, 1L));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(maakPersoon(SoortPersoon.INGESCHREVENE, 2L));
        when(bericht.getStuurgegevens()).thenReturn(
                new StuurgegevensElement(
                        attr,
                        new StringElement("1547"),
                        new StringElement("zendendeSysteem"),
                        null,
                        new StringElement("referentienummer"),
                        null,
                        new DatumTijdElement(DatumUtil.nuAlsZonedDateTime())));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, 20160102, null));
        final AdministratieveHandelingElement element =
                new AdministratieveHandelingElement(
                        attr,
                        AdministratieveHandelingElementSoort.ONTBINDING_HUWELIJK_IN_BUITENLAND,
                        new StringElement("1547"),
                        null,
                        null,
                        null,
                        new ArrayList<>(),
                        acties);
        when(bericht.getAdministratieveHandeling()).thenReturn(element);
        final List<MeldingElement> meldingElements = actieElement.valideer();
        assertEquals(1, meldingElements.size());
        assertEquals(Regel.R1876, meldingElements.get(0).getRegel());
    }

    @Test
    public void tesNietigverklaringOngeldigeRedenR1876() throws OngeldigeObjectSleutelException {
        final RegistratieEindeGeregistreerdPartnerschapActieElement actieElement =
                maakValidatieGPActie(0, samengesteldeNaamElement, "0001", 20160202, REDEN_ONBEKEND, false);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(maakPersoon(SoortPersoon.INGESCHREVENE, 1L));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(maakPersoon(SoortPersoon.INGESCHREVENE, 2L));
        when(bericht.getStuurgegevens()).thenReturn(
                new StuurgegevensElement(
                        attr,
                        new StringElement("1547"),
                        new StringElement("zendendeSysteem"),
                        null,
                        new StringElement("referentienummer"),
                        null,
                        new DatumTijdElement(DatumUtil.nuAlsZonedDateTime())));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, 20160102, null));
        final AdministratieveHandelingElement element =
                new AdministratieveHandelingElement(
                        attr,
                        AdministratieveHandelingElementSoort.NIETIGVERKLARING_HUWELIJK_IN_NEDERLAND,
                        new StringElement("1547"),
                        null,
                        null,
                        null,
                        new ArrayList<>(),
                        acties);
        when(bericht.getAdministratieveHandeling()).thenReturn(element);
        final List<MeldingElement> meldingElements = actieElement.valideer();
        assertEquals(1, meldingElements.size());
        assertEquals(Regel.R1876, meldingElements.get(0).getRegel());
    }

    @Test
    public void testGeldigeRedenR1876() throws OngeldigeObjectSleutelException {
        final RegistratieEindeGeregistreerdPartnerschapActieElement actieElement = maakValidatieGPActie(0, null, "0001", 20160202, REDEN_BEKEND, false);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(maakPersoon(SoortPersoon.INGESCHREVENE, 1L));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(maakPersoon(SoortPersoon.INGESCHREVENE, 2L));
        when(bericht.getStuurgegevens()).thenReturn(
                new StuurgegevensElement(
                        attr,
                        new StringElement("1547"),
                        new StringElement("zendendeSysteem"),
                        null,
                        new StringElement("referentienummer"),
                        null,
                        new DatumTijdElement(DatumUtil.nuAlsZonedDateTime())));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatie(gemeente, 20160102, null));
        final AdministratieveHandelingElement element =
                new AdministratieveHandelingElement(
                        attr,
                        AdministratieveHandelingElementSoort.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                        new StringElement("1547"),
                        null,
                        null,
                        null,
                        new ArrayList<>(),
                        acties);
        element.setVerzoekBericht(bericht);
        when(bericht.getAdministratieveHandeling()).thenReturn(element);
        final List<MeldingElement> meldingElements = actieElement.valideer();
        assertEquals(0, meldingElements.size());
    }

    @Test
    public void testRegel1845() throws OngeldigeWaardeException, OngeldigeObjectSleutelException {
        // setup
        final int partijCode = 1;
        final String relatieObjectSleutel = "relatie.1";
        final String betrokkenheidSleutel = "betrokkenheid.1";
        final String persoonObjectSleutel = "persoon.1";

        final Relatie relatieUitBericht = new Relatie(SoortRelatie.HUWELIJK);
        relatieUitBericht.setId(1L);
        final Betrokkenheid betrokkenheidUitBericht = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatieUitBericht);
        betrokkenheidUitBericht.setId(2L);
        relatieUitBericht.addBetrokkenheid(betrokkenheidUitBericht);
        final Persoon persoonUitBericht = new Persoon(SoortPersoon.INGESCHREVENE);
        persoonUitBericht.setId(3L);
        persoonUitBericht.addBetrokkenheid(betrokkenheidUitBericht);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, persoonObjectSleutel)).thenReturn(
                BijhoudingPersoon.decorate(persoonUitBericht));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, relatieObjectSleutel)).thenReturn(
                BijhoudingRelatie.decorate(relatieUitBericht));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingBetrokkenheid.class, betrokkenheidSleutel)).thenReturn(
                BijhoudingBetrokkenheid.decorate(betrokkenheidUitBericht));

        final StuurgegevensElement stuurgegevensElement =
                new StuurgegevensElement(
                        new AbstractBmrGroep.AttributenBuilder().build(),
                        new StringElement("" + partijCode),
                        new StringElement("zendendeSysteem"),
                        null,
                        new StringElement("ref"),
                        null,
                        new DatumTijdElement(ZonedDateTime.now()));
        stuurgegevensElement.setVerzoekBericht(bericht);
        when(bericht.getStuurgegevens()).thenReturn(stuurgegevensElement);

        final String landOfGebiedCode = "0000";
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(landOfGebiedCode)).thenReturn(new LandOfGebied(landOfGebiedCode, landOfGebiedCode));
        final RelatieElement relatieElement =
                maakRelatieElementMetObjecttypeHierarchy(relatieObjectSleutel, betrokkenheidSleutel, persoonObjectSleutel, landOfGebiedCode, null);
        // execute
        final List<MeldingElement> meldingen = relatieElement.valideer();
        // verify
        assertEquals(0, meldingen.size());

        // pas relatie aan zodat betrokkenheid verkeerd wijst
        relatieUitBericht.getBetrokkenheidSet().clear();
        final Betrokkenheid andereBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatieUitBericht);
        andereBetrokkenheid.setId(22L);
        relatieUitBericht.addBetrokkenheid(andereBetrokkenheid);

        // execute run 2
        meldingen.clear();
        meldingen.addAll(relatieElement.valideer());

        // verify run 2
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1845, meldingen.iterator().next().getRegel());
        assertEquals(relatieElement.getBetrokkenheden().iterator().next(), meldingen.iterator().next().getReferentie());

        // pas relatie aan zodat betrokkenheid weer goed wijst
        relatieUitBericht.getBetrokkenheidSet().clear();
        relatieUitBericht.addBetrokkenheid(betrokkenheidUitBericht);

        // execute run 3
        meldingen.clear();
        meldingen.addAll(relatieElement.valideer());

        // verify run 3
        assertEquals(0, meldingen.size());

        // pas betrokenheid aan zodat persoon verkeerd wijst
        final Persoon anderePersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        anderePersoon.setId(33L);
        anderePersoon.addBetrokkenheid(betrokkenheidUitBericht);

        // execute run 4
        meldingen.clear();
        meldingen.addAll(relatieElement.valideer());

        // verify run 4
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1845, meldingen.iterator().next().getRegel());
        assertEquals(relatieElement.getBetrokkenheden().iterator().next().getPersoon(), meldingen.iterator().next().getReferentie());
    }

    @Test
    public void testGemeenteEindeIsNietRegisterGemeenteR2131() throws OngeldigeObjectSleutelException, OngeldigeWaardeException {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(createRelatieEinde(gemeente, 19800101, null));
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0001")).thenReturn(gemeente);
        RegistratieEindeGeregistreerdPartnerschapActieElement actie = maakValidatieGPActie(1, null, "0001", 20160101, new CharacterElement('Z'), true);
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        // verify
        assertEquals(1, meldingElements.size());
        assertEquals(Regel.R2131, meldingElements.iterator().next().getRegel());
    }

    private RelatieElement maakRelatieElementMetObjecttypeHierarchy(
            final String relatieObjectSleutel,
            final String betrokkenheidSleutel,
            final String persoonObjectSleutel,
            final String landOfGebiedCode,
            final StringElement gemeenteEinde) throws OngeldigeWaardeException {
        final RelatieGroepElement relatieGroep =
                new RelatieGroepElement(
                        new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_relatieGroep").build(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        gemeenteEinde,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatieGroep.setVerzoekBericht(bericht);
        final GeboorteElement geboorteElement =
                new GeboorteElement(
                        new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_persoon_geboorte").build(),
                        DatumElement.parseWaarde("2016-01-01"),
                        null,
                        null,
                        null,
                        null,
                        new StringElement("omschrijving"),
                        new StringElement(landOfGebiedCode));
        geboorteElement.setVerzoekBericht(bericht);
        final GeslachtsaanduidingElement geslachtsaanduidingElement =
                new GeslachtsaanduidingElement(
                        new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_persoon_geslachtsaanduiding").build(),
                        new StringElement("M"));
        geslachtsaanduidingElement.setVerzoekBericht(bericht);
        final SamengesteldeNaamElement samengesteldeNaamElementLocal =
                new SamengesteldeNaamElement(
                        new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_persoon_samengesteldeNaam").build(),
                        BooleanElement.NEE,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement("naam"));
        samengesteldeNaamElement.setVerzoekBericht(bericht);
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement(new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_persoon")
                .objecttype("Persoon")
                .objectSleutel(persoonObjectSleutel)
                .build(), params);
        persoonElement.setVerzoekBericht(bericht);
        final BetrokkenheidElement betrokkenheid =
                new BetrokkenheidElement(
                        new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_betrokkenheid")
                                .objecttype("Betrokkenheid")
                                .objectSleutel(betrokkenheidSleutel)
                                .build(),
                        BetrokkenheidElementSoort.PARTNER,
                        persoonElement, null, null, null);
        betrokkenheid.setVerzoekBericht(bericht);
        final List<BetrokkenheidElement> betrokkenheden = new ArrayList<>();
        betrokkenheden.add(betrokkenheid);
        final HuwelijkElement result =
                new HuwelijkElement(
                        new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_relatie")
                                .objecttype("Relatie")
                                .objectSleutel(relatieObjectSleutel)
                                .build(),
                        relatieGroep,
                        betrokkenheden);
        result.setVerzoekBericht(bericht);
        return result;
    }

    private AdministratieveHandeling maakAdministratieveHandeling() {
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(
                        new Partij("Gemeente Groningen", "000014"),
                        SoortAdministratieveHandeling.OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK,
                        Timestamp.from(Instant.now()));
        administratieveHandeling.setId(1L);
        return administratieveHandeling;
    }

    private BijhoudingPersoon maakPersoon(final SoortPersoon soortPersoon, final Long id) {
        final Persoon persoon = new Persoon(soortPersoon);
        persoon.setId(id);
        final PersoonAfgeleidAdministratiefHistorie afgeleidAdministratiefHistorie =
                new PersoonAfgeleidAdministratiefHistorie(id.shortValue(), persoon, maakAdministratieveHandeling(), Timestamp.from(Instant.now()));
        persoon.addPersoonAfgeleidAdministratiefHistorie(afgeleidAdministratiefHistorie);
        persoon.addPersoonSamengesteldeNaamHistorie(new PersoonSamengesteldeNaamHistorie(persoon, "stam", false, false));
        return BijhoudingPersoon.decorate(persoon);
    }

    private BijhoudingRelatie createRelatie(final Gemeente gemeenteAanvang, final Integer datumAanvang, final Integer datumEinde) {
        BijhoudingRelatie relatie = BijhoudingRelatie.decorate(new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP));
        relatie.setGemeenteAanvang(gemeenteAanvang);
        relatie.setDatumEinde(datumEinde);
        relatie.setDatumAanvang(datumAanvang);
        final RelatieHistorie historie = new RelatieHistorie(relatie);
        historie.setGemeenteAanvang(gemeenteAanvang);
        historie.setDatumEinde(datumEinde);
        historie.setDatumAanvang(datumAanvang);
        relatie.addRelatieHistorie(historie);
        return relatie;
    }

    private BijhoudingRelatie createRelatieEinde(final Gemeente gemeenteEinde, final Integer datumAanvang, final Integer datumEinde) {
        BijhoudingRelatie relatie = BijhoudingRelatie.decorate(new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP));
        relatie.setGemeenteEinde(gemeenteEinde);
        relatie.setDatumEinde(datumEinde);
        relatie.setDatumAanvang(datumAanvang);
        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);
        relatieHistorie.setDatumEinde(datumEinde);
        relatieHistorie.setGemeenteEinde(gemeenteEinde);
        relatieHistorie.setDatumAanvang(datumAanvang);
        relatie.addRelatieHistorie(relatieHistorie);
        return relatie;
    }

    private void testgroepen(final SamengesteldeNaamElement samnaam) {
        RegistratieEindeGeregistreerdPartnerschapActieElement actie = maakValidatieGPActie(1, samnaam, "0002", 20160101, new CharacterElement('A'), false);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        final List<MeldingElement> meldingElements = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingElements.size());
        assertEquals(Regel.R2254, meldingElements.get(0).getRegel());
    }

    private RelatieGroepElement createRelatieGroepElement(String gemeenteCode, final Integer datumeinde, final CharacterElement reden) {
        final Map<String, String> at1 = new HashMap<>();
        at1.put(OBJECTTYPE_ATTRIBUUT.toString(), "relatie");
        at1.put(OBJECT_SLEUTEL_ATTRIBUUT.toString(), "1");
        at1.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "relatie.1");
        final DatumElement eindDatum = datumeinde != null ? new DatumElement(datumeinde) : null;
        final RelatieGroepElement result =
                new RelatieGroepElement(
                        at1,
                        null,
                        null,
                        null,
                        reden,
                        eindDatum,
                        new StringElement(gemeenteCode),
                        new StringElement("dummy"),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        result.setVerzoekBericht(bericht);
        return result;
    }

    private RelatieGroepElement createRelatieGroepBuitenlandElement(
            String gemeenteCode,
            final Integer datumeinde,
            final CharacterElement reden,
            final String woonplaatsEinde,
            final String buitenlandsePlaatsEinde,
            final String blRegioEinde,
            final String omschrijvingLocEinde,
            final String landGebiedEindeCode) {
        final Map<String, String> at1 = new HashMap<>();
        at1.put(OBJECT_SLEUTEL_ATTRIBUUT.toString(), "1");
        at1.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "relatie.1");
        at1.put(OBJECTTYPE_ATTRIBUUT.toString(), "relatie");
        final DatumElement eindDatum = datumeinde != null ? new DatumElement(datumeinde) : null;
        final RelatieGroepElement result =
                new RelatieGroepElement(
                        at1,
                        null,
                        null,
                        null,
                        reden,
                        eindDatum,
                        StringElement.getInstance(gemeenteCode),
                        StringElement.getInstance(woonplaatsEinde),
                        null,
                        null,
                        null,
                        null,
                        StringElement.getInstance(buitenlandsePlaatsEinde),
                        StringElement.getInstance(blRegioEinde),
                        StringElement.getInstance(omschrijvingLocEinde),
                        StringElement.getInstance(landGebiedEindeCode));
        result.setVerzoekBericht(bericht);
        return result;
    }

    private RegistratieEindeGeregistreerdPartnerschapActieElement maakValidatieGPActieBuitenland(
            final int persoon_1_of_2,
            final SamengesteldeNaamElement samnaam,
            final String gemeenteCode,
            final Integer datumeinde,
            final CharacterElement reden,
            final String woonplaatsEinde,
            final String blPlaatsEinde,
            final String regioEinde,
            final String omschrLocEinde,
            final String landGebiedEinde) {
        final GeregistreerdPartnerschapElement gpElement =
                new GeregistreerdPartnerschapElement(
                        attr,
                        createRelatieGroepBuitenlandElement(
                                gemeenteCode,
                                datumeinde,
                                reden,
                                woonplaatsEinde,
                                blPlaatsEinde,
                                regioEinde,
                                omschrLocEinde,
                                landGebiedEinde),
                        createBetrokkenheidElements(persoon_1_of_2, samnaam));
        gpElement.setVerzoekBericht(bericht);
        final RegistratieEindeGeregistreerdPartnerschapActieElement result =
                new RegistratieEindeGeregistreerdPartnerschapActieElement(attr, new DatumElement(20010101), null, new ArrayList<>(), gpElement);
        result.setVerzoekBericht(bericht);
        return result;
    }

    private RegistratieEindeGeregistreerdPartnerschapActieElement maakValidatieGPActie(
            final int persoon_1_of_2,
            final SamengesteldeNaamElement samnaam,
            final String gemeenteCode,
            final Integer datumeinde,
            final CharacterElement reden,
            final boolean metDocument) {
        final GeregistreerdPartnerschapElement gpElement =
                new GeregistreerdPartnerschapElement(
                        attr,
                        createRelatieGroepElement(gemeenteCode, datumeinde, reden),
                        createBetrokkenheidElements(persoon_1_of_2, samnaam));
        gpElement.setVerzoekBericht(bericht);

        final DocumentElement documentElement = new DocumentElement(attr, new StringElement("OmzettingAkte"), null, null, STRING_ELEMENT_PARTIJ_CODE);
        documentElement.setVerzoekBericht(bericht);
        final BronElement bronElement = new BronElement(attr, documentElement, null);
        bronElement.setVerzoekBericht(bericht);
        final Map<String, String> attributen = new LinkedHashMap<>(attr);
        final String referentieId = "ref";
        attributen.put(REFERENTIE_ID_ATTRIBUUT.toString(), referentieId);
        final Map<String, BmrGroep> communicatieIdGroep = new LinkedHashMap<>();
        communicatieIdGroep.put(referentieId, bronElement);

        final BronReferentieElement bronReferentieElement = new BronReferentieElement(attributen);
        bronReferentieElement.setVerzoekBericht(bericht);
        bronReferentieElement.initialiseer(communicatieIdGroep);

        final RegistratieEindeGeregistreerdPartnerschapActieElement result =
                new RegistratieEindeGeregistreerdPartnerschapActieElement(
                        attr,
                        new DatumElement(20010101),
                        null,
                        metDocument ? Collections.singletonList(bronReferentieElement) : new ArrayList<>(),
                        gpElement);
        result.setVerzoekBericht(bericht);
        return result;
    }

    private RegistratieEindeHuwelijkActieElement maakValidatieHPActie(
            final int persoon_1_of_2,
            final SamengesteldeNaamElement samnaam,
            final String gemeenteCode,
            final Integer datumeinde,
            final CharacterElement reden) {
        final HuwelijkElement element =
                new HuwelijkElement(
                        attr,
                        createRelatieGroepElement(gemeenteCode, datumeinde, reden),
                        createBetrokkenheidElements(persoon_1_of_2, samnaam));
        element.setVerzoekBericht(bericht);
        final RegistratieEindeHuwelijkActieElement result =
                new RegistratieEindeHuwelijkActieElement(attr, new DatumElement(20010101), null, new ArrayList<>(), element);
        result.setVerzoekBericht(bericht);
        return result;
    }

    private List<BetrokkenheidElement> createBetrokkenheidElements(final int persoon_1_of_2, final SamengesteldeNaamElement samnaam) {
        final PersoonGegevensElement p1;
        final PersoonGegevensElement p2;
        final List<BetrokkenheidElement> betrokkenheden = new ArrayList<>();
        final Map<String, String> at1 = new HashMap<>();
        at1.put(OBJECTTYPE_ATTRIBUUT.toString(), "persoon");
        at1.put(OBJECT_SLEUTEL_ATTRIBUUT.toString(), "1");
        at1.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "1");
        final Map<String, String> at2 = new HashMap<>();
        at2.put(OBJECTTYPE_ATTRIBUUT.toString(), "persoon");
        at2.put(OBJECT_SLEUTEL_ATTRIBUUT.toString(), "2");
        at2.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "2");
        if (persoon_1_of_2 == 2) {
            p1 = createPersoon(at1, null, null, null, null);
            p2 = createPersoon(at2, samnaam, null, null, null);
        } else if (persoon_1_of_2 == 1) {
            p1 = createPersoon(at1, samnaam, null, null, null);
            p2 = createPersoon(at2, null, null, null, null);
        } else {
            p1 = createPersoon(at1, null, null, null, null);
            p2 = createPersoon(at2, null, null, null, null);
        }
        p1.setVerzoekBericht(bericht);
        p2.setVerzoekBericht(bericht);

        final BetrokkenheidElement betrokkenheidElement1 = new BetrokkenheidElement(attr, BetrokkenheidElementSoort.PARTNER, p1, null, null, null);
        final BetrokkenheidElement betrokkenheidElement2 = new BetrokkenheidElement(attr, BetrokkenheidElementSoort.PARTNER, p2, null, null, null);
        betrokkenheidElement1.setVerzoekBericht(bericht);
        betrokkenheidElement2.setVerzoekBericht(bericht);
        betrokkenheden.add(betrokkenheidElement1);
        betrokkenheden.add(betrokkenheidElement2);
        return betrokkenheden;
    }

}
