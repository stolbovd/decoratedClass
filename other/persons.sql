SELECT person0_.adress_id AS col_0_0_, person0_.id AS col_1_0_, person0_.name AS col_2_0_, adress1_.id AS id1_0_, adress1_.city AS city2_0_, adress1_.street AS street3_0_
FROM person person0_ LEFT OUTER JOIN adress adress1_ ON person0_.adress_id=adress1_.id
WHERE adress1_.id=?
