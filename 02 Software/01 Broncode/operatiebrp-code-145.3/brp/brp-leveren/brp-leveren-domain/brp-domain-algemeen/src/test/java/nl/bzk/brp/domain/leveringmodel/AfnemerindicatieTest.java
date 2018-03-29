/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereAanduidingVerval;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * AfnemerindicatieTest.
 */
public class AfnemerindicatieTest {

    @Test
    public void testEquals() {
        final Afnemerindicatie afnemerindicatie1 = Afnemerindicatie.maakBuilder().metAfnemerCode("000001").metVoorkomenSleutel(1L).build();
        final Afnemerindicatie afnemerindicatie2 = Afnemerindicatie.maakBuilder().metAfnemerCode("000001").metVoorkomenSleutel(1L).build();
        Assert.assertEquals(afnemerindicatie1, afnemerindicatie2);
        Assert.assertEquals(afnemerindicatie1, afnemerindicatie1);
        Assert.assertEquals(afnemerindicatie1.hashCode(), afnemerindicatie2.hashCode());

        final Afnemerindicatie afnemerindicatie3 = Afnemerindicatie.maakBuilder().metAfnemerCode("000001").metVoorkomenSleutel(1L).metPersoonId(1L).build();
        final Afnemerindicatie afnemerindicatie4 = Afnemerindicatie.maakBuilder().metAfnemerCode("000001").metVoorkomenSleutel(1L).metPersoonId(2L).build();
        Assert.assertNotEquals(afnemerindicatie3, afnemerindicatie4);

        final Afnemerindicatie afnemerindicatie5 = Afnemerindicatie.maakBuilder().metAfnemerCode("000001").metVoorkomenSleutel(1L).build();
        final Afnemerindicatie afnemerindicatie6 = Afnemerindicatie.maakBuilder().metAfnemerCode("000001").metVoorkomenSleutel(2L).build();
        Assert.assertNotEquals(afnemerindicatie5, afnemerindicatie6);

        final Afnemerindicatie afnemerindicatie7 = Afnemerindicatie.maakBuilder().metAfnemerCode("000001").metVoorkomenSleutel(1L).metPersoonId(1L)
                .metLeveringsAutorisatieId(1).build();
        final Afnemerindicatie afnemerindicatie8 = Afnemerindicatie.maakBuilder().metAfnemerCode("000001").metVoorkomenSleutel(1L).metPersoonId(
                1L).metLeveringsAutorisatieId(1).build();
        Assert.assertEquals(afnemerindicatie7, afnemerindicatie8);

        final Afnemerindicatie afnemerindicatie9 = Afnemerindicatie.maakBuilder().metAfnemerCode("000001").metVoorkomenSleutel(1L).metPersoonId(1L)
                .metLeveringsAutorisatieId(1).build();
        final Afnemerindicatie afnemerindicatie10 = Afnemerindicatie.maakBuilder().metAfnemerCode("000001").metVoorkomenSleutel(1L).metPersoonId(
                1L).metLeveringsAutorisatieId(2).build();
        Assert.assertNotEquals(afnemerindicatie9, afnemerindicatie10);

        Assert.assertNotEquals(afnemerindicatie9, "x");
        Assert.assertNotEquals(afnemerindicatie9, null);
    }

    @Test
    public void testBuild() {
        final ZonedDateTime tsReg = DatumUtil.nuAlsZonedDateTime();
        final ZonedDateTime tsVerval = DatumUtil.nuAlsZonedDateTime();
        final String afnemerCode = "000001";
        final Long persoonId = 2L;
        final Integer datumAanvangVolgen = 20001010;
        final Integer datumEindeVolgen = 20001010;
        final int leveringsAutorisatieId = 10;
        final Long voorkomenSleutel = 1L;
        final Long objectSleutel = 2L;
        final Afnemerindicatie afnemerindicatie =
                Afnemerindicatie.maakBuilder()
                        .metAfnemerCode(afnemerCode).metTijdstipRegistratie(tsReg).metPersoonId(persoonId).metTijdstipVerval(tsVerval)
                        .metDatumAanvangMaterielePeriode(datumAanvangVolgen).metDatumEindeVolgen(datumEindeVolgen)
                        .metLeveringsAutorisatieId(leveringsAutorisatieId).metNadereAanduidingVerval(NadereAanduidingVerval.S)
                        .metVoorkomenSleutel(voorkomenSleutel).metObjectSleutel(objectSleutel)
                        .build();
        Assert.assertEquals(afnemerCode, afnemerindicatie.getAfnemerCode());
        Assert.assertEquals(tsReg, afnemerindicatie.getTijdstipRegistratie());
        Assert.assertEquals(tsVerval, afnemerindicatie.getTijdstipVerval());
        Assert.assertEquals(persoonId, afnemerindicatie.getPersoonId());
        Assert.assertEquals(datumAanvangVolgen, afnemerindicatie.getDatumAanvangMaterielePeriode());
        Assert.assertEquals(datumEindeVolgen, afnemerindicatie.getDatumEindeVolgen());
        Assert.assertEquals(leveringsAutorisatieId, afnemerindicatie.getLeveringsAutorisatieId());
        Assert.assertEquals(NadereAanduidingVerval.S, afnemerindicatie.getNadereAanduidingVerval());
        Assert.assertEquals(voorkomenSleutel, afnemerindicatie.getVoorkomenSleutel());
        Assert.assertEquals(objectSleutel, afnemerindicatie.getObjectSleutel());

    }
}
