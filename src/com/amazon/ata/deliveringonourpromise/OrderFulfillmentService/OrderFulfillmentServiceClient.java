package com.amazon.ata.deliveringonourpromise.OrderFulfillmentService;


import com.amazon.ata.orderfulfillmentservice.OrderFulfillmentService;
import com.amazon.ata.orderfulfillmentservice.OrderPromise;

public class OrderFulfillmentServiceClient {

    private OrderFulfillmentService ofService;

    /**
     *
     * @param ofs service
     */
    public OrderFulfillmentServiceClient(OrderFulfillmentService ofs) {

        this.ofService = ofs;

    }

    /**
     *
     * @param orderId The orders id
     * @return Completed order for display
     */
    public OrderPromise getOrderPromiseByOrderItemId(String orderId) {

        OrderPromise orderPromise = ofService.getOrderPromise(orderId);

        if (orderPromise == null) {

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
