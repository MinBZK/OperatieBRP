/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;

/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 *
 * Buiten de BRP wordt ook wel de term 'Natuurlijk persoon' gebruikt. In de BRP worden zowel personen waarvan de
 * bijhouding valt onder afdeling I ('Ingezetenen') van de Wet BRP, als personen waarvoor de bijhouding onder afdeling
 * II ('Niet-ingezetenen') van de Wet BRP valt, ingeschreven.
 *
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor dit objecttype de naam
 * "Natuurlijk persoon" te gebruiken. Binnen de context van BRP hebben we het bij het hanteren van de term Persoon
 * echter nooit over niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de term Persoon is verder dermate
 * gebruikelijk binnen de context van BRP, dat ervoor gekozen is deze naam te blijven hanteren. We spreken dus over
 * Persoon en niet over "Natuurlijk persoon". 2. Voor gerelateerde personen, en voor niet-ingeschrevenen, gebruiken we
 * in het logisch & operationeel model (maar dus NIET in de gegevensset) het construct 'persoon'. Om die reden zijn veel
 * groepen niet verplicht, die wellicht wel verplicht zouden zijn in geval van (alleen) ingeschrevenen.
 *
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisMomentGenerator")
public interface PersoonHisMoment extends Persoon {

    /**
     * Retourneert Afgeleid administratief van Persoon.
     *
     * @return Afgeleid administratief.
     */
    HisPersoonAfgeleidAdministratiefGroep getAfgeleidAdministratief();

    /**
     * Retourneert Identificatienummers van Persoon.
     *
     * @return Identificatienummers.
     */
    HisPersoonIdentificatienummersGroep getIdentificatienummers();

    /**
     * Retourneert Samengestelde naam van Persoon.
     *
     * @return Samengestelde naam.
     */
    HisPersoonSamengesteldeNaamGroep getSamengesteldeNaam();

    /**
     * Retourneert Geboorte van Persoon.
     *
     * @return Geboorte.
     */
    HisPersoonGeboorteGroep getGeboorte();

    /**
     * Retourneert Geslachtsaanduiding van Persoon.
     *
     * @return Geslachtsaanduiding.
     */
    HisPersoonGeslachtsaanduidingGroep getGeslachtsaanduiding();

    /**
     * Retourneert Inschrijving van Persoon.
     *
     * @return Inschrijving.
     */
    HisPersoonInschrijvingGroep getInschrijving();

    /**
     * Retourneert Nummerverwijzing van Persoon.
     *
     * @return Nummerverwijzing.
     */
    HisPersoonNummerverwijzingGroep getNummerverwijzing();

    /**
     * Retourneert Bijhouding van Persoon.
     *
     * @return Bijhouding.
     */
    HisPersoonBijhoudingGroep getBijhouding();

    /**
     * Retourneert Overlijden van Persoon.
     *
     * @return Overlijden.
     */
    HisPersoonOverlijdenGroep getOverlijden();

    /**
     * Retourneert Naamgebruik van Persoon.
     *
     * @return Naamgebruik.
     */
    HisPersoonNaamgebruikGroep getNaamgebruik();

    /**
     * Retourneert Migratie van Persoon.
     *
     * @return Migratie.
     */
    HisPersoonMigratieGroep getMigratie();

    /**
     * Retourneert Verblijfsrecht van Persoon.
     *
     * @return Verblijfsrecht.
     */
    HisPersoonVerblijfsrechtGroep getVerblijfsrecht();

    /**
     * Retourneert Uitsluiting kiesrecht van Persoon.
     *
     * @return Uitsluiting kiesrecht.
     */
    HisPersoonUitsluitingKiesrechtGroep getUitsluitingKiesrecht();

    /**
     * Retourneert Deelname EU verkiezingen van Persoon.
     *
     * @return Deelname EU verkiezingen.
     */
    HisPersoonDeelnameEUVerkiezingenGroep getDeelnameEUVerkiezingen();

    /**
     * Retourneert Persoonskaart van Persoon.
     *
     * @return Persoonskaart.
     */
    HisPersoonPersoonskaartGroep getPersoonskaart();

}
