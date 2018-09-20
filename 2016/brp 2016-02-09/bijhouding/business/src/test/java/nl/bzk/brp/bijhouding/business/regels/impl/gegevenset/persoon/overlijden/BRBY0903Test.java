/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.util.ActieBerichtBuilder;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestGemeenteBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.util.PersoonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/** Unit test voor de {@link BRBY0903} bedrijfsregel. */
public class BRBY0903Test {

    private BRBY0903 brby0903;

    @Before
    public void init() {
        brby0903 = new BRBY0903();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0903, brby0903.getRegel());
    }

    @Test
    public void testCorrectePeriodeGemeenteGeenPeriode() {
        // happy flow
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
                brby0903.voerRegelUit(null, maakPersoonOverlijden(maakGemeente("24", "gemeente", null, null), 20120303),
                maakActie("id.actie1", 20120303, null), null);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testCorrectePeriode() {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
                brby0903.voerRegelUit(null, maakPersoonOverlijden(maakGemeente("24", "gemeente", 19000101, null), 19000101),
                maakActie("id.actie1", 19000101, null), null);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testNietCorrectePeriode() {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
                brby0903.voerRegelUit(null, maakPersoonOverlijden(maakGemeente("24", "gemeente", 19000101, 19000110), 19000110),
                maakActie("id.actie1", 19000101, null), null);
        Assert.assertEquals(1, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testCorrectePeriodeActieZonderOverlijden() {
        final PersoonBericht persoonBericht = maakPersoonOverlijden(null, null);

        List<BerichtEntiteit> objectenDieDeRegelOvertreden =
                brby0903.voerRegelUit(null, persoonBericht,
                maakActie("id.actie1", 20120303, 20120404), null);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());


        persoonBericht.setOverlijden(null);
        objectenDieDeRegelOvertreden =
                brby0903.voerRegelUit(null, persoonBericht,
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

        final ActieBericht actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_OVERLIJDEN).setDatumAanvang(beginDatum)
                               .setDatumEinde(eindDatum).getActie();
        actie.setCommunicatieID(verzendendId);
        return actie;
    }

    private PersoonBericht maakPersoonOverlijden(final Gemeente gemeente, final Integer overlijdingsDatum) {
        final PersoonBericht persoon =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 1234567890, Geslachtsaanduiding.MAN, 19660606, null, null, "vn", "vg", "gsln");

        final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setCommunicatieID("overlijden");
        if (gemeente != null) {
            overlijden.setGemeenteOverlijden(new GemeenteAttribuut(gemeente));
        }
        overlijden.setDatumOverlijden(new DatumEvtDeelsOnbekendAttribuut(overlijdingsDatum));

        persoon.setOverlijden(overlijden);

        return persoon;
    }

    private Gemeente maakGemeente(final String code, final String naam, final Integer begin, final Integer eind) {
        DatumEvtDeelsOnbekendAttribuut datumAanvang = null;
        if (begin != null) {
            datumAanvang = new DatumEvtDeelsOnbekendAttribuut(begin);
        }
        DatumEvtDeelsOnbekendAttribuut datumEind = null;
        if (eind != null) {
            datumEind = new DatumEvtDeelsOnbekendAttribuut(eind);
        }

        final Partij partij = TestPartijBuilder.maker()
            .metNaam(naam)
            .metSoort(SoortPartij.GEMEENTE)
            .metCode(new PartijCodeAttribuut(Integer.parseInt(code)))
            .metDatumAanvang(datumAanvang)
            .metDatumEinde(datumEind).maak();

        return TestGemeenteBuilder.maker()
            .metNaam(naam)
            .metCode(new GemeenteCodeAttribuut(Short.parseShort(code)))
            .metPartij(partij)
            .metDatumAanvang(datumAanvang)
            .metDatumEinde(datumEind).maak();
    }

}
