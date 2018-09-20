/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;

/**
 * De melding van twijfel bij de juistheid van in de BRP geregistreerde gegevens.
 *
 * Een ontvanger van BRP-gegevens die ‘gerede twijfel’ heeft over de juistheid van een gegeven, is gerechtigd om dat
 * gegeven niet te gebruiken, maar moet de bronhouder diens twijfel melden. Dat laatste is de zogenaamde terugmelding.
 *
 * 1. Een definitie van terugmelding is niet snel gevonden. Een redelijke beschrijving staat in 'Handreiking Gerede
 * Twijfel' van 25 mei 2012(http://www.bprbzk.nl/dsresource?objectid=39545&type=org), waarin de volgende zinsnede staat:
 * " Een ontvanger van GBA-gegevens die ‘gerede twijfel’ heeft over de juistheid van een gegeven, is gerechtigd om dat
 * gegeven niet te gebruiken, maar moet de bronhouder diens twijfel melden. Dat laatste is de zogenaamde terugmelding."
 * In de definitie is aangesloten bij de lossere 'diens twijfel' uit de ene laatste zin, in plaats van de strictere
 * 'gerede twijfel' die eerder in de alinea voorkomt. Dit ook, omdat een terugmelding kan plaatsvinden zonder dat er
 * gerede twijfel wás.
 *
 * 2. Keuze bij contactpersoongegevens is hoe de naam uit te modelleren. Enerzijds is "gewoon een string" wel voldoende.
 * Anderzijds scheidt bijv. KING bij e-formulieren dit altijd uit. Uiteindelijk gekozen om deze uit te schrijven zoals
 * samengestelde naam, maar dan de adelijke titel en predicaat weg te laten. NB: deze (of een andere) keuze is altijd
 * arbitrair.
 *
 * Modellering van Persoon binnen dit OT is nog niet 100%. Als de Terugmelding echt gebruikt gaat worden, dan een
 * constructie identiek aan Persoon \ Onderzoek maken waarbij er theoretisch meerdere Personen binnen de Terugmelding
 * kunnen vallen (maar dit mogelijk functioneel beperkt wordt tot 1). Dan houd je de concepten identiek.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisMomentGenerator")
public interface TerugmeldingHisMoment extends Terugmelding {

    /**
     * Retourneert Standaard van Terugmelding.
     *
     * @return Standaard.
     */
    HisTerugmeldingStandaardGroep getStandaard();

    /**
     * Retourneert Contactpersoon van Terugmelding.
     *
     * @return Contactpersoon.
     */
    HisTerugmeldingContactpersoonGroep getContactpersoon();

}
