/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.repository;

import java.util.Arrays;
import java.util.Collections;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.init.logging.runtime.repository.jpa.InitVullingLogRepositoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class InitVullingLogRepositoryMockTest {

    private InitVullingLogRepository subject;
    private EntityManager em;


    @Before
    public void setup() {
        em = Mockito.mock(EntityManager.class);
        subject = new InitVullingLogRepositoryImpl();
        ReflectionTestUtils.setField(subject, "em", em);
    }

    @Test
    public void saveInitVullingLogPersoon() {
        InitVullingLog log = new InitVullingLog();

        subject.saveInitVullingLogPersoon(log);

        Mockito.verify(em).merge(log);
        Mockito.verifyNoMoreInteractions(em);
    }

    @Test
    public void findInitVullingAfnemersindicatieEmpty() {
        final TypedQuery<InitVullingAfnemersindicatie> query = Mockito.mock(TypedQuery.class);
        Mockito.when(em.createQuery(Mockito.anyString(), Mockito.eq(InitVullingAfnemersindicatie.class))).thenReturn(query);
        Mockito.when(query.setParameter("administratienummer", "0000000001")).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(Collections.emptyList());

        Assert.assertNull(subject.findInitVullingAfnemersindicatie("0000000001"));
    }

    @Test
    public void findInitVullingAfnemersindicatieOne() {
        final TypedQuery<InitVullingAfnemersindicatie> query = Mockito.mock(TypedQuery.class);
        Mockito.when(em.createQuery(Mockito.anyString(), Mockito.eq(InitVullingAfnemersindicatie.class))).thenReturn(query);
        Mockito.when(query.setParameter("administratienummer", "0000000001")).thenReturn(query);
        InitVullingAfnemersindicatie afn = new InitVullingAfnemersindicatie(1L);
        Mockito.when(query.getResultList()).thenReturn(Arrays.asList(afn));

        Assert.assertSame(afn, subject.findInitVullingAfnemersindicatie("0000000001"));
    }


    @Test(expected = IllegalStateException.class)
    public void findInitVullingAfnemersindicatieMultiple() {
        final TypedQuery<InitVullingAfnemersindicatie> query = Mockito.mock(TypedQuery.class);
        Mockito.when(em.createQuery(Mockito.anyString(), Mockito.eq(InitVullingAfnemersindicatie.class))).thenReturn(query);
        Mockito.when(query.setParameter("administratienummer", "0000000001")).thenReturn(query);
        InitVullingAfnemersindicatie afn = new InitVullingAfnemersindicatie(1L);
        Mockito.when(query.getResultList()).thenReturn(Arrays.asList(afn, afn));

        subject.findInitVullingAfnemersindicatie("0000000001");
    }
}
