/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.*;


import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Before;
import org.junit.Test;


public class PersoonGeboorteHistorieTest {
    private Persoon persoon;

    @Before
    public void setup() {
        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
    }

    @Test
    public void testIsInhoudelijkGelijkAan() {
        final PersoonGeboorteHistorie persoonGeboorteHistorie = maakPersoonGeboorteHistorie();
        final PersoonGeboorteHistorie persoonGeboorteHistorieKopie = persoonGeboorteHistorie.kopieer();
        assertEquals(persoonGeboorteHistorieKopie.getBuitenlandsePlaatsGeboorte(), persoonGeboorteHistorie.getBuitenlandsePlaatsGeboorte());
        assertEquals(persoonGeboorteHistorieKopie.getBuitenlandseRegioGeboorte(), persoonGeboorteHistorie.getBuitenlandseRegioGeboorte());
        assertEquals(persoonGeboorteHistorieKopie.getDatumGeboorte(), persoonGeboorteHistorie.getDatumGeboorte());
        assertEquals(persoonGeboorteHistorieKopie.getDatumTijdRegistratie(), persoonGeboorteHistorie.getDatumTijdRegistratie());
        assertEquals(persoonGeboorteHistorieKopie.getDatumTijdVerval(), persoonGeboorteHistorie.getDatumTijdVerval());
        assertEquals(persoonGeboorteHistorieKopie.getElementenInOnderzoek(), persoonGeboorteHistorie.getElementenInOnderzoek());
        assertEquals(persoonGeboorteHistorieKopie.getGemeente(), persoonGeboorteHistorie.getGemeente());
        assertEquals(persoonGeboorteHistorieKopie.getGegevenInOnderzoekPerElementMap(), persoonGeboorteHistorie.getGegevenInOnderzoekPerElementMap());
        assertEquals(persoonGeboorteHistorieKopie.getIndicatieVoorkomenTbvLeveringMutaties(), persoonGeboorteHistorie.getIndicatieVoorkomenTbvLeveringMutaties());
        assertNull(persoonGeboorteHistorieKopie.getId());
        assertNotNull(persoonGeboorteHistorie.getId());
        assertEquals(persoonGeboorteHistorieKopie.getLandOfGebied(), persoonGeboorteHistorie.getLandOfGebied());
        assertEquals(persoonGeboorteHistorieKopie.getNadereAanduidingVerval(), persoonGeboorteHistorie.getNadereAanduidingVerval());
        assertEquals(persoonGeboorteHistorieKopie.getOmschrijvingGeboortelocatie(), persoonGeboorteHistorie.getOmschrijvingGeboortelocatie());
        assertEquals(persoonGeboorteHistorieKopie.getPersoon(), persoonGeboorteHistorie.getPersoon());
        assertEquals(persoonGeboorteHistorieKopie.getWoonplaatsnaamGeboorte(), persoonGeboorteHistorie.getWoonplaatsnaamGeboorte());

    }


    private PersoonGeboorteHistorie maakPersoonGeboorteHistorie() {
        final PersoonGeboorteHistorie persoonGeboorteHistorie = new PersoonGeboorteHistorie(persoon, 19860101, new LandOfGebied("0001", "code"));
        persoonGeboorteHistorie.setId(1L);
        persoonGeboorteHistorie.setGemeente(new Gemeente((short)1, "naam", "0045", new Partij("partijNaam", "000123")));
        persoonGeboorteHistorie.setBuitenlandsePlaatsGeboorte("plaats");
        persoonGeboorteHistorie.setWoonplaatsnaamGeboorte("woonplaatsnaamGeboorte");
        persoonGeboorteHistorie.setOmschrijvingGeboortelocatie("omschrijvingGeboortelocatie");
        persoonGeboorteHistorie.setBuitenlandseRegioGeboorte("buitenlandseRegioGeboorte");
        return persoonGeboorteHistorie;
    }
}
