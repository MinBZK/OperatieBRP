/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.delivery.algemeen.writer.BerichtTestUtil;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtAfnemerindicatie;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.OnderhoudAfnemerindicatieAntwoordBericht;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * Unit test voor {@link RegistreerAfnemerindicatieCallbackImpl}.
 */
public class RegistreerAfnemerindicatieBerichtWriterTest extends BerichtTestUtil {

    @Test(expected = UnsupportedOperationException.class)
    public void exceptieBijVerkeerdeSoortDienst() {
        final RegistreerAfnemerindicatieCallbackImpl callback = new RegistreerAfnemerindicatieCallbackImpl();
        callback.verwerkResultaat(SoortDienst.ATTENDERING, null);
    }

    @Test
    public void testPlaatsAfnemerindicatiePlaatBericht() throws Exception {
        //@formatter:off
        final ZonedDateTime time = ZonedDateTime.of(2016, 10, 5, 8, 42, 0, 0, ZoneId.of("CET"));
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                    .metReferentienummer("referentienummer")
                    .metCrossReferentienummer("crossReferentienummer")
                    .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                    .metTijdstipVerzending(time)
                .eindeStuurgegevens()
                .metMeldingen(Collections.singletonList(new Melding(Regel.R1321)))
                .metResultaat(BerichtVerwerkingsResultaat.builder()
                    .metVerwerking(VerwerkingsResultaat.GESLAAGD)
                    .metHoogsteMeldingsniveau("Waarschuwing")
                    .build()
                ).build();

        final BerichtAfnemerindicatie berichtAfnemerindicatie = BerichtAfnemerindicatie.builder()
                    .metBsn("123456789")
                    .metPartijCode("12345")
                    .metTijdstipRegistratie(time)
                    .build();
        //@formatter:on
        final OnderhoudAfnemerindicatieAntwoordBericht bericht = new OnderhoudAfnemerindicatieAntwoordBericht(basisBerichtGegevens, berichtAfnemerindicatie);
        final RegistreerAfnemerindicatieCallbackImpl callback = new RegistreerAfnemerindicatieCallbackImpl();
        callback.verwerkResultaat(SoortDienst.PLAATSING_AFNEMERINDICATIE, bericht);
        final String maakBericht = indent(callback.getResultaat());
        IOUtils.write(maakBericht, new FileOutputStream("target/response_plaatsen_afnemerindicatie.xml"), StandardCharsets.UTF_8);
        assertGelijk("response_plaatsen_afnemerindicatie.xml", maakBericht);
    }

    @Test
    public void testVerwijderAfnemerindicatieBericht() throws Exception {
        //@formatter:off
        final ZonedDateTime time = ZonedDateTime.of(2016, 10, 5, 8, 42, 0, 0, ZoneId.of("CET"));
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                    .metReferentienummer("referentienummer")
                    .metCrossReferentienummer("crossReferentienummer")
                    .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                    .metTijdstipVerzending(time)
                .eindeStuurgegevens()
                .metMeldingen(Collections.singletonList(new Melding(Regel.R1321)))
                .metResultaat(BerichtVerwerkingsResultaat.builder()
                    .metVerwerking(VerwerkingsResultaat.GESLAAGD)
                    .metHoogsteMeldingsniveau("Waarschuwing")
                    .build()
                ).build();
        final BerichtAfnemerindicatie berichtAfnemerindicatie = BerichtAfnemerindicatie.builder()
                    .metBsn("123456789")
                    .metPartijCode("12345")
                    .metTijdstipRegistratie(time)
                    .build();
        //@formatter:on
        final OnderhoudAfnemerindicatieAntwoordBericht bericht = new OnderhoudAfnemerindicatieAntwoordBericht(basisBerichtGegevens, berichtAfnemerindicatie);
        final RegistreerAfnemerindicatieCallbackImpl callback = new RegistreerAfnemerindicatieCallbackImpl();
        callback.verwerkResultaat(SoortDienst.VERWIJDERING_AFNEMERINDICATIE, bericht);
        final String maakBericht = indent(callback.getResultaat());
        IOUtils.write(maakBericht, new FileOutputStream("target/response_verwijderen_afnemerindicatie.xml"), StandardCharsets.UTF_8);
        assertGelijk("response_verwijderen_afnemerindicatie.xml", maakBericht);
    }

}
