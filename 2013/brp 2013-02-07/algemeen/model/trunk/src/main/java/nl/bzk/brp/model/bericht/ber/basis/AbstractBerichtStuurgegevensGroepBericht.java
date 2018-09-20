/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Applicatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Organisatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Sleutelwaardetekst;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.ber.basis.BerichtStuurgegevensGroepBasis;


/**
 *
 *
 */
public abstract class AbstractBerichtStuurgegevensGroepBericht extends AbstractGroepBericht implements
        BerichtStuurgegevensGroepBasis
{

    private Organisatienaam    organisatie;
    private Applicatienaam     applicatie;
    private Sleutelwaardetekst referentienummer;
    private Sleutelwaardetekst crossReferentienummer;

    /**
     * {@inheritDoc}
     */
    @Override
    public Organisatienaam getOrganisatie() {
        return organisatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Applicatienaam getApplicatie() {
        return applicatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sleutelwaardetekst getReferentienummer() {
        return referentienummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sleutelwaardetekst getCrossReferentienummer() {
        return crossReferentienummer;
    }

    /**
     * Zet Organisatie van Stuurgegevens.
     *
     * @param organisatie Organisatie.
     */
    public void setOrganisatie(final Organisatienaam organisatie) {
        this.organisatie = organisatie;
    }

    /**
     * Zet Applicatie van Stuurgegevens.
     *
     * @param applicatie Applicatie.
     */
    public void setApplicatie(final Applicatienaam applicatie) {
        this.applicatie = applicatie;
    }

    /**
     * Zet Referentienummer van Stuurgegevens.
     *
     * @param referentienummer Referentienummer.
     */
    public void setReferentienummer(final Sleutelwaardetekst referentienummer) {
        this.referentienummer = referentienummer;
    }

    /**
     * Zet Cross referentienummer van Stuurgegevens.
     *
     * @param crossReferentienummer Cross referentienummer.
     */
    public void setCrossReferentienummer(final Sleutelwaardetekst crossReferentienummer) {
        this.crossReferentienummer = crossReferentienummer;
    }

}
