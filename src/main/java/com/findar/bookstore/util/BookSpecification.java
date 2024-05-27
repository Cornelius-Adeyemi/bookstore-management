package com.findar.bookstore.util;

import com.findar.bookstore.model.entity.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



@Slf4j
@RequiredArgsConstructor
public class BookSpecification implements Specification<Book> {

    private final String keyword;

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        String likePattern = "%" + keyword.toLowerCase() + "%";

        predicates.add(criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likePattern),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), likePattern)

        ));


       return criteriaBuilder.and(predicates.toArray(new Predicate[0]));



    }


}
