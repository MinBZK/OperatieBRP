/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieDocumentEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SignaleringEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.DalConversietabelFactory;
import org.junit.Test;

/**
 * Tests voor de anonymous inner classes van de ConversietabelFactoryImpl.
 */
public class DalConversietabelFactoryTest {

    private static final DalConversietabelFactory FACTORY = new DalConversietabelFactory();

    @Test
    public void testIndicatiePKConversieTabel() {
        final Conversietabel<Lo3IndicatiePKVolledigGeconverteerdCode, BrpBoolean> tabel = FACTORY.createIndicatiePKConversietabel();
        final Lo3IndicatiePKVolledigGeconverteerdCode lo3Waarde = Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement();

        final BrpBoolean falseBrpBoolean = new BrpBoolean(false, null);
        final BrpBoolean trueBrpBoolean = new BrpBoolean(true, null);

        assertEquals(falseBrpBoolean, tabel.converteerNaarBrp(null));
        assertEquals(trueBrpBoolean, tabel.converteerNaarBrp(lo3Waarde));

        assertNull(tabel.converteerNaarLo3(null));
        assertNull(tabel.converteerNaarLo3(falseBrpBoolean));
        assertEquals(lo3Waarde, tabel.converteerNaarLo3(trueBrpBoolean));
    }

    @Test
    public void testSignaleringConversieTabel() {
        final Conversietabel<Lo3Signalering, BrpBoolean> tabel = FACTORY.createSignaleringConversietabel();
        final Lo3Signalering lo3Waarde = Lo3SignaleringEnum.SIGNALERING.asElement();

        assertNull(tabel.converteerNaarBrp(null));
        assertTrue(BrpBoolean.unwrap(tabel.converteerNaarBrp(lo3Waarde)));
        assertTrue(new BrpBoolean(true, null).equals(tabel.converteerNaarBrp(lo3Waarde)));

        assertNull(tabel.converteerNaarLo3(null));
        assertNull(tabel.converteerNaarLo3(new BrpBoolean(false, null)));
        assertEquals(lo3Waarde, tabel.converteerNaarLo3(new BrpBoolean(true, null)));
    }

    @Test
    public void testIndicatieDocumentConversieTabel() {
        final Conversietabel<Lo3IndicatieDocument, BrpBoolean> tabel = FACTORY.createIndicatieDocumentConversietabel();
        final Lo3IndicatieDocument lo3Waarde = Lo3IndicatieDocumentEnum.INDICATIE.asElement();
        final BrpBoolean falseBrpBoolean = new BrpBoolean(false, null);
        final BrpBoolean trueBrpBoolean = new BrpBoolean(true, null);

        assertEquals(falseBrpBoolean, tabel.converteerNaarBrp(null));
        assertEquals(trueBrpBoolean, tabel.converteerNaarBrp(lo3Waarde));

        assertNull(tabel.converteerNaarLo3(null));
        assertNull(tabel.converteerNaarLo3(falseBrpBoolean));
        assertEquals(lo3Waarde, tabel.converteerNaarLo3(trueBrpBoolean));
    }

    @Test
    public void testIndicatieCurateleConversieTabel() {
        final Conversietabel<Lo3IndicatieCurateleregister, BrpBoolean> tabel = FACTORY.createIndicatieCurateleConversietabel();
        final Lo3IndicatieCurateleregister lo3Waarde = Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement();

        assertNull(tabel.converteerNaarBrp(null));
        assertTrue(BrpBoolean.unwrap(tabel.converteerNaarBrp(lo3Waarde)));

        assertNull(tabel.converteerNaarLo3(null));
        assertNull(tabel.converteerNaarLo3(new BrpBoolean(false, null)));
        assertEquals(lo3Waarde, tabel.converteerNaarLo3(new BrpBoolean(true, null)));
    }

}
