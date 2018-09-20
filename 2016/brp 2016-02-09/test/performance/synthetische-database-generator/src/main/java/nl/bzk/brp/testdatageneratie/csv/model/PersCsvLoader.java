/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.testdatageneratie.csv.dto.BetrDto;
import nl.bzk.brp.testdatageneratie.csv.dto.PersAdresDto;
import nl.bzk.brp.testdatageneratie.csv.dto.PersDto;
import nl.bzk.brp.testdatageneratie.csv.dto.PersGeslachtsnaamcompDto;
import nl.bzk.brp.testdatageneratie.csv.dto.PersIndicatieDto;
import nl.bzk.brp.testdatageneratie.csv.dto.PersNationDto;
import nl.bzk.brp.testdatageneratie.csv.dto.PersVoornaamDto;
import nl.bzk.brp.testdatageneratie.csv.dto.RelatieDto;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcBetr;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisHuwelijkgeregistreerdpar;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisOuderouderlijkgezag;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisOuderouderschap;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisPersadres;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisPersbijhouding;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisPersgeboorte;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisPersgeslachtsaand;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisPersgeslachtsnaamcomp;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisPersids;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisPersindicatie;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisPersinschr;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisPersnaamgebruik;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisPersnation;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisPersoverlijden;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisPerssamengesteldenaam;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcHisPersvoornaam;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcPers;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcPersAdres;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcPersGeslachtsnaamcomp;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcPersIndicatie;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcPersNation;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcPersVoornaam;
import nl.bzk.brp.testdatageneratie.csv.processor.CsvProcRelatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisHuwelijkgeregistreerdpar;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOuderouderlijkgezag;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOuderouderschap;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersadres;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersbijhouding;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeboorte;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslachtsaand;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslnaamcomp;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersids;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersindicatie;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersinschr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersnaamgebruik;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersnation;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersoverlijden;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerssamengesteldenaam;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersvoornaam;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Persadres;
import nl.bzk.brp.testdatageneratie.domain.kern.Persgeslnaamcomp;
import nl.bzk.brp.testdatageneratie.domain.kern.Persindicatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Persnation;
import nl.bzk.brp.testdatageneratie.domain.kern.Persvoornaam;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;

/**
 * Pers csv loader.
 */
public class PersCsvLoader {
    private final String path;

    /**
     * Instantieert Pers csv loader.
     *
     * @param path pad naar bestanden
     */
    public PersCsvLoader(final String path) {
        this.path = path;
    }

    /**
     * Leest personen.
     *
     * @return map met personen met als key hun pers id
     * @throws IOException IO exception
     */
    public Map<Integer, PersDto> leesPersonen() throws IOException {
        List<Pers> listPers;
        listPers = new CsvLoader<Pers>(path, new CsvProcPers()).leesdata();

        Map<Integer, PersDto> mapPersDto = new HashMap<Integer, PersDto>();
        for (Pers p : listPers) {
            mapPersDto.put(p.getId(), new PersDto(p));
        }

        leesPersoonHistorieGroepen(mapPersDto);
        leesPersVoornaam(mapPersDto);
        leesPersGeslachtsnaamcomp(mapPersDto);
        leesPersNation(mapPersDto);
        leesPersIndicatie(mapPersDto);
        leesPersAdres(mapPersDto);
        return mapPersDto;
    }

    /**
     * Leest pers adres.
     *
     * @param mapPersDto map pers dto
     * @throws IOException IO exception
     */
    private void leesPersAdres(final Map<Integer, PersDto> mapPersDto) throws IOException {
        Map<Integer, PersAdresDto> mapPersAdresDto = new HashMap<Integer, PersAdresDto>();
        // maak een lijst met alle adressen + historie
        List<Persadres> listPersAdres =
                new CsvLoader<Persadres>(path, new CsvProcPersAdres()).leesdata();
        List<HisPersadres> listHisPersadres =
                new CsvLoader<HisPersadres>(path, new CsvProcHisPersadres()).leesdata();

        for (Persadres pa : listPersAdres) {
            mapPersAdresDto.put(pa.getId(), new PersAdresDto(pa));
        }
        for (HisPersadres hpa : listHisPersadres) {
            PersAdresDto pa = mapPersAdresDto.get(hpa.getPersadres());
            if (null != pa) {
                pa.add(hpa);
            }
        }
        // voeg adres list aan pers
        for (Integer paId : mapPersAdresDto.keySet()) {
            PersAdresDto pa = mapPersAdresDto.get(paId);
            PersDto p = mapPersDto.get(pa.getPersadres().getPers());
            if (null != p) {
                p.add(pa);
            }
        }


    }

    /**
     * Leest de pers voornaam.
     *
     * @param mapPersDto map pers dto
     * @throws IOException de IO exception
     */
    private void leesPersVoornaam(final Map<Integer, PersDto> mapPersDto) throws IOException {
        Map<Integer, PersVoornaamDto> mapPersVoornaamDto = new HashMap<Integer, PersVoornaamDto>();
        // maak een lijst met alle adressen + historie
        List<Persvoornaam> listPersVoornaam =
                new CsvLoader<Persvoornaam>(path, new CsvProcPersVoornaam()).leesdata();
        List<HisPersvoornaam> listHisPersvoornaam =
                new CsvLoader<HisPersvoornaam>(path, new CsvProcHisPersvoornaam()).leesdata();

        for (Persvoornaam pa : listPersVoornaam) {
            mapPersVoornaamDto.put(pa.getId(), new PersVoornaamDto(pa));
        }
        for (HisPersvoornaam hpa : listHisPersvoornaam) {
            PersVoornaamDto pa = mapPersVoornaamDto.get(hpa.getPersvoornaam());
            if (null != pa) {
                pa.add(hpa);
            }
        }
        // voeg voornamen list aan pers
        for (Integer paId : mapPersVoornaamDto.keySet()) {
            PersVoornaamDto pa = mapPersVoornaamDto.get(paId);
            PersDto p = mapPersDto.get(pa.getPersvoornaam().getPers());
            if (null != p) {
                p.add(pa);
            }
        }
    }

    /**
     * Leest pers geslachtsnaamcomp.
     *
     * @param mapPersDto map pers dto
     * @throws IOException IO exception
     */
    private void leesPersGeslachtsnaamcomp(final Map<Integer, PersDto> mapPersDto) throws IOException {
        Map<Integer, PersGeslachtsnaamcompDto> mapPersgeslnaamcompDto = new HashMap<Integer, PersGeslachtsnaamcompDto>();
        // maak een lijst met alle adressen + historie
        List<Persgeslnaamcomp> listPersgeslnaamcomp =
                new CsvLoader<Persgeslnaamcomp>(path, new CsvProcPersGeslachtsnaamcomp()).leesdata();
        List<HisPersgeslnaamcomp> listHisPersgeslnaamcomp =
                new CsvLoader<HisPersgeslnaamcomp>(path, new CsvProcHisPersgeslachtsnaamcomp()).leesdata();

        for (Persgeslnaamcomp pa : listPersgeslnaamcomp) {
            mapPersgeslnaamcompDto.put(pa.getId(), new PersGeslachtsnaamcompDto(pa));
        }
        for (HisPersgeslnaamcomp hpa : listHisPersgeslnaamcomp) {
            PersGeslachtsnaamcompDto pa = mapPersgeslnaamcompDto.get(hpa.getPersgeslnaamcomp());
            if (null != pa) {
                pa.add(hpa);
            }
        }
        // voeg voornamen list aan pers
        for (Integer paId : mapPersgeslnaamcompDto.keySet()) {
            PersGeslachtsnaamcompDto pa = mapPersgeslnaamcompDto.get(paId);
            PersDto p = mapPersDto.get(pa.getPersgeslnaamcomp().getPers());
            if (null != p) {
                p.add(pa);
            }
        }
    }

    /**
     * Leest pers nation.
     *
     * @param mapPersDto map pers dto
     * @throws IOException IO exception
     */
    private void leesPersNation(final Map<Integer, PersDto> mapPersDto) throws IOException {
        Map<Integer, PersNationDto> mapPersnationDto = new HashMap<Integer, PersNationDto>();
        // maak een lijst met alle adressen + historie
        List<Persnation> listPersnation =
                new CsvLoader<Persnation>(path, new CsvProcPersNation()).leesdata();
        List<HisPersnation> listHisPersnation =
                new CsvLoader<HisPersnation>(path, new CsvProcHisPersnation()).leesdata();

        for (Persnation pa : listPersnation) {
            mapPersnationDto.put(pa.getId(), new PersNationDto(pa));
        }
        for (HisPersnation hpa : listHisPersnation) {
            PersNationDto pa = mapPersnationDto.get(hpa.getPersnation());
            if (null != pa) {
                pa.add(hpa);
            }
        }
        // voeg voornamen list aan pers
        for (Integer paId : mapPersnationDto.keySet()) {
            PersNationDto pa = mapPersnationDto.get(paId);
            PersDto p = mapPersDto.get(pa.getPersnation().getPers());
            if (null != p) {
                p.add(pa);
            }
        }
    }

    /**
     * Leest pers indicatie.
     *
     * @param mapPersDto map pers dto
     * @throws IOException IO exception
     */
    private void leesPersIndicatie(final Map<Integer, PersDto> mapPersDto) throws IOException {
        // maak een lijst met alle adressen + historie
        List<Persindicatie> listPersindicatie =
            new CsvLoader<Persindicatie>(path, new CsvProcPersIndicatie()).leesdata();
        List<HisPersindicatie> listHisPersindicatie =
            new CsvLoader<HisPersindicatie>(path, new CsvProcHisPersindicatie()).leesdata();

        Map<Integer, PersIndicatieDto> mapPersindicatieDto = new HashMap<Integer, PersIndicatieDto>();
        for (Persindicatie pa : listPersindicatie) {
            mapPersindicatieDto.put(pa.getId(), new PersIndicatieDto(pa));
        }
        for (HisPersindicatie hpa : listHisPersindicatie) {
            PersIndicatieDto pa = mapPersindicatieDto.get(hpa.getPersindicatie());
            if (null != pa) {
                pa.add(hpa);
            }
        }
        // voeg voornamen list aan pers
        for (Integer paId : mapPersindicatieDto.keySet()) {
            PersIndicatieDto pa = mapPersindicatieDto.get(paId);
            PersDto p = mapPersDto.get(pa.getPersindicatie().getPers());
            if (null != p) {
                p.add(pa);
            }
        }
    }

    /**
     * Leest persoon historie groepen.
     *
     * @param mapPersDto map pers dto
     * @throws IOException IO exception
     */
    private void leesPersoonHistorieGroepen(final Map<Integer, PersDto> mapPersDto) throws IOException {
        List<HisPersnaamgebruik> listHisPersnaamgebruik =
                new CsvLoader<>(path, new CsvProcHisPersnaamgebruik()).leesdata();
        List<HisPersbijhouding> listHisPersbijhouding =
                new CsvLoader<>(path, new CsvProcHisPersbijhouding()).leesdata();
        List<HisPersgeboorte> listHisPersgeboorte =
                new CsvLoader<>(path, new CsvProcHisPersgeboorte()).leesdata();
        List<HisPersgeslachtsaand> listHisPersgeslachtsaand =
                new CsvLoader<>(path, new CsvProcHisPersgeslachtsaand()).leesdata();
        List<HisPersids> listHisPersids =
                new CsvLoader<>(path, new CsvProcHisPersids()).leesdata();
        List<HisPersinschr> listHisPersinschr =
                new CsvLoader<>(path, new CsvProcHisPersinschr()).leesdata();
//        List<HisPersopschorting> listHisPersopschorting =
//                new CsvLoader<>(path, new CsvProcHisPersopschorting()).leesdata();
        List<HisPersoverlijden> listHisPersoverlijden =
                new CsvLoader<>(path, new CsvProcHisPersoverlijden()).leesdata();
        List<HisPerssamengesteldenaam> listHisPerssamengesteldenaam =
                new CsvLoader<>(path, new CsvProcHisPerssamengesteldenaam()).leesdata();

        for (HisPersnaamgebruik hpa : listHisPersnaamgebruik) {
            PersDto p = mapPersDto.get(hpa.getPers());
            if (null != p) {
                p.add(hpa);
            }
        }

        for (HisPersbijhouding hpb : listHisPersbijhouding) {
            PersDto p = mapPersDto.get(hpb.getPers());
            if (null != p) {
                p.add(hpb);
            }
        }

        for (HisPersgeboorte hpg : listHisPersgeboorte) {
            PersDto p = mapPersDto.get(hpg.getPers());
            if (null != p) {
                p.add(hpg);
            }
        }

        for (HisPersgeslachtsaand hpg : listHisPersgeslachtsaand) {
            PersDto p = mapPersDto.get(hpg.getPers());
            if (null != p) {
                p.add(hpg);
            }
        }

        for (HisPersids hpi : listHisPersids) {
            PersDto p = mapPersDto.get(hpi.getPers());
            if (null != p) {
                p.add(hpi);
            }
        }

        for (HisPersinschr hpi : listHisPersinschr) {
            PersDto p = mapPersDto.get(hpi.getPers());
            if (null != p) {
                p.add(hpi);
            }
        }

        for (HisPersoverlijden hpo : listHisPersoverlijden) {
            PersDto p = mapPersDto.get(hpo.getPers());
            if (null != p) {
                p.add(hpo);
            }
        }

        for (HisPerssamengesteldenaam hpo : listHisPerssamengesteldenaam) {
            PersDto p = mapPersDto.get(hpo.getPers());
            if (null != p) {
                p.add(hpo);
            }
        }
    }

    /**
     * Leest relatie.
     *
     * @return the map
     * @throws IOException IO exception
     */
    public Map<Integer, RelatieDto> leesRelatie() throws IOException {
        Map<Integer, RelatieDto> mapRelatieDto = new HashMap<Integer, RelatieDto>();
        List<Relatie> listRelatie = new CsvLoader<Relatie>(path, new CsvProcRelatie()).leesdata();
        List<HisHuwelijkgeregistreerdpar>  listHisHuwelijkgeregistreerdpar =
                new CsvLoader<HisHuwelijkgeregistreerdpar>(path, new CsvProcHisHuwelijkgeregistreerdpar()).leesdata();

        for (Relatie r : listRelatie) {
            mapRelatieDto.put(r.getId(), new RelatieDto(r));
        }
        for (HisHuwelijkgeregistreerdpar hpo : listHisHuwelijkgeregistreerdpar) {
            RelatieDto r = mapRelatieDto.get(hpo.getRelatie());
            if (null != r) {
                r.add(hpo);
            }
        }
        // TODO HisNaamkeuzeongeborenvrucht
        // TODO HisErkenningongeborenvrucht

        List<Betr> listBetr = new CsvLoader<Betr>(path, new CsvProcBetr()).leesdata();
        List<HisOuderouderlijkgezag> listOuderouderlijkgezag =
                new CsvLoader<HisOuderouderlijkgezag>(path, new CsvProcHisOuderouderlijkgezag()).leesdata();
        List<HisOuderouderschap> listOuderouderschap =
                new CsvLoader<HisOuderouderschap>(path, new CsvProcHisOuderouderschap()).leesdata();

        Map<Integer, BetrDto> mapBetrDto = new HashMap<Integer, BetrDto>();

        for (Betr b : listBetr) {
            mapBetrDto.put(b.getId(), new BetrDto(b));
        }

        for (HisOuderouderlijkgezag hpo : listOuderouderlijkgezag) {
            BetrDto b = mapBetrDto.get(hpo.getBetr());
            if (null != b) {
                b.add(hpo);
            }
        }

        for (HisOuderouderschap hpo : listOuderouderschap) {
            BetrDto b = mapBetrDto.get(hpo.getBetr());
            if (null != b) {
                b.add(hpo);
            }
        }

        // voeg nu betrokhenheden aan de relatie
        for (Integer bId : mapBetrDto.keySet()) {
            BetrDto b = mapBetrDto.get(bId);
            RelatieDto r = mapRelatieDto.get(b.getBetr().getRelatie());
            if (null != r) {
                r.add(b);
            }
        }

        return mapRelatieDto;
    }

}
