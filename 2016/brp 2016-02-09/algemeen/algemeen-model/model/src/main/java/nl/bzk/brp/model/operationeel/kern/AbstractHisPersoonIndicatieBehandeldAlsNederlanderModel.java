/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.annotation.Generated;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;

/**
 * Subtype klasse voor indicatie Behandeld als Nederlander?
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonIndicatieBehandeldAlsNederlanderModel extends AbstractHisPersoonIndicatieMaterieleHistorieModel implements
        ElementIdentificeerbaar
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonIndicatieBehandeldAlsNederlanderModel() {
    }

    /**
     * Copy constructor met een groep instantie.
     *
     * @param persoonIndicatieHisVolledig backreference
     * @param groep groep
     * @param historie historie
     * @param actieInhoud actieInhoud
     */
    public AbstractHisPersoonIndicatieBehandeldAlsNederlanderModel(
        final PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl persoonIndicatieHisVolledig,
        final PersoonIndicatieStandaardGroep groep,
        final MaterieleHistorie historie,
        final ActieModel actieInhoud)
    {
        super(persoonIndicatieHisVolledig, groep, historie, actieInhoud);
    }

    /**
     * Copy constructor met alle atribuut waarden.
     *
     * @param persoonIndicatieHisVolledig backreference
     * @param waarde waarde
     * @param actieInhoud actieInhoud
     * @param historie historie
     */
    public AbstractHisPersoonIndicatieBehandeldAlsNederlanderModel(
        final PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl persoonIndicatieHisVolledig,
        final JaAttribuut waarde,
        final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        super(persoonIndicatieHisVolledig, waarde, actieInhoud, historie);
    }

    /**
     * Copy constructor.
     *
     * @param kopie kopie
     */
    public AbstractHisPersoonIndicatieBehandeldAlsNederlanderModel(final AbstractHisPersoonIndicatieBehandeldAlsNederlanderModel kopie) {
        super(kopie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HisPersoonIndicatieBehandeldAlsNederlanderModel kopieer() {
        return new HisPersoonIndicatieBehandeldAlsNederlanderModel(this);
    }

}
