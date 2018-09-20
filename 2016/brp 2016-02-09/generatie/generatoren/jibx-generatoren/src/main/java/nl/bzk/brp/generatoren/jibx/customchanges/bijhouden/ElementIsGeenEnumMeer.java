/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.customchanges.bijhouden;

import nl.bzk.brp.generatoren.jibx.customchanges.AbstractHandmatigeVerwijzing;
import nl.bzk.brp.generatoren.jibx.customchanges.HandmatigeWijziging;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;

/**
 * Vervang de element enum door een type
 */
public class ElementIsGeenEnumMeer extends AbstractHandmatigeVerwijzing implements HandmatigeWijziging {

    private static final String CLASS = "class";
    private static final String ABSTRACT = "abstract";
    private static final String TYPE_NAME = "type-name";
    private static final String STRUCTURE = "structure";
    private static final String NAME = "name";
    private static final String MAP_AS = "map-as";
    private static final String FIELD = "field";
    private static final String MAPPING = "mapping";
    private static final String TRUE = "true";

    @Override
    public void voerUit(final Document rootElementBinding)
            throws XPathFactoryConfigurationException, XPathExpressionException
    {
        opschonenAbstractGegevenInOnderzoekBericht(rootElementBinding);
        voegElementToeAlsElementAttribuut(rootElementBinding);
        voegNieuweElementDefinitiesToe(rootElementBinding);
    }

    /**
     * Handmatige wijziging, Element is geen Enum meer
     * @param rootElementBinding de root waaraan de elementen worden toegevoegd
     */
    private void voegNieuweElementDefinitiesToe(final Document rootElementBinding) {
        final Element bindingElement = zoekElement(rootElementBinding, "/binding");
        if (bindingElement != null) {
            bindingElement.addContent(createElementForAbstractElementOnderzoek());
            bindingElement.addContent(createElementForElementOnderzoek());
            bindingElement.addContent(createElementForElementAttribuutOnderzoek());
        }
    }

    /**
     * Maak de volgende structuur aan
     <mapping class="nl.bzk.brp.model.algemeen.stamgegeven.kern.AbstractElement" abstract="true" type-name="AbstractElementOnderzoek">
     <structure name="elementNaam" map-as="NaamEnumeratiewaardeLangAttribuut" field="naam"/>
     </mapping>
     * @return de node voor de mapping
     */
    private Element createElementForAbstractElementOnderzoek() {
        final Element mapping = new Element(MAPPING)
                .setAttribute(CLASS, "nl.bzk.brp.model.algemeen.stamgegeven.kern.AbstractElement")
                .setAttribute(ABSTRACT, TRUE)
                .setAttribute(TYPE_NAME, "AbstractElementOnderzoek");
        mapping.addContent(new Element(STRUCTURE).setAttribute(NAME, "elementNaam").setAttribute(MAP_AS, "NaamEnumeratiewaardeLangAttribuut").setAttribute(FIELD, "naam"));
        return mapping;
    }

    /**
     * Maak de volgende structuur aan
     *
     <mapping class="nl.bzk.brp.model.algemeen.stamgegeven.kern.Element" abstract="true" type-name="ElementOnderzoek">
     <structure map-as="AbstractElementOnderzoek"/>
     </mapping>
     * @return de node voor de mapping
     */
    private Element createElementForElementOnderzoek() {
        final Element mapping = new Element(MAPPING)
                .setAttribute(CLASS, "nl.bzk.brp.model.algemeen.stamgegeven.kern.Element")
                .setAttribute(ABSTRACT, TRUE)
                .setAttribute(TYPE_NAME, "ElementOnderzoek");
        mapping.addContent(new Element(STRUCTURE).setAttribute(MAP_AS, "AbstractElementOnderzoek"));
        return mapping;
    }

    /**
     * Maak de volgende structuur aan
     *      <mapping class="nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut" abstract="true" type-name="ElementAttribuutOnderzoek">
     <structure map-as="ElementOnderzoek" set-method="setWaarde" get-method="getWaarde" />
     </mapping>
     * @return de node voor de mapping
     */
    private Element createElementForElementAttribuutOnderzoek() {
        final Element mapping = new Element(MAPPING)
                .setAttribute(CLASS, "nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut")
                .setAttribute(ABSTRACT, TRUE)
                .setAttribute(TYPE_NAME, "ElementAttribuutOnderzoek");
        mapping.addContent(new Element(STRUCTURE).setAttribute(MAP_AS, "ElementOnderzoek").setAttribute("set-method", "setWaarde").setAttribute("get-method", "getWaarde"));
        return mapping;
    }

    /**
     * Voeg node toe aan AbstractGegevenInOnderzoekBericht
     * <structure field="element" usage="optional" map-as="ElementAttribuutOnderzoek"/>
     */
    private void voegElementToeAlsElementAttribuut(final Document rootElementBinding) {
        final Element bindingElement = zoekElement(rootElementBinding, "/binding/mapping[@type-name='AbstractGegevenInOnderzoekBericht']");
        if (bindingElement != null) {
            final Element fieldStructure = new Element(STRUCTURE)
                    .setAttribute(FIELD, "element")
                    .setAttribute("usage", "optional")
                    .setAttribute(MAP_AS, "ElementAttribuutOnderzoek");
            bindingElement.addContent(fieldStructure);
        }
    }

    private void opschonenAbstractGegevenInOnderzoekBericht(final Document rootElementBinding) {
        removeNode(rootElementBinding, "/binding/mapping[@type-name='AbstractGegevenInOnderzoekBericht']/value[@name='elementNaam']");
        removeNode(rootElementBinding, "/binding/mapping[@type-name='AbstractGegevenInOnderzoekBericht']/structure[@field='objectSleutel']");
    }
}
