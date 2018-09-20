/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.stappen;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.business.regels.BedrijfsregelManager;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Abstract class voor de verschillende bericht verwerkings stappen. Elk bericht wordt in verschillende stappen
 * verwerkt, waarbij elke stap wordt geimplementeerd middels een implementatie van deze klasse. De verwerking
 * van een bericht gebeurd door achtereenvolgens op de voor het bericht benodigde BerichtVerwerkingsStap instanties
 * de centrale {#voerVerwerkingsStapUitVoorBericht(nl.bzk.brp.binding.bijhouding.BijhoudingsBericht , List<Melding>)}
 * methode aan te roepen.
 *
 * @param <T> Type Bericht waar deze stap voor wordt uitgevoerd.
 * @param <C> Type berichtcontext die wordt gebruikt, voor elke webservice kan dit een andere zijn.
 * @param <Y> Type BerichtResultaat waar deze stap eventuele resultaten en meldingen in opslaat.
 */
public abstract class AbstractBerichtVerwerkingStap<T extends BerichtBericht, C extends BerichtContext,
        Y extends BerichtVerwerkingsResultaat>
        extends AbstractStap<T, C, Y>
{

    private static final String ONBEKENDE_PROPERTY_REPLACER = "<onbekend>";
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBerichtVerwerkingStap.class);

    @Inject
    @Named("bedrijfsregelManager")
    private BedrijfsregelManager bedrijfsregelManager;

    protected BedrijfsregelManager getBedrijfsregelManager() {
        return bedrijfsregelManager;
    }

    /**
     * Voegt meldingen toe aan het resultaat op basis van de regels die overtredingen hebben geconstateerd.
     *
     * @param falendeRegelsMetEntiteiten mapping van regels naar objecten waarin overtreding van de regels is
     *                                   geconstateerd.
     * @param resultaat                  resultaat van de verwerking voor het toevoegen van meldingen
     */
    protected void voegMeldingenToeAanResultaat(
            final Map<? extends BerichtIdentificeerbaar,
                    Map<RegelInterface, List<BerichtIdentificeerbaar>>> falendeRegelsMetEntiteiten,
            final BerichtVerwerkingsResultaat resultaat)
    {
        for (final Map.Entry<? extends BerichtIdentificeerbaar,
                Map<RegelInterface, List<BerichtIdentificeerbaar>>> falendeRegelMetEntiteiten
                : falendeRegelsMetEntiteiten.entrySet())
        {
            for (final Map.Entry<RegelInterface, List<BerichtIdentificeerbaar>> entry
                    : falendeRegelMetEntiteiten.getValue().entrySet())
            {
                final List<BerichtIdentificeerbaar> falendeObjecten = entry.getValue();
                final Regel falendeRegel = entry.getKey().getRegel();

                if (falendeObjecten == null || falendeObjecten.isEmpty()) {
                    voegMeldingToeAanResultaat(falendeRegel, null, resultaat, null);
                } else {
                    for (BerichtIdentificeerbaar objectDatDeRegelOvertreedt : falendeObjecten) {
                        voegMeldingToeAanResultaat(falendeRegel, objectDatDeRegelOvertreedt, resultaat, null);
                    }
                }
            }
        }
    }

    /**
     * Voegt een nieuwe melding toe aan het resultaat op basis van de regel die gefaald is en een eventueel object dat
     * de regel overtreedt.
     *
     * @param regel                      de regel die is gefaald.
     * @param objectDatDeRegelOvertreedt het object dat de regel overtreedt (optioneel).
     * @param resultaat                  resultaat van de verwerking voor het toevoegen van meldingen
     * @param databaseObject             waarde om databaseobject te overrulen, dit wordt gedaan voor BRAL's die van
     *                                   toepassing zijn op meerdere velden in het model. (Dan hangt de waarde van
     *                                   dbobject aan de annotatie)
     * @return de nieuwe melding die is toegevoegd aan het resultaat.
     */
    protected Melding voegMeldingToeAanResultaat(final Regel regel,
                                                 final BerichtIdentificeerbaar objectDatDeRegelOvertreedt,
                                                 final BerichtVerwerkingsResultaat resultaat,
                                                 final DatabaseObject databaseObject)
    {
        final RegelParameters regelParametersVoorRegel = bedrijfsregelManager.getRegelParametersVoorRegel(regel);
        final Melding melding =
                new Melding(regelParametersVoorRegel.getSoortMelding(), regel,
                        bouwMeldingTekst(regelParametersVoorRegel, objectDatDeRegelOvertreedt));

        if (objectDatDeRegelOvertreedt != null) {
            final DatabaseObject dbObject;
            if (databaseObject != null) {
                dbObject = databaseObject;
            } else {
                dbObject = regelParametersVoorRegel.getDatabaseObject();
            }

            String commId = null;
            if (objectDatDeRegelOvertreedt instanceof BerichtEntiteit) {
                if (dbObject != null) {
                    // Type is BerichtEntiteit
                    commId = ((BerichtEntiteit) objectDatDeRegelOvertreedt).getCommunicatieIdVoorElement(dbObject.getId());
                }
                if (commId == null) {
                    LOGGER.debug("Geen communicatieId gevonden voor {}; terugvallend op communicatieId van"
                            + " regel overtredende entiteit", dbObject);
                    commId = objectDatDeRegelOvertreedt.getCommunicatieID();
                }
            } else {
                // Type is BerichtIdentificeerbaar
                commId = objectDatDeRegelOvertreedt.getCommunicatieID();
            }
            melding.setReferentieID(commId);
        }
        // Voorlopig een dummy waarde voor element naam
        // TODO aanpassen zodra er een definitief besluit hierover is
        melding.setAttribuutNaam("Dummy waarde, TODO: invullen na functionele beslissing.");

        resultaat.voegMeldingToe(melding);
        return melding;
    }

    /**
     * Bouwt de te tonen meldingtekst behorende bij een falende regel en bijbehorend falend object.
     *
     * @param regelParametersVoorRegel   de regelparameters behorende bij de regel die faalt.
     * @param objectDatDeRegelOvertreedt het object dat gemarkeerd is als foutief.
     * @return de meldingstekst.
     */
    private String bouwMeldingTekst(final RegelParameters regelParametersVoorRegel,
                                    final BerichtIdentificeerbaar objectDatDeRegelOvertreedt)
    {
        final String meldingTekst;

        if (regelParametersVoorRegel.getMeldingTekst() != null) {
            if (objectDatDeRegelOvertreedt != null) {
                final Object[] meldingParameters;

                final String[] meldingParameterPaden =
                        regelParametersVoorRegel.getRegelCode().getPropertyMeldingPaden();
                if (meldingParameterPaden != null && meldingParameterPaden.length > 0) {
                    meldingParameters = new Object[meldingParameterPaden.length];
                    for (int i = 0; i < meldingParameterPaden.length; i++) {
                        try {
                            meldingParameters[i] =
                                    BeanUtils.getProperty(objectDatDeRegelOvertreedt, meldingParameterPaden[i]);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException
                                | NestedNullException e)
                        {
                            verwerkExceptieBijBepalingPropertyVanOvertredendObject(meldingParameters, i,
                                    meldingParameterPaden[i], objectDatDeRegelOvertreedt, e);
                        }
                    }
                } else {
                    meldingParameters = new Object[]{objectDatDeRegelOvertreedt};
                }

                meldingTekst = String.format(regelParametersVoorRegel.getMeldingTekst().getWaarde(), meldingParameters);
            } else {
                meldingTekst = regelParametersVoorRegel.getMeldingTekst().getWaarde();
            }
        } else {
            meldingTekst = null;
        }

        return meldingTekst;
    }

    /**
     * Methode die een fout bij het ophalen van een property uit een overtredend object afhandelt. Deze methode logt
     * een waarschuwing (want blijkbaar is er een foutief parameter pad of object meegegeven) en zet de te gebruiken
     * tekst parameter voor deze parameter voor de melding naar een standaard string.
     *
     * @param meldingParameters          de melding parameters voor een melding.
     * @param i                          de index van de melding parameter die een fout gaf.
     * @param meldingParameterPad        het pad dat wijst naar de property in het overtredend object dat als melding
     *                                   parameter gebruikt dient te worden.
     * @param objectDatDeRegelOvertreedt het object dat de regel heeft overtreden.
     * @param exceptie                   de exceptie die is gegooid bij het ophalen van de property.
     */
    private void
    verwerkExceptieBijBepalingPropertyVanOvertredendObject(final Object[] meldingParameters, final int i,
                                                           final String meldingParameterPad,
                                                           final BerichtIdentificeerbaar objectDatDeRegelOvertreedt,
                                                           final Exception exceptie)
    {
        LOGGER.warn("Fout bij het ophalen van property '{}' in object '{}': {}", new Object[]{meldingParameterPad,
            objectDatDeRegelOvertreedt, exceptie.getMessage(), });
        meldingParameters[i] = ONBEKENDE_PROPERTY_REPLACER;
    }

}
