/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import org.junit.Test;

public class BrpSamengesteldeNaamInhoudTest {

    public static final String VOORNAMEN = "Jan Pieter";
    public static final String VOORVOEGSEL = "van";
    public static final String TITEL = "Baron";
    public static final String STAMNAAM = "Adel";

    public static BrpSamengesteldeNaamInhoud createInhoud() {
        return createInhoud(VOORNAMEN, VOORVOEGSEL, TITEL, STAMNAAM);
    }

    public static BrpSamengesteldeNaamInhoud createInhoud(String voornamen, String voorvoegsel, String titel, String stamnaam) {
        BrpPredicaatCode predicaatCode = null;

        BrpString vnamen = voornamen != null ? new BrpString(voornamen) : null;
        BrpString vvoegsel = new BrpString(voorvoegsel);
        BrpCharacter scheidingsteken = new BrpCharacter(' ');
        BrpAdellijkeTitelCode adellijkeTitelCode = new BrpAdellijkeTitelCode(titel);
        BrpString gNaamStam = stamnaam != null ? new BrpString(stamnaam) : null;
        BrpBoolean indNamenReeks = null;
        BrpBoolean indAfgeleid = null;
        return new BrpSamengesteldeNaamInhoud(
                predicaatCode,
                vnamen,
                vvoegsel,
                scheidingsteken,
                adellijkeTitelCode,
                gNaamStam,
                indNamenReeks,
                indAfgeleid);
    }

    public static BrpStapel<BrpSamengesteldeNaamInhoud> createStapel(String voornamen, String voorvoegsel, String titel, String stamnaam) {
        List<BrpGroep<BrpSamengesteldeNaamInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpSamengesteldeNaamInhoud>
                groep =
                new BrpGroep<>(createInhoud(voornamen, voorvoegsel, titel, stamnaam), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    public static BrpStapel<BrpSamengesteldeNaamInhoud> createStapel() {
        List<BrpGroep<BrpSamengesteldeNaamInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpSamengesteldeNaamInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    @Test
    public void testValideer() throws Exception {

    }

    @Test
    public void testIsLeeg() throws Exception {

    }

    @Test
    public void testGetPredicaatCode() throws Exception {

    }

    @Test
    public void testGetVoornamen() throws Exception {

    }

    @Test
    public void testGetVoorvoegsel() throws Exception {

    }

    @Test
    public void testGetScheidingsteken() throws Exception {

    }

    @Test
    public void testGetAdellijkeTitelCode() throws Exception {

    }

    @Test
    public void testGetGeslachtsnaamstam() throws Exception {

    }

    @Test
    public void testGetIndicatieNamenreeks() throws Exception {

    }

    @Test
    public void testGetIndicatieAfgeleid() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {

    }

    @Test
    public void testHashCode() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }
}
