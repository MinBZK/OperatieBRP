/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

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

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.ber.basis.AdministratieveHandelingGedeblokkeerdeMeldingBasis;
import nl.bzk.brp.model.operationeel.ber.GedeblokkeerdeMeldingModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;


/**
 * Het door middel van een bericht deblokkeren of gedeblokkkerd hebben van een (deblokkeerbare) fout.
 *
 * Een bijhoudingsbericht kan aanleiding zijn tot ��n of meer deblokkeerbare fouten. Een deblokkeerbare fout kan worden
 * gedeblokkeerd door in een bijhoudingsbericht expliciet de (deblokkeerbare) fout te de-blokkeren. Een gedeblokkeerde
 * fout wordt twee keer gekoppeld aan een bericht: enerzijds door een koppeling tussen het inkomende bijhoudingsbericht
 * en de deblokkage; anderzijds door het uitgaand bericht waarin wordt medegedeeld welke deblokkeringen zijn verwerkt.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractAdministratieveHandelingGedeblokkeerdeMeldingModel extends AbstractDynamischObjectType
        implements AdministratieveHandelingGedeblokkeerdeMeldingBasis
{

    @Id
    @SequenceGenerator(name = "ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING",
                       sequenceName = "Ber.seq_AdmHndGedeblokkeerdeMelding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING")
    @JsonProperty
    private Long                          iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdmHnd")
    private AdministratieveHandelingModel administratieveHandeling;

    @ManyToOne
    @JoinColumn(name = "GedeblokkeerdeMelding")
    @JsonProperty
    private GedeblokkeerdeMeldingModel    gedeblokkeerdeMelding;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractAdministratieveHandelingGedeblokkeerdeMeldingModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param administratieveHandeling administratieveHandeling van Administratieve handeling \ Gedeblokkeerde melding.
     * @param gedeblokkeerdeMelding gedeblokkeerdeMelding van Administratieve handeling \ Gedeblokkeerde melding.
     */
    public AbstractAdministratieveHandelingGedeblokkeerdeMeldingModel(
            final AdministratieveHandelingModel administratieveHandeling,
            final GedeblokkeerdeMeldingModel gedeblokkeerdeMelding)
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
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Administratieve handeling van Administratieve handeling \ Gedeblokkeerde melding.
     *
     * @return Administratieve handeling.
     */
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Retourneert Gedeblokkeerde melding van Administratieve handeling \ Gedeblokkeerde melding.
     *
     * @return Gedeblokkeerde melding.
     */
    public GedeblokkeerdeMeldingModel getGedeblokkeerdeMelding() {
        return gedeblokkeerdeMelding;
    }

}
