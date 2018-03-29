/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

/**
 * Interface voor het vertalen van GBA rubrieken naar BRP Typen.
 */
@FunctionalInterface
public interface GbaRubriekNaarBrpTypeVertaler {

    /**
     * Vertaald de GBA Rubriek naar een BRP type. Indien het een lijst betreft
     * wordt de lijst expressie teruggegeven
     * @param rubriek de te vertalen GBA rubriek
     * @return het BRP Type
     * @throws GbaRubriekOnbekendExceptie wordt gegeven indien GBA rubriek niet voorkomt
     */
    BrpType[] vertaalGbaRubriekNaarBrpType(String rubriek) throws GbaRubriekOnbekendExceptie;

}
