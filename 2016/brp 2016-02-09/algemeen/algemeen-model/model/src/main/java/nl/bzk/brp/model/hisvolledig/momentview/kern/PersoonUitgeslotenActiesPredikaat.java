/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetDienstVerantwoording;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.VerantwoordingTbvLeveringMutaties;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.apache.commons.collections.Predicate;
import org.springframework.util.ReflectionUtils;

/**
 * Dit predikaat bepaalt de laatst geldige records, nog niet geldige acties in ogenschouw nemend. Dit gebruiken we om een persoon te reconstueren zoals
 * deze er uit zag voor een (of meerdere) bepaalde administratieve handeling.
 */
public final class PersoonUitgeslotenActiesPredikaat implements Predicate {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private Set<Long> uitgeslotenActieIds;

    /**
     * Default constructor, deze maakt een lege lijst van uitgesloten acties aan. Dit zal bij het uitvoeren van het predikaat altijd het laatste record
     * opleveren dat geldig is.
     */
    public PersoonUitgeslotenActiesPredikaat() {
        this.uitgeslotenActieIds = new HashSet<>();
    }

    /**
     * Constructor die de uitgesloten actieId's aanneemt. De records die deze acties raken worden buitengesloten bij het bepalen van het meest recente
     * record.
     *
     * @param uitgeslotenActieIds De uitgesloten acties.
     */
    public PersoonUitgeslotenActiesPredikaat(final Set<Long> uitgeslotenActieIds) {
        if (uitgeslotenActieIds == null) {
            throw new IllegalArgumentException(String.format("uitgesloteActieId kan niet null zijn in %s.", getClass().getName()));
        }
        this.uitgeslotenActieIds = uitgeslotenActieIds;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean evaluate(final Object object) {
        boolean resultaat = true;
        if (object instanceof FormeelVerantwoordbaar && !(object instanceof AbstractFormeelHistorischMetDienstVerantwoording)) {
            resultaat = evalueerFormeelverantwoordbaar((FormeelVerantwoordbaar<ActieModel>) object);
        }
        return resultaat;
    }

    /**
     * Evalueert het formeel verantwoordbare object.
     *
     * @param formeelVerantwoordbaar Het object.
     * @return Boolean true als aan het predikaat voldaan wordt, anders false.
     */
    private boolean evalueerFormeelverantwoordbaar(final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar) {
        verifieerFormeelVerantwoordbaar(formeelVerantwoordbaar);

        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;
        if (formeelVerantwoordbaar instanceof MaterieelHistorisch) {
            final MaterieelHistorisch materieelHistorisch = (MaterieelHistorisch) formeelVerantwoordbaar;
            datumEindeGeldigheid = materieelHistorisch.getMaterieleHistorie().getDatumEindeGeldigheid();
        } else {
            datumEindeGeldigheid = null;
        }

        final boolean nogNietVervallenEnNietToekomstig = formeelVerantwoordbaar.getVerantwoordingVerval() == null
            && datumEindeGeldigheid == null
            && (formeelVerantwoordbaar.getVerantwoordingInhoud() == null
            || !uitgeslotenActieIds.contains(formeelVerantwoordbaar.getVerantwoordingInhoud().getID()));


        final boolean isVervallenEnHeeftGeenEindeGeldigheid = formeelVerantwoordbaar.getVerantwoordingVerval() != null
            && datumEindeGeldigheid == null
            && formeelVerantwoordbaar.getVerantwoordingVerval() != null;

        boolean uitgeslotenActiesBevatActieVerval = false;

        if (formeelVerantwoordbaar instanceof VerantwoordingTbvLeveringMutaties) {
            final ActieModel actieVerval = geefMutLevActieVervalOfVerantwoordingVerval((VerantwoordingTbvLeveringMutaties) formeelVerantwoordbaar);
            if (actieVerval != null) {
                uitgeslotenActiesBevatActieVerval = uitgeslotenActieIds.contains(actieVerval.getID());
            }
        } else if (formeelVerantwoordbaar.getVerantwoordingVerval() != null) {
            uitgeslotenActiesBevatActieVerval = uitgeslotenActieIds.contains(formeelVerantwoordbaar.getVerantwoordingVerval().getID());
        }

        final boolean uitgeslotenActiesBevatActieInhoud = formeelVerantwoordbaar.getVerantwoordingInhoud() != null
            && uitgeslotenActieIds.contains(formeelVerantwoordbaar.getVerantwoordingInhoud().getID());

        final boolean welVervallenMaarInUitgeslotenActie = isVervallenEnHeeftGeenEindeGeldigheid
            && uitgeslotenActiesBevatActieVerval && !uitgeslotenActiesBevatActieInhoud;

        return nogNietVervallenEnNietToekomstig || welVervallenMaarInUitgeslotenActie;
    }

    private void verifieerFormeelVerantwoordbaar(final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar) {
        Objects.requireNonNull(formeelVerantwoordbaar, "formeelVerwantwoordbaar kan niet null zijn");

        if (formeelVerantwoordbaar.getVerantwoordingInhoud() == null) {
            LOGGER.debug("verantwoordingInhoud is null voor {}", formeelVerantwoordbaar.getClass().getName());
            try {
                final Number id = (Number) ReflectionUtils.getField(formeelVerantwoordbaar.getClass().getField("iD"), formeelVerantwoordbaar);
                LOGGER.debug("formeelVerantwoordbaar iD is {}", id.longValue());
            } catch (NoSuchFieldException e) {
                LOGGER.debug("Er is geen iD field beschikbaar voor ", formeelVerantwoordbaar.getClass().getName());
            }
        }
    }

    private ActieModel geefMutLevActieVervalOfVerantwoordingVerval(final VerantwoordingTbvLeveringMutaties verantwoordingTbvLeveringMutaties) {
        final ActieModel actieModel;
        if (verantwoordingTbvLeveringMutaties.getVerantwoordingVervalTbvLeveringMutaties() != null)
        {
            actieModel = verantwoordingTbvLeveringMutaties.getVerantwoordingVervalTbvLeveringMutaties();
        } else {
            actieModel = verantwoordingTbvLeveringMutaties.getVerantwoordingVerval();
        }
        return actieModel;
    }
}
