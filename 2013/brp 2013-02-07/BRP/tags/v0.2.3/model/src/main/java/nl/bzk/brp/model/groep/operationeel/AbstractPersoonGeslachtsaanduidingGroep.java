/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonGeslachtsaanduidingGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;


/**
 * Implementatie voor geslachtsaanduiding groep.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsaanduidingGroep extends AbstractGroep implements
    PersoonGeslachtsaanduidingGroepBasis
{

    @Column(name = "geslachtsaand")
    @Enumerated
    private Geslachtsaanduiding geslachtsaanduiding;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonGeslachtsaanduidingGroep() {

    }

    /**
     * .
     *
     * @param persoonGeslachtsaanduidingGroepBasis PersoonGeslachtsaanduidingGroepBasis
     */
    protected AbstractPersoonGeslachtsaanduidingGroep(
        final PersoonGeslachtsaanduidingGroepBasis persoonGeslachtsaanduidingGroepBasis)
    {
        geslachtsaanduiding = persoonGeslachtsaanduidingGroepBasis.getGeslachtsaanduiding();
    }

    @Override
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }
}
