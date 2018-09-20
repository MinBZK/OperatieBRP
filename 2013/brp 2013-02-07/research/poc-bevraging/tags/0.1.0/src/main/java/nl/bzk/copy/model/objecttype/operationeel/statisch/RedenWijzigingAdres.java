/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Reden wijziging adres.
 */
@Entity
@Table(schema = "Kern", name = "RdnWijzAdres")
@Access(AccessType.FIELD)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuppressWarnings("serial")
public class RedenWijzigingAdres extends AbstractStatischObjectType {

    @Id
    @Column(name = "id")
    private Short redenWijzigingAdresID;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private RedenWijzigingAdresCode redenWijzigingAdresCode;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private Naam naam;

    public Short getRedenWijzigingAdresID() {
        return redenWijzigingAdresID;
    }

    public void setRedenWijzigingAdresID(final Short redenWijzigingAdresID) {
        this.redenWijzigingAdresID = redenWijzigingAdresID;
    }

    public RedenWijzigingAdresCode getRedenWijzigingAdresCode() {
        return redenWijzigingAdresCode;
    }

    public void setRedenWijzigingAdresCode(final RedenWijzigingAdresCode redenWijzigingAdresCode) {
        this.redenWijzigingAdresCode = redenWijzigingAdresCode;
    }

    public Naam getNaam() {
        return naam;
    }

    public void setNaam(final Naam naam) {
        this.naam = naam;
    }
}
