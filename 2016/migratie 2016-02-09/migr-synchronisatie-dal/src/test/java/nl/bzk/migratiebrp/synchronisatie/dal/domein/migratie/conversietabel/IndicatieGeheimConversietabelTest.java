/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.IndicatieGeheimConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import org.junit.Test;

public class IndicatieGeheimConversietabelTest {

    @Test
    public void testNaarBrp() {
        final IndicatieGeheimConversietabel tabel = new IndicatieGeheimConversietabel();
        final BrpBoolean falseBrpBoolean = new BrpBoolean(false, null);
        final BrpBoolean trueBrpBoolean = new BrpBoolean(true, null);

        assertNull(tabel.converteerNaarBrp(null));
        assertEquals(falseBrpBoolean, tabel.converteerNaarBrp(Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement()));
        assertEquals(trueBrpBoolean, tabel.converteerNaarBrp(Lo3IndicatieGeheimCodeEnum.NIET_AAN_KERKEN.asElement()));
        assertEquals(
            trueBrpBoolean,
            tabel.converteerNaarBrp(Lo3IndicatieGeheimCodeEnum.NIET_AAN_KERKEN_EN_NIET_AAN_VRIJE_DERDEN.asElement()));
        assertEquals(trueBrpBoolean, tabel.converteerNaarBrp(Lo3IndicatieGeheimCodeEnum.NIET_AAN_VRIJE_DERDEN.asElement()));
        assertEquals(trueBrpBoolean, tabel.converteerNaarBrp(Lo3IndicatieGeheimCodeEnum.NIET_TER_UITVOERING_VAN_VOORSCHRIFT.asElement()));
        assertEquals(
            trueBrpBoolean,
            tabel.converteerNaarBrp(Lo3IndicatieGeheimCodeEnum.NIET_TER_UITVOERING_VAN_VOORSCHRIFT_EN_NIET_AAN_KERKEN.asElement()));
        assertEquals(
            trueBrpBoolean,
            tabel.converteerNaarBrp(Lo3IndicatieGeheimCodeEnum.NIET_TER_UITVOERING_VAN_VOORSCHRIFT_EN_NIET_AAN_VRIJE_DERDEN.asElement()));
        assertEquals(
            trueBrpBoolean,
            tabel.converteerNaarBrp(Lo3IndicatieGeheimCodeEnum.NIET_TER_UITVOERING_VAN_VOORSCHRIFT_EN_NIET_AAN_VRIJE_DERDEN_EN_NIET_AAN_KERKEN.asElement()));
    }

    @Test
    public void testNaarLo3() {
        final IndicatieGeheimConversietabel tabel = new IndicatieGeheimConversietabel();
        assertEquals(Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement(), tabel.converteerNaarLo3(null));
        assertEquals(Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement(), tabel.converteerNaarLo3(new BrpBoolean(false, null)));
        assertEquals(
            Lo3IndicatieGeheimCodeEnum.NIET_TER_UITVOERING_VAN_VOORSCHRIFT_EN_NIET_AAN_VRIJE_DERDEN_EN_NIET_AAN_KERKEN.asElement(),
            tabel.converteerNaarLo3(new BrpBoolean(true, null)));
    }

}
