/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.HistorieUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractDeltaTest;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;

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

    @Before public void setUp() throws Exception {
        bestaandPersoon = maakPersoon(true);
        nieuwPersoon = maakPersoon(false);

        context = new DeltaBepalingContext(nieuwPersoon, bestaandPersoon, null, false);
        proces = new AfgeleidAdministratiefDeltaProces();
    }

    @Test
    public void testBepaalVerschillen() {
        assertTrue(context.getDeltaEntiteitPaarSet().isEmpty());
        proces.bepaalVerschillen(context);
        assertTrue(context.getDeltaEntiteitPaarSet().isEmpty());
    }

    @Test
    public void testVerwerkVerschillenAdministratieveHandelingNietUitAanCat07() {
        assertTrue(bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size() == 1);
        proces.verwerkVerschillen(context);
        assertTrue(bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size() == 2);
        final AdministratieveHandeling administratieveHandeling = nieuwPersoon.getAdministratieveHandeling();
        final PersoonAfgeleidAdministratiefHistorie historie = HistorieUtil.getActueelHistorieVoorkomen(bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet());
        final Timestamp datumTijdRegistratie = administratieveHandeling.getDatumTijdRegistratie();
        assertEquals(datumTijdRegistratie, historie.getDatumTijdRegistratie());
        assertEquals(datumTijdRegistratie, historie.getDatumTijdLaatsteWijziging());
        assertEquals(datumTijdRegistratie, historie.getDatumTijdLaatsteWijzigingGba());
        assertEquals(administratieveHandeling, historie.getAdministratieveHandeling());
    }

    @Test
    public void testVerwerkVerschillenAdministratieveHandelingUitAanCat07() {
        final AdministratieveHandeling administratieveHandeling = nieuwPersoon.getAdministratieveHandeling();
        HistorieUtil.getActueelHistorieVoorkomen(bestaandPersoon.getPersoonInschrijvingHistorieSet()).getActieInhoud().setAdministratieveHandeling(administratieveHandeling);

        assertTrue(bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size() == 1);
        proces.verwerkVerschillen(context);
        assertTrue(bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size() == 2);
        final PersoonAfgeleidAdministratiefHistorie historie = HistorieUtil.getActueelHistorieVoorkomen(bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet());
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

        assertTrue(bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size() == 1);
        proces.verwerkVerschillen(context);
        assertTrue(bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet().size() == 3);
        final AdministratieveHandeling administratieveHandeling = nieuwPersoon.getAdministratieveHandeling();
        final PersoonAfgeleidAdministratiefHistorie historie = HistorieUtil.getActueelHistorieVoorkomen(bestaandPersoon.getPersoonAfgeleidAdministratiefHistorieSet());
        assertEquals(administratieveHandeling, historie.getAdministratieveHandeling());
        assertEquals(SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG, historie.getAdministratieveHandeling().getSoort());
    }
}
