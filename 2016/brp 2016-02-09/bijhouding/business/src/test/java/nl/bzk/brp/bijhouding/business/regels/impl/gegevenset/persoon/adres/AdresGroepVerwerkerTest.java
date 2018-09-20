/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding.BijhoudingAfleidingDoorVerhuizing;
import nl.bzk.brp.bijhouding.business.testconfig.AttribuutAdministratieTestConfig;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeAdresseerbaarObjectAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AttribuutAdministratieTestConfig.class })
public class AdresGroepVerwerkerTest {

    @Mock
    private ReferentieDataRepository referentieDataRepository;
    private PersoonBericht persoonBericht;
    private PersoonAdresBericht adres;
    private PersoonHisVolledigImpl persoonHisVolledigImpl;
    private ActieModel actie;
    private LandGebied nederland;
    private Gemeente gemeente;

    @Before
    public void setUp() throws IllegalAccessException, InstantiationException {
        MockitoAnnotations.initMocks(this);

        //Stamgegevens
        nederland = new LandGebied(new LandGebiedCodeAttribuut(new Short("1")), null, null, null, null);
        gemeente = new Gemeente(null, new GemeenteCodeAttribuut(new Short("589")), null, null, null, null);
        //Bericht
        persoonBericht = new PersoonBericht();
        //Adres
        adres = new PersoonAdresBericht();
        adres.setStandaard(new PersoonAdresStandaardGroepBericht());
        persoonBericht.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoonBericht.getAdressen().add(adres);

        final PersoonAdresStandaardGroepBericht adresStandaard = adres.getStandaard();


        adresStandaard.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        adresStandaard.setDatumTijdRegistratie(DatumTijdAttribuut.nu());
        adresStandaard.setIdentificatiecodeAdresseerbaarObject(new IdentificatiecodeAdresseerbaarObjectAttribuut("aaob"));
        adresStandaard.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimteAttribuut("nor"));
        adresStandaard.setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        adresStandaard.setHuisnummer(new HuisnummerAttribuut(1));
        adresStandaard.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));
        adresStandaard.setPostcode(new PostcodeAttribuut("1066AB"));
        adresStandaard.setHuisletter(new HuisletterAttribuut("A"));


        final ActieModel actieModel = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_ADRES), null, null,
                new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);

        persoonHisVolledigImpl = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .build();

        final PersoonAdresHisVolledigImpl persoonAdresHisVolledigImpl =
                new PersoonAdresHisVolledigImplBuilder(persoonHisVolledigImpl)
                        .nieuwStandaardRecord(actieModel)
                        .soort(FunctieAdres.WOONADRES)
                        .gemeente((short) 589)
                        .landGebied((short) 1)
                        .eindeRecord()
                        .build();

        persoonHisVolledigImpl.setAdressen(new HashSet<PersoonAdresHisVolledigImpl>());
        persoonHisVolledigImpl.getAdressen().add(persoonAdresHisVolledigImpl);
    }

    @Test
    public void testGetRegel() {
        final AdresGroepVerwerker verwerker = new AdresGroepVerwerker(persoonBericht.getAdressen().iterator().next(),
                                                                persoonHisVolledigImpl.getAdressen().iterator().next(),
                                                                actie);

        Assert.assertEquals(Regel.VR00013, verwerker.getRegel());
    }

    @Test
    public void testVerrijkBerichtVerhuizingBinnenGemeentelijk() {
        //Actie
        actie = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_ADRES),
                               new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                                       SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK), null,
                                       null, null), null, new DatumEvtDeelsOnbekendAttribuut(20120101), null, DatumTijdAttribuut.nu(), null);

        final AdresGroepVerwerker verwerker = new AdresGroepVerwerker(persoonBericht.getAdressen().iterator().next(),
                                                                persoonHisVolledigImpl.getAdressen().iterator().next(),
                                                                actie);
        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();

        final PersoonAdresHisVolledigImpl adresNaAfleiding = persoonHisVolledigImpl.getAdressen().iterator().next();
        Assert.assertEquals(3, adresNaAfleiding.getPersoonAdresHistorie().getAantal());
        final HisPersoonAdresModel actueleRecord = adresNaAfleiding.getPersoonAdresHistorie().getActueleRecord();
        Assert.assertEquals(actueleRecord.getDatumAanvangGeldigheid(),
                            adres.getStandaard().getDatumAanvangGeldigheid());
        Assert.assertEquals(actueleRecord.getIdentificatiecodeAdresseerbaarObject(), adres.getStandaard().getIdentificatiecodeAdresseerbaarObject());
        Assert.assertEquals(actueleRecord.getAfgekorteNaamOpenbareRuimte(),
                            adres.getStandaard().getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals(actueleRecord.getDatumAanvangAdreshouding(),
                            adres.getStandaard().getDatumAanvangAdreshouding());
        Assert.assertEquals(actueleRecord.getHuisnummer(), adres.getStandaard().getHuisnummer());
        Assert.assertEquals(actueleRecord.getSoort(), adres.getStandaard().getSoort());
        Assert.assertEquals(actueleRecord.getPostcode(), adres.getStandaard().getPostcode());
        Assert.assertEquals(actueleRecord.getHuisletter(), adres.getStandaard().getHuisletter());

        Assert.assertEquals(nederland.getCode().getWaarde(), actueleRecord.getLandGebied().getWaarde().getCode().getWaarde());
        Assert.assertEquals(gemeente.getCode().getWaarde(), actueleRecord.getGemeente().getWaarde().getCode().getWaarde());
    }

    @Test
    public void testVerrijkBerichtVerhuizingInterGemeentelijk() {
        //Actie
        actie = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_ADRES),
                               new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                                       SoortAdministratieveHandeling.VERHUIZING_INTERGEMEENTELIJK), null,
                                       null, null), null, new DatumEvtDeelsOnbekendAttribuut(20120101), null, DatumTijdAttribuut.nu(), null);

        final AdresGroepVerwerker verwerker = new AdresGroepVerwerker(persoonBericht.getAdressen().iterator().next(),
                                                                persoonHisVolledigImpl.getAdressen().iterator().next(),
                                                                actie);
        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();

        final PersoonAdresHisVolledigImpl adresNaAfleiding = persoonHisVolledigImpl.getAdressen().iterator().next();
        Assert.assertEquals(3, adresNaAfleiding.getPersoonAdresHistorie().getAantal());
        final HisPersoonAdresModel actueleRecord = adresNaAfleiding.getPersoonAdresHistorie().getActueleRecord();
        Assert.assertEquals(actueleRecord.getDatumAanvangGeldigheid(),
                            adres.getStandaard().getDatumAanvangGeldigheid());
        Assert.assertEquals(actueleRecord.getIdentificatiecodeAdresseerbaarObject(), adres.getStandaard().getIdentificatiecodeAdresseerbaarObject());
        Assert.assertEquals(actueleRecord.getAfgekorteNaamOpenbareRuimte(),
                            adres.getStandaard().getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals(actueleRecord.getDatumAanvangAdreshouding(),
                            adres.getStandaard().getDatumAanvangAdreshouding());
        Assert.assertEquals(actueleRecord.getHuisnummer(), adres.getStandaard().getHuisnummer());
        Assert.assertEquals(actueleRecord.getSoort(), adres.getStandaard().getSoort());
        Assert.assertEquals(actueleRecord.getPostcode(), adres.getStandaard().getPostcode());
        Assert.assertEquals(actueleRecord.getHuisletter(), adres.getStandaard().getHuisletter());

        Assert.assertEquals(nederland.getCode().getWaarde(), actueleRecord.getLandGebied().getWaarde().getCode().getWaarde());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testVerrijkBerichtOnbekendeAdministratieveHandeling() {
        //Actie
        actie = new ActieModel(new SoortActieAttribuut(SoortActie.CORRECTIE_ADRES),
                               new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                                       SoortAdministratieveHandeling.DUMMY), null, null, null),
                               null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                               DatumTijdAttribuut.nu(), null);

        final AdresGroepVerwerker verwerker = new AdresGroepVerwerker(persoonBericht.getAdressen().iterator().next(),
                                                                persoonHisVolledigImpl.getAdressen().iterator().next(),
                                                                actie);
        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();
    }

    @Test
    public void testVerzamelAfleidingsregelsVerhuizing() {
        final PersoonAdresHisVolledigImpl adresVolledig = persoonHisVolledigImpl.getAdressen().iterator().next();
        final LandGebied nl = new LandGebied(new LandGebiedCodeAttribuut(LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT), null, null, null, null);
        ReflectionTestUtils.setField(adresVolledig.getPersoonAdresHistorie().getActueleRecord(), "landGebied", new LandGebiedAttribuut(nl));
        final AdresGroepVerwerker verwerker = new AdresGroepVerwerker(persoonBericht.getAdressen().iterator().next(),
                                                                persoonHisVolledigImpl.getAdressen().iterator().next(),
                                                                actie);
        verwerker.verzamelAfleidingsregels();
        final List<Afleidingsregel> afleidingsregels = verwerker.getAfleidingsregels();
        Assert.assertEquals(1, afleidingsregels.size());
        final Afleidingsregel bijhGemAfleiding = afleidingsregels.get(0);
        Assert.assertTrue(bijhGemAfleiding instanceof BijhoudingAfleidingDoorVerhuizing);
    }
}
