select g.onderzoek,
       g.element,
       e.naam
from   kern.gegeveninonderzoek g
join   kern.element            e
on     g.element=e.id
order  by element