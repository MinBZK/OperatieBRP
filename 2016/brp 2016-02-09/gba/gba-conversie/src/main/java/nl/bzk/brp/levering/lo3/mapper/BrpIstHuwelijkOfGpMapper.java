/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.List;
import java.util.Set;

import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.logisch.ist.StapelVoorkomen;
import nl.bzk.brp.model.logisch.ist.StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroep;
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
 *
 */
@Component
public final class BrpIstHuwelijkOfGpMapper extends AbstractBrpIstMapper<BrpIstHuwelijkOfGpGroepInhoud> {

    /**
     * Mapped van de set met stapels de categorie 5 stapels op een lijst van BrpStapels met BrpIstRelatieGroepInhoud.
     *
     * @param stapels de set met IST stapels
     * @return een lijst van stapels met BrpIstRelatieGroepInhoud
     */
    public List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> map(final Set<Stapel> stapels) {
        return super.mapStapels(stapels);
    }

    /**
     * Mapped een StapelVoorkomen op een BrpIstRelatieGroepInhoud.
     *
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
        final BrpInteger datumAanvang;
        final BrpGemeenteCode gemeenteCodeAanvang;
        final BrpString buitenlandsePlaatsAanvang;
        final BrpString omschrijvingLocatieAanvang;
        final BrpLandOfGebiedCode landOfGebiedAanvang;
        final BrpRedenEindeRelatieCode redenBeeindigingRelatieCode;
        final BrpInteger datumEinde;
        final BrpGemeenteCode gemeenteCodeEinde;
        final BrpString buitenlandsePlaatsEinde;
        final BrpString omschrijvingLocatieEinde;
        final BrpLandOfGebiedCode landOfGebiedEinde;
        final BrpSoortRelatieCode soortRelatieCode;
        final StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroep groep = voorkomen.getCategorieHuwelijkGeregistreerdPartnerschap();
        if (groep != null) {
            datumAanvang = BrpMapperUtil.mapBrpInteger(groep.getDatumAanvang(), null);
            gemeenteCodeAanvang = BrpMapperUtil.mapBrpGemeenteCode(groep.getGemeenteAanvang(), null);
            buitenlandsePlaatsAanvang = BrpMapperUtil.mapBrpString(groep.getBuitenlandsePlaatsAanvang(), null);
            omschrijvingLocatieAanvang = BrpMapperUtil.mapBrpString(groep.getOmschrijvingLocatieAanvang(), null);
            landOfGebiedAanvang = BrpMapperUtil.mapBrpLandOfGebiedCode(groep.getLandGebiedAanvang(), null);
            redenBeeindigingRelatieCode = BrpMapperUtil.mapBrpRedenEindeRelatieCode(groep.getRedenEinde(), null);
            datumEinde = BrpMapperUtil.mapBrpInteger(groep.getDatumEinde(), null);
            gemeenteCodeEinde = BrpMapperUtil.mapBrpGemeenteCode(groep.getGemeenteEinde(), null);
            buitenlandsePlaatsEinde = BrpMapperUtil.mapBrpString(groep.getBuitenlandsePlaatsEinde(), null);
            omschrijvingLocatieEinde = BrpMapperUtil.mapBrpString(groep.getOmschrijvingLocatieEinde(), null);
            landOfGebiedEinde = BrpMapperUtil.mapBrpLandOfGebiedCode(groep.getLandGebiedEinde(), null);
            soortRelatieCode = BrpMapperUtil.mapBrpSoortRelatieCode(groep.getSoortRelatie(), null);
        } else {
            datumAanvang = null;
            gemeenteCodeAanvang = null;
            buitenlandsePlaatsAanvang = null;
            omschrijvingLocatieAanvang = null;
            landOfGebiedAanvang = null;
            redenBeeindigingRelatieCode = null;
            datumEinde = null;
            gemeenteCodeEinde = null;
            buitenlandsePlaatsEinde = null;
            omschrijvingLocatieEinde = null;
            landOfGebiedEinde = null;
            soortRelatieCode = null;
        }

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
