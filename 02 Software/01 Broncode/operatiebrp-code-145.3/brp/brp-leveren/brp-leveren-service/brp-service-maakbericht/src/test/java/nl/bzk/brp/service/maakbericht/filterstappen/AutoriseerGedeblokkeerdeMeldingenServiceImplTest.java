/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.collect.Lists;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class AutoriseerGedeblokkeerdeMeldingenServiceImplTest {

    @InjectMocks
    private AutoriseerGedeblokkeerdeMeldingenServiceImpl service;

    @Mock
    private LeveringsAutorisatieCache leveringsAutorisatieCache;

    @Test
    public void nietAfnemerNietGeautoriseerd() {

        final Leveringsautorisatie leveringsautorisatie1 = TestAutorisaties.metSoortDienst(SoortDienst.ZOEK_PERSOON);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie1, SoortDienst.ZOEK_PERSOON);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = TestAutorisaties.maak(Rol.AFNEMER, dienst);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(toegangLeveringsAutorisatie, dienst);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, null, null, autorisatiebundel, null);
        service.execute(berichtgegevens);
        Assert.assertFalse(berichtgegevens.isGeautoriseerdVoorGedeblokkeerdeMeldingen());

        Mockito.verifyZeroInteractions(leveringsAutorisatieCache);
    }

    @Test
    public void testAttribuutNietGeautoriseerd() {

        final Leveringsautorisatie leveringsautorisatie1 = TestAutorisaties.metSoortDienst(SoortDienst.ZOEK_PERSOON);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie1, SoortDienst.ZOEK_PERSOON);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = TestAutorisaties.maak(Rol.BIJHOUDINGSORGAAN_COLLEGE, dienst);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(toegangLeveringsAutorisatie, dienst);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, null, null, autorisatiebundel, null);
        service.execute(berichtgegevens);
        Assert.assertFalse(berichtgegevens.isGeautoriseerdVoorGedeblokkeerdeMeldingen());
        Mockito.verify(leveringsAutorisatieCache).geefGeldigeElementen(toegangLeveringsAutorisatie, dienst);
    }

    @Test
    public void testAttribuutGeautoriseerd() {
        final Leveringsautorisatie leveringsautorisatie1 = TestAutorisaties.metSoortDienst(SoortDienst.ZOEK_PERSOON);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie1, SoortDienst.ZOEK_PERSOON);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = TestAutorisaties.maak(Rol.BIJHOUDINGSORGAAN_COLLEGE, dienst);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(toegangLeveringsAutorisatie, dienst);

        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, null, null, autorisatiebundel, null);

        Mockito.when(leveringsAutorisatieCache.geefGeldigeElementen(toegangLeveringsAutorisatie, dienst))
                .thenReturn(Lists.newArrayList(ElementHelper.getAttribuutElement(Element.ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEREGEL_REGELCODE)));

        service.execute(berichtgegevens);
        Assert.assertTrue(berichtgegevens.isGeautoriseerdVoorGedeblokkeerdeMeldingen());
    }
}
