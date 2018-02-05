package managers;

import entities.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommentsManager {
    Comment comment;
    List<Comment>comments;
    List<Comment>doneComments;
    public static final CommentsManager INSTANCE = new CommentsManager();

    private CommentsManager(){
    	doneComments = new CopyOnWriteArrayList<>();
        comments = new CopyOnWriteArrayList<>();
    }
    
    public void addDoneComment(Comment comment){
    	boolean added = false;
    	for(Comment c:doneComments){
    		if(c.getTaskId()==comment.getTaskId()){
    			added = true;
    		}
    	}
    	if(added){
    		for(Comment c:doneComments){
        		if(c.getTaskId()==comment.getTaskId()){
        			c.setTs(comment.getTs());
        			c.setBody(comment.getBody());
        		}
        	}
    	}else{
    		doneComments.add(comment);
    	}
    }
    
    public List<Comment> getDoneComments() {
        return doneComments;
    }
    
    public void removeDoneComments(){
    	doneComments.clear();
    }

    public Comment getComment() {
        return comment;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void addAll(List<Comment> commentList){
        comments.addAll(commentList);
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void removeAll(){
        if(comments.size()>0){
            comments.clear();
        }
    }

    public List<Comment> getCommentsByTaskId(int taskId){
        List<Comment> commentList = new ArrayList<>();
        for(Comment c:comments){
            if(c.getTaskId()==taskId){
                commentList.add(c);
            }
        }
        return commentList;
    }
}
