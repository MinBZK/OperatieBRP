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
import nl.bzk.brp.testrunner.component.services.soap.ServicesNietBereikbaarException;
import nl.bzk.brp.testrunner.component.services.soap.SoapClient;
import nl.bzk.brp.testrunner.component.services.soap.SoapParameters;
import nl.bzk.brp.testrunner.component.util.XmlUtils;
import nl.bzk.brp.testrunner.omgeving.LogischeNaam;
import nl.bzk.brp.testrunner.omgeving.Omgeving;
import nl.bzk.brp.testrunner.component.util.VersieUrlChecker;
import org.w3c.dom.Node;

/**
 */
@LogischeNaam(ComponentNamen.ONDERHOUDAFNEMERINDICATIES)
final class OnderhoudAfnemerindicatieComponentImpl extends AbstractDockerComponentMetCache implements OnderhoudAfnemerindicatie {

    private static final String NAMESPACE = "http://www.bzk.nl/brp/levering/afnemerindicaties/service";

    protected OnderhoudAfnemerindicatieComponentImpl(final Omgeving omgeving) {
        super(omgeving, "onderhoud-afnermerindicaties");
    }

    @Override
    protected DockerImage geefDockerImage() {
        return new DockerImage("brp/onderhoud-afnemerindicaties");
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
    protected List<Integer> geefInternePoorten() {
        final List<Integer> internePoorten = super.geefInternePoorten();
        internePoorten.add(Poorten.WEB_POORT_8080);
        return internePoorten;
    }

    @Override
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
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar() && VersieUrlChecker.check(this, "afnemerindicaties");
    }

    @Override
    public String plaats() {
        //SOAP-request

        //FIXME tijdelijk even hardcoded, later vervangen door YML
        String request = "<brp:lvg_synRegistreerAfnemerindicatie xmlns:brp=\"http://www.bzk.nl/brp/brp0200\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
            + "      <brp:stuurgegevens>\n"
            + "         <brp:zendendePartij>199903</brp:zendendePartij>\n"
            + "         <brp:zendendeSysteem>BRP</brp:zendendeSysteem>\n"
            + "         <brp:referentienummer>*</brp:referentienummer>\n"
            + "         <brp:crossReferentienummer>*</brp:crossReferentienummer>\n"
            + "         <brp:tijdstipVerzending>*</brp:tijdstipVerzending>\n"
            + "      </brp:stuurgegevens>\n"
            + "      <brp:resultaat>\n"
            + "         <brp:verwerking>Geslaagd</brp:verwerking>\n"
            + "         <brp:bijhouding>Verwerkt</brp:bijhouding>\n"
            + "         <brp:hoogsteMeldingsniveau>Geen</brp:hoogsteMeldingsniveau>\n"
            + "      </brp:resultaat>\n"
            + "      <brp:plaatsingAfnemerindicatie brp:objectSleutel=\"*\" brp:objecttype=\"AdministratieveHandeling\">\n"
            + "         <brp:partijCode>017401</brp:partijCode>\n"
            + "         <brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>\n"
            + "         <brp:actieInhoud>*</brp:actieInhoud>\n"
            + "         <brp:bijgehoudenPersonen>\n"
            + "            <brp:persoon brp:objecttype=\"Persoon\">\n"
            + "               <brp:identificatienummers>\n"
            + "                  <brp:burgerservicenummer>110015927</brp:burgerservicenummer>\n"
            + "               </brp:identificatienummers>\n"
            + "              <brp:administratieveHandelingen>*</brp:administratieveHandelingen>\n"
            + "            </brp:persoon>\n"
            + "         </brp:bijgehoudenPersonen>\n"
            + "      </brp:plaatsingAfnemerindicatie>\n"
            + "   </brp:lvg_synRegistreerAfnemerindicatie>";

        final String endpoint = String.format("http://%s:%d/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties",
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

    @Override
    public void verwijderAfnemerindicatie() {
        //SOAP request
    }

}
