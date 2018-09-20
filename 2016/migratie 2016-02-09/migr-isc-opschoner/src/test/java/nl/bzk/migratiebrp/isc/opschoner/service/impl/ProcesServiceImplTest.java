/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import nl.bzk.migratiebrp.isc.opschoner.dao.ExtractieDao;
import nl.bzk.migratiebrp.isc.opschoner.dao.JbpmDao;
import nl.bzk.migratiebrp.isc.opschoner.dao.MigDao;
import nl.bzk.migratiebrp.isc.opschoner.exception.NietVerwijderbareProcesInstantieException;
import nl.bzk.migratiebrp.isc.opschoner.service.ProcesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcesServiceImplTest {

    private static final Timestamp DATUM_TOT = new Timestamp(System.currentTimeMillis());

    @Mock
    private JbpmDao jbpmDao;

    @Mock
    private MigDao migDao;

    @Mock
    private ExtractieDao extractieDao;

    @InjectMocks
    private final ProcesService subject = new ProcesServiceImpl();

    @Test
    public void testSelecterenIdsVanOpTeSchonenProcessen() {

        final List<Long> processenLijst = new ArrayList<>();
        processenLijst.add((long) 1);

        Mockito.when(extractieDao.haalProcesIdsOpBasisVanBeeindigdeProcesExtractiesOp(DATUM_TOT)).thenReturn(processenLijst);

        final List<Long> result = subject.selecteerIdsVanOpTeSchonenProcessen(DATUM_TOT);

        Mockito.verify(extractieDao, Mockito.times(1)).haalProcesIdsOpBasisVanBeeindigdeProcesExtractiesOp(DATUM_TOT);
        Mockito.verifyNoMoreInteractions(extractieDao);
        Mockito.verifyZeroInteractions(jbpmDao, migDao);

        Assert.assertEquals(processenLijst, result);
    }

    @Test
    public void testControleerProcesVerwijderbaarOkGeenSubEnGerelateerdeProcessen() throws NietVerwijderbareProcesInstantieException {

        final List<Long> verwerkteProcesIds = new ArrayList<>();
        final List<Long> verwijderdeProcesIds = new ArrayList<>();
        final Long procesId = (long) 1;
        final Timestamp datumLaatsteBericht = new Timestamp(System.currentTimeMillis());
        final Timestamp einddatumProces = new Timestamp(datumLaatsteBericht.getTime() + 1000);

        Mockito.when(jbpmDao.haalEinddatumProcesOp(procesId)).thenReturn(einddatumProces);
        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(procesId)).thenReturn(new ArrayList<Long>());
        Mockito.when(migDao.bepaalDatumLaatsteBerichtOntvangenVoorProces(procesId)).thenReturn(datumLaatsteBericht);
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(procesId)).thenReturn(new ArrayList<Long>());

        subject.controleerProcesVerwijderbaar(procesId, verwerkteProcesIds, verwijderdeProcesIds);

        Mockito.verify(jbpmDao, Mockito.times(1)).haalEinddatumProcesOp(procesId);
        Mockito.verify(migDao, Mockito.times(1)).bepaalDatumLaatsteBerichtOntvangenVoorProces(procesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).selecteerSubProcessenVoorProces(procesId);
        Mockito.verify(migDao, Mockito.times(1)).selecteerGerelateerdeProcessenVoorProces(procesId);
        Mockito.verifyNoMoreInteractions(jbpmDao, migDao);
        Mockito.verifyZeroInteractions(extractieDao);

        Assert.assertEquals(1, verwerkteProcesIds.size());
        Assert.assertEquals(verwerkteProcesIds.get(0), procesId);
    }

    @Test
    public void testControleerProcesVerwijderbaarOkBeeindigdeSubEnGereleteerdeProcessen() throws NietVerwijderbareProcesInstantieException {

        final List<Long> verwerkteProcesIds = new ArrayList<>();
        final List<Long> verwijderdeProcesIds = new ArrayList<>();
        final Long procesId = (long) 1;
        final Long subProcesId = (long) 0;
        final Long gerelateerdProcesId = (long) 2;
        final Timestamp datumLaatsteBericht = new Timestamp(System.currentTimeMillis());
        final Timestamp einddatumProces = new Timestamp(datumLaatsteBericht.getTime() + 1000);
        final List<Long> subProcessenLijst = new ArrayList<>();
        subProcessenLijst.add(subProcesId);
        final List<Long> gerelateerdeProcessenLijst = new ArrayList<>();
        gerelateerdeProcessenLijst.add(gerelateerdProcesId);

        Mockito.when(jbpmDao.haalEinddatumProcesOp(procesId)).thenReturn(einddatumProces);
        Mockito.when(jbpmDao.haalEinddatumProcesOp(subProcesId)).thenReturn(einddatumProces);
        Mockito.when(jbpmDao.haalEinddatumProcesOp(gerelateerdProcesId)).thenReturn(einddatumProces);
        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(procesId)).thenReturn(subProcessenLijst);
        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(subProcesId)).thenReturn(new ArrayList<Long>());
        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(gerelateerdProcesId)).thenReturn(new ArrayList<Long>());
        Mockito.when(migDao.bepaalDatumLaatsteBerichtOntvangenVoorProces(procesId)).thenReturn(datumLaatsteBericht);
        Mockito.when(migDao.bepaalDatumLaatsteBerichtOntvangenVoorProces(subProcesId)).thenReturn(datumLaatsteBericht);
        Mockito.when(migDao.bepaalDatumLaatsteBerichtOntvangenVoorProces(gerelateerdProcesId)).thenReturn(datumLaatsteBericht);
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(procesId)).thenReturn(gerelateerdeProcessenLijst);
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(subProcesId)).thenReturn(new ArrayList<Long>());
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(gerelateerdProcesId)).thenReturn(new ArrayList<Long>());

        subject.controleerProcesVerwijderbaar(procesId, verwerkteProcesIds, verwijderdeProcesIds);

        Mockito.verify(jbpmDao, Mockito.times(1)).haalEinddatumProcesOp(procesId);
        Mockito.verify(migDao, Mockito.times(1)).bepaalDatumLaatsteBerichtOntvangenVoorProces(procesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).selecteerSubProcessenVoorProces(procesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).haalEinddatumProcesOp(subProcesId);
        Mockito.verify(migDao, Mockito.times(1)).bepaalDatumLaatsteBerichtOntvangenVoorProces(subProcesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).selecteerSubProcessenVoorProces(subProcesId);
        Mockito.verify(migDao, Mockito.times(1)).selecteerGerelateerdeProcessenVoorProces(subProcesId);
        Mockito.verify(migDao, Mockito.times(1)).selecteerGerelateerdeProcessenVoorProces(procesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).haalEinddatumProcesOp(gerelateerdProcesId);
        Mockito.verify(migDao, Mockito.times(1)).bepaalDatumLaatsteBerichtOntvangenVoorProces(gerelateerdProcesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).selecteerSubProcessenVoorProces(gerelateerdProcesId);
        Mockito.verify(migDao, Mockito.times(1)).selecteerGerelateerdeProcessenVoorProces(gerelateerdProcesId);
        Mockito.verifyNoMoreInteractions(jbpmDao, migDao);
        Mockito.verifyZeroInteractions(extractieDao);

        Assert.assertEquals(3, verwerkteProcesIds.size());
        Assert.assertEquals(verwerkteProcesIds.get(0), procesId);
        Assert.assertEquals(verwerkteProcesIds.get(1), subProcesId);
        Assert.assertEquals(verwerkteProcesIds.get(2), gerelateerdProcesId);
    }

    @Test
    public void testControleerProcesVerwijderbaarOkBeeindigdeSubEnGereleteerdeProcessenDubbeleVerwijzingen()
        throws NietVerwijderbareProcesInstantieException
    {

        final List<Long> verwerkteProcesIds = new ArrayList<>();
        final List<Long> verwijderdeProcesIds = new ArrayList<>();
        final Long procesId = (long) 1;
        final Long subProcesId = (long) 0;
        final Long gerelateerdProcesId = (long) 2;
        final Timestamp datumLaatsteBericht = new Timestamp(System.currentTimeMillis());
        final Timestamp einddatumProces = new Timestamp(datumLaatsteBericht.getTime() + 1000);
        final List<Long> subProcessenLijst = new ArrayList<>();
        subProcessenLijst.add(subProcesId);
        final List<Long> gerelateerdeProcessenLijst = new ArrayList<>();
        gerelateerdeProcessenLijst.add(gerelateerdProcesId);

        Mockito.when(jbpmDao.haalEinddatumProcesOp(procesId)).thenReturn(einddatumProces);
        Mockito.when(jbpmDao.haalEinddatumProcesOp(subProcesId)).thenReturn(einddatumProces);
        Mockito.when(jbpmDao.haalEinddatumProcesOp(gerelateerdProcesId)).thenReturn(einddatumProces);
        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(procesId)).thenReturn(subProcessenLijst);
        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(subProcesId)).thenReturn(gerelateerdeProcessenLijst);
        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(gerelateerdProcesId)).thenReturn(subProcessenLijst);
        Mockito.when(migDao.bepaalDatumLaatsteBerichtOntvangenVoorProces(procesId)).thenReturn(datumLaatsteBericht);
        Mockito.when(migDao.bepaalDatumLaatsteBerichtOntvangenVoorProces(subProcesId)).thenReturn(datumLaatsteBericht);
        Mockito.when(migDao.bepaalDatumLaatsteBerichtOntvangenVoorProces(gerelateerdProcesId)).thenReturn(datumLaatsteBericht);
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(procesId)).thenReturn(gerelateerdeProcessenLijst);
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(subProcesId)).thenReturn(gerelateerdeProcessenLijst);
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(gerelateerdProcesId)).thenReturn(subProcessenLijst);

        subject.controleerProcesVerwijderbaar(procesId, verwerkteProcesIds, verwijderdeProcesIds);

        Mockito.verify(jbpmDao, Mockito.times(1)).haalEinddatumProcesOp(procesId);
        Mockito.verify(migDao, Mockito.times(1)).bepaalDatumLaatsteBerichtOntvangenVoorProces(procesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).selecteerSubProcessenVoorProces(procesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).haalEinddatumProcesOp(subProcesId);
        Mockito.verify(migDao, Mockito.times(1)).bepaalDatumLaatsteBerichtOntvangenVoorProces(subProcesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).selecteerSubProcessenVoorProces(subProcesId);
        Mockito.verify(migDao, Mockito.times(1)).selecteerGerelateerdeProcessenVoorProces(subProcesId);
        Mockito.verify(migDao, Mockito.times(1)).selecteerGerelateerdeProcessenVoorProces(procesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).haalEinddatumProcesOp(gerelateerdProcesId);
        Mockito.verify(migDao, Mockito.times(1)).bepaalDatumLaatsteBerichtOntvangenVoorProces(gerelateerdProcesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).selecteerSubProcessenVoorProces(gerelateerdProcesId);
        Mockito.verify(migDao, Mockito.times(1)).selecteerGerelateerdeProcessenVoorProces(gerelateerdProcesId);
        Mockito.verifyNoMoreInteractions(jbpmDao, migDao);
        Mockito.verifyZeroInteractions(extractieDao);

        Assert.assertEquals(3, verwerkteProcesIds.size());
        Assert.assertEquals(verwerkteProcesIds.get(0), procesId);
        Assert.assertEquals(verwerkteProcesIds.get(1), subProcesId);
        Assert.assertEquals(verwerkteProcesIds.get(2), gerelateerdProcesId);
    }

    @Test
    public void testControleerProcesVerwijderbaarNietBeeindigdGeenSubEnGerelateerdeProcessen()
        throws NietVerwijderbareProcesInstantieException
    {

        final List<Long> verwerkteProcesIds = new ArrayList<>();
        final List<Long> verwijderdeProcesIds = new ArrayList<>();
        final Long procesId = (long) 1;
        final Timestamp datumLaatsteBericht = new Timestamp(System.currentTimeMillis());
        final Timestamp einddatumProces = null;

        Mockito.when(jbpmDao.haalEinddatumProcesOp(procesId)).thenReturn(einddatumProces);
        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(procesId)).thenReturn(new ArrayList<Long>());
        Mockito.when(migDao.bepaalDatumLaatsteBerichtOntvangenVoorProces(procesId)).thenReturn(datumLaatsteBericht);
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(procesId)).thenReturn(new ArrayList<Long>());

        try {
            subject.controleerProcesVerwijderbaar(procesId, verwerkteProcesIds, verwijderdeProcesIds);
        } catch (final NietVerwijderbareProcesInstantieException exceptie) {
            Assert.assertNotNull(exceptie.getLaatsteActiviteitDatum());
            Assert.assertNotSame(datumLaatsteBericht, exceptie.getLaatsteActiviteitDatum());
            Assert.assertEquals("Het proces (id="
                                + procesId
                                + " )kan niet worden verwijderd aangezien er nog een of meerdere sub- of gerelateerd(e) "
                                + "proces(sen) nog niet is/zijn beeindigd).", exceptie.getMessage());
        }

        Mockito.verify(jbpmDao, Mockito.times(1)).haalEinddatumProcesOp(procesId);
        Mockito.verify(migDao, Mockito.times(1)).bepaalDatumLaatsteBerichtOntvangenVoorProces(procesId);
        Mockito.verifyNoMoreInteractions(jbpmDao, migDao);
        Mockito.verifyZeroInteractions(extractieDao);

        Assert.assertEquals(1, verwerkteProcesIds.size());
        Assert.assertEquals(verwerkteProcesIds.get(0), procesId);
    }

    @Test
    public void testControleerProcesVerwijderbaarBerichtNaBeeindigingGeenSubEnGerelateerdeProcessen()
        throws NietVerwijderbareProcesInstantieException
    {

        final List<Long> verwerkteProcesIds = new ArrayList<>();
        final List<Long> verwijderdeProcesIds = new ArrayList<>();
        final Long procesId = (long) 1;
        final Timestamp einddatumProces = new Timestamp(System.currentTimeMillis());
        final Timestamp datumLaatsteBericht = new Timestamp(einddatumProces.getTime() + 1000);

        Mockito.when(jbpmDao.haalEinddatumProcesOp(procesId)).thenReturn(einddatumProces);
        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(procesId)).thenReturn(new ArrayList<Long>());
        Mockito.when(migDao.bepaalDatumLaatsteBerichtOntvangenVoorProces(procesId)).thenReturn(datumLaatsteBericht);
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(procesId)).thenReturn(new ArrayList<Long>());

        try {
            subject.controleerProcesVerwijderbaar(procesId, verwerkteProcesIds, verwijderdeProcesIds);
        } catch (final NietVerwijderbareProcesInstantieException exceptie) {
            Assert.assertNotNull(exceptie.getLaatsteActiviteitDatum());
            Assert.assertEquals(datumLaatsteBericht, exceptie.getLaatsteActiviteitDatum());
            Assert.assertEquals("Het proces (id= "
                                + procesId
                                + " ) kan niet worden verwijderd (er is nog een bericht binnengekomen nadat het proces is "
                                + "beeindigd).", exceptie.getMessage());
        }

        Mockito.verify(jbpmDao, Mockito.times(1)).haalEinddatumProcesOp(procesId);
        Mockito.verify(migDao, Mockito.times(1)).bepaalDatumLaatsteBerichtOntvangenVoorProces(procesId);
        Mockito.verifyNoMoreInteractions(jbpmDao, migDao);
        Mockito.verifyZeroInteractions(extractieDao);

        Assert.assertEquals(1, verwerkteProcesIds.size());
        Assert.assertEquals(verwerkteProcesIds.get(0), procesId);
    }

    @Test
    public void testControleerProcesVerwijderbaarBerichtDatumNullGeenSubEnGerelateerdeProcessen()
        throws NietVerwijderbareProcesInstantieException
    {

        final List<Long> verwerkteProcesIds = new ArrayList<>();
        final List<Long> verwijderdeProcesIds = new ArrayList<>();
        final Long procesId = (long) 1;
        final Timestamp einddatumProces = new Timestamp(System.currentTimeMillis());
        final Timestamp datumLaatsteBericht = null;

        Mockito.when(jbpmDao.haalEinddatumProcesOp(procesId)).thenReturn(einddatumProces);
        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(procesId)).thenReturn(new ArrayList<Long>());
        Mockito.when(migDao.bepaalDatumLaatsteBerichtOntvangenVoorProces(procesId)).thenReturn(datumLaatsteBericht);
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(procesId)).thenReturn(new ArrayList<Long>());

        subject.controleerProcesVerwijderbaar(procesId, verwerkteProcesIds, verwijderdeProcesIds);

        Mockito.verify(jbpmDao, Mockito.times(1)).haalEinddatumProcesOp(procesId);
        Mockito.verify(migDao, Mockito.times(1)).bepaalDatumLaatsteBerichtOntvangenVoorProces(procesId);
        Mockito.verifyNoMoreInteractions(jbpmDao, migDao);
        Mockito.verifyZeroInteractions(extractieDao);

        Assert.assertEquals(1, verwerkteProcesIds.size());
        Assert.assertEquals(verwerkteProcesIds.get(0), procesId);
    }

    @Test
    public void testControleerProcesVerwijderbaarProcesAlVerwijderdGeenSubEnGerelateerdeProcessen()
        throws NietVerwijderbareProcesInstantieException
    {

        final List<Long> verwerkteProcesIds = new ArrayList<>();
        final List<Long> verwijderdeProcesIds = new ArrayList<>();
        final Long procesId = (long) 1;
        verwijderdeProcesIds.add(procesId);

        subject.controleerProcesVerwijderbaar(procesId, verwerkteProcesIds, verwijderdeProcesIds);

        Mockito.verifyZeroInteractions(extractieDao, jbpmDao, migDao);

        Assert.assertEquals(0, verwerkteProcesIds.size());

    }

    @Test
    public void testVerwijderProcesGeenSubEnGerelateerdeProcessen() throws NietVerwijderbareProcesInstantieException {

        final List<Long> verwerkteProcesIds = new ArrayList<>();
        final List<Long> verwijderdeProcesIds = new ArrayList<>();
        final List<Long> tokens = new ArrayList<>();
        final List<Long> taskInstances = new ArrayList<>();
        final List<Long> variableInstances = new ArrayList<>();
        final List<Long> byteArrays = new ArrayList<>();
        final Long procesId = (long) 1;
        final Long tokenId = (long) 11;
        final Long taskInstanceId = (long) 111;
        final Long variableInstanceId = (long) 1111;
        final Long byteArray = (long) 11111;
        tokens.add(tokenId);
        taskInstances.add(taskInstanceId);
        variableInstances.add(variableInstanceId);
        byteArrays.add(byteArray);

        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(procesId)).thenReturn(new ArrayList<Long>());
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(procesId)).thenReturn(new ArrayList<Long>());
        Mockito.when(jbpmDao.bepaalTokensVoorProces(procesId)).thenReturn(tokens);
        Mockito.when(jbpmDao.bepaalTaskInstancesVoorProces(procesId)).thenReturn(taskInstances);
        Mockito.when(jbpmDao.bepaalVariableInstancesVoorTokens(tokens)).thenReturn(variableInstances);
        Mockito.when(jbpmDao.bepaalByteArraysVoorVariableInstances(variableInstances)).thenReturn(byteArrays);

        subject.verwijderProces(procesId, verwerkteProcesIds, verwijderdeProcesIds);

        Mockito.verify(migDao, Mockito.times(1)).selecteerGerelateerdeProcessenVoorProces(procesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).selecteerSubProcessenVoorProces(procesId);
        Mockito.verify(jbpmDao).bepaalTokensVoorProces(procesId);
        Mockito.verify(jbpmDao).bepaalTaskInstancesVoorProces(procesId);
        Mockito.verify(jbpmDao).bepaalVariableInstancesVoorTokens(tokens);
        Mockito.verify(jbpmDao).bepaalByteArraysVoorVariableInstances(variableInstances);
        Mockito.verify(jbpmDao).verwijderByteBlocks(byteArrays);
        Mockito.verify(jbpmDao).elimineerReferentieLogNaarLogVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderLogsVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderCommentsVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderVariableInstancesVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderTokenVariableMapVoorToken(tokens);
        Mockito.verify(jbpmDao).verwijderByteArrays(byteArrays);
        Mockito.verify(jbpmDao).elimineerReferentieTokenNaarTokenVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderSwimlaneInstancesVoorTaskInstances(taskInstances);
        Mockito.verify(jbpmDao).verwijderTaskActorPoolsVoorTaskInstances(taskInstances);
        Mockito.verify(jbpmDao).verwijderRuntimeActionsVoorProces(procesId);
        Mockito.verify(jbpmDao).verwijderTaskInstancesVoorProces(procesId);
        Mockito.verify(jbpmDao).verwijderJobsVoorProces(procesId);
        Mockito.verify(jbpmDao).verwijderModuleInstancesVoorProces(procesId);
        Mockito.verify(jbpmDao).elimineerReferentieTokenNaarProcesInstantie(tokens);
        Mockito.verify(jbpmDao).verwijderProcessInstancesVoorProces(procesId);
        Mockito.verify(jbpmDao).verwijderTokens(tokens);
        Mockito.verify(migDao).verwijderBerichtenVanProces(procesId);

        Mockito.verifyNoMoreInteractions(jbpmDao, migDao);
        Mockito.verifyZeroInteractions(extractieDao);

        Assert.assertEquals(0, verwijderdeProcesIds.size());

    }

    @Test
    public void testVerwijderProcesMetSubEnGerelateerdeProcessen() throws NietVerwijderbareProcesInstantieException {

        final List<Long> verwerkteProcesIds = new ArrayList<>();
        final List<Long> verwijderdeProcesIds = new ArrayList<>();
        final List<Long> gerelateerdeProcessen = new ArrayList<>();
        final List<Long> subProcessen = new ArrayList<>();
        final List<Long> tokens = new ArrayList<>();
        final List<Long> taskInstances = new ArrayList<>();
        final List<Long> variableInstances = new ArrayList<>();
        final List<Long> byteArrays = new ArrayList<>();
        final Long procesId = (long) 1;
        final Long gerelateerdProcesId = (long) 2;
        final Long subProcesId = (long) 3;
        final Long tokenId = (long) 11;
        final Long taskInstanceId = (long) 111;
        final Long variableInstanceId = (long) 1111;
        final Long byteArray = (long) 11111;
        gerelateerdeProcessen.add(gerelateerdProcesId);
        subProcessen.add(subProcesId);
        tokens.add(tokenId);
        taskInstances.add(taskInstanceId);
        variableInstances.add(variableInstanceId);
        byteArrays.add(byteArray);

        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(procesId)).thenReturn(subProcessen);
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(procesId)).thenReturn(gerelateerdeProcessen);
        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(subProcesId)).thenReturn(new ArrayList<Long>());
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(subProcesId)).thenReturn(new ArrayList<Long>());
        Mockito.when(jbpmDao.selecteerSubProcessenVoorProces(gerelateerdProcesId)).thenReturn(new ArrayList<Long>());
        Mockito.when(migDao.selecteerGerelateerdeProcessenVoorProces(gerelateerdProcesId)).thenReturn(new ArrayList<Long>());
        Mockito.when(jbpmDao.bepaalTokensVoorProces(procesId)).thenReturn(tokens);
        Mockito.when(jbpmDao.bepaalTaskInstancesVoorProces(procesId)).thenReturn(taskInstances);
        Mockito.when(jbpmDao.bepaalVariableInstancesVoorTokens(tokens)).thenReturn(variableInstances);
        Mockito.when(jbpmDao.bepaalByteArraysVoorVariableInstances(variableInstances)).thenReturn(byteArrays);

        subject.verwijderProces(procesId, verwerkteProcesIds, verwijderdeProcesIds);

        Mockito.verify(migDao, Mockito.times(1)).selecteerGerelateerdeProcessenVoorProces(procesId);
        Mockito.verify(migDao, Mockito.times(1)).selecteerGerelateerdeProcessenVoorProces(subProcesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).selecteerSubProcessenVoorProces(subProcesId);
        Mockito.verify(migDao, Mockito.times(1)).selecteerGerelateerdeProcessenVoorProces(gerelateerdProcesId);
        Mockito.verify(jbpmDao, Mockito.times(1)).selecteerSubProcessenVoorProces(gerelateerdProcesId);

        // Verwijder gerelateerd proces.
        Mockito.verify(jbpmDao).bepaalTokensVoorProces(gerelateerdProcesId);
        Mockito.verify(jbpmDao).bepaalTaskInstancesVoorProces(gerelateerdProcesId);
        Mockito.verify(jbpmDao).bepaalVariableInstancesVoorTokens(tokens);
        Mockito.verify(jbpmDao).bepaalByteArraysVoorVariableInstances(variableInstances);
        Mockito.verify(jbpmDao).verwijderByteBlocks(byteArrays);
        Mockito.verify(jbpmDao).elimineerReferentieLogNaarLogVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderLogsVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderCommentsVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderVariableInstancesVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderTokenVariableMapVoorToken(tokens);
        Mockito.verify(jbpmDao).verwijderByteArrays(byteArrays);
        Mockito.verify(jbpmDao).elimineerReferentieTokenNaarTokenVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderSwimlaneInstancesVoorTaskInstances(taskInstances);
        Mockito.verify(jbpmDao).verwijderTaskActorPoolsVoorTaskInstances(taskInstances);
        Mockito.verify(jbpmDao).verwijderRuntimeActionsVoorProces(gerelateerdProcesId);
        Mockito.verify(jbpmDao).verwijderTaskInstancesVoorProces(gerelateerdProcesId);
        Mockito.verify(jbpmDao).verwijderJobsVoorProces(gerelateerdProcesId);
        Mockito.verify(jbpmDao).verwijderModuleInstancesVoorProces(gerelateerdProcesId);
        Mockito.verify(jbpmDao).elimineerReferentieTokenNaarProcesInstantie(tokens);
        Mockito.verify(jbpmDao).verwijderProcessInstancesVoorProces(gerelateerdProcesId);
        Mockito.verify(jbpmDao).verwijderTokens(tokens);
        Mockito.verify(migDao).verwijderBerichtenVanProces(gerelateerdProcesId);
        Mockito.verify(migDao).verwijderGerelateerdProcesVerwijzingVoorProces(procesId, gerelateerdProcesId);

        // Verwijder subproces.
        Mockito.verify(jbpmDao).bepaalTokensVoorProces(subProcesId);
        Mockito.verify(jbpmDao).bepaalTaskInstancesVoorProces(subProcesId);
        Mockito.verify(jbpmDao).bepaalVariableInstancesVoorTokens(tokens);
        Mockito.verify(jbpmDao).bepaalByteArraysVoorVariableInstances(variableInstances);
        Mockito.verify(jbpmDao).verwijderByteBlocks(byteArrays);
        Mockito.verify(jbpmDao).elimineerReferentieLogNaarLogVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderLogsVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderCommentsVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderVariableInstancesVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderTokenVariableMapVoorToken(tokens);
        Mockito.verify(jbpmDao).verwijderByteArrays(byteArrays);
        Mockito.verify(jbpmDao).elimineerReferentieTokenNaarTokenVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderSwimlaneInstancesVoorTaskInstances(taskInstances);
        Mockito.verify(jbpmDao).verwijderTaskActorPoolsVoorTaskInstances(taskInstances);
        Mockito.verify(jbpmDao).verwijderRuntimeActionsVoorProces(subProcesId);
        Mockito.verify(jbpmDao).verwijderTaskInstancesVoorProces(subProcesId);
        Mockito.verify(jbpmDao).verwijderJobsVoorProces(subProcesId);
        Mockito.verify(jbpmDao).verwijderModuleInstancesVoorProces(subProcesId);
        Mockito.verify(jbpmDao).elimineerReferentieTokenNaarProcesInstantie(tokens);
        Mockito.verify(jbpmDao).verwijderProcessInstancesVoorProces(subProcesId);
        Mockito.verify(jbpmDao).verwijderTokens(tokens);
        Mockito.verify(migDao).verwijderBerichtenVanProces(subProcesId);

        // Verwijder root proces.
        Mockito.verify(jbpmDao).bepaalTokensVoorProces(procesId);
        Mockito.verify(jbpmDao).bepaalTaskInstancesVoorProces(procesId);
        Mockito.verify(jbpmDao).bepaalVariableInstancesVoorTokens(tokens);
        Mockito.verify(jbpmDao).bepaalByteArraysVoorVariableInstances(variableInstances);
        Mockito.verify(jbpmDao).verwijderByteBlocks(byteArrays);
        Mockito.verify(jbpmDao).elimineerReferentieLogNaarLogVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderLogsVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderCommentsVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderVariableInstancesVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderTokenVariableMapVoorToken(tokens);
        Mockito.verify(jbpmDao).verwijderByteArrays(byteArrays);
        Mockito.verify(jbpmDao).elimineerReferentieTokenNaarTokenVoorTokens(tokens);
        Mockito.verify(jbpmDao).verwijderSwimlaneInstancesVoorTaskInstances(taskInstances);
        Mockito.verify(jbpmDao).verwijderTaskActorPoolsVoorTaskInstances(taskInstances);
        Mockito.verify(jbpmDao).verwijderRuntimeActionsVoorProces(procesId);
        Mockito.verify(jbpmDao).verwijderTaskInstancesVoorProces(procesId);
        Mockito.verify(jbpmDao).verwijderJobsVoorProces(procesId);
        Mockito.verify(jbpmDao).verwijderModuleInstancesVoorProces(procesId);
        Mockito.verify(jbpmDao).elimineerReferentieTokenNaarProcesInstantie(tokens);
        Mockito.verify(jbpmDao).verwijderProcessInstancesVoorProces(procesId);
        Mockito.verify(jbpmDao).verwijderTokens(tokens);
        Mockito.verify(migDao).verwijderBerichtenVanProces(procesId);

        Mockito.verifyZeroInteractions(extractieDao);

        Assert.assertEquals(3, verwerkteProcesIds.size());
        Assert.assertEquals(2, verwijderdeProcesIds.size());
        Assert.assertEquals(gerelateerdProcesId, verwijderdeProcesIds.get(0));
        Assert.assertEquals(subProcesId, verwijderdeProcesIds.get(1));

    }

    @Test
    public void testUpdateVerwachteVerwijderDatum() {

        final Long procesId = (long) 1;
        final Timestamp verwachteVerwijderDatum = new Timestamp(System.currentTimeMillis());

        subject.updateVerwachteVerwijderDatumProces(procesId, verwachteVerwijderDatum);

        Mockito.verify(extractieDao).updateVerwachteVerwijderDatum(procesId, verwachteVerwijderDatum);

        Mockito.verifyNoMoreInteractions(extractieDao);
        Mockito.verifyNoMoreInteractions(jbpmDao, migDao);

    }
}
