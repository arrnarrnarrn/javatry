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

/**
 * @author jflute
 * @author kawamoto
 */
public class TicketBuyResult {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final int handedMoney;
    private final int change;
    private final Ticket boughtTicket;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBuyResult(int handedMoney, int change, Ticket ticket) {
        this.handedMoney = handedMoney;
        this.change = change;
        this.boughtTicket = ticket;
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getHandedMoney() {
        return handedMoney;
    }

    public int getPaidPrice() {
        return boughtTicket.getTicketPrice();
    }

    public Ticket getTicket() {
        return boughtTicket;
    }

    public int getChange() {
        if (change < 0) { // 念のため
            throw new IllegalStateException("ありえないバグあり:" + handedMoney);
        }
        return change;
    }
}
