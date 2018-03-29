/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Test;

public class ActueelEnIdentificerendMetaRecordPredicateTest {

    private Actie actieInhoud = TestVerantwoording.maakActie(1, DatumUtil.nuAlsZonedDateTime());
    private Actie actieVerval = TestVerantwoording.maakActie(1, DatumUtil.nuAlsZonedDateTime().minusYears(1));

    @Test
    public void testActueelRecordIdentificerendeGroepHoofdpersoon() throws Exception {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieInhoud)
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 1L);
        final ActueelEnIdentificerendMetaRecordPredicate predicate =
                new ActueelEnIdentificerendMetaRecordPredicate(persoonslijst);
        final MetaRecord record = persoonslijst.getModelIndex().geefGroepen().iterator().next().getRecords().iterator().next();
        assertTrue(predicate.test(record));
    }

    @Test
    public void testVervallenRecordIdentificerendeGroepHoofdpersoon() throws Exception {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieInhoud)
                    .metActieVerval(actieVerval)
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 1L);
        final ActueelEnIdentificerendMetaRecordPredicate predicate =
                new ActueelEnIdentificerendMetaRecordPredicate(persoonslijst);
        final MetaRecord record = persoonslijst.getModelIndex().geefGroepen().iterator().next().getRecords().iterator().next();
        assertFalse(predicate.test(record));
    }

    @Test
    public void testActueelRecordNietIdentificerendeGroepHoofdpersoon() throws Exception {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_INSCHRIJVING.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieInhoud)
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 1L);
        final ActueelEnIdentificerendMetaRecordPredicate predicate =
                new ActueelEnIdentificerendMetaRecordPredicate(persoonslijst);
        final MetaRecord record = persoonslijst.getModelIndex().geefGroepen().iterator().next().getRecords().iterator().next();
        assertFalse(predicate.test(record));
    }

    @Test
    public void testActueelRecordIdentificerendeGroepGerelateerde() throws Exception {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                        .metRecord()
                            .metId(1)
                            .metActieInhoud(actieInhoud)
                        .eindeRecord()
                    .eindeGroep()

                    .metObject()
                            .metId(1)
                            .metObjectElement(getObjectElement(Element.PERSOON_KIND.getId()))
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.PERSOON_KIND_IDENTITEIT.getId()))
                                .metRecord()
                                    .metId(1)
                                    .metActieInhoud(actieInhoud)
                                .eindeRecord()
                            .eindeGroep()
                            .metObject()
                                .metId(2)
                                .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                                    .metGroep()
                                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD.getId()))
                                            .metRecord()
                                                .metActieInhoud(actieInhoud)
                                            .eindeRecord()
                                    .eindeGroep()
                                    .metObject()
                                        .metId(3)
                                        .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER.getId()))
                                                .metObject()
                                                    .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId()))
                                                    .metId(33)
                                                    .metGroep()
                                                        .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS.getId()))
                                                        .metRecord()
                                                            .metActieInhoud(actieInhoud)
                                                        .eindeRecord()
                                                    .eindeGroep()
                                                .eindeObject()
                                    .eindeObject()
                            .eindeObject()
                    .eindeObject()

             .eindeObject()
        .build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 1L);
        final ActueelEnIdentificerendMetaRecordPredicate predicate =
                new ActueelEnIdentificerendMetaRecordPredicate(persoonslijst);
        final MetaRecord record = persoonslijst.getModelIndex()
                .geefGroepenVanElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS.getId())).iterator().next()
                .getRecords().iterator().next();
        assertFalse(predicate.test(record));
    }

}
