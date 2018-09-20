/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht;

import java.util.List;

import javax.validation.Valid;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.objecttype.bericht.basis.AbstractActieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;


/**
 *
 */
@SuppressWarnings("serial")
public class ActieBericht extends AbstractActieBericht implements Actie {

    @Valid
    private List<RootObject> rootObjecten;

    @Override
    public List<RootObject> getRootObjecten() {
        return rootObjecten;
    }

    /**
     * Zet de persoon of relatie rootobject in deze actie naar het eerste object uit de opgegeven lijst.
     *
     * @param rootObjecten de lijst met rootobjecten.
     */
    public void setRootObjecten(final List<RootObject> rootObjecten) {
        this.rootObjecten = rootObjecten;
    }
}
