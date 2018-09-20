/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Zowel het Burgerservicenummer als het administratienummer worden in de Wet BRP genoemd en aangemerkt als een Algemeen
 * gegeven. Het is na wijziging van één van de nummers, ongeacht de reden voor de wijziging, noodzakelijk de afnemers
 * die geautoriseerd zijn voor deze gegevens te informeren over deze wijziging. De BRP voorziet in het leveren van deze
 * informatie. Om die reden dienen deze attributen aangemerkt te worden als Administratief gegeven en kunnen, mits de
 * afnemer daarvoor is geautoriseerd, worden geleverd. Het Centraal Bureau voor de Statistiek (CBS) is bijvoorbeeld een
 * voor de wijziging van identificerende nummers geautoriseerde afnemer.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonNummerverwijzingGroepBasis extends Groep {

    /**
     * Retourneert Vorige burgerservicenummer van Nummerverwijzing.
     *
     * @return Vorige burgerservicenummer.
     */
    BurgerservicenummerAttribuut getVorigeBurgerservicenummer();

    /**
     * Retourneert Volgende burgerservicenummer van Nummerverwijzing.
     *
     * @return Volgende burgerservicenummer.
     */
    BurgerservicenummerAttribuut getVolgendeBurgerservicenummer();

    /**
     * Retourneert Vorige administratienummer van Nummerverwijzing.
     *
     * @return Vorige administratienummer.
     */
    AdministratienummerAttribuut getVorigeAdministratienummer();

    /**
     * Retourneert Volgende administratienummer van Nummerverwijzing.
     *
     * @return Volgende administratienummer.
     */
    AdministratienummerAttribuut getVolgendeAdministratienummer();

}
