/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 * Deze afleidingsregel beëindigt een groep op de overlappende delen
 * van een nieuw record (van een andere groep), volgens het bekende historie patroon.
 * Het is een generieke, abstracte implementatie, die gebruikt kan worden door
 * specifieke afleidingsregels mbt beëindiging.
 *
 * @param <R> Het his volledig root object via waar de te beëindigen groep te vinden is
 * @param <G> De (his) groep die toegevoegd is, als trigger voor de beëindiging
 * @param <H> De (his) groep die beeindigd moet worden
 */
public abstract class AbstractBeeindigingAfleiding
    <R extends HisVolledigRootObject,
    G extends MaterieleHistorie & MaterieelVerantwoordbaar,
    H extends MaterieelHistorisch & MaterieelVerantwoordbaar>
    extends AbstractAfleidingsregel<R>
{

    private G toegevoegdHisRecord;

    /**
     * Forwarding constructor.
     *
     * @param rootObject het root object
     * @param actie de actie
     * @param toegevoegdHisRecord het his record dat is toegevoegd
     */
    public AbstractBeeindigingAfleiding(final R rootObject, final ActieModel actie,
            final G toegevoegdHisRecord)
    {
        super(rootObject, actie);
        this.toegevoegdHisRecord = toegevoegdHisRecord;
    }

    @Override
    public final AfleidingResultaat     leidAf() {
        if (getTeBeeindigenHistorie() != null && checkPrecondities(toegevoegdHisRecord)) {
            getTeBeeindigenHistorie().beeindig(toegevoegdHisRecord, getActie());
        }
        return GEEN_VERDERE_AFLEIDINGEN;
    }

    /**
     * Checkt of de toegevoegde nationaliteit van toepassing is voor de beëindigde afleiding.
     *
     * @param toegevoegdRecord het record dat toegevoegd is
     * @return of aan de precondities voldaan is
     */
    protected abstract boolean checkPrecondities(G toegevoegdRecord);

    /**
     * Geeft de smart set terug, waarvan de materiele historie beëindigd moet worden.
     * Mocht de betreffende historie niet bestaan, geef dan null terug om aan te geven dat
     * de beëindiging niet van toepassing is.
     *
     * @return de smart set voor beëindiging of null
     */
    protected abstract MaterieleHistorieSet<H> getTeBeeindigenHistorie();

}
