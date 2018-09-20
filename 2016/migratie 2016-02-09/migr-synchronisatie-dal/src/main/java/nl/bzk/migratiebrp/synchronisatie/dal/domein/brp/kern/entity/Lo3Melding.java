/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the lo3melding database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "lo3melding", schema = "verconv")
@SuppressWarnings("checkstyle:designforextension")
public class Lo3Melding extends AbstractDeltaEntiteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "lo3melding_id_generator", sequenceName = "verconv.seq_lo3melding", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lo3melding_id_generator")
    @Column(updatable = false)
    private Long id;

    private String groep;

    @Column(name = "rubr")
    private String rubriek;

    @Column(name = "logseverity", nullable = false)
    private short logSeverityId;

    @Column(name = "srt", nullable = false)
    private short soortMeldingId;

    // bi-directional many-to-one association to Lo3Voorkomen
    @ManyToOne
    @JoinColumn(name = "lo3voorkomen", nullable = false)
    private Lo3Voorkomen voorkomen;

    /**
     * JPA default constructor.
     */
    protected Lo3Melding() {
    }

    /**
     * Maak een nieuwe lo3 melding.
     *
     * @param voorkomen
     *            voorkomen
     * @param soortMelding
     *            soort melding
     * @param logSeverity
     *            log severity
     */
    public Lo3Melding(final Lo3Voorkomen voorkomen, final Lo3SoortMelding soortMelding, final Lo3Severity logSeverity) {
        setVoorkomen(voorkomen);
        setSoortMelding(soortMelding);
        setLogSeverity(logSeverity);
    }

    /**
     * Maak een nieuwe lo3 melding.
     *
     * @param voorkomen
     *            voorkomen
     * @param soortMelding
     *            soort melding
     * @param logSeverity
     *            log severity
     * @param groep
     *            groep
     * @param rubriek
     *            rubriek
     */
    public Lo3Melding(
        final Lo3Voorkomen voorkomen,
        final Lo3SoortMelding soortMelding,
        final Lo3Severity logSeverity,
        final String groep,
        final String rubriek)
    {
        this(voorkomen, soortMelding, logSeverity);
        this.groep = groep;
        this.rubriek = rubriek;
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    protected void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van groep.
     *
     * @return groep
     */
    public String getGroep() {
        return groep;
    }

    /**
     * Zet de waarde van groep.
     *
     * @param groep
     *            groep
     */
    public void setGroep(final String groep) {
        this.groep = groep;
    }

    /**
     * Geef de waarde van rubriek.
     *
     * @return rubriek
     */
    public String getRubriek() {
        return rubriek;
    }

    /**
     * Zet de waarde van rubriek.
     *
     * @param rubriek
     *            rubriek
     */
    public void setRubriek(final String rubriek) {
        this.rubriek = rubriek;
    }

    /**
     * Geef de waarde van log severity.
     *
     * @return log severity
     */
    public Lo3Severity getLogSeverity() {
        return Lo3Severity.parseId(logSeverityId);
    }

    /**
     * Zet de waarde van log severity.
     *
     * @param logSeverity
     *            log severity
     */
    public void setLogSeverity(final Lo3Severity logSeverity) {
        ValidationUtils.controleerOpNullWaarden("logSeverity mag niet null zijn", logSeverity);
        logSeverityId = logSeverity.getId();
    }

    /**
     * Geef de waarde van soort melding.
     *
     * @return soort melding
     */
    public Lo3SoortMelding getSoortMelding() {
        return Lo3SoortMelding.parseId(soortMeldingId);
    }

    /**
     * Zet de waarde van soort melding.
     *
     * @param soortMelding
     *            soort melding
     */
    public void setSoortMelding(final Lo3SoortMelding soortMelding) {
        ValidationUtils.controleerOpNullWaarden("soortMelding mag niet null zijn", soortMelding);
        soortMeldingId = soortMelding.getId();
    }

    /**
     * Geef de waarde van voorkomen.
     *
     * @return voorkomen
     */
    public Lo3Voorkomen getVoorkomen() {
        return voorkomen;
    }

    /**
     * Zet de waarde van voorkomen.
     *
     * @param voorkomen
     *            voorkomen
     */
    public void setVoorkomen(final Lo3Voorkomen voorkomen) {
        ValidationUtils.controleerOpNullWaarden("voorkomen mag niet null zijn", voorkomen);
        this.voorkomen = voorkomen;
    }

}
