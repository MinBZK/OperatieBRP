/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;

/**
 * The persistent class for the toegangbijhautorisatie database table.
 * 
 */
@Entity
@Table(name = "toegangbijhautorisatie", schema = "autaut")
@NamedQuery(name = "ToegangBijhoudingsautorisatie.findAll", query = "SELECT t FROM ToegangBijhoudingsautorisatie t")
@SuppressWarnings("checkstyle:designforextension")
public class ToegangBijhoudingsautorisatie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "toegangbijhautorisatie_id_generator", sequenceName = "autaut.seq_toegangbijhautorisatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "toegangbijhautorisatie_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang")
    private Integer datumIngang;

    // uni-directional many-to-one association to Partij
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geautoriseerde", nullable = false)
    private Partij geautoriseerde;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    // uni-directional many-to-one association to Partij
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ondertekenaar")
    private Partij ondertekenaar;

    // uni-directional many-to-one association to Partij
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transporteur")
    private Partij transporteur;

    // bi-directional many-to-one association to ToegangBijhoudingsautorisatieHistorie
    @OneToMany(mappedBy = "toegangBijhoudingsautorisatie", cascade = CascadeType.ALL)
    private Set<ToegangBijhoudingsautorisatieHistorie> toegangBijhoudingsautorisatieHistorieSet;

    /**
     * JPA no-args constructor.
     */
    ToegangBijhoudingsautorisatie() {
    }

    /**
     * Maakt een nieuw ToegangBijhoudingsautorisatie object.
     *
     * @param geautoriseerde
     *            geautoriseerde
     */
    public ToegangBijhoudingsautorisatie(final Partij geautoriseerde) {
        setGeautoriseerde(geautoriseerde);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datumEinde.
     *
     * @return datumEinde
     */
    public Integer getDatumEinde() {
        return this.datumEinde;
    }

    /**
     * Zet de waarde van datumEinde.
     *
     * @param datumEinde
     *            datumEinde
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datumIngang.
     *
     * @return datumIngang
     */
    public Integer getDatumIngang() {
        return this.datumIngang;
    }

    /**
     * Zet de waarde van datumIngang.
     *
     * @param datumIngang
     *            datumIngang
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van geautoriseerde.
     *
     * @return geautoriseerde
     */
    public Partij getGeautoriseerde() {
        return this.geautoriseerde;
    }

    /**
     * Zet de waarde van geautoriseerde.
     *
     * @param geautoriseerde
     *            geautoriseerde
     */
    public void setGeautoriseerde(final Partij geautoriseerde) {
        ValidationUtils.controleerOpNullWaarden("geautoriseerde mag niet null zijn", geautoriseerde);
        this.geautoriseerde = geautoriseerde;
    }

    /**
     * Geef de waarde van indicatieGeblokkeerd.
     *
     * @return indicatieGeblokkeerd
     */
    public Boolean getIndicatieGeblokkeerd() {
        return this.indicatieGeblokkeerd;
    }

    /**
     * Zet de waarde van indicatieGeblokkeerd.
     *
     * @param indicatieGeblokkeerd indicatieGeblokkeerd
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van ondertekenaar.
     *
     * @return ondertekenaar
     */
    public Partij getOndertekenaar() {
        return this.ondertekenaar;
    }

    /**
     * Zet de waarde van ondertekenaar.
     *
     * @param ondertekenaar
     *            ondertekenaar
     */
    public void setOndertekenaar(final Partij ondertekenaar) {
        this.ondertekenaar = ondertekenaar;
    }

    /**
     * Geef de waarde van transporteur.
     *
     * @return transporteur
     */
    public Partij getTransporteur() {
        return this.transporteur;
    }

    /**
     * Zet de waarde van transporteur.
     *
     * @param transporteur
     *            transporteur
     */
    public void setTransporteur(final Partij transporteur) {
        this.transporteur = transporteur;
    }

    /**
     * Geef de waarde van toegangBijhoudingsautorisatieHistorieSet.
     *
     * @return toegangBijhoudingsautorisatieHistorieSet
     */
    public Set<ToegangBijhoudingsautorisatieHistorie> getToegangBijhoudingsautorisatieHistorieSet() {
        return this.toegangBijhoudingsautorisatieHistorieSet;
    }

    /**
     * Zet de waarde van toegangBijhoudingsautorisatieHistorieSet.
     *
     * @param toegangBijhoudingsautorisatieHistorieSet
     *            toegangBijhoudingsautorisatieHistorieSet
     */
    public void setToegangBijhoudingsautorisatieHistorieSet(final Set<ToegangBijhoudingsautorisatieHistorie> toegangBijhoudingsautorisatieHistorieSet) {
        this.toegangBijhoudingsautorisatieHistorieSet = toegangBijhoudingsautorisatieHistorieSet;
    }

    /**
     * Voeg een toegangBijhoudingsautorisatieHistorie toe aan toegangBijhoudingsautorisatieHistorieSet.
     *
     * @param toegangBijhoudingsautorisatieHistorie
     *            toegangBijhoudingsautorisatieHistorie
     */
    public void addToegangBijhoudingsautorisatieHistorieSet(final ToegangBijhoudingsautorisatieHistorie toegangBijhoudingsautorisatieHistorie) {
        getToegangBijhoudingsautorisatieHistorieSet().add(toegangBijhoudingsautorisatieHistorie);
        toegangBijhoudingsautorisatieHistorie.setToegangBijhoudingsautorisatie(this);
    }

    /**
     * Verwijder een toegangBijhoudingsautorisatieHistorie uit toegangBijhoudingsautorisatieHistorieSet.
     *
     * @param toegangBijhoudingsautorisatieHistorie
     *            toegangBijhoudingsautorisatieHistorie
     * @return true als toegangBijhoudingsautorisatieHistorie is verwijderd anders false
     */
    public boolean removeToegangBijhoudingsautorisatieHistorieSet(final ToegangBijhoudingsautorisatieHistorie toegangBijhoudingsautorisatieHistorie) {
        final boolean result = getToegangBijhoudingsautorisatieHistorieSet().remove(toegangBijhoudingsautorisatieHistorie);
        toegangBijhoudingsautorisatieHistorie.setToegangBijhoudingsautorisatie(null);
        return result;
    }
}
