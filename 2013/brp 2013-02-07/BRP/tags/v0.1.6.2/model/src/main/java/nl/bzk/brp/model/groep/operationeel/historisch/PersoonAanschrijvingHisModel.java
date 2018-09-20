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

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonAanschrijvingHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/** User implementatie van BetrokkenheidOuderschapHisMdl. */
@Entity
@Table(schema = "kern", name = "his_persaanschr")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonAanschrijvingHisModel extends AbstractPersoonAanschrijvingHisModel {

    /** Default constructor tbv hibernate. */
    @SuppressWarnings("unused")
    private PersoonAanschrijvingHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonAanschrijvingGroep PersoonAanschrijvingGroepBasis
     * @param persoonModel .
     */
    public PersoonAanschrijvingHisModel(
        final AbstractPersoonAanschrijvingGroep persoonAanschrijvingGroep,
        final PersoonModel persoonModel)
    {
        super(persoonAanschrijvingGroep, persoonModel);
    }

    @Override
    public PersoonAanschrijvingHisModel kopieer() {
        return new PersoonAanschrijvingHisModel(this, getPersoon());
    }
}
