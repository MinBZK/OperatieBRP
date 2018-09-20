/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.autaut;

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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.autaut.BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigBasis;

/**
 * HisVolledig klasse voor Bijhoudingsautorisatie \ Soort administratieve handeling.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractBijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImpl implements HisVolledigImpl,
        BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigBasis
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ToegangBijhautorisatie")
    @JsonProperty
    private ToegangBijhoudingsautorisatieHisVolledigImpl toegangBijhoudingsautorisatie;

    @Embedded
    @AttributeOverride(name = SoortAdministratieveHandelingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "SrtAdmHnd"))
    @JsonProperty
    private SoortAdministratieveHandelingAttribuut soortAdministratieveHandeling;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractBijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param toegangBijhoudingsautorisatie toegangBijhoudingsautorisatie van Bijhoudingsautorisatie \ Soort
     *            administratieve handeling.
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van Bijhoudingsautorisatie \ Soort
     *            administratieve handeling.
     */
    public AbstractBijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImpl(
        final ToegangBijhoudingsautorisatieHisVolledigImpl toegangBijhoudingsautorisatie,
        final SoortAdministratieveHandelingAttribuut soortAdministratieveHandeling)
    {
        this();
        this.toegangBijhoudingsautorisatie = toegangBijhoudingsautorisatie;
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;

    }

    /**
     * Retourneert ID van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "BIJHOUDINGSAUTORISATIESOORTADMINISTRATIEVEHANDELING", sequenceName = "AutAut.seq_BijhautorisatieSrtAdmHnd")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BIJHOUDINGSAUTORISATIESOORTADMINISTRATIEVEHANDELING")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Toegang bijhoudingsautorisatie van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return Toegang bijhoudingsautorisatie.
     */
    public ToegangBijhoudingsautorisatieHisVolledigImpl getToegangBijhoudingsautorisatie() {
        return toegangBijhoudingsautorisatie;
    }

    /**
     * Retourneert Soort administratieve handeling van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return Soort administratieve handeling.
     */
    public SoortAdministratieveHandelingAttribuut getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Integer id) {
        this.iD = id;
    }

}
