database {
    ['kern', 'ber', 'prot'].each {
        this."${it}".url = 'jdbc:postgresql://localhost/art-brp'
        this."${it}".username = 'brp'
        this."${it}".password = 'brp'
        this."${it}".driverClassName = 'org.postgresql.Driver'
    }
}

queues {
    broker = 'tcp://localhost:61616?jms.useAsyncSend=true'
    admhnd = 'AdministratieveHandelingen'
}

environments {
    localhost {
        applications {
            host = 'http://localhost:8080'
        }
        jmx {
            url = 'service:jmx:rmi://localhost/jndi/rmi://localhost:1099/jmxrmi'
        }
    }
    oap01 {
        database {
            ['kern', 'ber', 'prot'].each {
                this."${it}".url = 'jdbc:postgresql://odb01.modernodam.nl/oap01-brp'
            }
        }
        applications {
            host = 'http://oap01.modernodam.nl:8080'
        }
        queues {
            broker = 'tcp://oap01.modernodam.nl:61616?jms.useAsyncSend=true'
        }
        jmx {
            url = 'service:jmx:rmi://oap01.modernodam.nl/jndi/rmi://oap01.modernodam.nl:1099/jmxrmi'
        }
    }
    oap02 {
        database {
            ['kern', 'ber', 'prot'].each {
                this."${it}".url = 'jdbc:postgresql://odb01.modernodam.nl/oap02-brp'
            }
        }
        applications {
            host = 'http://oap02.modernodam.nl:8080'
        }
        queues {
            broker = 'tcp://oap02.modernodam.nl:61616?jms.useAsyncSend=true'
        }
        jmx {
            url = 'service:jmx:rmi://oap02.modernodam.nl/jndi/rmi://oap02.modernodam.nl:1099/jmxrmi'
        }
    }
    oap10 {
        database {
            ['kern', 'ber', 'prot'].each {
                this."${it}".url = 'jdbc:postgresql://odb15.modernodam.nl/brp'
            }
        }
        applications {
            host = 'http://oap10.modernodam.nl:8080'
        }
        queues {
            broker = 'tcp://oap10.modernodam.nl:61616?jms.useAsyncSend=true'
        }
        jmx {
            url = 'service:jmx:rmi://oap10.modernodam.nl/jndi/rmi://oap10.modernodam.nl:1099/jmxrmi'
        }
    }
    oap11 {
        database {
            ['kern', 'ber', 'prot'].each {
                this."${it}".url = 'jdbc:postgresql://odb16.modernodam.nl/oap11-brp'
            }
        }
        applications {
            host = 'http://oap11.modernodam.nl:8080'
        }
        queues {
            broker = 'tcp://oap11.modernodam.nl:61616?jms.useAsyncSend=true'
        }
        jmx {
            url = 'service:jmx:rmi://oap11.modernodam.nl/jndi/rmi://oap11.modernodam.nl:1099/jmxrmi'
        }
    }
    oap12 {
        database {
            ['kern', 'ber', 'prot'].each {
                this."${it}".url = 'jdbc:postgresql://odb16.modernodam.nl/oap12-brp'
            }
        }
        applications {
            host = 'http://oap12.modernodam.nl:8080'
        }
        queues {
            broker = 'tcp://oap12.modernodam.nl:61616?jms.useAsyncSend=true'
        }
        jmx {
            url = 'service:jmx:rmi://oap12.modernodam.nl/jndi/rmi://oap12.modernodam.nl:1099/jmxrmi'
        }
    }
    oap13 {
        database {
            ['kern', 'ber', 'prot'].each {
                this."${it}".url = 'jdbc:postgresql://odb16.modernodam.nl/oap13-brp'
            }
        }
        applications {
            host = 'http://oap13.modernodam.nl:8080'
        }
        queues {
            broker = 'tcp://oap13.modernodam.nl:61616?jms.useAsyncSend=true'
        }
        jmx {
            url = 'service:jmx:rmi://oap13.modernodam.nl/jndi/rmi://oap13.modernodam.nl:1099/jmxrmi'
        }
    }
    oap14 {
        database {
            ['kern', 'ber', 'prot'].each {
                this."${it}".url = 'jdbc:postgresql://odb16.modernodam.nl/oap14-brp'
            }
        }
        applications {
            host = 'http://oap14.modernodam.nl:8080'
        }
        queues {
            broker = 'tcp://oap14.modernodam.nl:61616?jms.useAsyncSend=true'
        }
        jmx {
            url = 'service:jmx:rmi://oap14.modernodam.nl/jndi/rmi://oap14.modernodam.nl:1099/jmxrmi'
        }
    }
    oap19 {
        database {
            ['kern', 'ber', 'prot'].each {
                this."${it}".url = 'jdbc:postgresql://odb17.modernodam.nl/oap19-brp'
            }
        }
        applications {
            host = 'http://oap19.modernodam.nl:8080'
        }
        queues {
            broker = 'tcp://oap19.modernodam.nl:61616?jms.useAsyncSend=true'
        }
        jmx {
            url = 'service:jmx:rmi://oap19.modernodam.nl/jndi/rmi://oap19.modernodam.nl:1099/jmxrmi'
        }
    }
}
