/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.DatumBasisAttribuut;
import nl.bzk.brp.model.basis.ModelRootObject;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;

/**
 * Abstracte klasse die basis vormt voor bedrijfsregels die geldigheid van bestaansperiode van stamgegevens
 * controleren.
 *
 * @param <M> root object
 * @param <B> bericht root object
 * @param <E> het type bericht entiteit dat gecheckt moet worden
 */
public abstract class AbstractBestaansPeriodeStamgegevenRegel<
        M extends ModelRootObject,
        B extends BerichtRootObject,
        E extends BerichtEntiteit>
        implements VoorActieRegelMetMomentopname<M, B>
{

    @Override
    public List<BerichtEntiteit> voerRegelUit(final M huidigeSituatie,
                                              final B nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final Map<BestaansPeriodeStamgegeven, E> stamgegevensEnEntiteiten = getStamgegevensEnEntiteiten(nieuweSituatie);

        if (voerExtraControlesUit(nieuweSituatie)) {
            for (Entry<BestaansPeriodeStamgegeven, E> stamgegevenEnEntiteit : stamgegevensEnEntiteiten.entrySet()) {
                final BestaansPeriodeStamgegeven stamgegeven = stamgegevenEnEntiteit.getKey();
                final E entiteit = stamgegevenEnEntiteit.getValue();
                final DatumBasisAttribuut controleDatum = bepaalControleDatum(actie, entiteit);

                if (!controleDatum.isGeldigTussen(stamgegeven.getDatumAanvangGeldigheid(), stamgegeven.getDatumEindeGeldigheid()))
                {
                    objectenDieDeRegelOvertreden.add(entiteit);
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Bepaald de datum die gebruikt dient te worden voor de controle van de geldigheid. Dit is peildatum.
     * Indien deze in de toekomst ligt, dan de huidige (systeem) datum.
     *
     * @param actie de actie
     * @param entiteit de te checken entiteit
     * @return de datum die gebruikt dient te worden voor controle van geldigheid.
     */
    private DatumBasisAttribuut bepaalControleDatum(final Actie actie, final E entiteit) {
        final DatumAttribuut vandaag = DatumAttribuut.vandaag();
        DatumBasisAttribuut controleDatum = getPeilDatum(actie, entiteit);

        if (controleDatum == null || controleDatum.getWaarde() == null) {
            getLogger().warn(entiteit.getClass().getSimpleName()
                                     + " bevat geen peildatum. Bedrijfsregel "
                                     + getRegel() + " gebruikt daarom systeem datum.");
            controleDatum = vandaag;
        } else if (controleDatum.na(vandaag)) {
            controleDatum = vandaag;
        }

        return controleDatum;
    }

    /**
     * Verkrijgt de specifieke logger klasse.
     *
     * @return logger
     */
    protected abstract Logger getLogger();

    /**
     * Verkrijgt de stamgegevens en entiteiten.
     *
     * @param nieuweSituatie nieuwe situatie (uit het bericht)
     * @return de lijst met stamgegevens en entiteiten om te checken
     */
    protected abstract Map<BestaansPeriodeStamgegeven, E> getStamgegevensEnEntiteiten(final B nieuweSituatie);

    /**
     * Verkrijgt de peildatum.
     *
     * @param actie de actie
     * @param entiteit de te checken entiteit
     * @return peil datum
     */
    protected abstract DatumBasisAttribuut getPeilDatum(final Actie actie, final E entiteit);

    /**
     * Voert extra controles uit (optioneel). Deze methode kan gebruikt worden als er naast de gebruikelijke
     * geldigheidscontrole ook nog andere controles uitgevoerd dienen te worden.
     *
     * @param nieuweSituatie nieuwe situatie (uit het bericht)
     * @return true als aan controle voldaan is
     */
    protected boolean voerExtraControlesUit(final B nieuweSituatie) {
        return true;
    }
}
