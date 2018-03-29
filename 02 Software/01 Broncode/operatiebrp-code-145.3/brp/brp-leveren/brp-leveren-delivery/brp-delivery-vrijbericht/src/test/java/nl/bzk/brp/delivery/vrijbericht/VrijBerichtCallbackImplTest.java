/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtAntwoordBericht;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;

/**
 * Unit test voor {@link VrijBerichtCallbackImpl}.
 */
public class VrijBerichtCallbackImplTest {

    private final VrijBerichtCallbackImpl callback = new VrijBerichtCallbackImpl();

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
        final VrijBerichtAntwoordBericht antwoordBericht = new VrijBerichtAntwoordBericht(basisBerichtGegevens);
        //@formatter:on
        callback.verwerkBericht(antwoordBericht);

//        final URL resource = getClass().getResource("/expected_resultaat.xml");
        XMLUnit.setIgnoreWhitespace(true);
        final Diff
                diff =
                XMLUnit.compareXML(new String(Files.readAllBytes(Paths.get("target/test-classes/expected_resultaat.xml"))), callback.getBerichtResultaat());

        assertThat(diff.identical(), is(true));
    }

//    @Test(expected = MaakAntwoordBerichtException.class)
//    public void gooitStapExceptieBijXmlExceptie() throws Exception {
//        TestUtil.setFinalStatic(VrijBerichtCallbackImpl.class.getDeclaredField("WRITER"), new ExceptionBrpBerichtWriter());
//
//        callback.verwerkBericht(null);
//    }
}
