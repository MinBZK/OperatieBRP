/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.bronnen;

import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.domain.kern.Land;
import nl.bzk.brp.testdatageneratie.domain.kern.Partij;
import org.apache.log4j.Logger;

public class Locatie implements java.io.Serializable {

    private static final Logger log = Logger.getLogger(Locatie.class);

    public static final short LAND_CODE_NL = 6030;
    public static final short LAND_CODE_ONBEKEND = 0;

    private String plaats;
    private short  landCode;
    private Partij partij;

    public boolean isNederland() {
        return LAND_CODE_NL == landCode;
    }

    public Land getLand() {
        return MetaRepo.get(Land.class, landCode);
    }

    public String getPlaats() {
        return this.plaats;
    }

    public void setPlaats(final String plaats) {
        if (plaats.length() == 4) {

            try {
                partij = MetaRepo.get(Partij.class, Short.valueOf(plaats));

            } catch (NumberFormatException e) {
                log.warn(e.getClass().getName() + " " + e.getMessage());
            }
        }
        this.plaats = plaats;
    }

    public short getLandCode() {
        return this.landCode;
    }

    public void setLandCode(final short landCode) {
        this.landCode = landCode;
    }

    public Partij getPartij() {
        return partij;
    }

}
