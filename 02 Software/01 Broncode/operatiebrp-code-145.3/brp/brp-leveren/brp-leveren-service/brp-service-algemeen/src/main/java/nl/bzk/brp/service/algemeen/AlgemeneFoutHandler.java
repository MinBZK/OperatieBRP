/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import java.util.concurrent.Callable;

/**
 * Util klasse voor het uitvoeren van code waarbij een *catchall* nodig is. Dit willen we niet overal in de code.
 */
public final class AlgemeneFoutHandler {

    private final OnException e;

    private AlgemeneFoutHandler(final OnException e) {
        this.e = e;
    }

    /**
     * Voert de code uit.
     * @param c de uit te voeren callable
     * @param <V> type resultaat
     * @return het resultaat object
     */
    public <V> V voerUit(final Callable<V> c) {
        try {
            return c.call();
        } catch (Exception exception) {
            this.e.handle(exception);
        }
        return null;
    }

    /**
     * Maakt een handler.
     * @param handler handler om de fout af te handelen.
     * @return een nieuwe AlgemeneFoutHandler
     */
    public static AlgemeneFoutHandler doeBijFout(final OnException handler) {
        return new AlgemeneFoutHandler(handler);
    }

    /**
     * Handelt de exceptie af.
     */
    @FunctionalInterface
    public interface OnException {

        /**
         * Methode om de exceptie af te handelen.
         * @param e de exceptie
         */
        void handle(Exception e);
    }


}
