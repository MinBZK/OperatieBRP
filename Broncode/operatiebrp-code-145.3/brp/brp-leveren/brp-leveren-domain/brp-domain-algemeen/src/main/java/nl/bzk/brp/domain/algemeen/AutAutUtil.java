/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang3.BooleanUtils;

/**
 * Utilklasse voor diverse controles op geldigheid en blokkering.
 */
public final class AutAutUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private AutAutUtil() {
    }

    /**
     * Controleert toegangLeveringsAutorisatie en Leveringsautorisatie.
     * @param toegangLeveringsAutorisatie toegangLeveringsAutorisatie
     * @param peilDatum peilDatum
     * @return geldig
     */
    public static boolean isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie,
                                                                                 final Integer peilDatum) {
        if (toegangLeveringsAutorisatie == null) {
            return false;
        }
        final Leveringsautorisatie leveringsautorisatie = toegangLeveringsAutorisatie.getLeveringsautorisatie();
        return BooleanUtils.isNotTrue(toegangLeveringsAutorisatie.getIndicatieGeblokkeerd())
                && isGeldigOp(peilDatum, toegangLeveringsAutorisatie.getDatumIngang(), toegangLeveringsAutorisatie.getDatumEinde())
                && isGeldigEnNietGeblokkeerd(leveringsautorisatie, peilDatum);
    }

    /**
     * Controleert toegangLeveringsAutorisatie en Leveringsautorisatie.
     * @param toegangLeveringsAutorisatie toegangLeveringsAutorisatie
     * @param peilDatum peilDatum
     * @return geldig
     */
    public static boolean isGeldigEnNietGeblokkeerd(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie,
                                                                                 final Integer peilDatum) {
        if (toegangLeveringsAutorisatie == null) {
            return false;
        }
        return BooleanUtils.isNotTrue(toegangLeveringsAutorisatie.getIndicatieGeblokkeerd())
                && isGeldigOp(peilDatum, toegangLeveringsAutorisatie.getDatumIngang(), toegangLeveringsAutorisatie.getDatumEinde());
    }

    /**
     * Controleert of een dienstbundel geldig en niet geblokkeerd is op de gegeven peildatum.
     * @param dienstbundel dienstbundel de dienstbundel
     * @param peilDatum peilDatum de peildatum
     * @return indicatie of de dienstbundel geldig en niet geblokkeerd is.
     */
    public static boolean isGeldigEnNietGeblokkeerd(final Dienstbundel dienstbundel, final Integer peilDatum) {
        return BooleanUtils.isNotTrue(dienstbundel.getIndicatieGeblokkeerd())
                && isGeldigOp(peilDatum, dienstbundel.getDatumIngang(), dienstbundel.getDatumEinde());
    }

    /**
     * Controleert of een leveringsautorisatie geldig en niet geblokkeerd is op de gegeven peildatum.
     * @param leveringsautorisatie de leveringsautorisatie
     * @param peilDatum peilDatum de peildatum
     * @return indicatie of de dienstbundel geldig en niet geblokkeerd is.
     */
    public static boolean isGeldigEnNietGeblokkeerd(final Leveringsautorisatie leveringsautorisatie, final Integer peilDatum) {
        return BooleanUtils.isNotTrue(leveringsautorisatie.getIndicatieGeblokkeerd())
                && isGeldigOp(peilDatum, leveringsautorisatie.getDatumIngang(), leveringsautorisatie.getDatumEinde());
    }

    /**
     * Controleert of een dienst geldig en niet geblokkeerd is op de gegeven peildatum.
     * @param dienst dienst de dienst
     * @param peilDatum peilDatum de peildatum
     * @return indicatie of de dienst geldig en niet geblokkeerd is
     */
    public static boolean isGeldigEnNietGeblokkeerd(final Dienst dienst, final Integer peilDatum) {
        return BooleanUtils.isNotTrue(dienst.getIndicatieGeblokkeerd()) && isGeldigOp(peilDatum, dienst.getDatumIngang(), dienst.getDatumEinde());
    }

    /**
     * Zoekt binnen de leveringsautorisatie naar een dienst van de gegeven soort.
     * @param leveringsautorisatie de leveringsautorisatie
     * @param soortDienst de soortDienst
     * @return dienst de gevonden dienst of null
     */
    public static Dienst zoekDienst(final Leveringsautorisatie leveringsautorisatie, final SoortDienst soortDienst) {
        final Collection<Dienst> diensten = zoekDiensten(leveringsautorisatie, soortDienst);
        if (diensten.isEmpty()) {
            LOGGER.warn("Geen dienst gevonden ({}) voor leveringautorisatie met id {}.", soortDienst, leveringsautorisatie.getId());
            return null;
        } else if (diensten.size() > 1) {
            LOGGER.warn("Meerdere diensten gevonden ({}) voor leveringautorisatie met id {}.", soortDienst, leveringsautorisatie.getId());
        }
        return diensten.iterator().next();
    }

    /**
     * Zoekt binnen de leveringsautorisatie naar een dienst met gegeven dienst id.
     * @param leveringsautorisatie de leveringsautorisatie
     * @param dienstId id van de dienst
     * @return dienst de gevonden dienst of null indien niet gevonden
     */
    public static Dienst zoekDienst(final Leveringsautorisatie leveringsautorisatie, final int dienstId) {
        final Collection<Dienst> diensten = zoekDiensten(leveringsautorisatie);
        Dienst dienst = null;
        if (!diensten.isEmpty()) {
            dienst = diensten.stream().filter(dienstBinnenLevAut -> dienstBinnenLevAut.getId() == dienstId).findFirst().orElse(null);
        }
        return dienst;
    }

    /**
     * Zoekt binnen de leveringsautorisatie naar diensten van de gegeven soort.
     * @param leveringsautorisatie de leveringsautorisatie
     * @param dienstFilter soort filter
     * @return collection van diensten
     */
    public static Collection<Dienst> zoekDiensten(final Leveringsautorisatie leveringsautorisatie, final SoortDienst... dienstFilter) {
        final Set<Dienst> diensten = new HashSet<>();
        for (final Dienstbundel dienstbundel : leveringsautorisatie.getDienstbundelSet()) {
            for (final Dienst dienst : dienstbundel.getDienstSet()) {
                boolean voldoetAanFilter = bepaalVoldoetAanFilter(dienst, dienstFilter);
                if (voldoetAanFilter) {
                    diensten.add(dienst);
                }

            }
        }
        return diensten;
    }

    private static boolean bepaalVoldoetAanFilter(final Dienst dienst, final SoortDienst[] dienstFilter) {
        boolean voldoetAanFilter = dienstFilter == null || dienstFilter.length == 0;
        if (dienstFilter != null) {
            for (final SoortDienst soortDienst : dienstFilter) {
                if (soortDienst == dienst.getSoortDienst()) {
                    voldoetAanFilter = true;
                    break;
                }
            }
        }
        return voldoetAanFilter;
    }

    /**
     * Geeft indicatie of de een peildatum geldig is binnen de gegeven range.
     * @param peilDatum de peilDatum
     * @param datumIngang de datumIngang
     * @param datumEinde de datumEinde
     * @return boolean geldig
     */
    public static boolean isGeldigOp(final Integer peilDatum, final Integer datumIngang, final Integer datumEinde) {
        if (datumIngang == null) {
            return false;
        }
        return datumIngang <= peilDatum && (datumEinde == null || datumEinde > peilDatum);
    }
}
