/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test voor {@link TransformatieDw041}.
 */
public class TransformatieDw041Test extends AbstractTransformatieTest {
    private TransformatieDw041 transformer;

    @Before
    public void setup() {
        transformer = new TransformatieDw041();
    }

    @Test
    public void testAcceptNationaliteitWijziging() {
        assertTrue(transformer.accept(maakVerschilGroep()));
    }

    @Test
    public void testExecute() {
        final VerschilGroep adresActualisering = maakVerschilGroep();
        final BRPActie actieVervalTbvLeveringMuts = maakActieVerval("07", 0, 0);
        final VerschilGroep aangepasteActualisering = transformer.execute(adresActualisering, actieVervalTbvLeveringMuts, context);
        assertNotNull(aangepasteActualisering);
        final ArrayList<Verschil> nieuweVerschillen = new ArrayList<>();
        for (final Verschil verschil : aangepasteActualisering) {
            nieuweVerschillen.add(verschil);
        }
        for (final Verschil verschil : adresActualisering) {
            nieuweVerschillen.remove(verschil);
        }
        assertEquals(1, nieuweVerschillen.size());
        // verify verschil voor actieVervalTbvLeveringMutaties
        final Verschil actieVervalTbvLeveringMutatiesVerschil = zoekVerschil(nieuweVerschillen, "actieVervalTbvLeveringMutaties");
        assertNotNull(actieVervalTbvLeveringMutatiesVerschil);
    }

    @Test
    public void testGetCode() {
        assertEquals(DeltaWijziging.DW_041, transformer.getDeltaWijziging());
    }

    private VerschilGroep maakVerschilGroep() {
        final BRPActie actie =
                new BRPActie(
                    SoortActie.CONVERSIE_GBA,
                    getAdministratieveHandeling(),
                    getAdministratieveHandeling().getPartij(),
                    getAdministratieveHandeling().getDatumTijdRegistratie());
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("NL", (short) 1));
        final PersoonNationaliteitHistorie persoonNationaliteitHistorie = new PersoonNationaliteitHistorie(persoonNationaliteit);

        final Verschil actieVervalVerschil =
                new Verschil(maakEntiteitSleutel("actieVerval"), null, actie, persoonNationaliteitHistorie, persoonNationaliteitHistorie);
        final Verschil datumTijdVervalVerschil =
                new Verschil(
                    maakEntiteitSleutel("datumTijdVerval"),
                    null,
                    maakTimestamp("2000-01-02 02"),
                    persoonNationaliteitHistorie,
                    persoonNationaliteitHistorie);
        final Verschil eindeBijhoudingNationaliteit =
                new Verschil(
                    maakEntiteitSleutel(PersoonNationaliteitHistorie.EINDE_BIJHOUDING),
                    null,
                    true,
                    persoonNationaliteitHistorie,
                    persoonNationaliteitHistorie);
        final Verschil migratieDatumNationaliteit =
                new Verschil(
                    maakEntiteitSleutel(PersoonNationaliteitHistorie.MIGRATIE_DATUM),
                    null,
                    true,
                    persoonNationaliteitHistorie,
                    persoonNationaliteitHistorie);
        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(persoonNationaliteitHistorie);
        verschilGroep.addVerschillen(
            Arrays.asList(actieVervalVerschil, datumTijdVervalVerschil, eindeBijhoudingNationaliteit, migratieDatumNationaliteit));
        return verschilGroep;
    }

    private EntiteitSleutel maakEntiteitSleutel(final String veld) {
        final EntiteitSleutel persoonSleutel = new EntiteitSleutel(Persoon.class, "persoonNationaliteitSet", null);
        final EntiteitSleutel persoonNationaliteitSleutel =
                new EntiteitSleutel(PersoonNationaliteit.class, "persoonNationaliteitHistorieSet", persoonSleutel);
        return new EntiteitSleutel(PersoonNationaliteitHistorie.class, veld, persoonNationaliteitSleutel);
    }
}
