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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

public class TransformatieDw012Test extends AbstractTransformatieTest {

    private TransformatieDw012 transformer;

    @Before
    public void setup() {
        transformer = new TransformatieDw012();
    }

    @Test
    public void testAccept() {
        assertTrue(transformer.accept(maakGeboorteCorrectie()));
    }

    @Test
    public void testExecute() {
        final VerschilGroep geboorteActualisering = maakGeboorteCorrectie();
        final BRPActie actieVervalTbvLeveringMuts = maakActieVerval("07", 0, 0);
        final VerschilGroep aangepasteActualisering = transformer.execute(geboorteActualisering, actieVervalTbvLeveringMuts, context);

        assertNotNull(aangepasteActualisering);
        final ArrayList<Verschil> nieuweVerschillen = new ArrayList<>();
        for (final Verschil verschil : aangepasteActualisering) {
            nieuweVerschillen.add(verschil);
        }
        for (final Verschil verschil : geboorteActualisering) {
            nieuweVerschillen.remove(verschil);
        }
        assertEquals(1, nieuweVerschillen.size());
        final Verschil nieuwVerschil = nieuweVerschillen.get(0);
        assertNotNull(nieuwVerschil);
        assertEquals(VerschilType.ELEMENT_NIEUW, nieuwVerschil.getVerschilType());
        assertTrue(nieuwVerschil.getSleutel() instanceof EntiteitSleutel);
        final EntiteitSleutel sleutel = (EntiteitSleutel) nieuwVerschil.getSleutel();
        assertEquals("actieVervalTbvLeveringMutaties", sleutel.getVeld());
        assertEquals(((EntiteitSleutel) geboorteActualisering.get(0).getSleutel()).getEigenaarSleutel(), sleutel.getEigenaarSleutel());
        assertEquals(geboorteActualisering.get(0).getBestaandeHistorieRij(), nieuwVerschil.getBestaandeHistorieRij());
        assertEquals(geboorteActualisering.get(0).getNieuweHistorieRij(), nieuwVerschil.getNieuweHistorieRij());
        assertTrue(nieuwVerschil.getNieuweWaarde() instanceof BRPActie);
        final BRPActie actieMuts = (BRPActie) nieuwVerschil.getNieuweWaarde();
        assertEquals(
                geboorteActualisering.get(0).getNieuweHistorieRij().getActieInhoud().getAdministratieveHandeling(),
                actieMuts.getAdministratieveHandeling());
        assertEquals(geboorteActualisering.get(0).getSleutel().getDelen(), sleutel.getDelen());
        assertEquals(((EntiteitSleutel) geboorteActualisering.get(0).getSleutel()).getEntiteit(), sleutel.getEntiteit());
    }

    @Test
    public void testGetCode() {
        assertEquals(DeltaWijziging.DW_012, transformer.getDeltaWijziging());
    }

    private VerschilGroep maakGeboorteCorrectie() {
        final EntiteitSleutel datumTijdVervalSleutel = maakPersoonGeboorteHistorieEntiteitSleutel("datumTijdVerval", maakTimestamp("1990-01-02 02"));
        final EntiteitSleutel actieVervalSleutel = maakPersoonGeboorteHistorieEntiteitSleutel("actieVerval", maakTimestamp("1990-01-02 02"));
        final EntiteitSleutel nadereAanuidingVervalSleutel =
                maakPersoonGeboorteHistorieEntiteitSleutel("nadereAanduidingVerval", maakTimestamp("1990-01-02 02"));

        final Timestamp nieuweDatumTijdVerval = maakTimestamp("1990-01-02 02");
        final BRPActie nieuweActieVerval = maakActieVerval("01", 0, 0);
        final char nieuweNadereAanduidingVerval = 'O';

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final LandOfGebied landOfGebied = new LandOfGebied("0000", "testLand");
        final PersoonGeboorteHistorie oudeRij = new PersoonGeboorteHistorie(persoon, 19900101, landOfGebied);
        oudeRij.setActieInhoud(nieuweActieVerval);
        oudeRij.setDatumTijdRegistratie(nieuweDatumTijdVerval);
        final PersoonGeboorteHistorie nieuweRij = new PersoonGeboorteHistorie(persoon, 19900101, landOfGebied);
        nieuweRij.setDatumTijdRegistratie(nieuweDatumTijdVerval);
        nieuweRij.setDatumTijdVerval(nieuweDatumTijdVerval);
        nieuweRij.setActieInhoud(nieuweActieVerval);
        nieuweRij.setActieVerval(nieuweActieVerval);
        nieuweRij.setNadereAanduidingVerval(nieuweNadereAanduidingVerval);

        final Verschil datumTijdVervalVerschil =
                new Verschil(datumTijdVervalSleutel, null, nieuweDatumTijdVerval, VerschilType.ELEMENT_NIEUW, oudeRij, nieuweRij);
        final Verschil actieVervalVerschil = new Verschil(actieVervalSleutel, null, nieuweActieVerval, VerschilType.ELEMENT_NIEUW, oudeRij, nieuweRij);
        final Verschil nadereAanduidingVervalVerschil =
                new Verschil(nadereAanuidingVervalSleutel, null, nieuweNadereAanduidingVerval, VerschilType.ELEMENT_NIEUW, oudeRij, nieuweRij);

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(oudeRij);
        verschilGroep.addVerschillen(Arrays.asList(datumTijdVervalVerschil, actieVervalVerschil, nadereAanduidingVervalVerschil));
        return verschilGroep;
    }

    private EntiteitSleutel maakPersoonGeboorteHistorieEntiteitSleutel(final String veld, final Timestamp tsReg) {
        final EntiteitSleutel eigenaar = new EntiteitSleutel(Persoon.class, "persoonGeboorteHistorieSet");
        final EntiteitSleutel result = new EntiteitSleutel(PersoonGeboorteHistorie.class, veld, eigenaar);
        result.addSleuteldeel("tsreg", tsReg);
        return result;
    }

}
