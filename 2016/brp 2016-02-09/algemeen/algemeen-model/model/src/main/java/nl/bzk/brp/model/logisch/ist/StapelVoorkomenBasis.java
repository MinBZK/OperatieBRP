/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ist;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Een voorkomen binnen een stapel.
 *
 * Een stapel bestaat uit één of meer "categorieën", zijnde de actuele categorie, en de daaraan voorafgaande/nu
 * historisch geworden waarden. Een "stapel voorkomen" kan worden beschouwd als een "rij" binnen de stapel.
 *
 * De IST (InterStelselTabellen) zijn noodzakelijkerwijs een 'brug' tussen enerzijds de modellering binnen de BRP, en
 * anderzijds de modellering binnen de GBA c.q. de LO3.x wereld. Hierbij is voor de STRUCTUUR vooral gekeken naar de
 * LO3.x wereld: deze kent stapels van categorieën, en 'voorkomens' daarbinnen (in terminologie van LO3.x zijn dit
 * overigens categorieën). Voor individuele gegevenselementen is - daar waar mogelijk - juist gekozen voor de BRP
 * modellering. De reden hiervoor is dat de IST gegevens ook jaren ná uitfasering van de huidige GBA gebruikt zullen
 * worden (c.q.: geraadpleegd zullen worden); het is dan fijn om aan te sluiten bij de overige BRP gegevens qua
 * definitie en toelichting. Een voorbeeld waarbij dit speelt: LO3.x kent een enkel attribuut 72.10
 * "omschrijving van de aangifte adreshouding", waar de BRP er twéé kent: een "reden wijziging" (van een adres) en een
 * "aangever adreshouding". De vertaling tussen het ene LO3.x attribuut en de twee BRP attributen is éénduidig; het
 * voordeel van een modellering conform BRP formaat is dat de raadplegende ambtenaar van de toekomst alleen nog de BRP
 * attributen hoeft te kennen, en niet (opnieuw) hoeft te worden ingewijd in de terminologie van LO3.x. Op een paar
 * plaatsen gaat dit niet, omdat de LO3.x attributen geen directe BRP tegenhanger hebben. In deze situatie is gekozen
 * voor een herkenbare naamgeving (bijv. "Rubriek 8220 Datum Document") opdat deze attributen duidelijk herkenbaar zijn.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface StapelVoorkomenBasis extends BrpObject {

    /**
     * Retourneert Stapel van Stapel voorkomen.
     *
     * @return Stapel.
     */
    Stapel getStapel();

    /**
     * Retourneert Volgnummer van Stapel voorkomen.
     *
     * @return Volgnummer.
     */
    VolgnummerAttribuut getVolgnummer();

    /**
     * Retourneert Standaard van Stapel voorkomen.
     *
     * @return Standaard.
     */
    StapelVoorkomenStandaardGroep getStandaard();

    /**
     * Retourneert Categorie ouders van Stapel voorkomen.
     *
     * @return Categorie ouders.
     */
    StapelVoorkomenCategorieOudersGroep getCategorieOuders();

    /**
     * Retourneert Categorie gerelateerden van Stapel voorkomen.
     *
     * @return Categorie gerelateerden.
     */
    StapelVoorkomenCategorieGerelateerdenGroep getCategorieGerelateerden();

    /**
     * Retourneert Categorie Huwelijk/Geregistreerd partnerschap van Stapel voorkomen.
     *
     * @return Categorie Huwelijk/Geregistreerd partnerschap.
     */
    StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroep getCategorieHuwelijkGeregistreerdPartnerschap();

    /**
     * Retourneert Categorie gezagsverhouding van Stapel voorkomen.
     *
     * @return Categorie gezagsverhouding.
     */
    StapelVoorkomenCategorieGezagsverhoudingGroep getCategorieGezagsverhouding();

}
