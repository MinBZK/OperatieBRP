/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.util.ActieBerichtBuilder;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.util.PersoonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/** Unit test voor de {@link BRBY0904} bedrijfsregel. */
public class BRBY0904Test {

    private BRBY0904 brby0904;

    @Before
    public void init() {
        brby0904 = new BRBY0904();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0904, brby0904.getRegel());
    }

    @Test
    public void testCorrectePeriodeGemeenteGeenPeriode() {
        // happy flow
        List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0904.voerRegelUit(null, maakPersoonOverlijden(maakLand("24", "land", null, null), 20120303),
                maakActie("id.actie1", 20120303, 20120404), null);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testCorrectePeriode() {
        List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0904.voerRegelUit(null, maakPersoonOverlijden(maakLand("24", "land", 19000101, null), 19000101),
                maakActie("id.actie1", 19000101, null), null);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testNietCorrectePeriode() {
        List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0904.voerRegelUit(null, maakPersoonOverlijden(maakLand("24", "land", 19000101, 19000110), 19000110),
                    maakActie("id.actie1", 19000110, null), null);
        Assert.assertEquals(1, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testCorrectePeriodeActieZonderOverlijden() {
        PersoonBericht persoonBericht = maakPersoonOverlijden(null, null);

        List<BerichtEntiteit> objectenDieDeRegelOvertreden = brby0904.voerRegelUit(null, persoonBericht,
                maakActie("id.actie1", 20120303, 20120404), null);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());


        persoonBericht.setOverlijden(null);
        objectenDieDeRegelOvertreden = brby0904.voerRegelUit(null, persoonBericht,
                maakActie("id.actie1", 20120303, 20120404), null);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    private Actie maakActie(final String verzendendId, final Integer begin, final Integer eind) {
        DatumEvtDeelsOnbekendAttribuut beginDatum = null;
        DatumEvtDeelsOnbekendAttribuut eindDatum = null;

        if (begin != null) {
            beginDatum = new DatumEvtDeelsOnbekendAttribuut(begin);
        }

        if (eind != null) {
            eindDatum = new DatumEvtDeelsOnbekendAttribuut(eind);
        }

        ActieBericht actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_OVERLIJDEN).setDatumAanvang(beginDatum)
                               .setDatumEinde(eindDatum).getActie();
        actie.setCommunicatieID(verzendendId);
        return actie;
    }

    private PersoonBericht maakPersoonOverlijden(final LandGebied land, final Integer overlijdingsDatum) {
        PersoonBericht persoon =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 1234567890, Geslachtsaanduiding.MAN, 19660606, null, null, "vn", "vg", "gsln");

        PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setCommunicatieID("overlijden");
        if (land != null) {
            overlijden.setLandGebiedOverlijden(new LandGebiedAttribuut(land));
        }
        overlijden.setDatumOverlijden(new DatumEvtDeelsOnbekendAttribuut(overlijdingsDatum));

        persoon.setOverlijden(overlijden);

        return persoon;
    }

    private LandGebied maakLand(final String code, final String naam, final Integer begin, final Integer eind) {
        DatumEvtDeelsOnbekendAttribuut datumAanvang = null;
        DatumEvtDeelsOnbekendAttribuut datumEinde = null;

        if (begin != null) {
            datumAanvang = new DatumEvtDeelsOnbekendAttribuut(begin);
        }
        if (eind != null) {
            datumEinde = new DatumEvtDeelsOnbekendAttribuut(eind);
        }

        return new LandGebied(new LandGebiedCodeAttribuut(Short.parseShort(code)), new NaamEnumeratiewaardeAttribuut(naam), null, datumAanvang,
                        datumEinde);
    }
}
