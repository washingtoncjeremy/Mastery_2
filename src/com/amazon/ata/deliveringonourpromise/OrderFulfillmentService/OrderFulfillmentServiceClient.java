package com.amazon.ata.deliveringonourpromise.OrderFulfillmentService;


import com.amazon.ata.deliveringonourpromise.deliverypromiseservice.DeliveryPromiseServiceClient;
import com.amazon.ata.deliveringonourpromise.deliverypromiseservice.PromiseInterface;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.orderfulfillmentservice.OrderFulfillmentService;
import com.amazon.ata.orderfulfillmentservice.OrderPromise;

public class OrderFulfillmentServiceClient implements PromiseInterface  {

    private OrderFulfillmentService ofService;

    public OrderFulfillmentServiceClient(){


    }

    /**
     *
     * @param ofs service
     *
     *
     */
    public OrderFulfillmentServiceClient(OrderFulfillmentService ofs) {

        this.ofService = ofs;

    }

    /**
     *
     * @param orderId The orders id
     * @return Completed order for display
     */
    public Promise getPromiseByOrderItemId(String orderId) {

        OrderPromise orderPromise = ofService.getOrderPromise(orderId);

        if (orderPromise == null) {

            return null;


        }

        return Promise.builder().withPromiseLatestArrivalDate(orderPromise.getPromiseLatestArrivalDate())
                .withCustomerOrderItemId(orderPromise.getCustomerOrderItemId())
                .withPromiseLatestShipDate(orderPromise.getPromiseLatestShipDate())
                .withPromiseEffectiveDate(orderPromise.getPromiseEffectiveDate())
                .withIsActive(orderPromise.isActive())
                .withPromiseProvidedBy(orderPromise.getPromiseProvidedBy())
                .withAsin(orderPromise.getAsin())
                .build();

    }


}
