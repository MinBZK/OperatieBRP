/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EnumParser;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Enumeratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElement;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class ElementEnumReadonlyRepositoryTest {

    private final TestEnumRepository fouteRepository = new TestEnumRepository();
    private final ElementEnumReadonlyRepository<Element> repository = new ElementEnumReadonlyRepository<>(Element.class);

    @Test(expected = UnsupportedOperationException.class)
    public void testFilterEnumOpSoortMetVerkeerdeEnum() throws UnsupportedOperationException {
        fouteRepository.filterEnumOpSoort(SoortElement.OBJECTTYPE.getId());
    }

    @Test
    public void testFilterEnumOpSoort() throws UnsupportedOperationException {
        final List<Element> resultaat = repository.filterEnumOpSoort(SoortElement.ATTRIBUUT.getId());
        Assert.assertEquals("element moet van soort attribuut zijn", SoortElement.ATTRIBUUT, resultaat.get(0).getSoort());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFilterOpGroepEnAutorisatieMetVerkeerdeEnum() throws UnsupportedOperationException {
        fouteRepository.filterEnumOpGroepEnAutorisatie(1);
    }

    @Test
    public void testFilterEnumOpGroepEnAutorisatie() {
        final List<Element> resultaat = repository.filterEnumOpGroepEnAutorisatie(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS.getId());
        Assert.assertEquals(
            "Moet uit groep GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS komen",
            Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS,
            resultaat.get(0).getGroep());
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

    private static final class TestEnumRepository extends ElementEnumReadonlyRepository<TestEnum> {

        /**
         * Constructor.
         */
        TestEnumRepository() {
            super(TestEnum.class);
        }
    }
}
