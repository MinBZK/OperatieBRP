/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.stuf;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.google.common.collect.Lists;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtStufTransformatieResultaat;
import nl.bzk.brp.domain.berichtmodel.BerichtStufVertaling;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.StufAntwoordBericht;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;

/**
 * Unit test voor {@link StufBerichtCallbackImpl}.
 */
public class StufBerichtCallbackImplTest {

    private final StufBerichtCallbackImpl callback = new StufBerichtCallbackImpl();

    @Test
    public void verwerkBericht() throws Exception {
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
            .metStuurgegevens()
                .metReferentienummer("refnr")
                .metTijdstipVerzending(ZonedDateTime.of(2010, 1, 1, 1, 1, 1, 1, ZoneId.of("GMT")))
                .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                .metOntvangendePartij(TestPartijBuilder.maakBuilder().metCode("000456").build())
            .eindeStuurgegevens()
                .metResultaat(BerichtVerwerkingsResultaat.builder()
                        .metHoogsteMeldingsniveau("Fout")
                        .metVerwerking(VerwerkingsResultaat.FOUTIEF)
                    .build()
                )
        .build();
        //@formatter:on
        final BerichtStufTransformatieResultaat transformatieResultaat = new BerichtStufTransformatieResultaat(
                Lists.newArrayList(new BerichtStufVertaling("stuf", "1")));
        final StufAntwoordBericht antwoordBericht = new StufAntwoordBericht(basisBerichtGegevens, transformatieResultaat );
        callback.verwerkBericht(antwoordBericht);
        XMLUnit.setIgnoreWhitespace(true);
        final Diff
                diff =
                XMLUnit.compareXML(new String(Files.readAllBytes(Paths.get("target/test-classes/expected_resultaat.xml"))), callback.getBerichtResultaat());
        assertThat(diff.identical(), is(true));
    }

//    @Test(expected = MaakAntwoordBerichtException.class)
//    public void gooitStapExceptieBijXmlExceptie() throws Exception {
//      //  TestUtil.setFinalStatic(StufBerichtCallbackImpl.class.getDeclaredField("WRITER"), new ExceptionBrpBerichtWriter());
//
//        callback.verwerkBericht(null);
//    }
}
