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
 * Een indicatie dat een parameter overeenkomt met een child XML element.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XmlChild {
    /**
     * De naam van het XML element.
     * 
     * @return de naam
     */
    String naam() default "";

    /**
     * Bepaald de volgorde waarin de xml velden worden aangeboden aan de constructor en worden weggeschreven in de XML.
     * De velden worden gesorteerd op basis van de volgorde integer van laag naar hoog.
     *
     * @return de volgorde van dit xml veld
     */
    int volgorde() default 0;
}
