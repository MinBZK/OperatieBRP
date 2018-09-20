/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.art.util.sql;

import org.apache.commons.collections.map.HashedMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 *
 * Extract de informatie van een persoon of een set van personen uit de database incl. zijn relaties.
 * Creert delete statements om deze set op te ruimen (incl. relatie en betrokkenheden).
 * Creert ínsert statements om deze set weer terug te zetten (incl. zijn relaties).
 * Let op dat de personen die betrokken zijn in de relaties (en geen onderdeel maakt van de set) niet aangemaakt
 * verwijderd zijn. De sql gaat vanuit dat deze personen nog steeds bestaan.
 *
 */
public class JdbcExtract {

    private static final SimpleDateFormat datumFormatteerder = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S Z");

    private Connection connection;
    private String userName = "brp";
    private String password = "brp";
    private String url      = "jdbc:postgresql://bdev-db.modernodam.nl/brpdev";

    private final Map<String, List<String>> tableMap = new HashedMap();

    private final List<String> tabelPersHis = Arrays.asList(
        "His_PersAfgeleidAdministrati",
        "His_PersBijhouding",
        "His_PersDeelnEUVerkiezingen",
        "His_PersGeboorte",
        "His_PersGeslachtsaand",
        "His_PersIDs",
        "His_PersInschr",
        "His_PersMigratie",
        "His_PersNaamgebruik",
        "His_PersNrverwijzing",
        "His_PersOverlijden",
        "His_PersPK",
        "His_PersSamengesteldeNaam",
        "His_PersUitslKiesr",
        "His_PersVerblijfsR"
    );

    private final List<String> tabelRelatieHis = Arrays.asList(
        "His_Relatie"
    );

    private final List<String> tabelBetrHis = Arrays.asList(
        "His_OuderOuderlijkGezag",
        "His_OuderOuderschap"
    );

    private final List<String> andereTabellenBehalvePersHis = Arrays.asList(
        "Pers",
        "PersVoornaam",
        "His_PersVoornaam",
        "PersGeslnaamcomp",
        "His_PersGeslnaamcomp",
        "PersAdres",
        "His_PersAdres",
        "PersIndicatie",
        "His_PersIndicatie",
        "PersNation",
        "His_PersNation",
        "PersReisdoc",
        "His_PersReisdoc",
        "PersVerstrbeperking",
        "His_PersVerstrbeperking",
        "PersOnderzoek",
        "His_PersOnderzoek",
        "PersVerificatie",
        "His_PersVerificatie",
        "Relatie",
        "Betr",
        "His_Betr"
    );

    private StringBuffer sb = new StringBuffer();


    /**
     * .
     * @param url .
     * @param userName .
     * @param password .
     * @throws SQLException .
     * @throws ClassNotFoundException .
     */
    private void init(final String url, final String userName, final String password)
        throws SQLException, ClassNotFoundException
    {
        final Properties connectionProps = new Properties();
        this.userName = userName;
        this.password = password;
        this.url = url;
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(this.url, connectionProps);
    }

    /**
     * .
     * @param url .
     * @param user .
     * @param password .
     * @throws SQLException .
     * @throws ClassNotFoundException .
     */
    public JdbcExtract(final String url, final String user, final String password)
        throws SQLException, ClassNotFoundException
    {
        init(url, user, password);
        haalopKolomnamen(andereTabellenBehalvePersHis);
        haalopKolomnamen(tabelPersHis);
        haalopKolomnamen(tabelRelatieHis);
        haalopKolomnamen(tabelBetrHis);
    }

    /**
     * .
     * @param tabelNamen .
     * @throws SQLException .
     */
    private void haalopKolomnamen(final List<String> tabelNamen) throws SQLException {
        for (final String tabelNaam : tabelNamen) {
            final String sql = "SELECT * from kern." + tabelNaam + " LIMIT 1";
            final List<String> kolomNamen = getMetadataInfo(sql);
            tableMap.put(tabelNaam, kolomNamen);
        }
    }

    /**
     * .
     * @param sql .
     * @return .
     * @throws SQLException .
     */
    private List<String> getMetadataInfo(final String sql) throws SQLException {
        final List<String> kolomNamen = new ArrayList<>();
        final Statement st = getConnection().createStatement();
        final ResultSet rs = st.executeQuery(sql);
        final ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            kolomNamen.add(metaData.getColumnName(i + 1));
        }
        st.close();
        return kolomNamen;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * .
     * @param line .
     */
    private void writeLine(final String line) {
        sb.append(line).append("\n");
    }

    /**
     * .
     * @param waarden .
     * @param prefix .
     * @return .
     */
    private String buildSelectie(final List<?> waarden, final String prefix) {
        final StringBuilder str = new StringBuilder();
        for (int i = 0; i < waarden.size(); i++) {
            if (i > 0) {
                str.append(",");
            }
            if (prefix != null) {
                str.append(prefix);
            }
            str.append(waarden.get(i));
        }
        return str.toString();
    }

    /**
     * .
     * @param tabelNaam .
     * @param kolommen .
     * @param persIds .
     * @return .
     */
    private String builSelectPersSql(final String tabelNaam, final List<String> kolommen, final List<Integer> persIds) {
        return String.format("SELECT %s FROM kern.%s p WHERE p.id in (%s)",
                buildSelectie(kolommen, "p."), tabelNaam,  buildSelectie(persIds, null)
        );
    }

    /**
     * .
     * @param tabelNaam .
     * @param kolommen .
     * @param persIds .
     * @return .
     */
    private String builSelectPersHisSql(final String tabelNaam, final List<String> kolommen,
        final List<Integer> persIds)
    {
        return String.format("SELECT %s FROM kern.%s h, kern.pers p WHERE h.pers = p.id and p.id in (%s)",
                buildSelectie(kolommen, "h."), tabelNaam,  buildSelectie(persIds, null)
        );
    }

    /**
     * .
     * @param tabelNaam .
     * @param kolommen .
     * @param persIds .
     * @return .
     */
    private String builSelectPersSubjectSql(final String tabelNaam, final List<String> kolommen,
        final List<Integer> persIds)
    {
        return String.format("SELECT %s FROM kern.%s o, kern.pers p WHERE o.pers = p.id and p.id in (%s)",
                buildSelectie(kolommen, "o."), tabelNaam,  buildSelectie(persIds, null)
        );
    }

    /**
     * .
     * @param tabelNaam .
     * @param subjectTabelNaam .
     * @param kolommen .
     * @param persIds .
     * @return .
     */
    private String builSelectPersSubjectHisSql(final String tabelNaam, final String subjectTabelNaam, final List<String> kolommen, final List<Integer> persIds) {
        return String.format(
                "SELECT %s FROM kern.%s oh, kern.%s s, kern.pers p WHERE oh.%s = s.id and s.pers = p.id and p.id in (%s)",
            buildSelectie(kolommen, "oh."), tabelNaam, subjectTabelNaam, subjectTabelNaam, buildSelectie(persIds, null)
        );
    }

    /**
     * Speciaal gemaakt voor persverificatie.
     *
     * @param tabelNaam de tabel naam
     * @param kolommen de kolommen
     * @param persIds de pers ids
     * @return de string
     */
    private String builSelectGeverifieerdeSubjectSql(final String tabelNaam, final List<String> kolommen, final List<Integer> persIds)
    {
        return String.format("SELECT %s FROM kern.%s o, kern.pers p WHERE o.geverifieerde = p.id and p.id in (%s)",
            buildSelectie(kolommen, "o."), tabelNaam,  buildSelectie(persIds, null)
        );
    }

    /**
     * Speciaal gemaakt voor hispersverificatie.
     *
     * @param tabelNaam de tabel naam
     * @param subjectTabelNaam de subject tabel naam
     * @param kolommen de kolommen
     * @param persIds de pers ids
     * @return de string
     */
    private String builSelectGeverifieerdeSubjectHisSql(final String tabelNaam, final String subjectTabelNaam, final List<String> kolommen, final List<Integer> persIds) {
        return String.format(
            "SELECT %s "
                + " FROM kern.%s oh, kern.%s s, kern.pers p WHERE oh.%s = s.id and s.geverifieerde = p.id and p.id in (%s)",
            buildSelectie(kolommen, "oh."), tabelNaam, subjectTabelNaam, subjectTabelNaam, buildSelectie(persIds, null)
        );
    }

    /**
     * .
     * @param relatieTabelNaam .
     * @param betrokkenTabelNaam .
     * @param kolommen .
     * @param persIds .
     * @return .
     */
    private String builSelectRelatieSql(final String relatieTabelNaam, final String betrokkenTabelNaam,
            final List<String> kolommen, final List<Integer> persIds)
    {
        return String.format(
                "SELECT distinct %s "
                + " FROM kern.%s r, kern.%s b, kern.pers p WHERE r.id = b.%s and b.pers = p.id  and p.id in (%s)",
            buildSelectie(kolommen, "r."), relatieTabelNaam, betrokkenTabelNaam,
                relatieTabelNaam, buildSelectie(persIds, null)
        );
    }

    /**
     * .
     * @param tabelNaam .
     * @param relatieTabelNaam .
     * @param betrokkenTabelNaam .
     * @param kolommen .
     * @param persIds .
     * @return .
     */
    private String builSelectRelatieHisSql(final String tabelNaam, final String relatieTabelNaam,
            final String betrokkenTabelNaam, final List<String> kolommen, final List<Integer> persIds)
    {
        return String.format(
            "SELECT distinct %s "
            + " FROM kern.%s rh, kern.%s r, kern.%s b, kern.pers p "
            + " WHERE rh.%s = r.id and r.id = b.%s and b.pers = p.id  and p.id in (%s)",
            buildSelectie(kolommen, "rh."), tabelNaam, relatieTabelNaam, betrokkenTabelNaam,
                relatieTabelNaam, relatieTabelNaam, buildSelectie(persIds, null)
        );
    }

    /**
     * .
     * @param betrokkenTabelNaam .
     * @param kolommen .
     * @param persIds .
     * @param relatieIds .
     * @return .
     */
    private String builSelectBetrokkenheidSql(final String betrokkenTabelNaam,
            final List<String> kolommen, final List<?> persIds, final List<?> relatieIds)
    {
        return String.format("SELECT distinct %s FROM kern.%s b WHERE (b.pers in (%s) or b.relatie in (%s))",
                buildSelectie(kolommen, "b."), betrokkenTabelNaam,
                buildSelectie(persIds, null), buildSelectie(relatieIds, null)
        );
    }

    /**
     * .
     * @param tabelNaam .
     * @param betrokkenTabelNaam .
     * @param kolommen .
     * @param persIds .
     * @param relatieIds .
     * @return .
     */
    private String builSelectBetrokkenheidHisSql(final String tabelNaam, final String betrokkenTabelNaam,
            final List<String> kolommen, final List<?> persIds, final List<?> relatieIds) {
        return String.format(
            "SELECT distinct %s "
                + " FROM kern.%s bh, kern.%s b WHERE bh.%s = b.id and (b.pers in (%s) or b.relatie in (%s))",
            buildSelectie(kolommen, "bh."), tabelNaam, betrokkenTabelNaam, betrokkenTabelNaam,
            buildSelectie(persIds, null), buildSelectie(relatieIds, null)
        );
    }

    /**
     * .
     * @return .
     * @throws SQLException .
     */
    public List<String> getVersionNumber() throws SQLException  {
        final List<String> versionNr = new ArrayList<>();
        try (Statement st = getConnection().createStatement()) {
            final ResultSet rs = st.executeQuery("select releaseversion, exceltimestamp from test.artversion");
            if (rs.next()) {
                versionNr.add(rs.getString("releaseversion"));
                versionNr.add(rs.getString("exceltimestamp"));
            }
        }
        return versionNr;
    }

    /**
     * .
     * @return .
     * @throws SQLException .
     */
    public List<PersoonDto> getAllPersIds() throws SQLException {
        final List<PersoonDto> persIds = new ArrayList<>();
        try (Statement st = getConnection().createStatement()) {
            final ResultSet rs = st.executeQuery("select ID, BSN, ANR from kern.pers");
            while (rs.next()) {
                persIds.add(new PersoonDto(rs.getInt("ID"), rs.getObject("BSN"), rs.getObject("ANR")));
            }
        }
        return persIds;
    }

    /**
     * .
     * @param tabelNaam .
     * @param selectieSQL .
     * @return .
     * @throws SQLException .
     */
    private List<?> dumpData(final String tabelNaam, final String selectieSQL) throws SQLException {
        // System.out.println(selectieSQL);
        final List<Object> ids = new ArrayList<>();
        final List<String> kolommen = tableMap.get(tabelNaam);
        try (Statement st = getConnection().createStatement()) {
            final ResultSet rs = st.executeQuery(selectieSQL);
            while (rs.next()) {
                final StringBuilder
                        insertSql = new StringBuilder("INSERT INTO ").append("kern.").append(tabelNaam).append(" ");
                insertSql.append(" (").append(buildSelectie(kolommen, null)).append(") ");
                insertSql.append(" VALUES (");
                for (int i = 0; i < kolommen.size(); i++) {
                    if (i > 0) {
                        insertSql.append(",");
                    }
                    if (rs.getObject(kolommen.get(i)) != null) {
                        insertSql.append("'")
                                 .append(zetWaardeObjectOmNaarString(rs.getObject(kolommen.get(i))))
                                 .append("'");
                    } else {
                        insertSql.append("null");
                    }
                }
                insertSql.append(");");
                ids.add(rs.getObject("id"));
                writeLine(insertSql.toString());
            }
        }
        return ids;
    }

    /**
     * Zet een waarde opgehaald uit de database om naar een tekstuele representatie zoals deze in een SQL insert
     * statement gebruikt kan/dient te worden.
     *
     * @param waardeObject het object dat de waarde uit de database representeert.
     * @return een string/tekstuele representatie van het opgegeven waarde object uit de database.
     */
    private String zetWaardeObjectOmNaarString(final Object waardeObject) {
        final String waardeString;
        if (waardeObject instanceof Date) {
            waardeString = datumFormatteerder.format((Date) waardeObject);
        } else {
            waardeString = waardeObject.toString();
        }
        return waardeString;
    }

    /**
     * .
     * @param tabelNaam .
     * @param persIds .
     * @return .
     * @throws SQLException .
     */
    private List<?> dumpDataPers(final String tabelNaam, final List<Integer> persIds) throws SQLException
    {
        final String selectSql = builSelectPersSql(tabelNaam, tableMap.get(tabelNaam), persIds);
        return dumpData(tabelNaam, selectSql);
    }

    /**
     * .
     * @param tabelNaam .
     * @param persIds .
     * @return .
     * @throws SQLException .
     */
    private List<?> dumpDataPersHis(final String tabelNaam, final List<Integer> persIds)
        throws SQLException
    {
        final String selectSql = builSelectPersHisSql(tabelNaam, tableMap.get(tabelNaam), persIds);
        return dumpData(tabelNaam, selectSql);
    }

    /**
     * .
     * @param tabelNaam .
     * @param persIds .
     * @return .
     * @throws SQLException .
     */
    private List<?> dumpDataPersSubject(final String tabelNaam, final List<Integer> persIds)
        throws SQLException
    {
        final String selectSql = builSelectPersSubjectSql(tabelNaam, tableMap.get(tabelNaam), persIds);
        return dumpData(tabelNaam, selectSql);
    }

    /**
     * .
     * @param tabelNaam .
     * @param persIds .
     * @return .
     * @throws SQLException .
     */
    private List<?> dumpDataGeverifieerdeSubject(final String tabelNaam, final List<Integer> persIds)
        throws SQLException
    {
        final String selectSql = builSelectGeverifieerdeSubjectSql(tabelNaam, tableMap.get(tabelNaam), persIds);
        return dumpData(tabelNaam, selectSql);
    }

    /**
     * .
     * @param tabelNaam .
     * @param persIds .
     * @param subjectTabelNaam .
     * @return .
     * @throws SQLException .
     */
    private List<?> dumpDataPersSubjectHis(final String tabelNaam, final String subjectTabelNaam,
        final List<Integer> persIds) throws SQLException
    {
        final String selectSql = builSelectPersSubjectHisSql(tabelNaam, subjectTabelNaam, tableMap.get(tabelNaam), persIds);
        return dumpData(tabelNaam, selectSql);
    }

    /**
     * .
     * @param tabelNaam .
     * @param persIds .
     * @param subjectTabelNaam .
     * @return .
     * @throws SQLException .
     */
    private List<?> dumpDataGeverifieerdeSubjectHis(final String tabelNaam, final String subjectTabelNaam,
                                           final List<Integer> persIds) throws SQLException
    {
        final String selectSql = builSelectGeverifieerdeSubjectHisSql(tabelNaam, subjectTabelNaam, tableMap.get(tabelNaam), persIds);
        return dumpData(tabelNaam, selectSql);
    }

    /**
     * .
     * @param relatieTabelNaam .
     * @param betrokkenTabelNaam .
     * @param persIds .
     * @return .
     * @throws SQLException .
     */
    private List<?> dumpDataRelatie(final String relatieTabelNaam, final String betrokkenTabelNaam,
        final List<Integer> persIds) throws SQLException
    {
        final String selectSql = builSelectRelatieSql(relatieTabelNaam, betrokkenTabelNaam,
            tableMap.get(relatieTabelNaam), persIds);
        return dumpData(relatieTabelNaam, selectSql);
    }

    /**
     * .
     * @param tabelNaam .
     * @param relatieTabelNaam .
     * @param betrokkenTabelNaam .
     * @param persIds .
     * @return .
     * @throws SQLException .
     */
    private List<?> dumpDataRelatieHis(final String tabelNaam, final String relatieTabelNaam,
        final String betrokkenTabelNaam, final List<Integer> persIds) throws SQLException
    {
        final String selectSql = builSelectRelatieHisSql(tabelNaam, relatieTabelNaam,
            betrokkenTabelNaam, tableMap.get(tabelNaam), persIds);
        return dumpData(tabelNaam, selectSql);
    }

    /**
     * .
     * @param persIds .
     * @return .
     * @throws SQLException .
     */
    private List<?> dumpRelatie(final List<Integer> persIds) throws SQLException {
        final List<?> relatieIds = dumpDataRelatie("Relatie", "Betr", persIds);
        for (final String tabelNaam : tabelRelatieHis) {
            dumpDataRelatieHis(tabelNaam, "Relatie", "Betr", persIds);
        }
        return relatieIds;
    }

    /**
     * .
     * @param persIds .
     * @param relatieIds .
     * @return .
     * @throws SQLException .
     */
    private List<?> dumpBetrokkenheid(final List<?> persIds,
        final List<?> relatieIds) throws SQLException
    {
        final String selectSql = builSelectBetrokkenheidSql("Betr", tableMap.get("Betr"), persIds, relatieIds);
        final List<?> betrIds = dumpData("Betr", selectSql);
        for (final String tabelNaam : tabelBetrHis) {
            dumpBetrokkenheidHis(tabelNaam, persIds, relatieIds);
        }
        return betrIds;
    }

    /**
     * .
     * @param tabelNaam .
     * @param persIds .
     * @param relatieIds .
     * @return .
     * @throws SQLException .
     */
    private List<?> dumpBetrokkenheidHis(final String tabelNaam, final List<?> persIds,
        final List<?> relatieIds) throws SQLException
    {
        final String selectSql = builSelectBetrokkenheidHisSql(tabelNaam, "Betr",
            tableMap.get(tabelNaam), persIds, relatieIds);
        return dumpData(tabelNaam, selectSql);
    }

    /**
     * .
     * @param outputFileName .
     * @param persIds .
     * @return .
     * @throws SQLException .
     */
    public String dumpPersById(final String outputFileName, final List<Integer> persIds) throws SQLException
    {
        try {
            final String deleteRelaties = String.format(
                   "DELETE FROM kern.relatie r USING kern.betr b, kern.pers p"
                       + " WHERE r.id = b.relatie"
                       + " AND b.pers = p.id"
                       + " AND p.id IN (%s);", buildSelectie(persIds, null)
            );
            final String deletePers = String.format(
                      "DELETE FROM kern.pers p WHERE p.id in (%s);", buildSelectie(persIds, null));
            sb = new StringBuffer();
            writeLine(deleteRelaties);
            writeLine(deletePers);

            final List<?> gevondenIds = dumpDataPers("Pers", persIds);
            if (null != gevondenIds && gevondenIds.size() > 0) {
                dumpDataPersHis(persIds);
                final List<?> relatieIds = dumpRelatie(persIds);
                if (null != relatieIds && relatieIds.size() > 0) {
                    dumpBetrokkenheid(persIds, relatieIds);
                }
            } else {
                System.out.println("Kan geen records vinden met deze Ids: " + persIds);
            }
            return sb.toString();
           // TODO: multirealiteit, levering aan Derde,
        } finally {
            if ((sb.length() > 0) && (outputFileName != null)) {
                try {
                    final FileOutputStream fos;
                    fos = new FileOutputStream(new File(outputFileName));
                    fos.write(sb.toString().getBytes());

                    try { fos.close(); } catch (final Throwable e) {
                        System.out.println("");
                    }

                } catch (final IOException ex) {
                    throw new SQLException("Geconverteerde IO Exception: " + ex.getMessage(), ex);
                }
            }
        }

    }

    /**
     * .
     * @param persIds .
     * @throws SQLException .
     */
    private void dumpDataPersHis(final List<Integer> persIds) throws SQLException {
        for (final String tabelNaam : tabelPersHis) {
            dumpDataPersHis(tabelNaam, persIds);
        }
        dumpDataPersSubject("PersVoornaam", persIds);
        dumpDataPersSubjectHis("His_PersVoornaam", "PersVoornaam", persIds);
        dumpDataPersSubject("PersGeslnaamcomp", persIds);
        dumpDataPersSubjectHis("His_PersGeslnaamcomp", "PersGeslnaamcomp", persIds);
        dumpDataPersSubject("PersAdres", persIds);
        dumpDataPersSubjectHis("His_PersAdres", "PersAdres", persIds);
        dumpDataPersSubject("PersIndicatie", persIds);
        dumpDataPersSubjectHis("His_PersIndicatie", "PersIndicatie", persIds);
        dumpDataPersSubject("PersNation", persIds);
        dumpDataPersSubjectHis("His_PersNation", "PersNation", persIds);
        dumpDataPersSubject("PersReisdoc", persIds);
        dumpDataPersSubjectHis("His_PersReisdoc", "PersReisdoc", persIds);
        dumpDataPersSubject("PersVerstrbeperking", persIds);
        dumpDataPersSubjectHis("His_PersVerstrbeperking", "PersVerstrbeperking", persIds);
        dumpDataPersSubject("PersOnderzoek", persIds);
        dumpDataPersSubjectHis("His_PersOnderzoek", "PersOnderzoek", persIds);
        dumpDataGeverifieerdeSubject("PersVerificatie", persIds);
        dumpDataGeverifieerdeSubjectHis("His_PersVerificatie", "PersVerificatie", persIds);
        //TODO: Pers cache?
    }

}
