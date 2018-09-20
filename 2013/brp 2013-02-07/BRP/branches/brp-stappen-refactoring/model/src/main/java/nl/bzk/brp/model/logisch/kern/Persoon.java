/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.kern.basis.PersoonBasis;


/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 *
 * Buiten de BRP wordt ook wel de term 'Natuurlijk persoon' gebruikt.
 * In de BRP worden zowel personen ingeschreven die onder een College van Burgemeester en Wethouders vallen
 * ('ingezetenen'), als personen waarvoor de Minister verantwoordelijkheid geldt.
 *
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor dit objecttype de naam
 * "Natuurlijk persoon" te gebruiken. Binnen de context van BRP hebben we het bij het hanteren van de term Persoon
 * echter nooit over niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de term Persoon is verder dermate
 * gebruikelijk binnen de context van BRP, dat ervoor gekozen is deze naam te blijven hanteren. We spreken dus over
 * Persoon en niet over "Natuurlijk persoon".
 * 2. Voor "alternatieve realiteit" personen, en voor niet-ingeschrevenen, gebruiken we in het logisch & operationeel
 * model (maar dus NIET in de gegevensset) het construct 'persoon'. Om die reden zijn veel groepen niet verplicht, die
 * wellicht wel verplicht zouden zijn in geval van (alleen) ingeschrevenen.
 * RvdP 27 juni 2011
 *
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.1.8.
 * Gegenereerd op: Tue Nov 27 12:05:00 CET 2012.
 */
public interface Persoon extends PersoonBasis, RootObject {

}
