/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.verwerker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaVergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DeltaRootEntiteitModus;

/**
 * Test het verwerken van verschillen op een entiteit.
 */
public class AbstractDeltaVerschilVerwerkerTest {

    private TestData testData;

    @Before
    public void setup() {
        this.testData = new TestData();
    }

    @Test
    public void testToevoegenStapels() {
        // test data
        final EntiteitSleutel persoonNationaliteit1Sleutel = new EntiteitSleutel(Persoon.class, "persoonNationaliteitSet", null);
        persoonNationaliteit1Sleutel.addSleuteldeel("nation", testData.persoonNationaliteit1.getNationaliteit().getId());
        final EntiteitSleutel persoonNationaliteit2Sleutel = new EntiteitSleutel(Persoon.class, "persoonNationaliteitSet", null);
        persoonNationaliteit1Sleutel.addSleuteldeel("nation", testData.persoonNationaliteit2.getNationaliteit().getId());

        final Verschil verschil1 =
                new Verschil(persoonNationaliteit1Sleutel, null, testData.persoonNationaliteit1, VerschilType.RIJ_TOEGEVOEGD, null, null);
        final Verschil verschil2 =
                new Verschil(persoonNationaliteit2Sleutel, null, testData.persoonNationaliteit2, VerschilType.RIJ_TOEGEVOEGD, null, null);

        // execute
        testData.verwerker.verwerkWijzigingen(vergelijkerResultaat(verschil1, verschil2), testData.persoon, testData.administratieveHandeling);

        // verify
        assertEquals(testData.persoonNationaliteitSet, testData.persoon.getPersoonNationaliteitSet());
    }

    @Test
    public void testVerwijderStapels() {
        // test data
        final EntiteitSleutel persoonNationaliteit1Sleutel = new EntiteitSleutel(Persoon.class, "persoonNationaliteitSet", null);
        persoonNationaliteit1Sleutel.addSleuteldeel("nation", testData.persoonNationaliteit1.getNationaliteit().getId());
        final EntiteitSleutel persoonNationaliteit2Sleutel = new EntiteitSleutel(Persoon.class, "persoonNationaliteitSet", null);
        persoonNationaliteit1Sleutel.addSleuteldeel("nation", testData.persoonNationaliteit2.getNationaliteit().getId());

        final Verschil verschil1 =
                new Verschil(persoonNationaliteit1Sleutel, testData.persoonNationaliteit1, null, VerschilType.RIJ_VERWIJDERD, null, null);
        final Verschil verschil2 =
                new Verschil(persoonNationaliteit2Sleutel, testData.persoonNationaliteit2, null, VerschilType.RIJ_VERWIJDERD, null, null);

        // execute
        testData.verwerker.verwerkWijzigingen(vergelijkerResultaat(verschil1, verschil2), testData.persoon, testData.administratieveHandeling);

        // verify
        assertTrue(testData.persoon.getPersoonNationaliteitSet().isEmpty());
    }

    private DeltaVergelijkerResultaat vergelijkerResultaat(final Verschil... verschillen) {
        final DeltaVergelijkerResultaat resultaat = new DeltaVergelijkerResultaat();
        resultaat.voegVerschillenToe(Arrays.asList(verschillen));
        return resultaat;
    }

    private final static class TestData {
        private final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(new Partij("partij", "000000"),
                        SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL,
                        new Timestamp(System.currentTimeMillis()));
        private final Persoon persoon;
        private final AbstractDeltaVerschilVerwerker verwerker;
        private final Set<PersoonNationaliteit> persoonNationaliteitSet;
        private final PersoonNationaliteit persoonNationaliteit1;
        private final PersoonNationaliteit persoonNationaliteit2;

        private TestData() {
            persoon = new Persoon(SoortPersoon.INGESCHREVENE);
            persoon.setAdministratieveHandeling(administratieveHandeling);
            final Nationaliteit nationaliteit1 = new Nationaliteit("1", "0001");
            nationaliteit1.setId(1);
            final Nationaliteit nationaliteit2 = new Nationaliteit("2", "0002");
            nationaliteit2.setId(2);
            persoonNationaliteit1 = new PersoonNationaliteit(persoon, nationaliteit1);
            persoonNationaliteit2 = new PersoonNationaliteit(persoon, nationaliteit2);
            verwerker = new DeltaRootEntiteitVerschilVerwerker(DeltaRootEntiteitModus.bepaalModus(persoon));
            persoonNationaliteitSet = new LinkedHashSet<>();
            persoonNationaliteitSet.add(persoonNationaliteit1);
            persoonNationaliteitSet.add(persoonNationaliteit2);
        }
    }
}
