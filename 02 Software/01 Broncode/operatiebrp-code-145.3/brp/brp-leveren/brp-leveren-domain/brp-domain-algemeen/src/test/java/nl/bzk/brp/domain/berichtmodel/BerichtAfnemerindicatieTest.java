/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * BerichtAfnemerindicatieTest.
 */
public class BerichtAfnemerindicatieTest {

    @Test
    public void testBouwBerichtAfnemerindicatie() {

        final BerichtAfnemerindicatie.Builder builder = BerichtAfnemerindicatie.builder();
        final ZonedDateTime tijd = ZonedDateTime.of(1920, 1, 3, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        final String bsn = "123456";
        builder.metBsn(bsn);
        builder.metTijdstipRegistratie(tijd);
        final String partijCode = "1";
        builder.metPartijCode(partijCode);
        final BerichtAfnemerindicatie berichtAfnemerindicatie = builder.build();
        Assert.assertEquals(bsn, berichtAfnemerindicatie.getBsn());
        Assert.assertEquals(tijd, berichtAfnemerindicatie.getTijdstipRegistratie());
        Assert.assertEquals(partijCode, berichtAfnemerindicatie.getPartijCode());

        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder().build();
        final OnderhoudAfnemerindicatieAntwoordBericht onderhoudAfnemerindicatieAntwoordBericht =
                new OnderhoudAfnemerindicatieAntwoordBericht(basisBerichtGegevens, berichtAfnemerindicatie);
        Assert.assertEquals(berichtAfnemerindicatie, onderhoudAfnemerindicatieAntwoordBericht.getBerichtAfnemerindicatie());
        Assert.assertEquals(basisBerichtGegevens, onderhoudAfnemerindicatieAntwoordBericht.getBasisBerichtGegevens());
    }
}
