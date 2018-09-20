/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils.bijhouding;


public class HisPersoonBijhoudingsgemeente extends HisPersoonRecord {

    private Integer bijhoudingsgemeente;
    private Integer datumInschrijvingInGemeente;
    private String indicatieOnverwerktDocumentAanwezig;

    public Integer getBijhoudingsgemeente() {
        return bijhoudingsgemeente;
    }
    public void setBijhoudingsgemeente(final Integer bijhoudingsgemeente) {
        this.bijhoudingsgemeente = bijhoudingsgemeente;
    }
    public Integer getDatumInschrijvingInGemeente() {
        return datumInschrijvingInGemeente;
    }
    public void setDatumInschrijvingInGemeente(final Integer datumInschrijvingInGemeente) {
        this.datumInschrijvingInGemeente = datumInschrijvingInGemeente;
    }
    public String getIndicatieOnverwerktDocumentAanwezig() {
        return indicatieOnverwerktDocumentAanwezig;
    }
    public void setIndicatieOnverwerktDocumentAanwezig(
            final String indicatieOnverwerktDocumentAanwezig) {
        this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;
    }

}
