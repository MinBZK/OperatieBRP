/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;

/**
 * Service voor Persoon.
 */
public interface PersoonService {

    /**
     * Converteert een Persoon naar een BrpPersoonslijst.
     * @param persoon de te converteren persoon
     * @return de geconverteerde BrpPersoonslijst
     */
    BrpPersoonslijst converteerNaarBrpPersoonslijst(Persoon persoon);

    /**
     * Persisteer persoon.
     * @param persoon de te persisteren persoon
     */
    void persisteerPersoon(Persoon persoon);

    /**
     * Zoekt naar personen in de BRP database die voldoen aan de gegevens zoals meegegeven in het Persoon-object.
     * @param persoon De persoongegevens waarop wordt gezocht.
     * @return lijst van gevonden personen die voldoen aan de meegegeven zoekargumenten.
     */
    List<Persoon> zoekPersoon(Persoon persoon);

    /**
     * Zoekt op basis van de meegegeven actuele gegevens personen in de database.
     * @param administratienummer a-nummer
     * @param burgerservicenummer bsn
     * @param geslachtsnaamstam geslachtsnaam
     * @param postcode postcode
     * @return Lijst van gevonden personen
     */
    List<Persoon> zoekPersonenOpActueleGegevens(String administratienummer, String burgerservicenummer, String geslachtsnaamstam, String postcode);

    /**
     * Zoekt op basis van de meegegeven historische gegevens personen in de database.
     * @param administratienummer a-nummer
     * @param burgerservicenummer bsn
     * @param geslachtsnaamstam geslachtsnaam
     * @return Lijst van gevonden personen
     */
    List<Persoon> zoekPersonenOpHistorischeGegevens(String administratienummer, String burgerservicenummer, String geslachtsnaamstam);

    /**
     * Vraag een persoon op uit de BRP database voor de meegegeven technische sleutel, deze persoon moet bestaan anders
     * treedt er een fout op.
     * @param technischeSleutel de technische sleutel van de op te zoeken persoon
     * @param indicatieFoutiefOpgeschortUitsluiten indicator of we foutief opgeschorte persoonlijsten met opschort reden 'F' en 'W' meenemen in het resultaat of
     * negeren
     * @return De Persoon in brp formaat
     * @throws IllegalStateException wanneer geen persoon gevonden is
     */
    Persoon zoekIngeschrevenPersoonOpTechnischeSleutel(Long technischeSleutel, boolean indicatieFoutiefOpgeschortUitsluiten);

    /**
     * Zoekt ingeschreven persoon op basis van administratienummer.
     * @param administratienummer administratienummer
     * @param indicatieFoutiefOpgeschortUitsluiten indicatie of foutief opgeschorte personen moeten worden uitgesloten
     * @return de gevonden persoon
     */
    Persoon zoekIngeschrevenPersoon(String administratienummer, boolean indicatieFoutiefOpgeschortUitsluiten);


    /**
     * Zoekt ingeschreven personen (foutief opgeschorte personen uitgesloten) op basis van historisch administratienummer.
     * @param administratienummer administratienummer
     * @return lijst van gevonden personen
     */
    List<Persoon> zoekIngeschrevenPersonenHistorisch(String administratienummer);

    /**
     * Zoekt ingeschreven persoon (foutief opgeschorte personen uitgesloten) op basis van burgerservicenumer
     * @param burgerservicenummer burgerservicenummer
     * @return lijst van gevonden personen
     */
    Persoon zoekIngeschrevenPersoonOpBsn(String burgerservicenummer);

    /**
     * Zoekt ingeschreven personen (foutief opgeschorte personen uitgesloten) op basis van historisch burgerservicenumer
     * @param burgerservicenummer burgerservicenummer
     * @return lijst van gevonden personen
     */
    List<Persoon> zoekIngeschrevenPersonenHistorischOpBsn(String burgerservicenummer);

    /**
     * Zoekt ingeschreven personen (foutief opgeschorte personen uitgesloten) op basis van volgende administratienummer.
     * @param administratienummer volgende administratienummer
     * @return lijst van gevonden personen
     */
    List<Persoon> zoekIngeschrevenPersonenOpVolgendeAnummer(String administratienummer);

    /**
     * Zoekt ingeschreven personen (foutief opgeschorte personen uitgesloten) op basis van historisch volgende administratienummer.
     * @param administratienummer volgende administratienummer
     * @return lijst van gevonden personen
     */
    List<Persoon> zoekIngeschrevenPersonenHistorischOpVolgendeAnummer(String administratienummer);

    /**
     * Zoekt ingeschreven personen (foutief opgeschorte personen uitgesloten) op basis van vorige administratienummer.
     * @param administratienummer vorige administratienummer
     * @return lijst van gevonden personen
     */
    List<Persoon> zoekIngeschrevenPersonenOpVorigeAnummer(String administratienummer);

    /**
     * Zoekt ingeschreven personen (foutief opgeschorte personen uitgesloten) op basis van historisch vorige administratienummer.
     * @param administratienummer vorige administratienummer
     * @return lijst van gevonden personen
     */
    List<Persoon> zoekIngeschrevenPersonenHistorischOpVorigeAnummer(String administratienummer);
}
