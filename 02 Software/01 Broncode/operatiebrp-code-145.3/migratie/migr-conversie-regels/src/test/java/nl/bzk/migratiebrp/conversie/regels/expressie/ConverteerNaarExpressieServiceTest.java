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
        Assert.assertEquals("GEWIJZIGD(oud, nieuw, [Persoon.Adres.AfgekorteNaamOpenbareRuimte])", attenderingsCriterium);
    }

    @Test
    public void testSleutelRubriekenAdellijkeTitelPredicaat() {
        final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken("01.02.20", herkomst);
        Assert.assertFalse(attenderingsCriterium.length() == 0);
        Assert.assertEquals(
                "GEWIJZIGD(oud, nieuw, [Persoon.SamengesteldeNaam.AdellijkeTitelCode]) OF GEWIJZIGD(oud, nieuw, [Persoon.SamengesteldeNaam.PredicaatCode])",
                attenderingsCriterium);
    }

    @Test
    public void testSleutelRubriekenAdellijkeTitelPredicaatMetVoornaam() {
        final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken("01.02.10#01.02.20", herkomst);
        Assert.assertFalse(attenderingsCriterium.length() == 0);
        Assert.assertEquals(
                "GEWIJZIGD(oud, nieuw, [Persoon.SamengesteldeNaam.Voornamen]) OF GEWIJZIGD(oud, nieuw, [Persoon.SamengesteldeNaam.AdellijkeTitelCode]) OF "
                        + "GEWIJZIGD(oud, nieuw, [Persoon.SamengesteldeNaam.PredicaatCode])",
                attenderingsCriterium);
    }

    @Test
    public void testSleutelRubriekenFunctieKinderen() {
        final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken("09.02.10", herkomst);
        Assert.assertFalse(attenderingsCriterium.length() == 0);
        Assert.assertEquals("GEWIJZIGD(oud, nieuw, [GerelateerdeKind.Persoon.SamengesteldeNaam.Voornamen])", attenderingsCriterium);
    }

    @Test
    public void testSleutelRubriekenFunctieGerelateerdeBetrokkenheden() {
        final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken("02.62.10", herkomst);
        Assert.assertFalse(attenderingsCriterium.length() == 0);
        Assert.assertEquals("GEWIJZIGD(oud, nieuw, [GerelateerdeOuder.Ouderschap.DatumAanvangGeldigheid])", attenderingsCriterium);
    }

    @Test
    public void testSleutelRubriekenFunctieAls() {
        final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken("08.13.20", herkomst);
        Assert.assertFalse(attenderingsCriterium.length() == 0);
        Assert.assertEquals(
                "GEWIJZIGD(MAP(FILTER(oud.Persoon.Migratie, x, x.SoortCode E= \"E\"), y, y.DatumAanvangGeldigheid), MAP(FILTER(nieuw.Persoon"
                        + ".Migratie, x, x.SoortCode E= \"E\"), y, y.DatumAanvangGeldigheid))",
                attenderingsCriterium);
    }

    @Test
    public void testSleutelRubriekenMeerdere() {
        final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken("01.02.20#01.02.40", herkomst);
        Assert.assertTrue(attenderingsCriterium.matches(SLEUTEL_EXPRESSIE_PATTERN + " OF " + SLEUTEL_EXPRESSIE_PATTERN));
    }

    @Test
    public void testSleutelRubriekenMeerdereActies() {
        final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken("01.88.10#04.88.10#06.88.10", herkomst);
        Assert.assertFalse(attenderingsCriterium.length() == 0);
        Assert.assertEquals(
                "GEWIJZIGD(ACTIE(oud.Persoon.Identificatienummers.ActieInhoud, [Actie.PartijCode]), ACTIE(nieuw.Persoon.Identificatienummers.ActieInhoud,"
                        + " [Actie.PartijCode])) OF GEWIJZIGD(ACTIE(oud.Persoon.SamengesteldeNaam.ActieInhoud, [Actie.PartijCode]), ACTIE(nieuw"
                        + ".Persoon.SamengesteldeNaam.ActieInhoud, [Actie.PartijCode])) OF GEWIJZIGD(ACTIE(oud.Persoon.Geboorte.ActieInhoud, [Actie"
                        + ".PartijCode]), ACTIE(nieuw.Persoon.Geboorte.ActieInhoud, [Actie.PartijCode])) OF GEWIJZIGD(ACTIE(oud.Persoon"
                        + ".Geslachtsaanduiding.ActieInhoud, [Actie.PartijCode]), ACTIE(nieuw.Persoon.Geslachtsaanduiding.ActieInhoud, [Actie"
                        + ".PartijCode])) OF GEWIJZIGD(ACTIE(oud.Persoon.Nummerverwijzing.ActieInhoud, [Actie.PartijCode]), ACTIE(nieuw.Persoon"
                        + ".Nummerverwijzing.ActieInhoud, [Actie.PartijCode])) OF GEWIJZIGD(ACTIE(oud.Persoon.Naamgebruik.ActieInhoud, [Actie"
                        + ".PartijCode]), ACTIE(nieuw.Persoon.Naamgebruik.ActieInhoud, [Actie.PartijCode])) OF GEWIJZIGD(ACTIE(oud.Persoon"
                        + ".Nationaliteit.ActieInhoud, [Actie.PartijCode]), ACTIE(nieuw.Persoon.Nationaliteit.ActieInhoud, [Actie.PartijCode])) OF "
                        + "GEWIJZIGD(ACTIE(oud.Persoon.Indicatie.BehandeldAlsNederlander.ActieInhoud, [Actie.PartijCode]), ACTIE(nieuw.Persoon.Indicatie"
                        + ".BehandeldAlsNederlander.ActieInhoud, [Actie.PartijCode])) OF GEWIJZIGD(ACTIE(oud.Persoon.Indicatie"
                        + ".VastgesteldNietNederlander.ActieInhoud, [Actie.PartijCode]), ACTIE(nieuw.Persoon.Indicatie.VastgesteldNietNederlander"
                        + ".ActieInhoud, [Actie.PartijCode])) OF GEWIJZIGD(ACTIE(oud.Persoon.Indicatie.Staatloos.ActieInhoud, [Actie.PartijCode]), "
                        + "ACTIE(nieuw.Persoon.Indicatie.Staatloos.ActieInhoud, [Actie.PartijCode])) OF GEWIJZIGD(ACTIE(oud.Persoon.Overlijden"
                        + ".ActieInhoud, [Actie.PartijCode]), ACTIE(nieuw.Persoon.Overlijden.ActieInhoud, [Actie.PartijCode]))",
                attenderingsCriterium);
    }

    @Test
    public void testVoorwaarderegel() {
        final String voorwaarderegel = "KV 07.67.10";
        final String expressieExpected = "NIET(Persoon.Bijhouding.NadereBijhoudingsaardCode A= \"A\")";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaarderegel);

        Assert.assertEquals(expressieExpected, expressie);
    }

    @Test
    public void testKVVoorwaardeRegel() {
        final String voorwaarderegel = "KV 01.01.10";
        final String expressieExpected = "KV(Persoon.Identificatienummers.Administratienummer)";
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
                        + "OFVGL 0986 OFVGL 0988 OFVGL 0994 OFVGL 1507 OFVGL 1640 OFVGL 1641 OFVGL 1669 OFVGL 1711 OFVGL 1729 OFVGL 1883 OFVGL 1894 OFVGL "
                        + "1903) "
                        + "ENVWD 08.10.10 OGA1 \"B\" ENVWD KNV 07.67.10";
        final String
                expressieVerwacht =
                "Persoon.Bijhouding.PartijCode EIN {\"088101\", \"088201\", \"088801\", \"088901\", \"089301\", \"089901\", \"090701\", \"091701\", "
                        + "\"092801\", \"093501\", \"093801\", "
                        + "\"094401\", \"094601\", \"095101\", \"095701\", \"096201\", \"096501\", \"097101\", \"098101\", \"098301\", \"098401\", "
                        + "\"098601\", \"098801\", \"099401\", \"150701\", \"164001\", \"164101\", "
                        + "\"166901\", \"171101\", \"172901\", \"188301\", \"189401\", \"190301\"} "
                        + "EN NIET(Persoon.Adres.SoortCode A= \"B\") "
                        + "EN Persoon.Bijhouding.NadereBijhoudingsaardCode E= \"A\"";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);

        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testComplexeVoorwaardeRegel2() {
        final String voorwaardeRegel =
                "(08.09.10 GA1 0881 OFVGL 0882 OFVGL 0888 OFVGL 0889 OFVGL 0893 OFVGL 0899 OFVGL 0907 OFVGL 0917 OFVGL 0928 "
                        + "OFVGL 0935 OFVGL 0938 OFVGL 0944 OFVGL 0946 OFVGL 0951 OFVGL 0957 OFVGL 0962 OFVGL 0965 OFVGL 0971 OFVGL 0981 OFVGL 0983 OFVGL 0984 "
                        + "OFVGL 0986 OFVGL 0988 OFVGL 0994 OFVGL 1507 OFVGL 1640 OFVGL 1641 OFVGL 1669 OFVGL 1711 OFVGL 1729 OFVGL 1883 OFVGL 1894 OFVGL "
                        + "1903) "
                        + "ENVWD 08.10.10 OGA1 \"B\" ENVWD KNV 07.67.10 OFVWD (08.09.10 GA1 0889 OFVGL 0893 OFVGL 0899 OFVGL 0907 "
                        + "OFVGL 0917 OFVGL 0928 "
                        + "OFVGL 0935 OFVGL 0938 OFVGL 0944 OFVGL 0946 OFVGL 0951 OFVGL 0957 OFVGL 0962 OFVGL 0965 OFVGL 0971 OFVGL 0981 OFVGL 0983 OFVGL 0984 "
                        + "OFVGL 0986 OFVGL 0988 OFVGL 0994 OFVGL 1507 OFVGL 1640 OFVGL 1641 OFVGL 1669 OFVGL 1711 OFVGL 1729)";
        final String
                expressieVerwacht =
                "Persoon.Bijhouding.PartijCode EIN {\"088101\", \"088201\", \"088801\", \"088901\", \"089301\", \"089901\", \"090701\", \"091701\", "
                        + "\"092801\", \"093501\", \"093801\", "
                        + "\"094401\", \"094601\", \"095101\", \"095701\", \"096201\", \"096501\", \"097101\", \"098101\", \"098301\", \"098401\", "
                        + "\"098601\", \"098801\", \"099401\", \"150701\", \"164001\", \"164101\", "
                        + "\"166901\", \"171101\", \"172901\", \"188301\", \"189401\", \"190301\"} "
                        + "EN NIET(Persoon.Adres.SoortCode A= \"B\") "
                        + "EN Persoon.Bijhouding.NadereBijhoudingsaardCode E= \"A\" "
                        + "OF Persoon.Bijhouding.PartijCode EIN {\"088901\", \"089301\", \"089901\", \"090701\", \"091701\", \"092801\", \"093501\", "
                        + "\"093801\", \"094401\", \"094601\", \"095101\", "
                        + "\"095701\", \"096201\", \"096501\", \"097101\", \"098101\", \"098301\", \"098401\", \"098601\", \"098801\", \"099401\", "
                        + "\"150701\", \"164001\", \"164101\", \"166901\", \"171101\", \"172901\"}";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);

        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testEenDeelsOnvertaaldeVoorwaarde() {
        final String voorwaardeRegel = "08.10.30 GDOGA 19.89.30 + 001511 ENVWD KV 08.10.30";
        final String expressieVerwacht =
                "Persoon.Adres.DatumAanvangAdreshouding A>= VANDAAG() + ^15/11/? " + "EN KV(Persoon.Adres.DatumAanvangAdreshouding)";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);

        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testTeVeelSpaties() {
        final String voorwaardeRegel =
                "08.09.10 GA1 0881 OFVGL 0882 OFVGL 0888 OFVGL 0897 OFVGL 0899 OFVGL 0902 OFVGL 0905 OFVGL 0906 OFVGL "
                        + "0913 OFVGL 0917 OFVGL 0928 OFVGL 0933 OFVGL 0935 OFVGL 0936 OFVGL 0938 OFVGL "
                        + "0951 OFVGL 0957 OFVGL 0962 OFVGL 0965 OFVGL "
                        + "0968 OFVGL 0971 OFVGL 0974 OFVGL 0981 OFVGL 0986 OFVGL 0990 OFVGL 0994 OFVGL "
                        + "1669 OFVGL 1679 ENVWD 08.10.10 OGAA \"B\" ENVWD "
                        + "KNV 07.67.10 ENVWD (KNV 13.38.10 OFVWD 13.38.20 KDA   19.89.20 + 00000000)";
        final String
                expressieVerwacht =
                "Persoon.Bijhouding.PartijCode EIN {\"088101\", \"088201\", \"088801\", \"089701\", \"089901\", \"090201\", \"090501\", \"090601\", "
                        + "\"091301\", \"091701\", "
                        + "\"092801\", \"093301\", \"093501\", \"093601\", \"093801\", \"095101\", \"095701\", \"096201\", \"096501\", \"096801\", \"097101\", "
                        + "\"097401\", \"098101\", \"098601\", \"099001\", \"099401\", \"166901\", \"167901\"} "
                        + "EN NIET(Persoon.Adres.SoortCode E= \"B\") EN Persoon.Bijhouding.NadereBijhoudingsaardCode E= \"A\" "
                        + "EN (KNV(Persoon.UitsluitingKiesrecht.Indicatie) OF "
                        + "Persoon.UitsluitingKiesrecht.DatumVoorzienEinde A< SELECTIE_DATUM() + ^0/0/0)";

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
        final String
                expressieVerwacht =
                "KV(Persoon.Identificatienummers.Administratienummer) OF KV(Persoon.Identificatienummers.Burgerservicenummer) OF KV(Persoon.SamengesteldeNaam"
                        + ".Voornamen) OF (KV(Persoon.SamengesteldeNaam.AdellijkeTitelCode) OF KV(Persoon.SamengesteldeNaam.PredicaatCode)) OF (KV(Persoon"
                        + ".SamengesteldeNaam.Voorvoegsel) OF KV(Persoon.SamengesteldeNaam.Scheidingsteken)) OF KV(Persoon.SamengesteldeNaam"
                        + ".Geslachtsnaamstam) OF KV(Persoon.Geboorte.Datum) OF (KV(Persoon.Geboorte.GemeenteCode) OF KV(Persoon.Geboorte.BuitenlandsePlaats)"
                        + " OF KV(Persoon.Geboorte.OmschrijvingLocatie) OF KV(Persoon.Geboorte.Woonplaatsnaam)) OF KV(Persoon.Geboorte.LandGebiedCode) OF KV"
                        + "(Persoon.Geslachtsaanduiding.Code) OF KV(Persoon.Nummerverwijzing.VorigeAdministratienummer) OF KV(Persoon.Nummerverwijzing"
                        + ".VolgendeAdministratienummer)";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);

        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testOntbekendeSpatieRondOperator() {
        final String voorwaardeRegel = "08.11.60 GA1 \"2261*\" OFVGL \"2262*\"OFVGL \"2263*\" OFVGL\"2264*\" OFVGL \"2265*\" ";
        final String expressieVerwacht = "Persoon.Adres.Postcode EIN% {\"2261*\", \"2262*\", \"2263*\", \"2264*\", \"2265*\"}";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);

        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testVeelTeVeelHaakjes() {
        final String voorwaardeRegel = "(01.03.10 KD1 (19.89.30 - 0063))";
        final String expressieVerwacht = "Persoon.Geboorte.Datum E< VANDAAG() - ^63/?/?";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);
        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testTeVeelHaakjes() {
        final String voorwaardeRegel = "01.03.10 KD1 (19.89.30 - 0063)";
        final String expressieVerwacht = "Persoon.Geboorte.Datum E< VANDAAG() - ^63/?/?";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);
        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testMatopZonderSpaties() {
        final String voorwaardeRegel = "01.03.10 KD1 (19.89.30-0063)";
        final String expressieVerwacht = "Persoon.Geboorte.Datum E< VANDAAG() - ^63/?/?";

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
        final String expressieVerwacht = "Persoon.SamengesteldeNaam.Geslachtsnaamstam E= \"A(CHT/\"ERNAAM\"";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);
        Assert.assertEquals(expressieVerwacht, expressie);
    }

    @Test
    public void testSpatiesEnOperatorInWaarde() {
        final String voorwaardeRegel = "01.02.40GA1\"GA1\"";
        final String expressieVerwacht = "Persoon.SamengesteldeNaam.Geslachtsnaamstam E= \"GA1\"";

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
                "08.11.60 OGA1 \"1024/*\" ENVGL \"1025/*\" ENVGL \"1026/*\" ENVGL \"1027/*\" ENVGL \"1028/*\" ENVGL \"1034/*\" ENVGL \"1035/*\" ENVGL " +
                        "\"2521/*\"";
        final String expressieVerwacht =
                "NIET(Persoon.Adres.Postcode AIN% {\"1024*\", \"1025*\", \"1026*\", \"1027*\", \"1028*\", \"1034*\", \"1035*\", \"2521*\"})";

        final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaardeRegel);
        Assert.assertEquals(expressieVerwacht, expressie);
    }

}
