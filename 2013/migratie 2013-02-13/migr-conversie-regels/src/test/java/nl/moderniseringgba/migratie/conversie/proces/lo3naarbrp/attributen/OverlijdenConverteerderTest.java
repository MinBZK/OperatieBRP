/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.testutils.ReflectionUtil;

import org.junit.Test;

/**
 * Test het contract van OverlijdenConverteerder.
 * 
 */
public final class OverlijdenConverteerderTest extends AbstractComponentTest {

    @Inject
    private OverlijdenConverteerder overlijdenConverteerder;

    @Test
    public void testConverteer() throws InputValidationException {

        final List<Lo3Categorie<Lo3OverlijdenInhoud>> overlijdenCategorieen =
                new ArrayList<Lo3Categorie<Lo3OverlijdenInhoud>>();

        overlijdenCategorieen.add(new Lo3Categorie<Lo3OverlijdenInhoud>(new Lo3OverlijdenInhoud(
                new Lo3Datum(20100101), new Lo3GemeenteCode("1234"), new Lo3LandCode("6030")), null, new Lo3Historie(
                Lo3IndicatieOnjuistEnum.ONJUIST.asElement(), new Lo3Datum(20100101), new Lo3Datum(20100101)), null));
        overlijdenCategorieen.add(new Lo3Categorie<Lo3OverlijdenInhoud>(new Lo3OverlijdenInhoud(null, null, null),
                null, new Lo3Historie(null, new Lo3Datum(20100101), new Lo3Datum(20100201)), null));

        final Lo3Stapel<Lo3OverlijdenInhoud> lo3OverlijdenStapel =
                new Lo3Stapel<Lo3OverlijdenInhoud>(overlijdenCategorieen);
        final MigratiePersoonslijstBuilder builder = new MigratiePersoonslijstBuilder();

        overlijdenConverteerder.converteer(lo3OverlijdenStapel, builder);

        @SuppressWarnings("unchecked")
        final MigratieStapel<BrpOverlijdenInhoud> overlijdenStapel =
                (MigratieStapel<BrpOverlijdenInhoud>) ReflectionUtil.getField(builder, "overlijdenStapel");

        assertNotNull(overlijdenStapel);
        assertEquals(2, overlijdenStapel.size());
        assertEquals(new BrpDatum(20100101), overlijdenStapel.get(0).getInhoud().getDatum());
    }

}
