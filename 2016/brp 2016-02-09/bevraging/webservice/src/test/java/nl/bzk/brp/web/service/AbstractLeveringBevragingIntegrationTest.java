/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

import org.springframework.test.annotation.DirtiesContext;
import org.w3c.dom.Document;

/**
 *
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AbstractLeveringBevragingIntegrationTest extends AbstractIntegrationTest {

    private static final String URI = "http://www.bzk.nl/brp/levering/bevraging/service";
    private final String wsdlUrlBevraging = "http://localhost:%s/brp/LeveringBevragingService/lvgBevraging?wsdl";

    @Override
    URL getWsdlUrl() throws MalformedURLException {
        return new URL(String.format(wsdlUrlBevraging, jettyPort));
    }

    @Override
    QName getServiceQName() {
        return new QName(URI, "LeveringBevragingService");
    }

    @Override
    QName getPortName() {
        return new QName(URI, "lvgBevraging");
    }

    @Override
    protected void bewerkRequestDocumentVoorVerzending(final Document document) {
        //Doe niks.
    }
}
