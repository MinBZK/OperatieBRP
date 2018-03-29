/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.brp.generated.DatumMetOnzekerheid;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GemeenteCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepRelatieRelatie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Locatieomschrijving;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.RedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import org.springframework.stereotype.Component;

/**
 * Maakt een Relatie aan op basis van een BRP persoon en een bijbehorende toevalligegebeurtenis verzoek.
 */
@Component
public class RelatieMaker {

    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private final AttribuutMaker attribuutMaker;

    /**
     * Constructor.
     * @param attribuutMaker utility om attributen aan te maken
     */
    @Inject
    public RelatieMaker(final AttribuutMaker attribuutMaker) {
        this.attribuutMaker = attribuutMaker;
    }

    /**
     * Maak de relatiegroep van een huwelijk of geregistreerd partnerschap aan voor omzetting of ontbinding.
     * @param communicatieId id voor bericht.
     * @param isSluiting De relatiegroep heeft betrekking op een sluiting.
     * @param brpDatum De datum aanvang/einde vanuit het verzoek.
     * @param plaatsVerzoek De plaats aanvang/einde vanuit het verzoek.
     * @param brpBuitenlandsePlaats Plaats indien buitenland
     * @param omschrijvingPlaats Omschrijving plaats
     * @param redenEinde De reden einde vanuit het verzoek.
     * @return De relatiegroep.
     */
    public final GroepRelatieRelatie maakHuwelijkOfGeregistreerdPartnerschapRelatie(
            final String communicatieId,
            final boolean isSluiting,
            final BrpDatum brpDatum,
            final BrpGemeenteCode plaatsVerzoek,
            final BrpString brpBuitenlandsePlaats,
            final BrpString omschrijvingPlaats,
            final BrpRedenEindeRelatieCode redenEinde) {
        final GroepRelatieRelatie relatie = new GroepRelatieRelatie();
        relatie.setCommunicatieID(communicatieId);

        final DatumMetOnzekerheid datum = attribuutMaker.maakDatumMetOnzekerheid(brpDatum);
        if (isSluiting) {
            relatie.setDatumAanvang(OBJECT_FACTORY.createGroepRelatieRelatieDatumAanvang(datum));
        } else {
            relatie.setDatumEinde(OBJECT_FACTORY.createGroepRelatieRelatieDatumEinde(datum));
        }

        if (plaatsVerzoek != null) {
            // Binnenlands plaats.
            final GemeenteCode gemeenteCode = new GemeenteCode();
            gemeenteCode.setValue(plaatsVerzoek.getWaarde());
            if (isSluiting) {
                relatie.setGemeenteAanvangCode(OBJECT_FACTORY.createGroepRelatieRelatieGemeenteAanvangCode(gemeenteCode));
            } else {
                relatie.setGemeenteEindeCode(OBJECT_FACTORY.createGroepRelatieRelatieGemeenteEindeCode(gemeenteCode));
            }
        }

        if (brpBuitenlandsePlaats != null) {
            // Kan volgens de specificatie niet voorkomen
            throw new IllegalArgumentException("Buitenlandse plaatsen niet toegestaan in BRP bericht");
        }

        if (omschrijvingPlaats != null) {
            final Locatieomschrijving locatieomschrijving = new Locatieomschrijving();
            locatieomschrijving.setValue(omschrijvingPlaats.getWaarde());
            if (isSluiting) {
                relatie.setOmschrijvingLocatieAanvang(OBJECT_FACTORY.createGroepRelatieRelatieOmschrijvingLocatieAanvang(locatieomschrijving));
            } else {
                relatie.setOmschrijvingLocatieEinde(OBJECT_FACTORY.createGroepRelatieRelatieOmschrijvingLocatieAanvang(locatieomschrijving));
            }
        }

        if (redenEinde != null) {
            final RedenEindeRelatieCode redenEindeCode = new RedenEindeRelatieCode();
            redenEindeCode.setValue(String.valueOf(redenEinde.getWaarde()));
            relatie.setRedenEindeCode(OBJECT_FACTORY.createGroepRelatieRelatieRedenEindeCode(redenEindeCode));
        }

        return relatie;
    }
}
