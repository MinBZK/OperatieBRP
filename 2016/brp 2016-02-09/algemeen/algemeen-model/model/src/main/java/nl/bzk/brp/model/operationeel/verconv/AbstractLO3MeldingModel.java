/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.verconv;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
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
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.verconv.LO3MeldingBasis;

/**
 * De bij de verwerking van een LO3 bericht optredende melding.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractLO3MeldingModel extends AbstractDynamischObject implements LO3MeldingBasis, ModelIdentificeerbaar<Long> {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LO3Voorkomen")
    @JsonProperty
    private LO3VoorkomenModel lO3Voorkomen;

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
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractLO3MeldingModel() {
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
    public AbstractLO3MeldingModel(
        final LO3VoorkomenModel lO3Voorkomen,
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
     * {@inheritDoc}
     */
    @Override
    public LO3VoorkomenModel getLO3Voorkomen() {
        return lO3Voorkomen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3SoortMeldingAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3SeverityAttribuut getLogSeverity() {
        return logSeverity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3GroepAttribuut getGroep() {
        return groep;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (soort != null) {
            attributen.add(soort);
        }
        if (logSeverity != null) {
            attributen.add(logSeverity);
        }
        if (groep != null) {
            attributen.add(groep);
        }
        if (rubriek != null) {
            attributen.add(rubriek);
        }
        return attributen;
    }

}
