/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.actueel.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.basis.Onderzoekbaar;
import nl.bzk.brp.model.groep.logisch.basis.PersoonGeslachtsAanduidingGroepBasis;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonGeslachtsAanduidingGroep;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsAanduidingActGroepModel extends AbstractPersoonGeslachtsAanduidingGroep
        implements Onderzoekbaar
{

    @Transient
    private boolean        inOnderzoek;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonGeslachtsAanduidingActGroepModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonGeslachtsAanduidingGroepBasis PersoonGeslachtsAanduidingGroepBasis
     */
    protected AbstractPersoonGeslachtsAanduidingActGroepModel(
            final PersoonGeslachtsAanduidingGroepBasis persoonGeslachtsAanduidingGroepBasis)
    {
        super(persoonGeslachtsAanduidingGroepBasis);
    }

    @Override
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

}
