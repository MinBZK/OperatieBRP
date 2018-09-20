/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres.groep3;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.util.ActieBerichtBuilder;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestGemeenteBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.util.PersoonAdresBuilder;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test voor de {@link nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres.groep3.BRBY0175} klasse en bijbehorende bedrijfsregel.
 */
public class BRBY0175Test {

    private BRBY0175 brby0175;

    @Before
    public void init() {
        brby0175 = new BRBY0175();
    }

    @Test
    public void testCorrectePeriodeGemeenteGeenPeriode() {
        // happy flow
        List<BerichtEntiteit> fouteObjecten = brby0175.voerRegelUit(null,
                maakPersoonMetPlaats(maakGemeente("24", "gemeente", null, null), 20120303),
                maakActie("id.actie1", 20120303, 20120404), null);
        Assert.assertEquals(0, fouteObjecten.size());
    }

    @Test
    public void testGemeenteNietGevuld() {
        List<BerichtEntiteit> fouteObjecten = brby0175.voerRegelUit(null,
                                                                    maakPersoonMetPlaats(null, 20120303),
                                                                    maakActie("id.actie1", 20120303, 20120404), null);
        Assert.assertEquals(0, fouteObjecten.size());
    }

    @Test
    public void testCorrectePeriodeGemeenteWelPeriode() {
        List<BerichtEntiteit> fouteObjecten = brby0175.voerRegelUit(null,
                maakPersoonMetPlaats(maakGemeente("24", "gemeente", 19000101, 25000101), 20120303),
                maakActie("id.actie1", 20120303, 20120404), null);
        Assert.assertEquals(0, fouteObjecten.size());
    }

    @Test
    public void testCorrectePeriodeGemeenteZonderEinde() {
        List<BerichtEntiteit> fouteObjecten = brby0175.voerRegelUit(null,
                maakPersoonMetPlaats(maakGemeente("24", "gemeente", 19000101, null), 20120303),
                maakActie("id.actie1", 20120303, 20120404), null);
        Assert.assertEquals(0, fouteObjecten.size());
    }

    @Test
    public void testCorrectePeriodeActieZonderEinde() {
        List<BerichtEntiteit> fouteObjecten = brby0175.voerRegelUit(null,
                maakPersoonMetPlaats(maakGemeente("24", "gemeente", 19000101, null), 20120303),
                maakActie("id.actie1", 20120303, null), null);
        Assert.assertEquals(0, fouteObjecten.size());
    }

    @Test
    public void testCorrectePeriodeActieZonderEinde2() {
        List<BerichtEntiteit> fouteObjecten = brby0175.voerRegelUit(null,
                maakPersoonMetPlaats(maakGemeente("24", "gemeente", 19000101, null), 19000101),
                maakActie("id.actie1", 19000101, null), null);
        Assert.assertEquals(0, fouteObjecten.size());
    }

    @Test
    public void testAfgelopenPeriodeActieZonderEinde() {
        PersoonBericht persoon = maakPersoonMetPlaats(maakGemeente("24", "gemeente", 20000101, null), 19911231);
        List<BerichtEntiteit> fouteObjecten = brby0175.voerRegelUit(null,
                persoon, maakActie("id.actie1", 19911231, null), null);
        Assert.assertEquals(1, fouteObjecten.size());
        Assert.assertEquals(persoon.getAdressen().get(0), fouteObjecten.get(0));
    }

    @Test
    public void testAfgelopenPeriodeActieZonderEinde2() {
        PersoonBericht persoon = maakPersoonMetPlaats(maakGemeente("24", "gemeente", 20000101, null), 19911231);
        List<BerichtEntiteit> fouteObjecten = brby0175.voerRegelUit(null,
                persoon, maakActie("id.actie1", 19911231, 19921231), null);
        Assert.assertEquals(1, fouteObjecten.size());
    }

    @Test
    public void testVerschillendeDatums() {
        PersoonBericht persoon = maakPersoonMetPlaats(maakGemeente("24", "gemeente", 19920101, 200020101), 19910231);
        List<BerichtEntiteit> fouteObjecten = brby0175.voerRegelUit(null,
                persoon, maakActie("id.actie1", 19910231, 19970231), null);
        Assert.assertEquals(1, fouteObjecten.size());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals("BRBY0175", brby0175.getRegel().getCode());
    }

    /**
     * Bouwt een {@link ActieBericht} instantie van de soort {@link SoortActie#CORRECTIE_ADRES} met opgegeven datum
     * aanvang en datum einde geldigheid. Verder wordt ook de communicatie/verzendend id in het {@link ActieBericht}
     * gezet.
     *
     * @param verzendendId de communicatie/verzendend id van de actie in het bericht.
     * @param begin de datum aanvang geldigheid (als integer) van de actie.
     * @param eind de datum einde geldigheid (als integer) van de actie.
     * @return een nieuwe {@link ActieBericht} instantie met gevulde DAG/DEG.
     */
    private ActieBericht maakActie(final String verzendendId, final Integer begin, final Integer eind) {
        DatumEvtDeelsOnbekendAttribuut beginDatum = null;
        DatumEvtDeelsOnbekendAttribuut eindDatum = null;

        if (begin != null) {
            beginDatum = new DatumEvtDeelsOnbekendAttribuut(begin);
        }

        if (eind != null) {
            eindDatum = new DatumEvtDeelsOnbekendAttribuut(eind);
        }

        ActieBericht actie =
                ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES).setDatumAanvang(beginDatum)
                        .setDatumEinde(eindDatum).getActie();
        actie.setCommunicatieID(verzendendId);
        return actie;
    }

    /**
     * Bouwt een {@link PersoonBericht} met enkele vaste waardes (zoals BSN, Geslachtsaanduiding etc.) met daarin
     * tevens een {@link PersoonAdresBericht} met een vast adres. Hierbij is alleen de gemeente variabel en deze kan
     * gezet worden naar de opgegeven gemeente.
     *
     * @param gemeente de gemeente die gebruikt moet worden in het adres in het bericht.
     * @return een nieuwe {@link PersoonBericht} instantie met gevulde waardes en adres.
     */
    private PersoonBericht maakPersoonMetPlaats(final Gemeente gemeente, final Integer datumAanvangAdresHouding) {
        PersoonBericht persoon =
                PersoonBuilder
                        .bouwPersoon(SoortPersoon.INGESCHREVENE, 1234567890, Geslachtsaanduiding.MAN, 19660606, null, null, "vn", "vg", "gsln");

        PersoonAdresBericht paBericht =
                PersoonAdresBuilder.bouwWoonadres("NOR", 123, "1234HH", StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM.getWaarde(),
                        gemeente, datumAanvangAdresHouding);
        paBericht.setCommunicatieID("adres1");
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(paBericht);
        return persoon;
    }

    /**
     * Bouwt een nieuwe {@link Gemeente} instantie met opgegeven waardes.
     *
     * @param code de code van de gemeente.
     * @param naam de naam van de gemeente.
     * @param begin het begin (datum als integer) van de bestaansperiode van de gemeente.
     * @param eind het einde (datum als integer) van de bestaansperiode van de gemeente.
     * @return een nieuwe {@link Gemeente} instantie met opgegeven waardes en bestaansperiode.
     */
    private Gemeente maakGemeente(final String code, final String naam, final Integer begin, final Integer eind) {
        DatumEvtDeelsOnbekendAttribuut datumAanvang = null;
        if (begin != null) {
            datumAanvang = new DatumEvtDeelsOnbekendAttribuut(begin);
        }
        DatumEvtDeelsOnbekendAttribuut datumEind = null;
        if (eind != null) {
            datumEind = new DatumEvtDeelsOnbekendAttribuut(eind);
        }

        Partij partij = TestPartijBuilder.maker()
            .metSoort(SoortPartij.GEMEENTE)
            .metCode(Integer.parseInt(code))
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
