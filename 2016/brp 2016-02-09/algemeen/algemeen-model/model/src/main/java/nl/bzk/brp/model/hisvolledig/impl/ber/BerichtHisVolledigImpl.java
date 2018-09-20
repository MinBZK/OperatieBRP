/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.ber;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.ber.BerichtHisVolledig;

/**
 * HisVolledig klasse voor Bericht.
 *
 */
@Entity
@Table(schema = "Ber", name = "Ber")
public class BerichtHisVolledigImpl extends AbstractBerichtHisVolledigImpl implements HisVolledigImpl, BerichtHisVolledig {

    /**
     * Default contructor voor JPA.
     *
     */
    protected BerichtHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Bericht.
     * @param richting richting van Bericht.
     */
    public BerichtHisVolledigImpl(final SoortBerichtAttribuut soort, final RichtingAttribuut richting) {
        super(soort, richting);
    }

}
