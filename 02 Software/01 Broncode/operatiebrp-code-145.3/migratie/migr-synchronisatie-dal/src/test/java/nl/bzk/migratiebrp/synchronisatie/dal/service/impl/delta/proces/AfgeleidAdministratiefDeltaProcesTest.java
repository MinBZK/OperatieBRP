/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractDeltaTest;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;

import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaRootEntiteitMatch;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaVergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link AfgeleidAdministratiefDeltaProces}.
 */
public class AfgeleidAdministratiefDeltaProcesTest extends AbstractDeltaTest {

    private DeltaBepalingContext context;
    private DeltaProces proces;
    private Persoon bestaandPersoon;
    private Persoon nieuwPersoon;

    @Before
    public void setUp() throws Exception {
        bestaandPersoon = maakPersoon(true);
        nieuwPersoon = maakPersoon(false);

        context = new DeltaBepalingContext(nieuwPersoon, bestaandPersoon, null, false);
        proces = new AfgeleidAdministratiefDeltaProces();

        final DeltaRootEntiteitMatch match = new DeltaRootEntiteitMatch(bestaandPersoon, nieuwPersoon, null, null);
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        final Sleutel sleutel = new EntiteitSleutel(Persoon.class, "test");
        vergelijkerResultaat.voegToeOfVervangVerschil(new Verschil(sleutel, null, null, null, null));
        match.setVergelijkerResultaat(vergelijkerResultaat);
        final Set<DeltaRootEntiteitMatch> matches = Collections.singleton(match);
        context.setDeltaRootEntiteitMatches(matches);

    }

    @Test
    public void testBepaalVerschillen() {
        assertTrue(context.getDeltaEntiteitPaarSet().isEmpty());
        proces.bepaalVerschillen(context);
        assertTrue(context.getDeltaEntiteitPaarSet().isEmpty());
    }

    @Test
    public void testVerwerkVerschillenAdministratieveHandelingNietUitAanCat07() {
        assertEquals(1, bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size());
        proces.verwerkVerschillen(context);
        assertEquals(2, bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size());
        final PersoonAfgeleidAdministratiefHistorie historie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet());
        final AdministratieveHandeling administratieveHandeling = historie.getAdministratieveHandeling();
        final Timestamp datumTijdRegistratie = administratieveHandeling.getDatumTijdRegistratie();
        assertEquals(datumTijdRegistratie, historie.getDatumTijdRegistratie());
        assertEquals(datumTijdRegistratie, historie.getDatumTijdLaatsteWijziging());
        assertEquals(datumTijdRegistratie, historie.getDatumTijdLaatsteWijzigingGba());
        assertEquals(administratieveHandeling, historie.getAdministratieveHandeling());
    }

    @Test
    public void testVerwerkVerschillenAdministratieveHandelingUitAanCat07() {
        final AdministratieveHandeling administratieveHandeling = getAdministratieveHandeling(nieuwPersoon);
        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandPersoon.getPersoonInschrijvingHistorieSet())
                .getActieInhoud()
                .setAdministratieveHandeling(administratieveHandeling);

        assertEquals(1, bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size());
        proces.verwerkVerschillen(context);
        assertEquals(2, bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size());
        final PersoonAfgeleidAdministratiefHistorie historie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet());
        final Timestamp datumTijdRegistratie = administratieveHandeling.getDatumTijdRegistratie();
        assertEquals(datumTijdRegistratie, historie.getDatumTijdRegistratie());
        assertEquals(datumTijdRegistratie, historie.getDatumTijdLaatsteWijziging());
        assertEquals(datumTijdRegistratie, historie.getDatumTijdLaatsteWijzigingGba());
        assertEquals(administratieveHandeling, historie.getAdministratieveHandeling());
    }

    @Test
    public void testVerwerken2AdministratieveHandelingen() {
        context.setBijhoudingAnummerWijziging();
        context.setBijhoudingOverig();

        assertEquals(1, bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size());
        proces.verwerkVerschillen(context);
        assertEquals(3, bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size());
        final AdministratieveHandeling administratieveHandeling = getAdministratieveHandeling(nieuwPersoon);
        final PersoonAfgeleidAdministratiefHistorie historie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet());
        assertEquals(administratieveHandeling, historie.getAdministratieveHandeling());
        assertEquals(SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG, historie.getAdministratieveHandeling().getSoort());
    }

    private AdministratieveHandeling getAdministratieveHandeling(final Persoon persoon) {
        return FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonAfgeleidAdministratiefHistorieSet())
                .getAdministratieveHandeling();
    }
}
