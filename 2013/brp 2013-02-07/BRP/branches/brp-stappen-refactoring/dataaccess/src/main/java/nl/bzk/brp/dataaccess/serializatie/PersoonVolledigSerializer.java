/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.serializatie;

import java.io.IOException;

import nl.bzk.brp.model.operationeel.kern.PersoonVolledig;

/**
 * Serializer interface voor het serializeren van persoonVolledig.
 */
public interface PersoonVolledigSerializer {
    /**
     * Converteer een Java instantie naar een binair formaat.
     *
     * @param object de instantie om te serializeren
     * @return de binaire vorm van de instantie
     * @throws IOException als er iets niet goed gaat met converteren naar <code>byte[]</code>
     */
    byte[] serializeer(PersoonVolledig object) throws IOException;

    /**
     * Lees een binaire representatie en maak er een Java instantie van.
     *
     * @param bytes de binaire representatie
     * @return Java instantie
     * @throws IOException als er iets niet goed gaat met lezen van <code>byte[]</code>,
     *  converteren van JSON naar object
     */
    PersoonVolledig deserializeer(byte[] bytes) throws IOException;
}
