package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    private final Map<Long,TimeEntry> entity;
    public InMemoryTimeEntryRepository(){
        this.entity = new HashMap<>();
    }
    private long id = 0L;
    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        //entity.put(timeEntry.getTimeEntryId(), timeEntry);
        //Long entityId = entity.size()+1L;
        Long entityId = ++id;
        timeEntry.setId(entityId);
        entity.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(Long id) {
        return entity.get(id);
    }

    @Override
    public List<TimeEntry> list(){
        List<TimeEntry> timeEntryList = new ArrayList<>();
        timeEntryList.addAll(entity.values());
        return timeEntryList;
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        if(entity.containsKey(id)){
            timeEntry.setId(id);
            entity.put(id,timeEntry);
            return timeEntry;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        entity.remove(id);

    }

    /*@Override
    public void deleteKeepsTrackOfLatestIdProperly() {

    }*/
}
