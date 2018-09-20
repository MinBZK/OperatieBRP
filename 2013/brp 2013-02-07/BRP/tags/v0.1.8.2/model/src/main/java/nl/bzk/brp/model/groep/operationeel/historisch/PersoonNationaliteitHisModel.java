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

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonNationaliteitHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;


/**
 * User implementatie van PersoonNationaliteitHis.
 *
 */
@Entity
@Table(schema = "kern", name = "his_persnation")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonNationaliteitHisModel extends AbstractPersoonNationaliteitHisModel {

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private PersoonNationaliteitHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonNationaliteitStandaardGroep PersoonNationaliteitStandaardGroepBasis
     * @param persoonNationaliteit .
     */
    public PersoonNationaliteitHisModel(
            final AbstractPersoonNationaliteitStandaardGroep persoonNationaliteitStandaardGroep,
            final PersoonNationaliteitModel persoonNationaliteit)
    {
        super(persoonNationaliteitStandaardGroep, persoonNationaliteit);
    }

    @Override
    public PersoonNationaliteitHisModel kopieer() {
        return new PersoonNationaliteitHisModel(this, getPersoonNationaliteit());
    }
}
