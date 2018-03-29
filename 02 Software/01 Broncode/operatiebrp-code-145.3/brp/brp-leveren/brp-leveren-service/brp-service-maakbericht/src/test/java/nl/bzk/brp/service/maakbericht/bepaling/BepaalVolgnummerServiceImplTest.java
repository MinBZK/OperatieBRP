/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.bepaling;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Test;

/**
 * BepaalVolgnummerServiceImplTest.
 */
public class BepaalVolgnummerServiceImplTest {

    private Actie actieInhoud = TestVerantwoording.maakActie(1, ZonedDateTime.of(2010, 3, 22, 4, 10, 30, 17000000, DatumUtil.BRP_ZONE_ID));

    private BepaalVolgnummerService bepaalVolgnummerService = new BepaalVolgnummerServiceImpl();

    @Test
    public void testBepaalVolgnummer() {
        final Persoonslijst persoonslijst = new Persoonslijst(maakPersoon(), 0L);
        final Map<MetaObject, Integer> volgnummerMap = bepaalVolgnummerService.bepaalVolgnummers(persoonslijst);
        Assert.assertNotNull(volgnummerMap);
        int aantalObjecten = persoonslijst.getModelIndex().geefObjecten().size();
        Assert.assertEquals(aantalObjecten, volgnummerMap.size());
    }

    private MetaObject maakPersoon() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)
            .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_IDENTITEIT.getId()))
                    .metRecord()
                        .metId(4)
                        .metAttribuut(getAttribuutElement(Element.SOORTPERSOON_CODE.getId()), SoortPersoon.INGESCHREVENE.getCode())
                    .eindeRecord()
                .eindeGroep()
            .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GESLACHTSAANDUIDING.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_NAAMGEBRUIK.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .metObject()
                    .metObjectElement(getObjectElement(Element.PERSOON_VOORNAAM.getId()))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.PERSOON_VOORNAAM_STANDAARD.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                    .metObjectElement(getObjectElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT.getId()))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GESLACHTSAANDUIDING.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .metObject()
                .metId(111)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(456)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 3)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.ONDERZOEK.getId())
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                            .metAttribuut(Element.ONDERZOEK_PARTIJCODE.getId(), 1)
                        .eindeRecord()
                    .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_STANDAARD.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.ONDERZOEK_DATUMEINDE.getId(), 20000101)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.GEGEVENINONDERZOEK.getId())
                    .metGroep()
                        .metGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON_ADRES_HUISNUMMER.getNaam())
                            .metAttribuut(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId(), 456)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject()
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
        return metaObject;
    }
}
