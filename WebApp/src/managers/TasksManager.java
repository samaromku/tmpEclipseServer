package managers;

import entities.Task;
import entities.User;
import network.TaskEnum;
import storage.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import database.KeepConnectionDB;

public class TasksManager {
    private Task task;
    private List<Task> tasks;
    private List<Task> doneTasks;
    private List<Task> todayDoneTasks;
    private List<Task> usersTasksList;
    private Task removeTask;
    private String status;
    private String[] importanceString = new String[]{TaskEnum.STANDART, TaskEnum.INFO, TaskEnum.AVARY, TaskEnum.TIME};
    private String[] type = new String[]{"к сведению", "приемка", "УУИТЭ", "ИТП", "АРТФ"};
    private String[] statusesForCreate = new String[]{TaskEnum.NEW_TASK, TaskEnum.DISTRIBUTED_TASK};
    private String[] AllStatuses = new String[]{TaskEnum.NEW_TASK, TaskEnum.DISTRIBUTED_TASK, TaskEnum.DOING_TASK, TaskEnum.CONTROL_TASK, TaskEnum.DONE_TASK, TaskEnum.NEED_HELP};
    public static final TasksManager INSTANCE = new TasksManager();
    DateUtil dateUtil = new DateUtil();
    KeepConnectionDB connectionDB = KeepConnectionDB.instance;

    public List<Task> getDoneTasks() {
    	List<Task>doneTasksFromAll = new CopyOnWriteArrayList<>();
    	for(Task t:tasks){
    		if(t.getStatus().equals(TaskEnum.DONE_TASK)){
    			doneTasksFromAll.add(t);
    		}
    	}
		return doneTasksFromAll;
	}
    
    public List<Task>getUserTasks(User user){
    	List<Task>usersTAsks = new ArrayList<>();
    	for(Task t:tasksNotDone()){
    		if(t.getUserId()==user.getId()){
    			usersTAsks.add(t);
    		}
    	}
    	return usersTAsks;
    }
    
    public List<Task> getNotDoneTasks() {  	
    	List<Task>notDoneTasksFromAll = new CopyOnWriteArrayList<>();
    	for(Task t: getTasks()){
    		if(!t.getStatus().equals(TaskEnum.DONE_TASK)){
    			notDoneTasksFromAll.add(t);
    		}
    	}
		return notDoneTasksFromAll;
	}
    
    public void compareTasks(){
    	Comparator<Task> comp = Comparator
    			.comparing(Task::getStatus)
    			.thenComparing(Task::getDoneTime);
    	Collections.sort(tasks, comp);
    }

	public void setDoneTasks(List<Task> doneTasks) {
		this.doneTasks = doneTasks;
	}
	
	public List<Task> getTodayDoneTasks() {
		return todayDoneTasks;
	}

	public void setTodayDoneTasks(List<Task> todayDoneTasks) {
		this.todayDoneTasks = todayDoneTasks;
	}

	public Task getRemoveTask() {
        return removeTask;
    }

    public void setRemoveTask(Task removeTask) {
        this.removeTask = removeTask;
    }

    public String[] getAllStatuses() {
        return AllStatuses;
    }

    public String[] getStatusesForCreate() {
        return statusesForCreate;
    }

    public String[] getType() {
        return type;
    }

    public String[] getImportanceString() {
        return importanceString;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Task getTask() {
        return task;
    }

    public List<Task> getTasks() {
    	compareTasks(); 
        return tasks;
    }
    
    public void addToUsersTaskList(User user){
    	removeUsersTasks();
    	for(Task t:tasks){
    		if(t.getUserId()==user.getId()){
    			usersTasksList.add(t);
    		}
    	}
    }
    
    public void addToUsersTasks(Task task){
    	usersTasksList.add(task);
    }
    
    public void removeUsersTasks(){
    	usersTasksList.clear();
    }
    
    public List getTasksByUserId(User user){
		return usersTasksList;
    }

    public void addTask(Task task){
        tasks.add(task);
    }
    
    public void addDoneTask(Task task){
        doneTasks.add(task);
    }
    
    public void addUniqueDoneTodayTask(Task task){
    	Date taskDate = dateUtil.parseDateFromString(task.getDoneTime());
    	if(todayDoneTasks.size()>0){
    		for(Task t:todayDoneTasks){
        		if(t.getId()==task.getId()){
        			Date checktaskDate = dateUtil.parseDateFromString(t.getDoneTime());
        			if(checktaskDate.getTime()<taskDate.getTime()){
        				//добавляем задание вместо другого
        				todayDoneTasks.add(todayDoneTasks.indexOf(t), task);
        			}
        		}
        	}
    	}else todayDoneTasks.add(task);
    }
    public void addAll(List<Task> taskList){
        tasks.addAll(taskList);
    }

    public Task getById(int taskId){
        for(Task t:tasks){
            if(t.getId()==taskId){
                return t;
            }
        }
        return null;
    }
    
    public void removeDone(){
    	tasks.forEach(e -> {
    		if(e.getStatus().equals(TaskEnum.DONE_TASK))
    	        tasks.remove(e);
    	});
    }    

    public void removeTask(Task task){
    	tasks.forEach(e -> {
    	    if(e.equals(task))
    	        tasks.remove(e);
    	});
    }

    public List<Task> getByUserId(int userId){
        List<Task>taskList = new CopyOnWriteArrayList<>();
        for(Task t:tasks){
            if(t.getUserId()==userId){
                taskList.add(t);
            }
        }
        return taskList;
    }

    public void updateTask(Task task){
        tasks.forEach(e -> {
    	    if(e.getId()==task.getId())
    	        setTask(e, task);
    	});
    }
    
    private void setTask(Task set, Task task){
    	set.setAddress(task.getAddress());
    	set.setAddressId(task.getAddressId());
    	set.setBody(task.getBody());
    	set.setComments(task.getComments());
    	set.setCreated(task.getCreated());
    	set.setDoneTime(task.getDoneTime());
    	set.setImportance(task.getImportance());
    	set.setOrgName(task.getOrgName());
    	set.setStatus(task.getStatus());
    	set.setType(task.getType());
    	set.setUserId(task.getUserId());
    }

    public void addUnique(List<Task>taskList){
        for(Task t:taskList){
            if(!tasks.contains(t)){
                tasks.add(t);
            }
        }
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void updateTask(String status, int id){
        for(Task t:tasks){
            if(t.getId()==id){
                t.setStatus(status);
            }
        }
    }

    public void removeAll(){
        if(tasks.size()>0){
            tasks.clear();
        }
    }
    
    public void removeAllDone(){
        if(doneTasks.size()>0){
        	doneTasks.clear();
        }
    }
    
    public void removeAllTodayDone(){
        if(todayDoneTasks.size()>0){
        	todayDoneTasks.clear();
        }
    }

    public int getMaxId(){
        int max = 0;
        for(Task t:tasks){
            if(t.getId()>max){
                max=t.getId();
            }
        }
        return max;
    }

    private TasksManager(){
    	System.out.println("create new taskManager");
        tasks = new CopyOnWriteArrayList<>();
        doneTasks = new CopyOnWriteArrayList<>();
        todayDoneTasks = new CopyOnWriteArrayList<>();
        usersTasksList = new CopyOnWriteArrayList<>();
        connectionDB.getConnection();
    }
    
    public List<Task>tasksNotDone(){
    	List<Task>taskND = new CopyOnWriteArrayList<>();
    	for(Task t:tasks){
    		if(!t.getStatus().equals(TaskEnum.DONE_TASK)){
    			taskND.add(t);
    		}
    	}
    	return taskND;
    }
}

