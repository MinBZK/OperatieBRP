/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import static nl.bzk.brp.util.StatischeObjecttypeBuilder.PARTIJ_MINISTERIE_BZK;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.BijhoudingsresultaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingsresultaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtMeldingBericht;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.utils.SchemaUtils;
import org.apache.commons.lang.StringUtils;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;


/**
 * Abstract class voor het testen van de bericht binding, waarbij zowel de binding kan worden getest als ook de output tegen het schema kan worden
 * gevalideerd. Verder biedt deze abstracte test class methodes voor het retourneren van een standaard bericht resultaat (conform een template en opgegeven
 * waardes) en het vervangen van dynamische waarden in een bericht voor een specifiek op te geven waarde.
 */
public abstract class AbstractBindingUitIntegratieTest<T> extends AbstractBindingIntegratieTest<T> {

    protected static final Map<String, String> BRP_NAMESPACE_MAP = new HashMap<String, String>() {
        {
            put("brp", "http://www.bzk.nl/brp/brp0200");
        }
    };

    private static final int INDENT = 2;

    private final  SchemaUtils         schemaUtils       = new SchemaUtils();

    protected SchemaUtils getSchemaUtils() {
        return schemaUtils;
    }

    /**
     * Voert de marshalling uit om van een object middels de JiBX databinding de juiste XML te genereren.
     *
     * @param object het object dat gemarshalled dient te worden.
     * @return het naar XML gemarshallde object.
     * @throws org.jibx.runtime.JiBXException in geval van problemen tijdens de marshalling (mogelijk binding configuratie problemen).
     */
    protected String marshalObject(final T object) throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(getBindingClass());
        IMarshallingContext mctx = bfact.createMarshallingContext();
        mctx.setIndent(INDENT);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        mctx.marshalDocument(object, "UTF-8", true, os);

        return os.toString();
    }

    /**
     * Bouwt een berichtresultaat op, met verwachte opgegeven verwerkingcode, hoogste meldingsniveaucode, de opgegeven meldingen en een lijst van
     * bijgehouden personen op basis van de opgegeven bsns.
     *
     * @param resultaatNodeNaam        de node naam van de resultaat node.
     * @param verwerking               de voor het bericht geldende verwerkingCode.
     * @param hoogsteMeldingsniveau    de voor het bericht geldende hoogste meldings niveau.
     * @param meldingen                de meldingen die in het bericht aanwezig dienen te zijn.
     * @param bijgehoudenPersonen      de bijgehouden personen.
     * @param tijdstip                 het tijdstip van registratie.
     * @param administratieveHandeling de xml tag van de administratieve handeling
     * @return een bericht resultaat met de opgegeven waardes.
     */
    protected String getBerichtResultaatTemplate(final String resultaatNodeNaam, final String verwerking,
        final String hoogsteMeldingsniveau, final Melding[] meldingen,
        final List<PersoonBericht> bijgehoudenPersonen, final List<DocumentBericht> bijgehoudenDocumenten,
        final String tijdstip, final String administratieveHandeling, final boolean heeftBijhoudingsCode)
    {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>").append("<brp:")
            .append(resultaatNodeNaam).append(" xmlns:brp=\"" + NAMESPACE_BRP + "\"")
            .append(" xmlns:xsi=\"http://www.w3" + ".org/2001/XMLSchema-instance\">").append("<brp:stuurgegevens>")
            .append("<brp:zendendePartij>000101</brp:zendendePartij>")
            .append("<brp:zendendeSysteem>BRP</brp:zendendeSysteem>")
            .append("<brp:referentienummer>12345678-1234-1234-1234-123456789123</brp:referentienummer>")
            .append("<brp:crossReferentienummer>12345678-1234-1234-1234-123456789123</brp:crossReferentienummer>")
            .append("<brp:tijdstipVerzending>2012-03-25T14:35:06.789</brp:tijdstipVerzending>")
            .append("</brp:stuurgegevens>").append("<brp:resultaat>").append("<brp:verwerking>%s</brp:verwerking>");
        if (heeftBijhoudingsCode) {
            resultaat.append("<brp:bijhouding>Verwerkt</brp:bijhouding>");
        }

        if (StringUtils.isBlank(hoogsteMeldingsniveau)) {
            throw new IllegalArgumentException("Hoogste melding niveau is verplicht in antwoord berichten.");
        } else {
            resultaat.append("<brp:hoogsteMeldingsniveau>%s</brp:hoogsteMeldingsniveau>");
        }

        resultaat.append("</brp:resultaat>");
        if (meldingen != null && meldingen.length > 0) {
            resultaat.append("<brp:meldingen>");
            for (Melding melding : meldingen) {
                resultaat.append("<brp:melding brp:objecttype=\"Melding\" brp:referentieID=\"communicatieId\">");
                resultaat.append("<brp:regelCode>").append(melding.getRegel().name()).append("</brp:regelCode>");
                resultaat.append("<brp:soortNaam>").append(melding.getSoort().getNaam()).append("</brp:soortNaam>");
                resultaat.append("<brp:melding>").append(melding.getMeldingTekst()).append("</brp:melding>");
                resultaat.append("</brp:melding>");
            }
            resultaat.append("</brp:meldingen>");
        }

        resultaat.append("<brp:" + administratieveHandeling + " brp:objecttype=\"AdministratieveHandeling\">");
        resultaat.append("<brp:partijCode>000001</brp:partijCode>");
        resultaat.append("<brp:tijdstipRegistratie>" + tijdstip + "</brp:tijdstipRegistratie>");
        if (bijgehoudenPersonen != null && !bijgehoudenPersonen.isEmpty()) {
            resultaat.append("<brp:bijgehoudenPersonen>");
            for (PersoonBericht bijgehPers : bijgehoudenPersonen) {
                resultaat.append("<brp:persoon brp:objecttype=\"Persoon\">");
                resultaat.append("<brp:identificatienummers>");
                resultaat.append("<brp:burgerservicenummer>")
                    .append(bijgehPers.getIdentificatienummers().getBurgerservicenummer().getWaarde())
                    .append("</brp:burgerservicenummer>");
                resultaat.append("</brp:identificatienummers>");
                resultaat.append("</brp:persoon>");
            }
            resultaat.append("</brp:bijgehoudenPersonen>");
        }
        if (bijgehoudenDocumenten != null && !bijgehoudenDocumenten.isEmpty()) {
            resultaat.append("<brp:bijgehoudenDocumenten>");
            for (DocumentBericht bijgehDocument : bijgehoudenDocumenten) {
                resultaat.append("<brp:document ").append("brp:objecttype=\"Document\" ")
                    .append("brp:objectSleutel=\"").append(bijgehDocument.getObjectSleutel())
                    .append("\" brp:referentieID=\"").append(bijgehDocument.getReferentieID()).append("\">");
                resultaat.append("<brp:soortNaam>").append(bijgehDocument.getSoortNaam()).append("</brp:soortNaam>");
                resultaat.append("<brp:identificatie>")
                    .append(bijgehDocument.getStandaard().getIdentificatie().getWaarde())
                    .append("</brp:identificatie>");
                resultaat.append("<brp:partijCode>").append(bijgehDocument.getStandaard().getPartijCode())
                    .append("</brp:partijCode>");
                resultaat.append("</brp:document>");
            }
            resultaat.append("</brp:bijgehoudenDocumenten>");
        }
        resultaat.append("</brp:" + administratieveHandeling + ">");
        resultaat.append("</brp:").append(resultaatNodeNaam).append(">");
        return String.format(resultaat.toString(), verwerking, hoogsteMeldingsniveau);
    }

    /**
     * Vervangt een (dynamische) waarde tussen alle tags/nodes met de opgegeven naam met een "dummy" waarde in de opgegeven xml.
     *
     * @param xml         De xml waarin de waarde tussen de opgegeven nodes moet worden vervangen.
     * @param nodeNaam    Naam van de node.
     * @param dummyWaarde de "dummy" waarde die gezet moet worden.
     * @return De xml met de waardes omgezet naar een "dummy" waarde.
     */
    protected String vervangDynamischeWaardeVoorDummyWaarde(final String xml, final String nodeNaam,
        final Object dummyWaarde)
    {
        final String startNode = "<brp:" + nodeNaam + ">";
        final String eindNode = "</brp:" + nodeNaam + ">";
        return xml.replaceAll(startNode + "[^<]*" + eindNode, startNode + dummyWaarde + eindNode);
    }

    protected void bouwAntwoordElement(final StringBuilder stringBuilder, final String bestandsNaam) {
        InputStream is = getClass().getResourceAsStream(bestandsNaam);

        if (is == null) {
            Assert.fail(String.format("Kan bestand '%s' niet lezen/vinden voor inlezen verwacht resultaat",
                bestandsNaam));
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

    protected BerichtParametersGroepBericht maakParametersVoorAntwoordBericht(final Verwerkingswijze verwerkingswijze) {
        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        if (verwerkingswijze != null) {
            parameters.setVerwerkingswijze(new VerwerkingswijzeAttribuut(verwerkingswijze));
        }
        return parameters;
    }

    protected BerichtParametersGroepBericht maakParametersVoorSynchronisatieBericht(final String abonnementNaam) {
        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setLeveringsautorisatie(new LeveringsautorisatieAttribuut(
            TestLeveringsautorisatieBuilder.maker().metNaam(abonnementNaam).metPopulatiebeperking("")
                .metProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN)
                .metDatumIngang(new DatumAttribuut(20130101))
                .maak()));
        parameters.setSoortSynchronisatie(new SoortSynchronisatieAttribuut(SoortSynchronisatie.VOLLEDIGBERICHT));
        return parameters;
    }


    protected BerichtStuurgegevensGroepBericht maakStuurgegevensVoorAntwoordBericht(final String referentieNummer,
        final String crossReferentieNummer)
    {
        final BerichtStuurgegevensGroepBericht stuurgegevensGroep = new BerichtStuurgegevensGroepBericht();
        stuurgegevensGroep.setZendendeSysteem(new SysteemNaamAttribuut("BRP"));
        stuurgegevensGroep.setZendendePartij(PARTIJ_MINISTERIE_BZK);
        stuurgegevensGroep.setReferentienummer(new ReferentienummerAttribuut(referentieNummer));
        stuurgegevensGroep.setCrossReferentienummer(new ReferentienummerAttribuut(crossReferentieNummer));
        stuurgegevensGroep.setDatumTijdVerzending(DatumTijdAttribuut.nu());

        return stuurgegevensGroep;
    }

    protected BerichtStuurgegevensGroepBericht maakStuurgegevensVoorSynchronisatieBericht(final String referentieNummer)
    {
        final BerichtStuurgegevensGroepBericht stuurgegevensGroep = new BerichtStuurgegevensGroepBericht();
        stuurgegevensGroep.setZendendeSysteem(new SysteemNaamAttribuut("BRP"));
        stuurgegevensGroep.setZendendePartij(PARTIJ_MINISTERIE_BZK);
        stuurgegevensGroep.setOntvangendeSysteem(new SysteemNaamAttribuut("AfnemerSysteem"));
        stuurgegevensGroep.setOntvangendePartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM);
        stuurgegevensGroep.setReferentienummer(new ReferentienummerAttribuut(referentieNummer));
        stuurgegevensGroep.setDatumTijdVerzending(DatumTijdAttribuut.nu());

        return stuurgegevensGroep;
    }

    protected BerichtResultaatGroepBericht maakResultaatVoorAntwoordBericht(final SoortMelding hoogsteMeldingNiveau,
        final Verwerkingsresultaat verwerkingsresultaat, final Bijhoudingsresultaat bijhoudingResultaat)
    {
        final BerichtResultaatGroepBericht resultaatGroep = new BerichtResultaatGroepBericht();
        final SoortMelding hoogsteNiveau;
        if (null == hoogsteMeldingNiveau) {
            hoogsteNiveau = SoortMelding.GEEN;
        } else {
            hoogsteNiveau = hoogsteMeldingNiveau;
        }

        resultaatGroep.setHoogsteMeldingsniveau(new SoortMeldingAttribuut(hoogsteNiveau));
        if (bijhoudingResultaat != null) {
            resultaatGroep.setBijhouding(new BijhoudingsresultaatAttribuut(bijhoudingResultaat));
        }
        resultaatGroep.setVerwerking(new VerwerkingsresultaatAttribuut(verwerkingsresultaat));
        return resultaatGroep;
    }

    protected List<BerichtMeldingBericht> maakBerichtMeldingenBericht(final Melding... meldingen) {
        List<BerichtMeldingBericht> meldingBerichten = new ArrayList<>();
        for (Melding melding : meldingen) {
            BerichtMeldingBericht meldingBericht = new BerichtMeldingBericht(melding);
            meldingBerichten.add(meldingBericht);
        }
        return meldingBerichten;
    }

    public PersoonBericht bouwBijgehoudenPersoon(final Integer bsn) {
        final PersoonBericht persoon = new PersoonBericht();
        if (null != bsn) {

            // Groep identificatie nummers
            persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
            persoon.getIdentificatienummers().setBurgerservicenummer(new BurgerservicenummerAttribuut(bsn));
        }
        return persoon;
    }

}
