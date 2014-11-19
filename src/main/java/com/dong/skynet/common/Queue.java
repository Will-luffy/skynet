package com.dong.skynet.common;

import java.util.LinkedList;

/**
 * Created by dongshuwang on 14-11-19.
 */
public class Queue {
    private LinkedList queue = new LinkedList();

    public void enQueue(Object t) {
        queue.addLast(t);
    }

    public Object deQueue() {
        return queue.removeFirst();
    }

    public boolean contains(Object t) {
        return queue.contains(t);
    }

    public boolean isQueueEmpty() {
        return queue.isEmpty();
    }

    public void empty() {
        queue.clear();
    }
}
