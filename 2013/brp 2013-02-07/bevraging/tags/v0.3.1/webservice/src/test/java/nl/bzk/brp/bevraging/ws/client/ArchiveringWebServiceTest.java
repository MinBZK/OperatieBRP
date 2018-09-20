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

import nl.bzk.brp.bevraging.domein.repository.BerichtRepository;
import nl.bzk.brp.bevraging.ws.service.OpvragenPersoonPortType;
import nl.bzk.brp.bevraging.ws.util.TestUtil;
import nl.bzk.brp.domein.ber.Bericht;
import nl.bzk.brp.domein.ber.Richting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Testen tegen live webservices, gedeployed in een embedded Jetty Server.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ArchiveringWebServiceTest extends AbstractWebServiceTest {

    @Inject
    private OpvragenPersoonPortType opvragenPersoon;

    @Inject
    private BerichtRepository       berichtRepository;

    /**
     * Archivering integratie test.
     */
    @Test
    public void testArchivering() {
        opvragenPersoon.opvragenPersoon(createPersoonVraag(123456789L, 200L, 1L, 1L));

        List<Bericht> berichten = berichtRepository.findAll();
        assertEquals(2, berichten.size());

        Bericht ingaandBericht = null;
        Bericht uitgaandBericht = null;

        for (Bericht bericht : berichten) {
            if (Richting.INGAAND == bericht.getRichting()) {
                ingaandBericht = bericht;
            } else {
                uitgaandBericht = bericht;
            }
        }

        assertNotNull(uitgaandBericht);
        assertNotNull(ingaandBericht);

        assertTrue(ingaandBericht.getData().contains("123456789"));
        assertTrue(uitgaandBericht.getData().contains("123456789"));
        assertEquals(uitgaandBericht.getAntwoordOp(), ingaandBericht);
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
