/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.basis;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Configuratie object voor generatoren. Middels deze configuratie kan de generatie in een {@link Generator}
 * geconfigureerd worden en kan o.a. bepaald worden waar een generator naar toe genereert.
 */
public class GeneratorConfiguratie {

    private String  naam;
    private String  pad;
    private boolean overschrijf;
    private boolean generationGapPatroon;
    private boolean generationGapPatroonOverschrijf;
    private boolean alleenRapportage;

    /**
     * Standaard constructor die de verschillende configuratie parameters instantieert naar hun (veilige) standaard
     * waarden. Merk op dat standaard alle bestaande bestanden worden overschreven, behalve de 'user' variant indien
     * generation gap patroon gebruikt dient te worden.
     */
    public GeneratorConfiguratie() {
        this.naam = "generator";
        this.pad = ".";
        this.overschrijf = true;
        this.generationGapPatroon = false;
        this.generationGapPatroonOverschrijf = false;
        this.alleenRapportage = false;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public String getPad() {
        return pad;
    }

    public void setPad(final String pad) {
        this.pad = pad;
    }

    public boolean isOverschrijf() {
        return overschrijf;
    }

    public void setOverschrijf(final boolean overschrijf) {
        this.overschrijf = overschrijf;
    }

    public boolean isGenerationGapPatroon() {
        return generationGapPatroon;
    }

    public void setGenerationGapPatroon(final boolean generationGapPatroon) {
        this.generationGapPatroon = generationGapPatroon;
    }

    public boolean isGenerationGapPatroonOverschrijf() {
        return generationGapPatroonOverschrijf;
    }

    public void setGenerationGapPatroonOverschrijf(final boolean generationGapPatroonOverschrijf) {
        this.generationGapPatroonOverschrijf = generationGapPatroonOverschrijf;
    }

    public boolean isAlleenRapportage() {
        return alleenRapportage;
    }

    public void setAlleenRapportage(final boolean alleenRapportage) {
        this.alleenRapportage = alleenRapportage;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("naam", naam)
                                        .append("pad", pad)
                                        .append("overschrijf", overschrijf)
                                        .append("generation gap", generationGapPatroon)
                                        .append("overschrijf bij generation gap", generationGapPatroonOverschrijf)
                                        .append("alleen rapportage", alleenRapportage).toString();
    }
}
