/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.BetrokkenheidBasis;

/**
 * De wijze waarop een Persoon betrokken is bij een Relatie.
 *
 * Er wordt expliciet onderscheid gemaakt tussen de Relatie enerzijds, en de Persoon die in de Relatie betrokken is
 * anderzijds. De koppeling van een Persoon en een Relatie gebeurt via Betrokkenheid.
 *
 * Er zit geen unique constraint (meer) op de Relatie, Persoon combinatie. In een FRB kan het zo zijn dat het kind
 * beëindigd is op de PL van het ouder en de ouder niet op de PL van het kind (of vice versa). Bij migratie worden dan
 * twee ouderbetrekkingen aangemaakt met dezelfde ouder: de één ontkend door de ouder, de ander door het kind. Dit gaat
 * niet met een UC. Dit kan theoretisch ook voor komen bij 'heradoptie', waarbij op de ene PL de FRB is beeindigd en
 * daarna weer is opgenomen en op de andere PL niet is beëindigd, of is beëindigd maar niet weer opnieuw opgenomen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractBetrokkenheidBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar,
        BetrokkenheidBasis
{

    private static final Integer META_ID = 3857;
    private RelatieBericht relatie;
    private SoortBetrokkenheidAttribuut rol;
    private PersoonBericht persoon;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param rol de waarde van het discriminator attribuut
     */
    public AbstractBetrokkenheidBericht(final SoortBetrokkenheidAttribuut rol) {
        this.rol = rol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RelatieBericht getRelatie() {
        return relatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortBetrokkenheidAttribuut getRol() {
        return rol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBericht getPersoon() {
        return persoon;
    }

    /**
     * Zet Relatie van Betrokkenheid.
     *
     * @param relatie Relatie.
     */
    public void setRelatie(final RelatieBericht relatie) {
        this.relatie = relatie;
    }

    /**
     * Zet Persoon van Betrokkenheid.
     *
     * @param persoon Persoon.
     */
    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
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
