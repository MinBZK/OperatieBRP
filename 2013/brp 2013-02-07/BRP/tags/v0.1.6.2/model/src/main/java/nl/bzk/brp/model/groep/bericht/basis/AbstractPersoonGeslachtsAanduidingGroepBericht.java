/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonGeslachtsAanduidingGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.GeslachtsAanduiding;


/**
 * Implementatie voor geslachtsaanduiding groep.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsAanduidingGroepBericht extends AbstractGroepBericht implements
        PersoonGeslachtsAanduidingGroepBasis
{

    private GeslachtsAanduiding geslachtsAanduiding;

    @Override
    public GeslachtsAanduiding getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    public void setGeslachtsAanduiding(final GeslachtsAanduiding geslachtsAanduiding) {
        this.geslachtsAanduiding = geslachtsAanduiding;
    }
}
