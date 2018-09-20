/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.groep.impl.gen;

import nl.bzk.brp.model.groep.interfaces.gen.PersoonGeslachtsAanduidingGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.GeslachtsAanduiding;
import nl.bzk.brp.web.AbstractGroepWeb;

/**
 * Implementatie voor geslachtsaanduiding groep.
 */
public abstract class AbstractPersoonGeslachtsAanduidingGroepWeb extends AbstractGroepWeb
        implements PersoonGeslachtsAanduidingGroepBasis
{
    private GeslachtsAanduiding geslachtsAanduiding;

    @Override
    public GeslachtsAanduiding getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }
}
