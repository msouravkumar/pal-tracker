package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimeEntry {

    private List<Long> idList = new ArrayList<>();
    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof TimeEntry)) return false;
        TimeEntry timeEntry = (TimeEntry) o;
        return getId() == timeEntry.getId() &&
                getProjectId() == timeEntry.getProjectId() &&
                getUserId() == timeEntry.getUserId() &&
                getHours() == timeEntry.getHours() &&
                getDate().equals(timeEntry.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProjectId(), getUserId(), getDate(), getHours());
    }

    public TimeEntry(long timeEntryId, long projectId, long userId, LocalDate parse, int i) {
        this.id =timeEntryId;
        this.projectId=projectId;
        this.userId=userId;
        this.date=parse;
        this.hours=i;
    }

    public TimeEntry(long projectId, long userId, LocalDate parse, int i) {
        //this.timeEntryId = generatedId();
        this.projectId=projectId;
        this.userId=userId;
        this.date=parse;
        this.hours=i;
    }

    public TimeEntry() {

    }

    public long generatedId(){
        if(idList.isEmpty()){
            id =1L;
        } else{
            id = idList.get(idList.size()-1)+1;
        }
        idList.add(id);
        System.out.println("test id"+ id);
        return id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
