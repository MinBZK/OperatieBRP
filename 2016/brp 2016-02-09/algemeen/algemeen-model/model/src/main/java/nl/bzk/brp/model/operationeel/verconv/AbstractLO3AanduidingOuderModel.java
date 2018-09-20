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
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuderAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.verconv.LO3AanduidingOuderBasis;
import nl.bzk.brp.model.operationeel.kern.OuderModel;

/**
 * Aanduiding welke BRP Ouder Betrokkenheid als Ouder 1/2 in LO3 berichten geleverd wordt.
 *
 * Deze mapping zorgt voor een consistente opbouw van LO3 berichten. De inhoud van deze tabel wordt bepaald op basis van
 * een GBA bijhouding of tijdens het (eerste keer) leveren van een LO3 Bericht.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractLO3AanduidingOuderModel extends AbstractDynamischObject implements LO3AanduidingOuderBasis, ModelIdentificeerbaar<Long> {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Ouder")
    @JsonProperty
    private OuderModel ouder;

    @Embedded
    @AttributeOverride(name = LO3SoortAanduidingOuderAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt"))
    @JsonProperty
    private LO3SoortAanduidingOuderAttribuut soort;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractLO3AanduidingOuderModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param ouder ouder van LO3 Aanduiding Ouder.
     * @param soort soort van LO3 Aanduiding Ouder.
     */
    public AbstractLO3AanduidingOuderModel(final OuderModel ouder, final LO3SoortAanduidingOuderAttribuut soort) {
        this();
        this.ouder = ouder;
        this.soort = soort;

    }

    /**
     * Retourneert ID van LO3 Aanduiding Ouder.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "LO3AANDUIDINGOUDER", sequenceName = "VerConv.seq_LO3AandOuder")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LO3AANDUIDINGOUDER")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OuderModel getOuder() {
        return ouder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3SoortAanduidingOuderAttribuut getSoort() {
        return soort;
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
        return attributen;
    }

}
