package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {
    TimeEntry create(final TimeEntry timeEntry);


    TimeEntry find(final Long id);

    List<TimeEntry> list();


    TimeEntry update(final Long id, final TimeEntry timeEntry);


    void delete(final Long id);


    //void deleteKeepsTrackOfLatestIdProperly();
}
