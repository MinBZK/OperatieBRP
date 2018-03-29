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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.service.dalapi.QueryCancelledException;
import org.dbunit.DatabaseUnitException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * SqlBepalerActueelIntegratieTest.
 */
@Data(resources = {
        "classpath:/data/aut-lev.xml", "classpath:/data/dataset_zoekpersoon.xml"})
public class SqlBepalerActueelIntegratieTest extends AbstractDataAccessTest {

    @Inject
    private ZoekPersoonRepository zoekPersoonRepository;

    private boolean postgres = true;

    @Before
    public void setup() throws SQLException, DatabaseUnitException {
        this.postgres = zoekPersoonRepository.isPostgres();
    }

    @Test
    public void testZoekLeegGroepLeegActueel() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_OVERLIJDEN_DATUM),
                Zoekoptie.LEEG, null);

        zoekCriteria.add(zoekCriteria1);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        //persoon 1 heeft indagoverlijden false en geen overlijden datum
        //persoon 2 heeft indagoverlijden false en overlijden datum
        //persoon 3 heeft indagoverlijden true en overlijden datum explictiet NULL
        Assert.assertEquals(3, ids.size());
    }

    @Test
    public void testZoekLeegGroepOpBetrokkenheidLeegActueel() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN),
                Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
                Zoekoptie.EXACT, 4268046028L);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        //test persoon zonder relatie of betr maar match op anummer
        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testZoekLeegGroepOpRelatieLeegActueel() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE),
                Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
                Zoekoptie.EXACT, 4268046028L);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        //test persoon zonder relatie of betr maar match op anummer
        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testZoekLeegGroepOpBetrokkenPersoonLeegActueel() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM), Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
                Zoekoptie.EXACT, 4268046028L);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        //test persoon zonder relatie of betr maar match op anummer
        Assert.assertEquals(1, ids.size());
    }


    @Test
    public void testZoekIndicatieActueel() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        final String bsn = "402533928";
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
                Zoekoptie.EXACT, bsn);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE),
                Zoekoptie.EXACT, Boolean.TRUE);
        zoekCriteria1.setAdditioneel(zoekCriteria1);

        zoekCriteria.add(zoekCriteria1);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();
        System.out.println(sql);
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testZoekHeeftGeenIndicatieActueel() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        final String bsn = "402533929";
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
                Zoekoptie.EXACT, bsn);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE),
                Zoekoptie.LEEG, null);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testZoekBsnActueel() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        final String bsn = "402533928";
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
                Zoekoptie.EXACT, bsn);

        zoekCriteria.add(zoekCriteria1);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testZoekBsnOdAnrActueel() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        final String bsn = "111111111";
        final String anummer = "1268046023";
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
                Zoekoptie.EXACT, bsn);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
                Zoekoptie.EXACT, anummer, zoekCriteria1);

        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testZoekAnummerActueel() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        final String anr = "1268046023";
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
                Zoekoptie.EXACT, anr);

        zoekCriteria.add(zoekCriteria1);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }


    @Test
    public void testZoekGeboorteDatumHuisnummerActueel() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), Zoekoptie.EXACT, 3);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM), Zoekoptie.EXACT, 19811002);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testDatumGeboorteSamengesteldeNaamGeslachtsnaamstamWoonPlaatsActueel() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();

        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM), Zoekoptie.EXACT, 19811002);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM), Zoekoptie.EXACT,
                "Utrecht");
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.EXACT, "Vlag");

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());

    }

    @Test
    public void testPostcodeHuisnummerHuisletterToevoegingSamengesteldenamenVoornamenActueel() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();

        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_POSTCODE), Zoekoptie.EXACT, "3511BA");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), Zoekoptie.EXACT, 3);
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER), Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria4 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING), Zoekoptie.LEEG,
                null);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);
        zoekCriteria.add(zoekCriteria4);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());

    }

    @Test
    public void testPostcodeHuisnummerHuisletterToevoegingSamengesteldenamenVoornamenActueelMetNietmatchendOf() throws SQLException,
            QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();

        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_POSTCODE), Zoekoptie.EXACT, "3511BA");
        ZoekCriterium zoekCriteria2Of = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), Zoekoptie.EXACT, 5);

        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), Zoekoptie.EXACT, 3, zoekCriteria2Of);
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER), Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria4 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING), Zoekoptie.LEEG,
                null);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);
        zoekCriteria.add(zoekCriteria4);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());

    }

    @Test
    public void testPostcodeHuisnummerHuisletterToevoegingSamengesteldenamenVoornamenActueelMetMatchendOf() throws SQLException,
            QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();

        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_POSTCODE), Zoekoptie.EXACT, "3511BA");
        ZoekCriterium zoekCriteria2Of = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), Zoekoptie.EXACT, 3);

        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), Zoekoptie.EXACT, 5, zoekCriteria2Of);
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER), Zoekoptie.LEEG, null);
        ZoekCriterium zoekCriteria4 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING), Zoekoptie.LEEG,
                null);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);
        zoekCriteria.add(zoekCriteria4);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testGeslachtsnaamstamWoonplaatsnaamGeslachtsaanduidingCodeActueelVanafKlein() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();

        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.VANAF_KLEIN,
                "vla");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM), Zoekoptie.EXACT,
                "Utrecht");
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GESLACHTSAANDUIDING_CODE), Zoekoptie.EXACT, 1);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());

    }

    @Test
    public void testGeslachtsnaamstamWoonplaatsnaamGeslachtsaanduidingCodeActueelVanafExact() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        //geen match op vanaf exact, heeft hoofdletter start
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.VANAF_EXACT,
                "vla");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM), Zoekoptie.EXACT,
                "Utrecht");
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GESLACHTSAANDUIDING_CODE), Zoekoptie.EXACT, 1);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(0, ids.size());

    }

    @Test
    public void testGeslachtsnaamstamWoonplaatsnaamGeslachtsaanduidingCodeActueelKlein() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();

        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.EXACT,
                "Vlag");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM), Zoekoptie.KLEIN,
                "UTRECHT");
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GESLACHTSAANDUIDING_CODE), Zoekoptie.EXACT, 1);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testGeslachtsnaamstamWoonplaatsnaamGeslachtsaanduidingCodeActueelKleinGeenMatchWantVanaf() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();

        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.EXACT,
                "Vla");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM), Zoekoptie.KLEIN,
                "UTRECHT");
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GESLACHTSAANDUIDING_CODE), Zoekoptie.EXACT, 1);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(0, ids.size());
    }

    @Test
    public void testHuwelijkDatumaanvangSamengesteldenaamstamGeslachtsaanduidingActueel() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.HUWELIJK_DATUMAANVANG), Zoekoptie.EXACT, 20040101);
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.VANAF_KLEIN,
                "Vla");
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GESLACHTSAANDUIDING_CODE), Zoekoptie.EXACT, 1);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());

    }


    @Test
    public void testGeslachtsnaamstamGeslachtCodeOuderlijkGezagIndicatieHeeftOuderlijkgezagActueel() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.VANAF_KLEIN,
                "Jan");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GESLACHTSAANDUIDING_CODE), Zoekoptie.EXACT, 1);
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element
                .GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG), Zoekoptie.EXACT, true);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());

    }

    @Test
    public void testBetrokkenheidActueelIndicatieOuderUitWieKindIsGeboren() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER), Zoekoptie
                .EXACT, "402533930");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN), Zoekoptie
                .EXACT, true);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        Assert.assertEquals(1, ids.size());

    }

    @Test
    public void testBetrokkenheidActueelIndicatieOuderUitWieKindIsGeborenLeeg() throws SQLException, QueryCancelledException {
        //402533928 is kind
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER), Zoekoptie
                .EXACT, "402533928");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN), Zoekoptie
                .LEEG, null);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        Assert.assertEquals(1, ids.size());
    }

    @Test
    public void testBetrokkenheidActueelRelatieWoonplaatsEindeLeeg() throws SQLException, QueryCancelledException {
        //fam betr relaties hebben geen wpl einde in testdata maar persoon is wel kind en heeft relatie
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER), Zoekoptie
                .EXACT, "402533928");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAMEINDE), Zoekoptie
                .LEEG, null);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);
        Assert.assertEquals(1, ids.size());
    }


    @Test
    public void testGeslachtsnaamstamGeslachtCodeGerelateerdeOuderPersoonGeboortedatumActueel() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.VANAF_KLEIN,
                "Vla");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_GESLACHTSAANDUIDING_CODE), Zoekoptie.EXACT, 1);
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM),
                Zoekoptie.EXACT,
                19201010);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);
        zoekCriteria.add(zoekCriteria3);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }


    @Test
    public void testRelatieWoonplaatsAanvangActueel() throws SQLException, QueryCancelledException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.HUWELIJK_WOONPLAATSNAAMAANVANG),
                Zoekoptie.VANAF_KLEIN,
                "Leiden");

        zoekCriteria.add(zoekCriteria1);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(2, ids.size());
    }

    @Test
    public void testRelatieDatumAanvangActueel() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.HUWELIJK_DATUMAANVANG),
                Zoekoptie.EXACT,
                20040101);

        zoekCriteria.add(zoekCriteria1);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(2, ids.size());
    }


    @Test
    public void testIndicatieOnderCurateleActueel() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE), Zoekoptie.EXACT, true);

        zoekCriteria.add(zoekCriteria1);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();
        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }


    @Test
    public void testBijhoudingspartijActueel() throws SQLException, QueryCancelledException {

        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM),
                Zoekoptie.VANAF_KLEIN,
                "Vla");
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE), Zoekoptie.EXACT, 348);

        zoekCriteria.add(zoekCriteria1);
        zoekCriteria.add(zoekCriteria2);

        SqlStamementZoekPersoon sql = new SqlBepaler(zoekCriteria, 10, false, null, false).maakSql();

        final List<Long> ids = zoekPersoonRepository.zoekPersonen(sql, postgres);

        Assert.assertEquals(1, ids.size());
    }

}
