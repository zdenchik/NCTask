package ua.edu.sumdu.j2se.zozulia.tasks;

/**
 * Class LinkedTaskList - main purpose is to store default or
 * a repeatable task`s
 *
 * @version 1.20 06 Nov 2021
 * @author Denis Zozulia
 */
public class LinkedTaskList extends AbstractTaskList{

    /* Set up element structure*/
    private class Node {
        Node next;
        Node prev; //for a next time use
        Task data;
    }

        private Node head;
        private Node tail;
        private int lastElement = 0;

    /* Add`s @param tasks to Object*/
    public void add(Task task) throws Exception{
        if(task == null){throw new Exception("Task can`t be null");}

        Node a = new Node();
            a.data = task;

            if(head == null) { head = tail = a;}
            else {
                a.prev = head;
                head.next = a;
                head = a;
            }
            lastElement++;
    }

    /* Remove`s @param task from Object and @return true if task existed or false if not*/
    public boolean remove(Task task) {
            if(head == null)
                return false;

            if (head == tail) {
                head = tail = null;
                lastElement--;
                return true;
            }

            if (tail.data == task) {
                tail = tail.next;
                tail.prev = null;
                lastElement--;
                return true;
            }

            Node temp = tail;
            while (temp.next != null) {
                if (temp.next.data == task) {
                    if(head == temp.next)
                    {
                        head = temp;
                        head.next = null;
                        lastElement--;
                        return true;
                    }
                    temp.next.next.prev = temp;
                    temp.next = temp.next.next;
                    lastElement--;
                    return true;
                }
                temp = temp.next;
            }
            return false;
    }


    public int size(){ return lastElement;}

    public Task getTask(int index) throws IndexOutOfBoundsException{
        if(index > lastElement){ throw new IndexOutOfBoundsException("Index can`temp be bigger than" +
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
    public LinkedTaskList incoming(int from, int to)  throws Exception{
        if (from < 0 || to < 0){throw new IndexOutOfBoundsException();}

        LinkedTaskList tempArrayList = new LinkedTaskList();
        for (int i = 0; i < lastElement; i++) {
            int temp = getTask(i).nextTimeAfter(from);
            if(temp != -1 && temp <= to){
                if (getTask(i) != null) {
                    tempArrayList.add(getTask(i));
                }
            }
        }
        return tempArrayList;
    }

}
