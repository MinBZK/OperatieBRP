/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;
import javax.sql.DataSource;

import nl.bzk.brp.bevraging.business.configuratie.BrpConfiguratie;
import nl.bzk.brp.bevraging.business.handlers.BerichtVerwerkingsStap;
import nl.bzk.brp.bevraging.business.service.BsnLocker;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Creeert shared en exclusive locks op basis van BSN's. Deze implementatie is specifiek voor de Postgresql database.
 */
public class BsnLockerImpl implements BsnLocker {

    private static final Logger                  LOGGER                       = LoggerFactory
                                                                                      .getLogger(BsnLockerImpl.class);
    private static final ThreadLocal<Connection> TRANSACTIONSTATUSTHREADLOCAL = new ThreadLocal<Connection>();
    private static final int                     MSEC_IN_SECONDS              = 1000;
    private static final String                  TABLE                        = "Kern.PersoonsLock";
    private static final String                  COLUMN                       = "BSN";

    @Inject
    private DataSource                           dataSource;

    @Inject
    private BrpConfiguratie                      brpConfiguratie;

    /**
     * Converteert een aantal objecten naar een string met comma's er tussen.
     *
     * @param <T> type van collection
     * @param collection die geimplodeert moet worden
     * @return String met comma seperation
     */
    private static <T> String implode(final Collection<T> collection) {
        StringBuilder resultaat = new StringBuilder();
        boolean first = true;

        for (T mylong : collection) {
            if (!first) {
                resultaat.append(",");
            }
            resultaat.append("'").append(mylong).append("'");
            first = false;
        }

        return resultaat.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getLocks(final Long berichtId, final Collection<String> readBsnLocks,
            final Collection<String> writeBsnLocks)
    {
        if (TRANSACTIONSTATUSTHREADLOCAL.get() != null) {
            throw new IllegalStateException(
                    "Deze thread heeft al een lock connectie, niet 2 keer per thread getLocks() aanroepen!");
        }

        boolean resultaat = BerichtVerwerkingsStap.STOP_VERWERKING;
        try {
            voegBsnsToeAanLockTabel(berichtId, readBsnLocks, writeBsnLocks);
            creeerConnectieVoorTransactieEnPlaatsLock(berichtId, readBsnLocks, writeBsnLocks);

            resultaat = BerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;

        } catch (SQLException e) {
            LOGGER.error("Kan geen lock krijgen voor bericht " + berichtId, e);
            resultaat = BerichtVerwerkingsStap.STOP_VERWERKING;
        }
        return resultaat;
    }

    /**
     * Voegt de BSNs uit de twee collecties toe aan de tabel waarin de locks worden bijgehouden. Door het toevoegen
     * wordt dus niet al de lock gelegd, maar wordt puur de BSN toegevoegd aan de lijst van BSNs zodat er in een
     * latere fase op gelockt kan worden. Indien de BSN al in de tabel aanwezig is, wordt deze uiteraard niet
     * nogmaals toegevoegd.
     *
     * @param berichtId het id van het bericht dat verwerkt wordt.
     * @param readBsnLocks lijst van BSNs die moeten worden toegevoegd.
     * @param writeBsnLocks lijst van BSNs die moeten worden toegevoegd.
     * @throws SQLException indien er iets mis gaat met de connectie of het toevoegen van de BSNs aan de database.
     */
    private void voegBsnsToeAanLockTabel(final Long berichtId, final Collection<String> readBsnLocks,
            final Collection<String> writeBsnLocks) throws SQLException
    {
        Collection<String> alleBSNs = voegBsnCollectiesSamen(readBsnLocks, writeBsnLocks);
        Connection connectie = dataSource.getConnection();

        try {
            conditioneerConnectie(connectie);

            for (String bsn : alleBSNs) {
                new QueryRunner().update(connectie, "INSERT INTO " + TABLE + " (" + COLUMN + ") " + "SELECT " + bsn
                    + " WHERE NOT EXISTS" + " (SELECT true FROM " + TABLE + " WHERE " + COLUMN + "=?)", bsn);
            }

            DbUtils.commitAndClose(connectie);
        } finally {
            if (connectie != null && !connectie.isClosed()) {
                LOGGER.error("Connectie niet correct gesloten; poging rollback en alsnog correct sluiten voor "
                    + "bericht {}.", berichtId);
                try {
                    connectie.rollback();
                } catch (SQLException e) {
                    LOGGER.error("Rollback op mislukte sluiting van connectie ook gefaald.", e);
                } finally {
                    connectie.close();
                }
            }
        }
    }

    /**
     * Voegt de twee opgegeven collecties samen tot een nieuwe collectie en retourneert deze.
     *
     * @param readBsnLocks BSNs die gelezen moeten worden.
     * @param writeBsnLocks BSNs die geschreven moeten worden.
     * @return een collectie van de BSNs te samen.
     */
    @SuppressWarnings("unchecked")
    private Collection<String> voegBsnCollectiesSamen(final Collection<String> readBsnLocks,
            final Collection<String> writeBsnLocks)
    {
        // CHECKSTYLE:OFF
        return CollectionUtils.union(readBsnLocks != null ? readBsnLocks : Collections.EMPTY_LIST,
                writeBsnLocks != null ? writeBsnLocks : Collections.EMPTY_LIST);
        // CHECKSTYLE:ON
    }

    /**
     * Creeert een nieuwe (geconditioneerde) connectie, voegt deze toe aan de ThreadLocal voor later gebruik, en
     * plaatst locks op de opgegeven BSNs. Indien er een SQL gerelateerde fout optreedt wordt de connectie gerollbackt
     * en gesloten en de exceptie wordt opnieuw gegooid. <br/>
     * <br/>
     * <b>Let op:</b> Deze methode creeert dus een connectie, maar sluit deze (als er geen fouten optreden) niet! De
     * connectie wordt aan de ThreadLocal toegevoegd, zodat deze connectie verder nog in de thread gebruikt kan
     * worden. Het systeem dient er voor te zorgen dat als deze methode wordt aangeroepen, ook altijd nog de
     * {@link #unLock()} methode wordt aangeroepen.
     *
     * @param berichtId het id van het bericht dat verwerkt wordt.
     * @param readBsnLocks BSNs die gelezen moeten worden.
     * @param writeBsnLocks BSNs die geschreven moeten worden.
     * @throws SQLException indien er een fout is opgetreden bij het conditioneren van de connectie of bij het plaatsen
     *             van de lock(s).
     */
    private void creeerConnectieVoorTransactieEnPlaatsLock(final Long berichtId, final Collection<String> readBsnLocks,
            final Collection<String> writeBsnLocks) throws SQLException
    {
        Connection connectie = dataSource.getConnection();

        try {
            conditioneerConnectie(connectie);

            TRANSACTIONSTATUSTHREADLOCAL.set(connectie);

            if (readBsnLocks != null && readBsnLocks.size() > 0) {
                new QueryRunner().query(TRANSACTIONSTATUSTHREADLOCAL.get(), "SELECT " + COLUMN + " FROM " + TABLE
                    + " WHERE " + COLUMN + " IN (" + implode(readBsnLocks) + ") FOR SHARE", new ScalarHandler());
            }

            if (writeBsnLocks != null && writeBsnLocks.size() > 0) {
                new QueryRunner().query(TRANSACTIONSTATUSTHREADLOCAL.get(), "SELECT " + COLUMN + " FROM " + TABLE
                    + " WHERE " + COLUMN + " IN (" + implode(writeBsnLocks) + ") FOR UPDATE", new ScalarHandler());
            }
        } catch (SQLException e) {
            LOGGER.error(String.format("Fout opgetreden bij het plaatsen van de locks voor bericht %d; connectie "
                + "wordt gerollbackt en gesloten, exceptie wordt verder gegooid.", berichtId), e);
            DbUtils.rollbackAndCloseQuietly(connectie);
            throw e;
        }
    }

    /**
     * Conditioneert een opgegeven connectie. Standaard zal de <code>autoCommit</code> op de connectie worden
     * uitgezet, zal het isolatie level op {@link Connection.TRANSACTION_SERIALIZABLE} worden gezet en zal de timeout
     * worden gezet.
     *
     * @param connectie de connectie die moet worden geconditioneerd.
     * @throws SQLException indien er een fout is opgetreden tijdens het conditioneren van de connectie.
     */
    private void conditioneerConnectie(final Connection connectie) throws SQLException {
        connectie.setAutoCommit(false);
        connectie.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        String timeOutStatement =
            "SET statement_timeout TO " + (brpConfiguratie.getDatabaseTimeOutProperty() * MSEC_IN_SECONDS);

        Statement statement = connectie.createStatement();
        try {
            statement.execute(timeOutStatement);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unLock() {
        try {
            if (TRANSACTIONSTATUSTHREADLOCAL.get() != null) {
                TRANSACTIONSTATUSTHREADLOCAL.get().commit();
            }
        } catch (SQLException e) {
            LOGGER.error("bussines thread commit", e);
        } finally {
            try {
                if (TRANSACTIONSTATUSTHREADLOCAL.get() != null && !TRANSACTIONSTATUSTHREADLOCAL.get().isClosed()) {
                    TRANSACTIONSTATUSTHREADLOCAL.get().close();
                }
            } catch (SQLException e) {
                LOGGER.error("bussines thread close", e);
            } finally {
                TRANSACTIONSTATUSTHREADLOCAL.remove();
            }
        }
    }
}
