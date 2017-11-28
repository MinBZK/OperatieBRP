/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3Severity;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the lo3melding database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "lo3melding", schema = "verconv")
public class Lo3Melding extends AbstractEntiteit implements Serializable {
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
    private int logSeverityId;

    @Column(name = "srt", nullable = false)
    private int soortMeldingId;

    // bi-directional many-to-one association to Lo3Voorkomen
    @ManyToOne
    @JoinColumn(name = "lo3voorkomen", nullable = false)
    private Lo3Voorkomen voorkomen;

    /**
     * JPA default constructor.
     */
    protected Lo3Melding() {}

    /**
     * Maak een nieuwe lo3 melding.
     *
     * @param voorkomen voorkomen
     * @param soortMelding soort melding
     * @param logSeverity log severity
     */
    public Lo3Melding(final Lo3Voorkomen voorkomen, final Lo3SoortMelding soortMelding, final Lo3Severity logSeverity) {
        setVoorkomen(voorkomen);
        setSoortMelding(soortMelding);
        setLogSeverity(logSeverity);
    }

    /**
     * Maak een nieuwe lo3 melding.
     *
     * @param voorkomen voorkomen
     * @param soortMelding soort melding
     * @param logSeverity log severity
     * @param groep groep
     * @param rubriek rubriek
     */
    public Lo3Melding(final Lo3Voorkomen voorkomen, final Lo3SoortMelding soortMelding, final Lo3Severity logSeverity, final String groep,
            final String rubriek) {
        this(voorkomen, soortMelding, logSeverity);
        this.groep = groep;
        this.rubriek = rubriek;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Lo3Melding.
     *
     * @param id de nieuwe waarde voor id van Lo3Melding
     */
    protected void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van groep van Lo3Melding.
     *
     * @return de waarde van groep van Lo3Melding
     */
    public String getGroep() {
        return groep;
    }

    /**
     * Zet de waarden voor groep van Lo3Melding.
     *
     * @param groep de nieuwe waarde voor groep van Lo3Melding
     */
    public void setGroep(final String groep) {
        this.groep = groep;
    }

    /**
     * Geef de waarde van rubriek van Lo3Melding.
     *
     * @return de waarde van rubriek van Lo3Melding
     */
    public String getRubriek() {
        return rubriek;
    }

    /**
     * Zet de waarden voor rubriek van Lo3Melding.
     *
     * @param rubriek de nieuwe waarde voor rubriek van Lo3Melding
     */
    public void setRubriek(final String rubriek) {
        this.rubriek = rubriek;
    }

    /**
     * Geef de waarde van log severity van Lo3Melding.
     *
     * @return de waarde van log severity van Lo3Melding
     */
    public Lo3Severity getLogSeverity() {
        return Lo3Severity.parseId(logSeverityId);
    }

    /**
     * Zet de waarden voor log severity van Lo3Melding.
     *
     * @param logSeverity de nieuwe waarde voor log severity van Lo3Melding
     */
    public void setLogSeverity(final Lo3Severity logSeverity) {
        ValidationUtils.controleerOpNullWaarden("logSeverity mag niet null zijn", logSeverity);
        logSeverityId = logSeverity.getId();
    }

    /**
     * Geef de waarde van soort melding van Lo3Melding.
     *
     * @return de waarde van soort melding van Lo3Melding
     */
    public Lo3SoortMelding getSoortMelding() {
        return Lo3SoortMelding.parseId(soortMeldingId);
    }

    /**
     * Zet de waarden voor soort melding van Lo3Melding.
     *
     * @param soortMelding de nieuwe waarde voor soort melding van Lo3Melding
     */
    public void setSoortMelding(final Lo3SoortMelding soortMelding) {
        ValidationUtils.controleerOpNullWaarden("soortMelding mag niet null zijn", soortMelding);
        soortMeldingId = soortMelding.getId();
    }

    /**
     * Geef de waarde van voorkomen van Lo3Melding.
     *
     * @return de waarde van voorkomen van Lo3Melding
     */
    public Lo3Voorkomen getVoorkomen() {
        return voorkomen;
    }

    /**
     * Zet de waarden voor voorkomen van Lo3Melding.
     *
     * @param voorkomen de nieuwe waarde voor voorkomen van Lo3Melding
     */
    public void setVoorkomen(final Lo3Voorkomen voorkomen) {
        ValidationUtils.controleerOpNullWaarden("voorkomen mag niet null zijn", voorkomen);
        this.voorkomen = voorkomen;
    }

}
