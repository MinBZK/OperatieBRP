/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonVerificatieBasis;

/**
 * Verificatie van gegevens in de BRP.
 *
 * Vooral voor personen die zich in het buitenland bevinden, is de kans groot dat gegevens snel verouderen. Zo is het
 * niet gegarandeerd dat een overlijden van een niet-ingezetene snel wordt gemeld. Om die reden is het, (vooral) voor de
 * populatie waarvan de bijhoudingsverantwoordelijkheid bij de Minister ligt, van belang om te weten of er verificatie
 * heeft plaatsgevonden. Het kan bijvoorbeeld zo zijn dat een Aangewezen bestuursorgaan ('RNI deelnemer') recent nog
 * contact heeft gehad met de persoon, en dat dit tot verificatie van gegevens heeft geleid. Er zijn verschillende
 * soorten verificatie; de bekendste is de 'Attestie de vita' die aangeeft dat de persoon nog in leven was ten tijde van
 * de verificatie. Door verificatiegegevens te registreren, kan de actualiteit van de persoonsgegevens van de
 * niet-ingezetene beter op waarde worden geschat.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonVerificatieBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar,
        PersoonVerificatieBasis
{

    private static final Integer META_ID = 3775;
    private PersoonBericht geverifieerde;
    private String partijCode;
    private PartijAttribuut partij;
    private NaamEnumeratiewaardeAttribuut soort;
    private PersoonVerificatieStandaardGroepBericht standaard;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBericht getGeverifieerde() {
        return geverifieerde;
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
    public NaamEnumeratiewaardeAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonVerificatieStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * Zet Geverifieerde van Persoon \ Verificatie.
     *
     * @param geverifieerde Geverifieerde.
     */
    public void setGeverifieerde(final PersoonBericht geverifieerde) {
        this.geverifieerde = geverifieerde;
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
     * Zet Partij van Persoon \ Verificatie.
     *
     * @param partij Partij.
     */
    public void setPartij(final PartijAttribuut partij) {
        this.partij = partij;
    }

    /**
     * Zet Soort van Persoon \ Verificatie.
     *
     * @param soort Soort.
     */
    public void setSoort(final NaamEnumeratiewaardeAttribuut soort) {
        this.soort = soort;
    }

    /**
     * Zet Standaard van Persoon \ Verificatie.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonVerificatieStandaardGroepBericht standaard) {
        this.standaard = standaard;
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
        final List<BerichtEntiteitGroep> berichtGroepen = new ArrayList<>();
        berichtGroepen.add(getStandaard());
        return berichtGroepen;
    }

}
