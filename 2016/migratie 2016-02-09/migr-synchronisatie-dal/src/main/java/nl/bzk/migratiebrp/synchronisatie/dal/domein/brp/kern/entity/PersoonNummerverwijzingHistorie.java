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
 * The persistent class for the his_persnrverwijzing database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persnrverwijzing", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg", "dataanvgel" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonNummerverwijzingHistorie extends AbstractMaterieleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persnrverwijzing_id_generator", sequenceName = "kern.seq_his_persnrverwijzing", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persnrverwijzing_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "vorigebsn")
    private Integer vorigeBurgerservicenummer;

    @Column(name = "volgendebsn")
    private Integer volgendeBurgerservicenummer;

    @Column(name = "vorigeanr")
    private Long vorigeAdministratienummer;

    @Column(name = "volgendeanr")
    private Long volgendeAdministratienummer;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonNummerverwijzingHistorie() {
    }

    /**
     * Maak een nieuwe persoon nummerverwijzing historie.
     *
     * @param persoon
     *            persoon
     */
    public PersoonNummerverwijzingHistorie(final Persoon persoon) {
        setPersoon(persoon);
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
     * Geef de waarde van vorige burgerservicenummer.
     *
     * @return vorige burgerservicenummer
     */
    public Integer getVorigeBurgerservicenummer() {
        return vorigeBurgerservicenummer;
    }

    /**
     * Zet de waarde van vorige burgerservicenummer.
     *
     * @param vorigeBurgerservicenummer
     *            vorige burgerservicenummer
     */
    public void setVorigeBurgerservicenummer(final Integer vorigeBurgerservicenummer) {
        this.vorigeBurgerservicenummer = vorigeBurgerservicenummer;
    }

    /**
     * Geef de waarde van volgende burgerservicenummer.
     *
     * @return volgende burgerservicenummer
     */
    public Integer getVolgendeBurgerservicenummer() {
        return volgendeBurgerservicenummer;
    }

    /**
     * Zet de waarde van volgende burgerservicenummer.
     *
     * @param volgendeBurgerservicenummer
     *            volgende burgerservicenummer
     */
    public void setVolgendeBurgerservicenummer(final Integer volgendeBurgerservicenummer) {
        this.volgendeBurgerservicenummer = volgendeBurgerservicenummer;
    }

    /**
     * Geef de waarde van vorige administratienummer.
     *
     * @return vorige administratienummer
     */
    public Long getVorigeAdministratienummer() {
        return vorigeAdministratienummer;
    }

    /**
     * Zet de waarde van vorige administratienummer.
     *
     * @param vorigeAdministratienummer
     *            vorige administratienummer
     */
    public void setVorigeAdministratienummer(final Long vorigeAdministratienummer) {
        this.vorigeAdministratienummer = vorigeAdministratienummer;
    }

    /**
     * Geef de waarde van volgende administratienummer.
     *
     * @return volgende administratienummer
     */
    public Long getVolgendeAdministratienummer() {
        return volgendeAdministratienummer;
    }

    /**
     * Zet de waarde van volgende administratienummer.
     *
     * @param volgendeAdministratienummer
     *            volgende administratienummer
     */
    public void setVolgendeAdministratienummer(final Long volgendeAdministratienummer) {
        this.volgendeAdministratienummer = volgendeAdministratienummer;
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
}
