/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;
import static nl.bzk.brp.testdatageneratie.utils.RelatieUtil.creeerRelatie;
import static nl.bzk.brp.testdatageneratie.utils.RelatieUtil.opslaanBetrokkenheidMetHistorie;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.testdatageneratie.csv.dto.ArtHuishoudingDto;
import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.PersonenIds;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisErkenningongeborenvrucht;
import nl.bzk.brp.testdatageneratie.domain.kern.HisNaamskeuzeongeborenvruch;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;
import nl.bzk.brp.testdatageneratie.helper.brpbuilder.BrpRelatieBuilder;
import nl.bzk.brp.testdatageneratie.utils.Constanten;
import nl.bzk.brp.testdatageneratie.utils.HisUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import nl.bzk.brp.testdatageneratie.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;


/**
 * Familie generator.
 */
public class FamilieGenerator extends AbstractBasicGenerator {

    private static Logger log = Logger.getLogger(FamilieGenerator.class);
    private final PersonenIds     persoonIds;
    private final int     threadIndex;
    private final int     numberOfThreads;
    private final ArtHuishoudingDto brpHuishouding;

    /**
     * Instantieert Familie generator.
     *
     * @param persoonIds persoon ids
     * @param threadIndex thread index
     * @param numberOfThreads number of threads
     * @param brpHuishouding brp huishouding
     */
    public FamilieGenerator(final PersonenIds persoonIds, final int threadIndex, final int numberOfThreads,
            final ArtHuishoudingDto brpHuishouding)
    {
        super((int)persoonIds.getRangeSize()/numberOfThreads, persoonIds.getBatchBlockSize(), threadIndex );
        this.threadIndex = threadIndex;
        this.numberOfThreads = numberOfThreads;
        this.persoonIds = persoonIds;
        this.brpHuishouding = brpHuishouding;
    }

    @Override
    public GeneratorAdminData call() {
        try {
            Thread.sleep(50 + RandomUtil.random.nextInt(256));
        } catch (InterruptedException e) {
            log.info(e.getMessage());
            return getAdminData();
        }

        int rangeSize = (int) persoonIds.getRangeSize(); // range is max(persIds) - min(persIds)
        int rangeSizePerThread = rangeSize / numberOfThreads;
        int displacement = (0 + getAdminData().getThreadIndex()) * rangeSizePerThread;
        log.info("local range size = " + rangeSizePerThread);
        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();
            BrpRelatieBuilder brpBuilder = new BrpRelatieBuilder(
                    this, kernSession, brpHuishouding, threadIndex, numberOfThreads, persoonIds);

            log.info("@@@@@@@@@@@@@@@ Familie @@@@@@@@@@@@@@@@@@@@@");

            int kindId = (int) persoonIds.getMin() + displacement;
            log.info("kindIds lopen van " + kindId + " t/m " + (kindId + rangeSizePerThread) + " dislacement = " + displacement);
            // i = voor onze totallen, j = daadwerkelijk aangemaakt in de database.
            for (int i = 0, j = 0; i < rangeSizePerThread; i++) {

                Integer saved = null;
                if (isFractie(3)) {
                    saved = brpBuilder.voegFamilieToe();
                    updateCurrentCount(kernSession, j++);
                }
                voegFamilieToe(kernSession, kindId);
                kindId = (int) persoonIds.getVolgende(kindId);
                updateCurrentCount(kernSession, j++);
            }
            brpBuilder.voegRestOfHuidigSetToe(BrpRelatieBuilder.SRT_FAMRECHT_BETR);
//            brpBuilder.dumpBrpPersDto(brpHuishouding);
            log.info("########### Familie #########################");

            kernSession.getTransaction().commit();
        } finally {
            if (kernSession != null) {
                try {
                    kernSession.close();
                } catch (RuntimeException e) {
                    log.error("", e);
                }
            }
        }
        finish();
        return getAdminData();
    }

    /**
     * Voeg familie toe.
     *
     * @param kernSession kern session
     * @param kindId kind id
     */
    private void voegFamilieToe(final Session kernSession, final int kindId) {
        int vaderId = 0;
        Locatie geboortePlaats = RandomUtil.getGeboortePlaats();
        Integer geboorteDatum = RandomUtil.getDatGeboorte();
        Relatie relatie = creeerRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, geboortePlaats, geboorteDatum);
        relatie.setId(SequenceUtil.getMax(Relatie.class.getSimpleName()).intValue());

        Betr betrKind = new Betr((short) SoortBetrokkenheid.KIND.ordinal(), relatie.getId(), kindId);
        betrKind.setId(SequenceUtil.getMax(Betr.class.getSimpleName()).intValue());
        int moederId = (int) persoonIds.selecteerIdBehalve((long) kindId);
//        if (((PersonenIds)persoonIds).isBrp(kindId)) {
//            log.error("Oeps ...M " + moederId + " is brp");
//        }
        Betr betrMoeder = creeerOuderschap(relatie, moederId);
        betrMoeder.setId(SequenceUtil.getMax(Betr.class.getSimpleName()).intValue());
        kernSession.save(relatie);


        // Kind heeft geen historie betrokkenheid (ouderlijkgezag en ouderschap)
        kernSession.save(betrKind);
        opslaanBetrokkenheidMetHistorie(kernSession, betrMoeder);

        if (!RandomUtil.isFractie(100)) {
            vaderId = (int) persoonIds.selecteerIdBehalve((long) kindId, (long) moederId);
//            if (((PersonenIds)persoonIds).isBrp(kindId)) {
//                log.error("Oeps ...V " + vaderId + " is brp");
//            }
            Betr betrVader = creeerOuderschap(relatie, vaderId);
            betrVader.setId(SequenceUtil.getMax(Betr.class.getSimpleName()).intValue());
            opslaanBetrokkenheidMetHistorie(kernSession, betrVader);

            // Creeert bij een vader erkenning ongeboren vrucht en naamskeuze relaties met historie en correcties
            if (RandomUtil.isFractie(5)) {
                Relatie relatieErkenningOngeborenVrucht = creeerRelatie(SoortRelatie.ERKENNING_ONGEBOREN_VRUCHT, geboortePlaats, geboorteDatum);
                relatieErkenningOngeborenVrucht.setId(SequenceUtil.getMax(Relatie.class.getSimpleName()).intValue());
                relatieErkenningOngeborenVrucht.setDaterkenningongeborenvrucht(geboorteDatum);
                kernSession.save(relatieErkenningOngeborenVrucht);

                HisErkenningongeborenvrucht hisErkenningongeborenvrucht = HisUtil.creeerHisErkenningongeborenvrucht(relatieErkenningOngeborenVrucht);
                hisErkenningongeborenvrucht.setId(SequenceUtil.getMax(HisErkenningongeborenvrucht.class.getSimpleName()).intValue());
                kernSession.save(hisErkenningongeborenvrucht);

                if (RandomUtil.isFractie(His.CORRECTIE_FRACTIE)) {
                    HisErkenningongeborenvrucht hisErkenningongeborenvruchtCorrectie = (HisErkenningongeborenvrucht) HisUtil.creeerHisCorrectie(
                            HisUtil.creeerHisErkenningongeborenvrucht(relatieErkenningOngeborenVrucht), hisErkenningongeborenvrucht);
                    hisErkenningongeborenvruchtCorrectie.setId(SequenceUtil.getMax(HisErkenningongeborenvrucht.class.getSimpleName()).intValue());
                    kernSession.save(hisErkenningongeborenvruchtCorrectie);
                }

                Relatie relatienaamskeuzeOngeborenVrucht = creeerRelatie(SoortRelatie.NAAMSKEUZE_ONGEBOREN_VRUCHT, geboortePlaats, geboorteDatum);
                relatienaamskeuzeOngeborenVrucht.setId(SequenceUtil.getMax(Relatie.class.getSimpleName()).intValue());
                relatienaamskeuzeOngeborenVrucht.setDatnaamskeuzeongeborenvrucht(geboorteDatum);
                kernSession.save(relatienaamskeuzeOngeborenVrucht);

                HisNaamskeuzeongeborenvruch hisNaamskeuzeongeborenvruch = HisUtil.creeerHisNaamskeuzeongeborenvruch(relatienaamskeuzeOngeborenVrucht);
                hisNaamskeuzeongeborenvruch.setId(SequenceUtil.getMax(HisNaamskeuzeongeborenvruch.class.getSimpleName()).intValue());
                kernSession.save(hisNaamskeuzeongeborenvruch);

                if (RandomUtil.isFractie(His.CORRECTIE_FRACTIE)) {
                    HisNaamskeuzeongeborenvruch hisNaamskeuzeongeborenvruchCorrectie = (HisNaamskeuzeongeborenvruch) HisUtil.creeerHisCorrectie(
                            HisUtil.creeerHisNaamskeuzeongeborenvruch(relatienaamskeuzeOngeborenVrucht), hisNaamskeuzeongeborenvruch);
                    hisNaamskeuzeongeborenvruchCorrectie.setId(SequenceUtil.getMax(HisNaamskeuzeongeborenvruch.class.getSimpleName()).intValue());
                    kernSession.save(hisNaamskeuzeongeborenvruchCorrectie);
                }
            }

        }

    }

    /**
     * Creeer ouderschap.
     *
     * @param relatie relatie
     * @param persoonId persoon id
     * @return the betr
     */
    private Betr creeerOuderschap(final Relatie relatie, final Integer persoonId) {
        Betr ouderschap = new Betr((short) SoortBetrokkenheid.OUDER.ordinal(), relatie.getId(), persoonId);
        // TODO bolie: voorlopig alleen op true, moet eigenlijk Boolean.TRUE of null !!
        ouderschap.setIndouder(true);
        ouderschap.setIndouderheeftgezag(!RandomUtil.isFractie(Constanten.HONDERD));
        return ouderschap;
    }

}
