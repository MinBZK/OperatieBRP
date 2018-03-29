/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AdministratieveHandelingElementCorrectieGerelateerdeTest extends AbstractElementTest {
    private ElementBuilder builder;
    private List<ActieElement> acties;
    private ElementBuilder.AdministratieveHandelingParameters ahParams;
    public static final Partij PARTIJ_GRONINGEN = new Partij("Gemeente Groningen", "000014");
    final Gemeente gemeente = new Gemeente(Short.parseShort("17"), "Groningen", "0018", PARTIJ_GRONINGEN);



    @Before
    public void setup() {
        builder = new ElementBuilder();
        acties = new LinkedList<>();
        ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.soort(AdministratieveHandelingElementSoort.CORRECTIE_PARTNERGEGEVENS_GEREGISTREERD_PARTNERSCHAP).partijCode("17");
        final BijhoudingPersoon persoon1 = mock(BijhoudingPersoon.class);
        when(persoon1.getId()).thenReturn(2001L);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class,"12345")).thenReturn(persoon1);
        final BijhoudingPersoon persoon2 = mock(BijhoudingPersoon.class);
        when(persoon2.getId()).thenReturn(2002L);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class,"12346")).thenReturn(persoon2);
        final BijhoudingPersoon hoofdPersoon = mock(BijhoudingPersoon.class);
        final Set<PersoonBijhoudingHistorie> bijhoudingsSet = new HashSet<>();
        final PersoonBijhoudingHistorie bijhoudingsHistorie = new PersoonBijhoudingHistorie(hoofdPersoon,PARTIJ_GRONINGEN, Bijhoudingsaard.INGEZETENE,
                NadereBijhoudingsaard.ACTUEEL);
        bijhoudingsSet.add(bijhoudingsHistorie);
        when(hoofdPersoon.getPersoonBijhoudingHistorieSet()).thenReturn(bijhoudingsSet);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class,"12340")).thenReturn(hoofdPersoon);
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_GRONINGEN);
        when(getBericht().getDatumOntvangst()).thenReturn(new DatumElement(20160101));

        final ElementBuilder.StuurgegevensParameters stuurParams = new ElementBuilder.StuurgegevensParameters().zendendePartij("17").zendendeSysteem("brp")
                .referentienummer("88409eeb-1aa5-43fc-8614-43055123a165").tijdstipVerzending("2016-03-21T09:32:03.234+02:00");
        final StuurgegevensElement stuurgegevens = builder.maakStuurgegevensElement("stuur_comm", stuurParams);
        when(getBericht().getStuurgegevens()).thenReturn(stuurgegevens);

        PARTIJ_GRONINGEN.addPartijRol(new PartijRol(PARTIJ_GRONINGEN, Rol.BIJHOUDINGSORGAAN_COLLEGE));
        Mockito.when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("17")).thenReturn(gemeente);
        Mockito.when(getDynamischeStamtabelRepository().getPartijByCode("14")).thenReturn(PARTIJ_GRONINGEN);

    }

    @Test
    public void r2521TestAllePersonenGelijk() {
        acties.add(maakActieCorrectieRegistratieIdentificatieNummersGerlateerde("12345"));
        acties.add(maakActieCorrectieVervalIdentificatieNummersGerlateerde("12345"));
        acties.add(maakActieCorrectieRegistratieGeboorteGerlateerde("12345", 1));
        acties.add(maakActieCorrectieVervalGeboorteGerlateerde("12345", 1));
        acties.add(maakActieCorrectieRegistratieSamengesteldeNaamGerelateerde("12345"));
        acties.add(maakActieCorrectieVervalSamengesteldeNaamGerelateerde("12345"));
        acties.add(maakActieCorrectieRegistratieGeslachtsaanduidingGerelateerde("12345", 1));
        acties.add(maakActieCorrectieVervalGeslachtsaanduidingGerelateerde("12345"));
        ahParams.acties(acties);
        final AdministratieveHandelingElement aHandeling = builder.maakAdministratieveHandelingElement("ahd_comid", ahParams);
        builder.initialiseerVerzoekBericht(getBericht());
        controleerRegels(aHandeling.valideerInhoud());

    }

    @Test
    public void r2521TestAllePersonenGelijk2Acties() {
        acties.add(maakActieCorrectieRegistratieIdentificatieNummersGerlateerde("12345"));
        acties.add(maakActieCorrectieVervalIdentificatieNummersGerlateerde("12345"));
        ahParams.acties(acties);
        final AdministratieveHandelingElement aHandeling = builder.maakAdministratieveHandelingElement("ahd_comid", ahParams);
        builder.initialiseerVerzoekBericht(getBericht());
        controleerRegels(aHandeling.valideerInhoud());

    }

    @Test
    @Bedrijfsregel(Regel.R2521)
    public void r2521Test_hoofdactieAnderPersoonDusvoorElkeAndereActieDeMelding() {
        acties.add(maakActieCorrectieRegistratieIdentificatieNummersGerlateerde("12346"));
        acties.add(maakActieCorrectieVervalIdentificatieNummersGerlateerde("12345"));
        acties.add(maakActieCorrectieRegistratieGeboorteGerlateerde("12345", 1));
        acties.add(maakActieCorrectieVervalGeboorteGerlateerde("12345", 1));
        acties.add(maakActieCorrectieRegistratieSamengesteldeNaamGerelateerde("12345"));
        acties.add(maakActieCorrectieVervalSamengesteldeNaamGerelateerde("12345"));
        acties.add(maakActieCorrectieRegistratieGeslachtsaanduidingGerelateerde("12345", 1));
        acties.add(maakActieCorrectieVervalGeslachtsaanduidingGerelateerde("12345"));
        ahParams.acties(acties);
        final AdministratieveHandelingElement aHandeling = builder.maakAdministratieveHandelingElement("ahd_comid", ahParams);
        builder.initialiseerVerzoekBericht(getBericht());
        controleerRegels(aHandeling.valideerInhoud(), Regel.R2521,Regel.R2521,Regel.R2521,Regel.R2521,Regel.R2521,Regel.R2521,Regel.R2521);

    }

    @Test
    @Bedrijfsregel(Regel.R2526)
    public void r2526TestAlleenRegistratieGeboorte() {
        acties.add(maakActieCorrectieRegistratieIdentificatieNummersGerlateerde("12345"));
        acties.add(maakActieCorrectieVervalIdentificatieNummersGerlateerde("12345"));
        acties.add(maakActieCorrectieRegistratieGeboorteGerlateerde("12345", 1));
        acties.add(maakActieCorrectieRegistratieSamengesteldeNaamGerelateerde("12345"));
        acties.add(maakActieCorrectieVervalSamengesteldeNaamGerelateerde("12345"));
        acties.add(maakActieCorrectieRegistratieGeslachtsaanduidingGerelateerde("12345", 1));
        acties.add(maakActieCorrectieVervalGeslachtsaanduidingGerelateerde("12345"));
        ahParams.acties(acties);
        final AdministratieveHandelingElement aHandeling = builder.maakAdministratieveHandelingElement("ahd_comid", ahParams);
        builder.initialiseerVerzoekBericht(getBericht());
        controleerRegels(aHandeling.valideerInhoud(),Regel.R2526);

    }
    @Test
    @Bedrijfsregel(Regel.R2526)
    public void r2526TestAlleenVervalGeboorte() {
        acties.add(maakActieCorrectieRegistratieIdentificatieNummersGerlateerde("12345"));
        acties.add(maakActieCorrectieVervalIdentificatieNummersGerlateerde("12345"));
        acties.add(maakActieCorrectieVervalGeboorteGerlateerde("12345", 1));
        acties.add(maakActieCorrectieRegistratieSamengesteldeNaamGerelateerde("12345"));
        acties.add(maakActieCorrectieVervalSamengesteldeNaamGerelateerde("12345"));
        acties.add(maakActieCorrectieRegistratieGeslachtsaanduidingGerelateerde("12345", 1));
        acties.add(maakActieCorrectieVervalGeslachtsaanduidingGerelateerde("12345"));
        ahParams.acties(acties);
        final AdministratieveHandelingElement aHandeling = builder.maakAdministratieveHandelingElement("ahd_comid", ahParams);
        builder.initialiseerVerzoekBericht(getBericht());
        controleerRegels(aHandeling.valideerInhoud(),Regel.R2526);

    }

    @Test
    @Bedrijfsregel(Regel.R2526)
    public void r2526Test2keerRegistratieenVervalGeboorte() {
        acties.add(maakActieCorrectieRegistratieIdentificatieNummersGerlateerde("12345"));
        acties.add(maakActieCorrectieVervalIdentificatieNummersGerlateerde("12345"));
        acties.add(maakActieCorrectieRegistratieGeboorteGerlateerde("12345", 1));
        acties.add(maakActieCorrectieRegistratieGeboorteGerlateerde("12345", 2));
        acties.add(maakActieCorrectieVervalGeboorteGerlateerde("12345", 1));
        acties.add(maakActieCorrectieVervalGeboorteGerlateerde("12345", 2));
        acties.add(maakActieCorrectieRegistratieSamengesteldeNaamGerelateerde("12345"));
        acties.add(maakActieCorrectieVervalSamengesteldeNaamGerelateerde("12345"));
        acties.add(maakActieCorrectieRegistratieGeslachtsaanduidingGerelateerde("12345", 1));
        acties.add(maakActieCorrectieVervalGeslachtsaanduidingGerelateerde("12345"));
        ahParams.acties(acties);
        final AdministratieveHandelingElement aHandeling = builder.maakAdministratieveHandelingElement("ahd_comid", ahParams);
        builder.initialiseerVerzoekBericht(getBericht());
        controleerRegels(aHandeling.valideerInhoud(),Regel.R2526);

    }
    public CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde maakActieCorrectieRegistratieIdentificatieNummersGerlateerde(
            final String objectsleutelPartner) {
        final List<BetrokkenheidElement> betrokkenheden = new LinkedList<>();
        final List<BetrokkenheidElement> andereBetrokkenheid = new LinkedList<>();
        final ElementBuilder.PersoonParameters parameters = new ElementBuilder.PersoonParameters();
        final IdentificatienummersElement identificatienummersElement = builder.maakIdentificatienummersElement("ident1", "123456789", "1234567891");
        parameters.identificatienummers(identificatienummersElement);
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner1", objectsleutelPartner, null, parameters);
        andereBetrokkenheid.add(builder.maakBetrokkenheidElement("abr1", "abr1234", BetrokkenheidElementSoort.PARTNER, partner, null));
        final GeregistreerdPartnerschapElement gp = builder.maakGeregistreerdPartnerschapElement("gp1", null, andereBetrokkenheid);
        betrokkenheden.add(builder.maakBetrokkenheidElement("br1", "br1234", null, BetrokkenheidElementSoort.PARTNER, gp));
        final PersoonRelatieElement persoon1 = builder.maakPersoonRelatieElement("pr_1", null, "12340", betrokkenheden);
        return builder.maakCorrectieRegistratieIdentificatienummersGerelateerdeActieElement("regi", 20010101, 20020101, persoon1);
    }

    public CorrectieVervalIdentificatienummersGerelateerde maakActieCorrectieVervalIdentificatieNummersGerlateerde(final String objectsleutelPartner) {
        final List<BetrokkenheidElement> betrokkenheden = new LinkedList<>();
        final List<BetrokkenheidElement> andereBetrokkenheid = new LinkedList<>();
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner2", objectsleutelPartner);
        andereBetrokkenheid.add(builder.maakBetrokkenheidElement("abr2", "abr1234", BetrokkenheidElementSoort.PARTNER, partner, null));
        final GeregistreerdPartnerschapElement gp = builder.maakGeregistreerdPartnerschapElement("gp2", null, andereBetrokkenheid);
        betrokkenheden.add(builder.maakBetrokkenheidElement("br2", "br1234", null, BetrokkenheidElementSoort.PARTNER, gp));
        final PersoonRelatieElement persoon1 = builder.maakPersoonRelatieElement("pr_2", null, "12340", betrokkenheden);
        return builder.maakCorrectieVervalIdentificatienummersGerelateerdeActieElement("veri", persoon1, 'O');
    }

    public CorrectieRegistratieGeboorteRegistratieGegevensGerelateerde maakActieCorrectieRegistratieGeboorteGerlateerde(
            final String objectsleutelPartner, final int index) {
        final List<BetrokkenheidElement> betrokkenheden = new LinkedList<>();
        final List<BetrokkenheidElement> andereBetrokkenheid = new LinkedList<>();
        final ElementBuilder.PersoonParameters parameters = new ElementBuilder.PersoonParameters();
        final ElementBuilder.GeboorteParameters geboortePara = new ElementBuilder.GeboorteParameters();
        geboortePara.gemeenteCode("00125");
        parameters.geboorte(builder.maakGeboorteElement("geb3"+index, geboortePara));
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner3"+index, objectsleutelPartner, null, parameters);
        andereBetrokkenheid.add(builder.maakBetrokkenheidElement("abr3"+index, "abr1234", BetrokkenheidElementSoort.PARTNER, partner, null));
        final GeregistreerdPartnerschapElement gp = builder.maakGeregistreerdPartnerschapElement("gp3"+index, null, andereBetrokkenheid);
        betrokkenheden.add(builder.maakBetrokkenheidElement("br3"+index, "br1234", null, BetrokkenheidElementSoort.PARTNER, gp));
        final PersoonRelatieElement persoon1 = builder.maakPersoonRelatieElement("pr_3"+index, null, "12340", betrokkenheden);
        return builder.maakCorrectieRegistratieGeboorteGerelateerdeActieElement("regg"+index, 20010101, 20020101, persoon1);
    }

    public CorrectieVervalGeboorteGerelateerde maakActieCorrectieVervalGeboorteGerlateerde(final String objectsleutelPartner, final int index) {
        final List<BetrokkenheidElement> betrokkenheden = new LinkedList<>();
        final List<BetrokkenheidElement> andereBetrokkenheid = new LinkedList<>();
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner4"+index, objectsleutelPartner);
        andereBetrokkenheid.add(builder.maakBetrokkenheidElement("abr4"+index, "abr1234", BetrokkenheidElementSoort.PARTNER, partner, null));
        final GeregistreerdPartnerschapElement gp = builder.maakGeregistreerdPartnerschapElement("gp4"+index, null, andereBetrokkenheid);
        betrokkenheden.add(builder.maakBetrokkenheidElement("br4"+index, "br1234", null, BetrokkenheidElementSoort.PARTNER, gp));
        final PersoonRelatieElement persoon1 = builder.maakPersoonRelatieElement("pr_4"+index, null, "12340", betrokkenheden);
        return builder.maakCorrectieVervalGeboorteGerelateerdeActieElement("verg"+index, persoon1, 'O');
    }

    public CorrectieRegistratieGeslachtsaanduidingRegistratieGegevensGerelateerde maakActieCorrectieRegistratieGeslachtsaanduidingGerelateerde(
            final String objectsleutelPartner, final int index) {
        final List<BetrokkenheidElement> betrokkenheden = new LinkedList<>();
        final List<BetrokkenheidElement> andereBetrokkenheid = new LinkedList<>();
        final ElementBuilder.PersoonParameters parameters = new ElementBuilder.PersoonParameters();
        final GeslachtsaanduidingElement geslachtsaanduiding = builder.maakGeslachtsaanduidingElement("gesl5", "M");
        parameters.geslachtsaanduiding(geslachtsaanduiding);
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner5"+index, objectsleutelPartner, null, parameters);
        andereBetrokkenheid.add(builder.maakBetrokkenheidElement("abr5"+index, "abr1234", BetrokkenheidElementSoort.PARTNER, partner, null));
        final GeregistreerdPartnerschapElement gp = builder.maakGeregistreerdPartnerschapElement("gp5"+index, null, andereBetrokkenheid);
        betrokkenheden.add(builder.maakBetrokkenheidElement("br5"+index, "br1234", null, BetrokkenheidElementSoort.PARTNER, gp));
        final PersoonRelatieElement persoon1 = builder.maakPersoonRelatieElement("pr_5"+index, null, "12340", betrokkenheden);
        return builder.maakCorrectieRegistratieGeslachtsaanduidingGerelateerdeActieElement("regga"+index, 20010101, 20020101, persoon1);
    }

    public CorrectieVervalGeslachtsaanduidingGerelateerde maakActieCorrectieVervalGeslachtsaanduidingGerelateerde(final String objectsleutelPartner) {
        final List<BetrokkenheidElement> betrokkenheden = new LinkedList<>();
        final List<BetrokkenheidElement> andereBetrokkenheid = new LinkedList<>();
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner6", objectsleutelPartner);
        andereBetrokkenheid.add(builder.maakBetrokkenheidElement("abr6", "abr1234", BetrokkenheidElementSoort.PARTNER, partner, null));
        final GeregistreerdPartnerschapElement gp = builder.maakGeregistreerdPartnerschapElement("gp6", null, andereBetrokkenheid);
        betrokkenheden.add(builder.maakBetrokkenheidElement("br6", "br1234", null, BetrokkenheidElementSoort.PARTNER, gp));
        final PersoonRelatieElement persoon1 = builder.maakPersoonRelatieElement("pr_6", null, "12340", betrokkenheden);
        return builder.maakCorrectieVervalGeslachtsaanduidingGerelateerdeActieElement("verga", persoon1, 'O');
    }

    public CorrectieRegistratieSamengesteldeNaamRegistratieGegevensGerelateerde maakActieCorrectieRegistratieSamengesteldeNaamGerelateerde(
            final String objectsleutelPartner) {
        final List<BetrokkenheidElement> betrokkenheden = new LinkedList<>();
        final List<BetrokkenheidElement> andereBetrokkenheid = new LinkedList<>();
        final ElementBuilder.PersoonParameters parameters = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters naampara = new ElementBuilder.NaamParameters();
        naampara.geslachtsnaamstam("stam2");
        final SamengesteldeNaamElement samengesteldenaam = builder.maakSamengesteldeNaamElement("sa7", naampara);
        parameters.samengesteldeNaam(samengesteldenaam);
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner7", objectsleutelPartner, null, parameters);
        andereBetrokkenheid.add(builder.maakBetrokkenheidElement("abr7", "abr1234", BetrokkenheidElementSoort.PARTNER, partner, null));
        final GeregistreerdPartnerschapElement gp = builder.maakGeregistreerdPartnerschapElement("gp7", null, andereBetrokkenheid);
        betrokkenheden.add(builder.maakBetrokkenheidElement("br7", "br1234", null, BetrokkenheidElementSoort.PARTNER, gp));
        final PersoonRelatieElement persoon1 = builder.maakPersoonRelatieElement("pr_7", null, "12340", betrokkenheden);
        return builder.maakCorrectieRegistratieSamengesteldeNaamGerelateerdeActieElement("regsa", 20010101, 20020101, persoon1);
    }

    public CorrectieVervalSamengesteldeNaamGerelateerde maakActieCorrectieVervalSamengesteldeNaamGerelateerde(final String objectsleutelPartner) {
        final List<BetrokkenheidElement> betrokkenheden = new LinkedList<>();
        final List<BetrokkenheidElement> andereBetrokkenheid = new LinkedList<>();
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner8", objectsleutelPartner);
        andereBetrokkenheid.add(builder.maakBetrokkenheidElement("abr8", "abr1234", BetrokkenheidElementSoort.PARTNER, partner, null));
        final GeregistreerdPartnerschapElement gp = builder.maakGeregistreerdPartnerschapElement("gp8", null, andereBetrokkenheid);
        betrokkenheden.add(builder.maakBetrokkenheidElement("br8", "br1234", null, BetrokkenheidElementSoort.PARTNER, gp));
        final PersoonRelatieElement persoon1 = builder.maakPersoonRelatieElement("pr_8", null, "12340", betrokkenheden);
        return builder.maakCorrectieVervalSamengesteldeNaamGerelateerdeActieElement("versa", persoon1, 'O');
    }
}
