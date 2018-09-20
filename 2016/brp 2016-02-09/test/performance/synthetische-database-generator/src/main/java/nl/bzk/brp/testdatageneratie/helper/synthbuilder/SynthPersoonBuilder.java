/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.helper.synthbuilder;

import static nl.bzk.brp.testdatageneratie.utils.PersIndicatieUtil.creeerIndicatie;
import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.datagenerators.PersoonGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersAdresGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersbijhoudingGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersgeslachtsaandGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersgeslnaamcompGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersidsGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersindicatieGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersmigratieGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersnationGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPerssamengesteldenaamGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersverblijfsrGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersvoornaamGenerator;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Aandinhingvermissingreisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.Auttypevanafgiftereisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.Bijhaard;
import nl.bzk.brp.testdatageneratie.domain.kern.Gem;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersadres;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersbijhouding;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersdeelneuverkiezingen;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeboorte;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslachtsaand;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslnaamcomp;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersids;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersindicatie;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersinschr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersmigratie;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersnaamgebruik;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersnation;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersoverlijden;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerspk;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersreisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerssamengesteldenaam;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersuitslkiesr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersverblijfsr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersverificatie;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersvoornaam;
import nl.bzk.brp.testdatageneratie.domain.kern.Nation;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Persadres;
import nl.bzk.brp.testdatageneratie.domain.kern.Persgeslnaamcomp;
import nl.bzk.brp.testdatageneratie.domain.kern.Persindicatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Persnation;
import nl.bzk.brp.testdatageneratie.domain.kern.Persreisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.Persverificatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Persvoornaam;
import nl.bzk.brp.testdatageneratie.domain.kern.Plaats;
import nl.bzk.brp.testdatageneratie.domain.kern.Rdnverknlnation;
import nl.bzk.brp.testdatageneratie.domain.kern.Rdnverliesnlnation;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtnlreisdoc;
import nl.bzk.brp.testdatageneratie.helper.PersoonHelper;
import nl.bzk.brp.testdatageneratie.utils.Constanten;
import nl.bzk.brp.testdatageneratie.utils.GenUtil;
import nl.bzk.brp.testdatageneratie.utils.HisUtil;
import nl.bzk.brp.testdatageneratie.utils.PersAdresUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import nl.bzk.brp.testdatageneratie.utils.SequenceUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;

/**
 * Synth persoon builder, bouwt random personen op basis van een bronnen database.
 */
public class SynthPersoonBuilder {
    private final PersoonHelper persGenerator;
    private final PersoonGenerator owner;

    /**
     * Instantieert Synth persoon builder.
     *
     * @param owner PersoonGenerator die deze aanstuurt
     * @param persGenerator pers generator
     */
    public SynthPersoonBuilder(final PersoonGenerator owner, final PersoonHelper persGenerator) {
        this.owner = owner;
        this.persGenerator = persGenerator;
    }

    /**
     * Voeg persoon toe.
     *
     * @param kernSession kern session
     * @return persoon id integer
     */
    public Integer voegPersoonToe(final Session kernSession) {
        String[] voornamen = RandomUtil.getVoornamen();
        Pers pers = persGenerator.generatePers(voornamen);
        pers.setId(SequenceUtil.getMax(Persoon.class.getSimpleName()).intValue());
        kernSession.save(pers);

        owner.getHuwelijksboot().toevoegenIndienUitverkoren(pers);

        voegVoornamenToe(kernSession, voornamen, pers);

        voegGeslachtsnamenToe(kernSession, pers);

        boolean isStatenloos = isFractie(Constanten.VIJF);
        if (!isStatenloos) {
            voegNationaliteitenToe(kernSession, pers);
        }

        voegReisdocumentenToe(kernSession, pers);

        voegAdresToe(kernSession, pers);

        voegIndicatiesToe(kernSession, pers);

        if (Bijhaard.NIET_INGEZETENE.ordinal() == pers.getBijhaard()) {
            voegVerificatiesToe(kernSession, pers);
        }

        List<HisPersids> hisPersidsList =
                HisPersidsGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
        for (HisPersids hisPersids : hisPersidsList) {
            hisPersids.setId(SequenceUtil.getMax(HisPersids.class.getSimpleName()).intValue());
            kernSession.save(hisPersids);
        }
        if (isFractie(His.CORRECTIE_FRACTIE)) {
            int randomHisPersids = new Random().nextInt(hisPersidsList.size());
            HisPersids hisPersidsCorrectie = (HisPersids)
                    HisUtil.creeerHisCorrectie(HisPersidsGenerator.getInstance().generateHisMaterieel(pers, false),
                            hisPersidsList.get(randomHisPersids));
            hisPersidsCorrectie.setId(SequenceUtil.getMax(HisPersids.class.getSimpleName()).intValue());
            hisPersidsCorrectie.setDataanvgel(GenUtil.naarBrpDatum(hisPersidsCorrectie.getTsreg()));
            kernSession.save(hisPersidsCorrectie);
        }

        List<HisPersgeslachtsaand> hisPersgeslachtsaandList =
                HisPersgeslachtsaandGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
        for (HisPersgeslachtsaand hisPersgeslachtsaand : hisPersgeslachtsaandList) {
            hisPersgeslachtsaand.setId(SequenceUtil.getMax(HisPersgeslachtsaand.class.getSimpleName()).intValue());
            kernSession.save(hisPersgeslachtsaand);
        }
        if (isFractie(His.CORRECTIE_FRACTIE)) {
            int randomHisPersgeslachtsaand = new Random().nextInt(hisPersgeslachtsaandList.size());
            HisPersgeslachtsaand hisPersgeslachtsaandCorrectie =
                    (HisPersgeslachtsaand) HisUtil.creeerHisCorrectie(
                            HisPersgeslachtsaandGenerator.getInstance().generateHisMaterieel(pers, false),
                                        hisPersgeslachtsaandList.get(randomHisPersgeslachtsaand));
            hisPersgeslachtsaandCorrectie.setId(SequenceUtil.getMax(HisPersgeslachtsaand.class.getSimpleName())
                                                        .intValue());
            hisPersgeslachtsaandCorrectie
                    .setDataanvgel(GenUtil.naarBrpDatum(hisPersgeslachtsaandCorrectie.getTsreg()));
            kernSession.save(hisPersgeslachtsaandCorrectie);
        }

        List<HisPerssamengesteldenaam> hisPerssamengesteldenaamList =
                HisPerssamengesteldenaamGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
        for (HisPerssamengesteldenaam hisPerssamengesteldenaam : hisPerssamengesteldenaamList) {
            hisPerssamengesteldenaam.setId(SequenceUtil.getMax(HisPerssamengesteldenaam.class.getSimpleName()).intValue());
            kernSession.save(hisPerssamengesteldenaam);
        }
        if (isFractie(His.CORRECTIE_FRACTIE)) {
            int randomHisPerssamengesteldenaam = new Random().nextInt(hisPerssamengesteldenaamList.size());
            HisPerssamengesteldenaam hisPerssamengesteldenaamCorrectie =
                    (HisPerssamengesteldenaam) HisUtil.creeerHisCorrectie(
                            HisPerssamengesteldenaamGenerator.getInstance().generateHisMaterieel(pers, false),
                            hisPerssamengesteldenaamList.get(randomHisPerssamengesteldenaam));
            hisPerssamengesteldenaamCorrectie.setId(SequenceUtil.getMax(HisPerssamengesteldenaam.class.getSimpleName())
                                                            .intValue());
            hisPerssamengesteldenaamCorrectie
                    .setDataanvgel(GenUtil.naarBrpDatum(hisPerssamengesteldenaamCorrectie.getTsreg()));
            kernSession.save(hisPerssamengesteldenaamCorrectie);
        }

        HisPersnaamgebruik hisPersnaamgebruik = HisUtil.creeerHisPersnaamgebruik(pers);
        hisPersnaamgebruik.setId(SequenceUtil.getMax(HisPersnaamgebruik.class.getSimpleName()).intValue());
        kernSession.save(hisPersnaamgebruik);

        HisPersgeboorte hisPersgeboorte = HisUtil.creeerHisPersgeboorte(pers);
        hisPersgeboorte.setId(SequenceUtil.getMax(HisPersgeboorte.class.getSimpleName()).intValue());
        kernSession.save(hisPersgeboorte);
        if (isFractie(His.CORRECTIE_FRACTIE)) {
            HisPersgeboorte hisPersgeboorteCorrectie =
                    (HisPersgeboorte) HisUtil.creeerHisCorrectie(HisUtil.creeerHisPersgeboorte(pers), hisPersgeboorte);
            hisPersgeboorteCorrectie.setId(SequenceUtil.getMax(HisPersgeboorte.class.getSimpleName()).intValue());
            kernSession.save(hisPersgeboorteCorrectie);
        }

        HisPersinschr hisPersinschr = HisUtil.creerHisPersinschr(pers);
        hisPersinschr.setId(SequenceUtil.getMax(HisPersinschr.class.getSimpleName()).intValue());
        kernSession.save(hisPersinschr);

        List<HisPersbijhouding> hisPersbijhoudingList =
                HisPersbijhoudingGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
        for (HisPersbijhouding hisPersbijhaard : hisPersbijhoudingList) {
            hisPersbijhaard.setId(SequenceUtil.getMax(HisPersbijhouding.class.getSimpleName()).intValue());
            kernSession.save(hisPersbijhaard);
        }

        if (isFractie(His.CORRECTIE_FRACTIE)) {
            int randomHisPersbijhouding = new Random().nextInt(hisPersbijhoudingList.size());
            HisPersbijhouding hisPersbijhoudingCorrectie =
                    (HisPersbijhouding) HisUtil.creeerHisCorrectie(
                            HisPersbijhoudingGenerator.getInstance().generateHisMaterieel(pers, false),
                            hisPersbijhoudingList.get(randomHisPersbijhouding));
            hisPersbijhoudingCorrectie.setId(SequenceUtil.getMax(HisPersbijhouding.class.getSimpleName()).intValue());
            hisPersbijhoudingCorrectie.setDataanvgel(GenUtil.naarBrpDatum(hisPersbijhoudingCorrectie.getTsreg()));
            kernSession.save(hisPersbijhoudingCorrectie);
        }

        if (pers.getDatoverlijden() != null) {
            HisPersoverlijden hisPersoverlijden = HisUtil.creeerHisPersoverlijden(pers);
            hisPersoverlijden.setId(SequenceUtil.getMax(HisPersoverlijden.class.getSimpleName()).intValue());
            kernSession.save(hisPersoverlijden);
        }

        if (pers.getInduitslkiesr() != null) {
            HisPersuitslkiesr hisPersuitslkiesr = HisUtil.creeerHisPersuitslkiesr(pers);
            hisPersuitslkiesr.setId(SequenceUtil.getMax(HisPersuitslkiesr.class.getSimpleName()).intValue());
            kernSession.save(hisPersuitslkiesr);
        }

        if (pers.getInddeelneuverkiezingen() != null) {
            HisPersdeelneuverkiezingen hisPersdeelneuverkiezingen = HisUtil.creeerHisPersdeelneuverkiezingen(pers);
            hisPersdeelneuverkiezingen.setId(SequenceUtil.getMax(HisPersdeelneuverkiezingen.class.getSimpleName())
                                                     .intValue());
            kernSession.save(hisPersdeelneuverkiezingen);
        }

        //TODO: wellicht dient dit nog geimplementeerd te worden voor persbijhouding
//        if (pers.getRdnopschortingbijhouding() != null) {
//            List<HisPersopschorting> hisPersopschortingList =
//                    HisPersopschortingGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
//            for (HisPersopschorting hisPersopschorting : hisPersopschortingList) {
//                hisPersopschorting.setId(SequenceUtil.getMaxHisPersopschorting());
//                kernSession.save(hisPersopschorting);
//            }
//            if (isFractie(His.CORRECTIE_FRACTIE)) {
//                int randomHisPersopschorting = new Random().nextInt(hisPersopschortingList.size());
//                HisPersopschorting hisPersopschortingCorrectie = (HisPersopschorting) HisUtil.creeerHisCorrectie(
//                        HisPersopschortingGenerator.getInstance().generateHisMaterieel(pers, false),
//                        hisPersopschortingList.get(randomHisPersopschorting));
//                hisPersopschortingCorrectie.setId(SequenceUtil.getMaxHisPersopschorting());
//                hisPersopschortingCorrectie
//                        .setDataanvgel(GenUtil.naarBrpDatum(hisPersopschortingCorrectie.getTsreg()));
//                kernSession.save(hisPersopschortingCorrectie);
//            }
//        }

        if (pers.getGempk() != null) {
            HisPerspk hisPerspk = HisUtil.creeerHisPerspk(pers);
            hisPerspk.setId(SequenceUtil.getMax(HisPerspk.class.getSimpleName()).intValue());
            kernSession.save(hisPerspk);
        }
        if (pers.getLandgebiedgeboorte() != Locatie.LAND_CODE_ONBEKEND) {

            List<HisPersmigratie> hisPersmigratieList =
                    HisPersmigratieGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
            for (HisPersmigratie hisPersimmigratie : hisPersmigratieList) {
                hisPersimmigratie.setId(SequenceUtil.getMax(HisPersmigratie.class.getSimpleName()).intValue());

                if(isFractie(10)) {
                    hisPersimmigratie.setLandgebiedmigratie(RandomUtil.getLandgebiedBuitenlandsAdres().getId());
                    hisPersimmigratie.setSrtmigratie((short) SoortMigratie.EMIGRATIE.ordinal());
                } else {
                    hisPersimmigratie.setLandgebiedmigratie(229);
                    hisPersimmigratie.setSrtmigratie((short) SoortMigratie.IMMIGRATIE.ordinal());
                }
                kernSession.save(hisPersimmigratie);
            }
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPersimmigratie = new Random().nextInt(hisPersmigratieList.size());
                HisPersmigratie hisPersmigratieCorrectie = (HisPersmigratie) HisUtil.creeerHisCorrectie(
                        HisPersmigratieGenerator.getInstance().generateHisMaterieel(pers, false),
                        hisPersmigratieList.get(randomHisPersimmigratie));
                hisPersmigratieCorrectie.setId(SequenceUtil.getMax(HisPersmigratie.class.getSimpleName()).intValue());
                hisPersmigratieCorrectie
                        .setDataanvgel(GenUtil.naarBrpDatum(hisPersmigratieCorrectie.getTsreg()));
                if(isFractie(10)) {
                    hisPersmigratieCorrectie.setLandgebiedmigratie(RandomUtil.getLandgebiedBuitenlandsAdres().getId());
                    hisPersmigratieCorrectie.setSrtmigratie((short) SoortMigratie.EMIGRATIE.ordinal());
                } else {
                    hisPersmigratieCorrectie.setLandgebiedmigratie(229);
                    hisPersmigratieCorrectie.setSrtmigratie((short) SoortMigratie.IMMIGRATIE.ordinal());
                }
                kernSession.save(hisPersmigratieCorrectie);
            }
        }

        if (pers.getAandverblijfsr() != null) {
            List<HisPersverblijfsr> hisPersverblijfsrList =
                    HisPersverblijfsrGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
            for (HisPersverblijfsr hisPersverblijfsr : hisPersverblijfsrList) {
                hisPersverblijfsr.setId(SequenceUtil.getMax(HisPersverblijfsr.class.getSimpleName()).intValue());
                kernSession.save(hisPersverblijfsr);
            }
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPersverblijfsr = new Random().nextInt(hisPersverblijfsrList.size());
                HisPersverblijfsr hisPersverblijfsrCorrectie = (HisPersverblijfsr) HisUtil.
                        creeerHisCorrectie(HisPersverblijfsrGenerator.getInstance()
                                                   .generateHisMaterieel(pers, false),
                                           hisPersverblijfsrList.get(randomHisPersverblijfsr));
                hisPersverblijfsrCorrectie.setId(SequenceUtil.getMax(HisPersverblijfsr.class.getSimpleName())
                                                         .intValue());
                hisPersverblijfsrCorrectie
                        .setDataanvgel(GenUtil.naarBrpDatum(hisPersverblijfsrCorrectie.getTsreg()));
                kernSession.save(hisPersverblijfsrCorrectie);
            }
        }
        return pers.getId();
    }

    /**
     * Voeg verificaties toe.
     *
     * @param kernSession kern session
     * @param pers pers
     */
    private void voegVerificatiesToe(final Session kernSession, final Pers pers) {
        int aantal = RandomUtil.random.nextInt(Constanten.DRIE);
        for (int i = 0; i < aantal; i++) {
            voegVerificatieToe(kernSession, pers);
        }
    }

    /**
     * Voeg verificatie toe.
     *
     * @param kernSession kern session
     * @param pers pers
     */
    private void voegVerificatieToe(final Session kernSession, final Pers pers) {
        Persverificatie verificatie = new Persverificatie();
        verificatie.setGeverifieerde(pers.getId());
        verificatie.setSrt(RandomStringUtils.randomAlphabetic(Constanten.TWINTIG));
        if (isFractie(Constanten.TWEE)) {
            verificatie.setDat(RandomUtil.randomDate());
        }
        verificatie.setId(SequenceUtil.getMax(Persverificatie.class.getSimpleName()));
        if (pers.getBijhpartij() != null) {
            verificatie.setPartij(pers.getBijhpartij());
        } else {
            verificatie.setPartij(RandomUtil.getPartijByBijhgem());
        }


        kernSession.save(verificatie);
        HisPersverificatie hisPersverificatie = HisUtil.creerHisPersverificatie(verificatie);

        hisPersverificatie.setId(SequenceUtil.getMax(HisPersverificatie.class.getSimpleName()).intValue());
        kernSession.save(hisPersverificatie);
    }

    /**
     * Voeg geslachtsnamen toe.
     *
     * @param kernSession kern session
     * @param pers pers
     */
    private void voegGeslachtsnamenToe(final Session kernSession, final Pers pers) {
        List<Persgeslnaamcomp> geslachtsnaamComponenten = new ArrayList<>();
        StringTokenizer tokens = new StringTokenizer(pers.getGeslnaamstam(), " ");
        for (int i = 1; tokens.hasMoreTokens(); i++) {
            String token = tokens.nextToken();
            if (isHoofdletter(token.charAt(0))) {
                Persgeslnaamcomp persgeslnaamcomp = new Persgeslnaamcomp(i, pers.getId(), i, null, null,
                        persGenerator.getVoorvoegsel(), null, token);
                persgeslnaamcomp.setAdellijketitel(pers.getAdellijketitel());
                persgeslnaamcomp.setPredicaat(pers.getPredicaat());
                geslachtsnaamComponenten.add(persgeslnaamcomp);
            }
        }
        for (Persgeslnaamcomp persgeslnaamcomp : geslachtsnaamComponenten) {
            persgeslnaamcomp.setId(SequenceUtil.getMax(Persgeslnaamcomp.class.getSimpleName()).intValue());
            kernSession.save(persgeslnaamcomp);

            List<HisPersgeslnaamcomp> hisPersgeslnaamcompList =
                    HisPersgeslnaamcompGenerator.getInstance()
                            .generateHisMaterieels(persgeslnaamcomp, pers.getDatgeboorte());
            for (HisPersgeslnaamcomp hisPersgeslnaamcomp : hisPersgeslnaamcompList) {
                hisPersgeslnaamcomp.setId(SequenceUtil.getMax(HisPersgeslnaamcomp.class.getSimpleName()).intValue());
                kernSession.save(hisPersgeslnaamcomp);
            }
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPersgeslnaamcomp = new Random().nextInt(hisPersgeslnaamcompList.size());
                HisPersgeslnaamcomp hisPersgeslnaamcomp = (HisPersgeslnaamcomp) HisUtil.creeerHisCorrectie(
                        HisPersgeslnaamcompGenerator.getInstance().generateHisMaterieel(persgeslnaamcomp, false),
                            hisPersgeslnaamcompList.get(randomHisPersgeslnaamcomp));
                hisPersgeslnaamcomp.setId(SequenceUtil.getMax(HisPersgeslnaamcomp.class.getSimpleName()).intValue());
                hisPersgeslnaamcomp.setDataanvgel(GenUtil.naarBrpDatum(hisPersgeslnaamcomp.getTsreg()));
                kernSession.save(hisPersgeslnaamcomp);
            }
        }
    }

    /**
     * Is hoofdletter.
     *
     * @param letter letter
     * @return true als het een hoofdletter is
     */
    private boolean isHoofdletter(final char letter) {
        return letter >= 'A' && letter <= 'Z';
    }

    /**
     * Voeg voornamen toe.
     *
     * @param kernSession kern session
     * @param voornamen voornamen
     * @param pers pers
     */
    private void voegVoornamenToe(final Session kernSession, final String[] voornamen, final Pers pers) {
        for (int i = 0; i < voornamen.length; i++) {
            Persvoornaam persVoornaam = new Persvoornaam();
            persVoornaam.setNaam(voornamen[i]);
            persVoornaam.setPers(pers.getId());
            persVoornaam.setVolgnr(i + 1);
            persVoornaam.setId(SequenceUtil.getMax(Persvoornaam.class.getSimpleName()).intValue());
            kernSession.save(persVoornaam);

            List<HisPersvoornaam> hisPersvoornaamList =
                    HisPersvoornaamGenerator.getInstance().generateHisMaterieels(persVoornaam, pers.getDatgeboorte());
            for (HisPersvoornaam hisPersvoornaam : hisPersvoornaamList) {
                hisPersvoornaam.setId(SequenceUtil.getMax(HisPersvoornaam.class.getSimpleName()).intValue());
                kernSession.save(hisPersvoornaam);
            }
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPersvoornaam = new Random().nextInt(hisPersvoornaamList.size());
                HisPersvoornaam hisPersvoornaamCorrectie = (HisPersvoornaam) HisUtil.creeerHisCorrectie(
                        HisPersvoornaamGenerator.getInstance().generateHisMaterieel(persVoornaam, true),
                        hisPersvoornaamList.get(randomHisPersvoornaam));
                hisPersvoornaamCorrectie.setId(SequenceUtil.getMax(HisPersvoornaam.class.getSimpleName()).intValue());
                // hmm. is dit de goede datum?
                hisPersvoornaamCorrectie.setDataanvgel(pers.getDatinschr());
                hisPersvoornaamCorrectie.setDataanvgel(GenUtil.naarBrpDatum(hisPersvoornaamCorrectie.getTsreg()));
                kernSession.save(hisPersvoornaamCorrectie);
            }
        }
    }

    /**
     * Voeg nationaliteiten toe.
     *
     * @param kernSession kern session
     * @param pers pers
     */
    private void voegNationaliteitenToe(final Session kernSession, final Pers pers) {
        Nation nationaliteit = RandomUtil.getNationaliteit();
        if (nationaliteit != null) {
            voegNationalieitToe(kernSession, pers, nationaliteit);
        }
        if (isFractie(80)) {
            Nation nationaliteit2;
            do {
                nationaliteit2 = RandomUtil.getNationaliteit();
            } while (nationaliteit2 == nationaliteit);
            if (nationaliteit2 != null) {
                voegNationalieitToe(kernSession, pers, nationaliteit2);
            }
        }
    }

    /**
     * Voeg nationalieit toe.
     *
     * @param kernSession kern session
     * @param pers pers
     * @param nationaliteit nationaliteit
     */
    private void voegNationalieitToe(final Session kernSession, final Pers pers, final Nation nationaliteit) {
        Persnation persnation = new Persnation();
        persnation.setNation(nationaliteit.getId());
        persnation.setPers(pers.getId());
        if (nationaliteit.isNederlandse()) {
            Rdnverknlnation rdnVerkrjg = RandomUtil.getNationaliteitVerkrijgReden();
            persnation.setRdnverk(null == rdnVerkrjg ? null : rdnVerkrjg.getId());
            if (isFractie(5226)) {
                Rdnverliesnlnation rdnVerlies = RandomUtil.getNationaliteitVerliesReden();
                persnation.setRdnverlies(rdnVerlies == null ? null : rdnVerlies.getId());
            }
        }
        persnation.setId(SequenceUtil.getMax(Persnation.class.getSimpleName()).intValue());
        kernSession.save(persnation);

        List<HisPersnation> hisPersnations = HisPersnationGenerator.getInstance()
                .generateHisMaterieels(persnation, pers.getDatgeboorte());
        for (HisPersnation hisPersnation : hisPersnations) {
            hisPersnation.setId(SequenceUtil.getMax(HisPersnation.class.getSimpleName()).intValue());
            kernSession.save(hisPersnation);
        }
    }

    /**
     * Voeg reisdocumenten toe.
     *
     * @param kernSession kern session
     * @param pers pers
     */
    private void voegReisdocumentenToe(final Session kernSession, final Pers pers) {
        int aantal = RandomUtil.random.nextInt(Constanten.ZES);
        for (int i = 0; i < aantal; i++) {
            voegReisdocumentToe(kernSession, pers);
        }
    }

    /**
     * Voeg reisdocument toe.
     *
     * @param kernSession kern session
     * @param pers pers
     */
    private void voegReisdocumentToe(final Session kernSession, final Pers pers) {
        Persreisdoc persreisdoc = new Persreisdoc();
        persreisdoc.setId(SequenceUtil.getMax(Persreisdoc.class.getSimpleName()).intValue());
        Srtnlreisdoc srt = RandomUtil.getReisdocumentSoort();
        persreisdoc.setSrt((short) srt.ordinal());
        if (!RandomUtil.isFractie(Constanten.DRIE)) {
            Aandinhingvermissingreisdoc aandinhingvermissingreisdoc = MetaRepo.get(Aandinhingvermissingreisdoc.class);
            persreisdoc.setAandinhingvermissing(null == aandinhingvermissingreisdoc ? null : aandinhingvermissingreisdoc.getId());

            //TODO: actieverval vullen?
        }
        //TODO datum veranderd
        //persreisdoc.setDatuitgifte(RandomUtil.getAutvanafgiftereisdoc());
        persreisdoc.setPers(pers.getId());
        if (isFractie(Constanten.HONDERD)) {
            persreisdoc.setDatinhingvermissing(RandomUtil.randomDate());
            //TODO: actieverval vullen?
        }

        int startDate = RandomUtil.randomDate();

        persreisdoc.setDatingangdoc(startDate);
        persreisdoc.setDatuitgifte(startDate);
        persreisdoc.setDateindedoc(RandomUtil.randomDateNaDatum(startDate));
        persreisdoc.setNr(RandomStringUtils.randomAlphanumeric(Constanten.NEGEN));
        Auttypevanafgiftereisdoc aut = RandomUtil.getAuttypevanafgiftereisdoc();
        String autCode = "..";
        if (aut != null) {
            autCode = aut.getCode();
        }
        persreisdoc.setAutvanafgifte(autCode);


        kernSession.save(persreisdoc);
        HisPersreisdoc hisPersreisdoc = HisUtil.creeerHisPersreisdoc(persreisdoc);
        hisPersreisdoc.setId(SequenceUtil.getMax(HisPersreisdoc.class.getSimpleName()).intValue());
        kernSession.save(hisPersreisdoc);
    }

    /**
     * Voeg adres toe.
     *
     * @param kernSession kern session
     * @param pers pers
     */
    private void voegAdresToe(final Session kernSession, final Pers pers) {
        Persadres adres = PersAdresUtil.generatePersAdres(pers);
        adres.setId(SequenceUtil.getMax(Persadres.class.getSimpleName()).intValue());

        Integer dataanvHuidig = adres.getDataanvadresh();
        List<HisPersadres> hisPersAdressen = HisPersAdresGenerator.getInstance()
                .generateHisMaterieels(adres, dataanvHuidig);

        if (hisPersAdressen.size() == Constanten.EEN) {
            adres.setRdnwijz(null);
        }

        if (pers.getDatoverlijden() == null) {
            Plaats plaats = RandomUtil.getWplNuGeldig();
            Gem gem = RandomUtil.getGemeenteNuGeldig();
            adres.setWplnaam(null == plaats ? null : plaats.getNaam());
            adres.setGem(null == gem ? null : gem.getId());
        }

        if (isFractie(100000)) {
            adres.setIndpersaangetroffenopadres(false);
        }

        kernSession.save(adres);

        for (HisPersadres hisPersadres : hisPersAdressen) {
            hisPersadres.setId(SequenceUtil.getMax(HisPersadres.class.getSimpleName()).intValue());
            kernSession.save(hisPersadres);
        }

//        List<HisPersbijhgem> hisPersbijhgems = HisPersbijhgemGenerator.getInstance()
//                .generateHisMaterieels(pers, dataanvHuidig);
//        for (HisPersbijhgem hisPersbijhgem : hisPersbijhgems) {
//            hisPersbijhgem.setId(SequenceUtil.getMaxHisPersbijhgem());
//            kernSession.save(hisPersbijhgem);
//        }
    }

    /**
     * Voeg indicaties toe.
     *
     * @param kernSession kern session
     * @param pers pers
     */
    private void voegIndicatiesToe(final Session kernSession, final Pers pers) {
        List<Persindicatie> indicaties = creeerIndicaties(pers);
        for (Persindicatie indicatie : indicaties) {
            indicatie.setId(SequenceUtil.getMax(Persindicatie.class.getSimpleName()).intValue());
            List<HisPersindicatie> hisPersindicaties = HisPersindicatieGenerator.getInstance()
                    .generateHisMaterieels(indicatie, RandomUtil.randomDate());
            kernSession.save(indicatie);
            for (HisPersindicatie hisPersindicatie : hisPersindicaties) {
                hisPersindicatie.setId(SequenceUtil.getMax(HisPersindicatie.class.getSimpleName()).intValue());
                hisPersindicatie.setPersindicatie(indicatie.getId());
                kernSession.save(hisPersindicatie);
            }
        }
    }

    /**
     * Creeer indicaties.
     *
     * @param pers pers
     * @return the list
     */
    private List<Persindicatie> creeerIndicaties(final Pers pers) {

        List<Persindicatie> resultaat = new ArrayList<>();
        EnumSet<SoortIndicatie> indicatieSet = EnumSet.allOf(SoortIndicatie.class);
        indicatieSet.remove(SoortIndicatie.DUMMY);
        for (SoortIndicatie soortIndicatie : indicatieSet) {
            if (isFractie(Constanten.NEGENTIG)) {
                resultaat.add(creeerIndicatie(pers, soortIndicatie));
            }
        }
        return resultaat;
    }

}
