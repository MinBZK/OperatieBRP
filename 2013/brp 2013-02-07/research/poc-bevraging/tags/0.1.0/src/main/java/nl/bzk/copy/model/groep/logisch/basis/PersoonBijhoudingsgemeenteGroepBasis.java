/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.logisch.basis;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.JaNee;
import nl.bzk.copy.model.basis.Groep;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Partij;

/**
 * Interface voor groep persoon bijhoudingsgemeente.
 */
public interface PersoonBijhoudingsgemeenteGroepBasis extends Groep {

    /**
     * Retourneert de bijhoudingsgemeente.
     *
     * @return Partij.
     */
    Partij getBijhoudingsgemeente();

    /**
     * Retourneert indicatie onverwerkt document aanwezig.
     *
     * @return Ja of Nee.
     */
    JaNee getIndOnverwerktDocumentAanwezig();

    /**
     * Retourneert datum inschrijving in gemeente.
     *
     * @return Datum.
     */
    Datum getDatumInschrijvingInGemeente();
}
