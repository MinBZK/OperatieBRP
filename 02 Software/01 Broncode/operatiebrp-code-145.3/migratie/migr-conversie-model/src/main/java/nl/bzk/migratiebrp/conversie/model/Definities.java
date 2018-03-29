/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Enumeratie van alle te implementeren requirements.
 */

public enum Definities {
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
     * Niet opgeschort.
     */
    DEF040("DEF040", "Niet opgeschort"),
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
     * Staatloos (was Statenloos).
     */
    DEF054("DEF054", "Staatloos"),
    /**
     * Niet staatloos (was Niet statenloos).
     */
    DEF055("DEF055", "Niet staatloos"),
    /**
     * Staatloos (was Statenloos).
     */
    DEF056("DEF056", "Staatloos"),
    /**
     * Niet staatloos (was Niet statenloos).
     */
    DEF057("DEF057", "Niet staatloos"),

    /**
     * Persoonslijst is opgeschort, anders dan VOW.
     */
    DEF058("DEF058", "Persoonslijst is opgeschort, anders dan VOW"),
    /**
     * Geprivilegieerd.
     */
    DEF061("DEF061", "Geprivilegieerd"),
    /**
     * Niet geprivilegieerd.
     */
    DEF062("DEF062", "Niet geprivilegieerd"),
    /**
     * Nederlands reisdocument.
     */
    DEF063("DEF063", "Nederlands reisdocument"),
    /**
     * Belemmering verstrekking.
     */
    DEF065("DEF065", "Belemmering verstrekking"),
    /**
     * Directe registratie in de RNI.
     */
    DEF066("DEF066", "Directe registratie in de RNI"),
    /**
     * Niet vast te stellen aan de hand van opschorting.
     */
    DEF067("DEF067", "Niet vast te stellen aan de hand van opschorting"),
    /**
     * Reden opschorting bestaat niet in de BRP.
     */
    DEF068("DEF068", "Reden opschorting bestaat niet in de BRP"),
    /**
     * Reden opschorting bestaat ook in de BRP.
     */
    DEF069("DEF069", "Reden opschorting bestaat ook in de BRP"),
    /**
     * Immigratie.
     */
    DEF074("DEF074", "Emigratie naar een buitenlands adres waarvan de plaatsnaam bekend is"),
    /**
     * Immigratie.
     */
    DEF075("DEF075", "Emigratie naar een buitenlands adres waarvan meer dan de plaatsnaam bekend is"),
    /**
     * Immigratie.
     */
    DEF076("DEF076", "Immigratie"),
    /**
     * Opgeschort, met uitzondering van niet ingezetene.
     */
    DEF079("DEF079", "Opgeschort"),
    /**
     * Persoon is Vertrokken onbekend waarheen (VOW).
     */
    DEF080("DEF080", "Persoon is Vertrokken onbekend waarheen (VOW)"),
    /**
     * Actualisering (in RNI) op basis van een deelnemersopgave.
     */
    DEF081("DEF081", "Actualisering (in RNI) op basis van een deelnemersopgave"),
    /**
     * Actualisering niet op basis van een deelnemersopgave.
     */
    DEF082("DEF082", "Actualisering niet op basis van een deelnemersopgave"),
    /**
     * Lopend onderzoek.
     */
    DEF083("DEF083", "Lopend onderzoek"),
    /**
     * Afgesloten onderzoek.
     */
    DEF084("DEF084", "Afgesloten onderzoek"),
    /**
     * Bijhouding gedaan door Overheidsorgaan.
     */
    DEF085("DEF085", "Bijhouding gedaan door Overheidsorgaan"),
    /**
     * Bijhouding niet gedaan door Overheidsorgaan.
     */
    DEF086("DEF086", "Bijhouding niet gedaan door Overheidsorgaan"),
    /**
     * Persoonslijst is actueel (niet opgeschort).
     */
    DEF087("DEF087", "Persoonslijst is actueel (niet opgeschort)"),
    /**
     * Ingeschreven in gemeente.
     */
    DEF092("DEF092", "Ingeschreven in gemeente"),
    /**
     * Ingeschreven in RNI.
     */
    DEF093("DEF093", "Ingeschreven in RNI"),
    /**
     * Onbekend waar ingeschreven.
     */
    DEF094("DEF094", "Onbekend waar ingeschreven");

    private final String code;
    private final String omschrijving;

    /**
     * Constructor.
     * @param code code
     * @param omschrijving Omschrijving
     */
    Definities(final String code, final String omschrijving) {
        this.code = code;
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van code.
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Geef de waarde van omschrijving.
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("code", code).append("omschrijving", omschrijving).toString();
    }
}
