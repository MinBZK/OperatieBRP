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

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonGeslachtsAanduidingGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonGeslachtsAanduidingHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * User implementatie van PersoonGeboorteHisMdl.
 *
 */
@Entity
@Table(schema = "kern", name = "his_persgeslachtsaand")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonGeslachtsAanduidingHisModel extends AbstractPersoonGeslachtsAanduidingHisModel {

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private PersoonGeslachtsAanduidingHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonGeslachtsAanduidingGroep .
     * @param persoonModel .
     */
    public PersoonGeslachtsAanduidingHisModel(
            final AbstractPersoonGeslachtsAanduidingGroep persoonGeslachtsAanduidingGroep,
            final PersoonModel persoonModel)
    {
        super(persoonGeslachtsAanduidingGroep, persoonModel);
    }

    @Override
    public PersoonGeslachtsAanduidingHisModel kopieer() {
        return new PersoonGeslachtsAanduidingHisModel(this, getPersoon());
    }
}
