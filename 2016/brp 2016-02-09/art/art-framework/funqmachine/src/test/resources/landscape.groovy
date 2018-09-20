database {
    ['kern', 'ber', 'prot'].each {
        this."${it}".url = '${db.kern.url}'
        this."${it}".username = '${db.kern.username}'
        this."${it}".password = '${db.kern.password}'
        this."${it}".driverClassName = '${db.kern.driver}'
    }
}

environments {
    localhost {
        applications {
            host = 'http://localhost:8080'
        }
        queues {
            broker = 'tcp://localhost:61616?jms.useAsyncSend=true'
            admhnd = 'AdministratieveHandelingen'
        }
        jmx {
            url = 'service:jmx:rmi://localhost/jndi/rmi://localhost:1099/jmxrmi'
        }
    }
}
