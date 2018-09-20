/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.RedenVerliesNaam;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Objecttype Reden verlies nationaliteit.
 */
//TODO entity naam tijdelijk vanwege botsing met oude entities
@Entity
@Table(schema = "Kern", name = "rdnverliesnlnation")
@Access(AccessType.FIELD)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuppressWarnings("serial")
public class RedenVerliesNLNationaliteit extends AbstractStatischObjectType {

    @Id
    @Column(name = "id")
    private Short id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "oms"))
    private RedenVerliesNaam naam;

    public Short getId() {
        return id;
    }

    public RedenVerliesNaam getNaam() {
        return naam;
    }

    public void setNaam(final RedenVerliesNaam naam) {
        this.naam = naam;
    }

    public void setId(final Short id) {
        this.id = id;
    }
}
