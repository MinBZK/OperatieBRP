/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * The persistent class for the his_persaanschr database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persafgeleidadministrati", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonAfgeleidAdministratiefHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persafgeleidadministrati_id_generator", sequenceName = "kern.seq_his_persafgeleidadministrati", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persafgeleidadministrati_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "Sorteervolgorde", nullable = false)
    private short sorteervolgorde;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "admhnd", nullable = false)
    private AdministratieveHandeling administratieveHandeling;

    @Column(name = "tslaatstewijz", nullable = false)
    private Timestamp datumTijdLaatsteWijziging;

    @Column(name = "tslaatstewijzgbasystematiek", nullable = true)
    private Timestamp datumTijdLaatsteWijzigingGba;

    @Column(name = "indonverwbijhvoorstelnieting", nullable = false)
    private boolean indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;

    /**
     * JPA default constructor.
     */
    protected PersoonAfgeleidAdministratiefHistorie() {
    }

    /**
     * Maak een nieuwe persoon afgeleid administratief historie.
     *
     * @param sorteervolgorde
     *            sorteervolgorde
     * @param persoon
     *            persoon
     * @param administratieveHandeling
     *            administratieve handeling
     * @param datumTijdLaatsteWijziging
     *            datum tijd laatste wijziging
     * @param indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig
     *            indicatie onverwerkt bijhoudingsvoorstel niet ingezetene aanwezig
     */
    public PersoonAfgeleidAdministratiefHistorie(
        final short sorteervolgorde,
        final Persoon persoon,
        final AdministratieveHandeling administratieveHandeling,
        final Timestamp datumTijdLaatsteWijziging,
        final boolean indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig)
    {
        setSorteervolgorde(sorteervolgorde);
        setPersoon(persoon);
        setAdministratieveHandeling(administratieveHandeling);
        setDatumTijdLaatsteWijziging(datumTijdLaatsteWijziging);

        this.indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig = indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;
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
     * Geef de waarde van sorteervolgorde.
     *
     * @return sorteervolgorde
     */
    public short getSorteervolgorde() {
        return sorteervolgorde;
    }

    /**
     * Zet de waarde van sorteervolgorde.
     *
     * @param sorteervolgorde
     *            sorteervolgorde
     */
    public void setSorteervolgorde(final short sorteervolgorde) {
        this.sorteervolgorde = sorteervolgorde;
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
     * Geef de waarde van administratieve handeling.
     *
     * @return administratieve handeling
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet de waarde van administratieve handeling.
     *
     * @param administratieveHandeling
     *            administratieve handeling
     */
    public void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        ValidationUtils.controleerOpNullWaarden("administratieveHandeling mag niet null zijn", administratieveHandeling);
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Geef de waarde van datum tijd laatste wijziging.
     *
     * @return datum tijd laatste wijziging
     */
    public Timestamp getDatumTijdLaatsteWijziging() {
        return Kopieer.timestamp(datumTijdLaatsteWijziging);
    }

    /**
     * Zet de waarde van datum tijd laatste wijziging.
     *
     * @param datumTijdLaatsteWijziging
     *            datum tijd laatste wijziging
     */
    public void setDatumTijdLaatsteWijziging(final Timestamp datumTijdLaatsteWijziging) {
        ValidationUtils.controleerOpNullWaarden("datumTijdLaatsteWijziging mag niet null zijn", datumTijdLaatsteWijziging);
        this.datumTijdLaatsteWijziging = Kopieer.timestamp(datumTijdLaatsteWijziging);
    }

    /**
     * Geef de waarde van datum tijd laatste wijziging gba.
     *
     * @return datum tijd laatste wijziging gba
     */
    public Timestamp getDatumTijdLaatsteWijzigingGba() {
        return Kopieer.timestamp(datumTijdLaatsteWijzigingGba);
    }

    /**
     * Zet de waarde van datum tijd laatste wijziging gba.
     *
     * @param datumTijdLaatsteWijzigingGba
     *            datum tijd laatste wijziging gba
     */
    public void setDatumTijdLaatsteWijzigingGba(final Timestamp datumTijdLaatsteWijzigingGba) {
        this.datumTijdLaatsteWijzigingGba = Kopieer.timestamp(datumTijdLaatsteWijzigingGba);
    }

    /**
     * Geef de waarde van indicatie onverwerkt bijhoudingsvoorstel niet ingezetene aanwezig.
     *
     * @return indicatie onverwerkt bijhoudingsvoorstel niet ingezetene aanwezig
     */
    public boolean getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig() {
        return indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;
    }

    /**
     * Zet de waarde van indicatie onverwerkt bijhoudingsvoorstel niet ingezetene aanwezig.
     *
     * @param indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig
     *            indicatie onverwerkt bijhoudingsvoorstel niet ingezetene aanwezig
     */
    public void setIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(final boolean indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig) {
        this.indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig = indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;
    }
}
