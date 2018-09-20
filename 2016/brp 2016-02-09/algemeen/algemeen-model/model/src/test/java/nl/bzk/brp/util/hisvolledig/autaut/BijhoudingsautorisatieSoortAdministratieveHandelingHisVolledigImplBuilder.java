/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.autaut.BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.autaut.ToegangBijhoudingsautorisatieHisVolledigImpl;

/**
 * Builder klasse voor Bijhoudingsautorisatie \ Soort administratieve handeling.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImplBuilder {

    private BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param toegangBijhoudingsautorisatie toegangBijhoudingsautorisatie van Bijhoudingsautorisatie \ Soort
     *            administratieve handeling.
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van Bijhoudingsautorisatie \ Soort
     *            administratieve handeling.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImplBuilder(
        final ToegangBijhoudingsautorisatieHisVolledigImpl toegangBijhoudingsautorisatie,
        final SoortAdministratieveHandeling soortAdministratieveHandeling,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl =
                new BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImpl(
                    toegangBijhoudingsautorisatie,
                    new SoortAdministratieveHandelingAttribuut(soortAdministratieveHandeling));
        if (hisVolledigImpl.getSoortAdministratieveHandeling() != null) {
            hisVolledigImpl.getSoortAdministratieveHandeling().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param toegangBijhoudingsautorisatie toegangBijhoudingsautorisatie van Bijhoudingsautorisatie \ Soort
     *            administratieve handeling.
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van Bijhoudingsautorisatie \ Soort
     *            administratieve handeling.
     */
    public BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImplBuilder(
        final ToegangBijhoudingsautorisatieHisVolledigImpl toegangBijhoudingsautorisatie,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        this(toegangBijhoudingsautorisatie, soortAdministratieveHandeling, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
