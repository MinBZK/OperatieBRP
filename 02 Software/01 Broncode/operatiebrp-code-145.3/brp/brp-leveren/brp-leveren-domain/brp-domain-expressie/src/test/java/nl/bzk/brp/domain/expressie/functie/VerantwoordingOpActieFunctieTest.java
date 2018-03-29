/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.ExpressietaalTestPersoon;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Test;

/**
 */
public class VerantwoordingOpActieFunctieTest {


    @Test
    public void testExpressieOpActieVerval() throws ExpressieException {
        final Persoonslijst persoonslijst = ExpressietaalTestPersoon.PERSOONSLIJST;

        // geeft nooit resultaat, want is per definitie en vervallen record
        TestUtils.testEvaluatie("ACTIE(Persoon.Identificatienummers.ActieVerval, [Actie.SoortNaam])", "{}", persoonslijst);
    }

    @Test
    public void testExpressieOpActie() throws ExpressieException {
        final Persoonslijst persoonslijst = ExpressietaalTestPersoon.PERSOONSLIJST;

        TestUtils.testEvaluatie("ACTIE(Persoon.Identificatienummers.ActieInhoud, [Document.Aktenummer])", "{\"aktenummer3000\"}", persoonslijst);
        TestUtils.testEvaluatie("ACTIE(Persoon.Identificatienummers.ActieInhoud, [Document.Omschrijving])", "{\"omschrijving3000\"}", persoonslijst);
        TestUtils.testEvaluatie("ACTIE(Persoon.Identificatienummers.ActieInhoud, [Document.PartijCode])", "{\"000123\"}", persoonslijst);
        TestUtils.testEvaluatie("ACTIE(Persoon.Identificatienummers.ActieInhoud, [Document.SoortNaam])", "{\"soortnaam3000\"}", persoonslijst);
        TestUtils.testEvaluatie("ACTIE(Persoon.Identificatienummers.ActieInhoud, [ActieBron.RechtsgrondCode])", "{\"3000\"}", persoonslijst);
        TestUtils.testEvaluatie("ACTIE(Persoon.Identificatienummers.ActieInhoud, [ActieBron.Rechtsgrondomschrijving])", "{\"rechtsgrondomschrijving3000\"}", persoonslijst);
        TestUtils.testEvaluatie("ACTIE(Persoon.Identificatienummers.ActieInhoud, [Actie.SoortNaam])", "{\"" + SoortActie.ONTRELATEREN.getId() + "\"}", persoonslijst);
        TestUtils.testEvaluatie("ACTIE(Persoon.Identificatienummers.ActieInhoud, [Actie.PartijCode])", "{\"000123\"}", persoonslijst);
        TestUtils.testEvaluatie("ACTIE(Persoon.Identificatienummers.ActieInhoud, [Actie.TijdstipRegistratie]) "
                + "= Persoon.Identificatienummers.TijdstipRegistratie", "WAAR", persoonslijst);
        TestUtils.testEvaluatie("ACTIE(Persoon.Identificatienummers.ActieInhoud, [Actie.DatumOntlening])", "{2001/01/01}", persoonslijst);

        //statisch leeg
        TestUtils.testEvaluatie("ACTIE({}, [Actie.SoortNaam])", "{}");

        //dynamisch leeg
        TestUtils.testEvaluatie("ACTIE(Persoon.Verblijfsrecht.ActieInhoud, [Document.Aktenummer])", "{}", persoonslijst);

        //dynamische null
        TestUtils.testEvaluatie("ACTIE(ALS(2 > 1, NULL, {}), [Actie.SoortNaam])", "{}");
    }

    @Test
    public void testExcepties() {
        TestUtils.assertExceptie("ACTIE(Persoon.Identificatienummers.ActieInhoud)",
                "ACTIE Functie vereist 2 argumenten");
        TestUtils.assertExceptie("ACTIE(Persoon.Identificatienummers.ActieInhoud, 123)",
                "Ongeldig 2e argument : Argument 2 moet een [elementexpressie] zijn");
        TestUtils.assertExceptie("ACTIE(Persoon.Identificatienummers.ActieInhoud, [AdministratieveHandeling.TijdstipRegistratie])",
                "Ongeldig 2e argument : Argument 2 is geen geldige verantwoordingsexpressie");

        //statische null
        TestUtils.assertExceptie("ACTIE(NULL, [AdministratieveHandeling.TijdstipRegistratie])",
                "Ongeldig 1e argument : Argument 1 moet een LIJST opleveren");
    }
}
