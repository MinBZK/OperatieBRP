/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;

/**
 * AutorisatiebundelCacheUtil.
 */
final class AutorisatiebundelCacheUtil {

    static final SoortDienst[] LEVEREN_MUTATIES_DIENST_SOORTEN = new SoortDienst[]{
            SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING,
            SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE,
            SoortDienst.ATTENDERING,};

    private AutorisatiebundelCacheUtil() {

    }

    /**
     * Geeft de autorisatiebundels voor de gegeven diensten.
     * @param alleToegangLeveringsautorisaties alleToegangLeveringsautorisaties
     * @param vandaag vandaag
     * @return een lijst met Autorisatiebundels
     */
    @Bedrijfsregel(Regel.R1261)
    @Bedrijfsregel(Regel.R1265)
    @Bedrijfsregel(Regel.R2052)
    @Bedrijfsregel(Regel.R2056)
    @Bedrijfsregel(Regel.R2057)
    @Bedrijfsregel(Regel.R1263)
    @Bedrijfsregel(Regel.R1264)
    @Bedrijfsregel(Regel.R1258)
    static List<Autorisatiebundel> geefAutorisatiebundels(final List<ToegangLeveringsAutorisatie> alleToegangLeveringsautorisaties,
                                                          final int vandaag, final SoortDienst[] soortDiensten) {
        final List<Autorisatiebundel> autorisatiebundels = new ArrayList<>();
        for (final ToegangLeveringsAutorisatie tla : alleToegangLeveringsautorisaties) {
            if (!brpStelselCorrect(tla, vandaag) && !gbaStelselCorrect(tla, vandaag)) {
                continue;
            }
            final Iterable<Dienst> leveringsautorisatieDiensten = AutAutUtil
                    .zoekDiensten(tla.getLeveringsautorisatie(), soortDiensten);
            for (final Dienst dienst : leveringsautorisatieDiensten) {
                final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, dienst);
                if (isGeldigOp(vandaag, tla, dienst)) {
                    autorisatiebundels.add(autorisatiebundel);
                }
            }
        }
        return autorisatiebundels;
    }

    private static boolean brpStelselCorrect(final ToegangLeveringsAutorisatie tla, final Integer vandaag) {
        boolean stelselCorrect = tla.getLeveringsautorisatie().getStelsel() == Stelsel.BRP;
        final Integer datumOvergangNaarBrp = tla.getGeautoriseerde().getPartij().getDatumOvergangNaarBrp();
        stelselCorrect = stelselCorrect && datumOvergangNaarBrp != null;
        stelselCorrect = stelselCorrect && datumOvergangNaarBrp <= vandaag;
        return stelselCorrect;
    }

    private static boolean gbaStelselCorrect(final ToegangLeveringsAutorisatie tla, final Integer vandaag) {
        boolean stelselCorrect = tla.getLeveringsautorisatie().getStelsel() == Stelsel.GBA;
        final Integer datumOvergangNaarBrp = tla.getGeautoriseerde().getPartij().getDatumOvergangNaarBrp();
        stelselCorrect = stelselCorrect && (datumOvergangNaarBrp == null || datumOvergangNaarBrp > vandaag);
        return stelselCorrect;
    }


    /**
     * Geeft aan of deze Autorisatiebundel geldig is op de opgegeven datum. Checkt dat toegangleveringautorisatie geldig en niet geblokkeerd is. Checkt dat
     * leveringautorisatie geldig en niet geblokkeerd is. Checkt dat dienstbundel hij niet geblokkeerd is (check op geldigheid niet nodig) Checkt dat dienst
     * geldig en niet geblokkeerd is. Checkt dat partijrol geldig is. Checkt dat partij geldig is.
     * @param datum de datum
     * @param tla de ToegangLeveringsAutorisatie
     * @param dienst @return true als deze geldig is op de gegeven datum, anders false
     */
    @Bedrijfsregel(Regel.R1261)
    @Bedrijfsregel(Regel.R1265)
    @Bedrijfsregel(Regel.R2056)
    @Bedrijfsregel(Regel.R1263)
    @Bedrijfsregel(Regel.R1264)
    @Bedrijfsregel(Regel.R1258)
    @Bedrijfsregel(Regel.R2239)
    @Bedrijfsregel(Regel.R2242)
    @Bedrijfsregel(Regel.R2245)
    private static boolean isGeldigOp(final Integer datum, final ToegangLeveringsAutorisatie tla, final Dienst dienst) {
        final PartijRol geautoriseerde = tla.getGeautoriseerde();
        final List<BooleanSupplier> predicateList = Lists.newArrayList(
                () -> AutAutUtil.isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(tla, datum),
                () -> AutAutUtil.isGeldigEnNietGeblokkeerd(dienst, datum),
                () -> AutAutUtil.isGeldigEnNietGeblokkeerd(dienst.getDienstbundel(), datum),
                () -> AutAutUtil.isGeldigOp(datum, geautoriseerde.getDatumIngang(), geautoriseerde.getDatumEinde()),
                () -> AutAutUtil.isGeldigOp(datum, geautoriseerde.getPartij().getDatumIngang(), geautoriseerde.getPartij().getDatumEinde())
        );
        return predicateList.stream().allMatch(BooleanSupplier::getAsBoolean);
    }
}
