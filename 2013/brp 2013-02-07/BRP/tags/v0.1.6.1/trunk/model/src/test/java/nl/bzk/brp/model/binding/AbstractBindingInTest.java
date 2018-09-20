/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URISyntaxException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.xml.sax.SAXException;


/**
 * Deze klasse biedt standaard methodes om xml naar objecten te unmarshallen.
 *
 * @param <T> het object type waarnaar gemarshalled wordt.
 */
public abstract class AbstractBindingInTest<T> extends AbstractBindingTest<T> {

    /**
     * Valideert de opgegeven output XML tegen het schema. Middels assertions ({@link Assert#fail(String)}) worden
     * eventuele fouten aan de unit testing framework doorgegeven.
     *
     * @param outputXml de output xml die gevalideerd moet worden.
     */
    protected void valideerOutputTegenSchema(final String outputXml) {
        try {
            final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema =
                factory.newSchema(new StreamSource(new File(AbstractBindingUitTest.class
                        .getResource(getSchemaBestand()).toURI())));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(outputXml)));
        } catch (SAXException e) {
            Assert.fail(e.getMessage());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        } catch (URISyntaxException e) {
            Assert.fail(e.getMessage());
        }
    }

    protected abstract String getSchemaBestand();

    /**
     * Voert de unmarshalling uit om van een XML bericht (middels de JiBX databinding) de juiste Java object(en) te
     * genereren.
     *
     * @param xml het xml bericht dat geunmarshalled dient te worden.
     * @return het object dat is gegenereerd op basis van de unmarshalling.
     * @throws org.jibx.runtime.JiBXException in geval van problemen tijdens de unmarshalling (mogelijk binding
     *             configuratie problemen).
     */
    protected T unmarshalObject(final String xml) throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(getBindingClass());
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();

        @SuppressWarnings("unchecked")
        T unmarshalDocument = (T) uctx.unmarshalDocument(new ByteArrayInputStream(xml.getBytes()), "UTF-8");

        return unmarshalDocument;
    }

    /**
     * Methode om xml bestand in te lezen.
     *
     * @param xmlBestand xml bestands naam
     * @return de uit het bestand gelezen xml als String.
     * @throws java.io.IOException Indien een IO fout optreedt bij het lezen van het bestand.
     */
    protected String leesBestand(final String xmlBestand) throws IOException {
        InputStream is = getClass().getResourceAsStream(xmlBestand);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        StringBuilder inhoud = new StringBuilder();

        while ((line = br.readLine()) != null) {
            inhoud.append(line);
        }

        br.close();

        return inhoud.toString();
    }

}
