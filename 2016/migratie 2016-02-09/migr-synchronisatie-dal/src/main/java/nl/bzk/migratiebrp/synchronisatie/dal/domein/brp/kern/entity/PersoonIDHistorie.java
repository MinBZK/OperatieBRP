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
 * The persistent class for the his_persids database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persids", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg", "dataanvgel" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonIDHistorie extends AbstractMaterieleHistorie implements Serializable {

    /**
     * Melding indien een persoon null is.
     */
    public static final String PERSOON_MAG_NIET_NULL_ZIJN = "persoon mag niet null zijn";

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persids_id_generator", sequenceName = "kern.seq_his_persids", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persids_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "anr", precision = 10)
    private Long administratienummer;

    @Column(name = "bsn", precision = 9)
    private Integer burgerservicenummer;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonIDHistorie() {
    }

    /**
     * Maak een nieuwe persoon id historie.
     *
     * @param persoon
     *            persoon
     */
    public PersoonIDHistorie(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
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
     * Geef de waarde van administratienummer.
     *
     * @return administratienummer
     */
    public Long getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Zet de waarde van administratienummer.
     *
     * @param administratienummer
     *            administratienummer
     */
    public void setAdministratienummer(final Long administratienummer) {
        this.administratienummer = administratienummer;
    }

    /**
     * Geef de waarde van burgerservicenummer.
     *
     * @return burgerservicenummer
     */
    public Integer getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * Zet de waarde van burgerservicenummer.
     *
     * @param burgerservicenummer
     *            burgerservicenummer
     */
    public void setBurgerservicenummer(final Integer burgerservicenummer) {
        this.burgerservicenummer = burgerservicenummer;
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
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
    }
}
