/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.dto.verzoek;

import java.util.Date;

import nl.bzk.brp.poc.business.util.DatumUtility;
import nl.bzk.brp.poc.domein.PocPersoon;
import nl.bzk.brp.poc.domein.PocPersoonAdres;

/**
 * <complexType name="verhuizing">
        <sequence>
            <element type="bijh:Persoon" name="persoon"/>
            <element type="brp:Adres" name="nieuwAdres"/>
            <element type="bijh:document" name="document"/>
        </sequence>
    </complexType>
 */
public class Verhuizing {

    private PocPersoon aanmelder;
    private PocPersoon verhuizer;
    private PocPersoonAdres nieuwAdres;

    public Verhuizing() {
        nieuwAdres = new PocPersoonAdres();
    }

    public PocPersoon getAanmelder() {
        return aanmelder;
    }

    public void setAanmelder(final PocPersoon aanmelder) {
        this.aanmelder = aanmelder;
    }

    public PocPersoon getVerhuizer() {
        return verhuizer;
    }

    public void setVerhuizer(PocPersoon verhuizer) {
        this.verhuizer = verhuizer;
    }

    public PocPersoonAdres getNieuwAdres() {
        return nieuwAdres;
    }

    public void setNieuwAdres(final PocPersoonAdres nieuwAdres) {
        this.nieuwAdres = nieuwAdres;
    }

    public Date getDatumAanvangGeldigheid() {
        return DatumUtility.zetIntDatumOmNaarDatum(getNieuwAdres().getDatumAanvangAdresHouding());
    }

    public void setDatumAanvangGeldigheid(final Date datumAanvangGeldigheid) {
        getNieuwAdres().setDatumAanvangAdresHouding(DatumUtility.zetDatumOmNaarInteger(datumAanvangGeldigheid));
    }
}
