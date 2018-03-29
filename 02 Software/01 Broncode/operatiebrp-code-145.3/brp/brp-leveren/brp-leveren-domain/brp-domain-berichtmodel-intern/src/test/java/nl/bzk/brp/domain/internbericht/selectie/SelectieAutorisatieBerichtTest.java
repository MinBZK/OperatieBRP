/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.selectie;

import static org.junit.Assert.*;

import org.junit.Test;

public class SelectieAutorisatieBerichtTest {

    @Test
    public void test() {
        final SelectieAutorisatieBericht selectieAutorisatieBericht = new SelectieAutorisatieBericht();
        selectieAutorisatieBericht.setSelectietaakId(1);
        selectieAutorisatieBericht.setDienstId(2);
        selectieAutorisatieBericht.setToegangLeveringsAutorisatieId(3);

        assertEquals(1, selectieAutorisatieBericht.getSelectietaakId().intValue());
        assertEquals(2, selectieAutorisatieBericht.getDienstId().intValue());
        assertEquals(3, selectieAutorisatieBericht.getToegangLeveringsAutorisatieId().intValue());
    }

    @Test
    public void testEquals() {
        final SelectieAutorisatieBericht selectieAutorisatieBericht = new SelectieAutorisatieBericht();
        selectieAutorisatieBericht.setSelectietaakId(1);
        selectieAutorisatieBericht.setDienstId(2);
        selectieAutorisatieBericht.setToegangLeveringsAutorisatieId(3);

        final SelectieAutorisatieBericht selectieAutorisatieBerichtKopie = new SelectieAutorisatieBericht();
        selectieAutorisatieBerichtKopie.setSelectietaakId(1);
        selectieAutorisatieBerichtKopie.setDienstId(2);
        selectieAutorisatieBerichtKopie.setToegangLeveringsAutorisatieId(3);

        final SelectieAutorisatieBericht selectieAutorisatieBerichtDienstWijktAf = new SelectieAutorisatieBericht();
        selectieAutorisatieBerichtDienstWijktAf.setSelectietaakId(1);
        selectieAutorisatieBerichtDienstWijktAf.setDienstId(4);
        selectieAutorisatieBerichtDienstWijktAf.setToegangLeveringsAutorisatieId(3);

        final SelectieAutorisatieBericht selectieAutorisatieBerichtTaakWijktAf = new SelectieAutorisatieBericht();
        selectieAutorisatieBerichtTaakWijktAf.setSelectietaakId(4);
        selectieAutorisatieBerichtTaakWijktAf.setDienstId(2);
        selectieAutorisatieBerichtTaakWijktAf.setToegangLeveringsAutorisatieId(3);

        final SelectieAutorisatieBericht selectieAutorisatieBerichtTlaWijktAf = new SelectieAutorisatieBericht();
        selectieAutorisatieBerichtTlaWijktAf.setSelectietaakId(4);
        selectieAutorisatieBerichtTlaWijktAf.setDienstId(2);
        selectieAutorisatieBerichtTlaWijktAf.setToegangLeveringsAutorisatieId(3);

        assertTrue(selectieAutorisatieBericht.equals(selectieAutorisatieBericht));
        assertTrue(selectieAutorisatieBericht.equals(selectieAutorisatieBerichtKopie));
        assertFalse(selectieAutorisatieBericht.equals(selectieAutorisatieBerichtDienstWijktAf));
        assertFalse(selectieAutorisatieBericht.equals(selectieAutorisatieBerichtTaakWijktAf));
        assertFalse(selectieAutorisatieBericht.equals(selectieAutorisatieBerichtTlaWijktAf));
        assertFalse(selectieAutorisatieBericht.equals(null));
        assertFalse(selectieAutorisatieBericht.equals(new SelectieAfnemerindicatieTaak()));
    }
}
