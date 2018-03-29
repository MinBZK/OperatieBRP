/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.NaamgebruikConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;

import org.junit.Test;

public class NaamgebruikConversietabelTest {

    @Test
    public void testLo3NaarBrp() {
        final NaamgebruikConversietabel tabel = new NaamgebruikConversietabel();
        assertEquals(BrpNaamgebruikCode.E, tabel.converteerNaarBrp(null));
        assertEquals(BrpNaamgebruikCode.E, tabel.converteerNaarBrp(Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement()));
        assertEquals(BrpNaamgebruikCode.P, tabel.converteerNaarBrp(Lo3AanduidingNaamgebruikCodeEnum.GESLACHTSNAAM_PARTNER.asElement()));
        assertEquals(
                BrpNaamgebruikCode.V,
                tabel.converteerNaarBrp(Lo3AanduidingNaamgebruikCodeEnum.GESLACHTNAAM_PARTER_VOOR_EIGEN_GESLACHTSNAAM.asElement()));
        assertEquals(
                BrpNaamgebruikCode.N,
                tabel.converteerNaarBrp(Lo3AanduidingNaamgebruikCodeEnum.GESLACHTNAAM_PARTER_NA_EIGEN_GESLACHTSNAAM.asElement()));
    }

    @Test
    public void testBrpNaarLo3() {
        final NaamgebruikConversietabel tabel = new NaamgebruikConversietabel();
        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement(), tabel.converteerNaarLo3(BrpNaamgebruikCode.E));
        assertEquals(Lo3AanduidingNaamgebruikCodeEnum.GESLACHTSNAAM_PARTNER.asElement(), tabel.converteerNaarLo3(BrpNaamgebruikCode.P));
        assertEquals(
                Lo3AanduidingNaamgebruikCodeEnum.GESLACHTNAAM_PARTER_VOOR_EIGEN_GESLACHTSNAAM.asElement(),
                tabel.converteerNaarLo3(BrpNaamgebruikCode.V));
        assertEquals(
                Lo3AanduidingNaamgebruikCodeEnum.GESLACHTNAAM_PARTER_NA_EIGEN_GESLACHTSNAAM.asElement(),
                tabel.converteerNaarLo3(BrpNaamgebruikCode.N));
    }

}
