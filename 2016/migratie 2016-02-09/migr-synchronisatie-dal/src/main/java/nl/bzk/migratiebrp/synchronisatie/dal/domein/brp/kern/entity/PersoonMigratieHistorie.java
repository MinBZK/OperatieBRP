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
 * The persistent class for the his_persimmigratie database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persmigratie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg", "dataanvgel" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonMigratieHistorie extends AbstractMaterieleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persimmigratie_id_generator", sequenceName = "kern.seq_his_persmigratie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persimmigratie_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebiedmigratie")
    private LandOfGebied landOfGebied;

    @Column(name = "srtmigratie")
    private short soortMigratieId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdnwijzmigratie")
    private RedenWijzigingVerblijf redenWijzigingMigratie;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "aangmigratie")
    private Aangever aangeverMigratie;

    @Column(name = "bladresregel1migratie")
    private String buitenlandsAdresRegel1;
    @Column(name = "bladresregel2migratie")
    private String buitenlandsAdresRegel2;
    @Column(name = "bladresregel3migratie")
    private String buitenlandsAdresRegel3;
    @Column(name = "bladresregel4migratie")
    private String buitenlandsAdresRegel4;
    @Column(name = "bladresregel5migratie")
    private String buitenlandsAdresRegel5;
    @Column(name = "bladresregel6migratie")
    private String buitenlandsAdresRegel6;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonMigratieHistorie() {
    }

    /**
     * Maak een nieuwe persoon migratie historie.
     *
     * @param persoon
     *            persoon
     * @param soortMigratie
     *            soort migratie
     */
    public PersoonMigratieHistorie(final Persoon persoon, final SoortMigratie soortMigratie) {
        setPersoon(persoon);
        setSoortMigratie(soortMigratie);
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
     * Geef de waarde van land of gebied.
     *
     * @return land of gebied
     */
    public LandOfGebied getLandOfGebied() {
        return landOfGebied;
    }

    /**
     * Zet de waarde van land of gebied.
     *
     * @param landOfGebied
     *            land of gebied
     */
    public void setLandOfGebied(final LandOfGebied landOfGebied) {
        this.landOfGebied = landOfGebied;
    }

    /**
     * Geef de waarde van soort migratie.
     *
     * @return soort migratie
     */
    public SoortMigratie getSoortMigratie() {
        return SoortMigratie.parseId(soortMigratieId);
    }

    /**
     * Zet de waarde van soort migratie.
     *
     * @param soortMigratie
     *            soort migratie
     */
    public void setSoortMigratie(final SoortMigratie soortMigratie) {
        ValidationUtils.controleerOpNullWaarden("soortMigratie mag niet null zijn", soortMigratie);
        soortMigratieId = soortMigratie.getId();
    }

    /**
     * Geef de waarde van reden wijziging migratie.
     *
     * @return reden wijziging migratie
     */
    public RedenWijzigingVerblijf getRedenWijzigingMigratie() {
        return redenWijzigingMigratie;
    }

    /**
     * Zet de waarde van reden wijziging migratie.
     *
     * @param redenWijzigingMigratie
     *            reden wijziging migratie
     */
    public void setRedenWijzigingMigratie(final RedenWijzigingVerblijf redenWijzigingMigratie) {
        this.redenWijzigingMigratie = redenWijzigingMigratie;
    }

    /**
     * Geef de waarde van aangever migratie.
     *
     * @return aangever migratie
     */
    public Aangever getAangeverMigratie() {
        return aangeverMigratie;
    }

    /**
     * Zet de waarde van aangever migratie.
     *
     * @param aangeverMigratie
     *            aangever migratie
     */
    public void setAangeverMigratie(final Aangever aangeverMigratie) {
        this.aangeverMigratie = aangeverMigratie;
    }

    /**
     * Geef de waarde van buitenlands adres regel1.
     *
     * @return buitenlands adres regel1
     */
    public String getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * Zet de waarde van buitenlands adres regel1.
     *
     * @param buitenlandsAdresRegel1
     *            buitenlands adres regel1
     */
    public void setBuitenlandsAdresRegel1(final String buitenlandsAdresRegel1) {
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
    }

    /**
     * Geef de waarde van buitenlands adres regel2.
     *
     * @return buitenlands adres regel2
     */
    public String getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * Zet de waarde van buitenlands adres regel2.
     *
     * @param buitenlandsAdresRegel2
     *            buitenlands adres regel2
     */
    public void setBuitenlandsAdresRegel2(final String buitenlandsAdresRegel2) {
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
    }

    /**
     * Geef de waarde van buitenlands adres regel3.
     *
     * @return buitenlands adres regel3
     */
    public String getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * Zet de waarde van buitenlands adres regel3.
     *
     * @param buitenlandsAdresRegel3
     *            buitenlands adres regel3
     */
    public void setBuitenlandsAdresRegel3(final String buitenlandsAdresRegel3) {
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
    }

    /**
     * Geef de waarde van buitenlands adres regel4.
     *
     * @return buitenlands adres regel4
     */
    public String getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * Zet de waarde van buitenlands adres regel4.
     *
     * @param buitenlandsAdresRegel4
     *            buitenlands adres regel4
     */
    public void setBuitenlandsAdresRegel4(final String buitenlandsAdresRegel4) {
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
    }

    /**
     * Geef de waarde van buitenlands adres regel5.
     *
     * @return buitenlands adres regel5
     */
    public String getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * Zet de waarde van buitenlands adres regel5.
     *
     * @param buitenlandsAdresRegel5
     *            buitenlands adres regel5
     */
    public void setBuitenlandsAdresRegel5(final String buitenlandsAdresRegel5) {
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
    }

    /**
     * Geef de waarde van buitenlands adres regel6.
     *
     * @return buitenlands adres regel6
     */
    public String getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    /**
     * Zet de waarde van buitenlands adres regel6.
     *
     * @param buitenlandsAdresRegel6
     *            buitenlands adres regel6
     */
    public void setBuitenlandsAdresRegel6(final String buitenlandsAdresRegel6) {
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
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
