/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.ISO31661Alpha2;
import nl.bzk.copy.model.attribuuttype.Landcode;
import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import org.hibernate.annotations.*;


/**
 * Land objecttype.
 */
//TODO entity naam tijdelijk vanwege botsing met oude entities
@Entity
@Table(schema = "Kern", name = "Land")
@Access(AccessType.FIELD)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuppressWarnings("serial")
public class Land extends AbstractStatischObjectType {

    @Id
    private Integer id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private Landcode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "iso31661alpha2"))
    private ISO31661Alpha2 iso31661Alpha2;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private Naam naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    private Datum datumAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    private Datum datumEinde;


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

    public void setId(final Integer id) {
        this.id = id;
    }
}
