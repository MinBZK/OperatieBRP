/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistoriePatroon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.domain.element.GroepElement;
import org.apache.commons.lang3.StringUtils;

/**
 * SqlBepaler.
 */
@Bedrijfsregel(Regel.R1538)
@Bedrijfsregel(Regel.R1539)
@Bedrijfsregel(Regel.R2286)
@Bedrijfsregel(Regel.R2402)
final class SqlBepaler {

    private static final String PERS_TABEL = "Pers";
    private static final String BETR_TABEL = "Betr";
    private static final String RELATIE_TABEL = "Relatie";
    private static final String AND_CLAUSE = "and ";
    private static final String OR_CLAUSE = " or ";
    private static final String PERS_ALIAS_ROOT = "p";
    private static final String HIS_PREFIX = "his_";
    private static final String OPEN = "(";
    private static final String SLUIT = ")";
    private static final String STANDAARD_JOIN_FROM_CLAUSE = "%nkern.%s %s ";
    private final ZoekCriteria zoekCriteria;

    private int aliasIndex = 1;

    /**
     * @param zoekCriteriaSet zoekCriteria
     * @param maxResults maxResults
     * @param historischeZoekvraag historisch
     * @param materieelPeilmoment materieelPeilmoment
     * @param peilperiode peilperiode
     */
    SqlBepaler(final Set<ZoekCriterium> zoekCriteriaSet, final int maxResults, final boolean historischeZoekvraag,
               final Integer materieelPeilmoment, final boolean peilperiode) {
        this.zoekCriteria = new ZoekCriteria(zoekCriteriaSet, historischeZoekvraag);
        zoekCriteria.setMaxResults(maxResults);
        zoekCriteria.setMaterieelPeilmoment(materieelPeilmoment);
        zoekCriteria.setPeilperiode(peilperiode);
    }


    /**
     * @return de zoek sql
     */
    SqlStamementZoekPersoon maakSql() {
        final Statement rootStatement = new Statement(PERS_ALIAS_ROOT);
        final Map<MetaInformatieZoekCriterium, JoinInformatie> joinInformatieMap = Maps.newHashMap();
        rootStatement.sqlBuilder.append(String.format("select distinct p.id %nfrom%nkern.%s %s ", PERS_TABEL, PERS_ALIAS_ROOT));
        //selecteer geen
        rootStatement.sqlWhereBuilder.append(String
                .format("%nwhere p.srt = %d and (p.naderebijhaard is null or p.naderebijhaard not in (%d, %d, %d))", SoortPersoon.INGESCHREVENE.getId(),
                        NadereBijhoudingsaard.FOUT.getId(), NadereBijhoudingsaard.ONBEKEND.getId(), NadereBijhoudingsaard.GEWIST.getId()));
        final List<MetaInformatieZoekCriterium> metaInformatieZoekCriteria = new ArrayList<>();
        for (ZoekCriterium zoekCriterium : zoekCriteria.getZoekCriteria()) {
            final MetaInformatieZoekCriterium
                    metaInformatieZoekCriterium =
                    MetaInformatieZoekCriterium.maak(zoekCriterium, zoekCriteria.isHistorischeZoekvraag());
            metaInformatieZoekCriteria.add(metaInformatieZoekCriterium);
        }
        ///maak joins nodig voor de criteria
        maakJoins(rootStatement.basisJoin, metaInformatieZoekCriteria, joinInformatieMap, rootStatement);
        //maak clauses + indag
        maakClauses(metaInformatieZoekCriteria, joinInformatieMap, rootStatement);
        //maak not exist clauses voor LEEG zoekcriteria op groepen voor historie of objecten voor actueel
        maakBestaatNietClauses(rootStatement);
        if (zoekCriteria.isHistorischeZoekvraag()) {
            voegHistorieClausesToe(rootStatement.basisJoin, rootStatement);
        }
        if (rootStatement.sqlFromBuilder.length() != 0) {
            rootStatement.sqlBuilder.append(String.format("%n,"));
        }
        rootStatement.sqlBuilder.append(rootStatement.sqlFromBuilder.toString());
        rootStatement.sqlBuilder.append(rootStatement.sqlWhereBuilder.toString());
        rootStatement.sqlBuilder.append(String.format("%nlimit %d; ", zoekCriteria.getMaxResults() + 1));
        return new SqlStamementZoekPersoon(rootStatement.sqlBuilder.toString(), rootStatement.parameters);
    }

    private void maakBestaatNietClauses(final Statement rootStatement) {
        if (!zoekCriteria.isHistorischeZoekvraag()) {
            for (BestaatNietClauseActueel bestaatNietClause : zoekCriteria.getBestaatNietClausesActueel()) {
                startWhereClause(rootStatement.sqlWhereBuilder);
                maakBestaatNietActueel(rootStatement, bestaatNietClause);
            }
        } else {
            for (BestaatNietClauseHistorisch bestaatNietClause : zoekCriteria.getBestaatNietClausesHistorisch()) {
                //historisch vragen we of een rij niet bestaat via not exists OF we vragen of er een rij bestaat met lege waarde
                startWhereClause(rootStatement.sqlWhereBuilder);
                rootStatement.sqlWhereBuilder.append(OPEN);
                maakBestaatNietHistorisch(rootStatement, bestaatNietClause, true);
                rootStatement.sqlWhereBuilder.append(OR_CLAUSE);
                maakBestaatNietHistorisch(rootStatement, bestaatNietClause, false);
                rootStatement.sqlWhereBuilder.append(SLUIT);
            }
        }
    }

    private void maakBestaatNietActueel(Statement rootStatement,
                                        BestaatNietClauseActueel bestaatNietClause) {
        final Map<MetaInformatieZoekCriterium, JoinInformatie> joinInformatieMapNotExist = Maps.newHashMap();

        //not exists where indag = true and x is not null (dit kan simpeler met indag = false or x is null)
        final StringBuilder notExistsSql = new StringBuilder();
        final String notExistAliasPers = maakAlias(PERS_TABEL);
        final Statement statementNotExists = new Statement(notExistAliasPers);
        notExistsSql.append("not exists ( select 1 from ");
        statementNotExists.sqlFromBuilder.append(String.format("%nkern.%s %s%n", PERS_TABEL, notExistAliasPers));
        statementNotExists.sqlWhereBuilder.append(String.format("%nwhere %s.id = %s.id%n", PERS_ALIAS_ROOT, notExistAliasPers));
        final List<MetaInformatieZoekCriterium> metaInformatieZoekCriteria = new ArrayList<>();
        for (ZoekCriterium zoekCriterium : bestaatNietClause.getZoekCriteria()) {
            final MetaInformatieZoekCriterium
                    metaInformatieZoekCriterium =
                    MetaInformatieZoekCriterium.maak(zoekCriterium, zoekCriteria.isHistorischeZoekvraag());
            metaInformatieZoekCriteria.add(metaInformatieZoekCriterium);
        }
        maakJoins(statementNotExists.basisJoin, metaInformatieZoekCriteria, joinInformatieMapNotExist, statementNotExists);

        final List<GroepElement> toegevoegdeGroepen = new ArrayList<>();
        for (MetaInformatieZoekCriterium metaInformatieZoekCriterion : metaInformatieZoekCriteria) {
            maakClause(metaInformatieZoekCriterion, statementNotExists, true, joinInformatieMapNotExist);
            if (metaInformatieZoekCriterion.getAdditioneel() != null) {
                maakClause(metaInformatieZoekCriterion.getAdditioneel(), statementNotExists, false, joinInformatieMapNotExist);
            }
            appendActueelEnGeldigConditie(metaInformatieZoekCriterion, toegevoegdeGroepen, statementNotExists.sqlWhereBuilder, joinInformatieMapNotExist);
        }
        notExistsSql.append(statementNotExists.sqlFromBuilder).append(statementNotExists.sqlWhereBuilder);
        notExistsSql.append(SLUIT);
        rootStatement.sqlWhereBuilder.append(notExistsSql);
        rootStatement.parameters.addAll(statementNotExists.parameters);
    }


    private void maakBestaatNietHistorisch(Statement rootStatement, BestaatNietClauseHistorisch bestaatNietClause, boolean not) {
        final Map<MetaInformatieZoekCriterium, JoinInformatie> joinInformatieMapNotExist = Maps.newHashMap();
        rootStatement.sqlWhereBuilder.append(OPEN);
        final StringBuilder notExistsSql = new StringBuilder();
        final String notExistAliasPers = maakAlias(PERS_TABEL);
        final Statement statementNotExists = new Statement(notExistAliasPers);
        notExistsSql.append(String.format("%s exists ( select 1 from ", not ? "not" : ""));
        statementNotExists.sqlFromBuilder.append(String.format("%nkern.%s %s%n", PERS_TABEL, notExistAliasPers));
        statementNotExists.sqlWhereBuilder.append(String.format("%nwhere %s.id = %s.id%n", PERS_ALIAS_ROOT, notExistAliasPers));
        final List<MetaInformatieZoekCriterium> metaInformatieZoekCriteria = new ArrayList<>();
        for (ZoekCriterium zoekCriterium : bestaatNietClause.getZoekCriteria()) {
            final MetaInformatieZoekCriterium
                    metaInformatieZoekCriterium =
                    MetaInformatieZoekCriterium.maak(zoekCriterium, zoekCriteria.isHistorischeZoekvraag());
            metaInformatieZoekCriteria.add(metaInformatieZoekCriterium);
        }
        maakJoins(statementNotExists.basisJoin, metaInformatieZoekCriteria, joinInformatieMapNotExist, statementNotExists);
        for (MetaInformatieZoekCriterium metaInformatieZoekCriterion : metaInformatieZoekCriteria) {
            if (not) {
                //voor de not exist vragen van historisch niet bestaan zijn additionele criteria relevant e.g.
                // indicatie soort
                if (metaInformatieZoekCriterion.getAdditioneel() != null) {
                    maakClause(metaInformatieZoekCriterion.getAdditioneel(), statementNotExists, true, joinInformatieMapNotExist);
                }
            } else {
                maakClause(metaInformatieZoekCriterion, statementNotExists, false, joinInformatieMapNotExist);
                if (metaInformatieZoekCriterion.getAdditioneel() != null) {
                    maakClause(metaInformatieZoekCriterion.getAdditioneel(), statementNotExists, false, joinInformatieMapNotExist);
                }
            }
            if (!not) {
                //voor alleen de bestaans vraag via not exist is tsverval null niet relevant
                voegHistorieClausesToe(statementNotExists.basisJoin, statementNotExists);
            }
        }
        notExistsSql.append(statementNotExists.sqlFromBuilder).append(statementNotExists.sqlWhereBuilder);
        notExistsSql.append(SLUIT);
        rootStatement.sqlWhereBuilder.append(notExistsSql);
        rootStatement.parameters.addAll(statementNotExists.parameters);
        rootStatement.sqlWhereBuilder.append(SLUIT);
    }

    private void maakClauses(List<MetaInformatieZoekCriterium> metaInformatieZoekCriteria, Map<MetaInformatieZoekCriterium, JoinInformatie> joinInformatieMap,
                             final Statement statement) {
        final List<GroepElement> toegevoegdeGroepen = new ArrayList<>();
        for (MetaInformatieZoekCriterium metaInformatieZoekCriterium : metaInformatieZoekCriteria) {
            maakClause(metaInformatieZoekCriterium, statement, false, joinInformatieMap);
            if (metaInformatieZoekCriterium.getAdditioneel() != null) {
                maakClause(metaInformatieZoekCriterium.getAdditioneel(), statement, false, joinInformatieMap);
            }
            appendActueelEnGeldigConditie(metaInformatieZoekCriterium, toegevoegdeGroepen, statement.sqlWhereBuilder, joinInformatieMap);
            for (MetaInformatieZoekCriterium informatieZoekCriterium : metaInformatieZoekCriterium.getZoekCriteriaOrClauses()) {
                appendActueelEnGeldigConditie(informatieZoekCriterium, toegevoegdeGroepen, statement.sqlWhereBuilder, joinInformatieMap);
            }
        }
    }

    private void maakJoins(JoinInformatie basisJoinInformatie, List<MetaInformatieZoekCriterium> metaInformatieZoekCriteria,
                           Map<MetaInformatieZoekCriterium, JoinInformatie> joinInformatieMap, final Statement statement) {
        for (final MetaInformatieZoekCriterium metaInformatieZoekCriterium : metaInformatieZoekCriteria) {
            maakJoinVoorMetaInformatie(basisJoinInformatie, joinInformatieMap, metaInformatieZoekCriterium, statement);
            for (MetaInformatieZoekCriterium metaInformatieZoekCriteriumSub : metaInformatieZoekCriterium.getZoekCriteriaOrClauses()) {
                maakJoinVoorMetaInformatie(basisJoinInformatie, joinInformatieMap, metaInformatieZoekCriteriumSub, statement);
            }
            if (metaInformatieZoekCriterium.getAdditioneel() != null) {
                maakJoinVoorMetaInformatie(basisJoinInformatie, joinInformatieMap, metaInformatieZoekCriterium.getAdditioneel(), statement);
            }
        }
    }

    private void maakJoinVoorMetaInformatie(JoinInformatie basisJoinInformatie, Map<MetaInformatieZoekCriterium, JoinInformatie> joinInformatieMap,
                                            MetaInformatieZoekCriterium metaInformatieZoekCriterium, final Statement statement) {
        final SoortBetrokkenheid soortGerelateerdeBetrokkenheid = JoinUtil.getGerelateerdeBetrokkenheid(
                metaInformatieZoekCriterium.getObjectElementVanAttribuut());
        final SoortBetrokkenheid soortBetrokkenheid = JoinUtil.getBetrokkenheid(metaInformatieZoekCriterium.getObjectElementVanAttribuut());
        final SoortRelatie soortRelatie = JoinUtil.isRelatie(metaInformatieZoekCriterium.getObjectElementVanAttribuut());
        if (soortGerelateerdeBetrokkenheid != null) {
            final JoinInformatie
                    joinInformatieGerelateerdeBetrokkenheid =
                    mapGerelateerdeBetrokkenheid(basisJoinInformatie, metaInformatieZoekCriterium, soortGerelateerdeBetrokkenheid, statement);
            joinInformatieMap.put(metaInformatieZoekCriterium, joinInformatieGerelateerdeBetrokkenheid);
        } else if (soortBetrokkenheid != null) {
            final JoinInformatie
                    joinInformatieBetrokkenheid =
                    mapBetrokkenheid(basisJoinInformatie, metaInformatieZoekCriterium, soortBetrokkenheid, statement);
            joinInformatieMap.put(metaInformatieZoekCriterium, joinInformatieBetrokkenheid);
        } else if (soortRelatie != null) {
            final JoinInformatie joinInformatieRelatie = mapRelatie(basisJoinInformatie, metaInformatieZoekCriterium, soortRelatie, statement);
            joinInformatieMap.put(metaInformatieZoekCriterium, joinInformatieRelatie);
        } else {
            maakJoinVoorCriterium(basisJoinInformatie, metaInformatieZoekCriterium, statement);
            joinInformatieMap.put(metaInformatieZoekCriterium, basisJoinInformatie);
        }
    }

    private void voegHistorieClausesToe(final JoinInformatie basisJoinInformatie, final Statement statement) {
        final List<Tabel> tabellen = new ArrayList<>();
        voegTabellenToe(basisJoinInformatie, tabellen);
        for (JoinInformatie joinInformatie : statement.betrokkenheidJoins.values()) {
            voegTabellenToe(joinInformatie, tabellen);
        }
        for (JoinInformatie joinInformatie : statement.relatieJoins.values()) {
            voegTabellenToe(joinInformatie, tabellen);
        }
        for (JoinInformatie joinInformatie : statement.gerelateerdeBetrokkenheidJoins.values()) {
            voegTabellenToe(joinInformatie, tabellen);
        }
        voegHistorieClausesToeVoor(tabellen, statement);
    }

    private void voegHistorieClausesToeVoor(List<Tabel> tabellen, final Statement statement) {
        for (Tabel tabel : tabellen) {
            final String alias = tabel.getAlias();
            if (tabel.heeftFormeleHistorie()) {
                statement.sqlWhereBuilder.append(String.format("and %s.tsverval is null %n", alias));
            }
            if (tabel.isMaterieel() && zoekCriteria.getMaterieelPeilmoment() != null) {
                statement.sqlWhereBuilder.append(
                        String.format("and %d >= %s.dataanvgel and %s.dataanvgel is not null %n", zoekCriteria.getMaterieelPeilmoment(), alias, alias));
                if (!zoekCriteria.isPeilperiode()) {
                    statement.sqlWhereBuilder
                            .append(String
                                    .format("and (%s.dateindegel is null or %d < %s.dateindegel) %n", alias, zoekCriteria.getMaterieelPeilmoment(), alias));
                }
            }
        }
    }

    private void voegTabellenToe(final JoinInformatie basisJoinInformatie, final List<Tabel> tabellen) {
        tabellen.addAll(new ArrayList<>(basisJoinInformatie.getToegevoegdeTabellen()));
    }

    private JoinInformatie mapRelatie(final JoinInformatie joinInformatie, final MetaInformatieZoekCriterium metaInformatieZoekCriterium,
                                      final SoortRelatie soortRelatie, final Statement statement) {
        JoinInformatie
                joinInformatieRelatie =
                statement.relatieJoins.computeIfAbsent(metaInformatieZoekCriterium.getObjectElementVanAttribuut().getId(),
                        k -> maakRelatieJoin(joinInformatie, soortRelatie, statement));
        maakJoinVoorCriterium(joinInformatieRelatie, metaInformatieZoekCriterium, statement);
        return joinInformatieRelatie;
    }

    private JoinInformatie mapBetrokkenheid(final JoinInformatie joinInformatie,
                                            final MetaInformatieZoekCriterium metaInformatieZoekCriterium, final SoortBetrokkenheid soortBetrokkenheid,
                                            final Statement statement) {
        JoinInformatie
                joinInformatieBetrokkenheid =
                statement.betrokkenheidJoins
                        .computeIfAbsent(soortBetrokkenheid.getId(),
                                k -> maakBetrokkenheidJoin(joinInformatie, soortBetrokkenheid, statement));
        maakJoinVoorCriterium(joinInformatieBetrokkenheid, metaInformatieZoekCriterium, statement);
        return joinInformatieBetrokkenheid;
    }

    private JoinInformatie mapGerelateerdeBetrokkenheid(final JoinInformatie joinInformatie, final MetaInformatieZoekCriterium metaInformatieZoekCriterium,
                                                        final SoortBetrokkenheid soortGerelateerdeBetrokkenheid, final Statement statement) {
        //gerelateerde betrokkenheid
        JoinInformatie
                joinInformatieGerelateerdeBetrokkenheid =
                statement.gerelateerdeBetrokkenheidJoins.computeIfAbsent(soortGerelateerdeBetrokkenheid.getId(),
                        k -> maakGerelateerdeBetrokkenheidJoin(joinInformatie, soortGerelateerdeBetrokkenheid, statement));
        maakJoinVoorCriterium(joinInformatieGerelateerdeBetrokkenheid, metaInformatieZoekCriterium, statement);
        return joinInformatieGerelateerdeBetrokkenheid;
    }

    private JoinInformatie maakRelatieJoin(final JoinInformatie joinInformatie, final SoortRelatie soortRelatie, final Statement statement) {
        final String sqlFrom = "kern.Betr %s, kern.Relatie %s";
        final String betrokkenheidAlias = BETR_TABEL.toLowerCase() + geefAliasIndex();
        final String relatieAlias = RELATIE_TABEL.toLowerCase() + geefAliasIndex();
        //sql from
        voegToeAanFrom(String.format(sqlFrom, betrokkenheidAlias, relatieAlias), statement.sqlFromBuilder);
        //sql where
        final String sqlWhere = "%s.id = %s.Pers and %s.Relatie = %s.id and %s.srt = %d %n";
        startWhereClause(statement.sqlWhereBuilder);
        statement.sqlWhereBuilder.append(String.format(sqlWhere, joinInformatie.getPersAlias(), betrokkenheidAlias, betrokkenheidAlias, relatieAlias,
                relatieAlias, soortRelatie.getId()));
        //maak join informatie
        final JoinInformatie joinInformatieGerelateerdeBetrokkenheid = new JoinInformatie(PERS_ALIAS_ROOT);
        joinInformatieGerelateerdeBetrokkenheid.getToegevoegdeTabellen().add(new Tabel(PERS_TABEL, PERS_ALIAS_ROOT, HistoriePatroon.G));
        joinInformatieGerelateerdeBetrokkenheid.getToegevoegdeTabellen().add(new Tabel(RELATIE_TABEL, relatieAlias, HistoriePatroon.G));
        joinInformatieGerelateerdeBetrokkenheid.getAliasMap().put(RELATIE_TABEL, relatieAlias);
        joinInformatieGerelateerdeBetrokkenheid.getToegevoegdeTabellen().add(new Tabel(BETR_TABEL, betrokkenheidAlias, HistoriePatroon.G));
        return joinInformatieGerelateerdeBetrokkenheid;
    }

    private JoinInformatie maakBetrokkenheidJoin(final JoinInformatie joinInformatie, final SoortBetrokkenheid soortBetrokkenheid, final Statement statement) {
        final String gerelateerdeSqlFrom = "kern.Betr %s, kern.Pers %s ";
        final String betrokkenheidAlias = BETR_TABEL.toLowerCase() + geefAliasIndex();
        final String betrokkenPersoonAlias = "betrpers" + geefAliasIndex();
        //sql from
        final String sqlFrom = String.format(gerelateerdeSqlFrom, betrokkenheidAlias, betrokkenPersoonAlias);
        voegToeAanFrom(sqlFrom, statement.sqlFromBuilder);
        //sql where
        final String gerelateerdeSqlWhere = "%s.id = %s.Pers and %s.pers = %s.id and %s.rol = %d %n";
        final String sqlWhere = String
                .format(gerelateerdeSqlWhere, joinInformatie.getPersAlias(), betrokkenheidAlias, betrokkenheidAlias, betrokkenPersoonAlias, betrokkenheidAlias,
                        soortBetrokkenheid.getId());
        startWhereClause(statement.sqlWhereBuilder);
        statement.sqlWhereBuilder.append(sqlWhere);
        //maak join informatie
        final JoinInformatie joinInformatieBetrokkenheid = new JoinInformatie(betrokkenPersoonAlias);
        joinInformatieBetrokkenheid.getAliasMap().put(PERS_TABEL, betrokkenPersoonAlias);
        joinInformatieBetrokkenheid.getToegevoegdeTabellen().add(new Tabel(PERS_TABEL, betrokkenPersoonAlias, HistoriePatroon.G));
        joinInformatieBetrokkenheid.getToegevoegdeTabellen().add(new Tabel(BETR_TABEL, betrokkenheidAlias, HistoriePatroon.G));
        joinInformatieBetrokkenheid.getAliasMap().put(BETR_TABEL, betrokkenheidAlias);
        return joinInformatieBetrokkenheid;
    }


    private JoinInformatie maakGerelateerdeBetrokkenheidJoin(final JoinInformatie joinInformatie,
                                                             final SoortBetrokkenheid soortGerelateerdeBetrokkenheid, final Statement statement) {
        final String gerelateerdeSqlFrom = "kern.Betr %s, kern.Relatie %s, kern.Betr %s, kern.Pers %s ";
        final String betrokkenheidAlias = BETR_TABEL.toLowerCase() + aliasIndex + geefAliasIndex();
        final String gerelateerdeBetrokkenheidAlias = "gbetr" + geefAliasIndex();
        final String gerelateerdeBetrokkenPersoonAlias = "gbetrpers" + geefAliasIndex();
        final String relatieAlias = RELATIE_TABEL.toLowerCase() + geefAliasIndex();
        final SoortBetrokkenheid soortBetrokkenheid;
        if (SoortBetrokkenheid.PARTNER == soortGerelateerdeBetrokkenheid) {
            soortBetrokkenheid = SoortBetrokkenheid.PARTNER;
        } else if (SoortBetrokkenheid.KIND == soortGerelateerdeBetrokkenheid) {
            soortBetrokkenheid = SoortBetrokkenheid.OUDER;
        } else if (SoortBetrokkenheid.OUDER == soortGerelateerdeBetrokkenheid) {
            soortBetrokkenheid = SoortBetrokkenheid.KIND;
        } else {
            throw new IllegalArgumentException("onbekende soort betrokkenheid");
        }
        //sql from
        final String sqlFrom = String.format(gerelateerdeSqlFrom, betrokkenheidAlias, relatieAlias, gerelateerdeBetrokkenheidAlias,
                gerelateerdeBetrokkenPersoonAlias);
        voegToeAanFrom(sqlFrom, statement.sqlFromBuilder);
        //sql where
        final String gerelateerdeSqlWhere = "%s.id = %s.Pers and %s.rol = %s and %s.Relatie = %s.id and %s.id = %s.Relatie and %s.pers = %s.id and %s"
                + ".rol = %d %n";
        final String sqlWhere = String.format(gerelateerdeSqlWhere, joinInformatie.getPersAlias(), betrokkenheidAlias, betrokkenheidAlias,
                soortBetrokkenheid.getId(),
                betrokkenheidAlias, relatieAlias, relatieAlias,
                gerelateerdeBetrokkenheidAlias, gerelateerdeBetrokkenheidAlias, gerelateerdeBetrokkenPersoonAlias, gerelateerdeBetrokkenheidAlias,
                soortGerelateerdeBetrokkenheid.getId());
        startWhereClause(statement.sqlWhereBuilder);
        statement.sqlWhereBuilder.append(sqlWhere);
        //maak join informatie
        final JoinInformatie joinInformatieGerelateerdeBetrokkenheid = new JoinInformatie(gerelateerdeBetrokkenPersoonAlias);
        joinInformatieGerelateerdeBetrokkenheid.getAliasMap().put(PERS_TABEL, gerelateerdeBetrokkenPersoonAlias);
        joinInformatieGerelateerdeBetrokkenheid.getToegevoegdeTabellen().add(new Tabel(PERS_TABEL, gerelateerdeBetrokkenPersoonAlias, HistoriePatroon.G));
        joinInformatieGerelateerdeBetrokkenheid.getToegevoegdeTabellen().add(new Tabel(RELATIE_TABEL, relatieAlias, HistoriePatroon.G));
        joinInformatieGerelateerdeBetrokkenheid.getAliasMap().put(RELATIE_TABEL, relatieAlias);
        joinInformatieGerelateerdeBetrokkenheid.getToegevoegdeTabellen().add(new Tabel(BETR_TABEL, gerelateerdeBetrokkenheidAlias, HistoriePatroon.G));
        joinInformatieGerelateerdeBetrokkenheid.getAliasMap().put(BETR_TABEL, gerelateerdeBetrokkenheidAlias);
        return joinInformatieGerelateerdeBetrokkenheid;
    }

    private void voegToeAanFrom(final String sqlFrom, final StringBuilder sqlFromBuilder) {
        if (sqlFromBuilder.length() != 0) {
            sqlFromBuilder.append(", ");
        }
        sqlFromBuilder.append(sqlFrom);
    }

    private int geefAliasIndex() {
        return aliasIndex++;
    }

    private void maakJoinVoorCriterium(final JoinInformatie joinInformatie, final MetaInformatieZoekCriterium metaInformatieZoekCriterium,
                                       final Statement statement) {
        final String tabel = metaInformatieZoekCriterium.isBekijkHistorisch()
                ? metaInformatieZoekCriterium.getTabelDatabaseInfo().getElementWaarde().getHisidentdb()
                : metaInformatieZoekCriterium.getTabelDatabaseInfo().getElementWaarde().getIdentdb();
        //bepaal benodigde joins voor conditie
        voegALaagJoinToe(joinInformatie, metaInformatieZoekCriterium, statement);
        voegCdLaagJoinToe(joinInformatie, tabel, metaInformatieZoekCriterium, statement);
    }

    private void voegALaagJoinToe(JoinInformatie joinInformatie, MetaInformatieZoekCriterium metaInformatieZoekCriterium,
                                  final Statement statement) {
        //van object/alaag naar pers als niet platgeslagen op pers. Betrokkenheid en relaties zijn al toegevoegd.
        final String objectTabel = metaInformatieZoekCriterium.getObjectDatabaseInfo().getElementWaarde().getIdentdb();
        if (joinInformatie.getToegevoegdeTabellen().stream().noneMatch(metTabelNaam(objectTabel))) {
            final String objectAlias = maakAlias(objectTabel);
            joinInformatie.getAliasMap().put(objectTabel, objectAlias);
            String persJoinKolom = PERS_TABEL.toLowerCase();
            joinInformatie.getToegevoegdeTabellen().add(new Tabel(objectTabel, objectAlias, HistoriePatroon.G));
            if (statement.sqlFromBuilder.length() != 0) {
                statement.sqlFromBuilder.append(",");
            }
            statement.sqlFromBuilder.append(String.format(STANDAARD_JOIN_FROM_CLAUSE, objectTabel, objectAlias));
            startWhereClause(statement.sqlWhereBuilder);
            statement.sqlWhereBuilder.append(String.format("%s.id = %s.%s %n", joinInformatie.getPersAlias(), objectAlias, persJoinKolom));
        }
    }

    private void voegCdLaagJoinToe(final JoinInformatie joinInformatie, final String tabel, final MetaInformatieZoekCriterium metaInformatieZoekCriterium,
                                   final Statement statement) {
        //eventueel van cdlaag naar alaag die zich kan bevinden in aparte tabel zoals bijvoorbeeld persadres of platgeslagen op pers
        //alleen relevant als historisch zoeken
        final String objectTabel = metaInformatieZoekCriterium.getObjectDatabaseInfo().getElementWaarde().getIdentdb();
        final String alias = bepaalAlias(joinInformatie.getAliasMap(), tabel);
        if (metaInformatieZoekCriterium.isBekijkHistorisch() && joinInformatie.getToegevoegdeTabellen().stream()
                .noneMatch(metTabelNaam(tabel))) {
            final String objectAlias = joinInformatie.getAliasMap().get(objectTabel);
            final String joinKolom;
            final String joinAlias;
            //groepen onder betrokkenheid wijzen naar betr (relaties hebben alleen formele groepen dus raken we alleen alaag)
            if (BETR_TABEL.equalsIgnoreCase(objectTabel)) {
                joinKolom = BETR_TABEL.toLowerCase();
                joinAlias = joinInformatie.getAliasMap().get(BETR_TABEL);
            } else if (PERS_TABEL.equalsIgnoreCase(objectTabel)) {
                joinKolom = PERS_TABEL.toLowerCase();
                joinAlias = joinInformatie.getPersAlias();
            } else {
                joinKolom = tabel.substring(HIS_PREFIX.length(), tabel.length()).toLowerCase();
                joinAlias = objectAlias;
            }
            joinInformatie.getToegevoegdeTabellen()
                    .add(new Tabel(tabel, alias, metaInformatieZoekCriterium.getAttribuutElement().getGroep().getHistoriePatroon()));
            if (statement.sqlFromBuilder.length() != 0) {
                statement.sqlFromBuilder.append(",");
            }
            statement.sqlFromBuilder.append(String.format(STANDAARD_JOIN_FROM_CLAUSE, tabel, alias));
            startWhereClause(statement.sqlWhereBuilder);
            statement.sqlWhereBuilder.append(String.format("%s.id = %s.%s %n", joinAlias, alias, joinKolom));
        }
    }

    private void maakClause(final MetaInformatieZoekCriterium metaInformatieZoekCriterium, final Statement statement, final boolean nietLeeg,
                            final Map<MetaInformatieZoekCriterium, JoinInformatie> joinInformatieMap) {
        startWhereClause(statement.sqlWhereBuilder);
        statement.sqlWhereBuilder.append(OPEN);
        final StringBuilder clause = new StringBuilder();
        final List<MetaInformatieZoekCriterium> criteria = Lists.newArrayList(metaInformatieZoekCriterium);
        criteria.addAll(metaInformatieZoekCriterium.getZoekCriteriaOrClauses());
        for (MetaInformatieZoekCriterium zoekCriterium : criteria) {
            final String alias = getAlias(zoekCriterium, joinInformatieMap);
            final String kolom = zoekCriterium.isBekijkHistorisch()
                    ? zoekCriterium.getDatabaseInfoAttribuut().getElementWaarde().getHisidentdb()
                    : zoekCriterium.getDatabaseInfoAttribuut().getElementWaarde().getIdentdb();
            if (clause.length() != 0) {
                clause.append(OR_CLAUSE);
            }
            final SqlConditie sqlConditie = SqlConditie
                    .maakConditie(zoekCriterium.getZoekCriterium(), zoekCriterium.getAttribuutElement(), alias, kolom, nietLeeg);
            statement.parameters.addAll(sqlConditie.getParameters());
            clause.append(OPEN);
            clause.append(sqlConditie.getConditie());
            clause.append(SLUIT);
        }
        statement.sqlWhereBuilder.append(clause.toString());
        statement.sqlWhereBuilder.append(SLUIT);
        statement.sqlWhereBuilder.append(String.format("%n"));
    }

    private String getAlias(MetaInformatieZoekCriterium metaInformatieZoekCriterium, Map<MetaInformatieZoekCriterium, JoinInformatie> joinInformatieMap) {
        final JoinInformatie joinInformatie = joinInformatieMap.get(metaInformatieZoekCriterium);
        final String tabel = metaInformatieZoekCriterium.isBekijkHistorisch()
                ? metaInformatieZoekCriterium.getTabelDatabaseInfo().getElementWaarde().getHisidentdb()
                : metaInformatieZoekCriterium.getTabelDatabaseInfo().getElementWaarde().getIdentdb();
        return joinInformatie.getAliasMap().get(tabel);
    }

    /**
     * Append een indag conditie. Dit is enkel van toepassing op actuele zoekvragen
     * (en historische groepen zonder materiele historie).
     *
     * Identiteitsgroepen zonder historie hebben geen 'idag' indicator. Wanneer attributen uit de identiteitgroep
     * bevraagd worden dient er minimaal één andere groep te bestaan binnen hetzelfde object waarvoor geldt dat
     * de 'indag' indicator true is.
     * resultaat:
     *
     * AND (
     * alias.indagGroepX = TRUE
     * OR
     * alias.indagGroepY = TRUE
     * OR
     * etc.
     * )
     *
     * Voor overige groepen is het resultaat:
     * AND (
     * alias.indagGroepX = TRUE
     * )
     */
    private void appendActueelEnGeldigConditie(MetaInformatieZoekCriterium metaInformatieZoekCriterium, final List<GroepElement> toegevoegdeGroepen,
                                               final StringBuilder sqlWhereBuilder, final Map<MetaInformatieZoekCriterium, JoinInformatie> joinInformatieMap) {
        //alleen relevant als actueel en niet leeg (voor de reguliere vragen: niet not exists). Leeg en bestaat niet is functioneel hetzelfde
        //maar voor de actuele not exists is indag true relevant
        if (metaInformatieZoekCriterium.isBekijkHistorisch() || (zoekCriteria.getZoekCriteria().contains(metaInformatieZoekCriterium.getZoekCriterium())
                && metaInformatieZoekCriterium.getZoekCriterium().getZoekOptie() == Zoekoptie.LEEG)) {
            return;
        }
        final String alias = getAlias(metaInformatieZoekCriterium, joinInformatieMap);
        final GroepElement groepElement = metaInformatieZoekCriterium.getAttribuutElement().getGroep();
        if (toegevoegdeGroepen.contains(groepElement)) {
            return;
        }
        toegevoegdeGroepen.add(groepElement);
        if (!zoekCriteria.isHistorischeZoekvraag() && groepElement.isIdentiteitGroep()
                && metaInformatieZoekCriterium.getAttribuutElement().getGroep().getHistoriePatroon() == HistoriePatroon.G) {
            appendIndagConditieAlleGroepenInObject(metaInformatieZoekCriterium, alias, sqlWhereBuilder);
        } else {
            final Integer actueelEnGeldigAttr = groepElement.getElement().getElementWaarde().getActueelGeldigAttribuut();
            if (actueelEnGeldigAttr != null) {
                final Element actueelEnGeldigElement = Element.parseId(actueelEnGeldigAttr);
                final String actueelEnGeldigKolom = actueelEnGeldigElement.getElementWaarde().getIdentdb();
                sqlWhereBuilder.append(String.format("and %s.%s = true %n", alias, actueelEnGeldigKolom));
            }
        }
    }

    /**
     * Append een conditie met daarbinnen indag condities alle groepen in het gegeven object.
     * Dit is relevant voor bevraging op attributen binnen identiteitsgroepen zonder historie.
     *
     * resultaat:
     * AND (
     * alias.indagGroepX = TRUE
     * OR
     * alias.indagGroepY = TRUE
     * OR
     * etc.
     * )
     */
    private void appendIndagConditieAlleGroepenInObject(MetaInformatieZoekCriterium metaInformatieZoekCriterium, String alias,
                                                        final StringBuilder sqlWhereBuilder) {
        final List<String> indagList = Lists.newLinkedList();
        //dit levert nu mogelijk identieke clauses met de toevoegingen voor niet identiteit
        for (GroepElement groepElement : metaInformatieZoekCriterium.getAttribuutElement().getGroep().getObjectElement().getGroepen()) {
            final Element groep = groepElement.getElement();
            final Integer actueelEnGeldigAttr = groep.getElementWaarde().getActueelGeldigAttribuut();
            if (actueelEnGeldigAttr != null) {
                final Element actueelEnGeldigElement = Element.parseId(actueelEnGeldigAttr);
                final String actueelEnGeldigKolom = actueelEnGeldigElement.getElementWaarde().getIdentdb();
                indagList.add(String.format("%s.%s = true %n", alias, actueelEnGeldigKolom));
            }
        }
        if (!indagList.isEmpty()) {
            sqlWhereBuilder.append(AND_CLAUSE);
            sqlWhereBuilder.append(OPEN);
            sqlWhereBuilder.append(StringUtils.join(indagList, " or "));
            sqlWhereBuilder.append(SLUIT);
        }
    }

    private Predicate<Tabel> metTabelNaam(final String tabelNaam) {
        return tabel -> tabel.getNaam().equals(tabelNaam);
    }

    private String bepaalAlias(final Map<String, String> aliasMap, final String tabel) {
        return aliasMap.computeIfAbsent(tabel, t -> maakAlias(tabel));
    }

    private String maakAlias(final String tabel) {
        return (tabel.substring(0, 1) + geefAliasIndex()).toLowerCase();
    }

    private void startWhereClause(final StringBuilder sqlWhereBuilder) {
        if (sqlWhereBuilder.length() == 0) {
            sqlWhereBuilder.append(String.format("%nwhere %n"));
        } else {
            sqlWhereBuilder.append(AND_CLAUSE);
        }
    }

    private static class Statement {
        final Map<Integer, JoinInformatie> gerelateerdeBetrokkenheidJoins = Maps.newHashMap();
        final Map<Integer, JoinInformatie> relatieJoins = Maps.newHashMap();
        final Map<Integer, JoinInformatie> betrokkenheidJoins = Maps.newHashMap();
        final StringBuilder sqlBuilder = new StringBuilder();
        final JoinInformatie basisJoin;
        //alle parameters waardes op volgorde zoals de ? placeholders zijn gedefinieerd in sql string
        final List<Object> parameters = new ArrayList<>();
        final StringBuilder sqlFromBuilder = new StringBuilder();
        final StringBuilder sqlWhereBuilder = new StringBuilder();

        Statement(final String persAlias) {
            basisJoin = maakBasisJoin(persAlias);
        }

        private JoinInformatie maakBasisJoin(final String persAlias) {
            final JoinInformatie joinInformatie = new JoinInformatie(persAlias);
            joinInformatie.getAliasMap().put(PERS_TABEL, persAlias);
            joinInformatie.getToegevoegdeTabellen().add(new Tabel(PERS_TABEL, persAlias, HistoriePatroon.G));
            return joinInformatie;
        }
    }
}
