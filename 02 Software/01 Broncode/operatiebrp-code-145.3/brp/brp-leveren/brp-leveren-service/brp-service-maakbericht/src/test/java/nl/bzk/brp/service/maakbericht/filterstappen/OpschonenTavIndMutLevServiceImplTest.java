/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;


import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.AutorisatieAlles;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OpschonenTavIndMutLevServiceImplTest {

    @InjectMocks
    private KandidaatRecordBepaling kandidaatRecordBepaling;
    @Mock
    private MetaRecordFilterFactory metaRecordFilterFactory;

    private final OpschonenTavIndMutLevServiceImpl opschonenTavIndMutLevService = new OpschonenTavIndMutLevServiceImpl();

    @Before
    public void voorTest() {
        Mockito.when(metaRecordFilterFactory.maakRecordfilters(Mockito.any()))
                .thenReturn(new ConversieHistoriecorrectiePredicate());
    }

    /**
     * GerelateerdeOuder.Persoon
     */
    @Test
    public void allesGeautoriseerd() {
        //@formatter:off
        final MetaObject object = MetaObject.maakBuilder()
            .metId(1L)
            .metObjectElement(Element.PERSOON)
                .metGroep()
                    .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                    .metRecord()
                        .metId(2L)
                        .metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode())
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                    .metRecord()
                        .metId(3L)
                        .metAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId(), 31)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.PERSOON_KIND.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_KIND_IDENTITEIT.getId())
                            .metRecord()
                                .metId(4L)
                                .metAttribuut(Element.PERSOON_KIND_ROLCODE.getId(), SoortBetrokkenheid.KIND.getCode())
                                .metIndicatieTbvLeveringMutaties(false)
                            .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId())
                        .metGroep()
                            .metGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT.getId())
                                .metRecord()
                                    .metId(5L)
                                    .metAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_SOORTCODE.getId(), SoortRelatie.FAMILIERECHTELIJKE_BETREKKING.getCode())
                                .eindeRecord()
                        .eindeGroep()
                .metGroep()
                            .metGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD.getId())
                                .metRecord()
                                    .metId(6L)
                                    .metAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEPLAATSAANVANG.getId(), "BuitenlandsePlaatsAanvang")
                                .eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metObjectElement(Element.GERELATEERDEOUDER.getId())
                            .metGroep()
                                .metGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT.getId())
                                    .metRecord()
                                        .metId(7L)
                                        .metAttribuut(Element.OUDER_ROLCODE.getId(), SoortBetrokkenheid.OUDER.getCode())
                                        .metIndicatieTbvLeveringMutaties(false)
                                    .eindeRecord()
                            .eindeGroep()
                            .metObject()
                                .metObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId())
                                .metGroep()
                                    .metGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT.getId())
                                        .metRecord()
                                            .metId(8L)
                                            .metAttribuut(Element.GERELATEERDEOUDER_PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode())
                                        .eindeRecord()
                                .eindeGroep()
                                .metGroep()
                                    .metGroepElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE.getId())
                                        .metRecord()
                                            .metId(9L)
                                            .metAttribuut(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS.getId(), "buitenlandseplaats")
                                            .metIndicatieTbvLeveringMutaties(false)
                                        .eindeRecord()
                                .eindeGroep()
                            .eindeObject()
                        .eindeObject()
                    .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        ModelIndex index = new Persoonslijst(object, 0L).getModelIndex();
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        maakBerichtPersoonInformatie.setDatumAanvangmaterielePeriode(20160101);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, new Persoonslijst(object, 0L), maakBerichtPersoonInformatie, null, null);
        new AutorisatieAlles(berichtgegevens);

        //execute
        opschonenTavIndMutLevService.execute(berichtgegevens);

        //assert : alle objecten, groepen en records zijn geautoriseerd
        Assert.assertTrue(Sets.symmetricDifference(Sets.newHashSet(index.geefObjecten()), berichtgegevens.getGeautoriseerdeObjecten()).isEmpty());
        Assert.assertTrue(Sets.symmetricDifference(Sets.newHashSet(index.geefGroepen()), berichtgegevens.getGeautoriseerdeGroepen()).isEmpty());
        assertGeautoriseerdeRecords(berichtgegevens, Sets.newHashSet(2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L));
    }


    @Test
    public void deelsGeautoriseerd() {
        //@formatter:off
        final MetaObject object = MetaObject.maakBuilder()
            .metId(1L)
            .metObjectElement(Element.PERSOON)
                .metGroep()
                    .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                    .metRecord()
                        .metId(2L)
                        .metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode())
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                    .metRecord()
                        .metId(3L)
                        .metAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId(), 31)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.PERSOON_KIND.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_KIND_IDENTITEIT.getId())
                            .metRecord()
                                .metId(4L)
                                .metAttribuut(Element.PERSOON_KIND_ROLCODE.getId(), SoortBetrokkenheid.KIND.getCode())
                                .metIndicatieTbvLeveringMutaties(true)
                            .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId())
                        .metGroep()
                            .metGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT.getId())
                                .metRecord()
                                    .metId(5L)
                                    .metAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_SOORTCODE.getId(), SoortRelatie.FAMILIERECHTELIJKE_BETREKKING.getCode())
                                .eindeRecord()
                        .eindeGroep()
                        .metGroep()
                            .metGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD.getId())
                                .metRecord()
                                    .metId(6L)
                                    .metAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEPLAATSAANVANG.getId(), "BuitenlandsePlaatsAanvang")
                                .eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metObjectElement(Element.GERELATEERDEOUDER.getId())
                            .metGroep()
                                .metGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT.getId())
                                    .metRecord()
                                        .metId(7L)
                                        .metAttribuut(Element.OUDER_ROLCODE.getId(), SoortBetrokkenheid.OUDER.getCode())
                                        .metIndicatieTbvLeveringMutaties(true)
                                    .eindeRecord()
                            .eindeGroep()
                            .metObject()
                                .metObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId())
                                .metGroep()
                                    .metGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT.getId())
                                        .metRecord()
                                            .metId(8L)
                                            .metAttribuut(Element.GERELATEERDEOUDER_PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode())
                                        .eindeRecord()
                                .eindeGroep()
                                .metGroep()
                                    .metGroepElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE.getId())
                                        .metRecord()
                                            .metId(9L)
                                            .metAttribuut(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS.getId(), "buitenlandseplaats")
                                            .metIndicatieTbvLeveringMutaties(true)
                                        .eindeRecord()
                                .eindeGroep()
                            .eindeObject()
                        .eindeObject()
                    .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        Persoonslijst pl = new Persoonslijst(object, 0L);
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        maakBerichtPersoonInformatie.setDatumAanvangmaterielePeriode(20160101);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, pl,
              maakBerichtPersoonInformatie, null, null);
        new AutorisatieAlles(berichtgegevens);
        verwijderAutorisatieVoorNietKandidaatRecords(berichtgegevens);

        //execute
        opschonenTavIndMutLevService.execute(berichtgegevens);

        //assert
        assertGeautoriseerdeGroepen(berichtgegevens, Element.PERSOON_IDENTITEIT, Element.PERSOON_GEBOORTE, Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT,
                Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD);
        assertGeautoriseerdeObjecten(berichtgegevens, Element.PERSOON, Element.PERSOON_KIND, Element.FAMILIERECHTELIJKEBETREKKING);
        assertGeautoriseerdeRecords(berichtgegevens, Sets.newHashSet(2L, 3L, 5L, 6L));
    }


    private void assertGeautoriseerdeRecords(final Berichtgegevens berichtgegevens, Set<Long> recordIds) {
        Set<Long> geautoriseerdeRecords = new HashSet<>();
        for (MetaRecord record : berichtgegevens.getGeautoriseerdeRecords()) {
            geautoriseerdeRecords.add(record.getVoorkomensleutel());
        }
        Assert.assertTrue(Sets.symmetricDifference(geautoriseerdeRecords, recordIds).isEmpty());
    }

    private void assertGeautoriseerdeGroepen(final Berichtgegevens berichtgegevens, Element... groepElements) {
        ModelIndex index = berichtgegevens.getPersoonslijst().getModelIndex();
        Set<MetaGroep> geautoriseerdeGroepen = new HashSet<>();
        for (Element groepElement : groepElements) {
            geautoriseerdeGroepen.add(Iterables.getOnlyElement(index.geefGroepenVanElement(getGroepElement(groepElement))));
        }

        Assert.assertTrue(Sets.symmetricDifference(geautoriseerdeGroepen, berichtgegevens.getGeautoriseerdeGroepen()).isEmpty());
    }

    private void assertGeautoriseerdeObjecten(final Berichtgegevens berichtgegevens, Element... groepElements) {
        ModelIndex index = berichtgegevens.getPersoonslijst().getModelIndex();
        Set<MetaObject> geautoriseerdeObjecten = new HashSet<>();
        for (Element objectElement : groepElements) {
            geautoriseerdeObjecten.add(Iterables.getOnlyElement(index.geefObjecten(objectElement)));
        }

        Assert.assertTrue(Sets.symmetricDifference(geautoriseerdeObjecten, berichtgegevens.getGeautoriseerdeObjecten()).isEmpty());
    }

    private void verwijderAutorisatieVoorNietKandidaatRecords(final Berichtgegevens berichtgegevens) {
        kandidaatRecordBepaling.execute(berichtgegevens);
        berichtgegevens.getPersoonslijst().getMetaObject().accept(new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(MetaRecord record) {
                if (!berichtgegevens.getKandidaatRecords().contains(record)) {
                    berichtgegevens.verwijderAutorisatie(record);
                }
            }
        });
    }

}
