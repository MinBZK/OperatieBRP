package scripts.teststeps

import nl.bzk.brp.soapui.excel.TestStatus
import nl.bzk.brp.soapui.steps.ControlValues
import nl.bzk.brp.soapui.steps.TestRequest

/**
 * Leest de control toestand uit van de de 'Control Values' properties en
 * schakelt stappen uit/aan op basis van de waardes.
 */

ControlValues step_CONTROL_VALUES = ControlValues.fromContext(context)

boolean SEND_REQUEST = step_CONTROL_VALUES.isSendRequest()
boolean ASSERT_REQUEST = step_CONTROL_VALUES.getSoapResponseQuery()
boolean ASSERT_DB = step_CONTROL_VALUES.getDbQuery()
boolean PREPARE_DATA = step_CONTROL_VALUES.getPrepareData()
boolean PRE_QUERY = step_CONTROL_VALUES.getPreQuery()
boolean POST_QUERY = step_CONTROL_VALUES.getPostQuery()
boolean OVERSCHRIJF_VARIABELEN = step_CONTROL_VALUES.heeftOverschrijfVariabelen();

// Stappen blok configuratie
def stepConfig = [
    [
        enabled: PREPARE_DATA,
        steps: [ 'Prepare Database Data' ]
    ],
    [
        enabled: PRE_QUERY,
        steps: [ 'Pre Query' ]
    ],
    [
        enabled: SEND_REQUEST,
        steps: [ 'Read Excel and Write DataSource Values', 'Build Request', 'Test Request' ]
    ],
    [
        enabled: ASSERT_REQUEST,
        steps: [ 'Write Request and Response to File', 'Assert SOAP Request' ]
    ],
    [
        enabled: false,
        steps: [ 'Delay 1', 'Delay 2', 'Delay 3' ]
    ],
    [
        enabled: ASSERT_DB,
        steps: [ 'Build JDBC Request', 'Delay 4', 'JDBC Request', 'Assert JDBC Request', 'JDBC retry on assert', 'JDBC retries', 'Write SQL Request and SQL Response to File']
    ],
    [
        enabled: POST_QUERY,
        steps: [ 'Post Query' ]
    ],
    [
        enabled: OVERSCHRIJF_VARIABELEN,
        steps: [ 'Overschrijf Variabelen' ]
    ]
]

// Uitvoeren van configuratie
String STATUS = step_CONTROL_VALUES.getStatus()
boolean DISABLE_ALL = STATUS in TestStatus.nooitUitvoeren()
boolean SCHEMA_COMPLIANCE_CHECK = !(STATUS in TestStatus.geenXsdCheck())

int delay = step_CONTROL_VALUES.getDbQueryDelay()
testRunner.testCase.testSteps['Delay 4'].setDelay(delay)

stepConfig.each { conf ->
    conf.steps.each { step ->
        testRunner.testCase.testSteps[step].setDisabled(!conf.enabled || DISABLE_ALL)
    }
}

TestRequest step_TEST_REQUEST = TestRequest.fromContext(context)
step_TEST_REQUEST.setSchemaValidation(SCHEMA_COMPLIANCE_CHECK)
