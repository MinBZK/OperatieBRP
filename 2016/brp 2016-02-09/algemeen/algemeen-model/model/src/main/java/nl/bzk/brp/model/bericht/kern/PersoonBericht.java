/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.List;
import javax.validation.Valid;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.constraint.BRAL0211;
import nl.bzk.brp.model.validatie.constraint.CollectieMetUniekeWaarden;
import nl.bzk.brp.model.validatie.constraint.Volgnummers;


/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 * <p/>
 * Buiten de BRP wordt ook wel de term 'Natuurlijk persoon' gebruikt. In de BRP worden zowel personen waarvan de bijhouding valt onder afdeling I
 * ('Ingezetenen') van de Wet BRP, als personen waarvoor de bijhouding onder afdeling II ('Niet ingezetenen') van de Wet BRP valt, ingeschreven.
 * <p/>
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor dit objecttype de naam "Natuurlijk persoon" te gebruiken. Binnen de
 * context van BRP hebben we het bij het hanteren van de term Persoon echter nooit over niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de
 * term Persoon is verder dermate gebruikelijk binnen de context van BRP, dat ervoor gekozen is deze naam te blijven hanteren. We spreken dus over Persoon
 * en niet over "Natuurlijk persoon". 2. Voor "alternatieve realiteit" personen, en voor niet-ingeschrevenen, gebruiken we in het logisch & operationeel
 * model (maar dus NIET in de gegevensset) het construct 'persoon'. Om die reden zijn veel groepen niet verplicht, die wellicht wel verplicht zouden zijn
 * in geval van (alleen) ingeschrevenen. RvdP 27 juni 2011
 */
public final class PersoonBericht extends AbstractPersoonBericht implements Persoon, BerichtRootObject {

    private String identificerendeSleutel;

    /**
     * De database id waar de objectSleutel naar verwijst.
     */
    private Integer objectSleutelDatabaseID;

    public String getIdentificerendeSleutel() {
        return identificerendeSleutel;
    }

    public void setIdentificerendeSleutel(final String identificerendeSleutel) {
        this.identificerendeSleutel = identificerendeSleutel;
    }

    public Integer getObjectSleutelDatabaseID() {
        return objectSleutelDatabaseID;
    }

    public void setObjectSleutelDatabaseID(final Integer objectSleutelDatabaseID) {
        this.objectSleutelDatabaseID = objectSleutelDatabaseID;
    }

    @Valid
    @Override
    public PersoonIdentificatienummersGroepBericht getIdentificatienummers() {
        return super.getIdentificatienummers();
    }

    @Valid
    @Override
    public PersoonSamengesteldeNaamGroepBericht getSamengesteldeNaam() {
        return super.getSamengesteldeNaam();
    }

    @Valid
    @Override
    public PersoonGeboorteGroepBericht getGeboorte() {
        return super.getGeboorte();
    }

    @Valid
    @Override
    public PersoonNaamgebruikGroepBericht getNaamgebruik() {
        return super.getNaamgebruik();
    }

    @Valid
    @Override
    public PersoonAfgeleidAdministratiefGroepBericht getAfgeleidAdministratief() {
        return super.getAfgeleidAdministratief();
    }

    @Valid
    @Override
    public List<PersoonAdresBericht> getAdressen() {
        return super.getAdressen();
    }

    @Valid
    @Override
    public PersoonMigratieGroepBericht getMigratie() {
        return super.getMigratie();
    }

    @Valid
    @Volgnummers(code = Regel.BRAL0503)
    @BRAL0211
    @Override
    public List<PersoonVoornaamBericht> getVoornamen() {
        return super.getVoornamen();
    }

    @Valid
    @Volgnummers(code = Regel.BRAL0504)
    @Override
    public List<PersoonGeslachtsnaamcomponentBericht> getGeslachtsnaamcomponenten() {
        return super.getGeslachtsnaamcomponenten();
    }

    @Valid
    @CollectieMetUniekeWaarden(code = Regel.INC003)
    @Override
    public List<PersoonNationaliteitBericht> getNationaliteiten() {
        return super.getNationaliteiten();
    }

    /**
     * Controleert of persoon de Nederlandse nationaliteit bezit. LET OP: Dit checkt alleen of de Nederlandse nationaliteit in het bericht voorkomt. Voor
     * een algemene persoon check moet je een object uit de DB hebben!
     *
     * @return true als persoon Nederlandse nationaliteit bezit
     */
    @Override
    public boolean heeftNederlandseNationaliteit() {
        boolean heeftNederlandseNationaliteit = false;

        if (this.getNationaliteiten() != null) {
            for (final PersoonNationaliteitBericht nationaliteit : this.getNationaliteiten()) {
                if (nationaliteit.getNationaliteitCode().equals(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE_STRING)) {
                    heeftNederlandseNationaliteit = true;
                }
            }
        }

        return heeftNederlandseNationaliteit;
    }

    @Override
    public boolean isIngezetene() {
        return this.getBijhouding() != null && this.getBijhouding().getBijhoudingsaard() != null
            && this.getBijhouding().getBijhoudingsaard().getWaarde() == Bijhoudingsaard.INGEZETENE;
    }

    @Override
    public boolean isPersoonGelijkAan(final Persoon persoon) {
        final boolean resultaat;
        if (!(persoon instanceof PersoonBericht)) {
            resultaat = false;
        } else {
            final PersoonBericht persoonBericht = (PersoonBericht) persoon;
            resultaat = persoonBericht.getIdentificerendeSleutel().equals(this.getIdentificerendeSleutel());
        }

        return resultaat;
    }
}
