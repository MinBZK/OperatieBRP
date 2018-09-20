/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.impl.gen;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.BetrokkenheidOuderlijkGezagGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.StatusHistorie;
import org.hibernate.annotations.Type;


/**
 * Implementatie voor de groep ouderlijk gezag van objecttype betrokkenheid.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractBetrokkenheidOuderlijkGezagGroepMdl extends AbstractGroep implements
        BetrokkenheidOuderlijkGezagGroepBasis
{

    @Transient
    private StatusHistorie statusHistorie;

    @Column(name = "indouderheeftgezag")
    @Type(type = "JaNee")
    private JaNee          indOuderlijkGezag;

    @Override
    public JaNee getIndOuderlijkGezag() {
        return indOuderlijkGezag;
    }

    @Override
    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }

    protected void setIndOuderlijkGezag(final JaNee indOuderlijkGezag) {
        this.indOuderlijkGezag = indOuderlijkGezag;
    }
}
