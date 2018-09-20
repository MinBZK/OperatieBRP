/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit.InsertBefore;
import org.junit.Before;
import org.junit.Test;

public class RedenOpschortingConversietabelTest extends AbstractDatabaseTest {

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode> conversietabel;

    @Before
    public void setup() {
        conversietabel = conversietabelFactory.createRedenOpschortingBijhoudingConversietabel();
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    @Test
    public void testConverteerNaarBrp() {
        assertEquals(
            BrpNadereBijhoudingsaardCode.MINISTERIEEL_BESLUIT,
            conversietabel.converteerNaarBrp(Lo3RedenOpschortingBijhoudingCodeEnum.MINISTERIEEL_BESLUIT.asElement()));
        assertEquals(
            BrpNadereBijhoudingsaardCode.FOUT,
            conversietabel.converteerNaarBrp(Lo3RedenOpschortingBijhoudingCodeEnum.FOUT.asElement()));
        assertEquals(
            BrpNadereBijhoudingsaardCode.ONBEKEND,
            conversietabel.converteerNaarBrp(Lo3RedenOpschortingBijhoudingCodeEnum.ONBEKEND.asElement()));
        assertEquals(
            BrpNadereBijhoudingsaardCode.OVERLEDEN,
            conversietabel.converteerNaarBrp(Lo3RedenOpschortingBijhoudingCodeEnum.OVERLIJDEN.asElement()));
        assertNull(conversietabel.converteerNaarBrp(null));
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    @Test
    public void testConverteerNaarLo3() {
        assertEquals(
            Lo3RedenOpschortingBijhoudingCodeEnum.MINISTERIEEL_BESLUIT.asElement(),
            conversietabel.converteerNaarLo3(BrpNadereBijhoudingsaardCode.MINISTERIEEL_BESLUIT));
        assertEquals(
            Lo3RedenOpschortingBijhoudingCodeEnum.FOUT.asElement(),
            conversietabel.converteerNaarLo3(BrpNadereBijhoudingsaardCode.FOUT));
        assertEquals(
            Lo3RedenOpschortingBijhoudingCodeEnum.ONBEKEND.asElement(),
            conversietabel.converteerNaarLo3(BrpNadereBijhoudingsaardCode.ONBEKEND));
        assertEquals(
            Lo3RedenOpschortingBijhoudingCodeEnum.OVERLIJDEN.asElement(),
            conversietabel.converteerNaarLo3(BrpNadereBijhoudingsaardCode.OVERLEDEN));
        assertEquals(
            Lo3RedenOpschortingBijhoudingCodeEnum.RNI.asElement(),
            conversietabel.converteerNaarLo3(BrpNadereBijhoudingsaardCode.RECHTSTREEKS_NIET_INGEZETENE));
        assertEquals(
            Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE.asElement(),
            conversietabel.converteerNaarLo3(BrpNadereBijhoudingsaardCode.EMIGRATIE));
        assertNull(conversietabel.converteerNaarLo3(null));
    }
}
