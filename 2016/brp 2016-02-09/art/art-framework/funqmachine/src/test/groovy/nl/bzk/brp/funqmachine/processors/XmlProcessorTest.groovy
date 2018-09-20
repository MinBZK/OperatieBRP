package nl.bzk.brp.funqmachine.processors
import nl.bzk.brp.funqmachine.jbehave.context.StepResult
import nl.bzk.brp.funqmachine.processors.xml.AssertionMisluktError
import org.junit.Test

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual

class XmlProcessorTest {

    private XmlProcessor xmlProcessor = new XmlProcessor()

    @Test
    void verwijdertLegeAttributen() {
        def xml = '''<xml attr="[]"></xml>'''

        assertXMLEqual('<?xml version="1.0" encoding="UTF-8"?><xml/>', xmlProcessor.verwijderLegeElementen(xml))
    }

    @Test
    void verwijdertLegeAttributenEnParent() {
        def xml = '''<xml><child attr="[-1]">foo</child></xml>'''

        assertXMLEqual('<?xml version="1.0" encoding="UTF-8"?><xml/>', xmlProcessor.verwijderLegeElementen(xml))
    }

    @Test
    void verwijdertLegeElementen() {
        def xml = '''<xml><child><child>[-1]</child></child></xml>'''

        assertXMLEqual('<?xml version="1.0" encoding="UTF-8"?><xml/>', xmlProcessor.verwijderLegeElementen(xml))
    }

    @Test
    void cleanUpDbResult() {
        def xml = new File(getClass().getResource('db-ber-result.xml').toURI()).text

        def result = xmlProcessor.cleanupDbResult(xml)

        assert !result.contains('Payload:')
        assert result.contains('</soap:Envelope></DATA>')
        assert result.contains('<DATA><soap:Envelope ')
    }

    @Test
    void complexVerwijderElementen() {
        def xml = new File(getClass().getResource('verwijder-elementen.xml').toURI()).text

        def result = xmlProcessor.verwijderLegeElementen(xml)

        assert !result.contains('<brp:adressen>')
        assert !result.contains('<brp:afgeleidAdministratief>')
        assert !result.contains('<brp:ontvangendePartij>')
        assert !result.contains('<brp:geslachtsnaamcomponenten>')
        assert result.contains('<brp:soortSynchronisatie> </brp:soortSynchronisatie>')
    }

    @Test
    void xmlVergelijken() {
        StepResult result = new StepResult(StepResult.Soort.DATA)

        result.expected = '''<Results>
    <ResultSet fetchSize="0"/>
</Results>'''

        result.response = XmlProcessor.toXml(null)

        def regex = '//Results'
        xmlProcessor.vergelijk(regex, result.expected, result.response)
    }

    @Test(expected = AssertionMisluktError)
    void complexXmlVergelijken() {
        StepResult result = new StepResult(StepResult.Soort.DATA)

        result.expected = '''<Results>
    <ResultSet fetchSize="0">
        <Row rowNumber="1">
            <ABONNEMENT>5670014</ABONNEMENT>
            <NAAM>Mutaties op personen van wie u de bijhoudingspartij heeft id 347</NAAM>
            <DATA><soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
  <brp:lvg_synVerwerkPersoon xmlns:brp="http://www.bzk.nl/brp/brp0200">
  <brp:stuurgegevens>
    <brp:zendendePartij>199903</brp:zendendePartij>
   <brp:zendendeSysteem>BRP</brp:zendendeSysteem>
    <brp:ontvangendePartij>170201</brp:ontvangendePartij>
   <brp:ontvangendeSysteem>Leveringsysteem</brp:ontvangendeSysteem>
    <brp:referentienummer>*</brp:referentienummer>
    <brp:tijdstipVerzending>*</brp:tijdstipVerzending>
  </brp:stuurgegevens>
  <brp:parameters>
    <brp:soortSynchronisatie>*</brp:soortSynchronisatie>
    <brp:abonnementNaam>*</brp:abonnementNaam>
    <brp:categorieDienst>*</brp:categorieDienst>
  </brp:parameters>
  <brp:meldingen>
    <brp:melding brp:objecttype="Melding" brp:referentieID="1">
      <brp:regelCode>BRLV0028</brp:regelCode>
      <brp:soortNaam>Waarschuwing</brp:soortNaam>
      <brp:melding>De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt.</brp:melding>
    </brp:melding>
  </brp:meldingen>
  <brp:synchronisatie brp:objecttype="AdministratieveHandeling" brp:verwerkingssoort="Toevoeging">
    <brp:code>05008</brp:code>
    <brp:naam>Correctie adres Nederland</brp:naam>
    <brp:categorieCode>C</brp:categorieCode>
    <brp:partijCode>036101</brp:partijCode>
    <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>
    <brp:bijgehoudenPersonen>
      <brp:persoon brp:objecttype="Persoon" brp:verwerkingssoort="Wijziging" brp:communicatieID="*">
        <brp:soortCode>I</brp:soortCode>
        <brp:afgeleidAdministratief brp:verwerkingssoort="Toevoeging">
          <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>
          <brp:actieInhoud>*</brp:actieInhoud>
          <brp:tijdstipLaatsteWijziging>*</brp:tijdstipLaatsteWijziging>

          <brp:indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig>*</brp:indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig>
        </brp:afgeleidAdministratief>
        <brp:afgeleidAdministratief brp:verwerkingssoort="Verval">
          <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>
          <brp:tijdstipVerval>*</brp:tijdstipVerval>
          <brp:actieVerval>*</brp:actieVerval>
          <brp:tijdstipLaatsteWijziging>*</brp:tijdstipLaatsteWijziging>

          <brp:indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig>*</brp:indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig>
        </brp:afgeleidAdministratief>
        <brp:identificatienummers brp:verwerkingssoort="Identificatie">
          <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>
          <brp:datumAanvangGeldigheid>1969-12-27</brp:datumAanvangGeldigheid>
          <brp:burgerservicenummer>330006575</brp:burgerservicenummer>
          <brp:administratienummer>3021612471</brp:administratienummer>
        </brp:identificatienummers>
        <brp:samengesteldeNaam brp:verwerkingssoort="Identificatie">
          <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>
          <brp:datumAanvangGeldigheid>1969-12-27</brp:datumAanvangGeldigheid>
          <brp:indicatieAfgeleid>J</brp:indicatieAfgeleid>
          <brp:indicatieNamenreeks>N</brp:indicatieNamenreeks>
          <brp:voornamen>Eleonora Françoise</brp:voornamen>
          <brp:scheidingsteken>-</brp:scheidingsteken>
          <brp:geslachtsnaamstam>Crooy</brp:geslachtsnaamstam>
        </brp:samengesteldeNaam>
        <brp:geboorte brp:verwerkingssoort="Identificatie">
          <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>
          <brp:datum>1969-12-27</brp:datum>
          <brp:gemeenteCode>0947</brp:gemeenteCode>
          <brp:woonplaatsnaam>Reijmerstok</brp:woonplaatsnaam>
          <brp:landGebiedCode>6030</brp:landGebiedCode>
        </brp:geboorte>
        <brp:geslachtsaanduiding brp:verwerkingssoort="Identificatie">
          <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>
          <brp:datumAanvangGeldigheid>1969-12-27</brp:datumAanvangGeldigheid>
          <brp:code>V</brp:code>
        </brp:geslachtsaanduiding>
        <brp:bijhouding brp:verwerkingssoort="Toevoeging">
          <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>
          <brp:actieInhoud>*</brp:actieInhoud>
          <brp:datumAanvangGeldigheid>*</brp:datumAanvangGeldigheid>
          <brp:partijCode>170201</brp:partijCode>
          <brp:bijhoudingsaardCode>*</brp:bijhoudingsaardCode>
          <brp:nadereBijhoudingsaardCode>*</brp:nadereBijhoudingsaardCode>
          <brp:indicatieOnverwerktDocumentAanwezig>N</brp:indicatieOnverwerktDocumentAanwezig>
        </brp:bijhouding>
        <brp:bijhouding brp:verwerkingssoort="Wijziging">
          <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>
          <brp:datumAanvangGeldigheid>*</brp:datumAanvangGeldigheid>
          <brp:datumEindeGeldigheid>*</brp:datumEindeGeldigheid>
          <brp:actieAanpassingGeldigheid>*</brp:actieAanpassingGeldigheid>
          <brp:partijCode>034401</brp:partijCode>
          <brp:bijhoudingsaardCode>*</brp:bijhoudingsaardCode>
          <brp:nadereBijhoudingsaardCode>*</brp:nadereBijhoudingsaardCode>
          <brp:indicatieOnverwerktDocumentAanwezig>N</brp:indicatieOnverwerktDocumentAanwezig>
        </brp:bijhouding>
        <brp:bijhouding brp:verwerkingssoort="Verval">
          <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>
          <brp:tijdstipVerval>*</brp:tijdstipVerval>
          <brp:actieVerval>*</brp:actieVerval>
          <brp:datumAanvangGeldigheid>*</brp:datumAanvangGeldigheid>
          <brp:partijCode>034401</brp:partijCode>
          <brp:bijhoudingsaardCode>*</brp:bijhoudingsaardCode>
          <brp:nadereBijhoudingsaardCode>*</brp:nadereBijhoudingsaardCode>
          <brp:indicatieOnverwerktDocumentAanwezig>N</brp:indicatieOnverwerktDocumentAanwezig>
        </brp:bijhouding>
        <brp:adressen>
          <brp:adres brp:objecttype="PersoonAdres" brp:verwerkingssoort="Toevoeging">
            <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>
            <brp:actieInhoud>*</brp:actieInhoud>
            <brp:datumAanvangGeldigheid>1992-01-01</brp:datumAanvangGeldigheid>
            <brp:soortCode>W</brp:soortCode>
            <brp:redenWijzigingCode>M</brp:redenWijzigingCode>
            <brp:datumAanvangAdreshouding>1992-01-01</brp:datumAanvangAdreshouding>
            <brp:identificatiecodeAdresseerbaarObject>1234567890123456</brp:identificatiecodeAdresseerbaarObject>
            <brp:identificatiecodeNummeraanduiding>1234017890123400</brp:identificatiecodeNummeraanduiding>
            <brp:gemeenteCode>1702</brp:gemeenteCode>
            <brp:naamOpenbareRuimte>wijk25</brp:naamOpenbareRuimte>
            <brp:afgekorteNaamOpenbareRuimte>straatnaam</brp:afgekorteNaamOpenbareRuimte>
            <brp:huisnummer>25</brp:huisnummer>
            <brp:postcode>4811BW</brp:postcode>
            <brp:woonplaatsnaam>Breda</brp:woonplaatsnaam>
            <brp:landGebiedCode>6030</brp:landGebiedCode>
          </brp:adres>
          <brp:adres brp:objecttype="PersoonAdres" brp:verwerkingssoort="Toevoeging">
            <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>
            <brp:actieInhoud>*</brp:actieInhoud>
            <brp:datumAanvangGeldigheid>1990-12-01</brp:datumAanvangGeldigheid>
            <brp:datumEindeGeldigheid>1992-01-01</brp:datumEindeGeldigheid>
            <brp:soortCode>W</brp:soortCode>
            <brp:redenWijzigingCode>M</brp:redenWijzigingCode>
            <brp:datumAanvangAdreshouding>1990-12-01</brp:datumAanvangAdreshouding>
            <brp:identificatiecodeAdresseerbaarObject>1234017890123450</brp:identificatiecodeAdresseerbaarObject>
            <brp:identificatiecodeNummeraanduiding>1234017890123456</brp:identificatiecodeNummeraanduiding>
            <brp:gemeenteCode>1773</brp:gemeenteCode>
            <brp:naamOpenbareRuimte>wijk16</brp:naamOpenbareRuimte>
            <brp:afgekorteNaamOpenbareRuimte>straatnaam</brp:afgekorteNaamOpenbareRuimte>
            <brp:huisnummer>16</brp:huisnummer>
            <brp:postcode>4811AW</brp:postcode>
            <brp:woonplaatsnaam>Breda</brp:woonplaatsnaam>
            <brp:landGebiedCode>6030</brp:landGebiedCode>
          </brp:adres>
          <brp:adres brp:objecttype="PersoonAdres" brp:verwerkingssoort="Verval">
            <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>
            <brp:tijdstipVerval>*</brp:tijdstipVerval>
            <brp:actieVerval>*</brp:actieVerval>
            <brp:datumAanvangGeldigheid>1990-12-01</brp:datumAanvangGeldigheid>
            <brp:soortCode>W</brp:soortCode>
            <brp:redenWijzigingCode>P</brp:redenWijzigingCode>
            <brp:aangeverAdreshoudingCode>P</brp:aangeverAdreshoudingCode>
            <brp:datumAanvangAdreshouding>1990-12-01</brp:datumAanvangAdreshouding>
            <brp:identificatiecodeAdresseerbaarObject>1234567890123456</brp:identificatiecodeAdresseerbaarObject>
            <brp:gemeenteCode>0344</brp:gemeenteCode>
            <brp:naamOpenbareRuimte>Neude</brp:naamOpenbareRuimte>
            <brp:afgekorteNaamOpenbareRuimte>Neude</brp:afgekorteNaamOpenbareRuimte>
            <brp:huisnummer>13</brp:huisnummer>
            <brp:postcode>3512AE</brp:postcode>
            <brp:woonplaatsnaam>Utrecht</brp:woonplaatsnaam>
            <brp:landGebiedCode>6030</brp:landGebiedCode>
          </brp:adres>
        </brp:adressen>
        <brp:administratieveHandelingen>*</brp:administratieveHandelingen>
      </brp:persoon>
    </brp:bijgehoudenPersonen>
  </brp:synchronisatie>
</brp:lvg_synVerwerkPersoon>
  </soap:Body>
</soap:Envelope></DATA>
        </Row>
    </ResultSet>
</Results>'''

        result.response = XmlProcessor.toXml([])

        def regex = '//Results'
        xmlProcessor.vergelijk(regex, result.expected, result.response)
    }

    @Test
    void "dient kinderen te evalueren voor een xpath expressie in een tekst"() {
        xmlProcessor.berichtHeeftGeenKinderenVoorXPath("/hallo/aarde", '<hallo><aarde></aarde></hallo>')
    }

    @Test(expected = AssertionError)
    void "dient een assertionError te hebben voor een onverwacht kind node"() {
        xmlProcessor.berichtHeeftGeenKinderenVoorXPath("/hallo/aarde", '<hallo><aarde><jijhoorthierniet/></aarde></hallo>')
    }

    @Test(expected = AssertionError)
    void "dient een assertionError te hebben voor een niet gevonden node"() {
        xmlProcessor.berichtHeeftGeenKinderenVoorXPath("/hallo/aare", '<hallo><aarde></aarde></hallo>')
    }

    @Test
    void "dient de content van een node te vinden"() {
        def response = '<brp:hallo xmlns:brp="http://www.bzk.nl/brp/brp0200">' +
                '   <brp:aarde><hier>' +
                '       dus   </hier>' +
                '   </brp:aarde>' +
                '   <brp:planeet>welkom</brp:planeet>' +
                '</brp:hallo>'
        xmlProcessor.berichtAlsPlatteTekstVanafXPath("<brp:hallo><brp:aarde><hier>dus</hier></brp:aarde><brp:planeet>welkom</brp:planeet></brp:hallo>", "/brp:hallo", response)
        xmlProcessor.berichtAlsPlatteTekstVanafXPath("<brp:aarde><hier>dus</hier></brp:aarde>", "/brp:hallo/brp:aarde", response)
    }
    @Test
    void "dient alle nodes te vinden die voldoen"() {
        def response = '<brp:hallo xmlns:brp="http://www.bzk.nl/brp/brp0200">' +
                '   <brp:aarde><hier>' +
                '       dus   </hier>' +
                '   </brp:aarde>' +
                '   <brp:aarde>welkom</brp:aarde>' +
                '</brp:hallo>'
        xmlProcessor.berichtAlsPlatteTekstVanafXPath("<brp:aarde><hier>dus</hier></brp:aarde><brp:aarde>welkom</brp:aarde>", "/brp:hallo/brp:aarde", response)
    }

    @Test
    void "dient het aantal groepen te vinden in een bericht"() {
        def response = '<brp:hallo xmlns:brp="http://www.bzk.nl/brp/brp0200">' +
                '   <brp:aarde><hier>' +
                '       dus   </hier>' +
                '   </brp:aarde>' +
                '   <brp:aarde>welkom</brp:aarde>' +
                '</brp:hallo>'
        xmlProcessor.heeftAantalVoorkomensVanEenGroep(2, "aarde", response)
        xmlProcessor.heeftAantalVoorkomensVanEenGroep(0, "nietAanwezig", response)
    }
}
