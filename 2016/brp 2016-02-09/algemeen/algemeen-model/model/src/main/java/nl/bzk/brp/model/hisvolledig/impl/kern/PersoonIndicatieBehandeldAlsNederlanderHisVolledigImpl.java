/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import nl.bzk.brp.model.hisvolledig.impl.kern.AbstractPersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieBehandeldAlsNederlanderHisVolledig;


/**
 * Subtype klasse voor indicatie Behandeld als Nederlander?
 */
@Entity
@DiscriminatorValue(value = "5")
public class PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl extends
    AbstractPersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl implements
    PersoonIndicatieBehandeldAlsNederlanderHisVolledig
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     */
    protected PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl() {
        super();
    }

    /**
     * Constructor met backreference naar persoon.
     *
     * @param persoon backreference
     */
    public PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl(final PersoonHisVolledigImpl persoon) {
        super(persoon);
    }

}
