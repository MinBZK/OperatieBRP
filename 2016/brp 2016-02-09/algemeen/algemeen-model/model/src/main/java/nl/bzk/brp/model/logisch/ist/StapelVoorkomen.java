/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ist;


/**
 * Een voorkomen binnen een stapel.
 * <p/>
 * Een stapel bestaat uit ��n of meer "categorie�n", zijnde de actuele categorie, en de daaraan voorafgaande/nu historisch geworden waarden. Een "stapel
 * voorkomen" kan worden beschouwd als een "rij" binnen de stapel.
 * <p/>
 * De IST (InterStelselTabellen) zijn noodzakelijkerwijs een 'brug' tussen enerzijds de modellering binnen de BRP, en anderzijds de modellering binnen de
 * GBA c.q. de LO3.x wereld. Hierbij is voor de STRUCTUUR vooral gekeken naar de LO3.x wereld: deze kent stapels van categorie�n, en 'voorkomens'
 * daarbinnen (in terminologie van LO3.x zijn dit overigens categorie�n). Voor individuele gegevenselementen is - daar waar mogelijk - juist gekozen voor
 * de BRP modellering. De reden hiervoor is dat de IST gegevens ook jaren n� uitfasering van de huidige GBA gebruikt zullen worden (c.q.: geraadpleegd
 * zullen worden); het is dan fijn om aan te sluiten bij de overige BRP gegevens qua definitie en toelichting. Een voorbeeld waarbij dit speelt: LO3.x kent
 * een enkel attribuut 72.10 "omschrijving van de aangifte adreshouding", waar de BRP er tw�� kent: een "reden wijziging" (van een adres) en een "aangever
 * adreshouding". De vertaling tussen het ene LO3.x attribuut en de twee BRP attributen is ��nduidig; het voordeel van een modellering conform BRP formaat
 * is dat de raadplegende ambtenaar van de toekomst alleen nog de BRP attributen hoeft te kennen, en niet (opnieuw) hoeft te worden ingewijd in de
 * terminologie van LO3.x. Op een paar plaatsen gaat dit niet, omdat de LO3.x attributen geen directe BRP tegenhanger hebben. In deze situatie is gekozen
 * voor een herkenbare naamgeving (bijv. "Rubriek 8220 Datum Document") opdat deze attributen duidelijk herkenbaar zijn.
 */
public interface StapelVoorkomen extends StapelVoorkomenBasis {

}
