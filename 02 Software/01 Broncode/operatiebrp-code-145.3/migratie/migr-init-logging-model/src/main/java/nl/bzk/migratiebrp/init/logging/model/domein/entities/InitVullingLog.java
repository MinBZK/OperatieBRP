/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model.domein.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the sync log/result database table.
 */
@Entity
@Table(name = "initvullingresult", schema = "initvul")
@NamedQueries({@NamedQuery(name = "InitVullingLog.selectLogByAnummer", query = "select ivl from InitVullingLog ivl where ivl.anummer = :anummer")})
public class InitVullingLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "anummer", insertable = true, updatable = true, unique = true, nullable = false)
    private String anummer;

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
    private String berichtType;

    @Column(name = "gemeente_van_inschrijving", insertable = true, updatable = true)
    private String gemeenteVanInschrijving;

    @Column(name = "conversie_resultaat", insertable = true, updatable = true, length = 200)
    private String conversieResultaat;

    @Column(name = "foutmelding_terugconversie", insertable = true, updatable = true)
    private String foutmelding;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "log", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<FingerPrint> fingerPrints = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "log", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<VerschilAnalyseRegel> verschilAnalyseRegels = new LinkedHashSet<>();

    /**
     * Geef de waarde van anummer.
     * @return the anummer
     */
    public String getAnummer() {
        return anummer;
    }

    /**
     * Zet de waarde van anummer.
     * @param anummer the anummer to set
     */
    public void setAnummer(final String anummer) {
        this.anummer = anummer;
    }

    /**
     * Geef de waarde van bericht id.
     * @return the berichtId
     */
    public Integer getBerichtId() {
        return berichtId;
    }

    /**
     * Zet de waarde van bericht id.
     * @param berichtId the berichtId to set
     */
    public void setBerichtId(final Integer berichtId) {
        this.berichtId = berichtId;
    }

    /**
     * Geef de waarde van lo3 bericht.
     * @return the lo3Bericht
     */
    public String getLo3Bericht() {
        return berichtInhoud;
    }

    /**
     * Zet de waarde van lo3 bericht.
     * @param lo3Bericht the lo3Bericht to set
     */
    public void setLo3Bericht(final String lo3Bericht) {
        berichtInhoud = lo3Bericht;
    }

    /**
     * Geef de waarde van bericht na terug conversie.
     * @return een XML representatie van een terug geconverteerde LO3 Persoonslijst
     */
    public String getBerichtNaTerugConversie() {
        return berichtNaTerugConv;
    }

    /**
     * Zet de waarde van bericht na terug conversie.
     * @param brpLo3Bericht een XML representatie van een terug geconverteerde LO3 Persoonslijst
     */
    public void setBerichtNaTerugConversie(final String brpLo3Bericht) {
        berichtNaTerugConv = brpLo3Bericht;
    }

    /**
     * Geef de waarde van datum tijd opname gbav.
     * @return the datumTijdOpnameGbav
     */
    public Date getDatumTijdOpnameGbav() {
        return new Date(datumTijdOpnameGbav.getTime());
    }

    /**
     * Zet de waarde van datum tijd opname gbav.
     * @param datumTijdOpnameGbav the datumTijdOpnameGbav to set
     */
    public void setDatumTijdOpnameGbav(final Date datumTijdOpnameGbav) {
        this.datumTijdOpnameGbav = new Date(datumTijdOpnameGbav.getTime());
    }

    /**
     * Geef de waarde van pl id.
     * @return the plId
     */
    public Integer getPlId() {
        return plId;
    }

    /**
     * Zet de waarde van pl id.
     * @param plId the plId to set
     */
    public void setPlId(final Integer plId) {
        this.plId = plId;
    }

    /**
     * Geef de waarde van datum opschorting.
     * @return the datumOpschorting
     */
    public Integer getDatumOpschorting() {
        return datumOpschorting;
    }

    /**
     * Zet de waarde van datum opschorting.
     * @param datumOpschorting the datumOpschorting to set
     */
    public void setDatumOpschorting(final Integer datumOpschorting) {
        this.datumOpschorting = datumOpschorting;
    }

    /**
     * Geef de waarde van reden opschorting.
     * @return the redenOpschorting
     */
    public Character getRedenOpschorting() {
        return redenOpschorting;
    }

    /**
     * Zet de waarde van reden opschorting.
     * @param redenOpschorting the redenOpschorting to set
     */
    public void setRedenOpschorting(final Character redenOpschorting) {
        this.redenOpschorting = redenOpschorting;
    }

    /**
     * Geef de waarde van bericht type.
     * @return the berichtType
     */
    public String getBerichtType() {
        return berichtType;
    }

    /**
     * Zet de waarde van bericht type.
     * @param berichtType the berichtType to set
     */
    public void setBerichtType(final String berichtType) {
        this.berichtType = berichtType;
    }

    /**
     * Geef de waarde van gemeente van inschrijving.
     * @return the gemeenteVanInschrijving
     */
    public String getGemeenteVanInschrijving() {
        return gemeenteVanInschrijving;
    }

    /**
     * Zet de waarde van gemeente van inschrijving.
     * @param gemeenteVanInschrijving the gemeenteVanInschrijving to set
     */
    public void setGemeenteVanInschrijving(final String gemeenteVanInschrijving) {
        this.gemeenteVanInschrijving = gemeenteVanInschrijving;
    }

    /**
     * Geef de waarde van conversie resultaat.
     * @return the conversieResultaat
     */
    public String getConversieResultaat() {
        return conversieResultaat;
    }

    /**
     * Zet de waarde van conversie resultaat.
     * @param conversieResultaat the conversieResultaat to set
     */
    public void setConversieResultaat(final String conversieResultaat) {
        this.conversieResultaat = conversieResultaat;
    }

    /**
     * Geef de waarde van foutmelding.
     * @return the foutmelding
     */
    public String getFoutmelding() {
        return foutmelding;
    }

    /**
     * Zet de waarde van foutmelding.
     * @param foutmelding the foutmelding to set
     */
    public void setFoutmelding(final String foutmelding) {
        this.foutmelding = foutmelding;
    }

    /**
     * Geef de waarde van finger prints.
     * @return the fingerPrints
     */
    public Set<FingerPrint> getFingerPrints() {
        return fingerPrints;
    }

    /**
     * Zet de waarde van finger prints.
     * @param fingerPrints the fingerPrints to set
     */
    public void setFingerPrints(final Set<FingerPrint> fingerPrints) {
        for (final FingerPrint fingerPrint : fingerPrints) {
            fingerPrint.setLog(this);
        }
        this.fingerPrints = fingerPrints;
    }

    /**
     * Voeg een FingerPrint toe aan de log.
     * @param fingerPrint de FingerPrint
     */
    public void addFingerPrint(final FingerPrint fingerPrint) {
        fingerPrint.setLog(this);
        fingerPrints.add(fingerPrint);
    }

    /**
     * Geef de waarde van verschil analyse regels.
     * @return de verschilAnalyseRegels
     */
    public Set<VerschilAnalyseRegel> getVerschilAnalyseRegels() {
        return verschilAnalyseRegels;
    }

    /**
     * @param paramVerschilAnalyseRegels de verschilAnalyseRegels
     */
    public void setdiffAnalyseRegels(final Set<VerschilAnalyseRegel> paramVerschilAnalyseRegels) {
        for (final VerschilAnalyseRegel verschilAnalyseRegel : paramVerschilAnalyseRegels) {
            verschilAnalyseRegel.setLog(this);
        }
        verschilAnalyseRegels = paramVerschilAnalyseRegels;
    }

    /**
     * Voeg de DiffAnalyseRegels toe aan de bestaande lijst van DiffAnalyseRegels.
     * @param paramVerschilAnalyseRegels de DiffAnalyseRegel
     */
    public void addVerschilAnalyseRegels(final Collection<VerschilAnalyseRegel> paramVerschilAnalyseRegels) {
        for (final VerschilAnalyseRegel verschilAnalyseRegel : paramVerschilAnalyseRegels) {
            verschilAnalyseRegel.setLog(this);
        }
        verschilAnalyseRegels.addAll(paramVerschilAnalyseRegels);
    }

}
