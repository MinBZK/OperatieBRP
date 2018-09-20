import java.text.SimpleDateFormat;
import java.util.Calendar;

class DateSyntaxTranslatorUtil {	
	private static final String SEPARATOR_REGEXP = "[(]|[,]|[)]";

	//Support voor [vandaag()], [vandaag] en [vandaag(+12,0,-2)]
	private static final String VANDAAG = "\\[vandaag\\]";
	private static final String VANDAAG_WITH_ARG = "\\[vandaag\\(.?\\d+,.?\\d+,.?\\d+\\)\\]";
	private static final String MOMENT = "\\[moment\\]";
	private static final String MOMENT_WITH_ARG = "\\[moment\\(.?\\d+,.?\\d+,.?\\d+\\)\\]";
	
	private static def supportedRegExp = [
		VANDAAG,
		VANDAAG_WITH_ARG,
		MOMENT,
		MOMENT_WITH_ARG 
	]
	
	/**
	 * Parse gehele string en vervang syntax waar nodig is met een date waarde
	 */
	public static String parseString(String currentTimeStamp, String text) {
		text.replaceAll(supportedRegExp.join("|"), {
			translate(Long.parseLong(currentTimeStamp), it)
		})
	}	
	
	private static String translate(long currentTimeStamp, String syntax) {
		return convertDatumTijdreis(currentTimeStamp, syntax.replaceAll('\\[','').replaceAll('\\]',''));
	}


   /**
    * Berekent een nieuwe datum afhankelijk van de opgegen string, deze kan 0 of 3 parameters bevatten.
    * De code's moeten 'vandaag' of 'moment' zijn. In geval van 'vandaag' wordt slechts de datum teruggeven, anders datum en tijd.
    * bv. "vandaag, "vandaag()", "vandaag(0,0,0)" geeft het systeem datum terug.
    *     "vandaag (-2, +5, 0)" geeft de datum terug van vandaag 2 jaar geleden en 5 maanden later (maw. 12+7 = 19 maanden geleden)
    * De datum wordt terug gegeven in de formaat yyyyMMdd
    * De datum + tijd wordt terug gegeven in de formaat yyyyMMddHHmmssSS
    * @param waarde de formule
    * @return de berekende datum
    * @throws Exception conversie fouten.
    */
   private static String convertDatumTijdreis(long currentTimeStamp, final String waarde) throws Exception {
       String[] segments = waarde.split(SEPARATOR_REGEXP);
       if (segments.length == 0 || (segments.length > 1 && segments.length < 4)) {
           throw new Exception("[" + waarde + "] mist parameters, verwacht 3, gevonden " + (segments.length -1));
       }
       segments[0] = segments[0].toLowerCase().trim();
       if (!"vandaag".equals(segments[0]) && !"moment".equals(segments[0])) {
           throw new Exception("Code [" + waarde + "] wordt niet ondersteund, alleen 'vandaag' en 'moment'");
       }
       Calendar cal = Calendar.getInstance();
	   cal.setTimeInMillis(currentTimeStamp);
       if (segments.length > 1) {
           int year = Integer.parseInt(segments[1].replace('+',' ').trim());
           int month = Integer.parseInt(segments[2].replace('+', ' ').trim());
           int day = Integer.parseInt(segments[3].replace('+', ' ').trim());
           cal.add(Calendar.YEAR, year);
           cal.add(Calendar.MONTH, month);
           cal.add(Calendar.DATE, day);
       }
       SimpleDateFormat df = new SimpleDateFormat();

       df.setLenient(false);
       if ("vandaag".equals(segments[0])) {
           cal.set(Calendar.HOUR, 0);
           cal.set(Calendar.MINUTE, 0);
           cal.set(Calendar.SECOND, 0);
           cal.set(Calendar.MILLISECOND, 0);
           df.applyPattern("yyyy-MM-dd");
		   return df.format(cal.getTime());
       } else {
           df.applyPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		   //Laatste digit van ms wordt erafgehaald.
		   return df.format(cal.getTime()).substring(0, 24);
       }
   }
}