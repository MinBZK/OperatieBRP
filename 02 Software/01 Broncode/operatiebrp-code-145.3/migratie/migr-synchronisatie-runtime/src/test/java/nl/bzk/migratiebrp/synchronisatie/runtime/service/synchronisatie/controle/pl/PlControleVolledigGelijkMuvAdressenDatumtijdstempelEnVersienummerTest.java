/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlControleVolledigGelijkMuvAdressenDatumtijdstempelEnVersienummerTest {

    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    @Mock
    private SyncParameters syncParameters;
    private final Partij VOORSCHOTEN = new Partij("Gemeente Voorschoten", "062601");
    private final Partij MIGRATIEVOORZIENING = new Partij("Migratievoorziening", "200001");
    private final LandOfGebied NEDERLAND = new LandOfGebied("6030", "Nederland");
    private final SoortDocument SOORTDOCUMENT = new SoortDocument("naam document", "omschrijving document");
    private final RedenWijzigingVerblijf REDEN_WIJZIGING_VERBLIJF = new RedenWijzigingVerblijf('W', "Gewijzigd");

    private PlControleVolledigGelijkMuvAdressenDatumtijdstempelEnVersienummer subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
        Mockito.when(dynamischeStamtabelRepository.getPartijByCode(Matchers.anyString())).thenReturn(VOORSCHOTEN);
        Mockito.when(dynamischeStamtabelRepository.getPartijByCode(BrpPartijCode.MIGRATIEVOORZIENING.getWaarde())).thenReturn(MIGRATIEVOORZIENING);
        Mockito.when(dynamischeStamtabelRepository.getPartijByCode(Matchers.anyString())).thenReturn(VOORSCHOTEN);
        Mockito.when(dynamischeStamtabelRepository.getLandOfGebiedByCode(Matchers.anyString())).thenReturn(NEDERLAND);
        Mockito.when(dynamischeStamtabelRepository.getSoortDocumentByNaam(Matchers.anyString())).thenReturn(SOORTDOCUMENT);
        Mockito.when(dynamischeStamtabelRepository.getRedenWijzigingVerblijf(Matchers.anyChar())).thenReturn(REDEN_WIJZIGING_VERBLIJF);
        syncParameters.setInitieleVulling(Boolean.FALSE);
        subject =
                new PlControleVolledigGelijkMuvAdressenDatumtijdstempelEnVersienummer(syncParameters, dynamischeStamtabelRepository);
    }

    @Test
    public void test() {
        Assert.assertTrue(subject.controleer(
                maakContext(versie(1), bsns("123456789"), geboortes(19770101), adressen("1234AA")),
                maakPl(versie(1), bsns("123456789"), geboortes(19770101), adressen("1234AA"))));
        Assert.assertTrue(subject.controleer(
                maakContext(versie(2), bsns("123456789"), geboortes(19770101), adressen("2233AA")),
                maakPl(versie(1), bsns("123456789"), geboortes(19770101), adressen("1234AA"))));

        Assert.assertFalse(subject.controleer(
                maakContext(versie(1), bsns("432198765", "987654321", "123456789"), geboortes(19770101), adressen("1234AA")),
                maakPl(versie(1), bsns("123456789"), geboortes(19770101), adressen("1234AA"))));
        Assert.assertFalse(subject.controleer(
                maakContext(versie(1), bsns("123456789"), geboortes(19780101, 19770101), adressen("1234AA")),
                maakPl(versie(1), bsns("123456789"), geboortes(19770101), adressen("1234AA"))));
        Assert.assertFalse(subject.controleer(
                maakContext(versie(1), bsns("432198765", "987654321", "123456789"), geboortes(19780101, 19770101), adressen("1234AA")),
                maakPl(versie(1), bsns("123456789"), geboortes(19770101), adressen("1234AA"))));

    }

    private VerwerkingsContext maakContext(
            final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel,
            final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel,
            final BrpStapel<BrpGeboorteInhoud> geboorteStapel,
            final BrpStapel<BrpAdresInhoud> adresStapel) {
        Timestamp timestamp = Timestamp.from(Instant.now());
        final Lo3Bericht lo3Bericht = new Lo3Bericht("referentie", Lo3BerichtenBron.SYNCHRONISATIE, timestamp, "", true);
        return new VerwerkingsContext(null, lo3Bericht, null, maakPl(inschrijvingStapel, identificatienummersStapel, geboorteStapel, adresStapel));
    }

    private BrpPersoonslijst maakPl(
            final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel,
            final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel,
            final BrpStapel<BrpGeboorteInhoud> geboorteStapel,
            final BrpStapel<BrpAdresInhoud> adresStapel) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        builder.inschrijvingStapel(inschrijvingStapel);
        builder.identificatienummersStapel(identificatienummersStapel);
        builder.geboorteStapel(geboorteStapel);
        builder.adresStapel(adresStapel);
        builder.bijhoudingStapel(bijhouding());
        builder.persoonAfgeleidAdministratiefStapel(afgeleidAdministratiefStapel());

        return builder.build();
    }

    private BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel() {
        final BrpPersoonAfgeleidAdministratiefInhoud inhoud = new BrpPersoonAfgeleidAdministratiefInhoud();
        final BrpHistorie historie = BrpStapelHelper.his(20010130,  null, 20010131, null);
        final BrpActie actieInhoud = BrpStapelHelper.act(1, 20010131);
        return new BrpStapel<>(Collections.singletonList(new BrpGroep<>(inhoud, historie, actieInhoud, null, null)));
    }

    private BrpStapel<BrpInschrijvingInhoud> versie(final int versie) {
        return BrpStapelHelper.stapel(BrpStapelHelper.groep(
                BrpStapelHelper.inschrijving(19770101, versie, 20000101000000L),
                BrpStapelHelper.his(20000101),
                BrpStapelHelper.act(1, 20000101)));
    }

    private BrpStapel<BrpIdentificatienummersInhoud> bsns(final String... bsns) {
        final List<BrpGroep<BrpIdentificatienummersInhoud>> groepen = new ArrayList<>();
        for (int i = 0; i < bsns.length; i++) {
            final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(null, new BrpString(bsns[i]));
            final BrpHistorie historie = BrpStapelHelper.his(20010131 - i, i == 0 ? null : 20010132 - i, 20010131, null);
            final BrpActie actieInhoud = BrpStapelHelper.act(i, 20010131 - i);
            final BrpActie actieAanpassingGeldigheid = i == 0 ? null : BrpStapelHelper.act(i, 20010132 - i);
            groepen.add(BrpStapelHelper.groep(inhoud, historie, actieInhoud, null, actieAanpassingGeldigheid));
        }
        return new BrpStapel<>(groepen);
    }

    private BrpStapel<BrpBijhoudingInhoud> bijhouding() {
        final BrpBijhoudingInhoud inhoud = new BrpBijhoudingInhoud(new BrpPartijCode("051601"), BrpBijhoudingsaardCode.INGEZETENE, BrpNadereBijhoudingsaardCode.ACTUEEL);
        final BrpHistorie historie = BrpStapelHelper.his(20010130,  null, 20010131, null);
        final BrpActie actieInhoud = BrpStapelHelper.act(1, 20010131);
        return new BrpStapel<>(Collections.singletonList(new BrpGroep<>(inhoud, historie, actieInhoud, null, null)));
    }

    private BrpStapel<BrpGeboorteInhoud> geboortes(final Integer... geboorteDatums) {
        final List<BrpGroep<BrpGeboorteInhoud>> groepen = new ArrayList<>();
        for (int i = 0; i < geboorteDatums.length; i++) {
            final BrpGeboorteInhoud inhoud = BrpStapelHelper.geboorte(geboorteDatums[i], "0516");
            final BrpHistorie historie = BrpStapelHelper.his(20010131 - i, i == 0 ? null : 20010132 - i, 20010131, null);
            final BrpActie actieInhoud = BrpStapelHelper.act(i, 20010131 - i);
            final BrpActie actieAanpassingGeldigheid = i == 0 ? null : BrpStapelHelper.act(i, 20010132 - i);
            groepen.add(BrpStapelHelper.groep(inhoud, historie, actieInhoud, null, actieAanpassingGeldigheid));
        }
        return new BrpStapel<>(groepen);
    }

    private BrpStapel<BrpAdresInhoud> adressen(final String... postcodes) {
        final List<BrpGroep<BrpAdresInhoud>> groepen = new ArrayList<>();
        for (int i = 0; i < postcodes.length; i++) {
            final int datumAanvang = 20010131 - i;
            final BrpAdresInhoud inhoud = BrpStapelHelper.adres("W", 'I', 'P', datumAanvang, "0626", "", "", 1, postcodes[i], "Den Haag");
            final BrpHistorie historie = BrpStapelHelper.his(datumAanvang, i == 0 ? null : 20010132 - i, 20010131, null);
            final BrpActie actieInhoud = BrpStapelHelper.act(i, datumAanvang);
            final BrpActie actieAanpassingGeldigheid = i == 0 ? null : BrpStapelHelper.act(i, 20010132 - i);
            groepen.add(BrpStapelHelper.groep(inhoud, historie, actieInhoud, null, actieAanpassingGeldigheid));
        }
        return new BrpStapel<>(groepen);
    }
}
