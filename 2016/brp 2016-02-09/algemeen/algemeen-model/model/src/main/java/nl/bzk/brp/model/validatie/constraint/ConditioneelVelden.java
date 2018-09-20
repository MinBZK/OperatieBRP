/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Definieert verschillende <code>@ConditioneelVerplichtVeld</code> annotations voor een type.
 *
 * @see nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichtVeld
 */
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConditioneelVelden {

    /**
     * Lijst van @ConditioneelVerplichtVeld annotaties.
     */
    ConditioneelVeld[] value();
}
