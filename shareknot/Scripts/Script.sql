    select
        distinct party0_.id as id1_7_0_,
        tag2_.id as id1_14_1_,
        zone4_.id as id1_15_2_,
        party0_.closed as closed2_7_0_,
        party0_.closed_date_time as closed_d3_7_0_,
        party0_.full_description as full_des4_7_0_,
        party0_.image as image5_7_0_,
        party0_.member_count as member_c6_7_0_,
        party0_.path as path7_7_0_,
        party0_.published as publishe8_7_0_,
        party0_.published_date_time as publishe9_7_0_,
        party0_.recruiting as recruit10_7_0_,
        party0_.recruiting_updated_date_time as recruit11_7_0_,
        party0_.short_description as short_d12_7_0_,
        party0_.title as title13_7_0_,
        party0_.use_banner as use_ban14_7_0_,
        tag2_.title as title2_14_1_,
        tags1_.party_id as party_id1_10_0__,
        tags1_.tags_id as tags_id2_10_0__,
        zone4_.city as city2_15_2_,
        zone4_.country as country3_15_2_,
        zone4_.local_name_of_city as local_na4_15_2_,
        zone4_.province as province5_15_2_,
        zones3_.party_id as party_id1_11_1__,
        zones3_.zones_id as zones_id2_11_1__ 
    from
        party party0_ 
    left outer join
        party_tags tags1_ 
            on party0_.id=tags1_.party_id 
    left outer join
        tag tag2_ 
            on tags1_.tags_id=tag2_.id 
    left outer join
        party_zones zones3_ 
            on party0_.id=zones3_.party_id 
    left outer join
        zone zone4_ 
            on zones3_.zones_id=zone4_.id 
    where
        party0_.published=true 
        and party0_.closed=false 
        and (
            lower(party0_.title) like '%test%' escape '!'
        ) 
        or exists (
            select
                1 
            from
                party_tags tags5_,
                tag tag6_ 
            where
                party0_.id=tags5_.party_id 
                and tags5_.tags_id=tag6_.id 
                and (
                    lower(tag6_.title) like '%test%' escape '!'
                )
        ) 
        or exists (
            select
                1 
            from
                party_zones zones7_,
                zone zone8_ 
            where
                party0_.id=zones7_.party_id 
                and zones7_.zones_id=zone8_.id 
                and (
                    lower(zone8_.local_name_of_city) like '%test%' escape '!'
                )
        ) 
    order by
        party0_.published_date_time desc