/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc501;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.NotificatieBericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.factory.NotificatieBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractJbpmTest;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class Uc501VrijBerichtNaarGbaIT extends AbstractJbpmTest {
    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String VERZENDER = "069901";
    private static final String ONTVANGER = "059901";
    private static final String VRIJ_BERICHT_INHOUD = "Vrij bericht inhoud bericht";

    public Uc501VrijBerichtNaarGbaIT() {
        super("/uc501-VrijBericht-BRP/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @After
    public void endProces() {
        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void correcteVerwerkingVrijBericht() throws BerichtInhoudException {
        startProcess(readFile("brp/VerwerkVrijBerichtIT.xml"));
        controleerBerichten(0, 1, 0);
        final Vb01Bericht vb01Bericht = getBericht(Vb01Bericht.class);
        // controle vb01Bericht
        Assert.assertEquals("De verzender klopt niet", VERZENDER, vb01Bericht.getBronPartijCode());
        Assert.assertEquals("De ontvanger klopt niet", ONTVANGER, vb01Bericht.getDoelPartijCode());
        Assert.assertEquals("De inhoud klopt niet", VRIJ_BERICHT_INHOUD, vb01Bericht.getHeaderWaarde(Lo3HeaderVeld.BERICHT));
    }

    @Test
    public void verzenderIsNietCorrect() throws BerichtInhoudException {
        startProcess(readFile("brp/VerwerkVrijBerichtOngeldigeVerzenderIT.xml"));
        controleerBerichten(0, 0, 1);
        getBericht(VrijBerichtVerzoekBericht.class);
    }

    @Test
    public void NietBestaandeOntvangerIsNietCorrect() throws BerichtInhoudException {
        startProcess(readFile("brp/VerwerkVrijBerichtOngeldigeOntvangerIT.xml"));
        controleerBerichten(0, 0, 1);
        getBericht(VrijBerichtVerzoekBericht.class);
    }


    @Test
    public void NietBestaandeVerzenderIsNietCorrect() throws BerichtInhoudException {
        startProcess(readFile("brp/VerwerkVrijBerichtOngeldigeVerzenderIT.xml"));
        controleerBerichten(0, 0, 1);
        getBericht(VrijBerichtVerzoekBericht.class);
    }

    @Test
    public void ontvangerIsNietCorrect() throws BerichtInhoudException {
        startProcess(readFile("brp/VerwerkVrijBerichtOngeldigeOntvangerIT.xml"));
        controleerBerichten(0, 0, 1);
        getBericht(VrijBerichtVerzoekBericht.class);
    }

    private NotificatieBericht readFile(final String filename) {
        try {
            return NotificatieBerichtFactory.SINGLETON.getBericht(IOUtils.toString(this.getClass().getResourceAsStream(filename), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
