/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.AangeverAdreshoudingCode;
import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.attribuuttype.Omschrijving;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Aangever adreshouding.
 */
@Entity
@Table(schema = "Kern", name = "AangAdresh")
@Access(AccessType.FIELD)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuppressWarnings("serial")
public class AangeverAdreshouding extends AbstractStatischObjectType {

    @Id
    @Column(name = "id")
    private Short aangeverAdreshoudingId;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private Naam naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private AangeverAdreshoudingCode aangeverAdreshoudingCode;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "oms"))
    private Omschrijving omschrijving;

    public Short getAangeverAdreshoudingId() {
        return aangeverAdreshoudingId;
    }

    public Naam getNaam() {
        return naam;
    }

    public AangeverAdreshoudingCode getAangeverAdreshoudingCode() {
        return aangeverAdreshoudingCode;
    }

    public Omschrijving getOmschrijving() {
        return omschrijving;
    }
}
