/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc501;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.OngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractUcTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * IntegratieTest voor uc501 vrij bericht.
 */
@ContextConfiguration("classpath:/uc501-test-beans.xml")
public class Uc501VrijBerichtNaarBrpIT extends AbstractUcTest {

    private static final String VERZENDER = "059901";
    private static final String ONTVANGER = "042901";
    private static final String NIET_BESTAANDE_VERZENDER = "959901";
    private static final String NIET_BESTAANDE_ONTVANGER = "942901";
    private static final String LO3_BERICHT = "Vrij bericht";

    public Uc501VrijBerichtNaarBrpIT() {
        super("/uc501-VrijBericht-GBA/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Before
    public void setupPartijRegister() {
        // Partij register
        final List<Partij> partijen = new ArrayList<>();
        partijen.add(new Partij("059901", "0599", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("042901", "0429", intToDate(19900101), Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE)));
        partijen.add(new Partij("051801", "0518", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("199902", "1999", intToDate(19900101), Collections.emptyList()));
        partijen.add(new Partij("071701", "0717", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        setPartijRegister(new PartijRegisterImpl(partijen));
    }

    @After
    public void endProces() {
        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void correcteVerwerkingVrijBericht() throws BerichtInhoudException {
        startProcess(maakVb01Bericht(VERZENDER, ONTVANGER));
        controleerBerichten(0, 0, 1);
        final VrijBerichtVerzoekBericht verzoek = getBericht(VrijBerichtVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De verzender klopt niet", VERZENDER, verzoek.getVerzendendePartij());
        Assert.assertEquals("De ontvanger klopt niet", ONTVANGER, verzoek.getOntvangendePartij());
        Assert.assertEquals("De inhoud klopt niet", LO3_BERICHT, verzoek.getBericht());

        final VrijBerichtAntwoordBericht antwoord = new VrijBerichtAntwoordBericht();
        antwoord.setCorrelationId(verzoek.getMessageId());
        antwoord.setStatus(true);
        signalSync(antwoord);
    }

    @Test
    public void leegVrijBericht() throws BerichtInhoudException {
        Vb01Bericht vb01Bericht = maakVb01Bericht(VERZENDER, ONTVANGER);
        vb01Bericht.setHeader(Lo3HeaderVeld.BERICHT, "");
        vb01Bericht.setHeader(Lo3HeaderVeld.LENGTE_BERICHT, Integer.toString("".length()));
        startProcess(vb01Bericht);
        controleerBerichten(0, 1, 0);
        final Pf03Bericht foutmeldingBericht = getBericht(Pf03Bericht.class);
    }

    @Test
    public void verzenderIsNietCorrect() throws BerichtInhoudException {
        startProcess(maakVb01Bericht(ONTVANGER, VERZENDER));
        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);
    }

    @Test
    public void NietBestaandeOntvangerIsNietCorrect() throws BerichtInhoudException {
        startProcess(maakVb01Bericht(VERZENDER, NIET_BESTAANDE_ONTVANGER));
        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);
    }


    @Test
    public void NietBestaandeVerzenderIsNietCorrect() throws BerichtInhoudException {
        startProcess(maakVb01Bericht(NIET_BESTAANDE_VERZENDER, ONTVANGER));
        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);
    }

    @Test
    public void ontvangerIsNietCorrect() throws BerichtInhoudException {
        startProcess(maakVb01Bericht(VERZENDER, "071701"));
        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);
    }

    @Test
    public void afbrekenBeeindigenVerwerkingVrijBericht() throws BerichtInhoudException {
        startProcess(maakVb01Bericht(VERZENDER, ONTVANGER));
        controleerBerichten(0, 0, 1);
        final VrijBerichtVerzoekBericht verzoek = getBericht(VrijBerichtVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De verzender klopt niet", VERZENDER, verzoek.getVerzendendePartij());
        Assert.assertEquals("De ontvanger klopt niet", ONTVANGER, verzoek.getOntvangendePartij());
        Assert.assertEquals("De inhoud klopt niet", LO3_BERICHT, verzoek.getBericht());

        signalProcess("3c. afbreken");
        signalHumanTask("end");
        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);
    }

    @Test
    public void afbrekenOpnieuwProberenVerwerkingVrijBericht() throws BerichtInhoudException {
        startProcess(maakVb01Bericht(VERZENDER, ONTVANGER));
        controleerBerichten(0, 0, 1);
        final VrijBerichtVerzoekBericht verzoek = getBericht(VrijBerichtVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De verzender klopt niet", VERZENDER, verzoek.getVerzendendePartij());
        Assert.assertEquals("De ontvanger klopt niet", ONTVANGER, verzoek.getOntvangendePartij());
        Assert.assertEquals("De inhoud klopt niet", LO3_BERICHT, verzoek.getBericht());

        signalProcess("3c. afbreken");
        signalHumanTask("restartAtVersturenVrijBerichtNaarBrp");
        controleerBerichten(0, 0, 1);
        final VrijBerichtVerzoekBericht verzoekPoging2 = getBericht(VrijBerichtVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De verzender klopt niet", VERZENDER, verzoekPoging2.getVerzendendePartij());
        Assert.assertEquals("De ontvanger klopt niet", ONTVANGER, verzoekPoging2.getOntvangendePartij());
        Assert.assertEquals("De inhoud klopt niet", LO3_BERICHT, verzoekPoging2.getBericht());

        final VrijBerichtAntwoordBericht antwoordPoging2 = new VrijBerichtAntwoordBericht();
        antwoordPoging2.setCorrelationId(verzoekPoging2.getMessageId());
        antwoordPoging2.setStatus(true);
        signalSync(antwoordPoging2);
    }

    @Test
    public void technischeFoutBeeindigenVerwerkingVrijBericht() throws BerichtInhoudException {
        startProcess(maakVb01Bericht(VERZENDER, ONTVANGER));
        controleerBerichten(0, 0, 1);
        final VrijBerichtVerzoekBericht verzoek = getBericht(VrijBerichtVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De verzender klopt niet", VERZENDER, verzoek.getVerzendendePartij());
        Assert.assertEquals("De ontvanger klopt niet", ONTVANGER, verzoek.getOntvangendePartij());
        Assert.assertEquals("De inhoud klopt niet", LO3_BERICHT, verzoek.getBericht());

        final OngeldigBericht antwoord = new OngeldigBericht("Ongeldig", "Bericht");
        antwoord.setCorrelationId(verzoek.getMessageId());
        signalSync(antwoord);

        signalHumanTask("end");
        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);
    }

    @Test
    public void technischeFoutOpnieuwProberenVerwerkingVrijBericht() throws BerichtInhoudException {
        startProcess(maakVb01Bericht(VERZENDER, ONTVANGER));
        controleerBerichten(0, 0, 1);
        final VrijBerichtVerzoekBericht verzoek = getBericht(VrijBerichtVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De verzender klopt niet", VERZENDER, verzoek.getVerzendendePartij());
        Assert.assertEquals("De ontvanger klopt niet", ONTVANGER, verzoek.getOntvangendePartij());
        Assert.assertEquals("De inhoud klopt niet", LO3_BERICHT, verzoek.getBericht());

        final OngeldigBericht antwoord = new OngeldigBericht("Ongeldig", "Bericht");
        antwoord.setCorrelationId(verzoek.getMessageId());
        signalSync(antwoord);

        signalHumanTask("restartAtVersturenVrijBerichtNaarBrp");
        controleerBerichten(0, 0, 1);
        final VrijBerichtVerzoekBericht verzoekPoging2 = getBericht(VrijBerichtVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De verzender klopt niet", VERZENDER, verzoekPoging2.getVerzendendePartij());
        Assert.assertEquals("De ontvanger klopt niet", ONTVANGER, verzoekPoging2.getOntvangendePartij());
        Assert.assertEquals("De inhoud klopt niet", LO3_BERICHT, verzoekPoging2.getBericht());

        final VrijBerichtAntwoordBericht antwoordPoging2 = new VrijBerichtAntwoordBericht();
        antwoordPoging2.setCorrelationId(verzoekPoging2.getMessageId());
        antwoordPoging2.setStatus(true);
        signalSync(antwoordPoging2);
    }

    @Test
    public void functioneleFoutVerwerkingVrijBericht() throws BerichtInhoudException {
        startProcess(maakVb01Bericht(VERZENDER, ONTVANGER));
        controleerBerichten(0, 0, 1);
        final VrijBerichtVerzoekBericht verzoek = getBericht(VrijBerichtVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De verzender klopt niet", VERZENDER, verzoek.getVerzendendePartij());
        Assert.assertEquals("De ontvanger klopt niet", ONTVANGER, verzoek.getOntvangendePartij());
        Assert.assertEquals("De inhoud klopt niet", LO3_BERICHT, verzoek.getBericht());

        final VrijBerichtAntwoordBericht antwoord = new VrijBerichtAntwoordBericht();
        antwoord.setCorrelationId(verzoek.getMessageId());
        antwoord.setStatus(false);
        signalSync(antwoord);
        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);
    }

    private Vb01Bericht maakVb01Bericht(final String verzenderCode, final String ontvangerCode) throws BerichtInhoudException {
        final Vb01Bericht vb01Bericht = new Vb01Bericht();
        vb01Bericht.setBronPartijCode(verzenderCode);
        vb01Bericht.setDoelPartijCode(ontvangerCode);
        vb01Bericht.setHeader(Lo3HeaderVeld.BERICHT, LO3_BERICHT);
        vb01Bericht.setHeader(Lo3HeaderVeld.LENGTE_BERICHT, Integer.toString(LO3_BERICHT.length()));
        return vb01Bericht;
    }
}
