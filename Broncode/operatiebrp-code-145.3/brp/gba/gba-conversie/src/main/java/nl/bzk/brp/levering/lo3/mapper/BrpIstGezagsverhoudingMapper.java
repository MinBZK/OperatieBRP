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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.springframework.stereotype.Component;

/**
 * Mapped IST stapels van een Persoon op de IST gezagsverhouding stapel van de BrpPersoonslijst.
 */
@Component
public final class BrpIstGezagsverhoudingMapper extends AbstractBrpIstMapper<BrpIstGezagsVerhoudingGroepInhoud> {

    /**
     * Mapped van de set met stapels de categorie 11 stapels op een BrpStapel met BrpIstGezagsVerhoudingGroepInhoud.
     * @param stapels de set met IST stapels
     * @return een stapel van BrpIstGezagsVerhoudingGroepInhoud
     */
    public BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> map(final Set<Stapel> stapels) {
        final List<BrpStapel<BrpIstGezagsVerhoudingGroepInhoud>> result = super.mapStapels(stapels);
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    /**
     * Mapped een StapelVoorkomen op een BrpIstGezagsVerhoudingGroepInhoud.
     * @param voorkomen het voorkomen dat gemapped moet worden
     * @return het resultaat van de mapping
     */
    @Override
    protected BrpIstGezagsVerhoudingGroepInhoud mapBrpIstGroepInhoud(final StapelVoorkomen voorkomen) {
        final BrpBoolean indicatieOuder1HeeftGezag = BrpMapperUtil.mapBrpBoolean(voorkomen.getIndicatieOuder1HeeftGezag(), null);
        final BrpBoolean indicatieOuder2HeeftGezag = BrpMapperUtil.mapBrpBoolean(voorkomen.getIndicatieOuder2HeeftGezag(), null);
        final BrpBoolean indicatieDerdeHeeftGezag = BrpMapperUtil.mapBrpBoolean(voorkomen.getIndicatieDerdeHeeftGezag(), null);
        final BrpBoolean indicatieOnderCuratele = BrpMapperUtil.mapBrpBoolean(voorkomen.getIndicatieOnderCuratele(), null);

        // Map de standaard gegevens.
        final BrpIstStandaardGroepInhoud istStandaardGroepInhoud = mapBrpStandaardInhoud(voorkomen);

        // Map de gezagsverhouding groep gegevens.
        final BrpIstGezagsVerhoudingGroepInhoud.Builder gezagsverhoudingBuilder = new BrpIstGezagsVerhoudingGroepInhoud.Builder(istStandaardGroepInhoud);
        gezagsverhoudingBuilder.indicatieDerdeHeeftGezag(indicatieDerdeHeeftGezag);
        gezagsverhoudingBuilder.indicatieOnderCuratele(indicatieOnderCuratele);
        gezagsverhoudingBuilder.indicatieOuder1HeeftGezag(indicatieOuder1HeeftGezag);
        gezagsverhoudingBuilder.indicatieOuder2HeeftGezag(indicatieOuder2HeeftGezag);

        return gezagsverhoudingBuilder.build();
    }

    @Override
    Lo3CategorieEnum getActueleCategorie() {
        return Lo3CategorieEnum.CATEGORIE_11;
    }
}
