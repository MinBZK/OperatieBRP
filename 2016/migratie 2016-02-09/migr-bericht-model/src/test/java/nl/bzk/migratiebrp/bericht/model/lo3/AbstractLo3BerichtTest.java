/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ib01Bericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Assert;

public class AbstractLo3BerichtTest {

    private final Lo3BerichtFactory factory = new Lo3BerichtFactory();

    /**
     * Geef de waarde van factory.
     *
     * @return factory
     */
    protected Lo3BerichtFactory getFactory() {
        return factory;
    }

    protected void testFormatAndParseBericht(final Lo3Bericht bericht) throws BerichtInhoudException, IOException, ClassNotFoundException {
        final String formatted = bericht.format();
        Assert.assertNotNull(formatted);

        final Lo3Bericht parsed = factory.getBericht(formatted);
        Assert.assertNotNull(parsed);

        parsed.setMessageId(bericht.getMessageId());
        parsed.setCorrelationId(bericht.getCorrelationId());
        parsed.setBronGemeente(bericht.getBronGemeente());
        parsed.setDoelGemeente(bericht.getDoelGemeente());

        if (bericht instanceof Ib01Bericht) {
            final StringBuilder sb = new StringBuilder();
            Lo3StapelHelper.vergelijkPersoonslijst(sb, ((Ib01Bericht) bericht).getLo3Persoonslijst(), ((Ib01Bericht) parsed).getLo3Persoonslijst());
        }

        Assert.assertEquals(bericht, parsed);

        // TODO: aparte test

        // Serialize
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(parsed);

        final byte[] data = baos.toByteArray();

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final ObjectInputStream ois = new ObjectInputStream(bais);

        final Object deserialized = ois.readObject();
        Assert.assertEquals(parsed, deserialized);

        Assert.assertEquals(parsed.getMessageId(), ((Lo3Bericht) deserialized).getMessageId());
    }

    protected Lo3Persoonslijst maakPersoonslijst(final Long aNummerInhoud, final Long vorigANummerInhoud, final String gemeenteInhoud) {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(aNummerInhoud,
                                                                                                          null,
                                                                                                          "Jan",
                                                                                                          null,
                                                                                                          null,
                                                                                                          "Jansen",
                                                                                                          19700101,
                                                                                                          "0518",
                                                                                                          "6030",
                                                                                                          "M",
                                                                                                          vorigANummerInhoud,
                                                                                                          null,
                                                                                                          "E"),
                                                                               Lo3StapelHelper.lo3Akt(1),
                                                                               Lo3StapelHelper.lo3His(19700101),
                                                                               new Lo3Herkomst(Lo3CategorieEnum.PERSOON, 0, 0))));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats(gemeenteInhoud,
                                                                                                                        19700101,
                                                                                                                        19700101,
                                                                                                                        "Straat",
                                                                                                                        15,
                                                                                                                        "9876AA",
                                                                                                                        "I"),
                                                                                      null,
                                                                                      Lo3StapelHelper.lo3His(19700101),
                                                                                      new Lo3Herkomst(Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0))));
        // @formatter:on

        return builder.build();
    }

}
