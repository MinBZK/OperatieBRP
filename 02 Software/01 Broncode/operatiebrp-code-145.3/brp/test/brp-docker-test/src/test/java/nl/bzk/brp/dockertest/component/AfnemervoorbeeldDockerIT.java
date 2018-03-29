/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;
import javax.jms.JMSException;
import org.junit.Test;

/**
 */
public class AfnemervoorbeeldDockerIT {

    @Test
    public void test() throws InterruptedException, JMSException, IOException {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.AFNEMERVOORBEELD).build();
        omgeving.start();
        omgeving.stop();
    }


    @Test
    public void test2() throws IOException, InterruptedException, JMSException {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.AFNEMERVOORBEELD).build();
        omgeving.start();

        final Integer poort = omgeving.geefDocker(DockerNaam.AFNEMERVOORBEELD).getPoortMap().get(Poorten.APPSERVER_PORT);
        URL url = new URL(String.format("http://%s:%d/afnemervoorbeeld/BrpBerichtVerwerkingService/VrijBericht", omgeving.getDockerHostname(),
                poort));
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(
                connection.getOutputStream());
        out.write("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:brp=\"http://www.bzk.nl/brp/brp0200\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <brp:vrb_vrbVerwerkVrijBericht>\n"
                + "         <brp:stuurgegevens>\n"
                + "            <brp:zendendePartij>034401</brp:zendendePartij>\n"
                + "            <brp:zendendeSysteem>BRP</brp:zendendeSysteem>\n"
                + "            <brp:ontvangendePartij>034401</brp:ontvangendePartij>\n"
                + "            <brp:referentienummer>77398ffc-2bb4-65ae-7526-30453125c255</brp:referentienummer>\n"
                + "            <brp:tijdstipVerzending>2017-01-16T11:42:34.009Z</brp:tijdstipVerzending>\n"
                + "         </brp:stuurgegevens>\n"
                + "         <brp:parameters brp:communicatieID=\"02V\">\n"
                + "            <brp:zenderVrijBericht>034401</brp:zenderVrijBericht>\n"
                + "            <brp:ontvangerVrijBericht>039201</brp:ontvangerVrijBericht>\n"
                + "         </brp:parameters>\n"
                + "         <brp:vrijBericht brp:communicatieID=\"03V\">\n"
                + "            <brp:soortNaam>Beheermelding</brp:soortNaam>\n"
                + "            <brp:inhoud>Inhoud vrijbericht</brp:inhoud>\n"
                + "         </brp:vrijBericht>\n"
                + "      </brp:vrb_vrbVerwerkVrijBericht>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>");
        out.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        String decodedString;
        while ((decodedString = in.readLine()) != null) {
            System.out.println(decodedString);
        }
        in.close();

        omgeving.asynchroonBericht().wachtTotOntvangen(null);


        TimeUnit.MINUTES.sleep(10);

        omgeving.stop();
    }
}
