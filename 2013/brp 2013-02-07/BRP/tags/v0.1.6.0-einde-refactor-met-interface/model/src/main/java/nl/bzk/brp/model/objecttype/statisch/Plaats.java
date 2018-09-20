/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.statisch;

import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.TechnischIdMiddel;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;

/**
 * Woonplaats.
 */
public class Plaats extends AbstractStatischObjectType {

    private TechnischIdMiddel id;

    private PlaatsCode code;

    private Naam naam;

    public TechnischIdMiddel getId() {
        return id;
    }

    public PlaatsCode getCode() {
        return code;
    }

    public Naam getNaam() {
        return naam;
    }
}
