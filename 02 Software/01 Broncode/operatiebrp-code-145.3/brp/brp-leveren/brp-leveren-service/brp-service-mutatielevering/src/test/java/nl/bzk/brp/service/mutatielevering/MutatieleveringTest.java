/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;
import org.junit.Assert;
import org.junit.Test;

/**
 * MutatieleveringTest.
 */
public class MutatieleveringTest {

    @Test
    public void test() {

        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(
                new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER),
                TestAutorisaties.metSoortDienst(123, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE));
        final Map<Persoonslijst, Populatie> persoonslijstPopulatieMap = ImmutableMap.<Persoonslijst, Populatie>builder()
                .put(persoonslijst, Populatie.BINNEN).build();

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                toegangLeveringsAutorisatie, AutAutUtil.zoekDienst(toegangLeveringsAutorisatie.getLeveringsautorisatie(), SoortDienst
                .GEEF_DETAILS_PERSOON));

        final Mutatielevering mutatielevering = new Mutatielevering(autorisatiebundel, persoonslijstPopulatieMap);

        Assert.assertEquals(autorisatiebundel, mutatielevering.getAutorisatiebundel());
        Assert.assertEquals(persoonslijst, mutatielevering.getPersonen().iterator().next());
        Assert.assertEquals(Populatie.BINNEN, mutatielevering.getTeLeverenPersonenMap().get(persoonslijst));
        Assert.assertEquals(Stelsel.BRP, mutatielevering.getStelsel());
    }
}
