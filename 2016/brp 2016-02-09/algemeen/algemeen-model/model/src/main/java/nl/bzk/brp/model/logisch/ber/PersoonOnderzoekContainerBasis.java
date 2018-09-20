/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Koppelklasse tussen Persoon en Onderzoek ten behoeve van de berichten.
 *
 * Onderzoek wordt vanuit twee kanten door Persoon geraakt; de onderzoeken waar een persoon een rol in speelt en de
 * personen die een rol spelen in een Persoon. Om de structuur van beide kanten correct te laten genereren is de
 * Ber.Persoon \ Onderzoek container aangemaakt. Deze zorgt voor de koppeling van Personen aan (onder) Onderzoek.
 * Aangezien de Java code een eenduidige view wil hebben, is de Inverse Associatie op Kern.Persoon \ Onderzoek gezet
 * (zowel Persoon als Onderzoek). De XSD Inverse Associatie is gesplitst over de Ber en Kern varianten.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonOnderzoekContainerBasis extends BrpObject {

}
