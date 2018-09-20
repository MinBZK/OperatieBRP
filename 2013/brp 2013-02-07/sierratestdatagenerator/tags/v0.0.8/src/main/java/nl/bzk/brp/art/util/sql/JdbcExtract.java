/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.art.util.sql;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.map.HashedMap;
/**
 *
 * Extract de informatie van een persoon of een set van personen uit de database incl. zijn relaties.
 * Creert delete statements om deze set op te ruimen (incl. relatie en betrokkenheden).
 * Creert ínsert statements om deze set weer terug te zetten (incl. zijn relaties).
 * Let op dat de personen die betrokken zijn in de relaties (en geen onderdeel maakt van de set) niet aangemaakt
 * verwijderd zijn. De sql gaat vanuit dat deze personen nog steeds bestaat.
 *
 */
public class JdbcExtract {

    private Connection connection;
    private String userName = "brp";
    private String password = "brp";
    private String url = "jdbc:postgresql://bdev-db.modernodam.nl/brpdev";

    private final Map<String, List<String>> tableMap = new HashedMap();

    // 15 pers groepen met historie
    private final List<String> tabelPersHis = Arrays.asList(
            "His_PersAanschr",
            "His_PersBijhgem",
            "His_PersBijhverantwoordelijk",
            "His_PersEUVerkiezingen",
            "His_PersGeboorte",
            "His_PersGeslachtsaand",
            "His_PersIDs",
            "His_PersImmigratie",
            "His_PersInschr",
            "His_PersOpschorting",
            "His_PersOverlijden",
            "His_PersPK",
            "His_PersUitslNLKiesr",
            "His_PersVerblijfsr",
            "His_PersSamengesteldeNaam"
    );

    private final List<String> tabelNamen = Arrays.asList(
            "Pers",
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
            "PersVerificatie",
            "His_PersVerificatie",
            "PersVoornaam",
            "His_PersVoornaam",
            "PersOnderzoek",

            "Relatie",
            "His_Relatie",

            "Betr",
            "His_BetrOuderlijkgezag",
            "His_BetrOuderschap"
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
        Properties connectionProps = new Properties();
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
        haalopKolomnamen(tabelNamen);
        haalopKolomnamen(tabelPersHis);
    }


    /**
     * .
     * @param tabelNamen .
     * @throws SQLException .
     */
    private void haalopKolomnamen(final List<String> tabelNamen) throws SQLException {
        for (String tabelNaam : tabelNamen) {
            String sql = "SELECT * from kern." + tabelNaam + " LIMIT 1";
            List<String> kolomNamen = getMetadataInfo(sql);
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
        List<String> kolomNamen = new ArrayList<String>();
        Statement st = getConnection().createStatement();
        ResultSet rs = st.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
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
    private String buildSelectie(final List<? extends Object> waarden, final String prefix) {
        StringBuffer str = new StringBuffer();
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
     * @param bsns .
     * @return .
     * @throws SQLException .
     */
    private List<Integer> getPersIdsByBsn(final List<String> bsns) throws SQLException {
        return (List<Integer>) executeSelect("pers", builSelectPersIdsByBsn("pers", Arrays.asList("id"), bsns));
    }

    /**
     * .
     * @param tabelNaam .
     * @param kolommen .
     * @param bsns .
     * @return .
     */
    private String builSelectPersIdsByBsn(final String tabelNaam, final List<String> kolommen,
        final List<String> bsns)
    {
        return String.format("SELECT %s FROM kern.%s p WHERE p.bsn in (%s)",
                buildSelectie(kolommen, "p."), tabelNaam,  buildSelectie(bsns, null)
        );
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

    private String builSelectPersSubjectSql(final String tabelNaam, final List<String> kolommen,
        final List<Integer> persIds)
    {
        return String.format("SELECT %s FROM kern.%s o, kern.pers p WHERE o.pers = p.id and p.id in (%s)",
                buildSelectie(kolommen, "o."), tabelNaam,  buildSelectie(persIds, null)
        );
    }

    private String builSelectPersSubjectHisSql(final String tabelNaam, final String subjectTabelNaam,
            final List<String> kolommen, final List<Integer> persIds) {
        return String.format("SELECT %s FROM kern.%s oh, kern.%s s, kern.pers p WHERE oh.%s = s.id and s.pers = p.id and p.id in (%s)",
                buildSelectie(kolommen, "oh."), tabelNaam, subjectTabelNaam, subjectTabelNaam, buildSelectie(persIds, null)
        );
    }

    private String builSelectRelatieSql(final String relatieTabelNaam, final String betrokkenTabelNaam,
            final List<String> kolommen, final List<Integer> persIds) {
        return String.format("SELECT distinct %s FROM kern.%s r, kern.%s b, kern.pers p WHERE r.id = b.%s and b.pers = p.id  and p.id in (%s)",
                buildSelectie(kolommen, "r."), relatieTabelNaam, betrokkenTabelNaam, relatieTabelNaam, buildSelectie(persIds, null)
        );
    }

    private String builSelectRelatieHisSql(final String tabelNaam, final String relatieTabelNaam, final String betrokkenTabelNaam,
            final List<String> kolommen, final List<Integer> persIds) {
        return String.format("SELECT distinct %s FROM kern.%s rh, kern.%s r, kern.%s b, kern.pers p WHERE rh.%s = r.id and r.id = b.%s and b.pers = p.id  and p.id in (%s)",
                buildSelectie(kolommen, "rh."), tabelNaam, relatieTabelNaam, betrokkenTabelNaam, relatieTabelNaam, relatieTabelNaam, buildSelectie(persIds, null)
        );
    }

    private String builSelectBetrokkenheidSql(final String betrokkenTabelNaam,
            final List<String> kolommen, final List<? extends Object> persIds, final List<? extends Object> relatieIds) {
        return String.format("SELECT distinct %s FROM kern.%s b WHERE (b.pers in (%s)  or b.relatie in (%s))",
                buildSelectie(kolommen, "b."), betrokkenTabelNaam,
                buildSelectie(persIds, null), buildSelectie(relatieIds, null)
        );
    }

    private String builSelectBetrokkenheidHisSql(final String tabelNaam, final String betrokkenTabelNaam,
            final List<String> kolommen, final List<? extends Object> persIds, final List<? extends Object> relatieIds) {
        return String.format("SELECT distinct %s FROM kern.%s bh, kern.%s b WHERE bh.%s = b.id and (b.pers in (%s) or b.relatie in (%s))",
                buildSelectie(kolommen, "bh."), tabelNaam, betrokkenTabelNaam, betrokkenTabelNaam,
                buildSelectie(persIds, null), buildSelectie(relatieIds, null)
        );
    }

    private List<? extends Object> executeSelect(final String tabelNaam, final String selectieSQL) throws SQLException {
//        System.out.println(selectieSQL);
        List<Object> ids = new ArrayList<Object>();
        Statement st = getConnection().createStatement();
        ResultSet rs = st.executeQuery(selectieSQL);
        while (rs.next()) {
            ids.add(rs.getObject("id"));
        }
        return ids;
    }

    public List<String> getVersionNumber() throws SQLException  {
        List<String> versionNr = new ArrayList<String>();
        Statement st = getConnection().createStatement();
        try {
            ResultSet rs = st.executeQuery("select releaseversion, exceltimestamp from test.artversion");
            if (rs.next()) {
                versionNr.add(rs.getString("releaseversion"));
                versionNr.add(rs.getString("exceltimestamp"));
            }
        } finally {
            st.close();
        }
        return versionNr;
    }

    public List<PersoonDto> getAllPersIds() throws SQLException {
        List<PersoonDto> persIds = new ArrayList<PersoonDto>();
        Statement st = getConnection().createStatement();
        try {
            ResultSet rs = st.executeQuery("select ID, BSN, ANR from kern.pers");
            while (rs.next()) {
                persIds.add(new PersoonDto(rs.getInt("ID"), rs.getObject("BSN"),  rs.getObject("ANR")));
            }
        } finally {
            st.close();
        }
        return persIds;
    }

    private List<? extends Object> dumpData(final String tabelNaam, final String selectieSQL) throws SQLException {
        // System.out.println(selectieSQL);
        List<Object> ids = new ArrayList<Object>();
        List<String> kolommen = tableMap.get(tabelNaam);
        Statement st = getConnection().createStatement();
        try {
            ResultSet rs = st.executeQuery(selectieSQL);
            while (rs.next()) {
                StringBuffer insertSql = new StringBuffer("INSERT INTO ").append("kern.").append(tabelNaam).append(" ");
                insertSql.append(" (").append(buildSelectie(kolommen, null)).append(") ");
                insertSql.append(" VALUES (");
                for (int i = 0; i<kolommen.size(); i++) {
                    if (i>0) {
                        insertSql.append(",");
                    }
                    if (rs.getObject(kolommen.get(i)) != null) {
                        insertSql.append("'").append(rs.getObject(kolommen.get(i))).append("'");
                    } else {
                        insertSql.append("null");
                    }
                }
                insertSql.append(");");
                ids.add(rs.getObject("id"));
                writeLine(insertSql.toString());
            }
        } finally {
            st.close();
        }
        return ids;
    }

    private List<? extends Object> dumpDataPers(final String tabelNaam, final List<Integer> persIds) throws SQLException {
        String selectSql = builSelectPersSql(tabelNaam, tableMap.get(tabelNaam), persIds);
        return dumpData(tabelNaam, selectSql);
    }

    private List<? extends Object> dumpDataPersHis(final String tabelNaam, final List<Integer> persIds) throws SQLException {
        String selectSql = builSelectPersHisSql(tabelNaam, tableMap.get(tabelNaam), persIds);
        return dumpData(tabelNaam, selectSql);
    }

    private List<? extends Object> dumpDataPersSubject(final String tabelNaam, final List<Integer> persIds) throws SQLException {
        String selectSql = builSelectPersSubjectSql(tabelNaam, tableMap.get(tabelNaam), persIds);
        return dumpData(tabelNaam, selectSql);
    }

    private List<? extends Object> dumpDataPersSubjectHis(final String tabelNaam, final String subjectTabelNaam, final List<Integer> persIds) throws SQLException {
        String selectSql = builSelectPersSubjectHisSql(tabelNaam, subjectTabelNaam, tableMap.get(tabelNaam), persIds);
        return dumpData(tabelNaam, selectSql);
    }

    private List<? extends Object> dumpDataRelatie(final String relatieTabelNaam, final String betrokkenTabelNaam, final List<Integer> persIds) throws SQLException {
        String selectSql = builSelectRelatieSql(relatieTabelNaam, betrokkenTabelNaam, tableMap.get(relatieTabelNaam), persIds);
        return dumpData(relatieTabelNaam, selectSql);
    }

    private List<? extends Object> dumpDataRelatieHis(final String tabelNaam, final String relatieTabelNaam, final String betrokkenTabelNaam, final List<Integer> persIds) throws SQLException {
        String selectSql = builSelectRelatieHisSql(tabelNaam, relatieTabelNaam, betrokkenTabelNaam, tableMap.get(tabelNaam), persIds);
        return dumpData(tabelNaam, selectSql);
    }

    public List<? extends Object> dumpRelatie(final List<Integer> persIds) throws SQLException {
        List<? extends Object> relatieIds = dumpDataRelatie("Relatie", "Betr", persIds);
        dumpDataRelatieHis("His_Relatie", "Relatie", "Betr", persIds);
        return relatieIds;
    }

    public List<? extends Object> dumpBetrokkenheid(final List<? extends Object> persIds, final List<? extends Object> relatieIds) throws SQLException {
        String selectSql = builSelectBetrokkenheidSql("Betr", tableMap.get("Betr"), persIds, relatieIds);
        List<? extends Object> betrIds = dumpData("Betr", selectSql);
        dumpBetrokkenheidHis("His_BetrOuderlijkgezag", persIds, relatieIds);
        dumpBetrokkenheidHis("His_BetrOuderschap", persIds, relatieIds);
        return betrIds;
    }

    public List<? extends Object> dumpBetrokkenheidHis(final String tabelNaam, final List<? extends Object> persIds,
            final List<? extends Object> relatieIds) throws SQLException {
        String selectSql = builSelectBetrokkenheidHisSql(tabelNaam, "Betr", tableMap.get(tabelNaam), persIds, relatieIds);
        return dumpData(tabelNaam, selectSql);
    }

    public String dumpPersById(final String outputFileName, final List<Integer> persIds) throws SQLException {
       try {
           String deleteRelaties = String.format(
                   "DELETE FROM kern.relatie r WHERE r.id in " +
                   " (SELECT r2.id from kern.relatie r2, kern.betr b, kern.pers p " +
                   " WHERE r2.id = b.relatie and b.pers = p.id and p.id IN (%s));", buildSelectie(persIds, null) );
              String deletePers = String.format(
                      "DELETE FROM kern.pers p WHERE p.id in (%s);", buildSelectie(persIds, null) );
           sb = new StringBuffer();
//           fos = new FileOutputStream(new File(outputFileName));
           writeLine(deleteRelaties);
           writeLine(deletePers);

//               System.out.println (deleteRelaties);
//               System.out.println (deletePers);

           List<? extends Object> gevondenIds = dumpDataPers("Pers", persIds);
           if (null != gevondenIds && gevondenIds.size() > 0) {
               dumpDataPersHis(persIds);
               List<? extends Object> relatieIds = dumpRelatie(persIds);
               if (null != relatieIds && relatieIds.size() > 0) {
                   dumpBetrokkenheid(persIds, relatieIds);
               }
           } else {
               System.out.println("Kan geen records vinden met deze Ids: " + persIds);
           }
           return sb.toString();
       } finally {
           if ((sb.length() > 0) && (outputFileName != null)) {
               try {
                   FileOutputStream fos = null;
                   fos = new FileOutputStream(new File(outputFileName));
                   fos.write(sb.toString().getBytes());
                   if (fos != null) {
                       try { fos.close(); } catch (Throwable e) {}
                   }
               } catch (IOException ex) {
                   throw new SQLException("Geconverteerde IO Exception: " + ex.getMessage(), ex);
               }

           }
       }

    }

    public List<Integer> dumpPers(final String outputFileName, final List<String> bsnNrs) throws IOException, SQLException {
        List<Integer> persIds = getPersIdsByBsn(bsnNrs);
        if (persIds != null && persIds.size() > 0) {
           dumpPersById(outputFileName, persIds);
           return persIds;
        } else {
            System.out.println("Kan geen enkel records vinden met deze bsnNrs: " + bsnNrs);
            return null;
        }
    }

    private void dumpDataPersHis(final List<Integer> persIds) throws SQLException {
        for (String tabelNaam : tabelPersHis) {
            dumpDataPersHis(tabelNaam, persIds);
        }
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

//        dumpDataPersSubject("PersVerificatie", bsnNrs);
//        dumpDataPersSubjectHis("His_PersVerificatie", "PersVerificatie", bsnNrs);
        dumpDataPersSubject("PersVoornaam", persIds);
        dumpDataPersSubjectHis("His_PersVoornaam", "PersVoornaam", persIds);

        dumpDataPersSubject("PersOnderzoek", persIds);

    }

}
