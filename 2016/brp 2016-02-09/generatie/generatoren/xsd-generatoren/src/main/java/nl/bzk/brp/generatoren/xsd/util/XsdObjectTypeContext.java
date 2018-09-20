/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.xsd.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.common.InverseAssociatie;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jdom2.Element;

/**
 * Deze context klasse ondersteund het opbouwen van alle elementen voor een object type.
 */
public class XsdObjectTypeContext {

    private ObjectType objectType;
    private List<Groep> gevondenGroepen;
    private List<InverseAssociatie> gevondenInverseAssociaties;
    private List<Pair<String, Element>> gevondenElementen;

    /**
     * Een nieuw context object.
     *
     * @param objectType het object type
     */
    public XsdObjectTypeContext(final ObjectType objectType) {
        this.objectType = objectType;
        this.gevondenGroepen = new ArrayList<>();
        this.gevondenInverseAssociaties = new ArrayList<>();
        this.gevondenElementen = new ArrayList<>();
    }

    public ObjectType getObjectType() {
        return this.objectType;
    }

    public List<Groep> getGevondenGroepen() {
        return this.gevondenGroepen;
    }

    public List<InverseAssociatie> getGevondenInverseAssociaties() {
        return this.gevondenInverseAssociaties;
    }

    /**
     * Voeg een groep met bijbehorend elementen toe.
     * De groep zelf wordt NIET opgenomen als gevonden groep.
     *
     * @param groep de groep
     * @param elementen de elementen
     */
    public void voegGroepElementenToe(final Groep groep, final List<Element> elementen) {
        for (Element element : elementen) {
            this.voegGroepElementToe(groep, element, false);
        }
    }

    /**
     * Voeg een groep met bijbehorend element toe.
     *
     * @param groep de groep
     * @param element het element
     * @param behoortAlsLosstaandType of de groep als aparte entiteit onder het object type zit
     */
    public void voegGroepElementToe(final Groep groep, final Element element,
            final boolean behoortAlsLosstaandType)
    {
        // Sluit groepen uit met een override van het type (buiten de gegenereerde XSD gedefinieerd).
        if (behoortAlsLosstaandType && StringUtils.isBlank(groep.getXsdType())) {
            this.gevondenGroepen.add(groep);
        }
        this.gevondenElementen.add(new ImmutablePair<String, Element>(groep.getIdentCode(), element));
    }

    /**
     * Voeg een inverse associatie met bijbehorend element toe.
     *
     * @param inverseAssociatie de inverse associatie
     * @param element het element
     */
    public void voegInverseAssociatieToe(final InverseAssociatie inverseAssociatie, final Element element) {
        this.gevondenInverseAssociaties.add(inverseAssociatie);
        this.gevondenElementen.add(new ImmutablePair<String, Element>(inverseAssociatie.getIdentCode(), element));
    }

    /**
     * Voeg een los element toe.
     *
     * @param sorteerNaam de naam tbv het sorteren
     * @param element het element
     */
    public void voegLosElementToe(final String sorteerNaam, final Element element) {
        this.gevondenElementen.add(new ImmutablePair<String, Element>(sorteerNaam, element));
    }

    /**
     * Haal de toe te voegen elementen op. Dit is een combinatie van groepen en inverse associaties.
     * Deze lijst wordt gesorteerd op de eventueel aanwezige xsd sortering.
     *
     * @return de elementen
     */
    public List<Element> getToeTeVoegenElementen() {
        this.sorteer();
        List<Element> toeTeVoegenElementen = new ArrayList<>();
        for (Pair<String, Element> gevondenElement : this.gevondenElementen) {
            toeTeVoegenElementen.add(gevondenElement.getRight());
        }
        return toeTeVoegenElementen;
    }

    /**
     * Sorteer de elementen op de eventueel aanwezige xsd sortering.
     */
    private void sorteer() {
        // Als er een specifieke XSD sortering 'override' aanwezig is,
        // gebruik die om de sortering van de groepen en inverse associaties aan te passen.
        if (this.objectType.getXsdSortering() != null) {
            // Geef een fout als er elementen wel gevonden zijn, maar die niet in de sortering voorkomen.
            Iterator<Pair<String, Element>> iter = this.gevondenElementen.iterator();
            while (iter.hasNext()) {
                String identCode = iter.next().getLeft();
                if (this.objectType.getXsdSortering().toUpperCase().indexOf(identCode.toUpperCase()) == -1) {
                    throw new IllegalStateException("Kind element '" + identCode + "' gevonden voor "
                            + "object type '" + objectType.getNaam() + "' dat niet in de xsd sortering voorkomt.");
                }
            }

            // Sorteer op de custom xsd sortering aan de hand van de index van de naam in de sortering string.
            Collections.sort(this.gevondenElementen, new Comparator<Pair<String, Element>>() {
                @Override
                public int compare(final Pair<String, Element> paar1, final Pair<String, Element> paar2) {
                    return objectType.getXsdSortering().toUpperCase().indexOf(paar1.getLeft().toUpperCase())
                            - objectType.getXsdSortering().toUpperCase().indexOf(paar2.getLeft().toUpperCase());
                }
            });
        }
    }

}
