/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Omschrijving;
import nl.bzk.copy.model.attribuuttype.RedenBeeindigingRelatieCode;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Gegevens model object voor Reden beeindiging relatie.
 */
@Entity
@Table(schema = "Kern", name = "RdnBeeindRelatie")
@Access(AccessType.FIELD)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuppressWarnings("serial")
public class RedenBeeindigingRelatie extends AbstractStatischObjectType {

    @Id
    @Column(name = "id")
    private Short id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private RedenBeeindigingRelatieCode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "oms"))
    private Omschrijving omschrijving;

    public Short getId() {
        return id;
    }

    public RedenBeeindigingRelatieCode getCode() {
        return code;
    }

    public Omschrijving getOmschrijving() {
        return omschrijving;
    }


    public void setId(final Short id) {
        this.id = id;
    }


    public void setCode(final RedenBeeindigingRelatieCode code) {
        this.code = code;
    }


    public void setOmschrijving(final Omschrijving omschrijving) {
        this.omschrijving = omschrijving;
    }
}
