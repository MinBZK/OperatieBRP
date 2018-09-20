/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.ist;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.ist.StapelHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.ist.StapelVoorkomenHisVolledigImpl;

/**
 * Builder klasse voor Stapel voorkomen.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class StapelVoorkomenHisVolledigImplBuilder {

    private StapelVoorkomenHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param stapel stapel van Stapel voorkomen.
     * @param volgnummer volgnummer van Stapel voorkomen.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public StapelVoorkomenHisVolledigImplBuilder(
        final StapelHisVolledigImpl stapel,
        final VolgnummerAttribuut volgnummer,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new StapelVoorkomenHisVolledigImpl(stapel, volgnummer);
        if (hisVolledigImpl.getVolgnummer() != null) {
            hisVolledigImpl.getVolgnummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param stapel stapel van Stapel voorkomen.
     * @param volgnummer volgnummer van Stapel voorkomen.
     */
    public StapelVoorkomenHisVolledigImplBuilder(final StapelHisVolledigImpl stapel, final VolgnummerAttribuut volgnummer) {
        this(stapel, volgnummer, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param volgnummer volgnummer van Stapel voorkomen.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public StapelVoorkomenHisVolledigImplBuilder(final VolgnummerAttribuut volgnummer, final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = new StapelVoorkomenHisVolledigImpl(null, volgnummer);
        if (hisVolledigImpl.getVolgnummer() != null) {
            hisVolledigImpl.getVolgnummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param volgnummer volgnummer van Stapel voorkomen.
     */
    public StapelVoorkomenHisVolledigImplBuilder(final VolgnummerAttribuut volgnummer) {
        this(null, volgnummer, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public StapelVoorkomenHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
