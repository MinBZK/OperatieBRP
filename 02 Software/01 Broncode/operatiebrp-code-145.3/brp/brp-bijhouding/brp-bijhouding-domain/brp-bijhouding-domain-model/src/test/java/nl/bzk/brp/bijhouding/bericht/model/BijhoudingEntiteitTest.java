/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class BijhoudingEntiteitTest {

    @Test(expected = IllegalArgumentException.class)
    public void voegFormeleHistorieToe() throws Exception {
        PersoonAdresHistorie his = mock(PersoonAdresHistorie.class);

        BijhoudingEntiteit.voegFormeleHistorieToe(his,null,null);
    }


    @Test
    public void voegIndicatieHistorieMaterieelToeTest(){
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonIndicatie indicatie = new PersoonIndicatie(persoon, SoortIndicatie.ONDER_CURATELE);
        PersoonIndicatieHistorie his = new PersoonIndicatieHistorie(indicatie,true);
        final Timestamp timestamp = maakDatumTijd(2013, 8, 1);
        final BRPActie actie = maakActie(maakAdministratieveHandling(timestamp));
        his.setDatumAanvangGeldigheid(20010101);
        his.setActieInhoud(actie);
        his.setDatumTijdRegistratie(timestamp);
        BijhoudingEntiteit.voegPersoonIndicatieHistorieToe(his,actie,20010101,indicatie.getPersoonIndicatieHistorieSet());
        assertEquals(1,indicatie.getPersoonIndicatieHistorieSet().size());

        PersoonIndicatieHistorie his2 = new PersoonIndicatieHistorie(indicatie,true);
        final Timestamp timestamp2 = maakDatumTijd(2013, 8, 1);
        final BRPActie actie2 = maakActie(maakAdministratieveHandling(timestamp2));
        his2.setDatumAanvangGeldigheid(20020101);
        his2.setActieInhoud(actie2);
        his2.setDatumTijdRegistratie(timestamp2);
        BijhoudingEntiteit.voegPersoonIndicatieHistorieToe(his2,actie2,20020101,indicatie.getPersoonIndicatieHistorieSet());
        assertEquals(3,indicatie.getPersoonIndicatieHistorieSet().size());
    }

    @Test
    public void voegIndicatieHistorieFormeelToeTest() throws InterruptedException {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonIndicatie indicatie = new PersoonIndicatie(persoon, SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING);
        PersoonIndicatieHistorie his = new PersoonIndicatieHistorie(indicatie,true);

        final Timestamp timestamp = maakDatumTijd(2013, 8, 1);
        final BRPActie actie = maakActie(maakAdministratieveHandling(timestamp));
        final Set<PersoonIndicatieHistorie> persoonIndicatieHistorieSet = indicatie.getPersoonIndicatieHistorieSet();

        BijhoudingEntiteit.voegPersoonIndicatieHistorieToe(his,actie,null, persoonIndicatieHistorieSet);
        assertEquals(1, persoonIndicatieHistorieSet.size());
        PersoonIndicatieHistorie his2 = new PersoonIndicatieHistorie(indicatie,true);
        final Timestamp timestamp2 = maakDatumTijd(2016, 4, 2);
        final BRPActie actie2 = maakActie(maakAdministratieveHandling(timestamp2));

        BijhoudingEntiteit.voegPersoonIndicatieHistorieToe(his2,actie2,null, persoonIndicatieHistorieSet);
        assertEquals(2, persoonIndicatieHistorieSet.size());
    }


    protected static AdministratieveHandeling maakAdministratieveHandling(final Timestamp datumTijdRegistratie) {
        return new AdministratieveHandeling(new Partij("test", "000000"), SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND, datumTijdRegistratie);
    }

    protected static Timestamp maakDatumTijd(final int jaar, final int maand, final int dag) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, jaar);
        calendar.set(Calendar.MONTH, maand - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dag);
        return new Timestamp(calendar.getTimeInMillis());
    }
    protected static BRPActie maakActie(final AdministratieveHandeling administratieveHandeling) {
        final BRPActie result =
                new BRPActie(
                        SoortActie.REGISTRATIE_GEBORENE,
                        administratieveHandeling,
                        administratieveHandeling.getPartij(),
                        administratieveHandeling.getDatumTijdRegistratie());
        result.setId(new Random().nextLong());
        return result;
    }
}
