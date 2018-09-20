/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils;

public class ResultaatRegel {

    private ConverterCase converterCase;
    private String xpath;
    private String waarde;

    public ResultaatRegel(final ConverterCase converterCase, final String xpath, final String waarde) {
        this.converterCase = converterCase;
        this.xpath = xpath;
        this.waarde = waarde;
    }

    public ConverterCase getConverterCase() {
        return converterCase;
    }

    public String getXpath() {
        return xpath;
    }

    public String getWaarde() {
        return waarde;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((converterCase == null) ? 0 : converterCase.hashCode());
        result = prime * result + ((waarde == null) ? 0 : waarde.hashCode());
        result = prime * result + ((xpath == null) ? 0 : xpath.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResultaatRegel other = (ResultaatRegel) obj;
        if (converterCase != other.converterCase)
            return false;
        if (waarde == null) {
            if (other.waarde != null)
                return false;
        } else if (!waarde.equals(other.waarde))
            return false;
        if (xpath == null) {
            if (other.xpath != null)
                return false;
        } else if (!xpath.equals(other.xpath))
            return false;
        return true;
    }

}
