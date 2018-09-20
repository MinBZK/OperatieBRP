/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;

/**
 * Mapt een relatie.
 */
public abstract class AbstractRelatieMapper extends AbstractFormeelMapper<RelatieHisVolledig, HisRelatieModel, BrpRelatieInhoud> {

    private final ElementEnum datumAanvang;
    private final ElementEnum gemeenteAanvang;
    private final ElementEnum woonplaatsAanvang;
    private final ElementEnum buitenlandsePlaatsAanvang;
    private final ElementEnum buitenlandseRegioAanvang;
    private final ElementEnum landGebiedAanvang;
    private final ElementEnum omschrijvingLocatieAanvang;
    private final ElementEnum redenEinde;
    private final ElementEnum datumEinde;
    private final ElementEnum gemeenteEinde;
    private final ElementEnum woonplaatsEinde;
    private final ElementEnum buitenlandsePlaatsEinde;
    private final ElementEnum buitenlandseRegioEinde;
    private final ElementEnum landGebiedEinde;
    private final ElementEnum omschrijvingLocatieEinde;

    /**
     * Constructor.
     *
     * @param tijdstipRegistratie
     *            element voor tijdstip registratie
     * @param tijdstipVerval
     *            element voor tijdstip verval
     * @param datumAanvang
     *            element voor datum aanvang
     * @param gemeenteAanvang
     *            element voor gemeente aanvang
     * @param woonplaatsAanvang
     *            element voor woonplaats aanvang
     * @param buitenlandsePlaatsAanvang
     *            element voor buitenlandse plaats aanvang
     * @param buitenlandseRegioAanvang
     *            element voor buitenlandse regio aanvang
     * @param landGebiedAanvang
     *            element voor land/gebied aanvang
     * @param omschrijvingLocatieAanvang
     *            element voor omschrijving locatie aanvang
     * @param redenEinde
     *            element voor reden einde
     * @param datumEinde
     *            element voor datum einde
     * @param gemeenteEinde
     *            element voor gemeente einde
     * @param woonplaatsEinde
     *            element voor woonplaats einde
     * @param buitenlandsePlaatsEinde
     *            element voor buitenlandse plaats einde
     * @param buitenlandseRegioEinde
     *            element voor buitenlandse regio einde
     * @param landGebiedEinde
     *            element voor land/gebied einde
     * @param omschrijvingLocatieEinde
     *            element voor omschrijving locatie einde
     *
     */
    protected AbstractRelatieMapper(
        final ElementEnum tijdstipRegistratie,
        final ElementEnum tijdstipVerval,
        final ElementEnum datumAanvang,
        final ElementEnum gemeenteAanvang,
        final ElementEnum woonplaatsAanvang,
        final ElementEnum buitenlandsePlaatsAanvang,
        final ElementEnum buitenlandseRegioAanvang,
        final ElementEnum landGebiedAanvang,
        final ElementEnum omschrijvingLocatieAanvang,
        final ElementEnum redenEinde,
        final ElementEnum datumEinde,
        final ElementEnum gemeenteEinde,
        final ElementEnum woonplaatsEinde,
        final ElementEnum buitenlandsePlaatsEinde,
        final ElementEnum buitenlandseRegioEinde,
        final ElementEnum landGebiedEinde,
        final ElementEnum omschrijvingLocatieEinde)
    {
        super(tijdstipRegistratie, tijdstipVerval);
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
    protected final Iterable<HisRelatieModel> getHistorieIterable(final RelatieHisVolledig hisVolledig) {
        return hisVolledig.getRelatieHistorie();
    }

    @Override
    public final BrpRelatieInhoud mapInhoud(final HisRelatieModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpRelatieInhoud(
            BrpMapperUtil.mapBrpDatum(historie.getDatumAanvang(), onderzoekMapper.bepaalOnderzoek(historie.getID(), datumAanvang, true)),
            BrpMapperUtil.mapBrpGemeenteCode(historie.getGemeenteAanvang(), onderzoekMapper.bepaalOnderzoek(historie.getID(), gemeenteAanvang, true)),
            BrpMapperUtil.mapBrpString(historie.getWoonplaatsnaamAanvang(), onderzoekMapper.bepaalOnderzoek(historie.getID(), woonplaatsAanvang, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsePlaatsAanvang(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), buitenlandsePlaatsAanvang, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandseRegioAanvang(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), buitenlandseRegioAanvang, true)),
            BrpMapperUtil.mapBrpLandOfGebiedCode(
                historie.getLandGebiedAanvang(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), landGebiedAanvang, true)),
            BrpMapperUtil.mapBrpString(
                historie.getOmschrijvingLocatieAanvang(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), omschrijvingLocatieAanvang, true)),
            BrpMapperUtil.mapBrpRedenEindeRelatieCode(historie.getRedenEinde(), onderzoekMapper.bepaalOnderzoek(historie.getID(), redenEinde, true)),
            BrpMapperUtil.mapBrpDatum(historie.getDatumEinde(), onderzoekMapper.bepaalOnderzoek(historie.getID(), datumEinde, true)),
            BrpMapperUtil.mapBrpGemeenteCode(historie.getGemeenteEinde(), onderzoekMapper.bepaalOnderzoek(historie.getID(), gemeenteEinde, true)),
            BrpMapperUtil.mapBrpString(historie.getWoonplaatsnaamEinde(), onderzoekMapper.bepaalOnderzoek(historie.getID(), woonplaatsEinde, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsePlaatsEinde(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), buitenlandsePlaatsEinde, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandseRegioEinde(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), buitenlandseRegioEinde, true)),
            BrpMapperUtil.mapBrpLandOfGebiedCode(historie.getLandGebiedEinde(), onderzoekMapper.bepaalOnderzoek(historie.getID(), landGebiedEinde, true)),
            BrpMapperUtil.mapBrpString(
                historie.getOmschrijvingLocatieEinde(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), omschrijvingLocatieEinde, true)));
    }
}
