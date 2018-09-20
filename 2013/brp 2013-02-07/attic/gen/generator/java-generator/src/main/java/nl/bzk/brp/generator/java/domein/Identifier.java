/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java.domein;

import nl.bzk.brp.generator.java.GeneratorUtils;
import org.apache.commons.lang3.StringUtils;


public class Identifier {

    private String identifier;

    public String getJavaDoc() {
        return javaDoc;
    }

    public void setJavaDoc(String javaDoc) {
        this.javaDoc = javaDoc;
    }

    private String javaDoc;

    public Identifier(final String identifier) {
        this.identifier = GeneratorUtils.toUpperCamel(identifier);
    }

    public String getLowerCamel() {
        return StringUtils.uncapitalize(getUpperCamel());
    }

    public String getUpperCamel() {
        return GeneratorUtils.toUpperCamel(identifier);
    }

    @Override
    public String toString() {
        return identifier;
    }

}
