/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;
import javax.inject.Inject;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Naamgebruik;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNaamgebruikHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Predicaat;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import org.junit.Test;

public class BrpNaamgebruikTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    @Inject
    private BrpNaamgebruikMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonNaamgebruikHistorie historie =
                new PersoonNaamgebruikHistorie(
                    new Persoon(SoortPersoon.INGESCHREVENE),
                    "Janssen-van der Dingenmans",
                    Boolean.TRUE,
                    Naamgebruik.EIGEN);
        historie.setPredicaat(Predicaat.K);
        // historie.setAdellijkeTitel
        historie.setVoornamenNaamgebruik("Voor nam en");
        historie.setVoorvoegselNaamgebruik("van der den a la");
        historie.setScheidingstekenNaamgebruik('x');

        final BrpNaamgebruikInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(BrpNaamgebruikCode.E, result.getNaamgebruikCode());
        Assert.assertEquals(new BrpBoolean(Boolean.TRUE, null), result.getIndicatieAfgeleid());
        Assert.assertEquals(new BrpPredicaatCode("K"), result.getPredicaatCode());
        // Assert adellijke titel
        Assert.assertEquals(new BrpString("Voor nam en", null), result.getVoornamen());
        Assert.assertEquals(new BrpString("van der den a la", null), result.getVoorvoegsel());
        Assert.assertEquals(new BrpCharacter('x', null), result.getScheidingsteken());
        Assert.assertEquals(new BrpString("Janssen-van der Dingenmans", null), result.getGeslachtsnaamstam());
    }
}
