/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.utils;

import java.util.Date;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek;
import nl.bzk.brp.testdatageneratie.domain.kern.Doc;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisDoc;
import nl.bzk.brp.testdatageneratie.domain.kern.HisErkenningongeborenvrucht;
import nl.bzk.brp.testdatageneratie.domain.kern.HisHuwelijkgeregistreerdpar;
import nl.bzk.brp.testdatageneratie.domain.kern.HisNaamskeuzeongeborenvruch;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOnderzoek;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersdeelneuverkiezingen;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeboorte;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersinschr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersnaamgebruik;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersoverlijden;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerspk;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersreisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersuitslkiesr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersverificatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Onderzoek;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Persreisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.Persverificatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;


/**
 * Utility klasse tbv generatie historie.
 */
public final class HisUtil {

    /**
     * Maximaal tijd terug.
     */
    public static final long MAX_BACK = 10L * 365 * RandomUtil.DAG_IN_MS;

    /**
     * Private constructor.
     */
    private HisUtil() {

    }

    /**
     * Creeert his correctie.
     *
     * @param hisGecorrigeerd his die gecorrigeerd is
     * @param hisBasis his basis
     * @return his
     */
    public static His creeerHisCorrectie(final His hisGecorrigeerd, final His hisBasis) {
        // Creeer voorlopende actie
        hisGecorrigeerd.setActieverval(hisBasis.getActieinh());
        hisGecorrigeerd.setActieinh(RandomUtil.getActie());

        // Creeer voorlopend record met eerdere tijdstippen registratie en verval
        hisGecorrigeerd.setTsverval(hisBasis.getTsreg());

        long tsVerval;
        if (hisGecorrigeerd.getTsverval() != null) {
            tsVerval = hisGecorrigeerd.getTsverval().getTime();
        } else {
            tsVerval = new Date().getTime();
        }
        hisGecorrigeerd.setTsreg(RandomUtil.getPastTimestamp(tsVerval, MAX_BACK));

        return hisGecorrigeerd;
    }

    /**
     * Creeert his record.
     *
     * @param doc the doc
     * @return the his doc
     */
    public static HisDoc creeerHis(final Doc doc) {
        final HisDoc his = new HisDoc();
        his.setPartij(doc.getPartij());
        his.setDoc(doc.getId());
        his.setIdent(doc.getIdent());
        his.setAktenr(doc.getAktenr());
        his.setOms(doc.getOms());
        his.setActieinh(RandomUtil.getActie());
        his.setTsreg(RandomUtil.getPastTimestamp());

        return his;
    }

    /**
     * Creeert his onderzoek.
     *
     * @param onderzoek onderzoek
     * @return his onderzoek
     */
    public static HisOnderzoek creeerHis(final Onderzoek onderzoek) {
        final HisOnderzoek his = new HisOnderzoek();
        his.setOnderzoek(onderzoek.getId());
        his.setDataanv(onderzoek.getDataanv());
        his.setDateinde(onderzoek.getDateinde());
        his.setOms(onderzoek.getOms());
        his.setActieinh(RandomUtil.getActie());
        if (onderzoek.getDataanv() != null) {
            his.setTsreg(RandomUtil.getTimestamp(onderzoek.getDataanv()));
        } else {
            his.setTsreg(RandomUtil.getPastTimestamp());
        }

        // Gebruik maken van model?
        his.setStatus((short) StatusOnderzoek.IN_UITVOERING.ordinal());

        return his;
    }

    /**
     * Creeert his persdeelneuverkiezingen.
     *
     * @param pers pers
     * @return his persdeelneuverkiezingen
     */
    public static HisPersdeelneuverkiezingen creeerHisPersdeelneuverkiezingen(final Pers pers) {
        final HisPersdeelneuverkiezingen his = new HisPersdeelneuverkiezingen();
        his.setPers(pers.getId());
        his.setInddeelneuverkiezingen(pers.getInddeelneuverkiezingen());
        his.setDataanlaanpdeelneuverkiezing(pers.getDataanlaanpdeelneuverkiezing());
        his.setActieinh(RandomUtil.getActie());

        if (pers.getDataanlaanpdeelneuverkiezing() != null) {
            his.setTsreg(RandomUtil.getTimestamp(pers.getDataanlaanpdeelneuverkiezing()));
        } else {
            his.setTsreg(RandomUtil.getPastTimestamp());
        }

        return his;
    }

    /**
     * Creeer his persgeboorte.
     *
     * @param pers pers
     * @return his persgeboorte
     */
    public static HisPersgeboorte creeerHisPersgeboorte(final Pers pers) {
        final HisPersgeboorte his = new HisPersgeboorte();
        his.setPers(pers.getId());
        his.setDatgeboorte(pers.getDatgeboorte());
        his.setLandgebiedgeboorte(pers.getLandgebiedgeboorte());
        his.setWplnaamgeboorte(pers.getWplnaamgeboorte());
        his.setGemgeboorte(pers.getGemgeboorte());
        his.setOmslocgeboorte(pers.getOmslocgeboorte());
        his.setBlregiogeboorte(pers.getBlregiogeboorte());
        his.setBlplaatsgeboorte(pers.getBlplaatsgeboorte());
        his.setActieinh(RandomUtil.getActie());
        if (pers.getDatgeboorte() != null) {
            his.setTsreg(RandomUtil.getTimestamp(pers.getDatgeboorte()));
        } else {
            his.setTsreg(RandomUtil.getPastTimestamp());
        }

        return his;
    }

    /**
     * Creer his persinschr.
     *
     * @param pers pers
     * @return his persinschr
     */
    public static HisPersinschr creerHisPersinschr(final Pers pers) {
        final HisPersinschr his = new HisPersinschr();
        his.setPers(pers.getId());
        his.setDatinschr(pers.getDatinschr());
        his.setVersienr(pers.getVersienr());
        his.setDattijdstempel(GenUtil.vanBrpDatum(pers.getDatinschr()));
        his.setActieinh(RandomUtil.getActie());
        if (pers.getDatinschr() != null) {
            his.setTsreg(RandomUtil.getTimestamp(pers.getDatinschr()));
        } else {
            his.setTsreg(RandomUtil.getPastTimestamp());
        }

        return his;
    }

    /**
     * Creeer his persoverlijden.
     *
     * @param pers pers
     * @return his persoverlijden
     */
    public static HisPersoverlijden creeerHisPersoverlijden(final Pers pers) {
        final HisPersoverlijden his = new HisPersoverlijden();
        his.setPers(pers.getId());
        his.setDatoverlijden(pers.getDatoverlijden());
        his.setLandgebiedoverlijden(pers.getLandgebiedoverlijden());
        his.setWplnaamoverlijden(pers.getWplnaamoverlijden());
        his.setGemoverlijden(pers.getGemoverlijden());
        his.setBlplaatsoverlijden(pers.getBlplaatsoverlijden());
        his.setBlregiooverlijden(pers.getBlregiooverlijden());
        his.setOmslocoverlijden(pers.getOmslocoverlijden());
        his.setActieinh(RandomUtil.getActie());
        if (pers.getDatoverlijden() != null) {
            his.setTsreg(RandomUtil.getTimestamp(pers.getDatoverlijden()));
        } else {
            his.setTsreg(RandomUtil.getPastTimestamp());
        }

        return his;
    }

    /**
     * Creeer his perspk.
     *
     * @param pers pers
     * @return his perspk
     */
    public static HisPerspk creeerHisPerspk(final Pers pers) {
        final HisPerspk his = new HisPerspk();
        his.setPers(pers.getId());
        his.setGempk(pers.getGempk());
        his.setIndpkvollediggeconv(pers.getIndpkvollediggeconv());
        his.setActieinh(RandomUtil.getActie());
        his.setTsreg(RandomUtil.getPastTimestamp());

        return his;
    }

    /**
     * Creeert his persreisdoc.
     *
     * @param persreisdoc persreisdoc
     * @return his persreisdoc
     */
    public static HisPersreisdoc creeerHisPersreisdoc(final Persreisdoc persreisdoc) {
        final HisPersreisdoc his = new HisPersreisdoc();
        his.setPersreisdoc(persreisdoc.getId());
        his.setAandinhingvermissing(persreisdoc.getAandinhingvermissing());
        his.setAutvanafgifte(persreisdoc.getAutvanafgifte());
        his.setNr(persreisdoc.getNr());
        his.setDatingangdoc(persreisdoc.getDatingangdoc());
        his.setDateindedoc(persreisdoc.getDateindedoc());
        his.setDatuitgifte(persreisdoc.getDatuitgifte());
        his.setDatinhingvermissing(persreisdoc.getDatinhingvermissing());
        his.setDatinhingvermissing(persreisdoc.getDatinhingvermissing());
        his.setLengtehouder(persreisdoc.getLengtehouder());
        his.setActieinh(RandomUtil.getActie());
        if (persreisdoc.getDatingangdoc() != null) {
            his.setTsreg(RandomUtil.getTimestamp(persreisdoc.getDatingangdoc()));
        } else {
            his.setTsreg(RandomUtil.getPastTimestamp());
        }

        return his;
    }

    /**
     * Creeert his persuitslnlkiesr.
     *
     * @param pers pers
     * @return his persuitslnlkiesr
     */
    public static HisPersuitslkiesr creeerHisPersuitslkiesr(final Pers pers) {
        final HisPersuitslkiesr his = new HisPersuitslkiesr();
        his.setPers(pers.getId());
        his.setInduitslkiesr(pers.getInduitslkiesr());
        his.setDatvoorzeindeuitslkiesr(pers.getDatvoorzeindeuitslkiesr());
        his.setActieinh(RandomUtil.getActie());
        if (pers.getDatvoorzeindeuitslkiesr() != null) {
            his.setTsreg(RandomUtil.getTimestamp(pers.getDatvoorzeindeuitslkiesr()));
        } else {
            his.setTsreg(RandomUtil.getPastTimestamp());
        }

        return his;
    }

    /**
     * Creer his persverificatie.
     *
     * @param verificatie verificatie
     * @return his persverificatie
     */
    public static HisPersverificatie creerHisPersverificatie(final Persverificatie verificatie) {
        final HisPersverificatie his = new HisPersverificatie();
        his.setPersverificatie(verificatie.getId());
        his.setActieinh(RandomUtil.getActie());
        int dat = RandomUtil.randomDate();
        his.setDat(dat);
        his.setTsreg(RandomUtil.getTimestamp(dat));

        return his;
    }

    /**
     * Creeer his persnaamgebruik.
     *
     * @param pers pers
     * @return his persnaamgebruik
     */
    public static HisPersnaamgebruik creeerHisPersnaamgebruik(final Pers pers) {
        final HisPersnaamgebruik his = new HisPersnaamgebruik();
        his.setPers(pers.getId());
        his.setGeslnaamstamnaamgebruik(pers.getGeslnaamstamnaamgebruik());
        his.setIndnaamgebruikafgeleid(pers.getIndnaamgebruikafgeleid());
        his.setPredicaatnaamgebruik(pers.getPredicaatnaamgebruik());
        his.setNaamgebruik(pers.getNaamgebruik());
//        his.setIndtitelspredikatenbijaansch(pers.getIndtitelspredikatenbijaansch());
        his.setVoornamennaamgebruik(pers.getVoornamennaamgebruik());
        his.setVoorvoegselnaamgebruik(pers.getVoorvoegselnaamgebruik());
        his.setScheidingstekennaamgebruik(pers.getScheidingstekennaamgebruik());
        his.setActieinh(RandomUtil.getActie());
        his.setTsreg(RandomUtil.getPastTimestamp());

        return his;
    }

    /**
     * Creeer his huwelijkgeregistreerdpar.
     *
     * @param relatie relatie
     * @return his huwelijkgeregistreerdpar
     */
    public static HisHuwelijkgeregistreerdpar creeerHisHuwelijkgeregistreerdpar(final Relatie relatie) {
        final HisHuwelijkgeregistreerdpar his = new HisHuwelijkgeregistreerdpar();
        his.setRelatie(relatie.getId());
        his.setDataanv(relatie.getDataanv());
        his.setDateinde(relatie.getDateinde());
        his.setWplnaamaanv(relatie.getWplnaamaanv());
        his.setGemeinde(relatie.getGemeinde());
        his.setWplnaameinde(relatie.getWplnaameinde());
        his.setRdneinde(relatie.getRdneinde());
        his.setGemaanv(relatie.getGemaanv());
        his.setLandgebiedaanv(relatie.getLandgebiedaanv());
        his.setLandgebiedeinde(relatie.getLandgebiedeinde());
        his.setBlplaatsaanv(relatie.getBlplaatsaanv());
        his.setBlregioaanv(relatie.getBlregioaanv());
        his.setOmslocaanv(relatie.getOmslocaanv());
        his.setBlplaatseinde(relatie.getBlplaatseinde());
        his.setBlregioeinde(relatie.getBlregioeinde());
        his.setOmsloceinde(relatie.getOmsloceinde());
        his.setActieinh(RandomUtil.getActie());
        if (relatie.getDataanv() != null) {
            his.setTsreg(RandomUtil.getTimestamp(relatie.getDataanv()));
        } else {
            his.setTsreg(RandomUtil.getPastTimestamp());
        }

        return his;
    }

    /**
     * Creeer his naamskeuzeongeborenvruch.
     *
     * @param relatie relatie
     * @return his naamskeuzeongeborenvruch
     */
    public static HisNaamskeuzeongeborenvruch creeerHisNaamskeuzeongeborenvruch(final Relatie relatie) {
        final HisNaamskeuzeongeborenvruch his = new HisNaamskeuzeongeborenvruch();
        his.setRelatie(relatie.getId());
        his.setDatnaamskeuzeongeborenvrucht(relatie.getDatnaamskeuzeongeborenvrucht());
        his.setActieinh(RandomUtil.getActie());
        if (relatie.getDatnaamskeuzeongeborenvrucht() != null) {
            his.setTsreg(RandomUtil.getTimestamp(relatie.getDatnaamskeuzeongeborenvrucht()));
        } else {
            his.setTsreg(RandomUtil.getPastTimestamp());
        }

        return his;
    }

    /**
     * Creeer his erkenningongeborenvrucht.
     *
     * @param relatie relatie
     * @return his erkenningongeborenvrucht
     */
    public static HisErkenningongeborenvrucht creeerHisErkenningongeborenvrucht(final Relatie relatie) {
        final HisErkenningongeborenvrucht his = new HisErkenningongeborenvrucht();
        his.setRelatie(relatie.getId());
        his.setDaterkenningongeborenvrucht(relatie.getDaterkenningongeborenvrucht());
        his.setActieinh(RandomUtil.getActie());
        if (relatie.getDaterkenningongeborenvrucht() != null) {
            his.setTsreg(RandomUtil.getTimestamp(relatie.getDaterkenningongeborenvrucht()));
        } else {
            his.setTsreg(RandomUtil.getPastTimestamp());
        }

        return his;
    }
}
