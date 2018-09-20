/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;


/**
 * Groep gegevens over de bijhouding
 * <p/>
 * Verplicht aanwezig bij persoon
 * <p/>
 * 1. In geval van een opgeschorte bijhouding, is 'ingezetene' en 'niet-ingezetene' minder goed gedefinieerd. In het LO BRP wordt dit punt uitgewerkt. 2.
 * Vorm van historie: Beiden. De bijhoudingsverantwoordelijkheid kan verschuiven door bijvoorbeeld een verhuizing die om technische redenen later wordt
 * geregistreerd (maar wellicht op tijd is doorgegeven). De datum ingang geldigheid van de (nieuwe) bijhoudingsverantwoordelijkheid ligt dus vaak v��r de
 * datumtijd registratie. Een formele tijdslijn alleen is dus onvoldoende. Omdat, in geval van verschuivingen van verantwoordelijkheid tussen Minister en
 * College B&W, ook van belang kan zijn wie over een periode in het verleden 'de verantwoordelijke' was (voor het wijzigen van historische gegevens), is de
 * materi�le tijdslijn ook relevant, en dus wordt deze vastgelegd. 3. De naam is aangepast naar bijhoudingsaard, tegen de term
 * bijhoudingsverantwoordelijkheid was teveel weerstand. 7 januari 2013.
 */
public interface PersoonBijhoudingGroep extends PersoonBijhoudingGroepBasis {

}
