/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle.rapport;

import java.util.ArrayList;
import java.util.List;

/**
 * De gekozen opties waarmee de controle is uitgevoerd. <br />
 * Het aantal geselecteerde PL-en uit GBA-V-PL-en. <br />
 * Het aantal geselecteerde PL-en uit BRP-PL-en. <br />
 * Het aantal geconstateerde verschillen. <br />
 * Indien er verschillen zijn geconstateerd, dan wordt een lijst (tabel) gemaakt met per PL de voorgestelde herstelactie
 * (de herstellijst).
 */
public class ControleRapport {

    private static final String NEW_LINE = "\n";

    private Opties opties;
    private int aantalGbavPl;
    private int aantalBrpPl;
    private int aantalVerschillen;
    private Herstellijst herstellijst;
    private List<BijzondereSituatie> bijzondereSituaties;

    /**
     * @return the opties
     */
    public final Opties getOpties() {
        return opties;
    }

    /**
     * @param opties
     *            the opties to set
     */
    public final void setOpties(final Opties opties) {
        this.opties = opties;
    }

    /**
     * @return the aantalGbavPl
     */
    public final int getAantalGbavPl() {
        return aantalGbavPl;
    }

    /**
     * @param aantalGbavPl
     *            the aantalGbavPl to set
     */
    public final void setAantalGbavPl(final int aantalGbavPl) {
        this.aantalGbavPl = aantalGbavPl;
    }

    /**
     * @return the aantalBrpPl
     */
    public final int getAantalBrpPl() {
        return aantalBrpPl;
    }

    /**
     * @param aantalBrpPl
     *            the aantalBrpPl to set
     */
    public final void setAantalBrpPl(final int aantalBrpPl) {
        this.aantalBrpPl = aantalBrpPl;
    }

    /**
     * @return the aantalVerschillen
     */
    public final int getAantalVerschillen() {
        return aantalVerschillen;
    }

    /**
     * @param aantalVerschillen
     *            the aantalVerschillen to set
     */
    public final void setAantalVerschillen(final int aantalVerschillen) {
        this.aantalVerschillen = aantalVerschillen;
    }

    /**
     * @return the herstellijst
     */
    public final Herstellijst getHerstellijst() {
        if (herstellijst == null) {
            setHerstellijst(new Herstellijst());
        }
        return herstellijst;
    }

    /**
     * @param herstellijst
     *            the herstellijst to set
     */
    public final void setHerstellijst(final Herstellijst herstellijst) {
        this.herstellijst = herstellijst;
    }

    /**
     * @return the bijzondereSituaties
     */
    public final List<BijzondereSituatie> getBijzondereSituaties() {
        if (bijzondereSituaties == null) {
            setBijzondereSituaties(new ArrayList<BijzondereSituatie>());
        }
        return bijzondereSituaties;
    }

    /**
     * @param bijzondereSituaties
     *            the bijzondereSituaties to set
     */
    public final void setBijzondereSituaties(final List<BijzondereSituatie> bijzondereSituaties) {
        this.bijzondereSituaties = bijzondereSituaties;
    }

    /**
     * Formatter het rapport in een mooie string.
     * 
     * @return geformatteerde rapport als String.
     */
    public final String formatRapport() {
        final StringBuffer rapport = new StringBuffer();
        if (getOpties() != null) {
            rapport.append("Eventueel opgegeven gemeentecode: " + getOpties().getGemeenteCode() + NEW_LINE);
            rapport.append("Controle niveau: " + getOpties().getControleNiveau().name() + NEW_LINE);
            rapport.append("Controle type: " + getOpties().getControleType().name() + NEW_LINE);
            rapport.append("PL-en selecteren vanaf datum stempel: " + getOpties().getVanaf() + NEW_LINE);
            rapport.append("PL-en selecteren tot datum stempel: " + getOpties().getTot() + NEW_LINE);
        }
        rapport.append(NEW_LINE);
        rapport.append("Aantal BRP PL-en: " + getAantalBrpPl() + NEW_LINE);
        rapport.append("Aantal GBA-V PL-en: " + getAantalGbavPl() + NEW_LINE);
        rapport.append("Aantal verschillen: " + getAantalVerschillen() + NEW_LINE);
        rapport.append(NEW_LINE);

        if (getBijzondereSituaties() != null) {
            rapport.append("Aantal bijzondere situaties (kunnen meerdere zijn per PL): "
                    + getBijzondereSituaties().size() + NEW_LINE);
            for (final BijzondereSituatie situatie : getBijzondereSituaties()) {
                rapport.append("Bijzondere situatie: " + situatie.getFoutmelding() + NEW_LINE);
            }
        }
        rapport.append(NEW_LINE);
        final Herstellijst lijst = getHerstellijst();
        if (lijst != null && lijst.getHerstelActies() != null) {
            rapport.append("Aantal herstelacties: " + lijst.getHerstelActies().size() + NEW_LINE);
            for (final HerstelActie actie : lijst.getHerstelActies()) {
                rapport.append("Herstelactie (anummer, actie): "
                        + actie.getAnummer()
                        + " "
                        + (actie.getActie() != null ? actie.getActie().name()
                                : "Juiste actie kon niet bepaald worden. (Geen gemeentecode gevonden?)") + NEW_LINE);
            }
        }
        return rapport.toString();
    }
}
