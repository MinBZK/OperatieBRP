/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.model.RootObject;

/**
 * Generiek interface voor classes die specifieke attribuutwaarden uit een (root)object ophalen. Voor elk attribuut (in
 * Attributes) moet een AttributeGetter bestaan.
 */
public interface AttributeGetter {

    /**
     * Haalt de waarde van een attribuut op uit het (root)object.
     *
     * @param rootObject Object waaruit de waarde opgehaald moet worden.
     * @return De waarde van het attribuut of NULL, indien het attribuut niet bestaat.
     */
    Expressie getAttribuutWaarde(RootObject rootObject);

    /**
     * Haalt de waarde van een attribuut op uit het geïndiceerd (root)object (bijvoorbeeld adressen).
     *
     * @param rootObject Object waaruit de waarde opgehaald moet worden.
     * @param index      Index van het object.
     * @return De waarde van het attribuut of NULL, indien het attribuut niet bestaat.
     */
    Expressie getAttribuutWaarde(RootObject rootObject, Integer index);


    /**
     * Geeft de hoogste indexwaarde voor uit een bepaald geïndiceerd attribuut van een (root)object.
     * Dit is gelijk aan het aantal elementen in de verzameling.
     *
     * @param rootObject Object waarvan de hoogste indexwaarde bepaald moet worden.
     * @return Maximale index.
     */
    Integer getMaxIndex(RootObject rootObject);
}
