/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.bedrijfsregels;

import java.util.List;

import nl.bzk.brp.pocmotor.model.RootObject;

/**
 * Standaard interface voor all bedrijfsregels.
 */
public interface BedrijfsRegel<T extends RootObject> {

    /**
     * Controleert de bedrijfsregel en retourneert een mogelijk gevonden fout. Indien de nieuwe situatie (met de huidige
     * situatie in ogenschouw nemend) voldoet aan de bedrijfsregel zal er dus geen fout worden geretourneerd en zal
     * er dus <code>null</code> terug worden gegeven. Indien de bedrijfsregel wel problemen detecteert, zal hiervoor
     * een {@link BedrijfsRegelFout} worden aangemaakt en zal deze met een bepaalde {@link BedrijfsRegelFoutErnst}
     * worden geretourneerd.
     * 
     * @param nieuweSituatie een {@link RootObject} die de nieuwe situatie, de situatie uit het bijhoudingsbericht,
     *     bevat.
     * @param huidigeSituatie een lijst van {@link RootObject} instanties die de huidige stand van zaken representeert
     *     van de aan de nieuwe situatie gerelateerde objecten.
     * @return een door de bedrijfsregel geconstateerd probleem of <code>null</code> indien de bedrijfsregel geen fout
     *     detecteert.
     */
    BedrijfsRegelFout executeer(T nieuweSituatie, List<RootObject> huidigeSituatie);

}
