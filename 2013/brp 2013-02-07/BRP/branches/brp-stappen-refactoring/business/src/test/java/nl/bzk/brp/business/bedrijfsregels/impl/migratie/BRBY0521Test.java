/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.migratie;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.business.bedrijfsregels.util.ActieBerichtBuilder;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummer;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonInschrijvingGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Before;
import org.junit.Test;


/** Unit test voor de {@link BRBY0521} bedrijfsregel. */
public class BRBY0521Test {

    private BRBY0521 brby0521;

    @Before
    public void init() {
        brby0521 = new BRBY0521();
    }

    @Test
    public void testDatumAanvangNaInschrijving() {
        // happy flow
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120325))
                               .setDatumEinde(new Datum(20120324)).getActie();
        PersoonModel model = maakPersoonModel("123456789", 19960927);
        PersoonBericht bericht = maakPersoonBericht("123456789", 19960927);
        bericht.getAdressen().add(maakAdresCorrectieBericht("id.correctie.adres1", 20122012));

        List<Melding> meldingen = brby0521.executeer(model, bericht, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumAanvangVoorInschrijving() {
        // expected error
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120325))
                               .setDatumEinde(new Datum(20120324)).getActie();
        PersoonModel model = maakPersoonModel("123456789", 19960927);
        PersoonBericht bericht = maakPersoonBericht("123456789", 19960927);
        bericht.getAdressen().add(maakAdresCorrectieBericht("id.correctie.adres1", 19002012));

        List<Melding> meldingen = brby0521.executeer(model, bericht, actie);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0521, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.DEBLOKKEERBAAR, meldingen.get(0).getSoort());
    }

    @Test
    public void testDatumAanvangZelfde() {
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120325))
                               .setDatumEinde(new Datum(20120324)).getActie();
        PersoonModel model = maakPersoonModel("123456789", 19960927);
        PersoonBericht bericht = maakPersoonBericht("123456789", 19960927);
        bericht.getAdressen().add(maakAdresCorrectieBericht("id.correctie.adres1", 19960927));

        List<Melding> meldingen = brby0521.executeer(model, bericht, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBeideNull() {
        // geen persoon, geen bericht => niets doen.
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120325))
                               .setDatumEinde(new Datum(20120324)).getActie();
        List<Melding> meldingen = brby0521.executeer(null, null, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBerichtNull() {
        // geen bericht => niets doen.
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120325))
                               .setDatumEinde(new Datum(20120324)).getActie();
        PersoonModel model = maakPersoonModel("123456789", 19960927);
        List<Melding> meldingen = brby0521.executeer(model, null, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testModelNull() {
        // niet volledig, niets doen.
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120325))
                               .setDatumEinde(new Datum(20120324)).getActie();
        PersoonBericht bericht = maakPersoonBericht("123456789", 19960927);
        List<Melding> meldingen = brby0521.executeer(null, bericht, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBerichtGeenAdres() {
        // niet volledig, niets doen.
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120325))
                               .setDatumEinde(new Datum(20120324)).getActie();
        PersoonModel model = maakPersoonModel("123456789", 19960927);
        PersoonBericht bericht = maakPersoonBericht("123456789", 19960927);

        List<Melding> meldingen = brby0521.executeer(model, bericht, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBerichtAdresGeenAdresHouding() {
        // niet volledig, niets doen.
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120325))
                               .setDatumEinde(new Datum(20120324)).getActie();
        PersoonModel model = maakPersoonModel("123456789", 19960927);
        PersoonBericht bericht = maakPersoonBericht("123456789", 19960927);
        bericht.getAdressen().add(maakAdresCorrectieBericht("id.correctie.adres1", null));

        List<Melding> meldingen = brby0521.executeer(model, bericht, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testModelGeenInschrijving() {
        // niet volledig, niets doen.
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120325))
                               .setDatumEinde(new Datum(20120324)).getActie();
        PersoonModel model = maakPersoonModel("123456789", null);
        PersoonBericht bericht = maakPersoonBericht("123456789", 19960927);
        bericht.getAdressen().add(maakAdresCorrectieBericht("id.correctie.adres1", null));

        List<Melding> meldingen = brby0521.executeer(model, bericht, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBerichtMeerdereAdressenGoed() {
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120325))
                               .setDatumEinde(new Datum(20120324)).getActie();
        PersoonModel model = maakPersoonModel("123456789", 19960927);
        PersoonBericht bericht = maakPersoonBericht("123456789", 19960927);
        bericht.getAdressen().add(maakAdresCorrectieBericht("id.correctie.adres1", 20051030));
        bericht.getAdressen().add(maakAdresCorrectieBericht("id.correctie.adres2", 20060922));
        bericht.getAdressen().add(maakAdresCorrectieBericht("id.correctie.adres3", null));

        List<Melding> meldingen = brby0521.executeer(model, bericht, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBerichtMeerdereAdressenMetEenFout() {
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES).setDatumAanvang(new Datum(20120325))
                               .setDatumEinde(new Datum(20120324)).getActie();
        PersoonModel model = maakPersoonModel("123456789", 19960927);
        PersoonBericht bericht = maakPersoonBericht("123456789", 19960927);
        bericht.getAdressen().add(maakAdresCorrectieBericht("id.correctie.adres1", 20051030));
        bericht.getAdressen().add(maakAdresCorrectieBericht("id.correctie.adres2", 19000922));
        bericht.getAdressen().add(maakAdresCorrectieBericht("id.correctie.adres3", null));
        bericht.getAdressen().add(maakAdresCorrectieBericht("id.correctie.adres4", 19900712));

        List<Melding> meldingen = brby0521.executeer(model, bericht, actie);
        Assert.assertEquals(2, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0521, meldingen.get(0).getCode());
        Assert.assertEquals(MeldingCode.BRBY0521, meldingen.get(1).getCode());
        Assert.assertEquals("id.correctie.adres2", meldingen.get(0).getCommunicatieID());
        Assert.assertEquals("id.correctie.adres4", meldingen.get(1).getCommunicatieID());
    }

    private PersoonModel maakPersoonModel(final String bsn, final Integer inschrijfDatum) {
        PersoonModel model = new PersoonModel(maakPersoonBericht(bsn, inschrijfDatum));
        return model;
    }

    private PersoonBericht maakPersoonBericht(final String bsn, final Integer inschrijfDatum) {
        PersoonBericht bericht = new PersoonBericht();
        bericht.setAdressen(new ArrayList<PersoonAdresBericht>());
        bericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        bericht.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));
        if (inschrijfDatum != null) {
            bericht.setInschrijving(new PersoonInschrijvingGroepBericht());
            bericht.getInschrijving().setDatumInschrijving(new Datum(inschrijfDatum));
        }
        return bericht;
    }

    private PersoonAdresBericht maakAdresCorrectieBericht(final String id, final Integer datumAanvangAdreshouding) {
        PersoonAdresBericht bericht = new PersoonAdresBericht();
        bericht.setCommunicatieID(id);
        bericht.setStandaard(new PersoonAdresStandaardGroepBericht());
        if (datumAanvangAdreshouding != null) {
            bericht.getStandaard().setDatumAanvangAdreshouding(new Datum(datumAanvangAdreshouding));
        }
        bericht.getStandaard().setSoort(FunctieAdres.WOONADRES);
        bericht.getStandaard().setRedenWijzigingCode("P");
        bericht.getStandaard().setGemeenteCode("0360");
        bericht.getStandaard().setHuisnummer(new Huisnummer("22"));
        return bericht;
    }
}
