/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.helper.brpbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.testdatageneratie.csv.dto.ArtHuishoudingDto;
import nl.bzk.brp.testdatageneratie.csv.dto.ArtPersDto;
import nl.bzk.brp.testdatageneratie.csv.dto.PersAdresDto;
import nl.bzk.brp.testdatageneratie.csv.dto.PersDto;
import nl.bzk.brp.testdatageneratie.csv.dto.PersGeslachtsnaamcompDto;
import nl.bzk.brp.testdatageneratie.csv.dto.PersIndicatieDto;
import nl.bzk.brp.testdatageneratie.csv.dto.PersNationDto;
import nl.bzk.brp.testdatageneratie.csv.dto.PersVoornaamDto;
import nl.bzk.brp.testdatageneratie.datagenerators.PersoonGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersAdresGenerator;
import nl.bzk.brp.testdatageneratie.domain.brp.BrpPers;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisMaterieel;
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
import nl.bzk.brp.testdatageneratie.domain.kern.IntegerAsPrimaryKey;
import nl.bzk.brp.testdatageneratie.domain.kern.LongAsPrimaryKey;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Persadres;
import nl.bzk.brp.testdatageneratie.domain.kern.Persgeslnaamcomp;
import nl.bzk.brp.testdatageneratie.domain.kern.Persindicatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Persnation;
import nl.bzk.brp.testdatageneratie.domain.kern.Persvoornaam;
import nl.bzk.brp.testdatageneratie.helper.RandomBsnHelper;
import nl.bzk.brp.testdatageneratie.utils.Constanten;
import nl.bzk.brp.testdatageneratie.utils.PersAdresUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import nl.bzk.brp.testdatageneratie.utils.SequenceUtil;

import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * Brp persoon builder.
 */
public class BrpPersoonBuilder {
    private static Logger log = Logger.getLogger(BrpPersoonBuilder.class);

    public final RandomBsnHelper randomBSNService;
    private final ArtHuishoudingDto brpHuishouding;
    // shortcut
    private final Map<Integer, PersDto> mapPersDto;
    // makkelijkere array om er door te lopen.
    private final Integer[] pIds;
    private final int numberOfThreads;

    private int currentId = 0;
    private Integer setNr = 0;

    private final PersoonGenerator owner;

    private final int maxSetNr;

    /**
     * Instantieert een Brp persoon builder, deze bouwt personen grootendeels op basis van een Excel.
     *
     * @param owner persoongenerator
     * @param randomBSNService random BSN service
     * @param brpHuishouding brp huishouding
     * @param threadIndex thread index
     * @param numberOfThreads aantal threads
     * @param maxSetNr max set nr
     */
    public BrpPersoonBuilder(final PersoonGenerator owner, final RandomBsnHelper randomBSNService,
            final ArtHuishoudingDto brpHuishouding, final int threadIndex,
            final int numberOfThreads, final int maxSetNr)
    {
        this.owner = owner;
        this.randomBSNService = randomBSNService;
        this.brpHuishouding = brpHuishouding;
        this.numberOfThreads = numberOfThreads;
        setNr = threadIndex;
        this.maxSetNr = maxSetNr;
        // shortcut
        mapPersDto = brpHuishouding.getArtPersonenTemplateDto();
        pIds = brpHuishouding.getArtPersonenTemplateDto().keySet()
                .toArray(new Integer[brpHuishouding.getArtPersonenTemplateDto().keySet().size()]);
    }

    /**
     * Voegt rest of huidig set to.
     *
     * @param kernSession the kern session
     */
    public void voegRestOfHuidigSetTo(final Session kernSession) {
        if (setNr < maxSetNr) {
            while (currentId < pIds.length) {
                voegPersoonToe(kernSession);
            }
        }
    }

    /**
     * Voegt persoon toe.
     *
     * @param kernSession kern session
     * @return persoon id
     */
    public Integer voegPersoonToe(final Session kernSession) {
        if (currentId >= pIds.length) {
            currentId = 0;
            setNr += numberOfThreads;
        }
        if (setNr >= maxSetNr) {
            // bovenlimiet van set is bereikt, hoeft niet verder.
            return null;
        }
        final PersDto persDto = mapPersDto.get(pIds[currentId]);
        final Pers pers = persDto.getPers();

        pers.setBsn(randomBSNService.randomBsn());

        if (null != pers.getAnr()) {
            pers.setAnr(randomBSNService.randomAnr());
        }
        detachAndSave(kernSession, pers, SequenceUtil.getMax(Persoon.class.getSimpleName()).intValue());
        addPersoonAanBijhouding(setNr, pIds[currentId], pers.getId());
        final BrpPers brpPers = new BrpPers(pers.getId(), setNr, pIds[currentId], pers.getBsn());
        kernSession.save(brpPers);

        // OK, nu doe de rest

        for (final HisPersbijhouding hpb : persDto.getHispersbijhouding()) {
            hpb.setPers(pers.getId());
            voegHisObjectToe(kernSession, hpb, SequenceUtil.getMax(HisPersbijhouding.class.getSimpleName()).intValue());
        }
        for (final HisPersgeboorte hpg : persDto.getHispersgeboorte()) {
            hpg.setPers(pers.getId());
            voegHisObjectToe(kernSession, hpg, SequenceUtil.getMax(HisPersgeboorte.class.getSimpleName()).intValue());
        }
        for (final HisPersgeslachtsaand hpg : persDto.getHispersgeslachtsaand()) {
            hpg.setPers(pers.getId());
            voegHisObjectToe(kernSession, hpg, SequenceUtil.getMax(HisPersgeslachtsaand.class.getSimpleName())
                    .intValue());
        }
        for (final HisPersids hpi : persDto.getHispersids()) {
            hpi.setPers(pers.getId());
            hpi.setBsn(pers.getBsn());
            hpi.setAnr(pers.getAnr());
            voegHisObjectToe(kernSession, hpi, SequenceUtil.getMax(HisPersids.class.getSimpleName()).intValue());
        }
        for (final HisPersinschr hpi : persDto.getHispersinschr()) {
            hpi.setPers(pers.getId());
            voegHisObjectToe(kernSession, hpi, SequenceUtil.getMax(HisPersinschr.class.getSimpleName()).intValue());
        }
        for (final HisPersnaamgebruik hpa : persDto.getHispersnaamgebruik()) {
            hpa.setPers(pers.getId());
            voegHisObjectToe(kernSession, hpa, SequenceUtil.getMax(HisPersnaamgebruik.class
                                                                           .getSimpleName()).intValue());
        }
        for (final HisPersoverlijden hpo : persDto.getHispersoverlijden()) {
            hpo.setPers(pers.getId());
            voegHisObjectToe(kernSession, hpo, SequenceUtil.getMax(HisPersoverlijden.class.getSimpleName()).intValue());
        }
        for (final HisPerssamengesteldenaam hpo : persDto.getHisperssamengesteldenaam()) {
            hpo.setPers(pers.getId());
            voegHisObjectToe(kernSession, hpo, SequenceUtil.getMax(HisPerssamengesteldenaam.class.getSimpleName())
                    .intValue());
        }
        for (final PersVoornaamDto pv : persDto.getPersvoornaam()) {
            voegVoornaamToe(kernSession, pv, pers.getId());
        }
        for (final PersGeslachtsnaamcompDto pg : persDto.getPersgeslachtnaamcomp()) {
            voegGeslachtsnaamcompToe(kernSession, pg, pers.getId());
        }
        for (final PersAdresDto pa : persDto.getPersadres()) {
            voegAdresToe(kernSession, pa, pers.getId());
        }
        for (final PersNationDto pn : persDto.getPersnation()) {
            voegNationToe(kernSession, pn, pers.getId());
        }
        for (final PersIndicatieDto pi : persDto.getPersindicatie()) {
            voegIndicatieToe(kernSession, pi, pers.getId());
        }

        // TODO andere 1-n relaties zoals
        // reisdocumenten, ....
        currentId++;
        return pers.getId();
    }

    /**
     * Add persoon aan bijhouding.
     *
     * @param sesNr ses nr
     * @param logischPersId logisch pers id
     * @param persId pers id
     */
    private void addPersoonAanBijhouding(final Integer sesNr, final Integer logischPersId, final Integer persId) {
        List<ArtPersDto> list = brpHuishouding.getArtPersonenDto().get(sesNr);
        if (null == list) {
            list = new ArrayList<ArtPersDto>();
            brpHuishouding.getArtPersonenDto().put(sesNr, list);
        }
        list.add(new ArtPersDto(logischPersId, persId));
    }

    /**
     * Voeg voornaam toe.
     *
     * @param kernSession kern session
     * @param persvoornaamDto persvoornaam dto
     * @param persId pers id
     */
    private void voegVoornaamToe(final Session kernSession,
                                 final PersVoornaamDto persvoornaamDto,
                                 final Integer persId)
    {
        final Persvoornaam persvoornaam = persvoornaamDto.getPersvoornaam();
        persvoornaam.setPers(persId);
        detachAndSave(kernSession, persvoornaam, SequenceUtil.getMax(Persvoornaam.class.getSimpleName()).intValue());

        for (final HisPersvoornaam hpv : persvoornaamDto.getHispersvoornaam()) {
            hpv.setPersvoornaam(persvoornaam.getId());
            voegHisObjectToe(kernSession, hpv, SequenceUtil.getMax(HisPersvoornaam.class.getSimpleName()).intValue());
        }
    }

    /**
     * Voeg geslachtsnaamcomp toe.
     *
     * @param kernSession kern session
     * @param persgeslachtsnaamcompDto persgeslachtsnaamcomp dto
     * @param persId pers id
     */
    private void voegGeslachtsnaamcompToe(final Session kernSession,
                                          final PersGeslachtsnaamcompDto persgeslachtsnaamcompDto,
                                          final Integer persId)
    {
        final Persgeslnaamcomp persgeslachtsnaamcomp = persgeslachtsnaamcompDto.getPersgeslnaamcomp();
        persgeslachtsnaamcomp.setPers(persId);
        detachAndSave(kernSession, persgeslachtsnaamcomp, SequenceUtil.getMax(Persgeslnaamcomp.class.getSimpleName())
                .intValue());

        for (final HisPersgeslnaamcomp hpg : persgeslachtsnaamcompDto.getHispersgeslnaamcomp()) {
            hpg.setPersgeslnaamcomp(persgeslachtsnaamcomp.getId());
            voegHisObjectToe(kernSession, hpg, SequenceUtil.getMax(HisPersgeslnaamcomp.class.getSimpleName())
                    .intValue());
        }
    }

    /**
     * Voeg adres toe.
     *
     * @param kernSession kern session
     * @param persadresDto persadres dto
     * @param persId pers id
     */
    private void voegAdresToe(final Session kernSession, final PersAdresDto persadresDto, final Integer persId) {
        // We gebruiken synthetische adressen
        boolean gebruikAdressenUitExcel = false;

        if (gebruikAdressenUitExcel) {
            voegAdresToeUitExcel(kernSession, persadresDto, persId);
        } else {
            voegSynthetischAdresToe(kernSession, persId);
        }
    }

    /**
     * Voeg adres toe uit excel.
     *
     * @param kernSession kern session
     * @param persadresDto persadres dto
     * @param persId pers id
     */
    private void voegAdresToeUitExcel(final Session kernSession, final PersAdresDto persadresDto,
                                      final Integer persId)
    {
        final Persadres persAdres = persadresDto.getPersadres();

        persAdres.setPers(persId);
        persAdres.setIdentcodeadresseerbaarobject(RandomUtil.getZestienCijfersAsString());
        persAdres.setIdentcodenraand(RandomUtil.getZestienCijfersAsString());
        persAdres.setHuisnr((setNr * Constanten.HONDERD + persadresDto.getOrigineleHuisnr()) % Constanten.HONDERDDUIZEND);
        persAdres.setHuisnrtoevoeging("" + setNr);

        for (final HisPersadres hpa : persadresDto.getHispersadres()) {
            hpa.setPersadres(persAdres.getId());
            hpa.setIdentcodeadresseerbaarobject(persAdres.getIdentcodeadresseerbaarobject());
            hpa.setIdentcodenraand(persAdres.getIdentcodenraand());
            voegHisObjectToe(kernSession, hpa, SequenceUtil.getMax(HisPersadres.class.getSimpleName()).intValue());
        }
    }

    /**
     * Voeg synthetisch adres toe.
     *
     * @param kernSession kern session
     * @param persId pers id
     */
    private void voegSynthetischAdresToe(final Session kernSession, final Integer persId) {
        final Persadres persAdres = PersAdresUtil.generatePersAdres(persId);
        detachAndSave(kernSession, persAdres, SequenceUtil.getMax(Persadres.class.getSimpleName()).intValue());

        final Integer dataanvHuidig = persAdres.getDataanvadresh();
        final List<HisPersadres> hisPersAdressen = HisPersAdresGenerator.getInstance()
                .generateHisMaterieels(persAdres, dataanvHuidig);
        if (hisPersAdressen.size() == 1) {
            persAdres.setRdnwijz(null);
        }

        kernSession.save(persAdres);

        for (final HisPersadres hisPersadres : hisPersAdressen) {
            hisPersadres.setId(SequenceUtil.getMax(HisPersadres.class.getSimpleName()).intValue());
            kernSession.save(hisPersadres);
        }
    }

    /**
     * Voeg nation toe.
     *
     * @param kernSession kern session
     * @param persnationDto persnation dto
     * @param persId pers id
     */
    private void voegNationToe(final Session kernSession, final PersNationDto persnationDto, final Integer persId) {
        final Persnation persnation = persnationDto.getPersnation();
        persnation.setPers(persId);
        detachAndSave(kernSession, persnation, SequenceUtil.getMax(Persnation.class.getSimpleName()).intValue());

        for (final HisPersnation hpa : persnationDto.getHispersnation()) {
            hpa.setPersnation(persnation.getId());
            voegHisObjectToe(kernSession, hpa, SequenceUtil.getMax(HisPersnation.class.getSimpleName()).intValue());
        }
    }

    /**
     * Voeg indicatie toe.
     *
     * @param kernSession kern session
     * @param persindicatieDto persindicatie dto
     * @param persId pers id
     */
    private void voegIndicatieToe(final Session kernSession, final PersIndicatieDto persindicatieDto,
                                  final Integer persId)
    {
        final Persindicatie persindicatie = persindicatieDto.getPersindicatie();
        persindicatie.setPers(persId);
        detachAndSave(kernSession, persindicatie, SequenceUtil.getMax(Persindicatie.class.getSimpleName()).intValue());

        for (final HisPersindicatie hpi : persindicatieDto.getHispersindicatie()) {
            hpi.setPersindicatie(persindicatie.getId());
            voegHisObjectToe(kernSession, hpi, SequenceUtil.getMax(HisPersindicatie.class.getSimpleName()).intValue());
        }
    }

    /**
     * Voeg his object toe.
     *
     * @param kernSession kern session
     * @param hisObject his object
     * @param id id
     */
    private void voegHisObjectToe(final Session kernSession, final His hisObject, final Integer id) {
        if (null != hisObject.getActieinh()) {
            // new nr.
            hisObject.setActieinh(RandomUtil.getActie());
        }
        if (null != hisObject.getActieverval()) {
            // new nr.
            hisObject.setActieverval(RandomUtil.getActie());
        }
        if (hisObject instanceof HisMaterieel) {
            final HisMaterieel hm = (HisMaterieel) hisObject;
            if (hm.getActieaanpgel() != null) {
                hm.setActieaanpgel(RandomUtil.getActie());
            }
        }
        
        if (hisObject instanceof IntegerAsPrimaryKey) {
            detachAndSave(kernSession, (IntegerAsPrimaryKey)hisObject, id);
        } else if (hisObject instanceof LongAsPrimaryKey) {
            detachAndSave(kernSession, (LongAsPrimaryKey)hisObject, new Long(id));
        } else {
            throw new RuntimeException("Object implementing His must implement IntegerAsPrimaryKey of LongAsPrimaryKey");
        }

    }

    /**
     * Detach en sla op.
     *
     * @param kernSession kern session
     * @param object object
     * @param id id
     */
    private void detachAndSave(final Session kernSession, final IntegerAsPrimaryKey object, final Integer id) {
        kernSession.evict(object);
        object.setId(id);
        kernSession.save(object);
    }

    private void detachAndSave(final Session kernSession, final LongAsPrimaryKey object, final Long id) {
        kernSession.evict(object);
        object.setId(id);
        kernSession.save(object);
    }

    
//    public void dumpBrpPersDto(BrpHuishoudingDto brpHuishouding) {
//        for (Integer sesNr : brpHuishouding.getMapBrpPersDto().keySet()) {
//            List<BrpPersDto> list = brpHuishouding.getMapBrpPersDto().get(setNr);
//            if (null != list) {
//                for (BrpPersDto brpPersDto : list) {
//                    System.out.println("Set [" + setNr + "] BrpPersDto = " + brpPersDto.logischPersId
// +","+brpPersDto.persId);
//                }
//            }
//
//        }
//
//    }
}
