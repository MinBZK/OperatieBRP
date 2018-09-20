/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;


import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test klasse voor de methodes in de {@link HuwelijkGeregistreerdPartnerschapVerwerker} klasse.
 */
public class HuwelijkGeregistreerdPartnerschapVerwerkerTest {


    @Test
    public void testGetRegel() {
        final HuwelijkGeregistreerdPartnerschapVerwerker verwerker = new HuwelijkGeregistreerdPartnerschapVerwerker(null, null, null);

        Assert.assertEquals(Regel.VR02002, verwerker.getRegel());
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijkBericht = new HuwelijkBericht();
        huwelijkBericht.setStandaard(new RelatieStandaardGroepBericht());
        huwelijkBericht.getStandaard().setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(20130710));

        final HuwelijkGeregistreerdPartnerschapHisVolledigImpl huwelijkHisVolledig =
                new HuwelijkHisVolledigImplBuilder().build();

        Assert.assertTrue(huwelijkHisVolledig.getRelatieHistorie().getHistorie().size() == 0);

        final HuwelijkGeregistreerdPartnerschapVerwerker verwerker =
                new HuwelijkGeregistreerdPartnerschapVerwerker(huwelijkBericht, huwelijkHisVolledig,
                        creeerActie(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                                    SoortActie.REGISTRATIE_AANVANG_HUWELIJK_GEREGISTREERD_PARTNERSCHAP));

        verwerker.neemBerichtDataOverInModel();

        Assert.assertTrue(huwelijkHisVolledig.getRelatieHistorie().getHistorie().size() > 0);
        Assert.assertNotNull(huwelijkHisVolledig.getRelatieHistorie().getActueleRecord().getDatumAanvang());
    }

    @Test
    public void testNeemBerichtDataOverInModelBijEinde() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijkBericht = new HuwelijkBericht();
        huwelijkBericht.setStandaard(new RelatieStandaardGroepBericht());
        huwelijkBericht.getStandaard().setDatumEinde(new DatumEvtDeelsOnbekendAttribuut(20130701));

        final HuwelijkGeregistreerdPartnerschapHisVolledigImpl huwelijkHisVolledig =
                new HuwelijkHisVolledigImplBuilder()
                    .nieuwStandaardRecord(20130101)
                        .datumAanvang(20130101)
                    .eindeRecord()
                    .build();

        final HuwelijkGeregistreerdPartnerschapVerwerker verwerker =
                new HuwelijkGeregistreerdPartnerschapVerwerker(huwelijkBericht, huwelijkHisVolledig,
                        creeerActie(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                                    SoortActie.REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP));

        verwerker.neemBerichtDataOverInModel();

        Assert.assertNotNull(huwelijkHisVolledig.getRelatieHistorie().getActueleRecord().getDatumAanvang());
        Assert.assertNotNull(huwelijkHisVolledig.getRelatieHistorie().getActueleRecord().getDatumEinde());
    }

    @Test
    public void testVerzamelAfleidingsregelsVoorOmzettingPartnerschap() {
        final HuwelijkGeregistreerdPartnerschapVerwerker verwerker =
                new HuwelijkGeregistreerdPartnerschapVerwerker(null, null,
                                       creeerActie(SoortAdministratieveHandeling.OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK,
                                                   SoortActie.REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP));

        Assert.assertEquals(0, verwerker.getAfleidingsregels().size());

        verwerker.verzamelAfleidingsregels();

        Assert.assertEquals(1, verwerker.getAfleidingsregels().size());
    }

    @Test
    public void testVerzamelAfleidingsregelsVoorOverigeHandelingen() {
        HuwelijkGeregistreerdPartnerschapVerwerker verwerker =
            new HuwelijkGeregistreerdPartnerschapVerwerker(null, null,
                creeerActie(SoortAdministratieveHandeling.CORRECTIE_HUWELIJK, SoortActie.CORRECTIE_HUWELIJK));
        verwerker.verzamelAfleidingsregels();
        Assert.assertEquals(0, verwerker.getAfleidingsregels().size());

        verwerker = new HuwelijkGeregistreerdPartnerschapVerwerker(null, null,
                creeerActie(SoortAdministratieveHandeling.CORRECTIE_GEREGISTREERD_PARTNERSCHAP, SoortActie.CORRECTIE_GEREGISTREERD_PARTNERSCHAP));
        verwerker.verzamelAfleidingsregels();
        Assert.assertEquals(0, verwerker.getAfleidingsregels().size());

        verwerker = new HuwelijkGeregistreerdPartnerschapVerwerker(null, null,
            creeerActie(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, SoortActie.REGISTRATIE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP));
        verwerker.verzamelAfleidingsregels();
        Assert.assertEquals(0, verwerker.getAfleidingsregels().size());
    }

    /**
     * Creeert een standaard actie.
     *
     * @param soortAdministratieveHandeling soort administratieve handeling
     * @param soortActie soort actie
     * @return actie model
     */
    private ActieModel creeerActie(final SoortAdministratieveHandeling soortAdministratieveHandeling,
                                   final SoortActie soortActie)
    {
        return new ActieModel(new SoortActieAttribuut(soortActie),
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        soortAdministratieveHandeling), null,
                        null, null), null, new DatumEvtDeelsOnbekendAttribuut(20130710), null, DatumTijdAttribuut.bouwDatumTijd(2013, 7, 9), null);
    }
}
