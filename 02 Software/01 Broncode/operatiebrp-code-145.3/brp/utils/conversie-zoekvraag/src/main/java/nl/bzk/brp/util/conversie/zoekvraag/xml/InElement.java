/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.conversie.zoekvraag.xml;


public class InElement {

    private String indicatieAdresVraag;
    private String indicatieZoekenInHistorie;
    private Masker masker;
    private Parameters parameters;

    public String getIndicatieAdresVraag() {
        return indicatieAdresVraag;
    }

    public void setIndicatieAdresVraag(final String indicatieAdresVraag) {
        this.indicatieAdresVraag = indicatieAdresVraag;
    }

    public String getIndicatieZoekenInHistorie() {
        return indicatieZoekenInHistorie;
    }

    public void setIndicatieZoekenInHistorie(final String indicatieZoekenInHistorie) {
        this.indicatieZoekenInHistorie = indicatieZoekenInHistorie;
    }

    public Masker getMasker() {
        return masker;
    }

    public void setMasker(final Masker masker) {
        this.masker = masker;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(final Parameters parameters) {
        this.parameters = parameters;
    }
}
