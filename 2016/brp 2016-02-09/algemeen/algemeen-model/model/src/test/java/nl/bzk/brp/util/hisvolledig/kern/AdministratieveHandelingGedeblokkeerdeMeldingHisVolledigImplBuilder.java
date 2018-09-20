/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.GedeblokkeerdeMeldingHisVolledigImpl;

/**
 * Builder klasse voor Administratieve handeling \ Gedeblokkeerde melding.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImplBuilder {

    private AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param administratieveHandeling administratieveHandeling van Administratieve handeling \ Gedeblokkeerde melding.
     * @param gedeblokkeerdeMelding gedeblokkeerdeMelding van Administratieve handeling \ Gedeblokkeerde melding.
     */
    public AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImplBuilder(
        final AdministratieveHandelingHisVolledigImpl administratieveHandeling,
        final GedeblokkeerdeMeldingHisVolledigImpl gedeblokkeerdeMelding)
    {
        this.hisVolledigImpl = new AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl(administratieveHandeling, gedeblokkeerdeMelding);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param gedeblokkeerdeMelding gedeblokkeerdeMelding van Administratieve handeling \ Gedeblokkeerde melding.
     */
    public AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImplBuilder(final GedeblokkeerdeMeldingHisVolledigImpl gedeblokkeerdeMelding) {
        this.hisVolledigImpl = new AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl(null, gedeblokkeerdeMelding);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
