/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonImmigratieGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonImmigratieGroepBasis;


/**
 * Vorm van historie: beiden. Motivatie: je kunt vaker immigreren. Alleen vastleggen van materi�le tijdsaspecten is dus
 * niet voldoende: je moet meerdere (niet overlappende maar wel gaten hebbende) perioden kunnen aanwijzen waarin je
 * 'geimmigreerd bent'. (Logischerwijs is de datum einde geldigheid immigratie gelijk aan de datum ingang emigratie.) In
 * de praktijk zal de datum immigratie 'dicht' bij de datum liggen waarop de immigratie geregistreerd wordt; dit kan
 * echter afwijken. Om die reden materi�le historie vastgelegd NAAST de formele historie.
 *
 * De datum ingang geldigheid komt normaliter overeen met de datum vestiging in Nederland; de laatste is (ook) opgenomen
 * omdat hier vanuit migratie verschillende waarden in kunnen staan.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractPersoonImmigratieGroepBericht extends AbstractGroepBericht implements
        PersoonImmigratieGroepBasis
{

    private String landVanwaarGevestigdCode;
    private Land   landVanwaarGevestigd;
    private Datum  datumVestigingInNederland;

    /**
     *
     *
     * @return
     */
    public String getLandVanwaarGevestigdCode() {
        return landVanwaarGevestigdCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land getLandVanwaarGevestigd() {
        return landVanwaarGevestigd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumVestigingInNederland() {
        return datumVestigingInNederland;
    }

    /**
     *
     *
     * @param landVanwaarGevestigdCode
     */
    public void setLandVanwaarGevestigdCode(final String landVanwaarGevestigdCode) {
        this.landVanwaarGevestigdCode = landVanwaarGevestigdCode;
    }

    /**
     * Zet Land vanwaar gevestigd van Immigratie.
     *
     * @param landVanwaarGevestigd Land vanwaar gevestigd.
     */
    public void setLandVanwaarGevestigd(final Land landVanwaarGevestigd) {
        this.landVanwaarGevestigd = landVanwaarGevestigd;
    }

    /**
     * Zet Datum vestiging in Nederland van Immigratie.
     *
     * @param datumVestigingInNederland Datum vestiging in Nederland.
     */
    public void setDatumVestigingInNederland(final Datum datumVestigingInNederland) {
        this.datumVestigingInNederland = datumVestigingInNederland;
    }

}
