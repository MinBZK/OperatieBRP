/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.historisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonGeslachtsnaamCompStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonGeslachtsnaamComponentHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamComponentModel;


/**
 * User implementatie van PersoonGeslachtsnaamComponentHisMdl.
 *
 */
@Entity
@Table(schema = "kern", name = "his_persgeslnaamcomp")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonGeslachtsnaamComponentHisModel extends AbstractPersoonGeslachtsnaamComponentHisModel {

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private PersoonGeslachtsnaamComponentHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonGeslachtsnaamCompStandaardGroep .
     * @param geslachtsnaamComponentModel .
     */
    public PersoonGeslachtsnaamComponentHisModel(
            final AbstractPersoonGeslachtsnaamCompStandaardGroep persoonGeslachtsnaamCompStandaardGroep,
            final PersoonGeslachtsnaamComponentModel geslachtsnaamComponentModel)
    {
        super(persoonGeslachtsnaamCompStandaardGroep, geslachtsnaamComponentModel);
    }

    @Override
    public PersoonGeslachtsnaamComponentHisModel kopieer() {
        return new PersoonGeslachtsnaamComponentHisModel(this, getPersoonGeslachtsnaamcomponent());
    }
}
