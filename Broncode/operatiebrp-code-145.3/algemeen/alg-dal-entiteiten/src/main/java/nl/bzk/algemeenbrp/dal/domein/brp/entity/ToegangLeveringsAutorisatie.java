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
 * The persistent class for the toeganglevsautorisatie database table.
 *
 */
@Entity
@Table(name = "toeganglevsautorisatie", schema = "autaut",
        uniqueConstraints = @UniqueConstraint(columnNames = {"geautoriseerde", "levsautorisatie", "ondertekenaar", "transporteur"}))
@NamedQuery(name = "ToegangLeveringsAutorisatie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT t FROM ToegangLeveringsAutorisatie t")
@Access(AccessType.FIELD)
public class ToegangLeveringsAutorisatie implements Afleidbaar, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "toeganglevsautorisatie_id_generator", sequenceName = "autaut.seq_toeganglevsautorisatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "toeganglevsautorisatie_id_generator")
    @Column(updatable = false)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Column(name = "afleverpunt")
    private String afleverpunt;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang")
    private Integer datumIngang;

    // uni-directional many-to-one association to PartijRol
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geautoriseerde", nullable = false)
    private PartijRol geautoriseerde;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    @Column(name = "naderepopulatiebeperking")
    private String naderePopulatiebeperking;

    // uni-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ondertekenaar")
    private Partij ondertekenaar;

    // uni-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transporteur")
    private Partij transporteur;

    // bi-directional many-to-one association to ToegangLeveringsautorisatieHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toegangLeveringsautorisatie", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<ToegangLeveringsautorisatieHistorie> toegangLeveringsautorisatieHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Leveringsautorisatie
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "levsautorisatie", nullable = false)
    private Leveringsautorisatie leveringsautorisatie;

    /**
     * JPA no-args constructor.
     */
    ToegangLeveringsAutorisatie() {}

    /**
     * Maak een nieuw ToegangLeveringsAutorisatie object.
     *
     * @param geautoriseerde geautoriseerde
     * @param leveringsautorisatie leveringsautorisatie
     */
    public ToegangLeveringsAutorisatie(final PartijRol geautoriseerde, final Leveringsautorisatie leveringsautorisatie) {
        setGeautoriseerde(geautoriseerde);
        setLeveringsautorisatie(leveringsautorisatie);
    }

    /**
     * Geef de waarde van id van ToegangLeveringsAutorisatie.
     *
     * @return de waarde van id van ToegangLeveringsAutorisatie
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van ToegangLeveringsAutorisatie.
     *
     * @param id de nieuwe waarde voor id van ToegangLeveringsAutorisatie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum einde van ToegangLeveringsAutorisatie.
     *
     * @return de waarde van datum einde van ToegangLeveringsAutorisatie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van ToegangLeveringsAutorisatie.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van ToegangLeveringsAutorisatie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datum ingang van ToegangLeveringsAutorisatie.
     *
     * @return de waarde van datum ingang van ToegangLeveringsAutorisatie
     */
    public Integer getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van ToegangLeveringsAutorisatie.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van ToegangLeveringsAutorisatie
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van afleverpunt van ToegangLeveringsAutorisatie.
     *
     * @return de waarde van afleverpunt van ToegangLeveringsAutorisatie
     */
    public String getAfleverpunt() {
        return afleverpunt;
    }

    /**
     * Zet de waarden voor afleverpunt van ToegangLeveringsAutorisatie.
     *
     * @param afleverpunt de nieuwe waarde voor afleverpunt van ToegangLeveringsAutorisatie
     */
    public void setAfleverpunt(final String afleverpunt) {
        this.afleverpunt = afleverpunt;
    }

    /**
     * Geef de waarde van geautoriseerde van ToegangLeveringsAutorisatie.
     *
     * @return de waarde van geautoriseerde van ToegangLeveringsAutorisatie
     */
    public PartijRol getGeautoriseerde() {
        return geautoriseerde;
    }

    /**
     * Zet de waarden voor geautoriseerde van ToegangLeveringsAutorisatie.
     *
     * @param geautoriseerde de nieuwe waarde voor geautoriseerde van ToegangLeveringsAutorisatie
     */
    public void setGeautoriseerde(final PartijRol geautoriseerde) {
        ValidationUtils.controleerOpNullWaarden("geautoriseerde mag niet null zijn", geautoriseerde);
        this.geautoriseerde = geautoriseerde;
    }

    /**
     * Geef de waarde van indicatie geblokkeerd van ToegangLeveringsAutorisatie.
     *
     * @return de waarde van indicatie geblokkeerd van ToegangLeveringsAutorisatie
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarden voor indicatie geblokkeerd van ToegangLeveringsAutorisatie.
     *
     * @param indicatieGeblokkeerd de nieuwe waarde voor indicatie geblokkeerd van
     *        ToegangLeveringsAutorisatie
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
     * Geef de waarde van nadere populatiebeperking van ToegangLeveringsAutorisatie.
     *
     * @return de waarde van nadere populatiebeperking van ToegangLeveringsAutorisatie
     */
    public String getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Zet de waarden voor nadere populatiebeperking van ToegangLeveringsAutorisatie.
     *
     * @param naderePopulatiebeperking de nieuwe waarde voor nadere populatiebeperking van
     *        ToegangLeveringsAutorisatie
     */
    public void setNaderePopulatiebeperking(final String naderePopulatiebeperking) {
        this.naderePopulatiebeperking = naderePopulatiebeperking;
    }

    /**
     * Geef de waarde van ondertekenaar van ToegangLeveringsAutorisatie.
     *
     * @return de waarde van ondertekenaar van ToegangLeveringsAutorisatie
     */
    public Partij getOndertekenaar() {
        return ondertekenaar;
    }

    /**
     * Zet de waarden voor ondertekenaar van ToegangLeveringsAutorisatie.
     *
     * @param ondertekenaar de nieuwe waarde voor ondertekenaar van ToegangLeveringsAutorisatie
     */
    public void setOndertekenaar(final Partij ondertekenaar) {
        this.ondertekenaar = ondertekenaar;
    }

    /**
     * Geef de waarde van transporteur van ToegangLeveringsAutorisatie.
     *
     * @return de waarde van transporteur van ToegangLeveringsAutorisatie
     */
    public Partij getTransporteur() {
        return transporteur;
    }

    /**
     * Zet de waarden voor transporteur van ToegangLeveringsAutorisatie.
     *
     * @param transporteur de nieuwe waarde voor transporteur van ToegangLeveringsAutorisatie
     */
    public void setTransporteur(final Partij transporteur) {
        this.transporteur = transporteur;
    }

    /**
     * Geef de waarde van toegang leveringsautorisatie historie set van ToegangLeveringsAutorisatie.
     *
     * @return de waarde van toegang leveringsautorisatie historie set van
     *         ToegangLeveringsAutorisatie
     */
    public Set<ToegangLeveringsautorisatieHistorie> getToegangLeveringsautorisatieHistorieSet() {
        return toegangLeveringsautorisatieHistorieSet;
    }

    /**
     * Zet de waarden voor toegang leveringsautorisatie historie set van
     * ToegangLeveringsAutorisatie.
     *
     * @param toegangLeveringsautorisatieHistorieSet de nieuwe waarde voor toegang
     *        leveringsautorisatie historie set van ToegangLeveringsAutorisatie
     */
    public void setToegangLeveringsautorisatieHistorieSet(final Set<ToegangLeveringsautorisatieHistorie> toegangLeveringsautorisatieHistorieSet) {
        this.toegangLeveringsautorisatieHistorieSet = toegangLeveringsautorisatieHistorieSet;
    }

    /**
     * Voegt een toegangLeveringsautorisatieHistorie toe aan toegangLeveringsautorisatieHistorieSet.
     *
     * @param toegangLeveringsautorisatieHistorie toegangLeveringsautorisatieHistorie
     */
    public void addToegangLeveringsautorisatieHistorieSet(final ToegangLeveringsautorisatieHistorie toegangLeveringsautorisatieHistorie) {
        getToegangLeveringsautorisatieHistorieSet().add(toegangLeveringsautorisatieHistorie);
        toegangLeveringsautorisatieHistorie.setToegangLeveringsautorisatie(this);
    }

    /**
     * Verwijderd een toegangLeveringsautorisatieHistorie uit
     * toegangLeveringsautorisatieHistorieSet.
     *
     * @param toegangLeveringsautorisatieHistorie toegangLeveringsautorisatieHistorie
     * @return true als toegangLeveringsautorisatieHistorie is verwijderd, anders false
     */
    public boolean removeToegangLeveringsautorisatieHistorieSet(final ToegangLeveringsautorisatieHistorie toegangLeveringsautorisatieHistorie) {
        final boolean result = getToegangLeveringsautorisatieHistorieSet().remove(toegangLeveringsautorisatieHistorie);
        toegangLeveringsautorisatieHistorie.setToegangLeveringsautorisatie(null);
        return result;
    }

    /**
     * Geef de waarde van leveringsautorisatie van ToegangLeveringsAutorisatie.
     *
     * @return de waarde van leveringsautorisatie van ToegangLeveringsAutorisatie
     */
    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Zet de waarden voor leveringsautorisatie van ToegangLeveringsAutorisatie.
     *
     * @param leveringsautorisatie de nieuwe waarde voor leveringsautorisatie van
     *        ToegangLeveringsAutorisatie
     */
    public void setLeveringsautorisatie(final Leveringsautorisatie leveringsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("leveringsautorisatie mag niet null zijn", leveringsautorisatie);
        this.leveringsautorisatie = leveringsautorisatie;
    }
}
