/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import org.junit.Test;

public final class BrpStapelTest {

    @Test
    public void testBevatActueel() {
        final BrpStapel<BrpNationaliteitInhoud> stapelActueel = maakStapel(BrpStapelHelper.his(20010101, 20080101000000L));
        assertTrue(stapelActueel.bevatActueel());

        final BrpStapel<BrpNationaliteitInhoud> stapelEindig = maakStapel(BrpStapelHelper.his(20010101, 20080101, 20080101000000L, null));
        assertFalse(stapelEindig.bevatActueel());

        final BrpStapel<BrpNationaliteitInhoud> stapelVervallen = maakStapel(BrpStapelHelper.his(20010101, null, 20080101000000L, 20100101000000L));
        assertFalse(stapelVervallen.bevatActueel());

        final BrpStapel<BrpNationaliteitInhoud> stapelEindigEnVervallen =
                maakStapel(BrpStapelHelper.his(20010101, 20080101, 20080101000000L, 20100101000000L));
        assertFalse(stapelEindigEnVervallen.bevatActueel());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetActueelBijMeerdereActuele() {
        BrpStapel<BrpNationaliteitInhoud> stapel = maakStapel(2, 1);
        stapel.bevatActueel();
    }

    @Test
    public void testGetActueelOpActieVervalBij2Actuelen() {
        BrpStapel<BrpNationaliteitInhoud> stapel = maakStapel(2, 1);
        assertNotNull(stapel.getActueelOpActieVerval());
    }

    @Test
    public void testGetActueelOpActieVervalBij1Actuele() {
        BrpStapel<BrpNationaliteitInhoud> stapel = maakStapel(1, 2);
        assertNotNull(stapel.getActueelOpActieVerval());
    }

    @Test
    public void testGetActueelOpActieVervalBij0Actuele() {
        BrpStapel<BrpNationaliteitInhoud> stapel = maakStapel(0, 5);
        assertNull(stapel.getActueelOpActieVerval());
    }

    @Test
    public void testSorteringMaterieel() {
        final BrpPartijCode partij = new BrpPartijCode("051901");
        final BrpDatum dag = new BrpDatum(20160101, null);
        final BrpDatum dag2 = new BrpDatum(20170101, null);
        final BrpDatumTijd tsReg = BrpDatumTijd.fromDatumTijd(20160101010000L, null);
        final BrpDatumTijd tsReg2 = BrpDatumTijd.fromDatumTijd(20170101010000L, null);
        final BrpDatumTijd tsReg3 = BrpDatumTijd.fromDatumTijd(20170101020000L, null);

        // Migratie model maakt geen onderscheid of een bepaalde inhoude materieel is of niet.
        final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(null, null);
        // Alle historie
        final BrpHistorie historieActueel = new BrpHistorie(dag2, null, tsReg3, null, null);
        final BrpHistorie historieEindeGeldigheid2 = new BrpHistorie(dag2, dag2, tsReg2, null, null);
        final BrpHistorie historieVerval2 = new BrpHistorie(dag2, null, tsReg2, tsReg3, null);
        final BrpHistorie historieEindeGeldigheid1 = new BrpHistorie(dag, dag2, tsReg2, null, null);
        final BrpHistorie historieVerval1 = new BrpHistorie(dag, null, tsReg, tsReg2, null);
        // Acties
        final BrpActie actie1 = new BrpActie(1L, BrpSoortActieCode.CONVERSIE_GBA, partij, tsReg, null, null, 0, null);
        final BrpActie actie2 = new BrpActie(2L, BrpSoortActieCode.CONVERSIE_GBA, partij, tsReg2, null, null, 0, null);
        final BrpActie actieActueel = new BrpActie(3L, BrpSoortActieCode.CONVERSIE_GBA, partij, tsReg3, null, null, 0, null);
        // BRP Groepen
        final BrpGroep<BrpIdentificatienummersInhoud> verval1 = new BrpGroep<>(inhoud, historieVerval1, actie1, actie2, null);
        final BrpGroep<BrpIdentificatienummersInhoud> eindeGeldigheid1 = new BrpGroep<>(inhoud, historieEindeGeldigheid1, actie1, null, actie2);
        final BrpGroep<BrpIdentificatienummersInhoud> verval2 = new BrpGroep<>(inhoud, historieVerval2, actie2, actieActueel, null);
        final BrpGroep<BrpIdentificatienummersInhoud> eindeGeldigheid2 = new BrpGroep<>(inhoud, historieEindeGeldigheid2, actie2, null, actieActueel);
        final BrpGroep<BrpIdentificatienummersInhoud> actueel = new BrpGroep<>(inhoud, historieActueel, actieActueel, null, null);
        // BRP Stapel
        final BrpStapel<BrpIdentificatienummersInhoud> stapel = new BrpStapel<>(Arrays.asList(verval1, verval2, actueel, eindeGeldigheid1, eindeGeldigheid2));
        stapel.sorteer();
        final List<BrpGroep<BrpIdentificatienummersInhoud>> groepen = stapel.getGroepen();
        assertEquals(actueel, groepen.get(0));
        assertEquals(eindeGeldigheid2, groepen.get(1));
        assertEquals(verval2, groepen.get(2));
        assertEquals(eindeGeldigheid1, groepen.get(3));
        assertEquals(verval1, groepen.get(4));
    }

    @Test
    public void testImmutabillityStapel() {
        final BrpStapel<BrpNationaliteitInhoud> stapel = maakStapel(1, 0);
        final List<BrpGroep<BrpNationaliteitInhoud>> groepen = stapel.getGroepen();
        assertEquals(1, groepen.size());

        final BrpNationaliteitInhoud inhoud =
                new BrpNationaliteitInhoud(new BrpNationaliteitCode("0002"), null, null, null, null, null, null);
        final BrpActie actieInhoud = BrpStapelHelper.act(1, 20080101);
        BrpGroep<BrpNationaliteitInhoud> groep = new BrpGroep<>(inhoud, BrpHistorieTest.createdefaultInhoud(), actieInhoud, null, null);
        groepen.add(groep);
        assertEquals(2, groepen.size());
        assertEquals(1, stapel.getGroepen().size());
    }


    private BrpStapel<BrpNationaliteitInhoud> maakStapel(final BrpHistorie historie) {
        final List<BrpGroep<BrpNationaliteitInhoud>> groepen = new ArrayList<>();
        final BrpNationaliteitInhoud inhoud =
                new BrpNationaliteitInhoud(new BrpNationaliteitCode("0002"), null, null, null, null, null, null);
        final BrpActie actieInhoud = BrpStapelHelper.act(1, 20080101);
        final BrpGroep<BrpNationaliteitInhoud> nationaliteit = new BrpGroep<>(inhoud, historie, actieInhoud, null, null);
        groepen.add(nationaliteit);
        return new BrpStapel<>(groepen);
    }

    private BrpStapel<BrpNationaliteitInhoud> maakStapel(int aantalActuelen, int aantalVervallen) {
        final List<BrpGroep<BrpNationaliteitInhoud>> groepen = new ArrayList<>();
        final BrpNationaliteitInhoud inhoud =
                new BrpNationaliteitInhoud(new BrpNationaliteitCode("0002"), null, null, null, null, null, null);
        final BrpActie actieInhoud = BrpStapelHelper.act(1, 20080101);
        for (int i = 0; i < aantalActuelen; i++) {
            final BrpGroep<BrpNationaliteitInhoud> nationaliteit = new BrpGroep<>(inhoud, BrpHistorieTest.createdefaultInhoud(), actieInhoud, null, null);
            groepen.add(nationaliteit);
        }
        for (int i = 0; i < aantalVervallen; i++) {
            final BrpGroep<BrpNationaliteitInhoud> nationaliteit =
                    new BrpGroep<>(inhoud, BrpHistorieTest.createNietActueleInhoud(), actieInhoud, null, null);
            groepen.add(nationaliteit);
        }
        return new BrpStapel<>(groepen);
    }
}
