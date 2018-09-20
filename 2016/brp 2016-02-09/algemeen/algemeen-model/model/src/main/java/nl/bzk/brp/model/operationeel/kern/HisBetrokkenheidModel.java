/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.BestaansperiodeFormeel;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisBetrokkenheidIdentiteitGroep;

/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_Betr")
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 2071)
public class HisBetrokkenheidModel extends AbstractHisBetrokkenheidModel implements ModelIdentificeerbaar<Integer>, BestaansperiodeFormeel,
        ElementIdentificeerbaar, HisBetrokkenheidIdentiteitGroep
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected HisBetrokkenheidModel() {
        super();
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param betrokkenheid betrokkenheid van HisBetrokkenheidModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public HisBetrokkenheidModel(final BetrokkenheidHisVolledig betrokkenheid, final ActieModel actieInhoud) {
        super(betrokkenheid, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisBetrokkenheidModel(final AbstractHisBetrokkenheidModel kopie) {
        super(kopie);
    }

}
