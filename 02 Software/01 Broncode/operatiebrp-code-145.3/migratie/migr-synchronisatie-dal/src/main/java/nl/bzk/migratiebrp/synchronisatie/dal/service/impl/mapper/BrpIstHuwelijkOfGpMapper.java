/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

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
        final BrpIstStandaardGroepInhoud standaardInhoud = mapBrpStandaardInhoud(voorkomen);
        final BrpIstRelatieGroepInhoud istRelatieGroepInhoud = mapBrpRelatieGroepInhoud(voorkomen);

        final BrpInteger datumAanvang = BrpInteger.wrap(voorkomen.getDatumAanvang(), null);
        final BrpGemeenteCode gemeenteCodeAanvang =
                voorkomen.getGemeenteAanvang() != null ? BrpGemeenteCode.wrap(voorkomen.getGemeenteAanvang().getCode(), null) : null;
        final BrpString buitenlandsePlaatsAanvang = BrpString.wrap(voorkomen.getBuitenlandsePlaatsAanvang(), null);
        final BrpString omschrijvingLocatieAanvang = BrpString.wrap(voorkomen.getOmschrijvingLocatieAanvang(), null);
        final BrpLandOfGebiedCode landOfGebiedAanvang =
                voorkomen.getLandOfGebiedAanvang() != null ? BrpLandOfGebiedCode.wrap(voorkomen.getLandOfGebiedAanvang().getCode(), null) : null;

        final BrpRedenEindeRelatieCode redenBeeindigingRelatieCode;
        if (voorkomen.getRedenBeeindigingRelatie() != null) {
            redenBeeindigingRelatieCode = BrpRedenEindeRelatieCode.wrap(voorkomen.getRedenBeeindigingRelatie().getCode(), null);
        } else {
            redenBeeindigingRelatieCode = null;
        }

        final BrpInteger datumEinde = BrpInteger.wrap(voorkomen.getDatumEinde(), null);
        final BrpGemeenteCode gemeenteCodeEinde =
                voorkomen.getGemeenteEinde() != null ? BrpGemeenteCode.wrap(voorkomen.getGemeenteEinde().getCode(), null) : null;
        final BrpString buitenlandsePlaatsEinde = BrpString.wrap(voorkomen.getBuitenlandsePlaatsEinde(), null);
        final BrpString omschrijvingLocatieEinde = BrpString.wrap(voorkomen.getOmschrijvingLocatieEinde(), null);
        final BrpLandOfGebiedCode landOfGebiedEinde =
                voorkomen.getLandOfGebiedEinde() != null ? BrpLandOfGebiedCode.wrap(voorkomen.getLandOfGebiedEinde().getCode(), null) : null;

        final BrpSoortRelatieCode soortRelatieCode =
                voorkomen.getSoortRelatie() != null ? BrpSoortRelatieCode.wrap(voorkomen.getSoortRelatie().getCode(), null) : null;

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
