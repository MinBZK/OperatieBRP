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
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonInschrijvingGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
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
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
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
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
                               .setDatumEinde(new Datum(20120324)).getActie();
        PersoonModel model = maakPersoonModel("123456789", 19960927);
        PersoonBericht bericht = maakPersoonBericht("123456789", 19960927);
        bericht.getAdressen().add(maakAdresCorrectieBericht("id.correctie.adres1", 19002012));

        List<Melding> meldingen = brby0521.executeer(model, bericht, actie);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0521, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, meldingen.get(0).getSoort());
    }

    @Test
    public void testDatumAanvangZelfde() {
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
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
                ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
                                   .setDatumEinde(new Datum(20120324)).getActie();
        List<Melding> meldingen = brby0521.executeer(null, null, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBerichtNull() {
        // geen bericht => niets doen.
        Actie actie =
                ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
                                   .setDatumEinde(new Datum(20120324)).getActie();
        PersoonModel model = maakPersoonModel("123456789", 19960927);
        List<Melding> meldingen = brby0521.executeer(model, null, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testModelNull() {
        // niet volledig, niets doen.
        Actie actie =
                ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
                                   .setDatumEinde(new Datum(20120324)).getActie();
        PersoonBericht bericht = maakPersoonBericht("123456789", 19960927);
        List<Melding> meldingen = brby0521.executeer(null, bericht, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBerichtGeenAdres() {
        // niet volledig, niets doen.
        Actie actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
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
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
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
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
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
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
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
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(new Datum(20120325))
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
        Assert.assertEquals("id.correctie.adres2", meldingen.get(0).getVerzendendId());
        Assert.assertEquals("id.correctie.adres4", meldingen.get(1).getVerzendendId());
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
        bericht.setVerzendendId(id);
        bericht.setGegevens(new PersoonAdresStandaardGroepBericht());
        if (datumAanvangAdreshouding != null) {
            bericht.getGegevens().setDatumAanvangAdreshouding(new Datum(datumAanvangAdreshouding));
        }
        bericht.getGegevens().setSoort(FunctieAdres.WOONADRES);
        bericht.getGegevens().setRedenwijzigingCode(new RedenWijzigingAdresCode("P"));
        bericht.getGegevens().setGemeentecode(new Gemeentecode((short) 0360));
        bericht.getGegevens().setHuisnummer(new Huisnummer(22));
        return bericht;
    }
}
