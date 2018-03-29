/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

public class TransformatieDw022Test extends AbstractTransformatieTest {

    private TransformatieDw022 transformer;

    @Before
    public void setup() {
        transformer = new TransformatieDw022();
    }

    @Test
    public void testAccept() {
        assertTrue(transformer.accept(maakPersoonAdresActualisering()));
    }

    @Test
    public void testExecute() {
        final VerschilGroep adresActualisering = maakPersoonAdresActualisering();
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
        final Verschil nieuwVerschil = nieuweVerschillen.get(0);
        assertNotNull(nieuwVerschil);
        assertEquals(VerschilType.ELEMENT_NIEUW, nieuwVerschil.getVerschilType());
        assertTrue(nieuwVerschil.getSleutel() instanceof EntiteitSleutel);
        final EntiteitSleutel sleutel = (EntiteitSleutel) nieuwVerschil.getSleutel();
        assertEquals("actieVervalTbvLeveringMutaties", sleutel.getVeld());
        assertEquals(((EntiteitSleutel) adresActualisering.get(0).getSleutel()).getEigenaarSleutel(), sleutel.getEigenaarSleutel());
        assertEquals(adresActualisering.get(0).getBestaandeHistorieRij(), nieuwVerschil.getBestaandeHistorieRij());
        assertEquals(adresActualisering.get(0).getNieuweHistorieRij(), nieuwVerschil.getNieuweHistorieRij());
        assertTrue(nieuwVerschil.getNieuweWaarde() instanceof BRPActie);
        final BRPActie actieMuts = (BRPActie) nieuwVerschil.getNieuweWaarde();
        assertEquals(
                adresActualisering.get(0).getNieuweHistorieRij().getActieInhoud().getAdministratieveHandeling(),
                actieMuts.getAdministratieveHandeling());
        assertEquals(adresActualisering.get(0).getSleutel().getDelen(), sleutel.getDelen());
        assertEquals(((EntiteitSleutel) adresActualisering.get(0).getSleutel()).getEntiteit(), sleutel.getEntiteit());
    }

    @Test
    public void testGetCode() {
        assertEquals(DeltaWijziging.DW_022, transformer.getDeltaWijziging());
    }

    private VerschilGroep maakPersoonAdresActualisering() {
        final EntiteitSleutel datumTijdVervalSleutel =
                maakPersoonAdresHistorieEntiteitSleutel("datumTijdVerval", maakTimestamp("1990-01-02 02"));
        final EntiteitSleutel actieVervalSleutel = maakPersoonAdresHistorieEntiteitSleutel("actieVerval", maakTimestamp("1990-01-02 02"));
        final EntiteitSleutel nadereAanduidingVervalSleutel =
                maakPersoonAdresHistorieEntiteitSleutel("nadereAanduidingVerval", maakTimestamp("1990-01-02 02"));

        final Timestamp nieuweDatumTijdVerval = maakTimestamp("1990-01-02 02");
        final BRPActie nieuweActieVerval = maakActieVerval("08", 0, 0);
        final char nieuweNadereAanduidingVerval = 'O';

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonAdres persoonAdres = new PersoonAdres(persoon);
        persoonAdres.setHuisnummer(1);

        final LandOfGebied landOfGebied = new LandOfGebied("0001", "testland");
        final RedenWijzigingVerblijf redenWijzging = new RedenWijzigingVerblijf('t', "test");
        final PersoonAdresHistorie oudeRij = new PersoonAdresHistorie(persoonAdres, SoortAdres.WOONADRES, landOfGebied, redenWijzging);
        oudeRij.setActieInhoud(nieuweActieVerval);
        oudeRij.setDatumTijdRegistratie(nieuweActieVerval.getDatumTijdRegistratie());
        final PersoonAdresHistorie nieuweRij = new PersoonAdresHistorie(persoonAdres, SoortAdres.WOONADRES, landOfGebied, redenWijzging);
        nieuweRij.setActieInhoud(nieuweActieVerval);
        nieuweRij.setDatumTijdRegistratie(nieuweActieVerval.getDatumTijdRegistratie());

        final Verschil datumTijdVervalVerschil =
                new Verschil(datumTijdVervalSleutel, null, nieuweDatumTijdVerval, VerschilType.ELEMENT_NIEUW, oudeRij, nieuweRij);
        final Verschil nadereAanduidingVervalVerschil =
                new Verschil(nadereAanduidingVervalSleutel, null, nieuweNadereAanduidingVerval, VerschilType.ELEMENT_NIEUW, oudeRij, nieuweRij);
        final Verschil actieVervalVerschil = new Verschil(actieVervalSleutel, null, nieuweActieVerval, VerschilType.ELEMENT_NIEUW, oudeRij, nieuweRij);

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(oudeRij);
        verschilGroep.addVerschillen(Arrays.asList(datumTijdVervalVerschil, nadereAanduidingVervalVerschil, actieVervalVerschil));
        return verschilGroep;
    }

    private EntiteitSleutel maakPersoonAdresHistorieEntiteitSleutel(final String veld, final Timestamp tsReg) {
        final EntiteitSleutel eigenaar = new EntiteitSleutel(Persoon.class, "persoonAdresSet");
        final EntiteitSleutel persoonAdres = new EntiteitSleutel(PersoonVoornaam.class, "persoonAdresHistorieSet", eigenaar);
        final EntiteitSleutel result = new EntiteitSleutel(PersoonAdresHistorie.class, veld, persoonAdres);
        result.addSleuteldeel("dataanvgel", 19900101);
        result.addSleuteldeel("tsreg", tsReg);
        return result;
    }
}
