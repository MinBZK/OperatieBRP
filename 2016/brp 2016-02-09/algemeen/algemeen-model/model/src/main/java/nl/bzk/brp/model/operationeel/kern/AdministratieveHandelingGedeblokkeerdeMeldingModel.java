/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandelingGedeblokkeerdeMelding;


/**
 * Het door middel van een bericht deblokkeren of gedeblokkkerd hebben van een (deblokkeerbare) fout.
 * <p/>
 * Een bijhoudingsbericht kan aanleiding zijn tot ��n of meer deblokkeerbare fouten. Een deblokkeerbare fout kan worden gedeblokkeerd door in een
 * bijhoudingsbericht expliciet de (deblokkeerbare) fout te de-blokkeren. Een gedeblokkeerde fout wordt twee keer gekoppeld aan een bericht: enerzijds door
 * een koppeling tussen het inkomende bijhoudingsbericht en de deblokkage; anderzijds door het uitgaand bericht waarin wordt medegedeeld welke
 * deblokkeringen zijn verwerkt.
 */
@Entity
@Table(schema = "Kern", name = "AdmHndGedeblokkeerdeMelding")
public class AdministratieveHandelingGedeblokkeerdeMeldingModel extends
    AbstractAdministratieveHandelingGedeblokkeerdeMeldingModel implements
    AdministratieveHandelingGedeblokkeerdeMelding, ModelIdentificeerbaar<Long>
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected AdministratieveHandelingGedeblokkeerdeMeldingModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param administratieveHandeling administratieveHandeling van Administratieve handeling \ Gedeblokkeerde melding.
     * @param gedeblokkeerdeMelding    gedeblokkeerdeMelding van Administratieve handeling \ Gedeblokkeerde melding.
     */
    public AdministratieveHandelingGedeblokkeerdeMeldingModel(
        final AdministratieveHandelingModel administratieveHandeling,
        final GedeblokkeerdeMeldingModel gedeblokkeerdeMelding)
    {
        super(administratieveHandeling, gedeblokkeerdeMelding);
    }

}
