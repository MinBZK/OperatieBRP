/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;

/**
 * Deze interface beschrijft de methodes om van objectsleutel naar bijbehorende objecten te komen. Daarnaast bevat deze
 * class functies om sleutels te valideren.
 */
public interface ObjectSleutelIndex {
    /**
     * Voegt een objectsleutel met bijbehorend element toe aan deze index alleen als het gegeven element een
     * objectsleutel bevat en te mappen is op een entiteit.
     * @param element het element met objectsleutel
     * @throws IllegalArgumentException als de index al is geinitialiseerd, dan mag er geen objectsleutel meer worden toegevoegd
     */
    void voegToe(Element element);

    /**
     * Indicatie of de index is geinitialiseerd. Een index die geinitialiseerd is heeft voor alle objectsleutels de
     * bijbehorende entiteiten gelezen uit de database.
     * @return true als de index is geinitialiseerd, anders false
     */
    boolean isInitialized();

    /**
     * Laad voor alle objectsleutels de bijbehorende RootEntiteit en geef de meldingen terug voor alle ongeldige
     * objectsleutels.
     * @return de lijst met meldingen van ongeldige objectsleutels
     */
    List<MeldingElement> initialize();

    /**
     * Geeft de entiteit die hoort bij het gegeven type en objectsleutel of null als deze niet bestaat.
     * @param entiteitType het entiteit type
     * @param objectSleutel de objectsleutel
     * @param <T> het type entiteit
     * @return de entiteit
     */
    <T extends RootEntiteit> T getEntiteitVoorObjectSleutel(Class<T> entiteitType, String objectSleutel);


    /**
     * Geeft de entiteiten die horen bij het gegeven type of een lege lijst als deze niet bestaan.
     * @param entiteitType het entiteit type
     * @param <T> het type entiteit
     * @return de entiteit
     */
    <T extends RootEntiteit> List<T> getEntiteiten(Class<T> entiteitType);

    /**
     * Geeft de entiteit die hoort bij het gegeven type en database id of een lege lijst als deze niet bestaat.
     * @param entiteitType het entiteit type
     * @param databaseId de database id
     * @param <T> het type entiteit
     * @return de lijst van entiteiten
     */
    <T extends RootEntiteit> T getEntiteitVoorId(Class<T> entiteitType, Number databaseId);

    /**
     * Voegt een root entiteit toe aan deze index. Wanneer er al een root entiteit met hetzelfde id in de index aanwezig
     * is dan wordt deze vervangen. Let op: wanneer er al een mapping bestaat van een objectsleutel op een database id
     * die gelijk is aan de id van deze root entiteit dan wordt deze nieuwe root entiteit geretourneerd bij een verzoek
     * obv objectsleutel: {@link #getEntiteitVoorObjectSleutel(Class, String)}.
     * @param rootEntiteit de root entiteit die moet worden toegevoegd
     * @return de oude root entiteit met hetzelfde id wanneer deze aanwezig is in de index, anders null
     * @throws IllegalArgumentException als {@link #isInitialized()} false is, of als {@link RootEntiteit#getId()} null is
     */
    RootEntiteit voegToe(RootEntiteit rootEntiteit);

    /**
     * Geeft het aantal root entiteiten in deze index.
     * @return het aantal root entiteiten
     */
    int size();

    /**
     * Vervangt de {@link RootEntiteit} in de index die aangewezen wordt door de gegeven database id met de nieuwe
     * {@link RootEntiteit}.
     * @param entiteitType het type root entiteit
     * @param databaseId de database id waaronder de root entiteit te vinden is in de {@link ObjectSleutelIndex}
     * @param nieuweRootEntiteit de nieuwe {@link RootEntiteit}
     * @param <T> het type van de {@link RootEntiteit}
     */
    <T extends RootEntiteit> void vervangEntiteitMetId(Class<T> entiteitType, Number databaseId, T nieuweRootEntiteit);

    /**
     * Voegt een mapping toe van de gegeven objectsleutel op een persoon in de objectsleutel index.
     * @param objectSleutel de nieuwe objectsleutel
     * @param entiteitType het type van de entiteit waarnaar de nieuwe sleutel gaat verwijzen
     * @param databaseId het id van de entiteit waarnaar deze sleutel gaat verwijzen
     * @param <T> het type entiteit
     * @throws IllegalArgumentException wanneer voor het gegeven type al een objectsleutel mapping bestaat in deze index
     * @throws IllegalArgumentException wanneer er geen persoon in de index voorkomt voor het gegeven type en database id
     */
    <T extends RootEntiteit> void voegObjectSleutelToe(String objectSleutel, Class<T> entiteitType, Number databaseId);
}
