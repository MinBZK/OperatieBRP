/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * The persistent class for the his_partij database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_partij", schema = "kern", uniqueConstraints = {@UniqueConstraint(columnNames = {"partij", "tsreg"})})
public class PartijHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_partij_id_generator", sequenceName = "kern.seq_his_partij", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_partij_id_generator")
    @Column(nullable = false)
    /*
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(nullable = false, length = 80, unique = true)
    private String naam;

    // uni-directional many-to-one association to SoortPartij
    @ManyToOne
    @JoinColumn(name = "srt")
    private SoortPartij soortPartij;

    @Column(name = "datingang", nullable = false)
    private int datumIngang;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(length = 40)
    private String oin;

    // bi-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @Column(name = "indverstrbeperkingmogelijk", nullable = false)
    private Boolean indicatieVerstrekkingsbeperkingMogelijk;

    @Column(name = "datovergangnaarbrp")
    private Integer datumOvergangNaarBrp;

    /**
     * JPA default constructor.
     */
    protected PartijHistorie() {}

    /**
     * Maak een nieuwe partij historie.
     *
     * @param partij partij
     * @param datumTijdRegistratie datumTijdRegistratie
     * @param datumIngang datum ingang
     * @param indicatieVerstrekkingsbeperkingMogelijk indicatie verstrekkingsbeperking mogelijk
     * @param naam naam
     */
    public PartijHistorie(final Partij partij, final Timestamp datumTijdRegistratie, final int datumIngang,
            final Boolean indicatieVerstrekkingsbeperkingMogelijk, final String naam) {
        setPartij(partij);
        setDatumTijdRegistratie(datumTijdRegistratie);
        setDatumIngang(datumIngang);
        setIndicatieVerstrekkingsbeperkingMogelijk(indicatieVerstrekkingsbeperkingMogelijk);
        setNaam(naam);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de waarden voor id van PartijHistorie.
     *
     * @param id de nieuwe waarde voor id van PartijHistorie
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van naam van PartijHistorie.
     *
     * @return de waarde van naam van PartijHistorie
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van PartijHistorie.
     *
     * @param naam de nieuwe waarde voor naam van PartijHistorie
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        ValidationUtils.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van soort partij van PartijHistorie.
     *
     * @return de waarde van soort partij van PartijHistorie
     */
    public SoortPartij getSoortPartij() {
        return soortPartij;
    }

    /**
     * Zet de waarden voor soort partij van PartijHistorie.
     *
     * @param soortPartij de nieuwe waarde voor soort partij van PartijHistorie
     */
    public void setSoortPartij(final SoortPartij soortPartij) {
        this.soortPartij = soortPartij;
    }

    /**
     * Geef de waarde van datum ingang van PartijHistorie.
     *
     * @return de waarde van datum ingang van PartijHistorie
     */
    public int getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van PartijHistorie.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van PartijHistorie
     */
    public void setDatumIngang(final int datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van datum einde van PartijHistorie.
     *
     * @return de waarde van datum einde van PartijHistorie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van PartijHistorie.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van PartijHistorie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van oin van PartijHistorie.
     *
     * @return de waarde van oin van PartijHistorie
     */
    public String getOin() {
        return oin;
    }

    /**
     * Zet de waarden voor oin van PartijHistorie.
     *
     * @param oin de nieuwe waarde voor oin van PartijHistorie
     */
    public void setOin(final String oin) {
        this.oin = oin;
    }

    /**
     * Geef de waarde van partij van PartijHistorie.
     *
     * @return de waarde van partij van PartijHistorie
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarden voor partij van PartijHistorie.
     *
     * @param partij de nieuwe waarde voor partij van PartijHistorie
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
     * Zet de waarden voor indicatie verstrekkingsbeperking mogelijk van PartijHistorie.
     *
     * @param indicatieVerstrekkingsbeperkingMogelijk de nieuwe waarde voor indicatie
     *        verstrekkingsbeperking mogelijk van PartijHistorie
     */
    public void setIndicatieVerstrekkingsbeperkingMogelijk(final Boolean indicatieVerstrekkingsbeperkingMogelijk) {
        ValidationUtils.controleerOpNullWaarden("indicatieVerstrekkingsbeperkingMogelijk mag niet null zijn", indicatieVerstrekkingsbeperkingMogelijk);
        this.indicatieVerstrekkingsbeperkingMogelijk = indicatieVerstrekkingsbeperkingMogelijk;
    }

    /**
     * Geeft de waarde van datumOvergangNaarBrp.
     * @return datumOvergangNaarBrp datumOvergangNaarBrp
     */
    public Integer getDatumOvergangNaarBrp() {
        return datumOvergangNaarBrp;
    }

    /**
     * Zet de waarde van datumOvergangNaarBrp.
     * @param datumOvergangNaarBrp datumOvergangNaarBrp
     */
    public void setDatumOvergangNaarBrp(Integer datumOvergangNaarBrp) {
        this.datumOvergangNaarBrp = datumOvergangNaarBrp;
    }
}
