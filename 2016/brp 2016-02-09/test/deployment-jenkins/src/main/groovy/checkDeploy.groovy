@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.6')
import groovy.util.slurpersupport.GPathResult
import groovyx.net.http.HTTPBuilder

import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory

import java.security.KeyStore

import static groovyx.net.http.ContentType.HTML
import static groovyx.net.http.Method.GET

// local properties
def keyStoreName = 'pt-links-mGBA_client.jks'
def keyStorePassword = 'changedit'.toCharArray()

// execution
def http = createBuilder(keyStoreName, keyStorePassword)

def environment = [
    'http://oap01.modernodam.nl:8080': ['/bijhouding/version.html', '/bevraging/version.html'],
    'http://oap02.modernodam.nl:8080': ['/bijhouding/version.html', '/bevraging/version.html'],
    'http://oap10.modernodam.nl:8080': ['/bijhouding/version.html', '/bevraging/version.html', '/mutatielevering/versie.html', '/brp-verzending/versie.html'],
    'http://oap11.modernodam.nl:8080': ['/bijhouding/version.html','/bevraging/version.html', '/mutatielevering/versie.html', '/brp-verzending/versie.html'],
    'http://oap12.modernodam.nl:8080': ['/bijhouding/version.html', '/bevraging/version.html','/mutatielevering/versie.html', '/brp-verzending/versie.html'],
    'http://oap13.modernodam.nl:8080': ['/bijhouding/version.html', '/bevraging/version.html','/mutatielevering/versie.html', '/brp-verzending/versie.html'],
    'http://oap14.modernodam.nl:8080': ['/bijhouding/version.html', '/bevraging/version.html','/mutatielevering/versie.html', '/brp-verzending/versie.html'],
    'https://brp-proeftuin-links.modernodam.nl': ['/bijhouding/version.html','/bevraging/version.html'],
    'http://plap01.modernodam.nl:8080': ['/bijhouding/version.html','/bevraging/version.html'],
    'http://prap01.modernodam.nl:8080': ['/bijhouding/version.html','/bevraging/version.html']
//    'http://plap02.modernodam.nl:8080': ['/mutatielevering/versie.html','/brp-verzending/versie.html']
]

environment.each { host, apps ->
    println "\n${host}\n------------------------------------------------------------------"
    apps.each { app ->
        try {


            http.request(host + app, GET, HTML) {
                response.success = { resp, html ->
                    printResponse(html)
                }

                response.failure = { resp ->

                }
            }
        } catch (Exception e) {
            println("${app} : ${e.message}")
        }

    }
    println('------------------------------------------------------------------')
}

// helpers
def createBuilder(String store, char[] password) {
    def http = new HTTPBuilder()
    def keyStore = KeyStore.getInstance( KeyStore.defaultType )

        getClass().getResource( store ).withInputStream {
        keyStore.load(it, password)
    }
    SSLSocketFactory sf = new SSLSocketFactory(keyStore)
    sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
    http.client.connectionManager.schemeRegistry.register(
        new Scheme('https', sf, 443)
    )

    return http
}

def printResponse(GPathResult html) {
    def app = html.BODY.H1.text()
    def rows = html.BODY.TABLE.TBODY.TR

    println("${rows[2].TD[1].text()} : ${rows[0].TD[1].text()} | ${app}")
}
