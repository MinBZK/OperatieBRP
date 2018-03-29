/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Test;

/**
 */
public class HismLaatsteFunctieTest {

    private final Persoonslijst persoonslijst = TestBuilders.PERSOON_MET_MATERIELE_EN_FORMELE_HISTORIE;

    @Test
    public void testExpressieIsGeenGroep() throws ExpressieException {
        TestUtils.assertExceptie("HISM_LAATSTE(Persoon.Adres.Woonplaatsnaam)", persoonslijst,
                "HISM_LAATSTE verwacht een groep als argument!");
    }

    @Test
    public void testHismLaatsteOpMaterieleGroep() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("HISM_LAATSTE(Persoon.Adres.Standaard)",
                persoonslijst), "{4@Persoon.Adres.Standaard}");

        final Persoonslijst vorigeHandeling = this.persoonslijst.beeldVan().vorigeHandeling();
        TestUtils.assertEqual(TestUtils.evalueer("HISM_LAATSTE(Persoon.Adres.Standaard)",
                vorigeHandeling), "{2@Persoon.Adres.Standaard}");

        final Persoonslijst vorigeVorigeHandeling = vorigeHandeling.beeldVan().vorigeHandeling();
        TestUtils.assertEqual(TestUtils.evalueer("HISM_LAATSTE(Persoon.Adres.Standaard)",
                vorigeVorigeHandeling), "{}");
    }

    @Test
    public void testHismLaatsteOpFormeleGroep() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("HISM_LAATSTE(Persoon.Geboorte)", persoonslijst), "{}");
    }


    @Test(expected = ExpressieException.class)
    public void test2ObjectenFout() throws ExpressieException {
        final Persoonslijst persoonslijst = TestBuilders.maakPersoonMet2Nationaliteiten(1);
        TestUtils.assertEqual(TestUtils.evalueer("HISM_LAATSTE(Persoon.Nationaliteit.Standaard)", persoonslijst), "{}");
    }

    @Test
    public void test2Objecten() throws ExpressieException {
        final Persoonslijst persoonslijst = TestBuilders.maakPersoonMet2Nationaliteiten(1);
        TestUtils.assertEqual(TestUtils.evalueer("AANTAL(MAP(Persoon.Nationaliteit.Standaard, g, HISM_LAATSTE(g)))", persoonslijst), "2");
    }


}
