/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.bericht;

import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import org.junit.Test;

/**
 */
public class BerichtStuurgegevensTest extends BerichtTestUtil {

    @Test
    public void testCompleet() throws Exception {
        final String referentienummer = "88409eeb-1aa5-43fc-8614-43055123a165";
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                    .metTijdstipVerzending(getVasteDatum())
                    .metReferentienummer(referentienummer)
                    .metCrossReferentienummer(referentienummer)
                    .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                    .metOntvangendePartij(TestPartijBuilder.maakBuilder().metCode("000456").build())
                    .metZendendeSysteem("BRP")
                .eindeStuurgegevens()
            .build();
        //@formatter:on
        final VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(basisBerichtGegevens, null, null);
        final String output = geefOutput(writer -> BerichtStuurgegevensWriter.write(writer, bericht.getBasisBerichtGegevens().getStuurgegevens()));
        assertGelijk("stuurgegevens.xml", output);
    }

    @Test
    public void testOptioneel() throws Exception {
        final String referentienummer = "88409eeb-1aa5-43fc-8614-43055123a165";
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                    .metTijdstipVerzending(getVasteDatum())
                    .metReferentienummer(referentienummer)
                    .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                    .metZendendeSysteem("BRP")
                .eindeStuurgegevens()
            .build();
        //@formatter:on
        final VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(basisBerichtGegevens, null, null);
        final String output = geefOutput(writer -> BerichtStuurgegevensWriter.write(writer, bericht.getBasisBerichtGegevens().getStuurgegevens()));
        assertGelijk("stuurgegevens_partieel.xml", output);
    }
}
