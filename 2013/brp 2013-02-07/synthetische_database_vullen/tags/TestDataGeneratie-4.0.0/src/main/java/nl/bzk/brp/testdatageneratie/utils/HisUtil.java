/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.utils;

import nl.bzk.brp.testdatageneratie.domain.kern.Doc;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisDoc;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOnderzoek;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersaanschr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerseuverkiezingen;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeboorte;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersinschr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersoverlijden;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerspk;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersreisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersuitslnlkiesr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersverificatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Onderzoek;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Persreisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.Persverificatie;


public final class HisUtil {

    public static final long MAX_BACK = 10L * 365 * RandomUtil.DAG_IN_MS;

    public static His creeerHisCorrectie(final His hisNieuw, final His hisBasis) {

        // Creeer voorlopende actie
        hisNieuw.setActieverval(hisBasis.getActieinh());
        hisNieuw.setActieinh(RandomUtil.getActie());

        // Creeer voorlopend record met eerdere tijdstippen registratie en verval
        hisNieuw.setTsverval(hisBasis.getTsreg());
        hisNieuw.setTsreg(RandomUtil.getPastTimestamp(hisNieuw.getTsverval().getTime(), MAX_BACK));

        return hisNieuw;
    }

    public static HisDoc creeerHis(final Doc doc) {
        HisDoc his = new HisDoc();
        his.setPartij(doc.getPartij());
        his.setDoc(doc);
        his.setIdent(doc.getIdent());
        his.setAktenr(doc.getAktenr());
        his.setOms(doc.getOms());
        return his;
    }

    public static HisOnderzoek creeerHis(final Onderzoek onderzoek) {
        HisOnderzoek his = new HisOnderzoek();
        his.setOnderzoek(onderzoek);
        his.setDatbegin(onderzoek.getDatbegin());
        his.setDateinde(onderzoek.getDateinde());
        his.setOms(onderzoek.getOms());
        return his;
    }

    public static HisPerseuverkiezingen creeerHisPerseuverkiezingen(final Pers pers) {
        HisPerseuverkiezingen his = new HisPerseuverkiezingen();
        his.setPers(pers);
        his.setInddeelneuverkiezingen(pers.getInddeelneuverkiezingen());
        his.setDataanlaanpdeelneuverkiezing(pers.getDataanlaanpdeelneuverkiezing());
        return his;
    }

    public static HisPersgeboorte creeerHisPersgeboorte(final Pers pers) {
        HisPersgeboorte his = new HisPersgeboorte();
        his.setPers(pers);
        his.setDatgeboorte(pers.getDatgeboorte());
        his.setLand(pers.getLandByLandgeboorte());
        his.setPlaats(pers.getPlaatsByWplgeboorte());
        his.setPartij(pers.getPartijByGemgeboorte());
        his.setOmslocgeboorte(pers.getOmslocgeboorte());
        his.setBlregiogeboorte(pers.getBlregiogeboorte());
        his.setBlplaatsgeboorte(pers.getBlplaatsgeboorte());
        return his;
    }

    public static HisPersinschr creerHisPersinschr(final Pers pers) {
        HisPersinschr his = new HisPersinschr();
        his.setPersByVolgendepers(pers.getPersByVolgendepers());
        his.setPersByPers(pers);
        his.setPersByVorigepers(pers.getPersByVorigepers());
        his.setDatinschr(pers.getDatinschr());
        his.setVersienr(pers.getVersienr());
        return his;
    }

    public static HisPersoverlijden creeerHisPersoverlijden(final Pers pers) {
        HisPersoverlijden his = new HisPersoverlijden();
        his.setPers(pers);
        his.setDatoverlijden(pers.getDatoverlijden());
        his.setLand(pers.getLandByLandoverlijden());
        his.setPlaats(pers.getPlaatsByWploverlijden());
        his.setPartij(pers.getPartijByGemoverlijden());
        his.setBlplaatsoverlijden(pers.getBlplaatsoverlijden());
        his.setBlregiooverlijden(pers.getBlregiooverlijden());
        his.setOmslocoverlijden(pers.getOmslocoverlijden());
        return his;
    }

    public static HisPerspk creeerHisPerspk(final Pers pers) {
        HisPerspk his = new HisPerspk();
        his.setPers(pers);
        his.setPartij(pers.getPartijByGempk());
        his.setIndpkvollediggeconv(pers.getIndpkvollediggeconv());
        return his;
    }

    public static HisPersreisdoc creeerHisPersreisdoc(final Persreisdoc persreisdoc) {
        HisPersreisdoc his = new HisPersreisdoc();
        his.setPersreisdoc(persreisdoc);
        his.setRdnvervallenreisdoc(persreisdoc.getRdnvervallenreisdoc());
        his.setAutvanafgiftereisdoc(persreisdoc.getAutvanafgiftereisdoc());
        his.setNr(persreisdoc.getNr());
        his.setDatingangdoc(persreisdoc.getDatingangdoc());
        his.setDatuitgifte(persreisdoc.getDatuitgifte());
        his.setDatvoorzeeindegel(persreisdoc.getDatvoorzeeindegel());
        his.setDatinhingvermissing(persreisdoc.getDatinhingvermissing());
        his.setLengtehouder(persreisdoc.getLengtehouder());
        return his;
    }

    public static HisPersuitslnlkiesr creeerHisPersuitslnlkiesr(final Pers pers) {
        HisPersuitslnlkiesr his = new HisPersuitslnlkiesr();
        his.setPers(pers);
        his.setInduitslnlkiesr(pers.getInduitslnlkiesr());
        his.setDateindeuitslnlkiesr(pers.getDateindeuitslnlkiesr());
        return his;
    }

    public static HisPersverificatie creerHisPersverificatie(final Persverificatie verificatie) {
        HisPersverificatie his = new HisPersverificatie();
        his.setPersverificatie(verificatie);
        his.setDat(RandomUtil.randomDate());
        return his;
    }

    public static HisPersaanschr creeerHisPersaanschr(final Pers pers) {
        HisPersaanschr his = new HisPersaanschr();
        his.setPers(pers);
        his.setGeslnaamaanschr(pers.getGeslnaamaanschr());
        his.setIndaanschralgoritmischafgele(pers.getIndaanschralgoritmischafgele());
        his.setPredikaat(pers.getPredikaatByPredikaat());
        his.setWijzegebruikgeslnaam(pers.getWijzegebruikgeslnaam());
        his.setIndtitelspredikatenbijaansch(pers.getIndtitelspredikatenbijaansch());
        his.setVoornamenaanschr(pers.getVoornamenaanschr());
        his.setVoorvoegselaanschr(pers.getVoorvoegselaanschr());
        his.setScheidingstekenaanschr(pers.getScheidingstekenaanschr());
        return his;
    }

    // Weg, is niet meer aanwezig in nieuw model
    // public static HisRelatie creeerHis(final Relatie relatie) {
    // HisRelatie his = new HisRelatie();
    // his.setRelatie(relatie);
    // his.setPlaatsByWplaanv(relatie.getPlaatsByWplaanv());
    // his.setPartijByGemeinde(relatie.getPartijByGemeinde());
    // his.setPlaatsByWpleinde(relatie.getPlaatsByWpleinde());
    // his.setRdnbeeindrelatie(relatie.getRdnbeeindrelatie());
    // his.setPartijByGemaanv(relatie.getPartijByGemaanv());
    // his.setLandByLandaanv(relatie.getLandByLandaanv());
    // his.setLandByLandeinde(relatie.getLandByLandeinde());
    // his.setBlplaatsaanv(relatie.getBlplaatsaanv());
    // his.setBlregioaanv(relatie.getBlregioaanv());
    // his.setOmslocaanv(relatie.getOmslocaanv());
    // his.setBlplaatseinde(relatie.getBlplaatseinde());
    // his.setBlregioeinde(relatie.getBlregioeinde());
    // his.setOmsloceinde(relatie.getOmsloceinde());
    // return his;
    // }
}
