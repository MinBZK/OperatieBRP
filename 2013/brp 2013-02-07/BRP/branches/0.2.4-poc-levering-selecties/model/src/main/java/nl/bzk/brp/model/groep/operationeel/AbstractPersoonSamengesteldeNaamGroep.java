/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.Scheidingsteken;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonSamengesteldeNaamGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonSamengesteldeNaamGroep extends AbstractGroep implements
        PersoonSamengesteldeNaamGroepBasis
{

    @ManyToOne
    @JoinColumn(name = "predikaat")
    @Fetch(FetchMode.JOIN)
    private Predikaat              predikaat;

    @ManyToOne
    @JoinColumn(name = "adellijkeTitel")
    @Fetch(FetchMode.JOIN)
    private AdellijkeTitel         adellijkeTitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "voornamen"))
    private Voornaam               voornamen;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "voorvoegsel"))
    private Voorvoegsel            voorvoegsel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "scheidingsteken"))
    private Scheidingsteken scheidingsteken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "geslnaam"))
    private Geslachtsnaamcomponent geslachtsnaam;

    @Column(name = "indnreeksalsgeslnaam")
    @Type(type = "JaNee")
    private JaNee                  indNamenreeksAlsGeslachtsNaam;

    @Column(name = "indalgoritmischafgeleid")
    @Type(type = "JaNee")
    private JaNee                  indAlgorithmischAfgeleid;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonSamengesteldeNaamGroep() {

    }

    /**
     * .
     *
     * @param persoonSamengesteldeNaamGroepBasis PersoonSamengesteldeNaamGroepBasis
     */
    protected AbstractPersoonSamengesteldeNaamGroep(
            final PersoonSamengesteldeNaamGroepBasis persoonSamengesteldeNaamGroepBasis)
    {
        predikaat = persoonSamengesteldeNaamGroepBasis.getPredikaat();
        adellijkeTitel = persoonSamengesteldeNaamGroepBasis.getAdellijkeTitel();
        voornamen = persoonSamengesteldeNaamGroepBasis.getVoornamen();
        voorvoegsel = persoonSamengesteldeNaamGroepBasis.getVoorvoegsel();
        scheidingsteken = persoonSamengesteldeNaamGroepBasis.getScheidingsteken();
        geslachtsnaam = persoonSamengesteldeNaamGroepBasis.getGeslachtsnaam();
        indNamenreeksAlsGeslachtsNaam = persoonSamengesteldeNaamGroepBasis.getIndNamenreeksAlsGeslachtsNaam();
        indAlgorithmischAfgeleid = persoonSamengesteldeNaamGroepBasis.getIndAlgorithmischAfgeleid();
    }

    @Override
    public Predikaat getPredikaat() {
        return predikaat;
    }

    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    @Override
    public Voornaam getVoornamen() {
        return voornamen;
    }

    @Override
    public Voorvoegsel getVoorvoegsel() {
        return voorvoegsel;
    }

    @Override
    public Scheidingsteken getScheidingsteken() {
        return scheidingsteken;
    }

    @Override
    public Geslachtsnaamcomponent getGeslachtsnaam() {
        return geslachtsnaam;
    }

    @Override
    public JaNee getIndNamenreeksAlsGeslachtsNaam() {
        return indNamenreeksAlsGeslachtsNaam;
    }

    @Override
    public JaNee getIndAlgorithmischAfgeleid() {
        return indAlgorithmischAfgeleid;
    }
}
