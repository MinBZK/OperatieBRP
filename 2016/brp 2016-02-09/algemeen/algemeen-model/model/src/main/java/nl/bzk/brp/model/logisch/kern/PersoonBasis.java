/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import java.util.Collection;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatie;

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
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonBasis extends BrpObject {

    /**
     * Retourneert Soort van Persoon.
     *
     * @return Soort.
     */
    SoortPersoonAttribuut getSoort();

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
     * Retourneert Nummerverwijzing van Persoon.
     *
     * @return Nummerverwijzing.
     */
    PersoonNummerverwijzingGroep getNummerverwijzing();

    /**
     * Retourneert Bijhouding van Persoon.
     *
     * @return Bijhouding.
     */
    PersoonBijhoudingGroep getBijhouding();

    /**
     * Retourneert Overlijden van Persoon.
     *
     * @return Overlijden.
     */
    PersoonOverlijdenGroep getOverlijden();

    /**
     * Retourneert Naamgebruik van Persoon.
     *
     * @return Naamgebruik.
     */
    PersoonNaamgebruikGroep getNaamgebruik();

    /**
     * Retourneert Migratie van Persoon.
     *
     * @return Migratie.
     */
    PersoonMigratieGroep getMigratie();

    /**
     * Retourneert Verblijfsrecht van Persoon.
     *
     * @return Verblijfsrecht.
     */
    PersoonVerblijfsrechtGroep getVerblijfsrecht();

    /**
     * Retourneert Uitsluiting kiesrecht van Persoon.
     *
     * @return Uitsluiting kiesrecht.
     */
    PersoonUitsluitingKiesrechtGroep getUitsluitingKiesrecht();

    /**
     * Retourneert Deelname EU verkiezingen van Persoon.
     *
     * @return Deelname EU verkiezingen.
     */
    PersoonDeelnameEUVerkiezingenGroep getDeelnameEUVerkiezingen();

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
     * Retourneert Verificaties van Persoon.
     *
     * @return Verificaties van Persoon.
     */
    Collection<? extends PersoonVerificatie> getVerificaties();

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

    /**
     * Retourneert Personen \ Onderzoek van Persoon.
     *
     * @return Personen \ Onderzoek van Persoon.
     */
    Collection<? extends PersoonOnderzoek> getOnderzoeken();

    /**
     * Retourneert Persoon \ Verstrekkingsbeperkingen van Persoon.
     *
     * @return Persoon \ Verstrekkingsbeperkingen van Persoon.
     */
    Collection<? extends PersoonVerstrekkingsbeperking> getVerstrekkingsbeperkingen();

    /**
     * Retourneert Persoon \ Afnemerindicaties van Persoon.
     *
     * @return Persoon \ Afnemerindicaties van Persoon.
     */
    Collection<? extends PersoonAfnemerindicatie> getAfnemerindicaties();

    /**
     * Geeft de indicatie Derde heeft gezag? terug.
     *
     * @return Derde heeft gezag?
     */
    PersoonIndicatie getIndicatieDerdeHeeftGezag();

    /**
     * Geeft de indicatie Onder curatele? terug.
     *
     * @return Onder curatele?
     */
    PersoonIndicatie getIndicatieOnderCuratele();

    /**
     * Geeft de indicatie Volledige verstrekkingsbeperking? terug.
     *
     * @return Volledige verstrekkingsbeperking?
     */
    PersoonIndicatie getIndicatieVolledigeVerstrekkingsbeperking();

    /**
     * Geeft de indicatie Vastgesteld niet Nederlander? terug.
     *
     * @return Vastgesteld niet Nederlander?
     */
    PersoonIndicatie getIndicatieVastgesteldNietNederlander();

    /**
     * Geeft de indicatie Behandeld als Nederlander? terug.
     *
     * @return Behandeld als Nederlander?
     */
    PersoonIndicatie getIndicatieBehandeldAlsNederlander();

    /**
     * Geeft de indicatie Signalering met betrekking tot verstrekken reisdocument? terug.
     *
     * @return Signalering met betrekking tot verstrekken reisdocument?
     */
    PersoonIndicatie getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument();

    /**
     * Geeft de indicatie Staatloos? terug.
     *
     * @return Staatloos?
     */
    PersoonIndicatie getIndicatieStaatloos();

    /**
     * Geeft de indicatie Bijzondere verblijfsrechtelijke positie? terug.
     *
     * @return Bijzondere verblijfsrechtelijke positie?
     */
    PersoonIndicatie getIndicatieBijzondereVerblijfsrechtelijkePositie();

}
