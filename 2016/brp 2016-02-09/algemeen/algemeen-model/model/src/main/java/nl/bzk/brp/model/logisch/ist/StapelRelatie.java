/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ist;


/**
 * Het relateren van een stapel aan een relatie.
 * <p/>
 * Een stapel heeft normaliter betrekking op ��n relatie. Uitzonderingen hierop zijn omzettingen van huwelijk in een geregistreerd partnerschap (die leiden
 * tot twee relaties) en stapels met alleen onjuiste voorkomens (die leiden tot g��n relaties). Omdat ��n stapel altijd behoort tot (de persoonslijst van)
 * ��n persoon, kunnen meerdere stapels behoren tot ��n relatie.
 */
public interface StapelRelatie extends StapelRelatieBasis {

}
