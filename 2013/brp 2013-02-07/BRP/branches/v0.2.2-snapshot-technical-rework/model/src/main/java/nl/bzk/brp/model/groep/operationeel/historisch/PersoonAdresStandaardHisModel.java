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

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonAdresStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonAdresStandaardHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;


/** User implementatie van PersoonAdresHis. */
@Entity
@Table(schema = "kern", name = "his_persadres")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonAdresStandaardHisModel extends AbstractPersoonAdresStandaardHisModel {
    /** Default constructor tbv hibernate. */
    @SuppressWarnings("unused")
    private PersoonAdresStandaardHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonAdresStandaardGroep .
     * @param persoonAdresModel .
     */
    public PersoonAdresStandaardHisModel(
        final AbstractPersoonAdresStandaardGroep persoonAdresStandaardGroep,
        final PersoonAdresModel persoonAdresModel)
    {
        super(persoonAdresStandaardGroep, persoonAdresModel);
    }

    @Override
    public PersoonAdresStandaardHisModel kopieer() {
        return new PersoonAdresStandaardHisModel(this, getPersoonAdres());
    }
}
