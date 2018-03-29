/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.vragen.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeRegelFactory;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.RubriekWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Criterium;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KVOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OfWaarde;
import nl.bzk.migratiebrp.conversie.vragen.ConversieExceptie;
import nl.bzk.migratiebrp.conversie.vragen.ConversieLo3NaarBrpVragen;
import nl.bzk.migratiebrp.conversie.vragen.ZoekCriterium;
import org.springframework.stereotype.Component;

/**
 * Implementatie van de {@link ConversieLo3NaarBrpVragen} interface.
 */
@Component
public final class ConversieLo3NaarBrpVragenImpl implements ConversieLo3NaarBrpVragen {

    private static final Set<String> JA_TYPE_CRITERIA = new HashSet<>(Arrays.asList(
            "Persoon.Indicatie.BehandeldAlsNederlander",
            "Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie",
            "Persoon.Indicatie.DerdeHeeftGezag",
            "Persoon.Indicatie.OnderCuratele",
            "Persoon.Indicatie.OnverwerktDocumentAanwezig",
            "Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument",
            "Persoon.Indicatie.Staatloos.Waarde",
            "Persoon.UitsluitingKiesrecht.Indicatie",
            "Persoon.Indicatie.VastgesteldNietNederlander",
            "Persoon.Indicatie.VolledigeVerstrekkingsbeperking",
            "Persoon.Nationaliteit.IndicatieBijhoudingBeeindigd"
    ));
    private static final Set<String> JANEE_TYPE_CRITERIA = new HashSet<>(Arrays.asList(
            "GerelateerdeOuder.OuderlijkGezag.IndicatieOuderHeeftGezag",
            "GerelateerdeOuder.Ouderschap.IndicatieOuderUitWieKindIsGeboren",
            "Persoon.Naamgebruik.IndicatieAfgeleid",
            "Persoon.DeelnameEUVerkiezingen.IndicatieDeelname",
            "Persoon.SamengesteldeNaam.IndicatieAfgeleid",
            "Persoon.SamengesteldeNaam.IndicatieNamenreeks",
            "Persoon.Persoonskaart.IndicatieVolledigGeconverteerd"
    ));
    private final GbaVoorwaardeRegelFactory gbaVoorwaardeRegelFactory;

    /**
     * constructor.
     * @param gbaVoorwaardeRegelFactory Voorwaarde regel factory
     */
    @Inject
    public ConversieLo3NaarBrpVragenImpl(GbaVoorwaardeRegelFactory gbaVoorwaardeRegelFactory) {
        this.gbaVoorwaardeRegelFactory = gbaVoorwaardeRegelFactory;
    }

    @Override
    public List<ZoekCriterium> converteer(final List<Lo3CategorieWaarde> lo3zoekCriteria) {
        final List<ZoekCriterium> resultaat = new ArrayList<>();
        lo3zoekCriteria.forEach(categorie ->
                categorie.getElementen().keySet().stream().map(element -> new RubriekWaarde(categorie, element)).forEach(rubriekWaarde -> {
                    try {
                        converteerNaarZoekCriterium(resultaat, rubriekWaarde);
                    } catch (GbaVoorwaardeOnvertaalbaarExceptie ex) {
                        throw new ConversieExceptie("Een vraag of deel van vraag kon niet worden geconverteerd", ex);
                    }
                })
        );
        return resultaat;
    }

    private void converteerNaarZoekCriterium(List<ZoekCriterium> resultaat, RubriekWaarde rubriekWaarde) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie expressie = gbaVoorwaardeRegelFactory.maakGbaVoorwaardeRegel(rubriekWaarde).getBrpExpressie(rubriekWaarde);
        if (expressie instanceof OfWaarde) {
            final Iterator<Criterium> iteratorCriterium = expressie.getCriteria().iterator();
            if (iteratorCriterium.hasNext()) {
                final Criterium criterium = iteratorCriterium.next();
                final ZoekCriterium brpCriterium = new ZoekCriterium(criterium.getElement(), getWaarde(criterium));
                resultaat.add(voegOfVoorwaardeToe(brpCriterium, iteratorCriterium));
            }
        } else {
            for (final Criterium criterium : expressie.getCriteria()) {
                final ZoekCriterium brpCriterium = new ZoekCriterium(criterium.getElement(), getWaarde(criterium));
                resultaat.add(brpCriterium);
            }
        }
    }

    private ZoekCriterium voegOfVoorwaardeToe(final ZoekCriterium brpCriterium, final Iterator<Criterium> criteriumIterator) {
        if (criteriumIterator.hasNext()) {
            final Criterium criterium = criteriumIterator.next();
            final ZoekCriterium ofBrpCriterium = new ZoekCriterium(criterium.getElement(), getWaarde(criterium));
            brpCriterium.setOf(voegOfVoorwaardeToe(ofBrpCriterium, criteriumIterator));
        }
        return brpCriterium;
    }

    private String getWaarde(final Criterium criterium) {
        final String resultaat;
        if (criterium.isDatumWaarde()) {
            // Corrigeer zoekwaarde ivm mogelijk gedeeltelijk onbekende datums.
            resultaat = criterium.getWaarde().replaceAll("/", "-").replaceAll("\\?", "00");
        } else if (JA_TYPE_CRITERIA.contains(criterium.getElement())) {
            if (criterium.getOperator() instanceof KVOperator) {
                resultaat = "J";
            } else {
                resultaat = null;
            }
        } else if (JANEE_TYPE_CRITERIA.contains(criterium.getElement())) {
            if ("WAAR".equals(criterium.getWaarde())) {
                resultaat = "J";
            } else if ("ONWAAR".equals(criterium.getWaarde())) {
                resultaat = "N";
            } else {
                resultaat = null;
            }
        } else {
            resultaat = criterium.getWaarde();
        }
        return resultaat;
    }
}
