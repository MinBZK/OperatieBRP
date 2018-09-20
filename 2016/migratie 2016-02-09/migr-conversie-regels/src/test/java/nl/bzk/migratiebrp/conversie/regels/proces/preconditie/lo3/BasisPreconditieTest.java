/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BasisPreconditieTest extends AbstractPreconditieTest {

    protected static final Foutmelding FOUTMELDING = Foutmelding.maakMeldingFout(
        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, -1, -1),
        LogSeverity.ERROR,
        SoortMeldingCode.STRUC_DATUM,
        null);

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private AbstractLo3Precondities precondities;

    @Test
    public void testANummer() {
        precondities.controleerAnummer(Lo3Long.wrap(1868196961L), FOUTMELDING);
        Assert.assertEquals(0, Logging.getLogging().getRegels().size());
    }

    @Test
    public void testBsn() {
        precondities.controleerBsn(Lo3Integer.wrap(422531881), FOUTMELDING);
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
        Assert.assertEquals(1, Logging.getLogging().getRegels().size());
    }

    @Test
    public void testDatumDocumentVolledigOnbekend() {
        precondities.controleerDatumNietOnbekend(new Lo3Datum(0), FOUTMELDING, true);
    }

}
