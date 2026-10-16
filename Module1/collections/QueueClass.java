package collections;

import java.util.*;

public class QueueClass {
    public static void main(String[] args) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(4);
        q.offer(2);
        q.offer(5);
        q.offer(9);
        System.out.println(q);
        System.out.println(q.poll());
        q.offer(4);
        System.out.println(q);
        System.out.println(q.peek());
//        Collections.sort(q);
        System.out.println(q);
        QueueArrayDeque e = new QueueArrayDeque();
        e.Display();
        stackdeque f=new stackdeque();
        f.push(3);
        f.push(9);
        f.pop();
        f.display();
        stackdeque g=new stackdeque();

        g.display();



    }
}
    class QueueArrayDeque{
        Queue<Integer> d=new ArrayDeque<>();
        void Display() {
            d.offer(1);
            d.offer(3);
            d.offer(4);
            d.offer(5);
            System.out.println(d);

        }



}
class stackdeque{
    ArrayDeque<Integer> w=new ArrayDeque<>();

        void push ( int x){
            w.push(x);
        }
       void pop () {
            w.pop();
        }
        void peek () {
            w.peek();
        }
    void display() {
        System.out.println(w);
    }

}

class queuepriority {
    PriorityQueue<Integer> pq = new PriorityQueue<>();

    void display() {
        pq.add(3);
        pq.add(4);
        pq.offer(6);
        //pq.clear();

        System.out.println(pq);
    }
}
