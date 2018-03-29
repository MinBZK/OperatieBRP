/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;

public class GeboorteElementTest extends AbstractElementTest {

    private static final StringElement GEMEENTECODE_PLAATS_GRONINGEN = new StringElement("0017");
    private static final StringElement GEMEENTECODE_PLAATS_ALMERE = new StringElement("0037");
    private static final StringElement GEMEENTECODE_ONBEKEND = new StringElement("0000");
    private static final StringElement GEMEENTECODE_PLAATS_NULL = new StringElement("null");
    private static final StringElement LANDGEBIEDCODE_NL = new StringElement("6030");
    private static final StringElement LANDGEBIEDCODE_NIET_NL = new StringElement("0001");
    private static final StringElement LANDGEBIEDCODE_ONBEKEND = new StringElement("0000");
    private static final StringElement LANDGEBIEDCODE_INTERNATIONAAL = new StringElement("9999");
    private static final StringElement BUITENLANDSE_PLAATS = new StringElement("buitenlandsePlaats");
    private static final StringElement BUITENLANDSE_REGIO = new StringElement("buitenlandseRegio");
    private static final StringElement LOCATIE_OMSCHRIJVING = new StringElement("locatie");
    private static final LandOfGebied NEDERLAND = new LandOfGebied("6030", "Nederland");

    private LandOfGebied ongeldigLandOfGebied;
    private Gemeente gemeenteMetGeldigheidDatums;
    private Gemeente gemeenteZonderGeldigheidDatums;
    private Map<String, String> attributes;

    @Before
    public void setupGeboorteElementTest() {
        attributes = new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_test").build();

        ongeldigLandOfGebied = new LandOfGebied("0000", "Onbekend");

        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(LANDGEBIEDCODE_NL.getWaarde())).thenReturn(
                NEDERLAND);
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(LANDGEBIEDCODE_NIET_NL.getWaarde())).thenReturn(
                new LandOfGebied("0001", "Niet Nederland"));
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(LANDGEBIEDCODE_ONBEKEND.getWaarde())).thenReturn(ongeldigLandOfGebied);
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(LANDGEBIEDCODE_INTERNATIONAAL.getWaarde())).thenReturn(
                new LandOfGebied("9999", "Internationaal gebied"));

        gemeenteMetGeldigheidDatums = new Gemeente(Short.parseShort("17"), "Groningen", "0018", new Partij("Gemeente Groningen", "000014"));
        gemeenteZonderGeldigheidDatums = new Gemeente(Short.parseShort("37"), "Almere", "0038", new Partij("Gemeente Almere", "000038"));
        final Gemeente gemeenteOnbekend = new Gemeente(Short.parseShort("4"), "Onbekend", "0000", new Partij("Gemeente Onbekend", "000000"));

        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(GEMEENTECODE_ONBEKEND.getWaarde())).thenReturn(gemeenteOnbekend);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(GEMEENTECODE_PLAATS_GRONINGEN.getWaarde())).thenReturn(
                gemeenteMetGeldigheidDatums);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(GEMEENTECODE_PLAATS_ALMERE.getWaarde())).thenReturn(
                gemeenteZonderGeldigheidDatums);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(GEMEENTECODE_PLAATS_NULL.getWaarde())).thenReturn(null);

        final Plaats plaatsMetGeldigheidDatums = new Plaats("Groningen");
        final Plaats plaatsZonderGeldigheidDatums = new Plaats("Almere");

        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(GEMEENTECODE_PLAATS_NULL.getWaarde())).thenReturn(null);
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(GEMEENTECODE_PLAATS_GRONINGEN.getWaarde())).thenReturn(plaatsMetGeldigheidDatums);
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(GEMEENTECODE_PLAATS_ALMERE.getWaarde())).thenReturn(plaatsZonderGeldigheidDatums);
    }

    @Test
    public void testNullGemeenteGeboorte() {
        GeboorteElement geboorte =
                new GeboorteElement(attributes, new DatumElement(20160130), GEMEENTECODE_PLAATS_NULL, null, null, null, null, LANDGEBIEDCODE_NL);
        List<MeldingElement> meldingen = geboorte.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2164.getCode(), meldingen.get(0).getRegelCode().getWaarde());
    }

    @Test
    public void testGeldigGemeenteGeboorte() {
        GeboorteElement geboorte =
                new GeboorteElement(attributes, new DatumElement(20160210), GEMEENTECODE_PLAATS_GRONINGEN, null, null, null, null, LANDGEBIEDCODE_NL);
        assertEquals(0, geboorte.valideer().size());
    }

    @Test
    public void testNietGeldigGemeenteGeboorte() {
        GeboorteElement geboorte =
                new GeboorteElement(attributes, new DatumElement(20160130), GEMEENTECODE_PLAATS_ALMERE, null, null, null, null, LANDGEBIEDCODE_NL);
        gemeenteZonderGeldigheidDatums.setDatumAanvangGeldigheid(20160101);
        gemeenteZonderGeldigheidDatums.setDatumEindeGeldigheid(20160130);

        List<MeldingElement> meldingen = geboorte.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1466.getCode(), meldingen.get(0).getRegelCode().getWaarde());
    }

    @Test
    public void testCorrecteDatumNederland() {
        GeboorteElement geboorte =
                new GeboorteElement(attributes, new DatumElement(20160502), GEMEENTECODE_PLAATS_GRONINGEN, null, null, null, null, LANDGEBIEDCODE_NL);
        assertEquals(0, geboorte.valideer().size());
    }

    @Test
    public void testCorrecteDatumOnbekend() {
        GeboorteElement geboorte =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160002),
                        null,
                        null,
                        BUITENLANDSE_PLAATS,
                        null,
                        null,
                        LANDGEBIEDCODE_NIET_NL);
        assertEquals(0, geboorte.valideer().size());
    }

    @Test
    public void testIncorrecteDatumNederland() {
        GeboorteElement geboorte =
                new GeboorteElement(attributes, new DatumElement(20160002), GEMEENTECODE_PLAATS_GRONINGEN, null, null, null, null, LANDGEBIEDCODE_NL);
        List<MeldingElement> meldingen = geboorte.valideer();
        assertEquals(1, meldingen.size());

        MeldingElement melding = meldingen.get(0);
        String code = melding.getRegelCode().getWaarde();
        assertEquals(Regel.R1469.getCode(), code);
    }

    @Test
    public void testOngeldigLandOfGebied() {
        final GeboorteElement geboorte =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160002),
                        null,
                        null,
                        new StringElement("Montreal"),
                        null,
                        LOCATIE_OMSCHRIJVING,
                        new StringElement(String.valueOf(9876)));
        List<MeldingElement> meldingen = geboorte.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1649.getCode(), meldingen.get(0).getRegelCode().getWaarde());
    }

    @Test
    public void testOngeldigBuitenlandsePlaatsOfRegioNietNL() {
        List<MeldingElement> meldingen =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160002),
                        null,
                        null,
                        null,
                        null,
                        null,
                        LANDGEBIEDCODE_NIET_NL).valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2021.getCode(), meldingen.get(0).getRegelCode().getWaarde());

        List<MeldingElement> meldingen2 =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160002),
                        null,
                        null,
                        null,
                        BUITENLANDSE_REGIO,
                        null,
                        LANDGEBIEDCODE_NIET_NL).valideer();
        assertEquals(0, meldingen2.size());
    }

    @Test
    public void testGeldigBuitenlandsePlaatsOfRegioInternationaal() {
        final GeboorteElement geboorte =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160002),
                        null,
                        null,
                        null,
                        null,
                        LOCATIE_OMSCHRIJVING,
                        LANDGEBIEDCODE_INTERNATIONAAL);
        List<MeldingElement> meldingen = geboorte.valideer();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeldigBuitenlandsePlaatsOfRegioNationaal() {
        final GeboorteElement geboorte =
                new GeboorteElement(attributes, new DatumElement(20160502), GEMEENTECODE_PLAATS_GRONINGEN, null, null, null, null, LANDGEBIEDCODE_NL);
        List<MeldingElement> meldingen = geboorte.valideer();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeldigBuitenlandsePlaatsOfRegioOnbekend() {
        final GeboorteElement geboorte =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160502),
                        null,
                        null,
                        null,
                        null,
                        LOCATIE_OMSCHRIJVING,
                        LANDGEBIEDCODE_ONBEKEND);
        List<MeldingElement> meldingen = geboorte.valideer();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testValtNietBinnenDatumStreng() {
        GeboorteElement geboorte =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160201),
                        null,
                        null,
                        null,
                        null,
                        LOCATIE_OMSCHRIJVING,
                        LANDGEBIEDCODE_ONBEKEND);
        ongeldigLandOfGebied.setDatumAanvangGeldigheid(20160102);
        ongeldigLandOfGebied.setDatumEindeGeldigheid(20160103);
        List<MeldingElement> meldingen = geboorte.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1467.getCode(), meldingen.get(0).getRegelCode().toString());
    }

    @Test
    public void testValtBinnenDatumStreng() {
        GeboorteElement geboorte =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160215),
                        null,
                        null,
                        null,
                        null,
                        LOCATIE_OMSCHRIJVING,
                        LANDGEBIEDCODE_ONBEKEND);
        ongeldigLandOfGebied.setDatumAanvangGeldigheid(20160201);
        ongeldigLandOfGebied.setDatumEindeGeldigheid(20160228);
        assertEquals(0, geboorte.valideer().size());
    }


    @Test
    public void testGeldigPlaatsGeboorte() {
        GeboorteElement geboorte =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160210),
                        GEMEENTECODE_PLAATS_GRONINGEN,
                        GEMEENTECODE_PLAATS_GRONINGEN,
                        null,
                        null,
                        null,
                        LANDGEBIEDCODE_NL);
        assertEquals(0, geboorte.valideer().size());
    }

    @Test
    public void testNullPlaatsGeboorte() {
        GeboorteElement geboorte =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160130),
                        GEMEENTECODE_PLAATS_GRONINGEN,
                        GEMEENTECODE_PLAATS_NULL,
                        null,
                        null,
                        null,
                        LANDGEBIEDCODE_NL);
        List<MeldingElement> meldingen = geboorte.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2155.getCode(), meldingen.get(0).getRegelCode().getWaarde());
    }

    @Test
    public void testGeboortedatum() {
        final GeboorteElement geboorteVoorVandaag =
                new GeboorteElement(
                        Collections.emptyMap(),
                        new DatumElement(20150101),
                        GEMEENTECODE_PLAATS_ALMERE,
                        null,
                        null,
                        null,
                        null,
                        LANDGEBIEDCODE_NL);
        assertEquals(0, geboorteVoorVandaag.valideer().size());

        final GeboorteElement geboorteVandaag =
                new GeboorteElement(
                        Collections.emptyMap(),
                        new DatumElement(DatumUtil.vandaag()),
                        GEMEENTECODE_PLAATS_ALMERE,
                        null,
                        null,
                        null,
                        null,
                        LANDGEBIEDCODE_NL);
        assertEquals(0, geboorteVandaag.valideer().size());

        final GeboorteElement geboorteNaVandaag =
                new GeboorteElement(
                        attributes,
                        new DatumElement(21000101),
                        GEMEENTECODE_PLAATS_ALMERE,
                        null,
                        null,
                        null,
                        null,
                        LANDGEBIEDCODE_NL);
        final List<MeldingElement> meldingen = geboorteNaVandaag.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2177, meldingen.get(0).getRegel());
    }

    @Test
    public void testBuitenlandsePlaatsNotNull() {
        List<MeldingElement> meldingen =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160002),
                        null,
                        null,
                        BUITENLANDSE_PLAATS,
                        null,
                        LOCATIE_OMSCHRIJVING,
                        LANDGEBIEDCODE_ONBEKEND).valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2032.getCode(), meldingen.get(0).getRegelCode().getWaarde());
        List<MeldingElement> meldingen1 =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160102),
                        GEMEENTECODE_ONBEKEND,
                        null,
                        BUITENLANDSE_PLAATS,
                        null,
                        null,
                        LANDGEBIEDCODE_NL).valideer();
        assertEquals(1, meldingen1.size());
        assertEquals(Regel.R2032.getCode(), meldingen1.get(0).getRegelCode().getWaarde());
        List<MeldingElement> meldingen2 =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160002),
                        null,
                        null,
                        BUITENLANDSE_PLAATS,
                        null,
                        LOCATIE_OMSCHRIJVING,
                        LANDGEBIEDCODE_INTERNATIONAAL).valideer();
        assertEquals(1, meldingen2.size());
        assertEquals(Regel.R2032.getCode(), meldingen2.get(0).getRegelCode().getWaarde());
    }

    @Test
    public void testBuitenlandseRegioNotNull() {
        List<MeldingElement> meldingen =
                new GeboorteElement(
                        attributes,
                        new DatumElement(20160002),
                        null,
                        null,
                        null,
                        BUITENLANDSE_REGIO,
                        LOCATIE_OMSCHRIJVING,
                        LANDGEBIEDCODE_ONBEKEND).valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2032.getCode(), meldingen.get(0).getRegelCode().getWaarde());
    }

    @Test
    public void testWoonplaatsOfGemeenteGevuldLandNL() {
        assertEquals(
                0,
                new GeboorteElement(
                        Collections.emptyMap(),
                        new DatumElement(19850215),
                        GEMEENTECODE_PLAATS_ALMERE,
                        null,
                        null,
                        null,
                        null,
                        LANDGEBIEDCODE_NL).valideerInhoud().size());

        final List<MeldingElement> meldingen =
                new GeboorteElement(attributes, new DatumElement(19850215), null, null, null, null, null, LANDGEBIEDCODE_NL).valideer(
                );
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2041, meldingen.get(0).getRegel());
    }

    @Test
    public void testGemeenteNullLandNL() {

        final List<MeldingElement> meldingen =
                new GeboorteElement(attributes, new DatumElement(19850215), null, GEMEENTECODE_PLAATS_ALMERE, null, null, null, LANDGEBIEDCODE_NL).valideer();
        assertEquals(2, meldingen.size());
        assertEquals(Regel.R2036, meldingen.get(1).getRegel());
        assertEquals(Regel.R2041, meldingen.get(0).getRegel());
    }

    @Test
    public void testLocatieOmschijvingLeegLandOnbekend() {
        final GeboorteElement geboorte =
                new GeboorteElement(attributes, new DatumElement(20160502), null, null, null, null, null, LANDGEBIEDCODE_ONBEKEND);
        List<MeldingElement> meldingen = geboorte.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2046, meldingen.get(0).getRegel());
    }

    @Test
    public void r2046_ORANJE_4736_OmschijvingLeegLandInt() {
        final GeboorteElement geboorte =
                new GeboorteElement(attributes, new DatumElement(20160502), null, null, null, null, null, LANDGEBIEDCODE_INTERNATIONAAL);
        List<MeldingElement> meldingen = geboorte.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2046, meldingen.get(0).getRegel());
    }

    @Test
    public void r2046_ORANJE_4737_OmschijvingGevuldLandNL() {
        final GeboorteElement geboorte =
                new GeboorteElement(attributes, new DatumElement(20160502), GEMEENTECODE_PLAATS_GRONINGEN, null, null, null, LOCATIE_OMSCHRIJVING,
                        LANDGEBIEDCODE_NL);
        List<MeldingElement> meldingen = geboorte.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2046, meldingen.get(0).getRegel());
    }

    @Test
    public void testMaakPersoonGeboorteHistorieEntiteit() {
        final GeboorteElement element =
                new GeboorteElement(attributes, new DatumElement(20160210), GEMEENTECODE_PLAATS_GRONINGEN, null, null, null, null, LANDGEBIEDCODE_NL);
        assertEquals(0, element.valideer().size());
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        persoon.voegPersoonGeboorteHistorieToe(element, getActie());
        final PersoonGeboorteHistorie entiteit = FormeleHistorieZonderVerantwoording
                .getActueelHistorieVoorkomen(persoon.getPersoonGeboorteHistorieSet());
        assertNull(entiteit.getId());
        assertEquals(element.getDatum().getWaarde(), Integer.valueOf(entiteit.getDatumGeboorte()));
        assertEquals(gemeenteMetGeldigheidDatums, entiteit.getGemeente());
        assertNull(entiteit.getWoonplaatsnaamGeboorte());
        assertNull(entiteit.getBuitenlandsePlaatsGeboorte());
        assertNull(entiteit.getBuitenlandseRegioGeboorte());
        assertNull(entiteit.getOmschrijvingGeboortelocatie());
        assertEquals(NEDERLAND, entiteit.getLandOfGebied());
        assertEquals(entiteit, persoon.getPersoonGeboorteHistorieSet().iterator().next());
        assertEntiteitMetFormeleHistorie(entiteit);
    }

    @Test
    public void testGetInstance() {
        //setup
        final PersoonGeboorteHistorie
                geboorteHistorie =
                new PersoonGeboorteHistorie(new Persoon(SoortPersoon.INGESCHREVENE), 20000101, new LandOfGebied("0000", "test land of gebied"));
        geboorteHistorie.setGemeente(new Gemeente(Short.parseShort("0"), "test gemeente", "0000", new Partij("test partij", "000000")));
        geboorteHistorie.setWoonplaatsnaamGeboorte("Test woonplaatsnaam");
        geboorteHistorie.setBuitenlandsePlaatsGeboorte("Test buitenlandseplaats");
        geboorteHistorie.setBuitenlandseRegioGeboorte("Test buitenlandseregio");
        geboorteHistorie.setOmschrijvingGeboortelocatie("Test omschrijving locatie");
        final BijhoudingVerzoekBericht verzoekBericht = mock(BijhoudingVerzoekBericht.class);
        //execute
        GeboorteElement geboorteElement = GeboorteElement.getInstance(geboorteHistorie, verzoekBericht);
        //verify
        assertEquals(Integer.valueOf(geboorteHistorie.getDatumGeboorte()), BmrAttribuut.getWaardeOfNull(geboorteElement.getDatum()));
        assertEquals(String.valueOf(geboorteHistorie.getLandOfGebied().getCode()), BmrAttribuut.getWaardeOfNull(geboorteElement.getLandGebiedCode()));
        assertEquals(String.valueOf(geboorteHistorie.getGemeente().getCode()), BmrAttribuut.getWaardeOfNull(geboorteElement.getGemeenteCode()));
        assertEquals(geboorteHistorie.getWoonplaatsnaamGeboorte(), BmrAttribuut.getWaardeOfNull(geboorteElement.getWoonplaatsnaam()));
        assertEquals(geboorteHistorie.getBuitenlandsePlaatsGeboorte(), BmrAttribuut.getWaardeOfNull(geboorteElement.getBuitenlandsePlaats()));
        assertEquals(geboorteHistorie.getBuitenlandseRegioGeboorte(), BmrAttribuut.getWaardeOfNull(geboorteElement.getBuitenlandseRegio()));
        assertEquals(geboorteHistorie.getOmschrijvingGeboortelocatie(), BmrAttribuut.getWaardeOfNull(geboorteElement.getOmschrijvingLocatie()));

        //setup null values
        geboorteHistorie.setGemeente(null);
        geboorteHistorie.setWoonplaatsnaamGeboorte(null);
        geboorteHistorie.setBuitenlandsePlaatsGeboorte(null);
        geboorteHistorie.setBuitenlandseRegioGeboorte(null);
        geboorteHistorie.setOmschrijvingGeboortelocatie(null);
        //execute
        geboorteElement = GeboorteElement.getInstance(geboorteHistorie, verzoekBericht);
        //verify
        assertNotNull(geboorteElement.getDatum());
        assertNotNull(geboorteElement.getLandGebiedCode());
        assertNull(geboorteElement.getGemeenteCode());
        assertNull(geboorteElement.getWoonplaatsnaam());
        assertNull(geboorteElement.getBuitenlandsePlaats());
        assertNull(geboorteElement.getBuitenlandseRegio());
        assertNull(geboorteElement.getOmschrijvingLocatie());

        //verify null voorkomen
        assertNull(GeboorteElement.getInstance(null, verzoekBericht));
    }
}
