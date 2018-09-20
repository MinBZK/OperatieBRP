/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.RegelverantwoordingHisVolledigBasis;

/**
 * HisVolledig klasse voor Regelverantwoording.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractRegelverantwoordingHisVolledigImpl implements HisVolledigImpl, RegelverantwoordingHisVolledigBasis, ElementIdentificeerbaar {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Actie")
    @JsonProperty
    private ActieHisVolledigImpl actie;

    @Embedded
    @AttributeOverride(name = RegelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Regel"))
    @JsonProperty
    private RegelAttribuut regel;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractRegelverantwoordingHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param actie actie van Regelverantwoording.
     * @param regel regel van Regelverantwoording.
     */
    public AbstractRegelverantwoordingHisVolledigImpl(final ActieHisVolledigImpl actie, final RegelAttribuut regel) {
        this();
        this.actie = actie;
        this.regel = regel;

    }

    /**
     * Retourneert ID van Regelverantwoording.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "REGELVERANTWOORDING", sequenceName = "Kern.seq_Regelverantwoording")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "REGELVERANTWOORDING")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Actie van Regelverantwoording.
     *
     * @return Actie.
     */
    public ActieHisVolledigImpl getActie() {
        return actie;
    }

    /**
     * Retourneert Regel van Regelverantwoording.
     *
     * @return Regel.
     */
    public RegelAttribuut getRegel() {
        return regel;
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
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.REGELVERANTWOORDING;
    }

}
