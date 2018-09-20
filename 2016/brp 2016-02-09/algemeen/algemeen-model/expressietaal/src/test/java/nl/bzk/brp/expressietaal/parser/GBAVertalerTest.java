/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.parser.antlr.gba.GBAVoorwaarderegelLexer;
import nl.bzk.brp.expressietaal.parser.antlr.gba.GBAVoorwaarderegelParser;
import nl.bzk.brp.expressietaal.parser.gba.VoorwaarderegelVisitor;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.junit.Assert;
import org.junit.Test;


public class GBAVertalerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Test
    public void testNumRubriekwaarde() {
        parseNumRubriekWaarde("01.01.10", "persoon.identificatienummers.administratienummer");
        parseNumRubriekWaarde("12345", "12345");
        parseNumRubriekWaarde("01", "1");
        parseNumRubriekWaarde("0003 OFVGL 0005 OFVGL 0007", "{3, 5, 7}");
    }

    @Test
    public void testNumVergelijking() {
        testVoorwaarde("08.09.10 GA1 0003 OFVGL 0005 OFVGL 0007", "persoon.bijhouding.bijhoudingspartij IN {3, 5, 7}");
        testVoorwaarde("08.09.10 OGAA 0003 ENVGL 0005 ENVGL 0007",
                "NIET (persoon.bijhouding.bijhoudingspartij IN {3, 5, 7})");
        testVoorwaarde("01.01.10 GA1 123456789", "persoon.identificatienummers.administratienummer = 123456789");
        testVoorwaarde("01.01.10 OGA1 123456789", "persoon.identificatienummers.administratienummer <> 123456789");
        testVoorwaarde("04.05.10 GA1 0001", "ER_IS(persoon.nationaliteiten, v, v.nationaliteit = 1)");
        testVoorwaarde("04.05.10 OGA1 0001", "ER_IS(persoon.nationaliteiten, v, v.nationaliteit <> 1)");
        testVoorwaarde("04.05.10 OGAA 0001", "ALLE(persoon.nationaliteiten, v, v.nationaliteit <> 1)");
        testVoorwaarde(
                "08.09.10 GA1 0197 OFVGL 0200 OFVGL 0213 OFVGL 0222 OFVGL 0230 OFVGL 0232 OFVGL 0233 OFVGL 0243 "
                    + "OFVGL 0244 OFVGL 0246 OFVGL 0262 OFVGL 0269 OFVGL 0273 OFVGL 0285 OFVGL 0294 OFVGL 0301 "
                    + "OFVGL 0302 OFVGL 1509 OFVGL 1586 OFVGL 1859 OFVGL 1876 OFVGL 1955",
                "persoon.bijhouding.bijhoudingspartij IN {197, 200, 213, 222, 230, 232, 233, 243, 244, 246, 262, 269, 273, "
                    + "285, 294, 301, 302, 1509, 1586, 1859, 1876, 1955}");
    }

    @Test
    public void testAlfanumRubriekwaarde() {
        parseAlfanumRubriekWaarde("01.04.10", "persoon.geslachtsaanduiding.geslachtsaanduiding");
        parseAlfanumRubriekWaarde("\"M\"", "\"M\"");
        parseAlfanumRubriekWaarde("\"Blabla001\"", "\"Blabla001\"");
        parseAlfanumRubriekWaarde("\"9403/*\"", "\"9403*\"");
    }

    @Test
    public void testAlfanumVergelijking() {
        testVoorwaarde("01.04.10 GA1 \"V\"", "persoon.geslachtsaanduiding.geslachtsaanduiding = \"V\"");
        testVoorwaarde("(01.04.10 GA1 \"V\")", "persoon.geslachtsaanduiding.geslachtsaanduiding = \"V\"");
        testVoorwaarde("08.11.60 GA1 \"7858/*\" OFVGL \"7859/*\" OFVGL \"7872/*\" OFVGL \"7875/*\"",
                "ER_IS(persoon.adressen, v, v.postcode IN% {\"7858*\", \"7859*\", \"7872*\", \"7875*\"})");
    }

    @Test
    public void testDatRubriekwaarde() {
        parseDatRubriekWaarde("19700102", "1970/01/02");
        parseDatRubriekWaarde("20000301 + 0001", "2000/03/01 + ^1/0/0");
        parseDatRubriekWaarde("20000301 + 000101", "2000/03/01 + ^1/1/0");
        parseDatRubriekWaarde("20000301 - 00010203", "2000/03/01 - ^1/2/3");
        parseDatRubriekWaarde("20000301 + 00000004", "2000/03/01 + ^0/0/4");
    }

    @Test
    public void testDatVergelijking() {
        testVoorwaarde("01.03.10 KDOG1 19800301", "persoon.geboorte.datum <= 1980/03/01");
        testVoorwaarde("01.03.10 GDOG1 19800301", "persoon.geboorte.datum >= 1980/03/01");
        testVoorwaarde("01.03.10 KD1 19800301", "persoon.geboorte.datum < 1980/03/01");
        testVoorwaarde("01.03.10 GD1 19800301", "persoon.geboorte.datum > 1980/03/01");
        testVoorwaarde("01.03.10 GA1 19800301", "persoon.geboorte.datum = 1980/03/01");
        testVoorwaarde("01.03.10 OGA1 19800301", "persoon.geboorte.datum <> 1980/03/01");
        testVoorwaarde("08.10.30 GD1 19800301", "ER_IS(persoon.adressen, v, v.datum_aanvang_adreshouding > 1980/03/01)");
        testVoorwaarde("08.10.30 GDA 19800301", "ALLE(persoon.adressen, v, v.datum_aanvang_adreshouding > 1980/03/01)");
        testVoorwaarde("08.10.30 KD1 19800301", "ER_IS(persoon.adressen, v, v.datum_aanvang_adreshouding < 1980/03/01)");
        testVoorwaarde("08.10.30 KDA 19800301", "ALLE(persoon.adressen, v, v.datum_aanvang_adreshouding < 1980/03/01)");
        testVoorwaarde("08.10.30 GA1 19800301", "ER_IS(persoon.adressen, v, v.datum_aanvang_adreshouding = 1980/03/01)");
        testVoorwaarde("(01.03.10 KDOG1 19.89.20 - 00290000)", "persoon.geboorte.datum <= VANDAAG(-29)");
        testVoorwaarde("(01.03.10 KDOG1 19.89.20 - 0029)", "JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-29))");
    }

    @Test
    public void testVandaagVergelijking() {
        testVoorwaarde("01.03.10 KDOG1 19.89.30 - 0029", "JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-29))");
        testVoorwaarde("01.03.10 KDOG1 19.89.30 - 00290000", "persoon.geboorte.datum <= VANDAAG(-29)");
        testVoorwaarde("01.03.10 KDOG1 19.89.30 - 00000000", "persoon.geboorte.datum <= VANDAAG(0)");
    }

    @Test
    public void testVoorkomenVraag() {
        testVoorwaarde("KNV 07.67.10", "NIET IS_OPGESCHORT(persoon)");
        testVoorwaarde("KV 07.67.10", "IS_OPGESCHORT(persoon)");
        testVoorwaarde("KNV 01.03.10", "IS_NULL(persoon.geboorte.datum)");
        testVoorwaarde("KV 01.03.10", "NIET IS_NULL(persoon.geboorte.datum)");
    }

    @Test
    public void testEnOf() {
        testVoorwaarde("(01.04.10 GA1 \"V\") OFVWD (01.04.10 GA1 \"M\")",
                "persoon.geslachtsaanduiding.geslachtsaanduiding = \"V\" OF persoon.geslachtsaanduiding.geslachtsaanduiding = \"M\"");
        testVoorwaarde("(01.04.10 GA1 \"V\") ENVWD (01.03.10 KDOG1 19.89.20 - 0029)",
                "persoon.geslachtsaanduiding.geslachtsaanduiding = \"V\" EN JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-29))");
        testVoorwaarde(
                "((01.04.10 GA1 \"V\") ENVWD (01.03.10 KDOG1 19.89.20 - 0029)) OFVWD ((01.04.10 GA1 \"M\") ENVWD "
                    + "(01.03.10 KDOG1 19.89.20 - 0054))",
                "persoon.geslachtsaanduiding.geslachtsaanduiding = \"V\" EN JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-29)) OF persoon"
                    + ".geslachtsaanduiding.geslachtsaanduiding = \"M\" EN JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-54))");
    }

    @Test
    public void testWildcard() {
        testVoorwaarde("(08.09.10 GA1 0106 ENVWD 08.11.60 GA1 \"9408/*\")",
                "persoon.bijhouding.bijhoudingspartij = 106 EN ER_IS(persoon.adressen, v, v.postcode %= \"9408*\")");
    }

    @Test
    public void testBulk() {
        testVoorwaarde(
                "(((01.04.10 GA1 \"V\") ENVWD (01.03.10 KDOG1 19.89.20 - 0029)) OFVWD ((01.04.10 GA1 \"M\") ENVWD "
                    + "(01.03.10 KDOG1 19.89.20 - 0054))) ENVWD (08.09.10 GA1 0003 OFVGL 0005 OFVGL 0007 OFVGL "
                    + "0009 "
                    + "OFVGL 0010 OFVGL 0014 OFVGL 0015 OFVGL 0017 OFVGL 0018 OFVGL 0022 OFVGL 0024 OFVGL 0025 "
                    + "OFVGL 0037 OFVGL 0040 OFVGL 0047 OFVGL 0048 OFVGL 0051 OFVGL 0053 OFVGL 0055 OFVGL 0056 "
                    + "OFVGL 0058 OFVGL 0059 OFVGL 0060 OFVGL 0063 OFVGL 0070 OFVGL 0072 OFVGL 0074 OFVGL 0079 "
                    + "OFVGL 0080 OFVGL 0081 OFVGL 0082 OFVGL 0085 OFVGL 0086 OFVGL 0088 OFVGL 0090 OFVGL 0093 "
                    + "OFVGL 0096 OFVGL 0098 OFVGL 0106 OFVGL 0109 OFVGL 0114 OFVGL 0118 OFVGL 0119 OFVGL 0140 "
                    + "OFVGL 0653 OFVGL 0737 OFVGL 0765 OFVGL 1651 OFVGL 1663 OFVGL 1680 OFVGL 1681 OFVGL 1690 "
                    + "OFVGL 1699 OFVGL 1701 OFVGL 1722 OFVGL 1730 OFVGL 1731 OFVGL 1891 OFVGL 1895 OFVGL 1900 "
                    + "OFVGL 1908 OFVGL 1987) ENVWD KNV 07.67.10",
                "(persoon.geslachtsaanduiding.geslachtsaanduiding = \"V\" EN JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-29)) OF persoon"
                    + ".geslachtsaanduiding.geslachtsaanduiding = \"M\" EN JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-54))) EN (persoon"
                    + ".bijhouding.bijhoudingspartij IN {3, 5, 7, 9, 10, 14, 15, 17, 18, 22, 24, 25, 37, 40, 47, "
                    + "48, 51, 53,"
                    + " 55, 56, 58, 59, 60, 63, 70, 72, 74, 79, 80, 81, 82, 85, 86, 88, 90, 93, 96, 98, 106, 109, "
                    + "114, 118, 119, 140, 653, 737, 765, 1651, 1663, 1680, 1681, 1690, 1699, 1701, 1722, 1730, "
                    + "1731, 1891, 1895, 1900, 1908, 1987} EN NIET IS_OPGESCHORT(persoon))");
        testVoorwaarde(
                "(((01.04.10 GA1 \"V\") ENVWD (01.03.10 KDOG1 19.89.20 - 0029)) OFVWD ((01.04.10 GA1 \"M\") "
                    + "ENVWD (01.03.10 KDOG1 19.89.20 - 0054))) ENVWD (08.09.10 GA1 0034 OFVGL 0050 OFVGL 0171 "
                    + "OFVGL 0184 OFVGL 0303 OFVGL 0307 OFVGL 0308 OFVGL 0310 OFVGL 0312 OFVGL 0313 OFVGL 0317 OFVGL "
                    + "0321 OFVGL 0327 OFVGL 0331 OFVGL 0335 OFVGL 0339 OFVGL 0340 OFVGL 0342 OFVGL 0344 OFVGL "
                    + "0345 OFVGL 0351 OFVGL 0352 OFVGL 0353 OFVGL 0355 OFVGL 0356 OFVGL 0358 OFVGL 0361 OFVGL "
                    + "0362 OFVGL 0363 OFVGL 0365 OFVGL 0370 OFVGL 0373 OFVGL 0375 OFVGL 0376 OFVGL 0377 OFVGL "
                    + "0381 OFVGL 0383 OFVGL 0384 OFVGL 0385 OFVGL 0388 OFVGL 0392 OFVGL 0393 OFVGL 0394 OFVGL "
                    + "0396 OFVGL 0397 OFVGL 0398 OFVGL 0399 OFVGL 0400 OFVGL 0402 OFVGL 0405 OFVGL 0406 OFVGL "
                    + "0415 OFVGL 0416 OFVGL 0417 OFVGL 0420 OFVGL 0424 OFVGL 0425 OFVGL 0431 OFVGL 0432 OFVGL "
                    + "0437 OFVGL 0439 OFVGL 0441 OFVGL 0448 OFVGL 0450 OFVGL 0451 OFVGL 0453 OFVGL 0457 OFVGL "
                    + "0458 OFVGL 0473 OFVGL 0478 OFVGL 0479 OFVGL 0498 OFVGL 0532 OFVGL 0589 OFVGL 0620 OFVGL "
                    + "0632 OFVGL 0736 OFVGL 0852 OFVGL 0880 OFVGL 0995 OFVGL 1581 OFVGL 1598 OFVGL 1696 OFVGL "
                    + "1904 OFVGL 1911) ENVWD KNV 07.67.10",
                "(persoon.geslachtsaanduiding.geslachtsaanduiding = \"V\" EN JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-29)) OF persoon"
                    + ".geslachtsaanduiding.geslachtsaanduiding = \"M\" EN JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-54))) EN (persoon"
                    + ".bijhouding.bijhoudingspartij IN {34, 50, 171, 184, 303, 307, 308, 310, 312, 313, 317, 321, 327, 331,"
                    + " 335, 339, 340, 342, 344, 345, 351, 352, 353, 355, 356, 358, 361, 362, 363, 365, 370, 373, "
                    + "375, 376, 377, 381, 383, 384, 385, 388, 392, 393, 394, 396, 397, 398, 399, 400, 402, 405, "
                    + "406, 415, 416, 417, 420, 424, 425, 431, 432, 437, 439, 441, 448, 450, 451, 453, 457, 458, "
                    + "473, 478, 479, 498, 532, 589, 620, 632, 736, 852, 880, 995, 1581, 1598, 1696, 1904, "
                    + "1911} EN NIET IS_OPGESCHORT(persoon))");

        testVoorwaarde(
                "((01.04.10 GA1 \"V\") ENVWD (01.03.10 KDOG1 19.89.30 - 0029)) OFVWD ((01.04.10 GA1 \"M\") ENVWD (01.03.10 KDOG1 19.89.30 - 0054))",
                "persoon.geslachtsaanduiding.geslachtsaanduiding = \"V\" EN JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-29)) OF persoon"
                    + ".geslachtsaanduiding.geslachtsaanduiding = \"M\" EN JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-54))");

        testVoorwaarde(
                "08.09.10 GA1 0003 OFVGL 0005 OFVGL 0007 OFVGL 0009 OFVGL 0010 OFVGL 0014 OFVGL 0015 OFVGL 0017 OFVGL 0018"
                    + " OFVGL 0022 OFVGL 0024 OFVGL 0025 OFVGL 0037 OFVGL 0040 OFVGL 0047 OFVGL 0048 OFVGL 0051 OFVGL 0053 OFVGL 0055"
                    + " OFVGL 0056 OFVGL 0058 OFVGL 0059 OFVGL 0060 OFVGL 0063 OFVGL 0070 OFVGL 0072 OFVGL 0074 OFVGL 0079 OFVGL 0080"
                    + " OFVGL 0081 OFVGL 0082 OFVGL 0085 OFVGL 0086 OFVGL 0088 OFVGL 0090 OFVGL 0093 OFVGL 0096 OFVGL 0098 OFVGL 0140"
                    + " OFVGL 0653 OFVGL 0737 OFVGL 0765 OFVGL 1651 OFVGL 1663 OFVGL 1680 OFVGL 1699 OFVGL 1722 OFVGL 1730 OFVGL 1891"
                    + " OFVGL 1895 OFVGL 1900 OFVGL 1908 OFVGL 1987",
                "persoon.bijhouding.bijhoudingspartij IN {3, 5, 7, 9, 10, 14, 15, 17, 18, 22, 24, 25, 37, 40, 47, 48, 51, 53,"
                    + " 55, 56, 58, 59, 60, 63, 70, 72, 74, 79, 80, 81, 82, 85, 86, 88, 90, 93, 96, 98, 140, 653, "
                    + "737, 765, 1651, 1663, 1680, 1699, 1722, 1730, 1891, 1895, 1900, 1908, 1987}");

        testVoorwaarde("08.09.10 GA1 0106 ENVWD 08.11.60 GA1 " + "\"9401/*\" OFVGL \"9402/*\""
            + " OFVGL \"9403/*\" OFVGL \"9404/*\" OFVGL \"9405/*\" OFVGL \"9406/*\" OFVGL \"9407/*\" "
            + "OFVGL \"9409/*\" OFVGL \"9486/*\" OFVGL"
            + " \"9487/*\" OFVGL \"9488/*\" OFVGL \"9489/*\" OFVGL \"9492/*\"",
                "persoon.bijhouding.bijhoudingspartij = 106 EN ER_IS(persoon.adressen, v, v.postcode IN% {\"9401*\", \"9402*\","
                    + " \"9403*\", \"9404*\", \"9405*\", \"9406*\", \"9407*\", \"9409*\", \"9486*\", \"9487*\", "
                    + "\"9488*\", \"9489*\", \"9492*\"})");

        testVoorwaarde("(08.09.10 GA1 0109 ENVWD 08.11.60 GA1 \"7849/*\")",
                "persoon.bijhouding.bijhoudingspartij = 109 EN ER_IS(persoon.adressen, v, v.postcode %= \"7849*\")");

        testVoorwaarde("(08.09.10 GA1 0109 ENVWD 08.11.60 OGA1 \"7849/*\")",
                "persoon.bijhouding.bijhoudingspartij = 109 EN ER_IS(persoon.adressen, v, NIET (v.postcode %= \"7849*\"))");

        testVoorwaarde(
                "(08.09.10 GA1 0114 ENVWD 08.11.60 GA1 \"7826/*\" OFVGL \"7831/*\" OFVGL \"7881/*\" OFVGL \"7884/*\" "
                    + "OFVGL \"7889/*\" OFVGL \"7895/*\")",
                "persoon.bijhouding.bijhoudingspartij = 114 EN ER_IS(persoon.adressen, v, v.postcode IN% {\"7826*\", "
                    + "\"7831*\", \"7881*\", \"7884*\", \"7889*\", \"7895*\"})");

        testVoorwaarde(
                "(08.09.10 GA1 1681 ENVWD 08.11.60 GA1 \"7858/*\" OFVGL \"7859/*\" OFVGL \"7872/*\" OFVGL \"7875/*\" OFVGL"
                    + " \"7876/*\" OFVGL \"7877/*\" OFVGL \"952/*\" OFVGL \"953/*\" OFVGL \"9564/*\" OFVGL "
                    + "\"957/*\")",
                "persoon.bijhouding.bijhoudingspartij = 1681 EN ER_IS(persoon.adressen, v, v.postcode IN% {\"7858*\", "
                    + "\"7859*\", \"7872*\", \"7875*\", \"7876*\", \"7877*\", \"952*\", \"953*\", \"9564*\", \"957*\"})");

        testVoorwaarde("(08.09.10 GA1 1731 ENVWD"
            + " 08.11.60 GA1 \"9414/*\") ENVWD 08.10.10 OGA1 \"B\" ENVWD KNV 07.67.10",
                "(persoon.bijhouding.bijhoudingspartij = 1731 EN ER_IS(persoon.adressen, v, v.postcode %= \"9414*\")) EN (ER_IS"
                    + "(persoon.adressen, v, v.soort <> \"B\") EN NIET IS_OPGESCHORT(persoon))");

        testVoorwaarde(
                "((08.09.10 GA1 0003 OFVGL 0005 OFVGL 0007 OFVGL 0009 OFVGL 0010 OFVGL 0014 OFVGL 0015 OFVGL 0017 "
                    + "OFVGL 0018"
                    + " OFVGL 0022 OFVGL 0024 OFVGL 0025 OFVGL 0037 OFVGL 0040 OFVGL 0047 OFVGL 0048 OFVGL 0051 "
                    + "OFVGL 0053 OFVGL 0055"
                    + " OFVGL 0056 OFVGL 0058 OFVGL 0059 OFVGL 0060 OFVGL 0063 OFVGL 0070 OFVGL 0072 OFVGL 0074 "
                    + "OFVGL 0079 OFVGL 0080"
                    + " OFVGL 0081 OFVGL 0082 OFVGL 0085 OFVGL 0086 OFVGL 0088 OFVGL 0090 OFVGL 0093 OFVGL 0096 "
                    + "OFVGL 0098 OFVGL 0140"
                    + " OFVGL 0653 OFVGL 0737 OFVGL 0765 OFVGL 1651 OFVGL 1663 OFVGL 1680 OFVGL 1699 OFVGL 1722 "
                    + "OFVGL 1730 OFVGL 1891"
                    + " OFVGL 1895 OFVGL 1900 OFVGL 1908 OFVGL 1987) OFVWD (08.09.10 GA1 0106 ENVWD 08.11.60 GA1 "
                    + "\"9401/*\" OFVGL \"9402/*\""
                    + " OFVGL \"9403/*\" OFVGL \"9404/*\" OFVGL \"9405/*\" OFVGL \"9406/*\" OFVGL \"9407/*\" "
                    + "OFVGL \"9409/*\" OFVGL \"9486/*\" OFVGL"
                    + " \"9487/*\" OFVGL \"9488/*\" OFVGL \"9489/*\" OFVGL \"9492/*\") OFVWD (08.09.10 GA1 0109 "
                    + "ENVWD 08.11.60 GA1 \"7849/*\") OFVWD"
                    + " (08.09.10 GA1 0114 ENVWD 08.11.60 GA1 \"7826/*\" OFVGL \"7831/*\" OFVGL \"7881/*\" OFVGL "
                    + "\"7884/*\" OFVGL \"7889/*\" OFVGL"
                    + " \"7895/*\") OFVWD (08.09.10 GA1 1681 ENVWD 08.11.60 GA1 \"7858/*\" OFVGL \"7859/*\" OFVGL "
                    + "\"7872/*\" OFVGL \"7875/*\" OFVGL"
                    + " \"7876/*\" OFVGL \"7877/*\" OFVGL \"952/*\" OFVGL \"953/*\" OFVGL \"9564/*\" OFVGL "
                    + "\"957/*\") OFVWD (08.09.10 GA1 1731 ENVWD"
                    + " 08.11.60 GA1 \"9414/*\")) ENVWD 08.10.10 OGA1 \"B\" ENVWD KNV 07.67.10",
                "(persoon.bijhouding.bijhoudingspartij IN {3, 5, 7, 9, 10, 14, 15, 17, 18, 22, 24, 25, 37, 40, 47, 48, 51, 53,"
                    + " 55, 56, 58, 59, 60, 63, 70, 72, 74, 79, 80, 81, 82, 85, 86, 88, 90, 93, 96, 98, 140, 653, "
                    + "737, 765, 1651, 1663, 1680, 1699, 1722, 1730, 1891, 1895, 1900, 1908, "
                    + "1987} OF (persoon.bijhouding.bijhoudingspartij = 106 EN ER_IS(persoon.adressen, v, "
                    + "v.postcode IN% {\"9401*\", \"9402*\", \"9403*\", \"9404*\", \"9405*\", \"9406*\", \"9407*\", "
                    + "\"9409*\", \"9486*\", \"9487*\", \"9488*\", \"9489*\", "
                    + "\"9492*\"}) OF (persoon.bijhouding.bijhoudingspartij = 109 EN ER_IS(persoon.adressen, v, "
                    + "v.postcode %= \"7849*\") OF (persoon.bijhouding.bijhoudingspartij = 114 EN ER_IS(persoon.adressen, v, "
                    + "v.postcode IN% {\"7826*\", \"7831*\", \"7881*\", \"7884*\", \"7889*\", "
                    + "\"7895*\"}) OF (persoon.bijhouding.bijhoudingspartij = 1681 EN ER_IS(persoon.adressen, v, "
                    + "v.postcode IN% {\"7858*\", \"7859*\", \"7872*\", \"7875*\", \"7876*\", \"7877*\", \"952*\", "
                    + "\"953*\", \"9564*\", \"957*\"}) OF persoon.bijhouding.bijhoudingspartij = 1731 EN ER_IS(persoon"
                    + ".adressen, v, v.postcode %= \"9414*\")))))) EN (ER_IS(persoon.adressen, v, "
                    + "v.soort <> \"B\") EN NIET IS_OPGESCHORT(persoon))");

        testVoorwaarde(
                "((08.09.10 GA1 0034 OFVGL 0050 OFVGL 0109 OFVGL 0118 OFVGL 0119 OFVGL 0141 OFVGL 0147 OFVGL 0148"
                    + " OFVGL 0150 OFVGL 0153 OFVGL 0158 OFVGL 0160 OFVGL 0163 OFVGL 0164 OFVGL 0166 OFVGL 0168 "
                    + "OFVGL 0171 OFVGL 0173 OFVGL 0175"
                    + " OFVGL 0177 OFVGL 0180 OFVGL 0183 OFVGL 0184 OFVGL 0189 OFVGL 0193 OFVGL 0196 OFVGL 0197 "
                    + "OFVGL 0200 OFVGL 0203"
                    + " OFVGL 0213 OFVGL 0221 OFVGL 0222 OFVGL 0226 OFVGL 0228 OFVGL 0230 OFVGL 0232 OFVGL 0233 "
                    + "OFVGL 0243 OFVGL 0244"
                    + " OFVGL 0246 OFVGL 0262 OFVGL 0267 OFVGL 0269 OFVGL 0273 OFVGL 0274 OFVGL 0275 OFVGL 0277 "
                    + "OFVGL 0279 OFVGL 0285"
                    + " OFVGL 0289 OFVGL 0293 OFVGL 0294 OFVGL 0299 OFVGL 0301 OFVGL 0302 OFVGL 0303 OFVGL 0307 "
                    + "OFVGL 0308 OFVGL 0313"
                    + " OFVGL 0317 OFVGL 0327 OFVGL 0339 OFVGL 0342 OFVGL 0345 OFVGL 0351 OFVGL 0995 OFVGL 1509 "
                    + "OFVGL 1586 OFVGL 1690"
                    + " OFVGL 1700 OFVGL 1701 OFVGL 1708 OFVGL 1731 OFVGL 1735 OFVGL 1742 OFVGL 1773 OFVGL 1774 "
                    + "OFVGL 1859 OFVGL 1876"
                    + " OFVGL 1896 OFVGL 1955) OFVWD (08.09.10 GA1 0082 ENVWD 08.11.60 GA1 \"8531/*\") OFVWD (08"
                    + ".09.10 GA1 0106"
                    + " ENVWD 08.11.60 GA1 \"9408/*\") OFVWD (08.09.10 GA1 0114 ENVWD 08.11.60 GA1 \"776/*\" "
                    + "OFVGL \"781/*\" OFVGL \"7821/*\" OFVGL"
                    + " \"7822/*\" OFVGL \"7823/*\" OFVGL \"7824/*\" OFVGL \"7825/*\" OFVGL \"7827/*\" OFVGL "
                    + "\"7828/*\" OFVGL \"7833/*\" OFVGL \"7844/*\""
                    + " OFVGL \"7885/*\" OFVGL \"7887/*\" OFVGL \"7891/*\" OFVGL \"7892/*\" OFVGL \"7894/*\") "
                    + "OFVWD (08.09.10 GA1 0202 ENVWD 08.11.60 GA1"
                    + " \"681/*\" OFVGL \"682/*\") OFVWD (08.09.10 GA1 0340 ENVWD 08.11.60 GA1 \"3911/*\") OFVWD "
                    + "(08.09.10 GA1 1581"
                    + " ENVWD 08.11.60 GA1 \"3951/*\" OFVGL \"3953/*\") OFVWD (08.09.10 GA1 1681 ENVWD 08.11.60 "
                    + "GA1 \"7871/*\" OFVGL \"7873/*\" OFVGL"
                    + " \"7874/*\")) ENVWD 08.10.10 OGA1 \"B\" ENVWD KNV 07.67.10",
                "(persoon.bijhouding.bijhoudingspartij IN {34, 50, 109, 118, 119, 141, 147, 148, 150, 153, 158, 160, 163, 164, 166, 168, 171, 173, 175, "
                    + "177, 180, 183, 184, 189, 193, 196, 197, 200, 203, 213, 221, 222, 226, 228, 230, 232, 233, 243, 244, 246, 262, 267, 269, 273, 274, "
                    + "275, 277, 279, 285, 289, 293, 294, 299, 301, 302, 303, 307, 308, 313, 317, 327, 339, 342, 345, 351, 995, 1509, 1586, 1690, 1700, "
                    + "1701, 1708, 1731, 1735, 1742, 1773, 1774, 1859, 1876, 1896, 1955} OF (persoon.bijhouding.bijhoudingspartij = 82 EN "
                    + "ER_IS(persoon.adressen, v, v.postcode %= \"8531*\") OF (persoon.bijhouding.bijhoudingspartij = 106 EN ER_IS(persoon.adressen, v, "
                    + "v.postcode %= \"9408*\") OF (persoon.bijhouding.bijhoudingspartij = 114 EN ER_IS(persoon.adressen, v, v.postcode IN% {\"776*\", "
                    + "\"781*\", \"7821*\", \"7822*\", \"7823*\", \"7824*\", \"7825*\", \"7827*\", \"7828*\", \"7833*\", \"7844*\", \"7885*\", \"7887*\", "
                    + "\"7891*\", \"7892*\", \"7894*\"}) OF (persoon.bijhouding.bijhoudingspartij = 202 EN ER_IS(persoon.adressen, v, v.postcode IN% {"
                    + "\"681*\", \"682*\"}) OF (persoon.bijhouding.bijhoudingspartij = 340 EN ER_IS(persoon.adressen, v, v.postcode %= \"3911*\") OF "
                    + "(persoon.bijhouding.bijhoudingspartij = 1581 EN ER_IS(persoon.adressen, v, v.postcode IN% {\"3951*\", \"3953*\"}) OF "
                    + "persoon.bijhouding.bijhoudingspartij = 1681 EN ER_IS(persoon.adressen, v, v.postcode IN% {\"7871*\", \"7873*\", \"7874*\"}))))))))"
                    + " EN (ER_IS(persoon.adressen, v, v.soort <> \"B\") EN NIET IS_OPGESCHORT(persoon))");

        testVoorwaarde(
                "((08.09.10 GA1 0209 OFVGL 0214 OFVGL 0216 OFVGL 0225 OFVGL 0236 OFVGL 0241 OFVGL 0252 OFVGL 0263 "
                    + "OFVGL 0265 "
                    + "OFVGL 0268 OFVGL 0281 OFVGL 0282 OFVGL 0296 OFVGL 0297 OFVGL 0304 OFVGL 0352 OFVGL 0482 "
                    + "OFVGL 0512 OFVGL 0523 "
                    + "OFVGL 0545 OFVGL 0590 OFVGL 0610 OFVGL 0620 OFVGL 0668 OFVGL 0689 OFVGL 0707 OFVGL 0733 "
                    + "OFVGL 0738 OFVGL 0870  "
                    + "OFVGL 0874 OFVGL 1705 OFVGL 1734 OFVGL 1740 OFVGL 1927) OFVWD (08.09.10 GA1 0202 ENVWD 08"
                    + ".11.60 GA1 " + "\"683/*\" OFVGL \"684/*\")) ENVWD 08.10.10 OGA1 \"B\" ENVWD KNV 07.67.10",
                "(persoon.bijhouding.bijhoudingspartij IN {209, 214, 216, 225, 236, 241, 252, 263, 265, 268, 281, 282, 296, 297, 304, 352, 482, 512, 523, "
                    + "545, 590, 610, 620, 668, 689, 707, 733, 738, 870, 874, 1705, 1734, 1740, 1927} OF persoon.bijhouding.bijhoudingspartij = 202 EN "
                    + "ER_IS(persoon.adressen, v, v.postcode IN% {\"683*\", \"684*\"})) EN (ER_IS(persoon.adressen, v, v.soort <> \"B\") EN NIET "
                    + "IS_OPGESCHORT(persoon))");

        testVoorwaarde(
                "          ((08.09.10 GA1 0377 OFVGL 0392 OFVGL 0393 OFVGL 0394 OFVGL 0397 OFVGL 0473 OFVGL "
                    + "0484 OFVGL 0499 OFVGL 0513 OFVGL 0534 OFVGL 0537 OFVGL 0546 OFVGL 0547 OFVGL 0553 OFVGL 0569 OFVGL "
                    + "0575 OFVGL 0576 OFVGL 0579 OFVGL 0626 OFVGL 0629 OFVGL 0638 OFVGL 1525 OFVGL 1672 OFVGL 1884 OFVGL "
                    + "1901) OFVWD (08.09.10 GA1 0358 ENVWD 08.11.60 GA1 \"1431/*\" OFVGL \"1432/*\") OFVWD (08.09.10 GA1 "
                    + "0363 ENVWD 08.11.60 GA1 \"1014/*\" OFVGL \"1043/*\" OFVGL \"1046/*\" OFVGL \"1056/*\" OFVGL "
                    + "\"1057/*\" OFVGL \"1058/*\" OFVGL \"1059/*\" OFVGL \"106/*\" OFVGL \"1076/*\" OFVGL \"1081/*\") "
                    + "OFVWD( 08.09.10 GA1 0453 ENVWD 08.11.60 GA1 \"197/*\" OFVGL \"198/*\" OFVGL \"1991/*\" OFVGL "
                    + "\"2071/*\" OFVGL \"2082/*\") OFVWD (08.09.10 GA1 0637 ENVWD 08.11.60 GA1 \"2711/*\" OFVGL "
                    + "\"2713/*\" OFVGL \"2714/*\" OFVGL \"2715/*\" OFVGL \"2716/*\" OFVGL \"2717/*\" OFVGL \"2722/*\" "
                    + "OFVGL \"2723/*\" OFVGL \"2724/*\" OFVGL \"2725/*\" OFVGL \"2726/*\" OFVGL \"2727/*\" OFVGL "
                    + "\"2728/*\") OFVWD( 08.09.10 GA1 1916 ENVWD 08.11.60 GA1 \"226/*\")) ENVWD 08.10.10 OGA1 \"B\" ENVWD"
                    + " KNV 07.67.10",
                "(persoon.bijhouding.bijhoudingspartij IN {377, 392, 393, 394, 397, 473, 484, 499, 513, 534, 537, 546, 547, 553, 569, 575, 576, 579, 626, "
                    + "629, 638, 1525, 1672, 1884, 1901} OF (persoon.bijhouding.bijhoudingspartij = 358 EN ER_IS(persoon.adressen, v, v.postcode IN% "
                    + "{\"1431*\", \"1432*\"}) OF (persoon.bijhouding.bijhoudingspartij = 363 EN ER_IS(persoon.adressen, v, v.postcode IN% {\"1014*\", "
                    + "\"1043*\", \"1046*\", \"1056*\", \"1057*\", \"1058*\", \"1059*\", \"106*\", \"1076*\", \"1081*\"}) OF "
                    + "(persoon.bijhouding.bijhoudingspartij = 453 EN ER_IS(persoon.adressen, v, v.postcode IN% {\"197*\", \"198*\", \"1991*\", \"2071*\", "
                    + "\"2082*\"}) OF (persoon.bijhouding.bijhoudingspartij = 637 EN ER_IS(persoon.adressen, v, v.postcode IN% {\"2711*\", \"2713*\", "
                    + "\"2714*\", \"2715*\", \"2716*\", \"2717*\", \"2722*\", \"2723*\", \"2724*\", \"2725*\", \"2726*\", \"2727*\", \"2728*\"}) OF "
                    + "persoon.bijhouding.bijhoudingspartij = 1916 EN ER_IS(persoon.adressen, v, v.postcode %= \"226*\")))))) EN (ER_IS(persoon.adressen, "
                    + "v, v.soort <> \"B\") EN NIET IS_OPGESCHORT(persoon))");

        testVoorwaarde("(01.03.10 GD1 19.89.30 - 00000100) ENVWD KNV 07.67.10",
                "persoon.geboorte.datum > VANDAAG(0) - ^0/1/0 EN NIET IS_OPGESCHORT(persoon)");

        testVoorwaarde(
                "(01.03.10 GD1 19.89.30 - 0019) ENVWD (08.09.10 GA1 0003 OFVGL 0005 OFVGL 0007 OFVGL "
                    + "0009 OFVGL 0010 OFVGL 0014 OFVGL 0015 OFVGL 0017 OFVGL 0018 OFVGL 0022 OFVGL 0024 OFVGL 0025 OFVGL "
                    + "0037 OFVGL 0040 OFVGL 0047 OFVGL 0048 OFVGL 0053 OFVGL 0056 OFVGL 0765 OFVGL 1651 OFVGL 1663 OFVGL "
                    + "1895 OFVGL 1987) ENVWD KNV 07.67.10",
                "JAAR(persoon.geboorte.datum) > JAAR(VANDAAG(-19)) EN (persoon.bijhouding.bijhoudingspartij IN {3, 5, 7, 9, 10, 14, 15, 17, 18, 22, 24, 25, "
                    + "37, 40, 47, 48, 53, 56, 765, 1651, 1663, 1895, 1987} EN NIET IS_OPGESCHORT(persoon))");

        testVoorwaarde(
                "(01.03.10 KD1 19.89.20 - 000206 ENVWD 01.03.10 GDOG1 19.89.20 - 000306) ENVWD KNV 07.67.10",
                "(persoon.geboorte.datum < VANDAAG(0) - ^2/6/0 EN persoon.geboorte.datum >= VANDAAG(0) - ^3/6/0) EN NIET IS_OPGESCHORT(persoon)");

        testVoorwaarde(
                "(01.03.10 KDOG1 19.89.20 - 0003 ENVWD 01.03.10 GD1 19.89.20 - 0023) ENVWD (08.09.10 GA1 0317 OFVGL "
                    + "0376 OFVGL 0381 OFVGL 0402 OFVGL 0406 OFVGL 0417 OFVGL 0424 OFVGL 0425 OFVGL 0457 OFVGL 1696) ENVWD KNV 07.67.10",
                "(JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-3)) EN JAAR(persoon.geboorte.datum) > JAAR(VANDAAG(-23))) EN "
                    + "(persoon.bijhouding.bijhoudingspartij IN {317, 376, 381, 402, 406, 417, 424, 425, 457, 1696} EN NIET IS_OPGESCHORT(persoon))");

        testVoorwaarde(
                "(01.03.10 KDOG1 19.89.20 - 000300 ENVWD 01.03.10 GD1 19.89.20 - 001800) ENVWD (08.09"
                    + ".10 GA1 0050 OFVGL 0230 OFVGL 0233 OFVGL 0243 OFVGL 0269 OFVGL 0273 OFVGL 0302) ENVWD KNV 07.67.10",
                "(persoon.geboorte.datum <= VANDAAG(-3) EN persoon.geboorte.datum > VANDAAG(-18)) EN (persoon.bijhouding.bijhoudingspartij IN {50, 230, 233, "
                    + "243, 269, 273, 302} EN NIET IS_OPGESCHORT(persoon))");

        testVoorwaarde("(01.03.10 KDOG1 19.89.20 - 0023)", "JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-23))");

        testVoorwaarde("(08.14.20 GA1 19.89.20 - 0004)",
                "JAAR(persoon.migratie.datum_aanvang_geldigheid) = JAAR(VANDAAG(-4))");

        testVoorwaarde("01.03.10 GDOG1 19.89.20 - 0005 OFVWD 06.08.10 GDOG1 19.89.20 - 0005",
                "JAAR(persoon.geboorte.datum) >= JAAR(VANDAAG(-5)) OF JAAR(persoon.overlijden.datum) >= JAAR(VANDAAG(-5))");
        testVoorwaarde("08.13.20 GDOG1 19.89.20 - 0005 OFVWD 08.14.20 GDOG1 19.89.20 - 0005",
                "JAAR(persoon.migratie.datum_aanvang_geldigheid) >= JAAR(VANDAAG(-5)) OF JAAR(persoon.migratie"
                    + ".datum_aanvang_geldigheid) >= JAAR(VANDAAG(-5))");

        testVoorwaarde("(07.70.10 GA1 2 OFVGL 4 OFVGL 6 OFVGL 7)", "v.indicatie.volledige_verstrekkingsbeperking");
        testVoorwaarde("14.40.10 GA1 850001", "WAAR");
        testVoorwaarde(
                "(08.09.10 GA1 0141 OFVGL 0147 OFVGL 0153 OFVGL 0158 OFVGL 0164 OFVGL 0168) ENVWD 08.10.10 OGA1 \"B\" ENVWD KNV 07.67.10",
                "persoon.bijhouding.bijhoudingspartij IN {141, 147, 153, 158, 164, 168} EN (ER_IS(persoon.adressen, v, "
                    + "v.soort <> \"B\") EN NIET IS_OPGESCHORT(persoon))");
        // 07.66.20 nog niet gemapt
        // testVoorwaarde("(08.09.10 GA1 0358 OFVGL 0362 OFVGL 0363 OFVGL 0384 OFVGL 0437 OFVGL 0451) ENVWD KNV 07.66
        // .20 ENVWD KNV 07.67.10", "");
        testVoorwaarde("(08.09.10 GA1 0375 OFVGL 0392 OFVGL 0394 OFVGL 0431 OFVGL 0534 OFVGL 0880) ENVWD 08.10.10 "
            + "OGA1 \"B\" ENVWD KNV 07.67.10",
                "persoon.bijhouding.bijhoudingspartij IN {375, 392, 394, 431, 534, 880} EN (ER_IS(persoon.adressen, v, "
                    + "v.soort <> \"B\") EN NIET IS_OPGESCHORT(persoon))");
        testVoorwaarde("(08.09.10 GA1 0505 OFVGL 0531 OFVGL 0590 OFVGL 0610 OFVGL 0642) ENVWD 08.10.10 OGA1 \"B\" "
            + "ENVWD KNV 07.67.10",
                "persoon.bijhouding.bijhoudingspartij IN {505, 531, 590, 610, 642} EN (ER_IS(persoon.adressen, v, "
                    + "v.soort <> \"B\") EN NIET IS_OPGESCHORT(persoon))");
        testVoorwaarde("(08.09.10 GA1 0627) ENVWD 14.40.10 OGAA 250601 ENVWD 08.10.10 OGAA \"B\" ENVWD KNV 07.67.10",
                "persoon.bijhouding.bijhoudingspartij = 627 EN (ALLE(persoon.adressen, v, v.soort <> \"B\") EN NIET "
                    + "IS_OPGESCHORT(persoon))");

    }

    @Test
    public void testVoorbeeldenFrank() {
        testVoorwaarde(
                "(01.03.10 GD1 19.89.20 - 0019) ENVWD (08.09.10 GA1 0244) ENVWD KNV 07.67.10",
                "JAAR(persoon.geboorte.datum) > JAAR(VANDAAG(-19)) EN (persoon.bijhouding.bijhoudingspartij = 244 EN NIET IS_OPGESCHORT(persoon))");

        testVoorwaarde(
                "(((01.04.10 GA1 \"V\") ENVWD (01.03.10 KDOG1 19.89.20 - 0029)) OFVWD ((01.04.10 GA1 \"M\") "
                    + "ENVWD (01.03.10 KDOG1 19.89.20 - 0054))) ENVWD (08.09.10 GA1 0003 OFVGL 0005 OFVGL 0007 OFVGL 0009 "
                    + "OFVGL 0010 OFVGL 0014 OFVGL 0015 OFVGL 0017 OFVGL 0018 OFVGL 0022 OFVGL 0024 OFVGL 0025 OFVGL 0037 "
                    + "OFVGL 0040 OFVGL 0047 OFVGL 0048 OFVGL 0051 OFVGL 0053 OFVGL 0055 OFVGL 0056 OFVGL 0058 OFVGL 0059 "
                    + "OFVGL 0060 OFVGL 0063 OFVGL 0070 OFVGL 0072 OFVGL 0074 OFVGL 0079 OFVGL 0080 OFVGL 0081 OFVGL 0082 "
                    + "OFVGL 0085 OFVGL 0086 OFVGL 0088 OFVGL 0090 OFVGL 0093 OFVGL 0096 OFVGL 0098 OFVGL 0106 OFVGL 0109 "
                    + "OFVGL 0114 OFVGL 0118 OFVGL 0119 OFVGL 0140 OFVGL 0653 OFVGL 0737 OFVGL 0765 OFVGL 1651 OFVGL 1663 "
                    + "OFVGL 1680 OFVGL 1681 OFVGL 1690 OFVGL 1699 OFVGL 1701 OFVGL 1722 OFVGL 1730 OFVGL 1731 OFVGL 1891 "
                    + "OFVGL 1895 OFVGL 1900 OFVGL 1908 OFVGL 1987) ENVWD KNV 07.67.10",
                "(persoon.geslachtsaanduiding.geslachtsaanduiding = \"V\" EN JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-29)) "
                    + "OF persoon.geslachtsaanduiding.geslachtsaanduiding = \"M\" "
                    + "EN JAAR(persoon.geboorte.datum) <= JAAR(VANDAAG(-54))) EN (persoon.bijhouding.bijhoudingspartij IN {3, 5, 7, 9, 10, 14, 15, "
                    + "17, 18, 22, 24, 25, 37, 40, 47, 48, 51, 53, 55, 56, 58, 59, 60, 63, 70, 72, 74, 79, 80, 81, 82, 85, 86, 88, 90, 93, 96, 98, "
                    + "106, 109, 114, 118, 119, 140, 653, 737, 765, 1651, 1663, 1680, 1681, 1690, 1699, 1701, 1722, 1730, 1731, 1891, 1895, 1900, "
                    + "1908, 1987} EN NIET IS_OPGESCHORT(persoon))");

        testVoorwaarde("01.03.10 KD1 19.89.20 - 000206", "persoon.geboorte.datum < VANDAAG(0) - ^2/6/0");

        testVoorwaarde(
                "(01.03.10 KD1 19.89.20 - 000206 ENVWD 01.03.10 GDOG1 19.89.20 - 000306) ENVWD KNV 07.67.10",
                "(persoon.geboorte.datum < VANDAAG(0) - ^2/6/0 EN persoon.geboorte.datum >= VANDAAG(0) - ^3/6/0) EN NIET IS_OPGESCHORT(persoon)");
    }

    private void parseNumRubriekWaarde(final String regel, final String expected) {
        final GBAVoorwaarderegelParser parser = initParser(regel);
        final ParserRuleContext tree = parser.numrubriekwaarde();
        Assert.assertEquals(expected, handleResults(parser, tree, false));
    }

    private void parseAlfanumRubriekWaarde(final String regel, final String expected) {
        final GBAVoorwaarderegelParser parser = initParser(regel);
        final ParserRuleContext tree = parser.alfanumrubriekwaarde();
        Assert.assertEquals(expected, handleResults(parser, tree, false));
    }

    private void parseDatRubriekWaarde(final String regel, final String expected) {
        final GBAVoorwaarderegelParser parser = initParser(regel);
        final ParserRuleContext tree = parser.datrubriekwaarde();
        Assert.assertEquals(expected, handleResults(parser, tree, false));
    }

    private void testVoorwaarde(final String regel, final String expected) {
        final GBAVoorwaarderegelParser parser = initParser(regel);
        final ParserRuleContext tree = parser.voorwaarde();
        Assert.assertEquals(expected, handleResults(parser, tree, true));
    }

    private String handleResults(final GBAVoorwaarderegelParser parser, final ParserRuleContext tree,
            final boolean optimaliseer)
    {
        if (parser.getNumberOfSyntaxErrors() > 0) {
            LOGGER.error("GBA test syntax errors: " + parser.getNumberOfSyntaxErrors());
        }
        final VoorwaarderegelVisitor visitor = new VoorwaarderegelVisitor();
        Expressie vertaaldeExpressie = visitor.visit(tree);
        if (optimaliseer) {
            vertaaldeExpressie = vertaaldeExpressie.optimaliseer(null);
        }
        return vertaaldeExpressie.toString();
    }

    private GBAVoorwaarderegelParser initParser(final String input) {
        final CharStream cs = new ANTLRInputStream(input);
        final GBAVoorwaarderegelLexer lexer = new GBAVoorwaarderegelLexer(cs);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final GBAVoorwaarderegelParser parser = new GBAVoorwaarderegelParser(tokens);
        parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);
        return parser;
    }

}
