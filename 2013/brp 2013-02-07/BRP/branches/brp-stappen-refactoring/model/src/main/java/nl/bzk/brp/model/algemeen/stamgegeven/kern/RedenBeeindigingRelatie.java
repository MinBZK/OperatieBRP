/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenBeeindigingRelatieCode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractRedenBeeindigingRelatie;


/**
 * De mogelijke reden om een Relatie te be�indigen.
 *
 * Dit stamgegeven kent geen bestaansperiode. Op zich is het denkbaar dat er een bestaansperiode is, zo is omzetting
 * vanuit een Huwelijk naar een Geregistreerd partnerschap vanaf een bepaalde datum niet meer toegestaan. Er is echter
 * besloten vooralsnog geen bestaansperiode te onderkennen. Redenen hiervoor: er is nu geen behoefte aan (immers,
 * omzetting Geregistreerd partnerschap naar Huwelijk is wel mogelijk), en het invoeren hiervan ZODRA DAT NODIG IS, is
 * relatief eenvoudig. Er wordt daarom nu hier geen voorschot op de toekomst genomen.
 * RvdP 19 maart 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.DynamischStamgegevensModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 10:41:47.
 * Gegenereerd op: Tue Nov 27 10:43:33 CET 2012.
 */
@Entity
@Table(schema = "Kern", name = "RdnBeeindRelatie")
public class RedenBeeindigingRelatie extends AbstractRedenBeeindigingRelatie {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected RedenBeeindigingRelatie() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van RedenBeeindigingRelatie.
     * @param omschrijving omschrijving van RedenBeeindigingRelatie.
     */
    protected RedenBeeindigingRelatie(final RedenBeeindigingRelatieCode code,
            final OmschrijvingEnumeratiewaarde omschrijving)
    {
        super(code, omschrijving);
    }

}
