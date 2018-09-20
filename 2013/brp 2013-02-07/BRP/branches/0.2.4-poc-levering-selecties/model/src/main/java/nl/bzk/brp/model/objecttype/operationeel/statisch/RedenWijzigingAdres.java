/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;

/**
 * Reden wijziging adres.
 */
@Entity
@Table(schema = "Kern", name = "RdnWijzAdres")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class RedenWijzigingAdres extends AbstractStatischObjectType implements Serializable, Externalizable {

    @Id
    @Column (name = "id")
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

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeObject(redenWijzigingAdresID);
        ExternalWriterService.schrijfWaarde(out, redenWijzigingAdresCode);
        ExternalWriterService.schrijfWaarde(out, naam);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        this.redenWijzigingAdresID = (Short) in.readObject();
        this.redenWijzigingAdresCode =
                (RedenWijzigingAdresCode) ExternalReaderService.leesWaarde(in, RedenWijzigingAdresCode.class);
        this.naam = (Naam) ExternalReaderService.leesWaarde(in, Naam.class);
    }
}
