/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Levert de noodzakelijk classes om een BRP-Model inhoud te maken voor autorisaties.
 * <p>
 * Een BRP groep bestaat mogelijk uit:
 * <ul>
 * <li>BrpGroepInhoud</li>
 * <li>BrpHistorie</li>
 * <li>BrpActie</li>
 * <li>Herkomst</li>
 * </ul>
 * 
 * De {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud inhoud} bestaat uit
 * {@link nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAttribuut brp-attributen}
 * die hier zijn gedefinieerd.
 * 
 * De BrpHistorie en BrpActie zijn onderdeel van het BRP model en zijn in het bovenliggende package gedefinieerd.
 * 
 * De Herkomst is toegevoegd om tracing mogelijk te maken tussen LO3 gegevens en de daaruit afgeleide BRP gegevens.
 * Deze tracing is bedoeld
 * om vast te leggen hoe BRP gegevens tot stand zijn gekomen naar conversie vanuit het LO3 domein.
 *
 * @since 1.0
 * @see nl.bzk.migratiebrp.conversie.model.brp.autorisatie
 */
package nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie;

