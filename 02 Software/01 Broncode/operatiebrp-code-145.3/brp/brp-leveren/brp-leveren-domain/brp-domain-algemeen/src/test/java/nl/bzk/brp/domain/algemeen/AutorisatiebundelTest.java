/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import static org.junit.Assert.assertEquals;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import org.junit.Test;

public class AutorisatiebundelTest {

    @Test
    public void nwBundel() {
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, dienst);

        assertEquals(dienst, autorisatiebundel.getDienst());
        assertEquals(SoortDienst.ATTENDERING, autorisatiebundel.getSoortDienst());
        assertEquals(autorisatiebundel.getToegangLeveringsautorisatie(), autorisatiebundel.getToegangLeveringsautorisatie());
        assertEquals(1, autorisatiebundel.getToegangLeveringsautorisatie().getLeveringsautorisatie().getId().intValue());
        assertEquals(1, autorisatiebundel.getLeveringsautorisatieId());
        assertEquals(leveringsautorisatie, autorisatiebundel.getLeveringsautorisatie());
        assertEquals(partij, autorisatiebundel.getPartij());
        assertEquals(Rol.AFNEMER, autorisatiebundel.getRol());
    }

}
