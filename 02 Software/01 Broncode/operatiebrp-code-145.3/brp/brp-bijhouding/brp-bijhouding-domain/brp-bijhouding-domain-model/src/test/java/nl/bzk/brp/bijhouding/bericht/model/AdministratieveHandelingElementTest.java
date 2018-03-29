/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.AbstractNaamTest.SCHEIDINGSTEKEN_ELEMENT;
import static nl.bzk.brp.bijhouding.bericht.model.AbstractNaamTest.VOORVOEGSEL_ELEMENT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECT_SLEUTEL_ATTRIBUUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class AdministratieveHandelingElementTest extends AbstractHuwelijkInNederlandTestBericht {
    private static final Integer[] INGEZETENE_PERIODE_1 = {20111201, 20120101};
    private static final Integer[] INGEZETENE_PERIODE_2 = {20160101};
    public static final String PARTIJ_CODE = "053001";
    private static final Partij PARTIJ = new Partij("partij", PARTIJ_CODE);
    private static final String SLEUTEL_HUWLIJK_P1 = "212121";
    private static final String SLEUTEL_PERSOON_REGISTRATIE_NAAMGEBRUIK = "123456789987654321";
    private static final String ID_PERSOON_REGISTRATIE_NAAMGEBRUIK = "1234";
    private BijhoudingVerzoekBericht bericht;
    private AdministratieveHandelingElement administratieveHandelingElement;
    private final ElementBuilder eBuilder = new ElementBuilder();

    private BijhoudingVerzoekBericht mb;

    @Before
    public void setupAdministratieveHandelingElementTest() throws OngeldigeObjectSleutelException {
        bericht = getSuccesHuwelijkInNederlandBericht();
        administratieveHandelingElement = bericht.getAdministratieveHandeling();

        Partij partij = new Partij("partij", PARTIJ_CODE);
        partij.addPartijRol(new PartijRol(partij, Rol.BIJHOUDINGSORGAAN_COLLEGE));
        Gemeente gemeente = new Gemeente(Short.parseShort("533"), "Hellevoetsluis", "0530", partij);

        when(getDynamischeStamtabelRepository().getPartijByCode(PARTIJ_CODE)).thenReturn(partij);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(PARTIJ_CODE)).thenReturn(gemeente);

        final BijhoudingPersoon persoonIngezetenne = createPersoon(SLEUTEL_HUWLIJK_P1, false, true);
        final BijhoudingPersoon persoonIngezetenne2 = createPersoon(ID_PERSOON_REGISTRATIE_NAAMGEBRUIK, false, false);
        persoonIngezetenne.addBetrokkenheid(
                BijhoudingBetrokkenheid.decorate(
                        new Betrokkenheid(SoortBetrokkenheid.PARTNER, BijhoudingRelatie.decorate(new Relatie(SoortRelatie.HUWELIJK)))));

        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_HUWLIJK_P1)).thenReturn(
                BijhoudingPersoon.decorate(persoonIngezetenne));
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_PERSOON_REGISTRATIE_NAAMGEBRUIK)).thenReturn(
                BijhoudingPersoon.decorate(persoonIngezetenne2));

        mb = mock(BijhoudingVerzoekBericht.class);
        final ElementBuilder.StuurgegevensParameters zParams = new ElementBuilder.StuurgegevensParameters();
        zParams.zendendePartij(PARTIJ_CODE);
        zParams.zendendeSysteem("brp");
        zParams.referentienummer("1234454675687");
        zParams.tijdstipVerzending("2016-05-10T15:16:40.829+02:00");
        when(mb.getStuurgegevens()).thenReturn(eBuilder.maakStuurgegevensElement("stuur", zParams));
        when(mb.getZendendePartij()).thenReturn(partij);

        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode("0001")).thenReturn(new Nationaliteit("land 0001",
                "0001"));
        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode("0026")).thenReturn(new Nationaliteit("land 0026",
                "0026"));
        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode("0027")).thenReturn(new Nationaliteit("land 0027",
                "0027"));
    }


    @Bedrijfsregel(Regel.R2784)
    @Test
    /*xsd voorkomt dit*/
    public void testDagAndersHuwelijk() {
        final ElementBuilder.AdministratieveHandelingParameters props = new ElementBuilder.AdministratieveHandelingParameters();
        props.soort(AdministratieveHandelingElementSoort.REGISTRATIE_NIET_AANGETROFFEN_OP_ADRES);
        props.partijCode("1234");
        final List<ActieElement> acties = new LinkedList<>();
        acties.add(createMockActie(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS_GERELATEERDE, 20101010, "1"));
        acties.add(createMockActie(SoortActie.REGISTRATIE_SAMENGESTELDE_NAAM_GERELATEERDE, 20101011, "2"));
        props.acties(acties);
        AdministratieveHandelingElement ah = eBuilder.maakAdministratieveHandelingElement("2784", props);
        final List<MeldingElement> meldingen = new LinkedList<>();
        ah.controleerDatumAanvangActieMetPeilDatum(meldingen);
        controleerRegels(meldingen);
    }

    @Bedrijfsregel(Regel.R2784)
    @Test
    public void testDagAndersWPH() {
        final ElementBuilder.AdministratieveHandelingParameters props = new ElementBuilder.AdministratieveHandelingParameters();
        props.soort(AdministratieveHandelingElementSoort.WIJZIGING_PARTNERGEGEVENS_HUWELIJK);
        props.partijCode("1234");
        final List<ActieElement> acties = new LinkedList<>();
        acties.add(createMockActie(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS_GERELATEERDE, 20101010, "1"));
        acties.add(createMockActie(SoortActie.REGISTRATIE_SAMENGESTELDE_NAAM_GERELATEERDE, 20101011, "2"));
        props.acties(acties);
        AdministratieveHandelingElement ah = eBuilder.maakAdministratieveHandelingElement("2784", props);
        final List<MeldingElement> meldingen = new LinkedList<>();
        ah.controleerDatumAanvangActieMetPeilDatum(meldingen);
        controleerRegels(meldingen, Regel.R2784);
    }

    @Bedrijfsregel(Regel.R2784)
    @Test
    public void testDagAndersWPGP() {
        final ElementBuilder.AdministratieveHandelingParameters props = new ElementBuilder.AdministratieveHandelingParameters();
        props.soort(AdministratieveHandelingElementSoort.WIJZIGING_PARTNERGEGEVENS_GEREGISTREERD_PARTNERSCHAP);
        props.partijCode("1234");
        final List<ActieElement> acties = new LinkedList<>();
        acties.add(createMockActie(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS_GERELATEERDE, 20101010, "1"));
        acties.add(createMockActie(SoortActie.REGISTRATIE_SAMENGESTELDE_NAAM_GERELATEERDE, 20101011, "2"));
        props.acties(acties);
        AdministratieveHandelingElement ah = eBuilder.maakAdministratieveHandelingElement("2784", props);
        final List<MeldingElement> meldingen = new LinkedList<>();
        ah.controleerDatumAanvangActieMetPeilDatum(meldingen);
        controleerRegels(meldingen, Regel.R2784);
    }

    @Bedrijfsregel(Regel.R2784)
    @Test
    public void testDagAndersWPHGeboorte() {
        final ElementBuilder.AdministratieveHandelingParameters props = new ElementBuilder.AdministratieveHandelingParameters();
        props.soort(AdministratieveHandelingElementSoort.WIJZIGING_PARTNERGEGEVENS_HUWELIJK);
        props.partijCode("1234");
        final List<ActieElement> acties = new LinkedList<>();
        acties.add(createMockActie(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS_GERELATEERDE, 20101010, "1"));
        acties.add(createMockActie(SoortActie.REGISTRATIE_GEBOORTE_GERELATEERDE, 20101011, "2"));
        props.acties(acties);
        AdministratieveHandelingElement ah = eBuilder.maakAdministratieveHandelingElement("2784", props);
        final List<MeldingElement> meldingen = new LinkedList<>();
        ah.controleerDatumAanvangActieMetPeilDatum(meldingen);
        controleerRegels(meldingen);
    }

    public ActieElement createMockActie(final SoortActie soort, final int dag, final String id) {
        ActieElement actie1 = mock(ActieElement.class);
        when(actie1.getSoortActie()).thenReturn(soort);
        when(actie1.getCommunicatieId()).thenReturn(id);

        when(actie1.getDatumAanvangGeldigheid()).thenReturn(new DatumElement(dag));
        when(actie1.getPeilDatum()).thenReturn(new DatumElement(dag));
        return actie1;
    }


    @Test
    @Bedrijfsregel(Regel.R2577)
    public void testGeenActies() {
        final ElementBuilder.AdministratieveHandelingParameters
                para =
                new ElementBuilder.AdministratieveHandelingParameters().soort(AdministratieveHandelingElementSoort.VOLTREKKING_HUWELIJK_IN_NEDERLAND)
                        .partijCode(PARTIJ_CODE).toelichtingOntlening("Test toelichting op de ontlening");
        final AdministratieveHandelingElement administratieveHandelingElement = eBuilder.maakAdministratieveHandelingElement("ah", para);
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2577, meldingen.get(0).getRegel());

    }

    @Test
    public void testAdministratieveHandelingHPDatumAanvangNietCorrect() {
        final BijhoudingVerzoekBericht foutBericht = VerzoekBerichtBuilder.maakSuccesHuwelijkInNederlandBerichtMetFoutInAanvangGeldheid();
        foutBericht.setObjectSleutelIndex(getObjectSleutelIndex());
        final List<MeldingElement>
                result =
                foutBericht.getAdministratieveHandeling().valideerInhoud();
        assertEquals(1, result.size());
        assertEquals(Regel.R1882, result.get(0).getRegel());
        assertEquals("CI_actie_reg_geslachtsnaam", result.get(0).getReferentieId());
    }

    @Test
    public void testAdministratieveHandelingGPDatumAanvangNietCorrect() {
        final BijhoudingVerzoekBericht foutBericht = VerzoekBerichtBuilder.maakSuccesHuwelijkInNederlandBerichtMetFoutInAanvangGeldheid();
        foutBericht.setObjectSleutelIndex(getObjectSleutelIndex());
        final AdministratieveHandelingElement administratieveHandelingElementAangepast =
                new AdministratieveHandelingElement(
                        foutBericht.getAdministratieveHandeling().getAttributen(),
                        AdministratieveHandelingElementSoort.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                        foutBericht.getAdministratieveHandeling().getPartijCode(),
                        foutBericht.getAdministratieveHandeling().getToelichtingOntlening(),
                        foutBericht.getAdministratieveHandeling().getBezienVanuitPersonen(),
                        foutBericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen(),
                        foutBericht.getAdministratieveHandeling().getBronnen(),
                        foutBericht.getAdministratieveHandeling().getActies());
        administratieveHandelingElementAangepast.setVerzoekBericht(foutBericht);
        final List<MeldingElement> result = administratieveHandelingElementAangepast.valideerInhoud();
        assertEquals(1, result.size());
        assertEquals(Regel.R1882, result.get(0).getRegel());
        assertEquals("CI_actie_reg_geslachtsnaam", result.get(0).getReferentieId());
    }

    @Test
    public void testAdministratieveHandelingAnderSoortCorrect() {
        final List<ActieElement> acties = new ArrayList<>();
        acties.add(administratieveHandelingElement.getActies().get(1));
        final AdministratieveHandelingElement administratieveHandelingElementAangepast =
                new AdministratieveHandelingElement(
                        administratieveHandelingElement.getAttributen(),
                        AdministratieveHandelingElementSoort.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND,
                        administratieveHandelingElement.getPartijCode(),
                        administratieveHandelingElement.getToelichtingOntlening(),
                        administratieveHandelingElement.getBezienVanuitPersonen(),
                        administratieveHandelingElement.getGedeblokkeerdeMeldingen(),
                        administratieveHandelingElement.getBronnen(),
                        acties);
        administratieveHandelingElementAangepast.setVerzoekBericht(bericht);

        assertTrue(bericht.laadEntiteitenVoorObjectSleutels().isEmpty());
        final List<MeldingElement> result = administratieveHandelingElementAangepast.valideerInhoud();
        assertEquals(0, result.size());
    }

    @Test
    public void testAdministratieveHandelingeindeGPDatumAnders() {
        final ElementBuilder elementBuilder = new ElementBuilder();

        when(mb.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(BijhoudingRelatie.decorate(new Relatie(SoortRelatie
                .GEREGISTREERD_PARTNERSCHAP)));
        when(mb.getStuurgegevens()).thenReturn(elementBuilder
                .maakStuurgegevensElement("st", new ElementBuilder.StuurgegevensParameters().zendendePartij(PARTIJ_CODE).zendendeSysteem("BZM Leverancier A")
                        .referentienummer("88409eeb-1aa5-43fc-8614-43055123a165").tijdstipVerzending("2016-03-21T09:32:03.234+02:00")));
        final RegistratieAanvangHuwelijkActieElement bActie = (RegistratieAanvangHuwelijkActieElement) administratieveHandelingElement.getActies().get(0);
        final List<ActieElement> acties = new ArrayList<>();
        final RelatieGroepElement relatie = new RelatieGroepElement(bActie.getAttributen(), null, null, null,
                new CharacterElement('A'), new DatumElement(20160324), new StringElement("dummy"), new StringElement("dummy"),
                null, null, null, null, null,
                null, null, null);
        relatie.setVerzoekBericht(mb);
        final GeregistreerdPartnerschapElement gp = new GeregistreerdPartnerschapElement(bActie.getAttributen(), relatie, null);
        gp.setVerzoekBericht(mb);
        RegistratieEindeGeregistreerdPartnerschapActieElement egp =
                new RegistratieEindeGeregistreerdPartnerschapActieElement(bActie.getAttributen(), new DatumElement(20160323), null, null, gp);
        egp.setVerzoekBericht(mb);
        acties.add(egp);
        final AdministratieveHandelingElement administratieveHandelingElementAangepast =
                new AdministratieveHandelingElement(
                        administratieveHandelingElement.getAttributen(),
                        AdministratieveHandelingElementSoort.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                        administratieveHandelingElement.getPartijCode(),
                        administratieveHandelingElement.getToelichtingOntlening(),
                        administratieveHandelingElement.getBezienVanuitPersonen(),
                        administratieveHandelingElement.getGedeblokkeerdeMeldingen(),
                        administratieveHandelingElement.getBronnen(),
                        acties);
        administratieveHandelingElementAangepast.setVerzoekBericht(mb);

        when(mb.getAdministratieveHandeling()).thenReturn(administratieveHandelingElementAangepast);

        final List<MeldingElement> result = administratieveHandelingElementAangepast.valideerInhoud();
        assertEquals(1, result.size());
        assertEquals(Regel.R1883, result.get(0).getRegel());
    }

    @Test
    public void testAdministratieveHandelingeindeGPCorrect() {
        final ElementBuilder elementBuilder = new ElementBuilder();
        when(mb.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(BijhoudingRelatie.decorate(new Relatie(SoortRelatie
                .GEREGISTREERD_PARTNERSCHAP)));
        when(mb.getStuurgegevens()).thenReturn(elementBuilder
                .maakStuurgegevensElement("st", new ElementBuilder.StuurgegevensParameters().zendendePartij(PARTIJ_CODE).zendendeSysteem("BZM Leverancier A")
                        .referentienummer("88409eeb-1aa5-43fc-8614-43055123a165").tijdstipVerzending("2016-03-21T09:32:03.234+02:00")));
        final RegistratieAanvangHuwelijkActieElement bActie = (RegistratieAanvangHuwelijkActieElement) administratieveHandelingElement.getActies().get(0);
        final List<ActieElement> acties = new ArrayList<>();
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        bActie.getAttributen(),
                        null,
                        null,
                        null,
                        new CharacterElement('A'),
                        new DatumElement(20160323),
                        new StringElement("dummy"),
                        new StringElement("dummy"),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(mb);
        final GeregistreerdPartnerschapElement gp =
                new GeregistreerdPartnerschapElement(bActie.getAttributen(), relatie, bActie.getHuwelijk().getBetrokkenheden());
        gp.setVerzoekBericht(mb);
        RegistratieEindeGeregistreerdPartnerschapActieElement egp =
                new RegistratieEindeGeregistreerdPartnerschapActieElement(bActie.getAttributen(), new DatumElement(20160323), null, null, gp);
        egp.setVerzoekBericht(mb);
        acties.add(egp);
        final AdministratieveHandelingElement administratieveHandelingElementAangepast =
                new AdministratieveHandelingElement(
                        administratieveHandelingElement.getAttributen(),
                        AdministratieveHandelingElementSoort.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                        administratieveHandelingElement.getPartijCode(),
                        administratieveHandelingElement.getToelichtingOntlening(),
                        administratieveHandelingElement.getBezienVanuitPersonen(),
                        administratieveHandelingElement.getGedeblokkeerdeMeldingen(),
                        administratieveHandelingElement.getBronnen(),
                        acties);
        administratieveHandelingElementAangepast.setVerzoekBericht(mb);
        when(mb.getAdministratieveHandeling()).thenReturn(administratieveHandelingElementAangepast);

        assertTrue(bericht.laadEntiteitenVoorObjectSleutels().isEmpty());
        final List<MeldingElement> result = administratieveHandelingElementAangepast.valideerInhoud();
        assertEquals(0, result.size());
    }

    @Test
    public void testAdministratieveHandelingDatumCorrect() {
        final RegistratieAanvangHuwelijkActieElement registratieAanvangHuwelijkGPActieElement =
                (RegistratieAanvangHuwelijkActieElement) administratieveHandelingElement.getActies().get(0);
        final RegistratieAanvangHuwelijkActieElement registratieAanvangHuwelijkGPActieElementAangepast =
                new RegistratieAanvangHuwelijkActieElement(
                        registratieAanvangHuwelijkGPActieElement.getAttributen(),
                        registratieAanvangHuwelijkGPActieElement.getHuwelijk().getRelatieGroep().getDatumAanvang(),
                        null,
                        registratieAanvangHuwelijkGPActieElement.getBronReferenties(),
                        registratieAanvangHuwelijkGPActieElement.getHuwelijk());
        final List<ActieElement> acties = new ArrayList<>();
        acties.add(registratieAanvangHuwelijkGPActieElementAangepast);
        final AdministratieveHandelingElement administratieveHandelingElementAangepast =
                new AdministratieveHandelingElement(
                        administratieveHandelingElement.getAttributen(),
                        AdministratieveHandelingElementSoort.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                        administratieveHandelingElement.getPartijCode(),
                        administratieveHandelingElement.getToelichtingOntlening(),
                        administratieveHandelingElement.getBezienVanuitPersonen(),
                        administratieveHandelingElement.getGedeblokkeerdeMeldingen(),
                        administratieveHandelingElement.getBronnen(),
                        acties);
        administratieveHandelingElementAangepast.setVerzoekBericht(bericht);

        assertTrue(bericht.laadEntiteitenVoorObjectSleutels().isEmpty());
        final List<MeldingElement> result = administratieveHandelingElementAangepast.valideerInhoud();
        assertEquals(0, result.size());
    }

    @Test
    public void tesGemeenteAanvangEnPartijAkteIncorrectORANJE5314() {
        final RegistratieAanvangHuwelijkActieElement registratieAanvangHuwelijkGPActieElement =
                (RegistratieAanvangHuwelijkActieElement) administratieveHandelingElement.getActies().get(0);
        final HuwelijkElement huwelijkElement = registratieAanvangHuwelijkGPActieElement.getHuwelijk();
        final RelatieGroepElement relatieGroepElementAangepast =
                new RelatieGroepElement(
                        huwelijkElement.getRelatieGroep().getAttributen(),
                        huwelijkElement.getRelatieGroep().getDatumAanvang(),
                        null,
                        huwelijkElement.getRelatieGroep().getWoonplaatsnaamAanvang(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        final HuwelijkElement huwelijkElementAangepast =
                new HuwelijkElement(huwelijkElement.getAttributen(), relatieGroepElementAangepast, huwelijkElement.getBetrokkenheden());

        final RegistratieAanvangHuwelijkActieElement registratieAanvangHuwelijkGPActieElementAangepast =
                new RegistratieAanvangHuwelijkActieElement(
                        registratieAanvangHuwelijkGPActieElement.getAttributen(),
                        registratieAanvangHuwelijkGPActieElement.getHuwelijk().getRelatieGroep().getDatumAanvang(),
                        null,
                        registratieAanvangHuwelijkGPActieElement.getBronReferenties(),
                        huwelijkElementAangepast);
        final List<ActieElement> acties = new ArrayList<>();
        acties.add(registratieAanvangHuwelijkGPActieElementAangepast);
        final AdministratieveHandelingElement administratieveHandelingElementAangepast =
                new AdministratieveHandelingElement(
                        administratieveHandelingElement.getAttributen(),
                        administratieveHandelingElement.getSoort(),
                        administratieveHandelingElement.getPartijCode(),
                        administratieveHandelingElement.getToelichtingOntlening(),
                        administratieveHandelingElement.getBezienVanuitPersonen(),
                        administratieveHandelingElement.getGedeblokkeerdeMeldingen(),
                        administratieveHandelingElement.getBronnen(),
                        acties);
        administratieveHandelingElementAangepast.setVerzoekBericht(bericht);
        assertTrue(bericht.laadEntiteitenVoorObjectSleutels().isEmpty());
        final List<MeldingElement> result = administratieveHandelingElementAangepast.valideerInhoud();
        assertEquals(0, result.size());
    }

    @Test
    public void r1836_ZelfdePersonenInNevenActieAlsInHoofdActie() throws OngeldigeObjectSleutelException {
        final BijhoudingPersoon p1 = createPersoon("212121", true, true);
        p1.addPersoonNationaliteit(createOnbekendeNationaliteit(p1));
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "212121")).thenReturn(p1);
        final BijhoudingPersoon p2 = createPersoon("212122", true, true);
        p2.addPersoonNationaliteit(createOnbekendeNationaliteit(p2));
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "212122")).thenReturn(p2);
        final BijhoudingPersoon p3 = createPersoon("212123", true, true);
        p3.addPersoonNationaliteit(createOnbekendeNationaliteit(p3));
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "212123")).thenReturn(p3);
        final BijhoudingVerzoekBericht bericht = getSuccesHuwelijkInNederlandBericht();
        assertTrue(bericht.laadEntiteitenVoorObjectSleutels().isEmpty());
        final List<MeldingElement> meldingen = bericht.getAdministratieveHandeling().valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void r1836_AnderePersonenInNevenActieAlsInHoofdActie() throws OngeldigeObjectSleutelException {
        final BijhoudingPersoon p3 = createPersoon("212123", true, true);
        p3.addPersoonNationaliteit(createOnbekendeNationaliteit(p3));
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "212123")).thenReturn(p3);
        final BijhoudingVerzoekBericht bericht = VerzoekBerichtBuilder.maakSuccesHuwelijkInNederlandBerichtTweeIngeschrevenen("212123");
        bericht.setObjectSleutelIndex(getObjectSleutelIndex());
        final List<MeldingElement> meldingen = bericht.getAdministratieveHandeling().valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1836.getCode(), meldingen.get(0).getRegel().getCode());
    }

    @Test
    public void r1836_NietIngeschrevenePersoonInHoofdActie() throws OngeldigeObjectSleutelException {
        final BijhoudingVerzoekBericht bericht = VerzoekBerichtBuilder.maakSuccesHuwelijkInNederlandBericht();
        bericht.setObjectSleutelIndex(getObjectSleutelIndex());
        final List<MeldingElement> meldingen = bericht.getAdministratieveHandeling().valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void r1836_SleutelServiceKanPersoonIdsNietBepalen() throws OngeldigeObjectSleutelException {
        final BijhoudingVerzoekBericht bericht = getSuccesHuwelijkInNederlandBericht();
        assertTrue(bericht.laadEntiteitenVoorObjectSleutels().isEmpty());
        final List<MeldingElement> meldingen = bericht.getAdministratieveHandeling().valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2536)
    @Test
    public void r2536_ORANJE_4722_EenPersoonNietIngezeteneEenWelIngezetene() throws OngeldigeObjectSleutelException {
        final BijhoudingPersoon persoonIngezetene = createPersoon("212121", true, true);
        persoonIngezetene.addBetrokkenheid(new Betrokkenheid(SoortBetrokkenheid.PARTNER, new Relatie(SoortRelatie.HUWELIJK)));
        persoonIngezetene.addPersoonNationaliteit(createOnbekendeNationaliteit(persoonIngezetene));
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "212121")).thenReturn(persoonIngezetene);
        final BijhoudingVerzoekBericht bericht = getSuccesHuwelijkInNederlandBericht();
        assertTrue(bericht.laadEntiteitenVoorObjectSleutels().isEmpty());
        final List<MeldingElement> meldingen = bericht.getAdministratieveHandeling().valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2536)
    @Test
    public void r2536_BeidePersonenNietIngezetene() throws OngeldigeObjectSleutelException {
        final BijhoudingPersoon persoonIngezetene = mock(BijhoudingPersoon.class);
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "212121")).thenReturn(persoonIngezetene);

        when(persoonIngezetene.isPersoonIngeschrevene()).thenReturn(true);
        when(persoonIngezetene.getId()).thenReturn(212121L);
        when(persoonIngezetene.getPersoonBijhoudingHistorieSet())
                .thenReturn(Collections.singleton(new PersoonBijhoudingHistorie(persoonIngezetene, getPartijGemeenteHellevoetsluis(),
                        Bijhoudingsaard.NIET_INGEZETENE, NadereBijhoudingsaard.EMIGRATIE)));
        final BijhoudingVerzoekBericht bericht = getSuccesHuwelijkInNederlandBericht();
        assertTrue(bericht.laadEntiteitenVoorObjectSleutels().isEmpty());
        final List<MeldingElement> meldingen = bericht.getAdministratieveHandeling().valideerInhoud();
        controleerRegels(meldingen, Regel.R2536);
    }

    @Test
    public void persoonInBZVUMaarActieGeenInvloedOpGerelateerdeMaarWelOpHoofdPersoon() throws OngeldigeObjectSleutelException {
        final int persoonId1 = 99901;

        final String objectSleutel1 = "abcd1";

        final BijhoudingPersoon persoon = createPersoon(persoonId1, true, false);
        persoon.addPersoonNationaliteit(createNederlandseNationaliteit(persoon));
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectSleutel1)).thenReturn(persoon);

        final PersoonGegevensElement bezienVanuitPersoonElement1 = maakBezienVanuitPersoonElement("comId.bzvu.persoon1", objectSleutel1);
        bezienVanuitPersoonElement1.setVerzoekBericht(bericht);

        final AdministratieveHandelingElement administratieveHandelingElementAangepast =
                new AdministratieveHandelingElement(
                        administratieveHandelingElement.getAttributen(),
                        administratieveHandelingElement.getSoort(),
                        administratieveHandelingElement.getPartijCode(),
                        administratieveHandelingElement.getToelichtingOntlening(),
                        Collections.singletonList(bezienVanuitPersoonElement1),
                        administratieveHandelingElement.getGedeblokkeerdeMeldingen(),
                        administratieveHandelingElement.getBronnen(),
                        administratieveHandelingElement.getActies());
        administratieveHandelingElementAangepast.setVerzoekBericht(bericht);

        assertTrue(bericht.laadEntiteitenVoorObjectSleutels().isEmpty());
        final List<MeldingElement> result = administratieveHandelingElementAangepast.valideerInhoud();
        assertEquals(1, result.size());
        assertEquals(Regel.R2214, result.get(0).getRegel());
    }

    @Test
    public void persoonInBZVUIsHoofdPersoon() throws OngeldigeObjectSleutelException {
        final PersoonGegevensElement bezienVanuitPersoonElement1 = maakBezienVanuitPersoonElement("comId.bzvu.persoon1", SLEUTEL_HUWLIJK_P1);
        bezienVanuitPersoonElement1.setVerzoekBericht(bericht);
        final AdministratieveHandelingElement administratieveHandelingElementAangepast =
                new AdministratieveHandelingElement(
                        administratieveHandelingElement.getAttributen(),
                        administratieveHandelingElement.getSoort(),
                        administratieveHandelingElement.getPartijCode(),
                        administratieveHandelingElement.getToelichtingOntlening(),
                        Collections.singletonList(bezienVanuitPersoonElement1),
                        administratieveHandelingElement.getGedeblokkeerdeMeldingen(),
                        administratieveHandelingElement.getBronnen(),
                        administratieveHandelingElement.getActies());
        administratieveHandelingElementAangepast.setVerzoekBericht(bericht);

        assertTrue(bericht.laadEntiteitenVoorObjectSleutels().isEmpty());
        final List<MeldingElement> result = administratieveHandelingElementAangepast.valideerInhoud();
        assertEquals(0, result.size());
    }

    @Test
    public void R2174_OnbekendeNationaliteit() throws OngeldigeObjectSleutelException {

        final AdministratieveHandelingElement administratieveHandelingElementAangepast = getAdminHandelingVoorNationaliteit();
        final BijhoudingPersoon persoonIngezetenne = createPersoon("212121", true, true);
        final PersoonNationaliteit onbekendeNationaliteit = createOnbekendeNationaliteit(persoonIngezetenne);
        persoonIngezetenne.addPersoonNationaliteit(onbekendeNationaliteit);
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, null)).thenReturn(persoonIngezetenne);
        when(getObjectSleutelIndex().getEntiteitVoorId(BijhoudingPersoon.class, persoonIngezetenne.getId())).thenReturn(persoonIngezetenne);
        final BijhoudingRelatie relatie = BijhoudingRelatie.decorate(new Relatie(SoortRelatie.HUWELIJK));
        BijhoudingBetrokkenheid bert = BijhoudingBetrokkenheid.decorate(new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie));
        bert.setPersoon(persoonIngezetenne);
        relatie.addBetrokkenheid(bert);
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(relatie);

        final List<MeldingElement> meldingen = administratieveHandelingElementAangepast.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R2174_NLNationaliteit() throws OngeldigeObjectSleutelException {
        final AdministratieveHandelingElement administratieveHandelingElementAangepast = getAdminHandelingVoorNationaliteit();
        final BijhoudingPersoon persoonIngezetenne = createPersoon("212121", true, true);
        persoonIngezetenne.addBetrokkenheid(new Betrokkenheid(SoortBetrokkenheid.PARTNER, new Relatie(SoortRelatie.HUWELIJK)));
        final PersoonNationaliteit onbekendeNationaliteit = createNederlandseNationaliteit(persoonIngezetenne);
        persoonIngezetenne.addPersoonNationaliteit(onbekendeNationaliteit);
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "212121")).thenReturn(persoonIngezetenne);
        when(getObjectSleutelIndex().getEntiteitVoorId(BijhoudingPersoon.class, persoonIngezetenne.getId())).thenReturn(persoonIngezetenne);
        final BijhoudingRelatie relatie = BijhoudingRelatie.decorate(new Relatie(SoortRelatie.HUWELIJK));
        Betrokkenheid bert = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        bert.setPersoon(persoonIngezetenne);
        relatie.addBetrokkenheid(bert);
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(relatie);
        final List<MeldingElement> meldingen = administratieveHandelingElementAangepast.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2174.getCode(), meldingen.get(0).getRegel().getCode());
    }

    @Test
    public void R1573_NLNationaliteit() throws OngeldigeObjectSleutelException {
        final BijhoudingPersoon persoonIngezetenne = createPersoon("212121", true, true);
        persoonIngezetenne.addBetrokkenheid(new Betrokkenheid(SoortBetrokkenheid.PARTNER, new Relatie(SoortRelatie.HUWELIJK)));
        persoonIngezetenne.addPersoonNationaliteit(createNederlandseNationaliteit(persoonIngezetenne));
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "212121")).thenReturn(persoonIngezetenne);
        final BijhoudingVerzoekBericht bericht = getSuccesHuwelijkInNederlandBericht();
        final List<MeldingElement> meldingen = bericht.getAdministratieveHandeling().valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1573.getCode(), meldingen.get(0).getRegel().getCode());
    }

    private AdministratieveHandelingElement getAdminHandelingVoorNationaliteit() {
        final RegistratieAanvangHuwelijkActieElement bActie = (RegistratieAanvangHuwelijkActieElement) administratieveHandelingElement.getActies().get(0);
        final List<ActieElement> acties = new ArrayList<>();
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        bActie.getAttributen(),
                        null,
                        null,
                        null,
                        new CharacterElement('A'),
                        new DatumElement(20160323),
                        new StringElement("dummy"),
                        new StringElement("dummy"),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(bericht);
        final GeregistreerdPartnerschapElement gp =
                new GeregistreerdPartnerschapElement(bActie.getAttributen(), relatie, bActie.getHuwelijk().getBetrokkenheden());
        gp.setVerzoekBericht(bericht);
        final Map<String, String> attributen = new HashMap<>();
        attributen.put(OBJECTTYPE_ATTRIBUUT.toString(), "persoon");
        attributen.put(OBJECT_SLEUTEL_ATTRIBUUT.toString(), "212121");
        attributen.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "1");
        final RegistratieEindeGeregistreerdPartnerschapActieElement egp =
                new RegistratieEindeGeregistreerdPartnerschapActieElement(attributen, new DatumElement(20160323), null, null, gp);
        egp.setVerzoekBericht(bericht);
        final GeslachtsnaamcomponentElement geslachtsnaamcomponentElement =
                new GeslachtsnaamcomponentElement(attributen, null, null, VOORVOEGSEL_ELEMENT, SCHEIDINGSTEKEN_ELEMENT, new StringElement("Stam"));
        geslachtsnaamcomponentElement.setVerzoekBericht(bericht);
        final List<GeslachtsnaamcomponentElement> geslachtsnaamcomponentElements = new ArrayList<>();
        geslachtsnaamcomponentElements.add(geslachtsnaamcomponentElement);

        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        params.geslachtsnaamcomponenten(geslachtsnaamcomponentElements);
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement(attributen, params);

        persoon.setVerzoekBericht(bericht);
        final RegistratieGeslachtsnaamActieElement rga = new RegistratieGeslachtsnaamActieElement(attributen, new DatumElement(20160323), null, null, persoon);
        rga.setVerzoekBericht(bericht);
        acties.add(egp);
        acties.add(rga);
        final AdministratieveHandelingElement result =
                new AdministratieveHandelingElement(
                        administratieveHandelingElement.getAttributen(),
                        AdministratieveHandelingElementSoort.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                        administratieveHandelingElement.getPartijCode(),
                        administratieveHandelingElement.getToelichtingOntlening(),
                        administratieveHandelingElement.getBezienVanuitPersonen(),
                        administratieveHandelingElement.getGedeblokkeerdeMeldingen(),
                        administratieveHandelingElement.getBronnen(),
                        acties);
        result.setVerzoekBericht(bericht);
        return result;
    }

    @Test
    public void R2413_toelichtingBijDeblokeerbareMeldingIsNull() throws OngeldigeObjectSleutelException {
        final BijhoudingVerzoekBericht berichtSucces = getSuccesHuwelijkInNederlandBericht();
        final AdministratieveHandelingElement orgineelElement = berichtSucces.getAdministratieveHandeling();
        final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen = new ArrayList<>();
        gedeblokkeerdeMeldingen.add(new GedeblokkeerdeMeldingElement(orgineelElement.getAttributen(), new StringElement("R1234"), new StringElement("Teskt")));
        final AdministratieveHandelingElement administratieveHandelingElementAangepast =
                new AdministratieveHandelingElement(
                        orgineelElement.getAttributen(),
                        orgineelElement.getSoort(),
                        orgineelElement.getPartijCode(),
                        null,
                        orgineelElement.getBezienVanuitPersonen(),
                        gedeblokkeerdeMeldingen,
                        orgineelElement.getBronnen(),
                        orgineelElement.getActies());
        administratieveHandelingElementAangepast.setVerzoekBericht(berichtSucces);

        final List<MeldingElement> meldingen = administratieveHandelingElementAangepast.valideerInhoud();

        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2413.getCode(), meldingen.get(0).getRegel().getCode());
    }

    @Test
    public void R2413_toelichtingBijDeblokeerbareMeldingEmptyList() throws OngeldigeObjectSleutelException {
        final BijhoudingVerzoekBericht berichtSucces = getSuccesHuwelijkInNederlandBericht();
        final AdministratieveHandelingElement orgineelElement = berichtSucces.getAdministratieveHandeling();
        final AdministratieveHandelingElement administratieveHandelingElementAangepast =
                new AdministratieveHandelingElement(
                        orgineelElement.getAttributen(),
                        orgineelElement.getSoort(),
                        orgineelElement.getPartijCode(),
                        null,
                        orgineelElement.getBezienVanuitPersonen(),
                        Collections.emptyList(),
                        orgineelElement.getBronnen(),
                        orgineelElement.getActies());
        administratieveHandelingElementAangepast.setVerzoekBericht(berichtSucces);

        final List<MeldingElement> meldingen = administratieveHandelingElementAangepast.valideerInhoud();

        assertEquals(0, meldingen.size());
    }

    @Test
    public void R2413_toelichtingBijDeblokeerbareMeldingIsNotNull() throws OngeldigeObjectSleutelException {
        final BijhoudingVerzoekBericht berichtSucces = getSuccesHuwelijkInNederlandBericht();
        final AdministratieveHandelingElement orgineelElement = berichtSucces.getAdministratieveHandeling();
        final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen = new ArrayList<>();
        gedeblokkeerdeMeldingen.add(new GedeblokkeerdeMeldingElement(orgineelElement.getAttributen(), new StringElement("R1234"), new StringElement("Teskt")));
        final AdministratieveHandelingElement administratieveHandelingElementAangepast =
                new AdministratieveHandelingElement(
                        orgineelElement.getAttributen(),
                        orgineelElement.getSoort(),
                        orgineelElement.getPartijCode(),
                        new StringElement("toelichting"),
                        orgineelElement.getBezienVanuitPersonen(),
                        gedeblokkeerdeMeldingen,
                        orgineelElement.getBronnen(),
                        orgineelElement.getActies());
        administratieveHandelingElementAangepast.setVerzoekBericht(berichtSucces);

        final List<MeldingElement> meldingen = administratieveHandelingElementAangepast.valideerInhoud();

        assertEquals(0, meldingen.size());
    }

    @Test
    public void controleerZendendePartijTestR2348() {
        final ElementBuilder elementBuilder = new ElementBuilder();
        when(mb.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(BijhoudingRelatie.decorate(new Relatie(SoortRelatie
                .GEREGISTREERD_PARTNERSCHAP)));
        when(mb.getStuurgegevens()).thenReturn(elementBuilder
                .maakStuurgegevensElement("st", new ElementBuilder.StuurgegevensParameters().zendendePartij("0000").zendendeSysteem("BZM Leverancier A")
                        .referentienummer("88409eeb-1aa5-43fc-8614-43055123a165").tijdstipVerzending("2016-03-21T09:32:03.234+02:00")));

        final AdministratieveHandelingElement administratieveHandeling = bericht.getAdministratieveHandeling();
        administratieveHandeling.setVerzoekBericht(mb);
        final List<MeldingElement> meldingen = administratieveHandeling.valideerInhoud();

        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2348.getCode(), meldingen.get(0).getRegel().getCode());
    }


    @Test
    public void controleerZendendePartijGBATestR2348() {
        final ElementBuilder elementBuilder = new ElementBuilder();
        when(mb.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, null)).thenReturn(BijhoudingRelatie.decorate(new Relatie(SoortRelatie
                .GEREGISTREERD_PARTNERSCHAP)));
        when(mb.getStuurgegevens()).thenReturn(elementBuilder
                .maakStuurgegevensElement("st", new ElementBuilder.StuurgegevensParameters().zendendePartij("0000").zendendeSysteem("BZM Leverancier A")
                        .referentienummer("88409eeb-1aa5-43fc-8614-43055123a165").tijdstipVerzending("2016-03-21T09:32:03.234+02:00")));

        final AdministratieveHandelingElement administratieveHandeling = bericht.getAdministratieveHandeling();
        final AdministratieveHandelingElement administratieveHandelingAangepast =
                new AdministratieveHandelingElement(
                        administratieveHandeling.getAttributen(),
                        AdministratieveHandelingElementSoort.GBA_AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                        administratieveHandeling.getPartijCode(),
                        administratieveHandeling.getToelichtingOntlening(),
                        administratieveHandeling.getBezienVanuitPersonen(),
                        administratieveHandeling.getGedeblokkeerdeMeldingen(),
                        administratieveHandeling.getBronnen(),
                        administratieveHandeling.getActies());
        administratieveHandelingAangepast.setVerzoekBericht(mb);
        final List<MeldingElement> meldingen = administratieveHandelingAangepast.valideerInhoud();

        controleerRegels(meldingen, Regel.R2348);
    }


    @Test
    public void testGetActieBySoort() {
        final Map<String, String> attributen = administratieveHandelingElement.getAttributen();
        final FamilierechtelijkeBetrekkingElement famBetr = eBuilder.maakFamilierechtelijkeBetrekkingElement("famb", null, Collections.emptyList());
        final RegistratieGeboreneActieElement actie = new RegistratieGeboreneActieElement(attributen, null, null, null, famBetr);
        final AdministratieveHandelingElement
                ahElement =
                new AdministratieveHandelingElement(attributen, AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND,
                        administratieveHandelingElement.getPartijCode(), null, null, null, null, Collections.singletonList(actie));

        assertEquals(actie, ahElement.getActieBySoort(SoortActie.REGISTRATIE_GEBORENE));
        assertNull(ahElement.getActieBySoort(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS));
    }

    @Test
    public void testIsGeboorteHandeling() {
        final EnumSet<AdministratieveHandelingElementSoort> okSoorten =
                EnumSet.of(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND,
                        AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM,
                        AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM,
                        AdministratieveHandelingElementSoort.GBA_GEBOORTE_IN_NEDERLAND,
                        AdministratieveHandelingElementSoort.GBA_GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM,
                        AdministratieveHandelingElementSoort.GBA_GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM);
        EnumSet<AdministratieveHandelingElementSoort> nokSoorten = EnumSet.complementOf(okSoorten);

        for (final AdministratieveHandelingElementSoort soort : okSoorten) {
            assertTrue(maakAdministratieveHandelingMetSoort(soort).isGeboorteHandeling());
        }

        for (final AdministratieveHandelingElementSoort soort : nokSoorten) {
            assertFalse(maakAdministratieveHandelingMetSoort(soort).isGeboorteHandeling());
        }
    }

    @Test
    public void testIsEersteInschrijving() {
        final EnumSet<AdministratieveHandelingElementSoort> okSoorten =
                EnumSet.of(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND,
                        AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM,
                        AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM,
                        AdministratieveHandelingElementSoort.GBA_GEBOORTE_IN_NEDERLAND,
                        AdministratieveHandelingElementSoort.GBA_GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM,
                        AdministratieveHandelingElementSoort.GBA_GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM,
                        AdministratieveHandelingElementSoort.VESTIGING_NIET_INGESCHREVENE);
        EnumSet<AdministratieveHandelingElementSoort> nokSoorten = EnumSet.complementOf(okSoorten);

        for (final AdministratieveHandelingElementSoort soort : okSoorten) {
            assertTrue(maakAdministratieveHandelingMetSoort(soort).isEersteInschrijving());
        }

        for (final AdministratieveHandelingElementSoort soort : nokSoorten) {
            assertFalse(maakAdministratieveHandelingMetSoort(soort).isEersteInschrijving());
        }
    }

    private AdministratieveHandelingElement maakAdministratieveHandelingMetSoort(final AdministratieveHandelingElementSoort soort) {
        final Map<String, String> attributen = administratieveHandelingElement.getAttributen();
        return new AdministratieveHandelingElement(attributen, soort,
                administratieveHandelingElement.getPartijCode(), null, null, null, null, null);
    }

    @Bedrijfsregel(Regel.R1690)
    @Test
    public void testDerdeGeneratieKindNederlandseOudersEnGrootouders() {
        final AdministratieveHandelingElement ahElement = maakGeboorteInNederlandAH("0001", "0001", true, Collections.emptyList(), 20160101);
        final List<MeldingElement> meldingen = ahElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R1691)
    @Test
    public void testControleerNederlandseNationaliteitKindBijNederlandseOuders() {
        final AdministratieveHandelingElement ahElement = maakGeboorteInNederlandAH("0001", "0001", false, Collections.emptyList(), 20160101);
        final List<MeldingElement> meldingen = ahElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R1691)
    @Test
    public void testControleerNederlandseNationaliteitKindBijNederlandseOuders_R1691() {
        final AdministratieveHandelingElement ahElement = maakGeboorteInNederlandAH("0001", "0033", false, Collections.emptyList(), 20160101);
        final List<MeldingElement> meldingen = ahElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1691, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1691)
    @Test
    public void testControleerNederlandseNationaliteitKindBijNederlandseOuders_R1691Meerderjarig() {
        final AdministratieveHandelingElement ahElement = maakGeboorteInNederlandAH("0001", "0033", false, Collections.emptyList(), 19950101);
        final List<MeldingElement> meldingen = ahElement.valideerInhoud();
        assertEquals(0, meldingen.stream().filter(meldingElement -> Objects.equals(meldingElement.getRegel(), Regel.R1691)).count());
    }

    private AdministratieveHandelingElement maakGeboorteInNederlandAH(final String ouderNationaliteitCode, final String kindNationaliteitCode,
                                                                      boolean grootOuderIngeschrevene,
                                                                      final List<BronReferentieElement> nationaliteitBronRef,
                                                                      final Integer geboorteDatum) {
        final List<ActieElement> acties = new ArrayList<>();
        final String commIdKind = "kind";

        final String objectSleutelOuder = "1234";
        final Persoon ouderPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        ouderPersoon.setId(1234L);
        final Persoon grootOuder = new Persoon(grootOuderIngeschrevene ? SoortPersoon.INGESCHREVENE : SoortPersoon.PSEUDO_PERSOON);
        grootOuder.setId(5678L);
        Relatie ouderRelatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        Betrokkenheid grootOuderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, ouderRelatie);
        grootOuderBetrokkenheid.addBetrokkenheidHistorie(new BetrokkenheidHistorie(grootOuderBetrokkenheid));
        grootOuder.addBetrokkenheid(grootOuderBetrokkenheid);
        Betrokkenheid kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, ouderRelatie);
        kindBetrokkenheid.addBetrokkenheidHistorie(new BetrokkenheidHistorie(kindBetrokkenheid));
        ouderPersoon.addBetrokkenheid(kindBetrokkenheid);
        ouderRelatie.addBetrokkenheid(kindBetrokkenheid);
        ouderRelatie.addBetrokkenheid(grootOuderBetrokkenheid);

        final PersoonNationaliteit
                ouderNationaliteit1 =
                new PersoonNationaliteit(ouderPersoon, new Nationaliteit("ouderNationaliteit", ouderNationaliteitCode));
        ouderNationaliteit1.getPersoonNationaliteitHistorieSet().add(createPersoonNationaliteitHistorie(ouderNationaliteit1, 19900101, null));
        ouderPersoon.addPersoonNationaliteit(ouderNationaliteit1);
        final PersoonNationaliteit ouderNationaliteit2 = new PersoonNationaliteit(ouderPersoon, new Nationaliteit("ouderNationaliteit2", "0033"));
        ouderNationaliteit1.getPersoonNationaliteitHistorieSet().add(createPersoonNationaliteitHistorie(ouderNationaliteit2, 20000101, null));
        ouderPersoon.addPersoonNationaliteit(ouderNationaliteit2);

        final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent(ouderPersoon, 1);
        PersoonGeslachtsnaamcomponentHistorie persoonGeslachtsnaamcomponentHistorie = new PersoonGeslachtsnaamcomponentHistorie(geslachtsnaamcomponent, "Stam");
        geslachtsnaamcomponent.addPersoonGeslachtsnaamcomponentHistorie(persoonGeslachtsnaamcomponentHistorie);
        ouderPersoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent);
        ouderPersoon.addPersoonSamengesteldeNaamHistorie(new PersoonSamengesteldeNaamHistorie(ouderPersoon, "Stam", false, false));

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectSleutelOuder)).thenReturn(BijhoudingPersoon.decorate(ouderPersoon));

        final PersoonGegevensElement ouderPersoonElement =
                eBuilder.maakPersoonGegevensElement("ouder", objectSleutelOuder, null, new ElementBuilder.PersoonParameters());
        ouderPersoonElement.setVerzoekBericht(bericht);
        final BetrokkenheidElement ouder = eBuilder.maakBetrokkenheidElement("ouderBetr", BetrokkenheidElementSoort.OUDER, ouderPersoonElement, null);

        final ElementBuilder.PersoonParameters kindPersParams = new ElementBuilder.PersoonParameters();
        final ElementBuilder.GeboorteParameters geboorteParams = new ElementBuilder.GeboorteParameters();
        geboorteParams.datum(geboorteDatum);
        kindPersParams.geboorte(eBuilder.maakGeboorteElement("kindGebElement", geboorteParams));

        kindPersParams.samengesteldeNaam(eBuilder.maakSamengesteldeNaamElement("com_samengesteldeNaam",
                new ElementBuilder.NaamParameters().voornamen("Pietje").indicatieNamenreeks(Boolean.FALSE)));
        kindPersParams.geslachtsnaamcomponenten(
                Collections.singletonList(eBuilder.maakGeslachtsnaamcomponentElement("geslnaamcompElement", null, null, null, null, "Stam")));
        final List<VoornaamElement> voornamen = new LinkedList<>();
        voornamen.add(eBuilder.maakVoornaamElement("k_vn", 1, "Pieter"));
        kindPersParams.voornamen(voornamen);
        final PersoonGegevensElement kindPersoonElement = eBuilder.maakPersoonGegevensElement(commIdKind, null, null, kindPersParams);
        final BetrokkenheidElement kind = eBuilder.maakBetrokkenheidElement("kindBetr", BetrokkenheidElementSoort.KIND, kindPersoonElement, null);

        // Hoofdactie
        final FamilierechtelijkeBetrekkingElement fbrElement = eBuilder.maakFamilierechtelijkeBetrekkingElement("fbr", null, Arrays.asList(kind, ouder));
        final RegistratieGeboreneActieElement registratieGeborene = eBuilder.maakRegistratieGeboreneActieElement("regGeboreneActie", 20160101, fbrElement);
        registratieGeborene.getHoofdPersonen().get(0).registreerPersoonElement(registratieGeborene.getPersoonElementen().get(0));
        acties.add(registratieGeborene);

        final ElementBuilder.PersoonParameters persParams = new ElementBuilder.PersoonParameters();
        if (kindNationaliteitCode != null) {
            // Nevenactie
            final NationaliteitElement natElement = eBuilder.maakNationaliteitElement("natElement", kindNationaliteitCode, null);
            persParams.nationaliteiten(Collections.singletonList(natElement));
            final PersoonGegevensElement persoonElement = eBuilder.maakPersoonGegevensElement("persNat", null, commIdKind, persParams);

            final RegistratieNationaliteitActieElement
                    registratieNationaliteit =
                    eBuilder.maakRegistratieNationaliteitActieElement("regNationaliteitActie", 20160101, nationaliteitBronRef, persoonElement);

            final Map<String, BmrGroep> commIdGroepMap = new HashMap<>();
            commIdGroepMap.put(commIdKind, kindPersoonElement);
            persoonElement.initialiseer(commIdGroepMap);
            registratieGeborene.getHoofdPersonen().get(0).registreerPersoonElement(persoonElement);
            acties.add(registratieNationaliteit);
        } else {
            final PersoonGegevensElement persoonElement = eBuilder.maakPersoonGegevensElement("staatloos_persoon", null, commIdKind, persParams);
            RegistratieStaatloosActieElement
                    registratieStaatloos =
                    eBuilder.maakRegistratieStaatloosActieElement("staatloos", 20160101, Collections.emptyList(), persoonElement);
            final Map<String, BmrGroep> commIdGroepMap = new HashMap<>();
            commIdGroepMap.put(commIdKind, kindPersoonElement);
            persoonElement.initialiseer(commIdGroepMap);
            registratieGeborene.getHoofdPersonen().get(0).registreerPersoonElement(persoonElement);
            acties.add(registratieStaatloos);
        }
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.acties(acties).soort(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND)
                .partijCode(PARTIJ_CODE);
        final AdministratieveHandelingElement ahElement = eBuilder.maakAdministratieveHandelingElement("ah", ahParams);
        ahElement.setVerzoekBericht(bericht);
        return ahElement;
    }

    @Bedrijfsregel(Regel.R2626)
    @Test
    public void testHoofdPersoonHeeftOnderzoek() {
        final ActieElement actieElement = mock(ActieElement.class);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.partijCode(PARTIJ_CODE);
        ahParams.soort(AdministratieveHandelingElementSoort.AANVANG_ONDERZOEK);
        ahParams.acties(Collections.singletonList(actieElement));
        final AdministratieveHandelingElement ah = eBuilder.maakAdministratieveHandelingElement("ah", ahParams);
        ah.setVerzoekBericht(bericht);

        final PersoonElement persoonElement = eBuilder.maakPersoonGegevensElement("persoon", "1234");
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(actieElement.getHoofdPersonen()).thenReturn(Collections.singletonList(persoon));
        when(actieElement.getPeilDatum()).thenReturn(new DatumElement(DatumUtil.vandaag()));
        when(persoon.heeftLopendOnderzoek()).thenReturn(true);
        when(persoon.getPersoonElementen()).thenReturn(Collections.singletonList(persoonElement));
        when(persoon.getPersoonBijhoudingHistorieSet())
                .thenReturn(Collections.singleton(new PersoonBijhoudingHistorie(persoon, PARTIJ, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL)));
        when(persoon.isPartijBijhoudingspartij(any(Partij.class))).thenReturn(true);
        controleerRegels(ah.valideerInhoud(), Regel.R2626);
    }

    @Bedrijfsregel(Regel.R2626)
    @Test
    public void testHoofdPersoonHeeftOnderzoek_BeeindigindOnderzoekHandeling() {
        final ActieElement actieElement = mock(ActieElement.class);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.partijCode(PARTIJ_CODE);
        ahParams.soort(AdministratieveHandelingElementSoort.BEEINDIGING_ONDERZOEK);
        ahParams.acties(Collections.singletonList(actieElement));
        final AdministratieveHandelingElement ah = eBuilder.maakAdministratieveHandelingElement("ah", ahParams);
        ah.setVerzoekBericht(bericht);

        final PersoonElement persoonElement = eBuilder.maakPersoonGegevensElement("persoon", "1234");
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(actieElement.getHoofdPersonen()).thenReturn(Collections.singletonList(persoon));
        when(actieElement.getPeilDatum()).thenReturn(new DatumElement(DatumUtil.vandaag()));
        when(persoon.heeftLopendOnderzoek()).thenReturn(true);
        when(persoon.getPersoonElementen()).thenReturn(Collections.singletonList(persoonElement));
        when(persoon.getPersoonBijhoudingHistorieSet())
                .thenReturn(Collections.singleton(new PersoonBijhoudingHistorie(persoon, PARTIJ, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL)));
        when(persoon.isPartijBijhoudingspartij(any(Partij.class))).thenReturn(true);
        controleerRegels(ah.valideerInhoud());
    }

    @Bedrijfsregel(Regel.R2626)
    @Test
    public void testHoofdPersoonHeeftOnderzoek_Hoofdpersoon_geen_onderzoek() {
        final ActieElement actieElement = mock(ActieElement.class);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.partijCode(PARTIJ_CODE);
        ahParams.soort(AdministratieveHandelingElementSoort.VERHUIZING_BINNENGEMEENTELIJK);
        ahParams.acties(Collections.singletonList(actieElement));
        final AdministratieveHandelingElement ah = eBuilder.maakAdministratieveHandelingElement("ah", ahParams);
        ah.setVerzoekBericht(bericht);

        final PersoonElement persoonElement = eBuilder.maakPersoonGegevensElement("persoon", "1234");
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(actieElement.getHoofdPersonen()).thenReturn(Collections.singletonList(persoon));
        when(actieElement.getPeilDatum()).thenReturn(new DatumElement(DatumUtil.vandaag()));
        when(persoon.heeftLopendOnderzoek()).thenReturn(false);
        when(persoon.getPersoonElementen()).thenReturn(Collections.singletonList(persoonElement));
        when(persoon.getPersoonBijhoudingHistorieSet())
                .thenReturn(Collections.singleton(new PersoonBijhoudingHistorie(persoon, PARTIJ, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL)));
        controleerRegels(ah.valideerInhoud());
    }

    private PersoonGegevensElement maakBezienVanuitPersoonElement(final String commId, final String objectSleutel) {
        final Map<String, String> bzvuElementAttributen =
                new AbstractBmrGroep.AttributenBuilder().objecttype("Persoon").objectSleutel(objectSleutel).communicatieId(commId).build();
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        return builder.maakPersoonGegevensElement(bzvuElementAttributen, params);
    }

    private BijhoudingPersoon createPersoon(final String id, final boolean ingezetene, final boolean heeftBijhoudingHistorie) {
        return createPersoon(Integer.parseInt(id), ingezetene, heeftBijhoudingHistorie);
    }

    private BijhoudingPersoon createPersoon(final int id, final boolean ingezetene, final boolean heeftBijhoudingHistorie) {
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        persoon.setId((long) id);
        persoon.setBijhoudingspartij(PARTIJ);
        if (!ingezetene) {
            persoon.setBijhoudingsaard(Bijhoudingsaard.NIET_INGEZETENE);
        } else {
            persoon.setBijhoudingsaard(Bijhoudingsaard.INGEZETENE);
        }
        if (heeftBijhoudingHistorie) {
            final PersoonBijhoudingHistorie persoonBijhoudingHistorie1 =
                    new PersoonBijhoudingHistorie(persoon, PARTIJ, Bijhoudingsaard.NIET_INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
            persoonBijhoudingHistorie1.setDatumAanvangGeldigheid(INGEZETENE_PERIODE_1[0]);
            persoonBijhoudingHistorie1.setDatumEindeGeldigheid(INGEZETENE_PERIODE_1[1]);
            persoon.addPersoonBijhoudingHistorie(persoonBijhoudingHistorie1);
            final PersoonBijhoudingHistorie persoonBijhoudingHistorie2 =
                    new PersoonBijhoudingHistorie(persoon, PARTIJ, Bijhoudingsaard.NIET_INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
            persoonBijhoudingHistorie2.setDatumAanvangGeldigheid(INGEZETENE_PERIODE_1[0]);
            persoonBijhoudingHistorie2.setDatumEindeGeldigheid(INGEZETENE_PERIODE_1[1]);
            persoon.addPersoonBijhoudingHistorie(persoonBijhoudingHistorie2);
            final PersoonBijhoudingHistorie persoonBijhoudingHistorie3 =
                    new PersoonBijhoudingHistorie(persoon, PARTIJ, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
            persoonBijhoudingHistorie3.setDatumAanvangGeldigheid(INGEZETENE_PERIODE_2[0]);
            persoon.addPersoonBijhoudingHistorie(persoonBijhoudingHistorie3);
        }
        final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent(persoon, 1);
        PersoonGeslachtsnaamcomponentHistorie persoonGeslachtsnaamcomponentHistorie = new PersoonGeslachtsnaamcomponentHistorie(geslachtsnaamcomponent, "Stam");
        geslachtsnaamcomponent.addPersoonGeslachtsnaamcomponentHistorie(persoonGeslachtsnaamcomponentHistorie);
        persoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent);
        return persoon;
    }

    private PersoonNationaliteit createNederlandseNationaliteit(final Persoon persoon) {
        final Nationaliteit nationaliteit = new Nationaliteit("Nederlandse", Nationaliteit.NEDERLANDSE);
        final PersoonNationaliteit pn = new PersoonNationaliteit(persoon, nationaliteit);
        pn.getPersoonNationaliteitHistorieSet().add(createPersoonNationaliteitHistorie(pn, 19900101, 19980101));
        pn.getPersoonNationaliteitHistorieSet().add(createPersoonNationaliteitHistorie(pn, 20050101, null));
        return pn;
    }

    private PersoonNationaliteit createOnbekendeNationaliteit(final Persoon persoon) {
        final Nationaliteit nationaliteit = new Nationaliteit("Onbekend", Nationaliteit.ONBEKEND);
        final PersoonNationaliteit pn = new PersoonNationaliteit(persoon, nationaliteit);
        pn.getPersoonNationaliteitHistorieSet().add(createPersoonNationaliteitHistorie(pn, null, null));
        return pn;
    }

    private PersoonNationaliteitHistorie createPersoonNationaliteitHistorie(
            final PersoonNationaliteit persoonNationaliteit,
            final Integer aanvangGeldigheid,
            final Integer eindegeldigheid) {
        PersoonNationaliteitHistorie result = new PersoonNationaliteitHistorie(persoonNationaliteit);
        result.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        result.setDatumAanvangGeldigheid(aanvangGeldigheid);
        result.setDatumEindeGeldigheid(eindegeldigheid);
        return result;
    }
}
