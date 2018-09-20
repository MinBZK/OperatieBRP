/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding.BijhoudingAfleidingDoorEmigratie;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding.BijhoudingAfleidingDoorVerhuizing;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
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
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class AdresAfleidingDoorGeboorteTest {

    private static final Integer DATUM_GEBOORTE = 20120101;

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    private AdresAfleidingDoorGeboorte adresAfleidingDoorGeboorte;

    @Before
    public void init() {
        Mockito.when(referentieDataRepository.vindRedenWijzingVerblijfOpCode(
                RedenWijzigingVerblijfCodeAttribuut.PERSOON_REDEN_WIJZIGING_ADRES_CODE_AMBTSHALVE_CODE))
                .thenReturn(new RedenWijzigingVerblijf(new RedenWijzigingVerblijfCodeAttribuut("A"),
                                                    new NaamEnumeratiewaardeAttribuut("Ambtshalve")));
    }

    @Test
    public void testGetRegel() {
        adresAfleidingDoorGeboorte = new AdresAfleidingDoorGeboorte(null, null, null);

        Assert.assertEquals(Regel.VR00013a, adresAfleidingDoorGeboorte.getRegel());
    }

    @Test
    public void testAdresgevendPersoonMetNederlandsAdres() {
        final ActieModel actieModel = creeerActie();

        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = new PersoonAdresHisVolledigImplBuilder(moeder)
                .nieuwStandaardRecord(19800101, null, 19800101)
                .indicatiePersoonAangetroffenOpAdres(null)
                .aangeverAdreshouding("P")
                .identificatiecodeAdresseerbaarObject("adresObj")
                .afgekorteNaamOpenbareRuimte("afkn")
                .datumAanvangAdreshouding(1980)
                .gemeente((short) 10)
                .gemeentedeel("gemdeel")
                .huisletter("a")
                .huisnummer(1)
                .huisnummertoevoeging("huisntoev")
                .identificatiecodeNummeraanduiding("identcode")
                .indicatiePersoonAangetroffenOpAdres(null)
                .landGebied((short) 6030)
                .locatieomschrijving("omschr")
                .locatieTenOpzichteVanAdres(LocatieTenOpzichteVanAdres.TO)
                .naamOpenbareRuimte("naamop")
                .postcode("1000AB")
                .redenWijziging("V")
                .soort(FunctieAdres.WOONADRES)
                .woonplaatsnaam("2")
                .eindeRecord().build();

        /*
         * Als de Geboorte van een Persoon (Kind) door de Actie RegistratieGeboorte binnen Inschriiving wordt
         * geregistreerd, dan wordt het (eerste) Adres van het Kind als volgt afgeleid geregistreerd:
         * Kind. Adres := (Kopie van) Adres van de AdresgevendeOuder op DatumGeboorte, met de volgende aanpassingen
         * RedenWijziging := "Ambtshalve"
         * AangeverAdreshouding := null
         * PersoonAangetroffenOpAdres := null
         * DatumAanvangAdreshouding := Geboorte.DatumGeboorte
         * DatumAanvangGeldigheid := Geboorte.DatumGeboorte
         * DatumEindeGeldigheid := null
         * ActieInhoud = RegistratieGeboorte.
         */

        moeder.getAdressen().add(persoonAdresHisVolledig);

        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(actieModel).datumGeboorte(20130702).eindeRecord().build();

        adresAfleidingDoorGeboorte = new AdresAfleidingDoorGeboorte(kind, moeder, actieModel);
        ReflectionTestUtils.setField(adresAfleidingDoorGeboorte, "referentieDataRepository", referentieDataRepository);

        final List<Afleidingsregel> afleidingsregels = adresAfleidingDoorGeboorte.leidAf().getVervolgAfleidingen();
        Assert.assertEquals(1, afleidingsregels.size());
        Assert.assertTrue(afleidingsregels.get(0) instanceof BijhoudingAfleidingDoorVerhuizing);

        final PersoonAdresHisVolledigImpl adresHisVolledig = kind.getAdressen().iterator().next();
        final HisPersoonAdresModel cLaagAdres = adresHisVolledig.getPersoonAdresHistorie().getActueleRecord();

        Assert.assertEquals(FunctieAdres.WOONADRES, cLaagAdres.getSoort().getWaarde());
        // RedenWijziging := "Ambtshalve"
        Assert.assertEquals("A", cLaagAdres.getRedenWijziging().getWaarde().getCode().getWaarde());
        // AangeverAdreshouding := null
        Assert.assertNull(cLaagAdres.getAangeverAdreshouding());
        // PersoonAangetroffenOpAdres := null
        Assert.assertNull(cLaagAdres.getIndicatiePersoonAangetroffenOpAdres());
        // DatumAanvangAdreshouding := Geboorte.DatumGeboorte
        Assert.assertEquals(kind.getPersoonGeboorteHistorie().getActueleRecord().getDatumGeboorte(),
                            cLaagAdres.getDatumAanvangAdreshouding());
        // DatumAanvangGeldigheid := Geboorte.DatumGeboorte
        Assert.assertEquals(kind.getPersoonGeboorteHistorie().getActueleRecord().getDatumGeboorte(),
                            cLaagAdres.getDatumAanvangGeldigheid());
        // DatumEindeGeldigheid := null
        Assert.assertNull(cLaagAdres.getDatumEindeGeldigheid());
        Assert.assertEquals("adresObj", cLaagAdres.getIdentificatiecodeAdresseerbaarObject().getWaarde());
        Assert.assertEquals("identcode", cLaagAdres.getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals(10, cLaagAdres.getGemeente().getWaarde().getCode().getWaarde().intValue());
        Assert.assertEquals("naamop", cLaagAdres.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("afkn", cLaagAdres.getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("gemdeel", cLaagAdres.getGemeentedeel().getWaarde());
        Assert.assertEquals(1, cLaagAdres.getHuisnummer().getWaarde().intValue());
        Assert.assertEquals("a", cLaagAdres.getHuisletter().getWaarde());
        Assert.assertEquals("huisntoev", cLaagAdres.getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("1000AB", cLaagAdres.getPostcode().getWaarde());
        Assert.assertEquals("2", cLaagAdres.getWoonplaatsnaam().getWaarde());
        Assert.assertEquals(LocatieTenOpzichteVanAdres.TO, cLaagAdres.getLocatieTenOpzichteVanAdres().getWaarde());
        Assert.assertEquals("omschr", cLaagAdres.getLocatieomschrijving().getWaarde());
        Assert.assertEquals(6030, cLaagAdres.getLandGebied().getWaarde().getCode().getWaarde().intValue());
    }

    @Test
    public void testAdresgevendPersoonMetBuitenlandsAdres() {
        final ActieModel actieModel = creeerActie();

        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = new PersoonAdresHisVolledigImplBuilder(moeder)
                .nieuwStandaardRecord(19800101, null, 19800101)
                .indicatiePersoonAangetroffenOpAdres(null)
                .aangeverAdreshouding("P")
                .identificatiecodeAdresseerbaarObject("adresObj")
                .afgekorteNaamOpenbareRuimte("afkn")
                .buitenlandsAdresRegel1("buitl1")
                .buitenlandsAdresRegel2("buitl2")
                .buitenlandsAdresRegel3("buitl3")
                .buitenlandsAdresRegel4("buitl4")
                .buitenlandsAdresRegel5("buitl5")
                .buitenlandsAdresRegel6("buitl6")
                .datumAanvangAdreshouding(1980)
                .gemeente((short) 10)
                .gemeentedeel("gemdeel")
                .huisletter("a")
                .huisnummer(1)
                .huisnummertoevoeging("huisntoev")
                .identificatiecodeNummeraanduiding("identcode")
                .indicatiePersoonAangetroffenOpAdres(null)
                .landGebied((short) 1)
                .locatieomschrijving("omschr")
                .locatieTenOpzichteVanAdres(LocatieTenOpzichteVanAdres.TO)
                .naamOpenbareRuimte("naamop")
                .postcode("1000AB")
                .redenWijziging("V")
                .soort(FunctieAdres.WOONADRES)
                .woonplaatsnaam("2")
                .eindeRecord().build();

        moeder.getAdressen().add(persoonAdresHisVolledig);

        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(actieModel).datumGeboorte(20130702).eindeRecord().build();

        adresAfleidingDoorGeboorte = new AdresAfleidingDoorGeboorte(kind, moeder, actieModel);
        ReflectionTestUtils.setField(adresAfleidingDoorGeboorte, "referentieDataRepository", referentieDataRepository);

        final List<Afleidingsregel> afleidingsregels = adresAfleidingDoorGeboorte.leidAf().getVervolgAfleidingen();
        Assert.assertEquals(1, afleidingsregels.size());
        Assert.assertTrue(afleidingsregels.get(0) instanceof BijhoudingAfleidingDoorEmigratie);

        final PersoonAdresHisVolledigImpl adresHisVolledig = kind.getAdressen().iterator().next();
        final HisPersoonAdresModel cLaagAdres = adresHisVolledig.getPersoonAdresHistorie().getActueleRecord();

        Assert.assertEquals(FunctieAdres.WOONADRES, cLaagAdres.getSoort().getWaarde());
        Assert.assertEquals("A", cLaagAdres.getRedenWijziging().getWaarde().getCode().getWaarde());
        Assert.assertNull(cLaagAdres.getAangeverAdreshouding());
        Assert.assertEquals(kind.getPersoonGeboorteHistorie().getActueleRecord().getDatumGeboorte(),
                            cLaagAdres.getDatumAanvangAdreshouding());
        Assert.assertEquals("adresObj", cLaagAdres.getIdentificatiecodeAdresseerbaarObject().getWaarde());
        Assert.assertEquals("identcode", cLaagAdres.getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals(10, cLaagAdres.getGemeente().getWaarde().getCode().getWaarde().intValue());
        Assert.assertEquals("naamop", cLaagAdres.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("afkn", cLaagAdres.getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("gemdeel", cLaagAdres.getGemeentedeel().getWaarde());
        Assert.assertEquals(1, cLaagAdres.getHuisnummer().getWaarde().intValue());
        Assert.assertEquals("a", cLaagAdres.getHuisletter().getWaarde());
        Assert.assertEquals("huisntoev", cLaagAdres.getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("1000AB", cLaagAdres.getPostcode().getWaarde());
        Assert.assertEquals("2", cLaagAdres.getWoonplaatsnaam().getWaarde());
        Assert.assertEquals(LocatieTenOpzichteVanAdres.TO, cLaagAdres.getLocatieTenOpzichteVanAdres().getWaarde());
        Assert.assertEquals("omschr", cLaagAdres.getLocatieomschrijving().getWaarde());
        Assert.assertEquals("buitl1", cLaagAdres.getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals("buitl2", cLaagAdres.getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals("buitl3", cLaagAdres.getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals("buitl4", cLaagAdres.getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals("buitl5", cLaagAdres.getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals("buitl6", cLaagAdres.getBuitenlandsAdresRegel6().getWaarde());
        Assert.assertEquals(1, cLaagAdres.getLandGebied().getWaarde().getCode().getWaarde().intValue());
        Assert.assertNull(cLaagAdres.getIndicatiePersoonAangetroffenOpAdres());
    }

    @Test
    public void testAdresGevendeOuderNietAangetroffenOpAdres() {
        final ActieModel actieModel = creeerActie();

        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = new PersoonAdresHisVolledigImplBuilder(moeder)
                .nieuwStandaardRecord(19800101, null, 19800101)
                .indicatiePersoonAangetroffenOpAdres(null)
                .aangeverAdreshouding("P")
                .identificatiecodeAdresseerbaarObject("adresObj")
                .afgekorteNaamOpenbareRuimte("afkn")
                .datumAanvangAdreshouding(1980)
                .gemeente((short) 10)
                .gemeentedeel("gemdeel")
                .huisletter("a")
                .huisnummer(1)
                .huisnummertoevoeging("huisntoev")
                .identificatiecodeNummeraanduiding("identcode")
                //Hier even EXPLICIET op NEE gezet
                .indicatiePersoonAangetroffenOpAdres(Nee.N)
                .landGebied((short) 6030)
                .locatieomschrijving("omschr")
                .locatieTenOpzichteVanAdres(LocatieTenOpzichteVanAdres.TO)
                .naamOpenbareRuimte("naamop")
                .postcode("1000AB")
                .redenWijziging("V")
                .soort(FunctieAdres.WOONADRES)
                .woonplaatsnaam("2")
                .eindeRecord().build();

        moeder.getAdressen().add(persoonAdresHisVolledig);

        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(actieModel).datumGeboorte(20130702).eindeRecord().build();

        adresAfleidingDoorGeboorte = new AdresAfleidingDoorGeboorte(kind, moeder, actieModel);
        ReflectionTestUtils.setField(adresAfleidingDoorGeboorte, "referentieDataRepository", referentieDataRepository);

        final List<Afleidingsregel> afleidingsregels = adresAfleidingDoorGeboorte.leidAf().getVervolgAfleidingen();
        Assert.assertEquals(1, afleidingsregels.size());
        Assert.assertTrue(afleidingsregels.get(0) instanceof BijhoudingAfleidingDoorVerhuizing);

        final PersoonAdresHisVolledigImpl adresHisVolledig = kind.getAdressen().iterator().next();
        final HisPersoonAdresModel cLaagAdres = adresHisVolledig.getPersoonAdresHistorie().getActueleRecord();

         // RedenWijziging := "Ambtshalve"
        Assert.assertEquals("A", cLaagAdres.getRedenWijziging().getWaarde().getCode().getWaarde());
        // AangeverAdreshouding := null
        Assert.assertNull(cLaagAdres.getAangeverAdreshouding());
        // PersoonAangetroffenOpAdres := null
        Assert.assertNull(cLaagAdres.getIndicatiePersoonAangetroffenOpAdres());
        // DatumAanvangAdreshouding := Geboorte.DatumGeboorte
        Assert.assertEquals(kind.getPersoonGeboorteHistorie().getActueleRecord().getDatumGeboorte(),
                            cLaagAdres.getDatumAanvangAdreshouding());
        // DatumAanvangGeldigheid := Geboorte.DatumGeboorte
        Assert.assertEquals(kind.getPersoonGeboorteHistorie().getActueleRecord().getDatumGeboorte(),
                            cLaagAdres.getDatumAanvangGeldigheid());

    }

    @Test
    public void testGeenAdresgevendAdres() {
        final ActieModel actieModel = creeerActie();

        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(actieModel).datumGeboorte(20130702).eindeRecord().build();

        adresAfleidingDoorGeboorte = new AdresAfleidingDoorGeboorte(kind, null, actieModel);
        final List<Afleidingsregel> afleidingsregels = adresAfleidingDoorGeboorte.leidAf().getVervolgAfleidingen();
        Assert.assertEquals(0, afleidingsregels.size());
        Assert.assertEquals(0, kind.getAdressen().size());
    }

    /**
     * Creeer een standaard actie.
     *
     * @return het actie model
     */
    private ActieModel creeerActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
                              new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                                      SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null,
                                      null, null), null, new DatumEvtDeelsOnbekendAttribuut(DATUM_GEBOORTE), null, DatumTijdAttribuut.nu(), null);
    }

}
