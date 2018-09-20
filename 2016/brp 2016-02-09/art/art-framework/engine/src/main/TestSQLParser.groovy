@Grab(group = 'com.github.jsqlparser', module = 'jsqlparser', version = '0.9')

import net.sf.jsqlparser.statement.Statement
import net.sf.jsqlparser.statement.Statements

import net.sf.jsqlparser.parser.CCJSqlParserUtil

def sqlQuery = """
insert into kern.pers (select * from kern.pers where bsn = ?);
select * from kern.pers;
"""

def update = 'update kern.dbobject his_db set  indonderdeelpl = a_db.indonderdeelpl,  indonderdeelplouder = a_db.indonderdeelplouder,  indonderdeelplpartner = a_db.indonderdeelplpartner,  indonderdeelplkind = a_db.indonderdeelplkind,  indverantwoordingpl = a_db.indverantwoordingpl  from kern.dbobject a_db, kern.element groep  where  his_db.srt = 2 and  his_db.ouder = groep.id and  groep.srt = 2 and  a_db.naam = his_db.naam and  a_db.ouder = groep.ouder; '

def upsert = /insert into kern.Partij (id, code, naam, srt, dataanv, dateinde, indverstrbeperkingmogelijk)
select (select 1 + max(teller) from kern.tempTeller),410601, 'Intergemeentelijke Sociale Dienst OptimISD',4, 20070601, 0, false
where not exists (
    select id from kern.Partij where naam ='Intergemeentelijke Sociale Dienst OptimISD'
);
insert into kern.tempTeller(select max(id) from kern.partij);/

try {
    Statements stmt = CCJSqlParserUtil.parseStatements(upsert)

    println stmt

    def selects = stmt.statements.any { Statement st ->
        st.class.simpleName == 'Select'
    }

    println selects

} catch (e) {
    println e
}
