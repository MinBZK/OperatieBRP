/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpDocumentatieConverteerder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Basis mutatie verwerker.
 */
public abstract class AbstractMutatieVerwerker {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    private BrpDocumentatieConverteerder brpDocumentatieConverteerder;
    @Autowired
    private BrpAttribuutConverteerder brpAttribuutConverteerder;

    /**
     * Converteer historie.
     *
     * @param historie
     *            brp historie
     * @param actie
     *            actie
     * @param indicatieEindeGeldigheid
     *            indicatie of einde (true) of ingang (false) geldigheid gebruikt moet worden
     * @return lo3 historie
     */
    protected final Lo3Historie converteerHistorie(final BrpHistorie historie, final BrpActie actie, final boolean indicatieEindeGeldigheid) {
        // 84.10 Onjuist
        final Lo3IndicatieOnjuist indicatieOnjuist;
        if (historie.getNadereAanduidingVerval() != null && historie.getNadereAanduidingVerval().getWaarde() != null) {
            indicatieOnjuist = Lo3IndicatieOnjuist.O;
        } else {
            indicatieOnjuist = null;
        }

        // 85.10 Ingangsdatum geldigheid
        final Lo3Datum ingangsdatumGeldigheid;
        if (indicatieEindeGeldigheid) {
            ingangsdatumGeldigheid = brpAttribuutConverteerder.converteerDatum(historie.getDatumEindeGeldigheid());

        } else {
            ingangsdatumGeldigheid = brpAttribuutConverteerder.converteerDatum(historie.getDatumAanvangGeldigheid());
        }

        // 86.10 Datum van opneming
        final Lo3Datum datumVanOpneming = brpAttribuutConverteerder.converteerDatum(actie.getDatumTijdRegistratie());

        return new Lo3Historie(indicatieOnjuist, ingangsdatumGeldigheid, datumVanOpneming);
    }

    /**
     * Converteer documentatie.
     *
     * @param actie
     *            actie
     * @return lo3 documentatie
     */
    protected final Lo3Documentatie converteerDocument(final BrpActie actie) {
        return brpDocumentatieConverteerder.maakDocumentatie(actie);
    }

    /**
     * Merge RNI gegevens in documentatie.
     *
     * @param nieuweDocumentatie
     *            nieuwe documentatie
     * @param oudeDocumentatie
     *            oude documentatie
     * @return nieuwe documentatie met eventueel RNI gemerged uit oude documentatie
     */
    protected final Lo3Documentatie mergeRni(final Lo3Documentatie nieuweDocumentatie, final Lo3Documentatie oudeDocumentatie) {
        LOGGER.debug("mergeRni ({}): nieuw -> {}, oud -> {}", this.getClass().getSimpleName(), nieuweDocumentatie, oudeDocumentatie);
        final Lo3Documentatie resultaat;
        if (oudeDocumentatie != null) {
            if (nieuweDocumentatie == null) {
                resultaat =
                        Lo3Documentatie.build(
                            null,
                            null,
                            null,
                            null,
                            null,
                            oudeDocumentatie.getRniDeelnemerCode(),
                            oudeDocumentatie.getOmschrijvingVerdrag());
            } else if (nieuweDocumentatie.getRniDeelnemerCode() == null
                       && nieuweDocumentatie.getOmschrijvingVerdrag() == null
                       && (oudeDocumentatie.getRniDeelnemerCode() != null || oudeDocumentatie.getOmschrijvingVerdrag() != null))
            {
                resultaat =
                        new Lo3Documentatie(
                            nieuweDocumentatie.getId(),
                            nieuweDocumentatie.getGemeenteAkte(),
                            nieuweDocumentatie.getNummerAkte(),
                            nieuweDocumentatie.getGemeenteDocument(),
                            nieuweDocumentatie.getDatumDocument(),
                            nieuweDocumentatie.getBeschrijvingDocument(),
                            oudeDocumentatie.getRniDeelnemerCode(),
                            oudeDocumentatie.getOmschrijvingVerdrag());
            } else {
                resultaat = nieuweDocumentatie;
            }
        } else {
            resultaat = nieuweDocumentatie;
        }
        LOGGER.debug("mergeRni ({}): resultaat -> {}", this.getClass().getSimpleName(), resultaat);
        return resultaat;
    }

    /**
     * Bepaal voorkomen sleutels.
     *
     * @param historieSet
     *            historie set
     * @param <H>
     *            historie type
     * @return voorkomen sleutels
     */
    protected final <H extends HistorieEntiteit & ModelIdentificeerbaar<? extends Number>> List<Long> bepaalVoorkomenSleutels(
        final HistorieSet<H> historieSet)
    {
        final List<Long> voorkomenSleutels = new ArrayList<>();

        for (final H historieVoorkomen : historieSet) {
            final Number voorkomenSleutel = historieVoorkomen.getID();
            if (voorkomenSleutel != null) {
                voorkomenSleutels.add(voorkomenSleutel.longValue());
            }
        }

        return voorkomenSleutels;
    }

    /**
     * Merge onderzoeken.
     *
     * @param bestaand
     *            bestaand onderzoek
     * @param nieuw
     *            nieuw onderzoek
     * @return gemerged onderzoek
     */
    protected Lo3Onderzoek mergeOnderzoek(final Lo3Onderzoek bestaand, final Lo3Onderzoek nieuw) {
        final Lo3Onderzoek resultaat;
        if (nieuw == null) {
            resultaat = bestaand;
        } else {
            if (bestaand == null) {
                resultaat = nieuw;
            } else {
                resultaat = mergeOnderzoeken(bestaand, nieuw);
            }
        }

        return resultaat;
    }

    /**
     * Merge onderzoeken; beide onderzoeken bestaan.
     *
     * @param onderzoek1
     *            onderzoek
     * @param onderzoek2
     *            onderzoek
     * @return onderzoek
     */
    private Lo3Onderzoek mergeOnderzoeken(final Lo3Onderzoek onderzoek1, final Lo3Onderzoek onderzoek2) {
        final Lo3Integer gegevensInOnderzoek = mergeGegevensInOnderzoek(onderzoek1, onderzoek2);
        final Lo3Datum datumIngang = geefKleinsteDatum(onderzoek1.getDatumIngangOnderzoek(), onderzoek2.getDatumIngangOnderzoek());
        final Lo3Datum datumEinde = geefLegeOfGrootsteDatum(onderzoek1.getDatumEindeOnderzoek(), onderzoek2.getDatumEindeOnderzoek());

        return new Lo3Onderzoek(gegevensInOnderzoek, datumIngang, datumEinde);
    }

    private Lo3Integer mergeGegevensInOnderzoek(final Lo3Onderzoek onderzoek1, final Lo3Onderzoek onderzoek2) {
        final int categorie = onderzoek1.getOnderzoekCategorienummer();
        final int groep1 = onderzoek1.getOnderzoekGroepnummer();
        final int groep2 = onderzoek2.getOnderzoekGroepnummer();
        final int groep = mergeGegevenInOnderzoek(groep1, groep2);
        final int element;
        if (groep == 0) {
            element = 0;
        } else {
            final int element1 = onderzoek1.getOnderzoekElementnummer();
            final int element2 = onderzoek2.getOnderzoekElementnummer();

            element = mergeGegevenInOnderzoek(element1, element2);
        }

        return new Lo3Integer(categorie * 10000 + groep * 100 + element);
    }

    private int mergeGegevenInOnderzoek(final int gegeven1, final int gegeven2) {
        if (gegeven1 == gegeven2) {
            return gegeven1;
        } else {
            return 0;
        }
    }

    private Lo3Datum geefKleinsteDatum(final Lo3Datum datumIngangOnderzoek1, final Lo3Datum datumIngangOnderzoek2) {
        final Lo3Datum resultaat;
        if (datumIngangOnderzoek1.getIntegerWaarde() < datumIngangOnderzoek2.getIntegerWaarde()) {
            resultaat = datumIngangOnderzoek1;
        } else {
            resultaat = datumIngangOnderzoek2;
        }
        return resultaat;
    }

    private Lo3Datum geefLegeOfGrootsteDatum(final Lo3Datum datumEindeOnderzoek1, final Lo3Datum datumEindeOnderzoek2) {
        final Lo3Datum resultaat;
        if (datumEindeOnderzoek1 == null || datumEindeOnderzoek2 == null) {
            resultaat = null;
        } else {
            if (datumEindeOnderzoek1.getIntegerWaarde() > datumEindeOnderzoek2.getIntegerWaarde()) {
                resultaat = datumEindeOnderzoek1;
            } else {
                resultaat = datumEindeOnderzoek2;
            }
        }
        return resultaat;
    }
}
