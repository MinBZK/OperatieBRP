/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.model.gedeeld.SoortIndicatie;
import nl.bzk.brp.model.logisch.PersoonIndicatie;
import org.jibx.runtime.IAliasable;
import org.jibx.runtime.IMarshaller;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshaller;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.impl.MarshallingContext;
import org.jibx.runtime.impl.UnmarshallingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Specifieke marshaller/unmarshaller voor de {@link nl.bzk.brp.model.logisch.PersoonIndicatie} instanties in de
 * {@link nl.bzk.brp.model.logisch.Persoon} class. Deze mapper class verzorgd zowel de JiBX marshalling als
 * unmarshalling van de {@link nl.bzk.brp.model.logisch.PersoonIndicatie} van en naar XML. Hierbij wordt bij de
 * marshalling voor elke indicatie, de soort van de indicatie gebruikt als de naam van de indicatie node/element en
 * wordt binnen het indicatie element de verdere content van de indicatie getoond, zoals de waarde en
 * eventuele historische data. Bij de unmarshalling wordt dus het indicatie element gebruikt om een nieuwe indicatie
 * aan de persoon toe te voegen, waarbij de naam van het indicatie element wordt gebruikt om het soort van de indicatie
 * te bepalen. Verder zal dan uiteraard de content van het indicatie element gebruikt worden om de waarde van de
 * indicatie te zetten.
 * </p>
 * <p>Het netto effect van deze marshaller/unmarshaller is dat de XML structuur in het volgende formaat zal zijn:</p>
 * <p/>
 * <pre>
 *   &lt;onderCuratele>
 *     &lt;waarde>J&lt;/waarde>
 *   &lt;/onderCuratele>
 *
 *   &lt;verstrekkingsbeperking>
 *     &lt;waarde>J&lt;/waarde>
 *   &lt;/verstrekkingsbeperking>
 *
 *   &lt;bezitBuitenlandsReisdocument>
 *     &lt;waarde>N&lt;/waarde>
 *   &lt;/bezitBuitenlandsReisdocument>
 * </pre>
 * <p/>
 */
public class PersoonIndicatieMapper implements IMarshaller, IUnmarshaller, IAliasable {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersoonIndicatieMapper.class);

    private static final String STANDAARD_WAARDE_ELEMENT_NAAM  = "waarde";
    private static final String NAMESPACE_URI                  = "http://www.bprbzk.nl/BRP/0001";
    private static final String WAARDE_POSITIEF                = "J";
    private static final String WAARDE_NEGATIEF                = "N";
    private static final String SOORT_INDICATIE_NAAM_SEPERATOR = "_";

    private static final Map<String, SoortIndicatie> INDICATIENAAM_MAP;

    static {
        INDICATIENAAM_MAP = new HashMap<String, SoortIndicatie>();
        for (SoortIndicatie soortIndicatie : SoortIndicatie.values()) {
            INDICATIENAAM_MAP.put(bepaalIndicatieElementNaam(soortIndicatie), soortIndicatie);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean isExtension(final String mapnaam) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void marshal(final Object obj, final IMarshallingContext marshallingContext) throws JiBXException {
        // controleer de parameters
        if (!(obj instanceof PersoonIndicatie)) {
            LOGGER.error(String.format("Onverwacht (en niet ondersteund) type voor JiBX Marshaller: %s",
                obj.getClass()));
            throw new JiBXException("Niet ondersteund type voor marshaller object");
        } else {
            MarshallingContext context = (MarshallingContext) marshallingContext;
            PersoonIndicatie indicatie = (PersoonIndicatie) obj;

            int namespaceIndex = bepaalNamespaceIndex(context);

            // start met element voor de indicatie
            context.startTagAttributes(namespaceIndex, bepaalIndicatieElementNaam(indicatie.getSoort()));
            context.closeStartContent();

            // Geef de content van het indicatie element
            context.startTagAttributes(namespaceIndex, STANDAARD_WAARDE_ELEMENT_NAAM);
            context.closeStartContent();
            context.content(bepaalWaardeContent(indicatie.getWaarde()));
            context.endTag(namespaceIndex, STANDAARD_WAARDE_ELEMENT_NAAM);

            // sluit het element
            context.endTag(namespaceIndex, bepaalIndicatieElementNaam(indicatie.getSoort()));
        }
    }

    /**
     * Bepaalt en retourneert de namespace index. Hiervoor zal op basis van de standaard namespace van dit element en
     * de aanwezige namespaces in de context de waarde worden bepaalt en geretourneerd. Indien de standaard namespace
     * van het element niet bekend is binnen de context, zal '0' worden geretourneerd.
     *
     * @param context de context waar binnen de marshalling plaats vindt.
     */
    private int bepaalNamespaceIndex(final MarshallingContext context) {
        int namespaceIndex = 0;
        if (context.getNamespaces() != null) {
            String[] namespaces = context.getNamespaces();
            for (int i = 0; i < namespaces.length; i++) {
                if (NAMESPACE_URI.equals(namespaces[i])) {
                    namespaceIndex = i;
                }
            }
        }
        return namespaceIndex;
    }

    /**
     * Bepaalt de waarde die in het bericht getoond moet worden op basis van de content van de waarde van een
     * indicatie. Indien de indicatie waarde <code>TRUE</code> is, dan zal 'J' geretourneerd worden en in alle andere
     * situaties 'N'.
     *
     * @param waarde de waarde van de indicatie.
     * @return de representatie van de waarde van de indicatie.
     */
    private String bepaalWaardeContent(final Boolean waarde) {
        final String resultaat;
        if (Boolean.TRUE.equals(waarde)) {
            resultaat = WAARDE_POSITIEF;
        } else {
            resultaat = WAARDE_NEGATIEF;
        }
        return resultaat;
    }

    /**
     * Bepaalt de element naam voor de marshalling op basis van de soort van de indicatie. De naam van de indicatie
     * soort wordt namelijk gebruikt (in een bepaalde vorm, namelijk CamelCase en zonder underscores) voor de element
     * naam.
     *
     * @param soortIndicatie de soort van de indicatie.
     * @return de te gebruiken element naam voor de soort indicatie.
     */
    private static String bepaalIndicatieElementNaam(final SoortIndicatie soortIndicatie) {
        String enumeratieNaam = soortIndicatie.name().toLowerCase();
        String[] enumeratieNaamElementen = enumeratieNaam.split(SOORT_INDICATIE_NAAM_SEPERATOR);

        StringBuilder indicatieElementNaam = new StringBuilder();
        for (int index = 0; index < enumeratieNaamElementen.length; index++) {
            if (index == 0) {
                indicatieElementNaam.append(enumeratieNaamElementen[index]);
            } else {
                indicatieElementNaam.append(enumeratieNaamElementen[index].substring(0, 1).toUpperCase());
                indicatieElementNaam.append(enumeratieNaamElementen[index].substring(1));
            }
        }
        return indicatieElementNaam.toString();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(final IUnmarshallingContext ctx) throws JiBXException {
        boolean isPresent = false;
        for (String indicatieNaam : INDICATIENAAM_MAP.keySet()) {
            isPresent |= ctx.isAt(NAMESPACE_URI, indicatieNaam);
        }
        return isPresent;
    }

    /** {@inheritDoc} */
    @Override
    public Object unmarshal(final Object obj, final IUnmarshallingContext unmarshallingContext) throws JiBXException {
        UnmarshallingContext context = (UnmarshallingContext) unmarshallingContext;

        // Haal eerst de naam van het element op en gebruik deze als soort van de indicatie
        String soortIndicatieNaam = context.toStart();
        String namespace = context.getElementNamespace();
        SoortIndicatie soortIndicatie = bouwSoortIndicatie(soortIndicatieNaam);

        // Bepaal de waarde van de indicatie door het waarde element uit te lezen en te parseren
        context.parsePastStartTag(namespace, soortIndicatieNaam);
        boolean waarde = unmarshalIndicatieWaarde(context.parseElementText(namespace, STANDAARD_WAARDE_ELEMENT_NAAM));
        context.toEnd();

        // Initialiseer de indicatie op basis van de gevonden waarden voor soort en waarde
        PersoonIndicatie persoonIndicatie = (PersoonIndicatie) obj;
        persoonIndicatie.setSoort(soortIndicatie);
        persoonIndicatie.setWaarde(waarde);

        return persoonIndicatie;
    }

    /**
     * Retourneert de waarde van een indicatie op basis van de opgegeven waarde uit de XML. Indien de waarde in de XML
     * gelijk is aan 'J', dan zal de waarde op <code>true</code> worden gezet; in alle andere gevallen op
     * <code>false</code>.
     *
     * @param waarde de waarde uit de XML en die geunmarshalled moet worden
     * @return een boolean die de waarde van de indicatie voorstelt
     */
    private boolean unmarshalIndicatieWaarde(final String waarde) {
        return WAARDE_POSITIEF.equals(waarde);
    }

    /**
     * Retourneert de {@link SoortIndicatie} op basis van de opgegeven indicatie naam. Indien de naam onbekend is, zal
     * er een exceptie worden gegooid.
     *
     * @param soortIndicatieNaam de naam van de indicatie
     * @return de indicatie soort
     * @throws JiBXException indien de opgegeven soort indicatie niet bekend is en dus niet gemapped kan worden.
     */
    private SoortIndicatie bouwSoortIndicatie(final String soortIndicatieNaam) throws JiBXException {
        if (!INDICATIENAAM_MAP.containsKey((soortIndicatieNaam))) {
            LOGGER.error(String.format("JiBX mapping fout: onbekende indicatie soort (%s)", soortIndicatieNaam));
            throw new JiBXException("Onbekende indicatie soort");
        }
        return INDICATIENAAM_MAP.get(soortIndicatieNaam);
    }
}
