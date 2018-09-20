/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging.domein.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the sync log/result database table.
 */
@Entity
@Table(name = "initvullingresult", schema = "public")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd t.b.v. het datamodel en bevat daarom geen javadoc, daarnaast mogen entities
 * en de methoden van entities niet final zijn.
 */
public class InitVullingLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "anummer", insertable = true, updatable = true, unique = true, nullable = false)
    private Long anummer;

    @Column(name = "berichtidentificatie", insertable = true, updatable = true)
    private Integer berichtId;

    @Column(name = "bericht_inhoud", insertable = true, updatable = true)
    private String berichtInhoud;

    @Column(name = "inhoud_na_terugconversie", insertable = true, updatable = true)
    private String berichtNaTerugConv;

    @Column(name = "datumtijd_opname_in_gbav", insertable = true, updatable = true)
    private Date datumTijdOpnameGbav;

    @Id
    @Column(name = "gbav_pl_id", insertable = true, updatable = true, unique = true)
    private Integer plId;

    @Column(name = "datum_opschorting", insertable = true, updatable = true)
    private Integer datumOpschorting;

    @Column(name = "reden_opschorting", insertable = true, updatable = true)
    private Character redenOpschorting;

    @Column(name = "berichttype", insertable = true, updatable = true)
    private Integer berichtType;

    @Column(name = "gemeente_van_inschrijving", insertable = true, updatable = true)
    private Integer gemeenteVanInschrijving;

    @Column(name = "bericht_diff", insertable = true, updatable = true)
    private String berichtDiff;

    @Column(name = "conversie_resultaat", insertable = true, updatable = true, length = 200)
    private String conversieResultaat;

    @Column(name = "foutmelding", insertable = true, updatable = true)
    private String foutmelding;

    @Column(name = "foutcategorie", insertable = true, updatable = true)
    private Integer foutCategorie;

    @Column(name = "preconditie", insertable = true, updatable = true)
    private String preconditie;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "log",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private Set<FingerPrint> fingerPrints = new LinkedHashSet<FingerPrint>();

    /**
     * @return the anummer
     */
    public Long getAnummer() {
        return anummer;
    }

    /**
     * @param anummer
     *            the anummer to set
     */
    public void setAnummer(final Long anummer) {
        this.anummer = anummer;
    }

    /**
     * @return the berichtId
     */
    public Integer getBerichtId() {
        return berichtId;
    }

    /**
     * @param berichtId
     *            the berichtId to set
     */
    public void setBerichtId(final Integer berichtId) {
        this.berichtId = berichtId;
    }

    /**
     * @return the lo3Bericht
     */
    public String getLo3Bericht() {
        return berichtInhoud;
    }

    /**
     * @param lo3Bericht
     *            the lo3Bericht to set
     */
    public void setLo3Bericht(final String lo3Bericht) {
        berichtInhoud = lo3Bericht;
    }

    /**
     * @return the brpLo3Bericht
     */
    public String getBrpLo3Bericht() {
        return berichtNaTerugConv;
    }

    /**
     * @param brpLo3Bericht
     *            the brpLo3Bericht to set
     */
    public void setBrpLo3Bericht(final String brpLo3Bericht) {
        berichtNaTerugConv = brpLo3Bericht;
    }

    /**
     * @return the datumTijdOpnameGbav
     */
    public Date getDatumTijdOpnameGbav() {
        return datumTijdOpnameGbav;
    }

    /**
     * @param datumTijdOpnameGbav
     *            the datumTijdOpnameGbav to set
     */
    public void setDatumTijdOpnameGbav(final Date datumTijdOpnameGbav) {
        this.datumTijdOpnameGbav = datumTijdOpnameGbav;
    }

    /**
     * @return the plId
     */
    public Integer getPlId() {
        return plId;
    }

    /**
     * @param plId
     *            the plId to set
     */
    public void setPlId(final Integer plId) {
        this.plId = plId;
    }

    /**
     * @return the datumOpschorting
     */
    public Integer getDatumOpschorting() {
        return datumOpschorting;
    }

    /**
     * @param datumOpschorting
     *            the datumOpschorting to set
     */
    public void setDatumOpschorting(final Integer datumOpschorting) {
        this.datumOpschorting = datumOpschorting;
    }

    /**
     * @return the redenOpschorting
     */
    public Character getRedenOpschorting() {
        return redenOpschorting;
    }

    /**
     * @param redenOpschorting
     *            the redenOpschorting to set
     */
    public void setRedenOpschorting(final Character redenOpschorting) {
        this.redenOpschorting = redenOpschorting;
    }

    /**
     * @return the berichtType
     */
    public Integer getBerichtType() {
        return berichtType;
    }

    /**
     * @param berichtType
     *            the berichtType to set
     */
    public void setBerichtType(final Integer berichtType) {
        this.berichtType = berichtType;
    }

    /**
     * @return the gemeenteVanInschrijving
     */
    public Integer getGemeenteVanInschrijving() {
        return gemeenteVanInschrijving;
    }

    /**
     * @param gemeenteVanInschrijving
     *            the gemeenteVanInschrijving to set
     */
    public void setGemeenteVanInschrijving(final Integer gemeenteVanInschrijving) {
        this.gemeenteVanInschrijving = gemeenteVanInschrijving;
    }

    /**
     * @return the berichtDiff
     */
    public String getBerichtDiff() {
        return berichtDiff;
    }

    /**
     * @param berichtDiff
     *            the berichtDiff to set
     */
    public void setBerichtDiff(final String berichtDiff) {
        this.berichtDiff = berichtDiff;
    }

    /**
     * @return the conversieResultaat
     */
    public String getConversieResultaat() {
        return conversieResultaat;
    }

    /**
     * @param conversieResultaat
     *            the conversieResultaat to set
     */
    public void setConversieResultaat(final String conversieResultaat) {
        this.conversieResultaat = conversieResultaat;
    }

    /**
     * @return the foutmelding
     */
    public String getFoutmelding() {
        return foutmelding;
    }

    /**
     * @param foutmelding
     *            the foutmelding to set
     */
    public void setFoutmelding(final String foutmelding) {
        this.foutmelding = foutmelding;
    }

    /**
     * @return the foutCategorie
     */
    public Integer getFoutCategorie() {
        return foutCategorie;
    }

    /**
     * @param foutCategorie
     *            the foutCategorie to set
     */
    public void setFoutCategorie(final Integer foutCategorie) {
        this.foutCategorie = foutCategorie;
    }

    /**
     * @return the preconditie
     */
    public String getPreconditie() {
        return preconditie;
    }

    /**
     * @param preconditie
     *            the preconditie to set
     */
    public void setPreconditie(final String preconditie) {
        this.preconditie = preconditie;
    }

    /**
     * @return the fingerPrints
     */
    public Set<FingerPrint> getFingerPrints() {
        return fingerPrints;
    }

    /**
     * @param fingerPrints
     *            the fingerPrints to set
     */
    public void setFingerPrints(final Set<FingerPrint> fingerPrints) {
        for (final FingerPrint fingerPrint : fingerPrints) {
            fingerPrint.setLog(this);
        }
        this.fingerPrints = fingerPrints;
    }

    /**
     * Voeg een FingerPrint toe aan de log
     * @param fingerPrint de FingerPrint
     */
    public void addFingerPrint(final FingerPrint fingerPrint) {
        fingerPrint.setLog(this);
        fingerPrints.add(fingerPrint);
    }
}
