/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import org.junit.Assert;
import org.junit.Test;

public class Lo3HistorieConversieVariantTest {

    private static final BrpGeslachtsaanduidingInhoud BRP_INHOUD = new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN);
    private static final Lo3Herkomst CAT01_HERKOMST = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0);

    private static final long DEFAULT_DATE = 20120102;
    private static final long DEFAULT_DATE_TIME = DEFAULT_DATE * 1000000 + 10000;
    private static final BrpDatumTijd TS_REG = BrpDatumTijd.fromDatumTijd(DEFAULT_DATE_TIME, null);
    private static final BrpDatumTijd EXPECTED_TS_REG = BrpDatumTijd.fromDatumTijd(DEFAULT_DATE_TIME + 100, null);
    private static final Lo3Datum DEFAULT_LO3_DATE = new Lo3Datum((int) DEFAULT_DATE);

    @Test
    public <T extends BrpGroepInhoud> void testDatumTijdRegistratieBepalenMaterieleHistorie() {
        final BrpDatum aanvangGeldigheid = new BrpDatum((int) DEFAULT_DATE, null);
        final BrpDatum aanvangGeldigheid2 = new BrpDatum(0, null);
        final List<BrpGroep<T>> brpGroepen = new ArrayList<>();

        BrpDatumTijd uniekeDatum = Lo3HistorieConversieVariant.updateDatumTijdRegistratie(aanvangGeldigheid, TS_REG, brpGroepen);

        Assert.assertEquals(TS_REG, uniekeDatum);

        brpGroepen.add((BrpGroep<T>) maakBrpGroep(aanvangGeldigheid, TS_REG));
        uniekeDatum = Lo3HistorieConversieVariant.updateDatumTijdRegistratie(aanvangGeldigheid, TS_REG, brpGroepen);
        Assert.assertEquals(EXPECTED_TS_REG, uniekeDatum);

        uniekeDatum = Lo3HistorieConversieVariant.updateDatumTijdRegistratie(aanvangGeldigheid2, TS_REG, brpGroepen);
        Assert.assertEquals(TS_REG, uniekeDatum);
    }

    @Test
    public <T extends BrpGroepInhoud> void testDatumTijdRegistratieBepalenFormeleHistorie() {
        final TussenGroep<T> lo3Groep = maakMigratieGroep();
        final List<BrpGroep<T>> brpGroepen = new ArrayList<>();
        BrpDatumTijd uniekeDatum = Lo3HistorieConversieVariant.bepaalDatumTijdRegistratie(lo3Groep, brpGroepen);

        Assert.assertEquals(TS_REG, uniekeDatum);

        brpGroepen.add((BrpGroep<T>) maakBrpGroep(null, TS_REG));
        uniekeDatum = Lo3HistorieConversieVariant.bepaalDatumTijdRegistratie(lo3Groep, brpGroepen);
        Assert.assertEquals(EXPECTED_TS_REG, uniekeDatum);
    }

    private <T extends BrpGroepInhoud> TussenGroep<T> maakMigratieGroep() {
        final Lo3Historie lo3Historie = new Lo3Historie(null, DEFAULT_LO3_DATE, DEFAULT_LO3_DATE);
        return new TussenGroep<>((T) BRP_INHOUD, lo3Historie, null, CAT01_HERKOMST);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> maakBrpGroep(final BrpDatum aanvangGeldigheid, final BrpDatumTijd datumTijdRegistratie) {
        final BrpHistorie historie = new BrpHistorie(aanvangGeldigheid, null, datumTijdRegistratie, null, null);

        final List<BrpActieBron> documentStapels = new ArrayList<>();
        final BrpActie actie =
                new BrpActie(
                    1L,
                    BrpSoortActieCode.CONVERSIE_GBA,
                    BrpPartijCode.MIGRATIEVOORZIENING,
                        datumTijdRegistratie,
                    null,
                    documentStapels,
                    0,
                    CAT01_HERKOMST);

        return new BrpGroep<>((T) BRP_INHOUD, historie, actie, null, null);
    }
}
