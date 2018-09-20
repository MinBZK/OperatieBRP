/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.autaut;

/**
 * Bijhoudingsvoorstellen waarvan de Partij niet wil dat deze automatisch gefiatteerd worden.
 *
 * Fiatteringsuitzonderingen mogen alleen gedefinieerd zijn als de Partij (in principe) automatisch fiatteert
 * (A:"Partij.Automatisch fiatteren?" = Ja). Alle bijhoudingen van andere bijhouders die voldoen aan een criterium in
 * dit Objecttype zijn dan uitzonderingen op het automatisch fiatteren en zal deze Partij handmatig fiatteren.
 *
 *
 *
 */
public interface PartijFiatteringsuitzondering extends PartijFiatteringsuitzonderingBasis {

}
