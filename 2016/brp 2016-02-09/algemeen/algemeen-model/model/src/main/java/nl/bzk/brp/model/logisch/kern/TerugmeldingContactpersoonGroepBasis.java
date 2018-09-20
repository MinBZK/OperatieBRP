/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.EmailadresAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.TelefoonnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface TerugmeldingContactpersoonGroepBasis extends Groep {

    /**
     * Retourneert Email van Contactpersoon.
     *
     * @return Email.
     */
    EmailadresAttribuut getEmail();

    /**
     * Retourneert Telefoonnummer van Contactpersoon.
     *
     * @return Telefoonnummer.
     */
    TelefoonnummerAttribuut getTelefoonnummer();

    /**
     * Retourneert Voornamen van Contactpersoon.
     *
     * @return Voornamen.
     */
    VoornamenAttribuut getVoornamen();

    /**
     * Retourneert Voorvoegsel van Contactpersoon.
     *
     * @return Voorvoegsel.
     */
    VoorvoegselAttribuut getVoorvoegsel();

    /**
     * Retourneert Scheidingsteken van Contactpersoon.
     *
     * @return Scheidingsteken.
     */
    ScheidingstekenAttribuut getScheidingsteken();

    /**
     * Retourneert Geslachtsnaamstam van Contactpersoon.
     *
     * @return Geslachtsnaamstam.
     */
    GeslachtsnaamstamAttribuut getGeslachtsnaamstam();

}
