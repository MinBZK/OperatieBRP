package assembly

database {
    ['kern', 'ber', 'lev'].each {
        this."${it}".url = 'jdbc:postgresql://localhost/brp'
        this."${it}".username = 'brp'
        this."${it}".password = 'brp'
        this."${it}".driverClassName = 'org.postgresql.Driver'
    }
}

environments {
    localhost {
        applications {
            host = 'http://localhost:8080'
        }
        jmx {
            url = 'service:jmx:rmi://localhost/jndi/rmi://localhost:1099/jmxrmi'
        }
        queues {
            broker = 'tcp://localhost:61616?jms.useAsyncSend=true'
            admhnd = 'AdministratieveHandelingen'
        }
    }
}
