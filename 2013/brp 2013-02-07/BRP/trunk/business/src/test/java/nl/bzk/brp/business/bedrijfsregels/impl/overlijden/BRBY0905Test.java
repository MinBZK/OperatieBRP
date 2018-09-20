/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.impl.migratie.BRBY0521;
import nl.bzk.brp.business.bedrijfsregels.util.ActieBerichtBuilder;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LangeNaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.PersoonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/** Unit test voor de {@link BRBY0521} bedrijfsregel. */
public class BRBY0905Test {

    private BRBY0905 brby0905;

    @Before
    public void init() {
        brby0905 = new BRBY0905();
    }

    @Test
    public void testCorrectePeriodeGemeenteGeenPeriode() {
        // happy flow
        List<Melding> meldingen =
            brby0905.executeer(null, maakPersoonOverlijden(maakPlaats("24", "plaats", null, null), 20120303),
                maakActie("id.actie1", 20120303, 20120404));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testCorrectePeriodeActieZonderDatums() {
        List<Melding> meldingen =
            brby0905.executeer(null, maakPersoonOverlijden(maakPlaats("24", "plaats", 19000101, null), 19000101),
                maakActie("id.actie1", 19000101, null));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testNietCorrectePeriode() {
        List<Melding> meldingen =
            brby0905.executeer(null,
                maakPersoonOverlijden(maakPlaats("24", "gemeente", 19000101, 19000110), 19000110),
                maakActie("id.actie1", 19000110, null));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testCorrectePeriodeActieZonderOverlijden() {
        PersoonBericht persoonBericht = maakPersoonOverlijden(null, null);

        List<Melding> meldingen = brby0905.executeer(null, persoonBericht, maakActie("id.actie1", 20120303, 20120404));
        Assert.assertEquals(0, meldingen.size());


        persoonBericht.setOverlijden(null);
        meldingen = brby0905.executeer(null, persoonBericht, maakActie("id.actie1", 20120303, 20120404));
        Assert.assertEquals(0, meldingen.size());
    }

    private Actie maakActie(final String verzendendId, final Integer begin, final Integer eind) {
        Datum beginDatum = null;
        Datum eindDatum = null;

        if (begin != null) {
            beginDatum = new Datum(begin);
        }

        if (eind != null) {
            eindDatum = new Datum(eind);
        }

        ActieBericht actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_OVERLIJDEN).setDatumAanvang(beginDatum)
                               .setDatumEinde(eindDatum).getActie();
        actie.setCommunicatieID(verzendendId);
        return actie;
    }

    private PersoonBericht maakPersoonOverlijden(final Plaats plaats, final Integer overlijdingsDatum) {
        PersoonBericht persoon =
            PersoonBuilder.bouwPersoon(1234567890, Geslachtsaanduiding.MAN, 19660606, null, "vn", "vg", "gsln");

        PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setCommunicatieID("overlijden");
        overlijden.setWoonplaatsOverlijden(plaats);
        overlijden.setDatumOverlijden(new Datum(overlijdingsDatum));

        persoon.setOverlijden(overlijden);

        return persoon;
    }

    private Plaats maakPlaats(final String code, final String naam, final Integer begin, final Integer eind) {
        Datum datumAanvang = null;
        Datum datumEinde = null;

        if (begin != null) {
            datumAanvang = new Datum(begin);
        }
        if (eind != null) {
            datumEinde = new Datum(eind);
        }

        Plaats plaats = new Plaats(new Woonplaatscode(Short.parseShort(code)),
                                   new LangeNaamEnumeratiewaarde(naam),
                                   datumAanvang,
                                   datumEinde);

        return plaats;
    }
}
