/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.schema;

import nl.bzk.brp.ecore.bmr.BedrijfsRegel;


public class UniekConstraint extends AbstractTabelConstraint {

    private static final String SQL_KEYWORDS = "UNIQUE";

    public UniekConstraint(final BedrijfsRegel bedrijfsRegel) {
        super(bedrijfsRegel);
    }

    @Override
    public String getKeywords() {
        return SQL_KEYWORDS;
    }
}
