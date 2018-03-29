/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.gba.autorisatie;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

/**
 * Gba Autorisaties.
 */
@Component
public final class GbaAutorisaties {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final LeveringsAutorisatieCache leveringsAutorisatieCache;

    /**
     * Constructor.
     * @param leveringsAutorisatieCache autorisatie cache
     */
    public GbaAutorisaties(final LeveringsAutorisatieCache leveringsAutorisatieCache) {
        this.leveringsAutorisatieCache = leveringsAutorisatieCache;
    }

    /**
     * Bepaal de GBA autorisatie.
     * @param partijCode partij
     * @param rol de rol waarvoor de autorisatie dient te bestaan (mag null zijn indien niet van toepassing)
     * @param soortDienst soort dienst
     * @return autorisatie
     * @throws MeerdereAutorisatiesGevondenException als er meeredere autorisaties gevonden worden
     */
    Optional<Autorisatiebundel> bepaalAutorisatie(final String partijCode, final Rol rol, final SoortDienst soortDienst) {
        if (partijCode == null) {
            LOGGER.info("Geen autorisatie gevonden voor lege partijCode.");
            return Optional.empty();
        }

        final List<Autorisatiebundel> autorisatiebundels =
                leveringsAutorisatieCache.geefToegangleveringautorisatiesVoorGeautoriseerdePartij(partijCode).stream()
                        .filter(this::isGba)
                        .filter(this::isGeldig)
                        .filter(toegang -> Objects.isNull(rol) || rol == toegang.getGeautoriseerde().getRol())
                        .map(autorisatie -> ImmutablePair.of(autorisatie, bepaalDienst(autorisatie.getLeveringsautorisatie(), soortDienst)))
                        .filter(pair -> pair.getRight().isPresent())
                        .map(pair -> new Autorisatiebundel(pair.getLeft(), pair.getRight().orElseThrow(IllegalStateException::new)))
                        .collect(Collectors.toList());

        if (autorisatiebundels.size() > 1) {
            throw new MeerdereAutorisatiesGevondenException();
        } else {
            return autorisatiebundels.stream().findFirst();
        }
    }

    private Optional<Dienst> bepaalDienst(final Leveringsautorisatie leveringsautorisatie, final SoortDienst soortDienst) {
        return leveringsautorisatie.getDienstbundelSet().stream()
                .filter(this::isGeldig)
                .map(Dienstbundel::getDienstSet)
                .flatMap(Collection::stream)
                .filter(dienst -> dienst.getSoortDienst() == soortDienst)
                .filter(this::isGeldig)
                .findFirst();
    }

    private boolean isGeldig(final ToegangLeveringsAutorisatie autorisatie) {
        return AutAutUtil.isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(autorisatie, BrpNu.get().alsIntegerDatumNederland());
    }

    private boolean isGeldig(final Dienst dienst) {
        return AutAutUtil.isGeldigEnNietGeblokkeerd(dienst, BrpNu.get().alsIntegerDatumNederland());
    }

    private boolean isGeldig(final Dienstbundel dienstbundel) {
        return AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, BrpNu.get().alsIntegerDatumNederland());
    }

    private boolean isGba(final ToegangLeveringsAutorisatie autorisatie) {
        return Stelsel.GBA == autorisatie.getLeveringsautorisatie().getStelsel();
    }
}
