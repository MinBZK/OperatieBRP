/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;

/**
 * Unittest voor {@link DeltaStapelMatch}.
 */
public class DeltaStapelMatchTest extends AbstractDeltaTest {

    @Test
    public void testDeltaStapelMatch() throws NoSuchFieldException {
        final Persoon persoon = maakPersoon(true);
        final Class<?> persoonClass = persoon.getClass();
        final String veldnaam = "versienummer";
        final EntiteitSleutel sleutel = new EntiteitSleutel(persoonClass, veldnaam);
        final Field veld = Persoon.class.getDeclaredField(veldnaam);

        final PersoonVoornaam voornaam = new PersoonVoornaam(persoon, 1);
        final PersoonVoornaamHistorie historie = new PersoonVoornaamHistorie(voornaam, "Pietje");
        final Set<FormeleHistorie> historieSet = new HashSet<>();
        historieSet.add(historie);
        final DeltaStapelMatch match = new DeltaStapelMatch(historieSet, historieSet, persoon, sleutel, veld);

        assertEquals(historieSet, match.getOpgeslagenRijen());
        assertEquals(historieSet, match.getNieuweRijen());
        assertEquals(persoon, match.getEigenaarDeltaEntiteit());
        assertEquals(sleutel, match.getEigenaarSleutel());
        assertEquals(veld, match.getEigenaarVeld());

        assertEquals(
                "DeltaStapelMatch[aantal opgeslagenRijen=1,aantal nieuweRijen=1,eigenaarEntiteit=Persoon,eigenaarSleutel=EntiteitSleutel[Entiteit=class nl"
                        + ".bzk.algemeenbrp.dal.domein.brp.entity.Persoon,Veld=versienummer,EigenaarSleutel=<null>],eigenaarVeld=private java.lang.Long nl"
                        + ".bzk.algemeenbrp.dal.domein.brp.entity.Persoon.versienummer]",
                match.toString());
    }
}
