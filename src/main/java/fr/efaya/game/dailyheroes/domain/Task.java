package fr.efaya.game.dailyheroes.domain;

import fr.efaya.game.dailyheroes.controller.api.TaskWebServiceController;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
@Document(collection = "Tasks")
public class Task {
    public enum STATE {todo, tovalidate, done}

    @Id
    private String id;

    @NotNull
    private String dashboardId;

    @NotNull
    private String name;

    @NotNull
    private STATE status = STATE.todo;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer complexity;

    private TaskWebServiceController.TASK_TYPE category;
    private String assignedTo;
    private String notes;
    private Date creationDate;
    private Date dueDate;
    private TaskWebServiceController.TASK_REDUNDANCY redundancy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(String dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public STATE getStatus() {
        return status;
    }

    public void setStatus(STATE status) {
        this.status = status;
    }

    public Integer getComplexity() {
        return complexity;
    }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity;
    }

    public TaskWebServiceController.TASK_TYPE getCategory() {
        return category;
    }

    public void setCategory(TaskWebServiceController.TASK_TYPE category) {
        this.category = category;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public TaskWebServiceController.TASK_REDUNDANCY getRedundancy() {
        return redundancy;
    }

    public void setRedundancy(TaskWebServiceController.TASK_REDUNDANCY redundancy) {
        this.redundancy = redundancy;
    }
}
