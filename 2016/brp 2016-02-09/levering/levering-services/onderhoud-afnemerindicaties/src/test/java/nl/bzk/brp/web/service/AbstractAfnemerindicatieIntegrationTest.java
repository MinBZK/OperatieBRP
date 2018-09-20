/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPathExpressionException;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.utils.XmlUtils;
import org.junit.Assert;
import org.springframework.test.annotation.DirtiesContext;
import org.w3c.dom.Document;

/**
 * Abstracte integratie test voor webservice van synchronisatie.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AbstractAfnemerindicatieIntegrationTest extends AbstractIntegrationTest {
    private static final String WSDL_URL      = "http://localhost:%s/brp/AfnemerindicatiesService/lvgAfnemerindicaties?wsdl";
    private static final String NAMESPACE_URL = "http://www.bzk.nl/brp/levering/afnemerindicaties/service";

    protected final String getSchemaBestand() {
        return "brp0200_lvgAfnemerindicatie_Berichten.xsd";
    }

    @Override
    public final URL getWsdlUrl() throws MalformedURLException {
        return new URL(String.format(WSDL_URL, jettyPort));
    }

    @Override
    QName getServiceQName() {

        return new QName(NAMESPACE_URL, "AfnemerindicatiesService");
    }

    @Override
    QName getPortName() {
        return new QName(NAMESPACE_URL, "lvgAfnemerindicaties");
    }

    // We gebruiken de testdata van de test-jar van levering-dataaccess.
    @Override
    protected List<String> getInitieleDataBestanden() {
        final List<String> dataBestanden = new ArrayList<>();
        dataBestanden.addAll(Arrays.asList(
            "/data/dataset.xml",
            "/data/aut-lev.xml"));

        return dataBestanden;
    }

    @Override
    protected void bewerkRequestDocumentVoorVerzending(final Document document) {
        //Doe niks.
    }

    /**
     * Controleert het verwerkingsresultaat.
     *
     * @param verwerkingsresultaat het verwerkingsresultaat
     * @param document             het document
     * @throws javax.xml.xpath.XPathExpressionException
     *          de x path expression exception
     */
    protected void assertVerwerkingsresultaat(final Verwerkingsresultaat verwerkingsresultaat, final Document document)
        throws XPathExpressionException
    {
        Assert.assertEquals(verwerkingsresultaat.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking", document));
    }

}
