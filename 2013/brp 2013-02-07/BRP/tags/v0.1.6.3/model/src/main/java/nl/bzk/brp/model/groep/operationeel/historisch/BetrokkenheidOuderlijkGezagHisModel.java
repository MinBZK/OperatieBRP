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

import nl.bzk.brp.model.groep.operationeel.AbstractBetrokkenheidOuderlijkGezagGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractBetrokkenheidOuderlijkGezagHisModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;


/**
 * User implementatie van BetrokkenheidOuderlijkGezagHisMdl.
 *
 */
@Entity
@Table(schema = "kern", name = "his_betrouderlijkgezag")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class BetrokkenheidOuderlijkGezagHisModel extends AbstractBetrokkenheidOuderlijkGezagHisModel {

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private BetrokkenheidOuderlijkGezagHisModel() {
        super();
    }

    /**
     * Standaard constructor.
     *
     * @param betrokkenheidOuderlijkGezagGroep groep voor ouderlijk gezag
     * @param betrokkenheidModel de betrokkenheid waartoe groep behoord
     */
    public BetrokkenheidOuderlijkGezagHisModel(
            final AbstractBetrokkenheidOuderlijkGezagGroep betrokkenheidOuderlijkGezagGroep,
            final BetrokkenheidModel betrokkenheidModel)
    {
        super(betrokkenheidOuderlijkGezagGroep, betrokkenheidModel);
    }

    @Override
    public BetrokkenheidOuderlijkGezagHisModel kopieer() {
        return new BetrokkenheidOuderlijkGezagHisModel(this, getBetrokkenheid());
    }
}
