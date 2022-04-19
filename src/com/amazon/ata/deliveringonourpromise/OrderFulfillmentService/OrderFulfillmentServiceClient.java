package com.amazon.ata.deliveringonourpromise.OrderFulfillmentService;

import com.amazon.ata.deliverypromiseservice.service.DeliveryPromise;
import com.amazon.ata.orderfulfillmentservice.OrderFulfillmentService;
import com.amazon.ata.orderfulfillmentservice.OrderPromise;

public class OrderFulfillmentServiceClient {

    private OrderFulfillmentService ofService;

    public OrderFulfillmentServiceClient(OrderFulfillmentService ofs){

        this.ofService = ofs;

    }

    public OrderPromise getOrderPromiseByOrderItemId(String orderId){

        OrderPromise orderPromise = ofService.getOrderPromise(orderId);

        if(orderPromise == null) {

            return null;

        }

        return OrderPromise.builder().withPromiseLatestArrivalDate(orderPromise.getPromiseLatestArrivalDate())
                .withCustomerOrderItemId(orderPromise.getCustomerOrderItemId())
                .withPromiseLatestShipDate(orderPromise.getPromiseLatestShipDate())
                .withPromiseEffectiveDate(orderPromise.getPromiseEffectiveDate())
                .withIsActive(orderPromise.isActive())
                .withPromiseProvidedBy(orderPromise.getPromiseProvidedBy())
                .withAsin(orderPromise.getAsin())
                .build();

    }


}
