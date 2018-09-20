/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import nl.bzk.brp.utils.SchemaUtils;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;


/**
 * Deze klasse biedt standaard methodes om xml naar objecten te unmarshallen.
 * Deze dient voor integratie testen oftwel het testen van volledige XML bestanden.
 *
 * @param <T> het object type waarnaar gemarshalled wordt.
 */
public abstract class AbstractBindingInIntegratieTest<T> extends AbstractBindingIntegratieTest<T> {

    private final SchemaUtils schemaUtils = new SchemaUtils();

    protected SchemaUtils getSchemaUtils() {
        return schemaUtils;
    }

    /**
     * Voert de unmarshalling uit om van een XML bericht (middels de JiBX databinding) de juiste Java object(en) te
     * genereren.
     *
     * @param xml het xml bericht dat geunmarshalled dient te worden.
     * @return het object dat is gegenereerd op basis van de unmarshalling.
     * @throws org.jibx.runtime.JiBXException in geval van problemen tijdens de unmarshalling (mogelijk binding
     *                                        configuratie problemen).
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
