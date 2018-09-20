/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import nl.bzk.brp.utils.XmlUtils;
import nl.bzk.brp.web.bijhouding.VerwerkingsResultaat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


/** Testen tegen live webservices, gedeployed in een embedded Jetty Server. */
@RunWith(SpringJUnit4ClassRunner.class)
public class BijhoudingServiceIntegrationTest extends AbstractIntegrationTest {	

    @Test
    public void testVerhuizing() throws Exception {
    	String bestandsNaam = "migratie_Verhuizing_Bijhouding_v0001_MAX.xml";
        zetActieVoorBericht("verhuizing");
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
//        System.out.println("----" + xml);
        Document document = XmlUtils.bouwDocument(xml);
        
        Assert.assertTrue("migratie_Verhuizing_BijhoudingResponse root node bestaat niet", XmlUtils.isNodeAanwezig("//migratie_Verhuizing_BijhoudingResponse", document));
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig("//resultaat", document));        
        Assert.assertEquals (VerwerkingsResultaat.GOED.getCode(),XmlUtils.getNodeWaarde("//resultaat/verwerkingCode",document));
        
        Document orgAanvraag = XmlUtils.bouwDocumentVanBestand(getInputStream(bestandsNaam));
//	    System.out.println("----" + XmlUtils.toXmlString(orgAanvraag));
        String bsn = XmlUtils.getNodeWaarde("//persoon/identificatienummers/burgerservicenummer",orgAanvraag);
        String anr = XmlUtils.getNodeWaarde("//persoon/identificatienummers/administratienummer",orgAanvraag);
        List<PersoonAdres> adressen = persoonAdresLevelA(bsn);
        Assert.assertTrue("Kan geen persoon vinden op levelA met bsn " + bsn, ((adressen!= null) && !adressen.isEmpty()));
        Assert.assertEquals("Blokhut", adressen.get(0).adreseerbaarObject);
        Assert.assertEquals("1066DG", adressen.get(0).postcode);

        // deze persoon had nog geen historie in het adressen bestand, Dus bij het verhuizen, is er slechts 1 hist. record.
        adressen = persoonAdresLevelC(bsn);
//        System.out.println("adressen: " + adressen +", size="+adressen.size());
        Assert.assertTrue("Kan geen persoon vinden op levelC met bsn " + bsn, adressen.size()>0);
        Assert.assertEquals("Blokhut", adressen.get(0).adreseerbaarObject);
        Assert.assertEquals("1066DG", adressen.get(0).postcode);
        
    }

    @Test
    public void testVerhuizing2() throws Exception {
    	String bestandsNaam = "migratie_Verhuizing_Bijhouding_v0002_MAX.xml";
        zetActieVoorBericht("verhuizing");
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
//        System.out.println("----" + xml);
        Document document = XmlUtils.bouwDocument(xml);
        printAlsResponseHeeftFoutCode(document);
        
        Assert.assertTrue("migratie_Verhuizing_BijhoudingResponse root node bestaat niet", XmlUtils.isNodeAanwezig("//migratie_Verhuizing_BijhoudingResponse", document));        
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig("//resultaat", document));        
        Assert.assertEquals (VerwerkingsResultaat.GOED.getCode(),XmlUtils.getNodeWaarde("//resultaat/verwerkingCode",document));
      }

    @Test
    public void testVerhuizinVanafOnbekendAdres() throws Exception {
    	System.out.println("----testVerhuizinVanafOnbekendAdres migratie_Verhuizing_Bijhouding_vanaf_onbekend_adres.xml");
    	String bestandsNaam = "migratie_Verhuizing_Bijhouding_vanaf_onbekend_adres.xml";
        zetActieVoorBericht("verhuizing");
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        System.out.println("----" + xml);
        Document document = XmlUtils.bouwDocument(xml);
        printAlsResponseHeeftFoutCode(document);
        
        Assert.assertTrue("migratie_Verhuizing_BijhoudingResponse root node bestaat niet", XmlUtils.isNodeAanwezig("//migratie_Verhuizing_BijhoudingResponse", document));        
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig("//resultaat", document));        
        Assert.assertEquals (VerwerkingsResultaat.GOED.getCode(),XmlUtils.getNodeWaarde("//resultaat/verwerkingCode",document));

        Document orgAanvraag = XmlUtils.bouwDocumentVanBestand(getInputStream(bestandsNaam));
//	    System.out.println("----" + XmlUtils.toXmlString(orgAanvraag));
        String bsn = XmlUtils.getNodeWaarde("//persoon/identificatienummers/burgerservicenummer",orgAanvraag);
        String anr = XmlUtils.getNodeWaarde("//persoon/identificatienummers/administratienummer",orgAanvraag);
        List<PersoonAdres> adressen = persoonAdresLevelA(bsn);
        Assert.assertTrue("Kan geen persoon vinden op levelA met bsn " + bsn, ((adressen!= null) && !adressen.isEmpty()));
        Assert.assertEquals("Blokhut15", adressen.get(0).adreseerbaarObject);
        Assert.assertEquals("1066dg", adressen.get(0).postcode); // totdat Postcodevalidatie aan wordt gezet (dan foutmelding)

      }
    
    
// TODO: xml aanpassen xsd validatie
//    @Test
//    public void testVerhuizing3() throws Exception {
//    	System.out.println("------------ testVerhuizing3() loading migratie_Verhuizing_Bijhouding_v0002.xml");
//    	String bestandsNaam = "migratie_Verhuizing_Bijhouding_v0002.xml";
//        zetActieVoorBericht("verhuizing");
//        Node antwoord = verzendBerichtNaarService(bestandsNaam);
//        Assert.assertNotNull(antwoord);
//        String xml = XmlUtils.toXmlString(antwoord);
//        System.out.println("----" + xml);
//        Document document = XmlUtils.bouwDocument(xml);
//        
//        Assert.assertTrue("migratie_Verhuizing_BijhoudingResponse root node bestaat niet", XmlUtils.isNodeAanwezig("//migratie_Verhuizing_BijhoudingResponse", document));
//        Assert.assertTrue("resultaat bestaat niet", XmlUtils.isNodeAanwezig("//resultaat", document));        
//        Assert.assertEquals (VerwerkingsResultaat.GOED.getCode(),XmlUtils.getNodeWaarde("//resultaat/verwerkingCode",document));
//    
//    }
    
    
    @Test
    public void testInschrijvingGeboorte() throws Exception {
        zetActieVoorBericht("inschrijvingGeboorte");
        Node antwoord = verzendBerichtNaarService("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001.xml");
	    Assert.assertNotNull(antwoord);
	    String xml = XmlUtils.toXmlString(antwoord);
	    Document document = XmlUtils.bouwDocument(xml);
//	    System.out.println("----" + xml);

        Assert.assertTrue("InschrijvingAangifteGeboorte root node bestaat niet", XmlUtils.isNodeAanwezig("//afstamming_InschrijvingAangifteGeboorte_BijhoudingResponse", document));
        Assert.assertTrue("resultaat bestaat niet", XmlUtils.isNodeAanwezig("//resultaat", document));        
        Assert.assertEquals (VerwerkingsResultaat.GOED.getCode(),XmlUtils.getNodeWaarde("//resultaat/verwerkingCode",document));
        // test de rest
        
        // now check in the database what has been written.
        Document orgAanvraag = XmlUtils.bouwDocumentVanBestand(getInputStream("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001.xml"));
        String bsn = XmlUtils.getNodeWaarde("//kind/persoon/identificatienummers/burgerservicenummer",orgAanvraag);
        String anr = XmlUtils.getNodeWaarde("//kind/persoon/identificatienummers/administratienummer",orgAanvraag);
        List<PersoonTest> personen = persoonMetBsnNumerBestaatLevelA(bsn);
        Assert.assertTrue("Kan geen persoon vinden", personen.size()>0);
        Assert.assertEquals(bsn, personen.get(0).bsn);
        Assert.assertEquals(anr, personen.get(0).anr);        
    }

// TODO: xml aanpassen xsd validatie
//    @Test
//    public void testInschrijvingGeboorteMin0() throws Exception {
//	    zetActieVoorBericht("inschrijvingGeboorte");
//	    Node antwoord = verzendBerichtNaarService("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MIN0.xml");
//	    Assert.assertNotNull(antwoord);
//	    String xml = XmlUtils.toXmlString(antwoord);
//	    Document document = XmlUtils.bouwDocument(xml);
////	    System.out.println("----" + xml);
//	
//	    Assert.assertTrue("InschrijvingAangifteGeboorte root bestaat niet", XmlUtils.isNodeAanwezig("//afstamming_InschrijvingAangifteGeboorte_BijhoudingResponse_v0001", document));
//	    Assert.assertEquals("afstamming_InschrijvingAangifteGeboorte_BijhoudingResponse_v0001",
//	        antwoord.getFirstChild().getNodeName());
//	    Assert.assertEquals(VerwerkingsResultaat.FOUT.getCode(), XmlUtils.getNodeWaarde("//verwerkingCode",document));
//	    // mist zowieso betrokkenen (+ bsnNr, ...)
//	    Assert.assertEquals(new Integer(1), XmlUtils.getNodeCount("count(//meldingen/melding)",document));
//	    Assert.assertEquals("Veld: betrokkenen", XmlUtils.getNodeWaarde("//meldingen/melding[1]/omschrijving",document));
//	}

// TODO: xml aanpassen xsd validatie
//    @Test
//    public void testInschrijvingGeboorteMin1() throws Exception {
//	    zetActieVoorBericht("inschrijvingGeboorte");
//	    Node antwoord = verzendBerichtNaarService("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MIN1.xml");
//	    Assert.assertNotNull(antwoord);
//	    String xml = XmlUtils.toXmlString(antwoord);
//	    Document document = XmlUtils.bouwDocument(xml);
////	    System.out.println("----" + xml);
//	
//	    Assert.assertTrue("InschrijvingAangifteGeboorte root bestaat niet", XmlUtils.isNodeAanwezig("//afstamming_InschrijvingAangifteGeboorte_BijhoudingResponse", document));
//	    Assert.assertEquals("afstamming_InschrijvingAangifteGeboorte_BijhoudingResponse",
//	        antwoord.getFirstChild().getNodeName());
//	    Assert.assertEquals(VerwerkingsResultaat.FOUT.getCode(), XmlUtils.getNodeWaarde("//verwerkingCode",document));
//	    // mist minimaal 4 hoofdgegevens.
//	    Assert.assertEquals(new Integer(4), XmlUtils.getNodeCount("count(//meldingen/melding)",document));
//	    Assert.assertEquals("Veld: geslachtsaanduiding", XmlUtils.getNodeWaarde("//meldingen/melding[1]/omschrijving",document));
//	    Assert.assertEquals("Veld: geboorte", XmlUtils.getNodeWaarde("//meldingen/melding[2]/omschrijving",document));
//	    Assert.assertEquals("Veld: voornamen", XmlUtils.getNodeWaarde("//meldingen/melding[3]/omschrijving",document));
//	    Assert.assertEquals("Veld: geslacht", XmlUtils.getNodeWaarde("//meldingen/melding[4]/omschrijving",document));
//	}

    
    @Override
    URL getWsdlUrl() throws MalformedURLException {
        return new URL("http://localhost:8181/brp/services/bijhouding?wsdl");
    }

    @Override
    QName getServiceQName() {
        return new QName("http://www.bprbzk.nl/BRP/bijhouding/service", "BijhoudingService");
    }

    @Override
    QName getPortName() {
        return new QName("http://www.bprbzk.nl/BRP/bijhouding/service", "BijhoudingPort");
    }

    private void printAlsResponseHeeftFoutCode(Document document) throws Exception {
    	if (! VerwerkingsResultaat.GOED.getCode().equals(XmlUtils.getNodeWaarde("//resultaat/verwerkingCode",document)) ) {
    		String xml = XmlUtils.toXmlString(document.getDocumentElement());
    		System.out.println("------"+xml);
    	}

    }
    
    
    
    class PersoonTest {
    	Long id;
    	String bsn;
    	String anr;
    	public PersoonTest(Long id, String bsn, String anr) {
    		this.id = id; this.bsn = bsn; this.anr = anr;
    	}
    }

    class PersoonAdres {
    	Long id;
    	Long historischId;
    	String adreseerbaarObject;
    	String huisnr;
    	String postcode;
    	
    	public PersoonAdres(Long id, Long historischId, String adreseerbaarObject, String huisnr, String postcode) {
    		this.id = id; this.historischId = historischId; this.adreseerbaarObject = adreseerbaarObject;
    		this.huisnr = huisnr; this.postcode = postcode;
    	}
    }

	private List<PersoonTest> persoonMetBsnNumerBestaatLevelA(String bsn)  {
		List<PersoonTest> personen = new ArrayList<PersoonTest>();
		
		List<Map<String, Object>> rows = this.simpleJdbcTemplate.queryForList("select id, bsn, anr from kern.pers where bsn = ?",bsn);
		for (Map<String, Object> row : rows) {
			personen.add(new PersoonTest(
					(Long)row.get("id"), 
					(String)row.get("bsn"), 
					(String)row.get("anr")
					));
		}
		return personen;
	}
	
	private List<PersoonAdres> persoonAdresLevelA(String bsn)  {
		String sql = "select pa.id, pa.adresseerbaarobject, pa.huisnr, pa.postcode from kern.persadres pa, kern.pers pe where pa.pers = pe.id and pe.bsn = ?";
		List<PersoonAdres> persoonAdressen = new ArrayList<PersoonAdres>();
		
		List<Map<String, Object>> rows = this.simpleJdbcTemplate.queryForList(sql,bsn);
		for (Map<String, Object> row : rows) {
//			System.out.println("Adres: " +row.get("id")+","+row.get("adresseerbaarobject")+","+row.get("huisnr")+","+row.get("postcode"));
			persoonAdressen.add(new PersoonAdres(
					(Long)row.get("id"), null, 
					(String)row.get("adresseerbaarobject"), 
					(String)row.get("huisnr"), 
					(String)row.get("postcode")
					));
		}
		return persoonAdressen;
	}

	private List<PersoonAdres> persoonAdresLevelC(String bsn)  {
		String sql = "select pa.id, pa.adresseerbaarobject, pa.huisnr, pa.postcode from kern.his_persadres pa, kern.persadres paN, kern.pers pe where paN.pers = pe.id and pa.persadres=paN.id and pe.bsn = ?"; 
		List<PersoonAdres> persoonAdressen = new ArrayList<PersoonAdres>();
		
		List<Map<String, Object>> rows = this.simpleJdbcTemplate.queryForList(sql, bsn);
		for (Map<String, Object> row : rows) {
			persoonAdressen.add(new PersoonAdres(
					(Long)row.get("id"), null, 
					(String)row.get("adresseerbaarobject"), 
					(String)row.get("huisnr"), 
					(String)row.get("postcode")
					));
		}
		return persoonAdressen;
	}

}
