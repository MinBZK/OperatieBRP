/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.helper.brpbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.testdatageneratie.csv.dto.ArtHuishoudingDto;
import nl.bzk.brp.testdatageneratie.csv.dto.ArtPersDto;
import nl.bzk.brp.testdatageneratie.csv.dto.BetrDto;
import nl.bzk.brp.testdatageneratie.csv.dto.RelatieDto;
import nl.bzk.brp.testdatageneratie.dataaccess.PersonenIds;
import nl.bzk.brp.testdatageneratie.datagenerators.AbstractBasicGenerator;
import nl.bzk.brp.testdatageneratie.domain.brp.BrpRelatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisErkenningongeborenvrucht;
import nl.bzk.brp.testdatageneratie.domain.kern.HisHuwelijkgeregistreerdpar;
import nl.bzk.brp.testdatageneratie.domain.kern.HisMaterieel;
import nl.bzk.brp.testdatageneratie.domain.kern.HisNaamskeuzeongeborenvruch;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOuderouderlijkgezag;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOuderouderschap;
import nl.bzk.brp.testdatageneratie.domain.kern.IntegerAsPrimaryKey;
import nl.bzk.brp.testdatageneratie.domain.kern.LongAsPrimaryKey;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;
import nl.bzk.brp.testdatageneratie.utils.Constanten;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import nl.bzk.brp.testdatageneratie.utils.SequenceUtil;

import org.hibernate.Session;

/**
 * Brp relatie builder.
 */
public class BrpRelatieBuilder {

    /**
     * Soort huwelijk.
     */
    public static final Short SRT_HUWELIJK =
            ((Integer) SoortRelatie.HUWELIJK.ordinal()).shortValue();
    /**
     * Soort geregistreerd partnerschap.
     */
    public static final Short SRT_GEREGPARTNER =
            ((Integer) SoortRelatie.GEREGISTREERD_PARTNERSCHAP.ordinal()).shortValue();
    /**
     * Soort familierechtelijke betrekking.
     */
    public static final Short SRT_FAMRECHT_BETR =
            ((Integer) SoortRelatie.FAMILIERECHTELIJKE_BETREKKING.ordinal()).shortValue();
    /**
     * Soort erkenning ov.
     */
    public static final Short SRT_ERKENNING_OV = 4;
    /**
     * Soort ontkenning ov.
     */
    public static final Short SRT_ONTKENNNING_OV = 5;
    /**
     * Soort naamskeuze.
     */
    public static final Short SRT_NAAMKEUZE = 6;

    /**
     * Rol kind.
     */
    public static final Short ROL_KIND = 1;
    /**
     * Rol ouder.
     */
    public static final Short ROL_OUDER = 2;
    /**
     * Rol partner.
     */
    public static final Short ROL_PARTNER = 3;

    /**
     * Brp huishouding.
     */
    protected final ArtHuishoudingDto brpHuishouding;
    /**
     * Map relatie dto, shortcut.
     */
    protected final Map<Integer, RelatieDto> mapRelatieDto;
    /**
     * Rel ids, makkelijkere array om er door te lopen.
     */
    protected final Integer[] relIds;

    /**
     * Aantal threads.
     */
    protected final int numberOfThreads;

    private int currentId = 0;
    private Integer setNr = 0;
    private boolean eindeData = false;

    private final PersonenIds persoonIds;
    private final AbstractBasicGenerator owner;
    private final Session kernSession;

    /**
     * Instantieert Brp relatie builder.
     *
     * @param owner owner
     * @param kernSession kern session
     * @param brpHuishouding brp huishouding
     * @param threadIndex thread index
     * @param numberOfThreads aantal threads
     * @param persoonIds persoon ids
     */
    public BrpRelatieBuilder(final AbstractBasicGenerator owner, final Session kernSession,
            final ArtHuishoudingDto brpHuishouding, final int threadIndex, final int numberOfThreads,
            final PersonenIds persoonIds)
    {
        this.owner = owner;
        this.kernSession = kernSession;
        this.brpHuishouding = brpHuishouding;
        this.numberOfThreads = numberOfThreads;
        this.persoonIds = persoonIds;
        setNr = threadIndex;
        // shortcut
        mapRelatieDto = brpHuishouding.getArtRelatiesTemplateDto();
        relIds = mapRelatieDto.keySet().toArray(new Integer[mapRelatieDto.keySet().size()]);
    }

    /**
     * Voeg huwelijk toe.
     *
     * @return relatie id
     */
    public Integer voegHuwelijkToe() {
        return voegRelatieToe(SRT_HUWELIJK);
    }

    /**
     * Voeg geregistreerd partnerschap toe.
     *
     * @return relatie id
     */
    public Integer voegGeregistreerdPartnerschapToe() {
        return voegRelatieToe(SRT_GEREGPARTNER);
    }

    /**
     * Voeg huwelijk en geregistreerd partnerschap toe.
     *
     * @return relatie id
     */
    public Integer voegHuwelijkEnGeregistreerdPartnerschapToe() {
        return voegRelatieToe(SRT_HUWELIJK, SRT_GEREGPARTNER);
    }

    /**
     * Voeg alle relaties behalve familie toe.
     *
     * @return relatie id
     */
    public Integer voegAlleRelatiesBehalveFamilieToe() {
        return voegRelatieToe(SRT_HUWELIJK, SRT_GEREGPARTNER, SRT_ERKENNING_OV, SRT_ONTKENNNING_OV, SRT_NAAMKEUZE);
    }

    /**
     * Voeg een familie toe, de volledige fam.rech.betr. wordt uit de template gehaald.
     * Er wordt  in principe geen personen toegeveoegd.
     * BEHALVE: aan het  van een set wordt gekeken, welk personen zijn geen kinderen van andere personen in de template.
     * Van deze personen worden dan AdamEvas aangemaakt.
     * En dan wordt het aantal AdamEvas teruggegeven.
     * De reden dat we dit aantal bijhouden, omdat dit aantal significant is. Als de helft van de personen in de
     * template geen kind is, worden 2x zoveel AdamEvas aangemaakt (dus 100% template personen + 100% AdamEvas !!)
     * @return aantal personene toegevoegd.
     */
    public Integer voegFamilieToe() {
        return voegRelatieToe(SRT_FAMRECHT_BETR);
    }

    /**
     * Voeg erkenning ongeboren vrucht toe.
     *
     * @return id
     */
    public Integer voegErkenningOngeborenVruchtToe() {
        return voegRelatieToe(SRT_ERKENNING_OV);
    }

    /**
     * Voeg ontkenning ongeboren vrucht toe.
     *
     * @return id
     */
    public Integer voegOntkenningOngeborenVruchtToe() {
        return voegRelatieToe(SRT_ONTKENNNING_OV);
    }

    /**
     * Voeg naamkeuze toe.
     *
     * @return id
     */
    public Integer voegNaamkeuzeToe() {
        return voegRelatieToe(SRT_NAAMKEUZE);
    }

    /**
     * Voeg rest of huidig set toe.
     *
     * @param srt srt
     */
    public void voegRestOfHuidigSetToe(final Short... srt) {
        if (!eindeData) {
            List<Short> soorten = new ArrayList<Short>();
            for (Short s : srt) {
                soorten.add(s);
            }
            while (currentId < relIds.length) {
                RelatieDto relatieDto = vindVolgendeRelatie(soorten);
                if (null != relatieDto) {
                    voegRelatieDaadwerkelijkToe(relatieDto);
                }
            }
        }
    }

    /**
     * Voeg relatie toe.
     *
     * @param srt srt
     * @return integer
     */
    protected Integer voegRelatieToe(final Short ... srt) {
        if (!eindeData) {
            Relatie relatie = null;
            List<Short> soorten = new ArrayList<Short>();
            for (Short s : srt) {
                soorten.add(s);
            }
            RelatieDto relatieDto = vindVolgendeRelatie(soorten);
            if (null == relatieDto) {
                eindeData = true;
            } else {
                // we moeten eerst testen of de personen aanwezig zijn voordat we doorgaan.
                for (BetrDto betrDto : relatieDto.getBetr()) {
                    if (null == brpHuishouding.getPersId(setNr, betrDto.getLogischePersId())) {
                        eindeData = true;
                        break;
                    }
                }
                if (!eindeData) {
                    return voegRelatieDaadwerkelijkToe(relatieDto);
                }
            }
        }
        return null;
    }

    /**
     * Voeg relatie daadwerkelijk toe.
     *
     * @param relatieDto relatie dto
     * @return relatie id
     */
    private Integer voegRelatieDaadwerkelijkToe(final RelatieDto relatieDto) {
        final Relatie relatie = relatieDto.getRelatie();

        detachAndSave(relatie, SequenceUtil.getMax(Relatie.class.getSimpleName()).intValue());
        final BrpRelatie brpRelatie = new BrpRelatie(relatie.getId(), setNr, relatie.getSrt(), relIds[currentId]);
        kernSession.save(brpRelatie);

        // OK, nu doe de rest
        for (final HisHuwelijkgeregistreerdpar hrh : relatieDto.getHisHuwelijkgeregistreerdpar()) {
            hrh.setRelatie(relatie.getId());
            voegHisObjectToe(hrh, SequenceUtil.getMax(HisHuwelijkgeregistreerdpar.class.getSimpleName()).intValue());
        }
        for (final HisErkenningongeborenvrucht hre : relatieDto.getHisErkenningongeborenvrucht()) {
            hre.setRelatie(relatie.getId());
            voegHisObjectToe(hre, SequenceUtil.getMax(HisErkenningongeborenvrucht.class.getSimpleName()).intValue());
        }
        for (final HisNaamskeuzeongeborenvruch hrn : relatieDto.getHisNaamskeuzeongeborenvruch()) {
            hrn.setRelatie(relatie.getId());
            voegHisObjectToe(hrn, SequenceUtil.getMax(HisNaamskeuzeongeborenvruch.class.getSimpleName()).intValue());
        }
        for (final BetrDto betr : relatieDto.getBetr()) {
            voegBetrokkenheidToe(betr, relatie.getId());
        }
        currentId++;

        Integer relatieId = null;
        if (relatie != null) {
            relatieId = relatie.getId();
        }
        return relatieId;
    }

    /**
     * Vind volgende relatie.
     *
     * @param soorten soorten
     * @return relatie dto
     */
    private RelatieDto vindVolgendeRelatie(final List<Short> soorten) {
        RelatieDto relatieDto = null;
        while (relatieDto == null) {
            if (currentId >= relIds.length) {
                currentId = 0;
                // in geval van familie, vul alle andere personen die GEEN kind is, Adam en Eva als ouders.
                if (soorten.contains(SRT_FAMRECHT_BETR)) {
                    voegToeAlleAdamEnEvas();
                }
                setNr += numberOfThreads;
            }
            relatieDto = mapRelatieDto.get(relIds[currentId]);
            if (!soorten.contains(relatieDto.getRelatie().getSrt())) {
                currentId++;
                relatieDto = null;
            }
        }
        return relatieDto;
    }

    /**
     * Voeg toe alle adam en evas.
     */
    private void voegToeAlleAdamEnEvas() {
        final List<Integer> kinderen = new ArrayList<>();
        for (final Integer relId : relIds) {
            final RelatieDto relatieDto = mapRelatieDto.get(relId);
            if (relatieDto.getRelatie().getSrt() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING.ordinal()) {
                for (final BetrDto betrDto : relatieDto.getBetr()) {
                    if (betrDto.getBetr().getRol() == SoortBetrokkenheid.KIND.ordinal()) {
                        kinderen.add(betrDto.getLogischePersId());
                    }
                }
            }
        }

        final List<ArtPersDto> list = brpHuishouding.getArtPersonenDto().get(setNr);
        if (null != list) {
            for (final ArtPersDto brpPersDto : list) {
                if (!kinderen.contains(brpPersDto.getLogischPersId())
                        && !Constanten.NIET_INGEZETEN_LOGISCHE_PERSOON_IDS.contains(brpPersDto.getLogischPersId())) {
                    voegAdamEvaVoorKind(kernSession, brpPersDto.getLogischPersId());
                }
            }
        }
    }


    /**
     * Voeg betrokkenheid toe.
     *
     * @param betrDto betr dto
     * @param relatieId relatie id
     */
    private void voegBetrokkenheidToe(final BetrDto betrDto, final Integer relatieId) {
        final Betr betr = betrDto.getBetr();
        betr.setRelatie(relatieId);
        betr.setPers(brpHuishouding.getPersId(setNr, betrDto.getLogischePersId()));
//        if (null == betr.getPers()) {
//            throw new RuntimeException(
//                    "Kan geen persoon record vinden voor set " + setNr + " logpersid " + betrDto.getLogischePersId());
//        }
        detachAndSave(betr, SequenceUtil.getMax(Betr.class.getSimpleName()).intValue());

        for (HisOuderouderlijkgezag hbo : betrDto.getHisOuderouderlijkgezag()) {
            hbo.setBetr(betr.getId());
            voegHisObjectToe(hbo, SequenceUtil.getMax(HisOuderouderlijkgezag.class.getSimpleName()).intValue());
        }
        for (HisOuderouderschap hbo : betrDto.getHisOuderouderschap()) {
            hbo.setBetr(betr.getId());
            voegHisObjectToe(hbo, SequenceUtil.getMax(HisOuderouderschap.class.getSimpleName()).intValue());
        }
    }


    /**
     * Voeg his object toe.
     *
     * @param hisObject his object
     * @param id id
     */
    private void voegHisObjectToe(final His hisObject, final Integer id) {
        if (null != hisObject.getActieinh()) {
            // new nr.
            hisObject.setActieinh(RandomUtil.getActie());
        }
        if (null != hisObject.getActieverval()) {
            // new nr.
            hisObject.setActieverval(RandomUtil.getActie());
        }
        if (hisObject instanceof HisMaterieel) {
            HisMaterieel hm = (HisMaterieel) hisObject;
            if (hm.getActieaanpgel() != null) {
                hm.setActieaanpgel(RandomUtil.getActie());
            }
        }
        
        
        if (hisObject instanceof IntegerAsPrimaryKey) {
            detachAndSave((IntegerAsPrimaryKey)hisObject, id);
        } else if (hisObject instanceof LongAsPrimaryKey) {
            detachAndSave((LongAsPrimaryKey)hisObject, new Long(id));
        } else {
            throw new RuntimeException("Object implementing His must implement IntegerAsPrimaryKey of LongAsPrimaryKey");
        }


    }

    /**
     * Detach en sla op.
     *
     * @param object object
     * @param id id
     */
    private void detachAndSave(final IntegerAsPrimaryKey object, final Integer id) {
        kernSession.evict(object);
        object.setId(id);
        kernSession.save(object);
    }

    private void detachAndSave(final LongAsPrimaryKey object, final Long id) {
        kernSession.evict(object);
        object.setId(id);
        kernSession.save(object);
    }
    
    /**
     * Creer adam of eva.
     *
     * @param voornaam voornaam
     * @param geslachtsaand geslachtsaand
     * @return pers
     */
    private Pers creerAdamEva(final String voornaam, final short geslachtsaand) {
        final Pers nietIngezetene = new Pers();
        // niet ingeschreve
        nietIngezetene.setSrt((short) SoortPersoon.NIET_INGESCHREVENE.ordinal());
        nietIngezetene.setGeslachtsaand(geslachtsaand);
        nietIngezetene.setDatinschr(Constanten.ADAM_EVA_DATUM_AANVANG_RELATIE);
        nietIngezetene.setDatgeboorte(Constanten.ADAM_EVA_DATUM_AANVANG_RELATIE);
        nietIngezetene.setLandgebiedgeboorte(Constanten.VIJF);
        nietIngezetene.setIndafgeleid(true);
        nietIngezetene.setIndnreeks(false);
        nietIngezetene.setVoornamen(voornaam);
        nietIngezetene.setGeslnaamstam("Modernodam");
        nietIngezetene.setIndonverwdocaanw(false);
        return nietIngezetene;
    }

    /**
     * Voeg adam eva toe voor kind.
     *
     * @param kernSessionParam kern session param
     * @param kindId kind id
     */
    private void voegAdamEvaVoorKind(final Session kernSessionParam, final Integer kindId) {
        Pers adam = creerAdamEva("Adam-" + kindId, (short) Constanten.EEN);
        adam.setId(SequenceUtil.getMax(Persoon.class.getSimpleName()).intValue());
        kernSessionParam.save(adam);

        Pers eva = creerAdamEva("Eva-" + kindId, (short) Constanten.TWEE);
        eva.setId(SequenceUtil.getMax(Persoon.class.getSimpleName()).intValue());
        kernSessionParam.save(eva);

        Relatie r = new Relatie();
        r.setDataanv(Constanten.ADAM_EVA_DATUM_AANVANG_RELATIE);
        r.setSrt(SRT_FAMRECHT_BETR);
        r.setId(SequenceUtil.getMax(Relatie.class.getSimpleName()).intValue());
        kernSessionParam.save(r);

        Betr bVader = new Betr();
        bVader.setRol(ROL_OUDER);
        bVader.setId(SequenceUtil.getMax(Betr.class.getSimpleName()).intValue());
        bVader.setRelatie(r.getId());
        bVader.setPers(adam.getId());
//        bVader.setPers((int) persoonIds.selecteerIdBehalve(kindId.longValue()));

        kernSessionParam.save(bVader);

        Betr bMoeder = new Betr();
        bMoeder.setRol(ROL_OUDER);
        bMoeder.setId(SequenceUtil.getMax(Betr.class.getSimpleName()).intValue());
        bMoeder.setRelatie(r.getId());
        bMoeder.setPers(eva.getId());
//        bMoeder.setPers((int) persoonIds.selecteerIdBehalve(kindId.longValue(), bVader.getId().longValue()));
        kernSessionParam.save(bMoeder);

        Betr bKind = new Betr();
        bKind.setRol(ROL_KIND);
        bKind.setId(SequenceUtil.getMax(Betr.class.getSimpleName()).intValue());
        bKind.setPers(kindId);
        bKind.setRelatie(r.getId());
        kernSessionParam.save(bKind);
        kernSessionParam.flush();
    }

}
