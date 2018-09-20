/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ist;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CoderingOnjuistAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3RubriekInclCategorieEnGroepAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.logisch.ist.StapelVoorkomenStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;


/**
 * Groep die van toepassing is voor ALLE stapels, dus voor zowel categorie 02, 03, 05, 09 als 11.
 */
@Embeddable
public class StapelVoorkomenStandaardGroepModel extends AbstractStapelVoorkomenStandaardGroepModel implements
    StapelVoorkomenStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected StapelVoorkomenStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param soortDocument                  soortDocument van Standaard.
     * @param partij                         partij van Standaard.
     * @param rubriek8220DatumDocument       rubriek8220DatumDocument van Standaard.
     * @param documentOmschrijving           documentOmschrijving van Standaard.
     * @param rubriek8310AanduidingGegevensInOnderzoek
     *                                       rubriek8310AanduidingGegevensInOnderzoek van Standaard.
     * @param rubriek8320DatumIngangOnderzoek
     *                                       rubriek8320DatumIngangOnderzoek van Standaard.
     * @param rubriek8330DatumEindeOnderzoek rubriek8330DatumEindeOnderzoek van Standaard.
     * @param rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde
     *                                       rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde van Standaard.
     * @param rubriek8510IngangsdatumGeldigheid
     *                                       rubriek8510IngangsdatumGeldigheid van Standaard.
     * @param rubriek8610DatumVanOpneming    rubriek8610DatumVanOpneming van Standaard.
     */
    public StapelVoorkomenStandaardGroepModel(
        final AdministratieveHandelingModel administratieveHandeling,
        final SoortDocumentAttribuut soortDocument, final PartijAttribuut partij,
        final DatumEvtDeelsOnbekendAttribuut rubriek8220DatumDocument,
        final DocumentOmschrijvingAttribuut documentOmschrijving,
        final LO3RubriekInclCategorieEnGroepAttribuut rubriek8310AanduidingGegevensInOnderzoek,
        final DatumEvtDeelsOnbekendAttribuut rubriek8320DatumIngangOnderzoek,
        final DatumEvtDeelsOnbekendAttribuut rubriek8330DatumEindeOnderzoek,
        final LO3CoderingOnjuistAttribuut rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde,
        final DatumEvtDeelsOnbekendAttribuut rubriek8510IngangsdatumGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut rubriek8610DatumVanOpneming)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(administratieveHandeling, soortDocument, partij, rubriek8220DatumDocument, documentOmschrijving,
            rubriek8310AanduidingGegevensInOnderzoek, rubriek8320DatumIngangOnderzoek,
            rubriek8330DatumEindeOnderzoek, rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde,
            rubriek8510IngangsdatumGeldigheid, rubriek8610DatumVanOpneming);
    }

}
