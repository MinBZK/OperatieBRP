/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversietabelFactory;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;

public class RedenOpschortingConversietabelTest extends AbstractDatabaseTest {

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<Lo3RedenOpschortingBijhoudingCode, BrpRedenOpschortingBijhoudingCode> conversietabel;

    @Before
    public void setup() {
        conversietabel = conversietabelFactory.createRedenOpschortingBijhoudingConversietabel();
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testConverteerNaarBrp() {
        assertEquals(BrpRedenOpschortingBijhoudingCode.MINISTERIEEL_BESLUIT,
                conversietabel.converteerNaarBrp(Lo3RedenOpschortingBijhoudingCodeEnum.MINISTERIEEL_BESLUIT
                        .asElement()));
        assertEquals(BrpRedenOpschortingBijhoudingCode.FOUT,
                conversietabel.converteerNaarBrp(Lo3RedenOpschortingBijhoudingCodeEnum.FOUT.asElement()));
        assertEquals(BrpRedenOpschortingBijhoudingCode.ONBEKEND,
                conversietabel.converteerNaarBrp(Lo3RedenOpschortingBijhoudingCodeEnum.ONBEKEND.asElement()));
        assertEquals(BrpRedenOpschortingBijhoudingCode.OVERLIJDEN,
                conversietabel.converteerNaarBrp(Lo3RedenOpschortingBijhoudingCodeEnum.OVERLIJDEN.asElement()));
        assertNull(conversietabel.converteerNaarBrp(null));
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testConverteerNaarLo3() {
        assertEquals(Lo3RedenOpschortingBijhoudingCodeEnum.MINISTERIEEL_BESLUIT.asElement(),
                conversietabel.converteerNaarLo3(BrpRedenOpschortingBijhoudingCode.MINISTERIEEL_BESLUIT));
        assertEquals(Lo3RedenOpschortingBijhoudingCodeEnum.FOUT.asElement(),
                conversietabel.converteerNaarLo3(BrpRedenOpschortingBijhoudingCode.FOUT));
        assertEquals(Lo3RedenOpschortingBijhoudingCodeEnum.ONBEKEND.asElement(),
                conversietabel.converteerNaarLo3(BrpRedenOpschortingBijhoudingCode.ONBEKEND));
        assertEquals(Lo3RedenOpschortingBijhoudingCodeEnum.OVERLIJDEN.asElement(),
                conversietabel.converteerNaarLo3(BrpRedenOpschortingBijhoudingCode.OVERLIJDEN));
        assertNull(conversietabel.converteerNaarLo3(null));
    }

    @InsertBefore("/sql/data/dbunitConversieTabel.xml")
    @Test
    public void testConverteerNaarNietBestaandeBrp() {
        assertNull(conversietabel.converteerNaarBrp(Lo3RedenOpschortingBijhoudingCodeEnum.RNI.asElement()));
        assertNull(conversietabel.converteerNaarBrp(Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE.asElement()));
    }
}
