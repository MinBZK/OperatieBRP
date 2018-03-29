/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonVerzoek;
import org.junit.Test;

/**
 * Unit test voor {@link GeefDetailsPersoonVerzoek}.
 */
public class ZoekPersoonVerzoekTest {

    @Test
    public void test() {
        AbstractZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();
        bevragingVerzoek.setSoortDienst(SoortDienst.ATTENDERING);
        bevragingVerzoek.setOin(new OIN("oinOndertekenaar", "oinTransporteur"));
        bevragingVerzoek.setXmlBericht("XML");
        //parameters
        bevragingVerzoek.getParameters().setDienstIdentificatie("dienstIdentificatie");
        bevragingVerzoek.getParameters().setLeveringsAutorisatieId("levAutId");
        bevragingVerzoek.getParameters().setRolNaam("rol");
        bevragingVerzoek.getParameters().setZoekBereik(Zoekbereik.MATERIELE_PERIODE);
        bevragingVerzoek.getParameters().setPeilmomentMaterieel("2014-01-03");
        //zoekcriteria
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakZoekCriteria("Persoon.Adres"));
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakZoekCriteria("Persoon.Geboorte"));

        assertEquals(SoortDienst.ATTENDERING, bevragingVerzoek.getSoortDienst());
        assertEquals("oinOndertekenaar", bevragingVerzoek.getOin().getOinWaardeOndertekenaar());
        assertEquals("oinTransporteur", bevragingVerzoek.getOin().getOinWaardeTransporteur());
        assertEquals("XML", bevragingVerzoek.getXmlBericht());

        assertEquals("dienstIdentificatie", bevragingVerzoek.getParameters().getDienstIdentificatie());
        assertEquals("levAutId", bevragingVerzoek.getParameters().getLeveringsAutorisatieId());
        assertEquals("rol", bevragingVerzoek.getParameters().getRolNaam());

        assertEquals(Zoekbereik.MATERIELE_PERIODE, bevragingVerzoek.getParameters().getZoekBereikParameters().getZoekBereik());
        assertEquals("2014-01-03", bevragingVerzoek.getParameters().getZoekBereikParameters().getPeilmomentMaterieel());

        Iterator<AbstractZoekPersoonVerzoek.ZoekCriteria> iter = new ArrayList<>(bevragingVerzoek.getZoekCriteriaPersoon()).iterator();
        AbstractZoekPersoonVerzoek.ZoekCriteria zoekCrit1 = iter.next();
        AbstractZoekPersoonVerzoek.ZoekCriteria zoekCrit2 = iter.next();

        assertTrue(zoekCrit1.equals(zoekCrit1));
        assertFalse(zoekCrit1.equals(zoekCrit2));
        assertFalse(zoekCrit1.equals(null));
        assertFalse(zoekCrit1.equals(bevragingVerzoek));
        assertThat(zoekCrit1.getElementNaam(), is("Persoon.Geboorte"));
        assertThat(zoekCrit1.getZoekOptie(), is(Zoekoptie.EXACT));
        assertThat(zoekCrit1.getWaarde(), is("waarde"));
    }

    private AbstractZoekPersoonVerzoek.ZoekCriteria maakZoekCriteria(String elementnaam) {
        AbstractZoekPersoonVerzoek.ZoekCriteria zoekCrit = new AbstractZoekPersoonVerzoek.ZoekCriteria();
        zoekCrit.setElementNaam(elementnaam);
        zoekCrit.setWaarde("waarde");
        zoekCrit.setZoekOptie(Zoekoptie.EXACT);
        return zoekCrit;
    }
}
