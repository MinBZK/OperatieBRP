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

import nl.bzk.copy.model.groep.operationeel.AbstractPersoonIndicatieStandaardGroep;
import nl.bzk.copy.model.groep.operationeel.historisch.basis.AbstractPersoonIndicatieHisModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonIndicatieModel;


/**
 * User implementatie van PersoonNationaliteitHis.
 */
@Entity
@Table(schema = "kern", name = "his_persindicatie")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonIndicatieHisModel extends AbstractPersoonIndicatieHisModel {

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private PersoonIndicatieHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonIndicatieStandaardGroep .
     * @param persoonIndicatieModel          .
     */
    public PersoonIndicatieHisModel(
            final AbstractPersoonIndicatieStandaardGroep persoonIndicatieStandaardGroep,
            final PersoonIndicatieModel persoonIndicatieModel)
    {
        super(persoonIndicatieStandaardGroep, persoonIndicatieModel);
    }

    @Override
    public PersoonIndicatieHisModel kopieer() {
        return new PersoonIndicatieHisModel(this, getPersoonIndicatie());
    }
}
