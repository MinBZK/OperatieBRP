/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
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
public class PlControleVolledigGelijkTest {

    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    @Mock
    private SyncParameters syncParameters;

    private PlControleVolledigGelijk subject;

    private final Partij AMSTERDAM = new Partij("Amsterdam", "059901");
    private final Partij MIGRATIEVOORZIENING = new Partij("Migratievoorziening", "200001");
    private final LandOfGebied NEDERLAND = new LandOfGebied("6030", "Nederland");
    private final SoortDocument SOORTDOCUMENT = new SoortDocument("naam document", "omschrijving document");
    private final RedenWijzigingVerblijf REDEN_WIJZIGING_VERBLIJF = new RedenWijzigingVerblijf('W', "Gewijzigd");

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
        Mockito.when(dynamischeStamtabelRepository.getPartijByCode(Matchers.anyString())).thenReturn(AMSTERDAM);
        Mockito.when(dynamischeStamtabelRepository.getPartijByCode(BrpPartijCode.MIGRATIEVOORZIENING.getWaarde())).thenReturn(MIGRATIEVOORZIENING);
        Mockito.when(dynamischeStamtabelRepository.getPartijByCode(Matchers.anyString())).thenReturn(AMSTERDAM);
        Mockito.when(dynamischeStamtabelRepository.getLandOfGebiedByCode(Matchers.anyString())).thenReturn(NEDERLAND);
        Mockito.when(dynamischeStamtabelRepository.getSoortDocumentByNaam(Matchers.anyString())).thenReturn(SOORTDOCUMENT);
        Mockito.when(dynamischeStamtabelRepository.getRedenWijzigingVerblijf(Matchers.anyChar())).thenReturn(REDEN_WIJZIGING_VERBLIJF);
        syncParameters.setInitieleVulling(Boolean.FALSE);
        subject = new PlControleVolledigGelijk(syncParameters, dynamischeStamtabelRepository);
    }

    @Test
    public void test() {
        Assert.assertTrue(subject.controleer(maakContext(bsns("123456789"), geboortes(19770101), bijhoudingen("059901")), maakPl(bsns("123456789"), geboortes(19770101),
                bijhoudingen("059901"))));
        Assert.assertFalse(subject.controleer(maakContext(bsns("432198765", "987654321", "123456789"), geboortes(19770101), bijhoudingen("059901")), maakPl(bsns("123456789"), geboortes(19770101),
                bijhoudingen("059901"))));
        Assert.assertFalse(subject.controleer(maakContext(bsns("123456789"), geboortes(19780101, 19770101), bijhoudingen("059901")), maakPl(bsns("123456789"), geboortes
                (19770101), bijhoudingen
                ("059901"))));
        Assert.assertFalse(subject.controleer(maakContext(bsns("432198765", "987654321", "123456789"), geboortes(19780101, 19770101), bijhoudingen("059901")), maakPl(bsns("123456789"), geboortes
                        (19770101),
                bijhoudingen("059901"))));

        Assert.assertFalse(subject.controleer(maakContext(bsns("432198765", "987654321", "123456789"), geboortes(19780101, 19770101), bijhoudingen("059901")), maakPl(bsns("567891234"), geboortes
                        (19760101),
                bijhoudingen("059901"))));
        Assert.assertFalse(subject.controleer(maakContext(bsns("432198765", "987654321", "123456789"), geboortes(19780101, 19770101), bijhoudingen("059901")), maakPl(bsns("432198765"), geboortes
                        (19760101),
                bijhoudingen("059901"))));
        Assert.assertFalse(subject.controleer(maakContext(bsns("432198765", "987654321", "123456789"), geboortes(19780101, 19770101), bijhoudingen("059901")), maakPl(bsns("567891234"), geboortes
                        (19770101),
                bijhoudingen("059901"))));

    }

    private VerwerkingsContext maakContext(
            final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel,
            final BrpStapel<BrpGeboorteInhoud> geboorteStapel,
            final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel) {
        return new VerwerkingsContext(null, new Lo3Bericht("TEST", Lo3BerichtenBron.SYNCHRONISATIE, new Timestamp(System.currentTimeMillis()),
                "Berichtdata", false), null, maakPl
                (identificatienummersStapel, geboorteStapel,
                        bijhoudingStapel));
    }

    private BrpPersoonslijst maakPl(
            final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel,
            final BrpStapel<BrpGeboorteInhoud> geboorteStapel,
            final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        builder.identificatienummersStapel(identificatienummersStapel);
        builder.bijhoudingStapel(bijhoudingStapel);
        builder.geboorteStapel(geboorteStapel);
        builder.persoonAfgeleidAdministratiefStapel(afgeleidAdministratief());
        builder.adresStapel(adres());
        return builder.build();
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

    private BrpStapel<BrpBijhoudingInhoud> bijhoudingen(final String... bijhoudingspartijen) {
        final List<BrpGroep<BrpBijhoudingInhoud>> groepen = new ArrayList<>();
        for (int i = 0; i < bijhoudingspartijen.length; i++) {
            final BrpBijhoudingInhoud inhoud = BrpStapelHelper.bijhouding(bijhoudingspartijen[i], BrpBijhoudingsaardCode.INGEZETENE,
                    BrpNadereBijhoudingsaardCode.ACTUEEL);
            final BrpHistorie historie = BrpStapelHelper.his(20010131 - i, i == 0 ? null : 20010132 - i, 20010131, null);
            final BrpActie actieInhoud = BrpStapelHelper.act(i, 20010131 - i);
            final BrpActie actieAanpassingGeldigheid = i == 0 ? null : BrpStapelHelper.act(i, 20010132 - i);
            groepen.add(BrpStapelHelper.groep(inhoud, historie, actieInhoud, null, actieAanpassingGeldigheid));
        }
        return new BrpStapel<>(groepen);
    }

    private BrpStapel<BrpAdresInhoud> adres() {
        final List<BrpGroep<BrpAdresInhoud>> groepen = new ArrayList<>();
        final BrpAdresInhoud inhoud = BrpStapelHelper.adres(BrpSoortAdresCode.W.getWaarde(),
                'W',
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        final BrpHistorie historie = BrpStapelHelper.his(20010131, null, 20010131, null);
        final BrpActie actieInhoud = BrpStapelHelper.act(0, 20010131);
        groepen.add(BrpStapelHelper.groep(inhoud, historie, actieInhoud, null, null));
        return new BrpStapel<>(groepen);
    }

    private BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> afgeleidAdministratief() {
        final List<BrpGroep<BrpPersoonAfgeleidAdministratiefInhoud>> groepen = new ArrayList<>();
        final BrpPersoonAfgeleidAdministratiefInhoud inhoud = BrpStapelHelper.afgeleid();
        final BrpHistorie historie = BrpStapelHelper.his(20010131, null, 20010131, null);
        final BrpActie actieInhoud = BrpStapelHelper.act(0, 20010131);
        groepen.add(BrpStapelHelper.groep(inhoud, historie, actieInhoud, null, null));
        return new BrpStapel<>(groepen);
    }
}
