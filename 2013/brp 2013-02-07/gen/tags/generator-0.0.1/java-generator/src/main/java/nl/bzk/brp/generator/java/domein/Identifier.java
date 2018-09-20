/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java.domein;

import nl.bzk.brp.generator.java.GeneratorUtils;


public class Identifier {

    private String upperCamel;

    public Identifier(final String upperCamel) {
        if ("ID".equals(upperCamel)) {
            this.upperCamel = "Id";
        } else {
            this.upperCamel = upperCamel;
        }
    }

    @Override
    public String toString() {
        return upperCamel;
    }

    public String getLowerCamel() {
        return GeneratorUtils.lowerTheFirstCharacter(upperCamel);
    }

}
