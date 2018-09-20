/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.alleenlezen;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import nl.bzk.brp.blobifier.exceptie.NietUniekeAnummerExceptie;
import nl.bzk.brp.blobifier.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.blobifier.exceptie.PersoonNietAanwezigExceptie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HisPersTabelRepositoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private EntityManager emAlleenLezen;

    @Mock
    private EntityManager emLezenSchrijven;

    @InjectMocks
    private HisPersTabelJpaRepository jpaRepository;

    @Test
    public void leesGenormaliseerdModel() {
        // arrange
        final PersoonHisVolledigImpl mockPersoon = mock(PersoonHisVolledigImpl.class);
        when(emAlleenLezen.find(eq(PersoonHisVolledigImpl.class), anyInt())).thenReturn(mockPersoon);

        // act
        final PersoonHisVolledig resultaat = jpaRepository.leesGenormalizeerdModelVoorInMemoryBlob(42);

        // assert
        assertThat(resultaat, notNullValue());
    }

    @Test(expected = PersoonNietAanwezigExceptie.class)
    public void leesGenormaliseerdModelMetOnbekendId() {
        // arrange
        when(emAlleenLezen.find(eq(PersoonHisVolledigImpl.class), anyInt())).thenReturn(null);

        // act
        final PersoonHisVolledig resultaat = jpaRepository.leesGenormalizeerdModelVoorInMemoryBlob(345);

        // assert
        assertThat(resultaat, nullValue());
    }

    @Test
    public void leesGenormaliseerdModelVoorNieuweBlob() {
        // arrange
        final PersoonHisVolledigImpl mockPersoon = mock(PersoonHisVolledigImpl.class);
        when(emLezenSchrijven.find(eq(PersoonHisVolledigImpl.class), anyInt())).thenReturn(mockPersoon);

        // act
        final PersoonHisVolledig resultaat = jpaRepository.leesGenormalizeerdModelVoorNieuweBlob(42);

        // assert
        assertThat(resultaat, notNullValue());
    }

    @Test
    public void zoekIdBijBsnEenResultaatUitDb() throws NietUniekeBsnExceptie {
        // arrange
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(emAlleenLezen.createQuery(anyString(), eq(Integer.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), eq(BurgerservicenummerAttribuut.class)))
                .thenReturn(typedQueryMock);
        when(typedQueryMock.getSingleResult()).thenReturn(42);

        // act
        final BurgerservicenummerAttribuut bsnAttribuut = new BurgerservicenummerAttribuut(123456789);
        final Integer resultaat = jpaRepository.zoekIdBijBSN(bsnAttribuut);

        // assert
        assertThat(resultaat, is(42));
    }

    @Test
    public void zoekIdBijBsnMeerResultatenUitDb() throws NietUniekeBsnExceptie {
        // arrange
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(emAlleenLezen.createQuery(anyString(), eq(Integer.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), eq(BurgerservicenummerAttribuut.class)))
                .thenReturn(typedQueryMock);
        when(typedQueryMock.getSingleResult()).thenThrow(new NonUniqueResultException());

        thrown.expectMessage("Meerdere personen gevonden met BSN: 123456789");

        // act
        final BurgerservicenummerAttribuut bsnAttribuut = new BurgerservicenummerAttribuut(123456789);
        jpaRepository.zoekIdBijBSN(bsnAttribuut);
    }

    @Test
    public void zoekIdBijBsnGeenResultaatUitDb() throws NietUniekeBsnExceptie {
        // arrange
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(emAlleenLezen.createQuery(anyString(), eq(Integer.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), eq(BurgerservicenummerAttribuut.class)))
                .thenReturn(typedQueryMock);
        when(typedQueryMock.getSingleResult()).thenThrow(new NoResultException());

        thrown.expectMessage("Geen persoon gevonden met BSN: 123456789");

        // act
        final BurgerservicenummerAttribuut bsnAttribuut = new BurgerservicenummerAttribuut(123456789);
        jpaRepository.zoekIdBijBSN(bsnAttribuut);
    }


    @Test
    public void zoekIdBijBsnActiefPersoonEenResultaatUitDb() throws NietUniekeBsnExceptie {
        // arrange
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(emAlleenLezen.createNativeQuery(anyString())).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), eq(BurgerservicenummerAttribuut.class)))
            .thenReturn(typedQueryMock);
        when(typedQueryMock.getSingleResult()).thenReturn(42);

        // act
        final BurgerservicenummerAttribuut bsnAttribuut = new BurgerservicenummerAttribuut(123456789);
        final Integer resultaat = jpaRepository.zoekIdBijBSNVoorActievePersoon(bsnAttribuut);

        // assert
        assertThat(resultaat, is(42));
    }

    @Test
    public void zoekIdBijBsnActiefPersoonMeerResultatenUitDb() throws NietUniekeBsnExceptie {
        // arrange
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(emAlleenLezen.createNativeQuery(anyString())).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), eq(BurgerservicenummerAttribuut.class)))
            .thenReturn(typedQueryMock);
        when(typedQueryMock.getSingleResult()).thenThrow(new NonUniqueResultException());

        thrown.expectMessage("Meerdere actieve personen gevonden met BSN: 123456789");

        // act
        final BurgerservicenummerAttribuut bsnAttribuut = new BurgerservicenummerAttribuut(123456789);
        jpaRepository.zoekIdBijBSNVoorActievePersoon(bsnAttribuut);
    }

    @Test
    public void zoekIdBijBsnActiefPersoonGeenResultaatUitDb() throws NietUniekeBsnExceptie {
        // arrange
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(emAlleenLezen.createNativeQuery(anyString())).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), eq(BurgerservicenummerAttribuut.class)))
            .thenReturn(typedQueryMock);
        when(typedQueryMock.getSingleResult()).thenThrow(new NoResultException());

        thrown.expectMessage("Geen actief persoon gevonden met BSN: 123456789");

        // act
        final BurgerservicenummerAttribuut bsnAttribuut = new BurgerservicenummerAttribuut(123456789);
        jpaRepository.zoekIdBijBSNVoorActievePersoon(bsnAttribuut);
    }


    @Test
    public void zoekIdBijAnummerEenResultaatUitDb() throws NietUniekeAnummerExceptie {
        // arrange
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(emAlleenLezen.createQuery(anyString(), eq(Integer.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), eq(AdministratienummerAttribuut.class)))
                .thenReturn(typedQueryMock);
        when(typedQueryMock.getSingleResult()).thenReturn(42);

        // act
        final AdministratienummerAttribuut anummerAttribuut = new AdministratienummerAttribuut(54321L);
        final Integer resultaat = jpaRepository.zoekIdBijAnummer(anummerAttribuut);

        // assert
        assertThat(resultaat, is(42));
    }

    @Test
    public void zoekIdBijAnummerMeerResultatenUitDb() throws NietUniekeAnummerExceptie {
        // arrange
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(emAlleenLezen.createQuery(anyString(), eq(Integer.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), eq(AdministratienummerAttribuut.class)))
                .thenReturn(typedQueryMock);
        when(typedQueryMock.getSingleResult()).thenThrow(new NonUniqueResultException());

        thrown.expectMessage("Meerdere personen gevonden met A-nummer: 0000054321");

        // act
        final AdministratienummerAttribuut anummerAttribuut = new AdministratienummerAttribuut(54321L);
        jpaRepository.zoekIdBijAnummer(anummerAttribuut);
    }

    @Test
    public void zoekIdBijAnummerGeenResultaatUitDb() throws NietUniekeAnummerExceptie {
        // arrange
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(emAlleenLezen.createQuery(anyString(), eq(Integer.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), eq(AdministratienummerAttribuut.class))).thenReturn(
                typedQueryMock);
        when(typedQueryMock.getSingleResult()).thenThrow(new NoResultException());

        thrown.expectMessage("Geen persoon gevonden met A-nummer: 0000054321");

        // act
        final AdministratienummerAttribuut anummerAttribuut = new AdministratienummerAttribuut(54321L);
        jpaRepository.zoekIdBijAnummer(anummerAttribuut);
    }

    @Test
    public void checkPersoonBestaat() {
        // arrange
        final Query queryMock = mock(Query.class);
        when(emAlleenLezen.createNativeQuery(anyString())).thenReturn(queryMock);
        doReturn(queryMock).when(queryMock).setParameter(anyString(), anyInt());
        when(queryMock.getResultList()).thenReturn(Collections.singletonList(1));

        final Integer persoonId = 2000001;

        // act
        final boolean bestaat = jpaRepository.bestaatPersoonMetId(persoonId);

        // assert
        assertTrue(bestaat);
    }

    @Test
    public void checkPersoonBestaatNiet() {
        // arrange
        final Query queryMock = mock(Query.class);
        when(emAlleenLezen.createNativeQuery(anyString())).thenReturn(queryMock);
        doReturn(queryMock).when(queryMock).setParameter(anyString(), anyInt());
        when(queryMock.getResultList()).thenReturn(Collections.emptyList());

        final Integer persoonId = 123;

        // act
        final boolean bestaat = jpaRepository.bestaatPersoonMetId(persoonId);

        // assert
        assertFalse(bestaat);
    }
}
