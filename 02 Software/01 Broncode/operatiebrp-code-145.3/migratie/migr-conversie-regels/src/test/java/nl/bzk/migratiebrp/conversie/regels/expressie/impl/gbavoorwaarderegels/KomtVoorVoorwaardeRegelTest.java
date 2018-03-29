/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test van de KomtVoorVoorwaarde Regel.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class KomtVoorVoorwaardeRegelTest {

    @Inject
    private KomtVoorVoorwaardeRegel instance;

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    /**
     * Test KomtVoorVoorwaardeRegel komt niet voor normale waarde.
     * @throws java.lang.Exception is fout
     */
    @Test
    public void testKomtVoorVoorwaardeRegelKomtNietVoor() throws Exception {
        testUtil.testVoorwaarde("KNV 01.03.10", "KNV(Persoon.Geboorte.Datum)");
    }

    @Test
    public void testNationaliteitUitzondering() throws Exception {
        testUtil.testVoorwaarde("KV 04.05.10", "(KV(Persoon.Nationaliteit.NationaliteitCode) OF KV(Persoon.Indicatie.Staatloos.Waarde))");
        testUtil.testVoorwaarde("KNV 04.05.10", "(KNV(Persoon.Nationaliteit.NationaliteitCode) EN KNV(Persoon.Indicatie.Staatloos.Waarde))");
    }

    /**
     * Test KomtVoorVoorwaardeRegel komt voor normale waarde.
     * @throws java.lang.Exception is fout
     */
    @Test
    public void testKomtVoorVoorwaardeRegelKomtVoor() throws Exception {
        testUtil.testVoorwaarde("KV 01.03.10", "KV(Persoon.Geboorte.Datum)");
        testUtil.testVoorwaarde("KV 01.01.10", "KV(Persoon.Identificatienummers.Administratienummer)");
    }

    /**
     * Test KomtVoorVoorwaardeRegel komt niet voor lijst waarde.
     * @throws java.lang.Exception is fout
     */
    @Test
    public void testKomtVoorVoorwaardeRegelLijstKomtNietVoor() throws Exception {
        testUtil.testVoorwaarde("KNV 08.11.60", "KNV(Persoon.Adres.Postcode)");
    }

    /**
     * Test KomtVoorVoorwaardeRegel komt voor lijst waarde.
     * @throws java.lang.Exception is fout
     */
    @Test
    public void testKomtVoorVoorwaardeRegelLijstKomtVoor() throws Exception {
        testUtil.testVoorwaarde("KV 08.11.60", "KV(Persoon.Adres.Postcode)");
    }

    /**
     * Test KomtVoorVoorwaardeRegel komt voor lijst waarde.
     * @throws java.lang.Exception is fout
     */
    @Test
    public void datumVestigingInNederlandKomtNietVoor() throws Exception {
        testUtil.testVoorwaarde("KNV 08.14.20", "NIET(MAP(FILTER(Persoon.Migratie, x, x.SoortCode E= \"I\"), y, y.DatumAanvangGeldigheid) E<> NULL)");
    }

    @Test
    public void datumVestigingInNederlandKomtVoor() throws Exception {
        testUtil.testVoorwaarde("KV 08.14.20", "MAP(FILTER(Persoon.Migratie, x, x.SoortCode E= \"I\"), y, y.DatumAanvangGeldigheid) E<> NULL");
    }

    @Test
    public void komtVoorHuwelijkAanvangDatum() throws Exception {
        testUtil.testVoorwaarde("KV 05.06.10",
                "(MAP(FILTER(Huwelijk, x, KNV(x.DatumEinde)), y, y.DatumAanvang) E<> NULL OF MAP(FILTER(GeregistreerdPartnerschap, x, KNV(x.DatumEinde)), y, "
                        + "y.DatumAanvang) E<> NULL)");
    }

    @Test
    public void komtNietVoorHuwelijkAanvangDatum() throws Exception {
        testUtil.testVoorwaarde("KNV 05.06.10",
                "(NIET(MAP(FILTER(Huwelijk, x, KNV(x.DatumEinde)), y, y.DatumAanvang) E<> NULL) EN NIET(MAP(FILTER(GeregistreerdPartnerschap, x, KNV(x"
                        + ".DatumEinde)), y, y.DatumAanvang) E<> NULL))");
    }
}
