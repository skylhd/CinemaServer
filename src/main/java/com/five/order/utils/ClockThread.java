package com.five.order.utils;

import com.five.order.model.Reservation;
import com.five.order.service.OrderService;

/**
 * Created by haoye on 17-6-6.
 */
public class ClockThread implements Runnable{

    private Reservation order;
    private OrderService orderService;

    private int pos;

    public void init(Reservation order, OrderService orderService) {
        this.order = order;
        this.orderService = orderService;
        pos = -1;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public void run() {
        try {
            ClockThreadPool clockThreadPool = ClockThreadPool.getInstance();
            clockThreadPool.put(order.getId(), this);
            Thread.sleep(1000 * 60 * 10);
            Reservation newOrder = orderService.findById(order.getId());
            if (newOrder.getStatus() == Reservation.NOTPAID) {
                orderService.orderOutOfDate(newOrder);
            }
            clockThreadPool.remove(order.getId());
            clockThreadPool.destoryThread(pos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
