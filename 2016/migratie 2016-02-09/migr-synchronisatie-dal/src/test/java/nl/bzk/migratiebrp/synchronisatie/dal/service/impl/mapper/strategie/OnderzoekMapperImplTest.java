/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractDeltaTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.GegevenInOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StatusOnderzoek;

import org.junit.Assert;
import org.junit.Test;

public class OnderzoekMapperImplTest extends AbstractDeltaTest{
    @Test
    public void testOnderzoekMapper() {
        final Persoon persoon = maakPersoon(true);
        final OnderzoekMapper mapper = new OnderzoekMapperImpl(persoon);

        final DeltaEntiteit entiteit1 = new Persoon(SoortPersoon.INGESCHREVENE);
        final DeltaEntiteit entiteit2a = new Persoon(SoortPersoon.INGESCHREVENE);
        final DeltaEntiteit entiteit2b = new Persoon(SoortPersoon.INGESCHREVENE);
        final DeltaEntiteit entiteit3 = new Persoon(SoortPersoon.INGESCHREVENE);

        final Lo3Herkomst lo3Herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0);

        final Lo3Onderzoek lo3Onderzoek1 = new Lo3Onderzoek(Lo3Integer.wrap(10203), new Lo3Datum(20120101), null, lo3Herkomst);
        final Lo3Onderzoek lo3Onderzoek2 = new Lo3Onderzoek(Lo3Integer.wrap(40506), new Lo3Datum(20120101), new Lo3Datum(20120108), lo3Herkomst);

        mapper.mapOnderzoek(entiteit1, new BrpString("test1a", lo3Onderzoek1), Element.PERSOON_OVERLIJDEN);
        mapper.mapOnderzoek(entiteit1, new BrpString("test1b", lo3Onderzoek1), Element.PERSOON_OVERLIJDEN_DATUM);
        mapper.mapOnderzoek(entiteit2a, new BrpString("test2a", lo3Onderzoek2), Element.PERSOON_NAAMGEBRUIK);
        mapper.mapOnderzoek(entiteit2b, new BrpString("test2b", lo3Onderzoek2), Element.PERSOON_SAMENGESTELDENAAM);
        mapper.mapOnderzoek(entiteit3, new BrpString("test3"), Element.PERSOON_GEBOORTE);

        final Set<Onderzoek> onderzoeken = mapper.getOnderzoekSet();
        assertEquals(2, onderzoeken.size());

        final Onderzoek brpOnderzoek1 = zoekLopendOnderzoek(onderzoeken);
        final Onderzoek brpOnderzoek2 = zoekBeeindigdOnderzoek(onderzoeken);

        assertNotNull(brpOnderzoek1);
        assertNotNull(brpOnderzoek1.getGegevenInOnderzoekSet());
        assertEquals(2, brpOnderzoek1.getGegevenInOnderzoekSet().size());
        for (final GegevenInOnderzoek gegevenInOnderzoek : brpOnderzoek1.getGegevenInOnderzoekSet()) {
            if (Element.PERSOON_OVERLIJDEN.equals(gegevenInOnderzoek.getSoortGegeven())) {
                Assert.assertSame(entiteit1, gegevenInOnderzoek.getObject());
            } else if (Element.PERSOON_OVERLIJDEN_DATUM.equals(gegevenInOnderzoek.getSoortGegeven())) {
                Assert.assertSame(entiteit1, gegevenInOnderzoek.getObject());
            } else {
                Assert.fail();
            }
        }

        assertNotNull(brpOnderzoek2);
        assertNotNull(brpOnderzoek2.getGegevenInOnderzoekSet());
        assertEquals(2, brpOnderzoek2.getGegevenInOnderzoekSet().size());
        for (final GegevenInOnderzoek gegevenInOnderzoek : brpOnderzoek2.getGegevenInOnderzoekSet()) {
            if (Element.PERSOON_NAAMGEBRUIK.equals(gegevenInOnderzoek.getSoortGegeven())) {
                Assert.assertSame(entiteit2a, gegevenInOnderzoek.getObject());
            } else if (Element.PERSOON_SAMENGESTELDENAAM.equals(gegevenInOnderzoek.getSoortGegeven())) {
                Assert.assertSame(entiteit2b, gegevenInOnderzoek.getObject());
            } else {
                Assert.fail();
            }
        }
    }

    private Onderzoek zoekLopendOnderzoek(final Set<Onderzoek> onderzoeken) {
        for (final Onderzoek onderzoek : onderzoeken) {
            if (onderzoek.getStatusOnderzoek().equals(StatusOnderzoek.IN_UITVOERING)) {
                return onderzoek;
            }
        }
        return null;
    }

    private Onderzoek zoekBeeindigdOnderzoek(final Set<Onderzoek> onderzoeken) {
        for (final Onderzoek onderzoek : onderzoeken) {
            if (onderzoek.getStatusOnderzoek().equals(StatusOnderzoek.AFGESLOTEN)) {
                return onderzoek;
            }
        }
        return null;
    }
}
