/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.prot;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.prot.LeveringsaantekeningPersoonHisVolledig;

/**
 * HisVolledig klasse voor Leveringsaantekening \ Persoon.
 *
 */
@Entity
@Table(schema = "Prot", name = "LevsaantekPers")
@Access(value = AccessType.FIELD)
public class LeveringsaantekeningPersoonHisVolledigImpl extends AbstractLeveringsaantekeningPersoonHisVolledigImpl implements HisVolledigImpl,
        LeveringsaantekeningPersoonHisVolledig
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected LeveringsaantekeningPersoonHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param leveringsaantekening leveringsaantekening van Leveringsaantekening \ Persoon.
     * @param persoonId persoonId van Leveringsaantekening \ Persoon.
     */
    public LeveringsaantekeningPersoonHisVolledigImpl(final LeveringsaantekeningHisVolledigImpl leveringsaantekening, final Integer persoonId) {
        super(leveringsaantekening, persoonId);
    }

}
