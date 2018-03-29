/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import com.google.common.collect.Iterables;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class WisOverigeMutLevRecordPredicateTest {

    private ZonedDateTime zdt = ZonedDateTime.now();
    private Actie actieVandaag = TestVerantwoording.maakActie(1, zdt);
    private Actie actieGisteren = TestVerantwoording.maakActie(2, zdt.minusDays(1L));
    private Actie actieMorgen = TestVerantwoording.maakActie(3, zdt.plusDays(1L));


    final WisOverigeMutLevRecordPredicate predicate = new WisOverigeMutLevRecordPredicate(Collections.emptySet());

    @Test
    public void test_behoudRecordMetLaatsteTsReg() {

        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
                .metObject()
                .metObjectElement(Element.PERSOON_ADRES)
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD)
                            .metRecord()
                                .metId(1)
                                .metIndicatieTbvLeveringMutaties(true)
                                .metActieInhoud(actieGisteren)
                                .metDatumAanvangGeldigheid(
                                        DatumUtil.vanDatumNaarInteger(DatumUtil.vanZonedDateTimeNaarLocalDateNederland(actieGisteren.getTijdstipRegistratie())))
                            .eindeRecord()
                            .metRecord()
                                .metId(2)
                                .metIndicatieTbvLeveringMutaties(true)
                                .metActieInhoud(actieVandaag)
                            .eindeRecord()
                            .metRecord()
                                .metId(3)
                                .metIndicatieTbvLeveringMutaties(false)
                                .metDatumAanvangGeldigheid(
                                        DatumUtil.vanDatumNaarInteger(DatumUtil.vanZonedDateTimeNaarLocalDateNederland(actieGisteren.getTijdstipRegistratie())))
                            .eindeRecord()
                            .metRecord()
                                .metId(4)
                                .metIndicatieTbvLeveringMutaties(false)
                                .metDatumAanvangGeldigheid(
                                        DatumUtil.vanDatumNaarInteger(DatumUtil.vanZonedDateTimeNaarLocalDateNederland(actieMorgen.getTijdstipRegistratie())))
                            .eindeRecord()
                    .eindeGroep()
                .eindeObject()
                .build();
        //@formatter:on

        final Persoonslijst persoonsLijst = new Persoonslijst(persoon, 1L);

        //indmutlev = true, maar niet record laatste tsreg
        Assert.assertFalse(predicate.test(geefRecordMetId(persoonsLijst, 1, Element.PERSOON_ADRES_STANDAARD)));

        //indmutlev = false + er bestaat record met indmutlev met gelijke dat aanv geldigheid
        Assert.assertFalse(predicate.test(geefRecordMetId(persoonsLijst, 3, Element.PERSOON_ADRES_STANDAARD)));

        //ind.mut.lev = true + is record met laatste tsreg
        Assert.assertTrue(predicate.test(geefRecordMetId(persoonsLijst, 2, Element.PERSOON_ADRES_STANDAARD)));

        //indmutlev = false, maar geen record met indlmutlev met gelijke dataanv geldigheid
        Assert.assertTrue(predicate.test(geefRecordMetId(persoonsLijst, 4, Element.PERSOON_ADRES_STANDAARD)));

    }

    @Test
    public void behoudVoorkomenMetLaatsteDatumAanvangGeldigheid() {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metGroep()
                .metGroepElement(Element.PERSOON_BIJHOUDING)
                    .metRecord()
                        .metId(1)
                        .metIndicatieTbvLeveringMutaties(true)
                        .metActieInhoud(actieGisteren)
                        .metDatumAanvangGeldigheid(20000101)
                    .eindeRecord()
                    .metRecord()
                        .metId(2)
                        .metIndicatieTbvLeveringMutaties(false)
                        .metActieInhoud(actieVandaag)
                        .metDatumAanvangGeldigheid(20100101)
                    .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        final Persoonslijst persoonsLijst = new Persoonslijst(persoon, 1L);

        //wordt weggefilterd, want metIndicatieTbvLeveringMutaties=true en er bestaat een
        //voorkomen met latere datumaanvangGeldigheid
        Assert.assertFalse(predicate.test(geefRecordMetId(persoonsLijst, 1, Element.PERSOON_BIJHOUDING)));

        //behoud
        Assert.assertTrue(predicate.test(geefRecordMetId(persoonsLijst, 2, Element.PERSOON_BIJHOUDING)));

    }

    private MetaRecord geefRecordMetId(final Persoonslijst persoonslijst, final int id, final Element element) {
        Set<MetaRecord> metaRecords =
                Iterables.getOnlyElement(
                        persoonslijst.getModelIndex().geefGroepenVanElement(
                                ElementHelper.getGroepElement(element))).getRecords();
        return metaRecords.stream().filter(record -> record.getVoorkomensleutel() == id).findFirst().orElseThrow(IllegalStateException::new);
    }
}
