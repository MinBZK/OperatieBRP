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

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonVoornaamStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonVoornaamHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;


/** User implementatie van PersoonVoornaamHisMdl. */
@Entity
@Access(AccessType.FIELD)
@Table(schema = "Kern", name = "His_PersVoornaam")
@SuppressWarnings("serial")
public class PersoonVoornaamHisModel extends AbstractPersoonVoornaamHisModel {

    /** Default constructor tbv hibernate. */
    @SuppressWarnings("unused")
    private PersoonVoornaamHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonVoornaamStandaardGroep .
     * @param persoonVoornaamModel .
     */
    public PersoonVoornaamHisModel(final AbstractPersoonVoornaamStandaardGroep persoonVoornaamStandaardGroep,
        final PersoonVoornaamModel persoonVoornaamModel)
    {
        super(persoonVoornaamStandaardGroep, persoonVoornaamModel);
    }

    @Override
    public PersoonVoornaamHisModel kopieer() {
        return new PersoonVoornaamHisModel(this, getPersoonVoornaam());
    }
}
