/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the toegangbijhautorisatie database table.
 *
 */
@Entity
@Table(name = "toegangbijhautorisatie", schema = "autaut",
        uniqueConstraints = @UniqueConstraint(columnNames = {"geautoriseerde", "bijhautorisatie", "ondertekenaar", "transporteur"}))
@NamedQuery(name = "ToegangBijhoudingsautorisatie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT t FROM ToegangBijhoudingsautorisatie t")
@Access(AccessType.FIELD)
public class ToegangBijhoudingsautorisatie implements Afleidbaar, Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "toegangbijhautorisatie_id_generator", sequenceName = "autaut.seq_toegangbijhautorisatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "toegangbijhautorisatie_id_generator")
    @Column(updatable = false)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "afleverpunt")
    private String afleverpunt;

    @Column(name = "datingang")
    private Integer datumIngang;

    // uni-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geautoriseerde", nullable = false)
    private PartijRol geautoriseerde;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    // uni-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ondertekenaar")
    private Partij ondertekenaar;

    // uni-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transporteur")
    private Partij transporteur;

    // uni-directional many-to-one association to Bijhoudingsautorisatie
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bijhautorisatie")
    private Bijhoudingsautorisatie bijhoudingsautorisatie;

    // bi-directional many-to-one association to ToegangBijhoudingsautorisatieHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(mappedBy = "toegangBijhoudingsautorisatie", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<ToegangBijhoudingsautorisatieHistorie> toegangBijhoudingsautorisatieHistorieSet = new LinkedHashSet<>(0);

    /**
     * JPA no-args constructor.
     */
    ToegangBijhoudingsautorisatie() {}

    /**
     * Maakt een nieuw ToegangBijhoudingsautorisatie object.
     *
     * @param geautoriseerde geautoriseerde partij rol, mag niet null zijn
     * @param bijhoudingsautorisatie bijhoudingsautorisatie, mag niet null zijn
     */
    public ToegangBijhoudingsautorisatie(final PartijRol geautoriseerde, final Bijhoudingsautorisatie bijhoudingsautorisatie) {
        setGeautoriseerde(geautoriseerde);
        setBijhoudingsautorisatie(bijhoudingsautorisatie);
    }

    /**
     * Geef de waarde van id van ToegangBijhoudingsautorisatie.
     *
     * @return de waarde van id van ToegangBijhoudingsautorisatie
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van ToegangBijhoudingsautorisatie.
     *
     * @param id de nieuwe waarde voor id van ToegangBijhoudingsautorisatie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum einde van ToegangBijhoudingsautorisatie.
     *
     * @return de waarde van datum einde van ToegangBijhoudingsautorisatie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van ToegangBijhoudingsautorisatie.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van ToegangBijhoudingsautorisatie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van afleverpunt van ToegangBijhoudingsautorisatie.
     *
     * @return de waarde van afleverpunt van ToegangBijhoudingsautorisatie
     */
    public String getAfleverpunt() {
        return afleverpunt;
    }

    /**
     * Zet de waarden voor afleverpunt van ToegangBijhoudingsautorisatie.
     *
     * @param afleverpunt de nieuwe waarde voor afleverpunt van ToegangBijhoudingsautorisatie
     */
    public void setAfleverpunt(final String afleverpunt) {
        this.afleverpunt = afleverpunt;
    }

    /**
     * Geef de waarde van datum ingang van ToegangBijhoudingsautorisatie.
     *
     * @return de waarde van datum ingang van ToegangBijhoudingsautorisatie
     */
    public Integer getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van ToegangBijhoudingsautorisatie.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van ToegangBijhoudingsautorisatie
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van geautoriseerde van ToegangBijhoudingsautorisatie.
     *
     * @return de waarde van geautoriseerde van ToegangBijhoudingsautorisatie
     */
    public PartijRol getGeautoriseerde() {
        return geautoriseerde;
    }

    /**
     * Zet de waarden voor geautoriseerde van ToegangBijhoudingsautorisatie.
     *
     * @param geautoriseerde de nieuwe waarde voor geautoriseerde van ToegangBijhoudingsautorisatie
     */
    public void setGeautoriseerde(final PartijRol geautoriseerde) {
        ValidationUtils.controleerOpNullWaarden("geautoriseerde mag niet null zijn", geautoriseerde);
        this.geautoriseerde = geautoriseerde;
    }

    /**
     * Geef de waarde van indicatie geblokkeerd van ToegangBijhoudingsautorisatie.
     *
     * @return de waarde van indicatie geblokkeerd van ToegangBijhoudingsautorisatie
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarden voor indicatie geblokkeerd van ToegangBijhoudingsautorisatie.
     *
     * @param indicatieGeblokkeerd de nieuwe waarde voor indicatie geblokkeerd van
     *        ToegangBijhoudingsautorisatie
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van isActueelEnGeldig.
     *
     * @return isActueelEnGeldig
     */
    public boolean isActueelEnGeldig() {
        return isActueelEnGeldig;
    }

    /**
     * Zet de waarde van isActueelEnGeldig.
     *
     * @param actueelEnGeldig isActueelEnGeldig
     */
    public void setActueelEnGeldig(final boolean actueelEnGeldig) {
        isActueelEnGeldig = actueelEnGeldig;
    }

    /**
     * Geef de waarde van ondertekenaar van ToegangBijhoudingsautorisatie.
     *
     * @return de waarde van ondertekenaar van ToegangBijhoudingsautorisatie
     */
    public Partij getOndertekenaar() {
        return ondertekenaar;
    }

    /**
     * Zet de waarden voor ondertekenaar van ToegangBijhoudingsautorisatie.
     *
     * @param ondertekenaar de nieuwe waarde voor ondertekenaar van ToegangBijhoudingsautorisatie
     */
    public void setOndertekenaar(final Partij ondertekenaar) {
        this.ondertekenaar = ondertekenaar;
    }

    /**
     * Geef de waarde van transporteur van ToegangBijhoudingsautorisatie.
     *
     * @return de waarde van transporteur van ToegangBijhoudingsautorisatie
     */
    public Partij getTransporteur() {
        return transporteur;
    }

    /**
     * Zet de waarden voor transporteur van ToegangBijhoudingsautorisatie.
     *
     * @param transporteur de nieuwe waarde voor transporteur van ToegangBijhoudingsautorisatie
     */
    public void setTransporteur(final Partij transporteur) {
        this.transporteur = transporteur;
    }

    /**
     * Geef de waarde van bijhoudingsautorisatie.
     *
     * @return bijhoudingsautorisatie
     */
    public Bijhoudingsautorisatie getBijhoudingsautorisatie() {
        return bijhoudingsautorisatie;
    }

    /**
     * Zet de waarde van bijhoudingsautorisatie.
     *
     * @param bijhoudingsautorisatie bijhoudingsautorisatie, mag niet null zijn
     */
    public void setBijhoudingsautorisatie(final Bijhoudingsautorisatie bijhoudingsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("bijhoudingsautorisatie mag niet null zijn", bijhoudingsautorisatie);
        this.bijhoudingsautorisatie = bijhoudingsautorisatie;
    }

    /**
     * Geef de waarde van toegang bijhoudingsautorisatie historie set van
     * ToegangBijhoudingsautorisatie.
     *
     * @return de waarde van toegang bijhoudingsautorisatie historie set van
     *         ToegangBijhoudingsautorisatie
     */
    public Set<ToegangBijhoudingsautorisatieHistorie> getToegangBijhoudingsautorisatieHistorieSet() {
        return toegangBijhoudingsautorisatieHistorieSet;
    }

    /**
     * Zet de waarden voor toegang bijhoudingsautorisatie historie set van
     * ToegangBijhoudingsautorisatie.
     *
     * @param toegangBijhoudingsautorisatieHistorieSet de nieuwe waarde voor toegang
     *        bijhoudingsautorisatie historie set van ToegangBijhoudingsautorisatie
     */
    public void setToegangBijhoudingsautorisatieHistorieSet(final Set<ToegangBijhoudingsautorisatieHistorie> toegangBijhoudingsautorisatieHistorieSet) {
        this.toegangBijhoudingsautorisatieHistorieSet = toegangBijhoudingsautorisatieHistorieSet;
    }

    /**
     * Voeg een toegangBijhoudingsautorisatieHistorie toe aan
     * toegangBijhoudingsautorisatieHistorieSet.
     *
     * @param toegangBijhoudingsautorisatieHistorie toegangBijhoudingsautorisatieHistorie
     */
    public void addToegangBijhoudingsautorisatieHistorieSet(final ToegangBijhoudingsautorisatieHistorie toegangBijhoudingsautorisatieHistorie) {
        getToegangBijhoudingsautorisatieHistorieSet().add(toegangBijhoudingsautorisatieHistorie);
        toegangBijhoudingsautorisatieHistorie.setToegangBijhoudingsautorisatie(this);
    }

    /**
     * Verwijder een toegangBijhoudingsautorisatieHistorie uit
     * toegangBijhoudingsautorisatieHistorieSet.
     *
     * @param toegangBijhoudingsautorisatieHistorie toegangBijhoudingsautorisatieHistorie
     * @return true als toegangBijhoudingsautorisatieHistorie is verwijderd anders false
     */
    public boolean removeToegangBijhoudingsautorisatieHistorieSet(final ToegangBijhoudingsautorisatieHistorie toegangBijhoudingsautorisatieHistorie) {
        return getToegangBijhoudingsautorisatieHistorieSet().remove(toegangBijhoudingsautorisatieHistorie);
    }
}
