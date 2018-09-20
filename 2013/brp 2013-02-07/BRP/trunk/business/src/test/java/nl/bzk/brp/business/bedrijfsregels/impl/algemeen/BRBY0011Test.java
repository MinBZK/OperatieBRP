/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.algemeen;

import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.business.bedrijfsregels.util.ActieBerichtBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;
import org.junit.Before;
import org.junit.Test;


public class BRBY0011Test {

    private BRBY0011 brby0011;

    @Before
    public void init() {
        brby0011 = new BRBY0011();
    }

    @Test
    public void testDatumAanvangInDeToekomst() {
        List<Melding> meldingen = brby0011.executeer(null, null, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.CORRECTIE_ADRES).setDatumAanvang(DatumUtil.morgen()).getActie());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0011, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.FOUT, meldingen.get(0).getSoort());
    }

    @Test
    public void testDatumAanvangVandaag() {
        List<Melding> meldingen = brby0011.executeer(null, null, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.CORRECTIE_ADRES).setDatumAanvang(DatumUtil.vandaag()).getActie());
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumAanvangInHetVerleden() {
        List<Melding> meldingen = brby0011.executeer(null, null, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.CORRECTIE_ADRES).setDatumAanvang(DatumUtil.gisteren()).getActie());
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void zouMeldingMoetenGevenOmdatDatumAanvangOverlijdenInToekomstLigt() {
        List<Melding> meldingen = brby0011.executeer(null, null, ActieBerichtBuilder.bouwNieuweActie(
                SoortActie.REGISTRATIE_OVERLIJDEN).setDatumAanvang(DatumUtil.morgen()).getActie());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0011, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.FOUT, meldingen.get(0).getSoort());
    }
}
