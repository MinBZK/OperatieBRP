/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.NaamgebruikConversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpWijzeGebruikGeslachtsnaamCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;

import org.junit.Test;

public class NaamgebruikConversietabelTest {

    @Test
    public void testLo3NaarBrp() {
        final NaamgebruikConversietabel tabel = new NaamgebruikConversietabel();
        assertNull(tabel.converteerNaarBrp(null));
        assertEquals(BrpWijzeGebruikGeslachtsnaamCode.E,
                tabel.converteerNaarBrp(Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement()));
        assertEquals(BrpWijzeGebruikGeslachtsnaamCode.P,
                tabel.converteerNaarBrp(Lo3AanduidingNaamgebruikCodeEnum.GESLACHTSNAAM_PARTNER.asElement()));
        assertEquals(BrpWijzeGebruikGeslachtsnaamCode.V,
                tabel.converteerNaarBrp(Lo3AanduidingNaamgebruikCodeEnum.GESLACHTNAAM_PARTER_VOOR_EIGEN_GESLACHTSNAAM
                        .asElement()));
        assertEquals(BrpWijzeGebruikGeslachtsnaamCode.N,
                tabel.converteerNaarBrp(Lo3AanduidingNaamgebruikCodeEnum.GESLACHTNAAM_PARTER_NA_EIGEN_GESLACHTSNAAM
                        .asElement()));
    }

    @Test
    public void testBrpNaarLo3() {
        final NaamgebruikConversietabel tabel = new NaamgebruikConversietabel();
        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement(),
                tabel.converteerNaarLo3(BrpWijzeGebruikGeslachtsnaamCode.E));
        assertEquals(Lo3AanduidingNaamgebruikCodeEnum.GESLACHTSNAAM_PARTNER.asElement(),
                tabel.converteerNaarLo3(BrpWijzeGebruikGeslachtsnaamCode.P));
        assertEquals(Lo3AanduidingNaamgebruikCodeEnum.GESLACHTNAAM_PARTER_VOOR_EIGEN_GESLACHTSNAAM.asElement(),
                tabel.converteerNaarLo3(BrpWijzeGebruikGeslachtsnaamCode.V));
        assertEquals(Lo3AanduidingNaamgebruikCodeEnum.GESLACHTNAAM_PARTER_NA_EIGEN_GESLACHTSNAAM.asElement(),
                tabel.converteerNaarLo3(BrpWijzeGebruikGeslachtsnaamCode.N));
    }

}
