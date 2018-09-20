/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.brm.persistent;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.domein.brm.Regelimplementatiesituatie;
import nl.bzk.brp.domein.brm.persistent.basis.AbstractPersistentRegelimplementatiesituatie;


@Entity
@Table(name = "Regelimplementatiesituatie", schema = "BRM")
public class PersistentRegelimplementatiesituatie extends AbstractPersistentRegelimplementatiesituatie implements
        Regelimplementatiesituatie
{

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSpecifiekerDan(final Regelimplementatiesituatie gedrag) {
        return gedrag == null
            || getSpecifiekheidsIndex() > ((PersistentRegelimplementatiesituatie) gedrag).getSpecifiekheidsIndex();
    }

    /**
     * @return getal dat aangeeft hoe specifiek dit gedrag is (0 betekend totaal niet specifiek)
     */
    private int getSpecifiekheidsIndex() {
        int resultaat = 0;
        if (getBijhoudingsverantwoordelijkheid() != null) {
            resultaat += 1;
        }
        resultaat <<= 1;
        if (getIndicatieOpgeschort() != null) {
            resultaat += 1;
        }
        resultaat <<= 1;
        if (getRedenOpschorting() != null) {
            resultaat += 1;
        }
        return resultaat;
    }
}
