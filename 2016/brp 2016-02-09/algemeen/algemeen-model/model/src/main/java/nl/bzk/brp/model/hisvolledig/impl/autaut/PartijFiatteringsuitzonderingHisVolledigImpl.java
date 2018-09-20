/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.autaut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.autaut.PartijFiatteringsuitzonderingHisVolledig;

/**
 * HisVolledig klasse voor Partij \ Fiatteringsuitzondering.
 *
 */
@Entity
@Table(schema = "AutAut", name = "PartijFiatuitz")
@Access(value = AccessType.FIELD)
public class PartijFiatteringsuitzonderingHisVolledigImpl extends AbstractPartijFiatteringsuitzonderingHisVolledigImpl implements HisVolledigImpl,
        PartijFiatteringsuitzonderingHisVolledig, ALaagAfleidbaar
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected PartijFiatteringsuitzonderingHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param partij partij van Partij \ Fiatteringsuitzondering.
     */
    public PartijFiatteringsuitzonderingHisVolledigImpl(final PartijAttribuut partij) {
        super(partij);
    }

}
