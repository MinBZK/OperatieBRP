/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.schedular;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * DataBuffer, wanneer de buffer vol is dan gaat de item die er als eerst in was gegaan eruit.
 *
 * @param <T>
 */
public class DataBuffer<T> extends ConcurrentLinkedQueue<T> {

    private static final long serialVersionUID = 3870717303972586466L;

    private final int         bufferGrootte;

    /**
     * Constructor van de buffer.
     *
     * @param bufferGrootte de maximale grootte van de buffer
     */
    public DataBuffer(final int bufferGrootte) {
        this.bufferGrootte = bufferGrootte;
    }

    @Override
    public boolean add(final T data) {
        boolean resultaat = super.add(data);
        if (size() > bufferGrootte) {
            // verwijderd de eerste item zodra de buffer vol is
            poll();
        }

        return resultaat;
    }
}
