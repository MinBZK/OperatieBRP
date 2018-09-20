/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.schema;

import nl.bzk.brp.ecore.bmr.BedrijfsRegel;
import nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel;


abstract public class AbstractTabelConstraint extends AbstractConstraint implements TabelFeature {

    public static AbstractTabelConstraint createConstraint(final BedrijfsRegel bedrijfsRegel) {
        AbstractTabelConstraint resultaat = null;
        if (bedrijfsRegel.getSoortBedrijfsRegel() == SoortBedrijfsRegel.UC) {
            resultaat = new UniekConstraint(bedrijfsRegel);
        } else if (bedrijfsRegel.getSoortBedrijfsRegel() == SoortBedrijfsRegel.ID) {
            resultaat = new PrimaryKeyConstraint(bedrijfsRegel);
        }
        return resultaat;
    }

    public AbstractTabelConstraint(final BedrijfsRegel bedrijfsRegel) {
        super(bedrijfsRegel);
    }
}
