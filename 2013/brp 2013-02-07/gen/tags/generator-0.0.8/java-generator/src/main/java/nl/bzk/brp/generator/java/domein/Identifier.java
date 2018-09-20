/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java.domein;

import org.apache.commons.lang3.StringUtils;


public class Identifier {

    private final String identifier;
    private String       javaDoc;

    public Identifier(final String identifier) {
        this.identifier = identifier;
    }

    /**
     * Zet de java doc, onder andere gebruikt voor parameters.
     * 
     * @param documentation de nieuwe javadoc
     */
    public void setJavaDoc(String documentation) {
        this.javaDoc = documentation;
    }

    private String toUpperCamel(final String s) {
        if (!StringUtils.isBlank(s)) {
            boolean isHoofd = false;
            boolean isVorigeHoofd = false;
            for (int i = 0; i < s.length(); i++) {
                isHoofd = s.charAt(i) >= 'A' && s.charAt(i) <= 'Z' || s.charAt(i) >= '0' && s.charAt(i) <= '9';
                if (i == s.length() - 1) {
                    return StringUtils.capitalize(s.toLowerCase());
                }
                if (i > 0 && !isVorigeHoofd && isHoofd) {
                    return StringUtils.capitalize(s.substring(0, i).toLowerCase()) + toUpperCamel(s.substring(i));
                }
                if (i > 1 && isVorigeHoofd && !isHoofd) {
                    return StringUtils.capitalize(s.substring(0, i - 1).toLowerCase())
                            + toUpperCamel(s.substring(i - 1));
                }
                isVorigeHoofd = isHoofd;
            }
        }
        return s;
    }

    public String getLowerCamel() {
        return StringUtils.uncapitalize(getUpperCamel());
    }

    public String getUpperCamel() {
        return toUpperCamel(identifier);
    }

    @Override
    public String toString() {
        return identifier;
    }

    public String getJavaDoc() {
        return StringUtils.isBlank(javaDoc) ? String.format("de %s.", getLowerCamel()) : javaDoc;
    }

}
