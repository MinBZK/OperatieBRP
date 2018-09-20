/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.verconv;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GroepAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3RubriekExclCategorieEnGroepAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SeverityAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortMeldingAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.verconv.LO3MeldingHisVolledigBasis;

/**
 * HisVolledig klasse voor LO3 Melding.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractLO3MeldingHisVolledigImpl implements HisVolledigImpl, LO3MeldingHisVolledigBasis {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LO3Voorkomen")
    @JsonProperty
    private LO3VoorkomenHisVolledigImpl lO3Voorkomen;

    @Embedded
    @AttributeOverride(name = LO3SoortMeldingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt"))
    @JsonProperty
    private LO3SoortMeldingAttribuut soort;

    @Embedded
    @AttributeOverride(name = LO3SeverityAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "LogSeverity"))
    @JsonProperty
    private LO3SeverityAttribuut logSeverity;

    @Embedded
    @AttributeOverride(name = LO3GroepAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Groep"))
    @JsonProperty
    private LO3GroepAttribuut groep;

    @Embedded
    @AttributeOverride(name = LO3RubriekExclCategorieEnGroepAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr"))
    @JsonProperty
    private LO3RubriekExclCategorieEnGroepAttribuut rubriek;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractLO3MeldingHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param lO3Voorkomen lO3Voorkomen van LO3 Melding.
     * @param soort soort van LO3 Melding.
     * @param logSeverity logSeverity van LO3 Melding.
     * @param groep groep van LO3 Melding.
     * @param rubriek rubriek van LO3 Melding.
     */
    public AbstractLO3MeldingHisVolledigImpl(
        final LO3VoorkomenHisVolledigImpl lO3Voorkomen,
        final LO3SoortMeldingAttribuut soort,
        final LO3SeverityAttribuut logSeverity,
        final LO3GroepAttribuut groep,
        final LO3RubriekExclCategorieEnGroepAttribuut rubriek)
    {
        this();
        this.lO3Voorkomen = lO3Voorkomen;
        this.soort = soort;
        this.logSeverity = logSeverity;
        this.groep = groep;
        this.rubriek = rubriek;

    }

    /**
     * Retourneert ID van LO3 Melding.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "LO3MELDING", sequenceName = "VerConv.seq_LO3Melding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LO3MELDING")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert LO3 voorkomen van LO3 Melding.
     *
     * @return LO3 voorkomen.
     */
    public LO3VoorkomenHisVolledigImpl getLO3Voorkomen() {
        return lO3Voorkomen;
    }

    /**
     * Retourneert Soort van LO3 Melding.
     *
     * @return Soort.
     */
    public LO3SoortMeldingAttribuut getSoort() {
        return soort;
    }

    /**
     * Retourneert Log severity van LO3 Melding.
     *
     * @return Log severity.
     */
    public LO3SeverityAttribuut getLogSeverity() {
        return logSeverity;
    }

    /**
     * Retourneert Groep van LO3 Melding.
     *
     * @return Groep.
     */
    public LO3GroepAttribuut getGroep() {
        return groep;
    }

    /**
     * Retourneert Rubriek van LO3 Melding.
     *
     * @return Rubriek.
     */
    public LO3RubriekExclCategorieEnGroepAttribuut getRubriek() {
        return rubriek;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Long id) {
        this.iD = id;
    }

}
