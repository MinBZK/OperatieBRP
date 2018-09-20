/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Logging regel.
 */
@Entity
@Table(name = "berichtlog", schema = "logging")
public class BerichtLog {

    @Id
    @SequenceGenerator(name = "berichtlog_id_generator", sequenceName = "logging.seq_berichtlog", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "berichtlog_id_generator")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pers_id")
    private Persoon persoon;

    @Column(name = "referentie", nullable = false, length = 36)
    private String referentie;

    @Column(name = "bron", nullable = false, length = 36)
    private String bron;

    @Column(name = "tijdstip_verwerking", nullable = false)
    private Timestamp tijdstipVerwerking;

    @Column(name = "a_nummer", nullable = true)
    private BigDecimal administratienummer;

    @Column(name = "bericht_data", nullable = true)
    private String berichtData;

    @Column(name = "bericht_hash", nullable = true, length = 64)
    private String berichtHash;

    @Column(name = "foutcode", nullable = true, length = 200)
    private String foutcode;

    @Column(name = "foutmelding", nullable = true)
    private String foutmelding;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "berichtLog", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<LogRegel> logRegelSet = new LinkedHashSet<LogRegel>();

    @Column(name = "gemeente_van_inschrijving", nullable = true)
    private Integer gemeenteVanInschrijving;

    @SuppressWarnings("unused")
    private BerichtLog() {
        // for Hibernate only
    }

    /**
     * Maakt een BerichtLog object.
     * 
     * @param referentie
     *            een referentie naar het bron bericht, bijv een uuid van het oorspronkelijke bericht
     * @param bron
     *            de bron van het bericht, bijv. de queue naam waar het bericht op binnen is gekomen
     * @param tijdstipVerwerking
     *            het tijdstip waarom het bericht wordt verwerkt
     */
    public BerichtLog(final String referentie, final String bron, final Timestamp tijdstipVerwerking) {
        if (referentie == null) {
            throw new NullPointerException("referentie mag niet null zijn");
        }
        if (bron == null) {
            throw new NullPointerException("bron mag niet null zijn");
        }
        if (tijdstipVerwerking == null) {
            throw new NullPointerException("tijdstipVerwerking mag niet null zijn");
        }
        this.referentie = referentie;
        this.bron = bron;
        this.tijdstipVerwerking = tijdstipVerwerking;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public final Long getId() {
        return id;
    }

    public final void setId(final Long id) {
        this.id = id;
    }

    public final Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de persoon waarop dit berichtlog betrekking heeft. Het a-nummer van de persoon wordt ook in de berichtlog
     * meegenomen.
     * 
     * @param persoon
     *            persoon
     */
    public final void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public final String getReferentie() {
        return referentie;
    }

    public final void setReferentie(final String referentie) {
        this.referentie = referentie;
    }

    public final String getBron() {
        return bron;
    }

    public final void setBron(final String bron) {
        this.bron = bron;
    }

    public final Timestamp getTijdstipVerwerking() {
        return tijdstipVerwerking;
    }

    public final void setTijdstipVerwerking(final Timestamp tijdstipVerwerking) {
        this.tijdstipVerwerking = tijdstipVerwerking;
    }

    public final BigDecimal getAdministratienummer() {
        return administratienummer;
    }

    public final void setAdministratienummer(final BigDecimal administratienummer) {
        this.administratienummer = administratienummer;
    }

    public final String getBerichtData() {
        return berichtData;
    }

    /**
     * Set de bericht data van deze logregel en genereert een hash op basis van deze bericht data.
     * 
     * @param berichtData
     *            de bericht data
     * @see #getBerichtHash()
     */
    public final void setBerichtData(final String berichtData) {
        this.berichtData = berichtData;
        setBerichtHash("" + new HashCodeBuilder().append(berichtData).build());
    }

    public final String getBerichtHash() {
        return berichtHash;
    }

    private void setBerichtHash(final String berichtHash) {
        this.berichtHash = berichtHash;
    }

    public final String getFoutcode() {
        return foutcode;
    }

    public final void setFoutcode(final String foutcode) {
        this.foutcode = foutcode;
    }

    public final String getFoutmelding() {
        return foutmelding;
    }

    public final void setFoutmelding(final String foutmelding) {
        this.foutmelding = foutmelding;
    }

    public final Set<LogRegel> getLogRegelSet() {
        return logRegelSet;
    }

    /**
     * Voeg logregel toe aan dit berichtlog. Zorgt dat ook koppeling van logregel gezet wordt.
     * 
     * @param logRegel
     *            logregel
     */
    public final void addLogRegel(final LogRegel logRegel) {
        logRegel.setBerichtLog(this);
        logRegelSet.add(logRegel);
    }

    /**
     * @return the gemeenteVanInschrijving
     */
    public final Integer getGemeenteVanInschrijving() {
        return gemeenteVanInschrijving;
    }

    /**
     * @param gemeenteVanInschrijving
     *            the gemeenteVanInschrijving to set
     */
    public final void setGemeenteVanInschrijving(final Integer gemeenteVanInschrijving) {
        this.gemeenteVanInschrijving = gemeenteVanInschrijving;
    }

    /**
     * Maakt een BerichtLog obv de huidige datum en tijd.
     * 
     * @param referentie
     *            een referentie naar het bericht (bijv. message id)
     * @param bron
     *            de bron waar het bericht vandaan komt (bijv. queue naam)
     * @param berichtData
     *            de inhoud van het bericht
     * @return een nieuw BerichtLog object
     */
    public static BerichtLog newInstance(final String referentie, final String bron, final String berichtData) {
        final BerichtLog result = new BerichtLog(referentie, bron, new Timestamp(System.currentTimeMillis()));
        result.setBerichtData(berichtData);
        return result;
    }
}
