package ua.edu.sumdu.j2se.zozulia.tasks;

/**
 * Class LinkedTaskList - main purpose is to store default or
 * a repeatable task`s
 *
 * @version 1.20 06 Nov 2021
 * @author Denis Zozulia
 */
public class LinkedTaskList {

    /* Set up element structure*/
    private class Node {
        Node next;
        Node prev; //for a next time use
        Task data;
    }

        private Node head;
        private Node tail;
        private int size = 0;

    /* Add`s @param tasks to temp array and then clones into main array*/
    public void add(Task task) {
            Node a = new Node();
            a.data = task;

            if(head == null) { head = tail = a;}
            else {
                a.prev = head;
                head.next = a;
                head = a;
            }
            size++;
    }

    public boolean remove(Task task) {
            if(head == null)
                return false;

            if (head == tail) {
                head = tail = null;
                size--;
                return true;
            }

            if (tail.data == task) {
                tail = tail.next;
                tail.prev = null;
                size--;
                return true;
            }

            Node temp = tail;
            while (temp.next != null) {
                if (temp.next.data == task) {
                    if(head == temp.next)
                    {
                        head = temp;
                        head.next = null;
                        size--;
                        return true;
                    }
                    temp.next.next.prev = temp;
                    temp.next = temp.next.next;
                    size--;
                    return true;
                }
                temp = temp.next;
            }
            return false;
    }


    public int size(){ return size;}

    public Task getTask(int index) throws IndexOutOfBoundsException{
        if(index > size){ throw new IndexOutOfBoundsException("Index can`temp be bigger than" +
                " maximum amount of elements in array"); }

        Node temp = tail;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.data;
    }

    /*
     * Check when task`s from array will be repeated from @param current-to, or will it repeat at all
     * @return LinkedTaskList containing tasks that will be repeated in that time
     */
    public LinkedTaskList incoming(int from, int to) throws Exception{

        LinkedTaskList tempArrayList = new LinkedTaskList();
        Node temp = tail;
        for (int i = 0; i < size; i++) {
            if (temp.data != null) {
                int startTime = temp.data.nextTimeAfter(from);
                if (startTime != -1 && startTime <= to) {
                    tempArrayList.add(temp.data);
                }
                temp = temp.next;
            }
        }
        return tempArrayList;
    }

}
