/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.RegelverantwoordingHisVolledig;

/**
 * HisVolledig klasse voor Regelverantwoording.
 *
 */
@Entity
@Table(schema = "Kern", name = "Regelverantwoording")
public class RegelverantwoordingHisVolledigImpl extends AbstractRegelverantwoordingHisVolledigImpl implements HisVolledigImpl,
        RegelverantwoordingHisVolledig, ElementIdentificeerbaar
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected RegelverantwoordingHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param actie actie van Regelverantwoording.
     * @param regel regel van Regelverantwoording.
     */
    public RegelverantwoordingHisVolledigImpl(final ActieHisVolledigImpl actie, final RegelAttribuut regel) {
        super(actie, regel);
    }

}
