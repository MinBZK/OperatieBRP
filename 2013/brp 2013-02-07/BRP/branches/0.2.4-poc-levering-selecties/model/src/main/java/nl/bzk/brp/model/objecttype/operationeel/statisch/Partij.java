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

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;



/**
 * Partij.
 */
@Entity
@Table(schema = "Kern", name = "Partij")
@Access(AccessType.FIELD)
public class Partij extends AbstractStatischObjectType implements Externalizable {

    @Id
    @Column(name = "id")
    @JsonProperty
    private Short        id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    @JsonProperty
    private Naam         naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanv"))
    @JsonProperty
    private Datum        datumAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateinde"))
    @JsonProperty
    private Datum        datumEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    @JsonProperty
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

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeObject(id);
        ExternalWriterService.schrijfWaarde(out, naam);
        ExternalWriterService.schrijfWaarde(out, datumAanvang);
        ExternalWriterService.schrijfWaarde(out, datumEinde);
        ExternalWriterService.schrijfWaarde(out, gemeentecode);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        id = (Short) in.readObject();
        naam = (Naam) ExternalReaderService.leesWaarde(in, Naam.class);
        datumAanvang = (Datum) ExternalReaderService.leesWaarde(in, Datum.class);
        datumEinde = (Datum) ExternalReaderService.leesWaarde(in, Datum.class);
        gemeentecode = (Gemeentecode) ExternalReaderService.leesWaarde(in, Gemeentecode.class);
    }

}
