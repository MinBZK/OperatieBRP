/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.testdatageneratie.SynDbGen;
import nl.bzk.brp.testdatageneratie.dataaccess.BronnenRepo;
import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.domain.bronnen.AangifteAdreshoudingOms;
import nl.bzk.brp.testdatageneratie.domain.bronnen.AkteRegisterGemeenteCode;
import nl.bzk.brp.testdatageneratie.domain.bronnen.BijhoudingOpschortReden;
import nl.bzk.brp.testdatageneratie.domain.bronnen.GeboorteDatum;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Geboorteplaats;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Geslachtsnaam;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.bronnen.NationaliteitCode;
import nl.bzk.brp.testdatageneratie.domain.bronnen.NationaliteitVerkrijgReden;
import nl.bzk.brp.testdatageneratie.domain.bronnen.NationaliteitVerliesReden;
import nl.bzk.brp.testdatageneratie.domain.bronnen.ReisDocAutoriteit;
import nl.bzk.brp.testdatageneratie.domain.bronnen.ReisDocSoort;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieEindReden;
import nl.bzk.brp.testdatageneratie.domain.bronnen.TitelPredikaat;
import nl.bzk.brp.testdatageneratie.domain.bronnen.VerbintenisSoort;
import nl.bzk.brp.testdatageneratie.domain.bronnen.VertrekLandCode;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Voornaam;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Voorvoegsel;
import nl.bzk.brp.testdatageneratie.domain.kern.Aandverblijfsr;
import nl.bzk.brp.testdatageneratie.domain.kern.Adellijketitel;
import nl.bzk.brp.testdatageneratie.domain.kern.Auttypevanafgiftereisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.Bijhaard;
import nl.bzk.brp.testdatageneratie.domain.kern.Gem;
import nl.bzk.brp.testdatageneratie.domain.kern.Landgebied;
import nl.bzk.brp.testdatageneratie.domain.kern.Nation;
import nl.bzk.brp.testdatageneratie.domain.kern.Plaats;
import nl.bzk.brp.testdatageneratie.domain.kern.Rdneinderelatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Rdnverknlnation;
import nl.bzk.brp.testdatageneratie.domain.kern.Rdnverliesnlnation;
import nl.bzk.brp.testdatageneratie.domain.kern.Rdnwijzadres;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtnlreisdoc;
import org.apache.log4j.Logger;


/**
 * Random utility klasse.
 */
public final class RandomUtil {


    private static Logger log = Logger.getLogger(RandomUtil.class);

    /**
     * Duizend.
     */
    private static final int DUIZEND = 1000;
    /**
     * Veertig.
     */
    private static final int VEERTIG = 40;

    /**
     * Een dag in milliseconden.
     */
    public static final int  DAG_IN_MS = 24 * 60 * 60 * 1000;

    /**
     * Maximum terug in tijd: 10 jaar.
     */
    public static final long MAX_BACK  = 10L * 365 * DAG_IN_MS;

    /**
     * Tot acht cijfers.
     */
    private static final int TOT_ACHT_CIJFERS = 90000000;

    /**
     * Tot acht cijfers offset.
     */
    private static final int TOT_ACHT_CIJFERS_OFFSET = 10000000;

    /**
     * Random instantie.
     */
    public static Random     random    = new Random();

    // For random strings generation:
    private static final String[] SCHEIDINGSTEKENS = { " ", "-", "/" };

    /**
     * Instantieert een nieuwe Random util.
     */
    private RandomUtil() {
    }

    /**
     * Geeft volgende random long terug.
     *
     * @param n n
     * @return random getal
     */
    public static long nextLong(final long n) {
        // error checking and 2^x checking removed for simplicity.
        long bits, val;
        do {
            bits = random.nextLong() << 1 >>> 1;
            val = bits % n;
        } while (bits - val + n - 1 < 0L);
        return val;
    }

    /**
     * Geeft volgende random int terug.
     *
     * @param n the n
     * @return the int
     */
    public static int nextInt(final int n) {
        // error checking and 2^x checking removed for simplicity.
        int bits, val;
        do {
            bits = random.nextInt() << 1 >>> 1;
            val = bits % n;
        } while (bits - val + n - 1 < 0L);
        return val;
    }

    /**
     * Geeft random enum terug zonder dummy waarde.
     *
     * @param values enum waarden
     * @return random enum
     */
    public static <T extends Enum<?>> T getRandom(final T[] values) {
        // sluit de dummy waarde uit
        return values[random.nextInt(values.length - 1) + 1];
    }

    /**
     * Geeft random scheidings teken.
     *
     * @return random scheidingsteken als string
     */
    public static String nextScheidingsTeken() {
        return SCHEIDINGSTEKENS[random.nextInt(SCHEIDINGSTEKENS.length)];
    }

    /**
     * Geeft random true terug op basis van een fractie getal. Eens in de fractie krijg je gemiddeld true terug
     * aangezien er check wordt gedaan dat random getal gelijk is aan 0.
     *
     * @param fractie fractie in hoeveel van de gevallen true moet teruggegeven worden
     * @return true als random op fractie gelijk is aan nul
     */
    public static boolean isFractie(final int fractie) {
        return random.nextInt(fractie) == 0;
    }

    /**
     * Geeft adellijketitel uit bronnen.
     *
     * @return adellijketitel
     */
    public static Adellijketitel getAdellijketitel() {
        return BronnenRepo.getBron(TitelPredikaat.class).getAdellijketitel();
    }

    /**
     * Geeft adellijketitel code uit bronnen.
     *
     * @return adellijketitel code
     */
    public static Short getAdellijketitelShort() {
        final Adellijketitel a = getAdellijketitel();
        return null == a ? null : new Short((short) a.ordinal());
    }

    /**
     * Geeft aangifte adreshouding omschrijving uit bronnen.
     *
     * @return aangifte adreshouding omschrijving
     */
    public static AangifteAdreshoudingOms getAangifteAdreshoudingOms() {
        return BronnenRepo.getBron(AangifteAdreshoudingOms.class);
    }

    /**
     * Geeft geboorte plaats uit bronnen.
     *
     * @return geboorte plaats
     */
    public static Locatie getGeboortePlaats() {
        Locatie locatie;
        do {
            locatie = BronnenRepo.getBron(Geboorteplaats.class).getLocatie();
        } while (locatie.getLandgebied() == null);
        return locatie;
    }

    /**
     * Geeft land buitenlands adres.
     *
     * @return land buitenlands adres
     */
    public static Landgebied getLandgebiedBuitenlandsAdres() {
        short landCode = BronnenRepo.getBron(VertrekLandCode.class).getLandCode();
        return MetaRepo.get(Landgebied.class, landCode);
    }

    /**
     * Geeft nationaliteit.
     *
     * @return nationaliteit
     */
    public static Nation getNationaliteit() {
        short nationaliteitCode = BronnenRepo.getBron(NationaliteitCode.class).getCode();
        return MetaRepo.get(Nation.class, nationaliteitCode);
    }

    /**
     * Geeft nationaliteit verkrijg reden.
     *
     * @return nationaliteit verkrijg reden
     */
    public static Rdnverknlnation getNationaliteitVerkrijgReden() {
        short code = BronnenRepo.getBron(NationaliteitVerkrijgReden.class).getCode();
        return MetaRepo.get(Rdnverknlnation.class, code);
    }

    /**
     * Geeft nationaliteit verlies reden.
     *
     * @return nationaliteit verlies reden
     */
    public static Rdnverliesnlnation getNationaliteitVerliesReden() {
        short code = BronnenRepo.getBron(NationaliteitVerliesReden.class).getCode();
        return MetaRepo.get(Rdnverliesnlnation.class, code);
    }

    /**
     * Geeft Aandverblijfsr.
     *
     * @return Aandverblijfsr
     */
    public static Aandverblijfsr getAandverblijfsr() {
        short aandverblijfsr =
                BronnenRepo.getBron(nl.bzk.brp.testdatageneratie.domain.bronnen.Verblijfstitel.class).getAanduiding();
        return MetaRepo.get(Aandverblijfsr.class, aandverblijfsr);
    }

    /**
     * Geeft auttypevanafgiftereisdoc.
     *
     * @return the auttypevanafgiftereisdoc
     */
    public static Auttypevanafgiftereisdoc getAuttypevanafgiftereisdoc() {
        Auttypevanafgiftereisdoc auttypevanafgiftereisdoc;
        int i = 0;
        do {
            String autoriteit = BronnenRepo.getBron(ReisDocAutoriteit.class).getCode();
            auttypevanafgiftereisdoc = MetaRepo.get(Auttypevanafgiftereisdoc.class, autoriteit);
        } while (auttypevanafgiftereisdoc == null && ++i < VEERTIG);
        return auttypevanafgiftereisdoc;
    }

    /**
     * Geeft reisdocument soort.
     *
     * @return reisdocument soort
     */
    public static Srtnlreisdoc getReisdocumentSoort() {
        Srtnlreisdoc srtnlreisdoc = null;
        while (srtnlreisdoc == null) {
            srtnlreisdoc = BronnenRepo.getBron(ReisDocSoort.class).getSrtnlreisdoc();
        }
        return srtnlreisdoc;
    }

    /**
     * Get voornamen.
     *
     * @return voornamen
     */
    public static String[] getVoornamen() {
        int aantal = random.nextInt(5) + 1;
        String[] voornamen = new String[aantal];

        for (int i = 0; i < aantal; i++) {
            String naamVoorBrp;
            do {
                Voornaam voornaam = BronnenRepo.getBron(Voornaam.class);
                naamVoorBrp = voornaam.getId().getNaamVoorBrp();
            } while (naamVoorBrp.length() > Constanten.VEERTIG);

            voornamen[i] = naamVoorBrp;
        }

        return voornamen;
    }

    /**
     * Geeft soort persoon.
     *
     * @return soort persoon
     */
    public static SoortPersoon getSrtpers() {
        return isFractie(Constanten.DUIZEND) ? SoortPersoon.NIET_INGESCHREVENE : SoortPersoon.INGESCHREVENE;
    }

    /**
     * Geeft bijhoudingsaard.
     *
     * @return bijhoudingsaard
     */
    public static Bijhaard getBijhaard() {
        // TODO welk percentages zijn geimmigreerd en welk niet ingeschrevene ??
        return isFractie(Constanten.TIEN) ? Bijhaard.NIET_INGEZETENE : Bijhaard.INGEZETENE;
    }

    /**
     * Geeft geslachtsnaam.
     *
     * @return geslachtsnaam
     */
    public static String getGeslnaam() {
        Geslachtsnaam geslachtsnaam = BronnenRepo.getBron(Geslachtsnaam.class);
        return geslachtsnaam.getId().getGeslachtsnaam();
    }

    /**
     * Bijhoudings gemeente: random gemeente op basis van bron Partij.
     * Partij met een ingevulde gemeentecode
     *
     * @return Partij A Random partij
     */
    public static Short getPartijByBijhgem() {
        return getPartijByBijhgem(RandomUtil.random);
    }

    /**
     * Bijhoudings gemeente: random gemeente op basis van bron Partij.
     * Partij met een ingevulde gemeentecode
     *
     * @return Partij A Random partij
     */
    public static Short getPartijByBijhgem(final Random randomParam) {
        Gem gem;
        Short partij;
        do {
            gem = getGem(randomParam);
            partij = gem.getPartij();
            if (null == partij) {
                log.warn("********** Kan geen partij viden met code" + gem.getCode());
            }
        } while (partij == null);
        return partij;
    }

    /**
     * Geeft gemeente.
     *
     * @param randomParam random param
     * @return gemeente
     */
    public static Gem getGem(final Random randomParam) {
        Gem gem;

        do {
            short gemeenteCode = BronnenRepo.getBron(AkteRegisterGemeenteCode.class, randomParam).getGemeenteCode();
            gem = MetaRepo.get(Gem.class, gemeenteCode);
            if (null == gem) {
                log.warn("********** Kan geen gemeente viden met code" + gemeenteCode);
            }
        } while (gem == null);

        return gem;
    }

    /**
     * Geeft gemeente die nu geldig is.
     *
     * @return gemeente die nu geldig is
     */
    public static Gem getGemeenteNuGeldig() {
        Gem gem;
        do {
            gem = getGem(RandomUtil.random);
        } while (gem.getDateindegel() != null);
        return gem;
    }

    /**
     * Geeft datum geboorte.
     *
     * @return datum geboorte
     */
    public static Integer getDatGeboorte() {
        final int datum = BronnenRepo.getBron(GeboorteDatum.class).getDatum();
        Integer datGeboorte;
        if (datum == 0) {
            datGeboorte = 0;
        } else {
            int jaar = datum % Constanten.TIENDUIZEND;
            int maand = datum % Constanten.HONDERD;
            int extraDagen = random.nextInt(getAantalDagen(jaar, maand));
            datGeboorte = datum + extraDagen;
        }
        return datGeboorte;
    }

    /**
     * Geeft aantal dagen voor maand van jaar.
     *
     * @param jaar jaar
     * @param maand maand
     * @return aantal dagen
     */
    private static int getAantalDagen(final int jaar, final int maand) {
        int aantalDagen;

        if (maand == 2) {
            aantalDagen = jaar % 4 == 0 ? 29 : 28;
        }
        switch (maand) {
            case 4:
            case 6:
            case 9:
            case 11:
                aantalDagen = 30;
                break;
            default:
                aantalDagen = 31;
                break;
        }

        return aantalDagen;
    }

    /**
     * Geeft voorvoegsel.
     *
     * @return voorvoegsel
     */
    public static String getVoorvoegsel() {
        final Voorvoegsel vv = BronnenRepo.getBron(Voorvoegsel.class);
        return vv.getVoorvoegsel();
    }

    /**
     * Geeft timestamp in verleden.
     *
     * @return timestamp in verleden
     */
    public static Date getPastTimestamp() {
        return new Date(nextLong(System.currentTimeMillis()));
    }

    /**
     * Geeft timestamp in verleden.
     *
     * @param max max
     * @param maxBack max terug in verleden
     * @return timestamp in verleden
     */
    public static Date getPastTimestamp(final long max, final long maxBack) {
        long back = nextLong(maxBack);
        return new Date(max - back);
    }

    /**
     * Random datum.
     *
     * @return random datum als int
     */
    public static int randomDate() {
        int date = 0;
        while (0 == date) {
            Date d = RandomUtil.getPastTimestamp();
            try {
                date = GenUtil.naarBrpDatum(d);
            } catch (Exception e) {
                log.error("randomDate Failed msg=(" + e.getMessage() + ") date=" + d, e);
                date = 0;
            }
        }
        return date;
    }

    /**
     * Random date na datum.
     *
     * @param beginDatum begin datum
     * @return int
     */
    public static int randomDateNaDatum(final int beginDatum) {
        return GenUtil.naarBrpDatum(RandomUtil.getPastTimestamp());
//        long now = System.currentTimeMillis();
//        long begin = GenUtil.vanBrpDatum(beginDatum).getTime();
//        return GenUtil.naarBrpDatum(new Date(begin + nextLong(now - begin)));
    }

    /**
     * Geeft timestamp.
     *
     * @param datum the datum
     * @return the timestamp
     */
    public static Date getTimestamp(final Integer datum) {
        try {
            Date timestamp;
            if (datum == 0) {
                timestamp = getPastTimestamp();
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                Date middernacht = simpleDateFormat.parse(datum.toString());
                long randomEpoch = middernacht.getTime() + random.nextInt(DAG_IN_MS);
                timestamp = new Date(randomEpoch);
            }
            return timestamp;
        } catch (ParseException e) {
            throw new RuntimeException(datum + "?", e);
        }
    }

    /**
     * Geeft reden opschorting.
     *
     * @return the reden opschorting
     */
    public static NadereBijhoudingsaard getNadereBijhaard() {
        BijhoudingOpschortReden bijhoudingOpschortReden = BronnenRepo.getBron(BijhoudingOpschortReden.class);

        NadereBijhoudingsaard nadereBijhaard;
        switch (bijhoudingOpschortReden.getReden()) {
            case 'O':
                nadereBijhaard = NadereBijhoudingsaard.OVERLEDEN;
                break;
            // E stond stond op onbekend
            case 'E':
                nadereBijhaard = NadereBijhoudingsaard.EMIGRATIE;
                break;
            case 'F':
                nadereBijhaard = NadereBijhoudingsaard.FOUT;
                break;
            default:
                nadereBijhaard = NadereBijhoudingsaard.MINISTERIEEL_BESLUIT;
                break;
        }

        return nadereBijhaard;
    }

    /**
     * Geeft soort relatie.
     *
     * @return srtrelatie
     */
    public static SoortRelatie getSrtrelatie() {
        VerbintenisSoort verbintenisSoort = BronnenRepo.getBron(VerbintenisSoort.class);
        if (verbintenisSoort.getSoort() == 'P') {
            return SoortRelatie.GEREGISTREERD_PARTNERSCHAP;
        } else {
            return SoortRelatie.HUWELIJK;
        }
    }

    /**
     * Geeft wpl geboorte.
     *
     * @return the wpl geboorte
     */
    public static Plaats getWplGeboorte() {
        return MetaRepo.get(Plaats.class);
    }

    /**
     * Geeft wpl nu geldig.
     *
     * @return the wpl nu geldig
     */
    public static Plaats getWplNuGeldig() {
        Plaats plaats;
        do {
            plaats = MetaRepo.get(Plaats.class);
        } while (plaats.getDateindegel() != null);
        return plaats;
    }

    /**
     * Geeft reden wijziging adres.
     *
     * @return the reden wijziging adres
     */
    public static Rdnwijzadres getRedenWijzigingAdres() {
        if (isFractie(DUIZEND)) {
            return getRandom(Rdnwijzadres.values());
        } else {
            return Rdnwijzadres.P;
        }
    }

    /**
     * Geeft reden einde relatie.
     *
     * @return the reden einde relatie
     */
    public static Rdneinderelatie getRedenEindeRelatie() {
        char redenChar = BronnenRepo.getBron(RelatieEindReden.class).getReden();
        switch (redenChar) {
            case ' ':
            case '.':
            case '0':
                return null;
            default:
                return Rdneinderelatie.valueOf("" + redenChar);
        }
    }

    /**
     * Geeft reden einde relatie short.
     *
     * @return the reden einde relatie short
     */
    public static Short getRedenEindeRelatieShort() {
        Rdneinderelatie r = getRedenEindeRelatie();
        return r == null ? null : new Short((short) r.ordinal());
    }

    /**
     * Geeft actie.
     *
     * @return the actie
     */
    public static long getActie() {
        return SynDbGen.ACTIE_IDS.getMax() == 0 ? 0 : SynDbGen.ACTIE_IDS.selecteerId();
    }

    /**
     * Geeft true false string.
     * Standaard TrueFalse met 50% - 50% verdeling tussen true en false
     *
     * @return true of false als string
     */
    public static String getTrueFalseString() {
        return getTrueFalseString(Constanten.TWEE);
    }

    /**
     * Geeft een true false string op basis van een fractie.
     *
     * @param fractieTrue fractie voor true
     * @return true of false als string op basis van fractie
     */
    public static String getTrueFalseString(final int fractieTrue) {
        String trueFalse = "F";

        if (isFractie(fractieTrue)) {
            trueFalse = "T";
        }

        return trueFalse;
    }

    /**
     * Geeft zestien cijfers as string.
     *
     * @return the zestien cijfers as string
     */
    public static String getZestienCijfersAsString() {
        final int deel1 = TOT_ACHT_CIJFERS_OFFSET + random.nextInt(TOT_ACHT_CIJFERS);
        final int deel2 = TOT_ACHT_CIJFERS_OFFSET + random.nextInt(TOT_ACHT_CIJFERS);
        return "" + deel1 + deel2;
    }
}
