/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Source level annotatie, die toegepast wordt om te documenteren welke {@link nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel}s worden geimplementeerd
 * door een stuk code.
 * <p/>
 * We gebruiken deze annotatie om terug te kunnen vinden waar de logica hier hoort bij een Regel wordt uitgevoerd.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD })
public @interface Regels {

    /**
     * De {@link nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel}(s) die worden toegepast.
     */
    Regel[] value() default { };

    /**
     * Eventuele beschrijving, indien nodig.
     */
    String beschrijving() default "";
}
