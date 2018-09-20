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

import nl.bzk.copy.model.groep.operationeel.AbstractRelatieStandaardGroep;
import nl.bzk.copy.model.groep.operationeel.historisch.basis.AbstractRelatieStandaardHisModel;
import nl.bzk.copy.model.objecttype.operationeel.RelatieModel;


/**
 * User implementatie van {@link AbstractRelatieStandaardHisModel}.
 */
@Entity
@Table(schema = "kern", name = "His_Relatie")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class RelatieStandaardHisModel extends AbstractRelatieStandaardHisModel {

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private RelatieStandaardHisModel() {
        super();
    }

    /**
     * Constructor.
     *
     * @param groep   de (blauwdruk) groep.
     * @param relatie de relatie.
     */
    public RelatieStandaardHisModel(final AbstractRelatieStandaardGroep groep, final RelatieModel relatie) {
        super(groep, relatie);
    }

    /**
     * Kopieer methode om de relatie te kopieren.
     *
     * @return een nieuwe relatie met dezelfde waardes als de huidige relatie.
     */
    public RelatieStandaardHisModel kopieer() {
        return new RelatieStandaardHisModel(this, getRelatie());
    }
}
