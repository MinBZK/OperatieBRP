package nl.bzk.brp.funqmachine.processors

import nl.bzk.brp.funqmachine.configuratie.Environment
import org.apache.http.client.utils.DateUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
/**
 * HttpProcessor voor het uitvoeren van http verzoeken.
 */
class HttpProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpProcessor.class)

    private static String hostUrl = Environment.instance().hostURL

    public static Date getApplicationServerTime() {
        try {
            // Eerst een sleep om het ms issue met afnemerindicaties te compenseren
            Thread.sleep(1500)

            URL obj = new URL(hostUrl);
            URLConnection conn = obj.openConnection();
            LOGGER.info("Opened connection to {}", hostUrl)

            conn.connect();
            Map<String, List<String>> headerFields = conn.getHeaderFields();

            /*headerFields.each {
                System.out.println(String.format("Header %s with value %s", it.getKey(), it.getValue()))
            }*/

            String date = headerFields.get("Date").get(0);
            Date applicationServerTime = DateUtils.parseDate(date)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(applicationServerTime);
            calendar.set(Calendar.MILLISECOND, Calendar.getInstance().get(Calendar.MILLISECOND));
            LOGGER.debug("Applicatie Server Tijd verkregen via {}: {}", hostUrl, calendar.getTime())

            return calendar.getTime()
        } catch (Exception e) {
            LOGGER.error("Fout opgetreden bij ophalen applicatie server tijd.", e)
        }
        return null
    }
}
