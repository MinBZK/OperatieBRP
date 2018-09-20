/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.casus;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;

/**
 * Test class om makkelijk de inhoud van de test casus op te nemen
 *
 * @param <T>
 */
public class BrpTestObject<T extends BrpGroepInhoud> {

    private T inhoud;
    private BrpDatum aanvangGeldigheid;
    private BrpDatum eindeGeldigheid;
    private BrpDatumTijd registratie;
    private BrpDatumTijd verval;
    private BrpActie actieInhoud;
    private BrpActie actieGeldigheid;
    private BrpActie actieVerval;

    // CHECKSTYLE:OFF - meer dan 7 parameters
    protected void vul(
            final T inhoud,
            final Integer aanvangGeldigheid,
            final Integer eindeGeldigheid,
            final Long registratie,
            final Long verval,
            final BrpActie actieInhoud,
            final BrpActie actieGeldigheid,
            final BrpActie actieVerval) {
        // CHECKSTYLE:ON
        this.inhoud = inhoud;
        this.aanvangGeldigheid = aanvangGeldigheid == null ? null : new BrpDatum(aanvangGeldigheid);
        this.eindeGeldigheid = eindeGeldigheid == null ? null : new BrpDatum(eindeGeldigheid);
        this.registratie = registratie == null ? null : BrpDatumTijd.fromDatumTijd(registratie);
        this.verval = verval == null ? null : BrpDatumTijd.fromDatumTijd(verval);
        this.actieInhoud = actieInhoud;
        this.actieGeldigheid = actieGeldigheid;
        this.actieVerval = actieVerval;
    }

    public T getInhoud() {
        return inhoud;
    }

    public BrpDatum getAanvangGeldigheid() {
        return aanvangGeldigheid;
    }

    public BrpDatum getEindeGeldigheid() {
        return eindeGeldigheid;
    }

    public BrpDatumTijd getRegistratie() {
        return registratie;
    }

    public BrpDatumTijd getVerval() {
        return verval;
    }

    public BrpActie getActieInhoud() {
        return actieInhoud;
    }

    public BrpActie getActieGeldigheid() {
        return actieGeldigheid;
    }

    public BrpActie getActieVerval() {
        return actieVerval;
    }

    public void vulHuwelijk(
            final Integer sluiting,
            final Integer ontbinding,
            final Long registratie,
            final Long verval,
            final BrpActie actieInhoud,
            final BrpActie actieVerval) {
        vulHuwelijk(sluiting, "1234", ontbinding, "1234", registratie, verval, actieInhoud, actieVerval);
    }

    // CHECKSTYLE:OFF - meer dan 7 parameters
    @SuppressWarnings("unchecked")
    public void vulHuwelijk(
            final Integer sluiting,
            final String sluitingGemeenteCode,
            final Integer ontbinding,
            final String ontbindingGemeenteCode,
            final Long registratie,
            final Long verval,
            final BrpActie actieInhoud,
            final BrpActie actieVerval) {
        // CHECKSTYLE:On
        this.inhoud =
                (T) CasusTest
                        .buildBrpRelatie(sluiting, sluitingGemeenteCode, ontbinding, ontbindingGemeenteCode, "S");
        this.registratie = registratie == null ? null : BrpDatumTijd.fromDatumTijd(registratie);
        this.verval = verval == null ? null : BrpDatumTijd.fromDatumTijd(verval);
        this.actieInhoud = actieInhoud;
        this.actieVerval = actieVerval;
    }

    // CHECKSTYLE:OFF - meer dan 7 parameters
    @SuppressWarnings("unchecked")
    public void vulHuwelijk(
            final Integer sluiting,
            final String sluitingGemeenteCode,
            final Integer ontbinding,
            final String ontbindingGemeenteCode,
            final String redenOntbinding,
            final Long registratie,
            final Long verval,
            final BrpActie actieInhoud,
            final BrpActie actieVerval) {
        // CHECKSTYLE:On
        this.inhoud =
                (T) CasusTest.buildBrpRelatie(sluiting, sluitingGemeenteCode, ontbinding, ontbindingGemeenteCode,
                        redenOntbinding);
        this.registratie = registratie == null ? null : BrpDatumTijd.fromDatumTijd(registratie);
        this.verval = verval == null ? null : BrpDatumTijd.fromDatumTijd(verval);
        this.actieInhoud = actieInhoud;
        this.actieVerval = actieVerval;
    }
}
