/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 02: Ouder 1.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3Ouder1Precondities extends Lo3OuderPrecondities {

    /**
     * Default constructor.
     */
    public Lo3Ouder1Precondities() {
        super(Ouder.OUDER_1);
    }
}
