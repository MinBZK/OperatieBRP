/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.jsf;

import org.junit.Assert;
import org.junit.Test;

public class MeldingenTest {

    private final Meldingen subject = new Meldingen();

    @Test
    public void get() {
        Assert.assertEquals("Bericht verwerken in ESB", subject.get("esb.verwerken"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void clear() {
        subject.clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void containsKey() {
        subject.containsKey(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void containsValue() {
        subject.containsValue(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void entrySet() {
        subject.entrySet();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void isEmpty() {
        subject.isEmpty();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void keySet() {
        subject.keySet();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void put() {
        subject.put(null, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void putAll() {
        subject.putAll(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void remove() {
        subject.remove(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void size() {
        subject.size();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void values() {
        subject.values();
    }
}
