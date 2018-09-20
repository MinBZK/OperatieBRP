/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardetekstAttribuut;
import nl.bzk.brp.model.basis.Groep;


/**
 * De groep zoekcriteria heeft als vrijwel enige groep een 'meervoudsvorm'. Een enkelvoudsvorm zou 'Zoekingang' zijn geweest. Echter, de aanduiding
 * 'zoekcriteria' is al langer in gebruik binnen het programma. Om die reden wordt voor de meervoudsvorm gekozen. Merk op dat Zoekcriterium een onjuiste
 * term zou zijn, omdat de groep gaat over meer dan ��n zoekcriterium. (Vergelijk bijv. samengestelde naam: daar slaat deze naam wel op ALLE elementen).
 * RvdP 11 maart 2013.
 */
public interface VraagZoekcriteriaGroepBasis extends Groep {

    /**
     * Retourneert Burgerservicenummer van Zoekcriteria.
     *
     * @return Burgerservicenummer.
     */
    BurgerservicenummerAttribuut getBurgerservicenummer();

    /**
     * Retourneert Administratienummer van Zoekcriteria.
     *
     * @return Administratienummer.
     */
    AdministratienummerAttribuut getAdministratienummer();

    /**
     * Retourneert Object sleutel van Zoekcriteria.
     *
     * @return Object sleutel.
     */
    SleutelwaardetekstAttribuut getObjectSleutel();

    /**
     * Retourneert Geslachtsnaam van Zoekcriteria.
     *
     * @return Geslachtsnaam.
     */
    GeslachtsnaamstamAttribuut getGeslachtsnaam();

    /**
     * Retourneert Geboortedatum van Zoekcriteria.
     *
     * @return Geboortedatum.
     */
    DatumAttribuut getGeboortedatum();

    /**
     * Retourneert Geboortedatum kind van Zoekcriteria.
     *
     * @return Geboortedatum kind.
     */
    DatumAttribuut getGeboortedatumKind();

    /**
     * Retourneert Gemeente code van Zoekcriteria.
     *
     * @return Gemeente code.
     */
    GemeenteCodeAttribuut getGemeenteCode();

    /**
     * Retourneert Naam openbare ruimte van Zoekcriteria.
     *
     * @return Naam openbare ruimte.
     */
    NaamOpenbareRuimteAttribuut getNaamOpenbareRuimte();

    /**
     * Retourneert Huisnummer van Zoekcriteria.
     *
     * @return Huisnummer.
     */
    HuisnummerAttribuut getHuisnummer();

    /**
     * Retourneert Huisletter van Zoekcriteria.
     *
     * @return Huisletter.
     */
    HuisletterAttribuut getHuisletter();

    /**
     * Retourneert Huisnummertoevoeging van Zoekcriteria.
     *
     * @return Huisnummertoevoeging.
     */
    HuisnummertoevoegingAttribuut getHuisnummertoevoeging();

    /**
     * Retourneert Postcode van Zoekcriteria.
     *
     * @return Postcode.
     */
    PostcodeAttribuut getPostcode();

}
