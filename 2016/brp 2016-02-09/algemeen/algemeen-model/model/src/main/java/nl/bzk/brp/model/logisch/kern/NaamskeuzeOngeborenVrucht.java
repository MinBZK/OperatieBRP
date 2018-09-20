/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;


/**
 * De relatie tussen twee ouders-in-spe waarmee zij aangeven welke naamskeuze zij wensen voor het kind/de kinderen waarvan ��n van beide zwanger is.
 * <p/>
 * Reeds voor de geboorte kunnen de toekomstig ouders afspraken maken welke geslachtsnaam het kind/de kinderen gaan krijgen. De ouder wiens geslachtnaam
 * zal overgaan op het kind, heeft hier de rol Naamgever; de andere ouder - die hiermee instemt - heeft in deze relatie dan de betrokkenheid in de rol van
 * instemmer.
 */
public interface NaamskeuzeOngeborenVrucht extends NaamskeuzeOngeborenVruchtBasis {

}
