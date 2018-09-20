/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import nl.bzk.brp.model.hisvolledig.impl.kern.AbstractPersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledig;


/**
 * Subtype klasse voor indicatie Volledige verstrekkingsbeperking?
 */
@Entity
@DiscriminatorValue(value = "3")
public class PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl extends
    AbstractPersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl implements
    PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledig
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     */
    protected PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl() {
        super();
    }

    /**
     * Constructor met backreference naar persoon.
     *
     * @param persoon backreference
     */
    public PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl(final PersoonHisVolledigImpl persoon) {
        super(persoon);
    }

}
