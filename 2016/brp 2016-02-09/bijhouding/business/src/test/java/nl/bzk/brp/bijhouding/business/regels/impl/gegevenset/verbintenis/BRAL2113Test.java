/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/** Unit test voor de {@link BRAL2113} regel. */
public class BRAL2113Test {

    private final BRAL2113 bral2113 = new BRAL2113();

    @Test
    public void getRegel() {
        Assert.assertEquals(Regel.BRAL2113, bral2113.getRegel());
    }


    @Test
    public void testDatumAanvangNaDatumEinde() {
        final HuwelijkView huidigeSituatie = maakbestaandeSituatie(20101010);
        final HuwelijkBericht nieuweSituatie = maakNieuweSituatie(20091010);

        final List<BerichtEntiteit> berichtEntiteits = bral2113.voerRegelUit(huidigeSituatie, nieuweSituatie, null, null);

        Assert.assertEquals(1, berichtEntiteits.size());
    }

    @Test
    public void testDatumEindeOpDatumAanvang() {
        final HuwelijkView huidigeSituatie = maakbestaandeSituatie(20101010);
        final HuwelijkBericht nieuweSituatie = maakNieuweSituatie(20101010);

        final List<BerichtEntiteit> berichtEntiteits = bral2113.voerRegelUit(huidigeSituatie, nieuweSituatie, null, null);

        Assert.assertEquals(0, berichtEntiteits.size());
    }

    @Test
    public void testDatumEindeNaDatumAanvang() {
        final HuwelijkView huidigeSituatie = maakbestaandeSituatie(20101010);
        final HuwelijkBericht nieuweSituatie = maakNieuweSituatie(20101011);

        final List<BerichtEntiteit> berichtEntiteits = bral2113.voerRegelUit(huidigeSituatie, nieuweSituatie, null, null);

        Assert.assertEquals(0, berichtEntiteits.size());
    }

    @Test
    public void testZonderDatumAanvang() {
        final HuwelijkView huidigeSituatie = maakbestaandeSituatie(null);
        final HuwelijkBericht nieuweSituatie = maakNieuweSituatie(20101011);

        final List<BerichtEntiteit> berichtEntiteits = bral2113.voerRegelUit(huidigeSituatie, nieuweSituatie, null, null);

        Assert.assertEquals(0, berichtEntiteits.size());
    }

    @Test
    public void testZonderDatumEinde() {
        final HuwelijkView huidigeSituatie = maakbestaandeSituatie(20101011);
        final HuwelijkBericht nieuweSituatie = maakNieuweSituatie(null);

        final List<BerichtEntiteit> berichtEntiteits = bral2113.voerRegelUit(huidigeSituatie, nieuweSituatie, null, null);

        Assert.assertEquals(0, berichtEntiteits.size());
    }

    /**
     * Maakt en retourneert een nieuw huwelijk bericht met daarin een eind datum zoals opgegeven.
     *
     * @param datumEinde de eind datum van het huwelijk.
     * @return een nieuw huwelijk bericht.
     */
    private HuwelijkBericht maakNieuweSituatie(final Integer datumEinde) {
        final HuwelijkBericht nieuweSituatie = new HuwelijkBericht();
        nieuweSituatie.setStandaard(new RelatieStandaardGroepBericht());
        if (datumEinde != null) {
            nieuweSituatie.getStandaard().setDatumEinde(new DatumEvtDeelsOnbekendAttribuut(datumEinde));
        } else {
            nieuweSituatie.getStandaard().setDatumEinde(null);
        }

        return nieuweSituatie;
    }

    /**
     * Maakt en retourneert een bestaand huwelijk met daarin een aanvang datum zoals opgegeven.
     *
     * @param datumAanvang de aanvang datum van het huwelijk.
     * @return een nieuw huwelijk instantie.
     */
    private HuwelijkView maakbestaandeSituatie(final Integer datumAanvang) {
        final ActieModel actieModel;
        if (datumAanvang == null) {
            actieModel = maakActie(null);
        } else {
            actieModel = maakActie(new DatumEvtDeelsOnbekendAttribuut(datumAanvang));
        }

        final HuwelijkHisVolledig huwelijkHisVolledig =
            new HuwelijkHisVolledigImplBuilder().nieuwStandaardRecord(actieModel).datumAanvang(datumAanvang)
                                                .eindeRecord()
                                                .build();

        return new HuwelijkView(huwelijkHisVolledig);
    }

    /**
     * Creeert een standaard actie.
     *
     * @return het actie model
     */
    private ActieModel maakActie(final DatumEvtDeelsOnbekendAttribuut datumAanvang) {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                    SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_NEDERLAND), null,
                null, null), null, datumAanvang, null, DatumTijdAttribuut.nu(), null);
    }
}
