/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import nl.bzk.brp.model.bericht.ber.basis.AbstractGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.logisch.ber.GedeblokkeerdeMelding;
import nl.bzk.brp.model.validatie.SoortOverruleMelding;


/**
 * Een melding die gedeblokkeerd is.
 * 
 * Bij het controleren van een bijhoudingsbericht kunnen er ��n of meer meldingen zijn die gedeblokkeerd dienen te
 * worden opdat de bijhouding ook daadwerkelijk verricht kan worden. De gedeblokkeerde meldingen worden geadministreerd.
 * 
 * 
 * 
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.4.6.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-12-18 10:50:13.
 * Gegenereerd op: Tue Dec 18 10:54:27 CET 2012.
 */
@SuppressWarnings("serial")
public class GedeblokkeerdeMeldingBericht extends AbstractGedeblokkeerdeMeldingBericht implements GedeblokkeerdeMelding
{

    /**
     * soort definieert welk type / categorie de bijbehorende bedrijfsregel worden uitgevoerd.
     * sommige van die bedrijfsregel kan pas NA de verwerking uitgevoerd worden, en bij de validatie
     * voor het uitvoer stap, moeten alle overrulebare fouten zijn geneutraliseerd
     * (behalve die geldig zijn voor bedrijfsregels die in de naverwerking fase zitten).
     * Daarom moeten we hier kunnen zetten wanneer deze bedrijfsregels validatie uitgevoerd wordt.
     *
     * Standaard staat deze op BEDRIJFSREGEL_MELDING
     * In de uitzonderlijke gevallen wordt deze op NABEWERKING_VALIDATIE_MELDING gezet.
     */
    private SoortOverruleMelding soort = SoortOverruleMelding.BEDRIJFSREGEL_MELDING;

    public SoortOverruleMelding getSoort() {
        return soort;
    }

    public void setSoort(final SoortOverruleMelding soort) {
        this.soort = soort;
    }

}
