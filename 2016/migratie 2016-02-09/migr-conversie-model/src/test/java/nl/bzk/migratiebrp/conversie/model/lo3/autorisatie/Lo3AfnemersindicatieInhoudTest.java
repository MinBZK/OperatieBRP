/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.autorisatie;

import junit.framework.Assert;

import org.junit.Test;

public class Lo3AfnemersindicatieInhoudTest {

    @Test
    public void testEmpty() {
        final Lo3AfnemersindicatieInhoud subject = new Lo3AfnemersindicatieInhoud();
        final Lo3AfnemersindicatieInhoud equals = new Lo3AfnemersindicatieInhoud();

        Assert.assertTrue(subject.isLeeg());

        Assert.assertEquals(subject, equals);
        Assert.assertEquals(subject.hashCode(), equals.hashCode());
        Assert.assertEquals(subject.toString(), equals.toString());
    }

    @Test
    public void testFilled() {
        final Lo3AfnemersindicatieInhoud subject = new Lo3AfnemersindicatieInhoud(123);
        final Lo3AfnemersindicatieInhoud equals = new Lo3AfnemersindicatieInhoud(123);
        final Lo3AfnemersindicatieInhoud different = new Lo3AfnemersindicatieInhoud(456);

        Assert.assertEquals(Integer.valueOf(123), subject.getAfnemersindicatie());
        Assert.assertFalse(subject.isLeeg());
        Assert.assertEquals(subject, subject);
        Assert.assertEquals(subject, equals);
        Assert.assertFalse(subject.equals(new Object()));
        Assert.assertFalse(subject.equals(different));
        Assert.assertEquals(subject.hashCode(), equals.hashCode());
        Assert.assertEquals(subject.toString(), equals.toString());
    }
}
