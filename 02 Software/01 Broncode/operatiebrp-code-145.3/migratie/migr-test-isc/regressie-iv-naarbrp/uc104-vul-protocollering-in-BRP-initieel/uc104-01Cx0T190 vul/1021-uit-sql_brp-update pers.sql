UPDATE kern.pers 
SET naderebijhaard=(SELECT id FROM kern.naderebijhaard WHERE code='O')
WHERE anr='4869635617';