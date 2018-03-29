/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_persgeslachtsaand database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persgeslachtsaand", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg", "dataanvgel"}))
public class PersoonGeslachtsaanduidingHistorie extends AbstractMaterieleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persgeslachtsaand_id_generator", sequenceName = "kern.seq_his_persgeslachtsaand", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persgeslachtsaand_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "geslachtsaand", nullable = false)
    private int geslachtsaanduidingId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonGeslachtsaanduidingHistorie() {}

    /**
     * Maak een nieuwe persoon geslachtsaanduiding historie.
     *
     * @param persoon persoon
     * @param geslachtsaanduiding geslachtsaanduiding
     */
    public PersoonGeslachtsaanduidingHistorie(final Persoon persoon, final Geslachtsaanduiding geslachtsaanduiding) {
        setPersoon(persoon);
        setGeslachtsaanduiding(geslachtsaanduiding);
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     *
     * @param ander het te kopieren object
     */
    public PersoonGeslachtsaanduidingHistorie(final PersoonGeslachtsaanduidingHistorie ander) {
        super(ander);
        geslachtsaanduidingId = ander.getGeslachtsaanduiding().getId();
        persoon = ander.getPersoon();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van PersoonGeslachtsaanduidingHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonGeslachtsaanduidingHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van geslachtsaanduiding van PersoonGeslachtsaanduidingHistorie.
     *
     * @return de waarde van geslachtsaanduiding van PersoonGeslachtsaanduidingHistorie
     */
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return Geslachtsaanduiding.parseId(geslachtsaanduidingId);
    }

    /**
     * Zet de waarden voor geslachtsaanduiding van PersoonGeslachtsaanduidingHistorie.
     *
     * @param geslachtsaanduiding de nieuwe waarde voor geslachtsaanduiding van
     *        PersoonGeslachtsaanduidingHistorie
     */
    public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
        ValidationUtils.controleerOpNullWaarden("geslachtsaanduiding mag niet null zijn", geslachtsaanduiding);
        geslachtsaanduidingId = geslachtsaanduiding.getId();
    }

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonGeslachtsaanduidingHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonGeslachtsaanduidingHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    @Override
    public final PersoonGeslachtsaanduidingHistorie kopieer() {
        return new PersoonGeslachtsaanduidingHistorie(this);
    }

    @Override
    public boolean moetHistorieAaneengeslotenZijn() {
        return true;
    }
}
