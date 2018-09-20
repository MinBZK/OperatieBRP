/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.proces.AbstractLoggingTest;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;

import org.junit.Test;

/**
 * Test klasse voor Lo3HistorieConversieVariantLB23.
 */
public class Lo3HistorieConversieVariantLB23Test extends AbstractLoggingTest {

    private final Lo3HistorieConversieVariantLB23 conversie = new Lo3HistorieConversieVariantLB23();

    @SuppressWarnings("unchecked")
    @Test
    public <T extends BrpGroepInhoud> void testBijzondereSituatieLB018() {
        final MigratieGroep<BrpOuderlijkGezagInhoud> migratieGroep1 =
                new MigratieGroep<BrpOuderlijkGezagInhoud>(new BrpOuderlijkGezagInhoud(true),
                        Lo3StapelHelper.lo3His(19830205), Lo3StapelHelper.lo3Doc(1L, null, null, null),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_61, 0, 1));
        final MigratieGroep<BrpOuderlijkGezagInhoud> migratieGroep2 =
                new MigratieGroep<BrpOuderlijkGezagInhoud>(new BrpOuderlijkGezagInhoud(null),
                        Lo3StapelHelper.lo3His(19840204), Lo3StapelHelper.lo3Doc(2L, null, null, null),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0));

        final List<MigratieGroep<T>> lo3Groepen = new ArrayList<MigratieGroep<T>>();
        lo3Groepen.add((MigratieGroep<T>) migratieGroep1);
        lo3Groepen.add((MigratieGroep<T>) migratieGroep2);
        conversie.converteer(lo3Groepen);

        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB018);
        assertBijzondereSituatieOmschrijving("BrpOuderlijkGezagInhoud");
    }

    @SuppressWarnings("unchecked")
    @Test
    public <T extends BrpGroepInhoud> void testBijzondereSituatieLB018LegeHistorie() {
        final MigratieGroep<BrpOuderlijkGezagInhoud> migratieGroep1 =
                new MigratieGroep<BrpOuderlijkGezagInhoud>(new BrpOuderlijkGezagInhoud(true),
                        Lo3StapelHelper.lo3His(19830205), Lo3StapelHelper.lo3Doc(1L, null, null, null),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0));
        final MigratieGroep<BrpOuderlijkGezagInhoud> migratieGroep2 =
                new MigratieGroep<BrpOuderlijkGezagInhoud>(new BrpOuderlijkGezagInhoud(null),
                        Lo3StapelHelper.lo3His(19840204), Lo3StapelHelper.lo3Doc(2L, null, null, null),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_61, 0, 1));

        final List<MigratieGroep<T>> lo3Groepen = new ArrayList<MigratieGroep<T>>();
        lo3Groepen.add((MigratieGroep<T>) migratieGroep1);
        lo3Groepen.add((MigratieGroep<T>) migratieGroep2);
        conversie.converteer(lo3Groepen);

        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB018);
    }

    @SuppressWarnings("unchecked")
    @Test
    public <T extends BrpGroepInhoud> void testBijzondereSituatieLB018NietGelogd() {
        final MigratieGroep<BrpOuderlijkGezagInhoud> migratieGroep2 =
                new MigratieGroep<BrpOuderlijkGezagInhoud>(new BrpOuderlijkGezagInhoud(null),
                        Lo3StapelHelper.lo3His(19840204), Lo3StapelHelper.lo3Doc(2L, null, null, null),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0));

        final List<MigratieGroep<T>> lo3Groepen = new ArrayList<MigratieGroep<T>>();
        lo3Groepen.add((MigratieGroep<T>) migratieGroep2);
        conversie.converteer(lo3Groepen);

        assertAantalInfos(0);
    }

    @SuppressWarnings("unchecked")
    @Test
    public <T extends BrpGroepInhoud> void testBijzondereSituatieLB019() {
        final MigratieGroep<BrpGeslachtsaanduidingInhoud> migratieGroep1 =
                new MigratieGroep<BrpGeslachtsaanduidingInhoud>(new BrpGeslachtsaanduidingInhoud(null),
                        Lo3StapelHelper.lo3His(null, 20100101, 20100101), new Lo3Documentatie(3L, null, null, null,
                                null, null, null, null), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        final MigratieGroep<BrpGeslachtsaanduidingInhoud> migratieGroep2 =
                new MigratieGroep<BrpGeslachtsaanduidingInhoud>(new BrpGeslachtsaanduidingInhoud(null),
                        Lo3StapelHelper.lo3His(null, 20100101, 20100101), new Lo3Documentatie(4L, null, null, null,
                                null, null, null, null), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 1));
        final MigratieGroep<BrpGeslachtsaanduidingInhoud> migratieGroep3 =
                new MigratieGroep<BrpGeslachtsaanduidingInhoud>(new BrpGeslachtsaanduidingInhoud(null),
                        Lo3StapelHelper.lo3His("O", 20100102, 20100102), new Lo3Documentatie(1L, null, null, null,
                                null, null, null, null), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 2));
        final MigratieGroep<BrpGeslachtsaanduidingInhoud> migratieGroep4 =
                new MigratieGroep<BrpGeslachtsaanduidingInhoud>(new BrpGeslachtsaanduidingInhoud(
                        BrpGeslachtsaanduidingCode.ONBEKEND), Lo3StapelHelper.lo3His("O", 20100101, 20100101),
                        new Lo3Documentatie(2L, null, null, null, null, null, null, null), new Lo3Herkomst(
                                Lo3CategorieEnum.CATEGORIE_51, 0, 3));

        final List<MigratieGroep<T>> lo3Groepen = new ArrayList<MigratieGroep<T>>();
        lo3Groepen.add((MigratieGroep<T>) migratieGroep1);
        lo3Groepen.add((MigratieGroep<T>) migratieGroep2);
        lo3Groepen.add((MigratieGroep<T>) migratieGroep3);
        lo3Groepen.add((MigratieGroep<T>) migratieGroep4);
        conversie.converteer(lo3Groepen);

        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB019);
        assertBijzondereSituatieOmschrijving("BrpGeslachtsaanduidingInhoud");
    }
}
