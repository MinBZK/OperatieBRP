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

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.BetrokkenheidOuderlijkGezagGroepBasis;
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
    @JsonProperty
    private JaNee indOuderlijkGezag;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractBetrokkenheidOuderlijkGezagGroep() {

    }

    /**
     * .
     *
     * @param betrokkenheidOuderlijkGezagGroepBasis BetrokkenheidOuderlijkGezagGroepBasis
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

    protected void setIndOuderlijkGezag(final JaNee indOuderlijkGezag) {
        this.indOuderlijkGezag = indOuderlijkGezag;
    }
}
