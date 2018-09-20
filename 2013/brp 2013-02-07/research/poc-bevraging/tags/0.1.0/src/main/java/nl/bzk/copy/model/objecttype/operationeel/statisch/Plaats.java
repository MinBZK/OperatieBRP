/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.attribuuttype.PlaatsCode;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import nl.bzk.copy.model.basis.GeldigheidsPeriode;
import nl.bzk.copy.util.DatumUtil;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Woonplaats.
 */
@Entity
@Table(schema = "Kern", name = "Plaats")
@Access(AccessType.FIELD)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

@SuppressWarnings("serial")
public class Plaats extends AbstractStatischObjectType implements GeldigheidsPeriode {

    @Id
    @Column(name = "id")
    private Integer id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private PlaatsCode code;

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
