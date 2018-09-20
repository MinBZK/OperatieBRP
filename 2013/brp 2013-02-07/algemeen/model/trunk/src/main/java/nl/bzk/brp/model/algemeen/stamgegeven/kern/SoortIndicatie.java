/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * Categorisatie van Indicaties.
 *
 *
 * Voor een Persoon kunnen ��n of meer Indicaties van toepassing zijn, bijvoorbeeld de indicatie 'Staatloos', of
 * 'Behandeld als Nederlander'.
 * De Soort indicatie is de typering van deze Indicaties voor een Persoon.
 *
 *
 * 1. Vanuit migratie kan er voor een aantal indicaties geen onderscheid gemaakt worden tussen "Nee" en "afwezig". We
 * kiezen ervoor om in die gevallen de beperking toe te voegen, dat alleen de waarde "JA" is toegestaan. RvdP 6 november
 * 2011.
 * 2. De soort 'buitenlands reisdocument aanwezig' is vervallen wegens volgende reden:
 * "Issue wordt gesloten cf. besluit Issueoverleg 120611 en Directeurenoverleg 120619 (zie ook bijlage):
 * Besloten tot niet meer opnemen �aanduiding bezit buitenlands reisdocument� in de BRP. Het gegeven hoeft ook niet
 * overgenomen te worden uit de GBA.
 * Het gegeven �aanduiding bezit buitenlands reisdocument� wordt dus niet in de BRP
 * opgenomen. In de migratiefase wordt een in de GBA aanwezige aanduiding dus niet naar de BRP overgezet. De
 * consequentie is dat een dergelijke aanduiding op de PL van een burger verloren gaat indien deze in de migratieperiode
 * verhuist van een GBA-gemeente naar een BRP-gemeente en vervolgens weer naar een GBA-gemeente. Deze consequentie wordt
 * door Beleid geaccepteerd."
 * Hierdoor ontstond er een 'gat' in de tuples: na 7 kwam 9, en niet 8.
 * Omdat we nog niet in productie zijn, is ingeschat dat het hernummeren weinig impact heeft. Hierdoor is soort
 * 'indicatie statenloos' hernummerd tot 8.
 * RvdP 4 oktober 2012.
 *
 *
 *
 */
public enum SoortIndicatie {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", null),
    /**
     * Derde heeft gezag?.
     */
    INDICATIE_DERDE_HEEFT_GEZAG("Derde heeft gezag?", true),
    /**
     * Onder curatele?.
     */
    INDICATIE_ONDER_CURATELE("Onder curatele?", true),
    /**
     * Verstrekkingsbeperking?.
     */
    INDICATIE_VERSTREKKINGSBEPERKING("Verstrekkingsbeperking?", false),
    /**
     * Vastgesteld niet Nederlander?.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER("Vastgesteld niet Nederlander?", true),
    /**
     * Behandeld als Nederlander?.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER("Behandeld als Nederlander?", true),
    /**
     * Belemmering verstrekking reisdocument?.
     */
    INDICATIE_BELEMMERING_VERSTREKKING_REISDOCUMENT("Belemmering verstrekking reisdocument?", false),
    /**
     * Staatloos?.
     */
    INDICATIE_STAATLOOS("Staatloos?", true);

    private final String  naam;
    private final Boolean indicatieMaterieleHistorieVanToepassing;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortIndicatie
     * @param indicatieMaterieleHistorieVanToepassing IndicatieMaterieleHistorieVanToepassing voor SoortIndicatie
     */
    private SoortIndicatie(final String naam, final Boolean indicatieMaterieleHistorieVanToepassing) {
        this.naam = naam;
        this.indicatieMaterieleHistorieVanToepassing = indicatieMaterieleHistorieVanToepassing;
    }

    /**
     * Retourneert Naam van Soort indicatie.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Materiele historie van toepassing? van Soort indicatie.
     *
     * @return Materiele historie van toepassing?.
     */
    public Boolean getIndicatieMaterieleHistorieVanToepassing() {
        return indicatieMaterieleHistorieVanToepassing;
    }

}
