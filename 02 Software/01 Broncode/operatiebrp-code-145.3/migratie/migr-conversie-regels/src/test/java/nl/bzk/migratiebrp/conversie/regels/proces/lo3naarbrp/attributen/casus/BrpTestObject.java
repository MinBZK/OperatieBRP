/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.casus;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;

/**
 * Test class om makkelijk de inhoud van de test casus op te nemen
 */
public class BrpTestObject<T extends BrpGroepInhoud> {

    private T inhoud;
    private BrpDatum aanvangGeldigheid;
    private BrpDatum eindeGeldigheid;
    private BrpDatumTijd registratie;
    private BrpDatumTijd verval;
    private BrpCharacter nadereAanduidingVerval;
    private BrpActie actieInhoud;
    private BrpActie actieGeldigheid;
    private BrpActie actieVerval;

    public void vul(
            final T inhoud,
            final Integer aanvangGeldigheid,
            final Integer eindeGeldigheid,
            final Long registratie,
            final Long verval,
            final Character nadereAanduidingVerval,
            final BrpActie actieInhoud,
            final BrpActie actieGeldigheid,
            final BrpActie actieVerval) {
        this.inhoud = inhoud;
        this.aanvangGeldigheid = aanvangGeldigheid == null ? null : new BrpDatum(aanvangGeldigheid, null);
        this.eindeGeldigheid = eindeGeldigheid == null ? null : new BrpDatum(eindeGeldigheid, null);
        this.registratie = registratie == null ? null : BrpDatumTijd.fromDatumTijd(registratie, null);
        this.verval = verval == null ? null : BrpDatumTijd.fromDatumTijd(verval, null);
        this.actieInhoud = actieInhoud;
        this.actieGeldigheid = actieGeldigheid;
        this.actieVerval = actieVerval;
        this.nadereAanduidingVerval = BrpCharacter.wrap(nadereAanduidingVerval, null);
    }

    /**
     * Geef de waarde van inhoud.
     * @return inhoud
     */
    public T getInhoud() {
        return inhoud;
    }

    /**
     * Geef de waarde van aanvang geldigheid.
     * @return aanvang geldigheid
     */
    public BrpDatum getAanvangGeldigheid() {
        return aanvangGeldigheid;
    }

    /**
     * Geef de waarde van einde geldigheid.
     * @return einde geldigheid
     */
    public BrpDatum getEindeGeldigheid() {
        return eindeGeldigheid;
    }

    /**
     * Geef de waarde van registratie.
     * @return registratie
     */
    public BrpDatumTijd getRegistratie() {
        return registratie;
    }

    /**
     * Geef de waarde van verval.
     * @return verval
     */
    public BrpDatumTijd getVerval() {
        return verval;
    }

    /**
     * Geef de waarde van actie inhoud.
     * @return actie inhoud
     */
    public BrpActie getActieInhoud() {
        return actieInhoud;
    }

    /**
     * Geef de waarde van actie geldigheid.
     * @return actie geldigheid
     */
    public BrpActie getActieGeldigheid() {
        return actieGeldigheid;
    }

    /**
     * Geef de waarde van actie verval.
     * @return actie verval
     */
    public BrpActie getActieVerval() {
        return actieVerval;
    }

    /**
     * Geef de waarde van nadere aanduiding verval.
     * @return nadere aanduiding verval
     */
    public BrpCharacter getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    public void vulHuwelijk(
            final Integer sluitingParam,
            final Integer ontbindingParam,
            final Long registratieParam,
            final Long vervalParam,
            final BrpActie actieInhoudParam,
            final BrpActie actieVervalParam) {
        vulHuwelijk(sluitingParam, "1234", ontbindingParam, "1234", registratieParam, vervalParam, actieInhoudParam, actieVervalParam);
    }

    public void vulHuwelijk(
            final Integer sluiting,
            final String sluitingGemeenteCode,
            final Integer ontbinding,
            final String ontbindingGemeenteCode,
            final Long registratie,
            final Long verval,
            final BrpActie actieInhoud,
            final BrpActie actieVerval) {
        inhoud = (T) AbstractCasusTest.buildBrpRelatie(sluiting, sluitingGemeenteCode, ontbinding, ontbindingGemeenteCode, 'S');
        this.registratie = registratie == null ? null : BrpDatumTijd.fromDatumTijd(registratie, null);
        this.verval = verval == null ? null : BrpDatumTijd.fromDatumTijd(verval, null);
        this.actieInhoud = actieInhoud;
        this.actieVerval = actieVerval;
    }

    public void vulHuwelijk(
            final Integer sluiting,
            final String sluitingGemeenteCode,
            final Integer ontbinding,
            final String ontbindingGemeenteCode,
            final Character redenOntbinding,
            final Long registratie,
            final Long verval,
            final BrpActie actieInhoud,
            final BrpActie actieVerval) {
        inhoud = (T) AbstractCasusTest.buildBrpRelatie(sluiting, sluitingGemeenteCode, ontbinding, ontbindingGemeenteCode, redenOntbinding);
        this.registratie = registratie == null ? null : BrpDatumTijd.fromDatumTijd(registratie, null);
        this.verval = verval == null ? null : BrpDatumTijd.fromDatumTijd(verval, null);
        this.actieInhoud = actieInhoud;
        this.actieVerval = actieVerval;
    }

    public void vulRelatie(final Long registratieParam, final Long vervalParam, final BrpActie actieInhoudParam, final BrpActie actieVervalParam) {
        this.inhoud = (T) AbstractCasusTest.buildBrpRelatie(null, null, null, null, null);
        this.registratie = registratieParam == null ? null : BrpDatumTijd.fromDatumTijd(registratieParam, null);
        this.verval = vervalParam == null ? null : BrpDatumTijd.fromDatumTijd(vervalParam, null);
        this.actieInhoud = actieInhoudParam;
        this.actieVerval = actieVervalParam;
    }

}
