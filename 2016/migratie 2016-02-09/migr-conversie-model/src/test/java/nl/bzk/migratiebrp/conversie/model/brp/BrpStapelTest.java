/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;

import org.junit.Test;

public final class BrpStapelTest {

    @Test
    public void testBevatActueel() {
        final BrpStapel<BrpNationaliteitInhoud> stapelActueel = maakStapel(BrpStapelHelper.his(20010101, 20080101000000L));
        assertTrue(stapelActueel.bevatActueel());

        final BrpStapel<BrpNationaliteitInhoud> stapelEindig = maakStapel(BrpStapelHelper.his(20010101, 20080101, 20080101000000L, null));
        assertFalse(stapelEindig.bevatActueel());

        final BrpStapel<BrpNationaliteitInhoud> stapelVervallen = maakStapel(BrpStapelHelper.his(20010101, null, 20080101000000L, 20100101000000L));
        assertFalse(stapelVervallen.bevatActueel());

        final BrpStapel<BrpNationaliteitInhoud> stapelEindigEnVervallen =
                maakStapel(BrpStapelHelper.his(20010101, 20080101, 20080101000000L, 20100101000000L));
        assertFalse(stapelEindigEnVervallen.bevatActueel());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetActueelBijMeerdereActuele() {
        BrpStapel<BrpNationaliteitInhoud> stapel = maakStapel(2, 1);
        stapel.bevatActueel();
    }

    @Test
    public void testGetActueelOpActieVervalBij2Actuelen() {
        BrpStapel<BrpNationaliteitInhoud> stapel = maakStapel(2, 1);
        assertNotNull(stapel.getActueelOpActieVerval());
    }

    @Test
    public void testGetActueelOpActieVervalBij1Actuele() {
        BrpStapel<BrpNationaliteitInhoud> stapel = maakStapel(1, 2);
        assertNotNull(stapel.getActueelOpActieVerval());
    }

    @Test
    public void testGetActueelOpActieVervalBij0Actuele() {
        BrpStapel<BrpNationaliteitInhoud> stapel = maakStapel(0, 5);
        assertNull(stapel.getActueelOpActieVerval());
    }

    private BrpStapel<BrpNationaliteitInhoud> maakStapel(final BrpHistorie historie) {
        final List<BrpGroep<BrpNationaliteitInhoud>> groepen = new ArrayList<>();
        final BrpNationaliteitInhoud inhoud =
                new BrpNationaliteitInhoud(new BrpNationaliteitCode(Short.parseShort("0002")), null, null, null, null, null, null);
        final BrpActie actieInhoud = BrpStapelHelper.act(1, 20080101);
        final BrpGroep<BrpNationaliteitInhoud> nationaliteit = new BrpGroep<>(inhoud, historie, actieInhoud, null, null);
        groepen.add(nationaliteit);
        return new BrpStapel<>(groepen);
    }

    private BrpStapel<BrpNationaliteitInhoud> maakStapel(int aantalActuelen, int aantalVervallen) {
        final List<BrpGroep<BrpNationaliteitInhoud>> groepen = new ArrayList<>();
        final BrpNationaliteitInhoud inhoud =
                new BrpNationaliteitInhoud(new BrpNationaliteitCode(Short.parseShort("0002")), null, null, null, null, null, null);
        final BrpActie actieInhoud = BrpStapelHelper.act(1, 20080101);
        for (int i = 0; i < aantalActuelen; i++) {
            final BrpGroep<BrpNationaliteitInhoud> nationaliteit = new BrpGroep<>(inhoud, BrpHistorieTest.createdefaultInhoud(), actieInhoud, null, null);
            groepen.add(nationaliteit);
        }
        for (int i = 0; i < aantalVervallen; i++) {
            final BrpGroep<BrpNationaliteitInhoud> nationaliteit =
                    new BrpGroep<>(inhoud, BrpHistorieTest.createNietActueleInhoud(), actieInhoud, null, null);
            groepen.add(nationaliteit);
        }
        return new BrpStapel<>(groepen);
    }
}
