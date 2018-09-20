/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonGeslachtsaanduidingGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;


/**
 * Implementatie voor geslachtsaanduiding groep.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsaanduidingGroepBericht extends AbstractGroepBericht implements
    PersoonGeslachtsaanduidingGroepBasis
{

    private Geslachtsaanduiding geslachtsaanduiding;

    @Override
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
        this.geslachtsaanduiding = geslachtsaanduiding;
    }
}
