package scripts.project

import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep

/**
 * Verwijder alle property values van Project, TestSuite, TestCase en Properties stappen.
 */
project.testSuiteList.each { suite ->
    suite.testCaseList.each { testcase ->
        testcase.testStepList.each { step ->
            if (step instanceof  WsdlPropertiesTestStep) {
                clearProperties(step)
            }
        }
        clearProperties(testcase)
    }
    clearProperties(suite)
}
clearProperties(project)

/*
 * Zorgt ervoor dat de init step niet uit staat
 */
project.testSuites['ARTengine'].testCases['Engine'].getTestStepByName('Init Counters').setDisabled(false)

// -- Helper methods ---------------------------------------------------------
private void clearProperties(modelItem) {
    modelItem.propertyNames.each { name ->
        modelItem.removeProperty name
    }
}
