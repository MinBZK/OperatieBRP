/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.serialize;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentiteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Registry;
import org.simpleframework.xml.convert.RegistryStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

/**
 * Algemeen XML encoding/decoding.
 */
public final class XmlEncoding {

    private XmlEncoding() {
        // Niet instantieerbaar
    }

    /**
     * Schrijft de XML representatie van een Lo3 autorisatielijst naar de meegegeven outputstream.
     *
     * @param autorisaties
     *            de Lo3Autorisaties
     * @param out
     *            de outputstream
     */
    public static void encodeLo3Autorisatie(final Lo3Autorisatie autorisaties, final OutputStream out) {
        XmlEncoding.encode(autorisaties, out);
    }

    /**
     * Schrijft de XML representatie van een Brp autorisatielijst naar de meegegeven outputstream.
     *
     * @param autorisatie
     *            de BrpAutorisatie
     * @param out
     *            de outputstream
     */
    public static void encodeBrpAutorisatie(final BrpAutorisatie autorisatie, final OutputStream out) {
        XmlEncoding.encode(autorisatie, out);
    }

    /**
     * Leest de inputstream en parsed deze naar objecten in het LO3 Model.
     *
     * @param in
     *            de inputstream
     * @return de LO3 autorisaties
     */
    public static Lo3Autorisatie decodeLo3Autorisatie(final InputStream in) {
        return XmlEncoding.decode(Lo3Autorisatie.class, in);
    }

    /**
     * Leest de inputstream en parsed deze naar objecten in het Brp Model.
     *
     * @param in
     *            de inputstream
     * @return de BRP autorisatie
     */
    public static BrpAutorisatie decodeBrpAutorisatie(final InputStream in) {
        return XmlEncoding.decode(BrpAutorisatie.class, in);
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
        try {
            XmlEncoding.getSerializer().write(object, new BufferedOutputStream(out));
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
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
        try {
            return XmlEncoding.getSerializer().read(resultClass, new BufferedInputStream(in));
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Serializer. NIET HERBRUIKBAAR! Single-use!!!!!!
     *
     * @return serializer
     */
    @SuppressWarnings("checkstyle:illegalcatch")
    private static Serializer getSerializer() {
        final Registry registry = new Registry();
        final Strategy strategy = new RegistryStrategy(registry);
        final Serializer serializer = new Persister(strategy);
        final BrpActieConverter actieConverter = new BrpActieConverter(serializer);
        final BrpIdentiteitInhoudConverter identiteitConverter = new BrpIdentiteitInhoudConverter();
        try {
            registry.bind(BrpActie.class, actieConverter);
            registry.bind(BrpIdentiteitInhoud.class, identiteitConverter);
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }

        return serializer;
    }
}
