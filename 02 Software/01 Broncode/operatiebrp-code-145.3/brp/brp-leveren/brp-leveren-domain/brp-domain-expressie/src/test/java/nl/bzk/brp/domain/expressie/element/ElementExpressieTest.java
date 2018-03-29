/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.element;

import static nl.bzk.brp.domain.expressie.util.ExpressietaalTestPersoon.PERSOONSLIJST;
import static nl.bzk.brp.domain.expressie.util.ExpressietaalTestPersoon.PERSOONSLIJST_LEEG;
import static nl.bzk.brp.domain.expressie.util.TestUtils.assertEqual;
import static nl.bzk.brp.domain.expressie.util.TestUtils.assertExceptie;
import static nl.bzk.brp.domain.expressie.util.TestUtils.evalueer;
import static nl.bzk.brp.domain.expressie.util.TestUtils.testEvaluatie;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class ElementExpressieTest {


    @Test
    public void testOngeldigeExpressie() throws ExpressieException {
        assertExceptie("Persoon.Geboorte.Buitenlandseplaats", "Ongeldige elementexpressie: Persoon.Geboorte.Buitenlandseplaats");
    }

    /**
     * Test voor resolven van objecten obv de element naam conventie.
     */
    @Test
    public void testOphalenObjectObvElementnaam() throws ExpressieException {
        assertEqual(evalueer("Persoon.Reisdocument", PERSOONSLIJST), "{@Persoon.Reisdocument, @Persoon.Reisdocument}");
        assertEqual(evalueer("Persoon.Nationaliteit", PERSOONSLIJST), "{@Persoon.Nationaliteit}");
        assertEqual(evalueer("Persoon.Indicatie.OnderCuratele", PERSOONSLIJST), "{@Persoon.Indicatie.OnderCuratele}");
        assertEqual(evalueer("Persoon.Verificatie", PERSOONSLIJST), "{@Persoon.Verificatie}");
        assertEqual(evalueer("Persoon.Ouder", PERSOONSLIJST), "{@Persoon.Ouder}");
        assertEqual(evalueer("FamilierechtelijkeBetrekking", PERSOONSLIJST), "{@FamilierechtelijkeBetrekking}");
        assertEqual(evalueer("GerelateerdeKind", PERSOONSLIJST), "{@GerelateerdeKind}");
        assertEqual(evalueer("GerelateerdeKind.Persoon", PERSOONSLIJST), "{@GerelateerdeKind.Persoon}");
        assertEqual(evalueer("Kind", PERSOONSLIJST_LEEG), "{}");
    }

    @Test
    public void testToString() {
        final ElementExpressie elementExpressie = new ElementExpressie("persoon", "Persoon.Indicatie.OnderCuratele");
        Assert.assertEquals("Persoon.Indicatie.OnderCuratele", elementExpressie.toString());
    }

    /**
     * Test voor resolven van objecten obv van inverse associatie code.
     */
    @Test
    public void testOphalenObjectObvInversecode() throws ExpressieException {
        assertEqual(evalueer("Reisdocumenten", PERSOONSLIJST), "{@Persoon.Reisdocument, @Persoon.Reisdocument}");
        assertEqual(evalueer("Nationaliteiten", PERSOONSLIJST), "{@Persoon.Nationaliteit}");
    }

    @Test
    public void testOphalenPersoon() throws ExpressieException {
        assertEqual(evalueer(ExpressieTaalConstanten.CONTEXT_PERSOON, PERSOONSLIJST), "@Persoon");
    }

    /**
     * Test voor resolven van groepn obv de element naam conventie.
     */
    @Test
    public void testOphalenGroepObvElementnaam() throws ExpressieException {
        assertEqual(evalueer("Persoon.Identificatienummers", PERSOONSLIJST), "{@Persoon.Identificatienummers}");
        assertEqual(evalueer("Persoon.Geboorte", PERSOONSLIJST), "{@Persoon.Geboorte}");
        assertEqual(evalueer("Persoon.Reisdocument.Standaard", PERSOONSLIJST), "{@Persoon.Reisdocument.Standaard, @Persoon.Reisdocument.Standaard}");
        assertEqual(evalueer("GerelateerdeKind.Persoon.Identificatienummers", PERSOONSLIJST),
                "{@GerelateerdeKind.Persoon.Identificatienummers}");

        //als groep niet bestaat op de PL geen fout maar lege lijst
        assertEqual(evalueer("Persoon.Migratie", PERSOONSLIJST_LEEG), "{}");

    }

    /**
     * Test voor resolven van groepn obv de ident expressie
     */
    @Test
    public void testOphalenGroepObvIdentExpressie() throws ExpressieException {
        assertEqual(evalueer("Geboorte", PERSOONSLIJST), "{@Persoon.Geboorte}");
        assertEqual(evalueer("persoon.Geboorte", PERSOONSLIJST), "{@Persoon.Geboorte}");

        //BMR-322
        //deze pingpongt omdat er 2 globale resolvers zijn
        //Object Naamgebruik en
        //Groep Persoon.Naamgebruik met alias Naamgebruik
        //assertEqual(evalueer("Naamgebruik", PERSOONSLIJST), "{@Persoon.Naamgebruik}");
        //assertEqual(evalueer("persoon.Naamgebruik", PERSOONSLIJST), "{@Persoon.Naamgebruik}");
        //assertEqual(evalueer("MAP({persoon}, x, x.Naamgebruik)", PERSOONSLIJST), "{@Persoon.Naamgebruik}");
        assertEqual(evalueer("MAP({persoon}, x, x.Migratie)", PERSOONSLIJST), "{@Persoon.Migratie}");

        //als groep niet bestaat op de PL geen fout maar lege lijst
        assertEqual(evalueer("Migratie", PERSOONSLIJST_LEEG), "{}");

        //ongeldig
        assertExceptie("MAP({persoon}, x, x.bla.soort)", PERSOONSLIJST_LEEG, "Ongeldige elementexpressie: bla.soort");
        assertExceptie("persoon.nietbestaand", PERSOONSLIJST_LEEG, "Ongeldige elementexpressie: nietbestaand");
    }

    @Test
    public void testOphalenAttribuutObvElementnaam() throws ExpressieException {
        assertEqual(evalueer("Persoon.Identificatienummers.Burgerservicenummer", PERSOONSLIJST), "{\"987654321\"}");
        assertEqual(evalueer("Persoon.Identificatienummers.Administratienummer", PERSOONSLIJST), "{\"0123456789\"}");
        assertEqual(evalueer("GerelateerdeKind.Persoon.Identificatienummers.Burgerservicenummer", PERSOONSLIJST), "{\"787654321\"}");
        assertEqual(evalueer("Persoon.Reisdocument.Nummer AIN {\"12\", \"10\"}", PERSOONSLIJST), "WAAR");
    }

    @Test
    public void testOphalenAttribuutObvElementnaamRef() throws ExpressieException {
        assertEqual(evalueer("Persoon.Identificatienummers.Burgerservicenummer WAARBIJ attribuutalswaarde = ONWAAR", PERSOONSLIJST),
                "{@Persoon.Identificatienummers.Burgerservicenummer[987654321]}");
        assertEqual(evalueer("Persoon.Identificatienummers.Administratienummer WAARBIJ attribuutalswaarde = ONWAAR", PERSOONSLIJST),
                "{@Persoon.Identificatienummers.Administratienummer[0123456789]}");
        assertEqual(evalueer("GerelateerdeKind.Persoon.Identificatienummers.Burgerservicenummer WAARBIJ attribuutalswaarde = ONWAAR", PERSOONSLIJST),
                "{@GerelateerdeKind.Persoon.Identificatienummers.Burgerservicenummer[787654321]}");
        testEvaluatie("Persoon.Reisdocument.Nummer WAARBIJ attribuutalswaarde = ONWAAR", "{@Persoon.Reisdocument.Nummer[10], @Persoon.Reisdocument.Nummer[12]}",
                PERSOONSLIJST);
    }

    @Test
    public void testAttribuutAliasMetGroepAlias() throws ExpressieException {
        assertEqual(evalueer("Migratie.SoortCode", PERSOONSLIJST), "{\"B\"}");
        assertEqual(evalueer("MAP({persoon}, x, x.Migratie.SoortCode)", PERSOONSLIJST), "{\"B\"}");
        assertEqual(evalueer("MAP({persoon}, x, x.Naamgebruik.Voorvoegsel)", PERSOONSLIJST), "{\"De\"}");
        assertEqual(evalueer("MAP(GerelateerdeKind.Persoon, x, x.Identificatienummers.Burgerservicenummer)", PERSOONSLIJST), "{\"787654321\"}");

        //test leeg
        assertEqual(evalueer("MAP({persoon}, x, x.Migratie.SoortCode)", PERSOONSLIJST_LEEG), "{}");

        //test foutief
        assertExceptie("MAP({persoon}, x, x.Migratie.soortbla)", PERSOONSLIJST, "Ongeldige elementexpressie: Migratie.soortbla");
        assertExceptie("MAP({persoon}, x, x.Migratie.soortbla)", PERSOONSLIJST_LEEG, "Ongeldige elementexpressie: Migratie.soortbla");
    }

    @Test
    public void testOphalenAttribuutOpRecord() throws ExpressieException {
        final Persoonslijst persoonslijst = TestBuilders.maakPersoonFormeleEnMaterieleHistorie(1);
        assertEqual(evalueer("MAP(HISM_LAATSTE(Persoon.Adres.Standaard), x, x.Woonplaatsnaam)", persoonslijst), "{\"Rijswijk\"}");
        assertEqual(evalueer("MAP(HISM_LAATSTE(Persoon.Adres.Standaard), x, x.Huisletter)", persoonslijst), "{NULL}");
    }

    @Test
    public void testOphalenAttribuutOnderObject() throws ExpressieException {
        assertEqual(evalueer("AANTAL(MAP(Persoon.Reisdocument, x, x.Nummer))", PERSOONSLIJST), "2");

    }

    @Test
    public void testDataTypen() throws ExpressieException {
        //getal type
        evalueer("Persoon.Identificatienummers.Burgerservicenummer", PERSOONSLIJST_LEEG);
        //datumtype
        evalueer("Persoon.Identificatienummers.DatumAanvangGeldigheid", PERSOONSLIJST);
        //datumtijd type
        evalueer("Persoon.Identificatienummers.TijdstipRegistratie", PERSOONSLIJST);
        //boolean type
        evalueer("Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties", PERSOONSLIJST);
    }

    /**
     * Er bestaan vooralsnog 2 shortcut expressies voor burgerservicenummer (bsn) en
     * administratienummer (anummer). Deze zijn hardcoded in de taal.
     */
    @Test
    public void testOphalenAttribuutObvShortcut() throws ExpressieException {
        assertEqual(evalueer("BSN", PERSOONSLIJST), "{\"987654321\"}");
        assertEqual(evalueer("BSN", PERSOONSLIJST_LEEG), "{}");
        assertEqual(evalueer("ANr", PERSOONSLIJST), "{\"0123456789\"}");
        assertEqual(evalueer("ANr", PERSOONSLIJST_LEEG), "{}");
    }


    @Test
    public void testMetPeriode() throws ExpressieException {
        assertEqual(evalueer("Persoon.Identificatienummers.DatumAanvangGeldigheid - ^0/1/0", PERSOONSLIJST), "{2000/12/04}");
        assertEqual(evalueer("Persoon.Identificatienummers.DatumAanvangGeldigheid + ^0/1/0", PERSOONSLIJST), "{2001/02/04}");
        testEvaluatie("Persoon.Reisdocument.DatumIngangDocument - ^0/1/0", "{2009/12/01, 2012/12/01}", PERSOONSLIJST);
        testEvaluatie("Persoon.Reisdocument.DatumIngangDocument + ^0/1/0", "{2010/02/01, 2013/02/01}", PERSOONSLIJST);

    }

    @Test(expected = ExpressieException.class)
    public void testFoutAlsElementNietBestaat() throws ExpressieException {
        evalueer("bla", PERSOONSLIJST_LEEG);
    }
}
