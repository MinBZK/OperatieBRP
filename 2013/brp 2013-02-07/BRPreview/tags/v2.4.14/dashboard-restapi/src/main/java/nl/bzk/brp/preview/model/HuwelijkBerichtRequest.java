/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.StringUtils;


public class HuwelijkBerichtRequest extends AbstractBerichtRequest {

    private final Datum datumAanvang = new Datum();

    private Plaats      plaats;

    private Persoon     persoon1;

    private Persoon     persoon2;

    @Override
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
            return OndersteundeBijhoudingsTypes.HUWELIJK;
    }

    @Override
    public List<Integer> creeerBsnLijst() {
        List<Integer> resultaat = new ArrayList<Integer>();
        resultaat.add(persoon1.getBsn());
        resultaat.add(persoon2.getBsn());
        return resultaat;
    }

    @Override
    public String creeerBerichtTekst() {
        String format = "Huwelijk op %s%s tussen %s en %s.";
        return String.format(format, getDatumAanvangTekst(), getPlaatsTekst(), persoon1.getNaamTekst(),
                persoon2.getNaamTekst());
    }

    public int getDatumAanvang() {
        return datumAanvang.getDecimalen();
    }

    @JsonIgnore
    public String getDatumAanvangTekst() {
        return datumAanvang.getTekst();
    }

    public void setDatumAanvang(final int datumAanvang) {
        this.datumAanvang.setDecimalen(datumAanvang);
    }

    public Plaats getPlaats() {
        return plaats;
    }

    @JsonIgnore
    public String getPlaatsTekst() {
        return hasPlaats() ? "" : (" te " + plaats.getNaam());
    }

    private boolean hasPlaats() {
        return plaats == null || !StringUtils.hasText(plaats.getNaam());
    }

    public void setPlaats(final Plaats plaats) {
        this.plaats = plaats;
    }

    public Persoon getPersoon1() {
        return persoon1;
    }

    public void setPersoon1(final Persoon persoon1) {
        this.persoon1 = persoon1;
    }

    public Persoon getPersoon2() {
        return persoon2;
    }

    public void setPersoon2(final Persoon persoon2) {
        this.persoon2 = persoon2;
    }

}
