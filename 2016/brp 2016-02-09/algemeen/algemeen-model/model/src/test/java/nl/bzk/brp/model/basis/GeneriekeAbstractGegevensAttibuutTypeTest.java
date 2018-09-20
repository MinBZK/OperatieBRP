/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeentedeelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


/**
 * Test klasse voor de {@link AbstractGegevensAttribuutType} en alle afgeleide daarvan. Dit wordt gedaan middels de
 * {@link nl.bzk.brp.model.algemeen.attribuuttype.kern.Gemeentedeel} klasse, die eerder genoemde abstracte klasse
 * implementeert.
 */
public class GeneriekeAbstractGegevensAttibuutTypeTest {

    @Test
    public void testEqualsWaardeNotNull() {
        GemeentedeelAttribuut gemeentedeel = new GemeentedeelAttribuut("12");

        Assert.assertNotEquals("null: Zou ongelijk moeten zijn", gemeentedeel, null);
        Assert.assertFalse("Integer: Zou ongelijk moeten zijn", gemeentedeel.equals(Integer.valueOf(12)));
        Assert.assertFalse("String: Zou ongelijk moeten zijn", gemeentedeel.equals("12"));
        Assert.assertFalse("Ander gegevens attribuuttype: Zou ongelijk moeten zijn",
                gemeentedeel.equals(new PostcodeAttribuut("12")));

        Assert.assertFalse("null waarde: Zou ongelijk moeten zijn",
                gemeentedeel.equals(new GemeentedeelAttribuut(null)));
        Assert.assertTrue("exacte waarde: Zou gelijk moeten zijn",
                gemeentedeel.equals(new GemeentedeelAttribuut("12")));
    }

    @Test
    public void testEqualsWaardeNull() {
        GemeentedeelAttribuut gemeentedeel = new GemeentedeelAttribuut(null);

        Assert.assertNotEquals("null: Zou ongelijk moeten zijn", gemeentedeel, null);
        Assert.assertFalse("Integer: Zou ongelijk moeten zijn", gemeentedeel.equals(Integer.valueOf(12)));
        Assert.assertFalse("String: Zou ongelijk moeten zijn", gemeentedeel.equals("12"));
        Assert.assertFalse("Ander gegevens attribuuttype: Zou ongelijk moeten zijn",
                gemeentedeel.equals(new PostcodeAttribuut("12")));

        Assert.assertTrue("null waarde: Zou gelijk moeten zijn", gemeentedeel.equals(new GemeentedeelAttribuut(null)));
        Assert.assertFalse("niet null waarde: Zou ongelijk moeten zijn",
                gemeentedeel.equals(new GemeentedeelAttribuut("12")));
    }

    @Ignore
    // TODO Oussama Chougna: soortnull en onderzoek veldje zijn buiten scope
    @Test
    public void testEqualsMetSoortNull() {
        GemeentedeelAttribuut gemeentedeel = new GemeentedeelAttribuut("12");

        Assert.assertFalse("exacte waarde: Zou ongelijk moeten zijn vanwege ontbrekende soortnull",
                gemeentedeel.equals(new GemeentedeelAttribuut("12")));
        Assert.assertFalse("exacte waarde: Zou ongelijk moeten zijn vanwege andere soortnull",
                gemeentedeel.equals(new GemeentedeelAttribuut("12")));
        Assert.assertTrue("exacte waarde: Zou gelijk moeten zijn vanwege gelijke soortnull",
                gemeentedeel.equals(new GemeentedeelAttribuut("12")));
    }

    @Ignore
    // TODO Oussama Chougna: soortnull en onderzoek veldje zijn buiten scope
    @Test
    public void testEqualsMetInOnderzoek() {
        GemeentedeelAttribuut gemeentedeel = new GemeentedeelAttribuut("12");

        Assert.assertFalse("exacte waarde: Zou ongelijk moeten zijn vanwege andere inonderzoek",
                gemeentedeel.equals(new GemeentedeelAttribuut("12")));
        Assert.assertFalse("exacte waarde: Zou ongelijk moeten zijn vanwege andere inonderzoek",
                gemeentedeel.equals(new GemeentedeelAttribuut("12")));
        Assert.assertTrue("exacte waarde: Zou gelijk moeten zijn vanwege gelijke inonderzoek",
                gemeentedeel.equals(new GemeentedeelAttribuut("12")));
    }
}
