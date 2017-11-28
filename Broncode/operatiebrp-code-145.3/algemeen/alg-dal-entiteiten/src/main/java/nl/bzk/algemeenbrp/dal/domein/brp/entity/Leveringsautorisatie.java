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
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the levsautorisatie database table.
 *
 */
@Entity
@Table(name = "levsautorisatie", schema = "autaut")
@NamedQuery(name = "Leveringsautorisatie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT l FROM Leveringsautorisatie l")
@Access(AccessType.FIELD)
public class Leveringsautorisatie implements Afleidbaar, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "levsautorisatie_id_generator", sequenceName = "autaut.seq_levsautorisatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "levsautorisatie_id_generator")
    @Column(updatable = false)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang")
    private Integer datumIngang;

    @Column(name = "indaliassrtadmhndleveren")
    private Boolean indicatieAliasSoortAdministratieveHandelingLeveren;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "indmodelautorisatie", nullable = false)
    private boolean indicatieModelautorisatie;

    @Column(name = "naam")
    private String naam;

    @Column(name = "populatiebeperking")
    private String populatiebeperking;

    @Column(name = "stelsel", nullable = false)
    private int stelselId;

    @Column(name = "toelichting")
    private String toelichting;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    // bi-directional many-to-one association to Dienstbundel
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "leveringsautorisatie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<Dienstbundel> dienstbundelSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to LeveringsautorisatieHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "leveringsautorisatie", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<LeveringsautorisatieHistorie> leveringsautorisatieHistorieSet = new LinkedHashSet<>(0);

    @Column(name = "protocolleringsniveau")
    private Integer protocolleringsniveauId;

    // bi-directional many-to-one association to ToegangLeveringsAutorisatie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "leveringsautorisatie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<ToegangLeveringsAutorisatie> toegangLeveringsautorisatieSet = new LinkedHashSet<>(0);

    /**
     * JPA no-args constructor.
     */
    Leveringsautorisatie() {}

    /**
     * Maakt een nieuw Leveringsautorisatie object aan.
     *
     * @param stelsel stelsel
     * @param indicatieModelautorisatie indicatieModelautorisatie
     */
    public Leveringsautorisatie(final Stelsel stelsel, final boolean indicatieModelautorisatie) {
        setStelsel(stelsel);
        setIndicatieModelautorisatie(indicatieModelautorisatie);
    }

    /**
     * Geef de waarde van id van Leveringsautorisatie.
     *
     * @return de waarde van id van Leveringsautorisatie
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Leveringsautorisatie.
     *
     * @param id de nieuwe waarde voor id van Leveringsautorisatie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum einde van Leveringsautorisatie.
     *
     * @return de waarde van datum einde van Leveringsautorisatie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van Leveringsautorisatie.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van Leveringsautorisatie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datum ingang van Leveringsautorisatie.
     *
     * @return de waarde van datum ingang van Leveringsautorisatie
     */
    public Integer getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van Leveringsautorisatie.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van Leveringsautorisatie
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van indicatie alias soort administratieve handeling leveren van
     * Leveringsautorisatie.
     *
     * @return de waarde van indicatie alias soort administratieve handeling leveren van
     *         Leveringsautorisatie
     */
    public Boolean getIndicatieAliasSoortAdministratieveHandelingLeveren() {
        return indicatieAliasSoortAdministratieveHandelingLeveren;
    }

    /**
     * Zet de waarden voor indicatie alias soort administratieve handeling leveren van
     * Leveringsautorisatie.
     *
     * @param indicatieAliasSoortAdministratieveHandelingLeveren de nieuwe waarde voor indicatie
     *        alias soort administratieve handeling leveren van Leveringsautorisatie
     */
    public void setIndicatieAliasSoortAdministratieveHandelingLeveren(final Boolean indicatieAliasSoortAdministratieveHandelingLeveren) {
        this.indicatieAliasSoortAdministratieveHandelingLeveren = indicatieAliasSoortAdministratieveHandelingLeveren;
    }

    /**
     * Geef de waarde van indicatie geblokkeerd van Leveringsautorisatie.
     *
     * @return de waarde van indicatie geblokkeerd van Leveringsautorisatie
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarden voor indicatie geblokkeerd van Leveringsautorisatie.
     *
     * @param indicatieGeblokkeerd de nieuwe waarde voor indicatie geblokkeerd van
     *        Leveringsautorisatie
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van indicatie modelautorisatie van Leveringsautorisatie.
     *
     * @return de waarde van indicatie modelautorisatie van Leveringsautorisatie
     */
    public boolean getIndicatieModelautorisatie() {
        return indicatieModelautorisatie;
    }

    /**
     * Zet de waarden voor indicatie modelautorisatie van Leveringsautorisatie.
     *
     * @param indicatieModelautorisatie de nieuwe waarde voor indicatie modelautorisatie van
     *        Leveringsautorisatie
     */
    public void setIndicatieModelautorisatie(final boolean indicatieModelautorisatie) {
        this.indicatieModelautorisatie = indicatieModelautorisatie;
    }

    /**
     * Geef de waarde van naam van Leveringsautorisatie.
     *
     * @return de waarde van naam van Leveringsautorisatie
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van Leveringsautorisatie.
     *
     * @param naam de nieuwe waarde voor naam van Leveringsautorisatie
     */
    public void setNaam(final String naam) {
        if ("".equals(naam)) {
            throw new IllegalArgumentException("naam moet null of een waarde met minimaal lengte 1 hebben.");
        }
        this.naam = naam;
    }

    /**
     * Geef de waarde van populatiebeperking van Leveringsautorisatie.
     *
     * @return de waarde van populatiebeperking van Leveringsautorisatie
     */
    public String getPopulatiebeperking() {
        return populatiebeperking;
    }

    /**
     * Zet de waarden voor populatiebeperking van Leveringsautorisatie.
     *
     * @param populatiebeperking de nieuwe waarde voor populatiebeperking van Leveringsautorisatie
     */
    public void setPopulatiebeperking(final String populatiebeperking) {
        this.populatiebeperking = populatiebeperking;
    }

    /**
     * Geef de waarde van stelsel van Leveringsautorisatie.
     *
     * @return de waarde van stelsel van Leveringsautorisatie
     */
    public Stelsel getStelsel() {
        return Stelsel.parseId(stelselId);
    }

    /**
     * Zet de waarden voor stelsel van Leveringsautorisatie.
     *
     * @param stelsel de nieuwe waarde voor stelsel van Leveringsautorisatie
     */
    public void setStelsel(final Stelsel stelsel) {
        ValidationUtils.controleerOpNullWaarden("stelsel mag niet null zijn", stelsel);
        stelselId = stelsel.getId();
    }

    /**
     * Geef de waarde van toelichting van Leveringsautorisatie.
     *
     * @return de waarde van toelichting van Leveringsautorisatie
     */
    public String getToelichting() {
        return toelichting;
    }

    /**
     * Zet de waarden voor toelichting van Leveringsautorisatie.
     *
     * @param toelichting de nieuwe waarde voor toelichting van Leveringsautorisatie
     */
    public void setToelichting(final String toelichting) {
        this.toelichting = toelichting;
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
     * Geef de waarde van dienstbundel set van Leveringsautorisatie.
     *
     * @return de waarde van dienstbundel set van Leveringsautorisatie
     */
    public Set<Dienstbundel> getDienstbundelSet() {
        return dienstbundelSet;
    }

    /**
     * Zet de waarden voor dienstbundel set van Leveringsautorisatie.
     *
     * @param dienstbundelSet de nieuwe waarde voor dienstbundel set van Leveringsautorisatie
     */
    public void setDienstbundelSet(final Set<Dienstbundel> dienstbundelSet) {
        this.dienstbundelSet = dienstbundelSet;
    }

    /**
     * Voeg een dienstbundel toe aan dienstbundelSet.
     *
     * @param dienstbundel dienstbundel
     */
    public void addDienstbundelSet(final Dienstbundel dienstbundel) {
        getDienstbundelSet().add(dienstbundel);
        dienstbundel.setLeveringsautorisatie(this);
    }

    /**
     * Verwijderd een dienstbundel uit dienstbundelSet.
     *
     * @param dienstbundel dienstbundel
     * @return true als dienstbundel is verwijderd uit dienstbundelSet, anders false
     */
    public boolean removeDienstbundelSet(final Dienstbundel dienstbundel) {
        final boolean result = getDienstbundelSet().remove(dienstbundel);
        dienstbundel.setLeveringsautorisatie(null);
        return result;
    }

    /**
     * Geef de waarde van leveringsautorisatie historie set van Leveringsautorisatie.
     *
     * @return de waarde van leveringsautorisatie historie set van Leveringsautorisatie
     */
    public Set<LeveringsautorisatieHistorie> getLeveringsautorisatieHistorieSet() {
        return leveringsautorisatieHistorieSet;
    }

    /**
     * Zet de waarden voor leveringsautorisatie historie set van Leveringsautorisatie.
     *
     * @param leveringsautorisatieHistorieSet de nieuwe waarde voor leveringsautorisatie historie
     *        set van Leveringsautorisatie
     */
    public void setLeveringsautorisatieHistorieSet(final Set<LeveringsautorisatieHistorie> leveringsautorisatieHistorieSet) {
        this.leveringsautorisatieHistorieSet = leveringsautorisatieHistorieSet;
    }

    /**
     * Voegt een leveringsautorisatieHistorie toe aan leveringsautorisatieHistorieSet.
     *
     * @param leveringsautorisatieHistorie leveringsautorisatieHistorie
     */
    public void addLeveringsautorisatieHistorieSet(final LeveringsautorisatieHistorie leveringsautorisatieHistorie) {
        getLeveringsautorisatieHistorieSet().add(leveringsautorisatieHistorie);
        leveringsautorisatieHistorie.setLeveringsautorisatie(this);
    }

    /**
     * Verwijderd een leveringsautorisatieHistorie uit leveringsautorisatieHistorieSet.
     *
     * @param leveringsautorisatieHistorie leveringsautorisatieHistorie
     * @return true als leveringsautorisatieHistorie is verwijderd uit
     *         leveringsautorisatieHistorieSet, anders false
     */
    public boolean removeLeveringsautorisatieHistorieSet(final LeveringsautorisatieHistorie leveringsautorisatieHistorie) {
        final boolean result = getLeveringsautorisatieHistorieSet().remove(leveringsautorisatieHistorie);
        leveringsautorisatieHistorie.setLeveringsautorisatie(null);
        return result;
    }

    /**
     * Geef de waarde van protocolleringsniveau van Leveringsautorisatie.
     *
     * @return de waarde van protocolleringsniveau van Leveringsautorisatie
     */
    public Protocolleringsniveau getProtocolleringsniveau() {
        if (protocolleringsniveauId == null) {
            return null;
        } else {
            return Protocolleringsniveau.parseId(protocolleringsniveauId);
        }
    }

    /**
     * Zet de waarden voor protocolleringsniveau van Leveringsautorisatie.
     *
     * @param protocolleringsniveau de nieuwe waarde voor protocolleringsniveau van
     *        Leveringsautorisatie
     */
    public void setProtocolleringsniveau(final Protocolleringsniveau protocolleringsniveau) {
        if (protocolleringsniveau == null) {
            protocolleringsniveauId = null;
        } else {
            protocolleringsniveauId = protocolleringsniveau.getId();
        }
    }

    /**
     * Geef de waarde van toegang leveringsautorisatie set van Leveringsautorisatie.
     *
     * @return de waarde van toegang leveringsautorisatie set van Leveringsautorisatie
     */
    public Set<ToegangLeveringsAutorisatie> getToegangLeveringsautorisatieSet() {
        return toegangLeveringsautorisatieSet;
    }

    /**
     * Zet de waarden voor toegang leveringsautorisatie set van Leveringsautorisatie.
     *
     * @param toegangLeveringsautorisatieSet de nieuwe waarde voor toegang leveringsautorisatie set
     *        van Leveringsautorisatie
     */
    public void setToegangLeveringsautorisatieSet(final Set<ToegangLeveringsAutorisatie> toegangLeveringsautorisatieSet) {
        this.toegangLeveringsautorisatieSet = toegangLeveringsautorisatieSet;
    }

    /**
     * Voegt een toegangLeveringsautorisatie toe aan toegangLeveringsautorisatieSet.
     *
     * @param toegangLeveringsAutorisatie toegangLeveringsautorisatie
     */
    public void addToegangLeveringsautorisatieSet(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie) {
        getToegangLeveringsautorisatieSet().add(toegangLeveringsAutorisatie);
        toegangLeveringsAutorisatie.setLeveringsautorisatie(this);
    }

    /**
     * Verwijderd een toegangLeveringsautorisatie uit toegangLeveringsautorisatieSet.
     *
     * @param toegangLeveringsAutorisatie toegangLeveringsautorisatie
     * @return true als toegangLeveringsautorisatie is verwijderd uit
     *         toegangLeveringsautorisatieSet, anders false
     */
    public boolean removeToegangLeveringsautorisatieSet(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie) {
        final boolean result = getToegangLeveringsautorisatieSet().remove(toegangLeveringsAutorisatie);
        toegangLeveringsAutorisatie.setLeveringsautorisatie(null);
        return result;
    }
}
