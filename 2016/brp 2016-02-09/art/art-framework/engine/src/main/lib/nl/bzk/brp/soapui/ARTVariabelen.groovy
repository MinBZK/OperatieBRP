package nl.bzk.brp.soapui

/**
 * Wrapper om de waardes van properties die door SoapUI worden gebruikt.
 * Door deze abstractie hoeft in de code niet bekend te zijn waar de properties
 * door SoapUI worden beheerd.
 */
final class ARTVariabelen {

    public static final String ENV = 'ENV'
    public static final String RUNTIME = 'runtime'

    public static final String PROJECT_DIR = 'projectDir'

    /** meegegeven aan cmdline of GUI bij runnen test. */
    public static final String TEST_SCRIPT_LOCATION = 'test_script_location'
    /** berekend op basis van {@link #TEST_SCRIPT_LOCATION}. */
    public static final String TEST_SCRIPT_PATH = 'test_script_path'

    /** uit test.properties (per ART). */
    public static final String REQUEST_TEMPLATE_FILE = 'request_template_file'
    /** uit test.properties (per ART). */
    public static final String ART_INPUT_FILE = 'art_input_file'
    /** uit test.properties (per ART). */
    public static final String ART_INPUT_SHEET = 'art_input_sheet'
    /** uit test.properties (per ART). */
    public static final String ART_INPUT_DATA_SHEET = 'art_input_data_sheet'
    /** uit test.properties (per ART). */
    public static final String ACTUAL_DIR = 'actual_dir'

    public static final String DATASOURCE_URL = 'ds_url'
    public static final String MQ_DATASOURCE_URL = 'mq_url'

    public static final String DATASOURCE_USER = 'ds_user'
    public static final String DATASOURCE_PASSWORD = 'ds_password'
    public static final String JDBC_DRIVER = 'jdbcDriver'

    public static final String SOAP_ENDPOINT = 'soap_endpoint'
    public static final String SOAP_INTERFACE = 'soap_interface'
    public static final String SOAP_OPERATION = 'soap_operation'

    public static final String alt_test_script_path = 'alt_test_script_path'
    public static final String alt_art_input_file = 'alt_art_input_file'

    public static String makeTestCaseProperty(String key) {
        '${#TestCase#' + key + '}'
    }

    public static String makeProjectProperty(String key) {
        '${#Project#' + key + '}'
    }

    static String getRuntime(def context) { context.expand(makeProjectProperty(RUNTIME)) }
    static String getProjectDir(def context) { context.expand(makeProjectProperty(PROJECT_DIR)) }
    static String getScriptLocation(def context) { context.expand(makeProjectProperty(TEST_SCRIPT_LOCATION)) }

    static String getActualDir(def context) { context.expand(makeTestCaseProperty(ACTUAL_DIR)) }
    static String getScriptPath(def context) { context.expand(makeTestCaseProperty(TEST_SCRIPT_PATH)) }
    static String getInputFile(def context) { context.expand(makeTestCaseProperty(ART_INPUT_FILE)) }
    static String getInputSheetNaam(def context) { context.expand(makeTestCaseProperty(ART_INPUT_SHEET)) }

    /**
     * Geeft de namen van de Datasheets zoals geconfigureerd.
     * @param context context om de waardes uit te halen
     * @return de lijst met namen
     */
    static List<String> getInputDataSheetNamen(def context) {
        def namen = context.expand(makeTestCaseProperty(ART_INPUT_DATA_SHEET))
        namen.tokenize(',').collect { it.trim() }
    }

    static String getRequestTemplateFile(def context) { context.expand(makeTestCaseProperty(REQUEST_TEMPLATE_FILE)) }
    static String getAlternativeScriptPath(def context) { context.expand(makeTestCaseProperty(alt_test_script_path)) }
    static String getAlternativeInputFile(def context) { context.expand(makeTestCaseProperty(alt_art_input_file)) }

    static String getSoapInterface(def context) { context.expand(makeTestCaseProperty(SOAP_INTERFACE)) }
    static String getSoapOperation(def context) { context.expand(makeTestCaseProperty(SOAP_OPERATION)) }
    static String getSoapEndpoint(def context) { context.expand(makeTestCaseProperty(SOAP_ENDPOINT)) }

    static String getDbUser(def context) { context.expand(makeProjectProperty(DATASOURCE_USER)) }
    static String getDbPassword(def context) { context.expand(makeProjectProperty(DATASOURCE_PASSWORD)) }
    static String getDbUrl(def context) { context.expand(makeProjectProperty(DATASOURCE_URL)) }
    static String getMqUrl(def context) { context.expand(makeProjectProperty(MQ_DATASOURCE_URL)) }

    static String getDbDriver(def context) { context.expand(makeProjectProperty(JDBC_DRIVER)) }
}
