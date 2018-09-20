/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Bijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.NadereBijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonBijhoudingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;

import org.junit.Before;
import org.junit.Test;

public class TransformatieDw031Test extends AbstractTransformatieTest {

    private TransformatieDw031 transformer;

    @Before
    public void setup() {
        transformer = new TransformatieDw031();
    }

    @Test
    public void testAccept() {
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
        assertEquals(6, nieuweVerschillen.size());
        // verify verschil voor actieVervalTbvLeveringMutaties
        final Verschil actieVervalTbvLeveringMutatiesVerschil = zoekVerschil(nieuweVerschillen, "actieVervalTbvLeveringMutaties");
        final Verschil indicatieVoorkomenTbvLeveringMutatiesVerschil = zoekVerschil(nieuweVerschillen, "indicatieVoorkomenTbvLeveringMutaties");
        final Verschil nieuweRijVerschil = zoekVerschil(nieuweVerschillen, "persoonBijhoudingHistorieSet");
        final Verschil nieuweRijActieInhoudVerschil = zoekNieuweRijAanpassingVerschil(nieuweVerschillen, "actieInhoud");
        final Verschil nieuweRijActieVervalVerschil = zoekNieuweRijAanpassingVerschil(nieuweVerschillen, "actieVerval");
        final Verschil nieuweRijActieVervalTbvLeveringMutatiesVerschil =
                zoekNieuweRijAanpassingVerschil(nieuweVerschillen, "actieVervalTbvLeveringMutaties");

        assertNotNull(actieVervalTbvLeveringMutatiesVerschil);
        assertNotNull(indicatieVoorkomenTbvLeveringMutatiesVerschil);
        assertNotNull(nieuweRijVerschil);
        assertNull(nieuweRijVerschil.getNieuweHistorieRij().getActieVervalTbvLeveringMutaties());
        assertNotNull(nieuweRijActieInhoudVerschil);
        assertNotNull(nieuweRijActieVervalVerschil);
        assertNotNull(nieuweRijActieVervalTbvLeveringMutatiesVerschil);
    }

    @Test
    public void testGetCode() {
        assertEquals(DeltaWijziging.DW_031, transformer.getDeltaWijziging());
    }

    private VerschilGroep maakVerschilGroep() {
        final BRPActie actie =
                new BRPActie(
                    SoortActie.CONVERSIE_GBA,
                    getAdministratieveHandeling(),
                    getAdministratieveHandeling().getPartij(),
                    getAdministratieveHandeling().getDatumTijdRegistratie());
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonBijhoudingHistorie persoonBijhoudingHistorie =
                new PersoonBijhoudingHistorie(
                    persoon,
                    getAdministratieveHandeling().getPartij(),
                    Bijhoudingsaard.INGEZETENE,
                    NadereBijhoudingsaard.ACTUEEL,
                    false);

        final Verschil actieVervalVerschil =
                new Verschil(maakEntiteitSleutel("actieVerval"), null, actie, persoonBijhoudingHistorie, persoonBijhoudingHistorie);
        final Verschil datumTijdVervalVerschil =
                new Verschil(
                    maakEntiteitSleutel("datumTijdVerval"),
                    null,
                    maakTimestamp("2000-01-02 02"),
                    persoonBijhoudingHistorie,
                    persoonBijhoudingHistorie);
        final Verschil bijhoudingsaardIdVerschil =
                new Verschil(maakEntiteitSleutel("bijhoudingsaardId"), (short) 1, (short) 3, persoonBijhoudingHistorie, persoonBijhoudingHistorie);
        final Verschil nadereBijhoudingsaardId =
                new Verschil(maakEntiteitSleutel("nadereBijhoudingsaardId"), (short) 1, (short) 3, persoonBijhoudingHistorie, persoonBijhoudingHistorie);

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(persoonBijhoudingHistorie);
        verschilGroep.addVerschillen(Arrays.asList(actieVervalVerschil, datumTijdVervalVerschil, bijhoudingsaardIdVerschil, nadereBijhoudingsaardId));
        return verschilGroep;
    }

    private EntiteitSleutel maakEntiteitSleutel(final String veld) {
        final EntiteitSleutel persoonSleutel = new EntiteitSleutel(Persoon.class, "persoonBijhoudingHistorieSet", null);
        final EntiteitSleutel result = new EntiteitSleutel(PersoonBijhoudingHistorie.class, veld, persoonSleutel);
        result.addSleuteldeel("dataanvgel", Integer.valueOf(19980622));
        result.addSleuteldeel("tsreg", maakTimestamp("2011-03-16 02"));
        return result;
    }
}
