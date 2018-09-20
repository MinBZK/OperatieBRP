package scripts.teststeps

import nl.bzk.brp.soapui.steps.AssertionResults
import nl.bzk.brp.soapui.steps.JdbcRetries

/**
 * De eerste stap in de loop voor een testregel.
 * Reset waardes in verschillende property-stappen van SoapUI.
 */

AssertionResults.fromContext(context).clearProperties()
JdbcRetries.fromContext(context).setRetries(3)
