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
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuderAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.verconv.LO3AanduidingOuderHisVolledigBasis;

/**
 * HisVolledig klasse voor LO3 Aanduiding Ouder.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractLO3AanduidingOuderHisVolledigImpl implements HisVolledigImpl, LO3AanduidingOuderHisVolledigBasis {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Ouder")
    @JsonProperty
    private OuderHisVolledigImpl ouder;

    @Embedded
    @AttributeOverride(name = LO3SoortAanduidingOuderAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt"))
    @JsonProperty
    private LO3SoortAanduidingOuderAttribuut soort;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractLO3AanduidingOuderHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param ouder ouder van LO3 Aanduiding Ouder.
     * @param soort soort van LO3 Aanduiding Ouder.
     */
    public AbstractLO3AanduidingOuderHisVolledigImpl(final OuderHisVolledigImpl ouder, final LO3SoortAanduidingOuderAttribuut soort) {
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
     * Retourneert Ouder van LO3 Aanduiding Ouder.
     *
     * @return Ouder.
     */
    public OuderHisVolledigImpl getOuder() {
        return ouder;
    }

    /**
     * Retourneert Soort van LO3 Aanduiding Ouder.
     *
     * @return Soort.
     */
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

}
