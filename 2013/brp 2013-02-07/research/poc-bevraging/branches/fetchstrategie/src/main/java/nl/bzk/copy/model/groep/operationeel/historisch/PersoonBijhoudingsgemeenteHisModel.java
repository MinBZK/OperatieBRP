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

import nl.bzk.copy.model.groep.operationeel.AbstractPersoonBijhoudingsgemeenteGroep;
import nl.bzk.copy.model.groep.operationeel.historisch.basis.AbstractPersoonBijhoudingsgemeenteHisModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;


/**
 * User implementatie van PersoonBijhoudingsgemeenteHis.
 */
@Entity
@Table(schema = "kern", name = "his_persbijhgem")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonBijhoudingsgemeenteHisModel extends AbstractPersoonBijhoudingsgemeenteHisModel {

    /**
     * Standaard (lege) constructor.
     */
    public PersoonBijhoudingsgemeenteHisModel() {
    }

    /**
     * Standaard constructor.
     *
     * @param persoonBijhoudingsgemeenteGroep
     *                bijhoudingsgemeente groep
     * @param persoon persoon waartoe groep behoord
     */
    public PersoonBijhoudingsgemeenteHisModel(
            final AbstractPersoonBijhoudingsgemeenteGroep persoonBijhoudingsgemeenteGroep,
            final PersoonModel persoon)
    {
        super(persoonBijhoudingsgemeenteGroep, persoon);
    }

    @Override
    public PersoonBijhoudingsgemeenteHisModel kopieer() {
        return new PersoonBijhoudingsgemeenteHisModel(this, getPersoon());
    }
}
