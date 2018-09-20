/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonImmigratieGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;


/**
 * .
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonImmigratieGroepBericht extends AbstractGroepBericht implements PersoonImmigratieGroepBasis {
    private Land landVanwaarGevestigd;
    private Datum datumVestigingInNederland;

    @Override
    public Land getLandVanwaarGevestigd() {
        return landVanwaarGevestigd;
    }

    @Override
    public Datum getDatumVestigingInNederland() {
        return datumVestigingInNederland;
    }

    public void setLandVanwaarGevestigd(final Land landVanwaarGevestigd) {
        this.landVanwaarGevestigd = landVanwaarGevestigd;
    }

    public void setDatumVestigingInNederland(final Datum datumVestigingInNederland) {
        this.datumVestigingInNederland = datumVestigingInNederland;
    }
}
