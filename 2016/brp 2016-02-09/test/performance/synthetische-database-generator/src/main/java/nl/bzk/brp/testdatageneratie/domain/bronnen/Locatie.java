/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.bronnen;

import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.domain.kern.Gem;
import nl.bzk.brp.testdatageneratie.domain.kern.Landgebied;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class Locatie implements java.io.Serializable {

    private static final Logger log                = Logger.getLogger(Locatie.class);

    public static final short   LAND_CODE_NL       = 6030;
    public static final short   LAND_CODE_ONBEKEND = 0;

    private String              plaats;
    private short               landCode;
    private Gem                 gem;

    public boolean isNederland() {
        return LAND_CODE_NL == this.landCode;
    }

    public Landgebied getLandgebied() {
        return MetaRepo.get(Landgebied.class, this.landCode);
    }

    public String getPlaats() {
        return this.plaats;
    }

    public void setPlaats(final String plaats) {
        // Als de lengte van de waarde van plaats gelijk is aan 4, dan is er sprake van een gemeente code.
        if (plaats.length() == 4 && isNederland()) {
            try {
                this.gem = MetaRepo.get(Gem.class, Short.valueOf(plaats));
            } catch (NumberFormatException e) {
                log.warn(e.getClass().getName() + " " + e.getMessage());
            }
        }
        // max veertig tekens voor bijv blplaatsgeboorte
        this.plaats = StringUtils.substring(plaats, 0, 40);
    }

    public short getLandCode() {
        return this.landCode;
    }

    public void setLandCode(final short landCode) {
        this.landCode = landCode;
    }

    public Gem getGemeente() {
        return this.gem;
    }

}
