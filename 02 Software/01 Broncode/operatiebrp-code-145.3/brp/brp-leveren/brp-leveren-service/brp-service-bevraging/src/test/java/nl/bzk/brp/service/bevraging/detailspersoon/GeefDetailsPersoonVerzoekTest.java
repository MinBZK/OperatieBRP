/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import static org.junit.Assert.assertEquals;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.service.algemeen.request.OIN;
import org.junit.Test;

/**
 * Unit test voor {@link GeefDetailsPersoonVerzoek}.
 */
public class GeefDetailsPersoonVerzoekTest {

    @Test
    public void test() {
        GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
        bevragingVerzoek.setSoortDienst(SoortDienst.ATTENDERING);
        bevragingVerzoek.setOin(new OIN("oinOndertekenaar", "oinTransporteur"));
        bevragingVerzoek.setXmlBericht("XML");
        //parameters
        bevragingVerzoek.getParameters().setPeilMomentFormeelResultaat("2014-01-01");
        bevragingVerzoek.getParameters().setPeilMomentMaterieelResultaat("2014-01-02");
        bevragingVerzoek.getParameters().setHistorieVorm(HistorieVorm.GEEN);
        bevragingVerzoek.getParameters().setVerantwoording("verantwoording");
        bevragingVerzoek.getParameters().setDienstIdentificatie("dienstIdentificatie");
        bevragingVerzoek.getParameters().setLeveringsAutorisatieId("levAutId");
        bevragingVerzoek.getParameters().setRolNaam("rol");
        //scoping
        bevragingVerzoek.getScopingElementen().getElementen().add("scopingElem1");
        bevragingVerzoek.getScopingElementen().getElementen().add("scopingElem2");
        //identificatie
        bevragingVerzoek.getIdentificatiecriteria().setAnr("anr");
        bevragingVerzoek.getIdentificatiecriteria().setBsn("bsn");
        bevragingVerzoek.getIdentificatiecriteria().setObjectSleutel("objectsleutel");

        assertEquals(SoortDienst.ATTENDERING, bevragingVerzoek.getSoortDienst());
        assertEquals("oinOndertekenaar", bevragingVerzoek.getOin().getOinWaardeOndertekenaar());
        assertEquals("oinTransporteur", bevragingVerzoek.getOin().getOinWaardeTransporteur());
        assertEquals("XML", bevragingVerzoek.getXmlBericht());

        assertEquals("2014-01-01", bevragingVerzoek.getParameters().getHistorieFilterParameters().getPeilMomentFormeelResultaat());
        assertEquals("2014-01-02", bevragingVerzoek.getParameters().getHistorieFilterParameters().getPeilMomentMaterieelResultaat());
        assertEquals(HistorieVorm.GEEN, bevragingVerzoek.getParameters().getHistorieFilterParameters().getHistorieVorm());
        assertEquals("verantwoording", bevragingVerzoek.getParameters().getHistorieFilterParameters().getVerantwoording());
        assertEquals("dienstIdentificatie", bevragingVerzoek.getParameters().getDienstIdentificatie());
        assertEquals("levAutId", bevragingVerzoek.getParameters().getLeveringsAutorisatieId());
        assertEquals("rol", bevragingVerzoek.getParameters().getRolNaam());
        assertEquals(2, bevragingVerzoek.getScopingElementen().getElementen().size());

        assertEquals("anr", bevragingVerzoek.getIdentificatiecriteria().getAnr());
        assertEquals("bsn", bevragingVerzoek.getIdentificatiecriteria().getBsn());
        assertEquals("objectsleutel", bevragingVerzoek.getIdentificatiecriteria().getObjectSleutel());
    }
}
