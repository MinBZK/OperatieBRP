/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding.BijhoudingAfleidingDoorOverlijden;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BeeindigHuwelijkGeregistreerdPartnerschapDoorOverlijden;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class OverlijdenGroepVerwerkerTest {

    private static final Integer DATUM_OVERLIJDEN = 20120101;
    private static final String LOCATIE_OMSCHRIJVING = "test locatie";

    private OverlijdenGroepVerwerker verwerker;
    private PersoonBericht persoonBericht;
    private PersoonHisVolledigImpl persoonHisVolledigImpl;

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        persoonBericht = new PersoonBericht();
        final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setDatumOverlijden(new DatumEvtDeelsOnbekendAttribuut(DATUM_OVERLIJDEN));
        overlijden.setOmschrijvingLocatieOverlijden(new LocatieomschrijvingAttribuut(LOCATIE_OMSCHRIJVING));
        persoonBericht.setOverlijden(overlijden);

        persoonHisVolledigImpl = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final ActieModel actie = creeerActie();

        verwerker = new OverlijdenGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);

        ReflectionTestUtils.setField(verwerker, "referentieDataRepository", referentieDataRepository);
        Mockito.when(referentieDataRepository.getNederland()).thenReturn(getNederland());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.VR00012, verwerker.getRegel());
    }

    @Test
    public void testVerrijkBericht() {
        Assert.assertNull(persoonBericht.getOverlijden().getLandGebiedOverlijden());

        verwerker.verrijkBericht();

        Assert.assertNotNull(persoonBericht.getOverlijden().getLandGebiedOverlijden());
        Assert.assertEquals(getNederland().getCode(), persoonBericht.getOverlijden().getLandGebiedOverlijden().getWaarde().getCode());
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        Assert.assertNull(persoonHisVolledigImpl.getPersoonOverlijdenHistorie().getActueleRecord());

        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();

        Assert.assertNotNull(persoonHisVolledigImpl.getPersoonOverlijdenHistorie().getActueleRecord());

        Assert.assertEquals(DATUM_OVERLIJDEN,
                            persoonHisVolledigImpl.getPersoonOverlijdenHistorie().getActueleRecord()
                                    .getDatumOverlijden()
                                    .getWaarde());
        Assert.assertEquals(LOCATIE_OMSCHRIJVING,
                            persoonHisVolledigImpl.getPersoonOverlijdenHistorie().getActueleRecord()
                                    .getOmschrijvingLocatieOverlijden().getWaarde());

    }

    @Test
    public void testVerzamelAfleidingsregels() {
        final HuwelijkHisVolledigImpl relatie = new HuwelijkHisVolledigImpl();
        relatie.getRelatieHistorie().voegToe(
                new HisRelatieModel(relatie, new DatumEvtDeelsOnbekendAttribuut(20110101),
                                    null, null, null, null, null, null, null, null,
                                    null, null, null, null, null, null, creeerActie()));

        final BetrokkenheidHisVolledigImpl betrokkenheid = new PartnerHisVolledigImpl(relatie, persoonHisVolledigImpl);
        persoonHisVolledigImpl.getBetrokkenheden().add(betrokkenheid);

        Assert.assertEquals(0, verwerker.getAfleidingsregels().size());

        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();
        verwerker.verzamelAfleidingsregels();

        Assert.assertEquals(2, verwerker.getAfleidingsregels().size());

        Assert.assertTrue(verwerker.getAfleidingsregels().get(0) instanceof BijhoudingAfleidingDoorOverlijden);
        Assert.assertTrue(verwerker.getAfleidingsregels().get(1) instanceof BeeindigHuwelijkGeregistreerdPartnerschapDoorOverlijden);
    }

    private LandGebied getNederland() {
        return new LandGebied(LandGebiedCodeAttribuut.NEDERLAND, null, null, null, null);
    }

    /**
     * Creeer een standaard actie.
     *
     * @return het actie model
     */
    private ActieModel creeerActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_OVERLIJDEN),
                              new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                                      SoortAdministratieveHandeling.OVERLIJDEN_IN_NEDERLAND), null,
                                      null, null), null, new DatumEvtDeelsOnbekendAttribuut(20120101), null, DatumTijdAttribuut.nu(), null);
    }
}
