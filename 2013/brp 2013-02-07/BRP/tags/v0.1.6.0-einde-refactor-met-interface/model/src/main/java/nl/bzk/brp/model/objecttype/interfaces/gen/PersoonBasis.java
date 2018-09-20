/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.interfaces.gen;

import java.util.List;

import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonBijhoudingsGemeenteGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonBijhoudingsVerantwoordelijkheidGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonGeboorteGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonGeslachtsAanduidingGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonIdentificatieNummersGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonInschrijvingGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonOpschortingGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonskaartGroep;
import nl.bzk.brp.model.objecttype.interfaces.usr.Betrokkenheid;
import nl.bzk.brp.model.objecttype.interfaces.usr.PersoonAdres;
import nl.bzk.brp.model.objecttype.statisch.SoortPersoon;

/**
 * .
 *
 */
public interface PersoonBasis extends ObjectType  {

    /**
     * .
     * @return soortPersoon
     */
    SoortPersoon                        getSoort();
    /**
     * .
     * @return persoon identificatieNummers groep
     */
    PersoonIdentificatieNummersGroep getIdentificatieNummers();
    /**
     * .
     * @return PersoonGeslachtsAanduidingGroep
     */
    PersoonGeslachtsAanduidingGroep getGeslachtsAanduiding();
    /**
     * .
     * @return PersoonSamengesteldeNaamGroep
     */
    PersoonSamengesteldeNaamGroep getSamengesteldeNaam();
    /**
     * .
     * @return PersoonAanschrijvingGroep
     */
    PersoonAanschrijvingGroep getAanschrijving();
    /**
     * .
     * @return PersoonGeboorteGroep
     */
    PersoonGeboorteGroep getGeboorte();
    /**
     * .
     * @return PersoonOverlijdenGroep
     */
    PersoonOverlijdenGroep getOverlijden();
    /**
     * .
     * @return PersoonVerblijfsrechtGroep
     */
    PersoonVerblijfsrechtGroep getVerblijfsrecht();
    /**
     * .
     * @return PersoonUitsluitingNLKiesrechtGroep
     */
    PersoonUitsluitingNLKiesrechtGroep getUitsluitingNLKiesrecht();
    /**
     * .
     * @return PersoonEUVerkiezingenGroep
     */
    PersoonEUVerkiezingenGroep getEUVerkiezingen();
    /**
     * .
     * @return PersoonBijhoudingsVerantwoordelijkeGroep
     */
    PersoonBijhoudingsVerantwoordelijkheidGroep getBijhoudingVerantwoordelijke();
    /**
     * .
     * @return PersoonOpschortingGroep
     */
    PersoonOpschortingGroep getOpschorting();
    /**
     * .
     * @return PersoonBijhoudingsGemeenteGroep
     */
    PersoonBijhoudingsGemeenteGroep getBijhoudenGemeente();
    /**
     * .
     * @return PersoonskaartGroep
     */
    PersoonskaartGroep getPersoonsKaart();
    /**
     * .
     * @return PersoonImmigratieGroep
     */
    PersoonImmigratieGroep getImmigratie();
    /**
     * .
     * @return PersoonInschrijvingGroep
     */
    PersoonInschrijvingGroep getInschrijving();
    /**
     * .
     * @return PersoonAfgeleidAdministratiefGroep
     */
    PersoonAfgeleidAdministratiefGroep getAfgeleidAdministratief();


    // indicaties....
    /**
     * Voorlopig nog niet implementeren.
     * @return JaNee
     */
    //    JaNee                           getGezagDerde();
    //    JaNee                           getCuratele();
    //    JaNee                           getVerstrekkingsbeperking();
    //    JaNee                           getGeprivilegieerde();
    //    JaNee                           getVastgesteldNietNederlander();
    //    JaNee                           getBehandeldAlsNederlander();
    //    JaNee                           getBelemmeringVerstrekkingReisdocument();
    //    JaNee                           getBezitBuitenlandsReisdocument();
    //    JaNee                           getStatenloos();

    /**
     * .
     * @return .
     */
    List<? extends Betrokkenheid>          getBetrokkenheden();
    /**
     * .
     * @return .
     */
    List<? extends PersoonAdres>           getAdressen();

}
