/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRolAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.autaut.ToegangBijhoudingsautorisatieHisVolledigImpl;

/**
 * Builder klasse voor Toegang bijhoudingsautorisatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class ToegangBijhoudingsautorisatieHisVolledigImplBuilder {

    private ToegangBijhoudingsautorisatieHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param geautoriseerde geautoriseerde van Toegang bijhoudingsautorisatie.
     * @param ondertekenaar ondertekenaar van Toegang bijhoudingsautorisatie.
     * @param transporteur transporteur van Toegang bijhoudingsautorisatie.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public ToegangBijhoudingsautorisatieHisVolledigImplBuilder(
        final PartijRol geautoriseerde,
        final Partij ondertekenaar,
        final Partij transporteur,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl =
                new ToegangBijhoudingsautorisatieHisVolledigImpl(
                    new PartijRolAttribuut(geautoriseerde),
                    new PartijAttribuut(ondertekenaar),
                    new PartijAttribuut(transporteur));
        if (hisVolledigImpl.getGeautoriseerde() != null) {
            hisVolledigImpl.getGeautoriseerde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getOndertekenaar() != null) {
            hisVolledigImpl.getOndertekenaar().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getTransporteur() != null) {
            hisVolledigImpl.getTransporteur().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param geautoriseerde geautoriseerde van Toegang bijhoudingsautorisatie.
     * @param ondertekenaar ondertekenaar van Toegang bijhoudingsautorisatie.
     * @param transporteur transporteur van Toegang bijhoudingsautorisatie.
     */
    public ToegangBijhoudingsautorisatieHisVolledigImplBuilder(final PartijRol geautoriseerde, final Partij ondertekenaar, final Partij transporteur) {
        this(geautoriseerde, ondertekenaar, transporteur, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public ToegangBijhoudingsautorisatieHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
