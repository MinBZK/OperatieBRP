/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.UUID;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.service.bevraging.algemeen.BevragingCallback;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.xml.sax.SAXException;

public class BevragingCallbackImplTest {

    @Before
    public void voorTest() {
        XMLUnit.setIgnoreWhitespace(true);
    }


    @Test
    public void testSchrijfLeverberichtNaarXml1() throws IOException, SAXException {
        final BevragingCallbackImpl callback = new BevragingCallbackImpl();
        final BevragingVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        verzoek.setSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON);
        final BevragingResultaat resultaat = new BevragingResultaat();
        resultaat.setBrpPartij(TestPartijBuilder.maakBuilder().metCode("123456").metNaam("testpartij").build());
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                    .metReferentienummer(UUID.randomUUID().toString())
                    .metCrossReferentienummer("refnr")
                    .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                    .metZendendePartij(resultaat.getBrpPartij())
                    .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                .eindeStuurgegevens()
                .metMeldingen(Collections.emptyList())
                .metResultaat(
                        BerichtVerwerkingsResultaat.builder()
                                .metVerwerking(VerwerkingsResultaat.FOUTIEF)
                                .metHoogsteMeldingsniveau(SoortMelding.GEEN.getNaam())
                                .build()
                ).build();
        //@formatter:on
        final VerwerkPersoonBericht verwerkPersoonBericht = new VerwerkPersoonBericht(basisBerichtGegevens, null, Collections.emptyList()) ;
        resultaat.setBericht(verwerkPersoonBericht);
        callback.verwerkResultaat(verzoek, resultaat);

        final String actual = geefActual(callback);
        final String expected = IOUtils.toString(BevragingCallbackImplTest.class.getResourceAsStream("/callbacktest.xml"), Charset.defaultCharset());
        Assert.assertTrue(XMLUnit.compareXML(expected, actual).identical());
    }


    @Test
    public void testSchrijfLeverbericht_LeverberichtInresultaatIsNull() throws IOException, SAXException {
        final BevragingCallbackImpl callback = new BevragingCallbackImpl();
        final BevragingVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        verzoek.getStuurgegevens().setReferentieNummer("refnr");
        verzoek.setSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON);
        final BevragingResultaat resultaat = new BevragingResultaat();
        resultaat.setBrpPartij(TestPartijBuilder.maakBuilder().metCode("123456").metNaam("testpartij").build());
        resultaat.setBericht(null);
        callback.verwerkResultaat(verzoek, resultaat);

        final String actual = geefActual(callback);
        final String expected = IOUtils.toString(BevragingCallbackImplTest.class.getResourceAsStream("/callbacktest.xml"), Charset.defaultCharset());
        Assert.assertTrue(XMLUnit.compareXML(expected, actual).identical());
    }


    private String geefActual(final BevragingCallback callback) {
       return ((String) ReflectionTestUtils.getField(callback, "xml"))
                .replaceAll("<brp:referentienummer>.*?</brp:referentienummer>", "<brp:referentienummer>*</brp:referentienummer>")
                .replaceAll("<brp:tijdstipVerzending>.*?</brp:tijdstipVerzending>", "<brp:tijdstipVerzending>*</brp:tijdstipVerzending>");

    }
}