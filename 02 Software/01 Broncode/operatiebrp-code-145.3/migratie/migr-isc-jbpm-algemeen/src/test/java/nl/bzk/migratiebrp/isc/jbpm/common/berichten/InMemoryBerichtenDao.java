/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.berichten;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.BerichtMetaData;
import nl.bzk.migratiebrp.bericht.model.MessageId;

public class InMemoryBerichtenDao implements BerichtenDao {

    private final AtomicLong sequence = new AtomicLong();
    private final Map<Long, Bericht> store = new HashMap<>();
    private final Map<Long, BerichtMetaData> metaDataStore = new HashMap<>();

    @Override
    public Bericht leesBericht(final Long id) {
        return store.get(id);
    }

    @Override
    public Long bewaarBericht(final Bericht bericht) {
        final Long id = sequence.incrementAndGet();
        if (bericht.getMessageId() == null) {
            bericht.setMessageId(MessageId.bepaalMessageId(id));
        }
        store.put(id, bericht);

        final BerichtMetaData berichtMetaData = new BerichtMetaData();
        berichtMetaData.setBerichtType(bericht.getBerichtType());
        berichtMetaData.setCorrelationId(bericht.getCorrelationId());
        berichtMetaData.setMessageId(bericht.getMessageId());
        metaDataStore.put(id, berichtMetaData);
        return id;
    }

    @Override
    public BerichtMetaData leesBerichtMetaData(final Long id) {
        return metaDataStore.get(id);
    }

    @Override
    public Long bewaar(
            final String kanaal,
            final Direction direction,
            final String messageId,
            final String correlatieId,
            final String bericht,
            final String originator,
            final String recipient,
            final Long msSequenceNumber,
            final Boolean requestNonReceipt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNaam(final Long berichtId, final String naam) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateProcessInstance(final Long berichtId, final Long processInstanceId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateActie(final Long berichtId, final String actie) {
        throw new UnsupportedOperationException();
    }

    @Override
    public nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht zoekVraagBericht(
            final String correlatieId,
            final String kanaal,
            final String originator,
            final String recipient) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht> zoekOpMsSequenceNumberBehalveId(final Long msSequenceNumber, final Long berichtId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int telBerichtenBehalveId(
            final String messageId,
            final String originator,
            final String recipient,
            final String kanaal,
            final Direction direction,
            final Long berichtId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht zoekMeestRecenteAntwoord(
            final String messageId,
            final String originator,
            final String recipient,
            final String kanaal) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateMessageId(final Long berichtId, final String messageId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateCorrelatieId(final Long berichtId, final String correlatieId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateVirtueelProcesId(final Long berichtId, final Long virtueelProcesId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void omzettenVirtueelProcesNaarActueelProces(final long virtueelProcesId, final long processInstanceId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht getBericht(final Long berichtId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateKanaalEnRichting(final Long berichtId, final String kanaal, final Direction direction) {
        throw new UnsupportedOperationException();
    }

}
