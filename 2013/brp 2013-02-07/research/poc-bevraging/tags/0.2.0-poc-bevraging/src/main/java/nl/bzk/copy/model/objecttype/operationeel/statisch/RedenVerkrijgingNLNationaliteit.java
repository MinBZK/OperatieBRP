/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.RedenVerkrijgingCode;
import nl.bzk.copy.model.attribuuttype.RedenVerkrijgingNaam;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Object type reden verkrijging NL nationaliteit.
 */
@Entity
@Table(schema = "Kern", name = "rdnverknlnation")
@Access(AccessType.FIELD)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuppressWarnings("serial")
public class RedenVerkrijgingNLNationaliteit extends AbstractStatischObjectType {

    @Id
    private Short id;

    /**
     * .
     */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private RedenVerkrijgingCode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "oms"))
    private RedenVerkrijgingNaam omschrijving;

    public Short getId() {
        return id;
    }

    public RedenVerkrijgingNaam getOmschrijving() {
        return omschrijving;
    }

    /**
     * .
     *
     * @return RedenVerkrijgingCode
     */
    public RedenVerkrijgingCode getCode() {
        return code;
    }

    public void setId(final Short id) {
        this.id = id;
    }

    public void setCode(final RedenVerkrijgingCode code) {
        this.code = code;
    }

    public void setOmschrijving(final RedenVerkrijgingNaam omschrijving) {
        this.omschrijving = omschrijving;
    }
}
