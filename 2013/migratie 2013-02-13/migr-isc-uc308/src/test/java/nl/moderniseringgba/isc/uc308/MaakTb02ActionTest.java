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
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02AdoptieBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02ErkenningBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02ErkenningNotarieelBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02GerechtelijkeVaststellingVaderschapBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02GeslachtsnaamwijzigingBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02LijkvindingBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02OntkenningVaderschapBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02OntkenningVaderschapDoorMoederBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02OverlijdenBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02TransseksualiteitBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02VernietigingErkenningBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02VoornaamswijzigingBericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.ConverteerNaarLo3AntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/uc308-test-beans.xml", "classpath*:usecase-beans.xml" })
public class MaakTb02ActionTest {

    /**
     * LO3 persoonslijst als teletext string.
     */
    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Inject
    private MaakTb02Action maakTb02Action;

    /**
     * ErkenningVerzoek
     */

    @Test
    public void testAanmakenTb02BerichtVanuitErkenningVerzoekBericht() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_erkenningVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.OK);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final Map<String, Object> result = maakTb02Action.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.TB02_BERICHT));
        Assert.assertEquals(true, result.get(UC308Constants.TB02_BERICHT) instanceof Tb02ErkenningBericht);
        Assert.assertEquals(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst(),
                ((Tb02Bericht) result.get(UC308Constants.TB02_BERICHT)).getLo3Persoonslijst());
    }

    /**
     * ErkenningNotarieelVerzoek
     */

    @Test
    public void testAanmakenTb02BerichtVanuitErkenningNotarieelVerzoekBericht() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_erkenningNotarieelVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.OK);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final Map<String, Object> result = maakTb02Action.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.TB02_BERICHT));
        Assert.assertEquals(true, result.get(UC308Constants.TB02_BERICHT) instanceof Tb02ErkenningNotarieelBericht);
        Assert.assertEquals(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst(),
                ((Tb02Bericht) result.get(UC308Constants.TB02_BERICHT)).getLo3Persoonslijst());
    }

    /**
     * ErkenningNotarieelVerzoek
     */

    @Test
    public void testAanmakenTb02BerichtVanuitVernietigingErkenningVerzoekBericht() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_erkenningVernietigingVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.OK);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final Map<String, Object> result = maakTb02Action.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.TB02_BERICHT));
        Assert.assertEquals(true, result.get(UC308Constants.TB02_BERICHT) instanceof Tb02VernietigingErkenningBericht);
        Assert.assertEquals(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst(),
                ((Tb02Bericht) result.get(UC308Constants.TB02_BERICHT)).getLo3Persoonslijst());
    }

    /**
     * OntkenningVaderschapVerzoek
     */

    @Test
    public void testAanmakenTb02BerichtVanuitOntkenningVaderschapVerzoekBericht() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_ontkenningVaderschapVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.OK);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final Map<String, Object> result = maakTb02Action.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.TB02_BERICHT));
        Assert.assertEquals(true, result.get(UC308Constants.TB02_BERICHT) instanceof Tb02OntkenningVaderschapBericht);
        Assert.assertEquals(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst(),
                ((Tb02Bericht) result.get(UC308Constants.TB02_BERICHT)).getLo3Persoonslijst());
    }

    /**
     * Ontkenning
     */

    @Test
    public void testAanmakenTb02BerichtVanuitOntkenningVaderschapDoorMoederVerzoekBericht() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_ontkenningVaderschapDoorMoederVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.OK);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final Map<String, Object> result = maakTb02Action.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.TB02_BERICHT));
        Assert.assertEquals(true,
                result.get(UC308Constants.TB02_BERICHT) instanceof Tb02OntkenningVaderschapDoorMoederBericht);
        Assert.assertEquals(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst(),
                ((Tb02Bericht) result.get(UC308Constants.TB02_BERICHT)).getLo3Persoonslijst());
    }

    @Test
    public void testAanmakenTb02BerichtVanuitGeslachtsnaamwijzigingVerzoekBericht() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_geslachtsnaamWijzigingVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.OK);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final Map<String, Object> result = maakTb02Action.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.TB02_BERICHT));
        Assert.assertEquals(true,
                result.get(UC308Constants.TB02_BERICHT) instanceof Tb02GeslachtsnaamwijzigingBericht);
        Assert.assertEquals(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst(),
                ((Tb02Bericht) result.get(UC308Constants.TB02_BERICHT)).getLo3Persoonslijst());
    }

    @Test
    public void testAanmakenTb02BerichtVanuitVoornaamswijzigingVerzoekBericht() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_voornaamsWijzigingVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.OK);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final Map<String, Object> result = maakTb02Action.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.TB02_BERICHT));
        Assert.assertEquals(true, result.get(UC308Constants.TB02_BERICHT) instanceof Tb02VoornaamswijzigingBericht);
        Assert.assertEquals(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst(),
                ((Tb02Bericht) result.get(UC308Constants.TB02_BERICHT)).getLo3Persoonslijst());
    }

    @Test
    public void testAanmakenTb02BerichtVanuitTransseksualiteitVerzoekBericht() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_transseksualiteitVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.OK);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final Map<String, Object> result = maakTb02Action.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.TB02_BERICHT));
        Assert.assertEquals(true, result.get(UC308Constants.TB02_BERICHT) instanceof Tb02TransseksualiteitBericht);
        Assert.assertEquals(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst(),
                ((Tb02Bericht) result.get(UC308Constants.TB02_BERICHT)).getLo3Persoonslijst());
    }

    @Test
    public void testAanmakenTb02BerichtVanuitGerechtelijkeVaststellingVaderschapVerzoekBericht() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_gerechtelijkeVaststellingVaderschapVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.OK);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final Map<String, Object> result = maakTb02Action.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.TB02_BERICHT));
        Assert.assertEquals(true,
                result.get(UC308Constants.TB02_BERICHT) instanceof Tb02GerechtelijkeVaststellingVaderschapBericht);
        Assert.assertEquals(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst(),
                ((Tb02Bericht) result.get(UC308Constants.TB02_BERICHT)).getLo3Persoonslijst());
    }

    // TODO: Aanzetten op het moment dat het bericht is gerealiseerd.
    @Test
    @Ignore
    public void testAanmakenTb02BerichtVanuitAdoptieVerzoekBericht() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class.getResourceAsStream("uc308_adoptieVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.OK);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final Map<String, Object> result = maakTb02Action.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.TB02_BERICHT));
        Assert.assertEquals(true, result.get(UC308Constants.TB02_BERICHT) instanceof Tb02AdoptieBericht);
        Assert.assertEquals(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst(),
                ((Tb02Bericht) result.get(UC308Constants.TB02_BERICHT)).getLo3Persoonslijst());
    }

    @Test
    public void testAanmakenTb02BerichtVanuitOverlijdenVerzoekBericht() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_overlijdenVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.OK);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final Map<String, Object> result = maakTb02Action.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.TB02_BERICHT));
        Assert.assertEquals(true, result.get(UC308Constants.TB02_BERICHT) instanceof Tb02OverlijdenBericht);
        Assert.assertEquals(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst(),
                ((Tb02Bericht) result.get(UC308Constants.TB02_BERICHT)).getLo3Persoonslijst());
    }

    // TODO: Aanzetten op het moment dat het bericht is gerealiseerd.
    @Test
    @Ignore
    public void testAanmakenTb02BerichtVanuitLijkvindingVerzoekBericht() throws IOException {

        final String origineel =
                IOUtils.toString(MaakTb02ActionTest.class
                        .getResourceAsStream("uc308_lijkvindingVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.OK);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final Map<String, Object> result = maakTb02Action.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.TB02_BERICHT));
        Assert.assertEquals(true, result.get(UC308Constants.TB02_BERICHT) instanceof Tb02LijkvindingBericht);
        Assert.assertEquals(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst(),
                ((Tb02Bericht) result.get(UC308Constants.TB02_BERICHT)).getLo3Persoonslijst());
    }

}
