/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.lang.reflect.Constructor;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.element.ElementHelper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test voor {@link BrpMapperUtil}: simple type mapping.
 */
public class BrpMapperUtilSpecifiekTest {

    public static final String Q      = "Q";
    public static final String X      = "X";
    public static final String I      = "I";
    public static final String E      = "E";
    public static final String WX_001 = "WX001";
    public static final String PD     = "PD";

    private MetaAttribuut maakAttribuut(final Element attribuut, final Object waarde) {
        final MetaAttribuut result;
        try {
            final Constructor<MetaAttribuut> constructor = MetaAttribuut.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            result = constructor.newInstance();
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan MetaAttribuut niet maken");
        }
        ReflectionTestUtils.setField(result, "attribuutElement", ElementHelper.getAttribuutElement(attribuut.getId()));
        ReflectionTestUtils.setField(result, "waarde", waarde);

        return result;
    }

    @Test
    public void mapBrpAanduidingBijHuisnummerCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpAanduidingBijHuisnummerCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpAanduidingBijHuisnummerCode(maakAttribuut(Element.PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES, null), null));

        Assert.assertEquals(
            "by",
            BrpMetaAttribuutMapper.mapBrpAanduidingBijHuisnummerCode(maakAttribuut(Element.PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES, "by"), null)
                .getWaarde());
    }

    @Test
    public void mapBrpAanduidingInhoudingOfVermissingReisdocumentCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(
                maakAttribuut(Element.PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE, null),
                null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(
            maakAttribuut(Element.PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE, ""),
            null));
        Assert.assertEquals(
            Character.valueOf('Q'),
            BrpMetaAttribuutMapper.mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(
                maakAttribuut(Element.PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE, "Q"),
                null).getWaarde());
    }

    @Test
    public void mapBrpAangeverCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpAangeverCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpAangeverCode(maakAttribuut(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE, null), null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpAangeverCode(maakAttribuut(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE, ""), null));

        Assert.assertEquals(
            Character.valueOf('P'),
            BrpMetaAttribuutMapper.mapBrpAangeverCode(maakAttribuut(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE, "P"), null).getWaarde());
    }

    @Test
    public void mapBrpAdellijkeTitelCodeIst() {
        Assert.assertNull(BrpMapperUtil.mapBrpAdellijkeTitelCode((AdellijkeTitel) null, null));

        Assert.assertEquals("B", BrpMapperUtil.mapBrpAdellijkeTitelCode(AdellijkeTitel.B, null).getWaarde());
    }

    @Test
    public void mapBrpAdellijkeTitelCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpAdellijkeTitelCode((MetaAttribuut) null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpAdellijkeTitelCode(maakAttribuut(Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, null), null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpAdellijkeTitelCode(maakAttribuut(Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, ""), null));

        Assert.assertEquals(
            X,
            BrpMetaAttribuutMapper.mapBrpAdellijkeTitelCode(maakAttribuut(Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, "X"), null).getWaarde());
    }

    @Test
    public void mapBrpBijhoudingsaardCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpBijhoudingsaardCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpBijhoudingsaardCode(maakAttribuut(Element.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE, null), null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpBijhoudingsaardCode(maakAttribuut(Element.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE, ""), null));

        Assert.assertEquals(
            I,
            BrpMetaAttribuutMapper.mapBrpBijhoudingsaardCode(
                maakAttribuut(Element.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE, Bijhoudingsaard.INGEZETENE.getCode()),
                null).getWaarde());
    }

    @Test
    public void mapBrpGemeenteCodeIst() {
        Assert.assertNull(BrpMapperUtil.mapBrpGemeenteCode((Gemeente) null, null));

        Assert.assertEquals(
           "0517",
            BrpMapperUtil.mapBrpGemeenteCode(new Gemeente(null, "Naam", "0517", new Partij("Naam", "000517")), null).getWaarde());
    }

    @Test
    public void mapBrpGemeenteCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpGemeenteCode((MetaAttribuut) null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpGemeenteCode(maakAttribuut(Element.PERSOON_ADRES_GEMEENTECODE, null), null));

        Assert.assertEquals(
           "0517",
            BrpMetaAttribuutMapper.mapBrpGemeenteCode(maakAttribuut(Element.PERSOON_ADRES_GEMEENTECODE, "0517"), null).getWaarde());
    }

    @Test
    public void mapBrpGeslachtsaanduidingCodeIst() {
        Assert.assertNull(BrpMapperUtil.mapBrpGeslachtsaanduidingCode((Geslachtsaanduiding) null, null));

        Assert.assertEquals("V", BrpMapperUtil.mapBrpGeslachtsaanduidingCode(Geslachtsaanduiding.VROUW, null).getWaarde());
    }

    @Test
    public void mapBrpGeslachtsaanduidingCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpGeslachtsaanduidingCode((MetaAttribuut) null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpGeslachtsaanduidingCode(maakAttribuut(Element.PERSOON_GESLACHTSAANDUIDING_CODE, null), null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpGeslachtsaanduidingCode(maakAttribuut(Element.PERSOON_GESLACHTSAANDUIDING_CODE, ""), null));

        Assert.assertEquals("V", BrpMetaAttribuutMapper
            .mapBrpGeslachtsaanduidingCode(
                maakAttribuut(Element.PERSOON_GESLACHTSAANDUIDING_CODE, Geslachtsaanduiding.VROUW.getCode()),
                null)
            .getWaarde());
    }

    @Test
    public void mapBrpLandOfGebiedCodeIst() {
        Assert.assertNull(BrpMapperUtil.mapBrpLandOfGebiedCode((LandOfGebied) null, null));

        Assert.assertEquals("0012", BrpMapperUtil.mapBrpLandOfGebiedCode(new LandOfGebied("0012", "Naam"), null).getWaarde());
    }

    @Test
    public void mapBrpLandOfGebiedCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpLandOfGebiedCode((MetaAttribuut) null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpLandOfGebiedCode(maakAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE, null), null));

        Assert.assertEquals(
           "0012",
            BrpMetaAttribuutMapper.mapBrpLandOfGebiedCode(maakAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE, "0012"), null).getWaarde());
    }

    @Test
    public void mapBrpNaamgebruikCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpNaamgebruikCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpNaamgebruikCode(maakAttribuut(Element.PERSOON_NAAMGEBRUIK_CODE, null), null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpNaamgebruikCode(maakAttribuut(Element.PERSOON_NAAMGEBRUIK_CODE, ""), null));

        Assert.assertEquals(
            E,
            BrpMetaAttribuutMapper.mapBrpNaamgebruikCode(maakAttribuut(Element.PERSOON_NAAMGEBRUIK_CODE, Naamgebruik.EIGEN.getCode()), null).getWaarde());
    }

    @Test
    public void mapBrpNadereBijhoudingsaardCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpNadereBijhoudingsaardCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpNadereBijhoudingsaardCode(maakAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE, null), null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpNadereBijhoudingsaardCode(maakAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE, ""), null));

        Assert.assertEquals(
            E,
            BrpMetaAttribuutMapper.mapBrpNadereBijhoudingsaardCode(
                maakAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE, NadereBijhoudingsaard.EMIGRATIE.getCode()),
                null).getWaarde());
    }

    @Test
    public void mapBrpNationaliteitCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpNationaliteitCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpNationaliteitCode(maakAttribuut(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE, null), null));

        Assert.assertEquals(
            "0027",
            BrpMetaAttribuutMapper.mapBrpNationaliteitCode(maakAttribuut(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE, "0027"), null).getWaarde());
    }

    @Test
    public void mapBrpPartijCodeIst() {
        Assert.assertNull(BrpMapperUtil.mapBrpPartijCode((Partij) null, null));

        Assert.assertEquals("000123", BrpMapperUtil.mapBrpPartijCode(new Partij("Naam", "000123"), null).getWaarde());
    }

    @Test
    public void mapBrpPartijCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpPartijCode((MetaAttribuut) null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpPartijCode(maakAttribuut(Element.PERSOON_BIJHOUDING_PARTIJCODE, null), null));

        Assert.assertEquals(
           "000123",
            BrpMetaAttribuutMapper.mapBrpPartijCode(maakAttribuut(Element.PERSOON_BIJHOUDING_PARTIJCODE, "000123"), null).getWaarde());
    }

    @Test
    public void mapBrpPredikaatCodeOud() {
        Assert.assertNull(BrpMapperUtil.mapBrpPredicaatCode((Predicaat) null, null));

        Assert.assertEquals("J", BrpMapperUtil.mapBrpPredicaatCode(Predicaat.J, null).getWaarde());
    }

    @Test
    public void mapBrpPredikaatCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpPredicaatCode((MetaAttribuut) null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpPredicaatCode(maakAttribuut(Element.PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, null), null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpPredicaatCode(maakAttribuut(Element.PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, ""), null));

        Assert.assertEquals(
            X,
            BrpMetaAttribuutMapper.mapBrpPredicaatCode(maakAttribuut(Element.PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, "X"), null).getWaarde());
    }

    @Test
    public void mapBrpRedenVerkrijgingNederlandschapCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpRedenVerkrijgingNederlandschapCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpRedenVerkrijgingNederlandschapCode(maakAttribuut(Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE, null), null));

        Assert.assertEquals(
            "039",
            BrpMetaAttribuutMapper.mapBrpRedenVerkrijgingNederlandschapCode(maakAttribuut(Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE, "039"), null)
                .getWaarde());
    }

    @Test
    public void mapBrpRedenVerliesNederlanderschapCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpRedenVerliesNederlanderschapCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpRedenVerkrijgingNederlandschapCode(maakAttribuut(Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE, null), null));

        Assert.assertEquals(
            "039",
            BrpMetaAttribuutMapper.mapBrpRedenVerliesNederlanderschapCode(maakAttribuut(Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE, "039"), null)
                .getWaarde());
    }

    @Test
    public void mapBrpRedenWijzigingVerblijfCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpRedenWijzigingVerblijfCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpRedenWijzigingVerblijfCode(maakAttribuut(Element.PERSOON_ADRES_REDENWIJZIGINGCODE, null), null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpRedenWijzigingVerblijfCode(maakAttribuut(Element.PERSOON_ADRES_REDENWIJZIGINGCODE, ""), null));

        Assert.assertEquals(
            Character.valueOf('Q'),
            BrpMetaAttribuutMapper.mapBrpRedenWijzigingVerblijfCode(maakAttribuut(Element.PERSOON_ADRES_REDENWIJZIGINGCODE, "Q"), null).getWaarde());

    }

    @Test
    public void mapBrpReisdocumentAutoriteitVanAfgifteCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpReisdocumentAutoriteitVanAfgifteCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpReisdocumentAutoriteitVanAfgifteCode(maakAttribuut(Element.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE, null), null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpReisdocumentAutoriteitVanAfgifteCode(maakAttribuut(Element.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE, ""), null));

        Assert.assertEquals(
            WX_001,
            BrpMetaAttribuutMapper.mapBrpReisdocumentAutoriteitVanAfgifteCode(maakAttribuut(Element.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE, WX_001), null)
                .getWaarde());
    }

    @Test
    public void mapBrpSoortActieCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpSoortActieCode(null, null));

        Assert.assertEquals(
            "Registratie kind",
            BrpMapperUtil.mapBrpSoortActieCode(nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie.REGISTRATIE_KIND, null).getWaarde());
    }

    @Test
    public void mapBrpSoortAdresCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpSoortAdresCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpSoortAdresCode(maakAttribuut(Element.PERSOON_ADRES_SOORTCODE, null), null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpSoortAdresCode(maakAttribuut(Element.PERSOON_ADRES_SOORTCODE, ""), null));

        Assert.assertEquals(
            "B",
            BrpMetaAttribuutMapper.mapBrpSoortAdresCode(maakAttribuut(Element.PERSOON_ADRES_SOORTCODE, SoortAdres.BRIEFADRES.getCode()), null).getWaarde
                ());
    }

    @Test
    public void mapBrpSoortMigratieCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpSoortMigratieCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpSoortMigratieCode(maakAttribuut(Element.PERSOON_MIGRATIE_SOORTCODE, null), null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpSoortMigratieCode(maakAttribuut(Element.PERSOON_MIGRATIE_SOORTCODE, ""), null));

        Assert.assertEquals(
            I,
            BrpMetaAttribuutMapper.mapBrpSoortMigratieCode(maakAttribuut(Element.PERSOON_MIGRATIE_SOORTCODE, SoortMigratie.IMMIGRATIE.getCode()), null)
                .getWaarde());
    }

    @Test
    public void mapBrpSoortNederlandsReisdocumentCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpSoortNederlandsReisdocumentCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpSoortNederlandsReisdocumentCode(maakAttribuut(Element.PERSOON_REISDOCUMENT_SOORTCODE, null), null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpSoortNederlandsReisdocumentCode(maakAttribuut(Element.PERSOON_REISDOCUMENT_SOORTCODE, ""), null));

        Assert.assertEquals(
            PD,
            BrpMetaAttribuutMapper.mapBrpSoortNederlandsReisdocumentCode(maakAttribuut(Element.PERSOON_REISDOCUMENT_SOORTCODE, PD), null).getWaarde());
    }

    @Test
    public void mapBrpVerblijfsrechtCode() {
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpVerblijfsrechtCode(null, null));
        Assert.assertNull(BrpMetaAttribuutMapper.mapBrpVerblijfsrechtCode(maakAttribuut(Element.PERSOON_VERBLIJFSRECHT_AANDUIDINGCODE, null), null));

        Assert.assertEquals(
            "06",
            BrpMetaAttribuutMapper.mapBrpVerblijfsrechtCode(maakAttribuut(Element.PERSOON_VERBLIJFSRECHT_AANDUIDINGCODE, "06"), null).getWaarde());
    }
}
