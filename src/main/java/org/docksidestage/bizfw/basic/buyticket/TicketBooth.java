/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

import org.docksidestage.bizfw.basic.buyticket.Ticket.TicketType;

/**
 * @author jflute
 * @auther kawamoto
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int ONE_DAY_MAX_QUANTITY = 10;
    private static final int TWO_DAY_MAX_QUANTITY = 10;
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========

    private Integer salesProceeds;

    public final Quantity oneDayQuantity = new Quantity(ONE_DAY_MAX_QUANTITY);
    public final Quantity twoDayQuantity = new Quantity(TWO_DAY_MAX_QUANTITY);

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========

    public TicketBooth() {
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========

    public Ticket buyOneDayPassport(int handedMoney) {
        Ticket ticket = new Ticket(handedMoney, TicketType.ONEDAY);
        doBuyPassportFlow(oneDayQuantity, handedMoney, ONE_DAY_PRICE);
        return ticket;
    }

    public TicketBuyResult buyTwoDayPassport(int handedMoney) {
        int change = handedMoney - TWO_DAY_PRICE;
        Ticket ticket = new Ticket(TWO_DAY_PRICE, TicketType.TWODAY);
        doBuyPassportFlow(twoDayQuantity, handedMoney, TWO_DAY_PRICE);
        return new TicketBuyResult(handedMoney, TWO_DAY_PRICE, change, ticket);
    }

    private void doBuyPassportFlow(Quantity quantity, int handedMoney, int payment) {
        assertTicketInStock(quantity.getValue());
        assertTicketEnoughMoney(handedMoney, payment);
        calculateSalesProceeds(payment);
        quantity.decrement();
    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    private void assertTicketInStock(int quantity) {
        if (quantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
    }

    //assert＋正常系
    private void assertTicketEnoughMoney(int handedMoney, int salesPrice) {
        if (handedMoney < salesPrice) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
    }

    private void calculateSalesProceeds(int price) {
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + price;
        } else {
            salesProceeds = price;
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                             

    public Integer getSalesProceeds() {
        return salesProceeds;
    }

    public Quantity getOneDayQuantity() {
        return oneDayQuantity;
    }
}
