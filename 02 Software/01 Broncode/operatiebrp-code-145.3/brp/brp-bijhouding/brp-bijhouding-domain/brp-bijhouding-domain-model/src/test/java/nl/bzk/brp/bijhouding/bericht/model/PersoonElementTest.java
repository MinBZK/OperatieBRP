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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselSleutel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import org.junit.Before;
import org.junit.Test;

/**
 * Testclass PersoonElement.
 */
public class PersoonElementTest extends AbstractNaamTest {
    private static final StringElement GEMEENTECODE_PLAATS_GRONINGEN = new StringElement("0017");
    private static final Partij PARTIJ_GRONINGEN = new Partij("Gemeente Groningen", "000014");
    private BijhoudingVerzoekBericht bericht;
    private Map<String, String> objecttypeAttributen;
    private SamengesteldeNaamElement samengesteldeNaamElement;
    private GeslachtsnaamcomponentElement geslachtsnaamcomponentElement;
    private VoornaamElement voornaamElement;
    private IdentificatienummersElement identificatienummersElement;
    private GeboorteElement geboorteElement;
    private GeslachtsaanduidingElement geslachtsaanduidingElement;

    @Before
    public void setupPersoonElementTest() {
        final String nederlandCode = "6030";

        final Gemeente gemeenteMetGeldigheidDatums = new Gemeente(Short.parseShort("17"), "Groningen", "0018", PARTIJ_GRONINGEN);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(GEMEENTECODE_PLAATS_GRONINGEN.getWaarde())).thenReturn(gemeenteMetGeldigheidDatums);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(GEMEENTECODE_PLAATS_GRONINGEN.getWaarde())).thenReturn(gemeenteMetGeldigheidDatums);

        final Plaats plaatsMetGeldigheidDatums = new Plaats("Groningen");
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(GEMEENTECODE_PLAATS_GRONINGEN.getWaarde())).thenReturn(plaatsMetGeldigheidDatums);

        when(getDynamischeStamtabelRepository().getVoorvoegselByVoorvoegselSleutel(any(VoorvoegselSleutel.class))).thenReturn(
                new Voorvoegsel(new VoorvoegselSleutel(' ', "van")));
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(nederlandCode)).thenReturn(new LandOfGebied("6030", "Nederland"));

        bericht = getBericht();
        objecttypeAttributen = new LinkedHashMap<>();
        objecttypeAttributen.put(OBJECTTYPE_ATTRIBUUT.toString(), "objecttype");
        objecttypeAttributen.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "ci_test");

        final StringElement geslachtsnaamstam = new StringElement("Stam");
        samengesteldeNaamElement = new SamengesteldeNaamElement(objecttypeAttributen, BooleanElement.NEE, null, null, null, null, null, geslachtsnaamstam);
        samengesteldeNaamElement.setVerzoekBericht(bericht);
        geboorteElement =
                new GeboorteElement(
                        objecttypeAttributen,
                        new DatumElement(20150101),
                        GEMEENTECODE_PLAATS_GRONINGEN,
                        GEMEENTECODE_PLAATS_GRONINGEN,
                        null,
                        null,
                        null,
                        new StringElement(String.valueOf(nederlandCode)));
        geboorteElement.setVerzoekBericht(bericht);
        geslachtsaanduidingElement = new GeslachtsaanduidingElement(objecttypeAttributen, new StringElement("M"));
        geslachtsaanduidingElement.setVerzoekBericht(bericht);
        geslachtsnaamcomponentElement =
                new GeslachtsnaamcomponentElement(objecttypeAttributen, null, null, VOORVOEGSEL_ELEMENT, SCHEIDINGSTEKEN_ELEMENT, geslachtsnaamstam);
        geslachtsnaamcomponentElement.setVerzoekBericht(bericht);
        voornaamElement = new VoornaamElement(new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_voornaam").build(), new IntegerElement(1),
                new StringElement("Piet"));
        voornaamElement.setVerzoekBericht(bericht);
        identificatienummersElement = new IdentificatienummersElement(objecttypeAttributen, new StringElement("515170185"), new StringElement("3219658514"));
        identificatienummersElement.setVerzoekBericht(bericht);
    }

    @Bedrijfsregel(Regel.R1809)
    @Test
    public void testIndicatieNamenReeksEnVoornamenInBericht() throws OngeldigeObjectSleutelException {
        final ElementBuilder builder = new ElementBuilder();
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        definieerWhenStapVoorPersoonOpvragen(persoon);
        samengesteldeNaamElement =
                new SamengesteldeNaamElement(objecttypeAttributen, BooleanElement.JA, null, null, null, null, null, new StringElement("Stam"));
        samengesteldeNaamElement.setVerzoekBericht(bericht);
        final List<VoornaamElement> voornamen = new LinkedList<>();
        voornamen.add(builder.maakVoornaamElement("voornaam1", 1, "Jaap"));
        test(identificatienummersElement, samengesteldeNaamElement, null, geslachtsaanduidingElement, geslachtsnaamcomponentElement, null, null, voornamen,
                Regel.R1809);
    }

    @Bedrijfsregel(Regel.R1809)
    @Test
    public void testIndicatieNamenReeksEnVoornamenInBericht_correctieHandeling() throws OngeldigeObjectSleutelException {
        final ElementBuilder builder = new ElementBuilder();
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        samengesteldeNaamElement =
                new SamengesteldeNaamElement(objecttypeAttributen, BooleanElement.JA, null, null, null, null, null, new StringElement("Stam"));
        samengesteldeNaamElement.setVerzoekBericht(bericht);
        final List<VoornaamElement> voornamen = new LinkedList<>();
        voornamen.add(builder.maakVoornaamElement("voornaam1", 1, "Jaap"));

        bericht = mock(BijhoudingVerzoekBericht.class);
        definieerWhenStapVoorPersoonOpvragen(persoon, bericht);
        when(bericht.getStuurgegevens()).thenReturn(getStuurgegevensElement());

        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.partijCode("052901");
        ahParams.soort(AdministratieveHandelingElementSoort.CORRECTIE_GEREGISTREERD_PARTNERSCHAP);
        final AdministratieveHandelingElement ahElement = builder.maakAdministratieveHandelingElement("ahElement", ahParams);
        when(bericht.getAdministratieveHandeling()).thenReturn(ahElement);

        test(identificatienummersElement, samengesteldeNaamElement, null, geslachtsaanduidingElement, geslachtsnaamcomponentElement, null, null, voornamen);
    }

    @Bedrijfsregel(Regel.R1809)
    @Test
    public void testIndicatieNamenReeksNeeEnVoornamenInBericht() throws OngeldigeObjectSleutelException {
        final ElementBuilder builder = new ElementBuilder();
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        definieerWhenStapVoorPersoonOpvragen(persoon);
        samengesteldeNaamElement =
                new SamengesteldeNaamElement(objecttypeAttributen, BooleanElement.NEE, null, null, null, null, null, new StringElement("Stam"));
        samengesteldeNaamElement.setVerzoekBericht(bericht);
        final List<VoornaamElement> voornamen = new LinkedList<>();
        voornamen.add(builder.maakVoornaamElement("voornaam1", 1, "Jaap"));
        test(identificatienummersElement, samengesteldeNaamElement, null, geslachtsaanduidingElement, geslachtsnaamcomponentElement, null, null, voornamen);
    }

    @Bedrijfsregel(Regel.R1809)
    @Test
    public void testIndicatieNamenReeksNeeEnVoornamenInDB() throws OngeldigeObjectSleutelException {
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        final PersoonSamengesteldeNaamHistorie samengesteldeNaam = new PersoonSamengesteldeNaamHistorie(persoon, "stam", false, false);
        samengesteldeNaam.setVoornamen("Jaap");
        samengesteldeNaam.setIndicatieAfgeleid(true);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaam);
        definieerWhenStapVoorPersoonOpvragen(persoon);
        samengesteldeNaamElement =
                new SamengesteldeNaamElement(objecttypeAttributen, BooleanElement.NEE, null, null, null, null, null, new StringElement("Stam"));
        samengesteldeNaamElement.setVerzoekBericht(bericht);
        test(identificatienummersElement, samengesteldeNaamElement, null, geslachtsaanduidingElement, geslachtsnaamcomponentElement, null, null, null);
    }

    @Bedrijfsregel(Regel.R1809)
    @Test
    public void testIndicatieNamenReeksInDBEnVoornamenInBericht() throws OngeldigeObjectSleutelException {
        final ElementBuilder builder = new ElementBuilder();
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        final PersoonSamengesteldeNaamHistorie samengesteldeNaam = new PersoonSamengesteldeNaamHistorie(persoon, "stam", false, true);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaam);
        definieerWhenStapVoorPersoonOpvragen(persoon);
        final List<VoornaamElement> voornamen = new LinkedList<>();
        voornamen.add(builder.maakVoornaamElement("voornaam1", 1, "Jaap"));
        test(identificatienummersElement, null, null, geslachtsaanduidingElement, geslachtsnaamcomponentElement, null, null, voornamen, Regel.R1809,
                Regel.R2736);
    }

    @Bedrijfsregel(Regel.R1809)
    @Test
    public void testIndicatieNamenReeksNeeInDBEnVoornamenInBericht() throws OngeldigeObjectSleutelException {
        final ElementBuilder builder = new ElementBuilder();
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        final PersoonSamengesteldeNaamHistorie samengesteldeNaam = new PersoonSamengesteldeNaamHistorie(persoon, "stam", false, false);
        samengesteldeNaam.setIndicatieAfgeleid(true);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaam);
        definieerWhenStapVoorPersoonOpvragen(persoon);
        final List<VoornaamElement> voornamen = new LinkedList<>();
        voornamen.add(builder.maakVoornaamElement("voornaam1", 1, "Jaap"));
        test(identificatienummersElement, null, null, geslachtsaanduidingElement, geslachtsnaamcomponentElement, null, null, voornamen);
    }

    @Bedrijfsregel(Regel.R1807)
    @Test
    public void voornamenLangerdan200Tekens() {
        final ElementBuilder builder = new ElementBuilder();
        objecttypeAttributen = new AbstractBmrGroep.AttributenBuilder().objecttype("Persoon").communicatieId("ci_persoon").build();
        final List<VoornaamElement> voornamen = new LinkedList<>();
        voornamen.add(builder.maakVoornaamElement("v1", 1, "1234567890123456789"));
        voornamen.add(builder.maakVoornaamElement("v2", 2, "1234567890123456789"));
        voornamen.add(builder.maakVoornaamElement("v3", 3, "1234567890123456789"));
        voornamen.add(builder.maakVoornaamElement("v4", 4, "1234567890123456789"));
        voornamen.add(builder.maakVoornaamElement("v5", 5, "1234567890123456789"));
        voornamen.add(builder.maakVoornaamElement("v6", 6, "1234567890123456789"));
        voornamen.add(builder.maakVoornaamElement("v7", 7, "1234567890123456789"));
        voornamen.add(builder.maakVoornaamElement("v8", 8, "1234567890123456789"));
        voornamen.add(builder.maakVoornaamElement("v9", 9, "1234567890123456789"));
        voornamen.add(builder.maakVoornaamElement("v10", 10, "1234567890123456789"));
        test(
                identificatienummersElement,
                samengesteldeNaamElement,
                null,
                geslachtsaanduidingElement,
                geslachtsnaamcomponentElement,
                null,
                null,
                voornamen);
        objecttypeAttributen = new AbstractBmrGroep.AttributenBuilder().objecttype("Persoon").communicatieId("ci_persoon2").build();
        voornamen.add(builder.maakVoornaamElement("v11", 11, "1"));
        test(
                identificatienummersElement,
                samengesteldeNaamElement,
                null,
                geslachtsaanduidingElement,
                geslachtsnaamcomponentElement,
                null,
                null,
                voornamen, Regel.R1807);
    }

    @Bedrijfsregel(Regel.R1919)
    @Test
    public void voornamenBevatSpatie() {
        final ElementBuilder builder = new ElementBuilder();
        objecttypeAttributen = new AbstractBmrGroep.AttributenBuilder().objecttype("Persoon").communicatieId("ci_persoon").build();
        final List<VoornaamElement> voornamen = new LinkedList<>();
        voornamen.add(builder.maakVoornaamElement("v1", 1, "123456789"));
        voornamen.add(builder.maakVoornaamElement("v2", 2, "1234 6789"));
        voornamen.add(builder.maakVoornaamElement("v3", 3, "123456789"));
        test(
                identificatienummersElement,
                samengesteldeNaamElement,
                null,
                geslachtsaanduidingElement,
                geslachtsnaamcomponentElement,
                null,
                null,
                voornamen, Regel.R1919);
    }

    @Bedrijfsregel(Regel.R1920)
    @Test
    public void voornamenVolgnummer1() {
        final ElementBuilder builder = new ElementBuilder();
        objecttypeAttributen = new AbstractBmrGroep.AttributenBuilder().objecttype("Persoon").communicatieId("ci_persoon").build();
        testVoorNaamVolgnummer(builder, 1, 3, 2);
    }

    @Bedrijfsregel(Regel.R1920)
    @Test
    public void voornamenVolgnummer2() {
        final ElementBuilder builder = new ElementBuilder();
        objecttypeAttributen = new AbstractBmrGroep.AttributenBuilder().objecttype("Persoon").communicatieId("ci_persoon").build();
        testVoorNaamVolgnummer(builder, 1, 1, 3);
    }

    @Bedrijfsregel(Regel.R2736)
    @Bedrijfsregel(Regel.R2738)
    @Test
    public void testWijzigenGeslachtscomponent_PartnerNaamgebruikPartner() {
        final String objectsleutel = "123ab";
        final ElementBuilder builder = new ElementBuilder();
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        final Persoon partner = mock(Persoon.class);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectsleutel)).thenReturn(persoon);
        when(persoon.isPersoonIngeschrevene()).thenReturn(true);
        when(persoon.isSamengesteldenaamAfgeleid()).thenReturn(false);
        when(persoon.getActuelePartner()).thenReturn(partner);
        when(partner.getNaamgebruik()).thenReturn(Naamgebruik.PARTNER);

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final GeslachtsnaamcomponentElement geslachtsnaamcomponent = builder.maakGeslachtsnaamcomponentElement("geslachtsnaam", null, null, null, null, "Stam");
        persoonParams.geslachtsnaamcomponenten(Collections.singletonList(geslachtsnaamcomponent));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persoon", objectsleutel, null, persoonParams);
        persoonElement.setVerzoekBericht(getBericht());
        controleerRegels(persoonElement.valideerInhoud(), Regel.R2738, Regel.R2736);
    }

    @Bedrijfsregel(Regel.R2736)
    @Bedrijfsregel(Regel.R2738)
    @Test
    public void testWijzigenGeslachtscomponent_PartnerNaamgebruikEigen() {
        final String objectsleutel = "123ab";
        final ElementBuilder builder = new ElementBuilder();
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        final Persoon partner = mock(Persoon.class);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectsleutel)).thenReturn(persoon);
        when(persoon.isPersoonIngeschrevene()).thenReturn(true);
        when(persoon.isSamengesteldenaamAfgeleid()).thenReturn(false);
        when(persoon.getActuelePartner()).thenReturn(partner);
        when(partner.getNaamgebruik()).thenReturn(Naamgebruik.EIGEN);

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final GeslachtsnaamcomponentElement geslachtsnaamcomponent = builder.maakGeslachtsnaamcomponentElement("geslachtsnaam", null, null, null, null, "Stam");
        persoonParams.geslachtsnaamcomponenten(Collections.singletonList(geslachtsnaamcomponent));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persoon", objectsleutel, null, persoonParams);
        persoonElement.setVerzoekBericht(getBericht());
        controleerRegels(persoonElement.valideerInhoud(), Regel.R2736);
    }

    @Bedrijfsregel(Regel.R2737)
    @Bedrijfsregel(Regel.R2738)
    @Test
    public void testWijzigenGeslachtscomponent_SamengesteldeNaamIsNietAfgeleid() {
        final String objectsleutel = "123ab";
        final ElementBuilder builder = new ElementBuilder();
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectsleutel)).thenReturn(persoon);
        when(persoon.isPersoonIngeschrevene()).thenReturn(true);
        when(persoon.isSamengesteldenaamAfgeleid()).thenReturn(true);
        when(persoon.isNaamgebruikAfgeleid()).thenReturn(false);
        when(persoon.getActuelePartner()).thenReturn(mock(Persoon.class));

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final GeslachtsnaamcomponentElement geslachtsnaamcomponent = builder.maakGeslachtsnaamcomponentElement("geslachtsnaam", null, null, null, null, "Stam");
        persoonParams.geslachtsnaamcomponenten(Collections.singletonList(geslachtsnaamcomponent));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persoon", objectsleutel, null, persoonParams);
        persoonElement.setVerzoekBericht(getBericht());
        controleerRegels(persoonElement.valideerInhoud(), Regel.R2737);
    }

    @Bedrijfsregel(Regel.R2737)
    @Test
    public void testWijzigenGeslachtscomponent_SamengesteldeNaamIsNietAfgeleidSoortnaamgebruikP() {
        final String objectsleutel = "123ab";
        final ElementBuilder builder = new ElementBuilder();
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectsleutel)).thenReturn(persoon);
        when(persoon.isPersoonIngeschrevene()).thenReturn(true);
        when(persoon.isSamengesteldenaamAfgeleid()).thenReturn(true);
        when(persoon.isNaamgebruikAfgeleid()).thenReturn(false);
        when(persoon.getActueelNaamGebruik()).thenReturn(Naamgebruik.PARTNER);
        when(persoon.getActuelePartner()).thenReturn(mock(Persoon.class));

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final GeslachtsnaamcomponentElement geslachtsnaamcomponent = builder.maakGeslachtsnaamcomponentElement("geslachtsnaam", null, null, null, null, "Stam");
        persoonParams.geslachtsnaamcomponenten(Collections.singletonList(geslachtsnaamcomponent));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persoon", objectsleutel, null, persoonParams);
        persoonElement.setVerzoekBericht(getBericht());
        controleerRegels(persoonElement.valideerInhoud());
    }


    @Bedrijfsregel(Regel.R2737)
    @Test
    public void testWijzigenGeslachtscomponent_SamengesteldeNaamIsAfgeleid() {
        final String objectsleutel = "123ab";
        final ElementBuilder builder = new ElementBuilder();
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectsleutel)).thenReturn(persoon);
        when(persoon.isPersoonIngeschrevene()).thenReturn(true);
        when(persoon.isSamengesteldenaamAfgeleid()).thenReturn(true);
        when(persoon.isNaamgebruikAfgeleid()).thenReturn(true);
        when(persoon.getActuelePartner()).thenReturn(mock(Persoon.class));

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final GeslachtsnaamcomponentElement geslachtsnaamcomponent = builder.maakGeslachtsnaamcomponentElement("geslachtsnaam", null, null, null, null, "Stam");
        persoonParams.geslachtsnaamcomponenten(Collections.singletonList(geslachtsnaamcomponent));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persoon", objectsleutel, null, persoonParams);
        persoonElement.setVerzoekBericht(getBericht());
        controleerRegels(persoonElement.valideerInhoud());
    }

    @Bedrijfsregel(Regel.R2739)
    @Test
    public void testWijzigenSamengesteldeNaam_PseudoPartner_NaamgebruikPartner() {
        final ElementBuilder builder = new ElementBuilder();
        final String pseudoSleutel = "pseudo";

        // Mocks
        final BijhoudingPersoon pseudoPartner = mock(BijhoudingPersoon.class);
        final BijhoudingPersoon eigenPersoon = mock(BijhoudingPersoon.class);
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final ActieElement actieElement = mock(ActieElement.class);

        final ElementBuilder.PersoonParameters pseudoParams = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters naamParams = new ElementBuilder.NaamParameters();
        naamParams.geslachtsnaamstam("Stam");
        naamParams.indicatieNamenreeks(false);
        final SamengesteldeNaamElement samengesteldeNaamElement = builder.maakSamengesteldeNaamElement("samengesteldeNaam", naamParams);
        pseudoParams.samengesteldeNaam(samengesteldeNaamElement);
        final PersoonElement pseudoPersoonElement = builder.maakPersoonGegevensElement("pseudo", pseudoSleutel, null, pseudoParams);
        pseudoPersoonElement.setVerzoekBericht(bericht);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.acties(Collections.singletonList(actieElement));
        ahParams.partijCode("052901");
        ahParams.soort(AdministratieveHandelingElementSoort.ERKENNING);
        final AdministratieveHandelingElement administratieveHandeling = builder.maakAdministratieveHandelingElement("ah", ahParams);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, pseudoSleutel)).thenReturn(pseudoPartner);
        when(pseudoPartner.isPersoonIngeschrevene()).thenReturn(false);
        when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandeling);
        when(actieElement.getHoofdPersonen()).thenReturn(Collections.singletonList(eigenPersoon));

        when(eigenPersoon.isNaamgebruikAfgeleid()).thenReturn(false);
        when(eigenPersoon.getActueelNaamGebruik()).thenReturn(Naamgebruik.PARTNER);
        controleerRegels(pseudoPersoonElement.valideer(), Regel.R2739);
    }

    @Bedrijfsregel(Regel.R2739)
    @Test
    public void testWijzigenSamengesteldeNaam_PseudoPartner_NaamgebruikEigen() {
        final ElementBuilder builder = new ElementBuilder();
        final String pseudoSleutel = "pseudo";

        // Mocks
        final BijhoudingPersoon pseudoPartner = mock(BijhoudingPersoon.class);
        final BijhoudingPersoon eigenPersoon = mock(BijhoudingPersoon.class);
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final ActieElement actieElement = mock(ActieElement.class);

        final ElementBuilder.PersoonParameters pseudoParams = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters naamParams = new ElementBuilder.NaamParameters();
        naamParams.geslachtsnaamstam("Stam");
        naamParams.indicatieNamenreeks(false);
        final SamengesteldeNaamElement samengesteldeNaamElement = builder.maakSamengesteldeNaamElement("samengesteldeNaam", naamParams);
        pseudoParams.samengesteldeNaam(samengesteldeNaamElement);
        final PersoonElement pseudoPersoonElement = builder.maakPersoonGegevensElement("pseudo", pseudoSleutel, null, pseudoParams);
        pseudoPersoonElement.setVerzoekBericht(bericht);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.acties(Collections.singletonList(actieElement));
        ahParams.partijCode("052901");
        ahParams.soort(AdministratieveHandelingElementSoort.ERKENNING);
        final AdministratieveHandelingElement administratieveHandeling = builder.maakAdministratieveHandelingElement("ah", ahParams);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, pseudoSleutel)).thenReturn(pseudoPartner);
        when(pseudoPartner.isPersoonIngeschrevene()).thenReturn(false);
        when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandeling);
        when(actieElement.getHoofdPersonen()).thenReturn(Collections.singletonList(eigenPersoon));

        when(eigenPersoon.isNaamgebruikAfgeleid()).thenReturn(false);
        when(eigenPersoon.getActueelNaamGebruik()).thenReturn(Naamgebruik.EIGEN);
        controleerRegels(pseudoPersoonElement.valideer());
    }


    @Bedrijfsregel(Regel.R2739)
    @Test
    public void testWijzigenSamengesteldeNaam_PseudoPartner_NaamgebruikAfgeleid() {
        final ElementBuilder builder = new ElementBuilder();
        final String pseudoSleutel = "pseudo";

        // Mocks
        final BijhoudingPersoon pseudoPartner = mock(BijhoudingPersoon.class);
        final BijhoudingPersoon eigenPersoon = mock(BijhoudingPersoon.class);
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final ActieElement actieElement = mock(ActieElement.class);

        final ElementBuilder.PersoonParameters pseudoParams = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters naamParams = new ElementBuilder.NaamParameters();
        naamParams.geslachtsnaamstam("Stam");
        naamParams.indicatieNamenreeks(false);
        final SamengesteldeNaamElement samengesteldeNaamElement = builder.maakSamengesteldeNaamElement("samengesteldeNaam", naamParams);
        pseudoParams.samengesteldeNaam(samengesteldeNaamElement);
        final PersoonElement pseudoPersoonElement = builder.maakPersoonGegevensElement("pseudo", pseudoSleutel, null, pseudoParams);
        pseudoPersoonElement.setVerzoekBericht(bericht);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.acties(Collections.singletonList(actieElement));
        ahParams.partijCode("052901");
        ahParams.soort(AdministratieveHandelingElementSoort.ERKENNING);
        final AdministratieveHandelingElement administratieveHandeling = builder.maakAdministratieveHandelingElement("ah", ahParams);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, pseudoSleutel)).thenReturn(pseudoPartner);
        when(pseudoPartner.isPersoonIngeschrevene()).thenReturn(false);
        when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandeling);
        when(actieElement.getHoofdPersonen()).thenReturn(Collections.singletonList(eigenPersoon));

        when(eigenPersoon.isNaamgebruikAfgeleid()).thenReturn(true);
        when(eigenPersoon.getActueelNaamGebruik()).thenReturn(Naamgebruik.PARTNER);
        controleerRegels(pseudoPersoonElement.valideer());
    }

    @Bedrijfsregel(Regel.R2739)
    @Test
    public void testGeenWijzigingenSamengesteldeNaam_PseudoPartner_NaamgebruikPartner() {
        final ElementBuilder builder = new ElementBuilder();
        final String pseudoSleutel = "pseudo";

        // Mocks
        final BijhoudingPersoon pseudoPartner = mock(BijhoudingPersoon.class);
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final ActieElement actieElement = mock(ActieElement.class);

        final ElementBuilder.PersoonParameters pseudoParams = new ElementBuilder.PersoonParameters();
        final ElementBuilder.GeboorteParameters geboorteParams = new ElementBuilder.GeboorteParameters();
        geboorteParams.datum(2016_01_01);
        final GeboorteElement geboorteElement = builder.maakGeboorteElement("geboorte", geboorteParams);
        pseudoParams.geboorte(geboorteElement);
        final PersoonElement pseudoPersoonElement = builder.maakPersoonGegevensElement("pseudo", pseudoSleutel, null, pseudoParams);
        pseudoPersoonElement.setVerzoekBericht(bericht);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.acties(Collections.singletonList(actieElement));
        ahParams.partijCode("052901");
        ahParams.soort(AdministratieveHandelingElementSoort.ERKENNING);
        final AdministratieveHandelingElement administratieveHandeling = builder.maakAdministratieveHandelingElement("ah", ahParams);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, pseudoSleutel)).thenReturn(pseudoPartner);
        when(pseudoPartner.isPersoonIngeschrevene()).thenReturn(false);
        when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandeling);

        controleerRegels(pseudoPersoonElement.valideer());
    }

    @Bedrijfsregel(Regel.R2739)
    @Test
    public void testWijzigenSamengesteldeNaam_PseudoPartnerInBericht_NaamgebruikPartner() {
        final ElementBuilder builder = new ElementBuilder();

        // Mocks
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final ActieElement actieElement = mock(ActieElement.class);

        final ElementBuilder.PersoonParameters pseudoParams = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters naamParams = new ElementBuilder.NaamParameters();
        naamParams.geslachtsnaamstam("Stam");
        naamParams.indicatieNamenreeks(false);
        final SamengesteldeNaamElement samengesteldeNaamElement = builder.maakSamengesteldeNaamElement("samengesteldeNaam", naamParams);
        pseudoParams.samengesteldeNaam(samengesteldeNaamElement);
        final PersoonElement pseudoPersoonElement = builder.maakPersoonGegevensElement("pseudo", null, null, pseudoParams);
        pseudoPersoonElement.setVerzoekBericht(bericht);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.acties(Collections.singletonList(actieElement));
        ahParams.partijCode("052901");
        ahParams.soort(AdministratieveHandelingElementSoort.ERKENNING);
        final AdministratieveHandelingElement administratieveHandeling = builder.maakAdministratieveHandelingElement("ah", ahParams);

        when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandeling);
        // Regel R1625 gaat af omdat niet alle verplichte groepen zijn toegevoegd
        controleerRegels(pseudoPersoonElement.valideer(), Regel.R1625);
    }

    private void testVoorNaamVolgnummer(final ElementBuilder builder, Integer... volgnummers) {
        final List<VoornaamElement> voornamen = new LinkedList<>();
        int i = 0;
        for (Integer volgnummer : volgnummers) {
            voornamen.add(builder.maakVoornaamElement("v" + i++, volgnummer, "1234"));
        }
        test(
                identificatienummersElement,
                samengesteldeNaamElement,
                null,
                geslachtsaanduidingElement,
                geslachtsnaamcomponentElement,
                null,
                null,
                voornamen, Regel.R1920);
    }

    @Bedrijfsregel(Regel.R1625)
    @Test
    public void verplichteGroepenBijNieuwPseudoPersoon() {
        objecttypeAttributen = new AbstractBmrGroep.AttributenBuilder().objecttype("Persoon").communicatieId("ci_persoon").build();
        test(
                identificatienummersElement,
                samengesteldeNaamElement,
                null,
                geslachtsaanduidingElement,
                null,
                null,
                null,
                Collections.singletonList(voornaamElement), Regel.R1625);
    }

    @Test
    public void namenreeksEnVoorvoegselGeslachtsnaamGeldig() {
        test(
                identificatienummersElement,
                samengesteldeNaamElement,
                geboorteElement,
                geslachtsaanduidingElement,
                geslachtsnaamcomponentElement,
                null,
                null, null);

        final GeslachtsnaamcomponentElement geslachtsnaamcomponentElementAangepast =
                new GeslachtsnaamcomponentElement(
                        geslachtsnaamcomponentElement.getAttributen(),
                        geslachtsnaamcomponentElement.getPredicaatCode(),
                        geslachtsnaamcomponentElement.getAdellijkeTitelCode(),
                        null,
                        null,
                        geslachtsnaamcomponentElement.getStam());

        final SamengesteldeNaamElement samengesteldeNaamElementAangepast =
                new SamengesteldeNaamElement(
                        samengesteldeNaamElement.getAttributen(),
                        BooleanElement.JA,
                        null,
                        null,
                        null,
                        null,
                        null,
                        samengesteldeNaamElement.getGeslachtsnaamstam());
        test(
                identificatienummersElement,
                samengesteldeNaamElementAangepast,
                geboorteElement,
                geslachtsaanduidingElement,
                geslachtsnaamcomponentElementAangepast,
                null,
                null, null);
    }

    @Test
    public void namenreeksEnVoorvoegselGeslachtsnaamOngeldig() {
        objecttypeAttributen.put(OBJECT_SLEUTEL_ATTRIBUUT.toString(), "1234");
        final SamengesteldeNaamElement samengesteldeNaamElementAangepast =
                new SamengesteldeNaamElement(
                        samengesteldeNaamElement.getAttributen(),
                        BooleanElement.JA,
                        null,
                        null,
                        null,
                        null,
                        null,
                        samengesteldeNaamElement.getGeslachtsnaamstam());
        test(
                identificatienummersElement,
                samengesteldeNaamElementAangepast,
                geboorteElement,
                geslachtsaanduidingElement,
                geslachtsnaamcomponentElement,
                null,
                null,
                null, Regel.R2173);

        final GeslachtsnaamcomponentElement geslachtsnaamcomponentElementAangepast =
                new GeslachtsnaamcomponentElement(
                        geslachtsnaamcomponentElement.getAttributen(),
                        geslachtsnaamcomponentElement.getPredicaatCode(),
                        geslachtsnaamcomponentElement.getAdellijkeTitelCode(),
                        new StringElement("van"),
                        new CharacterElement(' '),
                        geslachtsnaamcomponentElement.getStam());
        test(
                identificatienummersElement,
                samengesteldeNaamElementAangepast,
                geboorteElement,
                geslachtsaanduidingElement,
                geslachtsnaamcomponentElementAangepast,
                null,
                null,
                null, Regel.R2173);
    }

    @Test
    public void testGeslachtsnaamWijzigingPersoon() throws OngeldigeObjectSleutelException {
        test(null, samengesteldeNaamElement, geboorteElement, geslachtsaanduidingElement, geslachtsnaamcomponentElement, null,
                null, null);

        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));

        definieerWhenStapVoorPersoonOpvragen(persoon);
        test(null, null, null, null, geslachtsnaamcomponentElement, null,
                null, null);

        final Relatie huwelijk = new Relatie(SoortRelatie.HUWELIJK);
        final Betrokkenheid ikAlsPartner = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk);
        huwelijk.addBetrokkenheid(ikAlsPartner);
        persoon.addBetrokkenheid(ikAlsPartner);
        test(null, null, null, null, geslachtsnaamcomponentElement, null,
                null, null);

        final Relatie kindOuderRelatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid ikAlsKind = new Betrokkenheid(SoortBetrokkenheid.KIND, kindOuderRelatie);
        kindOuderRelatie.addBetrokkenheid(ikAlsKind);
        persoon.addBetrokkenheid(ikAlsKind);
        test(null, null, null, null, geslachtsnaamcomponentElement, null,
                null, null);

        final Relatie ouderKindRelatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid ikAlsOuder = new Betrokkenheid(SoortBetrokkenheid.OUDER, ouderKindRelatie);
        ouderKindRelatie.addBetrokkenheid(ikAlsOuder);
        persoon.addBetrokkenheid(ikAlsOuder);
        test(null, null, null, null, geslachtsnaamcomponentElement, null,
                null, null, Regel.R1572);
    }

    @Test
    public void testSamengesteldeNaamWijzigdEnNamenReeksJa() throws OngeldigeObjectSleutelException {
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));

        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = new PersoonSamengesteldeNaamHistorie(persoon, "Stam", true, true);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);

        definieerWhenStapVoorPersoonOpvragen(persoon);
        test(null, null, null, null, geslachtsnaamcomponentElement, null,
                null, null, Regel.R2132);

        samengesteldeNaamHistorie.setIndicatieAfgeleid(false);
        test(null, null, null, null, geslachtsnaamcomponentElement, null,
                null, null, Regel.R2736);

        samengesteldeNaamHistorie.setIndicatieAfgeleid(true);
        samengesteldeNaamHistorie.setIndicatieNamenreeks(false);
        test(null, null, null, null, geslachtsnaamcomponentElement, null,
                null, null);
    }

    @Test
    public void testNadereBijhoudingsAard() throws OngeldigeObjectSleutelException {
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        persoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.FOUT);
        PersoonBijhoudingHistorie his1 = new PersoonBijhoudingHistorie(persoon, PARTIJ_GRONINGEN, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.FOUT);
        persoon.getPersoonBijhoudingHistorieSet().add(his1);
        definieerWhenStapVoorPersoonOpvragen(persoon);
        test(null, null, null, null, null, null,
                null, null, Regel.R1580);

        persoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.GEWIST);
        persoon.getPersoonBijhoudingHistorieSet().clear();
        his1 = new PersoonBijhoudingHistorie(persoon, PARTIJ_GRONINGEN, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.GEWIST);
        persoon.getPersoonBijhoudingHistorieSet().add(his1);
        definieerWhenStapVoorPersoonOpvragen(persoon);
        test(null, null, null, null, null, null,
                null, null, Regel.R1580);

        persoon.setNadereBijhoudingsaard(NadereBijhoudingsaard.EMIGRATIE);
        persoon.getPersoonBijhoudingHistorieSet().clear();
        his1 = new PersoonBijhoudingHistorie(persoon, PARTIJ_GRONINGEN, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.EMIGRATIE);
        persoon.getPersoonBijhoudingHistorieSet().add(his1);
        definieerWhenStapVoorPersoonOpvragen(persoon);
        test(null, null, null, null, null, null,
                null, null);

        persoon.getPersoonBijhoudingHistorieSet().clear();
        definieerWhenStapVoorPersoonOpvragen(persoon);
        test(null, null, null, null, null, null,
                null, null);
    }

    @Test
    public void testPersoonIsGeblokkeerd() throws OngeldigeObjectSleutelException {
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        bericht = mock(BijhoudingVerzoekBericht.class);
        definieerWhenStapVoorPersoonOpvragen(persoon, bericht);
        when(bericht.getStuurgegevens()).thenReturn(getStuurgegevensElement());

        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.partijCode("052901");
        ahParams.soort(AdministratieveHandelingElementSoort.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND);
        final AdministratieveHandelingElement ahElement = builder.maakAdministratieveHandelingElement("ahElement", ahParams);
        when(bericht.getAdministratieveHandeling()).thenReturn(ahElement);

        when(bericht.isPrevalidatie()).thenReturn(true);
        when(getPersoonRepository().isPersoonGeblokkeerd(persoon)).thenReturn(false);
        test(null, null, null, null, null, null,
                null, null);
        when(getPersoonRepository().isPersoonGeblokkeerd(persoon)).thenReturn(true);
        test(null, null, null, null, null, null,
                null, null);

        when(bericht.isPrevalidatie()).thenReturn(false);
        when(getPersoonRepository().isPersoonGeblokkeerd(persoon)).thenReturn(false);
        test(null, null, null, null, null, null,
                null, null);
        when(getPersoonRepository().isPersoonGeblokkeerd(persoon)).thenReturn(true);
        test(null, null, null, null, null, null,
                null, null, Regel.R2098);
    }

    @Test
    public void testIsWijzigingVanGerelateerdeGegevens() {
        //setup
        final String administratienummer = "1234567890";
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(persoon.isPersoonIngeschrevene()).thenReturn(true);
        when(persoon.getAdministratienummer()).thenReturn(administratienummer);
        when(persoon.isSamengesteldenaamAfgeleid()).thenReturn(true);
        when(persoon.isNaamgebruikAfgeleid()).thenReturn(true);

        final Persoon gerelateerde = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.PSEUDO_PERSOON));
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final BetrokkenheidHistorie betrokkenheidHistorie = new BetrokkenheidHistorie(ouderBetrokkenheid);
        ouderBetrokkenheid.addBetrokkenheidHistorie(betrokkenheidHistorie);
        gerelateerde.addBetrokkenheid(ouderBetrokkenheid);

        final String objectsleutel = "sleutel persoon";
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectsleutel)).thenReturn(persoon);
        objecttypeAttributen = new AbstractBmrGroep.AttributenBuilder().objecttype("Persoon").communicatieId("ci_persoon").objectSleutel(objectsleutel).build();

        // execute en valideer
        test(identificatienummersElement, null, null, null, null, null, null, null);
        // setup vinden van gerelateerde pseudo personen
        when(getPersoonRepository().zoekGerelateerdePseudoPersonen(administratienummer, null)).thenReturn(Collections.singletonList(gerelateerde));

        // execute en valideer
        test(identificatienummersElement, null, null, null, null, null, null, null, Regel.R2437);
        test(null, samengesteldeNaamElement, null, null, null, null, null, null, Regel.R2437);
        test(null, null, geboorteElement, null, null, null, null, null, Regel.R2437);
        test(null, null, null, geslachtsaanduidingElement, null, null, null, null, Regel.R2437);
        //maak kind
        ouderBetrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
//        test(null, null, null, geslachtsaanduidingElement, null, null, null, null);
        test(null, null, null, null, geslachtsnaamcomponentElement, null, null, null, Regel.R2437);
        test(null, null, null, null, null, null, null, Collections.singletonList(voornaamElement), Regel.R2437);
        test(null, null, null, null, null, null, null, null);
    }

    @Test
    public void testHeeftPersoonEntiteit_Objectsleutel() {
        final ElementBuilder builder = new ElementBuilder();
        final String objectsleutel = "12";
        objecttypeAttributen.put(OBJECT_SLEUTEL_ATTRIBUUT.toString(), objectsleutel);
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement(objecttypeAttributen, params);
        persoonElement.setVerzoekBericht(bericht);
        assertTrue(persoonElement.heeftPersoonEntiteit());

        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectsleutel))
                .thenReturn(BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        assertNotNull(persoonElement.getPersoonEntiteit());
    }

    @Test
    public void testHeeftPersoonEntiteit_Nieuw_persoon() {
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        params.geslachtsnaamcomponenten(Collections.singletonList(geslachtsnaamcomponentElement));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement(objecttypeAttributen, params);
        persoonElement.setVerzoekBericht(bericht);
        assertTrue(persoonElement.heeftPersoonEntiteit());

        assertNotNull(persoonElement.getPersoonEntiteit());
    }

    @Test
    public void testHeeftPersoonEntiteit_Referentie() {
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        params.geslachtsnaamcomponenten(Collections.singletonList(geslachtsnaamcomponentElement));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement(objecttypeAttributen, params);
        persoonElement.setVerzoekBericht(bericht);

        objecttypeAttributen.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "ci_test_2");
        objecttypeAttributen.put(REFERENTIE_ID_ATTRIBUUT.toString(), "ci_test");
        final ElementBuilder.PersoonParameters refParams = new ElementBuilder.PersoonParameters();
        final PersoonGegevensElement referentieElement = builder.maakPersoonGegevensElement(objecttypeAttributen, refParams);
        referentieElement.setVerzoekBericht(bericht);
        final Map<String, BmrGroep> communicatieIdGroepMap = new HashMap<>();
        communicatieIdGroepMap.put("ci_test", persoonElement);
        referentieElement.initialiseer(communicatieIdGroepMap);

        assertTrue(referentieElement.heeftPersoonEntiteit());
        assertNotNull(referentieElement.getPersoonEntiteit());
        assertTrue(referentieElement.verwijstNaarBestaandEnJuisteType());
        assertTrue(persoonElement.verwijstNaarBestaandEnJuisteType());
    }

    @Test
    public void testHeeftPersoonEntiteit_PseudoPersoon() {

        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement(objecttypeAttributen, params);
        persoonElement.setVerzoekBericht(bericht);

        assertFalse(persoonElement.heeftPersoonEntiteit());
        assertNull(persoonElement.getPersoonEntiteit());
    }

    @Test
    public void testVerwijstNaarBestaandEnJuisteType_NOK() {
        final ElementBuilder builder = new ElementBuilder();
        final StringElement stringElement = new StringElement("");
        final BronElement
                bronElement =
                builder.maakBronElement("ci_test_2", new DocumentElement(objecttypeAttributen, stringElement, null, null, stringElement));

        objecttypeAttributen.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "ci_test_3");
        objecttypeAttributen.put(REFERENTIE_ID_ATTRIBUUT.toString(), "ci_test_2");
        final ElementBuilder.PersoonParameters refParams = new ElementBuilder.PersoonParameters();
        final PersoonGegevensElement referentieElement = builder.maakPersoonGegevensElement(objecttypeAttributen, refParams);
        referentieElement.setVerzoekBericht(bericht);
        final Map<String, BmrGroep> communicatieIdGroepMap = new HashMap<>();
        communicatieIdGroepMap.put("ci_test_2", bronElement);
        referentieElement.initialiseer(communicatieIdGroepMap);

        assertFalse(referentieElement.verwijstNaarBestaandEnJuisteType());
    }

    private void definieerWhenStapVoorPersoonOpvragen(final BijhoudingPersoon persoon) throws OngeldigeObjectSleutelException {
        definieerWhenStapVoorPersoonOpvragen(persoon, null);
    }

    private void definieerWhenStapVoorPersoonOpvragen(final BijhoudingPersoon persoon, final BijhoudingVerzoekBericht bericht)
            throws OngeldigeObjectSleutelException {
        final String objectSleutel = "s532era3s==";
        if (persoon != null) {
            final PersoonAfgeleidAdministratiefHistorie afgeleidAdministratiefHistorie =
                    new PersoonAfgeleidAdministratiefHistorie(Short.parseShort("1"), persoon, maakAdministratieveHandeling(), Timestamp.from(Instant.now()));
            persoon.addPersoonAfgeleidAdministratiefHistorie(afgeleidAdministratiefHistorie);
            final PersoonNaamgebruikHistorie naamgebruikHistorie = new PersoonNaamgebruikHistorie(persoon, "true", true, Naamgebruik.EIGEN);
            persoon.addPersoonNaamgebruikHistorie(naamgebruikHistorie);

            final PersoonSamengesteldeNaamHistorie persSamenHistSet = new PersoonSamengesteldeNaamHistorie(persoon, "stamie", false, false);
            persSamenHistSet.setIndicatieAfgeleid(true);
            persoon.addPersoonSamengesteldeNaamHistorie(persSamenHistSet);
            persoon.setIndicatieNaamgebruikAfgeleid(true);
            persoon.setIndicatieAfgeleid(true);
        }

        objecttypeAttributen.put(OBJECT_SLEUTEL_ATTRIBUUT.toString(), objectSleutel);
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectSleutel)).thenReturn(persoon);
        if (bericht != null) {
            when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectSleutel)).thenReturn(persoon);
        }
    }

    private AdministratieveHandeling maakAdministratieveHandeling() {
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(PARTIJ_GRONINGEN, SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, Timestamp.from(Instant.now()));
        administratieveHandeling.setId(1L);
        return administratieveHandeling;
    }

    @Test
    public void testControleerVerstrekkingsbeperking() throws OngeldigeObjectSleutelException {
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        definieerWhenStapVoorPersoonOpvragen(persoon);
        final ElementBuilder builder = new ElementBuilder();
        final IndicatieElement
                indicatieElement =
                builder.maakVolledigeVerstrekkingsbeperkingIndicatieElement("com_ind_vers",
                        new ElementBuilder.IndicatieElementParameters().heeftIndicatie(Boolean.TRUE));
        final VerstrekkingsbeperkingElement verstrekkingsbeperkingElement = builder.maakVerstrekkingsbeperkingElement("com_vol_vers", new ElementBuilder
                .VerstrekkingsbeperkingParameters().omschrijvingDerde("oms").gemeenteVerordeningPartijCodes("0017"));
        verstrekkingsbeperkingElement.setVerzoekBericht(getUitgebreidBericht());
        test(null, null, null, null, null, indicatieElement, verstrekkingsbeperkingElement, null, Regel.R1909);
    }

    private void test(
            final IdentificatienummersElement identificatienummersElement,
            final SamengesteldeNaamElement samengesteldeNaamElement,
            final GeboorteElement geboorteElement,
            final GeslachtsaanduidingElement geslachtsaanduidingElement,
            final GeslachtsnaamcomponentElement geslachtsnaamcomponentElement,
            final IndicatieElement indicatieElement,
            final VerstrekkingsbeperkingElement verstrekkingsbeperkingElement,
            List<VoornaamElement> voornaamElementen, final Regel... regels) {

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234"))
                .thenReturn(BijhoudingPersoon.decorate(new Persoon(SoortPersoon.PSEUDO_PERSOON)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "objSleutel"))
                .thenReturn(BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));

        final List<GeslachtsnaamcomponentElement> geslachtsnaamcomponenten;
        if (geslachtsnaamcomponentElement != null) {
            geslachtsnaamcomponenten = Collections.singletonList(geslachtsnaamcomponentElement);
        } else {
            geslachtsnaamcomponenten = Collections.emptyList();
        }
        final List<VoornaamElement> voornamen = new LinkedList<>();

        if (voornaamElementen != null && voornaamElementen.size() > 0) {
            voornamen.addAll(voornaamElementen);
        }
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        params.identificatienummers(identificatienummersElement);
        params.samengesteldeNaam(samengesteldeNaamElement);
        params.geboorte(geboorteElement);
        params.geslachtsaanduiding(geslachtsaanduidingElement);
        params.geslachtsnaamcomponenten(geslachtsnaamcomponenten);
        params.voornamen(voornamen);
        if (indicatieElement != null) {
            params.indicaties(Collections.singletonList(indicatieElement));
        }
        if (verstrekkingsbeperkingElement != null) {
            params.verstrekkingsbeperking(Collections.singletonList(verstrekkingsbeperkingElement));
        }
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement(objecttypeAttributen, params);
        persoonElement.setVerzoekBericht(bericht);
        controleerRegels(persoonElement.valideer(), regels);
    }
}
