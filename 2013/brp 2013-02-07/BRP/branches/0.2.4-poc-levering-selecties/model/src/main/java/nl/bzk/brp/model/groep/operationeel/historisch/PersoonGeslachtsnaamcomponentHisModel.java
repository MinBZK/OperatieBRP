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

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonGeslachtsnaamcomponentHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;


/**
 * User implementatie van PersoonGeslachtsnaamcomponentHis.
 *
 */
@Entity
@Table(schema = "kern", name = "his_persgeslnaamcomp")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonGeslachtsnaamcomponentHisModel extends AbstractPersoonGeslachtsnaamcomponentHisModel {

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    public PersoonGeslachtsnaamcomponentHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonGeslachtsnaamcomponentGroep .
     * @param geslachtsnaamcomponentModel .
     */
    public PersoonGeslachtsnaamcomponentHisModel(
        final AbstractPersoonGeslachtsnaamcomponentStandaardGroep persoonGeslachtsnaamcomponentGroep,
        final PersoonGeslachtsnaamcomponentModel geslachtsnaamcomponentModel)
    {
        super(persoonGeslachtsnaamcomponentGroep, geslachtsnaamcomponentModel);
    }

    @Override
    public PersoonGeslachtsnaamcomponentHisModel kopieer() {
        return new PersoonGeslachtsnaamcomponentHisModel(this, getPersoonGeslachtsnaamcomponent());
    }
}
