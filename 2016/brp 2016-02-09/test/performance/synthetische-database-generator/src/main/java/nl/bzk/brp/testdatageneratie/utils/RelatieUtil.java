/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.utils;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;

import java.util.List;
import java.util.Random;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.testdatageneratie.dataaccess.BronnenRepo;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisOuderouderlijkgezagGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisOuderouderschapGenerator;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieStartPlaats;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.Gem;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOuderouderlijkgezag;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOuderouderschap;
import nl.bzk.brp.testdatageneratie.domain.kern.Landgebied;
import nl.bzk.brp.testdatageneratie.domain.kern.Plaats;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;


/**
 * Relatie utility klasse.
 */
public final class RelatieUtil {

    private static final int VIJF = 5;
    private static final int TIEN = 10;
    private static final int VIJF_EN_DERTIG = 35;

    /**
     * Private constructor.
     */
    private RelatieUtil() {

    }

    /**
     * Slaat betrokkenheid met historie op.
     *
     * @param kernSession kern session
     * @param betr betrokkenheid
     */
    public static void opslaanBetrokkenheidMetHistorie(final Session kernSession, final Betr betr) {
        kernSession.save(betr);

        final List<HisOuderouderlijkgezag> hisOuderouderlijkgezagList =
                HisOuderouderlijkgezagGenerator.getInstance().generateHisMaterieels(betr, RandomUtil.randomDate());
        for (final HisOuderouderlijkgezag hisOuderouderlijkgezag : hisOuderouderlijkgezagList) {
            hisOuderouderlijkgezag.setId(SequenceUtil.getMax(HisOuderouderlijkgezag.class.getSimpleName()).intValue());
            kernSession.save(hisOuderouderlijkgezag);
        }

        // Iets hoger gezet ivm teveel records (* 5)
        if (isFractie(His.CORRECTIE_FRACTIE * VIJF)) {
            final int randomHisOuderouderlijkgezag =
                    new Random().nextInt(hisOuderouderlijkgezagList.size());
            final HisOuderouderlijkgezag hisOuderouderlijkgezagCorrectie =
                    (HisOuderouderlijkgezag) HisUtil.creeerHisCorrectie(
                        HisOuderouderlijkgezagGenerator.getInstance().generateHisMaterieel(betr, true),
                        hisOuderouderlijkgezagList.get(randomHisOuderouderlijkgezag));
            hisOuderouderlijkgezagCorrectie.setId(SequenceUtil.getMax(HisOuderouderlijkgezag.class.getSimpleName())
                                                          .intValue());
            hisOuderouderlijkgezagCorrectie
                    .setDataanvgel(GenUtil.naarBrpDatum(hisOuderouderlijkgezagCorrectie.getTsreg()));
            kernSession.save(hisOuderouderlijkgezagCorrectie);
        }

        final List<HisOuderouderschap> hisOuderouderschapList =
                HisOuderouderschapGenerator.getInstance().generateHisMaterieels(betr, RandomUtil.randomDate());
        for (final HisOuderouderschap hisOuderouderschap : hisOuderouderschapList) {
            hisOuderouderschap.setId(SequenceUtil.getMax(HisOuderouderschap.class.getSimpleName()).intValue());
            // TODO bolie: set voorlopig altijd op true; moet eigenlijk Boolean;
            // de enige toegestane waarde is 'T' of null
            hisOuderouderschap.setIndouder(true);
            kernSession.save(hisOuderouderschap);
        }

        // Iets hoger gezet ivm teveel records (* 5)
        if (isFractie(His.CORRECTIE_FRACTIE * VIJF)) {
            final int randomHisOuderouderschap = new Random().nextInt(hisOuderouderschapList.size());
            final HisOuderouderschap hisOuderouderschapCorrectie = (HisOuderouderschap)
                    HisUtil.creeerHisCorrectie(
                            HisOuderouderschapGenerator.getInstance().generateHisMaterieel(betr, true),
                            hisOuderouderschapList.get(randomHisOuderouderschap));
            // TODO bolie: set voorlopig altijd op true; moet eigenlijk Boolean;
            // de enige toegestane waarde is 'T' of null
            hisOuderouderschapCorrectie.setIndouder(true);
            hisOuderouderschapCorrectie.setId(SequenceUtil.getMax(HisOuderouderschap.class.getSimpleName())
                                                      .intValue());
            hisOuderouderschapCorrectie
                    .setDataanvgel(GenUtil.naarBrpDatum(hisOuderouderschapCorrectie.getTsreg()));
            kernSession.save(hisOuderouderschapCorrectie);
        }
    }

    /**
     * Creeert een relatie.
     *
     * @param srtrelatie soort relatie
     * @param locatieAanvang locatie aanvang
     * @param datumAanvang datum aanvang
     * @return relatie
     */
    public static Relatie creeerRelatie(final SoortRelatie srtrelatie,
                                        final Locatie locatieAanvang,
                                        final Integer datumAanvang)
    {
        final Relatie relatie = new Relatie();

        if (srtrelatie != null) {
            relatie.setSrt((short) srtrelatie.ordinal());
        }
        final Landgebied landAanv = locatieAanvang.getLandgebied();
        if (landAanv != null) {
            relatie.setLandgebiedaanv(landAanv.getId());
        }
        final Gem gemAanv = locatieAanvang.getGemeente();
        if (gemAanv != null) {
            relatie.setGemaanv(gemAanv.getId());
        }
        relatie.setDataanv(datumAanvang);
        if (locatieAanvang.isNederland()) {
            // NL
            // bolie: we kiezen hier nog random RandomUtil.getWplGeboorte(), omdat we niet kunen
            // garanderen dat Short(locatieAanvang.getPlaats()) ook daadwerkelijk voorkomt in de
            // kern.plaats tabel.
            final Plaats plaats = RandomUtil.getWplGeboorte();
            if (plaats != null) {
                relatie.setWplnaamaanv(plaats.getNaam());
            }
        } else if (locatieAanvang.getLandCode() == Locatie.LAND_CODE_ONBEKEND) {
            //Land onbekend, gebruik omschrijving:
            relatie.setOmslocaanv(locatieAanvang.getPlaats());
        } else {
            // Buitenland
            relatie.setBlplaatsaanv(locatieAanvang.getPlaats());
            if (RandomUtil.isFractie(TIEN)) {
                relatie.setBlregioaanv(RandomStringUtils.randomAlphabetic(VIJF_EN_DERTIG));
            }
        }

        return relatie;
    }

    /**
     * Geeft de relatie start plaats.
     *
     * @return relatie start plaats
     */
    public static Locatie getRelatieStartPlaats() {
        Locatie locatie;
        do {
            locatie = BronnenRepo.getBron(RelatieStartPlaats.class).getLocatie();
        } while (locatie.getLandgebied() == null);
        return locatie;
    }

}
