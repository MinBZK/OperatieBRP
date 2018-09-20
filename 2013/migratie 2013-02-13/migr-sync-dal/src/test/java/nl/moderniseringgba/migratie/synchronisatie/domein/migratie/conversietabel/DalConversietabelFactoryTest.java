/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingBezitBuitenlandsReisdocumentEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieDocumentEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3SignaleringEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBezitBuitenlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.DalConversietabelFactory;

import org.junit.Test;

/**
 * Tests voor de anonymous inner classes van de ConversietabelFactoryImpl.
 */
public class DalConversietabelFactoryTest {

    private static final DalConversietabelFactory FACTORY = new DalConversietabelFactory();

    @Test
    public void testIndicatiePKConversieTabel() {
        final Conversietabel<Lo3IndicatiePKVolledigGeconverteerdCode, Boolean> tabel =
                FACTORY.createIndicatiePKConversietabel();
        final Lo3IndicatiePKVolledigGeconverteerdCode lo3Waarde =
                Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement();

        assertFalse(tabel.converteerNaarBrp(null));
        assertTrue(tabel.converteerNaarBrp(lo3Waarde));

        assertNull(tabel.converteerNaarLo3(null));
        assertNull(tabel.converteerNaarLo3(false));
        assertEquals(lo3Waarde, tabel.converteerNaarLo3(true));
    }

    @Test
    public void testSignaleringConversieTabel() {
        final Conversietabel<Lo3Signalering, Boolean> tabel = FACTORY.createSignaleringConversietabel();
        final Lo3Signalering lo3Waarde = Lo3SignaleringEnum.SIGNALERING.asElement();

        assertNull(tabel.converteerNaarBrp(null));
        assertTrue(tabel.converteerNaarBrp(lo3Waarde));

        assertNull(tabel.converteerNaarLo3(null));
        assertNull(tabel.converteerNaarLo3(false));
        assertEquals(lo3Waarde, tabel.converteerNaarLo3(true));
    }

    @Test
    public void testAanduidingBezitBuitenlandsReisdocumentConversieTabel() {
        final Conversietabel<Lo3AanduidingBezitBuitenlandsReisdocument, Boolean> tabel =
                FACTORY.createAanduidingBezitBuitenlandsReisdocumentConversietabel();
        final Lo3AanduidingBezitBuitenlandsReisdocument lo3Waarde =
                Lo3AanduidingBezitBuitenlandsReisdocumentEnum.AANDUIDING.asElement();

        assertNull(tabel.converteerNaarBrp(null));
        assertTrue(tabel.converteerNaarBrp(lo3Waarde));

        assertNull(tabel.converteerNaarLo3(null));
        assertNull(tabel.converteerNaarLo3(false));
        assertEquals(lo3Waarde, tabel.converteerNaarLo3(true));
    }

    @Test
    public void testIndicatieDocumentConversieTabel() {
        final Conversietabel<Lo3IndicatieDocument, Boolean> tabel = FACTORY.createIndicatieDocumentConversietabel();
        final Lo3IndicatieDocument lo3Waarde = Lo3IndicatieDocumentEnum.INDICATIE.asElement();

        assertFalse(tabel.converteerNaarBrp(null));
        assertTrue(tabel.converteerNaarBrp(lo3Waarde));

        assertNull(tabel.converteerNaarLo3(null));
        assertNull(tabel.converteerNaarLo3(false));
        assertEquals(lo3Waarde, tabel.converteerNaarLo3(true));
    }

    @Test
    public void testIndicatieCurateleConversieTabel() {
        final Conversietabel<Lo3IndicatieCurateleregister, Boolean> tabel =
                FACTORY.createIndicatieCurateleConversietabel();
        final Lo3IndicatieCurateleregister lo3Waarde =
                Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement();

        assertNull(tabel.converteerNaarBrp(null));
        assertTrue(tabel.converteerNaarBrp(lo3Waarde));

        assertNull(tabel.converteerNaarLo3(null));
        assertNull(tabel.converteerNaarLo3(false));
        assertEquals(lo3Waarde, tabel.converteerNaarLo3(true));
    }

}
