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
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;


/**
 * Partij.
 */
@Entity
@Table(schema = "Kern", name = "Partij")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class Partij extends AbstractStatischObjectType {

    @Id
    @Column(name = "id")
    private Short        id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private NaamEnumeratiewaarde naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanv"))
    private Datum        datumAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateinde"))
    private Datum        datumEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private Gemeentecode gemeentecode;

    public Short getId() {
        return id;
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
}
