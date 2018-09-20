/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

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

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the his_persverblijfsr database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persverblijfsr", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonVerblijfsrechtHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persverblijfsrecht_id_generator", sequenceName = "kern.seq_his_persverblijfsr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persverblijfsrecht_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "dataanvverblijfsr", nullable = false)
    private int datumAanvangVerblijfsrecht;

    @Column(name = "datmededelingverblijfsr", nullable = false)
    private int datumMededelingVerblijfsrecht;

    @Column(name = "datvoorzeindeverblijfsr")
    private Integer datumVoorzienEindeVerblijfsrecht;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    // bi-directional many-to-one association to Verblijfsrecht
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "aandverblijfsr", nullable = false)
    private Verblijfsrecht verblijfsrecht;

    /**
     * JPA default constructor.
     */
    protected PersoonVerblijfsrechtHistorie() {
    }

    /**
     * Maak een nieuwe persoon verblijfsrecht historie.
     *
     * @param persoon
     *            persoon
     * @param verblijfsrecht
     *            verblijfsrecht
     * @param datumAanvangVerblijfsrecht
     *            datum aanvang verblijfsrecht
     * @param datumMededelingVerblijfsrecht
     *            datum mededeling verblijfsrecht
     */
    public PersoonVerblijfsrechtHistorie(
        final Persoon persoon,
        final Verblijfsrecht verblijfsrecht,
        final Integer datumAanvangVerblijfsrecht,
        final Integer datumMededelingVerblijfsrecht)
    {
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
        this.datumMededelingVerblijfsrecht = datumMededelingVerblijfsrecht;
        setPersoon(persoon);
        setVerblijfsrecht(verblijfsrecht);
    }

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de Id waarde voor de entiteit. Intern wordt de Id waarde geconverteert naar een Long voor integratie met
     * GegevenInOnderzoek.
     *
     * @param id
     *            de Id waarde.
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van datumAanvangVerblijfsrecht.
     *
     * @return datumAanvangVerblijfsrecht
     */
    public Integer getDatumAanvangVerblijfsrecht() {
        return datumAanvangVerblijfsrecht;
    }

    /**
     * Zet de waarde van datumAanvangVerblijfsrecht.
     *
     * @param datumAanvangVerblijfsrecht
     *            datumAanvangVerblijfsrecht
     */
    public void setDatumAanvangVerblijfsrecht(final int datumAanvangVerblijfsrecht) {
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
    }

    /**
     * Geef de waarde van datum mededeling verblijfsrecht.
     *
     * @return datum mededeling verblijfsrecht
     */
    public int getDatumMededelingVerblijfsrecht() {
        return datumMededelingVerblijfsrecht;
    }

    /**
     * Zet de waarde van datum mededeling verblijfsrecht.
     *
     * @param datumMededelingVerblijfsrecht
     *            datum mededeling verblijfsrecht
     */
    public void setDatumMededelingVerblijfsrecht(final int datumMededelingVerblijfsrecht) {
        this.datumMededelingVerblijfsrecht = datumMededelingVerblijfsrecht;
    }

    /**
     * Geef de waarde van datum voorzien einde verblijfsrecht.
     *
     * @return datum voorzien einde verblijfsrecht
     */
    public Integer getDatumVoorzienEindeVerblijfsrecht() {
        return datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * Zet de waarde van datum voorzien einde verblijfsrecht.
     *
     * @param datumVoorzienEindeVerblijfsrecht
     *            datum voorzien einde verblijfsrecht
     */
    public void setDatumVoorzienEindeVerblijfsrecht(final Integer datumVoorzienEindeVerblijfsrecht) {
        this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * Geef de waarde van persoon.
     *
     * @return persoon
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarde van persoon.
     *
     * @param persoon
     *            persoon
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van verblijfsrecht.
     *
     * @return verblijfsrecht
     */
    public Verblijfsrecht getVerblijfsrecht() {
        return verblijfsrecht;
    }

    /**
     * Zet de waarde van verblijfsrecht.
     *
     * @param verblijfsrecht
     *            verblijfsrecht
     */
    public void setVerblijfsrecht(final Verblijfsrecht verblijfsrecht) {
        ValidationUtils.controleerOpNullWaarden("verblijfsrecht mag niet null zijn", verblijfsrecht);
        this.verblijfsrecht = verblijfsrecht;
    }
}
