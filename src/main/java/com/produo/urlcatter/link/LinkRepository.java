package com.produo.urlcatter.link;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, Integer> {

    LinkEntity findByCode(String code);

    LinkEntity findByOriginal(String original);
}
