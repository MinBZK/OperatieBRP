/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.algemeen.request.Verzoek;
import org.junit.Assert;
import org.junit.Test;

/**
 * AfnemerindicatieVerzoekTest.
 */
public class AfnemerindicatieVerzoekTest {

    @Test
    public void testAfnemerindicatieVerzoek() {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = new AfnemerindicatieVerzoek();
        final String xmlBericht = "xml";
        final String communicatieId = "1";
        final String leveringsAutorisatieId = "2";
        final String oinOndertekenaar = "4";
        final String oinTransporteur = "5";
        final String dummyAfnemerCode = "6";
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final String referentieNummer = "99";
        final String zendendeSysteem = "BRP";
        final String zendendePartij = "BPR";
        final ZonedDateTime tijdstipVerzending = ZonedDateTime.now();

        final Afnemerindicatie afnemerindicatie = new Afnemerindicatie();
        final String bsn = "1";
        final String partijCode = "2";
        afnemerindicatie.setBsn(bsn);
        afnemerindicatie.setPartijCode(partijCode);
        afnemerindicatie.setDatumAanvangMaterielePeriode("20102020");
        afnemerindicatie.setDatumEindeVolgen("20102010");
        afnemerindicatieVerzoek.setAfnemerindicatie(afnemerindicatie);

        afnemerindicatieVerzoek.setXmlBericht(xmlBericht);
        afnemerindicatieVerzoek.setOin(new OIN(oinOndertekenaar, oinTransporteur));
        afnemerindicatieVerzoek.setSoortDienst(soortDienst);
        afnemerindicatieVerzoek.setDummyAfnemerCode(dummyAfnemerCode);

        final Verzoek.Stuurgegevens stuurgegevens = afnemerindicatieVerzoek.getStuurgegevens();
        final AfnemerindicatieVerzoek.Parameters parameters = afnemerindicatieVerzoek.getParameters();
        Assert.assertNotNull(parameters);
        Assert.assertNotNull(stuurgegevens);

        parameters.setCommunicatieId(communicatieId);
        parameters.setLeveringsAutorisatieId(leveringsAutorisatieId);

        stuurgegevens.setReferentieNummer(referentieNummer);
        stuurgegevens.setZendendSysteem(zendendeSysteem);
        stuurgegevens.setZendendePartijCode(zendendePartij);
        stuurgegevens.setTijdstipVerzending(tijdstipVerzending);

        Assert.assertEquals(xmlBericht, afnemerindicatieVerzoek.getXmlBericht());
        Assert.assertEquals(communicatieId, afnemerindicatieVerzoek.getParameters().getCommunicatieId());
        Assert.assertEquals(leveringsAutorisatieId, afnemerindicatieVerzoek.getParameters().getLeveringsAutorisatieId());

        Assert.assertEquals(referentieNummer, afnemerindicatieVerzoek.getStuurgegevens().getReferentieNummer());
        Assert.assertEquals(zendendeSysteem, afnemerindicatieVerzoek.getStuurgegevens().getZendendSysteem());
        Assert.assertEquals(zendendePartij, afnemerindicatieVerzoek.getStuurgegevens().getZendendePartijCode());
        Assert.assertEquals(tijdstipVerzending, afnemerindicatieVerzoek.getStuurgegevens().getTijdstipVerzending());

        Assert.assertEquals(oinOndertekenaar, afnemerindicatieVerzoek.getOin().getOinWaardeOndertekenaar());
        Assert.assertEquals(oinTransporteur, afnemerindicatieVerzoek.getOin().getOinWaardeTransporteur());
        Assert.assertEquals(dummyAfnemerCode, afnemerindicatieVerzoek.getDummyAfnemerCode());
        Assert.assertEquals(soortDienst, afnemerindicatieVerzoek.getSoortDienst());

        Assert.assertEquals(bsn, afnemerindicatieVerzoek.getAfnemerindicatie().getBsn());
        Assert.assertEquals(partijCode, afnemerindicatieVerzoek.getAfnemerindicatie().getPartijCode());
        //Assert.assertEquals(datumEindeVolgen, afnemerindicatieVerzoek.getAfnemerindicatie().getDatumEindeVolgen());
        //Assert.assertEquals(datumAanvangMaterielePeriode, afnemerindicatieVerzoek.getAfnemerindicatie().getDatumAanvangMaterielePeriode());
    }
}
