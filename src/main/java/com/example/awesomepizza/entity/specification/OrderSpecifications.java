package com.example.awesomepizza.entity.specification;

import com.example.awesomepizza.enumerator.ORDER_STATUS;
import com.example.awesomepizza.entity.Order;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class OrderSpecifications {

    public static Specification<Order> hasStatusInSet(Set<ORDER_STATUS> statusList) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (statusList == null || statusList.isEmpty()) {
                return null;
            }
            return root.get("status").in(statusList);
        };
    }
}
