package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.App;
import com.amazon.ata.deliveringonourpromise.OrderFulfillmentService.OrderFulfillmentServiceClient;
import com.amazon.ata.deliveringonourpromise.deliverypromiseservice.DeliveryPromiseServiceClient;
import com.amazon.ata.deliveringonourpromise.deliverypromiseservice.PromiseInterface;
import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.ordermanipulationauthority.OrderResult;
import com.amazon.ata.ordermanipulationauthority.OrderResultItem;
import com.amazon.ata.ordermanipulationauthority.OrderShipment;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for Promises.
 */
public class PromiseDao implements ReadOnlyDao<String, List<Promise>> {

    private DeliveryPromiseServiceClient dpsClient;
    private OrderFulfillmentServiceClient ofsClient;
    private OrderManipulationAuthorityClient omaClient;

    private List<OrderFulfillmentServiceClient> promiseClients;
    //private List<Promise> deliveryPromises;
    //private List<Promise> orderPromises;

    /**
     * PromiseDao constructor, accepting service clients for DPS and OMA.
     * @param dpsClient DeliveryPromiseServiceClient for DAO to access DPS
     *
     */
    public PromiseDao(DeliveryPromiseServiceClient dpsClient, OrderManipulationAuthorityClient omaClientList, OrderFulfillmentServiceClient ofsClient) {

        this.ofsClient = ofsClient;
        this.dpsClient = dpsClient;
        this.omaClient = omaClientList;

    }

    public PromiseDao(List<OrderFulfillmentServiceClient> promiseClients) {

        this.promiseClients = promiseClients;
        this.omaClient = App.getOrderManipulationAuthorityClient();

    }


    /**
     * Returns a list of all Promises associated with the given order item ID.
     * @param customerOrderItemId the order item ID to fetch promise for
     * @return a List of promises for the given order item ID
     */
    @Override
    public List<Promise> get(String customerOrderItemId) {
        // Fetch the delivery date, so we can add to any promises that we find
        ZonedDateTime itemDeliveryDate = getDeliveryDateForOrderItem(customerOrderItemId);

        List<Promise> promises = new ArrayList<>();

        // fetch Promise from Delivery Promise Service. If exists, add to list of Promises to return.
        // Set delivery date

        if(null != promiseClients){

            for(int i = 0; i < promiseClients.size(); i++){

                Promise newPromise = promiseClients.get(i).getPromiseByOrderItemId(customerOrderItemId);
                if(newPromise != null){

                    newPromise.setDeliveryDate(itemDeliveryDate);
                    promises.add(newPromise);

                }

            }

        }



        //Promise dpsPromise = dpsClient.getPromiseByOrderItemId(customerOrderItemId);
        //Promise ofsPromise = ofsClient.getPromiseByOrderItemId(customerOrderItemId);

        /*if (dpsPromise != null) {

            dpsPromise.setDeliveryDate(itemDeliveryDate);

            promises.add(dpsPromise);
        }

        if (ofsPromise != null){

            ofsPromise.setDeliveryDate(itemDeliveryDate);

            promises.add(ofsPromise);

        }*/

        return promises;
    }

    /*
     * Fetches the delivery date of the shipment containing the order item specified by the given order item ID,
     * if there is one.
     *
     * If the order item ID doesn't correspond to a valid order item, or if the shipment hasn't been delivered
     * yet, return null.
     */
    private ZonedDateTime getDeliveryDateForOrderItem(String customerOrderItemId) {
        OrderResultItem orderResultItem = omaClient.getCustomerOrderItemByOrderItemId(customerOrderItemId);

        if (null == orderResultItem) {
            return null;
        }

        OrderResult orderResult = omaClient.getCustomerOrderByOrderId(orderResultItem.getOrderId());

        for (OrderShipment shipment : orderResult.getOrderShipmentList()) {
            for (OrderShipment.ShipmentItem shipmentItem : shipment.getCustomerShipmentItems()) {
                if (shipmentItem.getCustomerOrderItemId().equals(customerOrderItemId)) {
                    return shipment.getDeliveryDate();
                }
            }
        }

        // didn't find a delivery date!
        return null;
    }
}
