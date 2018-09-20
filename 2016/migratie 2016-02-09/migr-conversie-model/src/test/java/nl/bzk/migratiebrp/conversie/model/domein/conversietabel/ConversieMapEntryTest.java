/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class ConversieMapEntryTest {

    private ConversieMapEntry<String, String> defaultConversieMap;
    private ConversieMapEntry<String, String> nullConversieMap;

    @Before
    public void setup() {
        defaultConversieMap = new ConversieMapEntry<>("LO3", "BRP");
        nullConversieMap = new ConversieMapEntry<>(null, null);
    }

    @Test
    public void testGetKey() {
        assertEquals("LO3", defaultConversieMap.getKey());
        assertNull(nullConversieMap.getKey());
    }

    @Test
    public void testGetValue() {
        assertEquals("BRP", defaultConversieMap.getValue());
        assertNull(nullConversieMap.getValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetValue() {
        defaultConversieMap.setValue("FOUT");
    }

}
