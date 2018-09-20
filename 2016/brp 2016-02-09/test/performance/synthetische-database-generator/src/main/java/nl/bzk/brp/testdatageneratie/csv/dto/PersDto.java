/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.dto;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersbijhouding;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeboorte;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslachtsaand;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersids;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersinschr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersnaamgebruik;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersoverlijden;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerssamengesteldenaam;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;

/**
 * Houder voor een Pers object.
 * het bsn, anr worden eleke keer overschreven.
 *  *
 */
public class PersDto {

    private Pers pers;
    private List<HisPersbijhouding> hispersbijhouding = new ArrayList<>();
    private List<HisPersgeboorte> hispersgeboorte = new ArrayList<>();
    private List<HisPersgeslachtsaand> hispersgeslachtsaand = new ArrayList<>();
    private List<HisPersids> hispersids = new ArrayList<>();
    private List<HisPersinschr> hispersinschr = new ArrayList<>();
    private List<HisPersnaamgebruik> hispersnaamgebruik = new ArrayList<>();
    private List<HisPersoverlijden> hispersoverlijden = new ArrayList<>();
    private List<HisPerssamengesteldenaam> hisperssamengesteldenaam = new ArrayList<>();
    private List<PersAdresDto> persadres = new ArrayList<>();
    private List<PersVoornaamDto> persvoornaam = new ArrayList<>();
    private List<PersGeslachtsnaamcompDto> persgeslachtnaamcomp = new ArrayList<>();
    private List<PersNationDto> persnation = new ArrayList<>();
    private List<PersIndicatieDto> persindicatie = new ArrayList<>();

    /**
     * Instantieert Pers dto.
     *
     * @param pers pers
     */
    public PersDto(final Pers pers) {
        this.pers = pers;
    }

    /**
     * Toevoegen historie.
     *
     * @param his historie
     */
    public void add(final His his) {

        if (his instanceof HisPersbijhouding) {
            hispersbijhouding.add((HisPersbijhouding) his);
        } else if (his instanceof HisPerssamengesteldenaam) {
            hisperssamengesteldenaam.add((HisPerssamengesteldenaam) his);
        } else if (his instanceof HisPersgeboorte) {
            hispersgeboorte.add((HisPersgeboorte) his);
        } else if (his instanceof HisPersgeslachtsaand) {
            hispersgeslachtsaand.add((HisPersgeslachtsaand) his);
        } else if (his instanceof HisPersids) {
            hispersids.add((HisPersids) his);
        } else if (his instanceof HisPersinschr) {
            hispersinschr.add((HisPersinschr) his);
        } else if (his instanceof HisPersnaamgebruik) {
            hispersnaamgebruik.add((HisPersnaamgebruik) his);
        } else if (his instanceof HisPersoverlijden) {
            hispersoverlijden.add((HisPersoverlijden) his);
        } else {
            throw new RuntimeException("Kan geen object vinden met type " + his.getClass().getSimpleName());
        }
    }

    /**
     * Adres toevoegen.
     *
     * @param pa persadres
     */
    public void add(final PersAdresDto pa) {
        persadres.add(pa);
    }

    /**
     * Voornaam toevoegen.
     *
     * @param pv pers voornaam
     */
    public void add(final PersVoornaamDto pv) {
        persvoornaam.add(pv);
    }

    /**
     * Geslachtsnaamcomponent toevoegen.
     *
     * @param pg pers geslachtsnaamcomp
     */
    public void add(final PersGeslachtsnaamcompDto pg) {
        persgeslachtnaamcomp.add(pg);
    }

    /**
     * Nationaliteit toevoegen.
     *
     * @param pn pers nationaliteit
     */
    public void add(final PersNationDto pn) {
        persnation.add(pn);
    }

    /**
     * Indicatie toevoegen.
     *
     * @param pi pers indicatie
     */
    public void add(final PersIndicatieDto pi) {
        persindicatie.add(pi);
    }

    /**
     * Geeft persindicatie.
     *
     * @return persindicatie
     */
    public List<PersIndicatieDto> getPersindicatie() {
        return persindicatie;
    }

    /**
     * Geeft pers.
     *
     * @return pers
     */
    public Pers getPers() {
        return pers;
    }

    /**
     * Geeft hispersaanschr.
     *
     * @return hispersaanschr
     */
    public List<HisPersnaamgebruik> getHispersnaamgebruik() {
        return hispersnaamgebruik;
    }

    /**
     * Geeft hispersbijhaard.
     *
     * @return hispersbijhaard
     */
    public List<HisPersbijhouding> getHispersbijhouding() {
        return hispersbijhouding;
    }

    /**
     * Geeft hispersgeboorte.
     *
     * @return hispersgeboorte
     */
    public List<HisPersgeboorte> getHispersgeboorte() {
        return hispersgeboorte;
    }

    /**
     * Geeft hispersgeslachtsaand.
     *
     * @return hispersgeslachtsaand
     */
    public List<HisPersgeslachtsaand> getHispersgeslachtsaand() {
        return hispersgeslachtsaand;
    }

    /**
     * Geeft hispersids.
     *
     * @return hispersids
     */
    public List<HisPersids> getHispersids() {
        return hispersids;
    }

    /**
     * Geeft hispersinschr.
     *
     * @return hispersinschr
     */
    public List<HisPersinschr> getHispersinschr() {
        return hispersinschr;
    }

    /**
     * Geeft hispersoverlijden.
     *
     * @return hispersoverlijden
     */
    public List<HisPersoverlijden> getHispersoverlijden() {
        return hispersoverlijden;
    }

    /**
     * Geeft hisperssamengesteldenaam.
     *
     * @return hisperssamengesteldenaam
     */
    public List<HisPerssamengesteldenaam> getHisperssamengesteldenaam() {
        return hisperssamengesteldenaam;
    }

    /**
     * Geeft persadres.
     *
     * @return persadres
     */
    public List<PersAdresDto> getPersadres() {
        return persadres;
    }

    /**
     * Geeft persvoornaam.
     *
     * @return persvoornaam
     */
    public List<PersVoornaamDto> getPersvoornaam() {
        return persvoornaam;
    }

    /**
     * Geeft persgeslachtnaamcomp.
     *
     * @return persgeslachtnaamcomp
     */
    public List<PersGeslachtsnaamcompDto> getPersgeslachtnaamcomp() {
        return persgeslachtnaamcomp;
    }

    /**
     * Geeft persnation.
     *
     * @return persnation
     */
    public List<PersNationDto> getPersnation() {
        return persnation;
    }
}
