/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Externe code
package nl.moderniseringgba.migratie.logging.service.impl;

import java.util.Set;

import nl.gba.gbav.lo3.GegevensSet;
import nl.gba.gbav.lo3.PLData;
import nl.gba.gbav.spontaan.impl.Gebeurtenis;
import nl.gba.gbav.spontaan.verschilanalyse.StapelMatch;
import nl.gba.gbav.spontaan.verschilanalyse.VoorkomenMatch;
import nl.ictu.spg.common.util.StringUtil;
import nl.ictu.spg.domain.lo3.util.LO3Meta;

/**
 * Generator klasse voor de vingerafdruk. De vingerafdruk wordt als volgt opgebouwd:
 * <ul>
 * <li>Cxx - waarbij xx het categorie nummer is</li>
 * <li>Sxx - waarbij xx het stapel nummer is</li>
 * <li>Vxx - waarbij xx het voorkomen nummer is</li>
 * <li>elementnummer (bv 0110)</li>
 * <li>A,R,M - Geeft aan of het elementnummer nieuw (A), verwijderd(R) of aangepast(M) is</li>
 * </ul>
 * <br>
 * Een voorbeeld van een vingerafdruk is:<br>
 * <code>C01S00V000110A0120A</code><br>
 * Hierbij is dus van Categorie 1, Stapel 0, Voorkomen 0 (actuele voorkomen) elementnummers 01.10 en 01.20 toegevoegd. <br>
 * <br>
 * Als het voorkomen het element 83.10 bevat, dan wordt de waarde van dit element meegenomen in de vingerafdruk.<br>
 * Deze waarde wordt geplaatst tussen het elementnummer en de modifier.<br>
 * Als het voorkomen het element 72.10 bevat en de waarde is 'T' of 'W', dan wordt deze waarde meegenomen in de
 * vingerafdruk.<br>
 * Deze waarde wordt geplaatst tussen het elementnummer en de modifier.<br>
 * <br>
 * Wanneer een stapel verwijderd is, dan wordt de vingerafdruk als volgt opgebouwd:
 * <ul>
 * <li>Cxx - waarbij xx het categorie nummer is</li>
 * <li>Sxx - waarbij xx het stapel nummer is</li>
 * <li>DELETED</li>
 * </ul>
 * <br>
 * <br>
 * Indien een stapel niet uniek gematched kan worden, dan zal het volgende vingerafdruk worden opgebouwd:
 * <ul>
 * <li>Cxx - waarbij xx het categorie nummer is</li>
 * <li>NON_UNIQUE</li>
 * </ul>
 * 
 * @author Jan-Paul van Reenen
 * 
 */

public class FingerPrintGenerator {

    private static final String CAT_PREFIX = "C";
    private static final String STAPEL_PREFIX = "S";
    private static final String VK_PREFIX = "V";
    private static final String ADDED = "A";
    private static final String REMOVED = "R";
    private static final String MODIFIED = "M";
    private static final int CAT_AFNEMER_INDICATIE = 14;

    /**
     * Genereert de vingerafdruk aan de hand van de meegegeven gebeurtenis
     * 
     * @param gebeurtenis
     *            Gebeurtenis waar de vingerafdruk van gegenereerd moet worden
     * @return de gegenereerde vingerafdruk
     */
    public static String createFingerprint(final Gebeurtenis gebeurtenis) {
        final StapelMatch stapelMatch = gebeurtenis.getStapelMatch();
        String fingerprint = null;

        if (stapelMatch.getCategorie() != FingerPrintGenerator.CAT_AFNEMER_INDICATIE) {
            if (StapelMatch.NON_UNIQUE_MATCHED == stapelMatch.getType()) {
                fingerprint = FingerPrintGenerator.buildNonUniqueFingerprint(stapelMatch);
            } else if (StapelMatch.DELETED == stapelMatch.getType()) {
                fingerprint = FingerPrintGenerator.buildDeletedFingerprint(stapelMatch);
            } else {
                fingerprint = FingerPrintGenerator.buildVoorkomenFingerprint(gebeurtenis);
            }
        }
        return fingerprint;
    }

    private static String buildVoorkomenFingerprint(final Gebeurtenis gebeurtenis) {
        final StringBuilder sb = new StringBuilder();
        final VoorkomenMatch voorkomenMatch = gebeurtenis.getVoorkomenMatch();
        final PLData difference = voorkomenMatch.getDifference();
        final int catID = voorkomenMatch.getCategorie();

        final GegevensSet newVoorkomen = difference.getGegevensSet(catID, 0, 0);
        final GegevensSet oldVoorkomen = difference.getGegevensSet(catID, 0, 1);

        final Set<Integer> oldVkElements = oldVoorkomen.getElements();
        final Set<Integer> newVkElements = newVoorkomen.getElements();

        boolean changed = false;
        sb.append(FingerPrintGenerator.CAT_PREFIX).append(StringUtil.zeroPadded(catID, 2));
        if (oldVkElements.isEmpty()) {
            if (newVkElements.isEmpty()) {
                // Geen wijzigingen, dus geen vingerprint
            } else {
                final int stapelNr = gebeurtenis.getStapelMatch().getNewStapel(0).getStapelNr();
                sb.append(FingerPrintGenerator.STAPEL_PREFIX).append(StringUtil.zeroPadded(stapelNr, 2));

                // Nieuwe voorkomen toegevoegd
                sb.append(FingerPrintGenerator.VK_PREFIX).append(
                        StringUtil.zeroPadded(voorkomenMatch.getNewVoorkomen().getVolgNr(), 2));
                for (final Integer elementID : newVoorkomen.getElements()) {
                    sb.append(StringUtil.zeroPadded(elementID, 4));
                    sb.append(FingerPrintGenerator.ADDED);
                }
                changed = true;
            }
        } else {
            if (newVkElements.isEmpty()) {
                // Voorkomen verwijderd
                changed = true;
                final int stapelNr = gebeurtenis.getStapelMatch().getOldStapel(0).getStapelNr();
                sb.append(FingerPrintGenerator.STAPEL_PREFIX).append(StringUtil.zeroPadded(stapelNr, 2));
                sb.append(FingerPrintGenerator.VK_PREFIX).append(
                        StringUtil.zeroPadded(voorkomenMatch.getOldVoorkomen().getVolgNr(), 2));
                sb.append(FingerPrintGenerator.REMOVED);
            } else {
                // Wijzigingen in de voorkomen
                final int stapelNr = gebeurtenis.getStapelMatch().getNewStapel(0).getStapelNr();
                sb.append(FingerPrintGenerator.STAPEL_PREFIX).append(StringUtil.zeroPadded(stapelNr, 2));

                sb.append(FingerPrintGenerator.VK_PREFIX).append(
                        StringUtil.zeroPadded(voorkomenMatch.getNewVoorkomen().getVolgNr(), 2));
                for (final Integer elementID : newVoorkomen.getElements()) {
                    changed = true;

                    final String newWaarde = (String) newVoorkomen.getRubriek(elementID);
                    final String oldWaarde = (String) oldVoorkomen.getRubriek(elementID);
                    String value = "";
                    if (LO3Meta.RUBRIEK_AANGIFTE_ADRESHOUDING_OMS == elementID) {
                        if (newWaarde.equalsIgnoreCase("W")) {
                            value = "W";
                        } else if (newWaarde.equalsIgnoreCase("T")) {
                            value = "T";
                        }
                    }
                    if (LO3Meta.RUBRIEK_ONDERZOEK_GEGEVENS_AAND == elementID) {
                        if (newWaarde != null) {
                            value = newWaarde;
                        } else {
                            value = oldWaarde;
                        }
                    }
                    if ((oldWaarde == null) || oldWaarde.isEmpty()) {
                        sb.append(StringUtil.zeroPadded(elementID, 4));
                        sb.append(value);
                        sb.append(FingerPrintGenerator.ADDED);
                    } else if ((newWaarde == null) || newWaarde.isEmpty()) {
                        sb.append(StringUtil.zeroPadded(elementID, 4));
                        sb.append(value);
                        sb.append(FingerPrintGenerator.REMOVED);
                    } else if (!oldWaarde.equals(newWaarde)) {
                        sb.append(StringUtil.zeroPadded(elementID, 4));
                        sb.append(value);
                        sb.append(FingerPrintGenerator.MODIFIED);
                    }
                }
            }
        }
        return changed ? sb.toString() : null;
    }

    private static String buildNonUniqueFingerprint(final StapelMatch sm) {
        final StringBuilder sb = new StringBuilder();
        sb.append(FingerPrintGenerator.CAT_PREFIX).append(StringUtil.zeroPadded(sm.getCategorie(), 2));
        sb.append(sm.getTypeDescription());
        return sb.toString();
    }

    private static String buildDeletedFingerprint(final StapelMatch sm) {
        final StringBuilder sb = new StringBuilder();
        sb.append(FingerPrintGenerator.CAT_PREFIX).append(StringUtil.zeroPadded(sm.getCategorie(), 2));
        sb.append(FingerPrintGenerator.STAPEL_PREFIX).append(
                StringUtil.zeroPadded(sm.getOldStapel(0).getStapelNr(), 2));
        sb.append(sm.getTypeDescription());
        return sb.toString();
    }
}
