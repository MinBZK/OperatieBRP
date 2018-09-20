/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.exceptie;

/**
 * Deze exceptie wordt gegooid als er geen proxy gemaakt kan worden om lazy te laden.
 */
public class ProxyAanmakenMisluktExceptie extends RuntimeException {

    /**
     * De constructor voor deze klasse.
     *
     * @param bericht Het bericht.
     * @param oorzaak De oorzaak.
     */
    public ProxyAanmakenMisluktExceptie(final String bericht, final Throwable oorzaak) {
        super(bericht, oorzaak);
    }

}
