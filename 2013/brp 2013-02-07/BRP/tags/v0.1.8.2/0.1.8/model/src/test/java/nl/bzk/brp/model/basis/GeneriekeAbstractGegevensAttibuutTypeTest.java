/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;


import junit.framework.Assert;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Postcode;
import org.junit.Test;

/**
 * Test klasse voor de {@link AbstractGegevensAttribuutType} en alle afgeleide daarvan. Dit wordt gedaan middels de
 * {@link nl.bzk.brp.model.attribuuttype.Gemeentedeel} klasse, die eerder genoemde abstracte klasse implementeert.
 */
public class GeneriekeAbstractGegevensAttibuutTypeTest {

    @Test
    public void testEqualsWaardeNotNull() {
        Gemeentedeel gemeentedeel = new Gemeentedeel("12");

        Assert.assertFalse("null: Zou ongelijk moeten zijn", gemeentedeel.equals(null));
        Assert.assertFalse("Integer: Zou ongelijk moeten zijn", gemeentedeel.equals(Integer.valueOf(12)));
        Assert.assertFalse("String: Zou ongelijk moeten zijn", gemeentedeel.equals("12"));
        Assert.assertFalse("Ander gegevens attribuuttype: Zou ongelijk moeten zijn",
            gemeentedeel.equals(new Postcode("12")));

        Assert.assertFalse("null waarde: Zou ongelijk moeten zijn", gemeentedeel.equals(new Gemeentedeel(null)));
        Assert.assertTrue("exacte waarde: Zou gelijk moeten zijn", gemeentedeel.equals(new Gemeentedeel("12")));
    }

    @Test
    public void testEqualsWaardeNull() {
        Gemeentedeel gemeentedeel = new Gemeentedeel(null);

        Assert.assertFalse("null: Zou ongelijk moeten zijn", gemeentedeel.equals(null));
        Assert.assertFalse("Integer: Zou ongelijk moeten zijn", gemeentedeel.equals(Integer.valueOf(12)));
        Assert.assertFalse("String: Zou ongelijk moeten zijn", gemeentedeel.equals("12"));
        Assert.assertFalse("Ander gegevens attribuuttype: Zou ongelijk moeten zijn",
            gemeentedeel.equals(new Postcode("12")));

        Assert.assertTrue("null waarde: Zou gelijk moeten zijn", gemeentedeel.equals(new Gemeentedeel(null)));
        Assert.assertFalse("niet null waarde: Zou ongelijk moeten zijn", gemeentedeel.equals(new Gemeentedeel("12")));
    }

    @Test
    public void testEqualsMetSoortNull() {
        Gemeentedeel gemeentedeel = new Gemeentedeel("12", false, SoortNull.NIET_GEAUTORISEERD);

        Assert.assertFalse("exacte waarde: Zou ongelijk moeten zijn vanwege ontbrekende soortnull",
            gemeentedeel.equals(new Gemeentedeel("12")));
        Assert.assertFalse("exacte waarde: Zou ongelijk moeten zijn vanwege andere soortnull",
            gemeentedeel.equals(new Gemeentedeel("12", false, SoortNull.NIET_ONDERSTEUND)));
        Assert.assertTrue("exacte waarde: Zou gelijk moeten zijn vanwege gelijke soortnull",
            gemeentedeel.equals(new Gemeentedeel("12", false, SoortNull.NIET_GEAUTORISEERD)));
    }

    @Test
    public void testEqualsMetInOnderzoek() {
        Gemeentedeel gemeentedeel = new Gemeentedeel("12", true, SoortNull.NIET_GEAUTORISEERD);

        Assert.assertFalse("exacte waarde: Zou ongelijk moeten zijn vanwege andere inonderzoek",
            gemeentedeel.equals(new Gemeentedeel("12")));
        Assert.assertFalse("exacte waarde: Zou ongelijk moeten zijn vanwege andere inonderzoek",
            gemeentedeel.equals(new Gemeentedeel("12", false, SoortNull.NIET_GEAUTORISEERD)));
        Assert.assertTrue("exacte waarde: Zou gelijk moeten zijn vanwege gelijke inonderzoek",
            gemeentedeel.equals(new Gemeentedeel("12", true, SoortNull.NIET_GEAUTORISEERD)));
    }
}
