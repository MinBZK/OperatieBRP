/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Assert;
import org.junit.Test;

public class EncodeDecodeTest {

    @Test
    public void testLo3() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        // TODO: Vul alle stapels
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());

        final Lo3Persoonslijst persoonslijst = builder.build();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PersoonslijstEncoder.encodePersoonslijst(persoonslijst, baos);

        final byte[] data = baos.toByteArray();
        System.out.println(new String(data));

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final Lo3Persoonslijst decodedPl = PersoonslijstDecoder.decodeLo3Persoonslijst(bais);

        final StringBuilder log = new StringBuilder();
        final boolean result = Lo3StapelHelper.vergelijkPersoonslijst(log, persoonslijst, decodedPl, true);
        System.out.println(log);

        Assert.assertTrue(result);

    }

    @Test
    public void testLo3MetLegeRij() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());

        // Nationaliteit met lege stapel
        builder.nationaliteitStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Nationaliteit("0050", null, null, null), Lo3StapelHelper.lo3His(19900101),
                Lo3StapelHelper.lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1)), Lo3StapelHelper
                .lo3Cat(Lo3StapelHelper.lo3Nationaliteit(null, null, null, null), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(2), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0))));

        final Lo3Persoonslijst persoonslijst = builder.build();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PersoonslijstEncoder.encodePersoonslijst(persoonslijst, baos);

        final byte[] data = baos.toByteArray();
        System.out.println(new String(data));

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final Lo3Persoonslijst decodedPl = PersoonslijstDecoder.decodeLo3Persoonslijst(bais);

        final StringBuilder log = new StringBuilder();
        final boolean result = Lo3StapelHelper.vergelijkPersoonslijst(log, persoonslijst, decodedPl, true);
        System.out.println(log);

        Assert.assertTrue(result);

    }

    @Test
    public void testBrp() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final BrpGroep<BrpVoornaamInhoud> voornaam =
                BrpStapelHelper.groep(BrpStapelHelper.voornaam("Jaap", 1), BrpStapelHelper.his(20000101),
                        BrpStapelHelper.act(1, 20000101));
        @SuppressWarnings("unchecked")
        final BrpStapel<BrpVoornaamInhoud> voornaamStapel = BrpStapelHelper.stapel(voornaam);
        builder.voornaamStapel(voornaamStapel);
        // TODO: Vul alle stapels
        final BrpPersoonslijst persoonslijst = builder.build();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PersoonslijstEncoder.encodePersoonslijst(persoonslijst, baos);

        final byte[] data = baos.toByteArray();
        System.out.println(new String(data));

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final BrpPersoonslijst decodedPl = PersoonslijstDecoder.decodeBrpPersoonslijst(bais);

        final StringBuilder log = new StringBuilder();
        final boolean result = BrpStapelHelper.vergelijkPersoonslijsten(log, persoonslijst, decodedPl, true);
        System.out.println(log);

        Assert.assertTrue(result);
    }
}
