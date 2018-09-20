/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de geboorte.
 */
@Component
public abstract class AbstractGeboorteMapper extends AbstractFormeelMapper<PersoonHisVolledig, HisPersoonGeboorteModel, BrpGeboorteInhoud> {

    private final ElementEnum datum;
    private final ElementEnum gemeenteCode;
    private final ElementEnum woonplaatsnaam;
    private final ElementEnum buitenlandsePlaats;
    private final ElementEnum buitenlandseRegio;
    private final ElementEnum landgebiedCode;
    private final ElementEnum omschrijvingLocatie;

    /**
     * Constructor.
     * 
     * @param tijdstipRegistratie
     *            element voor tijdstip registratie
     * @param tijdstipVerval
     *            element voor tijdstip verval
     * @param datum
     *            element voor datum
     * @param gemeenteCode
     *            element voor gemeente
     * @param woonplaatsnaam
     *            element voor woonplaats
     * @param buitenlandsePlaats
     *            element voor buitenlandse plaats
     * @param buitenlandseRegio
     *            element voor buitenlandse regio
     * @param landgebiedCode
     *            element voor land/gebied
     * @param omschrijvingLocatie
     *            element voor omschrijving locatie
     */
    protected AbstractGeboorteMapper(
        final ElementEnum tijdstipRegistratie,
        final ElementEnum tijdstipVerval,
        final ElementEnum datum,
        final ElementEnum gemeenteCode,
        final ElementEnum woonplaatsnaam,
        final ElementEnum buitenlandsePlaats,
        final ElementEnum buitenlandseRegio,
        final ElementEnum landgebiedCode,
        final ElementEnum omschrijvingLocatie)
    {
        super(tijdstipRegistratie, tijdstipVerval);
        this.datum = datum;
        this.gemeenteCode = gemeenteCode;
        this.woonplaatsnaam = woonplaatsnaam;
        this.buitenlandsePlaats = buitenlandsePlaats;
        this.buitenlandseRegio = buitenlandseRegio;
        this.landgebiedCode = landgebiedCode;
        this.omschrijvingLocatie = omschrijvingLocatie;

    }

    @Override
    protected final Iterable<HisPersoonGeboorteModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonGeboorteHistorie();
    }

    @Override
    public final BrpGeboorteInhoud mapInhoud(final HisPersoonGeboorteModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpGeboorteInhoud(
            BrpMapperUtil.mapBrpDatum(historie.getDatumGeboorte(), onderzoekMapper.bepaalOnderzoek(historie.getID(), datum, true)),
            BrpMapperUtil.mapBrpGemeenteCode(historie.getGemeenteGeboorte(), onderzoekMapper.bepaalOnderzoek(historie.getID(), gemeenteCode, true)),
            BrpMapperUtil.mapBrpString(historie.getWoonplaatsnaamGeboorte(), onderzoekMapper.bepaalOnderzoek(historie.getID(), woonplaatsnaam, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsePlaatsGeboorte(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), buitenlandsePlaats, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandseRegioGeboorte(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), buitenlandseRegio, true)),
            BrpMapperUtil.mapBrpLandOfGebiedCode(
                historie.getLandGebiedGeboorte(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), landgebiedCode, true)),
            BrpMapperUtil.mapBrpString(
                historie.getOmschrijvingLocatieGeboorte(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), omschrijvingLocatie, true)));
    }
}
