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
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.GeldigheidsPeriode;
import nl.bzk.brp.util.DatumUtil;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;

/**
 * Woonplaats.
 */
@Entity
@Table(schema = "Kern", name = "Plaats")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class Plaats extends AbstractStatischObjectType implements GeldigheidsPeriode, Serializable, Externalizable {

    @Id
    @Column (name = "id")
    @JsonProperty
    private Integer id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    @JsonProperty
    private PlaatsCode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    @JsonProperty
    private Naam naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    @JsonProperty
    private Datum        datumAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    @JsonProperty
    private Datum        datumEinde;


    public Integer getId() {
        return id;
    }

    public PlaatsCode getCode() {
        return code;
    }

    public void setCode(final PlaatsCode code) {
        this.code = code;
    }

    public Naam getNaam() {
        return naam;
    }

    public void setNaam(final Naam naam) {
        this.naam = naam;
    }

    @Override
    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    @Override
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
    public boolean isGeldigOp(final Datum peilDatum) {
        if (peilDatum == null) {
            throw new IllegalArgumentException("Peildatum kan niet leeg zijn voor geldigheidOp plaats.");
        }
        return DatumUtil.isGeldigOp(datumAanvang, datumEinde, peilDatum);
    }

    @Override
    public boolean isGeldigPeriode(final Datum beginDatum, final Datum eindDatum) {
        if (beginDatum != null && eindDatum != null && beginDatum.equals(eindDatum)) {
            throw new IllegalArgumentException("begin datum en eind datum kunnen niet dezelfde zijn.");
        }
        return DatumUtil.isDatumsGeldigOpPeriode(datumAanvang, datumEinde, beginDatum, eindDatum);
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
        this.code = (PlaatsCode) ExternalReaderService.leesWaarde(in, PlaatsCode.class);
        this.naam = (Naam) ExternalReaderService.leesWaarde(in, Naam.class);
        this.datumAanvang = (Datum) ExternalReaderService.leesWaarde(in, Datum.class);
        this.datumEinde = (Datum) ExternalReaderService.leesWaarde(in, Datum.class);
    }
}
