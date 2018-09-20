/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.logisch.basis;

import java.util.Collection;

import nl.bzk.copy.model.basis.ObjectType;
import nl.bzk.copy.model.groep.logisch.*;
import nl.bzk.copy.model.objecttype.logisch.*;
import nl.bzk.copy.model.objecttype.operationeel.statisch.SoortPersoon;


/**
 * .
 */
public interface PersoonBasis extends ObjectType {

    /**
     * .
     *
     * @return soortPersoon
     */
    SoortPersoon getSoort();

    /**
     * .
     *
     * @return persoon identificatienummers groep
     */
    PersoonIdentificatienummersGroep getIdentificatienummers();

    /**
     * .
     *
     * @return PersoonGeslachtsaanduidingGroep
     */
    PersoonGeslachtsaanduidingGroep getGeslachtsaanduiding();

    /**
     * .
     *
     * @return PersoonSamengesteldeNaamGroep
     */
    PersoonSamengesteldeNaamGroep getSamengesteldeNaam();

    /**
     * .
     *
     * @return PersoonAanschrijvingGroep
     */
    PersoonAanschrijvingGroep getAanschrijving();

    /**
     * .
     *
     * @return PersoonGeboorteGroep
     */
    PersoonGeboorteGroep getGeboorte();

    /**
     * .
     *
     * @return PersoonOverlijdenGroep
     */
    PersoonOverlijdenGroep getOverlijden();

    /**
     * .
     *
     * @return PersoonVerblijfsrechtGroep
     */
    PersoonVerblijfsrechtGroep getVerblijfsrecht();

    /**
     * .
     *
     * @return PersoonUitsluitingNLKiesrechtGroep
     */
    PersoonUitsluitingNLKiesrechtGroep getUitsluitingNLKiesrecht();

    /**
     * .
     *
     * @return PersoonEUVerkiezingenGroep
     */
    PersoonEUVerkiezingenGroep getEUVerkiezingen();

    /**
     * .
     *
     * @return PersoonBijhoudingsverantwoordelijkheidGroep
     */
    PersoonBijhoudingsverantwoordelijkheidGroep getBijhoudingsverantwoordelijkheid();

    /**
     * .
     *
     * @return PersoonOpschortingGroep
     */
    PersoonOpschortingGroep getOpschorting();

    /**
     * .
     *
     * @return PersoonBijhoudingsgemeenteGroep
     */
    PersoonBijhoudingsgemeenteGroep getBijhoudingsgemeente();

    /**
     * .
     *
     * @return PersoonskaartGroep
     */
    PersoonskaartGroep getPersoonsKaart();

    /**
     * .
     *
     * @return PersoonImmigratieGroep
     */
    PersoonImmigratieGroep getImmigratie();

    /**
     * .
     *
     * @return PersoonInschrijvingGroep
     */
    PersoonInschrijvingGroep getInschrijving();

    /**
     * .
     *
     * @return PersoonAfgeleidAdministratiefGroep
     */
    PersoonAfgeleidAdministratiefGroep getAfgeleidAdministratief();

    // indicaties....
    /**
     * Voorlopig nog niet implementeren.
     *
     * @return JaNee
     */
    // JaNee getGezagDerde();
    // JaNee getCuratele();
    // JaNee getVerstrekkingsbeperking();
    // JaNee getGeprivilegieerde();
    // JaNee getVastgesteldNietNederlander();
    // JaNee getBehandeldAlsNederlander();
    // JaNee getBelemmeringVerstrekkingReisdocument();
    // JaNee getBezitBuitenlandsReisdocument();
    // JaNee getStatenloos();

    /**
     * De collectie van betrokkenheden van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set.
     *
     * @return de collectie
     */
    Collection<? extends Betrokkenheid> getBetrokkenheden();

    /**
     * De collectie van adressen van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set.
     *
     * @return de collectie
     */
    Collection<? extends PersoonAdres> getAdressen();

    /**
     * De collectie van voornamen van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set.
     *
     * @return de collectie
     */
    Collection<? extends PersoonVoornaam> getPersoonVoornaam();

    /**
     * De collectie van geslachtsnaam componenten van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set.
     *
     * @return de collectie
     */
    Collection<? extends PersoonGeslachtsnaamcomponent> getGeslachtsnaamcomponenten();

    /**
     * De collectie van betrokkenheden van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set.
     *
     * @return de collectie
     */
    Collection<? extends PersoonNationaliteit> getNationaliteiten();

    /**
     * De collectie van indicaties van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set.
     *
     * @return de collectie
     */
    Collection<? extends PersoonIndicatie> getIndicaties();
}
