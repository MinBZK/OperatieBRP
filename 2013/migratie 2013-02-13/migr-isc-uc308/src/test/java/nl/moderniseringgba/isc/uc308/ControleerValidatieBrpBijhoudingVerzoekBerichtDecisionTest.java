/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc308;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningNotarieelVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.IscGemeenten;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningNotarieelVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningVerzoekBericht;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/uc308-test-beans.xml", "classpath*:usecase-beans.xml" })
public class ControleerValidatieBrpBijhoudingVerzoekBerichtDecisionTest {

    private static final String BRP_GEMEENTE = "1904";
    private static final String LO3_GEMEENTE = "1905";
    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Inject
    private ControleerValidatieBrpBijhoudingVerzoekBerichtDecision controleerValidatieBrpBijhoudingVerzoekBerichtDecision;

    /**
     * ErkenningVerzoek
     */

    @Test
    public void testOkAntwoordErkenningVerzoek() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_erkenningVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        Assert.assertTrue(bericht instanceof ErkenningVerzoekBericht);

        final ErkenningVerzoekBericht erkenningVerzoekBericht = (ErkenningVerzoekBericht) bericht;
        Assert.assertNotNull(erkenningVerzoekBericht.getBrpPersoonslijst());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningVerzoekBericht);

        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNull(result);
    }

    @Test
    public void testFoutAntwoordGeenAktenummerErkenningVerzoek() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_erkenningVerzoekBericht_fout_geen_aktenummer.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        Assert.assertTrue(bericht instanceof ErkenningVerzoekBericht);

        final ErkenningVerzoekBericht erkenningVerzoekBericht = (ErkenningVerzoekBericht) bericht;

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningVerzoekBericht);

        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertEquals(UC308Constants.VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT, result);
    }

    @Test
    public void testFoutAntwoordGeenIngangsdaumErkenningVerzoek() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_erkenningVerzoekBericht_fout_geen_ingangsdatum.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        Assert.assertTrue(bericht instanceof ErkenningVerzoekBericht);

        final ErkenningVerzoekBericht erkenningVerzoekBericht = (ErkenningVerzoekBericht) bericht;

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningVerzoekBericht);

        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertEquals(UC308Constants.VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT, result);
    }

    @Test
    public void testFoutAntwoordGeenBrpGemeenteErkenningVerzoek() {

        final ErkenningVerzoekType type = new ErkenningVerzoekType();
        final IscGemeenten iscGemeenten = new IscGemeenten();
        iscGemeenten.setLo3Gemeente(LO3_GEMEENTE);
        type.setIscGemeenten(iscGemeenten);
        final ErkenningVerzoekBericht erkenningVerzoekBericht = new ErkenningVerzoekBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningVerzoekBericht);

        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertEquals(UC308Constants.VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT, result);
    }

    @Test
    public void testFoutAntwoordGeenLo3GemeenteErkenningVerzoek() {

        final ErkenningVerzoekType type = new ErkenningVerzoekType();
        final IscGemeenten iscGemeenten = new IscGemeenten();
        iscGemeenten.setBrpGemeente(BRP_GEMEENTE);
        type.setIscGemeenten(iscGemeenten);
        final ErkenningVerzoekBericht erkenningVerzoekBericht = new ErkenningVerzoekBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningVerzoekBericht);

        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertEquals(UC308Constants.VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT, result);
    }

    @Test
    public void testFoutAntwoordLeegVerzoekTypeErkenningVerzoek() {

        final ErkenningVerzoekType type = new ErkenningVerzoekType();
        final ErkenningVerzoekBericht erkenningVerzoekBericht = new ErkenningVerzoekBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningVerzoekBericht);

        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertEquals(UC308Constants.VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT, result);
    }

    @Test
    public void testFoutAntwoordErkenningVerzoek() {

        final Map<String, Object> parameters = new HashMap<String, Object>();
        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertEquals(UC308Constants.VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT, result);
    }

    /**
     * ErkenningNotarieelVerzoek
     */

    @Test
    public void testOkAntwoordErkenningNotarieelVerzoek() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_erkenningNotarieelVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        Assert.assertTrue(bericht instanceof ErkenningNotarieelVerzoekBericht);

        final ErkenningNotarieelVerzoekBericht erkenningNotarieelVerzoekBericht =
                (ErkenningNotarieelVerzoekBericht) bericht;
        Assert.assertNotNull(erkenningNotarieelVerzoekBericht.getBrpPersoonslijst());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningNotarieelVerzoekBericht);

        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNull(result);
    }

    @Test
    public void testFoutAntwoordGeenAktenummerErkenningNotarieelVerzoek() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_erkenningNotarieelVerzoekBericht_fout_geen_aktenummer.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        Assert.assertTrue(bericht instanceof ErkenningNotarieelVerzoekBericht);

        final ErkenningNotarieelVerzoekBericht erkenningNotarieelVerzoekBericht =
                (ErkenningNotarieelVerzoekBericht) bericht;

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningNotarieelVerzoekBericht);

        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertEquals(UC308Constants.VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT, result);
    }

    @Test
    public void testFoutAntwoordGeenIngangsdaumErkenningNotarieelVerzoek() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_erkenningNotarieelVerzoekBericht_fout_geen_ingangsdatum.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        Assert.assertTrue(bericht instanceof ErkenningNotarieelVerzoekBericht);

        final ErkenningNotarieelVerzoekBericht erkenningNotarieelVerzoekBericht =
                (ErkenningNotarieelVerzoekBericht) bericht;

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningNotarieelVerzoekBericht);

        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertEquals(UC308Constants.VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT, result);
    }

    @Test
    public void testFoutAntwoordGeenBrpGemeenteErkenningNotarieelVerzoek() {

        final ErkenningNotarieelVerzoekType type = new ErkenningNotarieelVerzoekType();
        final IscGemeenten iscGemeenten = new IscGemeenten();
        iscGemeenten.setLo3Gemeente(LO3_GEMEENTE);
        type.setIscGemeenten(iscGemeenten);
        final ErkenningNotarieelVerzoekBericht erkenningNotarieelVerzoekBericht =
                new ErkenningNotarieelVerzoekBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningNotarieelVerzoekBericht);

        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertEquals(UC308Constants.VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT, result);
    }

    @Test
    public void testFoutAntwoordGeenLo3GemeenteErkenningNotarieelVerzoek() {

        final ErkenningNotarieelVerzoekType type = new ErkenningNotarieelVerzoekType();
        final IscGemeenten iscGemeenten = new IscGemeenten();
        iscGemeenten.setBrpGemeente(BRP_GEMEENTE);
        type.setIscGemeenten(iscGemeenten);
        final ErkenningNotarieelVerzoekBericht erkenningNotarieelVerzoekBericht =
                new ErkenningNotarieelVerzoekBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningNotarieelVerzoekBericht);

        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertEquals(UC308Constants.VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT, result);
    }

    @Test
    public void testFoutAntwoordLeegVerzoekTypeErkenningNotarieelVerzoek() {

        final ErkenningNotarieelVerzoekType type = new ErkenningNotarieelVerzoekType();
        final ErkenningNotarieelVerzoekBericht erkenningNotarieelVerzoekBericht =
                new ErkenningNotarieelVerzoekBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningNotarieelVerzoekBericht);

        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertEquals(UC308Constants.VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT, result);
    }

    @Test
    public void testFoutAntwoordErkenningNotarieelVerzoek() {

        final Map<String, Object> parameters = new HashMap<String, Object>();
        final String result = controleerValidatieBrpBijhoudingVerzoekBerichtDecision.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertEquals(UC308Constants.VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT, result);
    }
}
