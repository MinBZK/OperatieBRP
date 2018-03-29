/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType.Kandidaat;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.synchronisatie.dal.service.TeLeverenAdministratieveHandelingenAanwezigException;


/**
 * Persoonslijst service. Facade voor alle bewerking op een persoonslijst (zoeken, opslaan, etc).
 */
public interface PlService {

    /**
     * Zoek een (niet opgeschort met reden 'F' of 'W') persoonslijst met het gegeven actuele
     * a-nummer.
     * @param anummer a-nummer
     * @return gevonden persoonslijst, of null als niet gevonden.
     * @throws IllegalStateException als meerdere niet foutief opgeschorte persoonslijsten worden gevonden met het gegevens actuele a-nummer
     */
    BrpPersoonslijst zoekNietFoutievePersoonslijstOpActueelAnummer(String anummer);

    /**
     * Zoek een (niet opgeschort met reden 'F' of 'W') persoonslijst met het gegeven burgerservicenummer.
     * @param burgerservicenummer het burgerservicenummer
     * @return gevonden persoonslijst, of null als niet gevonden.
     * @throws IllegalStateException als meerdere niet foutief opgeschorte persoonslijsten worden gevonden met het gegeven burgerservicenummer
     */
    BrpPersoonslijst zoekNietFoutievePersoonslijstOpActueelBurgerservicenummer(String burgerservicenummer);

    /**
     * Zoek (niet opgeschort met reden 'F' of 'W') persoonslijsten met het gegeven historische a-nummer.
     * @param anummer a-nummer
     * @return gevonden persoonslijsten, lege lijst als niets gevonden
     */
    List<BrpPersoonslijst> zoekNietFoutievePersoonslijstenOpHistorischAnummer(String anummer);

    /**
     * Zoek (niet opgeschort met reden 'F' of 'W') persoonslijsten met het gegeven historische burgerservice.
     * @param burgerservicenummer burgerservicenummer
     * @return gevonden persoonslijsten, lege lijst als niets gevonden
     */
    List<BrpPersoonslijst> zoekNietFoutievePersoonslijstenOpHistorischBurgerservicenummer(String burgerservicenummer);


    /**
     * Sla een persoonslijst als nieuwe persoonslijst op.
     * @param brpPersoonslijst persoonslijst
     * @param loggingBericht logging bericht (om te koppelen aan persoonslijst)
     * @return administratieve handeling id
     */
    List<Long> persisteerPersoonslijst(BrpPersoonslijst brpPersoonslijst, Lo3Bericht loggingBericht);

    /**
     * Sla een persoonslijst op als vervanging voor een bestaande persoonslijst.
     * @param brpPersoonslijst persoonslijst
     * @param teVervangenPersoonslijstId id van de te vervangen persoonslijst
     * @param loggingBericht logging bericht (om te koppelen aan persoonslijst)
     * @return administratieve handeling ids
     */
    List<Long> persisteerPersoonslijst(BrpPersoonslijst brpPersoonslijst, Long teVervangenPersoonslijstId, Lo3Bericht loggingBericht)
            throws TeLeverenAdministratieveHandelingenAanwezigException;

    /**
     * Sla een logging bericht op (zonder koppeling naar persoonslijst).
     * @param bericht logging bericht
     */
    void persisteerLogging(Lo3Bericht bericht);

    /**
     * Converteer een Lo3Persoonslijst naar een BrpPersoonslijst.
     * @param lo3Persoonslijst De te converteren LO3 persoonslijst
     * @return De BRP persoonslijst
     */
    BrpPersoonslijst converteerLo3PersoonlijstNaarBrpPersoonslijst(Lo3Persoonslijst lo3Persoonslijst);

    /**
     * Converteer een kandidaat PL naar LO3 teletex string.
     * @param persoonslijst persoonslijst
     * @return LO3 teletex string.
     */
    String converteerKandidaat(BrpPersoonslijst persoonslijst);

    /**
     * Converteer meerdere kandidaat PL-en naar LO3 teleex strings.
     * @param persoonslijsten persoonlijsten
     * @return array van kandidaten
     */
    Kandidaat[] converteerKandidaten(List<BrpPersoonslijst> persoonslijsten);

    /**
     * Zoekt een bestaande persoonslijst in de database op basis van de technische sleutel.
     * @param technischeSleutel De sleutel waarop wordt gezocht
     * @return De gevonden BRP persoonslijst
     */
    BrpPersoonslijst zoekPersoonslijstOpTechnischeSleutel(Long technischeSleutel);

    /**
     * Zoek (niet opgeschort met reden 'F' of 'W') persoonslijsten met het gegeven actuele volgende a-nummer.
     * @param anummer volgende a-nummer
     * @return gevonden persoonslijsten, lege lijst als niets gevonden
     */
    List<BrpPersoonslijst> zoekNietFoutievePersoonslijstenOpActueelVolgendeAnummer(String anummer);

    /**
     * Zoek (niet opgeschort met reden 'F' of 'W') persoonslijsten met het gegeven historische volgende a-nummer.
     * @param anummer volgende a-nummer
     * @return gevonden persoonslijsten, lege lijst als niets gevonden
     */
    List<BrpPersoonslijst> zoekNietFoutievePersoonslijstenOpHistorischVolgendeAnummer(String anummer);

    /**
     * Zoek (niet opgeschort met reden 'F' of 'W') persoonslijsten met het gegeven actuele vorige a-nummer.
     * @param anummer vorige a-nummer
     * @return gevonden persoonslijsten, lege lijst als niets gevonden
     */
    List<BrpPersoonslijst> zoekNietFoutievePersoonslijstenOpActueelVorigeAnummer(String anummer);

    /**
     * Zoek (niet opgeschort met reden 'F' of 'W') persoonslijsten met het gegeven historische vorige a-nummer.
     * @param anummer volgende a-nummer
     * @return gevonden persoonslijsten, lege lijst als niets gevonden
     */
    List<BrpPersoonslijst> zoekNietFoutievePersoonslijstenOpHistorischVorigeAnummer(String anummer);
}
