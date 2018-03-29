/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.request;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import org.junit.Assert;
import org.junit.Test;

public class VerzoekBasisTest {

    private static final String XML_BERICHT = "XMLBericht";
    private static final String OIN_ONDERTEKENAAR = "OINOndertekenaar";
    private static final String OIN_TRANSPORTEUR = "OINTransporteur";
    private static final SoortDienst SOORT_DIENST = SoortDienst.ATTENDERING;
    private static final String ZENDENDE_PARTIJ = "Zendende Partij";
    private static final String ZENDENDE_SYSTEEM = "Zendende Systeem";
    private static final String REF_NR = "RefNr";
    private static final ZonedDateTime TS_VERZENDING = ZonedDateTime.now();

    @Test
    public void test() {
        Verzoek verzoek = new VerzoekBasis();
        verzoek.setXmlBericht(XML_BERICHT);
        verzoek.setOin(new OIN(OIN_ONDERTEKENAAR, OIN_TRANSPORTEUR));
        verzoek.setSoortDienst(SOORT_DIENST);

        Assert.assertEquals(XML_BERICHT, verzoek.getXmlBericht());
        Assert.assertEquals(OIN_ONDERTEKENAAR, verzoek.getOin().getOinWaardeOndertekenaar());
        Assert.assertEquals(OIN_TRANSPORTEUR, verzoek.getOin().getOinWaardeTransporteur());
        Assert.assertEquals(SOORT_DIENST, verzoek.getSoortDienst());
        Assert.assertNotNull(verzoek.getStuurgegevens());
        Assert.assertNull(verzoek.getStuurgegevens().getZendendePartijCode());
        Assert.assertNull(verzoek.getStuurgegevens().getZendendSysteem());
        Assert.assertNull(verzoek.getStuurgegevens().getReferentieNummer());
        Assert.assertNull(verzoek.getStuurgegevens().getTijdstipVerzending());
    }

    @Test
    public void testVerzoekMetStuurgegevens() {
        Verzoek verzoek = new VerzoekBasis();
        verzoek.setXmlBericht(XML_BERICHT);
        verzoek.setOin(new OIN(OIN_ONDERTEKENAAR, OIN_TRANSPORTEUR));
        verzoek.getStuurgegevens().setZendendePartijCode(ZENDENDE_PARTIJ);
        verzoek.getStuurgegevens().setZendendSysteem(ZENDENDE_SYSTEEM);
        verzoek.getStuurgegevens().setReferentieNummer(REF_NR);
        verzoek.getStuurgegevens().setTijdstipVerzending(TS_VERZENDING);

        Assert.assertEquals(XML_BERICHT, verzoek.getXmlBericht());
        Assert.assertEquals(OIN_ONDERTEKENAAR, verzoek.getOin().getOinWaardeOndertekenaar());
        Assert.assertEquals(OIN_TRANSPORTEUR, verzoek.getOin().getOinWaardeTransporteur());
        Assert.assertNotNull(verzoek.getStuurgegevens());
        Assert.assertEquals(ZENDENDE_PARTIJ, verzoek.getStuurgegevens().getZendendePartijCode());
        Assert.assertEquals(ZENDENDE_SYSTEEM, verzoek.getStuurgegevens().getZendendSysteem());
        Assert.assertEquals(REF_NR, verzoek.getStuurgegevens().getReferentieNummer());
        Assert.assertEquals(TS_VERZENDING, verzoek.getStuurgegevens().getTijdstipVerzending());
    }

    @Test
    public void testVerzoekMetStuurgegevensTsVerzendingIsNull() {
        Verzoek verzoek = new VerzoekBasis();
        verzoek.getStuurgegevens().setTijdstipVerzending(null);

        Assert.assertNull(verzoek.getStuurgegevens().getTijdstipVerzending());
    }
}
