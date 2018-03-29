/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.ExpressietaalTestPersoon;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class VerantwoordingOpHandelingFunctieTest {

    final Persoonslijst persoonslijst = ExpressietaalTestPersoon.PERSOONSLIJST;

    @Test
    public void testGeldigVerantwoordingElement() {
        VerantwoordingOpHandelingFunctie functie = new VerantwoordingOpHandelingFunctie();

        Assert.assertFalse(functie.isGeldigVerantwoordingElement(ElementHelper.getObjectElement(Element.ADMINISTRATIEVEHANDELING)));
        Assert.assertTrue(functie.isGeldigVerantwoordingElement(ElementHelper.getAttribuutElement(Element.ADMINISTRATIEVEHANDELING_PARTIJCODE)));
        Assert.assertTrue(functie.isGeldigVerantwoordingElement(ElementHelper.getAttribuutElement(Element.ACTIE_PARTIJCODE)));
        Assert.assertTrue(functie.isGeldigVerantwoordingElement(ElementHelper.getAttribuutElement(Element.ACTIEBRON_DOCUMENT)));
        Assert.assertTrue(functie.isGeldigVerantwoordingElement(ElementHelper.getAttribuutElement(Element.DOCUMENT_PARTIJCODE)));
        Assert.assertFalse(functie.isGeldigVerantwoordingElement(ElementHelper.getAttribuutElement(Element.BETROKKENHEID_NADEREAANDUIDINGVERVAL)));
    }

    @Test
    public void test() throws ExpressieException {
        TestUtils.testEvaluatie("AH(Persoon.Identificatienummers.ActieInhoud, "
                + "[Document.Aktenummer])", "{\"aktenummer3000\", \"aktenummer30001\"}", persoonslijst);
        TestUtils.testEvaluatie("AH(Persoon.Identificatienummers.ActieInhoud, "
                + "[Document.Omschrijving])", "{\"omschrijving3000\", \"omschrijving30001\"}", persoonslijst);
        TestUtils.testEvaluatie("AH(Persoon.Identificatienummers.ActieInhoud, "
                + "[Document.PartijCode])", "{\"000123\", \"000123\"}", persoonslijst);
        TestUtils.testEvaluatie("AH(Persoon.Identificatienummers.ActieInhoud, "
                + "[Document.SoortNaam])", "{\"soortnaam3000\", \"soortnaam30001\"}", persoonslijst);
        TestUtils.testEvaluatie("AH(Persoon.Identificatienummers.ActieInhoud, "
                + "[ActieBron.RechtsgrondCode])", "{\"3000\", \"3001\"}", persoonslijst);
        TestUtils.testEvaluatie("AH(Persoon.Identificatienummers.ActieInhoud, [ActieBron.Rechtsgrondomschrijving])",
                "{\"rechtsgrondomschrijving3000\", \"rechtsgrondomschrijving30001\"}", persoonslijst);
        TestUtils.testEvaluatie("AH(Persoon.Identificatienummers.ActieInhoud, "
                + "[Actie.SoortNaam])", "{\"" + SoortActie.ONTRELATEREN.getId() + "\", \"" + SoortActie.ONTRELATEREN.getId() + "\"}", persoonslijst);
        TestUtils.testEvaluatie("AH(Persoon.Identificatienummers.ActieInhoud, "
                + "[Actie.PartijCode])", "{\"000123\", \"000123\"}", persoonslijst);
        TestUtils.testEvaluatie("AH(Persoon.Identificatienummers.ActieInhoud, [Actie.TijdstipRegistratie]) "
                + "A= Persoon.Identificatienummers.TijdstipRegistratie", "WAAR", persoonslijst);
        TestUtils.testEvaluatie("AH(Persoon.Identificatienummers.ActieInhoud, [Actie.DatumOntlening])", "{2001/01/01, 2001/01/01}", persoonslijst);

        TestUtils.testEvaluatie("AH(Persoon.Identificatienummers.ActieInhoud, [AdministratieveHandeling.PartijCode])", "{\"000123\"}", persoonslijst);
        TestUtils.testEvaluatie("AH(Persoon.Identificatienummers.ActieInhoud, [AdministratieveHandeling.ToelichtingOntlening])", "{\"toelichting3000\"}",
                persoonslijst);
        TestUtils.testEvaluatie("AH(Persoon.Identificatienummers.ActieInhoud, [AdministratieveHandeling.TijdstipRegistratie])"
                + "= Persoon.Identificatienummers.TijdstipRegistratie", "WAAR", persoonslijst);

        //statisch leeg
        TestUtils.testEvaluatie("AH({}, [Document.Aktenummer])", "{}", persoonslijst);

        //dynamische leeg
        TestUtils.testEvaluatie("AH(Persoon.Verblijfsrecht.ActieInhoud, [Document.Aktenummer])", "{}", persoonslijst);

        //dynamische null
        TestUtils.testEvaluatie("AH(ALS(2>1, NULL, {}), [AdministratieveHandeling.TijdstipRegistratie])", "{}");
    }

    @Test
    public void testExcepties() {
        TestUtils.assertExceptie("AH(Persoon.Identificatienummers.ActieInhoud)",
                "ACTIE Functie vereist 2 argumenten");
        TestUtils.assertExceptie("AH(Persoon.Identificatienummers.ActieInhoud, 123)",
                "Ongeldig 2e argument : Argument 2 moet een [elementexpressie] zijn");
        TestUtils.assertExceptie("AH(Persoon.Identificatienummers.ActieInhoud, [Persoon])",
                "Ongeldig 2e argument : Argument 2 is geen geldige verantwoordingsexpressie");
        TestUtils.assertExceptie("AH(Persoon.Identificatienummers.ActieInhoud, [Persoon.Voornaam.TijdstipRegistratie])", "Ongeldig 2e argument : Argument 2 is geen geldige verantwoordingsexpressie");

        //statische null
        TestUtils.assertExceptie("AH(NULL, [AdministratieveHandeling.TijdstipRegistratie])",
                "Ongeldig 1e argument : Argument 1 moet een LIJST opleveren");
    }
}
