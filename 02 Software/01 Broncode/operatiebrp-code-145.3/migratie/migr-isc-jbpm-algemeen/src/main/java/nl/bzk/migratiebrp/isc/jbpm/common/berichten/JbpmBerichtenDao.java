/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.berichten;

import java.util.List;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.BerichtMetaData;
import nl.bzk.migratiebrp.isc.jbpm.common.JbpmDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringServiceUtil;

/**
 * Berichten DAO implementatie obv connectie uit JBPM.
 */
public final class JbpmBerichtenDao extends JbpmDao implements BerichtenDao {
    /**
     * Static beschikbare instantie van de configuratie dao (voor integratie in de processen).
     */
    public static final BerichtenDao INSTANCE = new JbpmBerichtenDao();

    private JbpmBerichtenDao() {
        // Niet extern instantieerbaar
    }

    @Override
    public Bericht leesBericht(final Long id) {
        return SpringServiceUtil.getBean(BerichtenDao.class).leesBericht(id);
    }

    @Override
    public Long bewaarBericht(final Bericht bericht) {
        throw new UnsupportedOperationException("bewaarBericht niet toegestaan");
    }

    @Override
    public BerichtMetaData leesBerichtMetaData(final Long id) {
        throw new UnsupportedOperationException("leesBerichtMetaData niet toegestaan");
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
        throw new UnsupportedOperationException("bewaar niet toegestaan");

    }

    @Override
    public void updateNaam(final Long berichtId, final String naam) {
        throw new UnsupportedOperationException("updateNaam niet toegestaan");
    }

    @Override
    public void updateProcessInstance(final Long berichtId, final Long processInstanceId) {
        throw new UnsupportedOperationException("updateProcessInstance niet toegestaan");
    }

    @Override
    public void updateActie(final Long berichtId, final String actie) {
        throw new UnsupportedOperationException("updateActie niet toegestaan");
    }

    @Override
    public nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht zoekVraagBericht(
            final String correlatieId,
            final String kanaal,
            final String originator,
            final String recipient) {
        throw new UnsupportedOperationException("updateActie niet toegestaan");
    }

    @Override
    public List<nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht> zoekOpMsSequenceNumberBehalveId(final Long msSequenceNumber, final Long berichtId) {
        throw new UnsupportedOperationException("zoekOpMsSequenceNumberBehalveId niet toegestaan");
    }

    @Override
    public int telBerichtenBehalveId(
            final String messageId,
            final String originator,
            final String recipient,
            final String kanaal,
            final Direction direction,
            final Long berichtId) {
        throw new UnsupportedOperationException("telBerichtenBehalveId niet toegestaan");
    }

    @Override
    public nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht zoekMeestRecenteAntwoord(
            final String messageId,
            final String originator,
            final String recipient,
            final String kanaal) {
        throw new UnsupportedOperationException("zoekMeestRecenteAntwoord niet toegestaan");
    }

    @Override
    public void updateMessageId(final Long berichtId, final String messageId) {
        throw new UnsupportedOperationException("updateMessageId niet toegestaan");
    }

    @Override
    public void updateCorrelatieId(final Long berichtId, final String correlatieId) {
        throw new UnsupportedOperationException("updateCorrelatieId niet toegestaan");
    }

    @Override
    public void updateVirtueelProcesId(final Long berichtId, final Long virtueelProcesId) {
        throw new UnsupportedOperationException("updateVirtueelProcesId niet toegestaan");
    }

    @Override
    public void omzettenVirtueelProcesNaarActueelProces(final long virtueelProcesId, final long processInstanceId) {
        throw new UnsupportedOperationException("omzettenVirtueelProcesNaarActueelProces niet toegestaan");
    }

    @Override
    public nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht getBericht(final Long berichtId) {
        throw new UnsupportedOperationException("getBericht niet toegestaan");
    }

    @Override
    public void updateKanaalEnRichting(final Long berichtId, final String kanaal, final Direction direction) {
        throw new UnsupportedOperationException("updateKanaalEnRichting niet toegestaan");
    }

}
