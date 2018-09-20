/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.insert;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import nl.bzk.brp.testclient.InsertTest;
import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Constanten;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Insert tester.
 */
public class InsertTester {

    /**
     * bericht.
     */
    private static String bericht = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
	    + "   <soap:Header>"
	    + "      <wsse:Security soap:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
	    + "         <wsse:BinarySecurityToken EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\" ValueType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3\" wsu:Id=\"X509-04602948A448EDA83813487554654311\">MIID2TCCAsGgAwIBAgIJALhwCgOsixz1MA0GCSqGSIb3DQEBBQUAMIGMMQwwCgYDVQQKEwNCWksxDDAKBgNVBAsTA0JSUDEkMCIGCSqGSIb3DQEJARYVc3VwcG9ydEBtb2Rlcm5vZGFtLm5sMQswCQYDVQQGEwJOTDEVMBMGA1UECBMMWnVpZC1Ib2xsYW5kMREwDwYDVQQHEwhEZW4gSGFhZzERMA8GA1UEAxMIV2hpdGVib3gwHhcNMTIwNDA1MTMwNTQ5WhcNMTMwNDA1MTMwNTQ5WjCBjDEMMAoGA1UEChMDQlpLMQwwCgYDVQQLEwNCUlAxJDAiBgkqhkiG9w0BCQEWFXN1cHBvcnRAbW9kZXJub2RhbS5ubDELMAkGA1UEBhMCTkwxFTATBgNVBAgTDFp1aWQtSG9sbGFuZDERMA8GA1UEBxMIRGVuIEhhYWcxETAPBgNVBAMTCFdoaXRlYm94MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA+5qWQvCzIsFuh7fBwbOwBWyrrdsG0VlSewWmoCNWmH8r4BSnifR0+i5Fn++PnV/xSCiJtCD8zoriXRFZXmUwT6mzZFeHiR9bQ+bBs+2kNjAf0WZ9s+aRjwxfkYxrT11TVKOzdkZsIdSw8xg0+KEUQUYiTw4ECcG79I0ftWWj7G+ltNbKq1W0dfaGWdwEFPF1sTaMpIlOV42Dye+yMaEqF/UbVOwQGYSOjyUJgdg6KbhBUcSm1noZ9iNvJ3sVMwc7YvaR/xWW85gKD1ZMQ+cZDW7aNkQU3vj+bp1ou3K+pS4d9jTCF5etfietw6N/ujyMk3cxQVmpotlUlAtRG8Ij9wIDAQABozwwOjAMBgNVHRMBAf8EAjAAMAsGA1UdDwQEAwIDuDAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwDQYJKoZIhvcNAQEFBQADggEBANSWTfGapN+cJZRIojaxmbC0UOryMhFPqhvppA19KBYvUci66NwRFx1mvEkbDOmzF6N1lKZfUhWtJmKet6TzOpnYPkadn2+hQTcJ8cCZvxgFSV/0OWcOf+VhYrejpTo5fsQiOouDaJ15D+E8Lg5KQy6pFunTufi0vLnbkH4HgZdt20c4qlZwjMz64nIj4B7o+4kYxam82VGFhR0Wv2MiWMQBOTu9Img20RnhYYJTcdrxPuOWPDEUvBMxQ/Jjqq9tXqR7+B5YFvNO/iztqcy4lA4vxnA+efkNR4QOQvBRvvI8Q8XCLqYXGylS3BB4hqneEnonpYjzijokpq3T9mHfOBM=</wsse:BinarySecurityToken>"
	    + "         <ds:Signature Id=\"SIG-3\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">"
	    + "            <ds:SignedInfo>"
	    + "               <ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\">"
	    + "                  <ec:InclusiveNamespaces PrefixList=\"soap\" xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>"
	    + "               </ds:CanonicalizationMethod>"
	    + "               <ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>"
	    + "               <ds:Reference URI=\"#id-2\">"
	    + "                  <ds:Transforms>"
	    + "                     <ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\">"
	    + "                        <ec:InclusiveNamespaces PrefixList=\"\" xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>"
	    + "                     </ds:Transform>"
	    + "                  </ds:Transforms>"
	    + "                  <ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>"
	    + "                  <ds:DigestValue>7Hz2HIUz1q8lVF+jrbFIw5BFZ6E=</ds:DigestValue>"
	    + "               </ds:Reference>"
	    + "               <ds:Reference URI=\"#TS-1\">"
	    + "                  <ds:Transforms>"
	    + "                     <ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\">"
	    + "                        <ec:InclusiveNamespaces PrefixList=\"wsse soap\" xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>"
	    + "                     </ds:Transform>"
	    + "                  </ds:Transforms>"
	    + "                  <ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>"
	    + "                  <ds:DigestValue>abvrA8fDq4TX+ue8rUgU2cBHqEE=</ds:DigestValue>"
	    + "               </ds:Reference>"
	    + "            </ds:SignedInfo>"
	    + "            <ds:SignatureValue>+KmTUlEXRNG9pW4us41iKRlJguHhBgvBpzDi2oaMS7XuRivk4HCn++lXgjrBFIgL7aY8UZNmI12DMon5vOtlDv1vkU+5z8Skuqiveu27VA3rVUGe+1wNw5PO2xFm8El7ocZ6VEVfAe5hsgBtfMffNQobekp+vhz8ksCyE6+QXu1P6tv9t13tMLwmTEU9KyrE0WmUSoLTGoKZXPq9wxospW7VwbK2DsNAF/bwAY1EU3zvsTCWkDYtuHhJwU1PjqnsnH0aKOdM/C2V7St3lkUa9yzA+W9+0ZoJ7luEyrDjMLSV+q4fFsMw1ihq5qH9us1FDgveU0g8XgUGpLCAr9Uh6w==</ds:SignatureValue>"
	    + "            <ds:KeyInfo Id=\"KI-04602948A448EDA83813487554654312\">" + "               <wsse:SecurityTokenReference wsu:Id=\"STR-04602948A448EDA83813487554654313\">"
	    + "                  <wsse:Reference URI=\"#X509-04602948A448EDA83813487554654311\" ValueType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3\"/>" + "               </wsse:SecurityTokenReference>" + "            </ds:KeyInfo>"
	    + "         </ds:Signature>" + "         <wsu:Timestamp wsu:Id=\"TS-1\">" + "            <wsu:Created>2012-09-27T14:17:45.424Z</wsu:Created>" + "            <wsu:Expires>2012-09-27T14:22:45.424Z</wsu:Expires>" + "         </wsu:Timestamp>" + "      </wsse:Security>"
	    + "   </soap:Header>" + "   <soap:Body wsu:Id=\"id-2\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
	    + "      <brp:bevragen_VraagDetailsPersoon_Antwoord xmlns:brp=\"http://www.bprbzk.nl/BRP/0001\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + "         <brp:stuurgegevens>" + "            <brp:organisatie>Ministerie BZK</brp:organisatie>"
	    + "            <brp:applicatie>BRP</brp:applicatie>" + "            <brp:referentienummer>3550</brp:referentienummer>" + "            <brp:crossReferentienummer>1234</brp:crossReferentienummer>" + "         </brp:stuurgegevens>" + "         <brp:resultaat>"
	    + "            <brp:verwerkingCode>G</brp:verwerkingCode>" + "            <brp:hoogsteMeldingsniveauCode xsi:nil=\"true\"/>" + "         </brp:resultaat>" + "         <brp:antwoord>" + "            <brp:persoon>" + "               <brp:identificatienummers>"
	    + "                  <brp:burgerservicenummer>100000009</brp:burgerservicenummer>" + "                  <brp:administratienummer>1010101025</brp:administratienummer>" + "               </brp:identificatienummers>" + "               <brp:geslachtsaanduiding>"
	    + "                  <brp:code>M</brp:code>" + "               </brp:geslachtsaanduiding>" + "               <brp:samengesteldeNaam>" + "                  <brp:voornamen>Mustafa</brp:voornamen>" + "                  <brp:geslachtsnaam>Tanaka</brp:geslachtsnaam>"
	    + "               </brp:samengesteldeNaam>" + "               <brp:aanschrijving>" + "                  <brp:wijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartnerCode>E</brp:wijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartnerCode>"
	    + "                  <brp:indicatieAlgoritmischAfgeleid>J</brp:indicatieAlgoritmischAfgeleid>" + "                  <brp:voornamen>Mustafa</brp:voornamen>" + "                  <brp:geslachtsnaam>Tanaka</brp:geslachtsnaam>" + "               </brp:aanschrijving>"
	    + "               <brp:geboorte>" + "                  <brp:datum>19811002</brp:datum>" + "                  <brp:gemeenteCode>0519</brp:gemeenteCode>" + "                  <brp:woonplaatsCode>2662</brp:woonplaatsCode>" + "                  <brp:landCode>6030</brp:landCode>"
	    + "               </brp:geboorte>" + "               <brp:bijhoudingsverantwoordelijkheid>" + "                  <brp:verantwoordelijkeCode>C</brp:verantwoordelijkeCode>" + "               </brp:bijhoudingsverantwoordelijkheid>" + "               <brp:bijhoudingsgemeente>"
	    + "                  <brp:code>0344</brp:code>" + "                  <brp:datumInschrijvingInGemeente>20050328</brp:datumInschrijvingInGemeente>" + "                  <brp:indicatieOnverwerktDocumentAanwezig>N</brp:indicatieOnverwerktDocumentAanwezig>"
	    + "               </brp:bijhoudingsgemeente>" + "               <brp:inschrijving>" + "                  <brp:datum>19811002</brp:datum>" + "                  <brp:versienummer>1</brp:versienummer>" + "               </brp:inschrijving>" + "               <brp:afgeleidAdministratief>"
	    + "                  <brp:tijdstipLaatsteWijziging>2012091216080144</brp:tijdstipLaatsteWijziging>" + "               </brp:afgeleidAdministratief>" + "               <brp:betrokkenheden>" + "                  <brp:kind>" + "                     <brp:familierechtelijkeBetrekking>"
	    + "                        <brp:betrokkenheden>" + "                           <brp:ouder>" + "                              <brp:persoon>" + "                                 <brp:geslachtsaanduiding>" + "                                    <brp:code>M</brp:code>"
	    + "                                 </brp:geslachtsaanduiding>" + "                                 <brp:samengesteldeNaam>" + "                                    <brp:voornamen>Adam-Mustafa</brp:voornamen>" + "                                    <brp:voorvoegsel>van</brp:voorvoegsel>"
	    + "                                    <brp:scheidingsteken></brp:scheidingsteken>" + "                                    <brp:geslachtsnaam>Modernodam</brp:geslachtsnaam>" + "                                 </brp:samengesteldeNaam>"
	    + "                                 <brp:afgeleidAdministratief>" + "                                    <brp:tijdstipLaatsteWijziging>2012091216382678</brp:tijdstipLaatsteWijziging>" + "                                 </brp:afgeleidAdministratief>"
	    + "                              </brp:persoon>" + "                              <brp:ouderschap>" + "                                 <brp:datumAanvang>19811002</brp:datumAanvang>" + "                              </brp:ouderschap>" + "                           </brp:ouder>"
	    + "                        </brp:betrokkenheden>" + "                     </brp:familierechtelijkeBetrekking>" + "                  </brp:kind>" + "                  <brp:kind>" + "                     <brp:familierechtelijkeBetrekking>" + "                        <brp:betrokkenheden>"
	    + "                           <brp:ouder>" + "                              <brp:persoon>" + "                                 <brp:geslachtsaanduiding>" + "                                    <brp:code>V</brp:code>" + "                                 </brp:geslachtsaanduiding>"
	    + "                                 <brp:samengesteldeNaam>" + "                                    <brp:voornamen>Eva-Mustafa</brp:voornamen>" + "                                    <brp:voorvoegsel>van</brp:voorvoegsel>"
	    + "                                    <brp:scheidingsteken></brp:scheidingsteken>" + "                                    <brp:geslachtsnaam>Modernodam</brp:geslachtsnaam>" + "                                 </brp:samengesteldeNaam>"
	    + "                                 <brp:afgeleidAdministratief>" + "                                    <brp:tijdstipLaatsteWijziging>2012091216384927</brp:tijdstipLaatsteWijziging>" + "                                 </brp:afgeleidAdministratief>"
	    + "                              </brp:persoon>" + "                              <brp:ouderschap>" + "                                 <brp:datumAanvang>19811002</brp:datumAanvang>" + "                              </brp:ouderschap>" + "                           </brp:ouder>"
	    + "                        </brp:betrokkenheden>" + "                     </brp:familierechtelijkeBetrekking>" + "                  </brp:kind>" + "                  <brp:partner>" + "                     <brp:huwelijk>"
	    + "                        <brp:datumAanvang>20030327</brp:datumAanvang>" + "                        <brp:landAanvangCode>0000</brp:landAanvangCode>" + "                        <brp:omschrijvingLocatieAanvang>Modernodam</brp:omschrijvingLocatieAanvang>"
	    + "                        <brp:betrokkenheden>" + "                           <brp:partner>" + "                              <brp:persoon>" + "                                 <brp:identificatienummers>"
	    + "                                    <brp:burgerservicenummer>100000010</brp:burgerservicenummer>" + "                                    <brp:administratienummer>1010101502</brp:administratienummer>" + "                                 </brp:identificatienummers>"
	    + "                                 <brp:geslachtsaanduiding>" + "                                    <brp:code>V</brp:code>" + "                                 </brp:geslachtsaanduiding>" + "                                 <brp:samengesteldeNaam>"
	    + "                                    <brp:voornamen>Felicia</brp:voornamen>" + "                                    <brp:voorvoegsel>de</brp:voorvoegsel>" + "                                    <brp:geslachtsnaam>McClintock</brp:geslachtsnaam>"
	    + "                                 </brp:samengesteldeNaam>" + "                                 <brp:geboorte>" + "                                    <brp:datum>19820528</brp:datum>" + "                                    <brp:gemeenteCode>0519</brp:gemeenteCode>"
	    + "                                    <brp:woonplaatsCode>2662</brp:woonplaatsCode>" + "                                    <brp:landCode>6030</brp:landCode>" + "                                 </brp:geboorte>" + "                                 <brp:afgeleidAdministratief>"
	    + "                                    <brp:tijdstipLaatsteWijziging>2012091216080144</brp:tijdstipLaatsteWijziging>" + "                                 </brp:afgeleidAdministratief>" + "                              </brp:persoon>" + "                           </brp:partner>"
	    + "                        </brp:betrokkenheden>" + "                     </brp:huwelijk>" + "                  </brp:partner>" + "               </brp:betrokkenheden>" + "               <brp:voornamen>" + "                  <brp:voornaam>"
	    + "                     <brp:volgnummer>1</brp:volgnummer>" + "                     <brp:naam>Mustafa</brp:naam>" + "                  </brp:voornaam>" + "               </brp:voornamen>" + "               <brp:geslachtsnaamcomponenten>"
	    + "                  <brp:geslachtsnaamcomponent>" + "                     <brp:volgnummer>1</brp:volgnummer>" + "                     <brp:naam>Tanaka</brp:naam>" + "                  </brp:geslachtsnaamcomponent>" + "               </brp:geslachtsnaamcomponenten>"
	    + "               <brp:adressen>" + "                  <brp:adres>" + "                     <brp:soortCode>W</brp:soortCode>" + "                     <brp:redenWijzigingCode>P</brp:redenWijzigingCode>"
	    + "                     <brp:aangeverAdreshoudingCode>P</brp:aangeverAdreshoudingCode>" + "                     <brp:datumAanvangAdreshouding>20050328</brp:datumAanvangAdreshouding>" + "                     <brp:gemeenteCode>0344</brp:gemeenteCode>"
	    + "                     <brp:naamOpenbareRuimte>Vredenburg</brp:naamOpenbareRuimte>" + "                     <brp:afgekorteNaamOpenbareRuimte>Vredenburg</brp:afgekorteNaamOpenbareRuimte>" + "                     <brp:huisnummer>3</brp:huisnummer>"
	    + "                     <brp:huisnummertoevoeging>AA</brp:huisnummertoevoeging>" + "                     <brp:postcode>3511BA</brp:postcode>" + "                     <brp:woonplaatsCode>3295</brp:woonplaatsCode>" + "                     <brp:landCode>6030</brp:landCode>"
	    + "                  </brp:adres>" + "               </brp:adressen>" + "               <brp:nationaliteiten>" + "                  <brp:nationaliteit>" + "                     <brp:nationaliteitNaam>0001</brp:nationaliteitNaam>"
	    + "                     <brp:redenVerkrijgingNaam>017</brp:redenVerkrijgingNaam>" + "                  </brp:nationaliteit>" + "               </brp:nationaliteiten>" + "            </brp:persoon>" + "         </brp:antwoord>" + "      </brp:bevragen_VraagDetailsPersoon_Antwoord>"
	    + "   </soap:Body>" + "</soap:Envelope>";

    private static final Logger LOG = LoggerFactory.getLogger(TestClient.class);

    /**
     * Zet data source.
     *
     * @param connectURI connect uRI
     * @return data source
     */
    private static DataSource setupDataSource(final String connectURI) {
	ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI, "brp", "brp");
	ObjectPool connectionPool = new GenericObjectPool(null);
	new PoolableConnectionFactory(connectionFactory, connectionPool, null, "SELECT 42", false, false);
	PoolingDataSource dataSource = new PoolingDataSource(connectionPool);
	return dataSource;
    }

    /**
     * Execute void.
     *
     * @throws ClassNotFoundException class not found exception
     * @throws SQLException sQL exception
     */
    public void execute() throws ClassNotFoundException, SQLException {
	Class.forName("org.postgresql.Driver");
	DataSource dataSource = setupDataSource("jdbc:postgresql://" + InsertTest.getEigenschappen().getDbHostBijhouding() + "/brp");
	runInsertTest(dataSource);
	LOG.info("Done");
    }

    static volatile long speed = 0;
    static volatile long totalcount = 0;

    private static final double INTERVAL = 5.0;

    /**
     * Uitvoeren van de insert test.
     *
     * @param dataSourceBijhouding data source bijhouding
     * @throws SQLException sQL exception
     */
    private void runInsertTest(final DataSource dataSourceBijhouding) throws SQLException {

	Connection conn = null;

	Thread thread = new Thread() {
	    @Override
	    public void run() {
		try {
		    while (!Thread.currentThread().isInterrupted()) {

			LOG.debug("Interts: " + (speed / INTERVAL) + " / sec, berichtgrootte=" + bericht.length() + " bytes, totalcount=" + totalcount);
			speed = 0;
			Thread.sleep((long) (Constanten.DUIZEND * INTERVAL));
		    }
		} catch (Exception e) {
		    Thread.currentThread().interrupt();
		}

	    };
	};

	thread.start();

	try {
	    while (totalcount < Constanten.MILJOEN) {
		conn = dataSourceBijhouding.getConnection();
		new QueryRunner().update(conn, "INSERT INTO ber.ber(verwerking, bijhouding, hoogstemeldingsniveau, tijdstipreg, peilmommaterieel, peilmomformeel, aanschouwer, srt, data, tsontv, tsverzenden, antwoordop, richting, organisatie, applicatie, referentienr, "
			+ "crossreferentienr, indprevalidatie)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?);", 1, 1, 1, null, 1, null, 1, 1, bericht, null, null, null, 1, 1, 1, 1, 1, false);
		conn.commit();
		conn.close();
		conn = null;
		totalcount++;
		speed++;
	    }

	} finally {
	    if (conn != null) {
		DbUtils.close(conn);
	    }
	    if (thread != null) {
		thread.interrupt();
		LOG.debug("Killing thread");
	    }
	}

    }

}
