/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.logisch.basis;

import java.util.Collection;

import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.groep.logisch.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsverantwoordelijkheidGroep;
import nl.bzk.brp.model.groep.logisch.PersoonEuVerkiezingenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeboorteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.groep.logisch.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.logisch.PersoonInschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOpschortingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.logisch.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonPersoonskaartGroep;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.objecttype.logisch.PersoonIndicatie;
import nl.bzk.brp.model.objecttype.logisch.PersoonNationaliteit;
import nl.bzk.brp.model.objecttype.logisch.PersoonVoornaam;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;


/**
 * .
 *
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
    PersoonEuVerkiezingenGroep getEUVerkiezingen();

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
    PersoonPersoonskaartGroep getPersoonsKaart();

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
     * @return de collectie
     */
    Collection<? extends Betrokkenheid> getBetrokkenheden();

    /**
     * De collectie van adressen van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set.
     * @return de collectie
     */
    Collection<? extends PersoonAdres> getAdressen();

    /**
     * De collectie van voornamen van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set.
     * @return de collectie
     */
    Collection<? extends PersoonVoornaam> getPersoonVoornaam();

    /**
     * De collectie van geslachtsnaam componenten van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set.
     * @return de collectie
     */
    Collection<? extends PersoonGeslachtsnaamcomponent> getGeslachtsnaamcomponenten();

    /**
     * De collectie van betrokkenheden van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set.
     * @return de collectie
     */
    Collection<? extends PersoonNationaliteit> getNationaliteiten();
    /**
     * De collectie van indicaties van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set.
     * @return de collectie
     */
    Collection<? extends PersoonIndicatie>    getIndicaties();
}
