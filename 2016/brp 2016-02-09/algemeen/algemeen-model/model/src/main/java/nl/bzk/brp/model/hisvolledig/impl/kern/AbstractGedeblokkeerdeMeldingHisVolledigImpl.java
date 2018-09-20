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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.GedeblokkeerdeMeldingHisVolledigBasis;

/**
 * HisVolledig klasse voor Gedeblokkeerde melding.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractGedeblokkeerdeMeldingHisVolledigImpl implements HisVolledigImpl, GedeblokkeerdeMeldingHisVolledigBasis,
        ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private Long iD;

    @Embedded
    @AttributeOverride(name = RegelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Regel"))
    @JsonProperty
    private RegelAttribuut regel;

    @Embedded
    @AttributeOverride(name = MeldingtekstAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Melding"))
    @JsonProperty
    private MeldingtekstAttribuut melding;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractGedeblokkeerdeMeldingHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param regel regel van Gedeblokkeerde melding.
     * @param melding melding van Gedeblokkeerde melding.
     */
    public AbstractGedeblokkeerdeMeldingHisVolledigImpl(final RegelAttribuut regel, final MeldingtekstAttribuut melding) {
        this();
        this.regel = regel;
        this.melding = melding;

    }

    /**
     * Retourneert ID van Gedeblokkeerde melding.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "GEDEBLOKKEERDEMELDING", sequenceName = "Kern.seq_GedeblokkeerdeMelding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "GEDEBLOKKEERDEMELDING")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Regel van Gedeblokkeerde melding.
     *
     * @return Regel.
     */
    public RegelAttribuut getRegel() {
        return regel;
    }

    /**
     * Retourneert Melding van Gedeblokkeerde melding.
     *
     * @return Melding.
     */
    public MeldingtekstAttribuut getMelding() {
        return melding;
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
        return ElementEnum.GEDEBLOKKEERDEMELDING;
    }

}
