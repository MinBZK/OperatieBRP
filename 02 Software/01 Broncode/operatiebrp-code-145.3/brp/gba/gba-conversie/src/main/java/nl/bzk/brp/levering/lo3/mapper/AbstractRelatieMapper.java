/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;

/**
 * Mapt een relatie.
 */
public abstract class AbstractRelatieMapper extends AbstractMapper<BrpRelatieInhoud> {

    private final AttribuutElement datumAanvang;
    private final AttribuutElement gemeenteAanvang;
    private final AttribuutElement woonplaatsAanvang;
    private final AttribuutElement buitenlandsePlaatsAanvang;
    private final AttribuutElement buitenlandseRegioAanvang;
    private final AttribuutElement landGebiedAanvang;
    private final AttribuutElement omschrijvingLocatieAanvang;
    private final AttribuutElement redenEinde;
    private final AttribuutElement datumEinde;
    private final AttribuutElement gemeenteEinde;
    private final AttribuutElement woonplaatsEinde;
    private final AttribuutElement buitenlandsePlaatsEinde;
    private final AttribuutElement buitenlandseRegioEinde;
    private final AttribuutElement landGebiedEinde;
    private final AttribuutElement omschrijvingLocatieEinde;

    /**
     * Constructor.
     * @param identiteitGroep element voor identiteit groep
     * @param groep element voor te mappen groep
     * @param tijdstipRegistratie element voor tijdstip registratie
     * @param tijdstipVerval element voor tijdstip verval
     * @param datumAanvang element voor datum aanvang
     * @param gemeenteAanvang element voor gemeente aanvang
     * @param woonplaatsAanvang element voor woonplaats aanvang
     * @param buitenlandsePlaatsAanvang element voor buitenlandse plaats aanvang
     * @param buitenlandseRegioAanvang element voor buitenlandse regio aanvang
     * @param landGebiedAanvang element voor land/gebied aanvang
     * @param omschrijvingLocatieAanvang element voor omschrijving locatie aanvang
     * @param redenEinde element voor reden einde
     * @param datumEinde element voor datum einde
     * @param gemeenteEinde element voor gemeente einde
     * @param woonplaatsEinde element voor woonplaats einde
     * @param buitenlandsePlaatsEinde element voor buitenlandse plaats einde
     * @param buitenlandseRegioEinde element voor buitenlandse regio einde
     * @param landGebiedEinde element voor land/gebied einde
     * @param omschrijvingLocatieEinde element voor omschrijving locatie einde
     */
    //
    /*
     * squid:S00107 Methods should not have too many parameters
     *
     * Terecht, geaccepteerd voor deze klasse.
     */
    protected AbstractRelatieMapper(
            final GroepElement identiteitGroep,
            final GroepElement groep,
            final AttribuutElement tijdstipRegistratie,
            final AttribuutElement tijdstipVerval,
            final AttribuutElement datumAanvang,
            final AttribuutElement gemeenteAanvang,
            final AttribuutElement woonplaatsAanvang,
            final AttribuutElement buitenlandsePlaatsAanvang,
            final AttribuutElement buitenlandseRegioAanvang,
            final AttribuutElement landGebiedAanvang,
            final AttribuutElement omschrijvingLocatieAanvang,
            final AttribuutElement redenEinde,
            final AttribuutElement datumEinde,
            final AttribuutElement gemeenteEinde,
            final AttribuutElement woonplaatsEinde,
            final AttribuutElement buitenlandsePlaatsEinde,
            final AttribuutElement buitenlandseRegioEinde,
            final AttribuutElement landGebiedEinde,
            final AttribuutElement omschrijvingLocatieEinde) {
        super(identiteitGroep, groep, null, null, tijdstipRegistratie, tijdstipVerval);
        this.datumAanvang = datumAanvang;
        this.gemeenteAanvang = gemeenteAanvang;
        this.woonplaatsAanvang = woonplaatsAanvang;
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
        this.landGebiedAanvang = landGebiedAanvang;
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
        this.redenEinde = redenEinde;
        this.datumEinde = datumEinde;
        this.gemeenteEinde = gemeenteEinde;
        this.woonplaatsEinde = woonplaatsEinde;
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
        this.landGebiedEinde = landGebiedEinde;
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    @Override
    public final BrpRelatieInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpRelatieInhoud(
                BrpMetaAttribuutMapper.mapBrpDatum(
                        record.getAttribuut(datumAanvang),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), datumAanvang, true)),
                BrpMetaAttribuutMapper.mapBrpGemeenteCode(
                        record.getAttribuut(gemeenteAanvang),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), gemeenteAanvang, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(woonplaatsAanvang),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), woonplaatsAanvang, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(buitenlandsePlaatsAanvang),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), buitenlandsePlaatsAanvang, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(buitenlandseRegioAanvang),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), buitenlandseRegioAanvang, true)),
                BrpMetaAttribuutMapper.mapBrpLandOfGebiedCode(
                        record.getAttribuut(landGebiedAanvang),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), landGebiedAanvang, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(omschrijvingLocatieAanvang),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), omschrijvingLocatieAanvang, true)),
                BrpMetaAttribuutMapper.mapBrpRedenEindeRelatieCode(
                        record.getAttribuut(redenEinde),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), redenEinde, true)),
                BrpMetaAttribuutMapper
                        .mapBrpDatum(record.getAttribuut(datumEinde), onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), datumEinde, true)),
                BrpMetaAttribuutMapper.mapBrpGemeenteCode(
                        record.getAttribuut(gemeenteEinde),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), gemeenteEinde, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(woonplaatsEinde),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), woonplaatsEinde, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(buitenlandsePlaatsEinde),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), buitenlandsePlaatsEinde, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(buitenlandseRegioEinde),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), buitenlandseRegioEinde, true)),
                BrpMetaAttribuutMapper.mapBrpLandOfGebiedCode(
                        record.getAttribuut(landGebiedEinde),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), landGebiedEinde, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(omschrijvingLocatieEinde),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), omschrijvingLocatieEinde, true)));
    }
}
