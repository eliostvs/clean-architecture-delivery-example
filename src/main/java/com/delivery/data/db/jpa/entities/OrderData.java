package com.delivery.data.db.jpa.entities;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Order;
import com.delivery.core.domain.OrderItem;
import com.delivery.core.domain.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.delivery.data.db.jpa.entities.IdConverter.convertId;

@Entity(name = "order")
@EqualsAndHashCode(of = {"customer", "store", "total", "status", "createdAt", "updatedAt"})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "orders")
@ToString(of = {"customer", "store", "total", "status", "createdAt", "updatedAt"})
public class OrderData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerData customer;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreData store;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "order",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true
    )
    private Set<OrderItemData> orderItems;

    @Column(nullable = false)
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // TODO: test
    public void addOrderItem(OrderItemData orderItem) {
        if (this.orderItems == null) {
            this.orderItems = new HashSet<>();
        }

        orderItem.setOrder(this);
        this.orderItems.add(orderItem);

        this.calculateTotal();
    }

    // TODO: test
    public Order fromThis() {
        return new Order(
                new Identity(id),
                status,
                customer.fromThis(),
                store.fromThis(),
                fromOrderItemData(),
                total,
                createdAt,
                updatedAt
        );
    }

    private List<OrderItem> fromOrderItemData() {
        return orderItems
                .stream()
                .map(OrderItemData::fromThis)
                .collect(Collectors.toList());
    }

    // TODO: test
    public static OrderData newInstance(CustomerData customer,
                                        StoreData store,
                                        Set<OrderItemData> orderItems) {
        OrderData order = new OrderData(
                null,
                customer,
                store,
                null,
                0d,
                Status.OPEN,
                Instant.now(),
                Instant.now()
        );

        orderItems.forEach(order::addOrderItem);

        return order;
    }

    private void calculateTotal() {
        this.total = this.orderItems
                .stream()
                .mapToDouble(OrderItemData::getTotal)
                .sum();
    }

    // TODO: test
    public static OrderData from(Order order) {
        OrderData orderData = new OrderData(
                convertId(order.getId()),
                CustomerData.from(order.getCustomer()),
                StoreData.from(order.getStore()),
                new HashSet<>(),
                order.getTotal(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );

        OrderItemData
                .from(order.getOrderItems())
                .forEach(orderData::addOrderItem);

        return orderData;
    }
}
