/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ActieBron;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;

/**
 * Util class voor delta op onderzoek.
 */
public final class OnderzoekUtil {

    /**
     * De relatie categorieen.
     */
    private static final Set<String> RELATIE_CATEGORIEEN = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "02",
            "52",
            "03",
            "53",
            "05",
            "55",
            "09",
            "59",
            "11",
            "61")));

    private OnderzoekUtil() {
    }

    /**
     * Controleert of het onderzoek een onderzoek is op een relatie categorie.
     * @param onderzoek onderzoek wat mogelijk aan de relatie gekoppeld is.
     * @return true als het een onderzoek op een relatie categorie betreft
     */
    public static boolean isOnderzoekOpRelatie(final Onderzoek onderzoek) {
        return RELATIE_CATEGORIEEN.contains(getCategorieUitOnderzoekOmschrijving(onderzoek));
    }

    /**
     * Geeft de categorie terug uit de omschrijving van het onderzoek. De omschrijving bestaat uit
     * {@link nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapperImpl#LO3_ONDERZOEK_OMSCHRIJVING_PREFIX}
     * en de waarde uit het veld 83.10 uit LO3. Deze methode bepaalt de categorie uit de waarde van 83.10 adhv de eerste 2 posities van dit veld.
     * @param onderzoek het onderzoek
     * @return de categorie waar dit onderzoek voor is
     */
    public static String getCategorieUitOnderzoekOmschrijving(final Onderzoek onderzoek) {
        final int lo3OnderzoekLength = 6;
        final String omschrijving = onderzoek.getOmschrijving();
        final String lo3Omschrijving = omschrijving.substring(omschrijving.length() - lo3OnderzoekLength);
        final int catLength = 2;
        return lo3Omschrijving.substring(0, catLength);
    }

    /**
     * Controleert of de meegegeven verzameling van onderzoeken alleen gegevens in onderzoek heeft die behoren tot
     * BRPActie, Actiebron of Document.
     * @param onderzoeken set van onderzoeken.
     * @return true als de verzameling alleen gegevens in onderzoek heeft die behoren tot BRPActie, Actiebron of Document.
     */
    public static boolean bevatAlleenActieOnderdelen(final Collection<Onderzoek> onderzoeken) {
        boolean result = !onderzoeken.isEmpty();
        for (final Onderzoek onderzoek : onderzoeken) {
            final Set<GegevenInOnderzoek> gegevenInOnderzoekSet = onderzoek.getGegevenInOnderzoekSet();
            for (final GegevenInOnderzoek gegevenInOnderzoek : gegevenInOnderzoekSet) {
                final Class<?> gegevenInOnderzoekClass = gegevenInOnderzoek.getEntiteitOfVoorkomen().getClass();
                if (!isAssignableFrom(gegevenInOnderzoekClass, BRPActie.class, Document.class, ActieBron.class)) {
                    result = false;
                }
            }
        }
        return result;
    }

    private static boolean isAssignableFrom(final Class<?> checkedClass, final Class<?>... classes) {
        boolean result = false;
        for (final Class<?> clz : classes) {
            if (clz.isAssignableFrom(checkedClass)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
