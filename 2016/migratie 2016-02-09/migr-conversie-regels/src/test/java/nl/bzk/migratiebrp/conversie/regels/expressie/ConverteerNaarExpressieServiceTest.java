/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class ConverteerNaarExpressieServiceTest {

    private static final String SLEUTEL_EXPRESSIE_PATTERN = "GEWIJZIGD\\(oud, nieuw, \\[.+\\]\\)";

    private final Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_35, -1, -1);

    @Inject
    private ConverteerNaarExpressieService converteerNaarExpressieService;

    @Before
    public void setup() {
        Logging.initContext();
    }

    @After
    public void destroy() {
        Logging.destroyContext();
    }

    @Test
    public void testSleutelRubriekenNull() {
        Assert.assertEquals(null, converteerNaarExpressieService.converteerSleutelRubrieken(null, herkomst));
        Assert.assertEquals(null, converteerNaarExpressieService.converteerSleutelRubrieken("", herkomst));
        Assert.assertEquals(null, converteerNaarExpressieService.converteerSleutelRubrieken("onbekenderubriek", herkomst));
    }

    @Test
    public void testSleutelRubrieken() {
        final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken("01.01.20", herkomst);
        Assert.assertFalse(attenderingsCriterium.length() == 0);
        Assert.assertTrue(attenderingsCriterium.matches(SLEUTEL_EXPRESSIE_PATTERN));
    }

    @Test
    public void testSleutelRubriekenLijst() {
        final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken("08.11.10", herkomst);
        Assert.assertFalse(attenderingsCriterium.length() == 0);
        Assert.assertEquals("GEWIJZIGD(oud, nieuw, [adressen], [afgekorte_naam_openbare_ruimte])", attenderingsCriterium);
    }

    @Test
    public void testSleutelRubriekenFunctieKinderen() {
        final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken("09.02.10", herkomst);
        Assert.assertFalse(attenderingsCriterium.length() == 0);
        Assert.assertEquals("GEWIJZIGD(KINDEREN(oud), KINDEREN(nieuw), [samengestelde_naam.voornamen])", attenderingsCriterium);
    }

    @Test
    public void testSleutelRubriekenFunctieGerelateerdeBetrokkenheden() {
        final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken("02.62.10", herkomst);
        Assert.assertFalse(attenderingsCriterium.length() == 0);
        Assert.assertEquals(
            "GEWIJZIGD(GERELATEERDE_BETROKKENHEDEN(oud,\"KIND\",\"FAMILIERECHTELIJKE_BETREKKING\",\"OUDER\"), GERELATEERDE_BETROKKENHEDEN(nieuw,\"KIND\",\"FAMILIERECHTELIJKE_BETREKKING\",\"OUDER\"), [ouderschap.datum_aanvang_geldigheid])",
            attenderingsCriterium);
    }

    @Test
    public void testSleutelRubriekenFunctieAls() {
        final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken("08.13.20", herkomst);
        Assert.assertFalse(attenderingsCriterium.length() == 0);
        Assert.assertEquals(
            "GEWIJZIGD(ALS(oud.migratie.soort=\"E\",oud.migratie.datum_aanvang_geldigheid,NULL), ALS(nieuw.migratie.soort=\"E\",nieuw.migratie.datum_aanvang_geldigheid,NULL))",
            attenderingsCriterium);
    }

    @Test
    public void testSleutelRubriekenMeerdere() {
        final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken("01.02.20#01.02.40", herkomst);
        Assert.assertTrue(attenderingsCriterium.matches(SLEUTEL_EXPRESSIE_PATTERN + " OF " + SLEUTEL_EXPRESSIE_PATTERN));
    }

    @Test
    public void testVoorwaarderegel() {
        final String voorwaarderegel = "KV 07.67.10";
        final String expressieExpected = "bijhouding.nadere_bijhoudingsaard <> \"A\"";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaarderegel);

        Assert.assertEquals(expressieExpected, expressie);
    }

    @Test
    public void testKVVoorwaardeRegel() {
        final String voorwaarderegel = "KV 01.01.10";
        final String expressieExpected = "NIET IS_NULL(identificatienummers.administratienummer)";
        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaarderegel);

        Assert.assertEquals(expressieExpected, expressie);
    }

    @Test
    public void testVoorwaarderegelLeeg() {
        final String expressieExpected = "WAAR";

        Assert.assertEquals(expressieExpected, converteerNaarExpressieService.converteerVoorwaardeRegel(""));
        Assert.assertEquals(expressieExpected, converteerNaarExpressieService.converteerVoorwaardeRegel(null));
    }

    @Test
    public void testComplexeVoorwaardeRegel() {
        final String voorwaardeRegel =
                "(08.09.10 GA1 0881 OFVGL 0882 OFVGL 0888 OFVGL 0889 OFVGL 0893 OFVGL 0899 OFVGL 0907 OFVGL 0917 OFVGL 0928 "
                                       + "OFVGL 0935 OFVGL 0938 OFVGL 0944 OFVGL 0946 OFVGL 0951 OFVGL 0957 OFVGL 0962 OFVGL 0965 OFVGL 0971 OFVGL 0981 OFVGL 0983 OFVGL 0984 "
                                       + "OFVGL 0986 OFVGL 0988 OFVGL 0994 OFVGL 1507 OFVGL 1640 OFVGL 1641 OFVGL 1669 OFVGL 1711 OFVGL 1729 OFVGL 1883 OFVGL 1894 OFVGL 1903) "
                                       + "ENVWD 08.10.10 OGA1 \"B\" ENVWD KNV 07.67.10";
        final String expressieVerwacht =
                "bijhouding.bijhoudingspartij IN {88101, 88201, 88801, 88901, 89301, 89901, 90701, 91701, 92801, 93501, 93801, "
                                         + "94401, 94601, 95101, 95701, 96201, 96501, 97101, 98101, 98301, 98401, 98601, 98801, 99401, 150701, 164001, 164101, 166901, 171101, "
                                         + "172901, 188301, 189401, 190301} "
                                         + "EN (ER_IS(RMAP(adressen, x, x.soort), v, NIET(v = \"B\")) OF AANTAL(RMAP(adressen, x, x.soort)) = 0) "
                                         + "EN bijhouding.nadere_bijhoudingsaard = \"A\"";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);

        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testComplexeVoorwaardeRegel2() {
        final String voorwaardeRegel =
                "(08.09.10 GA1 0881 OFVGL 0882 OFVGL 0888 OFVGL 0889 OFVGL 0893 OFVGL 0899 OFVGL 0907 OFVGL 0917 OFVGL 0928 "
                                       + "OFVGL 0935 OFVGL 0938 OFVGL 0944 OFVGL 0946 OFVGL 0951 OFVGL 0957 OFVGL 0962 OFVGL 0965 OFVGL 0971 OFVGL 0981 OFVGL 0983 OFVGL 0984 "
                                       + "OFVGL 0986 OFVGL 0988 OFVGL 0994 OFVGL 1507 OFVGL 1640 OFVGL 1641 OFVGL 1669 OFVGL 1711 OFVGL 1729 OFVGL 1883 OFVGL 1894 OFVGL 1903) "
                                       + "ENVWD 08.10.10 OGA1 \"B\" ENVWD KNV 07.67.10 OFVWD (08.09.10 GA1 0889 OFVGL 0893 OFVGL 0899 OFVGL 0907 "
                                       + "OFVGL 0917 OFVGL 0928 "
                                       + "OFVGL 0935 OFVGL 0938 OFVGL 0944 OFVGL 0946 OFVGL 0951 OFVGL 0957 OFVGL 0962 OFVGL 0965 OFVGL 0971 OFVGL 0981 OFVGL 0983 OFVGL 0984 "
                                       + "OFVGL 0986 OFVGL 0988 OFVGL 0994 OFVGL 1507 OFVGL 1640 OFVGL 1641 OFVGL 1669 OFVGL 1711 OFVGL 1729)";
        final String expressieVerwacht =
                "bijhouding.bijhoudingspartij IN {88101, 88201, 88801, 88901, 89301, 89901, 90701, 91701, 92801, 93501, 93801, "
                                         + "94401, 94601, 95101, 95701, 96201, 96501, 97101, 98101, 98301, 98401, 98601, 98801, 99401, 150701, 164001, 164101, 166901, 171101, "
                                         + "172901, 188301, 189401, 190301} "
                                         + "EN (ER_IS(RMAP(adressen, x, x.soort), v, NIET(v = \"B\")) OF AANTAL(RMAP(adressen, x, x.soort)) = 0) "
                                         + "EN bijhouding.nadere_bijhoudingsaard = \"A\" "
                                         + "OF bijhouding.bijhoudingspartij IN {88901, 89301, 89901, 90701, 91701, 92801, 93501, 93801, 94401, 94601, 95101, 95701, "
                                         + "96201, 96501, 97101, 98101, 98301, 98401, 98601, 98801, 99401, 150701, 164001, 164101, 166901, 171101, 172901}";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);

        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testEenDeelsOnvertaaldeVoorwaarde() {
        final String voorwaardeRegel = "08.10.30 GDOGA 19.89.30 + 001511 ENVWD KV 08.10.30";
        final String expressieVerwacht =
                "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) > JAAR(VANDAAG() + ^15/11/0)) "
                                         + "OF (JAAR(v) = JAAR(VANDAAG() + ^15/11/0) EN MAAND(v) >= MAAND(VANDAAG() + ^15/11/0)))) "
                                         + "EN ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, NIET IS_NULL(v))";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);

        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testTeVeelSpaties() {
        final String voorwaardeRegel =
                "08.09.10 GA1 0881 OFVGL 0882 OFVGL 0888 OFVGL 0897 OFVGL 0899 OFVGL 0902 OFVGL 0905 OFVGL 0906 OFVGL "
                                       + "0913 OFVGL 0917 OFVGL 0928 OFVGL 0933 OFVGL 0935 OFVGL 0936 OFVGL 0938 OFVGL 0951 OFVGL 0957 OFVGL 0962 OFVGL 0965 OFVGL "
                                       + "0968 OFVGL 0971 OFVGL 0974 OFVGL 0981 OFVGL 0986 OFVGL 0990 OFVGL 0994 OFVGL 1669 OFVGL 1679 ENVWD 08.10.10 OGAA \"B\" ENVWD "
                                       + "KNV 07.67.10 ENVWD (KNV 13.38.10 OFVWD 13.38.20 KDA   19.89.20 + 00000000)";
        final String expressieVerwacht =
                "bijhouding.bijhoudingspartij IN {88101, 88201, 88801, 89701, 89901, 90201, 90501, 90601, 91301, 91701, "
                                         + "92801, 93301, 93501, 93601, 93801, 95101, 95701, 96201, 96501, 96801, 97101, 97401, 98101, 98601, 99001, 99401, 166901, 167901} "
                                         + "EN ALLE(RMAP(adressen, x, x.soort), v, NIET(v = \"B\")) EN bijhouding.nadere_bijhoudingsaard = \"A\" "
                                         + "EN (IS_NULL(uitsluiting_kiesrecht.uitsluiting_kiesrecht) OF uitsluiting_kiesrecht.datum_voorzien_einde_uitsluiting_kiesrecht < SELECTIE_DATUM() + ^0/0/0)";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);

        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test(expected = Lo3VoorwaardeRegelOnvertaalbaarExceptie.class)
    public void testAantekening() {
        converteerNaarExpressieService.converteerVoorwaardeRegel("15.42.10 GA1 \"D\" OFVGL \"DVr\"");
    }

    @Test(expected = Lo3VoorwaardeRegelOnvertaalbaarExceptie.class)
    public void testBlokkering() {
        converteerNaarExpressieService.converteerVoorwaardeRegel("KV 07.66.20");
    }

    @Test
    public void testMeerDanTienEnkelvoudigeVoorwaarden() {
        final String voorwaardeRegel =
                "KV 01.01.10 OFVWD KV 01.01.20 OFVWD "
                                       + "KV 01.02.10 OFVWD KV 01.02.20 OFVWD KV 01.02.30 OFVWD KV 01.02.40 OFVWD "
                                       + "KV 01.03.10 OFVWD KV 01.03.20 OFVWD KV 01.03.30 "
                                       + "OFVWD KV 01.04.10 "
                                       + "OFVWD KV 01.20.10 OFVWD KV 01.20.20";
        final String expressieVerwacht =
                "NIET IS_NULL(identificatienummers.administratienummer) OF NIET IS_NULL(identificatienummers.burgerservicenummer) OF "
                                         + "NIET IS_NULL(samengestelde_naam.voornamen) OF (NIET IS_NULL(samengestelde_naam.adellijke_titel) OF NIET IS_NULL(samengestelde_naam.predicaat)) OF NIET IS_NULL(samengestelde_naam.voorvoegsel) OF NIET IS_NULL(samengestelde_naam.geslachtsnaamstam) OF "
                                         + "NIET IS_NULL(geboorte.datum) OF NIET IS_NULL(geboorte.woonplaatsnaam) OF NIET IS_NULL(geboorte.land_gebied) OF "
                                         + "NIET IS_NULL(geslachtsaanduiding.geslachtsaanduiding) OF "
                                         + "NIET IS_NULL(nummerverwijzing.vorige_administratienummer) OF NIET IS_NULL(nummerverwijzing.volgende_administratienummer)";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);

        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testOntbekendeSpatieRondOperator() {
        final String voorwaardeRegel = "08.11.60 GA1 \"2261*\" OFVGL \"2262*\"OFVGL \"2263*\" OFVGL\"2264*\" OFVGL \"2265*\" ";
        final String expressieVerwacht = "ER_IS(RMAP(adressen, x, x.postcode), v, v IN {\"2261*\", \"2262*\", \"2263*\", \"2264*\", \"2265*\"})";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);

        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testVeelTeVeelHaakjes() {
        final String voorwaardeRegel = "(01.03.10 KD1 (19.89.30 - 0063))";
        final String expressieVerwacht = "JAAR(geboorte.datum) < JAAR(VANDAAG() - ^63/0/0)";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);
        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testTeVeelHaakjes() {
        final String voorwaardeRegel = "01.03.10 KD1 (19.89.30 - 0063)";
        final String expressieVerwacht = "JAAR(geboorte.datum) < JAAR(VANDAAG() - ^63/0/0)";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);
        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testMatopZonderSpaties() {
        final String voorwaardeRegel = "01.03.10 KD1 (19.89.30-0063)";
        final String expressieVerwacht = "JAAR(geboorte.datum) < JAAR(VANDAAG() - ^63/0/0)";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);
        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test(expected = Lo3VoorwaardeRegelOnvertaalbaarExceptie.class)
    public void testOnsamenhangendAantalHaakjes() throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String voorwaardeRegel = "(KNV 01.03.10";
        converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);
    }

    @Test
    public void testHaakjeEnQuoteInWaarde() {
        final String voorwaardeRegel = "01.02.40 GA1 \"A(CHT/\"ERNAAM\"";
        final String expressieVerwacht = "samengestelde_naam.geslachtsnaamstam = \"A(CHT/\"ERNAAM\"";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);
        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testSpatiesEnOperatorInWaarde() {
        final String voorwaardeRegel = "01.02.40GA1\"GA1\"";
        final String expressieVerwacht = "samengestelde_naam.geslachtsnaamstam = \"GA1\"";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);
        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test(expected = Lo3VoorwaardeRegelOnvertaalbaarExceptie.class)
    public void testOnsamenhangendAantalQuotes() throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String voorwaardeRegel = "01.02.40 GA1 \"A(CHT/\"ERNAAM";
        converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);
    }

    @Test
    public void testInWildcard() {
        final String voorwaardeRegel =
                "08.11.60 OGA1 \"1024/*\" ENVGL \"1025/*\" ENVGL \"1026/*\" ENVGL \"1027/*\" ENVGL \"1028/*\" ENVGL \"1034/*\" ENVGL \"1035/*\" ENVGL \"2521/*\"";
        final String expressieVerwacht =
                "(ER_IS(RMAP(adressen, x, x.postcode), v, NIET(v IN% {\"1024*\", \"1025*\", \"1026*\", \"1027*\", \"1028*\", \"1034*\", \"1035*\", \"2521*\"})) OF AANTAL(RMAP(adressen, x, x.postcode)) = 0)";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);
        Assert.assertEquals(expressieVerwacht, expressie);
    }

}
