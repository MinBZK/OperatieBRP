/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel.historisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.copy.model.groep.operationeel.AbstractPersoonInschrijvingGroep;
import nl.bzk.copy.model.groep.operationeel.historisch.basis.AbstractPersoonInschrijvingHisModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;


/**
 * User implementatie van PersoonInschrijvingHis.
 */
@Entity
@Table(schema = "kern", name = "his_persinschr")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonInschrijvingHisModel extends AbstractPersoonInschrijvingHisModel {

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private PersoonInschrijvingHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonInschrijvingGroep .
     * @param persoonModel             .
     */
    public PersoonInschrijvingHisModel(
            final AbstractPersoonInschrijvingGroep persoonInschrijvingGroep,
            final PersoonModel persoonModel)
    {
        super(persoonInschrijvingGroep, persoonModel);
    }

}
