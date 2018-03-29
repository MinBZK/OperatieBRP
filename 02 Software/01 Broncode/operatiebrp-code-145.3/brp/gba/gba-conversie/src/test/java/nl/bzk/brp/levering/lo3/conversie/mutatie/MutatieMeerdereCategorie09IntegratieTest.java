/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.logMetaObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.levering.lo3.builder.MetaObjectAdder;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Assert;
import org.junit.Test;

/**
 * Kind.
 */
public class MutatieMeerdereCategorie09IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Inject
    private MutatieConverteerder subject;

    @Test
    public void test() {
        final AdministratieveHandeling administratieveHandeling = getBijhoudingsAdministratieveHandeling();
        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(basisAdministratieveHandeling);
        administratieveHandelingen.add(administratieveHandeling);

        final MetaObject.Builder persoonBuilder = maakBasisPersoonBuilder(idTeller.getAndIncrement());
        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(persoonBuilder.build());

        Actie lokaleActie = administratieveHandeling.getActies().iterator().next();

        final MetaRecord ouder1Record =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_OUDER.getId()))
                          .metId(1)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_OUDER_IDENTITEIT.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(lokaleActie)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OUDER_ROLCODE.getId()), "OUDER")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_OUDER_IDENTITEIT.getId()))
                          .getRecords()
                          .iterator()
                          .next();
        persoonAdder.voegPersoonMutatieToe(ouder1Record);

        final MetaRecord ouder2Record =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_OUDER.getId()))
                          .metId(2)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_OUDER_IDENTITEIT.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metActieInhoud(lokaleActie)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OUDER_ROLCODE.getId()), "OUDER")
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_OUDER_IDENTITEIT.getId()))
                          .getRecords()
                          .iterator()
                          .next();
        persoonAdder.voegPersoonMutatieToe(ouder2Record);

        MetaObject.Builder familierechterlijkeBetrekking1Builder =
                new MetaObject.Builder().metObjectElement(ElementHelper.getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                                        .metId(10)
                                        .metGroep()
                                        .metGroepElement(ElementHelper.getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT.getId()))
                                        .metRecord()
                                        .metId(idTeller.getAndIncrement())
                                        .metAttribuut(ElementHelper.getAttribuutElement(Element.FAMILIERECHTELIJKEBETREKKING_SOORTCODE.getId()), "F")
                                        .eindeRecord()
                                        .eindeGroep();

        MetaObject.Builder familierechterlijkeBetrekking2Builder =
                new MetaObject.Builder().metObjectElement(ElementHelper.getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                                        .metId(11)
                                        .metGroep()
                                        .metGroepElement(ElementHelper.getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT.getId()))
                                        .metRecord()
                                        .metId(idTeller.getAndIncrement())
                                        .metAttribuut(ElementHelper.getAttribuutElement(Element.FAMILIERECHTELIJKEBETREKKING_SOORTCODE.getId()), "F")
                                        .eindeRecord()
                                        .eindeGroep();

        final MetaRecord mutatieRecord1 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_OUDER.getId()))
                          .metId(1)
                          .metObject(familierechterlijkeBetrekking1Builder)
                          .build()
                          .getObjecten(ElementHelper.getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                          .iterator()
                          .next()
                          .getGroep(ElementHelper.getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT.getId()))
                          .getRecords()
                          .iterator()
                          .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord1);

        final MetaRecord mutatieRecord2 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_OUDER.getId()))
                          .metId(2)
                          .metObject(familierechterlijkeBetrekking2Builder)
                          .build()
                          .getObjecten(ElementHelper.getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                          .iterator()
                          .next()
                          .getGroep(ElementHelper.getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT.getId()))
                          .getRecords()
                          .iterator()
                          .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord2);

        final MetaObject.Builder kind1MetaObjectBuilder =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId()))
                          .metId(100)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_IDENTITEIT.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SOORTCODE.getId()), "O")
                          .eindeRecord()
                          .eindeGroep();

        voegGerelateerdeKindGeboorteToe(kind1MetaObjectBuilder, lokaleActie, 19400101, "0518", "6030");
        voegGerelateerdeKindIdentificatieNummersToe(kind1MetaObjectBuilder, lokaleActie, 19400101, "1231231234", "345345345");
        voegGerelateerdeKindSamengesteldeNaamToe(kind1MetaObjectBuilder, lokaleActie, 19400101, "Pimmetje", "van", " ", "Trommelen");

        MetaObject.Builder kind1GerelateerdeKindBuilder = new MetaObject.Builder();
        kind1GerelateerdeKindBuilder.metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND.getId()))
                                    .metId(20)
                                    .metObject(kind1MetaObjectBuilder)
                                    .metGroep()
                                    .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_IDENTITEIT.getId()))
                                    .metRecord()
                                    .metId(idTeller.getAndIncrement())
                                    .metActieInhoud(lokaleActie)
                                    .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_ROLCODE.getId()), "KIND")
                                    .eindeRecord()
                                    .eindeGroep();

        final MetaRecord mutatieRecord4 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                          .metId(10)
                          .metObject(kind1GerelateerdeKindBuilder)
                          .build()
                          .getObjecten(ElementHelper.getObjectElement(Element.GERELATEERDEKIND.getId()))
                          .iterator()
                          .next()
                          .getGroep(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_IDENTITEIT.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        persoonAdder.voegPersoonMutatieToe(mutatieRecord4);

        MetaObject.maakBuilder()
                  .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND.getId()))
                  .metId(20)
                  .metObject(kind1MetaObjectBuilder)
                  .build()
                  .getObjecten(ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId()))
                  .iterator()
                  .next()
                  .getGroepen()
                  .forEach(g -> g.getRecords().stream().forEach(persoonAdder::voegPersoonMutatieToe));

        final MetaObject.Builder kind2MetaObjectBuilder =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId()))
                          .metId(101)
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_IDENTITEIT.getId()))
                          .metRecord()
                          .metId(idTeller.getAndIncrement())
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SOORTCODE.getId()), "O")
                          .eindeRecord()
                          .eindeGroep();

        voegGerelateerdeKindGeboorteToe(kind2MetaObjectBuilder, lokaleActie, 19400101, "0518", "6030");
        voegGerelateerdeKindIdentificatieNummersToe(kind2MetaObjectBuilder, lokaleActie, 19400101, "1231231235L", "345345346");
        voegGerelateerdeKindSamengesteldeNaamToe(kind2MetaObjectBuilder, lokaleActie, 19400101, "Pommetje", "van", " ", "Trommelen");

        MetaObject.Builder kind2GerelateerdeKindBuilder = new MetaObject.Builder();
        kind2GerelateerdeKindBuilder.metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND.getId()))
                                    .metId(21)
                                    .metObject(kind2MetaObjectBuilder)
                                    .metGroep()
                                    .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_IDENTITEIT.getId()))
                                    .metRecord()
                                    .metId(idTeller.getAndIncrement())
                                    .metActieInhoud(lokaleActie)
                                    .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_ROLCODE.getId()), "KIND")
                                    .eindeRecord()
                                    .eindeGroep();

        final MetaRecord mutatieRecord5 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                          .metId(11)
                          .metObject(kind2GerelateerdeKindBuilder)
                          .build()
                          .getObjecten(ElementHelper.getObjectElement(Element.GERELATEERDEKIND.getId()))
                          .iterator()
                          .next()
                          .getGroep(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_IDENTITEIT.getId()))
                          .getRecords()
                          .iterator()
                          .next();
        persoonAdder.voegPersoonMutatieToe(mutatieRecord5);

        MetaObject.maakBuilder()
                  .metObjectElement(ElementHelper.getObjectElement(Element.GERELATEERDEKIND.getId()))
                  .metId(21)
                  .metObject(kind2MetaObjectBuilder)
                  .build()
                  .getObjecten(ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId()))
                  .iterator()
                  .next()
                  .getGroepen()
                  .forEach(g -> g.getRecords().stream().forEach(persoonAdder::voegPersoonMutatieToe));

        final Persoonslijst persoon = new Persoonslijst(persoonAdder.build(), 0L);

        logMetaObject(persoon.getMetaObject());
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, administratieveHandeling, null, new ConversieCache());

        Assert.assertEquals(4, resultaat.size());
    }

}
