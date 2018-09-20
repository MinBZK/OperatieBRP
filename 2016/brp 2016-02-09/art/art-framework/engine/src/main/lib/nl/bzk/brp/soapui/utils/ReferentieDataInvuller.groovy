package nl.bzk.brp.soapui.utils

import groovy.sql.Sql
import nl.bzk.brp.soapui.handlers.SqlHandler

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Deze klasse vervangt referentiedata in de queries en/of xml requests.
 */
class ReferentieDataInvuller {

    static final Pattern PERSOON_ID_OP_BSN = Pattern.compile('persoon_id_voor_bsn\\((.+?)\\)')

    static final String PERSOON_ID_SQL = "SELECT id FROM kern.pers WHERE bsn = "

    /**
     * Methode die bsn's vervangt voor persoon-id's.
     * @param context De context.
     * @param originieleString De originele string.
     * @return De originele string, met vervangen bsn's voor persoon-id's.
     */
    public static String vervangReferentieData(def context, String originieleString) {
        Matcher m = PERSOON_ID_OP_BSN.matcher(originieleString);

        StringBuffer resultaat = new StringBuffer(originieleString.length());
        Sql sql = null
        try {
            sql = SqlHandler.makeSql(context)
            while (m.find()) {
                String bsnVariabeleString = m.group(1);
                def bsn = context.expand(bsnVariabeleString)
                if (bsn == null) {
                    throw new IllegalStateException(
                            "[Referentiedata] Geen BSN gevonden voor variabele $bsnVariabeleString")
                }

                def queryResult = sql.firstRow(PERSOON_ID_SQL + bsn)
                if (queryResult == null) {
                    throw new IllegalStateException("[Referentiedata] Geen persoon gevonden met BSN $bsn")
                }
                def persoonId = queryResult[0]

                m.appendReplacement(resultaat, persoonId as String);
            }
            m.appendTail(resultaat);
        } finally {
            sql?.close()
        }
        return resultaat.toString()
    }

}
