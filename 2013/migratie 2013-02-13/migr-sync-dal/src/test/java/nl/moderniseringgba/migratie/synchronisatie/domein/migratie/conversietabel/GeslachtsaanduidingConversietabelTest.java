/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.GeslachtsaanduidingConversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;

import org.junit.Before;
import org.junit.Test;

public class GeslachtsaanduidingConversietabelTest {

    private Conversietabel<Lo3Geslachtsaanduiding, BrpGeslachtsaanduidingCode> conversietabel;

    @Before
    public void setup() {
        conversietabel = new GeslachtsaanduidingConversietabel();
    }

    @Test
    public void testConverteerNaarBrp() {
        final BrpGeslachtsaanduidingCode brpResultaatMan =
                conversietabel.converteerNaarBrp(Lo3GeslachtsaanduidingEnum.MAN.asElement());
        final BrpGeslachtsaanduidingCode brpResultaatVrouw =
                conversietabel.converteerNaarBrp(Lo3GeslachtsaanduidingEnum.VROUW.asElement());
        final BrpGeslachtsaanduidingCode brpResultaatOnbekend =
                conversietabel.converteerNaarBrp(Lo3GeslachtsaanduidingEnum.ONBEKEND.asElement());
        assertEquals(BrpGeslachtsaanduidingCode.MAN, brpResultaatMan);
        assertEquals(BrpGeslachtsaanduidingCode.VROUW, brpResultaatVrouw);
        assertEquals(BrpGeslachtsaanduidingCode.ONBEKEND, brpResultaatOnbekend);
        assertNull(conversietabel.converteerNaarBrp(null));
    }

    @Test
    public void testConverteerNaarLo3() {
        final Lo3Geslachtsaanduiding lo3ResultaatMan =
                conversietabel.converteerNaarLo3(BrpGeslachtsaanduidingCode.MAN);
        final Lo3Geslachtsaanduiding lo3ResultaatVrouw =
                conversietabel.converteerNaarLo3(BrpGeslachtsaanduidingCode.VROUW);
        final Lo3Geslachtsaanduiding lo3ResultaatOnbekend =
                conversietabel.converteerNaarLo3(BrpGeslachtsaanduidingCode.ONBEKEND);
        assertEquals(Lo3GeslachtsaanduidingEnum.MAN.asElement(), lo3ResultaatMan);
        assertEquals(Lo3GeslachtsaanduidingEnum.VROUW.asElement(), lo3ResultaatVrouw);
        assertEquals(Lo3GeslachtsaanduidingEnum.ONBEKEND.asElement(), lo3ResultaatOnbekend);
        assertNull(conversietabel.converteerNaarLo3(null));
    }

}
