/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber.basis;

import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.bericht.ber.GedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.logisch.ber.AdministratieveHandelingGedeblokkeerdeMelding;
import nl.bzk.brp.model.logisch.ber.basis.AdministratieveHandelingGedeblokkeerdeMeldingBasis;


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
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractAdministratieveHandelingGedeblokkeerdeMeldingBericht extends AbstractObjectTypeBericht
        implements AdministratieveHandelingGedeblokkeerdeMeldingBasis
{

    private AdministratieveHandelingBericht administratieveHandeling;
    private GedeblokkeerdeMeldingBericht    gedeblokkeerdeMelding;

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
