/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.objecttype.impl.gen;

import nl.bzk.brp.model.groep.interfaces.usr.RelatieStandaardGroep;
import nl.bzk.brp.model.objecttype.interfaces.gen.RelatieBasis;
import nl.bzk.brp.model.objecttype.statisch.SoortRelatie;
import nl.bzk.brp.web.AbstractObjectTypeWeb;

/**
 * Implementatie voor objecttyp relatie.
 */
public abstract class AbstractRelatieWeb extends AbstractObjectTypeWeb implements RelatieBasis {

    private SoortRelatie soort;
    private RelatieStandaardGroep gegevens;

    @Override
    public SoortRelatie getSoort() {
        return soort;
    }

    @Override
    public RelatieStandaardGroep getGegevens() {
        return gegevens;
    }
}
