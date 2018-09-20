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
import nl.bzk.brp.model.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.model.attribuuttype.Woonplaatscode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.GeldigheidsPeriode;
import nl.bzk.brp.util.DatumUtil;

/**
 * Woonplaats.
 */
@Entity
@Table(schema = "Kern", name = "Plaats")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class Plaats extends AbstractStatischObjectType implements GeldigheidsPeriode {

    @Id
    @Column (name = "id")
    private Integer id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private Woonplaatscode code;

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

    public Woonplaatscode getCode() {
        return code;
    }

    public void setCode(final Woonplaatscode code) {
        this.code = code;
    }

    public NaamEnumeratiewaarde getNaam() {
        return naam;
    }

    public void setNaam(final NaamEnumeratiewaarde naam) {
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

}
