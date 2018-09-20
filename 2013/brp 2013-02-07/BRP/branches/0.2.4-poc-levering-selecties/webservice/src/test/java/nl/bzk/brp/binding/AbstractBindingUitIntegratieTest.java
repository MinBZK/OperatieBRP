/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import nl.bzk.brp.business.dto.bevraging.VraagDetailsPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.VraagKandidaatVaderBericht;
import nl.bzk.brp.business.dto.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenBericht;
import nl.bzk.brp.business.dto.bijhouding.CorrectieAdresBericht;
import nl.bzk.brp.business.dto.bijhouding.HuwelijkEnGeregistreerdPartnerschapBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.model.attribuuttype.Applicatienaam;
import nl.bzk.brp.model.attribuuttype.Organisatienaam;
import nl.bzk.brp.model.attribuuttype.Sleutelwaardetekst;
import nl.bzk.brp.model.groep.bericht.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BerichtBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;
import nl.bzk.brp.model.validatie.Melding;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;

/**
 * Abstract class voor het testen van de bericht binding, waarbij zowel de binding kan worden getest als ook de output
 * tegen het schema kan worden gevalideerd. Verder biedt deze abstracte test class methodes voor het retourneren van
 * een standaard bericht resultaat (conform een template en opgegeven waardes) en het vervangen van dynamische waarden
 * in een bericht voor een specifiek op te geven waarde.
 */
public abstract class AbstractBindingUitIntegratieTest<T> extends AbstractBindingIntegratieTest<T> {

    /**
     * Voert de marshalling uit om van een object middels de JiBX databinding de juiste XML te genereren.
     *
     * @param object het object dat gemarshalled dient te worden.
     * @return het naar XML gemarshallde object.
     * @throws org.jibx.runtime.JiBXException in geval van problemen tijdens de marshalling (mogelijk binding
     * configuratie problemen).
     */
    protected String marshalObject(final T object) throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(getBindingClass());
        IMarshallingContext mctx = bfact.createMarshallingContext();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        mctx.marshalDocument(object, "UTF-8", true, os);

        return os.toString();
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
     * @param tijdstipRegistratie het tijdstip van registratie.
     * @return een bericht resultaat met de opgegeven waardes.
     */
    protected String getBerichtResultaatTemplate(final String resultaatNodeNaam, final String verwerkingCode,
        final String hoogsteMeldingsniveauCode, final Melding[] meldingen, final String[] bsns,
        final String tijdstipRegistratie, final boolean heeftBijhoudingsCode)
    {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>").append("<brp:")
                 .append(resultaatNodeNaam).append(" xmlns:brp=\"" + NAMESPACE_BRP + "\"")
                 .append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">").append("<brp:stuurgegevens>")
                 .append("<brp:organisatie>Ministerie BZK</brp:organisatie>").append("<brp:applicatie>BRP</brp:applicatie>")
                 .append("<brp:referentienummer>OnbekendeID</brp:referentienummer>")
                 .append("<brp:crossReferentienummer>OnbekendeID</brp:crossReferentienummer>")
                 .append("</brp:stuurgegevens>").append("<brp:resultaat>").append("<brp:verwerkingCode>%s</brp:verwerkingCode>");
        if (heeftBijhoudingsCode) {
            resultaat.append("<brp:bijhoudingCode>V</brp:bijhoudingCode>");
        }

        if (hoogsteMeldingsniveauCode == null) {
            resultaat.append("<brp:hoogsteMeldingsniveauCode xsi:nil=\"true\"/>");
        } else {
            resultaat.append("<brp:hoogsteMeldingsniveauCode>%s</brp:hoogsteMeldingsniveauCode>");
        }
        if (tijdstipRegistratie != null) {
            resultaat.append("<brp:tijdstipRegistratie>").append(tijdstipRegistratie).append("</brp:tijdstipRegistratie>");
        } else {
            resultaat.append("<brp:tijdstipRegistratie xsi:nil=\"true\"/>");
        }
        resultaat.append("</brp:resultaat>");
        if (meldingen != null && meldingen.length > 0) {
            resultaat.append("<brp:meldingen>");
            for (Melding melding : meldingen) {
                resultaat.append("<brp:melding>");
                resultaat.append("<brp:regelCode>").append(melding.getCode().getNaam()).append("</brp:regelCode>");
                resultaat.append("<brp:soortCode>").append(melding.getSoort().getCode()).append("</brp:soortCode>");
                resultaat.append("<brp:melding>").append(melding.getOmschrijving()).append("</brp:melding>");
                resultaat.append("</brp:melding>");
            }
            resultaat.append("</brp:meldingen>");
        }
        if (bsns != null && bsns.length > 0) {
            resultaat.append("<brp:bijgehoudenPersonen>");
            for (String bsn : bsns) {
                resultaat.append("<brp:persoon>");
                resultaat.append("<brp:identificatienummers>");
                resultaat.append("<brp:burgerservicenummer>").append(bsn).append("</brp:burgerservicenummer>");
                resultaat.append("</brp:identificatienummers>");
                resultaat.append("</brp:persoon>");
            }
            resultaat.append("</brp:bijgehoudenPersonen>");
        }
        resultaat.append("</brp:").append(resultaatNodeNaam).append(">");
        return String.format(resultaat.toString(), verwerkingCode, hoogsteMeldingsniveauCode);
    }

    protected BerichtStuurgegevensGroepBericht bouwBerichtStuurgegevens() {
        BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        stuurgegevens.setOrganisatie(new Organisatienaam("Ministerie BZK"));
        stuurgegevens.setApplicatie(new Applicatienaam("BRP"));
        stuurgegevens.setReferentienummer(new Sleutelwaardetekst("OnbekendeID"));
        stuurgegevens.setCrossReferentienummer(new Sleutelwaardetekst("OnbekendeID"));
        return stuurgegevens;
    }

    protected BerichtBericht bouwIngaandBericht(final Soortbericht srt) {
        final BerichtBericht bericht;
        switch (srt) {
            case AFSTAMMING_INSCHRIJVINGAANGIFTEGEBOORTE_BIJHOUDING:
                bericht = new InschrijvingGeboorteBericht();
                break;
            case HUWELIJKPARTNERSCHAP_REGISTRATIEHUWELIJK_BIJHOUDING:
                bericht = new HuwelijkEnGeregistreerdPartnerschapBericht();
                break;
            case HUWELIJKPARTNERSCHAP_REGISTRATIEPARTNERSCHAP_BIJHOUDING:
                throw new UnsupportedOperationException("Registratie partnerschap wordt nog niet ondersteund.");
            case MIGRATIE_CORRECTIEADRESBINNENNL_BIJHOUDING:
                bericht = new CorrectieAdresBericht();
                break;
            case MIGRATIE_VERHUIZING_BIJHOUDING:
                bericht = new VerhuizingBericht();
                break;
            case VRAAG_DETAILSPERSOON_BEVRAGEN:
                bericht = new VraagDetailsPersoonBericht();
                break;
            case VRAAG_KANDIDAATVADER_BEVRAGEN:
                bericht = new VraagKandidaatVaderBericht();
                break;
            case VRAAG_PERSONENOPADRESINCLUSIEFBETROKKENHEDEN_BEVRAGEN:
                bericht = new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
                break;
            default:
                throw new IllegalStateException("Mapping van ingaande en uitgaande bericht soorten is niet compleet.");
        }
        bericht.setBerichtStuurgegevensGroep(bouwBerichtStuurgegevens());
        return bericht;
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
        final String startNode = "<brp:" + nodeNaam + ">";
        final String eindNode = "</brp:" + nodeNaam + ">";

        StringBuilder resultaat = new StringBuilder();
        int indexStart = xml.indexOf(startNode);
        int indexEind = 0;

        if (indexStart < 0) {
            resultaat.append(xml);
        } else {
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
        }
        return resultaat.toString();
    }

    protected void bouwAntwoordElement(final StringBuilder stringBuilder, final String bestandsNaam) {
        InputStream is = getClass().getResourceAsStream(bestandsNaam);

        if (is == null) {
            Assert.fail(
                String.format("Kan bestand '%s' niet lezen/vinden voor inlezen verwacht resultaat", bestandsNaam));
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            try {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line.trim());
                    line = reader.readLine();
                }
            } catch (IOException e) {
                Assert.fail("Fout bij het inlezen van bestand: " + bestandsNaam);
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Hoeft niets te doen
                }
            }
        }
    }

}
