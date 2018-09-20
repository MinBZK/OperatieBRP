/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.statisch;

import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.TechnischIdKlein;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;

/**
 * Adellijke titels.
 *
 */
public class AdellijkeTitel extends AbstractStatischObjectType {
    /* Addelijke titel heeft een id, code, mannelijk en vrouwelijke aanschrijving */
    private TechnischIdKlein adellijkeTitelID;
    private Naam naamMannelijk;
    private Naam naamVrouwelijk;
    private AdellijkeTitelCode adellijkeTitelCode;

    public TechnischIdKlein getAdellijkeTitelID() {
        return adellijkeTitelID;
    }

    public Naam getNaamMannelijk() {
        return naamMannelijk;
    }

    public Naam getNaamVrouwelijk() {
        return naamVrouwelijk;
    }

    public AdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }
}
