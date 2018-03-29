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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the bijhautorisatie database table.
 *
 */
@Entity
@Table(name = "bijhautorisatie", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"naam"}))
@NamedQuery(name = "Bijhoudingsautorisatie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT b FROM Bijhoudingsautorisatie b")
@Access(AccessType.FIELD)
public class Bijhoudingsautorisatie implements Afleidbaar, DynamischeStamtabel, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "bijhautorisatie_id_generator", sequenceName = "autaut.seq_bijhautorisatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bijhautorisatie_id_generator")
    @Column(updatable = false)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Column(name = "indmodelautorisatie", nullable = false)
    private boolean indicatieModelautorisatie;

    @Column(name = "naam", length = 80)
    private String naam;

    @Column(name = "datingang")
    private Integer datumIngang;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    // bi-directional many-to-one association to BijhoudingsautorisatieHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bijhoudingsautorisatie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<BijhoudingsautorisatieHistorie> bijhoudingsautorisatieHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to bijhoudingsautorisatieSoortAdministratieveHandeling
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bijhoudingsautorisatie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<BijhoudingsautorisatieSoortAdministratieveHandeling> bijhoudingsautorisatieSoortAdministratieveHandelingSet = new LinkedHashSet<>(0);

    /**
     * JPA no-args constructor.
     */
    Bijhoudingsautorisatie() {}

    /**
     * Maakt een nieuw Bijhoudingsautorisatie object.
     *
     * @param indicatieModelautorisatie indicatieModelautorisatie
     */
    public Bijhoudingsautorisatie(final boolean indicatieModelautorisatie) {
        this.indicatieModelautorisatie = indicatieModelautorisatie;
    }

    /**
     * Geef de waarde van id van Bijhoudingsautorisatie.
     *
     * @return de waarde van id van Bijhoudingsautorisatie
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Bijhoudingsautorisatie.
     *
     * @param id de nieuwe waarde voor id van Bijhoudingsautorisatie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van indicatieModelautorisatie.
     *
     * @return indicatieModelautorisatie
     */
    public boolean getIndicatieModelautorisatie() {
        return indicatieModelautorisatie;
    }

    /**
     * Zet de waarde van indicatieModelautorisatie.
     *
     * @param indicatieModelautorisatie indicatieModelautorisatie
     */
    public void setIndicatieModelautorisatie(final boolean indicatieModelautorisatie) {
        this.indicatieModelautorisatie = indicatieModelautorisatie;
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
     * @param naam naam
     */
    public void setNaam(final String naam) {
        this.naam = naam;
    }

    /**
     * Geef de waarde van datum einde van Bijhoudingsautorisatie.
     *
     * @return de waarde van datum einde van Bijhoudingsautorisatie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van Bijhoudingsautorisatie.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van Bijhoudingsautorisatie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datum ingang van Bijhoudingsautorisatie.
     *
     * @return de waarde van datum ingang van Bijhoudingsautorisatie
     */
    public Integer getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van Bijhoudingsautorisatie.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van Bijhoudingsautorisatie
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van indicatie geblokkeerd van Bijhoudingsautorisatie.
     *
     * @return de waarde van indicatie geblokkeerd van Bijhoudingsautorisatie
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarden voor indicatie geblokkeerd van Bijhoudingsautorisatie.
     *
     * @param indicatieGeblokkeerd de nieuwe waarde voor indicatie geblokkeerd van
     *        Bijhoudingsautorisatie
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
     * Geef de waarde van bijhoudingsautorisatie historie set van Bijhoudingsautorisatie.
     *
     * @return de waarde van bijhoudingsautorisatie historie set van Bijhoudingsautorisatie
     */
    public Set<BijhoudingsautorisatieHistorie> getBijhoudingsautorisatieHistorieSet() {
        return bijhoudingsautorisatieHistorieSet;
    }

    /**
     * Zet de waarden voor bijhoudingsautorisatie historie set van Bijhoudingsautorisatie.
     *
     * @param bijhoudingsautorisatieHistorieSet de nieuwe waarde voor bijhoudingsautorisatie
     *        historie set van Bijhoudingsautorisatie
     */
    public void setBijhoudingsautorisatieHistorieSet(final Set<BijhoudingsautorisatieHistorie> bijhoudingsautorisatieHistorieSet) {
        this.bijhoudingsautorisatieHistorieSet = bijhoudingsautorisatieHistorieSet;
    }

    /**
     * Voeg een bijhoudingsautorisatieHistorie toe aan bijhoudingsautorisatieHistorieSet.
     *
     * @param bijhoudingsautorisatieHistorie bijhoudingsautorisatieHistorie
     */
    public void addBijhoudingsautorisatieHistorieSet(final BijhoudingsautorisatieHistorie bijhoudingsautorisatieHistorie) {
        bijhoudingsautorisatieHistorieSet.add(bijhoudingsautorisatieHistorie);
        bijhoudingsautorisatieHistorie.setBijhoudingsautorisatie(this);
    }

    /**
     * Verwijder een bijhoudingsautorisatieHistorie uit bijhoudingsautorisatieHistorieSet.
     *
     * @param bijhoudingsautorisatieHistorie bijhoudingsautorisatieHistorie
     * @return true als bijhoudingsautorisatieHistorie is verwijderd anders false
     */
    public boolean removeBijhoudingsautorisatieHistorieSet(final BijhoudingsautorisatieHistorie bijhoudingsautorisatieHistorie) {
        return bijhoudingsautorisatieHistorieSet.remove(bijhoudingsautorisatieHistorie);
    }

    /**
     * Geef de waarde van bijhoudingsautorisatieSoortAdministratieveHandelingSet van
     * Bijhoudingsautorisatie.
     *
     * @return de waarde van bijhoudingsautorisatieSoortAdministratieveHandelingSet van
     *         Bijhoudingsautorisatie
     */
    public Set<BijhoudingsautorisatieSoortAdministratieveHandeling> getBijhoudingsautorisatieSoortAdministratieveHandelingSet() {
        return bijhoudingsautorisatieSoortAdministratieveHandelingSet;
    }

    /**
     * Zet de waarden voor bijhoudingsautorisatieSoortAdministratieveHandelingSet van
     * Bijhoudingsautorisatie.
     *
     * @param bijhoudingsautorisatieSoortAdministratieveHandelingSet de nieuwe waarde voor
     *        bijhoudingsautorisatieSoortAdministratieveHandelingSet van Bijhoudingsautorisatie
     */
    public void setBijhoudingsautorisatieSoortAdministratieveHandelingSet(
            final Set<BijhoudingsautorisatieSoortAdministratieveHandeling> bijhoudingsautorisatieSoortAdministratieveHandelingSet) {
        this.bijhoudingsautorisatieSoortAdministratieveHandelingSet = bijhoudingsautorisatieSoortAdministratieveHandelingSet;
    }

    /**
     * Voeg een bijhoudingsautorisatieSoortAdministratieveHandeling toe aan
     * bijhoudingsautorisatieSoortAdministratieveHandelingSet.
     *
     * @param bijhoudingsautorisatieSoortAdministratieveHandeling
     *        bijhoudingsautorisatieSoortAdministratieveHandeling
     */
    public void addBijhoudingsautorisatieSoortAdministratieveHandeling(
            final BijhoudingsautorisatieSoortAdministratieveHandeling bijhoudingsautorisatieSoortAdministratieveHandeling) {
        getBijhoudingsautorisatieSoortAdministratieveHandelingSet().add(bijhoudingsautorisatieSoortAdministratieveHandeling);
        bijhoudingsautorisatieSoortAdministratieveHandeling.setBijhoudingsautorisatie(this);
    }

    /**
     * Verwijder een bijhoudingsautorisatieSoortAdministratieveHandeling uit
     * bijhoudingsautorisatieSoortAdministratieveHandelingSet.
     *
     * @param bijhoudingsautorisatieSoortAdministratieveHandeling
     *        bijhoudingsautorisatieSoortAdministratieveHandeling
     * @return true als bijhoudingsautorisatieSoortAdministratieveHandeling is verwijderd anders
     *         false
     */
    public boolean removeBijhoudingsautorisatieSoortAdministratieveHandeling(
            final BijhoudingsautorisatieSoortAdministratieveHandeling bijhoudingsautorisatieSoortAdministratieveHandeling) {
        return getBijhoudingsautorisatieSoortAdministratieveHandelingSet().remove(bijhoudingsautorisatieSoortAdministratieveHandeling);
    }
}
