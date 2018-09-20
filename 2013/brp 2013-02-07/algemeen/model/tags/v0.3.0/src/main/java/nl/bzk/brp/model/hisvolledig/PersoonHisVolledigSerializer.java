/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig;

import java.io.IOException;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;

/**
 * Serializer interface voor het serializeren van persoon his volledig.
 */
public interface PersoonHisVolledigSerializer {
    /**
     * Converteer een Java instantie naar een binair formaat.
     *
     * @param object de instantie om te serializeren
     * @return de binaire vorm van de instantie
     * @throws java.io.IOException als er iets niet goed gaat met converteren naar <code>byte[]</code>
     */
    byte[] serializeer(PersoonHisVolledig object) throws IOException;

    /**
     * Lees een binaire representatie en maak er een Java instantie van.
     *
     * @param bytes de binaire representatie
     * @return Java instantie
     * @throws java.io.IOException als er iets niet goed gaat met lezen van <code>byte[]</code>,
     *  converteren van JSON naar object
     */
    PersoonHisVolledig deserializeer(byte[] bytes) throws IOException;
}
