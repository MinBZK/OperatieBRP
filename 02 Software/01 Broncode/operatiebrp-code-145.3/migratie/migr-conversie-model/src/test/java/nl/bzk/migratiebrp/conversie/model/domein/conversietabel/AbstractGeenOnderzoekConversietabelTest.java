/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel;

import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.junit.Test;

public class AbstractGeenOnderzoekConversietabelTest {
    private TestClass test = new TestClass(Collections.EMPTY_LIST);

    @Test
    public void testBepaalOnderzoekLo3() throws Exception {
        Lo3Onderzoek onderzoek = new Lo3Onderzoek(new Lo3Integer(6210), new Lo3Datum(20100101), null);
        BrpBoolean input = new BrpBoolean(Boolean.FALSE, onderzoek);
        assertNull(test.bepaalOnderzoekLo3(input));
    }

    @Test
    public void testBepaalOnderzoekBrp() throws Exception {
        Lo3Onderzoek onderzoek = new Lo3Onderzoek(new Lo3Integer(6210), new Lo3Datum(20100101), null);
        BrpBoolean input = new BrpBoolean(Boolean.FALSE, onderzoek);
        assertNull(test.bepaalOnderzoekBrp(input));
    }

    @Test
    public void testVoegOnderzoekToeLo3() throws Exception {
        Lo3Onderzoek onderzoek = new Lo3Onderzoek(new Lo3Integer(6210), new Lo3Datum(20100101), null);
        BrpBoolean input = new BrpBoolean(Boolean.FALSE, null);
        BrpBoolean result = (BrpBoolean) test.voegOnderzoekToeLo3(input, onderzoek);
        assertNull(result.getOnderzoek());
    }

    @Test
    public void testVoegOnderzoekToeBrp() throws Exception {
        Lo3Onderzoek onderzoek = new Lo3Onderzoek(new Lo3Integer(6210), new Lo3Datum(20100101), null);
        BrpBoolean input = new BrpBoolean(Boolean.FALSE, null);
        BrpBoolean result = (BrpBoolean) test.voegOnderzoekToeBrp(input, onderzoek);
        assertNull(result.getOnderzoek());
    }

    private class TestClass extends AbstractGeenOnderzoekConversietabel {

        /**
         * Maakt een AbstractConversietabel object.
         * @param conversieLijst de lijst met conversies.
         */
        public TestClass(List conversieLijst) {
            super(conversieLijst);
        }

    }
}
