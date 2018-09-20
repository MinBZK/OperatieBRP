/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.attribuuttype.Nationaliteitcode;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Objecttype Nationaliteit.
 */
//TODO
@Entity
@Table(schema = "Kern", name = "Nation")
@Access(AccessType.FIELD)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuppressWarnings("serial")
public class Nationaliteit extends AbstractStatischObjectType {

    @Id
    @Column(name = "id")
    private Integer nationaliteitId;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private Naam naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "nationcode"))
    private Nationaliteitcode nationaliteitcode;

    public Integer getNationaliteitId() {
        return nationaliteitId;
    }

    public Naam getNaam() {
        return naam;
    }

    public void setNaam(final Naam naam) {
        this.naam = naam;
    }

    public Nationaliteitcode getNationaliteitcode() {
        return nationaliteitcode;
    }

    public void setNationaliteitcode(final Nationaliteitcode nationaliteitcode) {
        this.nationaliteitcode = nationaliteitcode;
    }

    public void setNationaliteitId(final Integer nationaliteitId) {
        this.nationaliteitId = nationaliteitId;
    }
}
