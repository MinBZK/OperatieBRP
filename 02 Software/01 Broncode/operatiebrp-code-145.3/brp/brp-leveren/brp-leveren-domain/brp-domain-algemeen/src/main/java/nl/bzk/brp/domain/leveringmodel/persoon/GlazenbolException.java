/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

/**
 * Exceptie die aangeeft dat de BRP geen glazenbol heeft, d.w.z. geen beeld naar de toekomst kan geven.
 */
final class GlazenbolException extends IllegalArgumentException {

    private static final long serialVersionUID = 6536331737580637074L;

    /**
     * Contrueert een nieuwe GlazenbolException met de meegegeven beschrijving en oorzaak.
     *
     * @param message Een beschrijving van de situatie.
     */
    GlazenbolException(final String message) {
        super(message);
    }

}
