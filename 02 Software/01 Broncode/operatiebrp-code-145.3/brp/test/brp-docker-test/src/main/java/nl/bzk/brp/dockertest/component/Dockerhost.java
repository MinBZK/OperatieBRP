/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

/**
 */
final class Dockerhost {

    private final String hostname;
    private final int maxParallel;

    private int currentParallel;

    public Dockerhost(final String hostname) {
        this(hostname, -1);
    }

    Dockerhost(final String hostname, final int maxParallel) {
        this.hostname = hostname;
        this.maxParallel = maxParallel;
    }

    public String getHostname() {
        return hostname;
    }

    public int getMaxParallel() {
        return maxParallel;
    }

    public int getCurrentParallel() {
        return currentParallel;
    }

    public void claim() {
        currentParallel++;
    }

    public void release() {
        currentParallel--;
    }
}
