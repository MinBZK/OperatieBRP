/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Een indicatie dat een class overeenkomst met een verzameling XML elementen. De enum class die wordt meegegeven MOET
 * een methode hebben met de volgende signature: <code>public static List<String> getElementNamen()</code>. Deze wordt
 * gebruikt om te bepalen welke element namen bij dit type horen. Daarnaast dient deze enum ook de volgende methode te
 * hebben: <code>public String getElementNaam()</code>. Deze methode wordt gebruikt door de Writer om te bepalen welke
 * elementnaam moet worden gebruikt bij het schrijven van een instantie van het model naar XML.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface XmlElementen {

    /** De methode naam op het enum type dat de element naam teruggeeft als String waarde. */
    String ELEMENT_NAAM_METHOD = "getElementNaam";

    /**
     * De enum class met daarin de lijst met mogelijke element namen in de XML.
     *
     * @return enumType
     */
    Class<?> enumType();

    /**
     * De methode naam die een waarde van de enum class teruggeeft om te bepalen welk soort bij een instantie hoort.
     *
     * @return soort methode
     */
    String methode();

}
