/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.Gemeentecode;
import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Partij.
 */
@Entity
@Table(schema = "Kern", name = "Partij")
@Access(AccessType.FIELD)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

@SuppressWarnings("serial")
public class Partij extends AbstractStatischObjectType {

    @Id
    @Column(name = "id")
    private Short id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private Naam naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanv"))
    private Datum datumAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateinde"))
    private Datum datumEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private Gemeentecode gemeentecode;

    public Short getId() {
        return id;
    }

    public Naam getNaam() {
        return naam;
    }

    public void setNaam(final Naam naam) {
        this.naam = naam;
    }

    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    public void setDatumAanvang(final Datum datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    public Datum getDatumEinde() {
        return datumEinde;
    }

    public void setDatumEinde(final Datum datumEinde) {
        this.datumEinde = datumEinde;
    }

    public Gemeentecode getGemeentecode() {
        return gemeentecode;
    }

    public void setGemeentecode(final Gemeentecode gemeentecode) {
        this.gemeentecode = gemeentecode;
    }

    public void setId(final Short id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Partij partij = (Partij) o;

        if (datumAanvang != null ? !datumAanvang.equals(partij.datumAanvang) : partij.datumAanvang != null) {
            return false;
        }
        if (datumEinde != null ? !datumEinde.equals(partij.datumEinde) : partij.datumEinde != null) {
            return false;
        }
        if (gemeentecode != null ? !gemeentecode.equals(partij.gemeentecode) : partij.gemeentecode != null) {
            return false;
        }
        if (id != null ? !id.equals(partij.id) : partij.id != null) {
            return false;
        }
        if (naam != null ? !naam.equals(partij.naam) : partij.naam != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (naam != null ? naam.hashCode() : 0);
        result = 31 * result + (datumAanvang != null ? datumAanvang.hashCode() : 0);
        result = 31 * result + (datumEinde != null ? datumEinde.hashCode() : 0);
        result = 31 * result + (gemeentecode != null ? gemeentecode.hashCode() : 0);
        return result;
    }
}
