package entities;

public class Comment {
    private int id;
    private String ts;
    private String body;
    private int userId;
    private int taskId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Comment(String ts, String body) {
        this.ts = ts;
        this.body = body;
    }

    public Comment(int id, String ts, String body, int userId, int taskId) {
        this.id = id;
        this.ts = ts;
        this.body = body;
        this.userId = userId;
        this.taskId = taskId;
    }
    

    public Comment(String ts, String body, int userId, int taskId) {
        this.ts = ts;
        this.body = body;
        this.userId = userId;
        this.taskId = taskId;
    }

	@Override
	public String toString() {
		return "Comment [id=" + id + ", ts=" + ts + ", body=" + body + ", userId=" + userId + ", taskId=" + taskId
				+ "]";
	}
    
    
}