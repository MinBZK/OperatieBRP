/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import nl.bzk.brp.model.validatie.Melding;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

/**
 * Abstracte basis klasse voor alle binding tests. Deze klasse biedt standaard methodes om objecten naar xml te
 * marshallen en xml naar objecten te unmarshallen.
 */
public abstract class AbstractBindingTest<T> {

    protected static final String NAMESPACE_BRP = "http://www.bprbzk.nl/BRP/0001";

    /**
     * Voert de marshalling uit om van een object middels de JiBX databinding de juiste XML te genereren.
     *
     * @param object het object dat gemarshalled dient te worden.
     * @return het naar XML gemarshallde object.
     * @throws JiBXException in geval van problemen tijdens de marshalling (mogelijk binding configuratie problemen).
     */
    protected String marshalObject(final T object) throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(getBindingClass());
        IMarshallingContext mctx = bfact.createMarshallingContext();

        OutputStream os = new ByteArrayOutputStream();
        mctx.marshalDocument(object, "UTF-8", true, os);
        return os.toString();
    }

    /**
     * Voert de unmarshalling uit om van een XML bericht (middels de JiBX databinding) de juiste Java object(en) te
     * genereren.
     *
     * @param xml het xml bericht dat geunmarshalled dient te worden.
     * @return het object dat is gegenereerd op basis van de unmarshalling.
     * @throws JiBXException in geval van problemen tijdens de unmarshalling (mogelijk binding configuratie problemen).
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

    /**
     * Retourneert de class van het object dat gemarshalled en geunmarshalled moet worden.
     *
     * @return de class van het object dat gemarshalled en geunmarshalled moet worden.
     */
    protected abstract Class<T> getBindingClass();

    /**
     * Vervangt een (dynamische) waarde tussen alle tags/nodes met de opgegeven naam met een "dummy" waarde in de
     * opgegeven xml.
     *
     * @param xml De xml waarin de waarde tussen de opgegeven nodes moet worden vervangen.
     * @return De xml met de waardes omgezet naar een "dummy" waarde.
     */
    protected String vervangDynamischeWaardeVoorDummyWaarde(final String xml, final String nodeNaam) {
        final String startNode = "<" + nodeNaam + ">";
        final String eindNode = "</" + nodeNaam + ">";

        StringBuilder resultaat = new StringBuilder();
        int indexStart = xml.indexOf(startNode);
        int indexEind = 0;
        while (indexStart >= 0) {
            resultaat.append(xml.substring(indexEind, indexStart));
            resultaat.append(startNode);
            resultaat.append("dummy");

            indexEind = xml.indexOf(eindNode, indexStart);
            indexStart = xml.indexOf(startNode, indexEind);
        }
        if (indexEind > 0) {
            resultaat.append(xml.substring(indexEind));
        }
        return resultaat.toString();
    }

    /**
     * Bouwt een berichtresultaat op, met verwachte opgegeven verwerkingcode, hoogste meldingsniveaucode, de opgegeven
     * meldingen en een lijst van bijgehouden personen op basis van de opgegeven bsns.
     *
     * @param resultaatNodeNaam de node naam van de resultaat node.
     * @param verwerkingCode de voor het bericht geldende verwerkingCode.
     * @param hoogsteMeldingsniveauCode de voor het bericht geldende hoogste meldings niveau.
     * @param meldingen de meldingen die in het bericht aanwezig dienen te zijn.
     * @param bsns de bsns van bijgehouden personen.
     * @return een bericht resultaat met de opgegeven waardes.
     */
    protected String getBerichtResultaatTemplate(final String resultaatNodeNaam, final String verwerkingCode,
        final String hoogsteMeldingsniveauCode, final Melding[] meldingen, final String[] bsns)
    {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
                 .append("<").append(resultaatNodeNaam).append(" xmlns=\"" + NAMESPACE_BRP + "\">")
                 .append("<stuurgegevens>")
                 .append("<organisatie>mGBA</organisatie>")
                 .append("<applicatie>BRP</applicatie>")
                 .append("<referentienummer>1234567890</referentienummer>")
                 .append("</stuurgegevens>")
                 .append("<resultaat>")
                 .append("<verwerkingCode>%s</verwerkingCode>")
                 .append("<hoogsteMeldingsniveauCode>%s</hoogsteMeldingsniveauCode>")
                 .append("<tijdstipRegistratie>dummy</tijdstipRegistratie>")
                 .append("</resultaat>");
        if (meldingen != null && meldingen.length > 0) {
            resultaat.append("<meldingen>");
            for (Melding melding : meldingen) {
                resultaat.append("<bas:melding xmlns:bas=\"http://www.brp.bzk.nl/basis\">");
                resultaat.append("<bas:soort>").append(melding.getSoort().getNaam()).append("</bas:soort>");
                resultaat.append("<bas:code>").append(melding.getCode().getNaam()).append("</bas:code>");
                resultaat.append("<bas:omschrijving>").append(melding.getOmschrijving()).append("</bas:omschrijving>");
                resultaat.append("</bas:melding>");
            }
            resultaat.append("</meldingen>");
        }
        if (bsns != null && bsns.length > 0) {
            resultaat.append("<bijgehoudenPersonen>");
            for (String bsn : bsns) {
                resultaat.append("<persoon>");
                resultaat.append("<identificatieNummers>");
                resultaat.append("<burgerservicenummer>").append(bsn).append("</burgerservicenummer>");
                resultaat.append("</identificatieNummers>");
                resultaat.append("</persoon>");
            }
            resultaat.append("</bijgehoudenPersonen>");
        }
        resultaat.append("</").append(resultaatNodeNaam).append(">");
        return String.format(resultaat.toString(), verwerkingCode, hoogsteMeldingsniveauCode);
    }

}
