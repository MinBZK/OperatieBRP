/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.inject.Inject;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Profile("batch")
@Component
final class AfnemerindicatieBatchStrategy implements AfnemerindicatieDatabaseInteractieStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final int INDEX_ID = 1;
    private static final int INDEX_PERS = 2;
    private static final int INDEX_AFNEMER = 3;
    private static final int INDEX_LEVSAUTORISATIE = 4;
    private static final int INDEX_DATAANVANGMATERIELEPERIODE = 5;
    private static final int INDEX_DATEINDEVOLGEN = 6;
    private static final int INDEX_INDAG = 7;

    private static final int INDEX_HIS_ID = 1;
    private static final int INDEX_HIS_PERSAFNEMERINDICATIE = 2;
    private static final int INDEX_HIS_TSREG = 3;
    private static final int INDEX_HIS_TSVERVAL = 4;
    private static final int INDEX_HIS_DIENSTINHOUD = 5;
    private static final int INDEX_HIS_DIENSTVERVAL = 6;
    private static final int INDEX_HIS_DATAANVANGMATERIELEPERIODE = 7;
    private static final int INDEX_HIS_DATEINDEVOLGEN = 8;

    @Inject
    private DataSource masterDataSource;

    private AfnemerindicatieBatchStrategy() {
    }

    @Override
    public void dumpAfnemerindicatieTabelNaarFile(final File outputFile) {
        LOGGER.info("Genereer afnemerindicatie rijen naar {}", outputFile.getAbsolutePath());
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            final SqlRowSet sqlRowSet = new JdbcTemplate(masterDataSource).queryForRowSet("select * from autaut.persafnemerindicatie where levsautorisatie "
                    + "IN (select id from autaut.levsautorisatie where dateinde is null)");
            while (sqlRowSet.next()) {
                IOUtils.write(String.format("%s,%s,%s,%s,%s,%s,%s%n",
                        sqlRowSet.getString(INDEX_ID),
                        sqlRowSet.getString(INDEX_PERS),
                        sqlRowSet.getString(INDEX_AFNEMER),
                        sqlRowSet.getString(INDEX_LEVSAUTORISATIE),
                        StringUtils.defaultIfEmpty(sqlRowSet.getString(INDEX_DATAANVANGMATERIELEPERIODE), AfnemerindicatieConversie.NULL_VALUE),
                        StringUtils.defaultIfEmpty(sqlRowSet.getString(INDEX_DATEINDEVOLGEN), AfnemerindicatieConversie.NULL_VALUE),
                        sqlRowSet.getString(INDEX_INDAG)
                ), fos, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void vulAfnemerindicatieTabel(final File inputFile) {
        LOGGER.info("Vul afnemerindicatie tabel");
        try (FileInputStream fileInputStream = new FileInputStream(inputFile)) {

            final List<String> lines = IOUtils.readLines(fileInputStream, StandardCharsets.UTF_8);
            for (String line : lines) {
                final String[] splitVals = StringUtils.split(line, ",");
                new JdbcTemplate(masterDataSource).update("insert into autaut.persafnemerindicatie (id, pers, partij, "
                                + "levsautorisatie, dataanvmaterieleperiode, dateindevolgen, indag) values (?, ?, ?, ?, ?, ?, ?)",
                        preparedStatement -> vulPreparedStatementVoorAfnemerindicatie(splitVals, preparedStatement)
                );
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void dumpHisAfnemerindicatieTabel(final File outputFile) {
        LOGGER.info("Genereer hisafnemerindicatie rijen naar {}", outputFile.getAbsolutePath());
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            final SqlRowSet sqlRowSet = new JdbcTemplate(masterDataSource).queryForRowSet("select hpa.* from autaut.his_persafnemerindicatie hpa\n"
                    + "inner join (select pa.id as afnemerindid \n"
                    + "from autaut.persafnemerindicatie pa, autaut.levsautorisatie la where pa.levsautorisatie = la.id and la.dateinde is null) \n"
                    + "as x on hpa.persafnemerindicatie = x.afnemerindid");
            while (sqlRowSet.next()) {
                IOUtils.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s%n",
                        sqlRowSet.getString(INDEX_HIS_ID),
                        sqlRowSet.getString(INDEX_HIS_PERSAFNEMERINDICATIE),
                        sqlRowSet.getString(INDEX_HIS_TSREG),
                        StringUtils.defaultIfBlank(sqlRowSet.getString(INDEX_HIS_TSVERVAL), AfnemerindicatieConversie.NULL_VALUE),
                        StringUtils.defaultIfBlank(sqlRowSet.getString(INDEX_HIS_DIENSTINHOUD), AfnemerindicatieConversie.NULL_VALUE),
                        StringUtils.defaultIfBlank(sqlRowSet.getString(INDEX_HIS_DIENSTVERVAL), AfnemerindicatieConversie.NULL_VALUE),
                        StringUtils.defaultIfBlank(sqlRowSet.getString(INDEX_HIS_DATAANVANGMATERIELEPERIODE), AfnemerindicatieConversie.NULL_VALUE),
                        StringUtils.defaultIfBlank(sqlRowSet.getString(INDEX_HIS_DATEINDEVOLGEN), AfnemerindicatieConversie.NULL_VALUE)
                ), fos, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void vulHisAfnemerindicatieTabel(final File inputFile) {
        LOGGER.info("Vul his_afnemerindicatie tabel");
        try (FileInputStream fileInputStream = new FileInputStream(inputFile)) {
            final List<String> lines = IOUtils.readLines(fileInputStream, StandardCharsets.UTF_8);
            for (String line : lines) {
                final String[] splitVals = StringUtils.split(line, ",");
                new JdbcTemplate(masterDataSource).update("insert into autaut.his_persafnemerindicatie (id, persafnemerindicatie, tsreg, tsverval, "
                                + "dienstinh, dienstverval, dataanvmaterieleperiode, dateindevolgen) values (?, ?, ?, ?, ?, ?, ?, ?)",
                        preparedStatement -> vulPreparedStatementVoorHisAfnemerindicatie(preparedStatement, splitVals)
                );
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    private void vulPreparedStatementVoorAfnemerindicatie(final String[] splitVals, final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(INDEX_ID, Long.parseLong(splitVals[INDEX_ID - 1]));
        preparedStatement.setInt(INDEX_PERS, Integer.parseInt(splitVals[INDEX_PERS - 1]));
        preparedStatement.setInt(INDEX_AFNEMER, Integer.parseInt(splitVals[INDEX_AFNEMER - 1]));
        preparedStatement.setInt(INDEX_LEVSAUTORISATIE, Integer.parseInt(splitVals[INDEX_LEVSAUTORISATIE - 1]));
        if (!AfnemerindicatieConversie.NULL_VALUE.equals(splitVals[INDEX_DATAANVANGMATERIELEPERIODE - 1])) {
            preparedStatement.setInt(INDEX_DATAANVANGMATERIELEPERIODE, Integer.parseInt(splitVals[INDEX_DATAANVANGMATERIELEPERIODE - 1]));
        } else {
            preparedStatement.setNull(INDEX_DATAANVANGMATERIELEPERIODE, Types.INTEGER);
        }
        if (!AfnemerindicatieConversie.NULL_VALUE.equals(splitVals[INDEX_DATEINDEVOLGEN - 1])) {
            preparedStatement.setInt(INDEX_DATEINDEVOLGEN, Integer.parseInt(splitVals[INDEX_DATEINDEVOLGEN - 1]));
        } else {
            preparedStatement.setNull(INDEX_DATEINDEVOLGEN, Types.INTEGER);
        }
        preparedStatement.setBoolean(INDEX_INDAG, Boolean.parseBoolean(splitVals[INDEX_INDAG - 1]));
    }

    private void vulPreparedStatementVoorHisAfnemerindicatie(final PreparedStatement preparedStatement, final String[] splitVals) throws SQLException {
        //id
        preparedStatement.setLong(INDEX_HIS_ID, Long.parseLong(splitVals[INDEX_HIS_ID - 1]));
        //persafnemerindicatie
        preparedStatement.setLong(INDEX_HIS_PERSAFNEMERINDICATIE, Long.parseLong(splitVals[INDEX_HIS_PERSAFNEMERINDICATIE - 1]));
        //tsreg
        final String tsRegString = splitVals[INDEX_HIS_TSREG - 1];
        preparedStatement.setTimestamp(INDEX_HIS_TSREG, getTimestamp(tsRegString));

        //tsverval
        final String tsVervalString = splitVals[INDEX_HIS_TSVERVAL - 1];
        if (AfnemerindicatieConversie.NULL_VALUE.equals(tsVervalString)) {
            preparedStatement.setNull(INDEX_HIS_TSVERVAL, Types.TIMESTAMP_WITH_TIMEZONE);
        } else {
            preparedStatement.setTimestamp(INDEX_HIS_TSVERVAL, getTimestamp(tsVervalString));
        }
        //dienstinh
        final String dienstinhoudString = splitVals[INDEX_HIS_DIENSTINHOUD - 1];
        if (AfnemerindicatieConversie.NULL_VALUE.equals(dienstinhoudString)) {
            preparedStatement.setNull(INDEX_HIS_DIENSTINHOUD, Types.INTEGER);
        } else {
            preparedStatement.setInt(INDEX_HIS_DIENSTINHOUD, Integer.parseInt(dienstinhoudString));
        }
        //dienstverval
        final String dienstvervalString = splitVals[INDEX_HIS_DIENSTVERVAL - 1];
        if (AfnemerindicatieConversie.NULL_VALUE.equals(dienstvervalString)) {
            preparedStatement.setNull(INDEX_HIS_DIENSTVERVAL, Types.INTEGER);
        } else {
            preparedStatement.setInt(INDEX_HIS_DIENSTVERVAL, Integer.parseInt(dienstvervalString));
        }
        //dataanvmaterieleperiode
        final String datumAanvangMaterielePeriodeString = splitVals[INDEX_HIS_DATAANVANGMATERIELEPERIODE - 1];
        if (AfnemerindicatieConversie.NULL_VALUE.equals(datumAanvangMaterielePeriodeString)) {
            preparedStatement.setNull(INDEX_HIS_DATAANVANGMATERIELEPERIODE, Types.INTEGER);
        } else {
            preparedStatement.setInt(INDEX_HIS_DATAANVANGMATERIELEPERIODE, Integer.parseInt(datumAanvangMaterielePeriodeString));
        }
        //dateindevolgen
        final String datumEindeVolgenString = splitVals[INDEX_HIS_DATEINDEVOLGEN - 1];
        if (AfnemerindicatieConversie.NULL_VALUE.equals(datumEindeVolgenString)) {
            preparedStatement.setNull(INDEX_HIS_DATEINDEVOLGEN, Types.INTEGER);
        } else {
            preparedStatement.setInt(INDEX_HIS_DATEINDEVOLGEN, Integer.parseInt(datumEindeVolgenString));
        }
    }

    private Timestamp getTimestamp(final String datetimeString) {
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        final ZonedDateTime dateTime = ZonedDateTime.parse(datetimeString.replace(" ", "T").replace(".0", "+00:00"), formatter);
        final ZonedDateTime zonedDateTime = dateTime.withZoneSameLocal(DatumUtil.NL_ZONE_ID);
        return new Timestamp(zonedDateTime.toInstant().toEpochMilli());
    }
}
