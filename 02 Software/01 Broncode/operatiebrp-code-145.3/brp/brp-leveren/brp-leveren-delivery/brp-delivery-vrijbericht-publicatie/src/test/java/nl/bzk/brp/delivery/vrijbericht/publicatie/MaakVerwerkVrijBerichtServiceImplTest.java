/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht.publicatie;

import static org.hamcrest.CoreMatchers.is;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVrijBericht;
import nl.bzk.brp.domain.berichtmodel.VrijBericht;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtParameters;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtVerwerkBericht;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit test voor {@link MaakVerwerkVrijBerichtServiceImpl}.
 */
public class MaakVerwerkVrijBerichtServiceImplTest {

    private final MaakVerwerkVrijBerichtServiceImpl service = new MaakVerwerkVrijBerichtServiceImpl();

    @Test
    public void maakVerwerkVrijBericht() throws Exception {
        final Partij zendendePartij = TestPartijBuilder.maakBuilder().metCode("000123").build();
        final Partij ontvangendePartij = TestPartijBuilder.maakBuilder().metCode("000456").build();
        final VrijBericht vrijBericht = new VrijBericht("blaat", new SoortVrijBericht("blaat"));
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                .metReferentienummer("refnr")
                .metZendendePartij(zendendePartij)
                .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                .metOntvangendePartij(ontvangendePartij)
                .metTijdstipVerzending(ZonedDateTime.of(2010, 1, 1, 1, 1, 1, 1, ZoneId.of("GMT")))
                .eindeStuurgegevens()
                .build();
        //@formatter:off
        final VrijBerichtVerwerkBericht verwerkBericht = new VrijBerichtVerwerkBericht(basisBerichtGegevens,
                new BerichtVrijBericht(vrijBericht), new VrijBerichtParameters(zendendePartij, ontvangendePartij));

        //@formatter:on
        final String bericht = service.maakVerwerkVrijBericht(verwerkBericht);

        XMLUnit.setIgnoreWhitespace(true);
        final Diff
                diff =
                XMLUnit.compareXML(new String(Files.readAllBytes(Paths.get("target/test-classes/expected_verwerk_vrijbericht.xml"))), bericht);

        MatcherAssert.assertThat(diff.identical(), is(true));
    }
//
//    @Test(expected = StapException.class)
//    public void gooitStapExceptieBijXmlExceptie() throws Exception {
//        TestUtil.setFinalStatic(MaakVerwerkVrijBerichtServiceImpl.class.getDeclaredField("WRITER"), new ExceptionBrpBerichtWriter());
//
//        service.maakVerwerkVrijBericht(null);
//    }

}
