/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Iterables;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import org.junit.Test;

public class PeilmomentHistorievormPredicateTest {

    private final Integer materieelTotEnMetMoment = 20170101;
    private final ZonedDateTime formeelPeilmoment = ZonedDateTime.of(2017, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());
    private final Persoonslijst pl = maakPersoonslijst();


    @Test
    public void testRecordUitGroepMetHistoriePatroonGEEN() {
        MetaRecord record = geefRecordUitGroep(Element.PERSOON_ADRES_IDENTITEIT);
        final PeilmomentHistorievormPredicate predicate =
                new PeilmomentHistorievormPredicate(materieelTotEnMetMoment, formeelPeilmoment, HistorieVorm.GEEN);

        assertTrue(predicate.apply(record));
    }

    @Test
    public void testRecordUitGroep_MetFormeleHistorie_FormeelGeldig() {
        final MetaRecord record = geefRecordUitGroep(Element.PERSOON_GEBOORTE);
        final PeilmomentHistorievormPredicate predicate =
                new PeilmomentHistorievormPredicate(materieelTotEnMetMoment, formeelPeilmoment, HistorieVorm.MATERIEEL_FORMEEL);

        assertTrue(predicate.apply(record));
    }

    @Test
    public void testRecordUitGroep_MetFormeleHistorie_MetVerantwoordingcategorieD_formeelGeldig() {
        final MetaRecord record = geefRecordUitGroep(Element.PERSOON_AFNEMERINDICATIE_STANDAARD);
        final PeilmomentHistorievormPredicate predicate =
                new PeilmomentHistorievormPredicate(materieelTotEnMetMoment, formeelPeilmoment, HistorieVorm.MATERIEEL_FORMEEL);

        assertTrue(predicate.apply(record));
    }

    @Test
    public void testRecordUitGroep_MetMaterieelFormeleHistorie_MaterieelGeldig_FormeelGeldig() {
        final MetaRecord record = geefRecordUitGroep(Element.PERSOON_SAMENGESTELDENAAM);
        final PeilmomentHistorievormPredicate predicate =
                new PeilmomentHistorievormPredicate(materieelTotEnMetMoment, formeelPeilmoment, HistorieVorm.MATERIEEL_FORMEEL);

        assertTrue(predicate.apply(record));
    }

    @Test
    public void testRecordUitGroep_MetMaterieelFormeleHistorie_MaterieelOngeldig() {
        final MetaRecord record = geefRecordUitGroep(Element.PERSOON_IDENTIFICATIENUMMERS);
        final PeilmomentHistorievormPredicate predicate =
                new PeilmomentHistorievormPredicate(materieelTotEnMetMoment, formeelPeilmoment, HistorieVorm.MATERIEEL_FORMEEL);

        assertFalse(predicate.apply(record));
    }

    private MetaRecord geefRecordUitGroep(final Element element) {
        final Set<MetaGroep> groepSet = pl.getModelIndex().geefGroepenVanElement(ElementHelper.getGroepElement(element));
        return Iterables.getOnlyElement(Iterables.getOnlyElement(groepSet).getRecords());
    }

    private Persoonslijst maakPersoonslijst() {
        final Actie actieTsRegVoorFormeelPM = TestVerantwoording.maakActie(1, formeelPeilmoment.minusYears(1));
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon()
                //groep met formele historie met tsreg
                .metGroep()
                    .metGroepElement(Element.PERSOON_GEBOORTE)
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieTsRegVoorFormeelPM)
                    .eindeRecord()
                .eindeGroep()

                //groep met materiele historie met dateinde
                .metGroep()
                    .metGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS)
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieTsRegVoorFormeelPM)
                        .metDatumAanvangGeldigheid(20210101)
                    .eindeRecord()
                .eindeGroep()

                //groep met materiele historie met tsreg
                .metGroep()
                    .metGroepElement(Element.PERSOON_SAMENGESTELDENAAM)
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieTsRegVoorFormeelPM)
                    .eindeRecord()
                .eindeGroep()

                //groep met historievorm = GEEN
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES)
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_IDENTITEIT)
                        .metRecord()
                            .metId(2)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()

                //groep met formele historie, verantwoordingscategorie='D'
                .metObject()
                    .metObjectElement(Element.PERSOON_AFNEMERINDICATIE)
                    .metGroep()
                        .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT.getId())
                        .metRecord()
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE.getId(), "111")
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE.getId(), 111)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PERSOON.getId(), 1L)
                        .eindeRecord()
                    .eindeGroep()
                    .metGroep()
                        .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId())
                        .metRecord()
                            .metId(3)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD.getId(), 1)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId(), formeelPeilmoment.minusYears(1))
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
           .eindeObject()
           .build();
        //@formatter:on
        return new Persoonslijst(metaObject, 1L);
    }
}
