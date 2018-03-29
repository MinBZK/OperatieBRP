/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import nl.bzk.brp.delivery.algemeen.writer.BerichtTestUtil;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtAfnemerindicatie;
import nl.bzk.brp.domain.berichtmodel.OnderhoudAfnemerindicatieAntwoordBericht;
import org.junit.Test;

/**
 */
public class BerichtAfnemerindicatieTest extends BerichtTestUtil {

    @Test
    public void testWriteBerichtAfnemerindicatie() throws Exception {
        //@formatter:off
        final OnderhoudAfnemerindicatieAntwoordBericht bericht = new OnderhoudAfnemerindicatieAntwoordBericht(
                BasisBerichtGegevens.builder().build(),
                BerichtAfnemerindicatie.builder()
                    .metBsn("123456789")
                    .metPartijCode("99999")
                    .metTijdstipRegistratie(ZonedDateTime.of(2016, 10, 5, 8, 42, 0, 0, ZoneId.of("CET")))
                .build()
        );
        //@formatter:on

        final String output = geefOutput(writer ->
                RegistreerAfnemerindicatieCallbackImpl.write(writer, bericht.getBerichtAfnemerindicatie()));

        assertGelijk("afnemerindicatie.xml", output);
    }
}
