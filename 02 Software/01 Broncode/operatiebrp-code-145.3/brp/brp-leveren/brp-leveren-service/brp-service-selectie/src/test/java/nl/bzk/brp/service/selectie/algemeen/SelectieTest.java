/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.algemeen;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectierun;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor {@link Selectie}.
 */
public class SelectieTest {

    private Selectie selectie;

    @Before
    public void before() {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Selectierun selectierun = new Selectierun(new Timestamp(System.currentTimeMillis()));
        final Dienst dienst = new Dienst(new Dienstbundel(leveringsautorisatie), SoortDienst.SELECTIE);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = TestAutorisaties.maak(Rol.AFNEMER, dienst);
        final Selectietaak taak = new Selectietaak(dienst, toegangLeveringsAutorisatie, 1);
        final Set<Selectietaak> taken = Collections.singleton(taak);
        selectierun.setSelectieTaken(taken);
        selectie = new Selectie(selectierun,
                Collections.singletonList(
                        new SelectietaakAutorisatie(taak,
                                new ToegangLeveringsAutorisatie(new PartijRol(new Partij("test", "000123"), Rol.AFNEMER), leveringsautorisatie))));
    }

    @Test
    public void isUitvoerbaar() throws Exception {
        assertTrue(selectie.isUitvoerbaar());
    }

    @Test
    public void nietUitvoerbaarTakenLeeg() throws Exception {
        selectie.getSelectierun().setSelectieTaken(Collections.emptySet());

        assertFalse(selectie.isUitvoerbaar());
    }

    @Test
    public void nietUitvoerbaarAutorisatiesNull() throws Exception {
        ReflectionTestUtils.setField(selectie, "selectietaakAutorisatieList", null);

        assertFalse(selectie.isUitvoerbaar());
    }

    @Test
    public void nietUitvoerbaarAutorisatiesLeeg() throws Exception {
        ReflectionTestUtils.setField(selectie, "selectietaakAutorisatieList", emptyList());

        assertFalse(selectie.isUitvoerbaar());
    }

}
