/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

/**
 * Deze annotatie dient te worden gebruikt om de verantwoording van de code te verbeteren. Code gaat uit van bijzondere
 * situaties, deze moeten explicitiet worden genoemd in de javadoc van de class of methode zodat men kan herleiden welke
 * bijzondere situaties in de code zijn geimplementeerd.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface BijzondereSituatie {
    /**
     * Een unieke verwijzing naar een gedocumenteerde bijzondere situatie die door de code wordt geimplementeerd.
     * @return lijst van soort melding codes
     */
    SoortMeldingCode[] value();
}
