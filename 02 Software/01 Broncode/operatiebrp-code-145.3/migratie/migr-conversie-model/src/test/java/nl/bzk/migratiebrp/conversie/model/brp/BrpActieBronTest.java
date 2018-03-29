/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoudTestUtil;
import org.junit.Test;

public class BrpActieBronTest {

    @Test
    public void testEquals() throws Exception {
        List<BrpGroep<BrpDocumentInhoud>> groepen = new ArrayList<>();

        BrpDocumentInhoud inhoud = BrpDocumentInhoudTestUtil.createInhoud();
        BrpHistorie history = BrpHistorieTest.createdefaultInhoud();
        BrpGroep<BrpDocumentInhoud> groep = new BrpGroep<>(inhoud, history, null, null, null);
        groepen.add(groep);
        BrpStapel<BrpDocumentInhoud> docInhoud = new BrpStapel<>(groepen);
        BrpString rechtsgrond = null;
        BrpActieBron bron = new BrpActieBron(docInhoud, rechtsgrond);
        assertTrue(bron.equals(returnZelfde(bron)));
        assertFalse(bron.equals(bron.getDocumentStapel()));

    }

    private BrpActieBron returnZelfde(BrpActieBron b) {
        return b;
    }
}
