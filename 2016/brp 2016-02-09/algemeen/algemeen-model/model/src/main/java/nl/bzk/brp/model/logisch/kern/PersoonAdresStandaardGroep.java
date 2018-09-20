/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;


/**
 * Voor de modellering van buitenlands adres waren enkele opties: - Adres in een attribuut met 'regelovergang' tekens Nadeel: Regelovergangtekens zijn niet
 * platformonafhankelijk en het maximale aantal regels is niet goed af te dwingen. - Adres uitsplitsen volgens een of andere norm (wordt naar gezocht) RNI
 * heeft een actie gestart om te kijken of binnen Europa een werkbare standaard te vinden is. Wereldwijd gaat niet lukken. (Voorlopig) nog geen optie. -
 * Adres per regel opnemen. - Adresregels in een aparte tabel. Is ook mogelijk mits aantal regels beperkt wordt. Uiteindelijk is gekozen voor opname per
 * regel. Dat lijkt minder flexibel dan een vrij veld waarin meerdere regels geplaatst kunnen worden. Het geeft de afnemer echter wel duidelijkheid over
 * het maximale aantal regels en het maximale aantal karakters per regel dat deze kan verwachten. Het aantal zes is afkomstig uit onderzoek door de RNI
 * inzake de maximale grootte van internationale adressen. RvdP 5 september 2011, verplaatst naar nieuwe groep standaard op 13 jan 2012.
 */
public interface PersoonAdresStandaardGroep extends PersoonAdresStandaardGroepBasis {

}
