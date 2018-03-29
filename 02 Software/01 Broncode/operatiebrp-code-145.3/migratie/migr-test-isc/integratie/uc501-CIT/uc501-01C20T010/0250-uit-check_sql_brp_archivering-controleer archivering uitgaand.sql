select count(*) as uitgaand from ber.ber where srt = 109 and richting = 2 and zendendepartij = $$partijid$$ and ontvangendepartij = 630;
