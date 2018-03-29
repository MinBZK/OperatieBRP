/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.sql.Timestamp;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectierun;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.service.selectie.algemeen.Selectie;
import nl.bzk.brp.service.selectie.algemeen.SelectietaakAutorisatie;

/**
 */
public class TestSelectie {

    public static Selectie maakSelectie() {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = TestAutorisaties.maak(Rol.AFNEMER, dienst);
        final Selectierun selectierun = new Selectierun(new Timestamp(System.currentTimeMillis()));
        final Selectietaak selectietaak = new Selectietaak(dienst, toegangLeveringsAutorisatie, 1);
        selectietaak.setIndicatieSelectielijstGebruiken(true);
        return new Selectie(selectierun, Lists.newArrayList(new SelectietaakAutorisatie(selectietaak, toegangLeveringsAutorisatie)));
    }

    public static Selectie maakSelectieMetSelectietaakInRun() {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = TestAutorisaties.maak(Rol.AFNEMER, dienst);
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Selectierun selectierun = new Selectierun(new Timestamp(System.currentTimeMillis()));
        final Selectietaak selectietaak = new Selectietaak(dienst, toegangLeveringsAutorisatie, 1);
        selectietaak.setIndicatieSelectielijstGebruiken(true);
        selectierun.setSelectieTaken(Sets.newHashSet(selectietaak));
        return new Selectie(selectierun, Lists.newArrayList(new SelectietaakAutorisatie(selectietaak, toegangLeveringsAutorisatie)));
    }

    public static Selectie maakLegeSelectie() {
        final Selectierun selectierun = new Selectierun(new Timestamp(System.currentTimeMillis()));
        return new Selectie(selectierun, Lists.newArrayList());
    }
}
