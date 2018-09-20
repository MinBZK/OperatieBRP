/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import java.util.List;
import java.util.Map;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.soap.SOAPFaultException;
import nl.bzk.brp.testrunner.omgeving.LogischeNaam;
import nl.bzk.brp.testrunner.omgeving.Omgeving;
import nl.bzk.brp.testrunner.component.util.VersieUrlChecker;
import nl.bzk.brp.testrunner.component.util.XmlUtils;
import nl.bzk.brp.testrunner.component.services.soap.ServicesNietBereikbaarException;
import nl.bzk.brp.testrunner.component.services.soap.SoapClient;
import nl.bzk.brp.testrunner.component.services.soap.SoapParameters;
import org.w3c.dom.Node;

/**
 *   bericht: lvg_synGeefSynchronisatiePersoon
 endpoint: '${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie'
 namespace: ''
 handelingen:
 - geefSynchronisatiePersoon
 */
@LogischeNaam(ComponentNamen.SYNCHRONISATIE)
final class SynchronisatieComponentImpl extends AbstractDockerComponentMetCache implements Synchronisatie {


    private static final String NAMESPACE = "http://www.bzk.nl/brp/levering/synchronisatie/service";
    protected SynchronisatieComponentImpl(final Omgeving omgeving) {
        super(omgeving, "synchronisatie");
    }

    @Override
    protected DockerImage geefDockerImage() {
        return new DockerImage("brp/synchronisatie");
    }

    @Override
    protected Map<String, String> geefInterneLinkOpLogischeLinkMap() {
        final Map<String, String> map = super.geefInterneLinkOpLogischeLinkMap();
        map.put("brpdb", ComponentNamen.BRP_DB);
        map.put("archiveringdb", ComponentNamen.BRP_DB);
        map.put("lockingdb", ComponentNamen.BRP_DB);
        map.put("sleutelbos", ComponentNamen.SLEUTELBOS);
        map.put("routeringcentrale", ComponentNamen.ROUTERINGCENTRALE);
        return map;
    }

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar() && VersieUrlChecker.check(this, getLogischeNaam());
    }

    @Override
    protected List<Integer> geefInternePoorten() {
        final List<Integer> internePoorten = super.geefInternePoorten();
        internePoorten.add(Poorten.WEB_POORT_8080);
        return internePoorten;
    }


    public List<String> volumesFrom() {
        final List<String> volumes = super.volumesFrom();
        volumes.add(ComponentNamen.SLEUTELBOS);
        return volumes;
    }

    @Override
    protected Map<String, String> geefOmgevingsVariabelen() {
        final Map<String, String> map = super.geefOmgevingsVariabelen();
        map.put("JAVA_OPTS", "-Xmx1g");
        return map;
    }

    @Override
    public String synchroniseerPersoon() {


        //FIXME tijdelijk even hardcoded, later vervangen door YML
        String request = "<brp:lvg_synGeefSynchronisatiePersoon xmlns:brp=\"http://www.bzk.nl/brp/brp0200\">\n"
            + "    <brp:stuurgegevens brp:communicatieID=\"121331\">\n"
            + "        <brp:zendendePartij>199900</brp:zendendePartij>\n"
            + "        <brp:zendendeSysteem>SYNC</brp:zendendeSysteem>\n"
            + "        <brp:referentienummer>3a04fc7a-aaf4-499d-8109-b0f1804aff51</brp:referentienummer>\n"
            + "        <brp:tijdstipVerzending>2014-11-21T12:08:51.442+01:00</brp:tijdstipVerzending>\n"
            + "    </brp:stuurgegevens>\n"
            + "    <brp:parameters brp:communicatieID=\"21321421\">\n"
            + "        <brp:abonnementNaam>testabo0</brp:abonnementNaam>\n"
            + "    </brp:parameters>\n"
            + "    <brp:zoekcriteriaPersoon brp:communicatieID=\"214314\">\n"
            + "        <brp:burgerservicenummer>123434538</brp:burgerservicenummer>\n"
            + "    </brp:zoekcriteriaPersoon>\n"
            + "</brp:lvg_synGeefSynchronisatiePersoon>\n";

        final String endpoint = String.format("http://%s:%d/synchronisatie/SynchronisatieService/lvgSynchronisatie",
            getOmgeving().geefOmgevingHost(), getPoortMap().get(Poorten.WEB_POORT_8080));
        final SoapParameters parameters = new SoapParameters(endpoint, NAMESPACE);
        final SoapClient soapClient = new SoapClient(parameters);

//        try {
//            final Node node = soapClient.verzendBerichtNaarService(request);
//            System.err.println(node);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            Node response = soapClient.verzendBerichtNaarService(request);
            return XmlUtils.toXmlString(response);
        } catch (SOAPFaultException sfe) {
            // geef SOAPFault terug als bericht
            Node fault = new DOMSource(sfe.getFault()).getNode();
            return XmlUtils.toXmlString(fault);
        } catch (Exception e) {
            //LOGGER.error 'Fout "{}" met XML bericht:\n{}', e.message, brpBericht
            throw new ServicesNietBereikbaarException("Kon SOAP-service niet bereiken. Foutmelding: $e.message", e);
        }
    }
}
