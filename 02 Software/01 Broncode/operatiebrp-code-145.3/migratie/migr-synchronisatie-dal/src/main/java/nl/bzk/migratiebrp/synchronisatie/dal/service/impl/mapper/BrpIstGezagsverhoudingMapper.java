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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
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
        if (result == null || result.isEmpty()) {
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
        Lo3CategorieEnum categorie;
        try {
            categorie = Lo3CategorieEnum.getLO3Categorie(voorkomen.getStapel().getCategorie());
        } catch (final Lo3SyntaxException lse) {
            throw new IllegalArgumentException("Ongeldig categorie in stapelvoorkomen: " + voorkomen.getStapel().getCategorie(), lse);
        }

        final int stapel = voorkomen.getStapel().getVolgnummer();
        final int voorkomenNr = voorkomen.getVolgnummer();
        if (voorkomenNr > 0) {
            categorie = Lo3CategorieEnum.bepaalHistorischeCategorie(categorie);
        }

        final BrpSoortDocumentCode soortDocument =
                voorkomen.getSoortDocument() != null ? new BrpSoortDocumentCode(voorkomen.getSoortDocument().getNaam()) : null;
        final BrpPartijCode partij = voorkomen.getPartij() != null ? BrpPartijCode.wrap(voorkomen.getPartij().getCode(), null) : null;
        final BrpInteger rubriek8220DatumDocument = BrpInteger.wrap(voorkomen.getRubriek8220DatumDocument(), null);
        final BrpString documentOmschrijving = BrpString.wrap(voorkomen.getDocumentOmschrijving(), null);
        final BrpInteger rubriek8310AanduidingGegevensInOnderzoek = BrpInteger.wrap(voorkomen.getRubriek8310AanduidingGegevensInOnderzoek(), null);
        final BrpInteger rubriek8320DatumIngangOnderzoek = BrpInteger.wrap(voorkomen.getRubriek8320DatumIngangOnderzoek(), null);
        final BrpInteger rubriek8330DatumEindeOnderzoek = BrpInteger.wrap(voorkomen.getRubriek8330DatumEindeOnderzoek(), null);
        final BrpCharacter rubriek8410OnjuistOfStrijdigOpenbareOrde = BrpCharacter.wrap(voorkomen.getRubriek8410OnjuistOfStrijdigOpenbareOrde(), null);
        final BrpInteger rubriek8510IngangsdatumGeldigheid = BrpInteger.wrap(voorkomen.getRubriek8510IngangsdatumGeldigheid(), null);
        final BrpInteger rubriek8610DatumVanOpneming = BrpInteger.wrap(voorkomen.getRubriek8610DatumVanOpneming(), null);
        final BrpBoolean indicatieOuder1HeeftGezag = BrpBoolean.wrap(voorkomen.getIndicatieOuder1HeeftGezag(), null);
        final BrpBoolean indicatieOuder2HeeftGezag = BrpBoolean.wrap(voorkomen.getIndicatieOuder2HeeftGezag(), null);
        final BrpBoolean indicatieDerdeHeeftGezag = BrpBoolean.wrap(voorkomen.getIndicatieDerdeHeeftGezag(), null);
        final BrpBoolean indicatieOnderCuratele = BrpBoolean.wrap(voorkomen.getIndicatieOnderCuratele(), null);

        final BrpIstStandaardGroepInhoud.Builder standaardBuilder = new BrpIstStandaardGroepInhoud.Builder(categorie, stapel, voorkomenNr);
        standaardBuilder.soortDocument(soortDocument);
        standaardBuilder.partij(partij);
        standaardBuilder.rubriek8220DatumDocument(rubriek8220DatumDocument);
        standaardBuilder.documentOmschrijving(documentOmschrijving);
        standaardBuilder.rubriek8310AanduidingGegevensInOnderzoek(rubriek8310AanduidingGegevensInOnderzoek);
        standaardBuilder.rubriek8320DatumIngangOnderzoek(rubriek8320DatumIngangOnderzoek);
        standaardBuilder.rubriek8330DatumEindeOnderzoek(rubriek8330DatumEindeOnderzoek);
        standaardBuilder.rubriek8410OnjuistOfStrijdigOpenbareOrde(rubriek8410OnjuistOfStrijdigOpenbareOrde);
        standaardBuilder.rubriek8510IngangsdatumGeldigheid(rubriek8510IngangsdatumGeldigheid);
        standaardBuilder.rubriek8610DatumVanOpneming(rubriek8610DatumVanOpneming);

        final BrpIstGezagsVerhoudingGroepInhoud.Builder gezagsverhoudingBuilder = new BrpIstGezagsVerhoudingGroepInhoud.Builder(standaardBuilder.build());
        gezagsverhoudingBuilder.indicatieDerdeHeeftGezag(indicatieDerdeHeeftGezag);
        gezagsverhoudingBuilder.indicatieOnderCuratele(indicatieOnderCuratele);
        gezagsverhoudingBuilder.indicatieOuder1HeeftGezag(indicatieOuder1HeeftGezag);
        gezagsverhoudingBuilder.indicatieOuder2HeeftGezag(indicatieOuder2HeeftGezag);

        return gezagsverhoudingBuilder.build();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.AbstractBrpIstMapper#getActueleCategorie()
     */
    @Override
    Lo3CategorieEnum getActueleCategorie() {
        return Lo3CategorieEnum.CATEGORIE_11;
    }
}
