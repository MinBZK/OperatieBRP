/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BrpIdentiteitInhoudTest {

    public static BrpStapel<BrpIdentiteitInhoud> createStapel() {
        List<BrpGroep<BrpIdentiteitInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpIdentiteitInhoud> groep = new BrpGroep<>(BrpIdentiteitInhoud.IDENTITEIT, BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        BrpStapel<BrpIdentiteitInhoud> result = new BrpStapel<>(groepen);
        return result;
    }

    @Test
    public void testIsLeeg(){
        assertFalse(BrpIdentiteitInhoud.IDENTITEIT.isLeeg());
    }
}