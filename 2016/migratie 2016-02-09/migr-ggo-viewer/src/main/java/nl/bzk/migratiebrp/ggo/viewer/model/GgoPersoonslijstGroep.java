/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import java.util.List;

/**
 * Ons Model, bevattende alle data rondom een persoonslijst die we op het scherm weer willen geven, zoals de lo3 en BRP
 * persoonslijst, de bijbehorende getriggerde precondities en BCM regels, etc.
 */
public class GgoPersoonslijstGroep {
    private final Long aNummer;
    private final String volledigeNaam;
    private final List<GgoStapel> ggoLo3PL;
    private final List<GgoStapel> ggoLo3PLTerugConversie;
    private final List<GgoStapel> ggoBrpPL;
    private final List<GgoVoorkomenVergelijking> vergelijking;
    private final List<GgoFoutRegel> meldingen;
    private final List<GgoResultaatIndex> indexRegels;
    private final List<GgoFoutRegel> foutLog;

    /**
     * @param aNummer
     *            Het anummer.
     * @param volledigeNaam
     *            Volledige naam.
     * @param ggoLo3PL
     *            De Lo3 PL in viewer formaat.
     * @param ggoLo3PLTerugConversie
     *            De persoonslijst in lo3 formaat terugconverteerd van een brp pl.
     * @param ggoBrpPL
     *            De Brp PL in viewer formaat.
     * @param vergelijking
     *            De verschillen tussen de lo3Persoonslijst en teruggeconverteerd.
     * @param meldingen
     *            De meldingen van bcm, precondities, etc.
     * @param indexRegels
     *            De resultaat regels voor het tonen in de index.
     * @param foutLog
     *            De foutlog meldingen welke zijn opgetreden tijdens de verwerking.
     */
    public GgoPersoonslijstGroep(
        final Long aNummer,
        final String volledigeNaam,
        final List<GgoStapel> ggoLo3PL,
        final List<GgoStapel> ggoLo3PLTerugConversie,
        final List<GgoStapel> ggoBrpPL,
        final List<GgoVoorkomenVergelijking> vergelijking,
        final List<GgoFoutRegel> meldingen,
        final List<GgoResultaatIndex> indexRegels,
        final List<GgoFoutRegel> foutLog)
    {
        this.aNummer = aNummer;
        this.volledigeNaam = volledigeNaam;
        this.ggoLo3PL = ggoLo3PL;
        this.ggoLo3PLTerugConversie = ggoLo3PLTerugConversie;
        this.ggoBrpPL = ggoBrpPL;
        this.vergelijking = vergelijking;
        this.meldingen = meldingen;
        this.indexRegels = indexRegels;
        this.foutLog = foutLog;
    }

    /**
     * @return the aNummer
     */
    public final Long getaNummer() {
        return aNummer;
    }

    /**
     * Geef de waarde van volledige naam.
     *
     * @return the volledigeNaam
     */
    public final String getVolledigeNaam() {
        return volledigeNaam;
    }

    /**
     * Geef de waarde van ggo lo3 pl.
     *
     * @return the ggoLo3PL
     */
    public final List<GgoStapel> getGgoLo3PL() {
        return ggoLo3PL;
    }

    /**
     * Geef de waarde van ggo lo3 pl terug conversie.
     *
     * @return the ggoLo3PLTerugConversie
     */
    public final List<GgoStapel> getGgoLo3PLTerugConversie() {
        return ggoLo3PLTerugConversie;
    }

    /**
     * Geef de waarde van ggo brp pl.
     *
     * @return the ggoBrpPL
     */
    public final List<GgoStapel> getGgoBrpPL() {
        return ggoBrpPL;
    }

    /**
     * Geef de waarde van vergelijking.
     *
     * @return the vergelijking
     */
    public final List<GgoVoorkomenVergelijking> getVergelijking() {
        return vergelijking;
    }

    /**
     * Geef de waarde van meldingen.
     *
     * @return the meldingen
     */
    public final List<GgoFoutRegel> getMeldingen() {
        return meldingen;
    }

    /**
     * Geef de waarde van index regels.
     *
     * @return the indexRegels
     */
    public final List<GgoResultaatIndex> getIndexRegels() {
        return indexRegels;
    }

    /**
     * Geef de waarde van fout log.
     *
     * @return the foutLog
     */
    public final List<GgoFoutRegel> getFoutLog() {
        return foutLog;
    }
}
