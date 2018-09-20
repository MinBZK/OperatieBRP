/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils.bijhouding;


public class HisPersoonBijhouding extends HisPersoonRecord {

    private Integer bijhoudingsaard;
    private Integer bijhoudingspartij;
    private String indicatieOnverwerktDocumentAanwezig;
    private Integer nadereBijhoudingsaard;

    public Integer getBijhoudingsaard() {
        return bijhoudingsaard;
    }
    public void setBijhoudingsaard(final Integer bijhoudingsaard) {
        this.bijhoudingsaard = bijhoudingsaard;
    }
    public Integer getBijhoudingspartij() {
        return bijhoudingspartij;
    }
    public void setBijhoudingspartij(final Integer bijhoudingspartij) {
        this.bijhoudingspartij = bijhoudingspartij;
    }
    public String getIndicatieOnverwerktDocumentAanwezig() {
        return indicatieOnverwerktDocumentAanwezig;
    }
    public void setIndicatieOnverwerktDocumentAanwezig(
            final String indicatieOnverwerktDocumentAanwezig) {
        this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;
    }
    public Integer getNadereBijhoudingsaard() {
        return nadereBijhoudingsaard;
    }
    public void setNadereBijhoudingsaard(final Integer nadereBijhoudingsaard) {
        this.nadereBijhoudingsaard = nadereBijhoudingsaard;
    }

}
