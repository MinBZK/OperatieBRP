/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.serialize;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Registry;
import org.simpleframework.xml.convert.RegistryStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

public class SimpelXMLTest {

    @Test
    public void testWithValues() {
        test(new TestObject("A", "B", "C"));
    }

    @Test
    public void testWithoutValues() {
        test(new TestObject(null, null, null));
    }

    private void test(final Object object) {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final Registry registry = new Registry();
        final Strategy strategy = new RegistryStrategy(registry);
        final Serializer serializer = new Persister(strategy);
        try {
            serializer.write(object, new BufferedOutputStream(baos));
        } catch (final Exception e) {
            Assert.fail("Serializer write faalde.");
        }

        final byte[] data = baos.toByteArray();
        final ByteArrayInputStream bais = new ByteArrayInputStream(data);

        try {
            final Object result = serializer.read(object.getClass(), new BufferedInputStream(bais));
            Assert.assertEquals(object, result);
        } catch (final Exception e) {
            Assert.fail("Serializer read faalde.");
        }

    }

    @Test
    public void testMetLo3InhoudGevuld() {
        test(Lo3StapelHelper.lo3Nationaliteit("0050", null, null, null));
    }

    @Test
    public void testMetLo3InhoudLeeeg() {
        test(Lo3StapelHelper.lo3Nationaliteit(null, null, null, null));
    }

    @Test
    public void testMetLo3Categorie() {
        test(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Nationaliteit(null, null, null, null),
            Lo3StapelHelper.lo3Akt(2),
            Lo3StapelHelper.lo3His(20000101),
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0)));
    }

    public static final class TestObject {
        @Element(name = "attributeA", required = false)
        private final String attributeA;

        @Element(name = "attributeB", required = false)
        private final String attributeB;

        @Element(name = "attributeC", required = false)
        private final String attributeC;

        /**
         * No-arg constructor is nodig voor de-serialisatie test.
         */
        public TestObject() {
            this(null, null, null);
        }

        public TestObject(
            @Element(name = "attributeA", required = false) final String attributeA,
            @Element(name = "attributeB", required = false) final String attributeB,
            @Element(name = "attributeC", required = false) final String attributeC)
        {
            super();
            this.attributeA = attributeA;
            this.attributeB = attributeB;
            this.attributeC = attributeC;
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TestObject)) {
                return false;
            }
            final TestObject castOther = (TestObject) other;
            return new EqualsBuilder().append(attributeA, castOther.attributeA)
                                      .append(attributeB, castOther.attributeB)
                                      .append(attributeC, castOther.attributeC)
                                      .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(attributeA).append(attributeB).append(attributeC).toHashCode();
        }
    }
}
