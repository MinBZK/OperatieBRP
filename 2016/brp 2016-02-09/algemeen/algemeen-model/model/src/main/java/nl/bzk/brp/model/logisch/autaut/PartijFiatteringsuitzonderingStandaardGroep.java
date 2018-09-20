/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.autaut;

/**
 * De uitzondering wordt gedefinieerd door middel van een aantal attributen te weten; A:"Partij \
 * Fiatteringsuitzondering.Partij bijhoudingsvoorstel", A:"Partij \ Fiatteringsuitzondering.Soort document" en A:"Partij
 * \ Fiatteringsuitzondering.Soort administratieve handeling". Minimaal één van deze attributen moet gevuld zijn. Als er
 * meerdere attributen gevuld zijn, dan moet het bijhoudinsvoorstel voldoen aan alle criteria.
 *
 * Evaluatie van akten gaan simpel en bewust niet subtiel. We verzamelen alle akten die voorkomen bij een bijhouding en
 * kijken of in die set van akten het soort document (dat is opgegeven als uitzondering) voorkomt. We gaan dus niet per
 * persoon kijken etc.
 *
 *
 *
 */
public interface PartijFiatteringsuitzonderingStandaardGroep extends PartijFiatteringsuitzonderingStandaardGroepBasis {

}
