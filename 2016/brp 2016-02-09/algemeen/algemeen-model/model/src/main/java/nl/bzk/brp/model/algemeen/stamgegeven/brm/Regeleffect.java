/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.brm;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;

/**
 * De wijze waarop het systeem het resultaat van een Regel dient af te handelen, c.q. wat het effect hiervan is of dient
 * te zijn.
 *
 * Naar aanleiding van het controleren van een Regel zijn er verschillende wijzen waarop 'het systeem' de Regel kan
 * afhandelen: afkeuren van het bericht, waarschuwen etc etc.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum Regeleffect implements BestaansPeriodeStamgegeven {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy", null, null),
    /**
     * Verbieden zonder dat er sprake kan zijn van een overrule..
     */
    HARD_VERBIEDEN("Hard verbieden", "Verbieden zonder dat er sprake kan zijn van een overrule.", new DatumEvtDeelsOnbekendAttribuut(0),
            new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Verbieden, maar met mogelijkheid om te overrulen..
     */
    ZACHT_VERBIEDEN("Zacht verbieden", "Verbieden, maar met mogelijkheid om te overrulen.", new DatumEvtDeelsOnbekendAttribuut(0),
            new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Waarschuwen dat er iets speciaals aan de hand is..
     */
    WAARSCHUWEN("Waarschuwen", "Waarschuwen dat er iets speciaals aan de hand is.", new DatumEvtDeelsOnbekendAttribuut(0),
            new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * ??.
     */
    AFLEIDEN("Afleiden", "??", new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * ???.
     */
    INFORMEREN("Informeren", "???", new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231));

    private final String naam;
    private final String omschrijving;
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor Regeleffect
     * @param omschrijving Omschrijving voor Regeleffect
     * @param datumAanvangGeldigheid DatumAanvangGeldigheid voor Regeleffect
     * @param datumEindeGeldigheid DatumEindeGeldigheid voor Regeleffect
     */
    private Regeleffect(
        final String naam,
        final String omschrijving,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Retourneert Naam van Regeleffect.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Regeleffect.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Regeleffect.
     *
     * @return Datum aanvang geldigheid voor Regeleffect
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Regeleffect.
     *
     * @return Datum einde geldigheid voor Regeleffect
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
