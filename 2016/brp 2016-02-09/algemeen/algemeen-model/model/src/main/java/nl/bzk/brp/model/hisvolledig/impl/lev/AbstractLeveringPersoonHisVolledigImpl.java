/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.lev;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.lev.LeveringPersoonHisVolledigBasis;

/**
 * HisVolledig klasse voor Levering \ Persoon.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractLeveringPersoonHisVolledigImpl implements HisVolledigImpl, LeveringPersoonHisVolledigBasis {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "levsaantek")
    @JsonProperty
    private LeveringHisVolledigImpl levering;

    @Column(name = "Pers")
    @JsonProperty
    private Integer persoonId;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractLeveringPersoonHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param levering levering van Levering \ Persoon.
     * @param persoonId persoonId van Levering \ Persoon.
     */
    public AbstractLeveringPersoonHisVolledigImpl(final LeveringHisVolledigImpl levering, final Integer persoonId) {
        this();
        this.levering = levering;
        this.persoonId = persoonId;

    }

    /**
     * Retourneert ID van Levering \ Persoon.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "LEVERINGPERSOON", sequenceName = "Lev.seq_LevPers")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LEVERINGPERSOON")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Levering van Levering \ Persoon.
     *
     * @return Levering.
     */
    public LeveringHisVolledigImpl getLevering() {
        return levering;
    }

    /**
     * Retourneert Persoon van Levering \ Persoon.
     *
     * @return Persoon.
     */
    public Integer getPersoonId() {
        return persoonId;
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
