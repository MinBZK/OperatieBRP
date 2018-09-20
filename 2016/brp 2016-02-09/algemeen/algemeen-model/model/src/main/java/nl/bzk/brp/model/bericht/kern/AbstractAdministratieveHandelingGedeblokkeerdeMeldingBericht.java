/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandelingGedeblokkeerdeMeldingBasis;

/**
 * Het door middel van een bericht deblokkeren of gedeblokkkerd hebben van een (deblokkeerbare) fout.
 *
 * Een bijhoudingsbericht kan aanleiding zijn tot één of meer deblokkeerbare fouten. Een deblokkeerbare fout kan worden
 * gedeblokkeerd door in een bijhoudingsbericht expliciet de (deblokkeerbare) fout te de-blokkeren. Een gedeblokkeerde
 * fout wordt twee keer gekoppeld aan een bericht: enerzijds door een koppeling tussen het inkomende bijhoudingsbericht
 * en de deblokkage; anderzijds door het uitgaand bericht waarin wordt medegedeeld welke deblokkeringen zijn verwerkt.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractAdministratieveHandelingGedeblokkeerdeMeldingBericht implements BrpObject,
        AdministratieveHandelingGedeblokkeerdeMeldingBasis
{

    private AdministratieveHandelingBericht administratieveHandeling;
    private GedeblokkeerdeMeldingBericht gedeblokkeerdeMelding;

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratieveHandelingBericht getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GedeblokkeerdeMeldingBericht getGedeblokkeerdeMelding() {
        return gedeblokkeerdeMelding;
    }

    /**
     * Zet Administratieve handeling van Administratieve handeling \ Gedeblokkeerde melding.
     *
     * @param administratieveHandeling Administratieve handeling.
     */
    public void setAdministratieveHandeling(final AdministratieveHandelingBericht administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Zet Gedeblokkeerde melding van Administratieve handeling \ Gedeblokkeerde melding.
     *
     * @param gedeblokkeerdeMelding Gedeblokkeerde melding.
     */
    public void setGedeblokkeerdeMelding(final GedeblokkeerdeMeldingBericht gedeblokkeerdeMelding) {
        this.gedeblokkeerdeMelding = gedeblokkeerdeMelding;
    }

}
