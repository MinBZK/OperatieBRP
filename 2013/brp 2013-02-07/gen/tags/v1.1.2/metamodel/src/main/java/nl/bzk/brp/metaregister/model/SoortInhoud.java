/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.model;

import java.util.HashMap;
import java.util.Map;


/**
 * Enumeratie voor de soort inhoud van de gegevens van een object, waarbij dit met name iets zegt over hoe de gegevens
 * kunnen worden aangepast en in welke lifecylce.
 */
public enum SoortInhoud {

    /** Dynamische gegevens kunnen altijd worden aangepast en worden continu aangepast door de applicatie zelf. */
    DYNAMISCH('D'),
    /**
     * Statische gegevens kunnen altijd worden aangepast, maar dit gebeurd veel minder vaak en ook buiten de applicatie
     * om (door bijvoorbeeld beheer tooling).
     */
    STATISCH('S'),
    /** Gegevens die alleen bij een nieuwe release kunnen worden aangepast. */
    ENUMERATIE('X');

    private static final Map<Character, SoortInhoud> SOORTEN = new HashMap<Character, SoortInhoud>();

    private final Character code;

    static {
        for (SoortInhoud elementSoort : SoortInhoud.values()) {
            SOORTEN.put(elementSoort.code, elementSoort);
        }
    }

    /**
     * Standaard constructor die direct de code initialiseert.
     *
     * @param code de code behorende bij de element soort.
     */
    private SoortInhoud(final Character code) {
        this.code = code;
    }

    /**
     * Geeft aan of de inhoud van een element van deze soort is.
     *
     * @param element het element dat moet worden gecontroleerd.
     * @return een boolean die aangeeft of de inhoud van het element van deze soort is of niet.
     */
    public boolean isSoort(final Element element) {
        return this == getSoort(element);
    }

    /**
     * Retourneert de soort van de inhoud van het element.
     *
     * @param element het element waarvoor de soort van de inhoud moet worden bepaald.
     * @return de soort van de inhoud van het element.
     */
    public static SoortInhoud getSoort(final Element element) {
        return SOORTEN.get(element.getSoortInhoud());
    }

}
