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

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonIdentificatienummersGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonIdentificatienummersHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * User implementatie van PersoonIdentificatienummersHis.
 *
 */
@Entity
@Table(schema = "kern", name = "His_PersIds")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonIdentificatienummersHisModel extends AbstractPersoonIdentificatienummersHisModel {

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private PersoonIdentificatienummersHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonIdentificatienummersGroep .
     * @param persoonModel .
     */
    public PersoonIdentificatienummersHisModel(
            final AbstractPersoonIdentificatienummersGroep persoonIdentificatienummersGroep,
            final PersoonModel persoonModel)
    {
        super(persoonIdentificatienummersGroep, persoonModel);
    }

    @Override
    public PersoonIdentificatienummersHisModel kopieer() {
        return new PersoonIdentificatienummersHisModel(this, getPersoon());
    }
}
