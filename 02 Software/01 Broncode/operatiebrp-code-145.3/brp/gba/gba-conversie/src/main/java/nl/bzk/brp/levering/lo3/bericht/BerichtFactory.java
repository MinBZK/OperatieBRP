/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;

/**
 * Service voor het maken van lo3 berichten.
 */
public interface BerichtFactory {

    /**
     * Stelt een lijst met synchronisatieberichten samen, afhankelijk van de administratieve handeling en het abonnement/dienst dat verwerkt wordt komt er een
     * kennisgeving en/of een Volledigbericht in.
     * @param leveringAutorisatie populatie De leveringAutorisatie
     * @param populatie de personen en hun relatie met de indicatie hoe een persoon in de populatie staat
     * @param administratieveHandeling De adminstratieve handeling
     * @param identificatienummerMutatieResultaat Het resultaat van de identificatienummer mutatie, gebruikt voor A-nummer wijzigingen
     * @return De lijst met leveringsberichten.
     */
    List<Bericht> maakBerichten(
            Autorisatiebundel leveringAutorisatie,
            Map<Persoonslijst, Populatie> populatie,
            AdministratieveHandeling administratieveHandeling,
            IdentificatienummerMutatie identificatienummerMutatieResultaat);

    /**
     * Maak een bericht tbv plaatsen afnemersindicatie.
     * @param persoon persoon
     * @return bericht
     */
    Bericht maakAg01Bericht(final Persoonslijst persoon);

    /**
     * Maak een bericht tbv plaatsen afnemersindicatie bij selectie.
     * @param persoon persoon
     * @return bericht
     */
    Bericht maakAg11Bericht(final Persoonslijst persoon);

    /**
     * Maak een bericht tbv ad hoc persoonsvraag
     * @param persoon persoon
     * @return bericht
     */
    Bericht maakHa01Bericht(final Persoonslijst persoon);

    /**
     * Maak een fout bericht tbv selecties.
     * @param persoon persoon
     * @return bericht
     */
    Bericht maakSf01Bericht(final Persoonslijst persoon);

    /**
     * Maak een bericht tbv selecties.
     * @param persoon persoon
     * @return bericht
     */
    Bericht maakSv01Bericht(final Persoonslijst persoon);

    /**
     * Maak een bericht tbv selecties.
     * @return bericht
     */
    Bericht maakSv11Bericht();

    /**
     * Maak een bericht tbv ad hoc adresvraag
     * @param personen lijst van personen
     * @return Xa01 bericht
     */
    Xa01Bericht maakXa01Bericht(final List<Persoonslijst> personen);

    /**
     * Maak een antwoord tbv ad hoc webservice.
     * @param personen lijst van personen
     * @return webservice antwoord
     */
    AdhocWebserviceAntwoord maakAdhocAntwoord(final List<Persoonslijst> personen);

    /**
     * Maak een antwoord tbv vraag PL webservice.
     * @param personen lijst van personen
     * @return webservice antwoord
     */
    OpvragenPLWebserviceAntwoord maakVraagPLAntwoord(final List<Persoonslijst> personen);
}
