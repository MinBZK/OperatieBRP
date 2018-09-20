package scripts.teststeps

import com.eviware.soapui.impl.wsdl.teststeps.JdbcRequestTestStep
import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.handlers.SqlHandler
import nl.bzk.brp.soapui.steps.AssertionResults
import nl.bzk.brp.soapui.steps.ControlValues
import nl.bzk.brp.soapui.utils.ReferentieDataInvuller

/**
 * Bereid de stap "JDBC Request" voor. De waarde van de SQL statements wordt gezet en
 * de connection string naar de correcte database.
 */

AssertionResults step_ASSERTION_RESULTS = AssertionResults.fromContext(context)
ControlValues step_CONTROL_VALUES = ControlValues.fromContext(context)
String DB_CHECK = step_CONTROL_VALUES.getDbQuery()

String STATUS = step_CONTROL_VALUES.getStatus()
String QUARANTAINE = 'QUARANTAINE'


JdbcRequestTestStep jdbcRequestTestStep = testRunner.testCase.getTestStepByName('JDBC Request')

try {
    def sqlText = SqlHandler.leesSqlStatements(DB_CHECK, context, step_CONTROL_VALUES)

    sqlText = ReferentieDataInvuller.vervangReferentieData(context, sqlText)

    jdbcRequestTestStep.connectionString = SqlHandler.buildConnectionString(context, sqlText)

    if (sqlText.contains('MQ::')) {
        sqlText = sqlText.replace('MQ::', '')
    }
    jdbcRequestTestStep.query = sqlText
} catch (Exception e) {
    if (STATUS == QUARANTAINE) {
        step_ASSERTION_RESULTS.update('[Build JDBC Request]', QUARANTAINE, step_CONTROL_VALUES, e)
    } else {
        step_ASSERTION_RESULTS.update('[Build JDBC Request]', 'FAILED', step_CONTROL_VALUES, e)
        throw new Exception(e)
    }
}
