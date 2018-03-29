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
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_persverblijfsr database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persverblijfsr", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg"}))
public class PersoonVerblijfsrechtHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persverblijfsrecht_id_generator", sequenceName = "kern.seq_his_persverblijfsr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persverblijfsrecht_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "dataanvverblijfsr", nullable = false)
    private int datumAanvangVerblijfsrecht;

    @Column(name = "datmededelingverblijfsr", nullable = false)
    private int datumMededelingVerblijfsrecht;

    @Column(name = "datvoorzeindeverblijfsr")
    private Integer datumVoorzienEindeVerblijfsrecht;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    // bi-directional many-to-one association to Verblijfsrecht
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aandverblijfsr", nullable = false)
    private Verblijfsrecht verblijfsrecht;

    /**
     * JPA default constructor.
     */
    protected PersoonVerblijfsrechtHistorie() {}

    /**
     * Maak een nieuwe persoon verblijfsrecht historie.
     *
     * @param persoon persoon
     * @param verblijfsrecht verblijfsrecht
     * @param datumAanvangVerblijfsrecht datum aanvang verblijfsrecht
     * @param datumMededelingVerblijfsrecht datum mededeling verblijfsrecht
     */
    public PersoonVerblijfsrechtHistorie(final Persoon persoon, final Verblijfsrecht verblijfsrecht, final Integer datumAanvangVerblijfsrecht,
            final Integer datumMededelingVerblijfsrecht) {
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
        this.datumMededelingVerblijfsrecht = datumMededelingVerblijfsrecht;
        setPersoon(persoon);
        setVerblijfsrecht(verblijfsrecht);
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
     * Zet de waarden voor id van PersoonVerblijfsrechtHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonVerblijfsrechtHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum aanvang verblijfsrecht van PersoonVerblijfsrechtHistorie.
     *
     * @return de waarde van datum aanvang verblijfsrecht van PersoonVerblijfsrechtHistorie
     */
    public Integer getDatumAanvangVerblijfsrecht() {
        return datumAanvangVerblijfsrecht;
    }

    /**
     * Zet de waarden voor datum aanvang verblijfsrecht van PersoonVerblijfsrechtHistorie.
     *
     * @param datumAanvangVerblijfsrecht de nieuwe waarde voor datum aanvang verblijfsrecht van
     *        PersoonVerblijfsrechtHistorie
     */
    public void setDatumAanvangVerblijfsrecht(final int datumAanvangVerblijfsrecht) {
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
    }

    /**
     * Geef de waarde van datum mededeling verblijfsrecht van PersoonVerblijfsrechtHistorie.
     *
     * @return de waarde van datum mededeling verblijfsrecht van PersoonVerblijfsrechtHistorie
     */
    public int getDatumMededelingVerblijfsrecht() {
        return datumMededelingVerblijfsrecht;
    }

    /**
     * Zet de waarden voor datum mededeling verblijfsrecht van PersoonVerblijfsrechtHistorie.
     *
     * @param datumMededelingVerblijfsrecht de nieuwe waarde voor datum mededeling verblijfsrecht
     *        van PersoonVerblijfsrechtHistorie
     */
    public void setDatumMededelingVerblijfsrecht(final int datumMededelingVerblijfsrecht) {
        this.datumMededelingVerblijfsrecht = datumMededelingVerblijfsrecht;
    }

    /**
     * Geef de waarde van datum voorzien einde verblijfsrecht van PersoonVerblijfsrechtHistorie.
     *
     * @return de waarde van datum voorzien einde verblijfsrecht van PersoonVerblijfsrechtHistorie
     */
    public Integer getDatumVoorzienEindeVerblijfsrecht() {
        return datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * Zet de waarden voor datum voorzien einde verblijfsrecht van PersoonVerblijfsrechtHistorie.
     *
     * @param datumVoorzienEindeVerblijfsrecht de nieuwe waarde voor datum voorzien einde
     *        verblijfsrecht van PersoonVerblijfsrechtHistorie
     */
    public void setDatumVoorzienEindeVerblijfsrecht(final Integer datumVoorzienEindeVerblijfsrecht) {
        this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * Geef de waarde van persoon van PersoonVerblijfsrechtHistorie.
     *
     * @return de waarde van persoon van PersoonVerblijfsrechtHistorie
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonVerblijfsrechtHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonVerblijfsrechtHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van verblijfsrecht van PersoonVerblijfsrechtHistorie.
     *
     * @return de waarde van verblijfsrecht van PersoonVerblijfsrechtHistorie
     */
    public Verblijfsrecht getVerblijfsrecht() {
        return verblijfsrecht;
    }

    /**
     * Zet de waarden voor verblijfsrecht van PersoonVerblijfsrechtHistorie.
     *
     * @param verblijfsrecht de nieuwe waarde voor verblijfsrecht van PersoonVerblijfsrechtHistorie
     */
    public void setVerblijfsrecht(final Verblijfsrecht verblijfsrecht) {
        ValidationUtils.controleerOpNullWaarden("verblijfsrecht mag niet null zijn", verblijfsrecht);
        this.verblijfsrecht = verblijfsrecht;
    }
}
