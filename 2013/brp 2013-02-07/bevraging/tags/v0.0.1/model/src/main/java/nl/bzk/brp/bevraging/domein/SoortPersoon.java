/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SoortPersoon {

    INGESCHREVENE("I", "Ingeschrevene"),
    NIET_INGESCHREVENE("N", "Niet ingeschrevene"),
    ALTERNATIEVE_REALITEIT("A", "Alternatieve realiteit");
    private String code;
    private String omschrijving;

    private SoortPersoon(String code, String omschrijving) {
        this.code = code;
        this.omschrijving = omschrijving;
    }

    public String getCode() {
        return code;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
}
