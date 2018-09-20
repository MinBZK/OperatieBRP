/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StapelVoorkomen;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Decorator voor {@link StapelVoorkomen}. Bevat extra functionaliteit wat niet in de entiteit geimplementeerd kan
 * worden.
 */
public final class StapelVoorkomenDecorator extends AbstractDecorator {

    private final StapelVoorkomen voorkomen;

    /**
     * Maakt een StapelVoorkomenDecorator object.
     *
     * @param voorkomen
     *            het object waaraan functionaliteit moet worden toegevoegd
     */
    private StapelVoorkomenDecorator(final StapelVoorkomen voorkomen) {
        ValidationUtils.controleerOpNullWaarden("voorkomen mag niet null zijn", voorkomen);
        this.voorkomen = voorkomen;
    }

    /**
     * @param voorkomen
     *            het te decoreren StapelVoorkomen object
     * @return een StapelVoorkomenRelateerDecorator object
     */
    public static StapelVoorkomenDecorator decorate(final StapelVoorkomen voorkomen) {
        final StapelVoorkomenDecorator result;
        if (voorkomen == null) {
            result = null;
        } else {
            result = new StapelVoorkomenDecorator(voorkomen);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(voorkomen.getVolgnummer()).toHashCode();
    }

    @Override
    public boolean equals(final Object anderObject) {
        if (this == anderObject) {
            return true;
        }
        if (!(anderObject instanceof StapelVoorkomenDecorator)) {
            return false;
        }
        final StapelVoorkomenDecorator anderVoorkomen = (StapelVoorkomenDecorator) anderObject;

        return new EqualsBuilder().append(getVolgnummer(), anderVoorkomen.getVolgnummer()).isEquals();
    }

    /**
     * Geef de waarde van volgnummer.
     *
     * @return het volgnummer van dit voorkomen
     */
    public int getVolgnummer() {
        return voorkomen.getVolgnummer();
    }

    /**
     * Geef de waarde van voorkomen.
     *
     * @return het onderliggende voorkomen
     */
    public StapelVoorkomen getVoorkomen() {
        return voorkomen;
    }

    /**
     * @return Een kopie van het onderliggende StapelVoorkomen verpakt in een nieuwe StapelVoorkomenRelateerDecorator
     */
    public StapelVoorkomenDecorator kopieer() {
        final StapelVoorkomen nieuwVoorkomen =
                new StapelVoorkomen(voorkomen.getStapel(), voorkomen.getVolgnummer(), voorkomen.getAdministratieveHandeling());
        final StapelVoorkomenDecorator result = StapelVoorkomenDecorator.decorate(nieuwVoorkomen);
        result.werkGegevensBij(this);
        return result;
    }

    /**
     * Werk alle gegevens velden van dit voorkomen bij zodat ze dezelfe waarden bevatten als het gegeven voorkomen.
     *
     * @param bronVoorkomenDecorator
     *            Het voorkomen met de nieuwe gegevens
     */
    public void werkGegevensBij(final StapelVoorkomenDecorator bronVoorkomenDecorator) {
        final StapelVoorkomen bronVoorkomen = bronVoorkomenDecorator.getVoorkomen();

        werkStandaardGegevensBij(bronVoorkomen);
        werkGerelateerdenGegevensBij(bronVoorkomen);
        werkHuwelijkOfGpGegevensBij(bronVoorkomen);

        voorkomen.setRubriek6210DatumIngangFamilierechtelijkeBetrekking(bronVoorkomen.getRubriek6210DatumIngangFamilierechtelijkeBetrekking());
        voorkomen.setIndicatieOuder1HeeftGezag(bronVoorkomen.getIndicatieOuder1HeeftGezag());
        voorkomen.setIndicatieOuder2HeeftGezag(bronVoorkomen.getIndicatieOuder2HeeftGezag());
        voorkomen.setIndicatieDerdeHeeftGezag(bronVoorkomen.getIndicatieDerdeHeeftGezag());
        voorkomen.setIndicatieOnderCuratele(bronVoorkomen.getIndicatieOnderCuratele());
        voorkomen.setAdministratieveHandeling(bronVoorkomen.getAdministratieveHandeling());
    }

    private void werkStandaardGegevensBij(final StapelVoorkomen bronVoorkomen) {
        voorkomen.setSoortDocument(bronVoorkomen.getSoortDocument());
        voorkomen.setPartij(bronVoorkomen.getPartij());
        voorkomen.setRubriek8220DatumDocument(bronVoorkomen.getRubriek8220DatumDocument());
        voorkomen.setDocumentOmschrijving(bronVoorkomen.getDocumentOmschrijving());
        voorkomen.setRubriek8310AanduidingGegevensInOnderzoek(bronVoorkomen.getRubriek8310AanduidingGegevensInOnderzoek());
        voorkomen.setRubriek8320DatumIngangOnderzoek(bronVoorkomen.getRubriek8320DatumIngangOnderzoek());
        voorkomen.setRubriek8330DatumEindeOnderzoek(bronVoorkomen.getRubriek8330DatumEindeOnderzoek());
        voorkomen.setRubriek8410OnjuistOfStrijdigOpenbareOrde(bronVoorkomen.getRubriek8410OnjuistOfStrijdigOpenbareOrde());
        voorkomen.setRubriek8510IngangsdatumGeldigheid(bronVoorkomen.getRubriek8510IngangsdatumGeldigheid());
        voorkomen.setRubriek8610DatumVanOpneming(bronVoorkomen.getRubriek8610DatumVanOpneming());
    }

    private void werkGerelateerdenGegevensBij(final StapelVoorkomen bronVoorkomen) {
        voorkomen.setAktenummer(bronVoorkomen.getAktenummer());
        voorkomen.setAnummer(bronVoorkomen.getAnummer());
        voorkomen.setBsn(bronVoorkomen.getBsn());
        voorkomen.setVoornamen(bronVoorkomen.getVoornamen());
        voorkomen.setPredicaat(bronVoorkomen.getPredicaat());
        voorkomen.setAdellijkeTitel(bronVoorkomen.getAdellijkeTitel());
        voorkomen.setGeslachtsaanduidingBijAdellijkeTitelOfPredikaat(bronVoorkomen.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat());
        voorkomen.setVoorvoegsel(bronVoorkomen.getVoorvoegsel());
        voorkomen.setScheidingsteken(bronVoorkomen.getScheidingsteken());
        voorkomen.setGeslachtsnaamstam(bronVoorkomen.getGeslachtsnaamstam());
        voorkomen.setDatumGeboorte(bronVoorkomen.getDatumGeboorte());
        voorkomen.setGemeenteGeboorte(bronVoorkomen.getGemeenteGeboorte());
        voorkomen.setBuitenlandsePlaatsGeboorte(bronVoorkomen.getBuitenlandsePlaatsGeboorte());
        voorkomen.setOmschrijvingLocatieGeboorte(bronVoorkomen.getOmschrijvingLocatieGeboorte());
        voorkomen.setLandOfGebiedGeboorte(bronVoorkomen.getLandOfGebiedGeboorte());
        voorkomen.setGeslachtsaanduiding(bronVoorkomen.getGeslachtsaanduiding());
    }

    private void werkHuwelijkOfGpGegevensBij(final StapelVoorkomen bronVoorkomen) {
        voorkomen.setDatumAanvang(bronVoorkomen.getDatumAanvang());
        voorkomen.setGemeenteAanvang(bronVoorkomen.getGemeenteAanvang());
        voorkomen.setBuitenlandsePlaatsAanvang(bronVoorkomen.getBuitenlandsePlaatsAanvang());
        voorkomen.setOmschrijvingLocatieAanvang(bronVoorkomen.getOmschrijvingLocatieAanvang());
        voorkomen.setLandOfGebiedAanvang(bronVoorkomen.getLandOfGebiedAanvang());
        voorkomen.setRedenBeeindigingRelatie(bronVoorkomen.getRedenBeeindigingRelatie());
        voorkomen.setDatumEinde(bronVoorkomen.getDatumEinde());
        voorkomen.setGemeenteEinde(bronVoorkomen.getGemeenteEinde());
        voorkomen.setBuitenlandsePlaatsEinde(bronVoorkomen.getBuitenlandsePlaatsEinde());
        voorkomen.setOmschrijvingLocatieEinde(bronVoorkomen.getOmschrijvingLocatieEinde());
        voorkomen.setLandOfGebiedEinde(bronVoorkomen.getLandOfGebiedEinde());
        voorkomen.setSoortRelatie(bronVoorkomen.getSoortRelatie());
    }

    /**
     * @return true als dit voorkomen inhoudelijk gelijk is aan het gegeven voorkomen
     *
     * @param anderVoorkomen
     *            het andere voorkomen
     * @param ookOverigeGegevensControleren
     *            true als het ook de LO3 groepen 81, 82, 83, 84, 86 en 88 moet controleren.
     */
    public boolean isInhoudelijkGelijkAan(final StapelVoorkomenDecorator anderVoorkomen, final boolean ookOverigeGegevensControleren) {
        final StapelVoorkomen ander = anderVoorkomen.getVoorkomen();

        final Short gemeenteGeboorte = getGemeenteWaarde(voorkomen.getGemeenteGeboorte());
        final Short andereGemeenteGeboorte = getGemeenteWaarde(ander.getGemeenteGeboorte());
        final Short gemeenteAanvang = getGemeenteWaarde(voorkomen.getGemeenteAanvang());
        final Short andereGemeenteAanvang = getGemeenteWaarde(ander.getGemeenteAanvang());
        final Short gemeenteEinde = getGemeenteWaarde(voorkomen.getGemeenteEinde());
        final Short andereGemeenteEinde = getGemeenteWaarde(ander.getGemeenteEinde());

        final Short landOfGebiedGeboorte = getLandOfGebiedWaarde(voorkomen.getLandOfGebiedGeboorte());
        final Short andereLandOfGebiedGeboorte = getLandOfGebiedWaarde(ander.getLandOfGebiedGeboorte());
        final Short landOfGebiedAanvang = getLandOfGebiedWaarde(voorkomen.getLandOfGebiedAanvang());
        final Short andereLandOfGebiedAanvang = getLandOfGebiedWaarde(ander.getLandOfGebiedAanvang());
        final Short landOfGebiedEinde = getLandOfGebiedWaarde(voorkomen.getLandOfGebiedEinde());
        final Short andereLandOfGebiedEinde = getLandOfGebiedWaarde(ander.getLandOfGebiedEinde());
        final Character redenBeeindigingRelatie = getRedenEindeWaarde(voorkomen.getRedenBeeindigingRelatie());
        final Character andereRedenBeeindigingRelatie = getRedenEindeWaarde(ander.getRedenBeeindigingRelatie());

        EqualsBuilder equalsBuilder =
                new EqualsBuilder().append(voorkomen.getRubriek8510IngangsdatumGeldigheid(), ander.getRubriek8510IngangsdatumGeldigheid())
                                   .append(
                                       voorkomen.getRubriek6210DatumIngangFamilierechtelijkeBetrekking(),
                                       ander.getRubriek6210DatumIngangFamilierechtelijkeBetrekking())
                                   .append(voorkomen.getAnummer(), ander.getAnummer())
                                   .append(voorkomen.getBsn(), ander.getBsn())
                                   .append(voorkomen.getVoornamen(), ander.getVoornamen())
                                   .append(voorkomen.getPredicaat(), ander.getPredicaat())
                                   .append(voorkomen.getAdellijkeTitel(), ander.getAdellijkeTitel())
                                   .append(
                                       voorkomen.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat(),
                                       ander.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat())
                                   .append(voorkomen.getVoorvoegsel(), ander.getVoorvoegsel())
                                   .append(voorkomen.getScheidingsteken(), ander.getScheidingsteken())
                                   .append(voorkomen.getGeslachtsnaamstam(), ander.getGeslachtsnaamstam())
                                   .append(voorkomen.getDatumGeboorte(), ander.getDatumGeboorte())
                                   .append(gemeenteGeboorte, andereGemeenteGeboorte)
                                   .append(voorkomen.getBuitenlandsePlaatsGeboorte(), ander.getBuitenlandsePlaatsGeboorte())
                                   .append(voorkomen.getOmschrijvingLocatieGeboorte(), ander.getOmschrijvingLocatieGeboorte())
                                   .append(landOfGebiedGeboorte, andereLandOfGebiedGeboorte)
                                   .append(voorkomen.getGeslachtsaanduiding(), ander.getGeslachtsaanduiding())
                                   .append(voorkomen.getDatumAanvang(), ander.getDatumAanvang())
                                   .append(gemeenteAanvang, andereGemeenteAanvang)
                                   .append(voorkomen.getBuitenlandsePlaatsAanvang(), ander.getBuitenlandsePlaatsAanvang())
                                   .append(voorkomen.getOmschrijvingLocatieAanvang(), ander.getOmschrijvingLocatieAanvang())
                                   .append(landOfGebiedAanvang, andereLandOfGebiedAanvang)
                                   .append(redenBeeindigingRelatie, andereRedenBeeindigingRelatie)
                                   .append(voorkomen.getDatumEinde(), ander.getDatumEinde())
                                   .append(gemeenteEinde, andereGemeenteEinde)
                                   .append(voorkomen.getBuitenlandsePlaatsEinde(), ander.getBuitenlandsePlaatsEinde())
                                   .append(voorkomen.getOmschrijvingLocatieEinde(), ander.getOmschrijvingLocatieEinde())
                                   .append(landOfGebiedEinde, andereLandOfGebiedEinde)
                                   .append(voorkomen.getSoortRelatie(), ander.getSoortRelatie())
                                   .append(voorkomen.getIndicatieOuder1HeeftGezag(), ander.getIndicatieOuder1HeeftGezag())
                                   .append(voorkomen.getIndicatieOuder2HeeftGezag(), ander.getIndicatieOuder2HeeftGezag())
                                   .append(voorkomen.getIndicatieDerdeHeeftGezag(), ander.getIndicatieDerdeHeeftGezag())
                                   .append(voorkomen.getIndicatieOnderCuratele(), ander.getIndicatieOnderCuratele());

        if (ookOverigeGegevensControleren) {
            final Integer partij = getPartijWaarde(voorkomen.getPartij());
            final Integer anderePartij = getPartijWaarde(ander.getPartij());

            final String soortDocument = getSoortDocumentWaarde(voorkomen.getSoortDocument());
            final String andereSoortDocument = getSoortDocumentWaarde(ander.getSoortDocument());

            equalsBuilder =
                    equalsBuilder.append(voorkomen.getAktenummer(), ander.getAktenummer())
                                 .append(partij, anderePartij)
                                 .append(soortDocument, andereSoortDocument)
                                 .append(voorkomen.getRubriek8220DatumDocument(), ander.getRubriek8220DatumDocument())
                                 .append(voorkomen.getDocumentOmschrijving(), ander.getDocumentOmschrijving())
                                 .append(voorkomen.getRubriek8310AanduidingGegevensInOnderzoek(), ander.getRubriek8310AanduidingGegevensInOnderzoek())
                                 .append(voorkomen.getRubriek8320DatumIngangOnderzoek(), ander.getRubriek8320DatumIngangOnderzoek())
                                 .append(voorkomen.getRubriek8330DatumEindeOnderzoek(), ander.getRubriek8330DatumEindeOnderzoek())
                                 .append(voorkomen.getRubriek8410OnjuistOfStrijdigOpenbareOrde(), ander.getRubriek8410OnjuistOfStrijdigOpenbareOrde())
                                 .append(voorkomen.getRubriek8610DatumVanOpneming(), ander.getRubriek8610DatumVanOpneming());
        }
        return equalsBuilder.isEquals();
    }

    /**
     * Geef de waarde van administratieve handeling.
     *
     * @return de administratieve handeling voor dit voorkomen.
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return voorkomen.getAdministratieveHandeling();
    }

    /**
     * Zet de gegeven administratieve handeling voor dit voorkomen.
     *
     * @param administratieveHandeling
     *            de administratieve handeling
     */
    public void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        voorkomen.setAdministratieveHandeling(administratieveHandeling);
    }

    /**
     * @return De stapel waar dit voorkomen toe behoort.
     */
    public StapelDecorator getStapel() {
        return StapelDecorator.decorate(voorkomen.getStapel());
    }
}
