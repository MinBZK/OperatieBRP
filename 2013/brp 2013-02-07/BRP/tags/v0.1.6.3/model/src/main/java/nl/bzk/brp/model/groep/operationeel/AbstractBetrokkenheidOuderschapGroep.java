/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.BetrokkenheidOuderschapGroepBasis;
import org.hibernate.annotations.Type;


/**
 * Implementatie voor de groep ouderschap van objecttype betrokkenheid.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractBetrokkenheidOuderschapGroep extends AbstractGroep implements
        BetrokkenheidOuderschapGroepBasis
{

    @Column(name = "indOuder")
    @Type(type = "Ja")
    private Ja indOuder;

    @Transient
    private Ja indAdresGevend;

    @Transient
    private Datum datumAanvang;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractBetrokkenheidOuderschapGroep() {
    }

    /**
     * .
     *
     * @param betrokkenheidOuderschapGroepBasis BetrokkenheidOuderschapGroepBasis
     */
    protected AbstractBetrokkenheidOuderschapGroep(
            final BetrokkenheidOuderschapGroepBasis betrokkenheidOuderschapGroepBasis)
    {
        indOuder = betrokkenheidOuderschapGroepBasis.getIndOuder();
    }

    @Override
    public Ja getIndOuder() {
        return indOuder;
    }

    @Override
    public Ja getIndAdresGevend() {
        return indAdresGevend;
    }

    @Override
    public Datum getDatumAanvang() {
        return datumAanvang;
    }
}
