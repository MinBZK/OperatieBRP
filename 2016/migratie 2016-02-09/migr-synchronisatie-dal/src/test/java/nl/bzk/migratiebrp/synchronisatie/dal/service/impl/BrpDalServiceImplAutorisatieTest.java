/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import java.sql.Timestamp;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@Ignore("BMR44")
@RunWith(MockitoJUnitRunner.class)
public class BrpDalServiceImplAutorisatieTest {

    @Mock
    protected DynamischeStamtabelRepository dynamischeStamtabelRepository;
    // TODO BRM44
    // @Mock
    // AutorisatiebesluitRepository autorisatiebesluitRepository;
    // @Mock
    // AbonnementRepository abonnementRepository;

    @InjectMocks
    private BrpDalServiceImpl subject;

    @Test
    public void testPersisteer() {
        // Input
        final BrpAutorisatie input = maak(dynamischeStamtabelRepository);

        Mockito.when(dynamischeStamtabelRepository.savePartij(Matchers.<Partij>any())).thenAnswer(new SaveAnswer());
        // Mockito.when(autorisatiebesluitRepository.save(Matchers.<Autorisatiebesluit>any())).thenAnswer(new
        // SaveAnswer());
        // Mockito.when(abonnementRepository.save(Matchers.<Abonnement>any())).thenAnswer(new SaveAnswer());

        // Execute
        subject.persisteerAutorisatie(input);

        // Validate
        Mockito.verify(dynamischeStamtabelRepository, Mockito.times(1)).savePartij(Matchers.<Partij>any());
        // Mockito.verify(autorisatiebesluitRepository, Mockito.times(1)).save(Matchers.<Autorisatiebesluit>any());
        // Mockito.verify(abonnementRepository, Mockito.times(1)).save(Matchers.<Abonnement>any());

    }

    @Test
    public void testPersisteerBestaandePartijNaam() {
        // Input
        final BrpAutorisatie input = maak(dynamischeStamtabelRepository);
        final Partij partij = new Partij(input.getPartij().getNaam(), 12346);
        partij.setId((short) 1);
        Mockito.when(dynamischeStamtabelRepository.findPartijByNaam(Matchers.anyString())).thenReturn(partij);
        Mockito.when(dynamischeStamtabelRepository.savePartij(Matchers.<Partij>any())).thenAnswer(new SaveAnswer());
        // Mockito.when(autorisatiebesluitRepository.save(Matchers.<Autorisatiebesluit>any())).thenAnswer(new
        // SaveAnswer());
        // Mockito.when(abonnementRepository.save(Matchers.<Abonnement>any())).thenAnswer(new SaveAnswer());

        // Execute
        subject.persisteerAutorisatie(input);

        // Validate
        Mockito.verify(dynamischeStamtabelRepository, Mockito.times(1)).savePartij((Partij) Matchers.argThat(new MessagesArgumentMatcher()));
        // Mockito.verify(autorisatiebesluitRepository, Mockito.times(1)).save(Matchers.<Autorisatiebesluit>any());
        // Mockito.verify(abonnementRepository, Mockito.times(1)).save(Matchers.<Abonnement>any());
    }

    @Test
    @Ignore
    public void testPersisteerBestaandePartijMigratie() {
        // Input
        final BrpAutorisatie input = maak(dynamischeStamtabelRepository);
        final Partij existingPartij = new Partij(input.getPartij().getNaam(), 876543);
        existingPartij.setDatumIngang(19700101);
        final Timestamp datumTijdRegistratie = new Timestamp(System.currentTimeMillis());
        final PartijHistorie partijHistorie = new PartijHistorie(existingPartij, datumTijdRegistratie, 19700101, false, existingPartij.getNaam());
        final Partij migPartij = new Partij("Migratie", BrpPartijCode.MIGRATIEVOORZIENING.getWaarde());
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(migPartij, SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
        partijHistorie.setActieInhoud(new BRPActie(
            SoortActie.CONVERSIE_GBA,
            administratieveHandeling,
            migPartij,
            new Timestamp(System.currentTimeMillis())));
        existingPartij.getHisPartijen().add(partijHistorie);

        Mockito.when(dynamischeStamtabelRepository.findPartijByCode(876543)).thenReturn(existingPartij);
        final Partij partij = new Partij(input.getPartij().getNaam(), 876543);
        partij.setId((short) 1);
        Mockito.when(dynamischeStamtabelRepository.findPartijByNaam(Matchers.anyString())).thenReturn(partij);
        Mockito.when(dynamischeStamtabelRepository.savePartij(Matchers.<Partij>any())).thenAnswer(new SaveAnswer());
        // Mockito.when(autorisatiebesluitRepository.save(Matchers.<Autorisatiebesluit>any())).thenAnswer(new
        // SaveAnswer());
        // Mockito.when(abonnementRepository.save(Matchers.<Abonnement>any())).thenAnswer(new SaveAnswer());

        // Execute
        subject.persisteerAutorisatie(input);

        // Validate
        Mockito.verify(dynamischeStamtabelRepository, Mockito.times(1)).savePartij((Partij) Matchers.argThat(new PartijDatumArgumentMatcher()));
        // Mockito.verify(autorisatiebesluitRepository, Mockito.times(1)).save(Matchers.<Autorisatiebesluit>any());
        // Mockito.verify(abonnementRepository, Mockito.times(1)).save(Matchers.<Abonnement>any());
    }

    @Test
    public void testPersisteerBestaandePartijMigratieOudereDatum() {
        // Input
        final BrpAutorisatie input = maak(dynamischeStamtabelRepository);
        final Partij existingPartij = new Partij(input.getPartij().getNaam(), 876543);
        existingPartij.setDatumIngang(19900101);
        final Timestamp datumTijdRegistratie = new Timestamp(System.currentTimeMillis());
        final PartijHistorie partijHistorie = new PartijHistorie(existingPartij, datumTijdRegistratie, 19900101, false, existingPartij.getNaam());

        final Partij migPartij = new Partij("Migratie", BrpPartijCode.MIGRATIEVOORZIENING.getWaarde());
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(migPartij, SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
        partijHistorie.setActieInhoud(new BRPActie(
            SoortActie.CONVERSIE_GBA,
            administratieveHandeling,
            migPartij,
            new Timestamp(System.currentTimeMillis())));
        existingPartij.getHisPartijen().add(partijHistorie);

        Mockito.when(dynamischeStamtabelRepository.findPartijByCode(876543)).thenReturn(existingPartij);
        Mockito.when(dynamischeStamtabelRepository.savePartij(Matchers.<Partij>any())).thenAnswer(new SaveAnswer());
        // Mockito.when(autorisatiebesluitRepository.save(Matchers.<Autorisatiebesluit>any())).thenAnswer(new
        // SaveAnswer());
        // Mockito.when(abonnementRepository.save(Matchers.<Abonnement>any())).thenAnswer(new SaveAnswer());

        // Execute
        subject.persisteerAutorisatie(input);

        // Validate
        Mockito.verify(dynamischeStamtabelRepository, Mockito.times(1)).savePartij(Matchers.<Partij>any());
        // Mockito.verify(autorisatiebesluitRepository, Mockito.times(1)).save(Matchers.<Autorisatiebesluit>any());
        // Mockito.verify(abonnementRepository, Mockito.times(1)).save(Matchers.<Abonnement>any());
    }

    @Test
    public void testPersisteerBestaandePartijNietMigratie() {
        // Input
        final BrpAutorisatie input = maak(dynamischeStamtabelRepository);
        final Partij existingPartij = new Partij(input.getPartij().getNaam(), 876543);
        existingPartij.setDatumIngang(19700101);

        Mockito.when(dynamischeStamtabelRepository.findPartijByCode(876543)).thenReturn(existingPartij);
        Mockito.when(dynamischeStamtabelRepository.findPartijByNaam(Matchers.anyString())).thenReturn(new Partij(input.getPartij().getNaam(), 876543));
        Mockito.when(dynamischeStamtabelRepository.savePartij(Matchers.<Partij>any())).thenAnswer(new SaveAnswer());
        // Mockito.when(autorisatiebesluitRepository.save(Matchers.<Autorisatiebesluit>any())).thenAnswer(new
        // SaveAnswer());
        // Mockito.when(abonnementRepository.save(Matchers.<Abonnement>any())).thenAnswer(new SaveAnswer());

        // Execute
        subject.persisteerAutorisatie(input);

        // Validate
        Mockito.verify(dynamischeStamtabelRepository, Mockito.times(0)).savePartij(Matchers.<Partij>any());
        // Mockito.verify(autorisatiebesluitRepository, Mockito.times(1)).save(Matchers.<Autorisatiebesluit>any());
        // Mockito.verify(abonnementRepository, Mockito.times(1)).save(Matchers.<Abonnement>any());
    }

    @Test
    public void testBevraagAutorisatie() {
        final Integer partijCode = 123456;

        final Partij existingPartij = new Partij("Partij testje", partijCode);
        // final Autorisatiebesluit existingAutBesluit =
        // new Autorisatiebesluit("autbesluit", "autbesluitTekst", SoortAutorisatiebesluit.LEVERINGSAUTORISATIE,
        // existingPartij);

        Mockito.when(dynamischeStamtabelRepository.getPartijByCode(partijCode)).thenReturn(existingPartij);
        // Mockito.when(autorisatiebesluitRepository.findByPartij(existingPartij,
        // "autbesluit")).thenReturn(Collections.singletonList(existingAutBesluit));

        final BrpAutorisatie brpAutorisatie = subject.bevraagAutorisatie(partijCode, "autbesluit", null);
        Assert.assertNotNull(brpAutorisatie);

    }

    public static BrpAutorisatie maak(final DynamischeStamtabelRepository dynamischeStamtabelRepositoryMock) {
//        return new BrpAutorisatie(
//            PartijMapperTest.maak(dynamischeStamtabelRepositoryMock),
//            Collections.singletonList(AutorisatieBesluitMapperTest.maak(dynamischeStamtabelRepositoryMock)));
        //BRM44
        return null;

    }

    public static class SaveAnswer implements Answer<Object> {
        @Override
        public Object answer(final InvocationOnMock invocation) {
            return invocation.getArguments()[0];
        }

    }

    class MessagesArgumentMatcher extends ArgumentMatcher<Object> {

        @Override
        public boolean matches(final Object o) {
            if (o instanceof Partij) {
                final Partij partij = (Partij) o;
                if (!partij.getNaam().equals("Partij (876543)")) {
                    return false;
                }
            }
            return true;
        }
    }

    class PartijDatumArgumentMatcher extends ArgumentMatcher<Object> {

        @Override
        public boolean matches(final Object o) {
            if (o instanceof Partij) {
                final Partij partij = (Partij) o;
                if (!partij.getDatumIngang().equals(19800101)) {
                    return false;
                }
                if (!partij.getNaam().equals("Partij (876543)")) {
                    return false;
                }
            }
            return true;
        }
    }
}
