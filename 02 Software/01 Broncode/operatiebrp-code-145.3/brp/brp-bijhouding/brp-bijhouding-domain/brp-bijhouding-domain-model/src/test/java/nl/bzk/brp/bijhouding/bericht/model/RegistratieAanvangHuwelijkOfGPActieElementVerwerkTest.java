/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Test;

/**
 * Testen voor {@link RegistratieAanvangHuwelijkActieElement}.
 */
public class RegistratieAanvangHuwelijkOfGPActieElementVerwerkTest extends AbstractHuwelijkInNederlandTestBericht {

    @Test
    public void testVerwerk() {
        // setup
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1530", new Partij("partij", "001530")));
        final BijhoudingVerzoekBericht bericht = getSuccesHuwelijkInNederlandBericht();
        assertNotNull(bericht);
        final List<MeldingElement> meldingen = bericht.valideer();
        assertEquals(0, meldingen.size());
        getIngeschrevenPersoon().setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        // exucute
        bericht.getAdministratieveHandeling().getActies().iterator().next().verwerk(bericht, getAdministratieveHandeling());

        final Set<Relatie> relaties = getIngeschrevenPersoon().getRelaties();
        assertEquals(1, relaties.size());
        final Relatie relatie = relaties.iterator().next();

        assertEquals(SoortActie.REGISTRATIE_AANVANG_HUWELIJK,bericht.getAdministratieveHandeling().getHoofdActie().getSoortActie());
        assertIngeschrevenPersoon(getIngeschrevenPersoon());
        assertNietIngeschrevenPersoon(getPersoonVoorRelatie(SoortPersoon.PSEUDO_PERSOON, relatie));
        assertRelatie(relatie);
    }

    @Test
    public void testVerwerkMetAanmakenPseudoPersoon() {
        // setup
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1530", new Partij("partij", "001530")));
        final BijhoudingVerzoekBericht bericht = getHuwelijkTussenTweeIngeschrevenenBericht();
        assertNotNull(bericht);
        final List<MeldingElement> meldingen = bericht.valideer();
        assertEquals(0, meldingen.size());
        getIngeschrevenPersoon().setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        getIngeschrevenPersoonPartner().setBijhoudingSituatie(BijhoudingSituatie.OPNIEUW_INDIENEN);
        // exucute
        bericht.getAdministratieveHandeling().getActies().iterator().next().verwerk(bericht, getAdministratieveHandeling());

        final Set<Relatie> relaties = getIngeschrevenPersoon().getRelaties();
        assertEquals(1, relaties.size());
        final Relatie relatie = relaties.iterator().next();

        assertEquals(SoortActie.REGISTRATIE_AANVANG_HUWELIJK,bericht.getAdministratieveHandeling().getHoofdActie().getSoortActie());
        assertIngeschrevenPersoon(getIngeschrevenPersoon());
        assertNotNull(getPersoonVoorRelatie(SoortPersoon.PSEUDO_PERSOON, relatie));
        assertRelatie(relatie);
    }

    @Test
    public void testVerwerkNiet() {
        // setup
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1530", new Partij("partij", "001530")));
        final BijhoudingVerzoekBericht bericht = getSuccesHuwelijkInNederlandBericht();
        assertNotNull(bericht);
        final List<MeldingElement> meldingen = bericht.valideer();
        assertEquals(0, meldingen.size());
        final BijhoudingPersoon ingeschrevenPersoon = getIngeschrevenPersoon();
        ingeschrevenPersoon.setBijhoudingSituatie(BijhoudingSituatie.OPNIEUW_INDIENEN);
        assertEquals(0, ingeschrevenPersoon.getRelaties().size());
        // exucute
        bericht.getAdministratieveHandeling().getActies().iterator().next().verwerk(bericht, getAdministratieveHandeling());

        assertEquals(0, ingeschrevenPersoon.getRelaties().size());
    }

    @Test
    public void testVerwerkHerhaling() {
        // setup
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1530", new Partij("partij", "001530")));
        final BijhoudingVerzoekBericht bericht = getSuccesHuwelijkInNederlandBericht();
        assertNotNull(bericht);
        List<MeldingElement> meldingen = bericht.valideer();
        assertEquals(0, meldingen.size());
        getIngeschrevenPersoon().setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        // exucute
        bericht.getAdministratieveHandeling().getActies().iterator().next().verwerk(bericht, getAdministratieveHandeling());

        // ronde 2
        meldingen = bericht.valideer();
        assertEquals(2, meldingen.size());

        //vanwege de herhaling is er reeds een huwelijk, dus overtreding van R1869
        assertEquals(Regel.R1869, meldingen.get(0).getRegel());
        //vanwege de herhaling met dezelfde datum bestaat er al een feitdatum op de PL die gelijk is aan de DAG van de twee acties, dus twee overtredingen:
        //hoofdpersoon met objectsleutel 212121
        assertEquals(Regel.R2493, meldingen.get(1).getRegel());
    }

    private void assertRelatie(final Relatie relatieEntiteit) {
        final AdministratieveHandeling administratieveHandeling = getAdministratieveHandeling();
        assertEquals(SoortRelatie.HUWELIJK, relatieEntiteit.getSoortRelatie());
        assertEquals(1, relatieEntiteit.getRelatieHistorieSet().size());
        final RelatieHistorie relatieHistorie = relatieEntiteit.getRelatieHistorieSet().iterator().next();
        assertEquals(20160321, relatieHistorie.getDatumAanvang().intValue());
        assertEquals(administratieveHandeling.getDatumTijdRegistratie(), relatieHistorie.getDatumTijdRegistratie());
        assertEquals(administratieveHandeling, relatieHistorie.getActieInhoud().getAdministratieveHandeling());
    }

    private void assertIngeschrevenPersoon(final Persoon ingeschrevenPersoon) {
        assertNotNull(ingeschrevenPersoon);
        assertEquals(getIngeschrevenPersoon(), ingeschrevenPersoon);
        assertEquals(0, ingeschrevenPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size());
        assertBetrokkenheid(ingeschrevenPersoon.getBetrokkenheidSet().iterator().next());
    }

    private void assertNietIngeschrevenPersoon(final Persoon nietIngeschrevenPersoon) {
        assertNotNull(nietIngeschrevenPersoon);
        assertEquals(0, nietIngeschrevenPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size());
        assertEquals(1, nietIngeschrevenPersoon.getPersoonIDHistorieSet().size());
        assertEquals(1, nietIngeschrevenPersoon.getPersoonSamengesteldeNaamHistorieSet().size());
        assertEquals(1, nietIngeschrevenPersoon.getPersoonGeboorteHistorieSet().size());
        assertEquals(1, nietIngeschrevenPersoon.getPersoonGeslachtsaanduidingHistorieSet().size());
        assertEquals(0, nietIngeschrevenPersoon.getPersoonNationaliteitSet().size());
        assertBetrokkenheid(nietIngeschrevenPersoon.getBetrokkenheidSet().iterator().next());
    }

    private void assertBetrokkenheid(final Betrokkenheid betrokkenheid) {
        assertEquals(SoortBetrokkenheid.PARTNER, betrokkenheid.getSoortBetrokkenheid());
        assertEquals(1, betrokkenheid.getBetrokkenheidHistorieSet().size());
        assertEquals(0, betrokkenheid.getBetrokkenheidOuderHistorieSet().size());
        assertEquals(0, betrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet().size());
    }

    private Persoon getPersoonVoorRelatie(final SoortPersoon soortPersoon, final Relatie relatieEntiteit) {
        for (final Betrokkenheid betrokkenheid : relatieEntiteit.getBetrokkenheidSet()) {
            if (soortPersoon.equals(betrokkenheid.getPersoon().getSoortPersoon())) {
                return betrokkenheid.getPersoon();
            }
        }
        return null;
    }
}
