/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.springframework.stereotype.Component;

/**
 * Mapped IST stapels van een Persoon op de IST Huwelijk of GP stapel van de BrpPersoonslijst.
 */
@Component
public final class BrpIstHuwelijkOfGpMapper extends AbstractBrpIstMapper<BrpIstHuwelijkOfGpGroepInhoud> {

    /**
     * Mapped van de set met stapels de categorie 5 stapels op een lijst van BrpStapels met BrpIstRelatieGroepInhoud.
     * @param stapels de set met IST stapels
     * @return een lijst van stapels met BrpIstRelatieGroepInhoud
     */
    public List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> map(final Set<Stapel> stapels) {
        return super.mapStapels(stapels);
    }

    /**
     * Mapped een StapelVoorkomen op een BrpIstRelatieGroepInhoud.
     * @param voorkomen het voorkomen dat gemapped moet worden
     * @return het resultaat van de mapping
     */
    @Override
    protected BrpIstHuwelijkOfGpGroepInhoud mapBrpIstGroepInhoud(final StapelVoorkomen voorkomen) {

        // Map Standaard gegevens.
        final BrpIstStandaardGroepInhoud standaardInhoud = mapBrpStandaardInhoud(voorkomen);

        // Map Relatie gegevens.
        final BrpIstRelatieGroepInhoud istRelatieGroepInhoud = mapBrpRelatieGroepInhoud(voorkomen);

        // Map Huwelijk of Gp gegevens.
        final BrpInteger datumAanvang = BrpMapperUtil.mapBrpInteger(voorkomen.getDatumAanvang(), null);
        final BrpGemeenteCode gemeenteCodeAanvang = BrpMapperUtil.mapBrpGemeenteCode(voorkomen.getGemeenteAanvang(), null);
        final BrpString buitenlandsePlaatsAanvang = BrpMapperUtil.mapBrpString(voorkomen.getBuitenlandsePlaatsAanvang(), null);
        final BrpString omschrijvingLocatieAanvang = BrpMapperUtil.mapBrpString(voorkomen.getOmschrijvingLocatieAanvang(), null);
        final BrpLandOfGebiedCode landOfGebiedAanvang = BrpMapperUtil.mapBrpLandOfGebiedCode(voorkomen.getLandOfGebiedAanvang(), null);
        final BrpRedenEindeRelatieCode redenBeeindigingRelatieCode =
                BrpMapperUtil.mapBrpRedenEindeRelatieCode(voorkomen.getRedenBeeindigingRelatie(), null);
        final BrpInteger datumEinde = BrpMapperUtil.mapBrpInteger(voorkomen.getDatumEinde(), null);
        final BrpGemeenteCode gemeenteCodeEinde = BrpMapperUtil.mapBrpGemeenteCode(voorkomen.getGemeenteEinde(), null);
        final BrpString buitenlandsePlaatsEinde = BrpMapperUtil.mapBrpString(voorkomen.getBuitenlandsePlaatsEinde(), null);
        final BrpString omschrijvingLocatieEinde = BrpMapperUtil.mapBrpString(voorkomen.getOmschrijvingLocatieEinde(), null);
        final BrpLandOfGebiedCode landOfGebiedEinde = BrpMapperUtil.mapBrpLandOfGebiedCode(voorkomen.getLandOfGebiedEinde(), null);
        final BrpSoortRelatieCode soortRelatieCode = BrpMapperUtil.mapBrpSoortRelatieCode(voorkomen.getSoortRelatie(), null);

        final BrpIstHuwelijkOfGpGroepInhoud.Builder builder = new BrpIstHuwelijkOfGpGroepInhoud.Builder(standaardInhoud, istRelatieGroepInhoud);
        builder.datumAanvang(datumAanvang);
        builder.gemeenteCodeAanvang(gemeenteCodeAanvang);
        builder.buitenlandsePlaatsAanvang(buitenlandsePlaatsAanvang);
        builder.omschrijvingLocatieAanvang(omschrijvingLocatieAanvang);
        builder.landOfGebiedAanvang(landOfGebiedAanvang);
        builder.redenBeeindigingRelatieCode(redenBeeindigingRelatieCode);
        builder.datumEinde(datumEinde);
        builder.gemeenteCodeEinde(gemeenteCodeEinde);
        builder.buitenlandsePlaatsEinde(buitenlandsePlaatsEinde);
        builder.omschrijvingLocatieEinde(omschrijvingLocatieEinde);
        builder.landOfGebiedEinde(landOfGebiedEinde);
        builder.soortRelatieCode(soortRelatieCode);

        return builder.build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Lo3CategorieEnum getActueleCategorie() {
        return Lo3CategorieEnum.CATEGORIE_05;
    }

}
