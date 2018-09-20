/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;

/**
 * Deze enum representeert een unieke verwijzing naar de BRP stamtabel 'Wijze gebruik geslachtsnaam'.
 * 
 * Dit is een enum omdat het hier een statische stamtabel betreft.
 */
public enum BrpWijzeGebruikGeslachtsnaamCode implements BrpAttribuut {

    /**
     * Eigen geslachtsnaam.
     */
    E,
    /**
     * Geslachtsnaam echtgenoot/geregistreerd partner.
     */
    P,
    /**
     * Geslachtsnaam echtgenoot/geregistreerd partner voor eigen geslachtsnaam.
     */
    V,
    /**
     * Geslachtsnaam echtgenoot/geregistreerd partner na eigen geslachtsnaam.
     */
    N;
}
