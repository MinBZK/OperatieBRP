/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.Applicatienaam;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.Organisatienaam;
import nl.bzk.brp.model.attribuuttype.Sleutelwaardetekst;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.BerichtStuurgegevensGroepBasis;

/**
 * .
 */
public abstract class AbstractBerichtStuurgegevensGroepBericht extends AbstractGroepBericht
        implements BerichtStuurgegevensGroepBasis
{

    private Organisatienaam organisatie;
    private Applicatienaam applicatie;
    private Sleutelwaardetekst referentienummer;
    private Sleutelwaardetekst crossReferentienummer;
    private Ja indPrevalidatie;

    public Organisatienaam getOrganisatie() {
        return organisatie;
    }

    public Applicatienaam getApplicatie() {
        return applicatie;
    }

    public Sleutelwaardetekst getReferentienummer() {
        return referentienummer;
    }

    public Sleutelwaardetekst getCrossReferentienummer() {
        return crossReferentienummer;
    }

    public Ja getIndPrevalidatie() {
        return indPrevalidatie;
    }

    public void setOrganisatie(final Organisatienaam organisatie) {
        this.organisatie = organisatie;
    }

    public void setApplicatie(final Applicatienaam applicatie) {
        this.applicatie = applicatie;
    }

    public void setReferentienummer(final Sleutelwaardetekst referentienummer) {
        this.referentienummer = referentienummer;
    }

    public void setCrossReferentienummer(final Sleutelwaardetekst crossReferentienummer) {
        this.crossReferentienummer = crossReferentienummer;
    }

    public void setIndPrevalidatie(final Ja indPrevalidatie) {
        this.indPrevalidatie = indPrevalidatie;
    }
}
