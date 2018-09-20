/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid.Builder;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentiteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoudTest;

import org.junit.Test;

public class BrpBetrokkenheidTest {
    BrpBetrokkenheid.Builder bBuilder = new Builder();

    @Test
    public void testGetRol() throws Exception {
        BrpBetrokkenheid b = BrpBetrokkenheidTest.maakBetrokkenheid(BrpSoortBetrokkenheidCode.KIND);
        assertEquals("K", b.getRol().getWaarde());
    }

    @Test
    public void testGetIdentificatienummersStapel() throws Exception {
        BrpIdentificatienummersInhoud inh = new BrpIdentificatienummersInhoud(new BrpLong(1234L), null);
        BrpGroep<BrpIdentificatienummersInhoud> groep = new BrpGroep<>(inh, createHistory(), null, null, null);
        List<BrpGroep<BrpIdentificatienummersInhoud>> groepen = new ArrayList<>();
        groepen.add(groep);
        BrpStapel<BrpIdentificatienummersInhoud> iNumStapel = new BrpStapel<>(groepen);
        BrpBetrokkenheid b = BrpBetrokkenheidTest.maakBetrokkenheidINum(BrpSoortBetrokkenheidCode.KIND, iNumStapel);
        assertEquals(1, b.getIdentificatienummersStapel().size());
        assertEquals(1234, b.getIdentificatienummersStapel().getActueel().getInhoud().getAdministratienummer().getWaarde().intValue());
        bBuilder = new Builder();
        bBuilder = bBuilder.rol(BrpSoortBetrokkenheidCode.KIND);
        assertEquals(bBuilder.identificatienummersStapel(iNumStapel).build(), b);
    }

    @Test
    public void testGetGeslachtsaanduidingStapel() throws Exception {
        BrpGeslachtsaanduidingInhoud inh = new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN);
        BrpGroep<BrpGeslachtsaanduidingInhoud> groep = new BrpGroep<>(inh, createHistory(), null, null, null);
        List<BrpGroep<BrpGeslachtsaanduidingInhoud>> groepen = new ArrayList<>();
        groepen.add(groep);
        BrpStapel<BrpGeslachtsaanduidingInhoud> stapel = new BrpStapel<>(groepen);
        BrpBetrokkenheid b = BrpBetrokkenheidTest.maakBetrokkenheidGA(BrpSoortBetrokkenheidCode.KIND, stapel);
        assertEquals(1, b.getGeslachtsaanduidingStapel().size());
        assertEquals("M", b.getGeslachtsaanduidingStapel().getActueel().getInhoud().getGeslachtsaanduidingCode().getWaarde());
        bBuilder = new Builder();
        bBuilder = bBuilder.rol(BrpSoortBetrokkenheidCode.KIND);
        assertEquals(bBuilder.geslachtsaanduidingStapel(stapel).build(), b);

    }

    @Test
    public void testGetGeboorteStapel() throws Exception {
        BrpGeboorteInhoud inh = BrpGeboorteInhoudTest.getBrpGeboorteInhoud();
        BrpGroep<BrpGeboorteInhoud> groep = new BrpGroep<>(inh, createHistory(), null, null, null);
        List<BrpGroep<BrpGeboorteInhoud>> groepen = new ArrayList<>();
        groepen.add(groep);
        BrpStapel<BrpGeboorteInhoud> stapel = new BrpStapel<>(groepen);
        BrpBetrokkenheid b = BrpBetrokkenheidTest.maakBetrokkenheidGB(BrpSoortBetrokkenheidCode.KIND, stapel);
        assertEquals(1, b.getGeboorteStapel().size());
        assertEquals(BrpGeboorteInhoudTest.JAN_01_1980, b.getGeboorteStapel().getActueel().getInhoud().getGeboortedatum().getWaarde().intValue());
        bBuilder = new Builder();
        bBuilder = bBuilder.rol(BrpSoortBetrokkenheidCode.KIND);
        assertEquals(bBuilder.geboorteStapel(stapel).build(), b);
    }

    @Test
    public void testGetSamengesteldeNaamStapel() throws Exception {
        BrpSamengesteldeNaamInhoud inh = BrpSamengesteldeNaamInhoudTest.createInhoud();
        BrpGroep<BrpSamengesteldeNaamInhoud> groep = new BrpGroep<>(inh, createHistory(), null, null, null);
        List<BrpGroep<BrpSamengesteldeNaamInhoud>> groepen = new ArrayList<>();
        groepen.add(groep);
        BrpStapel<BrpSamengesteldeNaamInhoud> stapel = new BrpStapel<>(groepen);
        BrpBetrokkenheid b = BrpBetrokkenheidTest.maakBetrokkenheidSamenGEsteldeNaam(BrpSoortBetrokkenheidCode.KIND, stapel);
        assertEquals(1, b.getSamengesteldeNaamStapel().size());
        assertEquals(BrpSamengesteldeNaamInhoudTest.STAMNAAM, b.getSamengesteldeNaamStapel().getActueel().getInhoud().getGeslachtsnaamstam().getWaarde());
        bBuilder = new Builder();
        bBuilder = bBuilder.rol(BrpSoortBetrokkenheidCode.KIND);
        assertEquals(bBuilder.samengesteldeNaamStapel(stapel).build(), b);
    }

    @Test
    public void testGetOuderlijkGezagStapel() throws Exception {
        BrpOuderlijkGezagInhoud inh = BrpOuderlijkGezagInhoudTest.createInhoud(Boolean.TRUE);
        BrpGroep<BrpOuderlijkGezagInhoud> groep = new BrpGroep<>(inh, createHistory(), null, null, null);
        List<BrpGroep<BrpOuderlijkGezagInhoud>> groepen = new ArrayList<>();
        groepen.add(groep);
        BrpStapel<BrpOuderlijkGezagInhoud> stapel = new BrpStapel<>(groepen);
        BrpBetrokkenheid b = BrpBetrokkenheidTest.maakBetrokkenheidOuderlijkGezag(BrpSoortBetrokkenheidCode.OUDER, stapel);
        assertEquals(1, b.getOuderlijkGezagStapel().size());
        assertTrue(b.getOuderlijkGezagStapel().getActueel().getInhoud().getOuderHeeftGezag().getWaarde().booleanValue());
        bBuilder = new Builder();
        bBuilder = bBuilder.rol(BrpSoortBetrokkenheidCode.OUDER);
        assertEquals(bBuilder.ouderlijkGezagStapel(stapel).build(), b);
    }

    @Test
    public void testGetOuderStapel() throws Exception {
        BrpOuderInhoud inh = BrpOuderInhoudTest.createInhoud(Boolean.TRUE, Boolean.FALSE);
        BrpGroep<BrpOuderInhoud> groep = new BrpGroep<>(inh, createHistory(), null, null, null);
        List<BrpGroep<BrpOuderInhoud>> groepen = new ArrayList<>();
        groepen.add(groep);
        BrpStapel<BrpOuderInhoud> stapel = new BrpStapel<>(groepen);
        BrpBetrokkenheid b = BrpBetrokkenheidTest.maakBetrokkenheidOuderStapel(BrpSoortBetrokkenheidCode.OUDER, stapel);
        assertEquals(1, b.getOuderStapel().size());
        assertTrue(b.getOuderStapel().getActueel().getInhoud().getIndicatieOuder().getWaarde().booleanValue());
        assertFalse(b.getOuderStapel().getActueel().getInhoud().getIndicatieOuderUitWieKindIsGeboren().getWaarde().booleanValue());
        bBuilder = new Builder();
        bBuilder = bBuilder.rol(BrpSoortBetrokkenheidCode.OUDER);
        assertEquals(bBuilder.ouderStapel(stapel).build(), b);
    }

    @Test
    public void testGetIdentiteitStapel() throws Exception {
        BrpIdentiteitInhoud inh = BrpIdentiteitInhoud.IDENTITEIT;
        BrpGroep<BrpIdentiteitInhoud> groep = new BrpGroep<>(inh, createHistory(), null, null, null);
        List<BrpGroep<BrpIdentiteitInhoud>> groepen = new ArrayList<>();
        groepen.add(groep);
        BrpStapel<BrpIdentiteitInhoud> stapel = new BrpStapel<>(groepen);
        BrpBetrokkenheid b = BrpBetrokkenheidTest.maakIdentitieitStapel(BrpSoortBetrokkenheidCode.OUDER, stapel);
        assertEquals(1, b.getIdentiteitStapel().size());
        assertFalse(b.getIdentiteitStapel().getActueel().getInhoud().isLeeg());
        assertEquals("BrpIdentiteitInhoud[]", b.getIdentiteitStapel().getActueel().getInhoud().toString());
        bBuilder = new Builder();
        bBuilder = bBuilder.rol(BrpSoortBetrokkenheidCode.OUDER);
        assertEquals(bBuilder.identiteitStapel(stapel).build(), b);
    }

    @Test
    public void testEquals() throws Exception {
        BrpBetrokkenheid b = maakBetrokkenheid(BrpSoortBetrokkenheidCode.KIND);
        BrpBetrokkenheid c = maakBetrokkenheid(BrpSoortBetrokkenheidCode.OUDER);
        BrpBetrokkenheid d = maakBetrokkenheid(BrpSoortBetrokkenheidCode.OUDER);
        assertTrue(b.equals(geefZelfdeTerug(b)));
        assertFalse(b.equals(geefZelfdeTerug(b).getRol()));
        assertFalse(b.equals(geefZelfdeTerug(b).getRol()));
        assertFalse(b.equals(c));
        assertTrue(c.equals(d));

    }

    @Test
    public void testHashCode() throws Exception {
        BrpBetrokkenheid c = maakBetrokkenheid(BrpSoortBetrokkenheidCode.OUDER);
        BrpBetrokkenheid d = maakBetrokkenheid(BrpSoortBetrokkenheidCode.OUDER);
        assertTrue(c.hashCode() == d.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        BrpBetrokkenheid c = maakBetrokkenheid(BrpSoortBetrokkenheidCode.OUDER);
        BrpBetrokkenheid d = maakBetrokkenheid(BrpSoortBetrokkenheidCode.OUDER);
        assertTrue(c.toString().equals(d.toString()));

    }

    @Test
    public void testBuilder() {
        BrpBetrokkenheid.Builder b = new Builder(maakBetrokkenheid(BrpSoortBetrokkenheidCode.OUDER));
        BrpBetrokkenheid result = b.build();
        assertNull(result.getOuderStapel());
        assertEquals("O", result.getRol().getWaarde());
    }

    private BrpBetrokkenheid geefZelfdeTerug(BrpBetrokkenheid b) {
        return b;
    }

    private BrpHistorie createHistory() {
        return new BrpHistorie(new BrpDatumTijd(Calendar.getInstance().getTime()), null, null);
    }

    public static List<BrpBetrokkenheid> maaklijstMetBetrokkenheden(int aantalKinderen,boolean creerOuderStapelOpEerste,boolean creerOuderStapelOpTweede){
        List<BrpBetrokkenheid> result = new ArrayList<>();
        for(int i=0;i<aantalKinderen;i++){
            BrpBetrokkenheid betrokkenheid = maakBetrokkenheid(BrpSoortBetrokkenheidCode.KIND);
            if(i==0 && creerOuderStapelOpEerste || i==1 && creerOuderStapelOpTweede){
                BrpBetrokkenheid.Builder b = new Builder(betrokkenheid);
                b.ouderStapel(BrpOuderInhoudTest.createStapel());
                betrokkenheid = b.build();
            }
            result.add(betrokkenheid);
        }
        return result;
    }

    public static BrpBetrokkenheid maakBetrokkenheid(BrpSoortBetrokkenheidCode rol) {
        return maakBetrokkenheidMetNullStapels(rol, null, null, null, null, null, null, null);
    }

    public static BrpBetrokkenheid maakBetrokkenheidGA(BrpSoortBetrokkenheidCode rol, BrpStapel<BrpGeslachtsaanduidingInhoud> stapel) {
        return maakBetrokkenheidMetNullStapels(rol, null, stapel, null, null, null, null, null);
    }

    public static BrpBetrokkenheid maakBetrokkenheidINum(BrpSoortBetrokkenheidCode rol, BrpStapel<BrpIdentificatienummersInhoud> stapel) {
        return maakBetrokkenheidMetNullStapels(rol, stapel, null, null, null, null, null, null);
    }

    public static BrpBetrokkenheid maakBetrokkenheidGB(BrpSoortBetrokkenheidCode rol, BrpStapel<BrpGeboorteInhoud> stapel) {
        return maakBetrokkenheidMetNullStapels(rol, null, null, stapel, null, null, null, null);
    }

    public static BrpBetrokkenheid maakBetrokkenheidSamenGEsteldeNaam(BrpSoortBetrokkenheidCode rol, BrpStapel<BrpSamengesteldeNaamInhoud> stapel) {
        return maakBetrokkenheidMetNullStapels(rol, null, null, null, stapel, null, null, null);
    }
    public static BrpBetrokkenheid maakBetrokkenheidINumMetSnaam(BrpSoortBetrokkenheidCode rol, BrpStapel<BrpIdentificatienummersInhoud> stapel,BrpStapel<BrpSamengesteldeNaamInhoud> stapelNaam) {
        return maakBetrokkenheidMetNullStapels(rol, stapel, null, null, stapelNaam, null, null, null);
    }

    public static BrpBetrokkenheid maakBetrokkenheidOuderlijkGezag(BrpSoortBetrokkenheidCode rol, BrpStapel<BrpOuderlijkGezagInhoud> stapel) {
        return maakBetrokkenheidMetNullStapels(rol, null, null, null, null, stapel, null, null);
    }

    public static BrpBetrokkenheid maakBetrokkenheidOuderStapel(BrpSoortBetrokkenheidCode rol, BrpStapel<BrpOuderInhoud> stapel) {
        return maakBetrokkenheidMetNullStapels(rol, null, null, null, null, null, stapel, null);
    }

    public static BrpBetrokkenheid maakIdentitieitStapel(BrpSoortBetrokkenheidCode rol, BrpStapel<BrpIdentiteitInhoud> stapel) {
        return maakBetrokkenheidMetNullStapels(rol, null, null, null, null, null, null, stapel);
    }

    public static BrpBetrokkenheid maakBetrokkenheidMetNullStapels(
        BrpSoortBetrokkenheidCode rol,
        BrpStapel<BrpIdentificatienummersInhoud> iNumStapel,
        BrpStapel<BrpGeslachtsaanduidingInhoud> mvStapel,
        BrpStapel<BrpGeboorteInhoud> geboorteStapel,
        BrpStapel<BrpSamengesteldeNaamInhoud> naamStapel,
        BrpStapel<BrpOuderlijkGezagInhoud> oGezagStapel,
        BrpStapel<BrpOuderInhoud> ouderStapel,
        BrpStapel<BrpIdentiteitInhoud> identiteitStapel)
    {
        return new BrpBetrokkenheid(rol, iNumStapel, mvStapel, geboorteStapel, oGezagStapel, naamStapel, ouderStapel, identiteitStapel);
    }
}
