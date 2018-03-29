/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.OnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import org.junit.Test;

/**
 * Testen voor {@link BijhoudingOnderzoek}.
 */
public class BijhoudingOnderzoekTest {

    @Test
    public void decorateNull() {
        assertNull(BijhoudingOnderzoek.decorate(null));
    }

    @Test
    public void decorateNotNull() {
        assertNotNull(BijhoudingOnderzoek.decorate(new Onderzoek(new Partij("test partij", "123456"), new Persoon(SoortPersoon.INGESCHREVENE))));
    }

    @Test
    public void wijzigStatusOnderzoek() {
        final long nu = System.currentTimeMillis();
        final Onderzoek onderzoekDelegate = new Onderzoek(new Partij("test partij", "123456"), new Persoon(SoortPersoon.INGESCHREVENE));
        final OnderzoekHistorie onderzoekHistorie = new OnderzoekHistorie(20170101, StatusOnderzoek.IN_UITVOERING, onderzoekDelegate);
        onderzoekHistorie.setDatumTijdRegistratie(new Timestamp(nu - 1000));
        onderzoekDelegate.addOnderzoekHistorie(onderzoekHistorie);
        final BijhoudingOnderzoek onderzoek = BijhoudingOnderzoek.decorate(onderzoekDelegate);
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(new Partij("test partij", "123456"), SoortAdministratieveHandeling.WIJZIGING_ONDERZOEK,
                        new Timestamp(nu));
        final BRPActie
                actie =
                new BRPActie(SoortActie.REGISTRATIE_WIJZIGING_ONDERZOEK, administratieveHandeling, administratieveHandeling.getPartij(),
                        administratieveHandeling.getDatumTijdRegistratie());
        onderzoek.wijzigStatusOnderzoek(actie, StatusOnderzoek.GESTAAKT);
        assertEquals(StatusOnderzoek.GESTAAKT,
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(onderzoek.getOnderzoekHistorieSet()).getStatusOnderzoek());
    }

}
