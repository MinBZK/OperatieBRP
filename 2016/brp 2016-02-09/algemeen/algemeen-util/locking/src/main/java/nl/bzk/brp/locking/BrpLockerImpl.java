/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.locking;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;


/**
 * Cre&euml;ert shared of exclusive locks op basis van database id's. Deze implementatie is specifiek voor de Postgresql
 * database.
 */
@Service
public final class BrpLockerImpl implements BrpLocker {

    private static final Logger                  LOGGER                       = LoggerFactory.getLogger();
    private static final ThreadLocal<Connection> TRANSACTIONSTATUSTHREADLOCAL = new ThreadLocal<>();
    private static final int                     MSEC_IN_SECONDS              = 1000;
    private static final String                  SQLSTATE                     = "SQLState: ";
    private static final String                  ERROR_CODE                   = "ErrorCode: ";

    @Inject
    @Named("lockingDataSource")
    private DataSource                           dataSource;

    private final QueryRunner                    queryRunner                  = new QueryRunner();

    /**
     * Cre&euml;ert locks voor BRP elementen. Voor het cre&euml;eren van locks wordt gebruik gemaakt van Postgress
     * transaction level locking, waarvoor een
     * nieuwe database transactie wordt aangemaakt. Het meeliften op de mogelijk bestaande business transaction is
     * binnen deze implementatie niet mogelijk,
     * aangezien de status van de lock niet kan worden afgeleid a.d.h.v die tranactie.<br/>
     * Het cre&euml;eren van meerdere locks wordt ondersteund. Het is
     * mogelijk om bijv. eerst persoon 1 te locken en vervolgens persoon 1 & 2. De unlock methode zal alle gemaakte
     * locks weer vrijgeven.
     *
     * @param dbIds De database id's van objecten die een lock moeten krijgen
     * @param lockingElement Het element waarvoor locking gewenst is
     * @param lockingMode Het soort locking dat gewenst is
     * @param timeoutInSeconden Geeft aan hoeveel sec. er max. gewacht moet worden om het lock te verkrijgen
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false)
     * @throws BrpLockerExceptie Wanneer er binnen de gestelde timeout periode geen lock verkregen kon worden
     */
    @Override
    public boolean lock(final Collection<Integer> dbIds, final LockingElement lockingElement,
            final LockingMode lockingMode, final Integer timeoutInSeconden) throws BrpLockerExceptie
    {
        if (dbIds == null || dbIds.isEmpty()) {
            LOGGER.warn("Geen locks opgegeven.");
            return false;
        }
        try {
            if (TRANSACTIONSTATUSTHREADLOCAL.get() == null) {
                creeerConnectieVoorTransactie(timeoutInSeconden);
            }
            plaatsLocks(dbIds, lockingElement, lockingMode, timeoutInSeconden);
        } catch (final BrpLockerExceptie e) {
            // timeout periode is waarschijnlijk voorbij, waardoor er een exceptie is gegooid.
            // eerder gemaakte locks worden vrijgegeven, om te voorkomen dat een deel van de elementen gelocks is/blijft
            unLock();

            throw e;
        } catch (final SQLException e) {
            final String message =
                MessageFormatter.arrayFormat(
                        "Fout opgetreden bij het plaatsen van locks voor dbIds {}, lockingElement {} en "
                            + "lockingMode {}; connectie wordt gerollbackt en gesloten, exceptie wordt "
                            + "(gewrapped) verder gegooid.", new Object[] { dbIds, lockingElement, lockingMode })
                        .getMessage();
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(message, e);
                LOGGER.error(SQLSTATE + e.getSQLState());
                LOGGER.error(ERROR_CODE + e.getErrorCode());
            }
            DbUtils.rollbackAndCloseQuietly(TRANSACTIONSTATUSTHREADLOCAL.get());

            throw new BrpLockerExceptie(message, e);
        }

        return true;
    }

    /**
     * Verifieer dat de huidige connectie aanwezig is en niet gesloten is.
     *
     * @return Als de connectie niet null is en niet gesloten is, wordt true teruggegeven.
     * @throws SQLException als er een sql fout optreedt
     */
    private boolean isConnectieOpen() throws SQLException {
        return TRANSACTIONSTATUSTHREADLOCAL.get() != null && !TRANSACTIONSTATUSTHREADLOCAL.get().isClosed();
    }

    /**
     * Verifieer dat de connectie status valid is door het uitvoeren van een (controle) query.
     *
     * @return Wanneer de connectie valid is true
     * @throws SQLException als er een sql fout optreedt
     */
    private boolean isValidConnectieStatus() throws SQLException {
        return isConnectieOpen()
            && queryRunner.query(TRANSACTIONSTATUSTHREADLOCAL.get(), "SELECT 1", new ScalarHandler()) != null;
    }

    @Override
    public boolean isLockNogAanwezig() {
        boolean lockAanwezig;

        try {
            // doordat we gebruik maken van transaction level locking, kunnen we op basis van de status van de
            // connectie bepalen of het lock nog aanwezig is
            lockAanwezig = isValidConnectieStatus();
        } catch (final SQLException e) {
            lockAanwezig = false;
            LOGGER.error("Bepalen van de lock status is mislukt.", e);
        }

        return lockAanwezig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unLock() {
        final Connection currentConnection = TRANSACTIONSTATUSTHREADLOCAL.get();
        try {
            if (isConnectieOpen()) {
                currentConnection.commit();
            }
        } catch (final SQLException e) {
            LOGGER.error("Ontgrendelen van database elementen door commit mislukt.", e);
        } finally {
            try {
                if (isConnectieOpen()) {
                    currentConnection.close();
                }
            } catch (final SQLException e) {
                LOGGER.error("Sluiten van de connectie bij het ontgrendelen van database elementen mislukt.", e);
            } finally {
                TRANSACTIONSTATUSTHREADLOCAL.remove();
            }
        }
    }

    /**
     * Creeert een nieuwe (geconditioneerde) connectie en voegt deze toe aan de ThreadLocal voor later gebruik. Indien
     * er een SQL gerelateerde fout
     * optreedt wordt de connectie gerollbackt en gesloten en de exceptie wordt opnieuw gegooid. <br/>
     * <br/>
     * <b>Let op:</b> Deze methode creeert dus een
     * connectie, maar sluit deze (als er geen fouten optreden) niet! De connectie wordt aan de ThreadLocal toegevoegd,
     * zodat deze connectie verder nog in
     * de thread gebruikt kan worden. Het systeem dient er voor te zorgen dat als deze methode wordt aangeroepen, ook
     * altijd nog de {@link #unLock()} methode wordtaangeroepen.
     *
     * @param timeout Geeft aan hoeveel sec. er max. gewacht moet worden om het lock te verkrijgen
     * @throws SQLException indien er een fout is opgetreden bij het conditioneren van de connectie of bij het plaatsen
     *             van de lock(s).
     */
    private void creeerConnectieVoorTransactie(final Integer timeout) throws SQLException {

        @SuppressWarnings("PMD.CloseResource")
        Connection connectie = DataSourceUtils.getConnection(dataSource);
        if (connectie.isClosed()) {
            connectie = dataSource.getConnection();
        }
        try {
            conditioneerConnectie(connectie, timeout);
            TRANSACTIONSTATUSTHREADLOCAL.set(connectie);
        } catch (final SQLException e) {
            final String message = "Fout opgetreden bij het creeren van een connectie.";
            LOGGER.error(message, e);
            LOGGER.error(SQLSTATE + e.getSQLState());
            LOGGER.error(ERROR_CODE + e.getErrorCode());
            DbUtils.rollbackAndCloseQuietly(connectie);
            throw e;
        }
    }

    /**
     * Plaatst locks op de opgegeven database id's.<br/>
     * <br/>
     * <b>Let op:</b> Voor het plaatsen van een lock wordt gebruik gemaakt van de connectie die
     * gecreeerd wordt door de {@link #creeerConnectieVoorTransactie(Integer)} methode.
     *
     * @param dbIds De database id's van objecten die een lock moeten krijgen
     * @param lockingElement Het element waarvoor locking gewenst is
     * @param lockingMode Het soort locking dat gewenst is
     * @param timeoutInSeconden Geeft aan hoe lang er gewacht moet worden om het lock te verkrijgen. De max.
     *            (theoretische) timeoutInSeconden duur kan 2*
     *            de gestelde tijd zijn. Namelijk de huidige wachttijd + de statement timeoutInSeconden.
     * @throws SQLException Indien er een fout is opgetreden bij het conditioneren van deconnectie of bij het plaatsen
     *             van de lock(s).
     * @throws BrpLockerExceptie Indien het plaatsen van de lock(s) niet binnen de timeoutInSeconden periode uitgevoerd
     *             kon worden.
     */
    private void plaatsLocks(final Collection<Integer> dbIds, final LockingElement lockingElement,
            final LockingMode lockingMode, final Integer timeoutInSeconden) throws SQLException, BrpLockerExceptie
    {
        if (TRANSACTIONSTATUSTHREADLOCAL.get() != null) {
            final long startTimeMillis = System.currentTimeMillis();
            switch (lockingMode) {
                case SHARED:
                    for (final Integer id : dbIds) {
                        if (isBinnenTimeoutPeriode(startTimeMillis, timeoutInSeconden)) {
                            queryRunner.query(TRANSACTIONSTATUSTHREADLOCAL.get(),
                                    "SELECT pg_advisory_xact_lock_shared(?,?)", new ScalarHandler(),
                                    lockingElement.ordinal(), id);
                        } else {
                            throw new BrpLockerExceptie("Timeout periode voor het plaatsen van de locks is verstreken");
                        }
                    }
                    break;
                case EXCLUSIVE:
                    for (final Integer id : dbIds) {
                        if (isBinnenTimeoutPeriode(startTimeMillis, timeoutInSeconden)) {
                            queryRunner.query(TRANSACTIONSTATUSTHREADLOCAL.get(), "SELECT pg_advisory_xact_lock(?,?)",
                                    new ScalarHandler(), lockingElement.ordinal(), id);
                        } else {
                            throw new BrpLockerExceptie("Timeout periode is verstreken");
                        }
                    }
                    break;
                default:
                    LOGGER.error("Unsupported SoortLocking " + lockingMode);
            }
        } else {
            throw new BrpLockerExceptie("Geen connectie beschikbaar voor het plaatsen van een lock.");
        }
    }

    /**
     * Bepalen of we nog binnen de timeout periode zitten.
     *
     * @param startTimeMillis De startTime vanaf wanneer gerekend moet worden, in mills
     * @param timeout Geeft aan hoe lang er max. gewacht moet worden om het lock te verkrijgen, in sec.
     * @return een boolean die aangeeft of we nog binnen de timeout periode zitten (true) of niet (false)
     */
    private boolean isBinnenTimeoutPeriode(final long startTimeMillis, final Integer timeout) {
        if (timeout == null) {
            return true;
        }

        final long currentTimeMillis = System.currentTimeMillis();
        return (currentTimeMillis - startTimeMillis) < (timeout * MSEC_IN_SECONDS);
    }

    /**
     * Conditioneert een opgegeven connectie. Standaard zal de <code>autoCommit</code> op de connectie worden uitgezet,
     * zal het isolatie level op {@link Connection#TRANSACTION_SERIALIZABLE} worden gezet & zal de timeout worden gezet.
     *
     * @param connectie De connectie die moet worden geconditioneerd.
     * @param timeout Geeft aan hoeveel sec. er max. gewacht moet worden om het lock te verkrijgen
     * @throws SQLException Indien er een fout is opgetreden tijdens het conditioneren van de connectie.
     */
    private void conditioneerConnectie(final Connection connectie, final Integer timeout) throws SQLException {
        connectie.setAutoCommit(false);
        connectie.rollback();
        connectie.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        if (timeout == null) {
            return;
        }

        final String timeOutStatement = "SET statement_timeout TO " + (timeout * MSEC_IN_SECONDS);
        final Statement statement = connectie.createStatement();
        try {
            statement.execute(timeOutStatement);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }
}
