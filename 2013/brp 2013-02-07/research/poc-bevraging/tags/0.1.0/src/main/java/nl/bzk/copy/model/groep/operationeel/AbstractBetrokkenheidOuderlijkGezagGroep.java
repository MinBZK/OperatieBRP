/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import nl.bzk.copy.model.attribuuttype.JaNee;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.BetrokkenheidOuderlijkGezagGroepBasis;
import org.hibernate.annotations.Type;


/**
 * Implementatie voor de groep ouderlijk gezag van objecttype betrokkenheid.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractBetrokkenheidOuderlijkGezagGroep extends AbstractGroep implements
        BetrokkenheidOuderlijkGezagGroepBasis
{

    @Column(name = "indouderheeftgezag")
    @Type(type = "JaNee")
    private JaNee indOuderlijkGezag;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractBetrokkenheidOuderlijkGezagGroep() {

    }

    /**
     * .
     *
     * @param betrokkenheidOuderlijkGezagGroepBasis
     *         BetrokkenheidOuderlijkGezagGroepBasis
     */
    protected AbstractBetrokkenheidOuderlijkGezagGroep(
            final BetrokkenheidOuderlijkGezagGroepBasis betrokkenheidOuderlijkGezagGroepBasis)
    {
        indOuderlijkGezag = betrokkenheidOuderlijkGezagGroepBasis.getIndOuderlijkGezag();
    }

    @Override
    public JaNee getIndOuderlijkGezag() {
        return indOuderlijkGezag;
    }

    public void setIndOuderlijkGezag(final JaNee indOuderlijkGezag) {
        this.indOuderlijkGezag = indOuderlijkGezag;
    }
}
