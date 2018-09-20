/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.vergrendeling;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;


/**
 * Service die een sleutel vergrendelt op basis van zijn sleutel, zodat andere processen de persoon
 * niet kunnen wijzigen.
 */
@Service
public class SleutelVergrendelaarImpl implements SleutelVergrendelaar {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(SleutelVergrendelaarImpl.class);
    private static final ThreadLocal<Connection> CURRENTCONNECTION = new ThreadLocal<Connection>();
    private static final int MSEC_IN_SECONDS = 1000;

    /**
     * Vergrendel met een gedeelde lock, waardoor er geen exclusieve lock kan worden gezet om te schrijven.
     */
    private static final String VERGRENDEL_VOOR_LEZEN_QUERY = "SELECT pg_advisory_xact_lock_shared(?)";
    /**
     * Vergendel exclusief, ook een lock om te delen (=lezen) wordt niet toegestaan.
     */
    private static final String VERGRENDEL_VOOR_SCHRIJVEN_QUERY = "SELECT pg_advisory_xact_lock(?)";

    @Value(value = "${brp.database.timeout.seconden:10}")
    private Integer statementTimeOut;

    @Inject
    @Named("alpDataSource")
    private DataSource dataSource;


    private final QueryRunner queryRunner = new QueryRunner();

    /**
     * Ontgrendel geeft de connectie weer vrij.
     */
    @Override
    public final void ontgrendel() {

        final Connection currentConnection = CURRENTCONNECTION.get();
        try {
            if (isValidConnectionStatus()) {
                currentConnection.commit();
            }
        } catch (SQLException e) {
            LOGGER.error("Ontgrendelen van sleutels door commit mislukt, was er wel een transactie?", e);
        } finally {
            try {
                if (isValidConnectionStatus()) {
                    currentConnection.close();
                }
            } catch (SQLException e) {
                LOGGER.error("Sluiten van de connectie bij het ontgrendelen van de sleutels mislukt.", e);
            } finally {
                CURRENTCONNECTION.remove();
            }
        }
    }

    /**
     * Vergrendel de sleutels met het referentienummer in de gevraagde mode.
     *
     * @param sleutels De lijst van sleutels die vergrendelt moeten worden
     * @param referentieNummer Referentienummer van bijvoorbeeld het bijbehorende bericht.
     * @param mode De mode van vergrendeling (voor lezen of voor schrijven)
     * @throws VergrendelFout
     */
    @Override
    public final void vergrendel(final Collection<Integer> sleutels, final String referentieNummer, final VergrendelMode mode) throws VergrendelFout
    {
        /* Hier wordt er bewust voor gekozen om een connectie te openen en deze in een thread local te stoppen.
         * Vervolgens wordt deze connectie pas gesloten als de transactie wordt gesloten (met een commit of rollback).
         */
        @SuppressWarnings("PMD.CloseResource")
        final Connection connectie = DataSourceUtils.getConnection(dataSource);
        try {
            conditioneerConnectie(connectie);
            CURRENTCONNECTION.set(connectie);
            for (Integer sleutel : sleutels) {
                vergrendelSleutel(sleutel, mode);
            }

        } catch (SQLException e) {
            if (LOGGER.isErrorEnabled()) {
                final String message =
                        MessageFormatter.arrayFormat(
                                "Fout opgetreden bij het plaatsen van de '{}' vergrendeling op de sleutels {} "
                                        + "connectie wordt gerollbackt en gesloten, exceptie wordt (gewrapped) verder "
                                        + "gegooid.",
                                new Object[]{mode.name(), sleutels, referentieNummer}).getMessage();
                LOGGER.error(message, e);
                LOGGER.error("SQLState: " + e.getSQLState());
                LOGGER.error("ErrorCode: " + e.getErrorCode());
            }
            try {
                connectie.rollback();
                connectie.close();
            } catch (SQLException sqlExcecption) {
                LOGGER.error("Fout bij het sluiten of terugdraaien van de transactie op de connectie", sqlExcecption);
            }
            throw new VergrendelFout(e);
        }

    }

    /**
     * Vergrendel de sleutel.
     *
     * @param sleutel de sleutel waarop vergrendelt moet worden
     * @param mode    de mode van vergrendeling
     * @throws SQLException indien er een fout is opgetreden tijdens het conditioneren van de connectie.
     */
    private void vergrendelSleutel(final Integer sleutel, final VergrendelMode mode) throws SQLException {
        String query;
        if (mode.equals(VergrendelMode.GEDEELD)) {
            query = VERGRENDEL_VOOR_LEZEN_QUERY;
        } else {
            query = VERGRENDEL_VOOR_SCHRIJVEN_QUERY;
        }

        queryRunner.query(CURRENTCONNECTION.get(), query, new ScalarHandler(), sleutel);
    }

    /**
     * Conditioneert een opgegeven connectie. Standaard zal de <code>autoCommit</code> op de connectie worden
     * uitgezet, zal het isolatie level op {@link Connection#TRANSACTION_SERIALIZABLE} worden gezet en zal de timeout
     * worden gezet.
     *
     * @param connectie de connectie die moet worden geconditioneerd.
     * @throws SQLException indien er een fout is opgetreden tijdens het conditioneren van de connectie.
     */
    private void conditioneerConnectie(final Connection connectie) throws SQLException {
        connectie.setAutoCommit(false);
        connectie.rollback();
        connectie.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        final String timeOutStatement =
                "SET statement_timeout TO " + (statementTimeOut * MSEC_IN_SECONDS);
        final Statement statement = connectie.createStatement();
        try {
            statement.execute(timeOutStatement);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }


    /**
     * Verifieer dat de huidige connectie aanwezig is en niet gesloten is.
     *
     * @return Als de connectie niet null is en niet gesloten is, wordt true teruggegeven.
     * @throws SQLException de exceptie die kan optreden bij het bepalen of de connectie transactioneel is.
     */
    private boolean isValidConnectionStatus() throws SQLException {
        final Connection currentConnection = CURRENTCONNECTION.get();
        return currentConnection != null && !currentConnection.isClosed()
                && DataSourceUtils.isConnectionTransactional(currentConnection, dataSource);
    }

}
