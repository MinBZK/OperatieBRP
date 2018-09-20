/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BasisPreconditieTest extends AbstractPreconditieTest {

    protected static final Foutmelding FOUTMELDING = Foutmelding.maakStructuurFout(new Lo3Herkomst(
            Lo3CategorieEnum.CATEGORIE_01, -1, -1), LogSeverity.ERROR, "");

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private Lo3Precondities precondities;

    @Test
    public void testANummer() {
        precondities.controleerAnummer(1868196961L, FOUTMELDING);
        Assert.assertEquals(0, Logging.getLogging().getRegels().size());
    }

    @Test
    public void testBsn() {
        precondities.controleerBsn(422531881L, FOUTMELDING);
        Assert.assertEquals(0, Logging.getLogging().getRegels().size());
    }

    @Test
    public void testDatumOpnemingOk() {
        precondities.controleerDatumNietOnbekend(new Lo3Datum(20120101), FOUTMELDING);
        Assert.assertEquals(0, Logging.getLogging().getRegels().size());
    }

    @Test
    public void testDatumOpnemingGedeeltelijkOnbekend() {
        precondities.controleerDatumNietOnbekend(new Lo3Datum(20120001), FOUTMELDING);
        Assert.assertEquals(1, Logging.getLogging().getRegels().size());
    }

    @Test
    public void testDatumOpnemingVolledigOnbekend() {
        precondities.controleerDatumNietOnbekend(new Lo3Datum(0), FOUTMELDING);
        Assert.assertEquals(1, Logging.getLogging().getRegels().size());

        precondities.controleerDatumNietOnbekend(new Lo3Datum(0), FOUTMELDING, false);
        Assert.assertEquals(2, Logging.getLogging().getRegels().size());
    }

    @Test
    public void testDatumDocumentVolledigOnbekend() {
        precondities.controleerDatumNietOnbekend(new Lo3Datum(0), FOUTMELDING, true);
    }

}
