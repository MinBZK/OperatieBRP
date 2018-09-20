/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import nl.bzk.brp.model.binding.AbstractBindingUitTest;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.xml.sax.SAXException;

/**
 * Abstract class voor het testen van de bericht binding, waarbij zowel de binding kan worden getest als ook de output
 * tegen het schema kan worden gevalideerd. Verder biedt deze abstracte test class methodes voor het retourneren van
 * een standaard bericht resultaat (conform een template en opgegeven waardes) en het vervangen van dynamische waarden
 * in een bericht voor een specifiek op te geven waarde.
 */
public abstract class AbstractBerichtBindingUitTest<T> extends AbstractBindingUitTest<T> {

    /**
     * Valideert de opgegeven output XML tegen het schema. Middels assertions ({@link Assert#fail(String)}) worden
     * eventuele fouten aan de unit testing framework doorgegeven.
     *
     * @param outputXml de output xml die gevalideerd moet worden.
     */
    protected void valideerOutputTegenSchema(final String outputXml) {
        try {
            final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(
                new File(AbstractBindingUitTest.class.getResource(getSchemaBestand()).toURI())));
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
     * Bouwt een berichtresultaat op, met verwachte opgegeven verwerkingcode, hoogste meldingsniveaucode, de opgegeven
     * meldingen en een lijst van bijgehouden personen op basis van de opgegeven bsns.
     *
     * @param resultaatNodeNaam de node naam van de resultaat node.
     * @param verwerkingCode de voor het bericht geldende verwerkingCode.
     * @param hoogsteMeldingsniveauCode de voor het bericht geldende hoogste meldings niveau.
     * @param meldingen de meldingen die in het bericht aanwezig dienen te zijn.
     * @param bsns de bsns van bijgehouden personen.
     * @param tijdstipRegistratie het tijdstip van registratie.
     * @return een bericht resultaat met de opgegeven waardes.
     */
    protected String getBerichtResultaatTemplate(final String resultaatNodeNaam, final String verwerkingCode,
        final String hoogsteMeldingsniveauCode, final Melding[] meldingen, final String[] bsns,
        final String tijdstipRegistratie)
    {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>").append("<")
                 .append(resultaatNodeNaam).append(" xmlns=\"" + NAMESPACE_BRP + "\"")
                 .append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">").append("<stuurgegevens>")
                 .append("<organisatie>mGBA</organisatie>").append("<applicatie>BRP</applicatie>")
                 .append("<referentienummer>OnbekendeID</referentienummer>").append("</stuurgegevens>")
                 .append("<resultaat>").append("<verwerkingCode>%s</verwerkingCode>");
        if (hoogsteMeldingsniveauCode == null) {
            resultaat.append("<hoogsteMeldingsniveauCode xsi:nil=\"true\"/>");
        } else {
            resultaat.append("<hoogsteMeldingsniveauCode>%s</hoogsteMeldingsniveauCode>");
        }
        resultaat.append("<tijdstipRegistratie>").append(tijdstipRegistratie).append("</tijdstipRegistratie>");
        resultaat.append("</resultaat>");
        if (meldingen != null && meldingen.length > 0) {
            resultaat.append("<meldingen>");
            for (Melding melding : meldingen) {
                resultaat.append("<melding>");
                resultaat.append("<regelCode>").append(melding.getCode().getNaam()).append("</regelCode>");
                resultaat.append("<soortCode>").append(melding.getSoort().getCode()).append("</soortCode>");
                resultaat.append("<melding>").append(melding.getOmschrijving()).append("</melding>");
                resultaat.append("</melding>");
            }
            resultaat.append("</meldingen>");
        }
        if (bsns != null && bsns.length > 0) {
            resultaat.append("<bijgehoudenPersonen>");
            for (String bsn : bsns) {
                resultaat.append("<persoon>");
                resultaat.append("<identificatienummers>");
                resultaat.append("<burgerservicenummer>").append(bsn).append("</burgerservicenummer>");
                resultaat.append("</identificatienummers>");
                resultaat.append("</persoon>");
            }
            resultaat.append("</bijgehoudenPersonen>");
        }
        resultaat.append("</").append(resultaatNodeNaam).append(">");
        return String.format(resultaat.toString(), verwerkingCode, hoogsteMeldingsniveauCode);
    }

    /**
     * Vervangt een (dynamische) waarde tussen alle tags/nodes met de opgegeven naam met een "dummy" waarde in de
     * opgegeven xml.
     *
     * @param xml De xml waarin de waarde tussen de opgegeven nodes moet worden vervangen.
     * @param nodeNaam Naam van de node.
     * @param dummyWaarde de "dummy" waarde die gezet moet worden.
     * @return De xml met de waardes omgezet naar een "dummy" waarde.
     */
    protected String vervangDynamischeWaardeVoorDummyWaarde(final String xml, final String nodeNaam,
        final Object dummyWaarde)
    {
        final String startNode = "<" + nodeNaam + ">";
        final String eindNode = "</" + nodeNaam + ">";

        StringBuilder resultaat = new StringBuilder();
        int indexStart = xml.indexOf(startNode);
        int indexEind = 0;
        while (indexStart >= 0) {
            resultaat.append(xml.substring(indexEind, indexStart));
            resultaat.append(startNode);
            resultaat.append(dummyWaarde);

            indexEind = xml.indexOf(eindNode, indexStart);
            indexStart = xml.indexOf(startNode, indexEind);
        }
        if (indexEind > 0) {
            resultaat.append(xml.substring(indexEind));
        }
        return resultaat.toString();
    }

}
