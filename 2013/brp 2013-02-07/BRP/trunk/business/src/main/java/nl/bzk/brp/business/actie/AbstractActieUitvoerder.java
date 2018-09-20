/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;


/**
 * Abstracte implementatie van een actie uitvoerder die regelt dat de actie eerst wordt gevalideert en daarna
 * uitgevoerd. Implementerende classes moeten de implementatie voor de validatie en actie verwerking implementeren.
 */
public abstract class AbstractActieUitvoerder {

    @Inject
    private ActieRepository actieRepository;



    /**
     * Voert de actie uit en retourneert eventuele meldingen bij fouten.
     *
     * @param actie                         de actie die uitgevoerd dient te worden.
     * @param berichtContext                de context waarbinnen het bericht en dus actie uitgevoerd dient te worden.
     * @param administratieveHandelingModel administratieve handeling waarin actie is gedefinieerd.
     * @return meldingen.
     */
    public final List<Melding> voerUit(final Actie actie, final BerichtContext berichtContext,
                                       final AdministratieveHandelingModel administratieveHandelingModel)
    {
        List<Melding> meldingen;
        meldingen = valideerActieGegevens(actie);
        if (meldingen == null || meldingen.isEmpty()) {
            meldingen = bereidActieVerwerkingVoor(actie, berichtContext);
        }
        if (meldingen == null || meldingen.isEmpty()) {
            meldingen = verwerkActie(actie, berichtContext, administratieveHandelingModel);
        }
        return meldingen;
    }

    /**
     * Valideert de gegevens in de actie alvorens deze wordt verwerkt.
     *
     * @param actie De te valideren actie.
     * @return Lijst van eventuele validatie meldingen.
     */
    abstract List<Melding> valideerActieGegevens(final Actie actie);

    /**
     * Verwerkt de actie in de BRP.
     *
     * @param actie                         De te verwerken actie.
     * @param berichtContext                de context waarbinnen het bericht en dus actie uitgevoerd dient te worden.
     * @param administratieveHandelingModel administratieve handeling waarin actie is gedefinieerd.
     * @return Lijst van eventuele meldingen m.b.t. de verwerking.
     */
    abstract List<Melding> verwerkActie(final Actie actie, final BerichtContext berichtContext,
                                        final AdministratieveHandelingModel administratieveHandelingModel);

    /**
     * Persisteert de actie die in uitvoering is in de Actie tabel.
     *
     * @param brpActie                      De actie in uitvoering.
     * @param administratieveHandelingModel administratieve handeling waarin actie is gedefinieerd.
     * @return De gepersisteerde actie.
     */
    protected ActieModel persisteerActie(final Actie brpActie,
                                         final AdministratieveHandelingModel administratieveHandelingModel)
    {
        // let op: op dit ogenblik hangt bronnen/documenten binnen 1 actie, maar in de toekomst worden deze
        // bronnen gedeeld over meerdere acties.
        // Sla daarom nu de bronnen aprt op (Na de save.actie )

        final ActieModel actie = new ActieModel(brpActie, administratieveHandelingModel);
        return actieRepository.opslaanNieuwActie(actie);
    }

    /**
     * Bereidt de actie voor om doorgevoerd te worden in de DAL-Laag.
     *
     * @param actie          De in uitvoering zijnde actie.
     * @param berichtContext De bericht context.
     * @return Lijst met eventuele meldingen.
     */
    abstract List<Melding> bereidActieVerwerkingVoor(final Actie actie, final BerichtContext berichtContext);
}
