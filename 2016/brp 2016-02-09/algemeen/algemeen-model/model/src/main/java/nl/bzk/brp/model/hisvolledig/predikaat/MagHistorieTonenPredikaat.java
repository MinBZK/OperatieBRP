/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import java.util.Set;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstbundelGroep;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.basis.GerelateerdIdentificeerbaar;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieleHistorieModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat dat voor {@link ElementIdentificeerbaar} objecten valideert of deze voorkomen in de opgegeven lijst, en of voor typen ervan toestemming is
 * gegeven om ze te mogen zien. Deze toegang is geregeld op basis van de attributen van een {@link DienstbundelGroep}, namelijk {@code
 * indicatieMaterieleHistorie} en {@code indicatieFormeleHistorie}.
 *
 * @brp.bedrijfsregel VR00052, VR00059, VR00078
 */
@Regels({ Regel.VR00052, Regel.VR00059, Regel.VR00078 })
public final class MagHistorieTonenPredikaat implements Predicate {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Set<DienstbundelGroep> dienstbundelGroepen;
    private final boolean                isMutatieLevering;

    /**
     * Constructor die een lijst van AbonnementGroepen aanneemt, om te gebruiken bij het evalueren van het predikaat.
     *
     * @param dienstbundelGroepen de lijst van dienstbundelGroepenen
     * @param isMutatieLevering   Boolean of het in het kader van een mutatielevering is.
     */
    public MagHistorieTonenPredikaat(final Set<DienstbundelGroep> dienstbundelGroepen, final boolean isMutatieLevering) {
        this.dienstbundelGroepen = dienstbundelGroepen;
        this.isMutatieLevering = isMutatieLevering;
    }

    @Override
    public boolean evaluate(final Object object) {
        if (object instanceof ElementIdentificeerbaar) {
            return magVoorkomenTonenElementIdentificeerbaar((ElementIdentificeerbaar) object);
        }
        return false;
    }

    /**
     * Controleert voor een ElementIdentificeerbaar of het voorkomen van een groep getoond mag worden. Wanneer er geen voorkomen in de abonnementGroep
     * aanwezig is, dan zijn er twee uitkomsten: voor mutatielevering wordt de groep wel getoond, in andere gevallen niet.
     *
     * @param identificeerbaarVoorkomen het ElementIdentificeerbaar object
     * @return true als de groep mag voorkomen, anders false
     */
    private boolean magVoorkomenTonenElementIdentificeerbaar(final ElementIdentificeerbaar identificeerbaarVoorkomen) {
        ElementEnum elementEnum = identificeerbaarVoorkomen.getElementIdentificatie();
        // indien het een object van een gerelateerde persoon dient een andere enum gebruikt worden
        if (identificeerbaarVoorkomen instanceof GerelateerdIdentificeerbaar) {
            if (identificeerbaarVoorkomen instanceof HisRelatieModel) {
                // Bij HisRelatieModel is geen subtypering aanwezig op de elemenidentificatie, maar altijd RELATIE_STANDAARD of RELATIE_IDENTIFICATIE
                zetJuisteGerelateerdObjecttypeVoorRelatie((HisRelatieModel) identificeerbaarVoorkomen);
            }

            final GerelateerdIdentificeerbaar gerelateerdIdentificeerbaar = (GerelateerdIdentificeerbaar) identificeerbaarVoorkomen;
            if (gerelateerdIdentificeerbaar.getGerelateerdeObjectType() != null) {
                elementEnum = gerelateerdIdentificeerbaar.getGerelateerdeObjectType();
            }
        }

        final DienstbundelGroep dienstbundelGroep = bepaalDienstbundelGroep(elementEnum);

        final boolean magVoorkomenTonen;
        if (dienstbundelGroep != null) {
            final boolean heeftIndicatieMaterieleHistorie =
                dienstbundelGroep.getIndicatieMaterieleHistorie().heeftWaarde()
                    && dienstbundelGroep.getIndicatieMaterieleHistorie().getWaarde();

            final boolean heeftIndicatieFormeleHistorie =
                dienstbundelGroep.getIndicatieFormeleHistorie().heeftWaarde()
                    && dienstbundelGroep.getIndicatieFormeleHistorie().getWaarde();
            final boolean moetFormeleHistorieTonen = isMutatieLevering || heeftIndicatieFormeleHistorie;

            magVoorkomenTonen = magVoorkomenTonen(identificeerbaarVoorkomen, heeftIndicatieMaterieleHistorie, moetFormeleHistorieTonen);
        } else {
            // Er is geen voorkomen in de abonnementGroep autorisatie
            magVoorkomenTonen = magVoorkomenTonen(identificeerbaarVoorkomen, false, isMutatieLevering);
        }

        return magVoorkomenTonen;
    }

    /**
     * Zet het juiste gerelateerd objecttype voor relatie.
     *
     * @param hisRelatieModel the his relatie model
     */
    private void zetJuisteGerelateerdObjecttypeVoorRelatie(final HisRelatieModel hisRelatieModel) {
        final ElementEnum elementEnum;
        final SoortRelatie soortRelatie = hisRelatieModel.getRelatie().getSoort().getWaarde();
        if (hisRelatieModel.getElementIdentificatie().name().contains("_STANDAARD")) {
            elementEnum = ElementIdentificatieUtil.geefElementEnumVoorRelatieStandaardGroep(soortRelatie);
        } else if (hisRelatieModel.getElementIdentificatie().name().contains("_IDENTITEIT")) {
            elementEnum = ElementIdentificatieUtil.geefElementEnumVoorRelatieIdentiteitGroep(soortRelatie);
        } else {
            elementEnum = hisRelatieModel.getElementIdentificatie();
            LOGGER.warn("Er is een groep van hisrelatiemodel die nog niet ondersteund wordt in het predikaat: "
                + hisRelatieModel.getElementIdentificatie());
        }

        hisRelatieModel.setGerelateerdeObjectType(elementEnum);
    }

    /**
     * Bepaalt de abonnement groep op basis van het object element.
     *
     * @param objectElement object element
     * @return de abonnement groep die gelijk is aan de groepwaarde van het objectelement.
     */
    private DienstbundelGroep bepaalDienstbundelGroep(final ElementEnum objectElement) {
        if (dienstbundelGroepen != null) {
            for (final DienstbundelGroep dienstbundelGroep : dienstbundelGroepen) {
                final Element element = dienstbundelGroep.getGroep();

                if (objectElement.name().equals(element.geefElementEnumName())) {
                    return dienstbundelGroep;
                }
            }
        }
        return null;
    }

    /**
     * Bepaalt of het voorkomen van een groep mag worden getoond.
     *
     * @param identificeerbaarVoorkomen       een formeel of materieel historisch voorkomen van een groep
     * @param heeftIndicatieMaterieleHistorie de indicatie mag materiele historie zien
     * @param heeftIndicatieFormeleHistorie   de indicatie mag formele historie zien
     * @return {@code true} indien het voorkomen getoond mag worden
     */
    private boolean magVoorkomenTonen(final ElementIdentificeerbaar identificeerbaarVoorkomen,
        final boolean heeftIndicatieMaterieleHistorie,
        final boolean heeftIndicatieFormeleHistorie)
    {
        boolean resultaat = false;
        if (identificeerbaarVoorkomen instanceof FormeelHistorisch) {
            resultaat = heeftIndicatieFormeleHistorie || magFormeelZien((FormeelHistorisch) identificeerbaarVoorkomen);
        } else if (identificeerbaarVoorkomen instanceof MaterieelHistorisch) {
            resultaat = magMaterieelZien((MaterieelHistorisch) identificeerbaarVoorkomen, heeftIndicatieMaterieleHistorie, heeftIndicatieFormeleHistorie);
        }
        return resultaat;
    }

    /**
     * Bepaalt of het record met formele historie getoond mag worden.
     *
     * @param formeelHistorisch Het record.
     * @return {@code true} als het record getoond mag worden, anders false.
     */
    private boolean magFormeelZien(final FormeelHistorisch formeelHistorisch) {
        final FormeleHistorieModel historie = formeelHistorisch.getFormeleHistorie();
        final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = (FormeelVerantwoordbaar) formeelHistorisch;
        return isNietFormeelVervallen(historie, formeelVerantwoordbaar);
    }

    /**
     * Controleert of een entitieit niet formeel is vervallen.
     *
     * @param historie               De historie van de entiteit.
     * @param formeelVerantwoordbaar De entiteit als formeel verantwoordbaar.
     * @return {@code true} als de entiteit niet is vervallen, anders false.
     */
    private boolean isNietFormeelVervallen(final FormeleHistorieModel historie,
        final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar)
    {
        return (historie.getDatumTijdVerval() == null
            || historie.getDatumTijdVerval().heeftGeenWaarde())
            && formeelVerantwoordbaar.getVerantwoordingVerval() == null;
    }

    /**
     * Bepaalt of het record met materiele historie getoond mag worden.
     *
     * @param materieelHistorisch             Het record.
     * @param heeftIndicatieMaterieleHistorie De indicatie heeft materiele historie.
     * @param heeftIndicatieFormeleHistorie   De indicatie heeft formele historie.
     * @return {@code true} als het record getoond mag worden, anders false.
     */
    private boolean magMaterieelZien(final MaterieelHistorisch materieelHistorisch, final boolean heeftIndicatieMaterieleHistorie,
        final boolean heeftIndicatieFormeleHistorie)
    {
        if (heeftIndicatieMaterieleHistorie && heeftIndicatieFormeleHistorie) {
            // beide vlaggen op aan
            return true;
        }

        final MaterieleHistorieModel historie = materieelHistorisch.getMaterieleHistorie();
        final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = (FormeelVerantwoordbaar) materieelHistorisch;
        final boolean resultaat;
        final boolean entiteitIsNietVervallen = isNietFormeelVervallen(historie, formeelVerantwoordbaar);
        final boolean entiteitIsNietBeeindigd = historie.getDatumEindeGeldigheid() == null
            || historie.getDatumEindeGeldigheid().heeftGeenWaarde();

        if (!(heeftIndicatieMaterieleHistorie || heeftIndicatieFormeleHistorie)) {
            // beide vlaggen op uit, alleen entiteit "huidig"
            resultaat = entiteitIsNietVervallen && entiteitIsNietBeeindigd;
        } else if (heeftIndicatieMaterieleHistorie) {
            // alleen materiele vlag staat aan, dus alleen entiteiten zonder datumtijd verval
            resultaat = entiteitIsNietVervallen;
        } else {
            // alleen formele vlag staat aan, dus alleen entiteiten zonder datum einde geldigheid
            resultaat =
                entiteitIsNietBeeindigd;
        }

        return resultaat;
    }

    public Set<DienstbundelGroep> getDienstbundelGroepen() {
        return dienstbundelGroepen;
    }
}
