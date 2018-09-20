/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
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

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the his_partij database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_partij", schema = "kern", uniqueConstraints = {@UniqueConstraint(columnNames = {"partij", "tsreg" }) })
@SuppressWarnings("checkstyle:designforextension")
public class PartijHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_partij_id_generator", sequenceName = "kern.seq_his_partij", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_partij_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(nullable = false, insertable = true, updatable = true, length = 80, unique = true)
    private String naam;

    // uni-directional many-to-one association to SoortPartij
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "srt")
    private SoortPartij soortPartij;

    @Column(name = "datingang", nullable = false)
    private int datumIngang;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(insertable = true, updatable = true, length = 40)
    private String oin;

    // bi-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @Column(name = "indverstrbeperkingmogelijk", nullable = false)
    private Boolean indicatieVerstrekkingsbeperkingMogelijk;

    /**
     * JPA default constructor.
     */
    protected PartijHistorie() {
    }

    /**
     * Maak een nieuwe partij historie.
     *
     * @param partij
     *            partij
     * @param datumTijdRegistratie
     *            datumTijdRegistratie
     * @param datumIngang
     *            datum ingang
     * @param indicatieVerstrekkingsbeperkingMogelijk
     *            indicatie verstrekkingsbeperking mogelijk
     * @param naam
     *            naam
     */
    public PartijHistorie(
        final Partij partij,
        final Timestamp datumTijdRegistratie,
        final int datumIngang,
        final Boolean indicatieVerstrekkingsbeperkingMogelijk,
        final String naam)
    {
        setPartij(partij);
        setDatumTijdRegistratie(datumTijdRegistratie);
        setDatumIngang(datumIngang);
        setIndicatieVerstrekkingsbeperkingMogelijk(indicatieVerstrekkingsbeperkingMogelijk);
        setNaam(naam);
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
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarde van naam.
     *
     * @param naam
     *            naam
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        Validatie.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van soort partij.
     *
     * @return soort partij
     */
    public SoortPartij getSoortPartij() {
        return soortPartij;
    }

    /**
     * Zet de waarde van soort partij.
     *
     * @param soortPartij soort partij
     */
    public void setSoortPartij(final SoortPartij soortPartij) {
        this.soortPartij = soortPartij;
    }

    /**
     * Geef de waarde van datum ingang.
     *
     * @return datum ingang
     */
    public int getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarde van datum ingang.
     *
     * @param datumIngang
     *            datum ingang
     */
    public void setDatumIngang(final int datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van datum einde.
     *
     * @return datum einde
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarde van datum einde.
     *
     * @param datumEinde
     *            datum einde
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van oin.
     *
     * @return oin
     */
    public String getOin() {
        return oin;
    }

    /**
     * Zet de waarde van oin.
     *
     * @param oin
     *            oin
     */
    public void setOin(final String oin) {
        this.oin = oin;
    }

    /**
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarde van partij.
     *
     * @param partij
     *            partij
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

    /**
     * Controleert op indicatie verstrekkingsbeperking mogelijk.
     *
     * @return boolean
     */
    public Boolean isIndicatieVerstrekkingsbeperkingMogelijk() {
        return indicatieVerstrekkingsbeperkingMogelijk;
    }

    /**
     * Zet de waarde van indicatie verstrekkingsbeperking mogelijk.
     *
     * @param indicatieVerstrekkingsbeperkingMogelijk
     *            indicatie verstrekkingsbeperking mogelijk
     */
    public void setIndicatieVerstrekkingsbeperkingMogelijk(final Boolean indicatieVerstrekkingsbeperkingMogelijk) {
        ValidationUtils.controleerOpNullWaarden("indicatieVerstrekkingsbeperkingMogelijk mag niet null zijn", indicatieVerstrekkingsbeperkingMogelijk);
        this.indicatieVerstrekkingsbeperkingMogelijk = indicatieVerstrekkingsbeperkingMogelijk;
    }

    /**
     * Bepaal alle administratieve handelingen.
     *
     * @return zet de
     */
    public final Set<AdministratieveHandeling> bepaalAlleAdministratieveHandelingen() {
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();

        if (getActieInhoud() != null) {
            administratieveHandelingen.add(getActieInhoud().getAdministratieveHandeling());
        }
        if (getActieVerval() != null) {
            administratieveHandelingen.add(getActieVerval().getAdministratieveHandeling());
        }

        return administratieveHandelingen;
    }
}
