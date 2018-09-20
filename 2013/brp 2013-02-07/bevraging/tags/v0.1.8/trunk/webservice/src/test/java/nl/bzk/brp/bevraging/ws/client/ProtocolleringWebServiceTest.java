/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.client;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.domein.ber.Bericht;
import nl.bzk.brp.bevraging.domein.ber.LeveringCommunicatie;
import nl.bzk.brp.bevraging.domein.ber.Richting;
import nl.bzk.brp.bevraging.domein.lev.Levering;
import nl.bzk.brp.bevraging.domein.lev.LeveringPersoon;
import nl.bzk.brp.bevraging.domein.lev.LeveringSoort;
import nl.bzk.brp.bevraging.domein.repository.BerichtRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringCommunicatieRepository;
import nl.bzk.brp.bevraging.domein.repository.LeveringPersoonRepository;
import nl.bzk.brp.bevraging.ws.service.OpvragenPersoonPortType;
import nl.bzk.brp.bevraging.ws.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Testen tegen live webservices, gedeployed in een embedded Jetty Server.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ProtocolleringWebServiceTest extends AbstractWebServiceTest {

    @Inject
    private OpvragenPersoonPortType        opvragenPersoon;

    @Inject
    private LeveringCommunicatieRepository leveringCommunicatieRepository;

    @Inject
    private LeveringPersoonRepository      leveringPersoonRepository;

    @Inject
    private BerichtRepository              berichtRepository;

    /**
     * Integratie test van protocollering.
     */
    @Test
    public void testProtocollering() {
        final long testBSN = 123456789L;
        final long testAbonnement = 1L;

        opvragenPersoon.opvragenPersoon(createPersoonVraag(123456789L, 200L, testAbonnement, 1L));

        List<Bericht> berichten = berichtRepository.findAll();

        List<LeveringPersoon> leveringPersonen = leveringPersoonRepository.findAll();
        List<LeveringCommunicatie> leveringCommunicaties = leveringCommunicatieRepository.findAll();

        assertEquals(1, leveringPersonen.size());
        assertTrue(leveringCommunicaties.size() == 1);
        // Inkomende en uitgaande
        assertTrue(berichten.size() == 2);

        Bericht ingaandBericht = null;
        Bericht uitgaandBericht = null;
        for (Bericht bericht : berichten) {
            if (Richting.INGAAND == bericht.getRichting()) {
                ingaandBericht = bericht;
            } else {
                uitgaandBericht = bericht;
            }
        }

        assertNotNull(ingaandBericht);
        assertNotNull(uitgaandBericht);

        LeveringPersoon leveringPersoon = leveringPersonen.get(0);
        LeveringCommunicatie leveringCommunicatie = leveringCommunicaties.get(0);
        Levering levering = leveringPersoon.getLevering();

        assertEquals(Long.valueOf(testBSN), leveringPersoon.getPersoon().getBurgerservicenummer());

        assertEquals(LeveringSoort.BRP_BEVRAGING, levering.getSoort());
        assertEquals(Long.valueOf(testAbonnement), levering.getAbonnement().getId());

        assertEquals(Long.valueOf(levering.getId()), leveringCommunicatie.getLevering());
        assertEquals(uitgaandBericht.getId(), leveringCommunicatie.getBericht());

        assertEquals(ingaandBericht.getId(), leveringPersoon.getLevering().getGebaseerdOp());

    }

    /**
     * Creeer een nieuwe {@link nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonVraag} instantie, geinitialiseerd
     * met de meegegeven argumenten.
     *
     * @return de nieuwe {@linnk OpvragenPersoonVraag}.
     *
     * @throws javax.xml.datatype.DatatypeConfigurationException
     */
    private nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonVraag createPersoonVraag(final Long bsn,
            final Long id, final Long abonnement, final Long afzender)
    {
        nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonVraag vraag =
            new nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonVraag();
        vraag.setBsn(String.valueOf(bsn));
        vraag.setId(id);
        vraag.setAbonnementId(abonnement);
        vraag.setAfzenderId(afzender);
        vraag.setTijdstipVerzonden(TestUtil.toXMLGregorianCalendar(new Date()));
        return vraag;
    }

}
