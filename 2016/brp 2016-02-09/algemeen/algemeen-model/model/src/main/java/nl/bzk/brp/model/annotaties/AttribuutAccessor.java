/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.annotaties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotatie voor functies die een bepaald attribuut retourneren van een groep of objecttype. Dit zijn de zogenaamde getters in C/D laag (his-)
 * entiteiten.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AttribuutAccessor {

    /**
     * dbobjectid, unieke identificatie van het attribuut dat wordt geretourneerd.
     */
    int dbObjectId();
}
