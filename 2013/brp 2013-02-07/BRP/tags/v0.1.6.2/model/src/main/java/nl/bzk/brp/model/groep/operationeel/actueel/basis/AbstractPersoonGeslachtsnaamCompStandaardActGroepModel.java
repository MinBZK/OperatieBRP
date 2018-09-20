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
import nl.bzk.brp.model.groep.logisch.basis.PersoonGeslachtsnaamCompStandaardGroepBasis;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonGeslachtsnaamCompStandaardGroep;


/**
 * Implementatie standaard groep object type Persoon Geslachtsnaam component.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamCompStandaardActGroepModel extends
        AbstractPersoonGeslachtsnaamCompStandaardGroep implements Onderzoekbaar
{

    @Transient
    private boolean        inOnderzoek;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonGeslachtsnaamCompStandaardActGroepModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonGeslachtsnaamCompStandaardGroepBasis PersoonGeslachtsnaamCompStandaardGroepBasis
     */
    protected AbstractPersoonGeslachtsnaamCompStandaardActGroepModel(
            final PersoonGeslachtsnaamCompStandaardGroepBasis persoonGeslachtsnaamCompStandaardGroepBasis)
    {
        super(persoonGeslachtsnaamCompStandaardGroepBasis);
    }

    @Override
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

}
