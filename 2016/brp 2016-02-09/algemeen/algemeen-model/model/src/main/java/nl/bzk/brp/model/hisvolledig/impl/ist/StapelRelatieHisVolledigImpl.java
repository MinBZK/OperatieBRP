/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.ist;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.ist.StapelRelatieHisVolledig;

/**
 * HisVolledig klasse voor Stapel \ Relatie.
 *
 */
@Entity
@Table(schema = "IST", name = "StapelRelatie")
public class StapelRelatieHisVolledigImpl extends AbstractStapelRelatieHisVolledigImpl implements HisVolledigImpl,
        StapelRelatieHisVolledig
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected StapelRelatieHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param stapel stapel van Stapel \ Relatie.
     * @param relatie relatie van Stapel \ Relatie.
     */
    public StapelRelatieHisVolledigImpl(final StapelHisVolledigImpl stapel, final RelatieHisVolledigImpl relatie) {
        super(stapel, relatie);
    }

}
