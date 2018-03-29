/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Geeft aan dat een gegeven opgenomen moet worden als element lijst.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementList {

    /**
     * Naam voor het element (wordt niet gebruikt indien {@code inline true} is).
     *
     * Indien geen naam is opgegeven wordt de Java class van het geannoteerde gegeven gebruikt om
     * een naam te genereren.
     */
    String name() default "";

    /**
     * Naam voor het element voor de afzonderlijke rijen.
     *
     * Indien geen naam is opgegeven wordt de Java class van het geannoteerde gegeven gebruikt om
     * een naam te genereren.
     */
    String entry() default "";

    /**
     * Type van de afzonderlijke rijen.
     */
    Class<?> type();

    /**
     * Indien inline {@code true} geeft dan wordt geen omliggend element om de afzonderlijke rijen
     * gegenereerd.
     */
    boolean inline() default false;

    /**
     * Ongebruikt: enkel voor compatibiliteit met SimpleXML.
     */
    boolean required() default false;

}
