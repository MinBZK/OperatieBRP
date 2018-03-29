/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Converteert een xml response bericht naar expected (m.a.w. '*' voor waarden actieInhoud, voorkomenSleutels etc.)
 */
public class XmlResponseNaarExpectedConverter {

    public static void main(String[] args) throws IOException, URISyntaxException {

        String pad = "./testcases/levering/end2end/BRP_INTEGRATIE_TEST/AGNL/Expected_MutatieBerichten/expected_scenario_1.xml";

        converteer(pad);
    }

    private static void converteer(String pad) throws IOException, URISyntaxException {
        Path responseXml = Paths.get(ClassLoader.getSystemResource(pad).toURI());
        String content = new String(Files.readAllBytes(responseXml));

        content = content.replaceAll("<brp:referentienummer>.*?</brp:referentienummer>", "<brp:referentienummer>*</brp:referentienummer>");
        content = content.replaceAll("<brp:tijdstipVerzending>.*?</brp:tijdstipVerzending>", "<brp:tijdstipVerzending>*</brp:tijdstipVerzending>");
        content = content.replaceAll("brp:voorkomenSleutel=\".*?\"", "brp:voorkomenSleutel=\"*\"");
        content = content.replaceAll("brp:communicatieID=\".*?\"", "brp:communicatieID=\"*\"");
        content = content.replaceAll("brp:objectSleutel=\".*?\"", "brp:objectSleutel=\"*\"");
        content = content.replaceAll("brp:referentieID=\".*?\"", "brp:referentieID=\"*\"");
        content = content.replaceAll("<brp:actieInhoud>.*?</brp:actieInhoud>", "<brp:actieInhoud>*</brp:actieInhoud>");
        content = content.replaceAll("<brp:tijdstipVerval>.*?</brp:tijdstipVerval>", "<brp:tijdstipVerval>*</brp:tijdstipVerval>");
        content = content.replaceAll("<brp:actieVerval>.*?</brp:actieVerval>", "<brp:actieVerval>*</brp:actieVerval>");
        content = content.replaceAll("<brp:tijdstipLaatsteWijziging>.*?</brp:tijdstipLaatsteWijziging>", "<brp:tijdstipLaatsteWijziging>*</brp:tijdstipLaatsteWijziging>");
        content = content.replaceAll("<brp:tijdstipLaatsteWijzigingGBASystematiek>.*?</brp:tijdstipLaatsteWijzigingGBASystematiek>", "<brp:tijdstipLaatsteWijzigingGBASystematiek>*</brp:tijdstipLaatsteWijzigingGBASystematiek>");
        content = content.replaceAll("<brp:actieAanpassingGeldigheid>.*?</brp:actieAanpassingGeldigheid>", "<brp:actieAanpassingGeldigheid>*</brp:actieAanpassingGeldigheid>");
        content = content.replaceAll("<brp:tijdstipRegistratie>.*?</brp:tijdstipRegistratie>", "<brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>");

        System.out.println(content);
    }

}
