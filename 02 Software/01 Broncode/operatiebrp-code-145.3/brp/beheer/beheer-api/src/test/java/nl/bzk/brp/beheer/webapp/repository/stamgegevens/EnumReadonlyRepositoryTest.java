/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.EnumParser;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Enumeratie;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class EnumReadonlyRepositoryTest {

    private final TestEnumRepository repository = new TestEnumRepository();

    @Test
    public void testFindOne() {
        Assert.assertEquals(null, repository.findOne(0));
        Assert.assertEquals(TestEnum.EEN, repository.findOne(1));
        Assert.assertEquals(TestEnum.TWEE, repository.findOne(2));
        Assert.assertEquals(TestEnum.DRIE, repository.findOne(3));
        Assert.assertEquals(null, repository.findOne(4));
    }

    @Test
    public void testFindAll() {
        check(pageable(0, 2), TestEnum.EEN, TestEnum.TWEE);
        check(pageable(1, 2), TestEnum.DRIE);
        check(pageable(2, 2));
    }

    /**
     * maak pageable.
     *
     * @param page
     *            zero-based page index
     * @param size
     *            page size
     * @return pageable
     */
    private Pageable pageable(final int page, final int size) {
        return new PageRequest(page, size);
    }

    private void check(final Pageable pageable, final TestEnum... expected) {
        final Page<TestEnum> actualPage = repository.findAll(null, pageable);
        Assert.assertEquals("Aantal elementen (op pagina) klopt niet", expected.length, actualPage.getNumberOfElements());
        for (int index = 0; index < expected.length; index++) {
            Assert.assertEquals(expected[index], actualPage.getContent().get(index));
        }
        Assert.assertEquals("Aantal elementen (in totaal) klopt niet", 3, actualPage.getTotalElements());
        Assert.assertEquals("Aantal paginas klopt niet", 2, actualPage.getTotalPages());
    }

    private enum TestEnum implements Enumeratie {
        EEN(1, "Een", "Waarde 1 van de enum."), TWEE(2, "Twee", "Waarde 2 van de enum."), DRIE(3, "Drie", "Waarde 3 van de enum.");

        private static final EnumParser<TestEnum> PARSER = new EnumParser<>(TestEnum.class);
        private final int id;
        private final String naam;
        private final String code;

        /**
         * Maak een nieuw EffectAfnemerindicaties object.
         *
         * @param id
         *            id
         * @param naam
         *            naam
         * @param code
         *            code
         */
        TestEnum(final int id, final String naam, final String code) {
            this.id = id;
            this.naam = naam;
            this.code = code;
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.algemeen.dal.domein.brp.kern.enums.Enumeratie#getId()
         */
        @Override
        public int getId() {
            return id;
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.algemeen.dal.domein.brp.kern.enums.Enumeratie#getCode()
         */
        @Override
        public String getCode() {
            return code;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean heeftCode() {
            return false;
        }

        /**
         * Geef de waarde van naam van EffectAfnemerindicaties.
         *
         * @return de waarde van naam van EffectAfnemerindicaties
         */
        @Override
        public String getNaam() {
            return naam;
        }
    }

    private static final class TestEnumRepository extends EnumReadonlyRepository<TestEnum> {

        /**
         * Constructor.
         */
        TestEnumRepository() {
            super(TestEnum.class);
        }
    }

}
