/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.basis;

import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;

/**
 * BerichtControleService.
 */
public interface BerichtControleService extends Stateful {

    /**
     * Type bericht ter controle.
     */
    enum TypeBericht {
        /**
         * Het antwoordbericht.
         */
        ANTWOORDBERICHT,
        /**
         * Het leverbericht.
         */
        LEVERBERICHT;
    }

    /**
     * Assert dat het antwoordbericht gelijk is aan de gegeven expectedFile.
     * @param typeBericht het type bericht om te controleren
     * @param expectedFile een filepad met het verwachte antwoord
     * @throws Exception als er een fout optreedt
     */
    void assertBerichtGelijkAanExpected(TypeBericht typeBericht, String expectedFile, List<String> sorteerAttributen) throws Exception;

    /**
     * Assert dat het antwoordbericht gelijk is aan de gegeven expectedFile.
     * @param typeBericht het type bericht om te controleren
     * @param expectedFile een filepad met het verwachte antwoord
     * @param attributen de attributen waarvoor volgorde niet van belang is
     * @throws Exception als er een fout optreedt
     */
    void assertBerichtGelijkAanExpectedOngeachtVolgorde(TypeBericht typeBericht, String expectedFile, List<String> attributen)
            throws Exception;


    /**
     * Assert dat het antwoordbericht de gegeven verwerking heeft.
     * @param verwerking de verwerking die wordt verwacht. Mogelijke waardes zijn: `Geslaagd` en `Foutief`
     */
    void assertAntwoordberichtHeeftVerwerking(String verwerking);

    /**
     * Assert dat het antwoordbericht de gegeven meldingen bevat.
     * @param typeBericht het type bericht
     * @param meldingSet set van meldingen
     */
    void assertBerichtHeeftMelding(final TypeBericht typeBericht, Set<String> meldingSet);

    /**
     * Controleert xpath Controleert of er voor gegeven xpath geen node aanwezig is in het xml leveringbericht.
     * @param typeBericht het type bericht
     * @param xpathExpressie de xpath expressie
     */
    void assertNodeBestaatNiet(TypeBericht typeBericht, String xpathExpressie);

    /**
     * Controleert xpath Controleert of er voor gegeven xpath een node aanwezig is in het xml leveringbericht.
     * @param typeBericht het te controleren type bericht.
     * @param xpathExpressie de xpath expressie
     */
    void assertNodeBestaat(TypeBericht typeBericht, String xpathExpressie);

    /**
     * Controleert xpath Controleert of de waarde binnen de response gevonden kan worden via de opgegeven xpath expressie.
     * @param typeBericht het te controleren type bericht
     * @param xpathExpressie de xPath expressie
     * @param waarde de te vinden waarde
     */
    void assertPlatteWaardeGelijk(TypeBericht typeBericht, String xpathExpressie, String waarde);

    /**
     * Controleert xpath Controleert of de waarde binnen de response gevonden kan worden via de opgegeven xpath expressie.
     * @param typeBericht het te controleren type bericht
     * @param xpathExpressie de xPath expressie
     * @param waarde de te vinden waarde
     */
    void assertEvaluatieGelijkAanWaarde(TypeBericht typeBericht, String xpathExpressie, String waarde);

    /**
     * Assert dat de gegeven node bestaat en geen onderliggende nodes heeft.
     * @param typeBericht het te controleren type bericht
     * @param xpathExpressie de xPath expressie
     */
    void assertBevatLeafNode(TypeBericht typeBericht, String xpathExpressie);


    /**
     * Assert dat het bericht het gegeven aantal meldingen bevat.
     * @param typeBericht het te controleren type bericht
     * @param count het aantal meldingen
     */
    void assertHeeftBerichtAantalMeldingen(TypeBericht typeBericht, int count);

    /**
     * Assert dat het bericht het gegeven aantal meldingen bevat voor een bepaald regelnummer voor een
     * lijst personen
     * @param typeBericht het te controleren type bericht
     * @param count het aantal meldingen
     * @param regelnummer nummer van de {@link Regel}
     * @param bsnLijst lijst met burgerservicenummers
     */
    void assertHeeftBerichtAantalMeldingenVoorPersonen(TypeBericht typeBericht, int count, String regelnummer, List<String> bsnLijst);

    /**
     * Assert dat het bericht het gegeven element bevat.
     * @param typeBericht het type bericht
     * @param element het element om te controleren
     * @param moetAanwezigZijn de aanwezigheid
     */
    void assertElementAanwezig(TypeBericht typeBericht, String element, boolean moetAanwezigZijn);

    /**
     * Assert dat het bericht het gegeven element een X aantal keer voorkomt.
     * @param typeBericht het type bericht
     * @param element het element om te controleren
     * @param aantal het aantal keer dat het element voorkomt
     */
    void assertElementAantal(TypeBericht typeBericht, String element, int aantal);

    /**
     * Assert dat de verantwoording correct is.
     * @param typeBericht het type bericht
     */
    void assertVerantwoordingCorrect(TypeBericht typeBericht);


    /**
     * Attribuut in bericht controleren Valideert of in het (synchroon) antwoordbericht een attribuut van een groep een verwachte waarde heeft. Deze step
     * faciliteert het valideren van waardes in meerdere voorkomens, vandaar dat er meerdere waardes kunnen worden opgegeven, gescheiden door een komma.
     * <p>
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een bevraging antwoordbericht:
     * <p>
     * [source,xml] ---- &lt;adres&gt; &lt;huisnummer&gt;14&lt;/huisnummer&gt; ... &lt;/adres&gt; ----
     * @param typeBericht het type bericht
     * @param attribuut het attribuut (in XML een element) waarvan de waardes worden gevraagd
     * @param groep de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param verwachteWaardes de verwachte waardes van het gegeven attribuut. Indien een attribuut meer dan een keer voorkomt, geef dan de waardes in de
     * volgorde waarin ze in het bericht staan.
     */
    void assertBerichtHeeftWaardes(TypeBericht typeBericht, String groep, String attribuut, List<String> verwachteWaardes);

    /**
     * Attribuut aanwezigheid in (synchronisatie)bericht controleren Valideert of in het synchronisatiebericht een attribuut van een groep aanwezig is.
     * Deze step faciliteert het valideren van een specifiek voorkomen van een groep, vandaar dat het nummer van het voorkomen kan worden opgegeven.
     * <p>
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een synchronisatiebericht:
     * <p>
     * [source,xml] ---- &lt;adres&gt; &lt;huisnummer&gt;14&lt;/huisnummer&gt; ... &lt;/adres&gt; ----
     * @param typeBericht het type bericht
     * @param voorkomen de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param voorkomenIndex index van voorkomen
     * @param attribuut het attribuut (in XML een element) waarvan de aanwezigheid worden gevraagd
     * @param aanwezig de verwachte aanwezigheid van het gegeven attribuut
     */
    void assertAttribuutAanwezigheid(TypeBericht typeBericht, String voorkomen, int voorkomenIndex, String attribuut, boolean aanwezig);

    /**
     * Attribuut waardes in (synchronisatie)bericht controleren Valideert of in het synchronisatiebericht een attribuut van een groep een verwachte waarde
     * heeft. Deze step faciliteert het valideren van een specifiek voorkomen van een groep, vandaar dat het nummer van het voorkomen kan worden
     * opgegeven.
     * <p>
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een synchronisatiebericht:
     * <p>
     * [source,xml] ---- &lt;adres&gt; &lt;huisnummer&gt;14&lt;/huisnummer&gt; ... &lt;/adres&gt; ----
     * @param typeBericht het type bericht
     * @param attribuut het attribuut (in XML een element) waarvan de waardes worden gevraagd
     * @param voorkomen de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param voorkomenIndex index van voorkomen
     * @param verwachteWaarde de verwachte waardes van het gegeven attribuut. Indien een attribuut meer dan een keer voorkomt, geef dan de waardes in de
     * volgorde waarin ze in het bericht staan.
     */
    void assertBerichtHeeftWaarde(TypeBericht typeBericht, String voorkomen, int voorkomenIndex, String attribuut, String verwachteWaarde);


    /**
     * Waarde van het xml attribuut "verwerkingssoort" in (synchronisatie)bericht controleren Valideert of in het geselecteerde synchronisatiebericht de
     * verwerkingssoort van een groep een verwachte waarde heeft. Deze step faciliteert het valideren van een specifiek voorkomen van een groep, vandaar
     * dat het nummer van het voorkomen kan worden opgegeven.
     * @param typeBericht het type bericht
     * @param voorkomen de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param voorkomenIndex geeft aan welk voorkomen gevalideerd moet worden
     * @param verwerkingssoort de verwachte waarde van de verwerkingssoort
     */
    void assertBerichtVerwerkingssoortCorrect(TypeBericht typeBericht, String voorkomen, int voorkomenIndex, Verwerkingssoort verwerkingssoort);
}
