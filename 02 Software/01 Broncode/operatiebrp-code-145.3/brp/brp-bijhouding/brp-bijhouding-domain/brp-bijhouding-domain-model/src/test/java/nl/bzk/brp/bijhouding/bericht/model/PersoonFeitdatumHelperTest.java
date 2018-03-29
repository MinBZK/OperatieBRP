/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Testen voor {@link PersoonFeitdatumHelper}.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersoonFeitdatumHelperTest {

    private BRPActie actie;
    private Timestamp nu;
    private int datumAanvangGeldigheid;

    @Before
    public void setup() {
        nu = new Timestamp(System.currentTimeMillis());
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(new Partij("IV", "000000"), SoortAdministratieveHandeling.GBA_INITIELE_VULLING, nu);
        actie = new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, administratieveHandeling.getPartij(), nu);
        datumAanvangGeldigheid = 19800101;
    }

    @Test
    public void testPersoonMigratieHistorie() {
        final int datumEindeGeldigheid = 20170201;
        final Persoon persoon = maakStandaardPersoon();
        final PersoonMigratieHistorie migratieHistorie = new PersoonMigratieHistorie(persoon, SoortMigratie.IMMIGRATIE);
        migratieHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        migratieHistorie.setActieInhoud(actie);
        migratieHistorie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        migratieHistorie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        persoon.addPersoonMigratieHistorie(migratieHistorie);

        final ActieElement actieElement = mock(ActieElement.class);
        when(actieElement.getDatumAanvangGeldigheid()).thenReturn(new DatumElement(datumEindeGeldigheid - 1));

        final PersoonFeitdatumHelper.Feitdatum
                feitDatumDieKomtNaOfGelijkMetDitActieElement =
                new PersoonFeitdatumHelper(BijhoudingPersoon.decorate(persoon)).getFeitDatumDieKomtNaOfGelijkMetDitActieElement(actieElement);
        assertNotNull(feitDatumDieKomtNaOfGelijkMetDitActieElement);
    }

    @Test
    public void testEindeGeldigheidOuderlijkGezag() {
        final int datumEindeGeldigheid = 20170201;
        final Persoon persoon = maakStandaardPersoon();
        final Persoon ouder = maakStandaardPersoon();
        final Relatie familie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie relatieHistorie = new RelatieHistorie(familie);
        relatieHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        relatieHistorie.setActieInhoud(actie);
        familie.addRelatieHistorie(relatieHistorie);
        final Betrokkenheid ikAlsKindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, familie);
        final BetrokkenheidHistorie ikAlsKindBetrokkenheidHistorie = new BetrokkenheidHistorie(ikAlsKindBetrokkenheid);
        ikAlsKindBetrokkenheidHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        ikAlsKindBetrokkenheidHistorie.setActieInhoud(actie);
        ikAlsKindBetrokkenheid.addBetrokkenheidHistorie(ikAlsKindBetrokkenheidHistorie);
        persoon.addBetrokkenheid(ikAlsKindBetrokkenheid);
        familie.addBetrokkenheid(ikAlsKindBetrokkenheid);

        final Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, familie);
        final BetrokkenheidHistorie ouderBetrokkenheidHistorie = new BetrokkenheidHistorie(ouderBetrokkenheid);
        ouderBetrokkenheidHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        ouderBetrokkenheidHistorie.setActieInhoud(actie);
        ouderBetrokkenheid.addBetrokkenheidHistorie(ouderBetrokkenheidHistorie);
        final BetrokkenheidOuderlijkGezagHistorie ouderBetrokkenheidOuderlijkGezagHistorie = new BetrokkenheidOuderlijkGezagHistorie(ouderBetrokkenheid, true);
        ouderBetrokkenheidOuderlijkGezagHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        ouderBetrokkenheidOuderlijkGezagHistorie.setActieInhoud(actie);
        ouderBetrokkenheidOuderlijkGezagHistorie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        ouderBetrokkenheidOuderlijkGezagHistorie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        ouderBetrokkenheid.addBetrokkenheidOuderlijkGezagHistorie(ouderBetrokkenheidOuderlijkGezagHistorie);

        persoon.addBetrokkenheid(ikAlsKindBetrokkenheid);
        ouder.addBetrokkenheid(ouderBetrokkenheid);
        familie.addBetrokkenheid(ouderBetrokkenheid);
        familie.addBetrokkenheid(ouderBetrokkenheid);

        final ActieElement actieElement = mock(ActieElement.class);
        when(actieElement.getDatumAanvangGeldigheid()).thenReturn(new DatumElement(datumEindeGeldigheid - 1));

        PersoonFeitdatumHelper.Feitdatum
                feitDatumDieKomtNaOfGelijkMetDitActieElement =
                new PersoonFeitdatumHelper(BijhoudingPersoon.decorate(persoon)).getFeitDatumDieKomtNaOfGelijkMetDitActieElement(actieElement);
        assertNotNull(feitDatumDieKomtNaOfGelijkMetDitActieElement);
        when(actieElement.getDatumAanvangGeldigheid()).thenReturn(new DatumElement(datumEindeGeldigheid));
        feitDatumDieKomtNaOfGelijkMetDitActieElement =
                new PersoonFeitdatumHelper(BijhoudingPersoon.decorate(persoon)).getFeitDatumDieKomtNaOfGelijkMetDitActieElement(actieElement);
        assertNull(feitDatumDieKomtNaOfGelijkMetDitActieElement);
    }

    private Persoon maakStandaardPersoon() {
        return new Persoon(SoortPersoon.INGESCHREVENE);
    }
}
