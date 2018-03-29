/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DeltaWijzigingTest {

    @Test
    public void testToString() {
        assertEquals("DW-001", DeltaWijziging.DW_001.toString());
        assertEquals("DW-002", DeltaWijziging.DW_002.toString());
        assertEquals("DW-003", DeltaWijziging.DW_003.toString());
        assertEquals("DW-011", DeltaWijziging.DW_011.toString());
        assertEquals("DW-012", DeltaWijziging.DW_012.toString());
        assertEquals("DW-021", DeltaWijziging.DW_021.toString());
        assertEquals("DW-022", DeltaWijziging.DW_022.toString());
        assertEquals("DW-023", DeltaWijziging.DW_023.toString());
        assertEquals("DW-025", DeltaWijziging.DW_025.toString());
        assertEquals("DW-031", DeltaWijziging.DW_031.toString());
        assertEquals("DW-032", DeltaWijziging.DW_032.toString());
        assertEquals("DW-034", DeltaWijziging.DW_034.toString());
        assertEquals("DW-901", DeltaWijziging.DW_901.toString());
    }

}
