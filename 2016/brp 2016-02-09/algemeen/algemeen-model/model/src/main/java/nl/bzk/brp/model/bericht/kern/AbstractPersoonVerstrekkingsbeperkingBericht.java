/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonVerstrekkingsbeperkingBasis;

/**
 * De verstrekkingsbeperking zoals die voor een in de BRP gekende partij of een in een gemeentelijke verordening
 * benoemde derde voor de persoon van toepassing is.
 *
 * De formele historie van de identiteit groep is geen 'echte' formele historie maar een 'bestaandsperiode'-patroon op
 * formele historie; een verstrekkingsbeperking kan niet stoppen en weer herleven, het is dan een nieuwe
 * verstrekkingsbeperking die niets met de vorige te maken heeft. Feitelijk was er geen his_ tabel nodig en hadden de
 * verantwoordingsvelden (inclusief tijdstippen) direct op de tabel kunnen komen.
 *
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonVerstrekkingsbeperkingBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit,
        MetaIdentificeerbaar, PersoonVerstrekkingsbeperkingBasis
{

    private static final Integer META_ID = 9344;
    private PersoonBericht persoon;
    private String partijCode;
    private PartijAttribuut partij;
    private OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde;
    private String gemeenteVerordeningCode;
    private PartijAttribuut gemeenteVerordening;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBericht getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Partij van Identiteit.
     *
     * @return Partij.
     */
    public String getPartijCode() {
        return partijCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OmschrijvingEnumeratiewaardeAttribuut getOmschrijvingDerde() {
        return omschrijvingDerde;
    }

    /**
     * Retourneert Gemeente verordening van Identiteit.
     *
     * @return Gemeente verordening.
     */
    public String getGemeenteVerordeningCode() {
        return gemeenteVerordeningCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getGemeenteVerordening() {
        return gemeenteVerordening;
    }

    /**
     * Zet Persoon van Persoon \ Verstrekkingsbeperking.
     *
     * @param persoon Persoon.
     */
    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
    }

    /**
     * Zet Partij van Identiteit.
     *
     * @param partijCode Partij.
     */
    public void setPartijCode(final String partijCode) {
        this.partijCode = partijCode;
    }

    /**
     * Zet Partij van Persoon \ Verstrekkingsbeperking.
     *
     * @param partij Partij.
     */
    public void setPartij(final PartijAttribuut partij) {
        this.partij = partij;
    }

    /**
     * Zet Omschrijving derde van Persoon \ Verstrekkingsbeperking.
     *
     * @param omschrijvingDerde Omschrijving derde.
     */
    public void setOmschrijvingDerde(final OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde) {
        this.omschrijvingDerde = omschrijvingDerde;
    }

    /**
     * Zet Gemeente verordening van Identiteit.
     *
     * @param gemeenteVerordeningCode Gemeente verordening.
     */
    public void setGemeenteVerordeningCode(final String gemeenteVerordeningCode) {
        this.gemeenteVerordeningCode = gemeenteVerordeningCode;
    }

    /**
     * Zet Gemeente verordening van Persoon \ Verstrekkingsbeperking.
     *
     * @param gemeenteVerordening Gemeente verordening.
     */
    public void setGemeenteVerordening(final PartijAttribuut gemeenteVerordening) {
        this.gemeenteVerordening = gemeenteVerordening;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteit> getBerichtEntiteiten() {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
        return Collections.emptyList();
    }

}
