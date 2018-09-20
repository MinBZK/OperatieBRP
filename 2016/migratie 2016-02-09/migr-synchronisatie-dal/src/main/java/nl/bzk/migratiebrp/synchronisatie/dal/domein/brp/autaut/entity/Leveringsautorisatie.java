/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stelsel;

/**
 * The persistent class for the levsautorisatie database table.
 *
 */
@Entity
@Table(name = "levsautorisatie", schema = "autaut")
@NamedQuery(name = "Leveringsautorisatie.findAll", query = "SELECT l FROM Leveringsautorisatie l")
@SuppressWarnings("checkstyle:designforextension")
public class Leveringsautorisatie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "levsautorisatie_id_generator", sequenceName = "autaut.seq_levsautorisatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "levsautorisatie_id_generator")
    @Column(updatable = false)
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

    @Column(name = "indpopbeperkingvolconv")
    private Boolean indicatiePopulatiebeperkingVolledigGeconverteerd;

    @Column(name = "naam")
    private String naam;

    @Column(name = "populatiebeperking")
    private String populatiebeperking;

    @Column(name = "stelsel", nullable = false)
    private short stelselId;

    @Column(name = "toelichting")
    private String toelichting;

    // bi-directional many-to-one association to Dienstbundel
    @OneToMany(mappedBy = "leveringsautorisatie", cascade = CascadeType.ALL)
    private Set<Dienstbundel> dienstbundelSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to LeveringsautorisatieHistorie
    @OneToMany(mappedBy = "leveringsautorisatie", cascade = CascadeType.ALL)
    private Set<LeveringsautorisatieHistorie> leveringsautorisatieHistorieSet = new LinkedHashSet<>(0);

    @Column(name = "protocolleringsniveau")
    private Short protocolleringsniveauId;

    // bi-directional many-to-one association to ToegangLeveringsAutorisatie
    @OneToMany(mappedBy = "leveringsautorisatie", cascade = CascadeType.ALL)
    private Set<ToegangLeveringsAutorisatie> toegangLeveringsautorisatieSet = new LinkedHashSet<>(0);

    /**
     * JPA no-args constructor.
     */
    Leveringsautorisatie() {
    }

    /**
     * Maakt een nieuw Leveringsautorisatie object aan.
     *
     * @param stelsel
     *            stelsel
     * @param indicatieModelautorisatie
     *            indicatieModelautorisatie
     */
    public Leveringsautorisatie(final Stelsel stelsel, final boolean indicatieModelautorisatie) {
        setStelsel(stelsel);
        setIndicatieModelautorisatie(indicatieModelautorisatie);
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
     * Geef de waarde van indicatieAliasSoortAdministratieveHandelingLeveren.
     *
     * @return indicatieAliasSoortAdministratieveHandelingLeveren
     */
    public Boolean getIndicatieAliasSoortAdministratieveHandelingLeveren() {
        return indicatieAliasSoortAdministratieveHandelingLeveren;
    }

    /**
     * Zet de waarde van indicatieAliasSoortAdministratieveHandelingLeveren.
     *
     * @param indicatieAliasSoortAdministratieveHandelingLeveren
     *            indicatieAliasSoortAdministratieveHandelingLeveren
     */
    public void setIndicatieAliasSoortAdministratieveHandelingLeveren(final Boolean indicatieAliasSoortAdministratieveHandelingLeveren) {
        this.indicatieAliasSoortAdministratieveHandelingLeveren = indicatieAliasSoortAdministratieveHandelingLeveren;
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
     * Geef de waarde indicatieModelautorisatie.
     *
     * @return indicatieModelautorisatie
     */
    public boolean getIndicatieModelautorisatie() {
        return indicatieModelautorisatie;
    }

    /**
     * Zet de waarde van indicatieModelautorisatie.
     *
     * @param indicatieModelautorisatie
     *            indicatieModelautorisatie
     */
    public void setIndicatieModelautorisatie(final boolean indicatieModelautorisatie) {
        this.indicatieModelautorisatie = indicatieModelautorisatie;
    }

    /**
     * Geef de waarde van indicatiePopulatiebeperkingVolledigGeconverteerd.
     *
     * @return indicatiePopulatiebeperkingVolledigGeconverteerd
     */
    public Boolean getIndicatiePopulatiebeperkingVolledigGeconverteerd() {
        return indicatiePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Zet de waarde van indicatiePopulatiebeperkingVolledigGeconverteerd.
     *
     * @param indicatiePopulatiebeperkingVolledigGeconverteerd
     *            indicatiePopulatiebeperkingVolledigGeconverteerd
     */
    public void setIndicatiePopulatiebeperkingVolledigGeconverteerd(final Boolean indicatiePopulatiebeperkingVolledigGeconverteerd) {
        if (Boolean.TRUE.equals(indicatiePopulatiebeperkingVolledigGeconverteerd)) {
            throw new IllegalArgumentException("indicatiePopulatiebeperkingVolledigGeconverteerd moet null of FALSE zijn");
        }
        this.indicatiePopulatiebeperkingVolledigGeconverteerd = indicatiePopulatiebeperkingVolledigGeconverteerd;
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
     * @param naam
     *            naam
     */
    public void setNaam(final String naam) {
        if ("".equals(naam)) {
            throw new IllegalArgumentException("naam moet null of een waarde met minimaal lengte 1 hebben.");
        }
        this.naam = naam;
    }

    /**
     * Geef de waarde van populatiebeperking.
     *
     * @return populatiebeperking
     */
    public String getPopulatiebeperking() {
        return populatiebeperking;
    }

    /**
     * Zet de waarde van populatiebeperking.
     *
     * @param populatiebeperking
     *            populatiebeperking
     */
    public void setPopulatiebeperking(final String populatiebeperking) {
        this.populatiebeperking = populatiebeperking;
    }

    /**
     * Geef de waarde van stelsel.
     *
     * @return stelsel
     */
    public Stelsel getStelsel() {
        return Stelsel.parseId(stelselId);
    }

    /**
     * Zet de waarde van stelsel.
     *
     * @param stelsel
     *            stelsel
     */
    public void setStelsel(final Stelsel stelsel) {
        ValidationUtils.controleerOpNullWaarden("stelsel mag niet null zijn", stelsel);
        stelselId = stelsel.getId();
    }

    /**
     * Geef de waarde van toelichting.
     *
     * @return toelichting
     */
    public String getToelichting() {
        return toelichting;
    }

    /**
     * Zet de waarde van toelichting.
     *
     * @param toelichting
     *            toelichting
     */
    public void setToelichting(final String toelichting) {
        this.toelichting = toelichting;
    }

    /**
     * Geef de waarde van dienstbundelSet.
     *
     * @return dienstbundelSet
     */
    public Set<Dienstbundel> getDienstbundelSet() {
        return dienstbundelSet;
    }

    /**
     * Zet de waarde van dienstbundelSet.
     *
     * @param dienstbundelSet
     *            dienstbundelSet
     */
    public void setDienstbundelSet(final Set<Dienstbundel> dienstbundelSet) {
        this.dienstbundelSet = dienstbundelSet;
    }

    /**
     * Voeg een dienstbundel toe aan dienstbundelSet.
     *
     * @param dienstbundel
     *            dienstbundel
     */
    public void addDienstbundelSet(final Dienstbundel dienstbundel) {
        getDienstbundelSet().add(dienstbundel);
        dienstbundel.setLeveringsautorisatie(this);
    }

    /**
     * Verwijderd een dienstbundel uit dienstbundelSet.
     *
     * @param dienstbundel
     *            dienstbundel
     * @return true als dienstbundel is verwijderd uit dienstbundelSet, anders false
     */
    public boolean removeDienstbundelSet(final Dienstbundel dienstbundel) {
        final boolean result = getDienstbundelSet().remove(dienstbundel);
        dienstbundel.setLeveringsautorisatie(null);
        return result;
    }

    /**
     * Geef de waarde van leveringsautorisatieHistorieSet.
     *
     * @return leveringsautorisatieHistorieSet
     */
    public Set<LeveringsautorisatieHistorie> getLeveringsautorisatieHistorieSet() {
        return leveringsautorisatieHistorieSet;
    }

    /**
     * Zet de waarde van leveringsautorisatieHistorieSet.
     *
     * @param leveringsautorisatieHistorieSet
     *            leveringsautorisatieHistorieSet
     */
    public void setLeveringsautorisatieHistorieSet(final Set<LeveringsautorisatieHistorie> leveringsautorisatieHistorieSet) {
        this.leveringsautorisatieHistorieSet = leveringsautorisatieHistorieSet;
    }

    /**
     * Voegt een leveringsautorisatieHistorie toe aan leveringsautorisatieHistorieSet.
     *
     * @param leveringsautorisatieHistorie
     *            leveringsautorisatieHistorie
     */
    public void addLeveringsautorisatieHistorieSet(final LeveringsautorisatieHistorie leveringsautorisatieHistorie) {
        getLeveringsautorisatieHistorieSet().add(leveringsautorisatieHistorie);
        leveringsautorisatieHistorie.setLeveringsautorisatie(this);
    }

    /**
     * Verwijderd een leveringsautorisatieHistorie uit leveringsautorisatieHistorieSet.
     *
     * @param leveringsautorisatieHistorie
     *            leveringsautorisatieHistorie
     * @return true als leveringsautorisatieHistorie is verwijderd uit leveringsautorisatieHistorieSet, anders false
     */
    public boolean removeLeveringsautorisatieHistorieSet(final LeveringsautorisatieHistorie leveringsautorisatieHistorie) {
        final boolean result = getLeveringsautorisatieHistorieSet().remove(leveringsautorisatieHistorie);
        leveringsautorisatieHistorie.setLeveringsautorisatie(null);
        return result;
    }

    /**
     * Geef de waarde van protocolleringsniveau.
     *
     * @return protocolleringsniveau
     */
    public Protocolleringsniveau getProtocolleringsniveau() {
        if (protocolleringsniveauId == null) {
            return null;
        } else {
            return Protocolleringsniveau.parseId(protocolleringsniveauId);
        }
    }

    /**
     * Zet de waarde van protocolleringsniveau.
     *
     * @param protocolleringsniveau
     *            protocolleringsniveau
     */
    public void setProtocolleringsniveau(final Protocolleringsniveau protocolleringsniveau) {
        if (protocolleringsniveau == null) {
            protocolleringsniveauId = null;
        } else {
            protocolleringsniveauId = protocolleringsniveau.getId();
        }
    }

    /**
     * Geef de waarde van toegangLeveringsautorisatieSet.
     *
     * @return toegangLeveringsautorisatieSet
     */
    public Set<ToegangLeveringsAutorisatie> getToegangLeveringsautorisatieSet() {
        return toegangLeveringsautorisatieSet;
    }

    /**
     * Zet de waarde van toegangLeveringsautorisatieSet.
     *
     * @param toegangLeveringsautorisatieSet
     *            toegangLeveringsautorisatieSet
     */
    public void setToegangLeveringsautorisatieSet(final Set<ToegangLeveringsAutorisatie> toegangLeveringsautorisatieSet) {
        this.toegangLeveringsautorisatieSet = toegangLeveringsautorisatieSet;
    }

    /**
     * Voegt een toegangLeveringsautorisatie toe aan toegangLeveringsautorisatieSet.
     *
     * @param toegangLeveringsAutorisatie
     *            toegangLeveringsautorisatie
     */
    public void addToegangLeveringsautorisatieSet(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie) {
        getToegangLeveringsautorisatieSet().add(toegangLeveringsAutorisatie);
        toegangLeveringsAutorisatie.setLeveringsautorisatie(this);
    }

    /**
     * Verwijderd een toegangLeveringsautorisatie uit toegangLeveringsautorisatieSet.
     *
     * @param toegangLeveringsAutorisatie
     *            toegangLeveringsautorisatie
     * @return true als toegangLeveringsautorisatie is verwijderd uit toegangLeveringsautorisatieSet, anders false
     */
    public boolean removeToegangLeveringsautorisatieSet(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie) {
        final boolean result = getToegangLeveringsautorisatieSet().remove(toegangLeveringsAutorisatie);
        toegangLeveringsAutorisatie.setLeveringsautorisatie(null);
        return result;
    }
}
