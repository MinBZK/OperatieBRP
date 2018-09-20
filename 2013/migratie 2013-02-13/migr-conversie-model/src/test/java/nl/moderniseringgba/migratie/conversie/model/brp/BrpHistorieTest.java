/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import static org.junit.Assert.*;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper;

import org.junit.Test;

public class BrpHistorieTest {

    @Test
    public void testGeenOverlap() {
        final BrpHistorie een = BrpStapelHelper.his(20000101, 20020101, 20000101, 20020101);
        final BrpHistorie ander = BrpStapelHelper.his(19900101, 19950101, 19900101, 19950101);
        // ander valt er helemaal voor
        assertFalse(een.geldigheidOverlapt(ander));
        // een valt er helemaal na
        assertFalse(ander.geldigheidOverlapt(een));
        final BrpHistorie eenZonderEind = BrpStapelHelper.his(20010101);
        assertFalse(eenZonderEind.geldigheidOverlapt(ander));
        assertFalse(ander.geldigheidOverlapt(eenZonderEind));
    }

    @Test
    public void testOverlap() {
        final BrpHistorie een = BrpStapelHelper.his(20000101, 20020101, 20000101, 20020101);
        final BrpHistorie ander = BrpStapelHelper.his(19900101, 20010101, 19900101, 20010101);
        // valt er gedeeltelijk voor
        assertTrue(een.geldigheidOverlapt(ander));
        assertTrue(ander.geldigheidOverlapt(een));

        // valt er helemaal in
        final BrpHistorie anderIn = BrpStapelHelper.his(20010101, 20010601, 20000601, 20000601);
        assertTrue(een.geldigheidOverlapt(anderIn));
        assertTrue(anderIn.geldigheidOverlapt(een));

        // valt er gedeeltelijk na
        final BrpHistorie anderInEnNa = BrpStapelHelper.his(20010101, 20050601, 20000601, 20050601);
        assertTrue(een.geldigheidOverlapt(anderInEnNa));
        assertTrue(anderInEnNa.geldigheidOverlapt(een));

        // valt er in, zonder einde
        final BrpHistorie anderInZonderEind = BrpStapelHelper.his(20010101, null, 20000601, null);
        assertTrue(een.geldigheidOverlapt(anderInZonderEind));
        assertTrue(anderInZonderEind.geldigheidOverlapt(een));
    }

    @Test
    public void testOverlapMetGelijkeDatums() {
        final BrpHistorie een = BrpStapelHelper.his(20000101, 20020101, 20000101, 20020101);
        final BrpHistorie ander = BrpStapelHelper.his(19900101, 20000101, 19900101, 20000101);
        // begint op einddatum van ander
        assertFalse(een.geldigheidOverlapt(ander));
        assertFalse(ander.geldigheidOverlapt(een));
    }

    @Test
    public void testOverlapGelijkeHistories() {
        final BrpHistorie een = BrpStapelHelper.his(20000101, 20020101, 20000101, 20020101);
        assertTrue(een.geldigheidOverlapt(een));
    }

}
