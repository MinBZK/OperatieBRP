package nl.bzk.brp.funqmachine.ontvanger

import nl.bzk.brp.funqmachine.processors.xml.XmlUtils
import org.apache.commons.io.IOUtils
import org.junit.Test

/**
 *
 */
class LeveringHttpHandlerTest {
    def handler = new LeveringHttpHandler()

        def xmlVerwerkPersoon = """
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <brp:lvg_synVerwerkPersoon xmlns:brp="http://www.bzk.nl/brp/brp0200">
  <brp:stuurgegevens>
    <brp:zendendePartij>199903</brp:zendendePartij>
    <brp:ontvangendePartij>017401</brp:ontvangendePartij>
    <brp:referentienummer> </brp:referentienummer>
  </brp:stuurgegevens>
</brp:lvg_synVerwerkPersoon>
  </soap:Body>
</soap:Envelope>
"""

        def xmlNotificatie = """
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <brp:bhg_fiaNotificeerBijhoudingsplan xmlns:brp="http://www.bzk.nl/brp/brp0200">
  <brp:stuurgegevens>
    <brp:zendendePartij>199903</brp:zendendePartij>
    <brp:ontvangendePartij>017401</brp:ontvangendePartij>
    <brp:referentienummer> </brp:referentienummer>
  </brp:stuurgegevens>
</brp:bhg_fiaNotificeerBijhoudingsplan>
  </soap:Body>
</soap:Envelope>
"""

    @Test
    void ontvangtXmlVerwerkPersoonCorrect() {
        handler.doExecute(IOUtils.toInputStream(xmlVerwerkPersoon), null)

        assert handler.leveringen.size() == 1
    }

    @Test
    void behandeldXmlVerwerkPersoonCorrect() {
        handler.doExecute(IOUtils.toInputStream(xmlVerwerkPersoon), null)

        assert handler.leveringen.size() == 1

        String pretty = XmlUtils.prettify(handler.leveringen[0])

        assert pretty.contains('<brp:referentienummer> </brp:referentienummer>')
    }

    @Test
    void ontvangtXmlNotificatieCorrect() {
        handler.doExecute(IOUtils.toInputStream(xmlNotificatie), null)

        assert handler.leveringen.size() == 1
    }

    @Test
    void behandeldXmlNotificatieCorrect() {
        handler.doExecute(IOUtils.toInputStream(xmlNotificatie), null)

        assert handler.leveringen.size() == 1

        String pretty = XmlUtils.prettify(handler.leveringen[0])

        assert pretty.contains('<brp:referentienummer> </brp:referentienummer>')
    }
}
