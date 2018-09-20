/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Iso31661Alpha2;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;


/** Land objecttype. */
//TODO entity naam tijdelijk vanwege botsing met oude entities
@Entity
@Table(schema = "Kern", name = "Land")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class Land extends AbstractStatischObjectType {

    @Id
    private Integer id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private Landcode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "iso31661alpha2"))
    private Iso31661Alpha2 iso31661Alpha2;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private NaamEnumeratiewaarde naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    private Datum        datumAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    private Datum        datumEinde;


    public Integer getId() {
        return id;
    }

    public Landcode getCode() {
        return code;
    }

    public void setCode(final Landcode landcode) {
        code = landcode;
    }

    public Iso31661Alpha2 getIso31661Alpha2() {
        return iso31661Alpha2;
    }

    public void setIso31661Alpha2(final Iso31661Alpha2 iso31661Alpha2) {
        this.iso31661Alpha2 = iso31661Alpha2;
    }

    public NaamEnumeratiewaarde getNaam() {
        return naam;
    }

    public void setNaam(final NaamEnumeratiewaarde naam) {
        this.naam = naam;
    }

    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    public Datum getDatumEinde() {
        return datumEinde;
    }


}
