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

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.ISO31661Alpha2;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;


/** Land objecttype. */
// TODO entity naam tijdelijk vanwege botsing met oude entities
@Entity
@Table(schema = "Kern", name = "Land")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class Land extends AbstractStatischObjectType implements Serializable, Externalizable {

    @Id
    @JsonProperty
    private Integer        id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    @JsonProperty
    private Landcode       code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "iso31661alpha2"))
    @JsonProperty
    private ISO31661Alpha2 iso31661Alpha2;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    @JsonProperty
    private Naam           naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    @JsonProperty
    private Datum          datumAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    @JsonProperty
    private Datum          datumEinde;

    public Integer getId() {
        return id;
    }

    public Landcode getCode() {
        return code;
    }

    public void setCode(final Landcode landcode) {
        code = landcode;
    }

    public ISO31661Alpha2 getIso31661Alpha2() {
        return iso31661Alpha2;
    }

    public void setIso31661Alpha2(final ISO31661Alpha2 iso31661Alpha2) {
        this.iso31661Alpha2 = iso31661Alpha2;
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

    public Datum getDatumEinde() {
        return datumEinde;
    }

    public void setDatumAanvang(final Datum datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    public void setDatumEinde(final Datum datumEinde) {
        this.datumEinde = datumEinde;
    }

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeObject(id);
        ExternalWriterService.schrijfWaarde(out, code);
        ExternalWriterService.schrijfWaarde(out, naam);
        ExternalWriterService.schrijfWaarde(out, datumAanvang);
        ExternalWriterService.schrijfWaarde(out, datumEinde);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = (Integer) in.readObject();
        this.code = (Landcode) ExternalReaderService.leesWaarde(in, Landcode.class);
        this.naam = (Naam) ExternalReaderService.leesWaarde(in, Naam.class);
        this.datumAanvang = (Datum) ExternalReaderService.leesWaarde(in, Datum.class);
        this.datumEinde = (Datum) ExternalReaderService.leesWaarde(in, Datum.class);
    }
}
