/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.serialize;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

import nl.bzk.migratiebrp.conversie.model.Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentiteitInhoud;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Registry;
import org.simpleframework.xml.convert.RegistryStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

/**
 * Deze utility class bevat de functionaliteit om een LO3, Migratie en BRP Persoonslijst object te serialiseren naar
 * XML.
 *
 */
public final class PersoonslijstEncoder {

    /*
     * Explicit private constructor.
     */
    private PersoonslijstEncoder() {
        throw new AssertionError("De class mag niet geinstantieerd worden.");
    }

    /**
     * Schrijft de XML representatie van een persoonslijst naar de meegegeven outputstream.
     *
     * @param persoonslijst
     *            de persoonlijst
     * @param out
     *            de outputstream
     */
    public static void encodePersoonslijst(final Persoonslijst persoonslijst, final OutputStream out) {
        PersoonslijstEncoder.encode(persoonslijst, out);
    }

    /**
     * Schrijf een object naar de stream.
     *
     * @param object
     *            object
     * @param out
     *            outputstream
     */
    @SuppressWarnings("checkstyle:illegalcatch")
    public static void encode(final Object object, final OutputStream out) {
        final Registry registry = new Registry();
        final Strategy strategy = new RegistryStrategy(registry);
        final Serializer serializer = new Persister(strategy);
        final BrpActieConverter actieConverter = new BrpActieConverter(serializer);
        final BrpIdentiteitInhoudConverter identiteitConverter = new BrpIdentiteitInhoudConverter();
        try {
            registry.bind(BrpActie.class, actieConverter);
            registry.bind(BrpIdentiteitInhoud.class, identiteitConverter);
            serializer.write(object, new BufferedOutputStream(out));
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
