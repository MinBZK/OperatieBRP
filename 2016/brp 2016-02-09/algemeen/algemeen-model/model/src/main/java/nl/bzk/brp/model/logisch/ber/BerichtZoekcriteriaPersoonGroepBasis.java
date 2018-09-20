/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardetekstAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Zoekcriteria, waarmee een persoon - of meerdere personen - wordt gezocht.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface BerichtZoekcriteriaPersoonGroepBasis extends Groep {

    /**
     * Retourneert Burgerservicenummer van Zoekcriteria persoon.
     *
     * @return Burgerservicenummer.
     */
    BurgerservicenummerAttribuut getBurgerservicenummer();

    /**
     * Retourneert Administratienummer van Zoekcriteria persoon.
     *
     * @return Administratienummer.
     */
    AdministratienummerAttribuut getAdministratienummer();

    /**
     * Retourneert Object sleutel van Zoekcriteria persoon.
     *
     * @return Object sleutel.
     */
    SleutelwaardetekstAttribuut getObjectSleutel();

    /**
     * Retourneert Geslachtsnaamstam van Zoekcriteria persoon.
     *
     * @return Geslachtsnaamstam.
     */
    GeslachtsnaamstamAttribuut getGeslachtsnaamstam();

    /**
     * Retourneert Geboortedatum van Zoekcriteria persoon.
     *
     * @return Geboortedatum.
     */
    DatumEvtDeelsOnbekendAttribuut getGeboortedatum();

    /**
     * Retourneert Geboortedatum kind van Zoekcriteria persoon.
     *
     * @return Geboortedatum kind.
     */
    DatumEvtDeelsOnbekendAttribuut getGeboortedatumKind();

    /**
     * Retourneert Gemeente code van Zoekcriteria persoon.
     *
     * @return Gemeente code.
     */
    GemeenteCodeAttribuut getGemeenteCode();

    /**
     * Retourneert Naam openbare ruimte van Zoekcriteria persoon.
     *
     * @return Naam openbare ruimte.
     */
    NaamOpenbareRuimteAttribuut getNaamOpenbareRuimte();

    /**
     * Retourneert Afgekorte naam openbare ruimte van Zoekcriteria persoon.
     *
     * @return Afgekorte naam openbare ruimte.
     */
    AfgekorteNaamOpenbareRuimteAttribuut getAfgekorteNaamOpenbareRuimte();

    /**
     * Retourneert Huisnummer van Zoekcriteria persoon.
     *
     * @return Huisnummer.
     */
    HuisnummerAttribuut getHuisnummer();

    /**
     * Retourneert Huisletter van Zoekcriteria persoon.
     *
     * @return Huisletter.
     */
    HuisletterAttribuut getHuisletter();

    /**
     * Retourneert Huisnummertoevoeging van Zoekcriteria persoon.
     *
     * @return Huisnummertoevoeging.
     */
    HuisnummertoevoegingAttribuut getHuisnummertoevoeging();

    /**
     * Retourneert Postcode van Zoekcriteria persoon.
     *
     * @return Postcode.
     */
    PostcodeAttribuut getPostcode();

    /**
     * Retourneert Identificatiecode nummeraanduiding van Zoekcriteria persoon.
     *
     * @return Identificatiecode nummeraanduiding.
     */
    IdentificatiecodeNummeraanduidingAttribuut getIdentificatiecodeNummeraanduiding();

}
