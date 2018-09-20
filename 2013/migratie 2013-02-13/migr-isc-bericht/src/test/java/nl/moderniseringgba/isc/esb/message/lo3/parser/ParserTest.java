/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.parser;

import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBezitBuitenlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LengteHouder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Voornamen;

import org.junit.Assert;
import org.junit.Test;

public class ParserTest {

    @Test
    public void testParseWaarden() {

        Assert.assertEquals(Character.valueOf('T'), Parser.parseCharacter("T"));
        Assert.assertEquals(Integer.valueOf(10), Parser.parseInteger("10"));
        Assert.assertEquals(new Lo3AanduidingBezitBuitenlandsReisdocument(Integer.valueOf(1)),
                Parser.parseLo3AanduidingBezitBuitenlandsReisdocument("1"));
        Assert.assertEquals(new Lo3AanduidingBijzonderNederlandschap("1"),
                Parser.parseLo3AanduidingBijzonderNederlandschap("1"));
        Assert.assertEquals(new Lo3AanduidingEuropeesKiesrecht(0), Parser.parseLo3AanduidingEuropeesKiesrecht("0"));
        Assert.assertEquals(new Lo3AanduidingHuisnummer("0"), Parser.parseLo3AanduidingHuisnummer("0"));
        Assert.assertEquals(new Lo3AanduidingInhoudingVermissingNederlandsReisdocument("0"),
                Parser.parseLo3AanduidingInhoudingVermissingNederlandsReisdocument("0"));
        Assert.assertEquals(new Lo3AanduidingNaamgebruikCode("0"), Parser.parseLo3AanduidingNaamgebruikCode("0"));
        Assert.assertEquals(new Lo3AanduidingUitgeslotenKiesrecht("1"),
                Parser.parseLo3AanduidingUitgeslotenKiesrecht("1"));
        Assert.assertEquals(new Lo3AanduidingVerblijfstitelCode("0"),
                Parser.parseLo3AanduidingVerblijfstitelCode("0"));
        Assert.assertEquals(new Lo3AangifteAdreshouding("0"), Parser.parseLo3AangifteAdreshouding("0"));
        Assert.assertEquals(new Lo3AdellijkeTitelPredikaatCode("H"), Parser.parseLo3AdellijkeTitelPredikaatCode("H"));
        Assert.assertEquals(new Lo3AutoriteitVanAfgifteNederlandsReisdocument("BZK"),
                Parser.parseLo3AutoriteitVanAfgifteNederlandsReisdocument("BZK"));
        Assert.assertEquals(new Lo3Datum(20121218), Parser.parseLo3Datum("20121218"));
        Assert.assertEquals(new Lo3Datumtijdstempel(20121218000000L),
                Parser.parseLo3Datumtijdstempel("20121218000000"));
        Assert.assertEquals(new Lo3FunctieAdres("T"), Parser.parseLo3FunctieAdres("T"));
        Assert.assertEquals(new Lo3GemeenteCode("1904"), Parser.parseLo3GemeenteCode("1904"));
        Assert.assertEquals(new Lo3Geslachtsaanduiding("M"), Parser.parseLo3Geslachtsaanduiding("M"));
        Assert.assertEquals(new Lo3Huisnummer(12), Parser.parseLo3Huisnummer("12"));
        Assert.assertEquals(new Lo3IndicatieCurateleregister(Integer.valueOf(1)),
                Parser.parseLo3IndicatieCurateleregister("1"));
        Assert.assertEquals(new Lo3IndicatieDocument(Integer.valueOf(1)), Parser.parseLo3IndicatieDocument("1"));
        Assert.assertEquals(new Lo3IndicatieGeheimCode(Integer.valueOf(0)), Parser.parseLo3IndicatieGeheimCode("0"));
        Assert.assertEquals(new Lo3IndicatieGezagMinderjarige("J"), Parser.parseLo3IndicatieGezagMinderjarige("J"));
        Assert.assertEquals(new Lo3IndicatieOnjuist("0"), Parser.parseLo3IndicatieOnjuist("0"));
        Assert.assertEquals(new Lo3IndicatiePKVolledigGeconverteerdCode("0"),
                Parser.parseLo3IndicatiePKVolledigGeconverteerdCode("0"));
        Assert.assertEquals(new Lo3LandCode("3010"), Parser.parseLo3LandCode("3010"));
        Assert.assertEquals(new Lo3LengteHouder(0), Parser.parseLo3LengteHouder("0"));
        Assert.assertEquals(new Lo3NationaliteitCode("3010"), Parser.parseLo3NationaliteitCode("3010"));
        Assert.assertEquals(new Lo3RedenNederlandschapCode("007"), Parser.parseLo3RedenNederlandschapCode("007"));
        Assert.assertEquals(new Lo3RedenOntbindingHuwelijkOfGpCode("S"),
                Parser.parseLo3RedenOntbindingHuwelijkOfGpCode("S"));
        Assert.assertEquals(new Lo3RedenOpschortingBijhoudingCode("M"),
                Parser.parseLo3RedenOpschortingBijhoudingCode("M"));
        Assert.assertEquals(new Lo3Signalering(Integer.valueOf(0)), Parser.parseLo3Signalering("0"));
        Assert.assertEquals(new Lo3SoortNederlandsReisdocument("Paspoort"),
                Parser.parseLo3SoortNederlandsReisdocument("Paspoort"));
        Assert.assertEquals(new Lo3SoortVerbintenis("Huwelijk"), Parser.parseLo3SoortVerbintenis("Huwelijk"));
        Assert.assertEquals(new Lo3Voornamen("Johannes"), Parser.parseLo3Voornamen("Johannes"));
        Assert.assertEquals(Long.valueOf("10"), Parser.parseLong("10"));

    }

    @Test
    public void testParseNullWaarden() {

        Assert.assertNull(Parser.parseCharacter(null));
        Assert.assertNull(Parser.parseInteger(null));
        Assert.assertNull(Parser.parseLo3AanduidingBezitBuitenlandsReisdocument(null));
        Assert.assertNull(Parser.parseLo3AanduidingBijzonderNederlandschap(null));
        Assert.assertNull(Parser.parseLo3AanduidingEuropeesKiesrecht(null));
        Assert.assertNull(Parser.parseLo3AanduidingHuisnummer(null));
        Assert.assertNull(Parser.parseLo3AanduidingInhoudingVermissingNederlandsReisdocument(null));
        Assert.assertNull(Parser.parseLo3AanduidingNaamgebruikCode(null));
        Assert.assertNull(Parser.parseLo3AanduidingUitgeslotenKiesrecht(null));
        Assert.assertNull(Parser.parseLo3AanduidingVerblijfstitelCode(null));
        Assert.assertNull(Parser.parseLo3AangifteAdreshouding(null));
        Assert.assertNull(Parser.parseLo3AdellijkeTitelPredikaatCode(null));
        Assert.assertNull(Parser.parseLo3AutoriteitVanAfgifteNederlandsReisdocument(null));
        Assert.assertNull(Parser.parseLo3Datum(null));
        Assert.assertNull(Parser.parseLo3Datumtijdstempel(null));
        Assert.assertNull(Parser.parseLo3FunctieAdres(null));
        Assert.assertNull(Parser.parseLo3GemeenteCode(null));
        Assert.assertNull(Parser.parseLo3Geslachtsaanduiding(null));
        Assert.assertNull(Parser.parseLo3Huisnummer(null));
        Assert.assertNull(Parser.parseLo3IndicatieCurateleregister(null));
        Assert.assertNull(Parser.parseLo3IndicatieDocument(null));
        Assert.assertNull(Parser.parseLo3IndicatieGeheimCode(null));
        Assert.assertNull(Parser.parseLo3IndicatieGezagMinderjarige(null));
        Assert.assertNull(Parser.parseLo3IndicatieOnjuist(null));
        Assert.assertNull(Parser.parseLo3IndicatiePKVolledigGeconverteerdCode(null));
        Assert.assertNull(Parser.parseLo3LandCode(null));
        Assert.assertNull(Parser.parseLo3LengteHouder(null));
        Assert.assertNull(Parser.parseLo3NationaliteitCode(null));
        Assert.assertNull(Parser.parseLo3RedenNederlandschapCode(null));
        Assert.assertNull(Parser.parseLo3RedenOntbindingHuwelijkOfGpCode(null));
        Assert.assertNull(Parser.parseLo3RedenOpschortingBijhoudingCode(null));
        Assert.assertNull(Parser.parseLo3Signalering(null));
        Assert.assertNull(Parser.parseLo3SoortNederlandsReisdocument(null));
        Assert.assertNull(Parser.parseLo3SoortVerbintenis(null));
        Assert.assertNull(Parser.parseLo3Voornamen(null));
        Assert.assertNull(Parser.parseLong(null));

    }

}
