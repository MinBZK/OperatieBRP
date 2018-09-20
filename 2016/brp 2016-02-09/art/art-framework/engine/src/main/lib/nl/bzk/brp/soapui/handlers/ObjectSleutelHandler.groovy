package nl.bzk.brp.soapui.handlers

import groovy.sql.Sql
import org.apache.commons.codec.binary.Base64
import org.apache.commons.lang.StringUtils
import org.apache.log4j.Logger

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import java.util.regex.Pattern

import org.w3c.dom.Attr
import org.w3c.dom.Node
import org.w3c.dom.NodeList

import java.text.SimpleDateFormat
import java.text.ParsePosition

import nl.bzk.brp.soapui.utils.DateSyntaxTranslatorUtil
import nl.bzk.brp.soapui.steps.ControlValues

/**
 * Handler, die verantwoordelijk is voor het aanmaken van object sleutels.
 * LET OP: De encryptie code is zoveel mogelijk een kopie van de service uit de Java code!
 * (met zoveel mogelijk onnodige delen gestript)
 * Als hier changes nodig zijn, eerst in de Java code doorvoeren en dan pas hier overnemen!
 * NB: Er zijn dus ook geen tests voor deze code, die zitten al aan de Java kant.
 */
class ObjectSleutelHandler {

    private static final String CIPHER_ALGORITME = "DES/ECB/PKCS5Padding";
    private static final String ENCRYPT_DECRYPT_OBJECTSLEUTEL_PASSWD = "BRPTango";
    private SecretKeySpec secretKeySpec = new SecretKeySpec(ENCRYPT_DECRYPT_OBJECTSLEUTEL_PASSWD.getBytes("UTF-8"), "DES");
    
    public String genereerObjectSleutelString(final int persoonId, final int partijCode, long tijdstipVanUitgifte) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeInt(persoonId);
        oos.writeLong(tijdstipVanUitgifte);
        oos.writeInt(partijCode);
        oos.close();
        return encrypt(bos.toByteArray());
    }
    
    private String encrypt(final byte[] objectSleutelBytes) {
        final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITME);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] rawBytes = cipher.doFinal(objectSleutelBytes);
        return new String(Base64.encodeBase64(rawBytes, false), "UTF-8");
    }

    /**
     * Zoek alle objectSleutel attributen met een BSN erin, en vervang die door
     * een object sleutel, opgebouwd uit een encryptie van de persoon id, de partij code
     * en een tijdstip. Zie commentaar in methode voor de details.
     */
    static String vervangBsnsDoorObjectSleutels(def context, File requestTemplate, Logger log) {
    	String templateText = requestTemplate.text
    	/******************************************************
    	 * Stap 1: Zoek de partij uit de input values.
    	 *****************************************************/
        def partijWaarde;
        // De partij kan overruled worden door een speciale variabele in de input sheet te vullen.
        // Dit, zodat in de technische ART getest kan worden op verkeerde partijen in sleutels.
        def overrulePartijVariabele = '${DataSource Values#overruleObjectSleutelPartij}'
        // Haal de eventueel ingevulde waarde op.
        def overrulePartijWaarde = context.expand overrulePartijVariabele
        if (!StringUtils.isBlank(overrulePartijWaarde)) {
            log.info "[Object Sleutel] Overrule van partij gedetecteerd: " + overrulePartijWaarde
            if (StringUtils.isNumeric(overrulePartijWaarde)) {
                partijWaarde = overrulePartijWaarde
            } else {
                log.info "[Object Sleutel] Overrule van partij mislukt, geen geldige waarde: " + overrulePartijWaarde
            }
        } else {
        	// Geen overrule, we zoeken de variabele voor zendendePartij en gebruiken die als partij voor de sleutel.
        	def partijMatcher = templateText =~ /zendendePartij>(.*)</
        	if (partijMatcher.getCount() == 0) {
        	    throw new IllegalStateException("[Object Sleutel] Geen partij variabele gevonden in template")
        	}
        	def partijVariabele = partijMatcher[0][1];
        	// Haal de ingevulde waarde voor dit testgeval uit de context.
        	partijWaarde = context.expand(partijVariabele)
            if (StringUtils.isBlank(partijWaarde) || !StringUtils.isNumeric(partijWaarde)) {
                throw new IllegalStateException("[Object Sleutel] Ongeldige waarde gevonden voor partij: " + partijWaarde)
            }
        }
        
    	/******************************************************
    	 * Stap 2: Kies een tijdstip van uitgifte.
    	 *****************************************************/
    	// De default tijd van uitgifte is 12 uur geleden, zodat een mismatch tussen de tijd op het
    	// uitvoerende systeem en de ontvanger zo min mogelijk problemen geeft.
    	def tijdstipVanUitgifte = new Date().getTime() - (12 * 60 * 60 * 1000)
        // Het tijdstip kan overruled worden door een speciale variabele in de input sheet te vullen.
        // Dit, zodat in de technische ART getest kan worden op het verlopen van sleutels.
        def overruleTijdstipVariabele = '${DataSource Values#overruleObjectSleutelTijdstip}'
        // Haal de eventueel ingevulde waarde op.
        def overruleTijdstipWaarde = context.expand overruleTijdstipVariabele
        if (!StringUtils.isBlank(overruleTijdstipWaarde)) {
            log.info "[Object Sleutel] Overrule van tijdstip gedetecteerd: " + overruleTijdstipWaarde
            String currentTimestamp = ControlValues.fromContext(context).getExecutionTimestamp()
            // Vervang de ingevulde waarde door de dynamische interpretatie van moment(...).
            def tijdreisString = DateSyntaxTranslatorUtil.parseString(currentTimestamp, overruleTijdstipWaarde)
            log.info "[Object Sleutel] Overrule van tijdstip nieuwe tijd: " + tijdreisString
            // Parse het resultaat als date nav het patroon dat gebruikt wordt voor moment(...).
            SimpleDateFormat dateFormat = new SimpleDateFormat('yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'')
            def datumTijd = dateFormat.parse(tijdreisString, new ParsePosition(0))
            if (datumTijd != null) {
                tijdstipVanUitgifte = datumTijd.getTime()
            } else {
                log.info "[Object Sleutel] Overrule van tijdstip mislukt, geen geldige waarde: " + overruleTijdstipWaarde + " -> " + tijdreisString
            }
        }
    
    	/*********************************************************************
    	 * Stap 3: Zoek en vervang de object sleutels die van toepassing zijn.
    	 ********************************************************************/
    	def sleutelHandler = new ObjectSleutelHandler()
    
    	// XPath en DOM boiler plate code.
    	def factory = DocumentBuilderFactory.newInstance();
    	def builder = factory.newDocumentBuilder();
    	def doc = builder.parse(requestTemplate);
    	def xPathfactory = XPathFactory.newInstance();
    	def xpath = xPathfactory.newXPath();
    
    	// Selecteer met XPath alle 'objectSleutel' attributen binnen een 'persoon' tag (bijhouding)
        // en alle 'objectSleutel' tags (bevraging).
    	// De rare local-name constructie is om namespace issues te vermijden.
        def alleAttributen = new ArrayList<Node>();
    	def bijhoudingExpr = xpath.compile("//*[local-name()='persoon']/@*[local-name()='objectSleutel']");
    	def bijhoudingAttributen = bijhoudingExpr.evaluate(doc, XPathConstants.NODESET)
        voegNodesToe(alleAttributen, bijhoudingAttributen);
        def bevragingExpr = xpath.compile("//*[local-name()='objectSleutel']");
    	def bevragingAttributen = bevragingExpr.evaluate(doc, XPathConstants.NODESET);
    	voegNodesToe(alleAttributen, bevragingAttributen);

    	// Loop voor elk gevonden attribuut de sleutel logica door.
    	alleAttributen.each {
    	    // De waarde van het attribuut is de naam van de object sleutel variabele.
    	    def objectSleutelVariabele
            if (it instanceof Attr) {
                // Bij een attribuut halen we de waarde op met getValue().
                objectSleutelVariabele = it.getValue()
            } else {
                // Bij een element halen we de warde op met getTextContent().
                objectSleutelVariabele = it.getTextContent()
            }
    	    log.info "[Object Sleutel] Gevonden variabele: " + objectSleutelVariabele
    	    // Escape de speciale regex karakters in de variabele naam, want we willen deze later letterlijk matchen.
    	    def objectSleutelVariabelePattern = Pattern.quote(objectSleutelVariabele)
    	    // Haal de ingevulde waarde voor dit testgeval uit de context.
    	    def bsnWaarde = context.expand(objectSleutelVariabele)
    
    	    // Als er een waarde bekend is en die waarde 8 of 9 lang is en numeriek is en aan de 11-proef voldoet,
    	    // dan hebben we zeer waarschijnlijk te maken met een BSN die vervangen moet worden.
    	    // Soms is er namelijk een dummy waarde ingevuld, omdat dat deel van de template niet gebruikt wordt.
    	    // Tevens is dit een goede check op correcte BSN waardes.
    	    if (!StringUtils.isBlank(bsnWaarde)
    		    && (bsnWaarde.length() == 8 || bsnWaarde.length() == 9)
    		    && StringUtils.isNumeric(bsnWaarde)
    		    && voldoetAanBsnElfProef(bsnWaarde)) {
    		    log.info "[Object Sleutel] Gevonden BSN waarde: " + bsnWaarde
    		    def persoonIdSql = "SELECT id FROM kern.pers WHERE bsn = " + bsnWaarde
    		    Sql sql = null
    		    try {
    		        sql = SqlHandler.makeSql(context)
    		        def queryResult = sql.firstRow(persoonIdSql)
    		        if (queryResult == null) {
    			        throw new IllegalStateException("[Object Sleutel] Geen persoon gevonden met BSN " + bsnWaarde)
    		        }
    		        def persoonId = queryResult[0]
    		        // Nu hebben we alle benodigde parameters om een sleutel te laten maken.
    		        def sleutel = sleutelHandler.genereerObjectSleutelString(persoonId, partijWaarde.toInteger(), tijdstipVanUitgifte)
    		        // Vervang nu het voorkomen van de object sleutel variabele door de zojuist aangemaakte object sleutel.
    		        templateText = (templateText =~ objectSleutelVariabelePattern).replaceFirst(sleutel)
    		    } finally {
    		        sql?.close()
    		    }
    	    } else if (bsnWaarde.startsWith("db")) {
    		    // Een alternatief op een BSN waarde is een direct database id van een niet ingeschrevene, bijvoorbeeld bij
    		    // omzetting partnerschap in huwelijk. Dat geval kunnen we herkennen aan de prefix 'db'.
    		    int persoonId = bsnWaarde.substring(2).toInteger()
    		    // Nu hebben we alle benodigde parameters om een sleutel te laten maken.
    		    def sleutel = sleutelHandler.genereerObjectSleutelString(persoonId, partijWaarde.toInteger(), tijdstipVanUitgifte)
    		    // Vervang nu het voorkomen van de object sleutel variabele door de zojuist aangemaakte object sleutel.
    		    templateText = (templateText =~ objectSleutelVariabelePattern).replaceFirst(sleutel)
    	    } else {
    		    log.info "[Object Sleutel] Geen geldige BSN als object sleutel, dus ook geen versleuteling: " + bsnWaarde
    	    }
    	}
    
    	// Geef de nieuwe template text met alle vervangen sleutels erin terug.
    	return templateText
    }

    /*
     * Check of de bsn waarde string aan de BSN elf proef voldoet (die is net weer anders dan 'gewone' elf proef).
     */
    private static boolean voldoetAanBsnElfProef(String bsnWaarde) {
    	boolean voldoet = false
    	if (bsnWaarde.length() == 8) {
    	    bsnWaarde = "0" + bsnWaarde;
    	}
    	int som = 0
    	for (int i = 0; i < bsnWaarde.length() - 1; i++) {
    	    som += Character.getNumericValue(bsnWaarde.charAt(i)) * (9 - i)
    	}
    	som += Character.getNumericValue(bsnWaarde.charAt(bsnWaarde.length() - 1)) * -1
    	if (som % 11 == 0) {
    	    voldoet = true
    	}
    	return voldoet
    }
    
    /*
     * Voeg de nodes uit de NodeList toe aan de List<Node>
     */
    private static void voegNodesToe(List<Node> lijstMetNodes, NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            lijstMetNodes.add(nodeList.item(i))
        }
    }
}
