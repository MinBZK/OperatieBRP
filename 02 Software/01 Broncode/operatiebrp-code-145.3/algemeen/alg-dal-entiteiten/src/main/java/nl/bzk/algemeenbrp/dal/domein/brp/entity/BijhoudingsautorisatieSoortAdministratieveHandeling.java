/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the bijhautorisatiesrtadmhnd database table.
 *
 */
@Entity
@Table(name = "bijhautorisatiesrtadmhnd", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"bijhautorisatie", "srtadmhnd"}))
@NamedQuery(name = "BijhoudingsautorisatieSoortAdministratieveHandeling" + Constanten.ZOEK_ALLES_VOOR_CACHE,
        query = "SELECT t FROM BijhoudingsautorisatieSoortAdministratieveHandeling t")
public class BijhoudingsautorisatieSoortAdministratieveHandeling implements Afleidbaar, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "bijhautorisatiesrtadmhnd_id_generator", sequenceName = "autaut.seq_bijhautorisatiesrtadmhnd", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bijhautorisatiesrtadmhnd_id_generator")
    @Column(updatable = false)
    private Integer id;

    // bi-directional many-to-one association to Bijhoudingsautorisatie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bijhautorisatie", nullable = false)
    private Bijhoudingsautorisatie bijhoudingsautorisatie;

    @Column(name = "srtadmhnd", nullable = false)
    private int soortAdministratievehandelingId;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    // bi-directional many-to-one association to
    // BijhoudingsautorisatieSoortAdministratieveHandelingHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bijhoudingsautorisatieSoortAdministratieveHandeling", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<BijhoudingsautorisatieSoortAdministratieveHandelingHistorie> bijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet =
            new LinkedHashSet<>(0);

    /**
     * JPA no-args constructor.
     */
    BijhoudingsautorisatieSoortAdministratieveHandeling() {}

    /**
     * Maak een nieuw BijhoudingsautorisatieSoortAdministratieveHandeling object.
     *
     * @param bijhoudingsautorisatie bijhoudingsautorisatie
     * @param soortAdministratieveHandeling soortAdministratieveHandeling
     */
    public BijhoudingsautorisatieSoortAdministratieveHandeling(final Bijhoudingsautorisatie bijhoudingsautorisatie,
            final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        setBijhoudingsautorisatie(bijhoudingsautorisatie);
        setSoortAdministratievehandeling(soortAdministratieveHandeling);
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
     * Zet de waarden van id.
     *
     * @param id de id
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van bijhoudingsautorisatie.
     *
     * @return Bijhoudingsautorisatie
     */
    public Bijhoudingsautorisatie getBijhoudingsautorisatie() {
        return bijhoudingsautorisatie;
    }

    /**
     * Zet de waarde van bijhoudingsautorisatie.
     *
     * @param bijhoudingsautorisatie bijhoudingsautorisatie
     */
    public void setBijhoudingsautorisatie(final Bijhoudingsautorisatie bijhoudingsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("bijhoudingsautorisatie mag niet null zijn", bijhoudingsautorisatie);
        this.bijhoudingsautorisatie = bijhoudingsautorisatie;
    }

    /**
     * Geef de waarde van soortAdministratievehandeling.
     *
     * @return soortAdministratievehandeling
     */
    public SoortAdministratieveHandeling getSoortAdministratievehandeling() {
        return SoortAdministratieveHandeling.parseId(soortAdministratievehandelingId);
    }

    /**
     * Zet de waarde van soortAdministratievehandeling.
     *
     * @param soortAdministratievehandeling soortAdministratievehandeling
     */
    public void setSoortAdministratievehandeling(final SoortAdministratieveHandeling soortAdministratievehandeling) {
        ValidationUtils.controleerOpNullWaarden("soortAdministratievehandeling mag niet null zijn", soortAdministratievehandeling);
        soortAdministratievehandelingId = soortAdministratievehandeling.getId();
    }

    /**
     * Geef de waarde van bijhoudingsautorisatie soort administratieve handeling historie set van
     * BijhoudingsautorisatieSoortAdministratieveHandeling.
     *
     * @return de waarde van bijhoudingsautorisatie soort administratieve handeling historie set van
     *         BijhoudingsautorisatieSoortAdministratieveHandeling
     */
    public Set<BijhoudingsautorisatieSoortAdministratieveHandelingHistorie> getBijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet() {
        return bijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet;
    }

    /**
     * Zet de waarden voor bijhoudingsautorisatie soort administratieve handeling historie set van
     * BijhoudingsautorisatieSoortAdministratieveHandeling.
     *
     * @param bijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet de nieuwe waarde voor
     *        bijhoudingsautorisatie soort administratieve handeling historie set van
     *        BijhoudingsautorisatieSoortAdministratieveHandeling
     */
    public void setBijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet(
            final Set<BijhoudingsautorisatieSoortAdministratieveHandelingHistorie> bijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet) {
        this.bijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet = bijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet;
    }

    /**
     * Voegt een BijhoudingsautorisatieSoortAdministratieveHandelingHistorie toe aan
     * bijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet.
     *
     * @param bijhoudingsautorisatieSoortAdministratieveHandelingHistorie
     *        BijhoudingsautorisatieSoortAdministratieveHandelingHistorie
     */
    public void addBijhoudingsautorisatieSoortAdministratieveHandelingHistorie(
            final BijhoudingsautorisatieSoortAdministratieveHandelingHistorie bijhoudingsautorisatieSoortAdministratieveHandelingHistorie) {
        getBijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet().add(bijhoudingsautorisatieSoortAdministratieveHandelingHistorie);
        bijhoudingsautorisatieSoortAdministratieveHandelingHistorie.setBijhoudingsautorisatieSoortAdministratieveHandeling(this);
    }

    /**
     * Verwijderd een BijhoudingsautorisatieHistorie uit
     * bijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet.
     *
     * @param bijhoudingsautorisatieSoortAdministratieveHandelingHistorie
     *        bijhoudingsautorisatieSoortAdministratieveHandelingHistorie
     * @return true als bijhoudingsautorisatieSoortAdministratieveHandelingHistorie is verwijderd,
     *         anders false
     */
    public boolean removeBijhoudingsautorisatieSoortAdministratieveHandelingHistorie(
            final BijhoudingsautorisatieSoortAdministratieveHandelingHistorie bijhoudingsautorisatieSoortAdministratieveHandelingHistorie) {
        final boolean result =
                getBijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet().remove(bijhoudingsautorisatieSoortAdministratieveHandelingHistorie);
        bijhoudingsautorisatieSoortAdministratieveHandelingHistorie.setBijhoudingsautorisatieSoortAdministratieveHandeling(null);
        return result;
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
}
