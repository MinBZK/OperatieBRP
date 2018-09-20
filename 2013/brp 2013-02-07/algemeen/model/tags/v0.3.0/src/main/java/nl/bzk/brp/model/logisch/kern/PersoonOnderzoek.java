/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import nl.bzk.brp.model.logisch.kern.basis.PersoonOnderzoekBasis;


/**
 * De constatering dat een Onderzoek een Persoon raakt.
 *
 * Als er gegevens van een Persoon in Onderzoek staan, dan wordt (redundant) vastgelegd dat de Persoon onderwerp is van
 * een Onderzoek. Er wordt een koppeling tussen een Persoon en een Onderzoek gelegd indien er een gegeven in onderzoek
 * is dat behoort tot het objecttype Persoon, of tot de naar de Persoon verwijzende objecttypen. Een speciaal geval is
 * de Relatie: is de Relatie zelf in onderzoek, dan zijn alle Personen die betrokken zijn in die Relatie ook in
 * onderzoek.
 *
 * Het objecttype 'Persoon/Onderzoek' had ook de naam "Persoon in Onderzoek" kunnen heten. We sluiten echter aan bij de
 * naamgeving van andere koppeltabellen.
 * RvdP 30 maart 2012
 *
 * De exemplaren van Persoon/Onderzoek zijn volledig afleidbaar uit de exemplaren van Gegevens-in-onderzoek. We leggen
 * dit gegeven echter redundant vast om snel de vraag te kunnen beantwoorden of 'de gegevens over de Persoon' in
 * onderzoek zijn.
 * RvdP 30 maart 2012
 *
 *
 *
 */
public interface PersoonOnderzoek extends PersoonOnderzoekBasis {

}
