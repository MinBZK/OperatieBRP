/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FunctieAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdresHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenWijzigingVerblijf;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

import org.junit.Before;
import org.junit.Test;

public class TransformatieDw023Test extends AbstractTransformatieTest {

    private TransformatieDw023 transformer;

    @Before
    public void setup() {
        transformer = new TransformatieDw023();
    }

    @Test
    public void testAcceptZonderDeg() {
        assertTrue(transformer.accept(maakActieAanpassingGeldigheidVerschil(true, false)));
    }

    @Test
    public void testAcceptMetDegZonderAag() {
        assertFalse(transformer.accept(maakActieAanpassingGeldigheidVerschil(false, true)));
    }

    @Test
    public void testAcceptMetDeg() {
        assertTrue(transformer.accept(maakActieAanpassingGeldigheidVerschil(true, true)));
    }

    @Test
    public void testExecuteZonderDeg() {
        final VerschilGroep adresActualisering = maakActieAanpassingGeldigheidVerschil(true, false);
        final BRPActie actieVervalTbvLeveringMuts = maakActieVerval("07", 0, 0);
        final VerschilGroep aangepasteActualisering = transformer.execute(adresActualisering, actieVervalTbvLeveringMuts, context);
        verifyResult(aangepasteActualisering, adresActualisering);
    }

    @Test
    public void testExecuteMetDeg() {
        final VerschilGroep adresActualisering = maakActieAanpassingGeldigheidVerschil(true, true);
        final BRPActie actieVervalTbvLeveringMuts = maakActieVerval("07", 0, 0);
        final VerschilGroep aangepasteActualisering = transformer.execute(adresActualisering, actieVervalTbvLeveringMuts, context);
        verifyResult(aangepasteActualisering, adresActualisering);
    }

    private void verifyResult(final VerschilGroep aangepasteActualisering, final VerschilGroep adresActualisering) {
        assertNotNull(aangepasteActualisering);
        final ArrayList<Verschil> nieuweVerschillen = new ArrayList<>();
        for (final Verschil verschil : aangepasteActualisering) {
            nieuweVerschillen.add(verschil);
        }
        for (final Verschil verschil : adresActualisering) {
            nieuweVerschillen.remove(verschil);
        }
        assertEquals(7, nieuweVerschillen.size());
        final Verschil actieVervalVerschil = zoekActieVervalVerschil(nieuweVerschillen);
        final Verschil tsVervalVerschil = zoekTsVervalVerschil(nieuweVerschillen);
        final Verschil indicatieRijTbvLeveringVerschil = zoekIndicatieTbvLeveringVerschil(nieuweVerschillen);
        final Verschil actieTbvLeveringMutatiesVerschil = zoekActieTbvLeveringMutatiesVerschil(nieuweVerschillen);
        final Verschil nieuweRijVerschil = zoekNieuweRijVerschil(nieuweVerschillen, "persoonAdresHistorieSet");
        final Verschil nieuweRijActieInhoudVerschil = zoekNieuweRijAanpassingVerschil(nieuweVerschillen, "actieInhoud");
        final Verschil nieuweRijActieVervalVerschil = zoekNieuweRijAanpassingVerschil(nieuweVerschillen, "actieVerval");

        // Verify M-rij en nieuwe rij
        assertNotNull(actieVervalVerschil);
        assertNotNull(tsVervalVerschil);
        assertNotNull(indicatieRijTbvLeveringVerschil);
        assertNotNull(actieTbvLeveringMutatiesVerschil);
        assertNotNull(nieuweRijVerschil);
        assertNotNull(nieuweRijActieInhoudVerschil);
        assertNotNull(nieuweRijActieVervalVerschil);
    }

    @Test
    public void testGetCode() {
        assertEquals(DeltaWijziging.DW_023, transformer.getDeltaWijziging());
    }

    private VerschilGroep maakActieAanpassingGeldigheidVerschil(
        final boolean voegActieAanpassingGeldigheidVerschilToe,
        final boolean voegDatumEindeGeldigheidVerschilToe)
    {
        final EntiteitSleutel sleutel = maakActieAanpassingGeldigheidVerschilSleutel();
        final Object oudeWaarde = new Object();
        final Object nieuweWaarde = new Object();
        final PersoonAdres dummyPersoonAdres = maakDummyPersoonAdres();
        final PersoonAdresHistorie bestaandeHistorieRij = maakDummyPersoonAdresHistorie(dummyPersoonAdres);
        final PersoonAdresHistorie nieuweHistorieRij = maakDummyPersoonAdresHistorie(dummyPersoonAdres);
        final List<Verschil> verschillen = new ArrayList<>();
        if (voegDatumEindeGeldigheidVerschilToe) {
            final EntiteitSleutel degSleutel = maakDatumEindeGeldigheidVerschilSleutel();
            final int degOudeWaarde = 20140202;
            final int degNieuweWaarde = 20140201;
            verschillen.add(new Verschil(
                degSleutel,
                degOudeWaarde,
                degNieuweWaarde,
                VerschilType.ELEMENT_AANGEPAST,
                bestaandeHistorieRij,
                nieuweHistorieRij));
        }
        if (voegActieAanpassingGeldigheidVerschilToe) {
            verschillen.add(new Verschil(sleutel, oudeWaarde, nieuweWaarde, VerschilType.ELEMENT_AANGEPAST, bestaandeHistorieRij, nieuweHistorieRij));
        }

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(bestaandeHistorieRij);
        verschilGroep.addVerschillen(verschillen);
        return verschilGroep;
    }

    private EntiteitSleutel maakDatumEindeGeldigheidVerschilSleutel() {
        final EntiteitSleutel eigenaar = new EntiteitSleutel(Persoon.class, "persoonAdresSet");
        final EntiteitSleutel persoonAdres = new EntiteitSleutel(PersoonAdres.class, "persoonAdresHistorieSet", eigenaar);
        final EntiteitSleutel result = new EntiteitSleutel(PersoonAdresHistorie.class, "datumEindeGeldigheid", persoonAdres);
        result.addSleuteldeel("dataanvgel", Integer.valueOf(20000101));
        result.addSleuteldeel("tsreg", maakTimestamp("2000-01-02 02"));
        return result;
    }

    private EntiteitSleutel maakActieAanpassingGeldigheidVerschilSleutel() {
        final EntiteitSleutel eigenaar = new EntiteitSleutel(Persoon.class, "persoonAdresSet");
        final EntiteitSleutel persoonAdres = new EntiteitSleutel(PersoonAdres.class, "persoonAdresHistorieSet", eigenaar);
        final EntiteitSleutel result = new EntiteitSleutel(PersoonAdresHistorie.class, "actieAanpassingGeldigheid", persoonAdres);

        result.addSleuteldeel("dataanvgel", Integer.valueOf(20000101));
        result.addSleuteldeel("tsreg", maakTimestamp("2000-01-02 02"));
        return result;
    }

    private PersoonAdres maakDummyPersoonAdres() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonAdres persoonAdres = new PersoonAdres(persoon);
        persoonAdres.setHuisnummer(1);
        return persoonAdres;
    }

    private PersoonAdresHistorie maakDummyPersoonAdresHistorie(final PersoonAdres persoonAdres) {
        final LandOfGebied landOfGebied = new LandOfGebied((short) 1, "testland");
        final RedenWijzigingVerblijf redenWijzging = new RedenWijzigingVerblijf('t', "test");
        return new PersoonAdresHistorie(persoonAdres, FunctieAdres.WOONADRES, landOfGebied, redenWijzging);
    }
}
