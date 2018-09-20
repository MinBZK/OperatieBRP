/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model.annotatie;

import java.util.List;

import nl.bzk.brp.generatoren.java.model.JavaType;

/**
 * Generieke interface voor een annotatie parameter.
 */
public interface AnnotatieParameter extends Cloneable {

    /**
     * Geeft de string representatie terug van de annotatie parameter. Dit om de annotatie te kunnen genereren.
     * @return JPA String voor deze annotatie.
     */
    String getParameterString();

    /**
     * Geef de gebruikte types van deze annotatie parameter terug.
     *
     * @return de gebruikte types
     */
    List<JavaType> getGebruikteTypes();
}
