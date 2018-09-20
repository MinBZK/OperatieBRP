/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.migratie;

import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.business.bedrijfsregels.util.ActieBerichtBuilder;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit test voor de {@link BRBY0032} bedrijfsregel. */
public class BRBY0032Test {

    private BRBY0032 brby0032;

    @Before
    public void init() {
        brby0032 = new BRBY0032();
    }

    @Test
    public void testDatumEindeVoorAanvang() {
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
                    .setDatumEinde(new Datum(20120324)).getActie();

        List<Melding> meldingen = brby0032.executeer(null, null, actie);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0032, meldingen.get(0).getCode());
        Assert.assertEquals(Soortmelding.FOUT, meldingen.get(0).getSoort());
    }

    @Test
    public void testDatumEindeGelijkAanAanvang() {
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
                    .setDatumEinde(new Datum(20120325)).getActie();

        List<Melding> meldingen = brby0032.executeer(null, null, actie);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0032, meldingen.get(0).getCode());
        Assert.assertEquals(Soortmelding.FOUT, meldingen.get(0).getSoort());
    }

    @Test
    public void testDatumEindeNaAanvang() {
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
                    .setDatumEinde(new Datum(20120326)).getActie();

        List<Melding> meldingen = brby0032.executeer(null, null, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBedrijfsregelNietUitvoerbaar() {
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
                    .setDatumEinde(new Datum(20120326)).getActie();

        ReflectionTestUtils.setField(actie, "datumAanvangGeldigheid", null);

        List<Melding> meldingen = brby0032.executeer(null, null, actie);
        Assert.assertEquals(0, meldingen.size());

        ReflectionTestUtils.setField(actie, "datumAanvangGeldigheid", new Datum(20120325));
        ReflectionTestUtils.setField(actie, "datumEindeGeldigheid", null);

        meldingen = brby0032.executeer(null, null, actie);
        Assert.assertEquals(0, meldingen.size());

    }
}
