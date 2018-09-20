/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.basis.AbstractAttribuut;

import org.junit.Assert;
import org.junit.Test;


public class AttribuutUtilTest {

    @Test
    public void testIsNotBlank() {
        Assert.assertFalse(AttribuutUtil.isNotBlank(null));
        Assert.assertFalse(AttribuutUtil.isNotBlank(new AbstractAttribuut<String>(null) {

        }));
        Assert.assertFalse(AttribuutUtil.isNotBlank(new AbstractAttribuut<String>("") {

        }));
        Assert.assertFalse(AttribuutUtil.isNotBlank(new AbstractAttribuut<String>(" ") {

        }));
        Assert.assertFalse(AttribuutUtil.isNotBlank(new AbstractAttribuut<String>("  ") {

        }));
        Assert.assertFalse(AttribuutUtil.isNotBlank(new AbstractAttribuut<Long>(null) {

        }));
        Assert.assertFalse(AttribuutUtil.isNotBlank(new AbstractAttribuut<Integer>(null) {

        }));
        Assert.assertFalse(AttribuutUtil.isNotBlank(new AbstractAttribuut<Short>(null) {

        }));

        Assert.assertTrue(AttribuutUtil.isNotBlank(new AbstractAttribuut<String>("A") {

        }));
        Assert.assertTrue(AttribuutUtil.isNotBlank(new AbstractAttribuut<String>("AB") {

        }));
        Assert.assertTrue(AttribuutUtil.isNotBlank(new AbstractAttribuut<String>(" A ") {

        }));
        Assert.assertTrue(AttribuutUtil.isNotBlank(new AbstractAttribuut<Long>(Long.valueOf(1)) {

        }));
        Assert.assertTrue(AttribuutUtil.isNotBlank(new AbstractAttribuut<Integer>(Integer.valueOf(2)) {

        }));
        Assert.assertTrue(AttribuutUtil.isNotBlank(new AbstractAttribuut<Short>(Short.valueOf("3")) {

        }));
    }

    @Test
    public void testIsNotEmpty() {
        // Null, zowel als attribuut als als waarde van het attribuut, dient als leeg beschouwd te worden.
        Assert.assertFalse(AttribuutUtil.isNotEmpty(null));
        Assert.assertFalse(AttribuutUtil.isNotEmpty(new VolgnummerAttribuut(null)));

        // Een lege string als waarde van een attribuut dient als leeg beschouwd te worden.
        Assert.assertFalse(AttribuutUtil.isNotEmpty(new VoornaamAttribuut("")));

        // Een attribuut met een waarde (zijnde niet een lege string) dient als niet leeg beschouwd te worden.
        Assert.assertTrue(AttribuutUtil.isNotEmpty(new VolgnummerAttribuut(3)));
        Assert.assertTrue(AttribuutUtil.isNotEmpty(new VoornaamAttribuut(" ")));
        Assert.assertTrue(AttribuutUtil.isNotEmpty(new VoornaamAttribuut("Test")));
    }

    @Test
    public void testIsBlank() {
        // Null, zowel als attribuut als als waarde van het attribuut, dient als leeg/blanco beschouwd te worden.
        Assert.assertTrue(AttribuutUtil.isBlank(null));
        Assert.assertTrue(AttribuutUtil.isBlank(new VolgnummerAttribuut(null)));

        // Zowel een lege als een string met alleen whitespaces als waarde van een attribuut dient als leeg/blanco
        // beschouwd te worden.
        Assert.assertTrue(AttribuutUtil.isBlank(new VoornaamAttribuut("")));
        Assert.assertTrue(AttribuutUtil.isBlank(new VoornaamAttribuut(" ")));

        // Een attribuut met een waarde (zijnde niet een lege of blanco string) dient als niet leeg beschouwd te worden.
        Assert.assertFalse(AttribuutUtil.isBlank(new VolgnummerAttribuut(3)));
        Assert.assertFalse(AttribuutUtil.isBlank(new VoornaamAttribuut("Test")));
    }

    @Test
    public void testDefaultWaardeIfBlank() {
        final String standaardWaarde = "Test";
        final String eigenWaarde = "Waarde";

        Assert.assertSame(standaardWaarde, AttribuutUtil.defaultWaardeIfBlank(null, standaardWaarde));
        Assert.assertSame(standaardWaarde, AttribuutUtil.defaultWaardeIfBlank(new VoornaamAttribuut(null), standaardWaarde));
        Assert.assertSame(standaardWaarde, AttribuutUtil.defaultWaardeIfBlank(new VoornaamAttribuut(""), standaardWaarde));
        Assert.assertSame(standaardWaarde, AttribuutUtil.defaultWaardeIfBlank(new VoornaamAttribuut(" "), standaardWaarde));

        Assert.assertSame(eigenWaarde, AttribuutUtil.defaultWaardeIfBlank(new VoornaamAttribuut(eigenWaarde), standaardWaarde));
    }
}
