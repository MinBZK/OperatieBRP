/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_persaanschr database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persafgeleidadministrati", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg"}))
public class PersoonAfgeleidAdministratiefHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persafgeleidadministrati_id_generator", sequenceName = "kern.seq_his_persafgeleidadministrati", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persafgeleidadministrati_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "sorteervolgorde", nullable = false)
    private short sorteervolgorde;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "admhnd", nullable = false)
    private AdministratieveHandeling administratieveHandeling;

    @Column(name = "tslaatstewijz", nullable = false)
    private Timestamp tijdstipLaatsteWijziging;

    @Column(name = "tslaatstewijzgbasystematiek", nullable = true)
    private Timestamp tijdstipLaatsteWijzigingGbaSystematiek;

    /**
     * JPA default constructor.
     */
    protected PersoonAfgeleidAdministratiefHistorie() {}

    /**
     * Maak een nieuwe persoon afgeleid administratief historie.
     *
     * @param sorteervolgorde sorteervolgorde
     * @param persoon persoon
     * @param administratieveHandeling administratieve handeling
     * @param datumTijdLaatsteWijziging datum tijd laatste wijziging
     */
    public PersoonAfgeleidAdministratiefHistorie(final short sorteervolgorde, final Persoon persoon, final AdministratieveHandeling administratieveHandeling,
            final Timestamp datumTijdLaatsteWijziging) {
        setSorteervolgorde(sorteervolgorde);
        setPersoon(persoon);
        setAdministratieveHandeling(administratieveHandeling);
        setDatumTijdLaatsteWijziging(datumTijdLaatsteWijziging);
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
     * Zet de waarden voor id van PersoonAfgeleidAdministratiefHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonAfgeleidAdministratiefHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van sorteervolgorde van PersoonAfgeleidAdministratiefHistorie.
     *
     * @return de waarde van sorteervolgorde van PersoonAfgeleidAdministratiefHistorie
     */
    public short getSorteervolgorde() {
        return sorteervolgorde;
    }

    /**
     * Zet de waarden voor sorteervolgorde van PersoonAfgeleidAdministratiefHistorie.
     *
     * @param sorteervolgorde de nieuwe waarde voor sorteervolgorde van
     *        PersoonAfgeleidAdministratiefHistorie
     */
    public void setSorteervolgorde(final short sorteervolgorde) {
        this.sorteervolgorde = sorteervolgorde;
    }

    /**
     * Geef de waarde van persoon van PersoonAfgeleidAdministratiefHistorie.
     *
     * @return de waarde van persoon van PersoonAfgeleidAdministratiefHistorie
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonAfgeleidAdministratiefHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonAfgeleidAdministratiefHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van administratieve handeling van PersoonAfgeleidAdministratiefHistorie.
     *
     * @return de waarde van administratieve handeling van PersoonAfgeleidAdministratiefHistorie
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet de waarden voor administratieve handeling van PersoonAfgeleidAdministratiefHistorie.
     *
     * @param administratieveHandeling de nieuwe waarde voor administratieve handeling van
     *        PersoonAfgeleidAdministratiefHistorie
     */
    public void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        ValidationUtils.controleerOpNullWaarden("administratieveHandeling mag niet null zijn", administratieveHandeling);
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Geef de waarde van datum tijd laatste wijziging van PersoonAfgeleidAdministratiefHistorie.
     *
     * @return de waarde van datum tijd laatste wijziging van PersoonAfgeleidAdministratiefHistorie
     */
    public Timestamp getDatumTijdLaatsteWijziging() {
        return Entiteit.timestamp(tijdstipLaatsteWijziging);
    }

    /**
     * Zet de waarden voor datum tijd laatste wijziging van PersoonAfgeleidAdministratiefHistorie.
     *
     * @param datumTijdLaatsteWijziging de nieuwe waarde voor datum tijd laatste wijziging van
     *        PersoonAfgeleidAdministratiefHistorie
     */
    public void setDatumTijdLaatsteWijziging(final Timestamp datumTijdLaatsteWijziging) {
        ValidationUtils.controleerOpNullWaarden("datumTijdLaatsteWijziging mag niet null zijn", datumTijdLaatsteWijziging);
        tijdstipLaatsteWijziging = Entiteit.timestamp(datumTijdLaatsteWijziging);
    }

    /**
     * Geef de waarde van datum tijd laatste wijziging gba van
     * PersoonAfgeleidAdministratiefHistorie.
     *
     * @return de waarde van datum tijd laatste wijziging gba van
     *         PersoonAfgeleidAdministratiefHistorie
     */
    public Timestamp getDatumTijdLaatsteWijzigingGba() {
        return Entiteit.timestamp(tijdstipLaatsteWijzigingGbaSystematiek);
    }

    /**
     * Zet de waarden voor datum tijd laatste wijziging gba van
     * PersoonAfgeleidAdministratiefHistorie.
     *
     * @param datumTijdLaatsteWijzigingGba de nieuwe waarde voor datum tijd laatste wijziging gba
     *        van PersoonAfgeleidAdministratiefHistorie
     */
    public void setDatumTijdLaatsteWijzigingGba(final Timestamp datumTijdLaatsteWijzigingGba) {
        tijdstipLaatsteWijzigingGbaSystematiek = Entiteit.timestamp(datumTijdLaatsteWijzigingGba);
    }
}
