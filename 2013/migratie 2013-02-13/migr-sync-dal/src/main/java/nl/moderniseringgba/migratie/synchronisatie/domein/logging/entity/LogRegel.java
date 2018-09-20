/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Logging regel.
 */
@Entity
@Table(name = "logregel", schema = "logging")
public class LogRegel {

    /**
     * De maximale lengte van het omschrijving veld.
     */
    public static final int MAX_LENGTH_OMSCHRIJVING = 400;

    @Id
    @SequenceGenerator(name = "LOG_ID_GENERATOR", sequenceName = "LOGGING.SEQ_LOGREGEL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_ID_GENERATOR")
    @Column(name = "id", nullable = false)
    private Long id;

    // bi-directional many-to-one association to BerichtLog
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "berichtlog_id")
    private BerichtLog berichtLog;

    @Enumerated(EnumType.STRING)
    @Column(name = "lo3_categorie", nullable = false, length = 2)
    private Integer categorie;

    @Column(name = "lo3_stapel", nullable = false)
    private Integer stapel;

    @Column(name = "lo3_voorkomen", nullable = false)
    private Integer voorkomen;

    @Column(name = "log_severity", nullable = false)
    private Integer severity;

    @Column(name = "log_type", nullable = false, length = 20)
    private String type;

    @Column(name = "log_code", nullable = true, length = 20)
    private String code;

    @Column(name = "log_omschrijving", nullable = true, length = MAX_LENGTH_OMSCHRIJVING)
    private String omschrijving;

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

    public final BerichtLog getBerichtLog() {
        return berichtLog;
    }

    public final void setBerichtLog(final BerichtLog berichtLog) {
        this.berichtLog = berichtLog;
    }

    public final Integer getCategorie() {
        return categorie;
    }

    public final void setCategorie(final Integer categorie) {
        this.categorie = categorie;
    }

    public final Integer getStapel() {
        return stapel;
    }

    public final void setStapel(final Integer stapel) {
        this.stapel = stapel;
    }

    public final Integer getVoorkomen() {
        return voorkomen;
    }

    public final void setVoorkomen(final Integer voorkomen) {
        this.voorkomen = voorkomen;
    }

    public final Integer getSeverity() {
        return severity;
    }

    public final void setSeverity(final Integer severity) {
        this.severity = severity;
    }

    public final String getType() {
        return type;
    }

    public final void setType(final String type) {
        this.type = type;
    }

    public final String getCode() {
        return code;
    }

    public final void setCode(final String code) {
        this.code = code;
    }

    public final String getOmschrijving() {
        return omschrijving;
    }

    /**
     * De omschrijving van de logregel. Deze omschrijving mag niet langer zijn dan {@link #MAX_LENGTH_OMSCHRIJVING}.
     * 
     * @param omschrijving
     *            de omschrijving
     */
    public final void setOmschrijving(final String omschrijving) {
        if (omschrijving != null && omschrijving.length() > MAX_LENGTH_OMSCHRIJVING) {
            throw new IllegalArgumentException(String.format("De lengte van de omschrijving is '%d' en is langer "
                    + "dan het maximum van '%d'. Omschrijving: '%s'", omschrijving.length(), MAX_LENGTH_OMSCHRIJVING,
                    omschrijving));
        }
        this.omschrijving = omschrijving;
    }

}
