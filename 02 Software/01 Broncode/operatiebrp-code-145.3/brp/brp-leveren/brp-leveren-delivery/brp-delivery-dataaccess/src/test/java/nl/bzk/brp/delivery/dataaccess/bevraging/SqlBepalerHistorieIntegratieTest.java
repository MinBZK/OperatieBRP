/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.service.dalapi.QueryCancelledException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * SqlBepalerActueelIntegratieTest.
 */
@Data(resources = {
        "classpath:/data/aut-lev.xml", "classpath:/data/dataset_zoekpersoon.xml"})
public class SqlBepalerHistorieIntegratieTest extends AbstractDataAccessTest {

    @Inject
    private ZoekPersoonRepository zoekPersoonRepository;

    private boolean postgres = false;


    @Before
    public void setup() throws SQLException {
        this.postgres = zoekPersoonRepository.isPostgres();
    }

    @Test
    public void testZoekScheidingsTekenHistorischWasOoitLeegOnwaar() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        final String bsn = "402533928";
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
                Zoekoptie.EXACT, bsn);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN),
                Zoekoptie.LEEG, null);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        //niet vervallen en leeg
        Assert.assertEquals(0, ids.size());

    }

    @Test
    public void testZoekScheidingsTekenHistorischWasOoitLeegWaar() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        final String bsn = "402533929";
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
                Zoekoptie.EXACT, bsn);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN),
                Zoekoptie.LEEG, null);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        Assert.assertEquals(1, ids.size());

    }


    @Test
    public void testZoekNationaliteitHistorischIndagFalse() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        final String bsn = "402533928";
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
                Zoekoptie.EXACT, bsn);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE),
                Zoekoptie.EXACT, 2);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }


    @Test
    public void testZoekBsnHistorisch() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        final String bsn = "402533928";
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
                Zoekoptie.EXACT, bsn);

        zoekCriteria.add(zoekCriteria1);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testZoekGeboorteDatumHuisnummer() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), Zoekoptie.EXACT, 8);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM), Zoekoptie.EXACT, 19811002);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testZoekGeboorteDatumHuisnummerMetPeilmomentGeenResultaat() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), Zoekoptie.EXACT, 2);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM), Zoekoptie.EXACT, 19811002);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        //in 2010 wonend op nummer 8
        final Integer peilmoment = 20101010;

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, peilmoment, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(0, ids.size());
    }

    @Test
    public void testZoekGeboorteDatumHuisnummerMetPeilmomentWelResultaat() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), Zoekoptie.EXACT, 2);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM), Zoekoptie.EXACT, 19811002);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        final Integer peilmoment = 19751010;

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, peilmoment, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testDatumGeboorteSamengesteldeNaamGeslachtsnaamstamWoonPlaats() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();

        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM), Zoekoptie.EXACT, 19811002);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM), Zoekoptie.EXACT,
                "Drachten");
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.EXACT, "WasVlagX");

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testPostcodeHuisnummerHuisletterToevoegingSamengesteldenamenVoornamen() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();

        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_POSTCODE), Zoekoptie.EXACT, "3511BA");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), Zoekoptie.EXACT, 8);
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER), Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria4 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING), Zoekoptie.LEEG,
                null);
        ZoekCriterium zoekCriteria5 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN), Zoekoptie.VANAF_KLEIN,
                "KeesNietVervall");

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);
        zoekCriteria.add(zoekCriteria4);
        zoekCriteria.add(zoekCriteria5);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        Assert.assertEquals(1, ids.size());

    }

    @Test
    public void testPostcodeHuisnummerHuisletterToevoegingSamengesteldenamenVoornamenDubbelAlleenLeeg() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();

        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER), Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria4 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING), Zoekoptie.LEEG,
                null);
        ZoekCriterium zoekCriteria5 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN), Zoekoptie.VANAF_KLEIN,
                "KeesNietVervall");

        zoekCriteria.add(zoekCriteria3);
        zoekCriteria.add(zoekCriteria4);
        zoekCriteria.add(zoekCriteria5);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        Assert.assertEquals(1, ids.size());

    }

    @Test
    public void testBijoudingPartijcodeNaamOpenbareRuimteHuisnummer() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();

        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE), Zoekoptie.EXACT, 347);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE), Zoekoptie.EXACT,
                "Drachten");
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), Zoekoptie.EXACT, 8);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testGeslachtsnaamstamGeslachtCodeGerelateerdeOuderPersoonGeboortedatum() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.VANAF_KLEIN,
                "WASV");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GESLACHTSAANDUIDING_CODE), Zoekoptie.EXACT, 1);
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM),
                Zoekoptie.EXACT,
                19201010);
        ZoekCriterium zoekCriteria4 = new ZoekCriterium(getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.VANAF_KLEIN,
                "WasJan");

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);
        zoekCriteria.add(zoekCriteria4);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testGeslachtsnaamstamGeslachtCodeOuderlijkGezagIndicatieHeeftOuderlijkgezag() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.VANAF_KLEIN,
                "Krop");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GESLACHTSAANDUIDING_CODE), Zoekoptie.EXACT, 1);
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element
                .GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG), Zoekoptie.EXACT, true);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());

    }

    @Test
    public void testBetrokkenheidHistorischIndicatieOuderUitWieKindIsGeboren() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.VANAF_KLEIN,
                "WasJans");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN), Zoekoptie
                .EXACT, true);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testRelatieWoonplaatsAanvangHistorisch() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.HUWELIJK_WOONPLAATSNAAMAANVANG),
                Zoekoptie.VANAF_KLEIN,
                "Leiden");

        zoekCriteria.add(zoekCriteria1);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(2, ids.size());
    }

    @Test
    public void testIndicatieOnderCurateleHistorisch() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE), Zoekoptie.EXACT, true);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_SOORTNAAM), Zoekoptie.EXACT, SoortIndicatie.ONDER_CURATELE.getId());
        zoekCriteria1.setAdditioneel(zoekCriteria2);
        zoekCriteria.add(zoekCriteria1);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        System.out.println(sql);
        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testIndicatieOnderCurateleHistorischLeeg() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE), Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_SOORTNAAM), Zoekoptie.EXACT, SoortIndicatie.ONDER_CURATELE.getId());
        zoekCriteria1.setAdditioneel(zoekCriteria2);
        zoekCriteria.add(zoekCriteria1);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();
        System.out.println(sql);
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
       //5 valide personen, 1 heeft indicatie
        Assert.assertEquals(4, ids.size());
    }

    @Test
    public void testIndicatieOnderCurateleHistorischLeegMetBsn() throws SQLException, QueryCancelledException {
        final String bsn = "402533928";
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
                Zoekoptie.EXACT, bsn);

        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE), Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_SOORTNAAM), Zoekoptie.EXACT, SoortIndicatie.ONDER_CURATELE.getId());
        zoekCriteria2.setAdditioneel(zoekCriteria3);
        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        //402533928 heeft indicatie
        Assert.assertEquals(0, ids.size());
    }

    @Test
    public void testBijhoudingspartijHistorisch() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.VANAF_KLEIN,
                "WasVla");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE), Zoekoptie.EXACT, 347);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testZoekLeegGroepOpBetrokkenheidLeeg() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN),
                Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
                Zoekoptie.EXACT, 4268046028L);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        //test persoon zonder relatie of betr maar match op anummer
        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testZoekLeegGroepOpRelatieLeeg() throws SQLException, QueryCancelledException {
        //persoon heeft helemaal geen historisch adres of relatie wel een his samengestelde naam met null scheidingsteken
        //gekunstelde data
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE),
                Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
                Zoekoptie.EXACT, 4268046028L);
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN),
                Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria4 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER),
                Zoekoptie.LEEG, null);
        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);
        zoekCriteria.add(zoekCriteria4);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();
        System.out.println(sql);
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        //test persoon zonder relatie of betr maar match op anummer
        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testZoekLeegGroepOpBetrokkenPersoonLeeg() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM), Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
                Zoekoptie.EXACT, 4268046028L);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, true, null, false).maakSql();
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        //test persoon zonder relatie of betr maar match op anummer
        Assert.assertEquals(1, ids.size());
    }
}
