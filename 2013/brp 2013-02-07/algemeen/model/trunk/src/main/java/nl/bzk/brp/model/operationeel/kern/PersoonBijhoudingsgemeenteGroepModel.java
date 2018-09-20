/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonBijhoudingsgemeenteGroepModel;


/**
 * Vorm van historie: beiden.
 * Motivatie voor de materi�le tijdslijn: de bijhoudingsgemeente kan op een eerder moment dan technisch verwerkt de
 * verantwoordelijke gemeente zijn (geworden). Of te wel: formele tijdslijn kan anders liggen dan materi�le tijdslijn.
 * Voor het OOK bestaan van datum inschrijving: zie modelleringsbeslissing aldaar. RvdP 10 jan 2012.
 *
 *
 *
 */
@Embeddable
public class PersoonBijhoudingsgemeenteGroepModel extends AbstractPersoonBijhoudingsgemeenteGroepModel implements
    PersoonBijhoudingsgemeenteGroep
{

    /** Standaard constructor (t.b.v. Hibernate/JPA). */
    protected PersoonBijhoudingsgemeenteGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param bijhoudingsgemeente bijhoudingsgemeente van Bijhoudingsgemeente.
     * @param datumInschrijvingInGemeente datumInschrijvingInGemeente van Bijhoudingsgemeente.
     * @param indicatieOnverwerktDocumentAanwezig indicatieOnverwerktDocumentAanwezig van Bijhoudingsgemeente.
     */
    public PersoonBijhoudingsgemeenteGroepModel(final Partij bijhoudingsgemeente,
        final Datum datumInschrijvingInGemeente, final JaNee indicatieOnverwerktDocumentAanwezig)
    {
        super(bijhoudingsgemeente, datumInschrijvingInGemeente, indicatieOnverwerktDocumentAanwezig);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonBijhoudingsgemeenteGroep te kopieren groep.
     */
    public PersoonBijhoudingsgemeenteGroepModel(final PersoonBijhoudingsgemeenteGroep persoonBijhoudingsgemeenteGroep) {
        super(persoonBijhoudingsgemeenteGroep);
    }

}
