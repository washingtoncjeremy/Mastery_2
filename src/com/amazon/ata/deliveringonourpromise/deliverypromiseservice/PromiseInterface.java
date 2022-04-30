package com.amazon.ata.deliveringonourpromise.deliverypromiseservice;

import com.amazon.ata.deliveringonourpromise.types.Promise;

public interface PromiseInterface {

    public Promise getPromiseByOrderItemId(String orderId);

}
