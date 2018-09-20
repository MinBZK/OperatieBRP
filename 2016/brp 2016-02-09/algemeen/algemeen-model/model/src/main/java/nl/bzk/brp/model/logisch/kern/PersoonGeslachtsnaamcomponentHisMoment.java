/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;

/**
 * Component van de geslachtsnaam van een persoon
 *
 * De geslachtsnaam van een persoon kan uit meerdere delen bestaan, bijvoorbeeld ten gevolge van een namenreeks. Ook kan
 * er sprake zijn van het voorkomen van meerdere geslachten, die in de geslachtsnaam terugkomen. In dat geval valt de
 * geslachtsnaam uiteen in meerdere geslachtsnaamcomponenten. Een geslachtsnaamcomponent bestaat vervolgens
 * mogelijkerwijs uit meerdere onderdelen, waaronder voorvoegsel en naamdeel. De structuur van de gegevens in de
 * centrale voorzieningen en in de berichtuitwisseling met de centrale voorzieningen is voorbereid op het nauwkeuriger
 * vastleggen van naamgegevens. Dit is bijvoorbeeld te zien aan het feit dat de berichtopbouw van berichten met
 * naamgegevens ruimte geeft voor meerdere geslachtsnaamcomponenten en het feit dat de groep geslachtsnaamcomponent een
 * gegeven volgnummer kent.
 *
 * Indien in de toekomst, bijvoorbeeld als gevolg van liberalisering van het naamrecht, het zinvol wordt om
 * afzonderlijke geslachtsnaamcomponenten te onderkennen, dan kunnen ook die afzonderlijk worden vastgelegd. Daarbij
 * kunnen voorvoegsels, scheidingstekens en adellijke titels gekoppeld worden aan de specifieke geslachtsnaamcomponent
 * waarop deze van toepassing zijn. Predikaten kunnen worden opgenomen als onderdeel van de gegevens over de specifieke
 * geslachtsnaamcomponent die het gebruik van het predikaat rechtvaardigt.
 *
 * Vooralsnog kunnen deze mogelijkheden niet worden gebruikt. De GBA kent een beperkte vastlegging van naamgegevens.
 * Zolang de BRP en de GBA naast elkaar bestaan – en/of zolang akten nauwkeuriger registratie nog niet ondersteunen –
 * controleren de centrale voorzieningen dat het gebruik van de mogelijkheden van de BRP beperkt blijft tot een enkel
 * voorkomen van geslachtsnaamcomponent.
 *
 * 1. Vooruitlopend op liberalisering namenwet, waarbij het waarschijnlijk mogelijk wordt om de (volledige)
 * geslachtsnaam van een kind te vormen door delen van de geslachtsnaam van beide ouders samen te voegen, is het alvast
 * mogelijk gemaakt om deze delen apart te 'kennen'. Deze beslissing is genomen na raadpleging van ministerie van
 * Justitie, in de persoon van Jet Lenters. 2. De verplichting van de groep 'standaard' is niet geheel zuiver: in een
 * toekomstige situatie, waarin we meerdere geslachtsnaamcomponenten gaan krijgen, is het theoretisch denkbaar dat een
 * tweede, derde of hogere geslachtsnaamcomponent "geen actuele waarde" kent, c.q. alleen voor 'vervallen' records
 * staat. In deze zin zou de groep standaard dus optioneel moeten zijn: er kunnen gaten komen. Alleen komen deze alleen
 * voor ná dat we splitsingen in twee of meer componenten gaan krijgen; in de tussentijd dus niet. De modellering als
 * verplicht is dus nu juist, in de toekomst niet. Besloten is echter deze voorlopig zo te laten, en pas bij de
 * liberalisering de optionaliteit aan te passen, en ook dan pas na te denken over de bedrijfsregel
 * "elke geslachtsnaamcomponent met volgnummer 1 is verplicht, met volgnummer hoger dan 1 is deze WEL optioneel".
 *
 * Voorbeeld: Geslachtsnaam is samengesteld door: Voorvoegsel Scheidingsteken Geslachtsnaamstam van der <spatie> Meer
 * van <spatie> Jansen de Groot d ’ Ancona
 *
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisMomentGenerator")
public interface PersoonGeslachtsnaamcomponentHisMoment extends PersoonGeslachtsnaamcomponent {

    /**
     * Retourneert Standaard van Persoon \ Geslachtsnaamcomponent.
     *
     * @return Standaard.
     */
    HisPersoonGeslachtsnaamcomponentStandaardGroep getStandaard();

}
