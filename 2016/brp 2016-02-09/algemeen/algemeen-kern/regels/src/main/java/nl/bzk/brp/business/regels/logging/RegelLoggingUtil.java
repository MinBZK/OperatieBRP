/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.logging;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;

import org.apache.commons.lang.StringUtils;


/**
 * Utility class voor looging bij de verwerking van bedrijfsregels.
 */
public final class RegelLoggingUtil {

    /** De standaard prefix voor logmeldingen voor gefaalde regels. */
    public static final String  PREFIX_LOGMELDING_FOUT   = "Regel gefaald - ";

    /** De standaard prefix voor logmeldingen voor geslaagde regels. */
    public static final String  PREFIX_LOGMELDING_SUCCES = "Regel geslaagd - ";

    private static final Logger LOGGER                   = LoggerFactory.getLogger();

    private RegelLoggingUtil() {
        // private constructor
    }

    /**
     * Geeft een logmelding voor een geslaagde regel.
     *
     * @param regel de regel die is geslaagd
     * @return tekst voor de logmelding
     */
    public static String geefLogmeldingSucces(final Regel regel) {
        return geefLogmeldingSucces(PREFIX_LOGMELDING_SUCCES, regel);
    }

    /**
     * Geeft een logmelding voor een geslaagde regel.
     *
     * @param prefixLogmelding de prefix van de logmelding
     * @param regel de regel die is geslaagd
     * @return tekst voor de logmelding
     */
    public static String geefLogmeldingSucces(final String prefixLogmelding, final Regel regel) {
        return prefixLogmelding + regel.getCode();
    }

    /**
     * Geeft een logmelding voor een overtreden regel.
     *
     * @param regel de regel die is overtreden
     * @return tekst voor de logmelding
     */
    public static String geefLogmeldingFout(final Regel regel) {
        return geefLogmeldingFout(PREFIX_LOGMELDING_FOUT, regel, null, null);
    }

    /**
     * Geeft een logmelding voor een overtreden regel.
     *
     * @param regel de regel die is overtreden
     * @param situatie de situatie
     * @return tekst voor de logmelding
     */
    public static String geefLogmeldingFout(final Regel regel, final BrpObject situatie) {
        final List<Integer> burgerServiceNummers = getBurgerServiceNummers(situatie);
        final List<Long> administratienummers = getAdministratienummers(situatie);

        return geefLogmeldingFout(PREFIX_LOGMELDING_FOUT, regel, burgerServiceNummers, administratienummers);
    }

    /**
     * Geeft een logmelding voor een overtreden regel.
     *
     * @param regel de regel die is overtreden
     * @param burgerServiceNummers de burger service nummers
     * @param administratienummers de administratienummers
     * @return tekst voor de logmelding
     */
    public static String geefLogmeldingFout(final Regel regel, final List<Integer> burgerServiceNummers,
            final List<Long> administratienummers)
    {
        return geefLogmeldingFout(PREFIX_LOGMELDING_FOUT, regel, burgerServiceNummers, administratienummers);
    }

    /**
     * Geeft een logmelding voor een overtreden regel.
     *
     * @param prefix de prefix van de logmelding
     * @param regel de regel die is overtreden
     * @param situatie de situatie
     * @return tekst voor de logmelding
     */
    public static String geefLogmeldingFout(final String prefix, final Regel regel, final BrpObject situatie) {
        final List<Integer> burgerServiceNummer = getBurgerServiceNummers(situatie);
        final List<Long> administratienummers = getAdministratienummers(situatie);

        return geefLogmeldingFout(prefix, regel, burgerServiceNummer, administratienummers);
    }

    /**
     * Geeft een logmelding voor een overtreden regel met betrokken BSNs en/of ANRs.
     *
     * @param regel de regel die is overtreden
     * @param burgerServiceNummers lijst met burger service nummers
     * @param administratieNummers lijst met administratie nummers
     * @return tekst voor de logmelding
     */
    private static String geefLogmeldingFout(final String prefix, final Regel regel,
            final List<Integer> burgerServiceNummers, final List<Long> administratieNummers)
    {
        final String idPrefixBsn = " (BSN: ";
        final String idPrefixAnr = " (ANR: ";
        final String idSeparator = ", ";
        final String idPostfix = ")";

        final StringBuilder identificatieToevoeging = new StringBuilder("");
        if (burgerServiceNummers != null && !burgerServiceNummers.isEmpty()) {
            identificatieToevoeging.append(idPrefixBsn).append(StringUtils.join(burgerServiceNummers, idSeparator))
                    .append(idPostfix);
        }
        if (administratieNummers != null && !administratieNummers.isEmpty()) {
            identificatieToevoeging.append(idPrefixAnr).append(StringUtils.join(administratieNummers, idSeparator))
                    .append(idPostfix);
        }

        return prefix + regel.getCode() + ": " + regel.getOmschrijving() + identificatieToevoeging;
    }

    /**
     * Geeft een lijst met burgerservicenummers afhankelijk van het type situatie.
     *
     * @return de burger service nummers
     */
    private static List<Integer> getBurgerServiceNummers(final BrpObject situatie) {
        final List<Integer> burgerServiceNummers = new ArrayList<>();
        if (situatie instanceof PersoonView) {
            final Integer burgerServiceNummer = getBurgerServiceNummer((PersoonView) situatie);
            if (burgerServiceNummer != null) {
                burgerServiceNummers.add(burgerServiceNummer);
            }
        } else if (situatie instanceof BerichtBericht) {
            final List<Integer> burgerServiceNummersLijst = getBurgerServiceNummers((BerichtBericht) situatie);
            if (!burgerServiceNummersLijst.isEmpty()) {
                burgerServiceNummers.addAll(burgerServiceNummersLijst);
            }
        }
        return burgerServiceNummers;
    }

    /**
     * Geeft een lijst met administratienummers afhankelijk van het type situatie.
     *
     * @return de administratienummers
     */
    private static List<Long> getAdministratienummers(final BrpObject situatie) {
        final List<Long> administratienummers = new ArrayList<>();
        if (situatie instanceof PersoonView) {
            final Long administratienummer = getAdministratienummer((PersoonView) situatie);
            if (administratienummer != null) {
                administratienummers.add(administratienummer);
            }
        } else if (situatie instanceof BerichtBericht) {
            final List<Long> administratienummersLijst = getAdministratienummers((BerichtBericht) situatie);
            if (!administratienummersLijst.isEmpty()) {
                administratienummers.addAll(administratienummersLijst);
            }
        }
        return administratienummers;
    }

    /**
     * Geeft het burgerservicenummer als deze bekend is.
     *
     * @param persoonView de persoon view
     * @return het burger service nummer van de persoon.
     */
    private static Integer getBurgerServiceNummer(final PersoonView persoonView) {
        final Integer burgerServiceNummer;
        if (persoonView.getIdentificatienummers() != null
            && persoonView.getIdentificatienummers().getBurgerservicenummer() != null
            && persoonView.getIdentificatienummers().getBurgerservicenummer().heeftWaarde())
        {
            burgerServiceNummer = persoonView.getIdentificatienummers().getBurgerservicenummer().getWaarde();
        } else {
            burgerServiceNummer = null;
        }
        return burgerServiceNummer;
    }

    /**
     * Geeft het burgerservicenummer als deze bekend is.
     *
     * @param bericht het bericht
     * @return het burger service nummer van de persoon.
     */
    private static List<Integer> getBurgerServiceNummers(final BerichtBericht bericht) {
        final List<Integer> burgerServiceNummers = new ArrayList<>();

        if (bericht.getZoekcriteriaPersoon() != null
            && bericht.getZoekcriteriaPersoon().getBurgerservicenummer() != null
            && bericht.getZoekcriteriaPersoon().getBurgerservicenummer().getWaarde() != null)
        {
            final Integer bsn = bericht.getZoekcriteriaPersoon().getBurgerservicenummer().getWaarde();
            burgerServiceNummers.add(bsn);
        } else {
            LOGGER.warn("BSN kon niet uit het bericht worden verkregen.");
        }

        return burgerServiceNummers;
    }

    /**
     * Geeft het administratienummer als deze bekend is.
     *
     * @param persoonView de persoon view
     * @return het administratienummer van de persoon.
     */
    private static Long getAdministratienummer(final PersoonView persoonView) {
        final Long administratienummer;
        if (persoonView.getIdentificatienummers() != null
            && persoonView.getIdentificatienummers().getAdministratienummer() != null
            && persoonView.getIdentificatienummers().getAdministratienummer().heeftWaarde())
        {
            administratienummer = persoonView.getIdentificatienummers().getAdministratienummer().getWaarde();
        } else {
            administratienummer = null;
        }
        return administratienummer;
    }

    /**
     * Geeft het administratienummer als deze bekend is.
     *
     * @param bericht het bericht
     * @return het administratienummer van de persoon.
     */
    private static List<Long> getAdministratienummers(final BerichtBericht bericht) {
        final List<Long> administratienummers = new ArrayList<>();

        if (bericht.getZoekcriteriaPersoon() != null
            && bericht.getZoekcriteriaPersoon().getAdministratienummer() != null
            && bericht.getZoekcriteriaPersoon().getAdministratienummer().getWaarde() != null)
        {
            final Long anr = bericht.getZoekcriteriaPersoon().getAdministratienummer().getWaarde();
            administratienummers.add(anr);
        } else {
            LOGGER.warn("Administratienummer kon niet uit het bericht worden verkregen.");
        }

        return administratienummers;
    }
}
