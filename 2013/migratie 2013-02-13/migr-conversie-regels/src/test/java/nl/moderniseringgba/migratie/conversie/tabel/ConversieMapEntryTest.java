/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Map;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversieMapEntry;

import org.junit.Before;
import org.junit.Test;

public class ConversieMapEntryTest {

    private Map.Entry<String, String> defaultConversieMap;
    private Map.Entry<String, String> nullConversieMap;

    @Before
    public void setup() {
        defaultConversieMap = new ConversieMapEntry<String, String>("LO3", "BRP");
        nullConversieMap = new ConversieMapEntry<String, String>(null, null);
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
