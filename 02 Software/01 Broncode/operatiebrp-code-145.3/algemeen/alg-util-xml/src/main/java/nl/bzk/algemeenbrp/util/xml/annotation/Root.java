/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Geeft een root element aan.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Root {

    /**
     * Naam voor het element.
     *
     * Indien geen naam is opgegeven wordt de Java class van het geannoteerde gegeven gebruikt om
     * een naam te genereren.
     */
    String name() default "";

    /**
     * Ongebruikt: enkel voor compatibiliteit met SimpleXML.
     */
    boolean strict() default false;
}
