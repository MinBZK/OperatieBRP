/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.ber;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.ber.BerichtPersoonHisVolledig;

/**
 * HisVolledig klasse voor Bericht \ Persoon.
 *
 */
@Entity
@Table(schema = "Ber", name = "BerPers")
public class BerichtPersoonHisVolledigImpl extends AbstractBerichtPersoonHisVolledigImpl implements HisVolledigImpl,
        BerichtPersoonHisVolledig
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected BerichtPersoonHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param bericht bericht van Bericht \ Persoon.
     * @param persoonId persoon van Bericht \ Persoon.
     */
    public BerichtPersoonHisVolledigImpl(final BerichtHisVolledigImpl bericht, final Integer persoonId) {
        super(bericht, persoonId);
    }

}
