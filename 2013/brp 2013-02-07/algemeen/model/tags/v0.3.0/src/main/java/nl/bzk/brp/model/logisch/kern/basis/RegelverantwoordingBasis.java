/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * De verantwoording van het onderdrukken van een regel.
 *
 * De BRP signaleert indien een bijhouding(svoorstel) ��n of meer regels raakt. Elke regel waarvan wordt gesignaleerd
 * dat deze wordt geraakt wordt teruggekoppeld, waarna de ambtenaar in voorkomende gevallen toch vastlegging kan
 * afdwingen. De gevallen waarbij een regel wordt onderdrukt, worden expliciet vastgelegd.
 *
 *
 * In de praktijk is het niet de regel die is 'afgegaan', maar de 'regel zoals die is ge�mplementeerd' die afgaat. De
 * link naar 'regelimplementatie' (c.q. Regel/bericht zoals deze is gaan heten) is echter niet relevant: in de praktijk
 * zal er vooral interesse zijn in de vraag 'Welke regel is afgegaan'. We leggen daarom de link naar de regel vast, en
 * niet naar regel/bericht. Mocht in een bepaalde situatie er behoefte zijn aan informatie over welke implementatie van
 * de regel het betrof, dan kan deze informatie worden achterhaald: het is immers achterhaalbaar welk soort bericht
 * heeft geleid tot de actie, en dus welke implementatie van de regel het betrof.
 * RvdP 16 april 2012.
 *
 *
 *
 *
 */
public interface RegelverantwoordingBasis extends ObjectType {

    /**
     * Retourneert Actie van Regelverantwoording.
     *
     * @return Actie.
     */
    Actie getActie();

    /**
     * Retourneert Regel van Regelverantwoording.
     *
     * @return Regel.
     */
    Regel getRegel();

}
