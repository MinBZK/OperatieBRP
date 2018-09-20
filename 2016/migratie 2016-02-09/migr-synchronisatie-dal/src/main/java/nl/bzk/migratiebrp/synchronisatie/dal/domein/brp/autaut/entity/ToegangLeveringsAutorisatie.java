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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijRol;

/**
 * The persistent class for the toeganglevsautorisatie database table.
 *
 */
@Entity
@Table(name = "toeganglevsautorisatie", schema = "autaut")
@NamedQuery(name = "ToegangLeveringsAutorisatie.findAll", query = "SELECT t FROM ToegangLeveringsAutorisatie t")
@SuppressWarnings("checkstyle:designforextension")
public class ToegangLeveringsAutorisatie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "toeganglevsautorisatie_id_generator", sequenceName = "autaut.seq_toeganglevsautorisatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "toeganglevsautorisatie_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "afleverpunt")
    private String afleverpunt;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang")
    private Integer datumIngang;

    // uni-directional many-to-one association to PartijRol
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geautoriseerde", nullable = false)
    private PartijRol geautoriseerde;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "naderepopulatiebeperking")
    private String naderePopulatiebeperking;

    // uni-directional many-to-one association to Partij
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ondertekenaar")
    private Partij ondertekenaar;

    // uni-directional many-to-one association to Partij
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transporteur")
    private Partij transporteur;

    // bi-directional many-to-one association to ToegangLeveringsautorisatieHistorie
    @OneToMany(mappedBy = "toegangLeveringsautorisatie", cascade = CascadeType.ALL)
    private Set<ToegangLeveringsautorisatieHistorie> toegangLeveringsautorisatieHistorieSet;

    // bi-directional many-to-one association to Leveringsautorisatie
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "levsautorisatie", nullable = false)
    private Leveringsautorisatie leveringsautorisatie;

    /**
     * JPA no-args constructor.
     */
    ToegangLeveringsAutorisatie() {
    }

    /**
     * Maak een nieuw ToegangLeveringsAutorisatie object.
     *
     * @param geautoriseerde
     *            geautoriseerde
     * @param leveringsautorisatie
     *            leveringsautorisatie
     */
    public ToegangLeveringsAutorisatie(final PartijRol geautoriseerde, final Leveringsautorisatie leveringsautorisatie) {
        setGeautoriseerde(geautoriseerde);
        setLeveringsautorisatie(leveringsautorisatie);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Integer getId() {
        return id;
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
        return datumEinde;
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
        return datumIngang;
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
     * Geef de waarde van afleverpunt.
     *
     * @return afleverpunt
     */
    public String getAfleverpunt() {
        return afleverpunt;
    }

    /**
     * Zet de waarde van afleverpunt.
     *
     * @param afleverpunt
     *            afleverpunt
     */
    public void setAfleverpunt(final String afleverpunt) {
        this.afleverpunt = afleverpunt;
    }

    /**
     * Geef de waarde van geautoriseerde.
     *
     * @return geautoriseerde
     */
    public PartijRol getGeautoriseerde() {
        return geautoriseerde;
    }

    /**
     * Zet de waarde van geautoriseerde.
     *
     * @param geautoriseerde
     *            geautoriseerde
     */
    public void setGeautoriseerde(final PartijRol geautoriseerde) {
        ValidationUtils.controleerOpNullWaarden("geautoriseerde mag niet null zijn", geautoriseerde);
        this.geautoriseerde = geautoriseerde;
    }

    /**
     * Geef de waarde van indicatieGeblokkeerd.
     *
     * @return indicatieGeblokkeerd
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarde van indicatieGeblokkeerd.
     *
     * @param indicatieGeblokkeerd
     *            indicatieGeblokkeerd
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van naderePopulatiebeperking.
     *
     * @return naderePopulatiebeperking
     */
    public String getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Zet de waarde van naderePopulatiebeperking.
     *
     * @param naderePopulatiebeperking
     *            naderePopulatiebeperking
     */
    public void setNaderePopulatiebeperking(final String naderePopulatiebeperking) {
        this.naderePopulatiebeperking = naderePopulatiebeperking;
    }

    /**
     * Geef de waarde van ondertekenaar.
     *
     * @return ondertekenaar
     */
    public Partij getOndertekenaar() {
        return ondertekenaar;
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
        return transporteur;
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
     * Geef de waarde van toegangLeveringsautorisatieHistorieSet.
     *
     * @return toegangLeveringsautorisatieHistorieSet
     */
    public Set<ToegangLeveringsautorisatieHistorie> getToegangLeveringsautorisatieHistorieSet() {
        return toegangLeveringsautorisatieHistorieSet;
    }

    /**
     * Zet de waarde van toegangLeveringsautorisatieHistorieSet.
     *
     * @param toegangLeveringsautorisatieHistorieSet
     *            toegangLeveringsautorisatieHistorieSet
     */
    public void setToegangLeveringsautorisatieHistorieSet(final Set<ToegangLeveringsautorisatieHistorie> toegangLeveringsautorisatieHistorieSet) {
        this.toegangLeveringsautorisatieHistorieSet = toegangLeveringsautorisatieHistorieSet;
    }

    /**
     * Voegt een toegangLeveringsautorisatieHistorie toe aan toegangLeveringsautorisatieHistorieSet.
     *
     * @param toegangLeveringsautorisatieHistorie
     *            toegangLeveringsautorisatieHistorie
     */
    public void addToegangLeveringsautorisatieHistorieSet(final ToegangLeveringsautorisatieHistorie toegangLeveringsautorisatieHistorie) {
        getToegangLeveringsautorisatieHistorieSet().add(toegangLeveringsautorisatieHistorie);
        toegangLeveringsautorisatieHistorie.setToegangLeveringsautorisatie(this);
    }

    /**
     * Verwijderd een toegangLeveringsautorisatieHistorie uit toegangLeveringsautorisatieHistorieSet.
     *
     * @param toegangLeveringsautorisatieHistorie
     *            toegangLeveringsautorisatieHistorie
     * @return true als toegangLeveringsautorisatieHistorie is verwijderd, anders false
     */
    public boolean removeToegangLeveringsautorisatieHistorieSet(final ToegangLeveringsautorisatieHistorie toegangLeveringsautorisatieHistorie) {
        final boolean result = getToegangLeveringsautorisatieHistorieSet().remove(toegangLeveringsautorisatieHistorie);
        toegangLeveringsautorisatieHistorie.setToegangLeveringsautorisatie(null);
        return result;
    }

    /**
     * Geef de waarde van leveringsautorisatie.
     *
     * @return leveringsautorisatie
     */
    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Zet de waarde van leveringsautorisatie.
     *
     * @param leveringsautorisatie
     *            leveringsautorisatie
     */
    public void setLeveringsautorisatie(final Leveringsautorisatie leveringsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("leveringsautorisatie mag niet null zijn", leveringsautorisatie);
        this.leveringsautorisatie = leveringsautorisatie;
    }
}
