package com.example.shared

import com.example.Filter
import com.example.ListRequest
import io.micronaut.data.repository.jpa.criteria.PredicateSpecification
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root

class PredicateSpecificationConverter {
    companion object {
        fun <E> toPredicateSpecification(request: ListRequest): PredicateSpecification<E> {
            return PredicateSpecification { root: Root<E>, criteriaBuilder: CriteriaBuilder ->
                val predicates = mutableListOf<Predicate>()

                // Process each filter in the request
                for (filter in request.filtersList)
                    predicates.add(createPredicate(root, criteriaBuilder, filter))

                // Combine all predicates into a single predicate using AND
                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }

        private fun <E> createPredicate(
            root: Root<E>,
            criteriaBuilder: CriteriaBuilder,
            filter: Filter
        ): Predicate {
            return when (filter.operator) {
                Filter.Operator.EQUALS -> criteriaBuilder.equal(root.get<Any>(filter.field), filter.value)
                Filter.Operator.NOT_EQUALS -> criteriaBuilder.notEqual(root.get<Any>(filter.field), filter.value)
                Filter.Operator.GREATER_THAN -> criteriaBuilder.greaterThan(root.get(filter.field), filter.value)
                Filter.Operator.LESS_THAN -> criteriaBuilder.lessThan(root.get(filter.field), filter.value)
                Filter.Operator.GREATER_THAN_OR_EQUALS -> criteriaBuilder.greaterThanOrEqualTo(root.get(filter.field), filter.value)
                Filter.Operator.LESS_THAN_OR_EQUALS -> criteriaBuilder.lessThanOrEqualTo(root.get(filter.field), filter.value)
                Filter.Operator.CONTAINS -> criteriaBuilder.like(root.get(filter.field), "%${filter.value}%")
                Filter.Operator.STARTS_WITH -> criteriaBuilder.like(root.get(filter.field), "${filter.value}%")
                Filter.Operator.ENDS_WITH -> criteriaBuilder.like(root.get(filter.field), "%${filter.value}")
                Filter.Operator.IN -> root.get<String>(filter.field).`in`(filter.value.split(","))
                Filter.Operator.NOT_IN -> criteriaBuilder.not(root.get<String>(filter.field).`in`(filter.value.split(",")))

                else -> throw IllegalArgumentException("Unsupported operator: ${filter.operator}")
            }
        }
    }
}
