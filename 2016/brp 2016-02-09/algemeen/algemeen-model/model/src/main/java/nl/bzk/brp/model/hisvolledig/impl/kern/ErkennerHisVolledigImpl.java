/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.ErkennerHisVolledig;


/**
 * HisVolledig klasse voor Erkenner.
 */
@Entity
@DiscriminatorValue(value = "4")
public class ErkennerHisVolledigImpl extends AbstractErkennerHisVolledigImpl implements ErkennerHisVolledig,
    ElementIdentificeerbaar
{

    /**
     * Default contructor voor JPA.
     */
    protected ErkennerHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Erkenner.
     * @param persoon persoon van Erkenner.
     */
    public ErkennerHisVolledigImpl(final RelatieHisVolledigImpl relatie, final PersoonHisVolledigImpl persoon) {
        super(relatie, persoon);
    }

}
