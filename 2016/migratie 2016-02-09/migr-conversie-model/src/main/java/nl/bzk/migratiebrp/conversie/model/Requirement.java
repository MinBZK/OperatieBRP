/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Deze annotatie dient te worden gebruikt om de verantwoording van de code te verbeteren. Code implementeert
 * requirements, deze moeten explicitiet worden genoemd in de javadoc van de class of methode zodat men kan herleiden
 * welke eisen in de code zijn geimplementeerd.
 * 
 * 
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Requirement {

    /**
     * Een unieke verwijzing naar een gedocumenteerde eis die door de code wordt geimplementeerd.
     */
    Requirements[] value();
}
