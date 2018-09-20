/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;

public class VergelijkSqlTest {

    @Test
    public void testVergelijkOk() {
        final List<Map<String, Object>> origin = new ArrayList<>();
        final Map<String, Object> rij1 = new TreeMap<>();
        rij1.put("Kolom1", "Waarde1");
        rij1.put("Kolom2", 2);

        final Map<String, Object> rij2 = new TreeMap<>();
        rij2.put("Kolom1", "Waarde3");
        rij2.put("Kolom2", 4);

        origin.add(rij1);
        origin.add(rij2);
        final StringBuilder verschillenLog = new StringBuilder();
        assertTrue(VergelijkSql.vergelijkSqlResultaten(verschillenLog, origin, origin));
        assertTrue(verschillenLog.length() == 0);
    }

    @Test
    public void testVergelijkNokVerschilInWaarden() {
        final List<Map<String, Object>> origin = new ArrayList<>();
        final List<Map<String, Object>> actual = new ArrayList<>();

        final Map<String, Object> rij1 = new TreeMap<>();
        rij1.put("Kolom1", "Waarde1");
        rij1.put("Kolom2", 2);

        final Map<String, Object> rij2 = new TreeMap<>();
        rij2.put("Kolom1", "Waarde3");
        rij2.put("Kolom2", 4);

        final Map<String, Object> rij2a = new TreeMap<>();
        rij2a.put("Kolom1", "Waarde5");
        rij2a.put("Kolom2", 4);

        origin.add(rij1);
        origin.add(rij2);

        actual.add(rij2a);
        actual.add(rij1);

        final StringBuilder verschillenLog = new StringBuilder();
        assertFalse(VergelijkSql.vergelijkSqlResultaten(verschillenLog, actual, origin));
        assertTrue(verschillenLog.length() != 0);
        assertTrue(verschillenLog.toString().contains("Waarde5"));
        assertTrue(verschillenLog.toString().contains("Waarde3"));
    }

    @Test
    public void testVergelijkNokActueelMeerRijen() {
        final List<Map<String, Object>> origin = new ArrayList<>();
        final List<Map<String, Object>> actual = new ArrayList<>();

        final Map<String, Object> rij1 = new TreeMap<>();
        rij1.put("Kolom1", "Waarde1");
        rij1.put("Kolom2", 2);

        final Map<String, Object> rij2 = new TreeMap<>();
        rij2.put("Kolom1", "Waarde3");
        rij2.put("Kolom2", 4);

        final Map<String, Object> rij2a = new TreeMap<>();
        rij2a.put("Kolom1", "Waarde5");
        rij2a.put("Kolom2", 4);

        final Map<String, Object> rij3 = new TreeMap<>();
        rij2a.put("Kolom1", "Waarde6");
        rij2a.put("Kolom2", 7);

        origin.add(rij1);
        origin.add(rij2);

        actual.add(rij3);
        actual.add(rij2a);
        actual.add(rij1);

        final StringBuilder verschillenLog = new StringBuilder();
        assertFalse(VergelijkSql.vergelijkSqlResultaten(verschillenLog, actual, origin));
        assertTrue(verschillenLog.length() != 0);
        assertTrue(verschillenLog.toString().contains("Aantal verkregen rijen"));
    }
}
