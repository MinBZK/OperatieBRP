/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.curatele;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.derde;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.gezag1;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.gezag2;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.groep;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.stapel;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3Akt;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3Cat;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3Gezag;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3His;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3Stapel;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.groep.BrpOuder1GezagInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.groep.BrpOuder2GezagInhoud;

import org.junit.Test;

public class BrpGezagsverhoudingConverteerderTest extends AbstractComponentTest {

    private static final Lo3Herkomst LO3_HERKOMST_GEZAGSVERHOUDING = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11,
            0, 0);
    @Inject
    private BrpGezagsverhoudingConverteerder subject;

    @Test
    @SuppressWarnings("unchecked")
    public void test() {
        // Input
        //@formatter:off
        // groep(inhoud, historie, inhoud, verval, geldig)
        // his(aanvang, einde, registratie, verval)
        // act(id, registratie)
        final BrpStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleStapel = stapel(
                groep(curatele(true), his(20040101), act(7, 20040102)));
            
        final BrpStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagStapel = stapel(
                groep(derde(true), his(19940101, null, 19940102, 20000102), act(2, 19940102), act(5, 20000102), null),
                groep(derde(true), his(19940101, 20000101, 20000102, null), act(2, 19940102), null, act(5, 20000102)));
        
        final BrpStapel<BrpOuder1GezagInhoud> ouder1GezagStapel = stapel(
                groep(gezag1(true), his(19920101, null, 19920102, 19960102), act(1, 19920102), act(3, 19960102), null),
                groep(gezag1(true), his(19920101, 19960101, 19960102, null), act(1, 19920102), null, act(3, 19960102)),
                groep(gezag1(true), his(20020101, null, 20020102, 20040102), act(6, 20020102), act(7, 20040102), null),
                groep(gezag1(true), his(20020101, 20040101, 20040102, null), act(6, 20020102), null, act(7, 20040102)));
        
        final BrpStapel<BrpOuder2GezagInhoud> ouder2GezagStapel = stapel(
                groep(gezag2(true), his(19980101, null, 19980102, 20040102), act(4, 19980102), act(7, 20040102), null),
                groep(gezag2(true), his(19980101, 20040101, 20040102, null), act(4, 19980102), null, act(7, 20040102)));
        //@formatter:on

        // Execute
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> result =
                subject.converteer(onderCurateleStapel, derdeHeeftGezagStapel, ouder1GezagStapel, ouder2GezagStapel);

        // Expectation
        //@formatter:off
        // cat(inhoud, historie, documentatie)
        // his(ingangsdatumGeldigheid)
        // akt(id)
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> expected = lo3Stapel(
                lo3Cat(lo3Gezag("1",  null), lo3His(19920101), lo3Akt(1), LO3_HERKOMST_GEZAGSVERHOUDING),
                lo3Cat(lo3Gezag("1D", null), lo3His(19940101), lo3Akt(2), LO3_HERKOMST_GEZAGSVERHOUDING),
                lo3Cat(lo3Gezag("D",  null), lo3His(19960101), lo3Akt(3), LO3_HERKOMST_GEZAGSVERHOUDING),
                lo3Cat(lo3Gezag("2D", null), lo3His(19980101), lo3Akt(4), LO3_HERKOMST_GEZAGSVERHOUDING),
                lo3Cat(lo3Gezag("2",  null), lo3His(20000101), lo3Akt(5), LO3_HERKOMST_GEZAGSVERHOUDING),
                lo3Cat(lo3Gezag("12", null), lo3His(20020101), lo3Akt(6), LO3_HERKOMST_GEZAGSVERHOUDING),
                lo3Cat(lo3Gezag(null, true), lo3His(20040101), lo3Akt(7), LO3_HERKOMST_GEZAGSVERHOUDING)
                );
        
        //@formatter:on
        Lo3StapelHelper.vergelijk(expected, result);
    }
}
