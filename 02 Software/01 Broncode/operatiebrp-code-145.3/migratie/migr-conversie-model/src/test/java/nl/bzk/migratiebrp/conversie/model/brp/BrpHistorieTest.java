/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie.Builder;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import org.junit.Test;

public class BrpHistorieTest {

    private static BrpDatumTijd reg = new BrpDatumTijd(Calendar.getInstance().getTime());
    private static BrpDatum aanvang = new BrpDatum(20060101, null);
    private static BrpDatum einde = new BrpDatum(20090101, null);
    private static BrpDatumTijd verval = new BrpDatumTijd(Calendar.getInstance().getTime());

    public static BrpHistorie createInhoud(Integer aanvangGeldigheid, Integer eindeGeldigheid, Integer registratie, Integer verval) {
        return BrpStapelHelper.his(aanvangGeldigheid, eindeGeldigheid, registratie, verval);
    }

    public static BrpHistorie createdefaultInhoud() {
        return createInhoud(20000101, null, 20000101, null);
    }

    public static BrpHistorie createNietActueleInhoud() {
        return createInhoud(20000101, 20000202, 20000101, 20000202);
    }

    @Test(expected = NullPointerException.class)
    public void testGeenRegistratie() {
        new BrpHistorie(aanvang, einde, null, verval, new BrpCharacter('A'));
    }

    @Test
    public void testGeenOverlap() {
        final BrpHistorie een = createInhoud(20000101, 20020101, 20000101, 20020101);
        final BrpHistorie ander = createInhoud(19900101, 19950101, 19900101, 19950101);
        // ander valt er helemaal voor
        assertFalse(een.geldigheidOverlapt(ander));
        // een valt er helemaal na
        assertFalse(ander.geldigheidOverlapt(een));
        final BrpHistorie eenZonderEind = BrpStapelHelper.his(20010101);
        assertFalse(eenZonderEind.geldigheidOverlapt(ander));
        assertFalse(ander.geldigheidOverlapt(eenZonderEind));
    }

    @Test
    public void testOverlap() {
        final BrpHistorie een = createInhoud(20000101, 20020101, 20000101, 20020101);
        final BrpHistorie ander = createInhoud(19900101, 20010101, 19900101, 20010101);
        // valt er gedeeltelijk voor
        assertTrue(een.geldigheidOverlapt(ander));
        assertTrue(ander.geldigheidOverlapt(een));

        // valt er helemaal in
        final BrpHistorie anderIn = createInhoud(20010101, 20010601, 20000601, 20000601);
        assertTrue(een.geldigheidOverlapt(anderIn));
        assertTrue(anderIn.geldigheidOverlapt(een));

        // valt er gedeeltelijk na
        final BrpHistorie anderInEnNa = createInhoud(20010101, 20050601, 20000601, 20050601);
        assertTrue(een.geldigheidOverlapt(anderInEnNa));
        assertTrue(anderInEnNa.geldigheidOverlapt(een));

        // valt er in, zonder einde
        final BrpHistorie anderInZonderEind = createInhoud(20010101, null, 20000601, null);
        assertTrue(een.geldigheidOverlapt(anderInZonderEind));
        assertTrue(anderInZonderEind.geldigheidOverlapt(een));
    }

    @Test
    public void testOverlapMetGelijkeDatums() {
        final BrpHistorie een = createInhoud(20000101, 20020101, 20000101, 20020101);
        final BrpHistorie ander = createInhoud(19900101, 20000101, 19900101, 20000101);
        // begint op einddatum van ander
        assertFalse(een.geldigheidOverlapt(ander));
        assertFalse(ander.geldigheidOverlapt(een));
    }

    @Test
    public void testOverlapGelijkeHistories() {
        final BrpHistorie een = createInhoud(20000101, 20020101, 20000101, 20020101);
        assertTrue(een.geldigheidOverlapt(een));
    }

    @Test
    public void testIsActueel() {
        BrpHistorie his1 = createInhoud(20000101, 20020101, 20000101, 20020101);
        BrpHistorie his2 = createInhoud(20000101, 20020101, 20000101, null);
        BrpHistorie his3 = createInhoud(20000101, null, 20000101, null);
        assertFalse(his1.isActueel());
        assertFalse(his2.isActueel());
        assertTrue(his3.isActueel());
    }

    @Test(expected = IllegalStateException.class)
    public void testLaatVervallenAlVervallen() {
        BrpHistorie his1 = createInhoud(20000101, 20020101, 20000101, 20020101);
        his1.laatVervallen(new BrpDatumTijd(Calendar.getInstance().getTime()));
    }

    @Test
    public void testLaatVervallen() {
        BrpHistorie his1 = createInhoud(20000101, null, 20000101, null);
        assertFalse(his1.isVervallen());
        his1 = his1.laatVervallen(new BrpDatumTijd(Calendar.getInstance().getTime()));
        assertTrue(his1.isVervallen());
    }

    @Test
    public void testBuilder() {
        BrpHistorie.Builder builder;
        BrpHistorie his1 = createInhoud(20000101, null, 20000101, null);
        builder = new Builder(his1);
        BrpHistorie b = builder.build();
        assertFalse(b.isVervallen());
        assertEquals(20000101, b.getDatumAanvangGeldigheid().getWaarde().intValue());
        assertEquals(1670623744, b.getDatumTijdRegistratie().getWaarde().intValue());
        builder = new Builder();
        builder.setDatumTijdRegistratie(reg);
        builder.setDatumAanvangGeldigheid(aanvang);
        builder.setDatumEindeGeldigheid(einde);
        builder.setDatumTijdVerval(verval);
        builder.setNadereAanduidingVerval(new BrpCharacter('A'));
        b = builder.build();
        assertTrue(b.isVervallen());
        assertEquals(20060101, b.getDatumAanvangGeldigheid().getWaarde().intValue());
        assertEquals(reg.getWaarde().intValue(), b.getDatumTijdRegistratie().getWaarde().intValue());
        assertEquals('A', b.getNadereAanduidingVerval().getWaarde().charValue());

    }
}
