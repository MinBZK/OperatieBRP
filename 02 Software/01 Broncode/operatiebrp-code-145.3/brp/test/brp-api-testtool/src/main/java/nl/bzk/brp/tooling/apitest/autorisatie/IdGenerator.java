/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.autorisatie;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Id generator om ervoor te zorgen dat de ids consistent blijven.
 */
final class IdGenerator {

    private final AtomicInteger toegangleveringsautorisatieId = new AtomicInteger();
    private final AtomicInteger leveringsautorisatieId        = new AtomicInteger();
    private final AtomicInteger dienstbundelId                = new AtomicInteger();
    private final AtomicInteger dienstbundelGroepId           = new AtomicInteger();
    private final AtomicInteger dienstbundelGroepAttrId       = new AtomicInteger();
    private final AtomicInteger dienstId                      = new AtomicInteger();

    AtomicInteger getToegangleveringsautorisatieId() {
        return toegangleveringsautorisatieId;
    }

    AtomicInteger getLeveringsautorisatieId() {
        return leveringsautorisatieId;
    }

    AtomicInteger getDienstbundelId() {
        return dienstbundelId;
    }

    AtomicInteger getDienstbundelGroepId() {
        return dienstbundelGroepId;
    }

    AtomicInteger getDienstbundelGroepAttrId() {
        return dienstbundelGroepAttrId;
    }

    AtomicInteger getDienstId() {
        return dienstId;
    }
}
