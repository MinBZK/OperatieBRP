/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigBasis;

/**
 * HisVolledig klasse voor Administratieve handeling \ Gedeblokkeerde melding.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractAdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl implements HisVolledigImpl,
        AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigBasis, ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdmHnd")
    @JsonBackReference
    private AdministratieveHandelingHisVolledigImpl administratieveHandeling;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GedeblokkeerdeMelding")
    @JsonProperty
    private GedeblokkeerdeMeldingHisVolledigImpl gedeblokkeerdeMelding;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractAdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param administratieveHandeling administratieveHandeling van Administratieve handeling \ Gedeblokkeerde melding.
     * @param gedeblokkeerdeMelding gedeblokkeerdeMelding van Administratieve handeling \ Gedeblokkeerde melding.
     */
    public AbstractAdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl(
        final AdministratieveHandelingHisVolledigImpl administratieveHandeling,
        final GedeblokkeerdeMeldingHisVolledigImpl gedeblokkeerdeMelding)
    {
        this();
        this.administratieveHandeling = administratieveHandeling;
        this.gedeblokkeerdeMelding = gedeblokkeerdeMelding;

    }

    /**
     * Retourneert ID van Administratieve handeling \ Gedeblokkeerde melding.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING", sequenceName = "Kern.seq_AdmHndGedeblokkeerdeMelding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Administratieve handeling van Administratieve handeling \ Gedeblokkeerde melding.
     *
     * @return Administratieve handeling.
     */
    public AdministratieveHandelingHisVolledigImpl getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Retourneert Gedeblokkeerde melding van Administratieve handeling \ Gedeblokkeerde melding.
     *
     * @return Gedeblokkeerde melding.
     */
    public GedeblokkeerdeMeldingHisVolledigImpl getGedeblokkeerdeMelding() {
        return gedeblokkeerdeMelding;
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
        return ElementEnum.ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING;
    }

}
