/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.berekening;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.OperatorType;

/**
 * Annotatie voor berekeningen.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@interface BerekeningConfig {


    /**
     * @return het type operator dat gebruikt wordt
     */
    OperatorType operator();

    /**
     * @return het type linker expressie in de berekening
     */
    ExpressieType typeLinks();

    /**
     * @return de type rechterexpressie in de berekening
     */
    ExpressieType typeRechts();
}
