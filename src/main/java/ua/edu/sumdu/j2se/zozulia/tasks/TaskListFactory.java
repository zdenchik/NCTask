package ua.edu.sumdu.j2se.zozulia.tasks;

/**
 *  TaskListFactory creates Task containing Object depending on type that needed
 */
public class TaskListFactory {

    public static AbstractTaskList createTaskList(ListTypes.types type){

        AbstractTaskList answer;

        switch (type){
            case ARRAY:
                answer = new ArrayTaskList();
                break;
            case LINKED:
                answer = new LinkedTaskList();
                break;
            default:
                answer = null;
        }

        return answer;
    }
}
