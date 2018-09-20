/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Levert de classes die de inhoudelijke conversie van de attributen van LO3 naar BRP uitvoerd.
 * <p>
 * <ol>
 * <li>CAP001: Conversiepatroon plaatsaanduiding</li>
 * <li>CGR01: Identificatienummers</li>
 * <li>CEL0210: Conversie voornamen</li>
 * <li>CEL0220: Conversie adellijke titel/predikaat -> onderdeel van CEL0240</li>
 * <li>CEL0230: Conversie voorvoegsel -> onderdeel van CEL0240</li>
 * <li>CEL0240: Conversie geslachtsnaam</li>
 * <li>CEL0410: Conversie geslachtsaanduiding</li>
 * <li>CGR03: Conversie geboorte</li>
 * <li>CGR08: Conversie overlijden</li>
 * <li>CCA11: Conversie gezagsverhoudingen</li>
 * <li>CCA12: Conversie reisdocumenten</li>
 * <li>CCA10: Conversie verblijfsrecht</li>
 * <li>CGR67: Conversie opschorting</li>
 * <li>CCA08: Conversie verblijfsplaats</li>
 * <li>CCA07: Conversie inschrijving</li>
 * <li>CCA04: Conversie nationaliteit</li>
 * </ol>
 *
 * @since 1.0
 * @see nl.bzk.migratiebrp.conversie.model
 */
package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

