/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import nl.bzk.brp.model.hisvolledig.impl.kern.AbstractPersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieVastgesteldNietNederlanderHisVolledig;


/**
 * Subtype klasse voor indicatie Vastgesteld niet Nederlander?
 */
@Entity
@DiscriminatorValue(value = "4")
public class PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl extends
    AbstractPersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl implements
    PersoonIndicatieVastgesteldNietNederlanderHisVolledig
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     */
    protected PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl() {
        super();
    }

    /**
     * Constructor met backreference naar persoon.
     *
     * @param persoon backreference
     */
    public PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl(final PersoonHisVolledigImpl persoon) {
        super(persoon);
    }

}
