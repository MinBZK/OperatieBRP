/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import static org.junit.Assert.*;

import org.junit.Test;

public class AfnemerindicatieParametersTest {

    private final Long PERS_ID = 1L;
    private final Long PERS_LOCK_VERSIE = 2L;
    private final Long AFN_IND_LOCKVERSIE = 3L;


    @Test
    public void test() {
        final AfnemerindicatieParameters afnemerindicatieParameters = new AfnemerindicatieParameters(PERS_ID, PERS_LOCK_VERSIE, AFN_IND_LOCKVERSIE);

        assertEquals(PERS_ID, afnemerindicatieParameters.getPersoonId());
        assertEquals(PERS_LOCK_VERSIE, afnemerindicatieParameters.getPersoonLockVersie());
        assertEquals(AFN_IND_LOCKVERSIE, afnemerindicatieParameters.getAfnemerindicatieLockVersie());
    }

    @Test
    public void testEquals() throws Exception {
        final AfnemerindicatieParameters afnemerindicatieParameters = new AfnemerindicatieParameters(PERS_ID, PERS_LOCK_VERSIE, AFN_IND_LOCKVERSIE);
        final AfnemerindicatieParameters afnemerindicatieParameters1 = new AfnemerindicatieParameters(PERS_ID, PERS_LOCK_VERSIE, AFN_IND_LOCKVERSIE);
        final AfnemerindicatieParameters afnemerindicatieParameters2 = new AfnemerindicatieParameters(PERS_ID, PERS_LOCK_VERSIE+1, AFN_IND_LOCKVERSIE);
        final AfnemerindicatieParameters afnemerindicatieParameters3 = new AfnemerindicatieParameters(PERS_ID, PERS_LOCK_VERSIE, AFN_IND_LOCKVERSIE+1);

        assertTrue(afnemerindicatieParameters.equals(afnemerindicatieParameters1));
        assertTrue(afnemerindicatieParameters1.equals(afnemerindicatieParameters1));
        assertFalse(afnemerindicatieParameters1.equals(afnemerindicatieParameters2));
        assertFalse(afnemerindicatieParameters1.equals(afnemerindicatieParameters3));
        assertFalse(afnemerindicatieParameters1.equals(null));
        assertFalse(afnemerindicatieParameters1.equals(PERS_ID));
    }

    @Test
    public void testHashCode() throws Exception {
        final AfnemerindicatieParameters afnemerindicatieParameters = new AfnemerindicatieParameters(PERS_ID, PERS_LOCK_VERSIE, AFN_IND_LOCKVERSIE);
        assertEquals(1026, afnemerindicatieParameters.hashCode());

        assertEquals(961, new AfnemerindicatieParameters(PERS_ID, null, null).hashCode());
        assertEquals(964, new AfnemerindicatieParameters(PERS_ID, null, AFN_IND_LOCKVERSIE).hashCode());
        assertEquals(1023, new AfnemerindicatieParameters(PERS_ID, PERS_LOCK_VERSIE, null).hashCode());
    }

}