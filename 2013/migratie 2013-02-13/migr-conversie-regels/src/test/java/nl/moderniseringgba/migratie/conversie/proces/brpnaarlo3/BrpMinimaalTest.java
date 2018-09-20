/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test om met minimale BRP persoonslijsten te converteren.
 */
public class BrpMinimaalTest extends AbstractComponentTest {

    @Inject
    private BrpConverteerder brpConverteerder;

    /**
     * De toevallig geboorte bevat alleen de gegevens voor categorieen 1, 2 en 3 (persoon, ouder 1 en ouder 2).
     */
    @Test
    public void testToevalligeGeboorte() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        builder.samengesteldeNaamStapel(maakKindSamengesteldeNaamStapel());
        builder.geboorteStapel(maakKindGeboorteStapel());
        builder.geslachtsaanduidingStapel(maakKindGeslachtsaaanduidingStapel());

        final BrpPersoonslijst brpPersoonslijst = builder.build();

        final Lo3Persoonslijst lo3Persoonslijst = brpConverteerder.converteer(brpPersoonslijst);
        Assert.assertNotNull(lo3Persoonslijst);
    }

    @SuppressWarnings("unchecked")
    private BrpStapel<BrpGeslachtsaanduidingInhoud> maakKindGeslachtsaaanduidingStapel() {
        return BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.geslacht("M"),
                BrpStapelHelper.his(20000101), BrpStapelHelper.act(1, 20000102)));
    }

    @SuppressWarnings("unchecked")
    private BrpStapel<BrpGeboorteInhoud> maakKindGeboorteStapel() {
        return BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.geboorte(19991231, "0518"),
                BrpStapelHelper.his(20000101), BrpStapelHelper.act(1, 20000102)));
    }

    @SuppressWarnings("unchecked")
    private BrpStapel<BrpSamengesteldeNaamInhoud> maakKindSamengesteldeNaamStapel() {
        return BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.samengesteldnaam("Jaap", "Jansen"),
                BrpStapelHelper.his(20000101), BrpStapelHelper.act(1, 20000102)));
    }
}
