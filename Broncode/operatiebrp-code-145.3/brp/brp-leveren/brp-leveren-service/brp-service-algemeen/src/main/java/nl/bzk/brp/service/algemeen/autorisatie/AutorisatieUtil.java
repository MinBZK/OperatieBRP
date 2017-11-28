/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.autorisatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import org.apache.commons.lang3.BooleanUtils;

/**
 * Utilklasse voor generieke autorisatiecontroles.
 */
public final class AutorisatieUtil {

    private AutorisatieUtil() {
    }

    /**
     * Controleer of een dienst niet geblokkeerd is.
     * @param dienst de dienst
     * @throws AutorisatieException als de dienst geblokkeerd is
     */
    public static void assertDienstNietGeblokkeerd(final Dienst dienst) throws AutorisatieException {
        if (BooleanUtils.isTrue(dienst.getIndicatieGeblokkeerd())) {
            AutorisatieUtil.rapporteerRegelFout(Regel.R1264);
        }
    }

    /**
     * Controleer of een dienstbundel niet geblokkeerd is.
     * @param dienstbundel de dienstbundel
     * @throws AutorisatieException als de dienstbundel geblokeerd is
     */
    public static void assertDienstbundelNietGeblokkeerd(final Dienstbundel dienstbundel) throws AutorisatieException {
        if (dienstbundel != null && BooleanUtils.isTrue(dienstbundel.getIndicatieGeblokkeerd())) {
            AutorisatieUtil.rapporteerRegelFout(Regel.R2056);
        }
    }

    /**
     * Controleer of een toegang leveringsautorisatie niet geblokkeerd is.
     * @param toegangLeveringsAutorisatie de toegang leveringsautorisatie
     * @throws AutorisatieException als de toegang leveringsautorisatie geblokeerd is
     */
    public static void assertToegangLeveringsAutorisatieNietGeblokkeerd(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie)
            throws AutorisatieException {
        if (BooleanUtils.isTrue(toegangLeveringsAutorisatie.getIndicatieGeblokkeerd())) {
            AutorisatieUtil.rapporteerRegelFout(Regel.R2052);
        }
    }

    /**
     * Controleer of de leveringsautorisatie niet geblokeerd is.
     * @param leveringsautorisatie de leveringsautorisatie
     * @throws AutorisatieException als de leveringsautorisatie geblokeerd is
     */
    public static void assertLeveringsautorisieNietGeblokkeerd(final Leveringsautorisatie leveringsautorisatie) throws AutorisatieException {
        if (BooleanUtils.isTrue(leveringsautorisatie.getIndicatieGeblokkeerd())) {
            AutorisatieUtil.rapporteerRegelFout(Regel.R1263);
        }
    }

    /**
     * Controleer een dienst op geldigheid.
     * @param dienst de te controleren dienst
     * @throws AutorisatieException als de dienst niet geldig is
     */
    public static void assertDienstGeldig(final Dienst dienst) throws AutorisatieException {
        final Integer vandaag = BrpNu.get().alsIntegerDatumNederland();
        if (!AutAutUtil.isGeldigOp(vandaag, dienst.getDatumIngang(), dienst.getDatumEinde())) {
            rapporteerRegelFout(Regel.R1262);
        }
    }

    /**
     * Controleer een dienstbundel op geldigheid.
     * @param dienstbundel de dienstbundel
     * @throws AutorisatieException als de dienstbundel niet geldig is
     */
    public static void assertDienstbundelGeldig(final Dienstbundel dienstbundel) throws AutorisatieException {
        final Integer vandaag = BrpNu.get().alsIntegerDatumNederland();
        if (dienstbundel != null && !AutAutUtil.isGeldigOp(vandaag, dienstbundel.getDatumIngang(), dienstbundel.getDatumEinde())) {
            AutorisatieUtil.rapporteerRegelFout(Regel.R2239);
        }
    }

    /**
     * Controleer een toegang leveringsautorisatie op geldigheid.
     * @param toegangLeveringsAutorisatie de toegang leveringsautorisatie
     * @throws AutorisatieException als de toegang leveringsautorisatie niet geldig is
     */
    public static void assertToegangLeveringsAutorisatieGeldig(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie) throws AutorisatieException {
        final Integer vandaag = BrpNu.get().alsIntegerDatumNederland();
        if (!AutAutUtil.isGeldigOp(vandaag, toegangLeveringsAutorisatie.getDatumIngang(), toegangLeveringsAutorisatie.getDatumEinde())) {
            AutorisatieUtil.rapporteerRegelFout(Regel.R1258);
        }
    }

    /**
     * Controleer een leveringsautorisatie op geldigheid.
     * @param leveringsautorisatie de leveringsautorisatie
     * @throws AutorisatieException als de leveringsautorisatie niet geldig is
     */
    public static void assertLeveringsautorisieGeldig(final Leveringsautorisatie leveringsautorisatie) throws AutorisatieException {
        final Integer vandaag = BrpNu.get().alsIntegerDatumNederland();
        if (!AutAutUtil.isGeldigOp(vandaag, leveringsautorisatie.getDatumIngang(), leveringsautorisatie.getDatumEinde())) {
            AutorisatieUtil.rapporteerRegelFout(Regel.R1261);
        }
    }

    /**
     * Controleer een partijrol op geldigheid.
     * @param partijRol de partijrol
     * @throws AutorisatieException als de partijrol niet geldig is
     */
    public static void assertPartijRolGeldig(final PartijRol partijRol) throws AutorisatieException {
        final Integer vandaag = BrpNu.get().alsIntegerDatumNederland();
        if (!AutAutUtil.isGeldigOp(vandaag, partijRol.getDatumIngang(), partijRol.getDatumEinde())) {
            AutorisatieUtil.rapporteerRegelFout(Regel.R2245);
        }
    }

    /**
     * Controleer een aan partijrol gekoppelde partij op geldigheid.
     * @param partijRol de partijrol
     * @throws AutorisatieException als de aan de partijrol gekoppelde partij niet geldig is
     */
    public static void assertPartijUitPartijRolGeldig(final PartijRol partijRol) throws AutorisatieException {
        final Integer vandaag = BrpNu.get().alsIntegerDatumNederland();
        if (partijRol.getPartij() == null || !AutAutUtil.isGeldigOp(vandaag, partijRol.getPartij().getDatumIngang(), partijRol.getPartij().getDatumEinde())) {
            AutorisatieUtil.rapporteerRegelFout(Regel.R2242);
        }
    }

    /**
     * Controleer of een leveringsautorisatie met bijbehorende partij in het BRP stelsel zit.
     * @param leveringsautorisatie de leveringsautorisatie
     * @param zendendepartij de geautoriseerde partij
     * @param isVerzoekViaBrpKoppelvlak isVerzoekViaBrpKoppelvlak
     * @throws AutorisatieException als de leveringsautorsatie of de partij niet in het BRP stelsel zit
     */
    public static void assertStelselCorrect(final Leveringsautorisatie leveringsautorisatie, final Partij zendendepartij,
                                            final boolean isVerzoekViaBrpKoppelvlak)
            throws AutorisatieException {
        if (isVerzoekViaBrpKoppelvlak && leveringsautorisatie.getStelsel() != Stelsel.BRP) {
            AutorisatieUtil.rapporteerRegelFout(Regel.R2585);
        }
        if (!stelselBrpEnOverNaarBrp(leveringsautorisatie, zendendepartij) && !stelselNietBrpEnNietOverNaarBrp(leveringsautorisatie, zendendepartij)) {
            AutorisatieUtil.rapporteerRegelFout(Regel.R2524);
        }
    }

    private static boolean stelselBrpEnOverNaarBrp(final Leveringsautorisatie leveringsautorisatie, final Partij zendendepartij) {
        return leveringsautorisatie.getStelsel() == Stelsel.BRP && (zendendepartij.getDatumOvergangNaarBrp() != null
                && zendendepartij.getDatumOvergangNaarBrp() <= DatumUtil.vandaag());
    }

    private static boolean stelselNietBrpEnNietOverNaarBrp(final Leveringsautorisatie leveringsautorisatie, final Partij zendendepartij) {
        return leveringsautorisatie.getStelsel() != Stelsel.BRP && (zendendepartij.getDatumOvergangNaarBrp() == null
                || zendendepartij.getDatumOvergangNaarBrp() > DatumUtil.vandaag());
    }

    /**
     * Rapporteer een regelfout.
     * @param regel de regel
     * @throws AutorisatieException de fout
     */
    static void rapporteerRegelFout(final Regel regel) throws AutorisatieException {
        throw new AutorisatieException(new Melding(SoortMelding.FOUT, regel));
    }

}
