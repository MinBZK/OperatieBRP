/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.lev;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.lev.LeveringPersoonHisVolledig;

/**
 * HisVolledig klasse voor Levering \ Persoon.
 *
 */
@Entity
@Table(schema = "Prot", name = "levsaantekpers")
public class LeveringPersoonHisVolledigImpl extends AbstractLeveringPersoonHisVolledigImpl implements HisVolledigImpl,
        LeveringPersoonHisVolledig
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected LeveringPersoonHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param levering levering van Levering \ Persoon.
     * @param persoonId persoon van Levering \ Persoon.
     */
    public LeveringPersoonHisVolledigImpl(final LeveringHisVolledigImpl levering, final Integer persoonId) {
        super(levering, persoonId);
    }

}
