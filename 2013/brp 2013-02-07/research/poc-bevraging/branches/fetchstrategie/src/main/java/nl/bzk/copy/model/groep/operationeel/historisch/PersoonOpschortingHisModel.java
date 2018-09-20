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

import nl.bzk.copy.model.groep.operationeel.AbstractPersoonOpschortingGroep;
import nl.bzk.copy.model.groep.operationeel.historisch.basis.AbstractPersoonOpschortingHisModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;


/**
 * User implementatie van PersoonOpschortingHis.
 */
@Entity
@Table(schema = "kern", name = "his_persopschorting")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonOpschortingHisModel extends AbstractPersoonOpschortingHisModel {

    /**
     * Standaard (lege) constructor.
     */
    protected PersoonOpschortingHisModel() {
        super();
    }

    /**
     * copy constructor.
     *
     * @param persoonOpschortingGroep het ander object.
     * @param persoonModel            de persoon
     */
    public PersoonOpschortingHisModel(final AbstractPersoonOpschortingGroep persoonOpschortingGroep,
                                      final PersoonModel persoonModel)
    {
        super(persoonOpschortingGroep, persoonModel);
    }

    @Override
    public PersoonOpschortingHisModel kopieer() {
        return new PersoonOpschortingHisModel(this, getPersoon());
    }

}
