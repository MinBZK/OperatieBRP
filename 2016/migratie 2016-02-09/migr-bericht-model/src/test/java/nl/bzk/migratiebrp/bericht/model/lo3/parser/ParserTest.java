/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

public class ParserTest extends AbstractParserTest {
    private static final String WAARDE_ELEMENT_8310 = "010000";

    @Test
    public void testParseWaarden() {
        final Map<Lo3ElementEnum, String> elementen = maakElementen();
        /*
         * Nog niet aangepast naar onderzoek
         */
        Assert.assertEquals(new Lo3Datumtijdstempel(20121218000000L), SimpleParser.parseLo3Datumtijdstempel("20121218000000"));

        /*
         * Aangepast naar de nieuwe onderzoek methods
         */
        Assert.assertEquals(maakLo3Long(WAARDE_ELEMENT_0110, null), Parser.parseLo3Long(elementen, Lo3ElementEnum.ELEMENT_0110, null, null));
        Assert.assertEquals(maakLo3Integer(WAARDE_ELEMENT_0120, null), Parser.parseLo3Integer(elementen, Lo3ElementEnum.ELEMENT_0120, null, null));
        Assert.assertEquals(
            new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220),
            Parser.parseLo3AdellijkeTitelPredikaatCode(elementen, Lo3ElementEnum.ELEMENT_0220, null, null));
        Assert.assertEquals(new Lo3GemeenteCode(WAARDE_ELEMENT_0320), Parser.parseLo3GemeenteCode(elementen, Lo3ElementEnum.ELEMENT_0320, null, null));
        Assert.assertEquals(new Lo3LandCode(WAARDE_ELEMENT_0330), Parser.parseLo3LandCode(elementen, Lo3ElementEnum.ELEMENT_0330, null, null));
        Assert.assertEquals(
            new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410),
            Parser.parseLo3Geslachtsaanduiding(elementen, Lo3ElementEnum.ELEMENT_0410, null, null));
        Assert.assertEquals(
            new Lo3NationaliteitCode(WAARDE_ELEMENT_0510),
            Parser.parseLo3NationaliteitCode(elementen, Lo3ElementEnum.ELEMENT_0510, null, null));
        Assert.assertEquals(
            new Lo3RedenOntbindingHuwelijkOfGpCode(WAARDE_ELEMENT_0740),
            Parser.parseLo3RedenOntbindingHuwelijkOfGpCode(elementen, Lo3ElementEnum.ELEMENT_0740, null, null));
        Assert.assertEquals(new Lo3FunctieAdres(WAARDE_ELEMENT_1010), Parser.parseLo3FunctieAdres(elementen, Lo3ElementEnum.ELEMENT_1010, null, null));
        Assert.assertEquals(
            new Lo3Huisnummer(Integer.valueOf(WAARDE_ELEMENT_1120)),
            Parser.parseLo3Huisnummer(elementen, Lo3ElementEnum.ELEMENT_1120, null, null));
        Assert.assertEquals(maakLo3Character(WAARDE_ELEMENT_1130, null), Parser.parseLo3Character(elementen, Lo3ElementEnum.ELEMENT_1130, null, null));
        Assert.assertEquals(
            new Lo3AanduidingHuisnummer(WAARDE_ELEMENT_1150),
            Parser.parseLo3AanduidingHuisnummer(elementen, Lo3ElementEnum.ELEMENT_1150, null, null));
        Assert.assertEquals(
            new Lo3SoortVerbintenis(WAARDE_ELEMENT_1510),
            Parser.parseLo3SoortVerbintenis(elementen, Lo3ElementEnum.ELEMENT_1510, null, null));
        Assert.assertEquals(
            new Lo3AanduidingEuropeesKiesrecht(Integer.valueOf(WAARDE_ELEMENT_3110)),
            Parser.parseLo3AanduidingEuropeesKiesrecht(elementen, Lo3ElementEnum.ELEMENT_3110, null, null));
        Assert.assertEquals(
            new Lo3SoortNederlandsReisdocument(WAARDE_ELEMENT_3510),
            Parser.parseLo3SoortNederlandsReisdocument(elementen, Lo3ElementEnum.ELEMENT_3510, null, null));
        Assert.assertEquals(
            new Lo3AutoriteitVanAfgifteNederlandsReisdocument(WAARDE_ELEMENT_3540),
            Parser.parseLo3AutoriteitVanAfgifteNederlandsReisdocument(elementen, Lo3ElementEnum.ELEMENT_3540, null, null));
        Assert.assertEquals(
            new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(WAARDE_ELEMENT_3570),
            Parser.parseLo3AanduidingInhoudingVermissingNederlandsReisdocument(elementen, Lo3ElementEnum.ELEMENT_3570, null, null));
        Assert.assertEquals(
            new Lo3Signalering(Integer.valueOf(WAARDE_ELEMENT_3610)),
            Parser.parseLo3Signalering(elementen, Lo3ElementEnum.ELEMENT_3610, null, null));
        Assert.assertEquals(
            new Lo3AanduidingUitgeslotenKiesrecht(WAARDE_ELEMENT_3810),
            Parser.parseLo3AanduidingUitgeslotenKiesrecht(elementen, Lo3ElementEnum.ELEMENT_3810, null, null));
        Assert.assertEquals(
            new Lo3IndicatieGezagMinderjarige(WAARDE_ELEMENT_3210),
            Parser.parseLo3IndicatieGezagMinderjarige(elementen, Lo3ElementEnum.ELEMENT_3210, null, null));
        Assert.assertEquals(
            new Lo3IndicatieCurateleregister(Integer.valueOf(WAARDE_ELEMENT_3310)),
            Parser.parseLo3IndicatieCurateleregister(elementen, Lo3ElementEnum.ELEMENT_3310, null, null));
        Assert.assertEquals(
            new Lo3AanduidingVerblijfstitelCode(WAARDE_ELEMENT_3910),
            Parser.parseLo3AanduidingVerblijfstitelCode(elementen, Lo3ElementEnum.ELEMENT_3910, null, null));
        Assert.assertEquals(
            new Lo3AanduidingNaamgebruikCode(WAARDE_ELEMENT_6110),
            Parser.parseLo3AanduidingNaamgebruikCode(elementen, Lo3ElementEnum.ELEMENT_6110, null, null));
        Assert.assertEquals(
            new Lo3RedenNederlandschapCode(WAARDE_ELEMENT_6310),
            Parser.parseLo3RedenNederlandschapCode(elementen, Lo3ElementEnum.ELEMENT_6310, null, null));
        Assert.assertEquals(
            new Lo3AanduidingBijzonderNederlandschap(WAARDE_ELEMENT_6510),
            Parser.parseLo3AanduidingBijzonderNederlandschap(elementen, Lo3ElementEnum.ELEMENT_6510, null, null));
        Assert.assertEquals(
            new Lo3RedenOpschortingBijhoudingCode(WAARDE_ELEMENT_6720),
            Parser.parseLo3RedenOpschortingBijhoudingCode(elementen, Lo3ElementEnum.ELEMENT_6720, null, null));
        Assert.assertEquals(
            new Lo3IndicatieGeheimCode(Integer.valueOf(WAARDE_ELEMENT_7010)),
            Parser.parseLo3IndicatieGeheimCode(elementen, Lo3ElementEnum.ELEMENT_7010, null, null));
        Assert.assertEquals(
            new Lo3AangifteAdreshouding(WAARDE_ELEMENT_7210),
            Parser.parseLo3AangifteAdreshouding(elementen, Lo3ElementEnum.ELEMENT_7210, null, null));
        Assert.assertEquals(
            new Lo3IndicatieDocument(WAARDE_ELEMENT_7510, null),
            Parser.parseLo3IndicatieDocument(elementen, Lo3ElementEnum.ELEMENT_7510, null, null));
        Assert.assertEquals(
            new Lo3IndicatieOnjuist(WAARDE_ELEMENT_8410),
            Parser.parseLo3IndicatieOnjuist(elementen, Lo3ElementEnum.ELEMENT_8410, null, null));
        Assert.assertEquals(maakDatum(WAARDE_ELEMENT_8510, null), Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_8510, null, null));
        Assert.assertEquals(
            new Lo3IndicatiePKVolledigGeconverteerdCode(WAARDE_ELEMENT_8710),
            Parser.parseLo3IndicatiePKVolledigGeconverteerdCode(elementen, Lo3ElementEnum.ELEMENT_8710, null, null));
    }

    @Test
    public void testParseWaardenMetOnderzoek() {
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3CategorieEnum categorie = Lo3CategorieEnum.CATEGORIE_01;
        final Map<Lo3ElementEnum, String> elementen = maakElementen();

        Assert.assertEquals(new Lo3Long(WAARDE_ELEMENT_0110, null), Parser.parseLo3Long(elementen, Lo3ElementEnum.ELEMENT_0110, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3Integer(WAARDE_ELEMENT_0120, onderzoek),
            Parser.parseLo3Integer(elementen, Lo3ElementEnum.ELEMENT_0120, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220, onderzoek),
            Parser.parseLo3AdellijkeTitelPredikaatCode(elementen, Lo3ElementEnum.ELEMENT_0220, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3GemeenteCode(WAARDE_ELEMENT_0320, onderzoek),
            Parser.parseLo3GemeenteCode(elementen, Lo3ElementEnum.ELEMENT_0320, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3LandCode(WAARDE_ELEMENT_0330, onderzoek),
            Parser.parseLo3LandCode(elementen, Lo3ElementEnum.ELEMENT_0330, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410, onderzoek),
            Parser.parseLo3Geslachtsaanduiding(elementen, Lo3ElementEnum.ELEMENT_0410, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3NationaliteitCode(WAARDE_ELEMENT_0510, onderzoek),
            Parser.parseLo3NationaliteitCode(elementen, Lo3ElementEnum.ELEMENT_0510, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3RedenOntbindingHuwelijkOfGpCode(WAARDE_ELEMENT_0740, onderzoek),
            Parser.parseLo3RedenOntbindingHuwelijkOfGpCode(elementen, Lo3ElementEnum.ELEMENT_0740, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3FunctieAdres(WAARDE_ELEMENT_1010, onderzoek),
            Parser.parseLo3FunctieAdres(elementen, Lo3ElementEnum.ELEMENT_1010, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3Huisnummer(WAARDE_ELEMENT_1120, onderzoek),
            Parser.parseLo3Huisnummer(elementen, Lo3ElementEnum.ELEMENT_1120, categorie, onderzoek));
        Assert.assertEquals(
            maakLo3Character(WAARDE_ELEMENT_1130, onderzoek),
            Parser.parseLo3Character(elementen, Lo3ElementEnum.ELEMENT_1130, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AanduidingHuisnummer(WAARDE_ELEMENT_1150, onderzoek),
            Parser.parseLo3AanduidingHuisnummer(elementen, Lo3ElementEnum.ELEMENT_1150, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3SoortVerbintenis(WAARDE_ELEMENT_1510, onderzoek),
            Parser.parseLo3SoortVerbintenis(elementen, Lo3ElementEnum.ELEMENT_1510, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AanduidingEuropeesKiesrecht(WAARDE_ELEMENT_3110, null),
            Parser.parseLo3AanduidingEuropeesKiesrecht(elementen, Lo3ElementEnum.ELEMENT_3110, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3IndicatieGezagMinderjarige(WAARDE_ELEMENT_3210, onderzoek),
            Parser.parseLo3IndicatieGezagMinderjarige(elementen, Lo3ElementEnum.ELEMENT_3210, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3IndicatieCurateleregister(WAARDE_ELEMENT_3310, onderzoek),
            Parser.parseLo3IndicatieCurateleregister(elementen, Lo3ElementEnum.ELEMENT_3310, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3SoortNederlandsReisdocument(WAARDE_ELEMENT_3510, onderzoek),
            Parser.parseLo3SoortNederlandsReisdocument(elementen, Lo3ElementEnum.ELEMENT_3510, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AutoriteitVanAfgifteNederlandsReisdocument(WAARDE_ELEMENT_3540, onderzoek),
            Parser.parseLo3AutoriteitVanAfgifteNederlandsReisdocument(elementen, Lo3ElementEnum.ELEMENT_3540, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(WAARDE_ELEMENT_3570, onderzoek),
            Parser.parseLo3AanduidingInhoudingVermissingNederlandsReisdocument(elementen, Lo3ElementEnum.ELEMENT_3570, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3Signalering(WAARDE_ELEMENT_3610, onderzoek),
            Parser.parseLo3Signalering(elementen, Lo3ElementEnum.ELEMENT_3610, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AanduidingUitgeslotenKiesrecht(WAARDE_ELEMENT_3810),
            Parser.parseLo3AanduidingUitgeslotenKiesrecht(elementen, Lo3ElementEnum.ELEMENT_3810, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AanduidingVerblijfstitelCode(WAARDE_ELEMENT_3910, onderzoek),
            Parser.parseLo3AanduidingVerblijfstitelCode(elementen, Lo3ElementEnum.ELEMENT_3910, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AanduidingNaamgebruikCode(WAARDE_ELEMENT_6110, onderzoek),
            Parser.parseLo3AanduidingNaamgebruikCode(elementen, Lo3ElementEnum.ELEMENT_6110, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AanduidingBijzonderNederlandschap(WAARDE_ELEMENT_6510, onderzoek),
            Parser.parseLo3AanduidingBijzonderNederlandschap(elementen, Lo3ElementEnum.ELEMENT_6510, categorie, onderzoek));
        Assert.assertEquals(maakDatum(WAARDE_ELEMENT_8510, onderzoek), Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_8510, categorie, onderzoek));

        // Categorie 7 elementen hebben nooit onderzoek
        Assert.assertEquals(
            new Lo3RedenOpschortingBijhoudingCode(WAARDE_ELEMENT_6720, null),
            Parser.parseLo3RedenOpschortingBijhoudingCode(elementen, Lo3ElementEnum.ELEMENT_6720, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3IndicatieGeheimCode(WAARDE_ELEMENT_7010, null),
            Parser.parseLo3IndicatieGeheimCode(elementen, Lo3ElementEnum.ELEMENT_7010, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3IndicatiePKVolledigGeconverteerdCode(WAARDE_ELEMENT_8710, null),
            Parser.parseLo3IndicatiePKVolledigGeconverteerdCode(elementen, Lo3ElementEnum.ELEMENT_8710, categorie, onderzoek));

        // Deze elementen worden niet in onderzoek gezet als de hele categorie in onderzoek is omdat dit administratief
        // gegevens zijn (3)
        Assert.assertEquals(
            new Lo3RedenNederlandschapCode(WAARDE_ELEMENT_6310, onderzoek),
            Parser.parseLo3RedenNederlandschapCode(elementen, Lo3ElementEnum.ELEMENT_6310, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AangifteAdreshouding(WAARDE_ELEMENT_7210, null),
            Parser.parseLo3AangifteAdreshouding(elementen, Lo3ElementEnum.ELEMENT_7210, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3IndicatieDocument(WAARDE_ELEMENT_7510, null),
            Parser.parseLo3IndicatieDocument(elementen, Lo3ElementEnum.ELEMENT_7510, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3IndicatieOnjuist(WAARDE_ELEMENT_8410, null),
            Parser.parseLo3IndicatieOnjuist(elementen, Lo3ElementEnum.ELEMENT_8410, categorie, onderzoek));
    }

    @Test
    public void testParseNullWaarden() {
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3CategorieEnum categorie = Lo3CategorieEnum.CATEGORIE_01;
        final Map<Lo3ElementEnum, String> elementen = Collections.emptyMap();

        Assert.assertNull(Parser.parseLo3Long(elementen, Lo3ElementEnum.ELEMENT_0110, categorie, onderzoek));
        Assert.assertEquals(new Lo3Integer(null, onderzoek), Parser.parseLo3Integer(elementen, Lo3ElementEnum.ELEMENT_0120, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AdellijkeTitelPredikaatCode(null, onderzoek),
            Parser.parseLo3AdellijkeTitelPredikaatCode(elementen, Lo3ElementEnum.ELEMENT_0220, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3GemeenteCode(null, onderzoek),
            Parser.parseLo3GemeenteCode(elementen, Lo3ElementEnum.ELEMENT_0320, categorie, onderzoek));
        Assert.assertEquals(new Lo3LandCode(null, onderzoek), Parser.parseLo3LandCode(elementen, Lo3ElementEnum.ELEMENT_0330, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3Geslachtsaanduiding(null, onderzoek),
            Parser.parseLo3Geslachtsaanduiding(elementen, Lo3ElementEnum.ELEMENT_0410, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3NationaliteitCode(null, onderzoek),
            Parser.parseLo3NationaliteitCode(elementen, Lo3ElementEnum.ELEMENT_0510, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3RedenOntbindingHuwelijkOfGpCode(null, onderzoek),
            Parser.parseLo3RedenOntbindingHuwelijkOfGpCode(elementen, Lo3ElementEnum.ELEMENT_0740, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3FunctieAdres(null, onderzoek),
            Parser.parseLo3FunctieAdres(elementen, Lo3ElementEnum.ELEMENT_1010, categorie, onderzoek));
        Assert.assertEquals(new Lo3Huisnummer(null, onderzoek), Parser.parseLo3Huisnummer(elementen, Lo3ElementEnum.ELEMENT_1120, categorie, onderzoek));
        Assert.assertEquals(maakLo3Character(null, onderzoek), Parser.parseLo3Character(elementen, Lo3ElementEnum.ELEMENT_1130, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AanduidingHuisnummer(null, onderzoek),
            Parser.parseLo3AanduidingHuisnummer(elementen, Lo3ElementEnum.ELEMENT_1150, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3SoortVerbintenis(null, onderzoek),
            Parser.parseLo3SoortVerbintenis(elementen, Lo3ElementEnum.ELEMENT_1510, categorie, onderzoek));
        Assert.assertNull(Parser.parseLo3AanduidingEuropeesKiesrecht(elementen, Lo3ElementEnum.ELEMENT_3110, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3IndicatieGezagMinderjarige(null, onderzoek),
            Parser.parseLo3IndicatieGezagMinderjarige(elementen, Lo3ElementEnum.ELEMENT_3210, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3IndicatieCurateleregister(null, onderzoek),
            Parser.parseLo3IndicatieCurateleregister(elementen, Lo3ElementEnum.ELEMENT_3310, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3SoortNederlandsReisdocument(null, onderzoek),
            Parser.parseLo3SoortNederlandsReisdocument(elementen, Lo3ElementEnum.ELEMENT_3510, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AutoriteitVanAfgifteNederlandsReisdocument(null, onderzoek),
            Parser.parseLo3AutoriteitVanAfgifteNederlandsReisdocument(elementen, Lo3ElementEnum.ELEMENT_3540, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(null, onderzoek),
            Parser.parseLo3AanduidingInhoudingVermissingNederlandsReisdocument(elementen, Lo3ElementEnum.ELEMENT_3570, categorie, onderzoek));
        Assert.assertEquals(new Lo3Signalering(null, onderzoek), Parser.parseLo3Signalering(elementen, Lo3ElementEnum.ELEMENT_3610, categorie, onderzoek));
        Assert.assertNull(Parser.parseLo3AanduidingUitgeslotenKiesrecht(elementen, Lo3ElementEnum.ELEMENT_3810, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AanduidingVerblijfstitelCode(null, onderzoek),
            Parser.parseLo3AanduidingVerblijfstitelCode(elementen, Lo3ElementEnum.ELEMENT_3910, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AanduidingNaamgebruikCode(null, onderzoek),
            Parser.parseLo3AanduidingNaamgebruikCode(elementen, Lo3ElementEnum.ELEMENT_6110, categorie, onderzoek));
        Assert.assertEquals(
            new Lo3AanduidingBijzonderNederlandschap(null, onderzoek),
            Parser.parseLo3AanduidingBijzonderNederlandschap(elementen, Lo3ElementEnum.ELEMENT_6510, categorie, onderzoek));
        Assert.assertEquals(maakDatum(null, onderzoek), Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_8510, categorie, onderzoek));
        Assert.assertEquals(new Lo3RedenNederlandschapCode(null,onderzoek),Parser.parseLo3RedenNederlandschapCode(elementen, Lo3ElementEnum.ELEMENT_6310, categorie, onderzoek));

        // Categorie 7 elementen hebben nooit onderzoek
        Assert.assertNull(Parser.parseLo3RedenOpschortingBijhoudingCode(elementen, Lo3ElementEnum.ELEMENT_6720, categorie, onderzoek));
        Assert.assertNull(Parser.parseLo3IndicatieGeheimCode(elementen, Lo3ElementEnum.ELEMENT_7010, categorie, onderzoek));
        Assert.assertNull(Parser.parseLo3IndicatiePKVolledigGeconverteerdCode(elementen, Lo3ElementEnum.ELEMENT_8710, categorie, onderzoek));

        // Deze elementen worden niet in onderzoek gezet als de hele categorie in onderzoek is omdat dit administratief
        // gegevens zijn (3)

        Assert.assertNull(Parser.parseLo3IndicatieOnjuist(elementen, Lo3ElementEnum.ELEMENT_8410, categorie, onderzoek));
    }

    @Test
    public void testNaamGebruikVulling() {
        final String eigenNaamCode = "E";
        final String partnerNaamCode = "P";
        final Map<Lo3ElementEnum, String> elementen = new HashMap<>();

        elementen.put(Lo3ElementEnum.ELEMENT_6110, eigenNaamCode);
        Assert.assertEquals(
            new Lo3AanduidingNaamgebruikCode(eigenNaamCode),
            Parser.parseLo3AanduidingNaamgebruikCode(elementen, Lo3ElementEnum.ELEMENT_6110, null, null));

        elementen.remove(Lo3ElementEnum.ELEMENT_6110);
        Assert.assertNull(Parser.parseLo3AanduidingNaamgebruikCode(elementen, Lo3ElementEnum.ELEMENT_6110, null, null));

        elementen.put(Lo3ElementEnum.ELEMENT_6110, partnerNaamCode);
        Assert.assertEquals(
            new Lo3AanduidingNaamgebruikCode(partnerNaamCode),
            Parser.parseLo3AanduidingNaamgebruikCode(elementen, Lo3ElementEnum.ELEMENT_6110, null, null));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.parser.AbstractParserTest#getGegevensInOnderzoek()
     */
    @Override
    String getGegevensInOnderzoek() {
        return WAARDE_ELEMENT_8310;
    }

    private Map<Lo3ElementEnum, String> maakElementen() {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_02, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0510, WAARDE_ELEMENT_0510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0610, WAARDE_ELEMENT_0610);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0620, WAARDE_ELEMENT_0620);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0630, WAARDE_ELEMENT_0630);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0710, WAARDE_ELEMENT_0710);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0720, WAARDE_ELEMENT_0720);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0730, WAARDE_ELEMENT_0730);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0740, WAARDE_ELEMENT_0740);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0810, WAARDE_ELEMENT_0810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0820, WAARDE_ELEMENT_0820);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0830, WAARDE_ELEMENT_0830);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1010, WAARDE_ELEMENT_1010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1120, WAARDE_ELEMENT_1120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1130, WAARDE_ELEMENT_1130);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1150, WAARDE_ELEMENT_1150);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1510, WAARDE_ELEMENT_1510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_2010, WAARDE_ELEMENT_2010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_2020, WAARDE_ELEMENT_2020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3110, WAARDE_ELEMENT_3110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3210, WAARDE_ELEMENT_3210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3310, WAARDE_ELEMENT_3310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3510, WAARDE_ELEMENT_3510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3540, WAARDE_ELEMENT_3540);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3570, WAARDE_ELEMENT_3570);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3580, WAARDE_ELEMENT_3580);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3610, WAARDE_ELEMENT_3610);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3710, WAARDE_ELEMENT_3710);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3810, WAARDE_ELEMENT_3810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3910, WAARDE_ELEMENT_3910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3920, WAARDE_ELEMENT_3920);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3930, WAARDE_ELEMENT_3930);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6110, WAARDE_ELEMENT_6110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6210, WAARDE_ELEMENT_6210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6310, WAARDE_ELEMENT_6310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6410, WAARDE_ELEMENT_6410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6510, WAARDE_ELEMENT_6510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6620, WAARDE_ELEMENT_6620);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6710, WAARDE_ELEMENT_6710);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6720, WAARDE_ELEMENT_6720);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6810, WAARDE_ELEMENT_6810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6910, WAARDE_ELEMENT_6910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7010, WAARDE_ELEMENT_7010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7110, WAARDE_ELEMENT_7110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7120, WAARDE_ELEMENT_7120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7210, WAARDE_ELEMENT_7210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7510, WAARDE_ELEMENT_7510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8010, WAARDE_ELEMENT_8010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8020, WAARDE_ELEMENT_8020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8710, WAARDE_ELEMENT_8710);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8810, WAARDE_ELEMENT_8810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8820, WAARDE_ELEMENT_8820);

        return categorie.getElementen();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.parser.AbstractParserTest#getCategorie()
     */
    @Override
    Lo3CategorieEnum getCategorie() {
        return null;
    }
}
