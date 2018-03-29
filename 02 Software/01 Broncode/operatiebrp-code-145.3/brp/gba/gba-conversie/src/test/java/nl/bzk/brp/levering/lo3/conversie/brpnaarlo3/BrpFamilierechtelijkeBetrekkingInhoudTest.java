/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.brpnaarlo3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import org.junit.Test;

public class BrpFamilierechtelijkeBetrekkingInhoudTest {
    private final BrpFamilierechtelijkeBetrekkingInhoud subject;
    private final BrpFamilierechtelijkeBetrekkingInhoud subjectLeegDatum;
    private final BrpDatum datum;
    private final BrpDatum leegDatum;

    public BrpFamilierechtelijkeBetrekkingInhoudTest() {
        datum = new BrpDatum(20161004, null);
        leegDatum = new BrpDatum(null, new Lo3Onderzoek());
        subject = new BrpFamilierechtelijkeBetrekkingInhoud(datum);
        subjectLeegDatum = new BrpFamilierechtelijkeBetrekkingInhoud(leegDatum);
    }

    @Test
    public void testIsLeeg() {
        assertEquals(false, subject.isLeeg());
        assertEquals(true, subjectLeegDatum.isLeeg());
    }

    @Test
    public void testGetDatumFamilierechtelijkeBetrekking() {
        assertEquals(datum, subject.getDatumFamilierechtelijkeBetrekking());
        assertEquals(leegDatum, subjectLeegDatum.getDatumFamilierechtelijkeBetrekking());
    }

    @Test
    public void testEquals() {
        assertEquals(true, subject.equals(subject));
        assertEquals(true, subject.equals(new BrpFamilierechtelijkeBetrekkingInhoud(datum)));
        assertEquals(false, subject.equals(new Object()));
        assertEquals(false, subject.equals(null));
        assertEquals(true, subject.equals(new BrpFamilierechtelijkeBetrekkingInhoud(new BrpDatum(20161004, null))));
    }

    @Test
    public void testHashcode() {
        assertEquals(new BrpFamilierechtelijkeBetrekkingInhoud(datum).hashCode(), subject.hashCode());
        assertEquals(new BrpFamilierechtelijkeBetrekkingInhoud(new BrpDatum(20161004, null)).hashCode(), subject.hashCode());
        assertNotEquals(new BrpFamilierechtelijkeBetrekkingInhoud(new BrpDatum(20161003, null)).hashCode(), subject.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
            "BrpFamilierechtelijkeBetrekkingInhoud[datumFamilierechtelijkeBetrekking=BrpDatum[waarde=20161004,onderzoek=<null>]]",
            subject.toString());
        assertEquals(
            "BrpFamilierechtelijkeBetrekkingInhoud[datumFamilierechtelijkeBetrekking=BrpDatum[waarde=<null>,onderzoek=Lo3Onderzoek[aanduidingGegevensInOnderzoek=<null>,datumIngangOnderzoek=<null>,datumEindeOnderzoek=<null>]]]",
            subjectLeegDatum.toString());
    }
}
