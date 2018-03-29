/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de geboorte.
 */
@Component
public abstract class AbstractGeboorteMapper extends AbstractMapper<BrpGeboorteInhoud> {

    private final AttribuutElement datum;
    private final AttribuutElement gemeenteCode;
    private final AttribuutElement woonplaatsnaam;
    private final AttribuutElement buitenlandsePlaats;
    private final AttribuutElement buitenlandseRegio;
    private final AttribuutElement landgebiedCode;
    private final AttribuutElement omschrijvingLocatie;

    /**
     * Constructor.
     * @param identiteitGroep element voor identiteit groep
     * @param groep element voor geboorte groep
     * @param tijdstipRegistratie element voor tijdstip registratie
     * @param tijdstipVerval element voor tijdstip verval
     * @param datum element voor datum
     * @param gemeenteCode element voor gemeente
     * @param woonplaatsnaam element voor woonplaats
     * @param buitenlandsePlaats element voor buitenlandse plaats
     * @param buitenlandseRegio element voor buitenlandse regio
     * @param landgebiedCode element voor land/gebied
     * @param omschrijvingLocatie element voor omschrijving locatie
     */
    //
    /*
     * squid:S00107 Methods should not have too many parameters
     *
     * Terecht, geaccepteerd voor deze klasse.
     */
    protected AbstractGeboorteMapper(
            final GroepElement identiteitGroep,
            final GroepElement groep,
            final AttribuutElement tijdstipRegistratie,
            final AttribuutElement tijdstipVerval,
            final AttribuutElement datum,
            final AttribuutElement gemeenteCode,
            final AttribuutElement woonplaatsnaam,
            final AttribuutElement buitenlandsePlaats,
            final AttribuutElement buitenlandseRegio,
            final AttribuutElement landgebiedCode,
            final AttribuutElement omschrijvingLocatie) {
        super(identiteitGroep, groep, null, null, tijdstipRegistratie, tijdstipVerval);
        this.datum = datum;
        this.gemeenteCode = gemeenteCode;
        this.woonplaatsnaam = woonplaatsnaam;
        this.buitenlandsePlaats = buitenlandsePlaats;
        this.buitenlandseRegio = buitenlandseRegio;
        this.landgebiedCode = landgebiedCode;
        this.omschrijvingLocatie = omschrijvingLocatie;

    }

    @Override
    public final BrpGeboorteInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpGeboorteInhoud(
                BrpMetaAttribuutMapper.mapBrpDatum(record.getAttribuut(datum), onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), datum, true)),
                BrpMetaAttribuutMapper.mapBrpGemeenteCode(
                        record.getAttribuut(gemeenteCode),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), gemeenteCode, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(woonplaatsnaam),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), woonplaatsnaam, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(buitenlandsePlaats),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), buitenlandsePlaats, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(buitenlandseRegio),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), buitenlandseRegio, true)),
                BrpMetaAttribuutMapper.mapBrpLandOfGebiedCode(
                        record.getAttribuut(landgebiedCode),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), landgebiedCode, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(omschrijvingLocatie),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), omschrijvingLocatie, true)));
    }
}
