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
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the bijhouderfiatuitz database table.
 *
 */
@Entity
@Table(name = "bijhouderfiatuitz", schema = "autaut")
@NamedQuery(name = "BijhouderFiatteringsuitzondering" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT b FROM BijhouderFiatteringsuitzondering b")
public class BijhouderFiatteringsuitzondering implements Afleidbaar, Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "bijhouderfiatuitz_id_generator", sequenceName = "autaut.seq_bijhouderfiatuitz", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bijhouderfiatuitz_id_generator")
    @Column(nullable = false, updatable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bijhouder", nullable = false)
    private PartijRol bijhouder;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang")
    private Integer datumIngang;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bijhouderbijhvoorstel")
    private PartijRol bijhouderBijhoudingsvoorstel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "srtdoc")
    private SoortDocument soortDocument;

    @Column(name = "srtadmhnd")
    private Integer soortAdministratieveHandelingId;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    // bi-directional many-to-one association to BijhouderFiatteringsuitzonderingHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(mappedBy = "bijhouderFiatteringsuitzondering", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<BijhouderFiatteringsuitzonderingHistorie> bijhouderFiatteringsuitzonderingHistorieSet = new LinkedHashSet<>(0);

    /**
     * JPA no-args constructor.
     */
    BijhouderFiatteringsuitzondering() {}

    /**
     * Maak een nieuw BijhouderFiatteringsuitzondering object.
     *
     * @param bijhouder bijhouder
     */
    public BijhouderFiatteringsuitzondering(final PartijRol bijhouder) {
        setBijhouder(bijhouder);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.DynamischeStamtabel#getId()
     */
    @Override
    public final Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van BijhouderFiatteringsuitzondering.
     *
     * @param id de nieuwe waarde voor id van BijhouderFiatteringsuitzondering
     */
    public final void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van bijhouder van BijhouderFiatteringsuitzondering.
     *
     * @return de waarde van bijhouder van BijhouderFiatteringsuitzondering
     */
    public final PartijRol getBijhouder() {
        return bijhouder;
    }

    /**
     * Zet de waarden voor bijhouder van BijhouderFiatteringsuitzondering.
     *
     * @param bijhouder de nieuwe waarde voor bijhouder van BijhouderFiatteringsuitzondering
     */
    public final void setBijhouder(final PartijRol bijhouder) {
        ValidationUtils.controleerOpNullWaarden("bijhouder mag niet null zijn", bijhouder);
        this.bijhouder = bijhouder;
    }

    /**
     * Geef de waarde van datum einde van BijhouderFiatteringsuitzondering.
     *
     * @return de waarde van datum einde van BijhouderFiatteringsuitzondering
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van BijhouderFiatteringsuitzondering.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van BijhouderFiatteringsuitzondering
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datum ingang van BijhouderFiatteringsuitzondering.
     *
     * @return de waarde van datum ingang van BijhouderFiatteringsuitzondering
     */
    public Integer getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van BijhouderFiatteringsuitzondering.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van BijhouderFiatteringsuitzondering
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van bijhouder bijhoudingsvoorstel van BijhouderFiatteringsuitzondering.
     *
     * @return de waarde van bijhouder bijhoudingsvoorstel van BijhouderFiatteringsuitzondering
     */
    public PartijRol getBijhouderBijhoudingsvoorstel() {
        return bijhouderBijhoudingsvoorstel;
    }

    /**
     * Zet de waarden voor bijhouder bijhoudingsvoorstel van BijhouderFiatteringsuitzondering.
     *
     * @param bijhouderBijhoudingsvoorstel de nieuwe waarde voor bijhouder bijhoudingsvoorstel van
     *        BijhouderFiatteringsuitzondering
     */
    public void setBijhouderBijhoudingsvoorstel(final PartijRol bijhouderBijhoudingsvoorstel) {
        this.bijhouderBijhoudingsvoorstel = bijhouderBijhoudingsvoorstel;
    }

    /**
     * Geef de waarde van soort document van BijhouderFiatteringsuitzondering.
     *
     * @return de waarde van soort document van BijhouderFiatteringsuitzondering
     */
    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    /**
     * Zet de waarden voor soort document van BijhouderFiatteringsuitzondering.
     *
     * @param soortDocument de nieuwe waarde voor soort document van
     *        BijhouderFiatteringsuitzondering
     */
    public void setSoortDocument(final SoortDocument soortDocument) {
        this.soortDocument = soortDocument;
    }

    /**
     * Geef de waarde van soort administratieve handeling id van BijhouderFiatteringsuitzondering.
     *
     * @return de waarde van soort administratieve handeling id van BijhouderFiatteringsuitzondering
     */
    public SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        return SoortAdministratieveHandeling.parseId(soortAdministratieveHandelingId);
    }

    /**
     * Zet de waarden voor soort administratieve handeling van BijhouderFiatteringsuitzondering.
     *
     * @param soortAdministratieveHandeling de nieuwe waarde voor soort administratieve handeling
     *        van BijhouderFiatteringsuitzondering
     */
    public void setSoortAdministratieveHandeling(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        if (soortAdministratieveHandeling == null) {
            soortAdministratieveHandelingId = null;
        } else {
            soortAdministratieveHandelingId = soortAdministratieveHandeling.getId();
        }
    }

    /**
     * Geef de waarde van indicatie geblokkeerd van BijhouderFiatteringsuitzondering.
     *
     * @return de waarde van indicatie geblokkeerd van BijhouderFiatteringsuitzondering
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarden voor indicatie geblokkeerd van BijhouderFiatteringsuitzondering.
     *
     * @param indicatieGeblokkeerd de nieuwe waarde voor indicatie geblokkeerd van
     *        BijhouderFiatteringsuitzondering
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
     * Geef de waarde van bijhouder fiatteringsuitzondering historie set van
     * BijhouderFiatteringsuitzondering.
     *
     * @return de waarde van bijhouder fiatteringsuitzondering historie set van
     *         BijhouderFiatteringsuitzondering
     */
    public Set<BijhouderFiatteringsuitzonderingHistorie> getBijhouderFiatteringsuitzonderingHistorieSet() {
        return bijhouderFiatteringsuitzonderingHistorieSet;
    }

    /**
     * Zet de waarden voor bijhouder fiatteringsuitzondering historie set van
     * BijhouderFiatteringsuitzondering.
     *
     * @param bijhouderFiatteringsuitzonderingHistorieSet de nieuwe waarde voor bijhouder
     *        fiatteringsuitzondering historie set van BijhouderFiatteringsuitzondering
     */
    public void setBijhouderFiatteringsuitzonderingHistorieSet(
            final Set<BijhouderFiatteringsuitzonderingHistorie> bijhouderFiatteringsuitzonderingHistorieSet) {
        this.bijhouderFiatteringsuitzonderingHistorieSet = bijhouderFiatteringsuitzonderingHistorieSet;
    }

    /**
     * Voegt een bijhouderFiatteringsuitzonderingHistorie toe aan
     * bijhouderFiatteringsuitzonderingHistorieSet.
     *
     * @param bijhouderFiatteringsuitzonderingHistorie bijhouderFiatteringsuitzonderingHistorie
     */
    public void addBijhouderFiatteringsuitzonderingHistorie(final BijhouderFiatteringsuitzonderingHistorie bijhouderFiatteringsuitzonderingHistorie) {
        getBijhouderFiatteringsuitzonderingHistorieSet().add(bijhouderFiatteringsuitzonderingHistorie);
        bijhouderFiatteringsuitzonderingHistorie.setBijhouderFiatteringsuitzondering(this);
    }

    /**
     * Verwijderd een bijhouderFiatteringsuitzonderingHistorie uit
     * bijhouderFiatteringsuitzonderingHistorieSet.
     *
     * @param bijhouderFiatteringsuitzonderingHistorie de te verwijderen
     *        bijhouderFiatteringsuitzonderingHistorie
     * @return true als dit element uit
     *         bijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet verwijderd is, anders
     *         false
     */
    public boolean removeBijhouderFiatteringsuitzonderingHistorie(final BijhouderFiatteringsuitzonderingHistorie bijhouderFiatteringsuitzonderingHistorie) {
        final boolean result = getBijhouderFiatteringsuitzonderingHistorieSet().remove(bijhouderFiatteringsuitzonderingHistorie);
        bijhouderFiatteringsuitzonderingHistorie.setBijhouderFiatteringsuitzondering(null);

        return result;
    }
}
