/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.serialize;

import java.io.BufferedInputStream;
import java.io.InputStream;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Registry;
import org.simpleframework.xml.convert.RegistryStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

/**
 * Deze utility class bevat de functionaliteit om een LO3, Migratie en Brp Persoonslijst in XML formaat te
 * deserialiseren naar objecten.
 *
 */
public final class PersoonslijstDecoder {

    /*
     * Explicit private constructor.
     */
    private PersoonslijstDecoder() {
        throw new AssertionError("De class mag niet geinstantieerd worden.");
    }

    /**
     * Leest de inputstream en parsed deze naar objecten in het LO3 Model.
     *
     * @param in
     *            de inputstream
     * @return de LO3 persoonslijst
     */
    public static Lo3Persoonslijst decodeLo3Persoonslijst(final InputStream in) {
        return decode(Lo3Persoonslijst.class, in);
    }

    /**
     * Leest de inputstream en parsed deze naar objecten in het BRP model.
     *
     * @param in
     *            de inputstream
     * @return de BRP persoonslijst
     */
    public static BrpPersoonslijst decodeBrpPersoonslijst(final InputStream in) {
        return decode(BrpPersoonslijst.class, in);
    }

    /**
     * Leest de inputstream en parsed deze naar een object.
     *
     * @param resultClass
     *            the type van de persoonslijst
     * @param in
     *            de inputstream
     * @param <T>
     *            de type persoonslijst
     * @return de persoonslijst
     */
    @SuppressWarnings("checkstyle:illegalcatch")
    public static <T> T decode(final Class<T> resultClass, final InputStream in) {
        final Registry registry = new Registry();
        final Strategy strategy = new RegistryStrategy(registry);
        final Serializer serializer = new Persister(strategy);
        final BrpActieConverter converter = new BrpActieConverter(serializer);
        try {
            registry.bind(BrpActie.class, converter);
            return serializer.read(resultClass, new BufferedInputStream(in));
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
