/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Enumeratie van alle te implementeren requirements.
 * 
 */
public enum Definities {
    // CHECKSTYLE:OFF - Sommige Strings komen dubbel voor, is niet erg aangezien het constanten zijn

    /**
     * Nederlandse gemeente.
     */
    DEF001("DEF001", "Nederlandse gemeente"),
    /**
     * Plaats/plaatsomschrijving in een bepaald buitenland.
     */
    DEF002("DEF002", "Plaats/plaatsomschrijving in een bepaald buitenland"),
    /**
     * Plaatsomschrijving in een onbekend land of een internationaal gebied.
     */
    DEF003("DEF003", "Plaatsomschrijving in een onbekend land of een internationaal gebied"),
    /**
     * Nederlandse gemeente.
     */
    DEF004("DEF004", "Nederlandse gemeente"),
    /**
     * Plaats in het buitenland.
     */
    DEF005("DEF005", "Plaats in het buitenland"),
    /**
     * Plaatsomschrijving in of buiten Nederland.
     */
    DEF006("DEF006", "Plaatsomschrijving in of buiten Nederland"),
    /**
     * Vastgesteld niet-Nederlander.
     */
    DEF007("DEF007", "Vastgesteld niet-Nederlander"),
    /**
     * Behandeld als Nederlander.
     */
    DEF008("DEF008", "Behandeld als Nederlander"),
    /**
     * Geen bijzonder Nederlanderschap.
     */
    DEF009("DEF009", "Geen bijzonder Nederlanderschap"),
    /**
     * Vastgesteld niet-Nederlander.
     */
    DEF010("DEF010", "Vastgesteld niet-Nederlander"),
    /**
     * Behandeld als Nederlander.
     */
    DEF011("DEF011", "Behandeld als Nederlander"),
    /**
     * Geen bijzonder Nederlanderschap.
     */
    DEF012("DEF012", "Geen bijzonder Nederlanderschap"),

    /**
     * Adellijke titel.
     */
    DEF014("DEF014", "Adellijke titel"),
    /**
     * Predikaat.
     */
    DEF015("DEF015", "Predikaat"),
    /**
     * Geen adellijke titel en geen predikaat.
     */
    DEF016("DEF016", "Geen adellijke titel en geen predikaat"),
    /**
     * Predikaat.
     */
    DEF017("DEF017", "Predikaat"),
    /**
     * Adellijke titel.
     */
    DEF018("DEF018", "Adellijke titel"),
    /**
     * Predikaat en adellijke titel.
     */
    DEF019("DEF019", "Predikaat en adellijke titel"),
    /**
     * Geen predikaat en geen adellijke titel.
     */
    DEF020("DEF020", "Geen predikaat en geen adellijke titel"),
    /**
     * Adres is niet bekend (zogenaamd puntadres).
     */
    DEF021("DEF021", "Adres is niet bekend (zogenaamd puntadres)"),
    /**
     * Adres is bekend.
     */
    DEF022("DEF022", "Adres is bekend"),
    /**
     * Adres is niet bekend (zogenaamd puntadres).
     */
    DEF023("DEF023", "Adres is niet bekend (zogenaamd puntadres)"),
    /**
     * Adres is bekend.
     */
    DEF024("DEF024", "Adres is bekend"),
    /**
     * Persoon verblijft in Nederland.
     */
    DEF025("DEF025", "Persoon verblijft in Nederland"),
    /**
     * Persoon verblijft in buitenland.
     */
    DEF026("DEF026", "Persoon verblijft in buitenland"),
    /**
     * Ouder1 heeft van rechtswege gezag.
     */
    DEF027("DEF027", "Ouder1 heeft van rechtswege gezag"),
    /**
     * Ouder1 heeft gezag aangetekend in het gezagsregister dan wel na uitspraak rechtbank.
     */
    DEF028("DEF028", "Ouder1 heeft gezag aangetekend in het gezagsregister dan wel na uitspraak rechtbank"),
    /**
     * Ouder1 heeft geen gezag.
     */
    DEF029("DEF029", "Ouder1 heeft geen gezag"),
    /**
     * Ouder2 heeft van rechtswege gezag.
     */
    DEF030("DEF030", "Ouder2 heeft van rechtswege gezag"),
    /**
     * Ouder2 heeft gezag aangetekend in het gezagsregister dan wel na uitspraak rechtbank.
     */
    DEF031("DEF031", "Ouder2 heeft gezag aangetekend in het gezagsregister dan wel na uitspraak rechtbank"),
    /**
     * Ouder2 heeft geen gezag.
     */
    DEF032("DEF032", "Ouder2 heeft geen gezag"),
    /**
     * Er is een buitenlandse adres waarvan alleen de plaatsnaam bekend is.
     */
    DEF033("DEF033", "Er is een buitenlandse adres waarvan alleen de plaatsnaam bekend is"),
    /**
     * Er is een buitenlandse adres waarvan meer dan alleen de plaatsnaam bekend is.
     */
    DEF034("DEF034", "Er is een buitenlandse adres waarvan meer dan alleen de plaatsnaam bekend is"),

    /**
     * geen verstrekkingsbeperking.
     */
    DEF035("DEF035", "geen verstrekkingsbeperking"),
    /**
     * niet zonder toestemming aan derden ter uitvoering van een algemeen verbindend voorschrift en niet aan vrije
     * derden en niet aan kerken.
     */
    DEF036("DEF036", "niet zonder toestemming aan derden ter uitvoering van een algemeen "
            + "verbindend voorschrift en niet aan vrije derden en niet aan kerken"),
    /**
     * Slechts een deel van de beperkingen uit DEF036 zijn van toepassing.
     */
    DEF037("DEF037", "Slechts een deel van de beperkingen uit DEF036 zijn van toepassing"),
    /**
     * Geprivilegieerd.
     */
    DEF038("DEF038", "Geprivilegieerd"),
    /**
     * Niet geprivilegieerd.
     */
    DEF039("DEF039", "Niet geprivilegieerd"),
    /**
     * Verantwoordelijkheid ligt bij gemeente.
     */
    DEF040("DEF040", "Verantwoordelijkheid ligt bij gemeente"),
    /**
     * Verantwoordelijkheid ligt bij Minister.
     */
    DEF041("DEF041", "Verantwoordelijkheid ligt bij Minister"),
    /**
     * Er is een brondocument.
     */
    DEF042("DEF042", "Er is een brondocument"),
    /**
     * Er is een akte.
     */
    DEF043("DEF043", "Er is een akte"),
    /**
     * Er is geen brondocument en geen akte.
     */
    DEF044("DEF044", "Er is geen brondocument en geen akte"),
    /**
     * Er is een buitenlandse adres waarvan alleen de plaatsnaam bekend is.
     */
    DEF045("DEF045", "Er is een buitenlandse adres waarvan alleen de plaatsnaam bekend is"),
    /**
     * Er is een buitenlandse adres waarvan meer dan alleen de plaatsnaam bekend is.
     */
    DEF046("DEF046", "Er is een buitenlandse adres waarvan meer dan alleen de plaatsnaam bekend is"),
    /**
     * Er is uitsluitend een brondocument.
     */
    DEF047("DEF047", "Er is uitsluitend een brondocument"),
    /**
     * Er is uitsluitend een akte.
     */
    DEF048("DEF048", "Er is uitsluitend een akte"),
    /**
     * Er is geen brondocument en geen akte.
     */
    DEF049("DEF049", "Er is geen brondocument en geen akte"),
    /**
     * Er zijn meerdere documenten (brondocumenten en/of akten).
     */
    DEF050("DEF050", "Er zijn meerdere documenten (brondocumenten en/of akten)"),
    /**
     * Scheidingsteken is gevuld: spatie.
     */
    DEF051("DEF051", "Scheidingsteken is gevuld: spatie"),
    /**
     * Scheidingsteken is gevuld: geen spatie.
     */
    DEF052("DEF052", "Scheidingsteken is gevuld: geen spatie"),
    /**
     * Geen scheidingsteken.
     */
    DEF053("DEF053", "Geen scheidingsteken"),
    /**
     * Statenloos.
     */
    DEF054("DEF054", "Statenloos"),
    /**
     * Niet statenloos.
     */
    DEF055("DEF055", "Niet statenloos"),
    /**
     * Statenloos.
     */
    DEF056("DEF056", "Statenloos"),
    /**
     * Niet statenloos.
     */
    DEF057("DEF057", "Niet statenloos"),

    /**
     * Persoonslijst is opgeschort.
     */
    DEF058("DEF058", "Persoonslijst is opgeschort"),
    /**
     * Persoon is geëmigreerd.
     */
    DEF059("DEF059", "Persoon is geëmigreerd"),
    /**
     * Directe opname in RNI.
     */
    DEF060("DEF060", "Directe opname in RNI"),
    /**
     * Geprivilegieerd.
     */
    DEF061("DEF061", "Geprivilegieerd"),
    /**
     * Niet geprivilegieerd.
     */
    DEF062("DEF062", "Niet geprivilegieerd"),

    ;
    // CHECKSTYLE:ON

    private String code;
    private String omschrijving;

    private Definities(final String code, final String omschrijving) {
        this.code = code;
        this.omschrijving = omschrijving;
    }

    public String getCode() {
        return code;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("code", code)
                .append("omschrijving", omschrijving).toString();
    }
}
