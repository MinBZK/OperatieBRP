/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Geeft aan dat een gegeven opgenomen moet worden als attribuut.
 *
 * Indien een attribuut de naam 'id' heeft dan wordt automatisch het bovenliggende element verwerkt
 * met 'ref's als het element met eenzelfde 'id' al is verwerkt.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {

    /**
     * Naam voor het attribuut.
     *
     * Indien geen naam is opgegeven wordt de Java class van het geannoteerde gegeven gebruikt om
     * een naam te genereren.
     */
    String name() default "";

    /**
     * Ongebruikt: enkel voor compatibiliteit met SimpleXML.
     */
    boolean required() default false;
}
