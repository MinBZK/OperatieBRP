/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.AutorisatieAlles;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class VerwijderGerelateerdeOuderServiceImplTest {


    private VerwijderGerelateerdeOuderServiceImpl service = new VerwijderGerelateerdeOuderServiceImpl();

    @Test
    public void testOuderValtWeg() throws Exception {

        //@formatter:off
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
            .metObject()
                .metId(0)
                .metObjectElement(getObjectElement(Element.PERSOON_OUDER))
                .metObject()
                    .metId(0)
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                    .metObjecten(Lists.newArrayList(
                        MetaObject.maakBuilder()
                            .metId(0)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEKIND)),
                        MetaObject.maakBuilder()
                            .metId(10)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER))
                    ))
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), null, new StatischePersoongegevens());
        new AutorisatieAlles(berichtgegevens);

        service.execute(berichtgegevens);

        //kind blijft geautoriseerd
        final MetaObject gerelateerdeKind = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefObjecten(getObjectElement(Element.GERELATEERDEKIND)).iterator().next();
        Assert.assertTrue(berichtgegevens.isGeautoriseerd(gerelateerdeKind));

        //gerelateerde oude valt weg
        final MetaObject gerelateerdeOuder = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefObjecten(getObjectElement(Element.GERELATEERDEOUDER)).iterator()
                .next();
        Assert.assertFalse(berichtgegevens.isGeautoriseerd(gerelateerdeOuder));

    }

    @Test
    public void testOuderBlijftStaan() throws Exception {

        //@formatter:off
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
            .metObject()
                .metId(0)
                .metObjectElement(getObjectElement(Element.PERSOON_KIND))
                .metObject()
                    .metId(0)
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                    .metObjecten(Lists.newArrayList(
                        MetaObject.maakBuilder()
                            .metId(0)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER)),
                        MetaObject.maakBuilder()
                            .metId(10)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER))
                    ))
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst, new MaakBerichtPersoonInformatie
                (SoortSynchronisatie.VOLLEDIG_BERICHT), null, new StatischePersoongegevens());

        new AutorisatieAlles(berichtgegevens);

        service.execute(berichtgegevens);

        //gerelateerde ouders blijven staan want parent is kind
        final Set<MetaObject> gerelateerdeOuder = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefObjecten(getObjectElement(Element.GERELATEERDEOUDER));
        for (MetaObject metaObject : gerelateerdeOuder) {
            Assert.assertTrue(berichtgegevens.isGeautoriseerd(metaObject));
        }

    }

}
