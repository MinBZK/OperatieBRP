package nl.bzk.brp.funqmachine.verstuurder

import static java.util.concurrent.TimeUnit.MILLISECONDS

import com.google.common.base.Predicate
import groovy.sql.Sql
import javax.annotation.Nullable
import javax.jms.Session
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.configuratie.Environment
import nl.bzk.brp.funqmachine.processors.SqlProcessor
import nl.bzk.brp.funqmachine.wachttijd.SqlWait
import org.apache.activemq.ActiveMQConnectionFactory
import org.threeten.bp.Duration

/**
 * Kan JMS berichten versturen.
 *
 * @see https://gist.github.com/welshstew/45aa6f42f847fce25593
 * @see http://prystash.blogspot.nl/2011/02/spring-jms-groovy-sending-and-receiving.html
 */
class JmsVerstuurder {
    def sql = new SqlProcessor(Database.KERN).sql

    /**
     * Plaatst de handelingID op de queue en wacht totdat deze is gemarkeerd als verwerkt.
     *
     * @param admhndId de handelingID om te publiceren
     */
    void plaatsHandelingOpQueueEnWachtTotVerwerkt(def admhndId, Duration timeout) {
        def wait = new SqlWait(sql, timeout.seconds)

        new ActiveMQConnectionFactory(brokerURL: Environment.instance().getBrokerURL()).createConnection().with {
            start()
            createSession(false, Session.AUTO_ACKNOWLEDGE).with {
                def message = createTextMessage(/{"administratieveHandelingId":$admhndId}/)
                createProducer(createQueue(Environment.instance().getAdmhQueue())).send(message)
            }
            close()
        }

        wait.pollingEvery(300, MILLISECONDS).until(new Predicate<Sql>() {
            @Override
            boolean apply(@Nullable final Sql sql) {
                sql.rows("SELECT id FROM kern.admhnd WHERE id = ${admhndId} AND tslev IS NOT NULL;")
            }
        })
    }
}
