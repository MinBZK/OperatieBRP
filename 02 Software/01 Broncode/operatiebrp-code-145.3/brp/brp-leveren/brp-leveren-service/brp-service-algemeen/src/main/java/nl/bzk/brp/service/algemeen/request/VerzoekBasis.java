/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.request;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;

/**
 * De implementatie van {@link Verzoek}.
 */
public class VerzoekBasis implements Verzoek {
    private String xmlBericht;
    private OIN oin;
    private SoortDienst soortDienst;
    private Verzoek.Stuurgegevens stuurgegevens;
    private boolean brpKoppelvlakVerzoek;

    @Override
    public final String getXmlBericht() {
        return xmlBericht;
    }

    @Override
    public final void setXmlBericht(final String xmlBericht) {
        this.xmlBericht = xmlBericht;
    }

    public OIN getOin() {
        return oin;
    }

    public void setOin(final OIN oin) {
        this.oin = oin;
    }

    @Override
    public final SoortDienst getSoortDienst() {
        return soortDienst;
    }

    @Override
    public final void setSoortDienst(final SoortDienst soortDienst) {
        this.soortDienst = soortDienst;
    }

    public boolean isBrpKoppelvlakVerzoek() {
        return brpKoppelvlakVerzoek;
    }

    public void setBrpKoppelvlakVerzoek(boolean brpKoppelvlakVerzoek) {
        this.brpKoppelvlakVerzoek = brpKoppelvlakVerzoek;
    }



    @Override
    public final Verzoek.Stuurgegevens getStuurgegevens() {
        if (stuurgegevens == null) {
            stuurgegevens = new Verzoek.Stuurgegevens();
        }
        return stuurgegevens;
    }


}
