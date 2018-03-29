/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class AbstractPersoonWijzigingActieElementTest extends AbstractElementTest {
    public static final String PERS_SLEUTEL = "1234";
    ElementBuilder builder;
    BijhoudingVerzoekBericht bericht;

    @Before
    public void setup() {
        builder = new ElementBuilder();
        bericht = mock(BijhoudingVerzoekBericht.class);

    }

    @Test
    @Bedrijfsregel(Regel.R2672)
    public void testcontroleerAanvangGeldigheidTegenBijhouding() {
        maakPersoonEntiteitMeBijhoudingsHistorie(20000101);
        final PersoonGegevensElement PersoonGegevensElement = builder.maakPersoonGegevensElement("pers", PERS_SLEUTEL);
        final ElementBuilder.AdministratieveHandelingParameters ahpara = new ElementBuilder.AdministratieveHandelingParameters();
        ahpara.partijCode("1234");
        ahpara.soort(AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK);
        when(bericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("ah",ahpara));
        PersoonGegevensElement.setVerzoekBericht(bericht);
        AbstractPersoonWijzigingActieElement element = builder.maakRegistratieMigratieActieElement("com", 20010101, PersoonGegevensElement);
        element.setVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = new LinkedList<>();
        element.controleerAanvangGeldigheidTegenBijhouding(meldingen);
        assertEquals(0, meldingen.size());
    }

    @Test
    @Bedrijfsregel(Regel.R2672)
    public void testcontroleerAanvangGeldigheidTegenBijhouding2() {
        maakPersoonEntiteitMeBijhoudingsHistorie(20000101, 20010101);
        final PersoonGegevensElement PersoonGegevensElement = builder.maakPersoonGegevensElement("pers", PERS_SLEUTEL);
        final ElementBuilder.AdministratieveHandelingParameters ahpara = new ElementBuilder.AdministratieveHandelingParameters();
        ahpara.partijCode("1234");
        ahpara.soort(AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK);
        when(bericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("ah",ahpara));
        PersoonGegevensElement.setVerzoekBericht(bericht);
        AbstractPersoonWijzigingActieElement element = builder.maakRegistratieMigratieActieElement("com", 20010101, PersoonGegevensElement);
        element.setVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = new LinkedList<>();
        element.controleerAanvangGeldigheidTegenBijhouding(meldingen);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2672, meldingen.get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R2672)
    public void testcontroleerAanvangGeldigheidTegenBijhoudingAndereAhSoort() {
        maakPersoonEntiteitMeBijhoudingsHistorie(20000101, 20010101);
        final PersoonGegevensElement PersoonGegevensElement = builder.maakPersoonGegevensElement("pers", PERS_SLEUTEL);
        final ElementBuilder.AdministratieveHandelingParameters ahpara = new ElementBuilder.AdministratieveHandelingParameters();
        ahpara.partijCode("1234");
        ahpara.soort(AdministratieveHandelingElementSoort.WIJZIGING_ADRES_INFRASTRUCTUREEL);
        when(bericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("ah",ahpara));
        PersoonGegevensElement.setVerzoekBericht(bericht);
        AbstractPersoonWijzigingActieElement element = builder.maakRegistratieMigratieActieElement("com", 20010101, PersoonGegevensElement);
        element.setVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = new LinkedList<>();
        element.controleerAanvangGeldigheidTegenBijhouding(meldingen);
        assertEquals(0, meldingen.size());

    }

    public void maakPersoonEntiteitMeBijhoudingsHistorie(final Integer... datumAanvangGeldigheid) {
        final Persoon ingeschrevene = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon persoonEntiteit = BijhoudingPersoon.decorate(ingeschrevene);
        voegBijhoudingsHistorieToe(ingeschrevene, datumAanvangGeldigheid);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERS_SLEUTEL)).thenReturn(persoonEntiteit);
    }

    public BRPActie maakActie() {
        AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(new Partij("test partij", "053001"), SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                        new Timestamp(
                                System.currentTimeMillis()));
        return new BRPActie(
                SoortActie.REGISTRATIE_AANVANG_HUWELIJK,
                administratieveHandeling,
                administratieveHandeling.getPartij(),
                administratieveHandeling.getDatumTijdRegistratie());
    }
}