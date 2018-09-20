/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app;

import org.apache.commons.chain.Context;

/**
 * Abstracte klasse voor een applicatie.
 */
public interface App {

    /**
     * Voer de applicatie uit.
     *
     * @param context gebruikt door de applicatie als informatiebron
     */
    void run(Context context);
}
