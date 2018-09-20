/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.lang.reflect.InvocationTargetException;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.regels.actie.BijhoudingRegelService;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.springframework.stereotype.Component;

/**
 * Factory voor het aanmaken van meldingen.
 */
@Component
public class MeldingFactory {

    private static final Logger LOGGER                      = LoggerFactory.getLogger();
    private static final String ONBEKENDE_PROPERTY_REPLACER = "<onbekend>";
    private static final String STANDAARD_MELDING_ATTRIBUUT_NAAM = "Dummy waarde, TODO: invullen na functionele beslissing.";

    @Inject
    private BijhoudingRegelService bijhoudingRegelService;

    /**
     * Maakt een resultaatmelding.
     * @param regel de regel
     * @param overtreder de overtreder
     * @param databaseObject het databaseobject
     * @return de melding
     */
    public ResultaatMelding maakResultaatMelding(final Regel regel, final BerichtIdentificeerbaar overtreder, final DatabaseObject databaseObject) {
        return maakResultaatMelding(regel, overtreder, databaseObject, STANDAARD_MELDING_ATTRIBUUT_NAAM);
    }

    /**
     * Maakt een resultaatmelding.
     * @param regel de regel
     * @param overtreder de overtreder
     * @param databaseObject het databaseobject
     * @param attribuutNaam de attribuutNaam
     * @return de melding
     */
    public ResultaatMelding maakResultaatMelding(final Regel regel, final BerichtIdentificeerbaar overtreder, final DatabaseObject databaseObject,
        final String attribuutNaam)
    {
        final RegelParameters regelParametersVoorRegel = bijhoudingRegelService.getRegelParametersVoorRegel(regel);
        final String referentieId = bepaalReferentieId(overtreder, databaseObject, regelParametersVoorRegel);

        return ResultaatMelding.builder()
                .withSoort(regelParametersVoorRegel.getSoortMelding())
                .withRegel(regel)
                .withMeldingTekst(bouwMeldingTekst(regelParametersVoorRegel, overtreder))
                .withReferentieID(referentieId)
                .withAttribuutNaam(attribuutNaam)
                .build();
    }

    private String bepaalReferentieId(final BerichtIdentificeerbaar objectDatDeRegelOvertreedt, final DatabaseObject databaseObject,
        final RegelParameters regelParametersVoorRegel)
    {
        String referentieId = null;
        if (objectDatDeRegelOvertreedt != null) {
            final DatabaseObject dbObject = bepaalDbObject(databaseObject, regelParametersVoorRegel);
            referentieId = bepaalReferentieId(objectDatDeRegelOvertreedt, dbObject);
        }
        return referentieId;
    }

    private DatabaseObject bepaalDbObject(final DatabaseObject databaseObject, final RegelParameters regelParametersVoorRegel) {
        final DatabaseObject dbObject;
        if (databaseObject != null) {
            dbObject = databaseObject;
        } else {
            dbObject = regelParametersVoorRegel.getDatabaseObject();
        }
        return dbObject;
    }

    private String bepaalReferentieId(final BerichtIdentificeerbaar objectDatDeRegelOvertreedt, final DatabaseObject dbObject) {
        String referentieId = null;
        if (objectDatDeRegelOvertreedt instanceof BerichtEntiteit) {
            if (dbObject != null) {
                // Type is BerichtEntiteit
                referentieId = ((BerichtEntiteit) objectDatDeRegelOvertreedt).getCommunicatieIdVoorElement(dbObject.getId());
            }
            if (referentieId == null) {
                LOGGER.debug("Geen communicatieId gevonden voor {}; terugvallend op communicatieId van"
                    + " regel overtredende entiteit", dbObject);
                referentieId = objectDatDeRegelOvertreedt.getCommunicatieID();
            }
        } else {
            // Type is BerichtIdentificeerbaar
            referentieId = objectDatDeRegelOvertreedt.getCommunicatieID();
        }
        return referentieId;
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

                final String[] meldingParameterPaden = regelParametersVoorRegel.getRegelCode().getPropertyMeldingPaden();
                if (meldingParameterPaden.length > 0) {
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
    private void verwerkExceptieBijBepalingPropertyVanOvertredendObject(final Object[] meldingParameters, final int i, final String meldingParameterPad,
        final BerichtIdentificeerbaar objectDatDeRegelOvertreedt, final Exception exceptie)
    {
        LOGGER.warn("Fout bij het ophalen van property '{}' in object '{}': {}", meldingParameterPad, objectDatDeRegelOvertreedt, exceptie.getMessage());
        meldingParameters[i] = ONBEKENDE_PROPERTY_REPLACER;
    }

}
