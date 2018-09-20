/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import java.util.Collection;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import nl.bzk.brp.model.logisch.kern.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijzondereVerblijfsrechtelijkePositieGroep;
import nl.bzk.brp.model.logisch.kern.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.kern.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.logisch.kern.PersoonImmigratieGroep;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;
import nl.bzk.brp.model.logisch.kern.PersoonInschrijvingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;
import nl.bzk.brp.model.logisch.kern.PersoonOpschortingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonPersoonskaartGroep;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocument;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.logisch.kern.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVerblijfstitelGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;


/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 *
 * Buiten de BRP wordt ook wel de term 'Natuurlijk persoon' gebruikt.
 * In de BRP worden zowel personen ingeschreven die onder een College van Burgemeester en Wethouders vallen
 * ('ingezetenen'), als personen waarvoor de Minister verantwoordelijkheid geldt.
 *
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor dit objecttype de naam
 * "Natuurlijk persoon" te gebruiken. Binnen de context van BRP hebben we het bij het hanteren van de term Persoon
 * echter nooit over niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de term Persoon is verder dermate
 * gebruikelijk binnen de context van BRP, dat ervoor gekozen is deze naam te blijven hanteren. We spreken dus over
 * Persoon en niet over "Natuurlijk persoon".
 * 2. Voor "alternatieve realiteit" personen, en voor niet-ingeschrevenen, gebruiken we in het logisch & operationeel
 * model (maar dus NIET in de gegevensset) het construct 'persoon'. Om die reden zijn veel groepen niet verplicht, die
 * wellicht wel verplicht zouden zijn in geval van (alleen) ingeschrevenen.
 * RvdP 27 juni 2011
 *
 *
 *
 *
 */
public interface PersoonBasis extends ObjectType {

    /**
     * Retourneert Soort van Persoon.
     *
     * @return Soort.
     */
    SoortPersoon getSoort();

    /**
     * Retourneert Afgeleid administratief van Persoon.
     *
     * @return Afgeleid administratief.
     */
    PersoonAfgeleidAdministratiefGroep getAfgeleidAdministratief();

    /**
     * Retourneert Identificatienummers van Persoon.
     *
     * @return Identificatienummers.
     */
    PersoonIdentificatienummersGroep getIdentificatienummers();

    /**
     * Retourneert Samengestelde naam van Persoon.
     *
     * @return Samengestelde naam.
     */
    PersoonSamengesteldeNaamGroep getSamengesteldeNaam();

    /**
     * Retourneert Geboorte van Persoon.
     *
     * @return Geboorte.
     */
    PersoonGeboorteGroep getGeboorte();

    /**
     * Retourneert Geslachtsaanduiding van Persoon.
     *
     * @return Geslachtsaanduiding.
     */
    PersoonGeslachtsaanduidingGroep getGeslachtsaanduiding();

    /**
     * Retourneert Inschrijving van Persoon.
     *
     * @return Inschrijving.
     */
    PersoonInschrijvingGroep getInschrijving();

    /**
     * Retourneert Bijhoudingsaard van Persoon.
     *
     * @return Bijhoudingsaard.
     */
    PersoonBijhoudingsaardGroep getBijhoudingsaard();

    /**
     * Retourneert Bijhoudingsgemeente van Persoon.
     *
     * @return Bijhoudingsgemeente.
     */
    PersoonBijhoudingsgemeenteGroep getBijhoudingsgemeente();

    /**
     * Retourneert Opschorting van Persoon.
     *
     * @return Opschorting.
     */
    PersoonOpschortingGroep getOpschorting();

    /**
     * Retourneert Overlijden van Persoon.
     *
     * @return Overlijden.
     */
    PersoonOverlijdenGroep getOverlijden();

    /**
     * Retourneert Aanschrijving van Persoon.
     *
     * @return Aanschrijving.
     */
    PersoonAanschrijvingGroep getAanschrijving();

    /**
     * Retourneert Immigratie van Persoon.
     *
     * @return Immigratie.
     */
    PersoonImmigratieGroep getImmigratie();

    /**
     * Retourneert Verblijfstitel van Persoon.
     *
     * @return Verblijfstitel.
     */
    PersoonVerblijfstitelGroep getVerblijfstitel();

    /**
     * Retourneert Bijzondere verblijfsrechtelijke positie van Persoon.
     *
     * @return Bijzondere verblijfsrechtelijke positie.
     */
    PersoonBijzondereVerblijfsrechtelijkePositieGroep getBijzondereVerblijfsrechtelijkePositie();

    /**
     * Retourneert Uitsluiting NL kiesrecht van Persoon.
     *
     * @return Uitsluiting NL kiesrecht.
     */
    PersoonUitsluitingNLKiesrechtGroep getUitsluitingNLKiesrecht();

    /**
     * Retourneert EU verkiezingen van Persoon.
     *
     * @return EU verkiezingen.
     */
    PersoonEUVerkiezingenGroep getEUVerkiezingen();

    /**
     * Retourneert Persoonskaart van Persoon.
     *
     * @return Persoonskaart.
     */
    PersoonPersoonskaartGroep getPersoonskaart();

    /**
     * Retourneert Voornamen van Persoon.
     *
     * @return Voornamen van Persoon.
     */
    Collection<? extends PersoonVoornaam> getVoornamen();

    /**
     * Retourneert Geslachtsnaamcomponenten van Persoon.
     *
     * @return Geslachtsnaamcomponenten van Persoon.
     */
    Collection<? extends PersoonGeslachtsnaamcomponent> getGeslachtsnaamcomponenten();

    /**
     * Retourneert Persoon \ Nationaliteiten van Persoon.
     *
     * @return Persoon \ Nationaliteiten van Persoon.
     */
    Collection<? extends PersoonNationaliteit> getNationaliteiten();

    /**
     * Retourneert Adressen van Persoon.
     *
     * @return Adressen van Persoon.
     */
    Collection<? extends PersoonAdres> getAdressen();

    /**
     * Retourneert Indicaties van Persoon.
     *
     * @return Indicaties van Persoon.
     */
    Collection<? extends PersoonIndicatie> getIndicaties();

    /**
     * Retourneert Reisdocumenten van Persoon.
     *
     * @return Reisdocumenten van Persoon.
     */
    Collection<? extends PersoonReisdocument> getReisdocumenten();

    /**
     * Retourneert Betrokkenheden van Persoon.
     *
     * @return Betrokkenheden van Persoon.
     */
    Collection<? extends Betrokkenheid> getBetrokkenheden();

}
