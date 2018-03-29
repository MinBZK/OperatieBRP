/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.bericht;

import java.util.Arrays;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import org.junit.Test;

/**
 */
public class BerichtMeldingTest extends BerichtTestUtil {

    @Test
    public void testMeldingen() throws Exception {
        final Melding melding1 = new Melding(Regel.R1316);
        final Melding melding2 = new Melding(Regel.R1340);
        melding1.setReferentieID("123");
        melding2.setReferentieID("456");
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metMeldingen(Arrays.asList(
                     melding1,
                     melding2
                ))
            .build();
        //@formatter:on
        final VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(basisBerichtGegevens, null, null);
        final String output = geefOutput(writer -> {BerichtMeldingWriter.write(bericht.getBasisBerichtGegevens().getMeldingen(), writer);});

        assertGelijk("meldingen.xml", output);
    }


}
